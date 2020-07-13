package com.Rexe0.Items.SummoningItems;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class MoltenCore extends CustomItem {
    public MoltenCore() {
        super(Material.MAGMA_CREAM, 1, "Molten Core", "RARE", 0, "MOLTEN_CORE", "SUMMON", 0);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }

    public static void use(Action clickType, Player player) {

    }
}
