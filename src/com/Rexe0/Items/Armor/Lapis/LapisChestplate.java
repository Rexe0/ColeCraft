package com.Rexe0.Items.Armor.Lapis;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LapisChestplate extends CustomItem {
    public LapisChestplate() {
        super(Material.LEATHER_CHESTPLATE, 1, "Lapis Chestplate", "UNCOMMON", 0, 8, 0, "LAPIS_CHESTPLATE", "CHESTPLATE", 500);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(0, 0, 255));
        this.setItemMeta(meta);
    }
}
