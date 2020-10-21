package fr.niamoroff.privatemessage;

import fr.niamoroff.privatemessage.commands.AnswerCommand;
import fr.niamoroff.privatemessage.commands.MessageCommand;
import fr.niamoroff.privatemessage.commands.PrivateMessageCommand;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class PrivateMessage extends JavaPlugin {

    private PrivateMessage instance;
    private FileConfiguration fileConfiguration;
    private YamlConfiguration fileTranslation;
    private File englishFile;
    private File spanishFile;
    private File frenchFile;
    private File logFile;
    private HashMap<String, String> discussions;

    @Override
    public void onEnable() {
        this.instance = this;
        instance.saveDefaultConfig();
        this.fileConfiguration = instance.getConfig();
        instance.createTranslationConfig();
        this.fileTranslation = instance.getTranslationFile();
        this.logFile = new File("plugins/PrivateMessage/logs.txt");
        this.discussions = new HashMap<String, String>();
        registerCommands();
    }

    private void registerCommands() {
        this.getCommand("privatemessage").setExecutor(new PrivateMessageCommand(instance));
        this.getCommand("message").setExecutor(new MessageCommand(instance));
        this.getCommand("answer").setExecutor(new AnswerCommand(instance));
    }

    private YamlConfiguration getTranslationFile() {
        YamlConfiguration yamlConfiguration =  new YamlConfiguration();
        if(getFileConfiguration().getString("language").equalsIgnoreCase("english")) {
            try {
                yamlConfiguration.load(englishFile);
                return yamlConfiguration;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(getFileConfiguration().getString("language").equalsIgnoreCase("spanish")) {
            try {
                yamlConfiguration.load(spanishFile);
                return yamlConfiguration;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(getFileConfiguration().getString("language").equalsIgnoreCase("french")) {
            try {
                yamlConfiguration.load(frenchFile);
                return yamlConfiguration;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public YamlConfiguration getFileTranslation() {
        return fileTranslation;
    }

    public File getLogFile() {
        return logFile;
    }

    public HashMap<String, String> getDiscussions() {
        return discussions;
    }

    public void createTranslationConfig() {
        englishFile = new File(getDataFolder(), "translation_english.yml");
        spanishFile = new File(getDataFolder(), "translation_spanish.yml");
        frenchFile = new File(getDataFolder(), "translation_french.yml");

        if(!englishFile.exists()) {
            instance.saveResource("translation_english.yml", false);
        }
        if(!spanishFile.exists()) {
            instance.saveResource("translation_spanish.yml", false);
        }
        if(!frenchFile.exists()) {
            instance.saveResource("translation_french.yml", false);
        }
    }

    public String getMessageFromConfig(String path) {
        return ChatColor.translateAlternateColorCodes('&', instance.getFileTranslation().getString(path));
    }
}
