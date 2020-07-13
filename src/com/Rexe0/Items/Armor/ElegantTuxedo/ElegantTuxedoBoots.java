package com.Rexe0.Items.Armor.ElegantTuxedo;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ElegantTuxedoBoots extends CustomItem {
    public ElegantTuxedoBoots() {
        super(Material.LEATHER_BOOTS, 1, "Elegant Tuxedo Oxfords", "LEGENDARY", 5, 0, 0, "ELEGANT_TUXEDO_BOOTS", "BOOTS", 25000000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(25, 25, 25));
        this.setItemMeta(meta);
    }
}