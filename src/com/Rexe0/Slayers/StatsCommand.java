package com.Rexe0.Slayers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;

import java.text.NumberFormat;
import java.util.ArrayList;

public class StatsCommand implements CommandExecutor {

    public static ArrayList<Inventory> statsInventorys = new ArrayList<>();

    public static float getDefence(LivingEntity player) {
        float health = 0;
        double protectionTotal = 0;

        ItemStack helmet = null;
        ItemStack chest = null;
        ItemStack boots = null;
        ItemStack pants = null;
        if (player instanceof Player) {
            PlayerInventory inv = ((Player) player).getInventory();
            helmet = inv.getHelmet();
            chest = inv.getChestplate();
            boots = inv.getBoots();
            pants = inv.getLeggings();
        } else {
            EntityEquipment inv = player.getEquipment();
            helmet = inv.getHelmet();
            chest = inv.getChestplate();
            boots = inv.getBoots();
            pants = inv.getLeggings();
        }


        helmetCalc:
        if (helmet != null) {
            protectionTotal += helmet.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);

            if (helmet.hasItemMeta()) {
                if (!helmet.getItemMeta().hasAttributeModifiers()) {
                    helmet = null;
                    break helmetCalc;
                }
                if (helmet.getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR) != null) {
                    for (AttributeModifier am : helmet.getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR)) {
                        health += am.getAmount();
                    }
                }
            }
        }

        chestCalc:
        if (chest != null) {
            protectionTotal += chest.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);

            if (chest.hasItemMeta()) {
                if (!chest.getItemMeta().hasAttributeModifiers()) {
                    chest = null;
                    break chestCalc;
                }
                if (chest.getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR) != null) {
                    for (AttributeModifier am : chest.getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR)) {
                        health += am.getAmount();
                    }
                }
            }
        }


        pantsCalc:
        if (pants != null) {
            protectionTotal += pants.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);

            if (pants.hasItemMeta()) {
                if (!pants.getItemMeta().hasAttributeModifiers()) {
                    pants = null;
                    break pantsCalc;
                }
                if (pants.getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR) != null) {
                    for (AttributeModifier am : pants.getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR)) {
                        health += am.getAmount();
                    }
                }
            }
        }


        bootsCalc:
        if (boots != null) {
            protectionTotal += boots.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);

            if (boots.hasItemMeta()) {
                if (!boots.getItemMeta().hasAttributeModifiers()) {
                    boots = null;
                    break bootsCalc;
                }
                if (boots.getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR) != null) {
                    for (AttributeModifier am : boots.getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR)) {
                        health += am.getAmount();
                    }
                }
            }
        }



        int resistanceLevel = 0;
        if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
            resistanceLevel = (player.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier() + 1) * 2;



        health += protectionTotal+resistanceLevel+(player.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue());


        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
            if (attribute.getName().equals("miningSkill")) {
                health += attribute.getAmount();

            }


        }

        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
            if (attribute.getName().equals("breadAccessory")) {
                health *= 1+(attribute.getAmount());
            }


        }



        return health;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED+"You must specify the player.");
                return true;
            } else {

                Player player = Bukkit.getPlayer(args[0]);

               if (player == null) {
                   sender.sendMessage(ChatColor.RED+"Not a valid player.");
                   return true;
               }


               Inventory statsPage = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY+player.getName()+"'s Stats");

               for (int i = 0; i < 54; i++) {
                   statsPage.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
               }

                for (int i = 0; i < 54; i++) {
                    if (i == 4) {
                        // Head Thing
                        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
                        SkullMeta meta = (SkullMeta) item.getItemMeta();
                        meta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));

                        meta.setDisplayName(ChatColor.GREEN+"Your Skyblock Profile");

                        ArrayList<String> lore = new ArrayList<>();

                        float health = (float) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
                            if (attribute.getName().equals("farmingSkill")) {
                                health += attribute.getAmount();

                            }

                        }

                        NumberFormat numberFormat = NumberFormat.getInstance();

                        numberFormat.setGroupingUsed(true);
                        String hp = numberFormat.format(health);

                        lore.add(ChatColor.RED+"❤ Health "+ChatColor.WHITE+hp+" HP");


                        health = getDefence(player);

                        hp = numberFormat.format(health);

                        lore.add(ChatColor.GREEN+"❈ Defense "+ChatColor.WHITE+hp);



                        health = (float) player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();


                        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers()) {
                            if (attribute.getName().equals("combatSkill")) {
                                health += attribute.getAmount();

                            }

                            if (attribute.getName().equals("foragingSkill")) {
                                health += attribute.getAmount();

                            }
                        }



                        hp = numberFormat.format(health);

                        lore.add(ChatColor.RED+"❁ Damage "+ChatColor.WHITE+hp);


                        double defaultValue = 0.10000000149011612;


                        health = (float) Math.floor(((player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()/defaultValue))*100);

                        hp = numberFormat.format(health);

                        lore.add(ChatColor.WHITE+"✦ Speed "+ChatColor.WHITE+hp);

                        health = (float) player.getAttribute(Attribute.GENERIC_LUCK).getValue();


                        hp = numberFormat.format(health);

                        lore.add(ChatColor.AQUA+"✯ Magic Find "+ChatColor.WHITE+hp);



                        meta.setLore(lore);

                        item.setItemMeta(meta);

                        statsPage.setItem(i, item);
                    }
                    if (i == 20) {
                        // Health

                        ItemStack item = new ItemStack(Material.GOLDEN_APPLE);

                        ItemMeta meta = item.getItemMeta();

                        float health = (float) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
                            if (attribute.getName().equals("farmingSkill")) {
                                health += attribute.getAmount();

                            }

                        }

                        float baseHealth = (float) (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());

                        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
                            if (attribute.getName().equals("farmingSkill")) {
                                baseHealth += attribute.getAmount();

                            }

                        }

                        float bonusHealth = (float) (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - baseHealth);

                        NumberFormat numberFormat = NumberFormat.getInstance();

                        numberFormat.setGroupingUsed(true);

                        String hp = numberFormat.format(health);

                        meta.setDisplayName(ChatColor.RED+"❤ Health "+ChatColor.WHITE+hp+" HP");

                        String regen = numberFormat.format(health/50f);

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY+"Health is your total maximum");
                        lore.add(ChatColor.GRAY+"health. Your natural");
                        lore.add(ChatColor.GRAY+"regeneration gives "+ChatColor.GREEN+regen+" HP");
                        lore.add(ChatColor.GRAY+"every"+ChatColor.GREEN+" 1s.");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Base Health: "+ChatColor.GREEN+baseHealth+" HP");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your base Health by");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"leveling your Farming and");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Fishing Skills and contributing");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"to the"+ChatColor.RESET+""+ChatColor.GOLD+" Chonky Boi"+ChatColor.DARK_GRAY+""+ChatColor.ITALIC+" in the ");
                        lore.add(ChatColor.RESET+"  "+ChatColor.DARK_GREEN+"Wilderness");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Bonus Health: "+ChatColor.DARK_GRAY+"+"+ChatColor.YELLOW+bonusHealth+" HP");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your bonus Health");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"by equipping Armor and holding");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Talismans in your inventory.");

                        meta.setLore(lore);

                        item.setItemMeta(meta);

                        statsPage.setItem(i, item);

                    }
                    if (i == 21) {
                        // Defense

                        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);

                        ItemMeta meta = item.getItemMeta();

                        float health = getDefence(player);

                        float baseHealth = (float) player.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue();


                        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
                            if (attribute.getName().equals("miningSkill")) {
                                baseHealth += attribute.getAmount();

                            }


                        }

                        float bonusHealth = (float) health - baseHealth;

                        NumberFormat numberFormat = NumberFormat.getInstance();

                        numberFormat.setGroupingUsed(true);

                        String hp = numberFormat.format(health);

                        double ehp = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*(health/20+1);
                        ehp = (float) (Math.floor(ehp*10f))/10f;

                        String formattedEHP = numberFormat.format(ehp);

                        float damageReduction = health/(health+20f);
                        damageReduction = (float) (Math.floor(damageReduction*1000f))/10f;

                        String DR = damageReduction+"";

                        meta.setDisplayName(ChatColor.GREEN+"❈ Defense "+ChatColor.WHITE+hp);

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY+"Defense reduces the damage that");
                        lore.add(ChatColor.GRAY+"you take from enemies.");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Base Defense: "+ChatColor.GREEN+baseHealth);
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your base Defense by");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"leveling your Mining skill");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"and contributing to the");
                        lore.add(ChatColor.RESET+"  "+ChatColor.GOLD+"Chonky Boi"+ChatColor.DARK_GRAY+""+ChatColor.ITALIC+" in the "+ChatColor.RESET+""+ChatColor.DARK_GREEN+"Wilderness");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Bonus Defense: "+ChatColor.DARK_GRAY+"+"+ChatColor.YELLOW+bonusHealth);
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your bonus Defense");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"by equipping Armor and holding");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Talismans in your inventory.");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Damage Reduction: "+ChatColor.GREEN+DR+"%");
                        lore.add(ChatColor.GRAY+"Effective Health: "+ChatColor.RED+""+formattedEHP+"❤");



                        meta.setLore(lore);

                        item.setItemMeta(meta);

                        statsPage.setItem(i, item);
                    }
                    if (i == 22) {
                        // Damage

                        ItemStack item = new ItemStack(Material.BLAZE_POWDER);

                        ItemMeta meta = item.getItemMeta();

                        float health = (float) player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();

                        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers()) {
                            if (attribute.getName().equals("combatSkill")) {
                                health += attribute.getAmount();

                            }

                            if (attribute.getName().equals("foragingSkill")) {
                                health += attribute.getAmount();

                            }
                        }

                        float baseHealth = (float) player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();

                        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers()) {
                            if (attribute.getName().equals("combatSkill")) {
                                baseHealth += attribute.getAmount();

                            }

                            if (attribute.getName().equals("foragingSkill")) {
                                baseHealth += attribute.getAmount();

                            }
                        }

                        float bonusHealth = (float) player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() - baseHealth;

                        NumberFormat numberFormat = NumberFormat.getInstance();

                        numberFormat.setGroupingUsed(true);

                        String hp = numberFormat.format(health);

                        meta.setDisplayName(ChatColor.RED+"❁ Damage "+ChatColor.WHITE+hp);

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY+"Damage increases your base");
                        lore.add(ChatColor.GRAY+"melee damage, including punching");
                        lore.add(ChatColor.GRAY+"and weapons.");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Base Damage: "+ChatColor.GREEN+baseHealth);
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your base Damage by");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"leveling your Combat and");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Foraging Skills and contributing");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"to the"+ChatColor.RESET+""+ChatColor.GOLD+" Chonky Boi"+ChatColor.DARK_GRAY+""+ChatColor.ITALIC+" in the ");
                        lore.add(ChatColor.RESET+"  "+ChatColor.DARK_GREEN+"Wilderness");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Bonus Damage: "+ChatColor.DARK_GRAY+"+"+ChatColor.YELLOW+bonusHealth);
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your bonus Damage");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"by equipping Armor and holding");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Talismans in your inventory.");

                        meta.setLore(lore);

                        item.setItemMeta(meta);

                        statsPage.setItem(i, item);
                    }
                    if (i == 23) {
                        // Speed

                        ItemStack item = new ItemStack(Material.SUGAR);

                        ItemMeta meta = item.getItemMeta();

                        double defaultValue = 0.10000000149011612;

                        float health = (float) Math.floor(((player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()/defaultValue))*100);

                        float baseHealth = (float) Math.floor(((player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue()/defaultValue))*100);


                        float bonusHealth = (float) Math.floor(((player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()/defaultValue))*100 - baseHealth);

                        NumberFormat numberFormat = NumberFormat.getInstance();

                        numberFormat.setGroupingUsed(true);

                        String hp = numberFormat.format(health);

                        meta.setDisplayName(ChatColor.WHITE+"✦ Speed "+ChatColor.WHITE+hp);

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY+"Speed increases your walk speed.");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Base Speed: "+ChatColor.GREEN+baseHealth);
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your base Speed by");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"contributing to the"+ChatColor.RESET+""+ChatColor.GOLD+" Chonky Boi"+ChatColor.DARK_GRAY+""+ChatColor.ITALIC+" in the ");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"in the "+ChatColor.RESET+""+ChatColor.DARK_GREEN+"Wilderness");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Bonus Speed: "+ChatColor.DARK_GRAY+"+"+ChatColor.YELLOW+bonusHealth);
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your bonus Speed by");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"equipping Armor and holding");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Talismans in your inventory.");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"You are "+ChatColor.GREEN+(health-100)+"% "+ChatColor.GRAY+"faster");


                        meta.setLore(lore);

                        item.setItemMeta(meta);

                        statsPage.setItem(i, item);
                    }
                    if (i == 24) {
                        // Magic Find

                        ItemStack item = new ItemStack(Material.STICK);

                        ItemMeta meta = item.getItemMeta();

                        float health = (float) player.getAttribute(Attribute.GENERIC_LUCK).getValue();

                        float baseHealth = (float) player.getAttribute(Attribute.GENERIC_LUCK).getBaseValue();


                        float bonusHealth = (float) player.getAttribute(Attribute.GENERIC_LUCK).getValue() - baseHealth;

                        NumberFormat numberFormat = NumberFormat.getInstance();

                        numberFormat.setGroupingUsed(true);

                        String hp = numberFormat.format(health);

                        meta.setDisplayName(ChatColor.AQUA+"✯ Magic Find "+ChatColor.WHITE+hp);

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY+"Magic Find increases how many");
                        lore.add(ChatColor.GRAY+"rare items you find.");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Base Magic Find: "+ChatColor.GREEN+baseHealth);
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"You cannot increase your");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"base Magic Find");
//                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your base Magic");
//                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Find by collecting unique pets.");
                        lore.add(ChatColor.GRAY+" ");
                        lore.add(ChatColor.GRAY+"Bonus Magic Find: "+ChatColor.DARK_GRAY+"+"+ChatColor.YELLOW+bonusHealth);
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Increase your bonus Magic");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"Find by equipping Armor and");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"holding Talismans in your");
                        lore.add(ChatColor.DARK_GRAY+"  "+ChatColor.ITALIC+"inventory.");


                        meta.setLore(lore);

                        item.setItemMeta(meta);

                        statsPage.setItem(i, item);
                    }



                }


               statsInventorys.add(statsPage);

               ((Player) sender).openInventory(statsPage);


            }
        }
        return true;
    }
}
