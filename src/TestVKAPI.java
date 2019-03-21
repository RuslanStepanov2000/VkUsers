import com.vk.api.sdk.actions.Groups;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.groups.GroupField;

import java.util.ArrayList;
//import com.vk.api.sdk.objects.account.UserSettings;

public class TestVKAPI {

	public static void main(String[] args) throws InterruptedException {
		TransportClient transportClient = new HttpTransportClient();
		VkApiClient vk = new VkApiClient(transportClient);
		UserActor ua = new UserActor(118971335,
				"a3b78eb8a3b78eb8a3b78eb8a9a3dfecf9aa3b7a3b78eb8ff3e539cb25ab8ee9dcea55e");
		/*String clientSecret="RJ1nteBYgFQqRtflVZkj";
		ServiceClientCredentialsFlowResponse authResponse = null;
		try {
			authResponse = vk.oauth().serviceClientCredentialsFlow(6840897, clientSecret).execute();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}


		ServiceActor actor = new ServiceActor(6840897, clientSecret, authResponse.getAccessToken());
		*/
		ArrayList<GroupField> gfs = new ArrayList<>();
		gfs.add(GroupField.SCREEN_NAME);
		gfs.add(GroupField.DESCRIPTION);
		gfs.add(GroupField.MEMBERS_COUNT);
		gfs.add(GroupField.ACTIVITY);

		//79138567
		int id=79138567; String id_str=String.valueOf(id);
		try {
			Groups groups=new Groups(vk);
			System.out.println(groups.getById(ua).fields(gfs).executeAsString());

		}  catch (Throwable  e) {
			e.printStackTrace();
		}
	}
}





