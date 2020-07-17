package com.Rexe0.Items.Armor.FrozenBlaze;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FrozenBlazeChestplate extends CustomItem {
    public FrozenBlazeChestplate() {
        super(Material.LEATHER_CHESTPLATE, 1, "Frozen Blaze Chestplate", "LEGENDARY", 8, 2, 36,5,"FROZEN_BLAZE_CHESTPLATE", "CHESTPLATE", 41600);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(160, 218, 239));
        this.setItemMeta(meta);
    }
}
