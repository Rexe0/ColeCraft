package com.Rexe0.Items.Swords.Common;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class AspectOfTheJerry extends CustomItem {
    public AspectOfTheJerry() {
        super(Material.WOODEN_SWORD, 1, "Aspect of the Jerry", "COMMON", 0.2f, "ASPECT_OF_THE_JERRY", "SWORD", 1);
    }

    public static void use(Action clickType, Player player) {
        if (clickType == Action.RIGHT_CLICK_AIR || clickType == Action.RIGHT_CLICK_BLOCK) player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, 1);
    }
}
