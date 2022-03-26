package me.anthony.discordbot_anthony.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class StopMusic extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
            return;

        String[] messageRecieved = event.getMessage().getContentRaw().split(" ");

        if (messageRecieved[0].equalsIgnoreCase("^stop")) {
            // connecting to voice channel
            VoiceChannel theVoiceChannel = event.getMember().getVoiceState().getChannel();

            AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(playerManager);
            AudioPlayer player = playerManager.createPlayer();

            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.setSendingHandler(new MyAudioSendHandler(player)); /////

            TrackScheduler trackScheduler = new TrackScheduler(player);
            player.addListener(trackScheduler);

            event.getChannel().sendMessage("Stopping the song/playlist...").queue();
            playerManager.shutdown();
        }
    }
}
