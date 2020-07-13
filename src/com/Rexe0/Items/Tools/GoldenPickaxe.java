package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class GoldenPickaxe extends CustomItem {
    public GoldenPickaxe() {
        super(Material.GOLDEN_PICKAXE, 1, "Golden Pickaxe", "COMMON", 0, "GOLDEN_PICKAXE", "TOOL", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
