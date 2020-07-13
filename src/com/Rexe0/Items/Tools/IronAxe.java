package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class IronAxe extends CustomItem {
    public IronAxe() {
        super(Material.IRON_AXE, 1, "Iron Axe", "COMMON", 0, "IRON_AXE", "TOOL", 1);
    }

    public static void use(Action clickType, Player player) {

    }
}
