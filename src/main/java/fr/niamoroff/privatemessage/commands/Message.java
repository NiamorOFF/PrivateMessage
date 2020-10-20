package fr.niamoroff.privatemessage.commands;

import fr.niamoroff.privatemessage.PrivateMessage;
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
        this.author.sendMessage("§8§l[§eMP§8§l]§a Vous §f» §a" + receiver.getName() + "§f : §e" + message);
        this.receiver.sendMessage("§8§l[§eMP§8§l]§a " + author.getName() + " §f» §aVous§f : §e" + message);
        instance.getDiscussions().put(receiver.getName(), author.getName());
        instance.getDiscussions().put(author.getName(), receiver.getName());
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(!player.hasPermission("privatemessage.socialpsy")) return;
            player.sendMessage("§8§l[§eSocialPsy§8§l]§a " + author.getName() + " §f» §a" + receiver.getName() + "§f : §e" + message);
        }
        logMessage();
    }

    private void logMessage() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
        String date = simpleDateFormat.format(this.date);
        try {
            FileWriter fileWriter = new FileWriter(instance.getLogFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("[" + date + "] " + author.getName() + " » " + receiver.getName() + " : " + message);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
