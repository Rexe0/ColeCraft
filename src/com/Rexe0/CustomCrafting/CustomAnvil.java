package com.Rexe0.CustomCrafting;

import com.Rexe0.ColeCrafterSlayers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class CustomAnvil implements Listener, CommandExecutor {
    public static HashMap<Player, Inventory> anvilGUI = new HashMap<>();
    public static HashMap<Player, Boolean> anvilCombined = new HashMap<>();

    @EventHandler
    public static void onInvClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) return;
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().equals(anvilGUI.get(player))) {
            if (e.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE || e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE || e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE || e.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE || e.getCurrentItem().getType() == Material.ANVIL) {
                e.setCancelled(true);
            }



            anvilCombined.putIfAbsent(player, false);
            if (e.getSlot() == 13 && !(anvilCombined.get(player))) {
                e.setCancelled(true);
            } else if (e.getSlot() == 13 && (anvilCombined.get(player))) {
                anvilCombined.put(player, false);
            }
            if (e.getSlot() == 13 && e.getInventory().getItem(e.getSlot()).getType() == Material.AIR) {
                e.setCancelled(true);
            }


            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED+"Exit")) {

                        player.closeInventory();
                        e.setCancelled(true);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"Combine")) {
                        if (e.getCurrentItem().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 1) {

                            if (player.getLevel() >= ColeCrafterSlayers.anvilXpCost.get(player)) {
                                anvilCombined.put(player, true);
                                e.getInventory().setItem(29, new ItemStack(Material.AIR));


                                ItemStack itemInSlot33 = e.getInventory().getItem(33);
                                int amount = itemInSlot33.getAmount();

                                if (amount > 1) {

                                    itemInSlot33.setAmount(amount - 1);
                                    e.getInventory().setItem(33, itemInSlot33);
                                } else {
                                    e.getInventory().setItem(33, new ItemStack(Material.AIR));
                                }
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 2, 1);
                                player.setLevel(player.getLevel()-ColeCrafterSlayers.anvilXpCost.get(player));
                            }
                        }
                    }
                }
            }

            if (e.getCurrentItem().getType() == Material.BARRIER) {
                e.setCancelled(true);
            }


        }

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            anvilGUI.putIfAbsent(player, Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY+"Anvil"));

            Inventory inv = anvilGUI.get(player);

            for (byte i = 0; i < 54; i++) {
                if ((i >= 0 && i <= 10) || (i > 15 && i < 20) || (i > 24 && i < 29) || (i > 29 && i < 33) || (i > 33 && i < 45) || i == 21 || i == 23) {
                    inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
                }
                if ((i >= 45 && i <= 48 ) || (i >= 50 && i <= 53) || i == 11  || i == 12 || i == 14 || i == 15 || i == 20 || i == 24) {
                    inv.setItem(i, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                }
                if (i == 49) {
                    ItemStack item = new ItemStack(Material.BARRIER, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.RED+"Close");
                    item.setItemMeta(meta);
                    inv.setItem(i, item);
                }

                if (i == 22) {
                    ItemStack item = new ItemStack(Material.ANVIL, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN+"Combine");
                    item.setItemMeta(meta);
                    inv.setItem(i, item);
                }


            }

            player.openInventory(inv);


        }
        return true;
    }
}
