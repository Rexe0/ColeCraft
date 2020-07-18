package com.Rexe0.Items.Swords.Rare;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.DefenseNerf;
import com.Rexe0.Effect.IceBolt;
import com.Rexe0.Items.CustomItem;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.HelixEffect;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
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

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location location = ((IceBolt) eff).beamLocation;

                        if (location != null) {

                            for (Entity e : location.getChunk().getEntities()) {
                                if (e instanceof LivingEntity && !(e instanceof Player)) {
                                    LivingEntity en = (LivingEntity) e;

                                    if (en.getEyeLocation().distanceSquared(location) < 5.0) {
                                        if (DefenseNerf.isFullSet(player, "FROZEN_BLAZE_HELMET", "FROZEN_BLAZE_CHESTPLATE", "FROZEN_BLAZE_LEGGINGS", "FROZEN_BLAZE_BOOTS")) {
                                            en.damage(15, player);
                                        } else {
                                            en.damage(10, player);
                                        }

                                        en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 6, true, true));
                                        en.getWorld().spawnParticle(Particle.CLOUD, en.getLocation(), 10, 0.1, 0.2, 0.1, 0);
                                    }
                                }
                            }
                        }
                        if (eff.isDone()) {
                            cancel();
                        }
                    }
                }.runTaskTimer(ColeCrafterSlayers.getInstance(), 0, 1);




                fsCD.put(player, true);
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        fsCD.put(player, false);
                    }
                }, 6);
            }
        }
    }
}
