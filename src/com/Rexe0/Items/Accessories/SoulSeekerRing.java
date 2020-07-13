package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class SoulSeekerRing extends CustomItem {
    public SoulSeekerRing() {
        super(Material.PLAYER_HEAD, 1, "Soul Seeker Ring", "EPIC", 0, "SOUL_SEEKER_RING", "Accessory", 129000);
    }

    public static void use(Action clickType, Player player) {

    }
}
