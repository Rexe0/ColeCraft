package com.Rexe0.Items.Swords.Legendary;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class AspectOfTheDragons extends CustomItem {
    public AspectOfTheDragons() {
        super(Material.DIAMOND_SWORD, 1, "Aspect of the Dragons", "LEGENDARY", 65f, "ASPECT_OF_THE_DRAGONS", "SWORD", 350000);
    }

    public static void use(Action clickType, Player player) {

    }
}
