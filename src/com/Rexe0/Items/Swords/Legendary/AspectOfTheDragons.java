package com.Rexe0.Items.Swords.Legendary;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.CustomItem;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.ConeEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class AspectOfTheDragons extends CustomItem {
    public AspectOfTheDragons() {
        super(Material.DIAMOND_SWORD, 1, "Aspect of the Dragons", "LEGENDARY", 65f, "ASPECT_OF_THE_DRAGONS", "SWORD", 350000);
    }

    public static void use(Action clickType, Player player) {
        if (clickType == Action.RIGHT_CLICK_AIR || clickType == Action.RIGHT_CLICK_BLOCK) {
            Location loc = player.getEyeLocation();

            Effect eff = new ConeEffect(ColeCrafterSlayers.effectManager);
            eff.setLocation(loc);

            ((ConeEffect)eff).lengthGrow = 0.01f;

            eff.iterations = 20;
            eff.start();



            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2, 1);
            for (Entity en : player.getNearbyEntities(2, 2, 2)) {
                if (en instanceof LivingEntity && !(en instanceof Player)) {
                    double knockBackRes = ((LivingEntity) en).getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue();

                    knockBackRes /= 10;
                    if (knockBackRes > 1) knockBackRes = 1;

                    String mobType = ColeCrafterSlayers.getMobType((LivingEntity) en);
                    boolean dontBlastAway = false;
                    if (mobType != null) {
                        if (mobType.equals("CREATURE") || mobType.equals("BOSS")) {
                            dontBlastAway = true;
                        }
                    }
                    if (!dontBlastAway) {

                        en.setVelocity((player.getLocation().getDirection().multiply(25)).multiply(1-knockBackRes));
                    }
                }
            }
        }
    }
}
