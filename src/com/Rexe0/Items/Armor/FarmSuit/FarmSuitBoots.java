package com.Rexe0.Items.Armor.FarmSuit;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FarmSuitBoots extends CustomItem {
    public FarmSuitBoots() {
        super(Material.LEATHER_BOOTS, 1, "Farm Suit Boots", "COMMON", 0, 3, 0, "FARM_SUIT_BOOTS", "BOOTS", 40);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(255, 255, 0));
        this.setItemMeta(meta);
    }
}
