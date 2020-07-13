package com.Rexe0.Items.Materials;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantedRottenFlesh extends CustomMaterial {
    public EnchantedRottenFlesh() {
        super(Material.ROTTEN_FLESH, 1, "Enchanted Rotten Flesh", "UNCOMMON", "ENCHANTED_ROTTEN_FLESH", 90);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }
}
