package com.Rexe0.Items.Armor.Blaze;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class BlazeBoots extends CustomItem {
    public BlazeBoots() {
        super(Material.LEATHER_BOOTS, 1, "Blaze Boots", "EPIC", 2, 2, 14,0,"BLAZE_BOOTS", "BOOTS", 8000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(247, 218, 51));
        this.setItemMeta(meta);
    }
}
