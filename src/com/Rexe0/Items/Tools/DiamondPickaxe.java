package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class DiamondPickaxe extends CustomItem {
    public DiamondPickaxe() {
        super(Material.DIAMOND_PICKAXE, 1, "Diamond Pickaxe", "COMMON", 0, "DIAMOND_PICKAXE", "TOOL", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
