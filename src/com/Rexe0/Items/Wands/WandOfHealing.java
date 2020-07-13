package com.Rexe0.Items.Wands;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.CustomItem;
import com.Rexe0.Items.Materials.CustomMaterial;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class WandOfHealing extends CustomItem {
    private static HashMap<Player, Boolean> healCD = new HashMap<>(); 
    
    public WandOfHealing() {
        super(Material.STICK, 1, "Wand of Healing", "UNCOMMON", 0, "WAND_OF_HEALING", "WAND", 500);
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
    }

    public static void use(Action clickType, Player player) {
        healCD.putIfAbsent(player, false);
        if (clickType == Action.RIGHT_CLICK_AIR || clickType == Action.RIGHT_CLICK_BLOCK) {
            if (!healCD.get(player)) {
                healCD.put(player, true);

                Bukkit.getServer().getPluginManager().callEvent(new EntityRegainHealthEvent(player, 10, EntityRegainHealthEvent.RegainReason.CUSTOM));


                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        healCD.put(player, false);
                    }
                }, 300);
            } else {
                player.sendMessage(ChatColor.RED+"This ability is on cooldown.");
            }
        }
    }
}
