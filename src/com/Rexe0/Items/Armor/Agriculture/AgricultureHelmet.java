package com.Rexe0.Items.Armor.Agriculture;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class AgricultureHelmet extends CustomItem {
    public AgricultureHelmet() {
        super(Material.LEATHER_HELMET, 1, "Agriculture Helmet", "RARE", 0, 5, 5,4,"AGRICULTURE_HELMET", "HELMET", 8000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(240, 208, 0));
        this.setItemMeta(meta);
    }
}
