package net.pierrev23.plhidehelp;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HelpCommand implements CommandExecutor {
//    private final YamlConfiguration config;
    private final Plugin pluginSelf;
    private final Plugin pluginPLHide;
    public HelpCommand(Plugin pluginSelf, Plugin pluginPLHide) {
        this.pluginSelf = pluginSelf;
        this.pluginPLHide = pluginPLHide;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String format = pluginSelf.getConfig().getString("formatter");
        if (format == null) return false;
        if (sender instanceof Player player) {
            player.sendMessage(
                pluginSelf.getConfig().getString("prefix")
                    +
                getPlayerCommands(player)
                    .stream()
                    .filter(Objects::nonNull)
                    .map(c -> format.replace("{command}", c).replace("{description}", getCommandDescription(c)))
                    .sorted()
                    .collect(Collectors.joining("\n"))
                    +
                pluginSelf.getConfig().getString("suffix")
            );
        }
        return true;
    }

    private Set<String> getPlayerCommands(Player player) {
        Set<String> commands = new HashSet<>();
        getPlayerGroups(player)
                .map(g -> new Group((YamlConfiguration) this.pluginPLHide.getConfig(), g)) // cast it to a `Group`, contains fields (name, priority, isWhitelist, commands)
                .sorted(Comparator.comparingInt(Group::getPriority)) // sorts all the groups based on their priority
                .forEach( // loop over the groups, if isWhitelist, add the functions, if not isWhitelist remove them
                        group -> {
                            if (group.isWhitelist()) {
                                commands.addAll(group.getCommands());
                            } else {
                                commands.removeAll(group.getCommands());
                            }
                        }
                );
        return commands;
    }

    private Stream<String> getPlayerGroups(Player player) {
        return getGroups()
            .stream()
            .filter(g -> player.hasPermission("plhide.group." + g))
            .flatMap(g -> Stream.concat(Stream.of(g), getInheritedGroups(g).stream()));
    }

    private Set<String> getGroups() {
        return pluginPLHide.getConfig().getConfigurationSection("groups").getKeys(false /* true for the keys of every element below and even further below. */);
    }

    private List<String> getInheritedGroups(String group) {
        return pluginPLHide.getConfig().getStringList("groups." + group + ".included-groups");
    }

    private List<String> getCommandsAvailable() {
        return pluginPLHide.getConfig().getStringList("commands");
    }

    private List<String> getCommands(String group) {
        return pluginPLHide.getConfig().getStringList("groups." + group + ".commands");
    }

    private String getCommandDescription(String command) {
        return pluginSelf.getConfig().getString("commands." + command, pluginSelf.getConfig().getString("no-description"));
    }
}
