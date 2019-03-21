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
				"60698b8a60698b8a60698b8ae56001e9cb6606960698b8a3c3b281e937fa7dcb7b50104");
	try {
			GetFollowersResponse g=vk.users().getFollowers(ua).userId(118971335).execute();
			List<Integer> lf=g.getItems();

			for (int i = 0; i < lf.size(); i++) {
				Integer b = lf.get(i);
				Thread.sleep(500);
				//System.out.println(b);
				VkUsers h1 = new VkUsers(118971335,
						"60698b8a60698b8a60698b8ae56001e9cb6606960698b8a3c3b281e937fa7dcb7b50104");

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
