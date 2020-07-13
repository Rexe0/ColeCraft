package com.Rexe0.Items.Bows;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class Bow extends CustomItem {
    public Bow() {
        super(Material.BOW, 1, "Bow", "COMMON", 6f, "BOW", "BOW", 3);
    }

    public static void use(Action clickType, Player player) {

    }
}
