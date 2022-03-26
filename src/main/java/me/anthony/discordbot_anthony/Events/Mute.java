/**
 *  Mute user command.
 *
 *  When a user, who is an administrator in the discord server, (has the necessary permission)
 *  sends the message "^mute" and the next thing after it is "@user_to_be_muted", the user_to_be_muted will be muted
 *  until the command is used again (by a user with the necessary permission) to unmute the user.
 */

package me.anthony.discordbot_anthony.Events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Mute extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        // if the message recieved is from a bot, exit (ignore)
        if (event.getAuthor().isBot())
            return;

        String[] messageReceived = event.getMessage().getContentRaw().split(" ");

        if (messageReceived[0].equalsIgnoreCase("^mute"))
        {
            Member toBeMuted = event.getMessage().getMentionedMembers().get(0);
            Role muted = event.getGuild().getRolesByName("Muted", true).get(0);

            Member userWhoRequestedMute = event.getMessage().getMember();

            // Checks for: logic (won't allow a user to mute themself), if the user requesting a mute has the permission to do so,
            // and if the user requesting a mute has the permission but is trying to mute the owner (not allowed).
            if (userWhoRequestedMute == toBeMuted)
            {
                event.getChannel().sendMessage("I'm not going to allow you to mute yourself.").queue();
                return;
            }
            if (!userWhoRequestedMute.hasPermission(Permission.ADMINISTRATOR))
            {
                event.getChannel().sendMessage("You do not have the permission to do that (you are not an adminstrator).").queue();
                return;
            }
            if (toBeMuted.hasPermission(Permission.ADMINISTRATOR) && userWhoRequestedMute != event.getGuild().getOwner())
            {
                event.getChannel().sendMessage("You cannot mute another administrator.").queue();
                return;
            }

            // Muting or unmuting the user depending on if they were already muted or not
            if (!toBeMuted.getRoles().contains(muted))
            {

                event.getChannel().sendMessage(messageReceived[1] + " has been muted. Use the mute command again to unmute this member").queue();
                event.getGuild().addRoleToMember(toBeMuted, muted).complete();
            }
            else
            {
                event.getChannel().sendMessage(messageReceived[1] + " has been unmuted.").queue();
                event.getGuild().removeRoleFromMember(toBeMuted, muted).complete();
            }
        }
    }
}
