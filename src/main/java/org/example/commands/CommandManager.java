package org.example.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("gamble")) {
            // runs the '/gamble' command
            String user = event.getUser().getEffectiveName();
            event.reply("The casino is currently closed, please come back at another time, **" + user + "**.").queue();
        }
        else if (command.equals("balance")) {
            // runs the '/balance' command
            String privateMessage = "Your balance is $0.";
            String publicMessage = event.getUser().getEffectiveName() + " is broke!";

            event.deferReply(true).queue();

            event.getHook().sendMessage(privateMessage).setEphemeral(true).queue();
            event.getHook().sendMessage(publicMessage).setEphemeral(false).queue();
        }

        else if (command.equals("jackpot")) {
            // runs the '/jackpot' command
            event.deferReply().queue();
            String userName = event.getUser().getEffectiveName();
            String serverUsers = "";

            // this only mentions online people
            for (Member member : event.getGuild().getMembers()) {
                serverUsers += member.getAsMention();
            }

            String message = "**" + userName + "** won the jackpot with a total of $0 dollars! \uD83E\uDD11 " + serverUsers;
            event.getHook().sendMessage(message).queue();
        }
    }

    // Guild commands
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("gamble", "Start gambling."));
        commandData.add(Commands.slash("balance", "Check your money balance."));
        commandData.add(Commands.slash("jackpot", "Celebrate winning the jackpot!"));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
