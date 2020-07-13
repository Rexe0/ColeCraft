package com.Rexe0.Items.Wands;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class WandOfRejuvenation extends CustomItem {
    private static HashMap<Player, Boolean> healCD = new HashMap<>();

    public WandOfRejuvenation() {
        super(Material.STICK, 1, "Wand of Rejuvenation", "LEGENDARY", 0, "WAND_OF_REJUVENATION", "WAND", 50000);
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

                Bukkit.getServer().getPluginManager().callEvent(new EntityRegainHealthEvent(player, 20, EntityRegainHealthEvent.RegainReason.CUSTOM));


                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        healCD.put(player, false);
                    }
                }, 480);
            } else {
                player.sendMessage(ChatColor.RED+"This ability is on cooldown.");
            }
        }
    }
}
