package com.Rexe0.Items;

import com.Rexe0.Items.Materials.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED+"You must specify the ID of the item.");
                return true;
            } else {
                ItemStack item = CustomItem.getItemClass(args[0].toUpperCase());
                ItemStack item1 = CustomMaterial.getItemClass(args[0].toUpperCase());

                if (item == null && item1 == null) {
                    sender.sendMessage(ChatColor.RED+"Not a valid ID.");
                    return true;
                } else {
                    if (sender.getName().equals("rexe0")) {

                        if (item1 == null) ((Player) sender).getInventory().addItem(item);
                        else if (item == null) ((Player) sender).getInventory().addItem(item1);

                    }
                    else {
                        sender.sendMessage(ChatColor.RED+"You are not rexe0.");
                    }
                }


            }
        }
        return true;
    }
}
