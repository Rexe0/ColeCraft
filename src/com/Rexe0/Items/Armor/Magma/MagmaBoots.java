package com.Rexe0.Items.Armor.Magma;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class MagmaBoots extends CustomItem {
    public MagmaBoots() {
        super(Material.LEATHER_BOOTS, 1, "Magma Boots", "EPIC", 0, 0,3, 4.5f, "MAGMA_BOOTS", "BOOTS", 3000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(255, 147, 0));
        this.setItemMeta(meta);
    }
}
