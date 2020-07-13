package com.Rexe0.Items.Materials;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantedEmeraldBlock extends CustomMaterial {
    public EnchantedEmeraldBlock() {
        super(Material.EMERALD_BLOCK, 1, "Enchanted Emerald Block", "RARE", "ENCHANTED_EMERALD_BLOCK", 1700);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }
}
