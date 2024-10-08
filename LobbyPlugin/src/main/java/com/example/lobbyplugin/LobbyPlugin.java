package com.example.lobbyplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class LobbyPlugin extends JavaPlugin implements Listener {

    private Location lobbySpawn;
    private Map<String, Location> minigameLocations = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("lobby").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lobby")) {
            if (args.length == 0) {
                sender.sendMessage("Use /lobby help for a list of commands.");
                return true;
            }

            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("/lobby help - Show this help message");
                sender.sendMessage("/lobby set spawn [x] [y] [z] - Set the lobby spawn location");
                sender.sendMessage("/lobby delete spawn - Delete the lobby spawn location");
                sender.sendMessage("/lobby set minigame <name> [x] [y] [z] - Set a minigame location");
                sender.sendMessage("/lobby delete minigame <name> - Delete a minigame location");
                return true;
            }

            if (args[0].equalsIgnoreCase("set") && args.length >= 2) {
                if (args[1].equalsIgnoreCase("spawn")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (args.length == 5) {
                            double x = Double.parseDouble(args[2]);
                            double y = Double.parseDouble(args[3]);
                            double z = Double.parseDouble(args[4]);
                            lobbySpawn = new Location(player.getWorld(), x, y, z);
                        } else {
                            lobbySpawn = player.getLocation();
                        }
                        sender.sendMessage("Lobby spawn location set.");
                        return true;
                    }
                } else if (args[1].equalsIgnoreCase("minigame") && args.length >= 3) {
                    String minigameName = args[2];
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        Location location;
                        if (args.length == 6) {
                            double x = Double.parseDouble(args[3]);
                            double y = Double.parseDouble(args[4]);
                            double z = Double.parseDouble(args[5]);
                            location = new Location(player.getWorld(), x, y, z);
                        } else {
                            location = player.getLocation();
                        }
                        minigameLocations.put(minigameName, location);
                        sender.sendMessage("Minigame location set for " + minigameName);
                        return true;
                    }
                }
            }

            if (args[0].equalsIgnoreCase("delete") && args.length >= 2) {
                if (args[1].equalsIgnoreCase("spawn")) {
                    lobbySpawn = null;
                    sender.sendMessage("Lobby spawn location deleted.");
                    return true;
                } else if (args[1].equalsIgnoreCase("minigame") && args.length == 3) {
                    String minigameName = args[2];
                    minigameLocations.remove(minigameName);
                    sender.sendMessage("Minigame location deleted for " + minigameName);
                    return true;
                }
            }
        }
        return false;
    }
}
