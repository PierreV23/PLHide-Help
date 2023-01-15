package net.pierrev23.plhidehelp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.stream.Collectors;

public class ReloadCommand implements CommandExecutor {
    private final Plugin pluginSelf;
    private final Plugin pluginPLHide;
    public ReloadCommand(Plugin pluginSelf, Plugin pluginPLHide) {
            this.pluginSelf = pluginSelf;
            this.pluginPLHide = pluginPLHide;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("[PLHideHelp] Reloading...");
        pluginSelf.reloadConfig();
        sender.sendMessage("[PLHideHelp] Done reloading.");
        sender.sendMessage("[PLHideHelp::PLHide] Reloading...");
        pluginPLHide.reloadConfig();
        sender.sendMessage("[PLHideHelp::PLHide] Done reloading.");
        return true;
    }
}
