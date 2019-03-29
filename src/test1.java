import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.GetResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.groups.GroupField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class test1 {

    public static void main(String[] args) {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        //https://oauth.vk.com/authorize?client_id=6840897&display=page&redirect_uri=http://vk.com&scope=offline&response_type=token&v=5.92&state=123456

        //Ua-аунтентификатор
        UserActor ua = new UserActor(6840897, "4a776ac6caf53516ec7e67125c3c434df5fb1851955c63f41e145dd8503c81e2f58387f9862c27ea4e906");
        //User-для иного испольования
        VkUsers user = new VkUsers(494345417, "4a776ac6caf53516ec7e67125c3c434df5fb1851955c63f41e145dd8503c81e2f58387f9862c27ea4e906");


        try {
            File file = new File("Input.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);



            // считаем сначала первую строку. Пока файл не пуст записываем в файл количество профессиональных групп и общее их количество у конкретного пользовотеля
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);

                try {
                    //String c = line.substring(15); Если брать страницы с полныой ссылкой, в метод ниже подаются айди либо непосредственно имя страницы
                    UserXtrCounters ufs=new UserXtrCounters();
                    ufs=user.getVkUser(line);

                    GetResponse getResponse = vk.groups().get(ua).userId(ufs.getId()).count(50).execute();
                    List<Integer> groupIDList = getResponse.getItems();

                    ArrayList<GroupField> gfs = new ArrayList<>();
                    gfs.add(GroupField.ACTIVITY);



                    int usefulGrops=0;

                    for (int gId : groupIDList) {

                        List<GroupFull>gr = vk.groups().getById(ua).
                                groupId(String.valueOf(gId)).fields(gfs).execute();
                        if (gr.get(0).getActivity() != null) {
                            byte[] converter = gr.get(0).getActivity().getBytes("UTF-8");
                            String check_activity = new String(converter, "cp1251");
                            Thread.sleep(500);
                            if (check_activity.contains("Программирование") | check_activity.equalsIgnoreCase("Наука")) {
                                usefulGrops++;
                            }
                        }
                    }
                    String to_write=(ufs.getFirstName()+" "+ufs.getLastName()+"   Total subscribes-"
                            +getResponse.getCount()+"  Useful subscribes-"+usefulGrops+
                            System.getProperty("line.separator"));


                    try (FileWriter writer = new FileWriter("FriendsGroups\\Groups_vk.txt", true)) {
                        // запись всей строки
                        writer.write(to_write);
                        writer.flush();
                        System.out.println("Successful write");
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    catch (NullPointerException ex) {
                    ex.printStackTrace();
                    }






                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ApiException e1) {
                    e1.printStackTrace();
                } catch (ClientException e1) {
                    e1.printStackTrace();
                } catch (NullPointerException e1){
                    e1.printStackTrace();
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

