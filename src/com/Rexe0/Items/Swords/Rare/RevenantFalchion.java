package com.Rexe0.Items.Swords.Rare;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class RevenantFalchion extends CustomItem {
    public RevenantFalchion() {
        super(Material.DIAMOND_SWORD, 1, "Revenant Falchion", "RARE", 28, "REVENANT_FALCHION", "SWORD", 70000);
    }

    public static void use(Action clickType, Player player) {

    }
}
