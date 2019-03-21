import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.GetResponse;
import com.vk.api.sdk.queries.groups.GroupField;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsertGroups {

	public static void main(String[] args) {
		TransportClient transportClient = new HttpTransportClient();
		VkApiClient vk = new VkApiClient(transportClient);
		String url = "jdbc:postgresql://localhost:5432/vk";
		UserActor ua = new UserActor(494345417,
				"60698b8a60698b8a60698b8ae56001e9cb6606960698b8a3c3b281e937fa7dcb7b50104");
		BufferedReader br;
		String fileName = "d.csv";
		try (Connection conn = DriverManager.getConnection(url, "postgres", "post")) {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			String line;

			while ((line = br.readLine()) != null) {
				String c = line.substring(15);
				VkUsers h1 = new VkUsers(494345417,
						"60698b8a60698b8a60698b8ae56001e9cb6606960698b8a3c3b281e937fa7dcb7b50104");
				try {
				int b = h1.getVkUser(c).getId();
				GetResponse getResponse = vk.groups().get(ua).userId(b).count(1000).execute();

				List<Integer> groupIDList = getResponse.getItems();

				ArrayList<GroupField> gfs = new ArrayList<>();
				gfs.add(GroupField.SCREEN_NAME);
				gfs.add(GroupField.DESCRIPTION);
				gfs.add(GroupField.MEMBERS_COUNT);

				for (int gId : groupIDList) {
					Statement s = conn.createStatement();
					ResultSet rs = s.executeQuery("select * from groupp where id_group=" + gId);
					if (rs.next()) {
						rs.close();
						s.close();
						continue;
					}

					List<GroupFull> gr = vk.groups().getById(ua).groupId(String.valueOf(gId)).fields(gfs).execute();
					System.out.println("gId=" + gId + ", gr count = " + gr.size());
					Thread.sleep(500);
					if (gr != null && gr.size() > 0)
						System.out.println("name: " + gr.get(0).getName() + ", description: "
								+ gr.get(0).getDescription() + ", member count: " + gr.get(0).getMembersCount());

					String t = gr.get(0).getName();
					String j = gr.get(0).getDescription();
					String sql = "insert into groupp (description, name, id_group) values ( ? , ? ,?) ";
					PreparedStatement statement = conn.prepareStatement(sql);

					statement.setString(1, j);
					statement.setString(2, t);
					statement.setInt(3, gId);

					statement.executeUpdate();

					statement.close();
					rs.close();
					s.close();
				}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		}
		catch (ApiException | ClientException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
