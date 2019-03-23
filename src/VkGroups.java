import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.GetResponse;
import com.vk.api.sdk.queries.groups.GroupField;
import com.vk.api.sdk.queries.users.UserField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class VkGroups {

	public static void main(String[] args) {
		TransportClient transportClient = new HttpTransportClient();
		VkApiClient vk = new VkApiClient(transportClient);
		//https://oauth.vk.com/authorize?client_id=6840897&display=page&redirect_uri=http://vk.com&scope=offline&response_type=token&v=5.92&state=123456

		UserActor ua = new UserActor(6840897, "4a776ac6caf53516ec7e67125c3c434df5fb1851955c63f41e145dd8503c81e2f58387f9862c27ea4e906");


		try {
            File file = new File("Input.txt");

            FileReader fr = new FileReader(file);

            BufferedReader reader = new BufferedReader(fr);

            // считаем сначала первую строку. Пока файл не пуст записываем в файл количество профессиональных групп и общее их количество у конкретного пользовотеля
			String line = reader.readLine();
            while (line != null) {
				System.out.println(line);


			try {
				ArrayList<UserField> ufs = new ArrayList<>();

					String userId= vk.users().get(ua).;


					GetResponse getResponse = vk.groups().get(ua).userId().count(50).execute();
					List<Integer> groupIDList = getResponse.getItems();


					ArrayList<GroupField> gfs = new ArrayList<>();
					gfs.add(GroupField.ACTIVITY);


					for (int gId : groupIDList) {
						List<GroupFull> gr = vk.groups().getById(ua).
								groupId(String.valueOf(gId)).fields(gfs).execute();
						Thread.sleep(500);
						//Запись в файл

						if (gr != null && gr.size() > 0) {
							String GroupsInfo = (gId + ", name: " + gr.get(0).getName() + ", activity: " + gr.get(0).getActivity() + ", member count: " + gr.get(0).getMembersCount() + System.getProperty("line.separator"));//System.getProperty("line.separator") новая строка
							try (FileWriter writer = new FileWriter("FriendsGroups\\Groups_vk.txt", true)) {
								// запись всей строки
								writer.write(GroupsInfo);
								writer.flush();
							} catch (IOException ex) {
								System.out.println(ex.getMessage());
							}
						}
					}
				} catch (ApiException | ClientException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				line = reader.readLine().toString();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

