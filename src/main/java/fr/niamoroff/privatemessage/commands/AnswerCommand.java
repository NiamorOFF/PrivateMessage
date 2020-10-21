package fr.niamoroff.privatemessage.commands;

import fr.niamoroff.privatemessage.Message;
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
            sender.sendMessage(instance.getMessageFromConfig("no-permission"));
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(instance.getMessageFromConfig("usage-answer"));
            return true;
        }

        if(!instance.getDiscussions().containsKey(instance.getRightName(sender))) {
            sender.sendMessage(instance.getMessageFromConfig("no-body-to-answer"));
            return true;
        }

        String nameAuthor = instance.getDiscussions().get(instance.getRightName(sender));
        CommandSender author;

        if(nameAuthor.equals(instance.getMessageFromConfig("console-name"))) {
            author = Bukkit.getConsoleSender();
        } else {
            author = Bukkit.getPlayer(nameAuthor);
        }

        if(author == null) {
            sender.sendMessage(instance.getMessageFromConfig("target-disconnected").replaceAll("%targetname%", nameAuthor));
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
