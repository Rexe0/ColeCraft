package com.Rexe0.Items.Materials;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantedSugarCane extends CustomMaterial {
    public EnchantedSugarCane() {
        super(Material.SUGAR_CANE, 1, "Enchanted Sugar Cane", "RARE", "ENCHANTED_SUGAR_CANE", 400);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }
}
