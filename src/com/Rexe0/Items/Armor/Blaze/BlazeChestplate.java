package com.Rexe0.Items.Armor.Blaze;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class BlazeChestplate extends CustomItem {
    public BlazeChestplate() {
        super(Material.LEATHER_CHESTPLATE, 1, "Blaze Chestplate", "EPIC", 2, 2, 30,0,"BLAZE_CHESTPLATE", "CHESTPLATE", 16000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(247, 218, 51));
        this.setItemMeta(meta);
    }
}
