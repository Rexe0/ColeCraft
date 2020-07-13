package com.Rexe0.Items.Bows;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ScorpionBow extends CustomItem {
    public ScorpionBow() {
        super(Material.BOW, 1, "Scorpion Bow", "EPIC", 24f, "SCORPION_BOW", "BOW", 0);
    }

    public static void use(Action clickType, Player player) {

    }
}
