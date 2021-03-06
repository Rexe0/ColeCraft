package com.Rexe0;

import com.Rexe0.Events.CustomBossDeathEvent;
import com.Rexe0.Events.CustomDamageEvent;
import com.Rexe0.Events.CustomLootDrop;
import com.Rexe0.Events.CustomPlayerDeathEvent;
import com.Rexe0.Items.Armor.Pieces.SpiderBoots;
import com.Rexe0.Items.CustomItem;
import com.Rexe0.Items.Materials.CustomMaterial;
import com.Rexe0.Items.Materials.EmberFragment;
import com.Rexe0.Items.Swords.Epic.EmberRod;
import com.Rexe0.Items.Tools.JungleAxe;
import com.Rexe0.Mobs.CustomMob;
import com.Rexe0.Slayers.StatsCommand;
import com.Rexe0.Slayers.sendMessageToSelf;
import com.Rexe0.Slayers.SlayerMenu;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.ConeEffect;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.Vector;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;


@SuppressWarnings( "deprecation" )
public class DefenseNerf implements Listener {

    static HashMap<Player, Integer> antiStandStill = new HashMap<>();
    public static HashMap<LivingEntity, Integer> antiNotHit = new HashMap<>();
    static HashMap<Player, Location> oldLocation = new HashMap<>();
    static HashMap<Player, Location> newLocation = new HashMap<>();
    public static HashMap<Player, Float> pestilence = new HashMap<>();
    static HashMap<Player, Boolean> rottenRounds = new HashMap<>();
    public static HashMap<Player, ArrayList<Item>> playerSpecificPickup = new HashMap<>();
    public static HashMap<LivingEntity, Player> lastKilledEntity = new HashMap<>();
    public static HashMap<Player, Float> magmaBossDamage = new HashMap<>();
    static HashMap<Player, Integer> totalDefense = new HashMap<>();
    public static HashMap<Entity, Integer> thunderlordHits = new HashMap<Entity, Integer>();


    public static boolean isWithinEntityBoundingBox(Location location, Entity entity) {

        AxisAlignedBB bb = ((CraftEntity) entity).getHandle().getBoundingBox();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();


        return
                x >= bb.minX &&
                        x <= bb.maxX &&
                        y >= bb.minY &&
                        y <= bb.maxY &&
                        z >= bb.minZ &&
                        z <= bb.maxZ;

    }

    public static Vector rotateYAxis(Vector dir, double angleD) {
        double angleR = Math.toRadians(angleD);
        double x = dir.getX();
        double z = dir.getZ();
        double cos = Math.cos(angleR);
        double sin = Math.sin(angleR);
        return (new Vector(x*cos+z*(-sin), dir.getY(), x*sin+z*cos));
    }

    public static boolean isFullSet(Player player, String helmet, String chestplate, String leggings, String boot) {
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemID");


        PlayerInventory inv = player.getInventory();
        ItemStack helm = inv.getHelmet();
        ItemStack chest = inv.getChestplate();
        ItemStack boots = inv.getBoots();
        ItemStack pants = inv.getLeggings();


        ItemMeta helmMeta = null;

        ItemMeta chestMeta = null;

        ItemMeta pantsMeta = null;

        ItemMeta bootsMeta = null;

        if (helm != null) {
            if (helm.hasItemMeta()) {
                helmMeta = helm.getItemMeta();
            }
        }

        if (chest != null) {
            if (chest.hasItemMeta()) {
                chestMeta = chest.getItemMeta();
            }
        }

        if (pants != null) {
            if (pants.hasItemMeta()) {
                pantsMeta = pants.getItemMeta();
            }
        }

        if (boots != null) {
            if (boots.hasItemMeta()) {
                bootsMeta = boots.getItemMeta();
            }
        }

        PersistentDataContainer helmcontainer = null;
        PersistentDataContainer chestcontainer = null;
        PersistentDataContainer pantscontainer = null;
        PersistentDataContainer bootscontainer = null;
        if (helmMeta != null) {
            helmcontainer = helmMeta.getPersistentDataContainer();
        }
        if (chestMeta != null) {
            chestcontainer = chestMeta.getPersistentDataContainer();
        }

        if (pantsMeta != null) {
            pantscontainer = pantsMeta.getPersistentDataContainer();
        }

        if (bootsMeta != null) {
            bootscontainer = bootsMeta.getPersistentDataContainer();
        }

        String helmfoundValue = null;
        String chestfoundValue = null;
        String pantsfoundValue = null;
        String bootsfoundValue = null;


        if (helmcontainer != null) {
            if (helmcontainer.has(key, PersistentDataType.STRING)) {
                helmfoundValue = helmcontainer.get(key, PersistentDataType.STRING);
            }
        }

        if (chestcontainer != null) {
            if (chestcontainer.has(key, PersistentDataType.STRING)) {
                chestfoundValue = chestcontainer.get(key, PersistentDataType.STRING);
            }
        }

        if (pantscontainer != null) {
            if (pantscontainer.has(key, PersistentDataType.STRING)) {
                pantsfoundValue = pantscontainer.get(key, PersistentDataType.STRING);
            }
        }
        if (bootscontainer != null) {
            if (bootscontainer.has(key, PersistentDataType.STRING)) {
                bootsfoundValue = bootscontainer.get(key, PersistentDataType.STRING);
            }
        }

        boolean helmetAny = helmet == null;
        boolean chestAny = chestplate == null;
        boolean legsAny = leggings == null;
        boolean bootsAny = boot == null;

        boolean correctHelm = false;
        boolean correctChest = false;
        boolean correctLegs = false;
        boolean correctFeet = false;

        if (!helmetAny) {
            if (helmfoundValue != null) {
                if (helmfoundValue.equals(helmet)) {
                    correctHelm = true;
                }
            }
        } else {
            correctHelm = true;
        }

        if (!chestAny) {
            if (chestfoundValue != null) {
                if (chestfoundValue.equals(chestplate)) {
                    correctChest = true;
                }
            }
        } else {
            correctChest = true;
        }
        if (!legsAny) {
            if (pantsfoundValue != null) {
                if (pantsfoundValue.equals(leggings)) {
                    correctLegs = true;
                }
            }
        } else {
            correctLegs = true;
        }
        if (!bootsAny) {

            if (bootsfoundValue != null) {
                if (bootsfoundValue.equals(boot)) {
                    correctFeet = true;
                }
            }
        } else {
            correctFeet = true;
        }

        if (correctChest && correctFeet && correctHelm && correctLegs) {
            return true;
        } else {
            return false;
        }


    }

