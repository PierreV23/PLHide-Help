package net.pierrev23.plhidehelp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class PLHideHelpPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "[PLHideHelpPlugin] Enabled " + this.getName());
        this.getCommand("plhhelp").setExecutor(new HelpCommand());
    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "[PLHideHelpPlugin] Disabled " + this.getName());
    }

}
