/**
 *  Anthony's Discord Bot
 *  created by: Anthony Liscio
 *  December 2021
 *
 */

package me.anthony.discordbot_anthony;

import me.anthony.discordbot_anthony.Events.*;
import me.anthony.discordbot_anthony.Info.GeneralCommandHelp;
import me.anthony.discordbot_anthony.Info.UserInfo;
import me.anthony.discordbot_anthony.Music.LeaveVoiceChannel;
import me.anthony.discordbot_anthony.Music.PlayMusic;
import me.anthony.discordbot_anthony.Music.SkipSong;
import me.anthony.discordbot_anthony.Music.StopMusic;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main
{
    private static final String bot_token = "";

    public static void main(String[] args) throws LoginException
    {
       JDA jda = JDABuilder.createDefault(bot_token).build();

       jda.addEventListener(new HelloEvent());
       jda.addEventListener(new SortList());
       jda.addEventListener(new Mock());
       jda.addEventListener(new UserInfo());
       jda.addEventListener(new GeneralCommandHelp());
       jda.addEventListener(new Mute());
       jda.addEventListener(new PlayMusic());
       jda.addEventListener(new LeaveVoiceChannel());
       jda.addEventListener(new StopMusic());
       jda.addEventListener(new SkipSong());
    }
}
