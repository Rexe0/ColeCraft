package com.Rexe0.Items.Swords.Common;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class UndeadSword extends CustomItem {
    public UndeadSword() {
        super(Material.IRON_SWORD, 1, "Undead Sword", "COMMON", 6, "UNDEAD_SWORD", "SWORD", 50);
    }

    public static void use(Action clickType, Player player) {

    }
}
