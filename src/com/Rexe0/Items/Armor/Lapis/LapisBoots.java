package com.Rexe0.Items.Armor.Lapis;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LapisBoots extends CustomItem {
    public LapisBoots() {
        super(Material.LEATHER_BOOTS, 1, "Lapis Boots", "UNCOMMON", 0, 4, 0, "LAPIS_BOOTS", "BOOTS", 200);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(0, 0, 255));
        this.setItemMeta(meta);
    }
}
