package com.Rexe0.Items.Armor.Magma;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class MagmaHelmet extends CustomItem {
    public MagmaHelmet() {
        super(Material.LEATHER_HELMET, 1, "Magma Helmet", "EPIC", 0, 0,3, 1.5f, "MAGMA_HELMET", "HELMET", 3800);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(255, 147, 0));
        this.setItemMeta(meta);
    }
}
