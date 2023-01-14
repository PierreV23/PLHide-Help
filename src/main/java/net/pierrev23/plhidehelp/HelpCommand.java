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
import java.util.stream.Stream;

public class HelpCommand implements CommandExecutor {
    private final YamlConfiguration config;
    public HelpCommand(YamlConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            player.sendMessage(
                    getPlayerCommands(player)
                        .stream()
                        .filter(Objects::nonNull)
                        .sorted()
                        .map(c -> "/" + c + ": " + getCommandDescription(c))
                        .collect(Collectors.joining("\n")
                        )
            );
        }
        return true;
    }

    private Set<String> getPlayerCommands(Player player) {
        return getPlayerGroups(player)
                .stream()
                .flatMap(g -> Stream.concat(Stream.of(g), getCommands(g).stream()))
                .collect(Collectors.toSet());
    }

    private Set<String> getPlayerGroups(Player player) {
        return getGroups()
            .stream()
            .filter(g -> player.hasPermission("plhide.group." + g))
            .flatMap(g -> Stream.concat(Stream.of(g), getInheritedGroups(g).stream()))
            .collect(Collectors.toSet());
    }

    private Set<String> getGroups() {
        return config.getConfigurationSection("groups").getKeys(false /* true for the keys of every element below and even further below. */);
    }

    private List<String> getInheritedGroups(String group) {
        return config.getStringList("groups." + group + ".included-groups");
    }

    private List<String> getCommandsAvailable() {
        return config.getStringList("commands");
    }

    private List<String> getCommands(String group) {
        return config.getStringList("groups." + group + ".commands");
    }

    private String getCommandDescription(String command) {
        return config.getString("commands." + command, "No description found");
    }
}
