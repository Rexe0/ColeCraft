package com.Rexe0.Items.Swords.Uncommon;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.HashMap;

public class GrapplingHook extends CustomItem {
    public static HashMap<Player, Boolean> grappleCooldown = new HashMap<>();

    public GrapplingHook() {
        super(Material.FISHING_ROD, 1, "Grappling Hook", "UNCOMMON", 0, "GRAPPLING_HOOK", "", 360);
    }

    public static void use(Action clickType, Player player) {

    }
}
