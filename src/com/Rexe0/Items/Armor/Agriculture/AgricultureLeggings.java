package com.Rexe0.Items.Armor.Agriculture;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class AgricultureLeggings extends CustomItem {
    public AgricultureLeggings() {
        super(Material.LEATHER_LEGGINGS, 1, "Agriculture Leggings", "RARE", 0, 5, 7,4,"AGRICULTURE_LEGGINGS", "LEGGINGS", 11000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(240, 208, 0));
        this.setItemMeta(meta);
    }
}
