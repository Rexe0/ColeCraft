package com.Rexe0.Items.Armor.Blaze;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class BlazeLeggings extends CustomItem {
    public BlazeLeggings() {
        super(Material.LEATHER_LEGGINGS, 1, "Blaze Leggings", "EPIC", 2, 2, 22,0,"BLAZE_LEGGINGS", "LEGGINGS", 14000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(247, 218, 51));
        this.setItemMeta(meta);
    }
}
