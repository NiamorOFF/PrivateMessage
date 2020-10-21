package fr.niamoroff.privatemessage.commands;

import fr.niamoroff.privatemessage.PrivateMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PrivateMessageCommand implements CommandExecutor {

    private final PrivateMessage instance;

    public PrivateMessageCommand (PrivateMessage instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("privatemessage.help")) {
            sender.sendMessage(instance.getMessageFromConfig("no-permission"));
            return true;
        }
        sendHelpMessage(sender);
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(instance.getMessageFromConfig("help-line-one"));
        sender.sendMessage(instance.getMessageFromConfig("help-line-two"));
        sender.sendMessage(instance.getMessageFromConfig("help-line-three"));
        sender.sendMessage(instance.getMessageFromConfig("help-line-four"));
        sender.sendMessage(instance.getMessageFromConfig("help-line-five"));
        sender.sendMessage(instance.getMessageFromConfig("help-line-six"));
        sender.sendMessage(instance.getMessageFromConfig("help-line-seven"));
    }
}
