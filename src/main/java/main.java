import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Raul on 5/29/2017.
 */
public class main extends ListenerAdapter {
    public static void main(String[] args) {
        Game game = Game.of("Destiny");
        try{

            JDA jda = new
                    JDABuilder(AccountType.BOT)
                    .setToken("MzE4NTgyNzk2MzYyNzExMDQx.DA30zg.h_S6Hm2anWTjc9QwTRklmntvLdU")
                    .setGame(game)
                    .setAutoReconnect(true)
                    .addEventListener(new MessageListener())
                    .buildBlocking();
        }catch (LoginException | InterruptedException | RateLimitedException e){
            e.printStackTrace();
        }
    }
}
