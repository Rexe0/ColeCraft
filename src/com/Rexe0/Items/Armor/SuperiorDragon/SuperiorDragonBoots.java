package com.Rexe0.Items.Armor.SuperiorDragon;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SuperiorDragonBoots extends CustomItem {
    public SuperiorDragonBoots() {
        super(Material.LEATHER_BOOTS, 1, "Superior Dragon Boots", "LEGENDARY", 3, 3,22, 8, 1, 0.5f, "SUPERIOR_DRAGON_BOOTS", "BOOTS", 350000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(242, 93, 24));
        this.setItemMeta(meta);
    }
}
