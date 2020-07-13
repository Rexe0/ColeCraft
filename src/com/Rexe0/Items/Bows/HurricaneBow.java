package com.Rexe0.Items.Bows;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class HurricaneBow extends CustomItem {
    public HurricaneBow() {
        super(Material.BOW, 1, "Hurricane Bow", "EPIC", 28f, "HURRICANE_BOW", "BOW", 5000);
    }

    public static void use(Action clickType, Player player) {

    }
}
