package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.HashMap;

public class Treecapitator extends CustomItem {
    public Treecapitator() {
        super(Material.GOLDEN_AXE, 1, "Treecapitator", "EPIC", 0, "TREECAPITATOR", "TOOL", 90000);
    }

    public static void use(Action clickType, Player player) {

    }
}
