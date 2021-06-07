package me.morphicdream.morbid;

import net.dv8tion.jda.api.entities.User;

import java.util.TimerTask;

public class CooldownTimer extends TimerTask {

    private final User user;
    private final Long time;

    CooldownTimer(User user, Long time){
        this.user = user;
        this.time = time;
    }

    private User getUser(){
        return user;
    }

    @Override
    public void run() {
        long timeout = time + 10000;
        if(System.currentTimeMillis() > timeout){
            Main.removeUser(getUser());
            this.cancel();
        }
    }
}
