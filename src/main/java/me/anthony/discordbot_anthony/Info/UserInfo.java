package me.anthony.discordbot_anthony.Info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class UserInfo extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        String[] messageRecieved = event.getMessage().getContentRaw().split(" ");

        if(event.getAuthor().isBot())
            return;

        if(messageRecieved[0].equalsIgnoreCase("^info"))
        {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.blue);
            eb.setImage(event.getAuthor().getAvatarUrl());
            eb.setTitle(event.getAuthor().getName());
            eb.setTimestamp(event.getGuild().getTimeCreated());
            eb.setFooter("Date this discord channel was created: ");
            eb.addField("Account created: ", ""+event.getAuthor().getTimeCreated(), false);
            eb.addField("Time boosted: ",""+event.getMessage().getMember().getTimeBoosted(), false);
            eb.addField("Playing: ",""+event.getMessage().getMember().getActivities(), false);
            //eb.addField("Roles: ",""+event.getMessage().getMember().getRoles().toString(),false);
            eb.addField("Joined server: ", ""+ event.getMessage().getMember().getTimeJoined(), false);
            eb.addField("Online status: ", ""+ event.getMessage().getMember().getOnlineStatus(), false);
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
