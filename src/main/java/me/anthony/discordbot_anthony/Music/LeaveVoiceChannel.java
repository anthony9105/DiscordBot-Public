package me.anthony.discordbot_anthony.Music;

import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class LeaveVoiceChannel extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
            return;

        String[] messageRecieved = event.getMessage().getContentRaw().split(" ");

        if (messageRecieved[0].equalsIgnoreCase("^leave"))
        {
            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.closeAudioConnection();
        }
    }
}
