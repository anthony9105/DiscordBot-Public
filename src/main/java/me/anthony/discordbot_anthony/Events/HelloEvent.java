/**
 *  Simple hello command.
 *
 *  When a user sends the message "^hello", the bot responds with "Hi"
 */

package me.anthony.discordbot_anthony.Events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class HelloEvent extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        // if the message recieved is from a bot, exit (ignore)
        if (event.getAuthor().isBot())
            return;

        String messageSent = event.getMessage().getContentRaw();

        if (messageSent.equals("^hello"))
        {
            event.getChannel().sendMessage("Hi").queue();
        }
    }
}
