package com.Rexe0.Items.Materials;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantedBlazeRod extends CustomMaterial {
    public EnchantedBlazeRod() {
        super(Material.BLAZE_ROD, 1, "Enchanted Blaze Rod", "RARE", "ENCHANTED_BLAZE_ROD", 2000);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }
}
