package fr.niamoroff.privatemessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private final PrivateMessage instance;
    private final CommandSender author;
    private final CommandSender receiver;
    private final Date date;
    private final String message;

    public Message (PrivateMessage instance, CommandSender author, CommandSender receiver, String message) {
        this.instance = instance;
        this.author = author;
        this.receiver = receiver;
        this.date = new Date();
        if(author.hasPermission("messageprivate.chatcolor")) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }
        this.message = message;
        sendMessage();
    }

    private void sendMessage() {
        this.author.sendMessage(instance.getMessageFromConfig("message-sent")
                .replaceAll("%receivername%", getRightName(receiver))
                .replaceAll("%message%", message));
        this.receiver.sendMessage(instance.getMessageFromConfig("message-received")
                .replaceAll("%authorname%", getRightName(author))
                .replaceAll("%message%", message));
        instance.getDiscussions().put(getRightName(receiver), getRightName(author));
        instance.getDiscussions().put(getRightName(author), getRightName(receiver));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(!player.hasPermission("privatemessage.socialpsy")) return;
            player.sendMessage(instance.getMessageFromConfig("socialpsy-message")
                    .replaceAll("%authorname%", getRightName(author))
                    .replaceAll("%receivername%", getRightName(receiver))
                    .replaceAll("%message%", message));
        }
        logMessage();
    }

    private void logMessage() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
        String date = simpleDateFormat.format(this.date);
        try {
            FileWriter fileWriter = new FileWriter(instance.getLogFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("[" + date + "] " + getRightName(author) + " » " + getRightName(receiver) + " : " + message);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRightName(CommandSender user) {
        if(!user.getName().equals("CONSOLE")) return user.getName();
        return instance.getMessageFromConfig("console-name");
    }
}
