package com.Rexe0.Items.Armor.SuperiorDragon;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SuperiorDragonLeggings extends CustomItem {
    public SuperiorDragonLeggings() {
        super(Material.LEATHER_LEGGINGS, 1, "Superior Dragon Leggings", "LEGENDARY", 3, 3,34, 13, 1, 0.5f,"SUPERIOR_DRAGON_LEGGINGS", "LEGGINGS", 500000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(242, 223, 17));
        this.setItemMeta(meta);
    }
}
