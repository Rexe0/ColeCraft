package com.Rexe0.Items.Swords.Common;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class StoneSword extends CustomItem {
    public StoneSword() {
        super(Material.STONE_SWORD, 1, "Stone Sword", "COMMON", 5, "STONE_SWORD", "SWORD", 2);
    }

    public static void use(Action clickType, Player player) {

    }
}
