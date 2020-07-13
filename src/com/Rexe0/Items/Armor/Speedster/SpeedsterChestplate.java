package com.Rexe0.Items.Armor.Speedster;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SpeedsterChestplate extends CustomItem {
    public SpeedsterChestplate() {
        super(Material.LEATHER_CHESTPLATE, 1, "Speedster Tunic", "EPIC", 0, 20,24, 0, "SPEEDSTER_CHESTPLATE", "CHESTPLATE", 3200);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(224, 252, 247));
        this.setItemMeta(meta);
    }
}
