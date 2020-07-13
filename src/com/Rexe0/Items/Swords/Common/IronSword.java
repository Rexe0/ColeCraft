package com.Rexe0.Items.Swords.Common;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class IronSword extends CustomItem {
    public IronSword() {
        super(Material.IRON_SWORD, 1, "Iron Sword", "UNCOMMON", 6, "IRON_SWORD", "SWORD", 4);
    }

    public static void use(Action clickType, Player player) {

    }
}
