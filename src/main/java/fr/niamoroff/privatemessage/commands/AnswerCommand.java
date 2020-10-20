package fr.niamoroff.privatemessage.commands;

import fr.niamoroff.privatemessage.PrivateMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AnswerCommand implements CommandExecutor {

    private final PrivateMessage instance;

    public AnswerCommand(PrivateMessage instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("privatemessage.answer")) {
            sender.sendMessage("§8§l[§e!§8§l]§c Vous n'avez pas la permission d'effectuer cette commande.");
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage("§8§l[§e!§8§l]§c Commande incorrecte ! §7§o(/reply <message>)");
            return true;
        }

        if(!instance.getDiscussions().containsKey(sender.getName())) {
            sender.sendMessage("§8§l[§e!§8§l]§c Vous n'avez personne à qui répondre.");
            return true;
        }

        String nameAuthor = instance.getDiscussions().get(sender.getName());
        CommandSender author;

        if(nameAuthor.equals("CONSOLE")) {
            author = Bukkit.getConsoleSender();
        } else {
            author = Bukkit.getPlayer(nameAuthor);
        }

        if(author == null) {
            sender.sendMessage("§8§l[§e!§8§l]§c Votre partenaire d'échange (§e"+ nameAuthor +"§c) a déconnecté.");
            return true;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String word : args) {
            stringBuilder.append(word).append(" ");
        }

        String contentMessage = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
        new Message(instance, sender, author, contentMessage);
        return true;
    }
}
