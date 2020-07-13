package com.Rexe0.Items.SpecialItems;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class HungTheFish extends CustomItem {
    public HungTheFish() {
        super(Material.SALMON, 1, "Hung the Fish", "SPECIAL", 0, "HUNG_THE_FISH", "", 0);
    }

    public static void use(Action clickType, Player player) {

    }
}
