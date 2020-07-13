package com.Rexe0.Items.Armor.SuperiorDragon;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SuperiorDragonChestplate extends CustomItem {
    public SuperiorDragonChestplate() {
        super(Material.LEATHER_CHESTPLATE, 1, "Superior Dragon Chestplate", "LEGENDARY", 3, 3,38, 15, 1, 0.5f,"SUPERIOR_DRAGON_CHESTPLATE", "CHESTPLATE", 600000);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(242, 223, 17));
        this.setItemMeta(meta);
    }
}
