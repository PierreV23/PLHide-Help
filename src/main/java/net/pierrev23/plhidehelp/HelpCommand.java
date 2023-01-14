package net.pierrev23.plhidehelp;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class HelpCommand implements CommandExecutor {
    private YamlConfiguration config;
    public HelpCommand(YamlConfiguration config) {
        this.config = config;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(
                    getPlayerCommands(player)
                        .stream()
                        .filter(Objects::nonNull)
                        .map(this::getCommandDescription)
                        .collect(Collectors.joining("\n")
                        )
            );

        }

        return true;
    }
    private Set<String> getPlayerGroups(Player player) {
        Set<String> groups = getGroups();
        Set<String> joined = new HashSet<String>();
        groups.forEach((group) -> {
            if (player.hasPermission("plhide.group." + group)) {
                joined.add(group);
                joined.addAll(getInheritedGroups(group));
            }
        });
        return joined;
    }
    private Set<String> getGroups() {
        return config.getConfigurationSection("groups").getKeys(false /* true for the keys of every element below and even further below. */);
    }
    private List<String> getInheritedGroups(String group) {
        return config.getStringList("groups." + group);
    }
    private List<String> getCommands(String group) {
        if (group == null)
            return getCommandsAvailable();

        return config.getStringList("groups." + group + ".commands");
        // As an alternative, you can always use config.getConfigurationSection("groups").getStringList(group);. Both work.
    }
    private List<String> getCommandsAvailable() {
        return config.getStringList("commands");
    }
    private Set<String> getPlayerCommands(Player player) {
        Set<String> commands = new HashSet<String>();
        getPlayerGroups(player).forEach((group) -> {
            commands.addAll(getCommands(group));
        });
        return commands;
    }
    private String getCommandDescription(String command) {
        return config.getString("commands." + command);
    }
}
