package com.Rexe0.Items.Swords.Uncommon;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class Cleaver extends CustomItem {
    public Cleaver() {
        super(Material.GOLDEN_SWORD, 1, "Cleaver", "UNCOMMON", 10, "CLEAVER", "SWORD", 30);
        this.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 4);
    }

    public static void use(Action clickType, Player player) {

    }
}
