package com.Rexe0.Items.Swords.Uncommon;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class SilverFang extends CustomItem {
    public SilverFang() {
        super(Material.GHAST_TEAR, 1, "Silver Fang", "UNCOMMON", 20f, "SILVER_FANG", "SWORD",  600);
    }

    public static void use(Action clickType, Player player) {

    }
}
