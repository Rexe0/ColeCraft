package com.Rexe0.Items;

import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.DefenseNerf;
import com.Rexe0.Items.Materials.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class UpdateItemCommand implements CommandExecutor {
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
                    if (sender.getName().equals("rexe0") || sender instanceof ConsoleCommandSender) {

                        ItemStack finalItem = null;
                        if (item == null) {
                            finalItem = item1;
                        } else {
                            finalItem = item;
                        }
                        for (Player player : Bukkit.getOnlinePlayers()) {

                            for (ItemStack item2 : player.getInventory().getContents()) {
                                String foundValue = DefenseNerf.getItemID(item2);
                                if (foundValue != null) {
                                    if (foundValue.equals(args[0])) {
                                        ItemMeta meta = item2.getItemMeta();
                                        ItemMeta targetMeta = finalItem.getItemMeta();
                                        meta.setLore(targetMeta.getLore());
                                        meta.setDisplayName(targetMeta.getDisplayName());

                                        for (Map.Entry<Attribute, AttributeModifier> attribute : meta.getAttributeModifiers().entries()) {
                                            if (attribute.getValue().getName().equals("damage")) {
                                                meta.removeAttributeModifier(attribute.getKey(), attribute.getValue());
                                            }
                                            if (attribute.getValue().getName().equals("health")) {
                                                meta.removeAttributeModifier(attribute.getKey(), attribute.getValue());
                                            }
                                            if (attribute.getValue().getName().equals("speed")) {
                                                meta.removeAttributeModifier(attribute.getKey(), attribute.getValue());
                                            }
                                            if (attribute.getValue().getName().equals("defense")) {
                                                meta.removeAttributeModifier(attribute.getKey(), attribute.getValue());
                                            }
                                        }

                                        for (Map.Entry<Attribute, AttributeModifier> attribute : targetMeta.getAttributeModifiers().entries()) {
                                            if (attribute.getValue().getName().equals("damage")) {
                                                meta.addAttributeModifier(attribute.getKey(), attribute.getValue());
                                            }
                                            if (attribute.getValue().getName().equals("health")) {
                                                meta.addAttributeModifier(attribute.getKey(), attribute.getValue());
                                            }
                                            if (attribute.getValue().getName().equals("speed")) {
                                                meta.addAttributeModifier(attribute.getKey(), attribute.getValue());
                                            }
                                            if (attribute.getValue().getName().equals("defense")) {
                                                meta.addAttributeModifier(attribute.getKey(), attribute.getValue());
                                            }
                                        }

                                        item2.setItemMeta(meta);
                                    }
                                }
                            }

                        }


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
