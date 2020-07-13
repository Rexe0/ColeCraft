package com.Rexe0.Items.Armor.FarmSuit;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FarmSuitChestplate extends CustomItem {
    public FarmSuitChestplate() {
        super(Material.LEATHER_CHESTPLATE, 1, "Farm Suit Chestplate", "COMMON", 0, 8, 0, "FARM_SUIT_CHESTPLATE", "CHESTPLATE", 80);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(255, 255, 0));
        this.setItemMeta(meta);
    }
}
