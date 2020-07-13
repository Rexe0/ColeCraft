package com.Rexe0.Items.Armor.ElegantTuxedo;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ElegantTuxedoChestplate extends CustomItem {
    public ElegantTuxedoChestplate() {
        super(Material.LEATHER_CHESTPLATE, 1, "Elegant Tuxedo Jacket", "LEGENDARY", 10, 0, 0, "ELEGANT_TUXEDO_CHESTPLATE", "CHESTPLATE", 25000000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(25, 25, 25));
        this.setItemMeta(meta);
    }
}