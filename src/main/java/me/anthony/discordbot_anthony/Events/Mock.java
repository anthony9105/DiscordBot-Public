/**
 *  Mock message command.
 *
 *  When a user sends the message "^mock", the next word after it has its letter case
 *  changed to alternating order.  (e.g. blue -> bLuE).
 */

package me.anthony.discordbot_anthony.Events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Mock extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        // if the message recieved is from a bot, exit (ignore)
        if (event.getAuthor().isBot())
            return;

        String[] messageRecieved = event.getMessage().getContentRaw().split(" ");

        if(messageRecieved[0].equalsIgnoreCase("^mock"))
        {
            try
            {
                String mockMessage = messageRecieved[1];
                char newChar = Character.toLowerCase(mockMessage.charAt(0));    // set the first character to lowercase
                mockMessage = mockMessage.substring(0, 0) + newChar + mockMessage.substring(1);

                // change the case of the rest of the characters in the String (alternating order)
                for(int i=1; i < messageRecieved[1].length(); i++)
                {
                    if (Character.isUpperCase(mockMessage.charAt(i-1)))
                    {
                        newChar = Character.toLowerCase(mockMessage.charAt(i));
                        mockMessage = mockMessage.substring(0, i) + newChar + mockMessage.substring(i + 1);
                    }
                    else
                    {
                        newChar = Character.toUpperCase(mockMessage.charAt(i));
                        mockMessage = mockMessage.substring(0, i) + newChar + mockMessage.substring(i + 1);
                    }
                }

                event.getChannel().sendMessage(mockMessage).queue();
            }
            catch(IllegalArgumentException e)   // if the user entered in ^mock but nothing else after it
            {
                event.getChannel().sendMessage("empty or invalid message to mock").queue();
            }
        }
    }
}
