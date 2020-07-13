package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ZombieRing extends CustomItem {
    public ZombieRing() {
        super(Material.PLAYER_HEAD, 1, "Zombie Ring", "UNCOMMON", 0, "ZOMBIE_RING", "Accessory", 300);
    }

    public static void use(Action clickType, Player player) {

    }
}
