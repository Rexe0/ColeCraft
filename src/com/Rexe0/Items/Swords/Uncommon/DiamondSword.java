package com.Rexe0.Items.Swords.Uncommon;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class DiamondSword extends CustomItem {
    public DiamondSword() {
        super(Material.DIAMOND_SWORD, 1, "Diamond Sword", "UNCOMMON", 7, "DIAMOND_SWORD", "SWORD", 10);
    }

    public static void use(Action clickType, Player player) {

    }
}
