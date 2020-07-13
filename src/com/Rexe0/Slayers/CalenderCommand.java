package com.Rexe0.Slayers;

import net.minecraft.server.v1_16_R1.ChatMessageType;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CalenderCommand implements CommandExecutor {
    public static HashMap<Player, HashMap<Byte, Inventory>> calendar = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = manager.getMainScoreboard();

            Score day = scoreboard.getObjective("day").getScore("Time");
            Score month = scoreboard.getObjective("month").getScore("Time");
            Score year = scoreboard.getObjective("year").getScore("Time");


            String month1 = "";
            switch (Bukkit.getScoreboardManager().getMainScoreboard().getObjective("month").getScore("Time").getScore()) {
                case 1:
                    month1 = "Early Spring";
                    break;
                case 2:
                    month1 = "Spring";
                    break;
                case 3:
                    month1 = "Late Spring";
                    break;
                case 4:
                    month1 = "Early Summer";
                    break;
                case 5:
                    month1 = "Summer";
                    break;
                case 6:
                    month1 = "Late Summer";
                    break;
                case 7:
                    month1 = "Early Autumn";
                    break;
                case 8:
                    month1 = "Autumn";
                    break;
                case 9:
                    month1 = "Late Autumn";
                    break;
                case 10:
                    month1 = "Early Winter";
                    break;
                case 11:
                    month1 = "Winter";
                    break;
                case 12:
                    month1 = "Late Winter";
                    break;

            }
            calendar.putIfAbsent(player, new HashMap<>());

            calendar.get(player).putIfAbsent((byte) month.getScore(), Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY+""+month1+", Year "+year.getScore()));



            Inventory monthPage = calendar.get(player).get((byte) month.getScore());


            int j = 1;
            for (int i = 0; i < 54; i++) {
                if ((i > 0 && i < 8) || (i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 40)) {
                    ItemStack noEvent = new ItemStack(Material.PAPER, 1);
                    ItemMeta meta = noEvent.getItemMeta();
                    ArrayList<String> lore = new ArrayList<>();

                    boolean eventExists = false;

                    if (j <= 31 && j >= 29 && month.getScore() == 12) {
                        noEvent.setType(Material.CAKE);
                        noEvent.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        lore.add(ChatColor.GRAY+"All Day: "+ChatColor.LIGHT_PURPLE+"New Year Celebration");
                        eventExists = true;
                    }
                    if (!eventExists) {
                        lore.add(ChatColor.GRAY+"No events");
                    }
                    if (j == day.getScore()) {
                        noEvent.setType(Material.LIME_DYE);
                    }
                    noEvent.setAmount(j);

                    if (eventExists) {
                        noEvent.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    }

                    meta.setDisplayName(ChatColor.GREEN+"Day "+j);

                    meta.setLore(lore);
                    noEvent.setItemMeta(meta);
                    monthPage.setItem(i, noEvent);
                    j++;
                } else if (i == 45 && month.getScore() > 1) {
                    ItemStack back = new ItemStack(Material.ARROW, 1);

                    ItemMeta meta = back.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN+"Previous Page");
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.YELLOW+"Page "+(month.getScore()-1));
                    meta.setLore(lore);
                    back.setItemMeta(meta);
                    monthPage.setItem(i, back);
                } else if (i == 53 && month.getScore() < 12) {
                    ItemStack back = new ItemStack(Material.ARROW, 1);

                    ItemMeta meta = back.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN+"Next Page");
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.YELLOW+"Page "+(month.getScore()+1));
                    meta.setLore(lore);
                    back.setItemMeta(meta);
                    monthPage.setItem(i, back);
                } else {
                    monthPage.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                }

            }

            player.openInventory(monthPage);
        }
        return true;
    }
}
