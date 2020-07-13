package com.Rexe0.Slayers;

import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.DefenseNerf;
import com.Rexe0.Items.CustomItem;
import com.Rexe0.Items.Materials.CustomMaterial;
import net.minecraft.server.v1_16_R1.ChatMessageType;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutChat;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.UUID;

public class SellCommand implements CommandExecutor {
    public static HashMap<Player, Boolean> canBuild = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Please specify the ID of the item you want to sell. The ID of an item is usually its name with underscores as spaces.");
                return true;
            } else {
                String arg = args[0].toUpperCase();

                CustomItem resultItem = CustomItem.getItemClass(arg);

                CustomMaterial resultMaterial = CustomMaterial.getItemClass(arg);

                boolean isMaterial = false;

                if (resultItem == null && resultMaterial == null) {
                    sender.sendMessage(ChatColor.RED + "Not a valid item ID.");
                    return true;
                }
                if (resultItem == null) {
                    isMaterial = true;
                }
                if (resultMaterial == null) {
                    isMaterial = false;
                }

                Player player = (Player) sender;

                long coins = 0;
                for (int i = 0; i < player.getInventory().getContents().length; i++) {
                    ItemStack item = player.getInventory().getItem(i);
                    String foundValue = DefenseNerf.getItemID(item);

                    if (foundValue != null) {
                        if (foundValue.equals(arg)) {
                            if (isMaterial) {
                                int amount = item.getAmount();
                                coins += amount * resultMaterial.getValue();
                                player.getInventory().setItem(i, new ItemStack(Material.AIR));
                            }
                            if (!isMaterial) {
                                int amount = item.getAmount();
                                coins += amount * resultItem.getValue();
                                player.getInventory().setItem(i, new ItemStack(Material.AIR));
                            }
                        }
                    }
                }

                NumberFormat numberFormat = NumberFormat.getInstance();

                numberFormat.setGroupingUsed(true);

                String formattedCoins = numberFormat.format(coins);
                player.sendMessage(ChatColor.GRAY+"Sold all of those items for: "+ChatColor.GOLD+formattedCoins+ChatColor.GRAY+" coins.");
                player.sendMessage(ChatColor.RED+"You will not be refunded if your sell was an accident.");

                ColeCrafterSlayers.setCoins(player, ColeCrafterSlayers.getCoins(player)+coins);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2f, 1.5f);
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2f, 1.9f);
                    }
                }, 2);

            }
        }
        return true;
    }

}
