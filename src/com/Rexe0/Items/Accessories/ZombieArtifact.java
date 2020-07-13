package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ZombieArtifact extends CustomItem {
    public ZombieArtifact() {
        super(Material.PLAYER_HEAD, 1, "Zombie Artifact", "RARE", 0, "ZOMBIE_ARTIFACT", "Accessory", 20000);
    }

    public static void use(Action clickType, Player player) {

    }
}
