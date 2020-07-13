package com.Rexe0.Items.Armor.Agriculture;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class AgricultureChestplate extends CustomItem {
    public AgricultureChestplate() {
        super(Material.LEATHER_CHESTPLATE, 1, "Agriculture Chestplate", "RARE", 0, 5, 10,4,"AGRICULTURE_CHESTPLATE", "CHESTPLATE", 12000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(240, 208, 0));
        this.setItemMeta(meta);
    }
}
