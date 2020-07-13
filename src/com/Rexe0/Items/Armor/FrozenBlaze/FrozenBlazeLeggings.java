package com.Rexe0.Items.Armor.FrozenBlaze;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FrozenBlazeLeggings extends CustomItem {
    public FrozenBlazeLeggings() {
        super(Material.LEATHER_LEGGINGS, 1, "Frozen Blaze Leggings", "LEGENDARY", 8, 2, 28,0,"FROZEN_BLAZE_LEGGINGS", "LEGGINGS", 39600);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(160, 218, 239));
        this.setItemMeta(meta);
    }
}
