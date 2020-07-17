package com.Rexe0.Items.Armor.Magma;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class MagmaChestplate extends CustomItem {
    public MagmaChestplate() {
        super(Material.LEATHER_CHESTPLATE, 1, "Magma Chestplate", "EPIC", 0, 0,6, 10f, "MAGMA_CHESTPLATE", "CHESTPLATE", 6000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(255, 147, 0));
        this.setItemMeta(meta);
    }
}
