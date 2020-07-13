package com.Rexe0.Items.Armor.Agriculture;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class AgricultureBoots extends CustomItem {
    public AgricultureBoots() {
        super(Material.LEATHER_BOOTS, 1, "Agriculture Boots", "RARE", 0, 5, 3, 4,"AGRICULTURE_BOOTS", "BOOTS", 6100);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(240, 208, 0));
        this.setItemMeta(meta);
    }
}
