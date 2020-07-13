package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class FrenchBread extends CustomItem {
    public FrenchBread() {
        super(Material.PLAYER_HEAD, 1, "French Bread", "RARE", 0, "FRENCH_BREAD", "Accessory", 300000);
    }

    public static void use(Action clickType, Player player) {

    }
}
