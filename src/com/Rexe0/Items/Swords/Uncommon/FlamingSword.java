package com.Rexe0.Items.Swords.Uncommon;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class FlamingSword extends CustomItem {
    public FlamingSword() {
        super(Material.IRON_SWORD, 1, "Flaming Sword", "UNCOMMON", 14, "FLAMING_SWORD", "SWORD", 70);
    }

    public static void use(Action clickType, Player player) {

    }
}
