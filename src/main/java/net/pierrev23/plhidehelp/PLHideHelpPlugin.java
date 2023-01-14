package net.pierrev23.plhidehelp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class PLHideHelpPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "[PLHideHelpPlugin] Enabled " + this.getName());
        YamlConfiguration config = (YamlConfiguration) super.getConfig();
        this.getCommand("help").setExecutor(new HelpCommand(config));
        this.getCommand("reload").setExecutor(new ReloadCommand(this));

    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "[PLHideHelpPlugin] Disabled " + this.getName());
    }

}
