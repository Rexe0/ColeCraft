package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class ThunderlordArtifact extends CustomItem {
    public ThunderlordArtifact() {
        super(Material.PLAYER_HEAD, 1, "Thunderlord Artifact", "RARE", 0, "THUNDERLORD_ARTIFACT", "Accessory", 600);
    }

    public static void use(Action clickType, Player player) {

    }
}
