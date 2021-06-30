package me.morphicdream.morbid;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.*;

public class Main implements EventListener {

    private static final List<String> quotes = new ArrayList<>();
    private static String nonRepeat;
    private static final List<User> users = new ArrayList<>();
    private static final String token = "TOKEN HERE PLS";

    private Main() {
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
                "This is life, it will be over before you know it",
                "In your last moments who will you think of?",
                "Life is like a book, after a few pages it ends",
                "Death is a comfort to the old and often a surprise to the young",
                "Without your skin, everything would fall out",
                "The dark exists to hide the bodies",
                "Love between two people ends when they are both dead",
                "Depression is depressing",
                "During laser eye surgery you can smell your eyes burning",
                "Most sitcom laugh tracks were recorded in the 1950's, I wonder how many of them are alive...",
                "Bodies take between 24 and 48 hours to 'decompress' after death"));
    }

    public static void main(String[] args) {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        try {
            jdaBuilder.setActivity(Activity.watching("!morbid")).addEventListeners(new Main()).build();
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
                if(users.contains(event.getMessage().getAuthor())){
                    PrivateChannel privateChannel = event.getMessage().getAuthor().openPrivateChannel().complete();
                    privateChannel.sendMessage("There is a cool down for the morbid command of around 10 seconds").submit();
                    return;
                }
                if (random.equalsIgnoreCase(nonRepeat)) {
                    List<String> repeatable = new ArrayList<>(quotes);
                    repeatable.remove(random);
                    random = getRandomString(repeatable);
                }
                event.getChannel().sendMessage(random).submit();
                nonRepeat = random;
                addUser(event.getMessage().getAuthor());
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

    private static void addUser(User user){
        users.add(user);
        CooldownTimer timer = new CooldownTimer(user, System.currentTimeMillis());
        Timer time = new Timer();
        time.schedule(timer, 20, 20);
    }

    static void removeUser(User user){
        users.remove(user);
    }

}
