package com.Rexe0.Items.SpecialItems;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class NewYearCake extends CustomItem {
    public NewYearCake() {
        super(Material.CAKE, 1, "New Year Cake", "SPECIAL", 0, "NEW_YEAR_CAKE", "", 0);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }

    public static void use(Action clickType, Player player) {

    }
}
