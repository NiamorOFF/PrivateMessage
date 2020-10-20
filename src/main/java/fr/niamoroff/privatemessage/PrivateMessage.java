package fr.niamoroff.privatemessage;

import fr.niamoroff.privatemessage.commands.AnswerCommand;
import fr.niamoroff.privatemessage.commands.MessageCommand;
import fr.niamoroff.privatemessage.commands.PrivateMessageCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class PrivateMessage extends JavaPlugin {

    private PrivateMessage instance;
    private File logFile;
    private HashMap<String, String> discussions;

    @Override
    public void onEnable() {
        instance = this;
        new File("plugins/PrivateMessage").mkdir();
        logFile = new File("plugins/PrivateMessage/logs.txt");
        discussions = new HashMap<String, String>();
        registerCommands();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands() {
        this.getCommand("privatemessage").setExecutor(new PrivateMessageCommand());
        this.getCommand("message").setExecutor(new MessageCommand(getInstance()));
        this.getCommand("answer").setExecutor(new AnswerCommand(getInstance()));
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        //pluginManager.registerEvents(new Listener(), getInstance());
    }

    private PrivateMessage getInstance() {
        return instance;
    }

    public File getLogFile() {
        return logFile;
    }

    public HashMap<String, String> getDiscussions() {
        return discussions;
    }
}
