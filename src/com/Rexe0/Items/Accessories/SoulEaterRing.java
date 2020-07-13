package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class SoulEaterRing extends CustomItem {
    public SoulEaterRing() {
        super(Material.PLAYER_HEAD, 1, "Soul Eater Ring", "LEGENDARY", 0, "SOUL_EATER_RING", "Accessory", 492000);
    }

    public static void use(Action clickType, Player player) {

    }
}
