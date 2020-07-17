package com.Rexe0;

import com.Rexe0.CustomCrafting.CustomAnvil;
import com.Rexe0.CustomCrafting.CustomCrafting;
import com.Rexe0.CustomCrafting.CustomRecipe;
import com.Rexe0.CustomCrafting.RecipeCommand;
import com.Rexe0.Items.*;
import com.Rexe0.Mobs.CustomMob;
import com.Rexe0.Mobs.CustomMobCommand;
import com.Rexe0.NPCs.CustomNPC;
import com.Rexe0.Slayers.*;
import de.slikey.effectlib.EffectManager;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.io.File;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ColeCrafterSlayers extends JavaPlugin {

    private File f = new File(getDataFolder(), "mobspawns.yml");
    private YamlConfiguration Config = YamlConfiguration.loadConfiguration(f);


    public FileConfiguration getMobSpawnConfig() {
        return Config;
    }

    public File getMobSpawnFile() {
        return f;
    }

    public static HashMap<Player, String> currentLocation = new HashMap<>();
    public static HashMap<Player, HashMap<Integer, Integer>> craftingAmounts = new HashMap<>();
    public static HashMap<Player, ItemStack> craftingResult = new HashMap<>();
    public static HashMap<Arrow, LivingEntity> aimingArrowTarget = new HashMap<>();
    public static HashMap<Player, Boolean> displayActionbar = new HashMap<>();
    public static HashMap<Player, Short> anvilXpCost = new HashMap<>();
    public static Location magmaBossLocation;
    public static EffectManager effectManager;

    public static String currentTime = "12:00am";

    public static int scheduleSyncDelayedTask(Runnable runnable, int delay){
        return Bukkit.getScheduler().scheduleSyncDelayedTask(instance, runnable, delay);
    }

    public static int scheduleSyncLoop(Runnable runnable, int delay, int period){
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, runnable, delay, period);
    }



    public static void setCoins(Player player, long amount) {
        NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "coins");

        player.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.LONG, amount);
    }

    public static long getCoins(Player player) {
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "coins");

        PersistentDataContainer container = player.getPersistentDataContainer();

        long foundValue = 0;
        if (container.has(key, PersistentDataType.LONG)) {
            foundValue = container.get(key, PersistentDataType.LONG);
        }

        return foundValue;

    }

    public static void setSkillXP(Player player, String name, float amount) {
        NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), name+"XP");

        player.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.FLOAT, amount);

        float xp = ColeCrafterSlayers.getSkillXP(player, name);
        int level = (int) ColeCrafterSlayers.getSkillLevel(player, name);
        if (name.equals("combat")) {
            if (xp >= Math.ceil(100*(Math.pow(1.26, level))+300*(level)) && (level < 50)) {
                player.sendMessage(ChatColor.DARK_AQUA+"--------------------------------------\n"+ChatColor.AQUA+""+ChatColor.BOLD+" SKILL LEVEL UP"+ChatColor.RESET+""
                        +ChatColor.DARK_AQUA+" Combat "+ChatColor.DARK_GRAY+level+"→ "+ChatColor.DARK_AQUA+(level+1)+ChatColor.GREEN+""+ChatColor.BOLD+"\n Rewards\n  "+ChatColor.RESET+""+ChatColor.DARK_GRAY+"+"+ChatColor.GREEN+"0.25"+ChatColor.RED+" ❁ Damage\n"+ChatColor.DARK_AQUA+"--------------------------------------\n");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 0);
                setSkillLevel(player, "combat", level+1);


                UUID uuid = UUID.fromString("41c90f75-20ea-4f77-997a-db7a3f9d35a5");

                for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers()) {
                    if (attribute.getName().equals("combatSkill")) {
                        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(attribute);

                    }

                }
                player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(new AttributeModifier(uuid, "combatSkill", 0.25*level, AttributeModifier.Operation.ADD_NUMBER));

            }

        } else if (name.equals("farming")) {
            if (xp >= Math.ceil(100 * (Math.pow(1.26, level)) + 400 * (level)) && (level < 50)) {
                player.sendMessage(ChatColor.DARK_AQUA + "--------------------------------------\n" + ChatColor.AQUA + "" + ChatColor.BOLD + " SKILL LEVEL UP" + ChatColor.RESET + ""
                        + ChatColor.DARK_AQUA + " Farming " + ChatColor.DARK_GRAY + level + "→ " + ChatColor.DARK_AQUA + (level + 1) + ChatColor.GREEN + "" + ChatColor.BOLD + "\n Rewards\n  " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "+" + ChatColor.RED + "0.2 ❤ Health\n" + ChatColor.DARK_AQUA + "--------------------------------------\n");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 0);
                setSkillLevel(player, "farming", level + 1);


                UUID uuid = UUID.fromString("c455ce96-55c0-46c0-904f-f2dae31f710c");

                for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
                    if (attribute.getName().equals("farmingSkill")) {
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(attribute);

                    }

                }
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(new AttributeModifier(uuid, "farmingSkill", 0.2*level, AttributeModifier.Operation.ADD_NUMBER));

            }
        } else if (name.equals("foraging")) {
            if (xp >= Math.ceil(100 * (Math.pow(1.26, level)) + 400 * (level)) && (level < 50)) {
                player.sendMessage(ChatColor.DARK_AQUA + "--------------------------------------\n" + ChatColor.AQUA + "" + ChatColor.BOLD + " SKILL LEVEL UP" + ChatColor.RESET + ""
                        + ChatColor.DARK_AQUA + " Foraging " + ChatColor.DARK_GRAY + level + "→ " + ChatColor.DARK_AQUA + (level + 1) + ChatColor.GREEN + "" + ChatColor.BOLD + "\n Rewards\n  " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "+" + ChatColor.RED + "0.25 ❁ Damage\n" + ChatColor.DARK_AQUA + "--------------------------------------\n");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 0);
                setSkillLevel(player, "foraging", level + 1);


                UUID uuid = UUID.fromString("1f40a901-c26e-4144-b02e-e704c609b9e6");

                for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers()) {
                    if (attribute.getName().equals("foragingSkill")) {
                        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(attribute);

                    }

                }
                player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(new AttributeModifier(uuid, "foragingSkill", 0.25*level, AttributeModifier.Operation.ADD_NUMBER));

            }
        } else if (name.equals("mining")) {
            if (xp >= Math.ceil(100 * (Math.pow(1.26, level)) + 200 * (level)) && (level < 50)) {
                player.sendMessage(ChatColor.DARK_AQUA + "--------------------------------------\n" + ChatColor.AQUA + "" + ChatColor.BOLD + " SKILL LEVEL UP" + ChatColor.RESET + ""
                        + ChatColor.DARK_AQUA + " Mining " + ChatColor.DARK_GRAY + level + "→ " + ChatColor.DARK_AQUA + (level + 1) + ChatColor.GREEN + "" + ChatColor.BOLD + "\n Rewards\n  " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "+" + ChatColor.GREEN + "0.5 ❈ Defense\n" + ChatColor.DARK_AQUA + "--------------------------------------\n");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 0);
                setSkillLevel(player, "mining", level + 1);


                UUID uuid = UUID.fromString("de1c2a12-e421-41f9-8728-d8556e7fde60");

                for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
                    if (attribute.getName().equals("miningSkill")) {
                        player.getAttribute(Attribute.GENERIC_ARMOR).removeModifier(attribute);

                    }

                }
                player.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier(uuid, "miningSkill", 0.5*level, AttributeModifier.Operation.ADD_NUMBER));

            }
        }
    }

    public static float getSkillXP(Player player, String name) {
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), name+"XP");

        PersistentDataContainer container = player.getPersistentDataContainer();

        float foundValue = 0;
        if (container.has(key, PersistentDataType.FLOAT)) {
            foundValue = container.get(key, PersistentDataType.FLOAT);
        }

        return foundValue;

    }

    public static void setSkillLevel(Player player, String name, float amount) {
        NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), name+"LEVEL");

        player.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.FLOAT, amount);
    }

    public static float getSkillLevel(Player player, String name) {
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), name+"LEVEL");

        PersistentDataContainer container = player.getPersistentDataContainer();

        float foundValue = 0;
        if (container.has(key, PersistentDataType.FLOAT)) {
            foundValue = container.get(key, PersistentDataType.FLOAT);
        }

        return foundValue;

    }

    public static long getBank(Player player) {
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "bank");

        PersistentDataContainer container = player.getPersistentDataContainer();

        long foundValue = 0;
        if (container.has(key, PersistentDataType.LONG)) {
            foundValue = container.get(key, PersistentDataType.LONG);
        }

        return foundValue;
    }

    public static String getMobType(LivingEntity player) {
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobType");

        PersistentDataContainer container = player.getPersistentDataContainer();

        String foundValue = null;
        if (container.has(key, PersistentDataType.STRING)) {
            foundValue = container.get(key, PersistentDataType.STRING);
        }

        return foundValue;
    }

    public static String getMobID(LivingEntity player) {
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

        PersistentDataContainer container = player.getPersistentDataContainer();

        String foundValue = null;
        if (container.has(key, PersistentDataType.STRING)) {
            foundValue = container.get(key, PersistentDataType.STRING);
        }

        return foundValue;
    }

    public static void setBank(Player player, long amount) {
        NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "bank");

        player.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.LONG, amount);
    }



    public void setupScoreboard(final Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective objective = scoreboard.registerNewObjective("sbSidebar", "dummy", "sbSidebar");

        objective.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"SKYBLOCK");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        player.setScoreboard(scoreboard);
        objective.getScore(ChatColor.GRAY+"date").setScore(15);
        objective.getScore(" ").setScore(14);
        objective.getScore(ChatColor.WHITE+"  Early Summer 1st").setScore(13);
        objective.getScore(ChatColor.GRAY+"  12:00am").setScore(12);
        objective.getScore(ChatColor.GRAY+"  ♰ area").setScore(11);
        objective.getScore("  ").setScore(10);
        objective.getScore(ChatColor.WHITE+"Purse: 0.0").setScore(9);
        objective.getScore("   ").setScore(8);
        objective.getScore(ChatColor.WHITE+"Objective").setScore(7);
        objective.getScore("     ").setScore(6);
        objective.getScore("      ").setScore(5);
        objective.getScore("       ").setScore(4);
        objective.getScore("        ").setScore(3);
        objective.getScore("         ").setScore(2);
        objective.getScore(ChatColor.YELLOW+"colecrafter.asuscomm.com").setScore(1);
    }

    static void newDay() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();

        Score day = scoreboard.getObjective("day").getScore("Time");
        Score month = scoreboard.getObjective("month").getScore("Time");
        Score year = scoreboard.getObjective("year").getScore("Time");

        day.setScore(day.getScore()+1);

        if (day.getScore() == 32) {
            day.setScore(1);
            month.setScore(month.getScore()+1);
        }

        if (month.getScore() == 13) {
            month.setScore(1);
            year.setScore(year.getScore()+1);
        }

        Bukkit.getWorld("hub").setFullTime( 0);

    }

    public static void updateScoreboard(Player player, String area, String goal) {
        org.bukkit.scoreboard.Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("sbSidebar");

        long purse = getCoins(player);


        String date = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        date = (dtf.format(now));

        for (String entry : scoreboard.getEntries()) {
            for (Score score : scoreboard.getScores(entry)) {
                if (score.getObjective().equals(objective)) {
                    if (score.getScore() == 15) {
                        scoreboard.resetScores(score.getEntry());
                        objective.getScore(ChatColor.GRAY+""+date).setScore(15);
                    }
                    if (score.getScore() == 13) {
                        scoreboard.resetScores(score.getEntry());
                        String month = "";
                        switch (Bukkit.getScoreboardManager().getMainScoreboard().getObjective("month").getScore("Time").getScore()) {
                            case 1:
                                month = "Early Spring";
                                break;
                            case 2:
                                month = "Spring";
                                break;
                            case 3:
                                month = "Late Spring";
                                break;
                            case 4:
                                month = "Early Summer";
                                break;
                            case 5:
                                month = "Summer";
                                break;
                            case 6:
                                month = "Late Summer";
                                break;
                            case 7:
                                month = "Early Autumn";
                                break;
                            case 8:
                                month = "Autumn";
                                break;
                            case 9:
                                month = "Late Autumn";
                                break;
                            case 10:
                                month = "Early Winter";
                                break;
                            case 11:
                                month = "Winter";
                                break;
                            case 12:
                                month = "Late Winter";
                                break;

                        }

                        String day = ""+Bukkit.getScoreboardManager().getMainScoreboard().getObjective("day").getScore("Time").getScore();


                        switch (day.substring(day.length()-1)) {
                            case "1":
                                day = day+"st";
                                break;
                            case "2":
                                day = day+"nd";
                                break;
                            case "3":
                                day = day+"rd";
                                break;
                            default:
                                day = day+"th";
                                break;
                        }

                        objective.getScore(ChatColor.WHITE+"  "+month+" "+day).setScore(13);
                    }
                    if (score.getScore() == 12) {
                        scoreboard.resetScores(score.getEntry());
                        objective.getScore(ChatColor.GRAY+"  "+currentTime).setScore(12);
                    }
                    if (score.getScore() == 11) {
                        scoreboard.resetScores(score.getEntry());
                        objective.getScore(ChatColor.GRAY+"  ♰ "+area).setScore(11);
                    }
                    if (score.getScore() == 9) {
                        scoreboard.resetScores(score.getEntry());
                        NumberFormat numberFormat = NumberFormat.getInstance();

                        numberFormat.setGroupingUsed(true);

                        String formattedPurse = numberFormat.format(purse);
                        objective.getScore(ChatColor.WHITE+"Purse: "+ChatColor.GOLD+formattedPurse+".0").setScore(9);
                    }
                    if (score.getScore() == 6) {
                        scoreboard.resetScores(score.getEntry());
                        objective.getScore(ChatColor.YELLOW+""+goal).setScore(6);
                    }
                }
            }
        }

    }



    public static boolean isEqual(ItemStack item, ItemStack item1) {
        if (item.getType() == item1.getType() && item.getAmount() >= item1.getAmount()) {
            if (item.hasItemMeta() && item1.hasItemMeta()) {
                if (item.getItemMeta().hasDisplayName() && item1.getItemMeta().hasDisplayName()) {
                    return item.getItemMeta().getDisplayName().equals(item1.getItemMeta().getDisplayName());
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
    }

    public static void consumeCraft(Player player, Inventory inv, HashMap<Integer, Integer> amounts, boolean isShiftClick, ItemStack result) {
        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
            @Override
            public void run() {
                if (isShiftClick) {
                    short amount0 = 0;
                    short amount1 = 0;
                    short amount2 = 0;
                    short amount3 = 0;
                    short amount4 = 0;
                    short amount5 = 0;
                    short amount6 = 0;
                    short amount7 = 0;
                    short amount8 = 0;

                    if (inv.getItem(10) != null) {
                        amount0 = (short) inv.getItem(10).getAmount();
                    }
                    if (inv.getItem(11) != null) {
                        amount1 = (short) inv.getItem(11).getAmount();
                    }
                    if (inv.getItem(12) != null) {
                        amount2 = (short) inv.getItem(12).getAmount();
                    }
                    if (inv.getItem(19) != null) {
                        amount3 = (short) inv.getItem(19).getAmount();
                    }
                    if (inv.getItem(20) != null) {
                        amount4 = (short) inv.getItem(20).getAmount();
                    }
                    if (inv.getItem(21) != null) {
                        amount5 = (short) inv.getItem(21).getAmount();
                    }
                    if (inv.getItem(28) != null) {
                        amount6 = (short) inv.getItem(28).getAmount();
                    }
                    if (inv.getItem(29) != null) {
                        amount7 = (short) inv.getItem(29).getAmount();
                    }
                    if (inv.getItem(30) != null) {
                        amount8 = (short) inv.getItem(30).getAmount();
                    }


                    if (amount0 >= amounts.get(10) && amount1 >= amounts.get(11) && amount2 >= amounts.get(12)
                            && amount3  >= amounts.get(19) && amount4 >= amounts.get(20) && amount5 >= amounts.get(21)
                            && amount6 >= amounts.get(28) && amount7 >= amounts.get(29) && amount8 >= amounts.get(30)) {
                        ArrayList<Integer> allIntegers = new ArrayList<>();
                        ArrayList<Integer> allCurrentAmounts = new ArrayList<>();
                        allCurrentAmounts.add((int) amount0);
                        allCurrentAmounts.add((int) amount1);
                        allCurrentAmounts.add((int) amount2);
                        allCurrentAmounts.add((int) amount3);
                        allCurrentAmounts.add((int) amount4);
                        allCurrentAmounts.add((int) amount5);
                        allCurrentAmounts.add((int) amount6);
                        allCurrentAmounts.add((int) amount7);
                        allCurrentAmounts.add((int) amount8);

                        for (Integer entry : allCurrentAmounts) {
                            if (entry != 0) {
                                allIntegers.add(entry);
                            }
                        }
                        int lowestAmount = 64;
                        for (Integer integer : allIntegers) {
                            if (integer < lowestAmount) {
                                lowestAmount = integer;
                            }
                        }



                        for (int i = 10; i < 13; i++) {

                            if (inv.getItem(i) == null) continue;
                            if (inv.getItem(i).getAmount() > amounts.get(i)) {
                                ItemStack item = inv.getItem(i);
                                if (lowestAmount > 0 && lowestAmount < amounts.get(i)) {
                                    item.setAmount(inv.getItem(i).getAmount() - (int) Math.floor(amounts.get(i)));
                                } else {
                                    item.setAmount(inv.getItem(i).getAmount() - (int) Math.floor(lowestAmount / amounts.get(i)) * amounts.get(i));
                                }
                                inv.setItem(i, item);
                            } else {
                                inv.setItem(i, new ItemStack(Material.AIR));
                            }
                        }

                        for (int i = 19; i < 22; i++) {
                            if (inv.getItem(i) == null) continue;
                            if (inv.getItem(i).getAmount() > amounts.get(i)) {
                                ItemStack item = inv.getItem(i);
                                if (lowestAmount > 0 && lowestAmount < amounts.get(i)) {
                                    item.setAmount(inv.getItem(i).getAmount() - (int) Math.floor(amounts.get(i)));
                                } else {
                                    item.setAmount(inv.getItem(i).getAmount() - (int) Math.floor(lowestAmount / amounts.get(i)) * amounts.get(i));
                                }
                                inv.setItem(i, item);
                            } else {
                                inv.setItem(i, new ItemStack(Material.AIR));
                            }
                        }

                        for (int i = 28; i < 31; i++) {
                            if (inv.getItem(i) == null) continue;
                            if (inv.getItem(i).getAmount() > amounts.get(i)) {
                                ItemStack item = inv.getItem(i);
                                if (lowestAmount > 0 && lowestAmount < amounts.get(i)) {
                                    item.setAmount(inv.getItem(i).getAmount() - (int) Math.floor(amounts.get(i)));
                                } else {
                                    item.setAmount(inv.getItem(i).getAmount() - (int) Math.floor(lowestAmount / amounts.get(i)) * amounts.get(i));
                                }
                                inv.setItem(i, item);
                            } else {
                                inv.setItem(i, new ItemStack(Material.AIR));
                            }
                        }

                        ArrayList<Integer> allIntegers1 = new ArrayList<>();

                        allIntegers.clear();
                        for (Map.Entry<Integer, Integer> entry : amounts.entrySet()) {
                            if (entry.getValue() != 0) allIntegers.add(entry.getValue());
                        }

                        for (Integer entry : allCurrentAmounts) {
                            if (entry != 0) {
                                allIntegers1.add(entry);
                            }
                        }


                        lowestAmount = 64;
                        for (int i = 0; i < allIntegers.size(); i++) {
                            if (Math.floor(allIntegers1.get(i)/allIntegers.get(i)) < lowestAmount) {
                                lowestAmount = (int) Math.floor(allIntegers1.get(i)/allIntegers.get(i));
                            }
                        }

                        for (byte i = 0; i < lowestAmount-1; i++) {
                            player.getInventory().addItem(result);
                        }


                    }
                } else {

                    for (int i = 10; i < 13; i++) {
                        if (inv.getItem(i) == null) continue;
                        if (inv.getItem(i).getAmount() > amounts.get(i)) {
                            inv.getItem(i).setAmount(inv.getItem(i).getAmount() - amounts.get(i));
                        } else {
                            inv.setItem(i, new ItemStack(Material.AIR));
                        }

                    }

                    for (int i = 19; i < 22; i++) {
                        if (inv.getItem(i) == null) continue;
                        if (inv.getItem(i).getAmount() > amounts.get(i)) {
                            inv.getItem(i).setAmount(inv.getItem(i).getAmount() - amounts.get(i));
                        } else {
                            inv.setItem(i, new ItemStack(Material.AIR));
                        }
                    }

                    for (int i = 28; i < 31; i++) {
                        if (inv.getItem(i) == null) continue;
                        if (inv.getItem(i).getAmount() > amounts.get(i)) {
                            inv.getItem(i).setAmount(inv.getItem(i).getAmount() - amounts.get(i));
                        } else {
                            inv.setItem(i, new ItemStack(Material.AIR));
                        }
                    }
                }

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 2);
            }
        }, 1);
    }


    private static ColeCrafterSlayers instance;
    public static ColeCrafterSlayers getInstance() {
        return instance;
    }































    public void onEnable() {
        instance = this;

        effectManager = new EffectManager(instance);



        if (!f.exists()) {
            saveResource("mobspawns.yml", false);
        }


        getServer().getPluginManager().registerEvents(new SlayerMenu(), this);
        getServer().getPluginManager().registerEvents(new DefenseNerf(), this);
        getServer().getPluginManager().registerEvents(new CustomCrafting(), this);
        getServer().getPluginManager().registerEvents(new CustomAnvil(), this);
        getServer().getPluginManager().registerEvents(new CustomNPC(), this);

        this.getCommand("spawnnpc").setExecutor(new MaddoxCommand());
        this.getCommand("item").setExecutor(new CustomItemCommand());
        this.getCommand("craft").setExecutor(new CustomCrafting());
        this.getCommand("anvil").setExecutor(new CustomAnvil());
        this.getCommand("resethealth").setExecutor(new ResetHealth());
        this.getCommand("mob").setExecutor(new CustomMobCommand());
        this.getCommand("build").setExecutor(new sendMessageToSelf());
        this.getCommand("viewRecipe").setExecutor(new RecipeCommand());
        this.getCommand("stats").setExecutor(new StatsCommand());
        this.getCommand("calendar").setExecutor(new CalenderCommand());
        this.getCommand("sell").setExecutor(new SellCommand());
        this.getCommand("mobspawn").setExecutor(new MobSpawnCommand());
        this.getCommand("updateitem").setExecutor(new UpdateItemCommand());

        CustomItem.loadRecipes();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();



        if (scoreboard.getObjective("day") == null) scoreboard.registerNewObjective("day", "dummy", "Day");
        if (scoreboard.getObjective("month") == null) scoreboard.registerNewObjective("month", "dummy", "Month");
        if (scoreboard.getObjective("year") == null) scoreboard.registerNewObjective("year", "dummy", "Year");


        if (scoreboard.getObjective("Health") == null) scoreboard.registerNewObjective("Health", "health", ChatColor.RED+"❤");

        Objective health = scoreboard.getObjective("Health");
        health.setDisplaySlot(DisplaySlot.BELOW_NAME);




        if (scoreboard.getObjective("day").getScore("Time").getScore() == 0) scoreboard.getObjective("day").getScore("Time").setScore(1);
        if (scoreboard.getObjective("month").getScore("Time").getScore() == 0) scoreboard.getObjective("month").getScore("Time").setScore(1);
        if (scoreboard.getObjective("year").getScore("Time").getScore() == 0) scoreboard.getObjective("year").getScore("Time").setScore(1);

        ArrayList<Player> farmingAura = new ArrayList<>();


        scheduleSyncLoop(new Runnable() {
            @Override
            public void run() {





                long time = Bukkit.getWorld("hub").getTime();

                String finalTime;

                double minutesPassedSince6am = (time/1000f)*60;

                byte hours = (byte) Math.floor(minutesPassedSince6am/60);
                byte minutes = (byte) Math.floor(minutesPassedSince6am%60);



                boolean isPM = (hours >= 6 && hours <= 17);

                byte finalHours = (byte) (hours > 6 ? (hours > 18 ? hours-18 : hours - 6) : hours+6);

                minutes = (byte) ((Math.floor(minutes/10))*10);




                finalTime = (finalHours)+":"+(minutes == 0 ? "00" : minutes )+(isPM ? "pm" : "am");

                currentTime = finalTime;






                String area = ChatColor.GRAY+"Nowhere";

                for (Player player : Bukkit.getOnlinePlayers()) {






                    double X = player.getLocation().getX();
                    double Y = player.getLocation().getY();
                    double Z = player.getLocation().getZ();

                    if (player.getLocation().getWorld().getName().equals("hub")) {
                        if ((X > -74 && Y > 40 && Z > -232) && (X < 8 && Y < 91 && Z < -146)) {
                            area = ChatColor.AQUA + "Coal Mine";
                        } else if ((X > 9 && Y > 63 && Z > -251) && (X < 90 && Y < 114 && Z < -124)) {
                            area = ChatColor.AQUA + "Farm";
                        } else if ((X > -236 && Y > 63 && Z > -83) && (X < -95 && Y < 120 && Z < 14)) {
                            area = ChatColor.AQUA + "Forest";
                        } else if ((X > -300 && Y > 37 && Z > -1) && (X < -105 && Y < 174 && Z < 162)) {
                            area = ChatColor.AQUA + "Ruins";
                        } else if ((X > -95 && Y > 43 && Z > -150) && (X < 65 && Y < 122 && Z < 2)) {
                            area = ChatColor.AQUA + "Village";
                        } else if ((X > 54 && Y > 57 && Z > 7) && (X < 185 && Y < 199 && Z < 209)) {
                            area = ChatColor.DARK_GREEN + "Wilderness";
                        } else if ((X > -91 && Y > 46 && Z > -21) && (X < 62 && Y < 255 && Z < 99)) {
                            area = ChatColor.AQUA + "Mountain";
                        } else if ((X > -130 && Y > 39 && Z > 68) && (X < 63 && Y < 160 && Z < 234)) {
                            area = ChatColor.RED + "High Level";
                        } else if ((X > -180 && Y > 67 && Z > -233) && (X < -48 && Y < 125 && Z < -77)) {
                            area = ChatColor.RED + "Graveyard";
                        } else if ((X > 80 && Y > 1 && Z > -337) && (X < 218 && Y < 155 && Z < -174)) {
                            area = ChatColor.AQUA + "The Barn";
                        } else if ((X > -96 && Y > 10 && Z > -403) && (X < 45 && Y < 179 && Z < -260)) {
                            area = ChatColor.GOLD + "Gold Mine";
                        } else if ((X > -94 && Y > 0 && Z > -648) && (X < 93 && Y < 267 && Z < -473)) {
                            area = ChatColor.AQUA + "Deep Caverns";
                        } else if ((X > 109 && Y > 0 && Z > -480) && (X < 272 && Y < 150 && Z < -332)) {
                            area = ChatColor.AQUA + "Mushroom Desert";
                        } else if ((X > -451 && Y > 10 && Z > -337) && (X < -92 && Y < 250 && Z < -166)) {
                            area = ChatColor.RED + "Spiders Den";
                        } else if ((X > -547 && Y > 5 && Z > -698) && (X < -195 && Y < 250 && Z < -379)) {
                            area = ChatColor.RED + "Blazing Fortress";
                        }


                        Location slime = player.getLocation();
                        slime.setY(slime.getY() - 1);

                        Location slime1 = player.getLocation();
                        slime1.setY(slime1.getY() - 2);

                        if (Bukkit.getWorld("hub").getBlockAt(slime).getType() == Material.SLIME_BLOCK || Bukkit.getWorld("hub").getBlockAt(slime1).getType() == Material.SLIME_BLOCK) {
                            HashMap<Location, Integer> locations = new HashMap<>();
                            locations.put(new Location(Bukkit.getWorld("hub"), -11, 64, -232), 0);
                            locations.put(new Location(Bukkit.getWorld("hub"), -6, 74, -270), 1);
                            locations.put(new Location(Bukkit.getWorld("hub"), -8, 67, -397), 2);
                            locations.put(new Location(Bukkit.getWorld("hub"), -4, 156, -486), 3);
                            locations.put(new Location(Bukkit.getWorld("hub"), 79, 72, -185), 4);
                            locations.put(new Location(Bukkit.getWorld("hub"), 111, 70, -203), 5);
                            locations.put(new Location(Bukkit.getWorld("hub"), 142, 92, -311), 6);
                            locations.put(new Location(Bukkit.getWorld("hub"), 150, 76, -359), 7);

                            locations.put(new Location(Bukkit.getWorld("hub"), -163, 73, -162), 8);
                            locations.put(new Location(Bukkit.getWorld("hub"), -198, 83, -229), 9);
                            locations.put(new Location(Bukkit.getWorld("hub"), -254, 132, -295), 10);
                            locations.put(new Location(Bukkit.getWorld("hub"), -310, 83, -377), 11);

                            // Teleport from graveyard to spider den: -163, 73, -162    -201.5, 84, -232.5, 135, 0

                            // From spiderden to graveyard: -198, 83, -229            -160.5, 73, -159.5, -45, 0

                            // From spider to blaze fort -254, 132, -295            -310.0, 83, -380.5, 180, 0

                            // From blaze to spider  -310, 83, -377        -254.0, 132, -290.5, 0, 0

                            for (Map.Entry<Location, Integer> requiredToTeleport : locations.entrySet()) {
                                long distanceSquared = (long) player.getLocation().distanceSquared(requiredToTeleport.getKey());


                                if (distanceSquared <= 25) {
                                    Location teleport = null;
                                    if (requiredToTeleport.getValue() == 0) {
                                        teleport = new Location(Bukkit.getWorld("hub"), -4.5f, 74f, -272.5f, 180, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 1) {
                                        teleport = new Location(Bukkit.getWorld("hub"), -9.5f, 64f, -227.5f, 0, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 2) {
                                        teleport = new Location(Bukkit.getWorld("hub"), -3, 157, -488.5, 180, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 3) {
                                        teleport = new Location(Bukkit.getWorld("hub"), -7, 68, -392.5, 0, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 4) {
                                        teleport = new Location(Bukkit.getWorld("hub"), 115.5, 71, -206.5, -135, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 5) {
                                        teleport = new Location(Bukkit.getWorld("hub"), 75.5, 72, -180.5, 45, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 6) {
                                        teleport = new Location(Bukkit.getWorld("hub"), 153.5, 77, -361.5, -135, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 7) {
                                        teleport = new Location(Bukkit.getWorld("hub"), 142.5, 91, -304.5, 0, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 8) {
                                        teleport = new Location(Bukkit.getWorld("hub"), -201.5, 84, -232.5, 135, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 9) {
                                        teleport = new Location(Bukkit.getWorld("hub"), -160.5, 73, -159.5, -45, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 10) {
                                        teleport = new Location(Bukkit.getWorld("hub"), -310.0, 83, -380.5, 180, 0);
                                    }
                                    if (requiredToTeleport.getValue() == 11) {
                                        teleport = new Location(Bukkit.getWorld("hub"), -254.0, 132, -290.5, 0, 0);
                                    }

                                    if (teleport != null) {
                                        player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 100, 0.3f, 0.6f, 0.3f);
                                        player.teleport(teleport);
                                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2f, 1.5f);
                                        break;
                                    }
                                }

                            }

                        }


                        currentLocation.put(player, area);

                        if (DefenseNerf.isFullSet(player, "LAPIS_HELMET", "LAPIS_CHESTPLATE", "LAPIS_LEGGINGS", "LAPIS_BOOTS")) {
                            UUID uuid = UUID.fromString("1ce3bf52-7c7e-474f-a000-430317f6838e");


                            boolean hasAttribute = false;
                            for (AttributeModifier attribute : player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
                                if (attribute.getName().equals("lapisArmorSetBonus")) {
                                    hasAttribute = true;
                                }

                            }
                            if (!hasAttribute) {
                                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(new AttributeModifier(uuid, "lapisArmorSetBonus", 6, AttributeModifier.Operation.ADD_NUMBER));
                            }


                        } else {
                            for (AttributeModifier attribute : player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
                                if (attribute.getName().equals("lapisArmorSetBonus")) {
                                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(attribute);

                                }

                            }
                        }

                        if (DefenseNerf.isFullSet(player, "SPEEDSTER_HELMET", "SPEEDSTER_CHESTPLATE", "SPEEDSTER_LEGGINGS", "SPEEDSTER_BOOTS")) {
                            UUID uuid = UUID.fromString("90a00ae3-c031-4e19-ad90-b69983874bb0");


                            boolean hasAttribute = false;
                            for (AttributeModifier attribute : player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
                                if (attribute.getName().equals("speedsterArmorSetBonus")) {
                                    hasAttribute = true;
                                }

                            }
                            if (!hasAttribute) {
                                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier(uuid, "speedsterArmorSetBonus", 0.2, AttributeModifier.Operation.ADD_SCALAR));
                            }


                        } else {
                            for (AttributeModifier attribute : player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
                                if (attribute.getName().equals("speedsterArmorSetBonus")) {
                                    player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(attribute);

                                }

                            }
                        }

                        if (DefenseNerf.isFullSet(player, "FARM_SUIT_HELMET", "FARM_SUIT_CHESTPLATE", "FARM_SUIT_LEGGINGS", "FARM_SUIT_BOOTS") && (currentLocation.get(player).equals(ChatColor.AQUA + "Farm") || currentLocation.get(player).equals(ChatColor.AQUA + "The Barn") || currentLocation.get(player).equals(ChatColor.AQUA + "Mushroom Desert"))) {
                            UUID uuid = UUID.fromString("34edb9a1-7925-4ec9-8348-d3eced701f16");


                            boolean hasAttribute = false;
                            for (AttributeModifier attribute : player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
                                if (attribute.getName().equals("farmSuitSetBonus")) {
                                    hasAttribute = true;
                                }

                            }
                            if (!hasAttribute) {
                                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier(uuid, "farmSuitSetBonus", 0.2, AttributeModifier.Operation.ADD_SCALAR));
                            }


                        } else {
                            for (AttributeModifier attribute : player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
                                if (attribute.getName().equals("farmSuitSetBonus")) {
                                    player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(attribute);

                                }

                            }
                        }

                        int removePlayerIndex = -1;
                        if (farmingAura.size() >= 1) {
                            for (int i = 0; i < farmingAura.size(); i++) {
                                if (farmingAura.get(i).equals(player)) {
                                    removePlayerIndex = i;
                                }
                            }
                        }

                        if (removePlayerIndex > -1) {
                            farmingAura.remove(removePlayerIndex);
                        }


                        if (DefenseNerf.isFullSet(player, "AGRICULTURE_HELMET", "AGRICULTURE_CHESTPLATE", "AGRICULTURE_LEGGINGS", "AGRICULTURE_BOOTS") && (currentLocation.get(player).equals(ChatColor.AQUA + "Farm") || currentLocation.get(player).equals(ChatColor.AQUA + "The Barn") || currentLocation.get(player).equals(ChatColor.AQUA + "Mushroom Desert"))) {
                            UUID uuid = UUID.fromString("3a53154a-6307-46e0-bc89-15f9651f2934");


                            boolean hasAttribute = false;
                            for (AttributeModifier attribute : player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
                                if (attribute.getName().equals("agricultureSetBonus")) {
                                    hasAttribute = true;
                                }

                            }
                            if (!hasAttribute) {
                                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier(uuid, "agricultureSetBonus", 0.5, AttributeModifier.Operation.ADD_SCALAR));
                            }

                            player.getLocation().getWorld().spawnParticle(Particle.SNOW_SHOVEL, player.getLocation(), 1000, 3, 3, 3);


                            for (Entity entity : player.getNearbyEntities(24, 24, 24)) {
                                if (entity instanceof Player) {
                                    Player p = (Player) entity;
                                    boolean hasAttribute1 = false;

                                    UUID uuid1 = UUID.fromString("98ebb103-cfc6-4d3f-a5a4-e2f1fe39ef0c");


                                    for (AttributeModifier attribute : p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
                                        if (attribute.getName().equals("agricultureAura")) {
                                            hasAttribute1 = true;
                                        }

                                    }
                                    if (!hasAttribute1) {
                                        farmingAura.add(p);
                                        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier(uuid1, "agricultureAura", 0.2, AttributeModifier.Operation.ADD_SCALAR));
                                    }
                                }
                            }


                        } else {
                            for (AttributeModifier attribute : player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
                                if (attribute.getName().equals("agricultureSetBonus")) {
                                    player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(attribute);

                                }

                            }
                        }

                        boolean removeFarmingAuraBoost = true;
                        for (Player p : farmingAura) {
                            if (p.equals(player)) {
                                removeFarmingAuraBoost = false;
                            }
                        }

                        if (removeFarmingAuraBoost) {
                            for (AttributeModifier attribute : player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
                                if (attribute.getName().equals("agricultureAura")) {
                                    player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(attribute);

                                }

                            }
                        }


                        byte breadAccessory = 0;


                        for (ItemStack item : player.getInventory().getContents()) {
                            if (item == null) continue;
                            if (!item.hasItemMeta()) continue;
                            ItemMeta finalMeta = item.getItemMeta();

                            if (finalMeta == null) continue;

                            String foundValue = DefenseNerf.getItemID(item);


                            if (foundValue == null) continue;
                            if (foundValue.equals("FRENCH_BREAD") || foundValue.equals("LE_BAGUETTE") || foundValue.equals("FRENCH_SANDWICH")) {


                                if (foundValue.equals("FRENCH_SANDWICH")) {
                                    breadAccessory = 3;
                                }
                                if (foundValue.equals("LE_BAGUETTE")) {
                                    if (breadAccessory < 2)
                                        breadAccessory = 2;
                                }

                                if (foundValue.equals("FRENCH_BREAD")) {
                                    if (breadAccessory < 1)
                                        breadAccessory = 1;

                                }


                            }

                            if (foundValue.equals("MINER_HELMET") || foundValue.equals("MINER_CHESTPLATE") || foundValue.equals("MINER_LEGGINGS") || foundValue.equals("MINER_BOOTS")) {


                                if (currentLocation.get(player).equals(ChatColor.GOLD + "Gold Mine") || currentLocation.get(player).equals(ChatColor.AQUA + "Deep Caverns")) {
                                    UUID uuid = UUID.randomUUID();


                                    boolean hasAttribute = false;
                                    for (AttributeModifier attribute : finalMeta.getAttributeModifiers(Attribute.GENERIC_ARMOR)) {
                                        if (attribute.getName().equals("minerArmorDefense")) {
                                            hasAttribute = true;
                                        }

                                    }
                                    if (!hasAttribute) {

                                        switch (foundValue) {
                                            case "MINER_CHESTPLATE":
                                                finalMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(uuid, "minerArmorDefense", 19, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                                                break;
                                            case "MINER_HELMET":
                                                finalMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(uuid, "minerArmorDefense", 12, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                                                break;
                                            case "MINER_BOOTS":
                                                finalMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(uuid, "minerArmorDefense", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
                                                break;
                                            case "MINER_LEGGINGS":
                                                finalMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(uuid, "minerArmorDefense", 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
                                                break;
                                        }
                                    }
                                } else {
                                    for (AttributeModifier attribute : finalMeta.getAttributeModifiers(Attribute.GENERIC_ARMOR)) {
                                        if (attribute.getName().equals("minerArmorDefense")) {
                                            finalMeta.removeAttributeModifier(Attribute.GENERIC_ARMOR, attribute);

                                        }

                                    }
                                }
                            }
                            item.setItemMeta(finalMeta);

                            if (foundValue.equals("MAGMA_HELMET") || foundValue.equals("MAGMA_CHESTPLATE") || foundValue.equals("MAGMA_LEGGINGS") || foundValue.equals("MAGMA_BOOTS")) {


                                if (currentLocation.get(player).equals(ChatColor.RED + "Blazing Fortress")) {
                                    UUID uuid = UUID.randomUUID();


                                    boolean hasAttribute = false;
                                    for (AttributeModifier attribute : finalMeta.getAttributeModifiers(Attribute.GENERIC_MAX_HEALTH)) {
                                        if (attribute.getName().equals("magmaArmorHealth")) {
                                            hasAttribute = true;
                                        }

                                    }
                                    if (!hasAttribute) {

                                        switch (foundValue) {
                                            case "MAGMA_CHESTPLATE":
                                                finalMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(uuid, "magmaArmorHealth", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                                                break;
                                            case "MAGMA_HELMET":
                                                finalMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(uuid, "magmaArmorHealth", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                                                break;
                                            case "MAGMA_BOOTS":
                                                finalMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(uuid, "magmaArmorHealth", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
                                                break;
                                            case "MAGMA_LEGGINGS":
                                                finalMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(uuid, "magmaArmorHealth", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
                                                break;
                                        }
                                    }
                                } else {
                                    for (AttributeModifier attribute : finalMeta.getAttributeModifiers(Attribute.GENERIC_MAX_HEALTH)) {
                                        if (attribute.getName().equals("magmaArmorHealth")) {
                                            finalMeta.removeAttributeModifier(Attribute.GENERIC_MAX_HEALTH, attribute);

                                        }

                                    }
                                }
                            }
                            item.setItemMeta(finalMeta);


                        }



                        if (breadAccessory > 0) {
                            boolean canApply = true;
                            for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
                                if (attribute.getName().equals("breadAccessory")) {
                                    canApply = false;

                                }
                            }

                            if (canApply) {
                                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(new AttributeModifier(UUID.randomUUID(), "breadAccessory", 0.02 + (breadAccessory / 100f), AttributeModifier.Operation.ADD_SCALAR));
                                player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(new AttributeModifier(UUID.randomUUID(), "breadAccessory", 0.02 + (breadAccessory / 100f), AttributeModifier.Operation.ADD_SCALAR));
                                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier(UUID.randomUUID(), "breadAccessory", 0.02 + (breadAccessory / 100f), AttributeModifier.Operation.ADD_SCALAR));
                                player.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier(UUID.randomUUID(), "breadAccessory", 0.02 + (breadAccessory / 100f), AttributeModifier.Operation.ADD_SCALAR));
                                player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(new AttributeModifier(UUID.randomUUID(), "breadAccessory", 0.02 + (breadAccessory / 100f), AttributeModifier.Operation.ADD_SCALAR));
                                player.getAttribute(Attribute.GENERIC_LUCK).addModifier(new AttributeModifier(UUID.randomUUID(), "breadAccessory", 2 + breadAccessory, AttributeModifier.Operation.ADD_NUMBER));
                            }
                        } else {
                            for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
                                if (attribute.getName().equals("breadAccessory")) {
                                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(attribute);

                                }
                            }

                            for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers()) {
                                if (attribute.getName().equals("breadAccessory")) {
                                    player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(attribute);

                                }

                            }


                            for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
                                if (attribute.getName().equals("breadAccessory")) {
                                    player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(attribute);

                                }

                            }


                            for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
                                if (attribute.getName().equals("breadAccessory")) {
                                    player.getAttribute(Attribute.GENERIC_ARMOR).removeModifier(attribute);

                                }

                            }

                            for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getModifiers()) {
                                if (attribute.getName().equals("breadAccessory")) {
                                    player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).removeModifier(attribute);

                                }

                            }

                            for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_LUCK).getModifiers()) {
                                if (attribute.getName().equals("breadAccessory")) {
                                    player.getAttribute(Attribute.GENERIC_LUCK).removeModifier(attribute);

                                }

                            }
                        }

                    }
                }
            }
        }, 1, 30);


        scheduleSyncLoop(new Runnable() {
            @Override
            public void run() {
                boolean isFireTwirl = false;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
                        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                        PersistentDataContainer container = entity.getPersistentDataContainer();


                        String foundValue = null;
                        if (container.has(key, PersistentDataType.STRING)) {
                            foundValue = container.get(key, PersistentDataType.STRING);
                        }

                        if (foundValue != null) {
                            if (foundValue.equals("MAGMA_CUBE_BOSS")) {

                                isFireTwirl = true;


                                Location origin = entity.getLocation();
                                origin.setY(entity.getLocation().getY() + 1);

                                ArmorStand armorStand = origin.getWorld().spawn(origin, ArmorStand.class);

                                armorStand.setInvulnerable(true);
                                armorStand.setMarker(true);


                                ArmorStand armorStand1 = origin.getWorld().spawn(origin, ArmorStand.class);

                                armorStand1.setInvulnerable(true);
                                armorStand1.setMarker(true);

                                armorStand1.setRotation(180, 0);


                                for (int i = 0; i <= 360; i+=2) {
                                    int finalI = i;
                                    if (i == 360) {
                                        armorStand.remove();
                                        armorStand1.remove();
                                    }
                                    scheduleSyncDelayedTask(new Runnable() {
                                        @Override
                                        public void run() {
                                            armorStand.setRotation(finalI, 0);
                                            SmallFireball fireball = entity.getWorld().spawn(magmaBossLocation, SmallFireball.class);
                                            fireball.setIsIncendiary(false);
                                            fireball.setDirection(armorStand.getLocation().getDirection());

                                            armorStand1.setRotation(finalI+180, 0);
                                            SmallFireball fireball1 = entity.getWorld().spawn(magmaBossLocation, SmallFireball.class);
                                            fireball1.setIsIncendiary(false);

                                            fireball1.setDirection(armorStand1.getLocation().getDirection());
                                        }
                                    }, i);

                                }


                                break;
                            }
                        }
                    }
                    if (isFireTwirl) break;
                }
            }
        }, 1, 800);

        scheduleSyncLoop(new Runnable() {
            @Override
            public void run() {


                FileConfiguration Config = getMobSpawnConfig();


                HashMap<String, Integer> amountSpawned = new HashMap<>();


                for (String key : Config.getKeys(false)) {
                    int j = 0;
                    for (String key1 : Config.getConfigurationSection(key).getKeys(false)) {

                        j++;

                    }

                    amountSpawned.put(key, j);
                }

                for (String key : Config.getKeys(false)) {
                    for (String key1 : Config.getConfigurationSection(key).getKeys(false)) {

                        String coords = (String) Config.getConfigurationSection(key).get(key1);

                        coords = coords.replace("[", "").replace("]", "");
                        String[] coords1 = coords.split(", ");

                        Location loc = new Location(Bukkit.getWorld("hub"), Double.parseDouble(coords1[0]), Double.parseDouble(coords1[1]), Double.parseDouble(coords1[2]));


                        int i = 0;
                        boolean thereIsPlayer = false;
                        for (Entity entity : Bukkit.getWorld("hub").getEntities()) {
                            if (entity instanceof LivingEntity) {
                                if (entity.getLocation().distanceSquared(loc) < 100000) {
                                    NamespacedKey key2 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                                    PersistentDataContainer container = entity.getPersistentDataContainer();

                                    String foundValue = null;
                                    if (container.has(key2, PersistentDataType.STRING)) {
                                        foundValue = container.get(key2, PersistentDataType.STRING);
                                    }

                                    if (foundValue != null) {
                                        if (foundValue.equals(key)) {
                                            i++;
                                        }
                                    }
                                    if (entity instanceof Player) {
                                        thereIsPlayer = true;
                                    }
                                }
                            }
                        }


                        if (i < amountSpawned.get(key) && thereIsPlayer) {
                            CustomMob.spawnMob(key.toUpperCase(), loc);
                        }
                    }
                }


            }
        }, 1, 400);

        scheduleSyncLoop(new Runnable() {
            @Override
            public void run() {
                if (Bukkit.getWorld("hub").getTime() == 0) {
                    newDay();
                }




                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
                        if (entity.getType() == EntityType.ARROW) {

                            if (((Arrow)entity).getShooter() instanceof Player) {
                                boolean aiming = false;
                                Player p = (Player) ((Arrow) entity).getShooter();
                                Arrow arrow = (Arrow) entity;

                                for (ItemStack item : ((Player) p).getInventory().getContents()) {
                                    if (item == null) continue;
                                    if (!item.hasItemMeta()) continue;
                                    NamespacedKey key1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemID");
                                    ItemMeta itemMeta = item.getItemMeta();
                                    if (itemMeta == null) continue;
                                    PersistentDataContainer container = itemMeta.getPersistentDataContainer();


                                    String foundValue = null;
                                    if (container.has(key1, PersistentDataType.STRING)) {
                                        foundValue = container.get(key1, PersistentDataType.STRING);
                                    }

                                    if (foundValue != null) {
                                        if (foundValue.equals("AIMING_ARTIFACT")) {
                                            aiming = true;
                                        }

                                    }

                                }
                                if (aiming) {

                                    double speed = arrow.getVelocity().length();

                                    LivingEntity target = null;
                                    if (aimingArrowTarget.get(arrow) == null) {
                                        HashMap<LivingEntity, Double> distances = new HashMap<>();
                                        double largestDistance = 100;

                                        for (Entity en : arrow.getNearbyEntities(20, 20, 20)) {
                                            if (!en.equals(p)) {
                                                if (en instanceof LivingEntity) {
                                                    if (en instanceof ArmorStand) {
                                                        if (!((ArmorStand)en).isMarker() && ((ArmorStand) en).hasLineOfSight(arrow)) {
                                                            double distance = arrow.getLocation().distance(en.getLocation());
                                                            distances.put((LivingEntity) en, distance);
                                                        }
                                                    } else {
                                                        if (!(en instanceof Player)) {
                                                            if (((LivingEntity)en).hasLineOfSight(arrow)) {
                                                                double distance = arrow.getLocation().distance(en.getLocation());
                                                                distances.put((LivingEntity) en, distance);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        for (Map.Entry<LivingEntity, Double> doub : distances.entrySet()) {
                                            if (doub.getValue() < largestDistance) {
                                                largestDistance = doub.getValue();
                                                target = doub.getKey();
                                            }
                                        }
                                        aimingArrowTarget.put(arrow, target);


                                    }

                                    target = aimingArrowTarget.get(arrow);

                                    if (target != null) {
                                        aimingArrowTarget.put(arrow, target);

                                        Vector newVelocity;


                                        double MaxRotationAngle = 0.3D*(1+speed/2);
                                        // From HomingArrows plugin
                                        Vector toTarget = target.getLocation().clone().add(new Vector(0.0D, 0.5D, 0.0D)).subtract(arrow.getLocation()).toVector();
                                        Vector dirVelocity = arrow.getVelocity().clone().normalize();
                                        Vector dirToTarget = toTarget.clone().normalize();
                                        double angle = dirVelocity.angle(dirToTarget);
                                        double newSpeed = 0.9D * speed + 0.14D;
                                        if (angle < 0.12D) {
                                            newVelocity = dirVelocity.clone().multiply(newSpeed);
                                        } else {
                                            Vector newDir = dirVelocity.clone().multiply((angle - (MaxRotationAngle)) / angle).add(dirToTarget.clone().multiply(MaxRotationAngle / angle));
                                            newDir.normalize();
                                            newVelocity = newDir.clone().multiply(newSpeed);
                                        }
                                        arrow.setVelocity(newVelocity);
                                    }
                                }




                            }

                            if (((Arrow)entity).isOnGround()) {
                                ((Arrow)entity).remove();
                            }

                            if (entity.getCustomName() != null) {
                                switch (entity.getCustomName()) {

                                    case "RUNAAN":

                                        Particle.DustOptions dust = new Particle.DustOptions(
                                                Color.fromRGB((int) 47, (int) 245, (int) 225), 1);
                                        entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 2, 0, 0, 0, 0, dust, true);

                                        Random rand = new Random();
                                        int n = rand.nextInt(50);
                                        if (n == 0) {
                                            boolean isIngnited = entity.getFireTicks() > 0;
                                            Arrow arrow1 = entity.getWorld().spawn(entity.getLocation(), Arrow.class);



                                            arrow1.setFireTicks(isIngnited ? 10000 : 0);
                                            arrow1.setShooter(((Arrow) entity).getShooter());
                                            arrow1.setCustomName("RUNAAN");

                                            Arrow finalArrow = (Arrow) entity;
                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Vector finalVelocity = DefenseNerf.rotateYAxis(finalArrow.getVelocity(), 9);
                                                    finalVelocity.setY(finalArrow.getVelocity().getY());

                                                    arrow1.setVelocity(finalVelocity);

                                                    Particle.DustOptions dust = new Particle.DustOptions(
                                                            Color.fromRGB((int) 0, (int) 30, (int) 30), 1);
                                                    entity.getWorld().spawnParticle(Particle.REDSTONE, arrow1.getLocation(), 50, 0.2, 0.2, 0.2, 0, dust, true);

                                                    dust = new Particle.DustOptions(
                                                            Color.fromRGB((int) 255, (int) 255, (int) 255), 1);
                                                    entity.getWorld().spawnParticle(Particle.REDSTONE, arrow1.getLocation(), 50, 0.2, 0.2, 0.2, 0, dust, true);

                                                }
                                            }, 1);
                                        }
                                        break;
                                    case "HURRICANE":
                                        dust = new Particle.DustOptions(
                                                Color.fromRGB((int) 47, (int) 116, (int) 245), 1);
                                        entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 2, 0, 0, 0, 0, dust, true);
                                        break;
                                    case "MOSQUITO":
                                        entity.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, entity.getLocation(), 2, 0, 0, 0, 0);

                                        Location loc = entity.getLocation();
                                        loc.setY(loc.getY()-0.2);
                                        entity.getWorld().spawnParticle(Particle.DRIP_LAVA, loc, 2, 0, 0, 0, 0);
                                        break;
                                    case "SCORPION":
                                    case "SCORPION_1":
                                        dust = new Particle.DustOptions(
                                                Color.fromRGB((int) 128, (int) 23, (int) 18), 1);
                                        entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 2, 0, 0, 0, 0, dust, true);

                                        loc = entity.getLocation();
                                        loc.setY(loc.getY()-0.2);
                                        entity.getWorld().spawnParticle(Particle.DRIP_LAVA, loc, 2, 0, 0, 0, 0);
                                        break;
                                    case "MOSQUITO_1":
                                        entity.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, entity.getLocation(), 4, 0, 0, 0, 0);

                                        Location loc1 = entity.getLocation();
                                        loc1.setY(loc1.getY()-0.2);
                                        entity.getWorld().spawnParticle(Particle.DRIP_LAVA, loc1, 4, 0, 0, 0, 0);
                                        break;

                                }
                            }
                        }
                    }

                }
            }
        }, 1, 2);

        scheduleSyncLoop(new Runnable() {
            @Override
            public void run() {



















                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (Entity entity : player.getNearbyEntities(100, 100, 100)) {


                        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                        PersistentDataContainer container = entity.getPersistentDataContainer();


                        String foundValue = null;
                        if (container.has(key, PersistentDataType.STRING)) {
                            foundValue = container.get(key, PersistentDataType.STRING);
                        }

                        if (foundValue != null) {
                            if (foundValue.equals("WOLF") || foundValue.equals("OLD_WOLF")) {
                                if (entity.getLocation().distanceSquared(player.getLocation()) <= 400) {
                                    ((Wolf)entity).setAngry(true);
                                    ((Wolf)entity).setTarget(player);
                                }
                            }
                            if (foundValue.equals("MAGMA_CUBE_BOSS")) {


                                Location origin = entity.getLocation();
                                origin.setY(entity.getLocation().getY() + 1);
                                magmaBossLocation = entity.getLocation();
                                origin.setY(entity.getLocation().getY() + 20);

                                Location target1 = player.getLocation();
                                target1.setY(player.getLocation().getY() + 1);
                                Vector target = target1.toVector();
                                origin.setDirection((target.subtract(origin.toVector())));

                                Random rand = new Random();
                                int n = rand.nextInt(10);

                                if (n < 4) {
                                    LargeFireball fireball = entity.getWorld().spawn(origin, LargeFireball.class);
                                    fireball.setIsIncendiary(false);
                                    fireball.setDirection(origin.getDirection());
                                } else if (n > 4) {
                                    SmallFireball fireball = entity.getWorld().spawn(origin, SmallFireball.class);
                                    fireball.setIsIncendiary(false);
                                    fireball.setDirection(origin.getDirection());
                                } else if (n == 4) {
                                    LargeFireball fireball = entity.getWorld().spawn(origin, LargeFireball.class);
                                    fireball.setDisplayItem(new ItemStack(Material.BLAZE_POWDER));
                                    fireball.setYield(2);
                                    fireball.setIsIncendiary(false);
                                    fireball.setDirection(origin.getDirection());
                                }


                                break;
                            }
                        }
                    }


                    if (DefenseNerf.isFullSet(player, "BLAZE_HELMET", "BLAZE_CHESTPLATE", "BLAZE_LEGGINGS", "BLAZE_BOOTS")) {
                        for (Entity entity : player.getNearbyEntities(8, 8, 8)) {

                            if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                                LivingEntity en = (LivingEntity)  entity;
                                double maxHealth = en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                                double damageAmount = maxHealth*0.006;
                                damageAmount+=0.1;
                                if (damageAmount > 10) damageAmount = 10;

                                if (en.getHealth() - damageAmount <= 0) {
                                    DefenseNerf.lastKilledEntity.put(en, (Player) player);

                                    en.damage(1000000);

                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                        @Override
                                        public void run() {
                                            en.setHealth(0);
                                        }
                                    }, 2);
                                } else {
                                    en.setHealth(en.getHealth() - damageAmount);
                                }



                            }
                        }
                        player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 5, 0.3, 0.8, 0.3, 0);
                    }
                    if (DefenseNerf.isFullSet(player, "FROZEN_BLAZE_HELMET", "FROZEN_BLAZE_CHESTPLATE", "FROZEN_BLAZE_LEGGINGS", "FROZEN_BLAZE_BOOTS")) {
                        for (Entity entity : player.getNearbyEntities(8, 8, 8)) {

                            if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                                LivingEntity en = (LivingEntity)  entity;
                                double maxHealth = en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                                double damageAmount = maxHealth*0.008;
                                damageAmount+=0.2;
                                if (damageAmount > 20) damageAmount = 20;

                                if (en.getHealth() - damageAmount <= 0) {
                                    DefenseNerf.lastKilledEntity.put(en, (Player) player);

                                    en.damage(1000000);

                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                        @Override
                                        public void run() {
                                            en.setHealth(0);
                                        }
                                    }, 2);
                                } else {
                                    en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 2, false, true));
                                    player.getWorld().spawnParticle(Particle.CLOUD, en.getLocation(), 1, 0.3, 0.8, 0.3, 0);
                                    en.setHealth(en.getHealth() - damageAmount);
                                }



                            }
                        }

                        player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 4, 0.3, 0.8, 0.3, 0);
                        player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 2, 0.3, 0.8, 0.3, 0);
                    }


                    Scoreboard playerScoreboard = player.getScoreboard();
                    if (playerScoreboard.getObjective("Health") == null) playerScoreboard.registerNewObjective("Health", "dummy", ChatColor.RED+"❤");

                    Objective Health = playerScoreboard.getObjective("Health");
                    Health.setDisplaySlot(DisplaySlot.BELOW_NAME);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Scoreboard playerScoreboard1 = player.getScoreboard();
                        if (playerScoreboard1.getObjective("Health") == null) continue;

                        Objective Health1 = playerScoreboard1.getObjective("Health");
                        Health1.getScore(player.getName()).setScore((int) Math.ceil(player.getHealth()));

                    }

                    DefenseNerf.totalDefense.putIfAbsent(player, 0);
                    ColeCrafterSlayers.displayActionbar.putIfAbsent(player, true);

                    int health = (int) Math.floor(StatsCommand.getDefence(player));


                    if (ColeCrafterSlayers.displayActionbar.get(player)) {
                        String message = "§c" + ((int) Math.floor(player.getHealth())) + "/" + ((int) Math.floor(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue())) + "❤     §a" + health + "❈ Defense     ";

                        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}"), ChatMessageType.GAME_INFO, UUID.randomUUID());

                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                    }

                    currentLocation.putIfAbsent(player, ChatColor.GRAY + "Nowhere");
                    String area = currentLocation.get(player);


                    updateScoreboard(player, area, "Program Update 0.1");


                    double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();


                    double healAmount = maxHealth/200;
                    Bukkit.getServer().getPluginManager().callEvent(new EntityRegainHealthEvent(player, healAmount, EntityRegainHealthEvent.RegainReason.CUSTOM));




                    player.setFoodLevel(20);


                    Inventory anvilInventory = CustomAnvil.anvilGUI.get(player);
                    Inventory craftInventory = CustomCrafting.craftingGUI.get(player);

                    if (anvilInventory != null) {
                        ItemStack item = anvilInventory.getItem(29);
                        ItemStack item1 = anvilInventory.getItem(33);
                        ItemStack finalItem = null;


                        if (item != null && item1 != null) {
                            if (item.getType() == Material.ENCHANTED_BOOK && item1.getType() == Material.ENCHANTED_BOOK) {

                                short xpCost = 0;

                                finalItem = new ItemStack(Material.ENCHANTED_BOOK);

                                EnchantmentStorageMeta finalMeta = (EnchantmentStorageMeta) finalItem.getItemMeta();

                                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
                                EnchantmentStorageMeta meta1 = (EnchantmentStorageMeta) item1.getItemMeta();

                                for (Map.Entry<Enchantment, Integer> ench : meta.getStoredEnchants().entrySet()) {
                                    for (Map.Entry<Enchantment, Integer> ench1 : meta1.getStoredEnchants().entrySet()) {

                                        if (ench.getKey().equals(ench1.getKey())) {
                                            int addedLevels = ench.getValue() + ench1.getValue();
                                            int finalLevel = 0;

                                            int enchlevel = 0;
                                            enchlevel = ench.getValue();
                                            finalLevel = ench.getValue();
                                            if (ench1.getValue() > ench.getValue()) {
                                                finalLevel = ench1.getValue();
                                            } else if (addedLevels == enchlevel * 2) {
                                                if (enchlevel + 1 <= ench.getKey().getMaxLevel()) {
                                                    finalLevel = enchlevel + 1;
                                                }
                                            }

                                            xpCost += ench1.getValue()+ench.getValue();

                                            finalMeta.addStoredEnchant(ench.getKey(), finalLevel, true);

                                        }
                                        if (!finalMeta.hasStoredEnchant(ench1.getKey())) {
                                            finalMeta.addStoredEnchant(ench1.getKey(), ench1.getValue(), true);
                                            xpCost += ench1.getValue();
                                        }
                                    }
                                    if (!finalMeta.hasStoredEnchant(ench.getKey())) {
                                        finalMeta.addStoredEnchant(ench.getKey(), ench.getValue(), true);
                                        xpCost += ench.getValue();
                                    }
                                }

                                finalItem.setItemMeta(finalMeta);
                                anvilXpCost.put(player, xpCost);


                            }

                            if (item1.getType() == Material.ENCHANTED_BOOK && (item.getType() != Material.ENCHANTED_BOOK)) {

                                finalItem = new ItemStack(item.getType(), 1);

                                short xpCost = 0;

                                ItemMeta finalMeta = finalItem.getItemMeta();

                                finalMeta = item.getItemMeta();
                                EnchantmentStorageMeta meta1 = (EnchantmentStorageMeta) item1.getItemMeta();


                                for (Map.Entry<Enchantment, Integer> ench1 : meta1.getStoredEnchants().entrySet()) {
                                    HashMap<Enchantment, Boolean> canApply = new HashMap<>();
                                    for (Map.Entry<Enchantment, Integer> ench : finalMeta.getEnchants().entrySet()) {
                                        boolean conflictsWith = false;
                                        if ((ench1.getKey().conflictsWith(ench.getKey()))) {
                                            conflictsWith = true;
                                        }
                                        if (ench1.getKey().equals(ench.getKey())) {
                                            conflictsWith = false;
                                        }
                                        if (conflictsWith) {
                                            canApply.put(ench1.getKey(), false);
                                        } else {
                                            canApply.put(ench1.getKey(), true);
                                        }

                                    }

                                    canApply.putIfAbsent(ench1.getKey(), true);
                                    if (canApply.get(ench1.getKey())) {
                                        boolean canApply2 = false;
                                        NamespacedKey key2 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemType");

                                        PersistentDataContainer container2 = item.getItemMeta().getPersistentDataContainer();


                                        String foundValue2 = null;
                                        if (container2.has(key2, PersistentDataType.STRING)) {
                                            foundValue2 = container2.get(key2, PersistentDataType.STRING);
                                        }


                                        if (foundValue2 != null) {
                                            if (foundValue2.equals("SWORD")) {
                                                if ((ench1.getKey().equals(Enchantment.DAMAGE_ALL)) || (ench1.getKey().equals(Enchantment.DAMAGE_ARTHROPODS)) || (ench1.getKey().equals(Enchantment.DAMAGE_UNDEAD)) || (ench1.getKey().equals(Enchantment.KNOCKBACK)) || (ench1.getKey().equals(Enchantment.SWEEPING_EDGE)) || (ench1.getKey().equals(Enchantment.FIRE_ASPECT))) {
                                                    canApply2 = true;
                                                }
                                            }
                                            if (foundValue2.equals("BOW")) {
                                                if ((ench1.getKey().equals(Enchantment.ARROW_DAMAGE)) || (ench1.getKey().equals(Enchantment.ARROW_FIRE)) || (ench1.getKey().equals(Enchantment.ARROW_KNOCKBACK))) {
                                                    canApply2 = true;
                                                }
                                            }

                                            if (foundValue2.equals("CHESTPLATE") || foundValue2.equals("LEGGINGS") || foundValue2.equals("HELMET") || foundValue2.equals("BOOTS")) {
                                                if ((ench1.getKey().equals(Enchantment.PROTECTION_ENVIRONMENTAL)) || (ench1.getKey().equals(Enchantment.PROTECTION_EXPLOSIONS)) || (ench1.getKey().equals(Enchantment.PROTECTION_FIRE)) || (ench1.getKey().equals(Enchantment.PROTECTION_PROJECTILE)) || (ench1.getKey().equals(Enchantment.THORNS))) {
                                                    canApply2 = true;
                                                }
                                                if (((ench1.getKey().equals(Enchantment.DEPTH_STRIDER)) || (ench1.getKey().equals(Enchantment.PROTECTION_FALL))) && foundValue2.equals("BOOTS")) {
                                                    canApply2 = true;
                                                }
                                                if (((ench1.getKey().equals(Enchantment.WATER_WORKER)) || (ench1.getKey().equals(Enchantment.PROTECTION_FALL))) && foundValue2.equals("HELMET")) {
                                                    canApply2 = true;
                                                }
                                            }
                                            if (foundValue2.equals("TOOL")) {
                                                if (ench1.getKey().equals(Enchantment.DIG_SPEED)) {
                                                    canApply2 = true;
                                                }
                                            }
                                        }




                                        if (canApply2) {
                                            if (!finalMeta.hasEnchant(ench1.getKey())) {
                                                if (!ench1.getKey().equals(Enchantment.ARROW_INFINITE)) {
                                                    finalMeta.addEnchant(ench1.getKey(), ench1.getValue(), true);
                                                    xpCost += ench1.getValue();
                                                }
                                            } else if (finalMeta.getEnchantLevel(ench1.getKey()) < ench1.getValue()) {
                                                if (!ench1.getKey().equals(Enchantment.ARROW_INFINITE)) {
                                                    finalMeta.addEnchant(ench1.getKey(), ench1.getValue(), true);
                                                    xpCost += ench1.getValue();
                                                }
                                            } else if (finalMeta.getEnchantLevel(ench1.getKey()) == ench1.getValue()) {
                                                if (!ench1.getKey().equals(Enchantment.ARROW_INFINITE)) {
                                                    if (ench1.getValue() + 1 <= ench1.getKey().getMaxLevel()) {
                                                        finalMeta.addEnchant(ench1.getKey(), ench1.getValue() + 1, true);
                                                        xpCost += ench1.getValue();
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }


                                finalItem.setItemMeta(finalMeta);

                                anvilXpCost.put(player, xpCost);


                            }

                            if (item1.getType() == Material.BOOK && (item.getType() != Material.BOOK)) {

                                finalItem = new ItemStack(item.getType(), 1);

                                ItemMeta finalMeta = finalItem.getItemMeta();

                                finalMeta = item.getItemMeta();

                                if (item1.hasItemMeta()) {
                                    NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemID");
                                    ItemMeta itemMeta = item1.getItemMeta();
                                    ItemMeta itemMeta1 = item.getItemMeta();
                                    if (itemMeta != null) {
                                        PersistentDataContainer container = itemMeta.getPersistentDataContainer();


                                        String foundValue = null;
                                        if (container.has(key, PersistentDataType.STRING)) {
                                            foundValue = container.get(key, PersistentDataType.STRING);
                                        }
                                        if (foundValue != null) {
                                            if (foundValue.equals("HOT_POTATO_BOOK")) {
                                                NamespacedKey key1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "hotPotatoBooks");

                                                PersistentDataContainer container1 = itemMeta1.getPersistentDataContainer();


                                                int foundValue1 = 0;
                                                if (container1.has(key1, PersistentDataType.INTEGER)) {
                                                    foundValue1 = container1.get(key1, PersistentDataType.INTEGER);
                                                }

                                                if (foundValue1 < 10) {
                                                    NamespacedKey HPBKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "hotPotatoBooks");

                                                    finalMeta.getPersistentDataContainer().set(HPBKey, PersistentDataType.INTEGER, foundValue1 + 1);


                                                    foundValue1 += 1;

                                                    NamespacedKey key2 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemType");

                                                    PersistentDataContainer container2 = itemMeta1.getPersistentDataContainer();


                                                    String foundValue2 = null;
                                                    if (container2.has(key2, PersistentDataType.STRING)) {
                                                        foundValue2 = container2.get(key2, PersistentDataType.STRING);
                                                    }

                                                    NamespacedKey key3 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemDamage");

                                                    PersistentDataContainer container3 = itemMeta1.getPersistentDataContainer();


                                                    float foundValue3 = 0;
                                                    if (container3.has(key3, PersistentDataType.FLOAT)) {
                                                        foundValue3 = container3.get(key3, PersistentDataType.FLOAT);
                                                    }


                                                    UUID uuid = UUID.fromString("4c7f27cb-3183-4b24-a7ab-790c8dc29eb1");


                                                    if (foundValue2 != null) {
                                                        if (foundValue2.equals("SWORD")) {
                                                            for (AttributeModifier attribute : finalMeta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE)) {
                                                                if (attribute.getName().equals("hotPotatoBooks")) {
                                                                    finalMeta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attribute);

                                                                }

                                                            }
                                                            finalMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(uuid, "hotPotatoBooks", foundValue1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));

                                                            NamespacedKey ItemHPBDamageKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "hpbDamage");

                                                            finalMeta.getPersistentDataContainer().set(ItemHPBDamageKey, PersistentDataType.INTEGER, foundValue1);

                                                            List<String> Lore = itemMeta1.getLore();
                                                            Lore.set(0, ChatColor.GRAY + "Base Damage: " + ChatColor.RED + "+" + foundValue3 + ChatColor.YELLOW + " (+" + foundValue1 + ")");
                                                            finalMeta.setLore(Lore);
                                                        }
                                                        if (foundValue2.equals("BOW")) {
                                                            NamespacedKey ItemHPBDamageKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "hpbDamage");

                                                            finalMeta.getPersistentDataContainer().set(ItemHPBDamageKey, PersistentDataType.INTEGER, foundValue1);

                                                            List<String> Lore = itemMeta1.getLore();
                                                            Lore.set(0, ChatColor.GRAY + "Base Damage: " + ChatColor.RED + "+" + foundValue3 + ChatColor.YELLOW + " (+" + foundValue1 + ")");
                                                            finalMeta.setLore(Lore);
                                                        }
//                                                    if (foundValue2.equals("HELMET") || foundValue2.equals("CHESTPLATE") || foundValue2.equals("LEGGINGS") || foundValue2.equals("BOOTS")) {
//                                                        NamespacedKey ItemHPBDamageKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "hpbDamage");
//
//                                                        finalMeta.getPersistentDataContainer().set(ItemHPBDamageKey, PersistentDataType.INTEGER, foundValue1);
//
//                                                        List<String> Lore = itemMeta1.getLore();
//                                                        Lore.set(0, ChatColor.GRAY + "Base Damage: " + ChatColor.RED + "+" + foundValue3 + ChatColor.YELLOW + " (+" + foundValue1 + ")");
//                                                        finalMeta.setLore(Lore);
//                                                    }


                                                    }
                                                }
                                            }

                                        }
                                    }

                                }
                                finalItem.setItemMeta(finalMeta);
                                anvilXpCost.put(player, (short) 0);


                            }


                        }
                        CustomAnvil.anvilCombined.putIfAbsent(player, false);
                        if (!(CustomAnvil.anvilCombined.get(player))) {
                            ItemStack anvilNo = new ItemStack(Material.BARRIER, 1);
                            ItemMeta meta = anvilNo.getItemMeta();
                            meta.setDisplayName(ChatColor.RED + "Anvil");
                            anvilNo.setItemMeta(meta);
                            anvilInventory.setItem(13, anvilNo);
                        }

                        if (finalItem != null) {


                            if (!(finalItem.equals(item))) {


                                if (anvilInventory.getItem(13).getType() == Material.BARRIER) {
                                    anvilInventory.setItem(13, finalItem);
                                }

                            }
                        }


                        if (anvilInventory.getItem(13).getType() != Material.BARRIER) {
                            ItemStack anvil = anvilInventory.getItem(22);

                            anvil.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                            ItemMeta anvilMeta = anvil.getItemMeta();
                            anvilMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(" ");
                            lore.add(ChatColor.DARK_AQUA+""+(anvilXpCost.get(player))+" Levels");

                            anvilMeta.setLore(lore);
                            anvil.setItemMeta(anvilMeta);



                            anvilInventory.setItem(22, anvil);
                        } else {
                            ItemStack anvil = anvilInventory.getItem(22);

                            anvil.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);


                            anvilInventory.setItem(22, anvil);
                        }

                        if (item != null) {
                            anvilInventory.setItem(11, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(12, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(20, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                        } else if (item == null) {
                            anvilInventory.setItem(11, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(12, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(20, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                        }

                        if (item1 != null) {
                            anvilInventory.setItem(14, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(15, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(24, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                        } else if (item1 == null) {
                            anvilInventory.setItem(14, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(15, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(24, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                        }

                        if (anvilInventory.getItem(13).getType() != Material.BARRIER && item != null && item1 != null) {
                            anvilInventory.setItem(45, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(46, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(47, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(48, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(50, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(51, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(52, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(53, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));


                        } else if (anvilInventory.getItem(13).getType() == Material.BARRIER || item == null || item1 == null) {
                            anvilInventory.setItem(45, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(46, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(47, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(48, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(50, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(51, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(52, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            anvilInventory.setItem(53, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                        }
                    }

                    if (craftInventory != null) {


                        ItemStack item = craftInventory.getItem(10);
                        ItemStack item1 = craftInventory.getItem(11);
                        ItemStack item2 = craftInventory.getItem(12);
                        ItemStack item3 = craftInventory.getItem(19);
                        ItemStack item4 = craftInventory.getItem(20);
                        ItemStack item5 = craftInventory.getItem(21);
                        ItemStack item6 = craftInventory.getItem(28);
                        ItemStack item7 = craftInventory.getItem(29);
                        ItemStack item8 = craftInventory.getItem(30);

                        if (item == null) {
                            item = new ItemStack(Material.AIR);
                        }

                        if (item1 == null) {
                            item1 = new ItemStack(Material.AIR);
                        }

                        if (item2 == null) {
                            item2 = new ItemStack(Material.AIR);
                        }

                        if (item3 == null) {
                            item3 = new ItemStack(Material.AIR);
                        }

                        if (item4 == null) {
                            item4 = new ItemStack(Material.AIR);
                        }

                        if (item5 == null) {
                            item5 = new ItemStack(Material.AIR);
                        }

                        if (item6 == null) {
                            item6 = new ItemStack(Material.AIR);
                        }

                        if (item7 == null) {
                            item7 = new ItemStack(Material.AIR);
                        }
                        if (item8 == null) {
                            item8 = new ItemStack(Material.AIR);
                        }


                        for (CustomRecipe recipe : CustomRecipe.customRecipes) {
                            ArrayList<Character> characterlist = new ArrayList<>();
                            for (Map.Entry<Character, ItemStack> entry : recipe.ingredients.entrySet()) {
                                characterlist.add(entry.getKey());
                            }

                            ItemStack item9 = new ItemStack(Material.AIR);
                            ItemStack item10 = new ItemStack(Material.AIR);
                            ItemStack item11 = new ItemStack(Material.AIR);
                            ItemStack item12 = new ItemStack(Material.AIR);
                            ItemStack item13 = new ItemStack(Material.AIR);
                            ItemStack item14 = new ItemStack(Material.AIR);
                            ItemStack item15 = new ItemStack(Material.AIR);
                            ItemStack item16 = new ItemStack(Material.AIR);
                            ItemStack item17 = new ItemStack(Material.AIR);


                            for (Character character : characterlist) {
                                if (recipe.patterns.charAt(0) == character) {
                                    item9 = recipe.ingredients.get(character);
                                    item9.setAmount(recipe.ingredientsAmount.get(character));
                                }
                                if (recipe.patterns.charAt(1) == character) {
                                    item10 = recipe.ingredients.get(character);
                                    item10.setAmount(recipe.ingredientsAmount.get(character));
                                }
                                if (recipe.patterns.charAt(2) == character) {
                                    item11 = recipe.ingredients.get(character);
                                    item11.setAmount(recipe.ingredientsAmount.get(character));
                                }
                                if (recipe.patterns.charAt(3) == character) {
                                    item12 = recipe.ingredients.get(character);
                                    item12.setAmount(recipe.ingredientsAmount.get(character));
                                }
                                if (recipe.patterns.charAt(4) == character) {
                                    item13 = recipe.ingredients.get(character);
                                    item13.setAmount(recipe.ingredientsAmount.get(character));
                                }
                                if (recipe.patterns.charAt(5) == character) {
                                    item14 = recipe.ingredients.get(character);
                                    item14.setAmount(recipe.ingredientsAmount.get(character));
                                }
                                if (recipe.patterns.charAt(6) == character) {
                                    item15 = recipe.ingredients.get(character);
                                    item15.setAmount(recipe.ingredientsAmount.get(character));
                                }
                                if (recipe.patterns.charAt(7) == character) {
                                    item16 = recipe.ingredients.get(character);
                                    item16.setAmount(recipe.ingredientsAmount.get(character));
                                }
                                if (recipe.patterns.charAt(8) == character) {
                                    item17 = recipe.ingredients.get(character);
                                    item17.setAmount(recipe.ingredientsAmount.get(character));
                                }
                            }


                            if (isEqual(item, item9) && isEqual(item1, item10) && isEqual(item2, item11) && isEqual(item3, item12) && isEqual(item4, item13) && isEqual(item5, item14) && isEqual(item6, item15) && isEqual(item7, item16) && isEqual(item8, item17)) {
                                HashMap<Integer, Integer> craftingAmounts1 = new HashMap<>();

                                craftingAmounts1.put(10, item9.getAmount());
                                if (item9.getType() == Material.AIR) {
                                    craftingAmounts1.put(10, 0);
                                }
                                craftingAmounts1.put(11, item10.getAmount());
                                if (item10.getType() == Material.AIR) {
                                    craftingAmounts1.put(11, 0);
                                }
                                craftingAmounts1.put(12, item11.getAmount());
                                if (item11.getType() == Material.AIR) {
                                    craftingAmounts1.put(12, 0);
                                }
                                craftingAmounts1.put(19, item12.getAmount());
                                if (item12.getType() == Material.AIR) {
                                    craftingAmounts1.put(19, 0);
                                }
                                craftingAmounts1.put(20, item13.getAmount());
                                if (item13.getType() == Material.AIR) {
                                    craftingAmounts1.put(20, 0);
                                }
                                craftingAmounts1.put(21, item14.getAmount());
                                if (item14.getType() == Material.AIR) {
                                    craftingAmounts1.put(21, 0);
                                }
                                craftingAmounts1.put(28, item15.getAmount());
                                if (item15.getType() == Material.AIR) {
                                    craftingAmounts1.put(28, 0);
                                }
                                craftingAmounts1.put(29, item16.getAmount());
                                if (item16.getType() == Material.AIR) {
                                    craftingAmounts1.put(29, 0);
                                }
                                craftingAmounts1.put(30, item17.getAmount());
                                if (item17.getType() == Material.AIR) {
                                    craftingAmounts1.put(30, 0);
                                }


                                craftingAmounts.put(player, craftingAmounts1);
                                ItemStack result1 = recipe.getResult();
                                result1.setAmount(recipe.getAmount());

                                craftingResult.put(player, result1);
                                craftInventory.setItem(24, result1);
                                break;
                            } else {
                                ItemStack anvilNo = new ItemStack(Material.BARRIER, 1);
                                ItemMeta meta = anvilNo.getItemMeta();
                                meta.setDisplayName(ChatColor.RED + "Craft");
                                anvilNo.setItemMeta(meta);
                                craftInventory.setItem(24, anvilNo);
                            }


                        }
                        if (craftInventory.getItem(24).getType() != Material.BARRIER) {
                            craftInventory.setItem(45, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(46, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(47, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(48, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(50, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(51, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(52, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(53, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));


                        } else if (craftInventory.getItem(24).getType() == Material.BARRIER) {
                            craftInventory.setItem(45, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(46, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(47, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(48, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(50, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(51, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(52, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                            craftInventory.setItem(53, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                        }
                    }
                }
            }
        }, 1, 4);






        SlayerMenu.slayerMenu = Bukkit.createInventory(Bukkit.getPlayer("rexe0"), 27, ChatColor.DARK_GRAY+"Slayer");

        for (byte i = 0; i < 27; i++) {
            if (i > 10 && i < 17) {
                ItemStack item = new ItemStack(Material.COAL_BLOCK);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED+"Not released yet!");
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY+"This boss is still in");
                lore.add(ChatColor.GRAY+"development!");
                meta.setLore(lore);
                item.setItemMeta(meta);
                SlayerMenu.slayerMenu.setItem(i, item);
            }

            if ((i >= 0 && i < 10) || (i >= 17 && i <= 26)) {
                ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("");
                item.setItemMeta(meta);
                SlayerMenu.slayerMenu.setItem(i, item);
            }

            if (i == 10) {
                ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED+"☠"+ChatColor.YELLOW+" Revenant Horror");
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY+"Abhorrant Zombie stuck");
                lore.add(ChatColor.GRAY+"between life and death for");
                lore.add(ChatColor.GRAY+"an eternity");
                lore.add(ChatColor.GRAY+" ");
                lore.add(ChatColor.YELLOW+"Click to view boss!");
                meta.setLore(lore);
                item.setItemMeta(meta);
                SlayerMenu.slayerMenu.setItem(i, item);
            }
        }

        // Revenant Horror drain and pestilence ability
        scheduleSyncLoop(new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (Entity e : p.getWorld().getEntities()) {
                        if (e.getType() == EntityType.ZOMBIE) {
                            if (((LivingEntity) e).getEquipment().getChestplate() != null) {
                                if (((LivingEntity) e).getEquipment().getChestplate().hasItemMeta()) {
                                    if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().hasDisplayName()) {
                                        if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant") && ((LivingEntity) e).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 5) {
                                            Player player = SlayerMenu.revenantHorrorPlayer.get(e);

                                            player.damage(1);

                                            DefenseNerf.antiNotHit.putIfAbsent((LivingEntity) e, 0);
                                            int antiNotInteger = DefenseNerf.antiNotHit.get(e);

                                            antiNotInteger += 60;

                                            DefenseNerf.antiNotHit.put((LivingEntity) e, antiNotInteger);

                                            double maxHealth = ((LivingEntity) e).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                                            if (antiNotInteger > 500) {
                                                if (((LivingEntity) e).getHealth() + 15 < maxHealth) {
                                                    ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 15);
                                                } else if (((LivingEntity) e).getHealth() + 15 > maxHealth) {
                                                    ((LivingEntity) e).setHealth(maxHealth);
                                                }
                                                e.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e.getLocation(), 300, 0.5, 0.8, 0.5);
                                            }

                                            if (antiNotInteger > 400) {
                                                e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 500, 0.5, 0.8, 0.5);
                                                e.teleport(player.getLocation());
                                                e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 300, 0.5, 0.8, 0.5);
                                                player.sendMessage(ChatColor.DARK_PURPLE+"The boss teleported to you using some really dark magic!");
                                            }




                                            if (((LivingEntity) e).getHealth() + 1 < maxHealth) {
                                                ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 1);
                                            } else if (((LivingEntity) e).getHealth() + 1 > maxHealth) {
                                                ((LivingEntity) e).setHealth(maxHealth);
                                            }

                                            int health = (int) Math.ceil(((LivingEntity) e).getHealth());

                                            String color = health > 15 ? ChatColor.GREEN + "" : health <= 15 && health > 8 ? ChatColor.YELLOW + "" : health <= 8 ? ChatColor.RED + "" : "";

                                            e.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv10"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/30" + ChatColor.RED + "❤");


                                            Location origin = e.getLocation();
                                            origin.setY(e.getLocation().getY() + 1);

                                            Location target1 = player.getLocation();
                                            target1.setY(player.getLocation().getY() + 1);
                                            Vector target = target1.toVector();
                                            origin.setDirection(target.subtract(origin.toVector()));
                                            Vector increase = origin.getDirection();
                                            for (int counter = 0; counter < (int) e.getLocation().distance(player.getLocation()); counter++) {
                                                Location loc = origin.add(increase);
                                                for (Player pp : Bukkit.getOnlinePlayers()) {

                                                    PacketPlayOutWorldParticles heartPacket = new PacketPlayOutWorldParticles(Particles.HAPPY_VILLAGER, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);
                                                    PacketPlayOutWorldParticles greenPacket = new PacketPlayOutWorldParticles(Particles.HEART, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);

                                                    ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(greenPacket);
                                                    ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(heartPacket);
                                                }
                                            }
                                        }
                                    }
                                }
                                if (((LivingEntity) e).getEquipment().getChestplate().hasItemMeta()) {
                                    if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().hasDisplayName()) {
                                        if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant") && ((LivingEntity) e).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 6) {

                                            Player player = SlayerMenu.revenantHorrorPlayer.get(e);

                                            player.damage(1);


                                            for (Entity en : e.getNearbyEntities(3, 3, 3)) {
                                                if (en instanceof Player) {
                                                    if (en.equals(player)) {
                                                        DefenseNerf.pestilence.put(player, 0.25f);

                                                        scheduleSyncDelayedTask(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                DefenseNerf.pestilence.put(player, 0f);
                                                            }
                                                        }, 40);
                                                    }
                                                }
                                            }
                                            e.getLocation().getWorld().spawnParticle(Particle.SLIME, e.getLocation(), 700, 2, 1.5, 2);


                                            DefenseNerf.oldLocation.putIfAbsent(player, player.getLocation());
                                            DefenseNerf.newLocation.put(player, player.getLocation());

                                            if (DefenseNerf.oldLocation.get(player).getBlockX() == DefenseNerf.newLocation.get(player).getBlockX() && DefenseNerf.oldLocation.get(player).getBlockZ() == DefenseNerf.newLocation.get(player).getBlockZ()) {
                                                DefenseNerf.antiStandStill.putIfAbsent(player, 0);
                                                int i = DefenseNerf.antiStandStill.get(player);
                                                i += 60;
                                                DefenseNerf.antiStandStill.put(player, i);
                                            } else if (DefenseNerf.oldLocation.get(player).getBlockX() != DefenseNerf.oldLocation.get(player).getBlockX() || DefenseNerf.oldLocation.get(player).getBlockZ() != DefenseNerf.newLocation.get(player).getBlockZ()) {
                                                DefenseNerf.antiStandStill.put(player, 0);
                                            }

                                            DefenseNerf.oldLocation.put(player, player.getLocation());


                                            if (DefenseNerf.antiStandStill.get(player) > 400) {
                                                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 2, 1);
                                                player.damage(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 0.5);
                                                player.setVelocity(player.getLocation().getDirection().multiply(10));
                                                player.getVelocity().setY(2);
                                                DefenseNerf.antiStandStill.put(player, 0);
                                            }

                                            DefenseNerf.antiNotHit.putIfAbsent((LivingEntity) e, 0);
                                            int antiNotInteger = DefenseNerf.antiNotHit.get(e);

                                            antiNotInteger += 60;

                                            DefenseNerf.antiNotHit.put((LivingEntity) e, antiNotInteger);

                                            double maxHealth = ((LivingEntity) e).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                                            if (antiNotInteger > 500) {
                                                if (((LivingEntity) e).getHealth() + 45 < maxHealth) {
                                                    ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 45);
                                                } else if (((LivingEntity) e).getHealth() + 45 > maxHealth) {
                                                    ((LivingEntity) e).setHealth(maxHealth);
                                                }
                                                e.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e.getLocation(), 300, 0.5, 0.8, 0.5);

                                                Location loc = e.getLocation();
                                                        loc.setY(loc.getY()+2);
                                                        ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(e.getWorld().getName()).spawn(loc, ArmorStand.class);

                                                as.setCustomName(ChatColor.GREEN+"Regenerating!");


                                                as.setCustomNameVisible(true);
                                                as.setVisible(false);
                                                as.setGravity(false);
                                                as.setMarker(true);


                                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        as.remove();
                                                    }
                                                }, 40);
                                            }

                                            if (antiNotInteger > 400) {
                                                e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 500, 0.5, 0.8, 0.5);
                                                e.teleport(player.getLocation());
                                                e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 300, 0.5, 0.8, 0.5);
                                                player.sendMessage(ChatColor.DARK_PURPLE+"The boss teleported to you using some really dark magic!");
                                            }




                                            if (((LivingEntity) e).getHealth() + 2 < maxHealth) {
                                                ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 2);
                                            } else if (((LivingEntity) e).getHealth() + 2 > maxHealth) {
                                                ((LivingEntity) e).setHealth(maxHealth);
                                            }

                                            int health = (int) Math.ceil(((LivingEntity) e).getHealth());

                                            String color = health > 45 ? ChatColor.GREEN + "" : health <= 45 && health > 23 ? ChatColor.YELLOW + "" : health <= 23 ? ChatColor.RED + "" : "";

                                            e.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv70"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/90" + ChatColor.RED + "❤");


                                            Location origin = e.getLocation();
                                            origin.setY(e.getLocation().getY() + 1);

                                            Location target1 = player.getLocation();
                                            target1.setY(player.getLocation().getY() + 1);
                                            Vector target = target1.toVector();
                                            origin.setDirection(target.subtract(origin.toVector()));
                                            Vector increase = origin.getDirection();
                                            for (int counter = 0; counter < (int) e.getLocation().distance(player.getLocation()); counter++) {
                                                Location loc = origin.add(increase);
                                                for (Player pp : Bukkit.getOnlinePlayers()) {

                                                    PacketPlayOutWorldParticles heartPacket = new PacketPlayOutWorldParticles(Particles.HAPPY_VILLAGER, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);
                                                    PacketPlayOutWorldParticles greenPacket = new PacketPlayOutWorldParticles(Particles.HEART, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);

                                                    ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(greenPacket);
                                                    ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(heartPacket);
                                                }
                                            }
                                        }
                                    }
                                }
                                if (((LivingEntity) e).getEquipment().getChestplate().hasItemMeta()) {
                                    if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().hasDisplayName()) {
                                        if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant") && ((LivingEntity) e).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 7) {

                                            Player player = SlayerMenu.revenantHorrorPlayer.get(e);

                                            player.damage(2);


                                            for (Entity en : e.getNearbyEntities(3, 3, 3)) {
                                                if (en instanceof Player) {
                                                    if (en.equals(player)) {
                                                        DefenseNerf.pestilence.put(player, 0.25f);

                                                        scheduleSyncDelayedTask(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                DefenseNerf.pestilence.put(player, 0f);
                                                            }
                                                        }, 40);
                                                    }
                                                }
                                            }
                                            e.getLocation().getWorld().spawnParticle(Particle.SLIME, e.getLocation(), 700, 2, 1.5, 2);


                                            DefenseNerf.oldLocation.putIfAbsent(player, player.getLocation());
                                            DefenseNerf.newLocation.put(player, player.getLocation());

                                            if (DefenseNerf.oldLocation.get(player).getBlockX() == DefenseNerf.newLocation.get(player).getBlockX() && DefenseNerf.oldLocation.get(player).getBlockZ() == DefenseNerf.newLocation.get(player).getBlockZ()) {
                                                DefenseNerf.antiStandStill.putIfAbsent(player, 0);
                                                int i = DefenseNerf.antiStandStill.get(player);
                                                i += 60;
                                                DefenseNerf.antiStandStill.put(player, i);
                                            } else if (DefenseNerf.oldLocation.get(player).getBlockX() != DefenseNerf.oldLocation.get(player).getBlockX() || DefenseNerf.oldLocation.get(player).getBlockZ() != DefenseNerf.newLocation.get(player).getBlockZ()) {
                                                DefenseNerf.antiStandStill.put(player, 0);
                                            }

                                            DefenseNerf.oldLocation.put(player, player.getLocation());


                                            if (DefenseNerf.antiStandStill.get(player) > 400) {
                                                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 2, 1);
                                                player.damage(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 0.5);
                                                player.setVelocity(player.getLocation().getDirection().multiply(10));
                                                player.getVelocity().setY(2);
                                                DefenseNerf.antiStandStill.put(player, 0);
                                            }

                                            DefenseNerf.antiNotHit.putIfAbsent((LivingEntity) e, 0);
                                            int antiNotInteger = DefenseNerf.antiNotHit.get(e);

                                            antiNotInteger += 60;

                                            DefenseNerf.antiNotHit.put((LivingEntity) e, antiNotInteger);

                                            double maxHealth = ((LivingEntity) e).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                                            if (antiNotInteger > 500) {
                                                if (((LivingEntity) e).getHealth() + 150 < maxHealth) {
                                                    ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 150);
                                                } else if (((LivingEntity) e).getHealth() + 150 > maxHealth) {
                                                    ((LivingEntity) e).setHealth(maxHealth);
                                                }
                                                e.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e.getLocation(), 300, 0.5, 0.8, 0.5);
                                                Location loc = e.getLocation();
                                                        loc.setY(loc.getY()+2);
                                                        ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(e.getWorld().getName()).spawn(loc, ArmorStand.class);

                                                as.setCustomName(ChatColor.GREEN+"Regenerating!");


                                                as.setCustomNameVisible(true);
                                                as.setVisible(false);
                                                as.setGravity(false);
                                                as.setMarker(true);


                                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        as.remove();
                                                    }
                                                }, 40);
                                            }

                                            if (antiNotInteger > 400) {
                                                e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 500, 0.5, 0.8, 0.5);
                                                e.teleport(player.getLocation());
                                                e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 300, 0.5, 0.8, 0.5);
                                                player.sendMessage(ChatColor.DARK_PURPLE+"The boss teleported to you using some really dark magic!");
                                            }

                                            if (((LivingEntity) e).getEquipment().getLeggings().hasItemMeta()) {
                                                if (((LivingEntity) e).getEquipment().getLeggings().getItemMeta().hasDisplayName()) {
                                                    if (((LivingEntity) e).getHealth() < maxHealth * 0.75 && Integer.parseInt(((LivingEntity) e).getEquipment().getLeggings().getItemMeta().getDisplayName()) < 1) {
                                                        e.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, e.getLocation(), 300, 0.5, 1, 0.5);
                                                        player.playSound(e.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                                        player.playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, 1);


                                                        LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                                        ((Zombie) revenantHorror).setBaby(false);


                                                        revenantHorror.setCustomNameVisible(true);
                                                        revenantHorror.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv70"+ChatColor.DARK_GRAY+"]"+ChatColor.RED + " Revenant Sycophant");

                                                        revenantHorror.getEquipment().setHelmet(new ItemStack(Material.AIR));

                                                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                        revenantHorror.getEquipment().setChestplate(chestplate);
                                                        revenantHorror.getEquipment().setChestplateDropChance(0);

                                                        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                                        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                        revenantHorror.getEquipment().setLeggings(leggings);
                                                        revenantHorror.getEquipment().setLeggingsDropChance(0);

                                                        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);

                                                        revenantHorror.getEquipment().setBoots(boots);
                                                        revenantHorror.getEquipment().setBootsDropChance(0);

                                                        ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD);
                                                        weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                        revenantHorror.getEquipment().setItemInMainHand(weapon);
                                                        revenantHorror.getEquipment().setItemInMainHandDropChance(0);


                                                        revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
                                                        revenantHorror.setHealth(100);

                                                        revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                                                        revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);
                                                        revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                                        revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 1.5);

                                                        ItemStack legs = ((LivingEntity) e).getEquipment().getLeggings();
                                                        ItemMeta meta = legs.getItemMeta();
                                                        meta.setDisplayName("1");
                                                        legs.setItemMeta(meta);
                                                        ((LivingEntity) e).getEquipment().setLeggings(legs);
                                                    }
                                                }
                                            }

                                            if (((LivingEntity) e).getHealth() + 3 < maxHealth) {
                                                ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 3);
                                            } else if (((LivingEntity) e).getHealth() + 3 > maxHealth) {
                                                ((LivingEntity) e).setHealth(maxHealth);
                                            }

                                            int health = (int) Math.ceil(((LivingEntity) e).getHealth());

                                            String color = health > 150 ? ChatColor.GREEN + "" : health <= 150 && health > 75 ? ChatColor.YELLOW + "" : health <= 75 ? ChatColor.RED + "" : "";

                                            e.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv310"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/300" + ChatColor.RED + "❤");


                                            Location origin = e.getLocation();
                                            origin.setY(e.getLocation().getY() + 1);

                                            Location target1 = player.getLocation();
                                            target1.setY(player.getLocation().getY() + 1);
                                            Vector target = target1.toVector();
                                            origin.setDirection(target.subtract(origin.toVector()));
                                            Vector increase = origin.getDirection();
                                            for (int counter = 0; counter < (int) e.getLocation().distance(player.getLocation()); counter++) {
                                                Location loc = origin.add(increase);
                                                for (Player pp : Bukkit.getOnlinePlayers()) {

                                                    PacketPlayOutWorldParticles heartPacket = new PacketPlayOutWorldParticles(Particles.HAPPY_VILLAGER, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);
                                                    PacketPlayOutWorldParticles greenPacket = new PacketPlayOutWorldParticles(Particles.HEART, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);

                                                    ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(greenPacket);
                                                    ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(heartPacket);
                                                }
                                            }
                                        }
                                    }
                                }

                            }

                            if (((LivingEntity) e).getEquipment().getChestplate().hasItemMeta()) {
                                if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().hasDisplayName()) {
                                    if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant") && ((LivingEntity) e).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 8) {

                                        Player player = SlayerMenu.revenantHorrorPlayer.get(e);

                                        player.damage(3);


                                        for (Entity en : e.getNearbyEntities(3, 3, 3)) {
                                            if (en instanceof Player) {
                                                if (en.equals(player)) {
                                                    DefenseNerf.pestilence.put(player, 0.5f);

                                                    scheduleSyncDelayedTask(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            DefenseNerf.pestilence.put(player, 0f);
                                                        }
                                                    }, 40);
                                                }
                                            }
                                        }
                                        e.getLocation().getWorld().spawnParticle(Particle.SLIME, e.getLocation(), 700, 2, 1.5, 2);
                                        e.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e.getLocation(), 300, 2, 1.5, 2);


                                        DefenseNerf.oldLocation.putIfAbsent(player, player.getLocation());
                                        DefenseNerf.newLocation.put(player, player.getLocation());

                                        if (DefenseNerf.oldLocation.get(player).getBlockX() == DefenseNerf.newLocation.get(player).getBlockX() && DefenseNerf.oldLocation.get(player).getBlockZ() == DefenseNerf.newLocation.get(player).getBlockZ()) {
                                            DefenseNerf.antiStandStill.putIfAbsent(player, 0);
                                            int i = DefenseNerf.antiStandStill.get(player);
                                            i += 60;
                                            DefenseNerf.antiStandStill.put(player, i);
                                        } else if (DefenseNerf.oldLocation.get(player).getBlockX() != DefenseNerf.oldLocation.get(player).getBlockX() || DefenseNerf.oldLocation.get(player).getBlockZ() != DefenseNerf.newLocation.get(player).getBlockZ()) {
                                            DefenseNerf.antiStandStill.put(player, 0);
                                        }

                                        DefenseNerf.oldLocation.put(player, player.getLocation());


                                        if (DefenseNerf.antiStandStill.get(player) > 200) {
                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 2, 1);
                                            player.damage(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 0.9);
                                            player.setVelocity(player.getLocation().getDirection().multiply(10));
                                            player.getVelocity().setY(2);
                                            DefenseNerf.antiStandStill.put(player, 0);
                                        }

                                        DefenseNerf.antiNotHit.putIfAbsent((LivingEntity) e, 0);
                                        int antiNotInteger = DefenseNerf.antiNotHit.get(e);

                                        antiNotInteger += 60;

                                        DefenseNerf.antiNotHit.put((LivingEntity) e, antiNotInteger);

                                        double maxHealth = ((LivingEntity) e).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                                        if (antiNotInteger > 500) {
                                            if (((LivingEntity) e).getHealth() + 450 < maxHealth) {
                                                ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 450);
                                            } else if (((LivingEntity) e).getHealth() + 450 > maxHealth) {
                                                ((LivingEntity) e).setHealth(maxHealth);
                                            }

                                            e.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e.getLocation(), 300, 0.5, 0.8, 0.5);

                                            Location loc = e.getLocation();
                                                        loc.setY(loc.getY()+2);
                                                        ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(e.getWorld().getName()).spawn(loc, ArmorStand.class);

                                            as.setCustomName(ChatColor.GREEN+"Regenerating!");


                                            as.setCustomNameVisible(true);
                                            as.setVisible(false);
                                            as.setGravity(false);
                                            as.setMarker(true);


                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                @Override
                                                public void run() {
                                                    as.remove();
                                                }
                                            }, 40);
                                        }

                                        if (antiNotInteger > 400) {
                                            e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 500, 0.5, 0.8, 0.5);
                                            e.teleport(player.getLocation());
                                            e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 300, 0.5, 0.8, 0.5);
                                            player.sendMessage(ChatColor.DARK_PURPLE+"The boss teleported to you using some really dark magic!");
                                        }


                                        if (((LivingEntity) e).getHealth() + 5 < maxHealth) {
                                            ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 5);
                                        } else if (((LivingEntity) e).getHealth() + 5 > maxHealth) {
                                            ((LivingEntity) e).setHealth(maxHealth);
                                        }

                                        int health = (int) Math.ceil(((LivingEntity) e).getHealth());

                                        String color = health > 450 ? ChatColor.GREEN + "" : health <= 450 && health > 225 ? ChatColor.YELLOW + "" : health <= 225 ? ChatColor.RED + "" : "";

                                        e.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv610"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/900" + ChatColor.RED + "❤");

                                        if (((LivingEntity) e).getEquipment().getLeggings().hasItemMeta()) {
                                            if (((LivingEntity) e).getEquipment().getLeggings().getItemMeta().hasDisplayName()) {
                                                if (((LivingEntity) e).getHealth() < maxHealth * 0.75 && Integer.parseInt(((LivingEntity) e).getEquipment().getLeggings().getItemMeta().getDisplayName()) < 1) {
                                                    e.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, e.getLocation(), 300, 0.5, 1, 0.5);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, 1);


                                                    LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                                    ((Zombie) revenantHorror).setBaby(false);


                                                    revenantHorror.setCustomNameVisible(true);
                                                    revenantHorror.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv70"+ChatColor.DARK_GRAY+"]"+ChatColor.RED + " Revenant Sycophant");

                                                    revenantHorror.getEquipment().setHelmet(new ItemStack(Material.AIR));

                                                    ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                    chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setChestplate(chestplate);
                                                    revenantHorror.getEquipment().setChestplateDropChance(0);

                                                    ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                                    leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setLeggings(leggings);
                                                    revenantHorror.getEquipment().setLeggingsDropChance(0);

                                                    ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);

                                                    revenantHorror.getEquipment().setBoots(boots);
                                                    revenantHorror.getEquipment().setBootsDropChance(0);

                                                    ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD);
                                                    weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setItemInMainHand(weapon);
                                                    revenantHorror.getEquipment().setItemInMainHandDropChance(0);


                                                    revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
                                                    revenantHorror.setHealth(100);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 1.5);

                                                    ItemStack legs = ((LivingEntity) e).getEquipment().getLeggings();
                                                    ItemMeta meta = legs.getItemMeta();
                                                    meta.setDisplayName("1");
                                                    legs.setItemMeta(meta);
                                                    ((LivingEntity) e).getEquipment().setLeggings(legs);
                                                }
                                                if (((LivingEntity) e).getHealth() < maxHealth * 0.5 && Integer.parseInt(((LivingEntity) e).getEquipment().getLeggings().getItemMeta().getDisplayName()) < 2) {
                                                    e.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, e.getLocation(), 300, 0.5, 1, 0.5);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, 1);




                                                    LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                                    ((Zombie) revenantHorror).setBaby(false);


                                                    revenantHorror.setCustomNameVisible(true);
                                                    revenantHorror.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv150"+ChatColor.DARK_GRAY+"]"+ChatColor.RED + " Revenant Champion");
                                                    revenantHorror.getEquipment().setHelmet(new ItemStack(Material.AIR));

                                                    ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                    chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setChestplate(chestplate);
                                                    revenantHorror.getEquipment().setChestplateDropChance(0);

                                                    ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                                    leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setLeggings(leggings);
                                                    revenantHorror.getEquipment().setLeggingsDropChance(0);

                                                    ItemStack boots = new ItemStack(Material.IRON_BOOTS);

                                                    revenantHorror.getEquipment().setBoots(boots);
                                                    revenantHorror.getEquipment().setBootsDropChance(0);

                                                    ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD);
                                                    weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setItemInMainHand(weapon);
                                                    revenantHorror.getEquipment().setItemInMainHandDropChance(0);


                                                    revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(300);
                                                    revenantHorror.setHealth(300);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(40);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 1.5);

                                                    ItemStack legs = ((LivingEntity) e).getEquipment().getLeggings();
                                                    ItemMeta meta = legs.getItemMeta();
                                                    meta.setDisplayName("2");
                                                    legs.setItemMeta(meta);
                                                    ((LivingEntity) e).getEquipment().setLeggings(legs);
                                                }
                                            }
                                        }

                                        Location origin = e.getLocation();
                                        origin.setY(e.getLocation().getY() + 1);

                                        Location target1 = player.getLocation();
                                        target1.setY(player.getLocation().getY() + 1);
                                        Vector target = target1.toVector();
                                        origin.setDirection(target.subtract(origin.toVector()));
                                        Vector increase = origin.getDirection();
                                        for (int counter = 0; counter < (int) e.getLocation().distance(player.getLocation()); counter++) {
                                            Location loc = origin.add(increase);
                                            for (Player pp : Bukkit.getOnlinePlayers()) {

                                                PacketPlayOutWorldParticles heartPacket = new PacketPlayOutWorldParticles(Particles.HAPPY_VILLAGER, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);
                                                PacketPlayOutWorldParticles greenPacket = new PacketPlayOutWorldParticles(Particles.HEART, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);

                                                ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(greenPacket);
                                                ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(heartPacket);
                                            }
                                        }
                                    }
                                }
                            }
                            if (((LivingEntity) e).getEquipment().getChestplate().hasItemMeta()) {
                                if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().hasDisplayName()) {
                                    if (((LivingEntity) e).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant") && ((LivingEntity) e).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 9) {

                                        Player player = SlayerMenu.revenantHorrorPlayer.get(e);

                                        player.damage(4);


                                        for (Entity en : e.getNearbyEntities(3, 3, 3)) {
                                            if (en instanceof Player) {
                                                if (en.equals(player)) {
                                                    DefenseNerf.pestilence.put(player, 0.5f);

                                                    scheduleSyncDelayedTask(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            DefenseNerf.pestilence.put(player, 0f);
                                                        }
                                                    }, 40);
                                                }
                                            }
                                        }
                                        e.getLocation().getWorld().spawnParticle(Particle.SLIME, e.getLocation(), 700, 2, 1.5, 2);
                                        e.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e.getLocation(), 300, 2, 1.5, 2);


                                        DefenseNerf.oldLocation.putIfAbsent(player, player.getLocation());
                                        DefenseNerf.newLocation.put(player, player.getLocation());

                                        if (DefenseNerf.oldLocation.get(player).getBlockX() == DefenseNerf.newLocation.get(player).getBlockX() && DefenseNerf.oldLocation.get(player).getBlockZ() == DefenseNerf.newLocation.get(player).getBlockZ()) {
                                            DefenseNerf.antiStandStill.putIfAbsent(player, 0);
                                            int i = DefenseNerf.antiStandStill.get(player);
                                            i += 60;
                                            DefenseNerf.antiStandStill.put(player, i);
                                        } else if (DefenseNerf.oldLocation.get(player).getBlockX() != DefenseNerf.oldLocation.get(player).getBlockX() || DefenseNerf.oldLocation.get(player).getBlockZ() != DefenseNerf.newLocation.get(player).getBlockZ()) {
                                            DefenseNerf.antiStandStill.put(player, 0);
                                        }

                                        DefenseNerf.oldLocation.put(player, player.getLocation());


                                        if (DefenseNerf.antiStandStill.get(player) > 200) {
                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 2, 1);
                                            player.damage(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                                            player.setVelocity(player.getLocation().getDirection().multiply(10));
                                            player.getVelocity().setY(2);
                                            DefenseNerf.antiStandStill.put(player, 0);
                                        }

                                        DefenseNerf.antiNotHit.putIfAbsent((LivingEntity) e, 0);
                                        int antiNotInteger = DefenseNerf.antiNotHit.get(e);

                                        antiNotInteger += 60;

                                        DefenseNerf.antiNotHit.put((LivingEntity) e, antiNotInteger);

                                        double maxHealth = ((LivingEntity) e).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                                        if (antiNotInteger > 500) {
                                            if (((LivingEntity) e).getHealth() + 1050 < maxHealth) {
                                                ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 1050);
                                            } else if (((LivingEntity) e).getHealth() + 1050 > maxHealth) {
                                                ((LivingEntity) e).setHealth(maxHealth);
                                            }

                                            e.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, e.getLocation(), 300, 0.5, 0.8, 0.5);

                                            Location loc = e.getLocation();
                                                        loc.setY(loc.getY()+2);
                                                        ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(e.getWorld().getName()).spawn(loc, ArmorStand.class);

                                            as.setCustomName(ChatColor.GREEN+"Regenerating!");


                                            as.setCustomNameVisible(true);
                                            as.setVisible(false);
                                            as.setGravity(false);
                                            as.setMarker(true);


                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                @Override
                                                public void run() {
                                                    as.remove();
                                                }
                                            }, 40);
                                        }

                                        if (antiNotInteger > 400) {
                                            e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 500, 0.5, 0.8, 0.5);
                                            e.teleport(player.getLocation());
                                            e.getLocation().getWorld().spawnParticle(Particle.PORTAL, e.getLocation(), 300, 0.5, 0.8, 0.5);
                                            player.sendMessage(ChatColor.DARK_PURPLE+"The boss teleported to you using some really dark magic!");
                                        }


                                        if (((LivingEntity) e).getHealth() + 10 < maxHealth) {
                                            ((LivingEntity) e).setHealth(((LivingEntity) e).getHealth() + 10);
                                        } else if (((LivingEntity) e).getHealth() + 10 > maxHealth) {
                                            ((LivingEntity) e).setHealth(maxHealth);
                                        }

                                        int health = (int) Math.ceil(((LivingEntity) e).getHealth());

                                        String color = health > 1050 ? ChatColor.GREEN + "" : health <= 1050 && health > 525 ? ChatColor.YELLOW + "" : health <= 525 ? ChatColor.RED + "" : "";

                                        e.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv890"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/2100" + ChatColor.RED + "❤");

                                        if (((LivingEntity) e).getEquipment().getLeggings().hasItemMeta()) {
                                            if (((LivingEntity) e).getEquipment().getLeggings().getItemMeta().hasDisplayName()) {
                                                if (((LivingEntity) e).getHealth() < maxHealth * 0.75 && Integer.parseInt(((LivingEntity) e).getEquipment().getLeggings().getItemMeta().getDisplayName()) < 1) {
                                                    e.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, e.getLocation(), 300, 0.5, 1, 0.5);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, 1);

                                                    DefenseNerf.rottenRounds.put(player, true);
                                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            e.getLocation().getWorld().spawnParticle(Particle.CRIT_MAGIC, e.getLocation(), 20000, 9, 3, 9, 0);

                                                        }
                                                    }, 5);


                                                    LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                                    ((Zombie) revenantHorror).setBaby(false);


                                                    revenantHorror.setCustomNameVisible(true);
                                                    revenantHorror.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv70"+ChatColor.DARK_GRAY+"]"+ChatColor.RED + " Revenant Sycophant");

                                                    revenantHorror.getEquipment().setHelmet(new ItemStack(Material.AIR));

                                                    ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                    chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setChestplate(chestplate);
                                                    revenantHorror.getEquipment().setChestplateDropChance(0);

                                                    ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                                    leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setLeggings(leggings);
                                                    revenantHorror.getEquipment().setLeggingsDropChance(0);

                                                    ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);

                                                    revenantHorror.getEquipment().setBoots(boots);
                                                    revenantHorror.getEquipment().setBootsDropChance(0);

                                                    ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD);
                                                    weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setItemInMainHand(weapon);
                                                    revenantHorror.getEquipment().setItemInMainHandDropChance(0);


                                                    revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
                                                    revenantHorror.setHealth(100);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 1.5);

                                                    ItemStack legs = ((LivingEntity) e).getEquipment().getLeggings();
                                                    ItemMeta meta = legs.getItemMeta();
                                                    meta.setDisplayName("1");
                                                    legs.setItemMeta(meta);
                                                    ((LivingEntity) e).getEquipment().setLeggings(legs);
                                                }
                                                if (((LivingEntity) e).getHealth() < maxHealth * 0.5 && Integer.parseInt(((LivingEntity) e).getEquipment().getLeggings().getItemMeta().getDisplayName()) < 2) {
                                                    e.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, e.getLocation(), 300, 0.5, 1, 0.5);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, 1);



                                                    LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                                    ((Zombie) revenantHorror).setBaby(false);


                                                    revenantHorror.setCustomNameVisible(true);
                                                    revenantHorror.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv150"+ChatColor.DARK_GRAY+"]"+ChatColor.RED + " Revenant Champion");

                                                    revenantHorror.getEquipment().setHelmet(new ItemStack(Material.AIR));

                                                    ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                    chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setChestplate(chestplate);
                                                    revenantHorror.getEquipment().setChestplateDropChance(0);

                                                    ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                                    leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setLeggings(leggings);
                                                    revenantHorror.getEquipment().setLeggingsDropChance(0);

                                                    ItemStack boots = new ItemStack(Material.IRON_BOOTS);

                                                    revenantHorror.getEquipment().setBoots(boots);
                                                    revenantHorror.getEquipment().setBootsDropChance(0);

                                                    ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD);
                                                    weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setItemInMainHand(weapon);
                                                    revenantHorror.getEquipment().setItemInMainHandDropChance(0);


                                                    revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(300);
                                                    revenantHorror.setHealth(300);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(40);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 1.5);

                                                    ItemStack legs = ((LivingEntity) e).getEquipment().getLeggings();
                                                    ItemMeta meta = legs.getItemMeta();
                                                    meta.setDisplayName("2");
                                                    legs.setItemMeta(meta);
                                                    ((LivingEntity) e).getEquipment().setLeggings(legs);
                                                }
                                                if (((LivingEntity) e).getHealth() < maxHealth * 0.25 && Integer.parseInt(((LivingEntity) e).getEquipment().getLeggings().getItemMeta().getDisplayName()) < 3) {
                                                    e.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, e.getLocation(), 300, 0.5, 1, 0.5);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                                    player.playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, 1);


                                                    LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                                    ((Zombie) revenantHorror).setBaby(false);


                                                    revenantHorror.setCustomNameVisible(true);
                                                    revenantHorror.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv300"+ChatColor.DARK_GRAY+"]"+ChatColor.DARK_RED + " Deformed Revenant");

                                                    ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                    chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setChestplate(chestplate);
                                                    revenantHorror.getEquipment().setChestplateDropChance(0);

                                                    ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                                                    leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                                                    LeatherArmorMeta lmeta = (LeatherArmorMeta) leggings.getItemMeta();
                                                    lmeta.setUnbreakable(true);
                                                    lmeta.setColor(Color.fromRGB(255, 5, 5));
                                                    leggings.setItemMeta(lmeta);

                                                    revenantHorror.getEquipment().setLeggings(leggings);


                                                    revenantHorror.getEquipment().setLeggings(leggings);
                                                    revenantHorror.getEquipment().setLeggingsDropChance(0);

                                                    ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);

                                                    revenantHorror.getEquipment().setBoots(boots);
                                                    revenantHorror.getEquipment().setBootsDropChance(0);

                                                    ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
                                                    helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                                                    LeatherArmorMeta hmeta = (LeatherArmorMeta) helmet.getItemMeta();
                                                    hmeta.setUnbreakable(true);
                                                    hmeta.setColor(Color.fromRGB(255, 5, 5));
                                                    helmet.setItemMeta(hmeta);

                                                    revenantHorror.getEquipment().setHelmet(helmet);


                                                    ItemStack weapon = new ItemStack(Material.IRON_SWORD);
                                                    weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                                    revenantHorror.getEquipment().setItemInMainHand(weapon);
                                                    revenantHorror.getEquipment().setItemInMainHandDropChance(0);


                                                    revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(700);
                                                    revenantHorror.setHealth(700);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(60);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);
                                                    revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                                    revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 1.8);

                                                    ItemStack legs = ((LivingEntity) e).getEquipment().getLeggings();
                                                    ItemMeta meta = legs.getItemMeta();
                                                    meta.setDisplayName("3");
                                                    legs.setItemMeta(meta);
                                                    ((LivingEntity) e).getEquipment().setLeggings(legs);
                                                }
                                            }
                                        }

                                        Location origin = e.getLocation();
                                        origin.setY(e.getLocation().getY() + 1);

                                        Location target1 = player.getLocation();
                                        target1.setY(player.getLocation().getY() + 1);
                                        Vector target = target1.toVector();
                                        origin.setDirection(target.subtract(origin.toVector()));
                                        Vector increase = origin.getDirection();
                                        for (int counter = 0; counter < (int) e.getLocation().distance(player.getLocation()); counter++) {
                                            Location loc = origin.add(increase);
                                            for (Player pp : Bukkit.getOnlinePlayers()) {

                                                PacketPlayOutWorldParticles heartPacket = new PacketPlayOutWorldParticles(Particles.HAPPY_VILLAGER, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);
                                                PacketPlayOutWorldParticles greenPacket = new PacketPlayOutWorldParticles(Particles.HEART, false, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, (float) 0, 2);

                                                ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(greenPacket);
                                                ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(heartPacket);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 5, 60);
    }
}