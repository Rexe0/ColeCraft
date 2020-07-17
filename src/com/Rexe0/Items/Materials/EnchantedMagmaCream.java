package com.Rexe0.Items.Materials;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantedMagmaCream extends CustomMaterial {

    public EnchantedMagmaCream() {
        super(Material.MAGMA_CREAM, 1, "Enchanted Magma Cream", "UNCOMMON", "ENCHANTED_MAGMA_CREAM", 60);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }

}

