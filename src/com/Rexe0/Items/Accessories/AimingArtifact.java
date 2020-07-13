package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class AimingArtifact extends CustomItem {
    public AimingArtifact() {
        super(Material.LEATHER, 1, "Aiming Artifact", "EPIC", 0, "AIMING_ARTIFACT", "Accessory", 1000);
    }

    public static void use(Action clickType, Player player) {

    }
}
