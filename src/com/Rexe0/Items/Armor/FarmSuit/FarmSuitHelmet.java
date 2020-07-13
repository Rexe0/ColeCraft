package com.Rexe0.Items.Armor.FarmSuit;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FarmSuitHelmet extends CustomItem {
    public FarmSuitHelmet() {
        super(Material.LEATHER_HELMET, 1, "Farm Suit Helmet", "COMMON", 0, 3, 0, "FARM_SUIT_HELMET", "HELMET", 40);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(255, 255, 0));
        this.setItemMeta(meta);
    }
}
