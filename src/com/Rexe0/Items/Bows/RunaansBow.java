package com.Rexe0.Items.Bows;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class RunaansBow extends CustomItem {
    public RunaansBow() {
        super(Material.BOW, 1, "Runaan's Bow", "LEGENDARY", 42f, "RUNAANS_BOW", "BOW", 15000);
    }

    public static void use(Action clickType, Player player) {

    }
}
