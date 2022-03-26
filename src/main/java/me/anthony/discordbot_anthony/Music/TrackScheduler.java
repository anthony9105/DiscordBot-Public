package me.anthony.discordbot_anthony.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter
{
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track)
    {
        if (!this.player.startTrack(track, true))
        {
            this.queue.offer(track);
        }
    }

    public void nextTrack()
    {
        System.out.println("next???");
        this.player.startTrack(this.queue.poll(), false);
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        super.onPlayerPause(player);
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        super.onPlayerResume(player);
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        super.onTrackStart(player, track);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext)
        {
            nextTrack();
        }
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        super.onTrackException(player, track, exception);
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        super.onTrackStuck(player, track, thresholdMs);
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs, StackTraceElement[] stackTrace) {
        super.onTrackStuck(player, track, thresholdMs, stackTrace);
    }

    @Override
    public void onEvent(AudioEvent event) {
        super.onEvent(event);
    }
}