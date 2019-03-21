import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.GetResponse;
import com.vk.api.sdk.queries.groups.GroupField;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;






public class VkGroups {

	public static void main(String[] args) {
		TransportClient transportClient = new HttpTransportClient();
		VkApiClient vk = new VkApiClient(transportClient);
		//https://oauth.vk.com/authorize?client_id=6840897&display=page&redirect_uri=http://vk.com&scope=offline&response_type=token&v=5.92&state=123456

		//Authorization();
		UserActor ua = new UserActor(6840897, "4a776ac6caf53516ec7e67125c3c434df5fb1851955c63f41e145dd8503c81e2f58387f9862c27ea4e906");

		try {
			GetResponse getResponse = vk.groups().get(ua).userId(118971335).count(1000).execute();

			List<Integer> groupIDList = getResponse.getItems();

			ArrayList<GroupField> gfs = new ArrayList<>();
			gfs.add(GroupField.SCREEN_NAME);
			gfs.add(GroupField.ACTIVITY);
			//gfs.add(GroupField.DESCRIPTION);
			gfs.add(GroupField.MEMBERS_COUNT);
			gfs.add(GroupField.TYPE);


			for (int gId : groupIDList) {
				List<GroupFull> gr = vk.groups().getById(ua).
						groupId(String.valueOf(gId)).fields(gfs).execute();
				//System.out.println("gId=" + gId +", gr count = "+gr.size());
				Thread.sleep(500);
				/*if(gr!=null&&gr.size()>0)
					System.out.println("name: "+gr.get(0).getName()+", description: "
							+gr.get(0).getDescription()+", member count: "+gr.get(0).getMembersCount());*/
				//Запись в файл

				if (gr != null && gr.size() > 0) {
					String GroupsInfo = ("gId=" + gId + ", name: " + gr.get(0).getName() + ", activity: " + gr.get(0).getActivity() + ", member count: " + gr.get(0).getMembersCount() + System.getProperty("line.separator"));//System.getProperty("line.separator") новая строка
					try (FileWriter writer = new FileWriter("Groups_vk.txt", true)) {
						// запись всей строки
						writer.write(GroupsInfo);
						writer.flush();
					} catch (IOException ex) {
						System.out.println(ex.getMessage());
					}
				}
				//Объеденил в блок записи выше
				/*String GroupsInfo = ("gId=" + gId +", gr count = "+gr.size()+","+ System.getProperty("line.separator"));//System.getProperty("line.separator") новая строка
				try (FileWriter writer = new FileWriter("Groups_vk.txt", true)) {
					// запись всей строки
					writer.write(GroupsInfo);
					writer.flush();
				}
				catch(IOException ex){
					System.out.println(ex.getMessage());
				}
				*/
			}
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

