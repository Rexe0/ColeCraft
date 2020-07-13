package com.Rexe0.Items.Tools;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.HashMap;

public class JungleAxe extends CustomItem {
    public static HashMap<Player, Integer> jungleAxeLimit = new HashMap<>();
    public JungleAxe() {
        super(Material.WOODEN_AXE, 1, "Jungle Axe", "UNCOMMON", 0, "JUNGLE_AXE", "TOOL", 4000);
    }

    public static void use(Action clickType, Player player) {

    }
}
