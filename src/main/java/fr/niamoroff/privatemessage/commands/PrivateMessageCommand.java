package fr.niamoroff.privatemessage.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PrivateMessageCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("privatemessage.help")) {
            sender.sendMessage("§8§l[§e!§8§l]§c Vous n'avez pas la permission d'effectuer cette commande.");
            return true;
        }
        sendHelpMessage(sender);
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage("§6---------------------- §9PrivateMessage §6----------------------");
        sender.sendMessage("§2/privatemessage §c» §5Afficher ce message d'aide.");
        sender.sendMessage("§2/message <joueur> <message> §c» §5Envoyer un message à un joueur.");
        sender.sendMessage("§2/answer <message> §c» §5Répondre au dernier message reçu.");
        sender.sendMessage("§3⇒ Plugin développé par NiamorOFF §7§o(Twitter : @NiamorOFF)§3.");
        sender.sendMessage("§6---------------------- §9PrivateMessage §6----------------------");
    }
}
