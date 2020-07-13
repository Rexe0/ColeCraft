package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class DevourRing extends CustomItem {
    public DevourRing() {
        super(Material.PLAYER_HEAD, 1, "Devour Ring", "RARE", 0, "DEVOUR_RING", "Accessory", 53000);
    }

    public static void use(Action clickType, Player player) {

    }
}
