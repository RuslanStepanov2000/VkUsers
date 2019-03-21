import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupsArray;
import com.vk.api.sdk.objects.users.responses.GetSubscriptionsResponse;
import com.vk.api.sdk.queries.groups.GroupsGetQueryWithExtended;

import java.io.FileWriter;
import java.io.IOException;

public class InteregtingPages {

    public static void main(String[] args) throws InterruptedException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor ua = new UserActor(6840897,
                "a3b78eb8a3b78eb8a3b78eb8a9a3dfecf9aa3b7a3b78eb8ff3e539cb25ab8ee9dcea55e");


        try {

            //UsersGetSubscriptionsQuery ugsq = vk.users().getSubscriptions(ua);
            GetSubscriptionsResponse ugsq = vk.users().getSubscriptions(ua).userId(118971335).count(50).execute();
            GroupsArray ugsqList = ugsq.getGroups();
            GroupsGetQueryWithExtended groupActivity=vk.groups().getExtended(ua);


            //Создаем массив из id страниц
            Object[] InterestingPagesArray = ugsqList.getItems().toArray();
            for (int i = 0; i < ugsqList.getCount(); i++) {
                System.out.println(InterestingPagesArray[i]);


                try (FileWriter writer = new FileWriter("InterestingPages_vk.txt", true)) {
                    // запись всей строки
                    String toWrite=String.valueOf(InterestingPagesArray[i])+System.getProperty("line.separator");
                    writer.write(toWrite);
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}


