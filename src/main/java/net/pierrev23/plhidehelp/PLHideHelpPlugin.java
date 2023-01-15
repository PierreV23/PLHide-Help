package net.pierrev23.plhidehelp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class PLHideHelpPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "[PLHideHelpPlugin] Enabled " + this.getName());
        YamlConfiguration config = (YamlConfiguration) super.getConfig();
        Plugin PLHide = Bukkit.getPluginManager().getPlugin("PL-Hide");
        this.getCommand("help").setExecutor(new HelpCommand(this, PLHide));
        this.getCommand("reload").setExecutor(new ReloadCommand(this, PLHide));

    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "[PLHideHelpPlugin] Disabled " + this.getName());
    }

}
