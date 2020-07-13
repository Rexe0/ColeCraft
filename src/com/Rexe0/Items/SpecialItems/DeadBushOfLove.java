package com.Rexe0.Items.SpecialItems;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class DeadBushOfLove extends CustomItem {
    public DeadBushOfLove() {
        super(Material.DEAD_BUSH, 1, "Dead Bush Of Love", "SPECIAL", 0, "DEAD_BUSH_OF_LOVE", "", 0);
    }

    public static void use(Action clickType, Player player) {

    }
}
