package com.Rexe0.Items.Armor.FrozenBlaze;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FrozenBlazeBoots extends CustomItem {
    public FrozenBlazeBoots() {
        super(Material.LEATHER_BOOTS, 1, "Frozen Blaze Boots", "LEGENDARY", 8, 2, 20,5,"FROZEN_BLAZE_BOOTS", "BOOTS", 33600);
        LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
        meta.setColor(Color.fromRGB(160, 218, 239));
        this.setItemMeta(meta);
    }
}
