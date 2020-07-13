package com.Rexe0.Items.Materials;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantedGhastTear extends CustomMaterial {
    public EnchantedGhastTear() {
        super(Material.GHAST_TEAR, 1, "Enchanted Ghast tear", "UNCOMMON", "ENCHANTED_GHAST_TEAR", 100);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }
}
