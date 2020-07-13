package com.Rexe0.Items.Armor.ElegantTuxedo;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ElegantTuxedoPants extends CustomItem {
    public ElegantTuxedoPants() {
        super(Material.LEATHER_LEGGINGS, 1, "Elegant Tuxedo Pants", "LEGENDARY", 5, 0, 0, "ELEGANT_TUXEDO_LEGGINGS", "LEGGINGS", 25000000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(254, 253, 252));
        this.setItemMeta(meta);
    }
}