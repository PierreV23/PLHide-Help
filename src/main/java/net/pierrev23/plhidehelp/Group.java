package net.pierrev23.plhidehelp;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Group {
    private final String groupName;
    private final int priority;
    private final boolean isWhitelist;
    private final List<String> commands;
    public Group(YamlConfiguration config, String groupName) {
        this.groupName = groupName;
        this.priority = config.getInt("groups." + groupName + ".priority");
        this.isWhitelist = Objects.equals(config.getString("groups." + groupName + ".group-mode"), "whitelist");
        this.commands = config.getStringList("groups." + groupName + ".commands");
    }

    public String getGroupName() {
        return groupName;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isWhitelist() {
        return isWhitelist;
    }

    public List<String> getCommands() {
        return commands;
    }
}
