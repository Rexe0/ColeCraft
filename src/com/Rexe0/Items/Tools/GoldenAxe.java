package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class GoldenAxe extends CustomItem {
    public GoldenAxe() {
        super(Material.GOLDEN_AXE, 1, "Golden Axe", "COMMON", 0, "GOLDEN_AXE", "TOOL", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
