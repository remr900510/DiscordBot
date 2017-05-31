import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

/**
 * Created by Raul on 5/29/2017.
 */
public class MessageListener extends ListenerAdapter{
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        JDA jda = event.getJDA();
        long responseNumber = event.getResponseNumber();
        User author = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel commandChannel = message.getChannel();
        MessageChannel channel = event.getJDA().getTextChannelById("318766074495631370");
        String msg = message.getContent();
        boolean bot = author.isBot();

        if (event.isFromType(ChannelType.TEXT))
        {
            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();
            Member member = event.getMember();
            String name;

            if (message.isWebhookMessage())
            {
                name = author.getName();
            }
            else
            {
                name = member.getEffectiveName();
            }
            //System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
        }
        else if (event.isFromType(ChannelType.PRIVATE))
        {
            PrivateChannel privateChannel = event.getPrivateChannel();
            //System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
        }
        else if (event.isFromType(ChannelType.GROUP))
        {
            Group group = event.getGroup();
            //String groupName = group.getName() != null ? group.getName() : "";
            //System.out.printf("[GRP: %s]<%s>: %s\n", groupName, author.getName(), msg);
        }
        if ((msg.equals("!porn")) && ((channel.getJDA().getTextChannelById("318766074495631370") == commandChannel)))
        {
            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
            try{
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet("https://www.reddit.com/r/FoodPorn/random.json");
                HttpResponse response = client.execute(request);
                BufferedReader rd = new BufferedReader
                (
                        new InputStreamReader
                        (
                                response
                                .getEntity()
                                .getContent()
                        )
                );
                String line = "";
                while ((line = rd.readLine()) != null)
                {
                    if ( !line.startsWith("["))
                    {
                        channel.sendMessage("Busy! Try again :c").queue();
                        System.out.println("Too Many Requests. Error 429");
                    }
                    else{
                        JSONArray jason = new JSONArray(line);
                        JSONObject obj = jason.getJSONObject(0);
                        JSONObject data = obj.getJSONObject("data");
                        JSONArray children = data.getJSONArray("children");
                        JSONObject index = children.getJSONObject(0);
                        JSONObject dataIndex = index.getJSONObject("data");
                        String url = dataIndex.getString("url");
                        System.out.println("[URL] - "+url);
                        if (url.contains("?")){
                            url = url.split("\\?", 2)[0];
                            System.out.println("[URLFormat] - "+url);
                        }
                        channel.sendMessage(url).queue();
                    }
                }
            }
            catch (ClientProtocolException e)
            {
                System.out.println("-------------------------------------------ClientProtocolException");
                e.printStackTrace();

            }
            catch (IOException e)
            {
                System.out.println("2------------------------------------------IOException");
                e.printStackTrace();
            }
        }
    }
}
