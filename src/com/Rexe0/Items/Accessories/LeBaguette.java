package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class LeBaguette extends CustomItem {
    public LeBaguette() {
        super(Material.PLAYER_HEAD, 1, "Le Baguette", "EPIC", 0, "LE_BAGUETTE", "Accessory", 1100000);
    }

    public static void use(Action clickType, Player player) {

    }
}
