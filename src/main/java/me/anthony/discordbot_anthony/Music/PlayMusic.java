package me.anthony.discordbot_anthony.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;


public class PlayMusic extends ListenerAdapter
{
    public static String identifier;
    public static AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    public static AudioPlayer player = playerManager.createPlayer();

    public static TrackScheduler trackScheduler = new TrackScheduler(player);

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
            return;

        String[] messageRecieved = event.getMessage().getContentRaw().split(" ");

        if (messageRecieved[0].equalsIgnoreCase("^play"))
        {
            if (messageRecieved.length < 2)
            {
                event.getChannel().sendMessage("Please enter the YouTube video link.\nExample: ^play youtube_video_link_here").queue();
                return;
            }

            // connecting to voice channel
            if (event.getMember().getVoiceState() == null)
            {
                event.getChannel().sendMessage("You have to be in a voice channel for this to work.").queue();
                return;
            }
            VoiceChannel theVoiceChannel = event.getMember().getVoiceState().getChannel();

            AudioSourceManagers.registerRemoteSources(playerManager);

            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.setSendingHandler(new MyAudioSendHandler(player)); /////

            player.addListener(trackScheduler);

            identifier = messageRecieved[1];

            playerManager.loadItem(identifier, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    event.getChannel().sendMessage("Loading track.").queue();
                    trackScheduler.queue(track);
                    event.getChannel().sendMessage("Playing...").queue();
                    //trackScheduler.onTrackStart(player, track);
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    event.getChannel().sendMessage("Loading playlist.").queue();
                    for (AudioTrack track : playlist.getTracks()) {
                        trackScheduler.queue(track);
                    }
                }

                @Override
                public void noMatches() {
                    // Notify the user that we've got nothing
                    event.getChannel().sendMessage("No matches.").queue();
                }

                @Override
                public void loadFailed(FriendlyException throwable) {
                    event.getChannel().sendMessage("Load failed.").queue();
                    // Notify the user that everything exploded
                }
            });

            audioManager.openAudioConnection(theVoiceChannel);

        }
    }
}