    public static String getItemID(ItemStack item) {
        if (item == null) return null;
        if (!item.hasItemMeta()) return null;
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemID");
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();


        String foundValue = null;
        if (container.has(key, PersistentDataType.STRING)) {
            foundValue = container.get(key, PersistentDataType.STRING);
        }

        return foundValue;
    }

    public static String getItemRarity(ItemStack item) {
        if (item == null) return null;
        if (!item.hasItemMeta()) return null;
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "rarity");
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();


        String foundValue = null;
        if (container.has(key, PersistentDataType.STRING)) {
            foundValue = container.get(key, PersistentDataType.STRING);
        }

        return foundValue;
    }

    public static String getItemType(ItemStack item) {
        if (item == null) return null;
        if (!item.hasItemMeta()) return null;
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemType");
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();


        String foundValue = null;
        if (container.has(key, PersistentDataType.STRING)) {
            foundValue = container.get(key, PersistentDataType.STRING);
        }

        return foundValue;
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent e) {

        ItemStack heldItem = e.getBow();

        Arrow arrow = null;

        if (e.getProjectile() instanceof Arrow) {
            arrow = (Arrow) e.getProjectile();
        }

        String foundValue = getItemID(heldItem);





        if (foundValue != null) {
            if (foundValue.equals("MOSQUITO_BOW")) {
                arrow.setCustomName("MOSQUITO");
                if (e.getEntity() instanceof Player) {
                    Player player = (Player) e.getEntity();
                    if (player.isSneaking() && e.getForce() == 1) {
                        arrow.setCustomName("MOSQUITO_1");
                    }
                }
            }

            if (foundValue.equals("SCORPION_BOW")) {
                arrow.setCustomName("SCORPION");
                if (e.getEntity() instanceof Player) {
                    Player player = (Player) e.getEntity();
                    if (player.isSneaking() && e.getForce() == 1) {
                        arrow.setCustomName("SCORPION_1");
                    }
                }
            }

            if (foundValue.equals("RUNAANS_BOW")) {

                arrow.setCustomName("RUNAAN");


                boolean isIngnited = arrow.getFireTicks() > 0;
                Arrow arrow1 = e.getEntity().getWorld().spawn(e.getEntity().getEyeLocation(), Arrow.class);



                arrow1.setFireTicks(isIngnited ? 10000 : 0);
                arrow1.setShooter(e.getEntity());
                arrow1.setCustomName("RUNAAN");



                Arrow arrow2 = e.getEntity().getWorld().spawn(e.getEntity().getEyeLocation(), Arrow.class);
                arrow2.setShooter(e.getEntity());
                arrow2.setCustomName("RUNAAN");


                arrow2.setFireTicks(isIngnited ? 10000 : 0);

                Arrow finalArrow = arrow;
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        Vector finalVelocity = rotateYAxis(finalArrow.getVelocity(), 9);
                        finalVelocity.setY(finalArrow.getVelocity().getY());

                        Vector finalVelocity1 = rotateYAxis(finalArrow.getVelocity(), -9);
                        finalVelocity1.setY(finalArrow.getVelocity().getY());


                        arrow1.setVelocity(finalVelocity);
                        arrow2.setVelocity(finalVelocity1);
                    }
                }, 1);
            } else if (foundValue.equals("HURRICANE_BOW")) {

                arrow.setCustomName("HURRICANE");



                boolean isIngnited = arrow.getFireTicks() > 0;

                for (byte i = 0; i < 4; i++) {
                    Arrow arrow1 = e.getEntity().getWorld().spawn(e.getEntity().getEyeLocation(), Arrow.class);


                    arrow1.setFireTicks(isIngnited ? 10000 : 0);
                    arrow1.setShooter(e.getEntity());
                    arrow1.setCustomName("HURRICANE");
                    Arrow finalArrow = arrow;
                    byte finalI = i;
                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            int angleRotated = finalI == 0 ? 12 : finalI == 1 ? 6 : finalI == 2 ? -6 : finalI == 3 ? -12 : 0;
                            Vector finalVelocity = rotateYAxis(finalArrow.getVelocity(), angleRotated);
                            finalVelocity.setY(finalArrow.getVelocity().getY());


                            arrow1.setVelocity(finalVelocity);
                        }
                    }, 1);

                }



            }
        }
    }


    @EventHandler
    public void onDeathFromDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Bukkit.getServer().getPluginManager().callEvent(new CustomDamageEvent(null, e.getEntity(), CustomDamageEvent.DamageType.NORMAL, CustomDamageEvent.DamageSource.FALL_DAMAGE, e.getDamage()));

        }

        if (!(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK ||  e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
            if (e.getEntity() instanceof Player) {
                if (((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0) {
                    CustomPlayerDeathEvent event = new CustomPlayerDeathEvent(e.getEntity(), CustomPlayerDeathEvent.DeathReason.ENVIRONMENT, null);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    e.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void onDeath(EntityDeathEvent e) {



        e.getDrops().clear();



        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

        PersistentDataContainer container = e.getEntity().getPersistentDataContainer();


        NamespacedKey key1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

        PersistentDataContainer container1 = e.getEntity().getPersistentDataContainer();

        String foundValue = null;
        if (container.has(key, PersistentDataType.STRING)) {
            foundValue = container.get(key, PersistentDataType.STRING);
        }

        Integer foundValue1 = null;
        if (container1.has(key1, PersistentDataType.INTEGER)) {
            foundValue1 = container.get(key1, PersistentDataType.INTEGER);
        }






        if (foundValue != null) {




            if (lastKilledEntity.get(e.getEntity()) != null) {

                Player player = lastKilledEntity.get(e.getEntity());

                if (foundValue1 != null) {

                    e.setDroppedExp(foundValue1/2);

                    NumberFormat numberFormat = NumberFormat.getInstance();

                    numberFormat.setGroupingUsed(true);





                    float combatXp = ColeCrafterSlayers.getSkillXP(player, "combat");
                    float combatLevel = ColeCrafterSlayers.getSkillLevel(player, "combat");
                    ColeCrafterSlayers.setSkillXP(player,"combat", combatXp+foundValue1);

                    String combatXpmessage = "§3+"+foundValue1+" Combat ("+numberFormat.format(combatXp)+"/"+numberFormat.format(combatLevel == 50 ? Math.ceil(100*(Math.pow(1.26, 48))+300*50) : Math.ceil(100*(Math.pow(1.26, combatLevel)))+300*(combatLevel))+")";
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 2);

                    PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + combatXpmessage + "\"}"), ChatMessageType.GAME_INFO, UUID.randomUUID());

                    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

                    ColeCrafterSlayers.displayActionbar.put(player, false);
                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            ColeCrafterSlayers.displayActionbar.put(player, true);
                        }
                    }, 40);



                }



                if (foundValue.equals("SPLITTER_SPIDER")) {
                    CustomMob.spawnMob("SILVERFISH", e.getEntity().getLocation());
                    CustomMob.spawnMob("SILVERFISH", e.getEntity().getLocation());
                }

                boolean isSpider = false;
                boolean isSkele = false;
                boolean isZombie = false;
                if (foundValue.contains("_")) {
                    for (String str : foundValue.split("_")) {
                        switch (str) {
                            case "SPIDER":
                                isSpider = true;
                                break;
                            case "SKELETON":
                                isSkele = true;
                                break;
                            case "ZOMBIE":
                                isZombie = true;
                                break;
                        }
                    }
                } else {

                    switch (foundValue) {
                        case "SPIDER":
                            isSpider = true;
                            break;
                        case "SKELETON":
                            isSkele = true;
                            break;
                        case "ZOMBIE":
                            isZombie = true;
                            break;
                    }
                }


                if (isSpider) {
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 1, CustomMaterial.getItemClass("STRING"), ChatColor.WHITE+"String", CustomLootDrop.Rarity.GUARANTEED, e.getEntity(), 1, 3));
                }

                if (isSkele) {
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 1, CustomMaterial.getItemClass("BONE"), ChatColor.WHITE+"Bone", CustomLootDrop.Rarity.GUARANTEED, e.getEntity(), 1, 2));
                }

                if (isZombie) {
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 1, CustomMaterial.getItemClass("ROTTEN_FLESH"), ChatColor.WHITE+"Rotten Flesh", CustomLootDrop.Rarity.GUARANTEED, e.getEntity(), 0, 2));
                }

                if (foundValue.equals("WITHERED_ARMOR")) {
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 1, CustomMaterial.getItemClass("NETHERITE_SCRAP"), ChatColor.BLUE+"Netherite Scrap", CustomLootDrop.Rarity.GUARANTEED, e.getEntity(), 1, 1));

                }

                if (foundValue.equals("MAGMA_CUBE")) {
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 1, CustomMaterial.getItemClass("MAGMA_CREAM"), ChatColor.WHITE+"Magma Cream", CustomLootDrop.Rarity.GUARANTEED, e.getEntity(), 1, 3));

                }

                if (foundValue.equals("BLAZE")) {
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 1, CustomMaterial.getItemClass("BLAZE_ROD"), ChatColor.WHITE+"Blaze Rod", CustomLootDrop.Rarity.GUARANTEED, e.getEntity(), 1, 2));

                }

                if (foundValue.equals("INFERNO")) {
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 1, CustomMaterial.getItemClass("BLAZE_ROD"), ChatColor.WHITE+"Blaze Rod", CustomLootDrop.Rarity.GUARANTEED, e.getEntity(), 3, 5));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 20, CustomMaterial.getItemClass("ENCHANTED_BLAZE_POWDER"), ChatColor.GREEN+"Enchanted Blaze Powder", CustomLootDrop.Rarity.UNCOMMON, e.getEntity(), 1, 1));
                }

                if (foundValue.equals("MAGMA_CUBE_BOSS")) {

                    HashMap<Player, Integer> spawner = new HashMap<>();
                    spawner.put(SlayerMenu.magmaBossSummoner, 1);
                    Bukkit.getServer().getPluginManager().callEvent(new CustomBossDeathEvent(player, CustomBossDeathEvent.BossType.MAGMA_CUBE_BOSS, e.getEntity(), spawner ,magmaBossDamage));


                    SlayerMenu.magmaBossSummoner = null;

                    magmaBossDamage.clear();
                }

                if (foundValue.equals("LAPIS_ZOMBIE")) {

                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("LAPIS_HELMET"), ChatColor.GREEN+"Lapis Armor Helmet", CustomLootDrop.Rarity.RARE, e.getEntity(), 1, 1));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("LAPIS_CHESTPLATE"), ChatColor.GREEN+"Lapis Armor Chestplate", CustomLootDrop.Rarity.RARE, e.getEntity(), 1, 1));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("LAPIS_LEGGINGS"), ChatColor.GREEN+"Lapis Armor Leggings", CustomLootDrop.Rarity.RARE, e.getEntity(), 1, 1));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("LAPIS_BOOTS"), ChatColor.GREEN+"Lapis Armor Boots", CustomLootDrop.Rarity.RARE, e.getEntity(), 1, 1));
                }

                if (foundValue.equals("MINER_ZOMBIE")) {

                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("MINER_HELMET"), ChatColor.BLUE+"Miner Armor Helmet", CustomLootDrop.Rarity.RARE, e.getEntity(), 1, 1));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("MINER_CHESTPLATE"), ChatColor.BLUE+"Miner Armor Chestplate", CustomLootDrop.Rarity.RARE, e.getEntity(), 1, 1));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("MINER_LEGGINGS"), ChatColor.BLUE+"Miner Armor Leggings", CustomLootDrop.Rarity.RARE, e.getEntity(), 1, 1));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("MINER_BOOTS"), ChatColor.BLUE+"Miner Armor Boots", CustomLootDrop.Rarity.RARE, e.getEntity(), 1, 1));
                }

                if (foundValue.equals("STRONG_MINER_ZOMBIE")) {
                    ItemStack item = CustomItem.getItemClass("MINER_CHESTPLATE");
                    item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 300, item, ChatColor.BLUE+"Miner Armor Chestplate", CustomLootDrop.Rarity.VERY_RARE, e.getEntity(), 1, 1));

                    item = CustomItem.getItemClass("MINER_HELMET");
                    item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 300, item, ChatColor.BLUE+"Miner Armor Helmet", CustomLootDrop.Rarity.VERY_RARE, e.getEntity(), 1, 1));

                    item = CustomItem.getItemClass("MINER_LEGGINGS");
                    item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 300, item, ChatColor.BLUE+"Miner Armor Leggings", CustomLootDrop.Rarity.VERY_RARE, e.getEntity(), 1, 1));

                    item = CustomItem.getItemClass("MINER_BOOTS");
                    item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 300, item, ChatColor.BLUE+"Miner Armor Boots", CustomLootDrop.Rarity.VERY_RARE, e.getEntity(), 1, 1));
                }

                if (foundValue.equals("ZOMBIE_PIGMAN")) {

                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 20, CustomItem.getItemClass("FLAMING_SWORD"), ChatColor.GREEN+"Flaming Sword", CustomLootDrop.Rarity.RARE, e.getEntity(), 1, 1));
                }
            }
        }


        if (e.getEntity().getType() == EntityType.ZOMBIE) {
            if (((LivingEntity) e.getEntity()).getEquipment().getChestplate() != null) {
                if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 4 && ((LivingEntity) e.getEntity()).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) < 10) {
                    Player player = SlayerMenu.revenantHorrorPlayer.get(e.getEntity());
                    SlayerMenu.revenantHorrorPlayer.put(e.getEntity(), null);

                    rottenRounds.put(player, false);


                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 0.6f);
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + " NICE! SLAYER BOSS SLAIN!\n" + ChatColor.DARK_PURPLE + " ⋙ " + ChatColor.GRAY + "Talk to Maddox to claim your Zombie Slayer XP!");

                    byte slayerTier = (byte) ((LivingEntity) e.getEntity()).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);


                    Item item = e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.ROTTEN_FLESH, 1));
                    if (slayerTier > 4 && slayerTier < 9) {
                        item.setItemStack(CustomMaterial.getItemClass("REVENANT_FLESH"));
                    } else if (slayerTier == 9) {
                        item.setItemStack(CustomMaterial.getItemClass("REVENANT_VISCERA"));
                    }

                    Random rand = new Random();

                    int n = 0;

                    switch (slayerTier) {
                        case 5:
                            n = rand.nextInt(3);
                            n += 1;
                            break;
                        case 6:
                            n = rand.nextInt(9);
                            n += 9;
                            break;
                        case 7:
                            n = rand.nextInt(20);
                            n += 30;
                            break;
                        case 8:
                            n = rand.nextInt(14);
                            n += 50;
                            break;
                        case 9:
                            n = 1;
                    }

                    item.getItemStack().setAmount(n);


                    item.setPickupDelay(10);



                    ArrayList<Item> itemDrops = new ArrayList<>();
                    if (slayerTier == 8) {
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 7, 13000, CustomItem.getItemClass("SCYTHE_BLADE"), ChatColor.GOLD+"Scythe Blade", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity(), 1, 1));
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 42, 13000, CustomItem.getItemClass("ROTTED_TOOTH"), ChatColor.DARK_PURPLE+"Rotted Tooth", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity(), 1, 1));
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 21, 13000, CustomItem.getItemClass("BEHEADED_HORROR"), ChatColor.DARK_PURPLE+"Beheaded Horror", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity(), 1, 1));


                    }
                    if (slayerTier == 9) {
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 10, 13000, CustomItem.getItemClass("SCYTHE_BLADE"), ChatColor.GOLD+"Scythe Blade", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity(), 1, 1));
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 60, 13000, CustomItem.getItemClass("ROTTED_TOOTH"), ChatColor.DARK_PURPLE+"Rotted Tooth", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity(), 1, 1));
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 30, 13000, CustomItem.getItemClass("BEHEADED_HORROR"), ChatColor.DARK_PURPLE+"Beheaded Horror", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity(), 1, 1));

                    }

                }
            }
        }
    }
























    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        Bukkit.getServer().getPluginManager().callEvent(new CustomDamageEvent(e.getDamager(), e.getEntity(), CustomDamageEvent.DamageType.NORMAL, CustomDamageEvent.DamageSource.ENTITY, e.getDamage()));

        e.setDamage(0);

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
//        ItemStack heldItem = player.getItemInHand();
//
//
//        if (!heldItem.hasItemMeta()) return;
//        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemType");
//        ItemMeta itemMeta = heldItem.getItemMeta();
//        if (itemMeta == null) return;
//        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
//
//        String foundValue = null;
//        if(container.has(key, PersistentDataType.STRING)) {
//            foundValue = container.get(key, PersistentDataType.STRING);
//        }
//
//        if (foundValue == null) return;
//        if (foundValue.equals("HELMET") || foundValue.equals("MATERIAL") || foundValue.equals("ACCESSORY")) {
//            e.setCancelled(true);
//        }

        sendMessageToSelf.canBuild.putIfAbsent(player, false);
        if (!sendMessageToSelf.canBuild.get(player)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();



        boolean telekinesis = false;
        for (ItemStack item : player.getInventory().getContents()) {
            String foundValue1 = getItemID(item);

            if (foundValue1 != null) {
                if (foundValue1.equals("TELEKINESIS_TALISMAN")) {
                    telekinesis = true;

                }
            }

        }


        int miningXPAdd = 0;
        int farmingXPAdd = 0;
        int forgagingXPAdd = 0;

        sendMessageToSelf.canBuild.putIfAbsent(player, false);
        if (!sendMessageToSelf.canBuild.get(player)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
            return;
        }



        if (e.isDropItems()) {
            if (ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "Coal Mine") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.GOLD + "Gold Mine") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "Deep Caverns")) {
                if (block.getType() == Material.STONE) {
                    block.setType(Material.BEDROCK);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("COBBLESTONE"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("COBBLESTONE"));
                    }

                    miningXPAdd = 1;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.STONE);
                        }
                    }, 40);
                }

                if (block.getType() == Material.COBBLESTONE) {
                    block.setType(Material.BEDROCK);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("COBBLESTONE"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("COBBLESTONE"));
                    }

                    miningXPAdd = 1;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.COBBLESTONE);
                        }
                    }, 60);
                }

                if (block.getType() == Material.ICE) {
                    block.setType(Material.WATER);

                    miningXPAdd = 1;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.ICE);
                        }
                    }, 600);
                }


                if (block.getType() == Material.IRON_ORE) {
                    block.setType(Material.BEDROCK);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("IRON_INGOT"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("IRON_INGOT"));
                    }

                    miningXPAdd = 5;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.IRON_ORE);
                        }
                    }, 80);
                }

                if (block.getType() == Material.EMERALD_ORE) {
                    block.setType(Material.BEDROCK);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("EMERALD"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("EMERALD"));
                    }

                    miningXPAdd = 9;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.IRON_ORE);
                        }
                    }, 80);
                }


                if (block.getType() == Material.COAL_ORE) {
                    block.setType(Material.BEDROCK);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("COAL"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("COAL"));
                    }

                    miningXPAdd = 3;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.COAL_ORE);
                        }
                    }, 80);
                }

                if (block.getType() == Material.DIAMOND_ORE) {
                    block.setType(Material.BEDROCK);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("DIAMOND"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("DIAMOND"));
                    }

                    miningXPAdd = 10;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.DIAMOND_ORE);
                        }
                    }, 80);
                }

                if (block.getType() == Material.GOLD_ORE) {
                    block.setType(Material.BEDROCK);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("GOLD_INGOT"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("GOLD_INGOT"));
                    }

                    miningXPAdd = 6;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.GOLD_ORE);
                        }
                    }, 80);
                }

                if (block.getType() == Material.LAPIS_ORE) {
                    block.setType(Material.BEDROCK);
                    ItemStack item = CustomMaterial.getItemClass("LAPIS_LAZULI");
                    item.setAmount(3);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), item);
                    } else {
                        player.getInventory().addItem(item);
                    }

                    miningXPAdd = 7;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.LAPIS_ORE);
                        }
                    }, 80);
                }

                if (block.getType() == Material.REDSTONE_ORE) {
                    block.setType(Material.BEDROCK);
                    ItemStack item = CustomMaterial.getItemClass("REDSTONE_DUST");
                    item.setAmount(4);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), item);
                    } else {
                        player.getInventory().addItem(item);
                    }

                    miningXPAdd = 7;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.REDSTONE_ORE);
                        }
                    }, 80);
                }

                if (block.getType() == Material.DIAMOND_BLOCK) {
                    block.setType(Material.BEDROCK);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("DIAMOND_BLOCK"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("DIAMOND_BLOCK"));
                    }

                    miningXPAdd = 15;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.DIAMOND_BLOCK);
                        }
                    }, 140);
                }

                if (block.getType() == Material.OBSIDIAN) {
                    block.setType(Material.BEDROCK);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("OBSIDIAN"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("OBSIDIAN"));
                    }

                    miningXPAdd = 20;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(Material.OBSIDIAN);
                        }
                    }, 140);
                }
            }


            if (ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "Forest")) {
                if (block.getType() == Material.OAK_LOG || block.getType() == Material.BIRCH_LOG || block.getType() == Material.SPRUCE_LOG || block.getType() == Material.JUNGLE_LOG || block.getType() == Material.DARK_OAK_LOG || block.getType() == Material.ACACIA_LOG ||
                        block.getType() == Material.OAK_WOOD || block.getType() == Material.BIRCH_WOOD || block.getType() == Material.SPRUCE_WOOD || block.getType() == Material.JUNGLE_WOOD || block.getType() == Material.DARK_OAK_WOOD || block.getType() == Material.ACACIA_WOOD) {
                    BlockData data = block.getBlockData();

                    ItemStack item = player.getItemInHand();
                    if (item != null) {
                        if (item.hasItemMeta()) {
                            ItemMeta itemMeta1 = item.getItemMeta();
                            if (itemMeta1 != null) {
                                String foundValue1 = getItemID(item);
                                boolean breakLoop = false;

                                if (foundValue1 != null) {
                                    if (foundValue1.equals("JUNGLE_AXE")) {
                                        JungleAxe.jungleAxeLimit.putIfAbsent(player, 0);

                                        if (JungleAxe.jungleAxeLimit.get(player) < 10) {
                                            for (int i = -1; i < 2; i++) {
                                                for (int j = -1; j < 2; j++) {
                                                    for (int k = -1; k < 2; k++) {
                                                        if (JungleAxe.jungleAxeLimit.get(player) < 10) {
                                                            Block relative = block.getRelative(i, j, k);
                                                            if (relative.getType() == Material.OAK_LOG || relative.getType() == Material.BIRCH_LOG || relative.getType() == Material.SPRUCE_LOG || relative.getType() == Material.JUNGLE_LOG || relative.getType() == Material.DARK_OAK_LOG || relative.getType() == Material.ACACIA_LOG ||
                                                                    relative.getType() == Material.OAK_WOOD || relative.getType() == Material.BIRCH_WOOD || relative.getType() == Material.SPRUCE_WOOD || relative.getType() == Material.JUNGLE_WOOD || relative.getType() == Material.DARK_OAK_WOOD || relative.getType() == Material.ACACIA_WOOD) {

                                                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Bukkit.getServer().getPluginManager().callEvent(new BlockBreakEvent(relative, player));


                                                                    }
                                                                }, 1);
                                                                JungleAxe.jungleAxeLimit.put(player, JungleAxe.jungleAxeLimit.get(player) + 1);


                                                            }
                                                        } else {
                                                            breakLoop = true;
                                                            JungleAxe.jungleAxeLimit.put(player, 0);
                                                            break;

                                                        }
                                                    }
                                                    if (breakLoop) break;
                                                }
                                                if (breakLoop) break;
                                            }
                                        } else {
                                            JungleAxe.jungleAxeLimit.put(player, 0);
                                        }
                                    }

                                    if (foundValue1.equals("TREECAPITATOR")) {
                                        JungleAxe.jungleAxeLimit.putIfAbsent(player, 0);

                                        if (JungleAxe.jungleAxeLimit.get(player) < 35) {
                                            for (int i = -1; i < 2; i++) {
                                                for (int j = -1; j < 2; j++) {
                                                    for (int k = -1; k < 2; k++) {
                                                        if (JungleAxe.jungleAxeLimit.get(player) < 35) {
                                                            Block relative = block.getRelative(i, j, k);
                                                            if (relative.getType() == Material.OAK_LOG || relative.getType() == Material.BIRCH_LOG || relative.getType() == Material.SPRUCE_LOG || relative.getType() == Material.JUNGLE_LOG || relative.getType() == Material.DARK_OAK_LOG || relative.getType() == Material.ACACIA_LOG ||
                                                                    relative.getType() == Material.OAK_WOOD || relative.getType() == Material.BIRCH_WOOD || relative.getType() == Material.SPRUCE_WOOD || relative.getType() == Material.JUNGLE_WOOD || relative.getType() == Material.DARK_OAK_WOOD || relative.getType() == Material.ACACIA_WOOD) {

                                                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Bukkit.getServer().getPluginManager().callEvent(new BlockBreakEvent(relative, player));


                                                                    }
                                                                }, 1);
                                                                JungleAxe.jungleAxeLimit.put(player, JungleAxe.jungleAxeLimit.get(player) + 1);


                                                            }
                                                        } else {
                                                            breakLoop = true;
                                                            JungleAxe.jungleAxeLimit.put(player, 0);
                                                            break;
                                                        }
                                                    }
                                                    if (breakLoop) break;
                                                }
                                                if (breakLoop) break;
                                            }
                                        } else {
                                            JungleAxe.jungleAxeLimit.put(player, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }


                    Material material = block.getType();

                    block.setType(Material.AIR);
                    if (!telekinesis) {
                        block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("OAK_LOG"));
                    } else {
                        player.getInventory().addItem(CustomMaterial.getItemClass("OAK_LOG"));
                    }


                    forgagingXPAdd = 6;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(material);
                            block.setBlockData(data);
                        }
                    }, 140);
                }
            }

            if (ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "Farm") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "Mountain") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "The Barn") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "Mushroom Desert")) {
                if (block.getType() == Material.CARVED_PUMPKIN || block.getType() == Material.MELON) {
                    BlockData data = block.getBlockData();

                    Material material = block.getType();

                    block.setType(Material.AIR);

                    if (block.getType() == Material.CARVED_PUMPKIN) {
                        if (!telekinesis) {
                            block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("PUMPKIN"));
                        } else {
                            player.getInventory().addItem(CustomMaterial.getItemClass("PUMPKIN"));
                        }

                    }

                    if (block.getType() == Material.MELON) {
                        ItemStack item = CustomMaterial.getItemClass("MELON_SLICE");
                        item.setAmount(3);
                        if (!telekinesis) {
                            block.getWorld().dropItem(block.getLocation(), item);
                        } else {
                            player.getInventory().addItem(item);
                        }

                    }

                    farmingXPAdd = 4;

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            block.setType(material);
                            block.setBlockData(data);
                        }
                    }, 200);
                }

                if (ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "Farm") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "Mountain") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "The Barn") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA + "Mushroom Desert")) {
                    if (block.getType() == Material.MUSHROOM_STEM || block.getType() == Material.BROWN_MUSHROOM_BLOCK || block.getType() == Material.RED_MUSHROOM_BLOCK) {
                        BlockData data = block.getBlockData();

                        Material material = block.getType();

                        block.setType(Material.AIR);


                        if (!telekinesis) {
                            block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("MUSHROOM"));
                        } else {
                            player.getInventory().addItem(CustomMaterial.getItemClass("MUSHROOM"));
                        }


                        farmingXPAdd = 3;

                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                            @Override
                            public void run() {
                                block.setType(material);
                                block.setBlockData(data);
                            }
                        }, 200);
                    }


                    if (block.getType() == Material.WHEAT) {
                        org.bukkit.block.data.Ageable ageable = (org.bukkit.block.data.Ageable) block.getBlockData();
                        if (ageable.getAge() == 7) {
                            block.setType(Material.AIR);
                            if (!telekinesis) {
                                block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("WHEAT"));
                            } else {
                                player.getInventory().addItem(CustomMaterial.getItemClass("WHEAT"));
                            }

                            farmingXPAdd = 1;

                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                @Override
                                public void run() {
                                    block.setType(Material.WHEAT);
                                    ageable.setAge(7);
                                    block.setBlockData(ageable);
                                }
                            }, 80);
                        }
                    }

                    if (block.getType() == Material.CARROTS) {
                        org.bukkit.block.data.Ageable ageable = (org.bukkit.block.data.Ageable) block.getBlockData();
                        if (ageable.getAge() == 7) {
                            block.setType(Material.AIR);
                            if (!telekinesis) {
                                block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("CARROT"));
                            } else {
                                player.getInventory().addItem(CustomMaterial.getItemClass("CARROT"));
                            }
                            farmingXPAdd = 2;


                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                @Override
                                public void run() {
                                    block.setType(Material.CARROTS);
                                    ageable.setAge(7);
                                    block.setBlockData(ageable);
                                }
                            }, 140);
                        }
                    }

                    if (block.getType() == Material.POTATOES) {
                        org.bukkit.block.data.Ageable ageable = (org.bukkit.block.data.Ageable) block.getBlockData();
                        if (ageable.getAge() == 7) {
                            block.setType(Material.AIR);
                            if (!telekinesis) {
                                block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("POTATO"));
                            } else {
                                player.getInventory().addItem(CustomMaterial.getItemClass("POTATO"));
                            }

                            farmingXPAdd = 2;

                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                @Override
                                public void run() {
                                    block.setType(Material.POTATOES);
                                    ageable.setAge(7);
                                    block.setBlockData(ageable);
                                }
                            }, 140);
                        }
                    }


                    if (block.getType() == Material.SUGAR_CANE) {
                        BlockData data = block.getBlockData();

                        Material material = block.getType();

                        Location relative = new Location(block.getLocation().getWorld(), block.getLocation().getBlockX(), block.getLocation().getBlockY() + 1, block.getLocation().getBlockZ());

                        if (player.getWorld().getBlockAt(relative).getType() == Material.SUGAR_CANE) {
                            Bukkit.getServer().getPluginManager().callEvent(new BlockBreakEvent(player.getWorld().getBlockAt(relative), player));
                        }


                        block.setType(Material.AIR);


                        if (!telekinesis) {
                            block.getWorld().dropItem(block.getLocation(), CustomMaterial.getItemClass("SUGAR_CANE"));
                        } else {
                            player.getInventory().addItem(CustomMaterial.getItemClass("SUGAR_CANE"));
                        }

                        farmingXPAdd = 2;

                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = block.getLocation().getBlockY(); i > 0; i--) {
                                    if (block.getWorld().getBlockAt(block.getLocation().getBlockX(), i, block.getLocation().getBlockZ()).getType() == Material.AIR) {
                                        block.getWorld().getBlockAt(block.getLocation().getBlockX(), i, block.getLocation().getBlockZ()).setType(material);
                                        block.getWorld().getBlockAt(block.getLocation().getBlockX(), i, block.getLocation().getBlockZ()).setBlockData(data);
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }, 200);
                    }
                }
            }

            NumberFormat numberFormat = NumberFormat.getInstance();

            numberFormat.setGroupingUsed(true);

            if (miningXPAdd > 0) {

                float combatXp = ColeCrafterSlayers.getSkillXP(player, "mining");
                float combatLevel = ColeCrafterSlayers.getSkillLevel(player, "mining");


                ColeCrafterSlayers.setSkillXP(player, "mining", combatXp + miningXPAdd);


                String combatXpmessage = "§3+" + miningXPAdd + " Mining (" + numberFormat.format(combatXp) + "/" + numberFormat.format(combatLevel == 50 ? numberFormat.format(Math.ceil(100 * (Math.pow(1.26, 48)) + 200 * 50)) : Math.ceil(100 * (Math.pow(1.26, combatLevel))) + 200 * (combatLevel)) + ")";
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 2);

                PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + combatXpmessage + "\"}"), ChatMessageType.GAME_INFO, UUID.randomUUID());

                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

                ColeCrafterSlayers.displayActionbar.put(player, false);
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        ColeCrafterSlayers.displayActionbar.put(player, true);
                    }
                }, 40);


            }

            if (farmingXPAdd > 0) {

                float combatXp = ColeCrafterSlayers.getSkillXP(player, "farming");
                float combatLevel = ColeCrafterSlayers.getSkillLevel(player, "farming");


                ColeCrafterSlayers.setSkillXP(player, "farming", combatXp + farmingXPAdd);


                String combatXpmessage = "§3+" + farmingXPAdd + " Farming (" + numberFormat.format(combatXp) + "/" + numberFormat.format(combatLevel == 50 ? Math.ceil(100 * (Math.pow(1.26, 48)) + 400 * 50) : Math.ceil(100 * (Math.pow(1.26, combatLevel))) + 400 * (combatLevel)) + ")";
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 2);

                PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + combatXpmessage + "\"}"), ChatMessageType.GAME_INFO, UUID.randomUUID());

                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

                ColeCrafterSlayers.displayActionbar.put(player, false);
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        ColeCrafterSlayers.displayActionbar.put(player, true);
                    }
                }, 40);


            }

            if (forgagingXPAdd > 0) {

                float combatXp = ColeCrafterSlayers.getSkillXP(player, "foraging");
                float combatLevel = ColeCrafterSlayers.getSkillLevel(player, "foraging");


                ColeCrafterSlayers.setSkillXP(player, "foraging", combatXp + forgagingXPAdd);


                String combatXpmessage = "§3+" + forgagingXPAdd + " Foraging (" + numberFormat.format(combatXp) + "/" + numberFormat.format(combatLevel == 50 ? Math.ceil(100 * (Math.pow(1.26, 48)) + 400 * 50) : Math.ceil(100 * (Math.pow(1.26, combatLevel))) + 400 * (combatLevel)) + ")";
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 2);

                PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + combatXpmessage + "\"}"), ChatMessageType.GAME_INFO, UUID.randomUUID());

                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

                ColeCrafterSlayers.displayActionbar.put(player, false);
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        ColeCrafterSlayers.displayActionbar.put(player, true);
                    }
                }, 40);


            }
        }





    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        boolean isSneaking = player.isSneaking();



        if(isSneaking) {



            Location loc = player.getLocation();
            loc.setY(loc.getY()-0.5);
            if (player.getWorld().getBlockAt(loc).getType() == Material.AIR) {
                ItemStack boots = player.getInventory().getBoots();

                String foundValue = getItemID(boots);

                if (foundValue == null) return;
                if (foundValue.equals("SPIDER_BOOTS")) {
                    SpiderBoots.spiderBootsCD.putIfAbsent(player, false);
                    if (!SpiderBoots.spiderBootsCD.get(player)) {
                        Vector velocity = player.getLocation().getDirection().normalize().multiply(0.5);
                        velocity.setY(velocity.getY() + 0.35);
                        player.setVelocity(velocity);

                        player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation().getX(), player.getLocation().getY() - 0.2, player.getLocation().getZ(), 50, 0.3, 0, 0.3, 0);

                        SpiderBoots.spiderBootsCD.put(player, true);
                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                            @Override
                            public void run() {
                                SpiderBoots.spiderBootsCD.put(player, false);
                            }
                        }, 8);

                    }
                }
            }
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent e) {
        double amount = e.getAmount();
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            if (e.getEntity() instanceof Player) {

                if (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
                    amount = 0;
                }

                Player player = (Player) e.getEntity();
                rottenRounds.putIfAbsent(player, false);
                if (rottenRounds.get(player)) {
                    amount *= 0.1;
                }

                ItemStack item = player.getEquipment().getHelmet();

                if (!(item == null)) {

                    if (item.hasItemMeta()) {
                        String foundValue = getItemID(item);

                        if (foundValue != null) {
                            if (foundValue.equals("ZOMBIE_HEART") || foundValue.equals("CRYSTALLIZED_HEART") || foundValue.equals("REVIVED_HEART")) {
                                amount *= 2;
                            }
                            if (foundValue.equals("REAPER_MASK")) {
                                amount *= 2.5;
                            }
                        }
                    }
                }


            }

            e.setAmount(0);
            double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            if (entity.getHealth() + amount < maxHealth) {

                entity.setHealth(entity.getHealth() + amount);
            } else if (entity.getHealth() + amount > maxHealth) {
                entity.setHealth(maxHealth);
            }
        }
    }

    @EventHandler
    public void onPickUP(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();



            boolean isOwnedBySomeone = false;

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.equals(player)) continue;
                if (playerSpecificPickup.get(p) == null) continue;
                for (Item item : playerSpecificPickup.get(p)) {
                    if (item.equals(e.getItem())) {
                        isOwnedBySomeone = true;
                        break;
                    }
                }
            }

            if (isOwnedBySomeone) {
                e.setCancelled(true);
                return;
            }

            if (CustomMaterial.getItemClass(e.getItem().getItemStack().getType().name()) != null) {
                if (!e.getItem().getItemStack().hasItemMeta()) {
                    ItemStack item = CustomMaterial.getItemClass(e.getItem().getItemStack().getType().name());
                    item.setAmount(e.getItem().getItemStack().getAmount());
                    player.getInventory().addItem(item);

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            player.getInventory().remove(e.getItem().getItemStack());
                        }
                    }, 1);
                }
            }

            if (CustomItem.getItemClass(e.getItem().getItemStack().getType().name()) != null) {
                if (!e.getItem().getItemStack().hasItemMeta()) {
                    ItemStack item = CustomItem.getItemClass(e.getItem().getItemStack().getType().name());
                    item.setAmount(e.getItem().getItemStack().getAmount());
                    player.getInventory().addItem(item);

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            player.getInventory().remove(e.getItem().getItemStack());
                        }
                    }, 1);
                }
            }

        }
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

        PersistentDataContainer container = e.getEntity().getPersistentDataContainer();



        String foundValue = null;
        if (container.has(key, PersistentDataType.STRING)) {
            foundValue = container.get(key, PersistentDataType.STRING);
        }

        if (foundValue != null) {
            if (foundValue.equals("SLIME") || foundValue.equals("MAGMA_CUBE_BOSS") || foundValue.equals("MAGMA_CUBE")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCustomDeath(CustomPlayerDeathEvent e) {
        e.execute();


        for (Entity en : e.getEntity().getWorld().getEntities()) {
            if (en.getType() == EntityType.ZOMBIE) {
                if (((LivingEntity) en).getEquipment().getChestplate() != null) {
                    if (((LivingEntity) en).getEquipment().getChestplate().hasItemMeta()) {
                        if (((LivingEntity) en).getEquipment().getChestplate().getItemMeta().hasDisplayName()) {
                            if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant")) {

                                Player player = SlayerMenu.revenantHorrorPlayer.get(en);

                                if (player.equals(e.getEntity())) {

                                    SlayerMenu.revenantHorrorPlayer.put((LivingEntity) en, null);
                                    rottenRounds.put(player, false);


                                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 0f);
                                    player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " SLAYER QUEST FAILED!\n" + ChatColor.DARK_PURPLE + " ⋙ " + ChatColor.GRAY + "You died and failed to kill the boss!");


                                    en.remove();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCustomLoot(CustomLootDrop e) {
        e.execute();
    }

    @EventHandler
    public void onCustomBossDeath(CustomBossDeathEvent e) {
        e.execute();
    }

    @EventHandler
    public void onCustomDamage(CustomDamageEvent e) {
        e.execute();
    }
}
