package com.Rexe0.Items.Swords.Common;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class WoodenSword extends CustomItem {
    public WoodenSword() {
        super(Material.WOODEN_SWORD, 1, "Wooden Sword", "COMMON", 4, "WOODEN_SWORD", "SWORD", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
