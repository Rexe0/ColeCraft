package com.Rexe0.Items.Swords.Legendary;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.HashMap;

public class ReaperScythe extends CustomItem {
    static HashMap<Player, Boolean> scytheCooldown = new HashMap<>();

    public ReaperScythe() {
        super(Material.DIAMOND_HOE, 1, "Reaper Scythe", "LEGENDARY", 66.6f, "REAPER_SCYTHE", "SWORD", 2100000);
    }

    public static void use(Action clickType, Player player) {
        if (clickType == Action.RIGHT_CLICK_AIR || clickType == Action.RIGHT_CLICK_BLOCK) {

            scytheCooldown.putIfAbsent(player, false);
            if (!scytheCooldown.get(player)) {
                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 2, 1);


                float healthHeal = 0;

                for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
                    if (entity instanceof LivingEntity) {
                        if (!(entity instanceof Player)) {
                            double health = ((LivingEntity) entity).getHealth();
                            ((LivingEntity) entity).damage(health / 2);
                            healthHeal += (health / 2) / 10;

                            entity.getWorld().spawnParticle(Particle.BLOCK_CRACK, entity.getLocation(), 100, 0.2, 0.7, 0.2, Material.REDSTONE_BLOCK.createBlockData());
                        }
                    }
                }


                double maxHealth = (player).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                if (player.getHealth() + healthHeal < maxHealth) {
                    (player).setHealth(player.getHealth() + healthHeal);
                } else if (player.getHealth() + healthHeal > maxHealth) {
                    (player).setHealth(maxHealth);
                }

                player.getWorld().spawnParticle(Particle.BLOCK_CRACK, player.getLocation(), 70, 0.3, 0.7, 0.3, Material.EMERALD_BLOCK.createBlockData());

                scytheCooldown.put(player, true);

                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        scytheCooldown.put(player, false);
                    }
                }, 1200);
            } else {
                player.sendMessage(ChatColor.RED+"This ability is on cooldown.");
            }
        }

    }
}
