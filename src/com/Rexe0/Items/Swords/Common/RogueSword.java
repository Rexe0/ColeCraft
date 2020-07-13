package com.Rexe0.Items.Swords.Common;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.HashMap;
import java.util.UUID;

public class RogueSword extends CustomItem {
    static HashMap<Player, Boolean> rogueSwordCooldown = new HashMap<>();


    public RogueSword() {
        super(Material.GOLDEN_SWORD, 1, "Rogue Sword", "COMMON", 4, "ROGUE_SWORD", "SWORD", 50);
    }

    public static void use(Action clickType, Player player) {
        if (clickType.equals(Action.RIGHT_CLICK_AIR) || clickType.equals(Action.RIGHT_CLICK_BLOCK)) {
            rogueSwordCooldown.putIfAbsent(player, false);
            if (!rogueSwordCooldown.get(player)) {
                rogueSwordCooldown.put(player, true);
                UUID uuid = UUID.randomUUID();
                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier(uuid, "rogueSword", 0.4, AttributeModifier.Operation.ADD_SCALAR));


                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        rogueSwordCooldown.put(player, false);
                    }
                }, 800);

                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(new AttributeModifier(uuid, "rogueSword", 0.4, AttributeModifier.Operation.ADD_SCALAR));
                    }
                }, 600);
            } else {
                player.sendMessage(ChatColor.RED+"This ability is on cooldown.");
            }
        }
    }
}
