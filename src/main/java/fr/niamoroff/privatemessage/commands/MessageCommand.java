package fr.niamoroff.privatemessage.commands;

import fr.niamoroff.privatemessage.Message;
import fr.niamoroff.privatemessage.PrivateMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    private final PrivateMessage instance;

    public MessageCommand(PrivateMessage instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("privatemessage.message")) {
            sender.sendMessage(instance.getMessageFromConfig("no-permission"));
            return true;
        }

        if(args.length < 2) {
            sender.sendMessage(instance.getMessageFromConfig("usage-message"));
            return true;
        }

        Player receiver = null;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(player.getName().equalsIgnoreCase(args[0])) {
                receiver = player;
                break;
            }
        }

        if(receiver == null) {
            sender.sendMessage(instance.getMessageFromConfig("target-not-found").replaceAll("%targetname%", args[0]));
            return true;
        }

        if (sender.getName().equals(receiver.getName())) {
            sender.sendMessage(instance.getMessageFromConfig("can-not-write-to-yourself"));
            return true;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            stringBuilder.append(args[i]).append(" ");
        }
        String contentMessage = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
        new Message(instance, sender, receiver, contentMessage);
        return true;
    }
}
