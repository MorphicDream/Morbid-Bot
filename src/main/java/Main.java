import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main implements EventListener {

    private static List<String> quotes = new ArrayList<>();
    private static String nonRepeat;

    public Main() {
        quotes.addAll(Arrays.asList(
                "Without people there is no money",
                "If everyone died, who would be left to mourn?",
                "There can't be light without darkness",
                "Bones are difficult to get rid of, but not impossible",
                "You only need one kidney to stay alive, which one is up to you",
                "Never freeze eyeballs if you want to use them for anything",
                "Hair and nails keep growing for a short time after death",
                "Hello darkness my old friend...",
                "Death is something we all will share, in the end",
                "Funerals are not for the dead, they are for the living",
                "This is life, it will be over before you know it"));
    }

    public static void main(String[] args) {
        JDABuilder jdaBuilder = JDABuilder.createDefault("TOKEN HERE");
        try {
            jdaBuilder.addEventListeners(new Main()).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent(@Nonnull GenericEvent genericEvent) {
        if (genericEvent instanceof MessageReceivedEvent) {
            MessageReceivedEvent event = (MessageReceivedEvent) genericEvent;
            if (event.getMessage().getContentRaw().toLowerCase().contains("!morbid") && !event.getAuthor().isBot()) {
                String random = getRandomString();
                if (random.equalsIgnoreCase(nonRepeat)) {
                    List<String> repeatable = new ArrayList<>(quotes);
                    repeatable.remove(random);
                    random = getRandomString(repeatable);
                }
                event.getChannel().sendMessage(random).submit();
                nonRepeat = random;
            }
        }
    }

    private static String getRandomString() {
        return getRandomString(quotes);
    }

    private static String getRandomString(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
