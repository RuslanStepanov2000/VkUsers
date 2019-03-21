
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.School;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;

import java.util.ArrayList;
import java.util.List;

public class VkUsers {

    private static String token =
            "a3b78eb8a3b78eb8a3b78eb8a9a3dfecf9aa3b7a3b78eb8ff3e539cb25ab8ee9dcea55e";

    private static int actorId = 6840897;

    public void setToken(String token) {
        this.token = token;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public VkUsers(int actorId, String token) {
        this.token = token;
        this.actorId = actorId;
    }

    public VkUsers() {
    }

    public List<UserXtrCounters> getVkUsers(List<String> usersId) {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);

        UserActor ua = new UserActor(actorId, token);
        ArrayList<UserField> ufs = new ArrayList<>();
        ufs.add(UserField.CITY);
        ufs.add(UserField.SCHOOLS);
        //ufs.add(UserField.SEX);
        ufs.add(UserField.EDUCATION);
        ufs.add(UserField.INTERESTS);
        ufs.add(UserField.DOMAIN);
        ufs.add(UserField.ABOUT);
        ufs.add(UserField.MAIDEN_NAME);
        ufs.add(UserField.ACTIVITIES);
        ufs.add(UserField.FOLLOWERS_COUNT);
        List<UserXtrCounters> uc = null;
        for(String s:usersId)
            System.out.println("���������� � id "+s);
        try {
            uc = vk.users().get(ua).userIds(usersId).fields(ufs).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return uc;
    }

    public UserXtrCounters getVkUser(String userId) {
        List<String> usersId = new ArrayList<>();
        usersId.add(userId);
        List<UserXtrCounters> res = getVkUsers(usersId);
        if (res!=null && res.size()>0) {
            return res.get(0);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);

        UserActor ua = new UserActor(actorId, token);
        ArrayList<UserField> ufs = new ArrayList<>();
        ufs.add(UserField.CITY);
        ufs.add(UserField.SCHOOLS);
        ufs.add(UserField.SEX);
        ufs.add(UserField.EDUCATION);
        ufs.add(UserField.INTERESTS);
        ufs.add(UserField.DOMAIN);
        ufs.add(UserField.ABOUT);
        ufs.add(UserField.MAIDEN_NAME);
        ufs.add(UserField.ACTIVITIES);
        ufs.add(UserField.FOLLOWERS_COUNT);

        try {

            List<String> l = new ArrayList<String>();
            l.add("118971335");
            l.add("10385605");
            l.add("494345417");
            l.add("k_a_z_a_n116");
            List<UserXtrCounters> uc = vk.users().get(ua).userIds(l).fields(ufs).execute();

            System.out.println(uc.get(2).getCity().getTitle());
            List<School> s = uc.get(2).getSchools();

            for (School element : s) {
                System.out.println(element.getName());
            }

            //System.out.println(uc.get(2).getSex());
            System.out.println(uc.get(3).getLastName());
            System.out.println(uc.get(3).getDomain());
            System.out.println(uc.get(2).getActivities());
            System.out.println(uc.get(2).getInterests());
            System.out.println(uc.get(2).getAbout());
            System.out.println(uc.get(2).getEducationStatus());
            System.out.println(uc.get(1).getMusic());
            System.out.println(uc.get(3).getId());
            System.out.println("���������� �����������="+uc.get(1).getFollowersCount());
            System.out.println(uc.get(2).getRelatives());
            VkUsers h=new VkUsers();
            h.personFirstName(uc);
            personSex(uc);
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }

    }
    public String personFirstName1(List<UserXtrCounters> u) {

        String d=u.get(0).getFirstName();
        return d;
    }
    public void personFirstName(List<UserXtrCounters> u) {
        for(int i=0;i<3;i++)
            System.out.println(u.get(i).getFirstName());
    }
    static void personSex(List<UserXtrCounters> u) {
        ArrayList<UserField> ufs = new ArrayList<>();
        ufs.add(UserField.SEX);
        for(int i=0;i<3;i++)
            System.out.println(u.get(i).getSex());
    }
}

