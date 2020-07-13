package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class DiamondAxe extends CustomItem {
    public DiamondAxe() {
        super(Material.DIAMOND_AXE, 1, "Diamond Axe", "UNCOMMON", 0, "DIAMOND_AXE", "TOOL", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
