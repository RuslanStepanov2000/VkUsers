import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.responses.GetFollowersResponse;

import java.util.List;
import java.io.*;

public class Followers_vk {
	public static void main(String[] args) throws InterruptedException {
		TransportClient transportClient = new HttpTransportClient();
		VkApiClient vk = new VkApiClient(transportClient);

		UserActor ua = new UserActor(118971335,
				"a3b78eb8a3b78eb8a3b78eb8a9a3dfecf9aa3b7a3b78eb8ff3e539cb25ab8ee9dcea55e");
	try {
			GetFollowersResponse g=vk.users().getFollowers(ua).userId(118971335).execute();
			List<Integer> lf=g.getItems();

			for (int i = 0; i < lf.size(); i++) {
				Integer b = lf.get(i);
				Thread.sleep(500);
				//System.out.println(b);
				VkUsers h1 = new VkUsers(118971335,
						"a3b78eb8a3b78eb8a3b78eb8a9a3dfecf9aa3b7a3b78eb8ff3e539cb25ab8ee9dcea55e");

				String c = String.valueOf(b);
				//System.out.println(h1.getVkUser(c).getFirstName());

				//Запись в файл
					String FriendsInfo = b + "," + String.valueOf(b) + "," + h1.getVkUser(c).getFirstName() + System.getProperty("line.separator");//System.getProperty("line.separator") новая строка
					try (FileWriter writer = new FileWriter("Followers_vk.txt", true)) {
						// запись всей строки
						writer.write(FriendsInfo);
						writer.flush();
					}

				catch(IOException ex){
					System.out.println(ex.getMessage());
				}
			}

			

		}
		catch (ApiException | ClientException e) {
			e.printStackTrace();
		}

	}

}
