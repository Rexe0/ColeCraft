package com.Rexe0.Shops;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomShop {
    private Inventory inventory;
    public CustomShop(String name, InventoryHolder holder, HashMap<ItemStack, Integer> itemAndCosts) {
        this.inventory = Bukkit.createInventory(holder, 54, name);

        HashMap<Integer, HashMap<ItemStack, Integer>> slotsWithItems = new HashMap<>();
        int j = 10;
        for (Map.Entry<ItemStack, Integer> entry: itemAndCosts.entrySet()) {
            HashMap<ItemStack, Integer> itemCosts2 = new HashMap<>();
            itemCosts2.put(entry.getKey(), entry.getValue());
            slotsWithItems.put(j, itemCosts2);
            j++;
            switch (j) {
                case 17:
                    j = 19;
                case 26:
                    j = 28;
                case 35:
                    j = 37;
            }
        }

        for (int i = 0; i < 54; i++) {
            if ((i <= 9) || (i >= 44) || i == 17 || i == 18  || i == 26 || i == 27 || i == 35 || i == 36) {
                this.inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
            }
            if (i == 49) {
                ItemStack item = new ItemStack(Material.HOPPER, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED+"Sell Item");

                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY+"This feature will be released");
                lore.add(ChatColor.GRAY+"at a later date.");
                meta.setLore(lore);
                item.setItemMeta(meta);
                this.inventory.setItem(i, item);
            }
            if ((i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 44)) {
                for (Map.Entry<Integer, HashMap<ItemStack, Integer>> entry : slotsWithItems.entrySet()) {
                    for (Map.Entry<ItemStack, Integer> entry1 : entry.getValue().entrySet()) {
                        if (entry.getKey() == i) {
                            ItemStack item = entry1.getKey();
                            ItemMeta meta = item.getItemMeta();
                            List<String> lore = meta.getLore();
                            lore.add(" ");
                            lore.add(ChatColor.GRAY+"Cost: "+ChatColor.GOLD+entry1.getValue());
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            this.inventory.setItem(i, item);
                        }
                    }
                }
            }
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
