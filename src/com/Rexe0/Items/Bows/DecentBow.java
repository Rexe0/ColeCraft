package com.Rexe0.Items.Bows;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class DecentBow extends CustomItem {
    public DecentBow() {
        super(Material.BOW, 1, "Decent Bow", "UNCOMMON", 9f, "DECENT_BOW", "BOW", 100);
    }

    public static void use(Action clickType, Player player) {

    }
}
