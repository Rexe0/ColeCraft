package com.Rexe0.Items.Swords.Rare;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class AspectOfTheEnd extends CustomItem {
    public static HashMap<Player, Integer> aoteCD = new HashMap<>();
    public AspectOfTheEnd() {
        super(Material.DIAMOND_SWORD, 1, "Aspect of the End", "RARE", 40f, "ASPECT_OF_THE_END", "SWORD", 55000);
    }

    public static void use(Action clickType, Player player) {
        if (clickType == Action.RIGHT_CLICK_BLOCK || clickType == Action.RIGHT_CLICK_AIR) {
            aoteCD.putIfAbsent(player, 0);
            if (aoteCD.get(player) < 5) {

                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), ("execute as @a[name="+player.getName()+"] at @s run tp @s ^ ^ ^8"));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 1);

                player.setVelocity(new Vector(0, 0, 0));

                aoteCD.put(player, aoteCD.get(player)+1);
                if (aoteCD.get(player) == 5) {
                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            aoteCD.put(player, 0);
                        }
                    }, 200);
                }
            } else {
                player.sendMessage(ChatColor.RED+"This ability is on cooldown.");
            }
        }
    }
}
