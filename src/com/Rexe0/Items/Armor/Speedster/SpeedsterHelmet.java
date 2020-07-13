package com.Rexe0.Items.Armor.Speedster;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SpeedsterHelmet extends CustomItem {
    public SpeedsterHelmet() {
        super(Material.LEATHER_HELMET, 1, "Speedster Hat", "EPIC", 0, 20,13, 0, "SPEEDSTER_HELMET", "HELMET", 2000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(224, 252, 247));
        this.setItemMeta(meta);
    }
}
