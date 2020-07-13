package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class IronPickaxe extends CustomItem {
    public IronPickaxe() {
        super(Material.IRON_PICKAXE, 1, "Stone Pickaxe", "COMMON", 0, "IRON_PICKAXE", "TOOL", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
