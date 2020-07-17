package com.Rexe0.Items.Swords.Rare;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Effect.IceBolt;
import com.Rexe0.Items.CustomItem;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.HelixEffect;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class FrozenScythe extends CustomItem {
    public static HashMap<Player, Boolean> fsCD = new HashMap<>();
    public FrozenScythe() {
        super(Material.IRON_HOE, 1, "Frozen Scythe", "RARE", 16f, "FROZEN_SCYTHE", "SWORD", 26000);
    }

    public static void use(Action clickType, Player player) {
        if (clickType == Action.RIGHT_CLICK_BLOCK || clickType == Action.RIGHT_CLICK_AIR) {
            fsCD.putIfAbsent(player, false);
            if (!fsCD.get(player)) {


                player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, 2);


                Location loc = player.getEyeLocation();

                Effect eff = new IceBolt(ColeCrafterSlayers.effectManager, player);
                eff.setLocation(loc);




                eff.iterations = 15;
                eff.start();




                fsCD.put(player, true);
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        fsCD.put(player, false);
                    }
                }, 10);
            }
        }
    }
}
