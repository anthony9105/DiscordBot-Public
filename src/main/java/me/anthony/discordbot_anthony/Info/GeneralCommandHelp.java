package me.anthony.discordbot_anthony.Info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GeneralCommandHelp extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
            return;

        String[] messageRecieved = event.getMessage().getContentRaw().split(" ");

        if (messageRecieved[0].equalsIgnoreCase("^help"))
        {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Anthony's Bot Commands");
            eb.setColor(Color.green);
            eb.addField("^info","Provides general info about your discord account" ,false);
            eb.addField("^sort {type} {list of numbers}","Select 'integer' or 'decimal' type and " +
                    "enter a list of numbers seperated by commas and no spaces" ,false);
            eb.addField("^mock {message}","" ,false);
            eb.addField("^hello","" ,false);
            eb.addField("^mute {@user}", "", false);
            eb.addField("^play {YouTube video link or playlist link}", "", true);
            eb.addField("^skip", "skips to the next song (when listening to a playlist)", true);
            eb.addField("^stop", "completely stops the song or playlist", true);
            eb.addField("^leave", "removes the bot from the voice channel", true);
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
