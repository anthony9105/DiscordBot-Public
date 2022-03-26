package me.anthony.discordbot_anthony.Music;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static me.anthony.discordbot_anthony.Music.PlayMusic.*;

public class SkipSong extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
            return;

        String[] messageRecieved = event.getMessage().getContentRaw().split(" ");

        if(messageRecieved[0].equalsIgnoreCase("^skip"))
        {
            player.stopTrack();
            event.getChannel().sendMessage("Skipping this song...").queue();
            trackScheduler.nextTrack();
        }
    }
}
