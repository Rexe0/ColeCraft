package com.Rexe0.Items.Armor.Magma;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class MagmaLeggings extends CustomItem {
    public MagmaLeggings() {
        super(Material.LEATHER_LEGGINGS, 1, "Magma Leggings", "EPIC", 0, 0,5, 7.5f, "MAGMA_LEGGINGS", "LEGGINGS", 5100);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(255, 147, 0));
        this.setItemMeta(meta);
    }
}
