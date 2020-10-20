package fr.niamoroff.privatemessage.commands;

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
            sender.sendMessage("§8§l[§e!§8§l]§c Vous n'avez pas la permission d'effectuer cette commande.");
            return true;
        }

        if(args.length < 2) {
            sender.sendMessage("§8§l[§e!§8§l]§c Commande incorrecte ! §7§o(/msg <joueur> <message>)");
            return true;
        }

        Player receiver = Bukkit.getPlayer(args[0]);
        if(receiver == null) {
            sender.sendMessage("§8§l[§e!§8§l]§c Le joueur §e" + args[0] + " §c n'est pas connecté.");
            return true;
        }

        if (sender.getName().equals(receiver.getName())) {
            sender.sendMessage("§8§l[§e!§8§l]§c Vous ne pouvez pas vous écrire à vous-même.");
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
