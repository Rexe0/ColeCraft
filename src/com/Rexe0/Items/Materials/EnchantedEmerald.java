package com.Rexe0.Items.Materials;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantedEmerald extends CustomMaterial {
    public EnchantedEmerald() {
        super(Material.EMERALD, 1, "Enchanted Emerald", "UNCOMMON", "ENCHANTED_EMERALD", 85);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }
}
