package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class FirstStrikeBead extends CustomItem {
    public FirstStrikeBead() {
        super(Material.PLAYER_HEAD, 1, "First Strike Bead", "RARE", 0, "FIRST_STRIKE_BEAD", "Accessory", 900);
    }

    public static void use(Action clickType, Player player) {

    }
}
