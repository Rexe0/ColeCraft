package com.Rexe0.Items.Materials;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantedBlazePowder extends CustomMaterial {
    public EnchantedBlazePowder() {
        super(Material.BLAZE_POWDER, 1, "Enchanted Blaze Powder", "UNCOMMON", "ENCHANTED_BLAZE_POWDER", 100);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }
}
