package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ZombieTalisman extends CustomItem {
    public ZombieTalisman() {
        super(Material.PLAYER_HEAD, 1, "Zombie Talisman", "COMMON", 0, "ZOMBIE_TALISMAN", "Accessory", 5);
    }

    public static void use(Action clickType, Player player) {

    }
}
