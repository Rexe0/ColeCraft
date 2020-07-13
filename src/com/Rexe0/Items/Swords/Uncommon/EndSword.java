package com.Rexe0.Items.Swords.Uncommon;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class EndSword extends CustomItem {
    public EndSword() {
        super(Material.DIAMOND_SWORD, 1, "End Sword", "UNCOMMON", 7, "END_SWORD", "SWORD", 120);
    }

    public static void use(Action clickType, Player player) {

    }
}
