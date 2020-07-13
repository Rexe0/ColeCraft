package com.Rexe0.Items.Swords.Epic;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ReaperFalchion extends CustomItem {
    public ReaperFalchion() {
        super(Material.DIAMOND_SWORD, 1, "Reaper Falchion", "EPIC", 44, "REAPER_FALCHION", "SWORD", 300000);
    }

    public static void use(Action clickType, Player player) {

    }
}
