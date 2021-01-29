package com.p3ng00.efsc.module;

import com.p3ng00.p3plugin.P3Command;
import com.p3ng00.efsc.EFSC;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

import static com.p3ng00.p3plugin.P3Plugin.*;

public abstract class Module {

    public static final String DISABLED = ChatColor.RED + "Module Disabled";

    public final String TITLE;
    private boolean enabled;

    public Module(String title, Listener listener, P3Command... commands) {
        TITLE = title;
        if (listener != null) {
            registerListener(listener);
        }
        registerCommands(commands);
        setEnabled(CONFIG.getBoolean("efsc.enable." + getPathName()) && enable());
        printToConsole(isEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled");
    }

    public boolean enable() {
        return true;
    }

    public void disable() { // All subclasses that inherit Module must use super.disable() if overriding this method
        CONFIG.set("efsc.enable." + TITLE.toLowerCase().replace(" ", "_"), isEnabled());
        if (isEnabled()) {
            setEnabled(false);
            printToConsole(ChatColor.RED + "Disabled");
        } else {
            printToConsole(ChatColor.YELLOW + "Already disabled");
        }
    }

    public final String getPathName() {
        return TITLE.toLowerCase().replace(" ", "_");
    }

    public final String createPath(String path) {
        return getPathName() + "." + path;
    }

    public final void printToConsole(Object... msg) {
        for (Object obj : msg) {
            EFSC.printToConsole(String.format("[%s] %s", TITLE, obj.toString()));
        }
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final boolean isEnabled() {
        return enabled;
    }
}
