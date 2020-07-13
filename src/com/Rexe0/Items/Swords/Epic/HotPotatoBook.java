package com.Rexe0.Items.Swords.Epic;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class HotPotatoBook extends CustomItem {
    public HotPotatoBook() {
        super(Material.BOOK, 1, "Hot Potato Book", "EPIC", 0, "HOT_POTATO_BOOK", "", 1200);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }

    public static void use(Action clickType, Player player) {

    }
}
