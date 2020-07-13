package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class WoodenAxe extends CustomItem {
    public WoodenAxe() {
        super(Material.WOODEN_AXE, 1, "Wooden Axe", "COMMON", 0, "WOODEN_AXE", "TOOL", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
