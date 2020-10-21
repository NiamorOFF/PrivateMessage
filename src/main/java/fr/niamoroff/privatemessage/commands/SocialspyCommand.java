package fr.niamoroff.privatemessage.commands;

import fr.niamoroff.privatemessage.PrivateMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialspyCommand implements CommandExecutor {

    private final PrivateMessage instance;

    public SocialspyCommand(PrivateMessage instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(instance.getMessageFromConfig("not-a-player"));
            return true;
        }
        if (!sender.hasPermission("privatemessage.socialspy")) {
            sender.sendMessage(instance.getMessageFromConfig("no-permission"));
            return true;
        }
        if (instance.getSocialspyActivated().contains(sender.getName())) {
            instance.getSocialspyActivated().remove(sender.getName());
            sender.sendMessage(instance.getMessageFromConfig("socialspy-off"));
            return true;
        }
        instance.getSocialspyActivated().add(sender.getName());
        sender.sendMessage(instance.getMessageFromConfig("socialspy-on"));
        return true;
    }
}
