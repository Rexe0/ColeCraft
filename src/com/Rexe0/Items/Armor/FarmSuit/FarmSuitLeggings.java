package com.Rexe0.Items.Armor.FarmSuit;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FarmSuitLeggings extends CustomItem {
    public FarmSuitLeggings() {
        super(Material.LEATHER_LEGGINGS, 1, "Farm Suit Leggings", "COMMON", 0, 6, 0, "FARM_SUIT_LEGGINGS", "LEGGINGS", 60);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(255, 255, 0));
        this.setItemMeta(meta);
    }
}
