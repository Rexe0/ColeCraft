package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class FrenchSandwich extends CustomItem {
    public FrenchSandwich() {
        super(Material.PLAYER_HEAD, 1, "French Sandwich", "LEGENDARY", 0, "FRENCH_SANDWICH", "Accessory", 3500000);
    }

    public static void use(Action clickType, Player player) {

    }
}
