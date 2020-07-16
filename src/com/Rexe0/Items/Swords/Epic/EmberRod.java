package com.Rexe0.Items.Swords.Epic;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.HashMap;

public class EmberRod extends CustomItem {
    static HashMap<Player, Boolean> emberRodCooldown = new HashMap<>();

    public EmberRod() {
        super(Material.BLAZE_ROD, 1, "Ember Rod", "EPIC", 23, "EMBER_ROD", "SWORD", 21000);
    }

    public static void use(Action clickType, Player player) {
        emberRodCooldown.putIfAbsent(player, false);
        if (clickType == Action.RIGHT_CLICK_AIR || clickType == Action.RIGHT_CLICK_BLOCK) {
            if (!emberRodCooldown.get(player)) {
                emberRodCooldown.put(player, true);

                Location loc = player.getLocation();
                loc.setY(loc.getY()+1.2);

                player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 1);
                LargeFireball fireball = player.getWorld().spawn(loc, LargeFireball.class);
                fireball.setShooter(player);

                fireball.setDirection(player.getLocation().getDirection());

                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        emberRodCooldown.put(player, false);
                    }
                }, 400);

                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 1);
                        LargeFireball fireball = player.getWorld().spawn(loc, LargeFireball.class);
                        fireball.setShooter(player);

                        fireball.setDirection(player.getLocation().getDirection());

                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 1);
                                LargeFireball fireball = player.getWorld().spawn(loc, LargeFireball.class);
                                fireball.setShooter(player);

                                fireball.setDirection(player.getLocation().getDirection());
                            }
                        }, 5);
                    }
                }, 5);
            } else {
            player.sendMessage(ChatColor.RED+"This ability is on cooldown.");
            }
        }
    }
}
