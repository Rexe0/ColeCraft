package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class WoodenPickaxe extends CustomItem {
    public WoodenPickaxe() {
        super(Material.WOODEN_PICKAXE, 1, "Wooden Pickaxe", "COMMON", 0, "WOODEN_PICKAXE", "TOOL", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
