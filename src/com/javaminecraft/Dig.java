package com.javaminecraft;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Dig extends JavaPlugin {
    public static final Logger log = Logger.getLogger("Minecraft");
    Player me;
    World world;
    Location spot;
    
    
    @Override
    public void onEnable() {
        this.log.info(this.getDescription().getName() +" Has been enabled!");
    }

    @Override 
    public void onDisable() {
        this.log.info(this.getDescription().getName() + " Has been disabled!");
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        me = (Player) sender;
        spot = me.getLocation();
        world = me.getWorld();
        
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("dig")) {
                executeCommand(sender, arguments);
            }
            return true;
        }
        return false;
    
    }
    
    public void executeCommand(CommandSender sender, String[] arguments) {
        // default radius
        double radius = 10;
        
        if (arguments.length > 0) {
            try {
                //get the user's radius
                
                if ((radius < 5) || (radius > 30)) {
                    radius = 10;
                }
            }
            
            catch (NumberFormatException exception) {
                me.sendMessage("Not a real number!");
            }
        }
        
        // loop through a square with sides 2x the radius size (diameter)
        for (double x = spot.getX() - radius; x < spot.getX() + radius; x++) {
            
            for (double y = spot.getY() - radius; y < spot.getY() + radius; y++) {
                
                for (double z = spot.getZ() - radius; z < spot.getZ() + radius; z++) {
                    
                    Location loc = new Location(world, x, y, z);
                    
                    if (y < 2) continue;
                    
                    double xd = x - spot.getX();
                    double yd = y - spot.getY();
                    double zd = z - spot.getZ();
                    
                    double distance = Math.sqrt(xd * xd + yd * yd + zd * zd);
                    
                    if (distance < radius) {
                        Block current = world.getBlockAt(loc);
                        current.setType(Material.AIR);
                    }
                }
            }
        }
        world.playSound(spot, Sound.BURP, 30, 5);
        log.info("Dig at (" + (int) spot.getX() + ", " + (int) spot.getY() + ", " + (int) spot.getZ() + ")");
    }
}