package org.example.listeners;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;


public class EventListener extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        String channelMention = event.getChannel().getAsMention();
        String jumpLink = event.getJumpUrl();

        String message = user.getAsTag() + " wants to gamble with " + emoji + " in the " + channelMention + " channel!";
        DefaultGuildChannelUnion defaultchannel = event.getGuild().getDefaultChannel();
        defaultchannel.asTextChannel().sendMessage(message).queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.equals("keep")) {
            event.getChannel().sendMessage("gambling").queue();
        } else if (message.contains("give up")) {
            File image = new File("src/main/resources/keep_gambling.jpeg");
            if (image.exists()) {
                FileUpload fileUpload = FileUpload.fromData(image);
                event.getChannel().sendFiles(fileUpload).queue();
            }
        }
    }

//    Uses Cache to find online users -- utilizes a lot of memory over a lot of users
//    @Override
//    public void onUserUpdateOnlineStatus(@NotNull UserUpdateOnlineStatusEvent event) {
//        List<Member> members = event.getGuild().getMembers();
//        int onlineMembers = 0;
//        for (Member member : members) {
//            if (member.getOnlineStatus() == OnlineStatus.ONLINE)
//                onlineMembers++;
//        }
//
//
//
//        User user = event.getUser();
//        String message = user.getName() + " updated their status to " + event.getNewOnlineStatus().getKey() + ". There are now " + onlineMembers + " online.";
//        event.getGuild().getDefaultChannel().asTextChannel().sendMessage(message).queue();
//    }
}
