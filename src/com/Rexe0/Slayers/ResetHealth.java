package com.Rexe0.Slayers;

import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.Materials.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ResetHealth implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED+"You must specify the player.");
                return true;
            } else {

                if (sender.getName().equals("rexe0")) {
                    Player player = Bukkit.getPlayer(args[0]);

                    player.setMaxHealth(20);
                    player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);
                    player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
                    player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
                    player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.10000000149011612);
                    player.getAttribute(Attribute.GENERIC_LUCK).setBaseValue(0);

                    for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
                        if (attribute.getName().equals("miningSkill")) {
                            player.getAttribute(Attribute.GENERIC_ARMOR).removeModifier(attribute);

                        }

                    }

                    for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers()) {
                        if (attribute.getName().equals("combatSkill") || attribute.getName().equals("foragingSkill")) {
                            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(attribute);

                        }

                    }

                    for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
                        if (attribute.getName().equals("farmingSkill")) {
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(attribute);

                        }

                    }



                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    Scoreboard scoreboard = manager.getMainScoreboard();

                    scoreboard.getObjective("foragingLevel").getScore(player.getName()).setScore(0);
                    scoreboard.getObjective("foragingXP").getScore(player.getName()).setScore(0);
                    scoreboard.getObjective("combatLevel").getScore(player.getName()).setScore(0);
                    scoreboard.getObjective("combatXP").getScore(player.getName()).setScore(0);
                    scoreboard.getObjective("farmingLevel").getScore(player.getName()).setScore(0);
                    scoreboard.getObjective("farmingXP").getScore(player.getName()).setScore(0);
                    scoreboard.getObjective("miningLevel").getScore(player.getName()).setScore(0);
                    scoreboard.getObjective("miningXP").getScore(player.getName()).setScore(0);

                    ColeCrafterSlayers.setCoins(player, 0);
                    ColeCrafterSlayers.setBank(player, 0);
                } else {
                    sender.sendMessage(ChatColor.RED + "You are not rexe0.");
                }

            }
        }
        return true;
    }
}
