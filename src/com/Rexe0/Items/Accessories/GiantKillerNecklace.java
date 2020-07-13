package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class GiantKillerNecklace extends CustomItem {
    public GiantKillerNecklace() {
        super(Material.PLAYER_HEAD, 1, "Giant Killer Necklace", "RARE", 0, "GIANT_KILLER_NECKLACE", "Accessory", 900);
    }

    public static void use(Action clickType, Player player) {

    }
}
