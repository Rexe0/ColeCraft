package com.Rexe0.Items.Armor.Speedster;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SpeedsterLeggings extends CustomItem {
    public SpeedsterLeggings() {
        super(Material.LEATHER_LEGGINGS, 1, "Speedster Pants", "EPIC", 0, 20,19, 0, "SPEEDSTER_LEGGINGS", "LEGGINGS", 2800);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(224, 252, 247));
        this.setItemMeta(meta);
    }
}
