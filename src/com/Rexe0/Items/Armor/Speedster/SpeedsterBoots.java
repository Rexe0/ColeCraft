package com.Rexe0.Items.Armor.Speedster;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SpeedsterBoots extends CustomItem {
    public SpeedsterBoots() {
        super(Material.LEATHER_BOOTS, 1, "Speedster Shoes", "EPIC", 0, 20,13, 0, "SPEEDSTER_BOOTS", "BOOTS", 1600);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(224, 252, 247));
        this.setItemMeta(meta);
    }
}
