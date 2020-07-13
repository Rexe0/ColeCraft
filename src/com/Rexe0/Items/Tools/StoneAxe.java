package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class StoneAxe extends CustomItem {
    public StoneAxe() {
        super(Material.STONE_AXE, 1, "Stone Axe", "COMMON", 0, "STONE_AXE", "TOOL", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
