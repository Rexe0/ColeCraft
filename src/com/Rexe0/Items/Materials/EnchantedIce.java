package com.Rexe0.Items.Materials;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantedIce extends CustomMaterial {
    public EnchantedIce() {
        super(Material.ICE, 1, "Enchanted Ice", "UNCOMMON", "ENCHANTED_ICE", 20);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }
}
