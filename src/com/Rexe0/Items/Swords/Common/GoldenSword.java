package com.Rexe0.Items.Swords.Common;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class GoldenSword extends CustomItem {
    public GoldenSword() {
        super(Material.GOLDEN_SWORD, 1, "Gold Sword", "COMMON", 4, "GOLDEN_SWORD", "SWORD", 6);
    }

    public static void use(Action clickType, Player player) {

    }
}
