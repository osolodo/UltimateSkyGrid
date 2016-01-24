package com.gmail.labuff.shane.UltimateSkyGrid;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportDelay extends BukkitRunnable {

    public final Player player;
    public final Location location;
    public final String worldName;

    public TeleportDelay(Player play, Location loc, String worldName) {

        this.player = play;
        this.location = loc;
        this.worldName = worldName;


    }

    @Override
    public void run() {

        this.player.sendMessage(ChatColor.GREEN + "Teleporting you to your home in " + this.worldName);
        this.player.teleport(location);

    }
}