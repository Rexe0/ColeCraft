package com.Rexe0.Mobs;

import com.Rexe0.Items.CustomItem;
import com.Rexe0.Items.Materials.CustomMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomMobCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 4) {
                sender.sendMessage(ChatColor.RED+"Correct Usage: /mob <ID> <x> <y> <z>.");
                return true;
            } else {

                CustomMob.spawnMob(args[0], new Location(((Player) sender).getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3])));

            }
        }
        return true;
    }
}
