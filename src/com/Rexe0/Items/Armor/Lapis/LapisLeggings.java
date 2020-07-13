package com.Rexe0.Items.Armor.Lapis;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LapisLeggings extends CustomItem {
    public LapisLeggings() {
        super(Material.LEATHER_LEGGINGS, 1, "Lapis Leggings", "UNCOMMON", 0, 7, 0, "LAPIS_LEGGINGS", "LEGGINGS", 400);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(0, 0, 255));
        this.setItemMeta(meta);
    }
}
