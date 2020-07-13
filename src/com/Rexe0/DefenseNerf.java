package com.Rexe0;

import com.Rexe0.Events.CustomBossDeathEvent;
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
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
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

    public static HashMap<Player, Integer> antiStandStill = new HashMap<>();
    public static HashMap<LivingEntity, Integer> antiNotHit = new HashMap<>();
    public static HashMap<Player, Location> oldLocation = new HashMap<>();
    public static HashMap<Player, Location> newLocation = new HashMap<>();
    public static HashMap<Player, Float> pestilence = new HashMap<>();
    public static HashMap<Player, Boolean> rottenRounds = new HashMap<>();
    public static HashMap<Player, ArrayList<Item>> playerSpecificPickup = new HashMap<>();
    public static HashMap<LivingEntity, Player> lastKilledEntity = new HashMap<>();
    private static HashMap<Player, Float> magmaBossDamage = new HashMap<>();
    public static HashMap<Player, Integer> totalDefense = new HashMap<>();
    private static HashMap<Entity, Integer> thunderlordHits = new HashMap<Entity, Integer>();


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
        if (!(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
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
            Random RNG = new Random();

            int number = 0;



            if (lastKilledEntity.get(e.getEntity()) != null) {

                Player player = lastKilledEntity.get(e.getEntity());

                if (foundValue1 != null) {

                    NumberFormat numberFormat = NumberFormat.getInstance();

                    numberFormat.setGroupingUsed(true);

                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    Scoreboard scoreboard = manager.getMainScoreboard();
                    Objective combatXp = scoreboard.getObjective("combatXP");
                    Objective combatLevel = scoreboard.getObjective("combatLevel");


                    Score combatXpScore = combatXp.getScore(lastKilledEntity.get(e.getEntity()).getName());
                    Score combatLevelScore = combatLevel.getScore(lastKilledEntity.get(e.getEntity()).getName());

                    if (combatXpScore == null) {
                        combatXpScore.setScore(0);
                    }

                    if (combatLevelScore == null) {
                        combatLevelScore.setScore(0);
                    }

                    combatXpScore.setScore(combatXp.getScore(lastKilledEntity.get(e.getEntity()).getName()).getScore()+foundValue1);

                    String combatXpmessage = "§3+"+foundValue1+" Combat ("+numberFormat.format(combatXpScore.getScore())+"/"+numberFormat.format(combatLevelScore.getScore() == 50 ? Math.ceil(100*(Math.pow(1.26, 48))+300*50) : Math.ceil(100*(Math.pow(1.26, combatLevelScore.getScore())))+300*(combatLevelScore.getScore()))+")";
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

                    if (combatXpScore.getScore() >= Math.ceil(100*(Math.pow(1.26, combatLevelScore.getScore()))+300*(combatLevelScore.getScore())) && (combatLevelScore.getScore() < 50)) {
                        player.sendMessage(ChatColor.DARK_AQUA+"--------------------------------------\n"+ChatColor.AQUA+""+ChatColor.BOLD+" SKILL LEVEL UP"+ChatColor.RESET+""
                                +ChatColor.DARK_AQUA+" Combat "+ChatColor.DARK_GRAY+combatLevelScore.getScore()+"→ "+ChatColor.DARK_AQUA+(combatLevelScore.getScore()+1)+ChatColor.GREEN+""+ChatColor.BOLD+"\n Rewards\n  "+ChatColor.RESET+""+ChatColor.DARK_GRAY+"+"+ChatColor.GREEN+"0.25"+ChatColor.RED+" ❁ Damage\n"+ChatColor.DARK_AQUA+"--------------------------------------\n");
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 0);
                        combatLevelScore.setScore(combatLevelScore.getScore()+1);


                        UUID uuid = UUID.fromString("41c90f75-20ea-4f77-997a-db7a3f9d35a5");

                        for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers()) {
                            if (attribute.getName().equals("combatSkill")) {
                                player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(attribute);

                            }

                        }
                        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(new AttributeModifier(uuid, "combatSkill", 0.25*combatLevelScore.getScore(), AttributeModifier.Operation.ADD_NUMBER));

                    }


                }

                if (foundValue.equals("SPLITTER_SPIDER")) {
                    CustomMob.spawnMob("SILVERFISH", e.getEntity().getLocation());
                    CustomMob.spawnMob("SILVERFISH", e.getEntity().getLocation());
                }

                if (foundValue.equals("MAGMA_CUBE_BOSS")) {

                    Bukkit.getServer().getPluginManager().callEvent(new CustomBossDeathEvent(player, CustomBossDeathEvent.BossType.MAGMA_CUBE_BOSS, e.getEntity(), player ,magmaBossDamage));


                    Location loc = e.getEntity().getLocation();

                    double spawnY = loc.getWorld().getHighestBlockYAt(loc);

                    Location spawnLocation = new Location(loc.getWorld(), loc.getX(), spawnY, loc.getZ());


                    for (Player entity : Bukkit.getOnlinePlayers()) {
                        entity.sendBlockChange(spawnLocation, Material.BEACON.createBlockData());

                        if (emberRodPerson != null) {
                            Bukkit.broadcastMessage("Session 2");
                            entity.sendMessage(ChatColor.GOLD + emberRodPerson.getName() + ChatColor.YELLOW + " has obtained " + ChatColor.DARK_PURPLE + "Ember Rod");
                        }
                    }


                    Item item = null;
                    if (emberRodPerson != null) {
                        item = spawnLocation.getWorld().dropItem(spawnLocation, new EmberRod());
                        item.setCustomNameVisible(true);
                        item.setCustomName(ChatColor.GOLD+""+ChatColor.MAGIC+"I"+ChatColor.RESET+""+ChatColor.DARK_PURPLE+" Ember Rod "+ChatColor.GOLD+""+ChatColor.MAGIC+"I");
                        item.setInvulnerable(true);

                        ArrayList<Item> reservedItems = new ArrayList<>();
                        if (playerSpecificPickup.get(emberRodPerson) != null) reservedItems = playerSpecificPickup.get(emberRodPerson);

                        Bukkit.broadcastMessage("Session 3  "+emberRodPerson.getName());

                        reservedItems.add(item);

                        playerSpecificPickup.put(emberRodPerson, reservedItems);
                    }



                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (emberRodPerson != null) {
                            if (!(p.equals(emberRodPerson))) {
//                                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(((CraftItem)item).getHandle().getId());
                                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(item.getEntityId());

                                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);

                                p.sendMessage("Session 4");
                            }

                        }
                        Item item1 = spawnLocation.getWorld().dropItem(spawnLocation, new EmberFragment());
                        item1.getItemStack().setAmount(emberAmount.get(p));

                        item1.setCustomNameVisible(true);
                        item1.setCustomName(ChatColor.GOLD+""+ChatColor.MAGIC+"I"+ChatColor.RESET+" "+ChatColor.BLUE+item1.getItemStack().getAmount()+"x Ember Fragment "+ChatColor.GOLD+""+ChatColor.MAGIC+"I");

                        item1.setInvulnerable(true);

                        ArrayList<Item> reservedItems = new ArrayList<>();
                        if (playerSpecificPickup.get(p) != null) reservedItems = playerSpecificPickup.get(p);

                        reservedItems.add(item1);

                        playerSpecificPickup.put(p, reservedItems);

                        p.sendMessage("Session 5  "+emberAmount.get(p)+" ");

                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            if (!(pl.equals(p))) {
//                                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(((CraftItem)item1).getHandle().getId());
                                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(item1.getEntityId());


                                ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(packet);
                                pl.sendMessage("Session 6");
                            }
                        }
                    }

                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), ("execute positioned "+spawnLocation.getBlockX()+" "+spawnLocation.getBlockY()+" "+spawnLocation.getBlockZ()+" run spreadplayers "+spawnLocation.getBlockX()+" "+spawnLocation.getBlockZ()+" "+1+" "+5+" "+false+" @e[type=item,distance=..2]"));
                    magmaBossDamage.clear();
                }

                if (foundValue.equals("LAPIS_ZOMBIE")) {

                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("LAPIS_HELMET"), ChatColor.GREEN+"Lapis Armor Helmet", CustomLootDrop.Rarity.RARE, e.getEntity()));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("LAPIS_CHESTPLATE"), ChatColor.GREEN+"Lapis Armor Chestplate", CustomLootDrop.Rarity.RARE, e.getEntity()));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("LAPIS_LEGGINGS"), ChatColor.GREEN+"Lapis Armor Leggings", CustomLootDrop.Rarity.RARE, e.getEntity()));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("LAPIS_BOOTS"), ChatColor.GREEN+"Lapis Armor Boots", CustomLootDrop.Rarity.RARE, e.getEntity()));
                }

                if (foundValue.equals("MINER_ZOMBIE")) {

                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("MINER_HELMET"), ChatColor.BLUE+"Miner Armor Helmet", CustomLootDrop.Rarity.RARE, e.getEntity()));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("MINER_CHESTPLATE"), ChatColor.BLUE+"Miner Armor Chestplate", CustomLootDrop.Rarity.RARE, e.getEntity()));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("MINER_LEGGINGS"), ChatColor.BLUE+"Miner Armor Leggings", CustomLootDrop.Rarity.RARE, e.getEntity()));
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 200, CustomItem.getItemClass("MINER_BOOTS"), ChatColor.BLUE+"Miner Armor Boots", CustomLootDrop.Rarity.RARE, e.getEntity()));
                }

                if (foundValue.equals("STRONG_MINER_ZOMBIE")) {
                    ItemStack item = CustomItem.getItemClass("MINER_CHESTPLATE");
                    item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 300, item, ChatColor.BLUE+"Miner Armor Chestplate", CustomLootDrop.Rarity.VERY_RARE, e.getEntity()));

                    item = CustomItem.getItemClass("MINER_HELMET");
                    item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 300, item, ChatColor.BLUE+"Miner Armor Helmet", CustomLootDrop.Rarity.VERY_RARE, e.getEntity()));

                    item = CustomItem.getItemClass("MINER_LEGGINGS");
                    item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 300, item, ChatColor.BLUE+"Miner Armor Leggings", CustomLootDrop.Rarity.VERY_RARE, e.getEntity()));

                    item = CustomItem.getItemClass("MINER_BOOTS");
                    item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 300, item, ChatColor.BLUE+"Miner Armor Boots", CustomLootDrop.Rarity.VERY_RARE, e.getEntity()));
                }

                if (foundValue.equals("ZOMBIE_PIGMAN")) {

                    Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 1, 20, CustomItem.getItemClass("FLAMING_SWORD"), ChatColor.GREEN+"Flaming Sword", CustomLootDrop.Rarity.RARE, e.getEntity()));
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
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 7, 13000, CustomItem.getItemClass("SCYTHE_BLADE"), ChatColor.GOLD+"Scythe Blade", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity()));
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 42, 13000, CustomItem.getItemClass("ROTTED_TOOTH"), ChatColor.DARK_PURPLE+"Rotted Tooth", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity()));
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 21, 13000, CustomItem.getItemClass("BEHEADED_HORROR"), ChatColor.DARK_PURPLE+"Beheaded Horror", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity()));


                    }
                    if (slayerTier == 9) {
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 10, 13000, CustomItem.getItemClass("SCYTHE_BLADE"), ChatColor.GOLD+"Scythe Blade", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity()));
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 60, 13000, CustomItem.getItemClass("ROTTED_TOOTH"), ChatColor.DARK_PURPLE+"Rotted Tooth", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity()));
                        Bukkit.getServer().getPluginManager().callEvent(new CustomLootDrop(player, 30, 13000, CustomItem.getItemClass("BEHEADED_HORROR"), ChatColor.DARK_PURPLE+"Beheaded Horror", CustomLootDrop.Rarity.CRAZY_RARE, e.getEntity()));

                    }

                }
            }
        }
    }
























    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {


        NamespacedKey key2 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

        PersistentDataContainer container2 = e.getEntity().getPersistentDataContainer();


        String foundValue2 = null;
        if (container2.has(key2, PersistentDataType.STRING)) {
            foundValue2 = container2.get(key2, PersistentDataType.STRING);
        }

        if (foundValue2 != null) {
            if (foundValue2.equals("MAGMA_CUBE_BOSS")) {
                int health = (int) Math.ceil(((LivingEntity) e.getEntity()).getHealth());

                String color = health > 150 ? ChatColor.GREEN + "" : health <= 150 && health > 75 ? ChatColor.YELLOW + "" : health <= 75 ? ChatColor.RED + "" : "";

                e.getEntity().setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv100"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Magma Cube Boss " + color + health + ChatColor.GREEN + "/300" + ChatColor.RED + "❤");

            }
        }



        if (e.getEntity().getType() == EntityType.ZOMBIE) {
            if (((LivingEntity) e.getEntity()).getEquipment().getChestplate() != null) {
                if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().hasItemMeta()) {
                    if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().getItemMeta().hasDisplayName()) {
                        if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant")) {
                            if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 5) {
                                int health = (int) Math.ceil(((LivingEntity) e.getEntity()).getHealth());

                                String color = health > 15 ? ChatColor.GREEN + "" : health <= 15 && health > 8 ? ChatColor.YELLOW + "" : health <= 8 ? ChatColor.RED + "" : "";

                                e.getEntity().setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv10"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/30" + ChatColor.RED + "❤");
                            }
                            if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 6) {
                                int health = (int) Math.ceil(((LivingEntity) e.getEntity()).getHealth());

                                String color = health > 45 ? ChatColor.GREEN + "" : health <= 45 && health > 23 ? ChatColor.YELLOW + "" : health <= 23 ? ChatColor.RED + "" : "";

                                e.getEntity().setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv70"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/90" + ChatColor.RED + "❤");
                            }
                            if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 7) {
                                int health = (int) Math.ceil(((LivingEntity) e.getEntity()).getHealth());

                                String color = health > 150 ? ChatColor.GREEN + "" : health <= 150 && health > 75 ? ChatColor.YELLOW + "" : health <= 75 ? ChatColor.RED + "" : "";

                                e.getEntity().setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv310"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/300" + ChatColor.RED + "❤");
                            }

                            if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 8) {
                                int health = (int) Math.ceil(((LivingEntity) e.getEntity()).getHealth());

                                String color = health > 450 ? ChatColor.GREEN + "" : health <= 450 && health > 225 ? ChatColor.YELLOW + "" : health <= 225 ? ChatColor.RED + "" : "";

                                e.getEntity().setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv610"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/900" + ChatColor.RED + "❤");
                            }
                            if (((LivingEntity) e.getEntity()).getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 9) {
                                int health = (int) Math.ceil(((LivingEntity) e.getEntity()).getHealth());

                                String color = health > 1050 ? ChatColor.GREEN + "" : health <= 1050 && health > 525 ? ChatColor.YELLOW + "" : health <= 525 ? ChatColor.RED + "" : "";

                                e.getEntity().setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv890"+ChatColor.DARK_GRAY+"] "+ChatColor.RED + "☠" + ChatColor.AQUA + " Revenant Horror " + color + health + ChatColor.GREEN + "/2100" + ChatColor.RED + "❤");
                            }
                        }
                    }
                }
            }
        }

        LivingEntity damager = e.getDamager() instanceof Arrow ? (LivingEntity) ((Arrow)e.getDamager()).getShooter() : e.getDamager() instanceof LivingEntity ? (LivingEntity) e.getDamager() : null;

        if (damager instanceof Player) {

            Player player = (Player) damager;
            ItemStack heldItem = player.getItemInHand();





            double attackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue();


            if (e.getEntity() instanceof LivingEntity) {
                attackSpeed = (((LivingEntity) e.getEntity()).getMaximumNoDamageTicks()-((((LivingEntity) e.getEntity()).getMaximumNoDamageTicks()/2f) * (attackSpeed / 100)));
            }


            double finalAttackSpeed = attackSpeed;
            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                @Override
                public void run() {
                    if (e.getEntity() instanceof LivingEntity) {


                        ((LivingEntity)e.getEntity()).setNoDamageTicks((int) finalAttackSpeed);
                    }
                }
            }, 1);


            ItemStack heldItem1 = damager.getEquipment().getItemInMainHand();

            if (e.getDamager() instanceof Arrow) {
                if (heldItem1 != null) {


                    if (heldItem1.hasItemMeta()) {
                        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemDamage");
                        ItemMeta itemMeta = heldItem1.getItemMeta();
                        if (itemMeta != null) {
                            PersistentDataContainer container = itemMeta.getPersistentDataContainer();


                            float foundValue = 0;
                            if (container.has(key, PersistentDataType.FLOAT)) {
                                foundValue = container.get(key, PersistentDataType.FLOAT);
                            }

                            NamespacedKey key1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "hpbDamage");
                            ;
                            PersistentDataContainer container1 = itemMeta.getPersistentDataContainer();


                            int foundValue1 = 0;
                            if (container1.has(key1, PersistentDataType.INTEGER)) {
                                foundValue1 = container1.get(key1, PersistentDataType.INTEGER);
                            }

                            if (foundValue != 0) {
                                int powerLevel = heldItem1.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
                                foundValue += foundValue1;
                                foundValue *= 1 + (0.08 * powerLevel);
                                e.setDamage(foundValue);
                            }
                        }
                    }

                }
            }


            float damageMultiplier = 0f;



            if (e.getDamager().getType() == EntityType.ARROW) {
                if (e.getDamager().getCustomName() != null) {
                    if (e.getDamager().getCustomName().equals("MOSQUITO_1")) {
                        damageMultiplier += 0.19;
                        pestilence.putIfAbsent((Player) damager, 0f);
                        if (pestilence.get(player) > 11) {
                            pestilence.put((Player) damager, 0.11f);
                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                @Override
                                public void run() {
                                   if (pestilence.get(player) <= 11) {
                                       pestilence.put((Player) damager, 0f);
                                   }
                                }
                            }, 100);
                        }

                    }

                    if (e.getDamager().getCustomName().equals("SCORPION_1")) {
                        damageMultiplier += 9.0;
                    }

                    if (e.getDamager().getCustomName().equals("HURRICANE")) {
                        if (e.getEntity() instanceof LivingEntity) {
                            if (!((LivingEntity) e.getEntity()).isInvulnerable()) {
                                Random rand = new Random();
                                int n = rand.nextInt(20);
                                if (n == 0) {
                                    double damage = e.getDamage();
                                    ((LivingEntity) e.getEntity()).damage(damage, damager);

                                    EntityLightning lightning = new EntityLightning(EntityTypes.LIGHTNING_BOLT, ((CraftWorld) e.getEntity().getWorld()).getHandle());

                                    Vec3D vec = new Vec3D(0, 0, 0);

                                    PacketPlayOutSpawnEntity lightningPacket = new PacketPlayOutSpawnEntity(lightning.getId(), lightning.getUniqueID(), e.getEntity().getLocation().getX(), e.getEntity().getLocation().getY(), e.getEntity().getLocation().getZ(), 0f, 0f, EntityTypes.LIGHTNING_BOLT, 0, vec);
                                    ((CraftPlayer) ((Player) damager)).getHandle().playerConnection.sendPacket(lightningPacket);
                                }
                            }
                        }
                    }
                }
            }


            if (heldItem.hasItemMeta()) {
                ItemMeta itemMeta = heldItem.getItemMeta();
                if (itemMeta != null) {
                    String foundValue = getItemID(heldItem);


                    int sharpnessLevel = heldItem.getEnchantmentLevel(Enchantment.DAMAGE_ALL);

                    damageMultiplier += (0.05 * sharpnessLevel);

                    if (e.getEntity().getType() == EntityType.ZOMBIE || e.getEntity().getType() == EntityType.SKELETON || e.getEntity().getType() == EntityType.HUSK || e.getEntity().getType() == EntityType.STRAY || e.getEntity().getType() == EntityType.DROWNED || e.getEntity().getType() == EntityType.WITHER_SKELETON || e.getEntity().getType() == EntityType.ZOMBIFIED_PIGLIN || e.getEntity().getType() == EntityType.PHANTOM || e.getEntity().getType() == EntityType.ZOMBIE) {
                        int smiteLevel = heldItem.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);

                        damageMultiplier += (0.1 * smiteLevel);

                        if (foundValue != null) {
                            if (foundValue.equals("UNDEAD_SWORD")) {
                                damageMultiplier += 1.0f;
                            }
                        }
                    }

                    if (e.getEntity().getType() == EntityType.ZOMBIE) {
                        if (foundValue != null) {
                            if (foundValue.equals("REVENANT_FALCHION")) {
                                damageMultiplier += 1.5f;
                            }

                            if (foundValue.equals("REAPER_FALCHION")) {
                                damageMultiplier += 2.0f;
                            }

                            if (foundValue.equals("REAPER_SCYTHE")) {
                                damageMultiplier += 2.5f;
                            }
                        }
                    }

                    if (e.getEntity().getType() == EntityType.ENDER_DRAGON || e.getEntity().getType() == EntityType.ENDERMAN || e.getEntity().getType() == EntityType.ENDERMITE) {
                        if (foundValue != null) {
                            if (foundValue.equals("END_SWORD")) {
                                damageMultiplier += 1.0f;
                            }
                        }
                    }

                    if (e.getEntity().getType() == EntityType.SPIDER || e.getEntity().getType() == EntityType.CAVE_SPIDER || e.getEntity().getType() == EntityType.SILVERFISH) {

                        int arthropodsLevel = heldItem.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);

                        damageMultiplier += (0.1 * arthropodsLevel);
                    }


                    if (e.getEntity() instanceof LivingEntity) {
                        boolean firstStrike = false;
                        boolean giantKiller = false;
                        boolean thunderLord = false;
                        for (ItemStack item : ((Player) damager).getInventory().getContents()) {
                            String foundValue1 = getItemID(item);

                            if (foundValue1 != null) {
                                if (foundValue1.equals("FIRST_STRIKE_BEAD")) {
                                    firstStrike = true;

                                }
                                if (foundValue1.equals("GIANT_KILLER_NECKLACE")) {
                                    giantKiller = true;

                                }
                                if (foundValue1.equals("THUNDERLORD_ARTIFACT")) {
                                    thunderLord = true;

                                }
                            }

                        }

                        double maxHealth = ((LivingEntity) e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                        double maxHealthPlayer = ((LivingEntity) damager).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                        if (firstStrike) {
                            if (((LivingEntity) e.getEntity()).getHealth() == maxHealth) {
                                damageMultiplier += 0.5;
                            }
                        }
                        if (giantKiller) {
                            if (maxHealth > maxHealthPlayer) {
                                damageMultiplier += 0.25;
                            }
                        }

                        if (thunderLord) {
                            if (!((LivingEntity) e.getEntity()).isInvulnerable()) {
                                thunderlordHits.putIfAbsent(e.getEntity(), 0);
                                thunderlordHits.put(e.getEntity(), thunderlordHits.get(e.getEntity()) + 1);
                                if (thunderlordHits.get(e.getEntity()) == 3) {
                                    double damage = ((LivingEntity) e.getDamager()).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
                                    ((LivingEntity) e.getEntity()).damage(damage / 2, e.getDamager());


                                    EntityLightning lightning = new EntityLightning(EntityTypes.LIGHTNING_BOLT, ((CraftWorld) e.getEntity().getWorld()).getHandle());

                                    Vec3D vec = new Vec3D(0, 0, 0);

                                    PacketPlayOutSpawnEntity lightningPacket = new PacketPlayOutSpawnEntity(lightning.getId(), lightning.getUniqueID(), e.getEntity().getLocation().getX(), e.getEntity().getLocation().getY(), e.getEntity().getLocation().getZ(), 0f, 0f, EntityTypes.LIGHTNING_BOLT, 0, vec);
                                    ((CraftPlayer) ((Player) damager)).getHandle().playerConnection.sendPacket(lightningPacket);

                                    thunderlordHits.put(e.getEntity(), 0);
                                }
                            }
                        }


                        int damageBonus = 0;



                        if (isFullSet(player, "SUPERIOR_DRAGON_HELMET", "SUPERIOR_DRAGON_CHESTPLATE", "SUPERIOR_DRAGON_LEGGINGS", "SUPERIOR_DRAGON_BOOTS") && foundValue.equals("ASPECT_OF_THE_DRAGONS")) {
                            damageBonus += 10;
                        }

                       if (isFullSet(player, null, "ELEGANT_TUXEDO_CHESTPLATE", "ELEGANT_TUXEDO_LEGGINGS", "ELEGANT_TUXEDO_BOOTS")) {
                           damageMultiplier += 1.5;
                       }


                        e.setDamage((e.getDamage() + damageBonus) * (1 + damageMultiplier));
                        if (foundValue != null) {
                            if (foundValue.equals("FLAMING_SWORD")) {
                                e.getEntity().setFireTicks(60);
                            }
                        }
                    }
                }
            }


        }
        if (e.getEntity() instanceof LivingEntity) {






            if (damager != null) {


                LivingEntity player = (LivingEntity) e.getEntity();
                if (damager instanceof LivingEntity) {
                    if (SlayerMenu.revenantHorrorPlayer.get(e.getDamager()) != null) {
                        if (((LivingEntity) e.getDamager()).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant")) {
                            if (SlayerMenu.revenantHorrorPlayer.get(e.getDamager()).equals(player)) {
                                antiNotHit.put((LivingEntity) damager, 0);
                            }
                        }
                    }
                }


                if (e.getDamager() instanceof Arrow) {
                    if (damager instanceof Player) {
                        ((Player) damager).playSound(damager.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 2f, 0.8f);
                    }
                }

                double damage = e.getDamage();
                double defensePoints = 0;


                if (player instanceof Player) pestilence.putIfAbsent((Player) player, 0f);


                defensePoints = StatsCommand.getDefence(player);


                if (player instanceof Player) {
                    byte zombieTalType = 0;
                    double damageReduction = 0;

                    NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemID");

                    PlayerInventory inv = ((Player) player).getInventory();

                    ItemStack helm1 = inv.getHelmet();
                    ItemStack chest1 = inv.getChestplate();
                    ItemStack boots1 = inv.getBoots();
                    ItemStack pants1 = inv.getLeggings();


                    ItemMeta helmMeta = null;
                    ItemMeta chestMeta = null;
                    ;
                    ItemMeta pantsMeta = null;
                    ;
                    ItemMeta bootsMeta = null;
                    ;
                    if (helm1 != null) {
                        if (helm1.hasItemMeta()) {
                            helmMeta = helm1.getItemMeta();
                        }
                    }

                    if (chest1 != null) {
                        if (chest1.hasItemMeta()) {
                            chestMeta = chest1.getItemMeta();
                        }
                    }

                    if (pants1 != null) {
                        if (pants1.hasItemMeta()) {
                            pantsMeta = pants1.getItemMeta();
                        }
                    }

                    if (boots1 != null) {
                        if (boots1.hasItemMeta()) {
                            bootsMeta = boots1.getItemMeta();
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


                    if (chestfoundValue != null) {
                        if (chestfoundValue.equals("REVENANT_CHESTPLATE")) {
                            if (damager instanceof Zombie) {
                                damageReduction += 0.1;
                                defensePoints += 80;
                            }
                        }
                    }

                    if (pantsfoundValue != null) {
                        if (pantsfoundValue.equals("REVENANT_LEGGINGS")) {
                            if (damager instanceof Zombie) {
                                damageReduction += 0.1;
                                defensePoints += 80;
                            }
                        }
                    }

                    if (bootsfoundValue != null) {
                        if (bootsfoundValue.equals("REVENANT_BOOTS")) {
                            if (damager instanceof Zombie) {
                                damageReduction += 0.1;
                                defensePoints += 80;
                            }
                        }
                    }


                    for (ItemStack item : ((Player) player).getInventory().getContents()) {
                        String foundValue = getItemID(item);

                        if (foundValue != null) {
                            if (damager instanceof Zombie) {
                                if (foundValue.equals("ZOMBIE_TALISMAN")) {
                                    if (zombieTalType < 1) zombieTalType = 1;
                                }
                                if (foundValue.equals("ZOMBIE_RING")) {
                                    if (zombieTalType < 2) zombieTalType = 2;

                                }
                                if (foundValue.equals("ZOMBIE_ARTIFACT")) {
                                    if (zombieTalType < 3) zombieTalType = 3;

                                }
                            }
                        }

                    }
                    damageReduction += zombieTalType * 0.05;
                    damage = damage * (1 - damageReduction);


                }


                if (player instanceof Player)
                    damage = damage * (1 - (defensePoints * (1 - pestilence.get(player)) / (20 + defensePoints * (1 - pestilence.get(player)))));
                else damage = damage * (1 - (defensePoints / (20 + defensePoints)));


                if (player instanceof Player) {

                    totalDefense.put((Player) player, (int) Math.floor(defensePoints));
                }
                double damage1 = 0;
                if (player instanceof Player) {
                    damage1 = damage / 4;

                }




                e.setDamage(0);


                if (damager instanceof Player) {
                    byte devourRingType = 0;
                    for (ItemStack item : ((Player) damager).getInventory().getContents()) {
                        String foundValue = getItemID(item);

                        if (foundValue != null) {
                            if (foundValue.equals("DEVOUR_RING")) {
                                if (devourRingType < 1) devourRingType = 1;
                            }
                            if (foundValue.equals("SOUL_SEEKER_RING")) {
                                if (devourRingType < 2) devourRingType = 2;

                            }
                            if (foundValue.equals("SOUL_EATER_RING")) {
                                if (devourRingType < 3) devourRingType = 3;

                            }
                        }

                    }


                    double healAmount = damage * (devourRingType == 1 ? 0.02 : devourRingType == 2 ? 0.03 : devourRingType == 3 ? 0.04 : 0);
                    Bukkit.getServer().getPluginManager().callEvent(new EntityRegainHealthEvent(damager, healAmount, EntityRegainHealthEvent.RegainReason.CUSTOM));


                }


                Location location = new Location(e.getEntity().getWorld(), e.getEntity().getLocation().getX(), e.getEntity().getLocation().getY() + 0.7, e.getEntity().getLocation().getZ());
                ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(e.getEntity().getWorld().getName()).spawn(location, ArmorStand.class);
                String damageAsString = Float.toString((float) (Math.ceil(damage * 100) / 100));
                if (damager instanceof Player)
                    as.setCustomName(ChatColor.WHITE + "✧" + damageAsString.charAt(0) + ChatColor.YELLOW + damageAsString.charAt(1) + ChatColor.GOLD + damageAsString.charAt(2) + ChatColor.RED + damageAsString.substring(3) + ChatColor.WHITE + "✧");
                else as.setCustomName(ChatColor.GRAY + damageAsString);


                as.setCustomNameVisible(true);
                as.setVisible(false);
                as.setGravity(false);
                as.setMarker(true);


                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        as.remove();
                    }
                }, 20);


                NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                PersistentDataContainer container = e.getEntity().getPersistentDataContainer();


                String foundValue = null;
                if (container.has(key, PersistentDataType.STRING)) {
                    foundValue = container.get(key, PersistentDataType.STRING);
                }

                if (foundValue != null) {
                    if (foundValue.equals("MAGMA_CUBE_BOSS")) {
                        if (damager instanceof Player) {
                            magmaBossDamage.putIfAbsent((Player) damager, 0f);
                            float damage2 = magmaBossDamage.get(damager);
                            magmaBossDamage.put((Player) damager, (float) (damage2 + damage));
                        }
                    }
                }


                if (player instanceof Player) {
                    if (player.getHealth() - damage <= 0) {
                        CustomPlayerDeathEvent event = new CustomPlayerDeathEvent(player, CustomPlayerDeathEvent.DeathReason.PLAYER, damager);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                    } else {
                        player.setHealth(player.getHealth() - damage);
                    }
                } else {
                    if (player.getHealth() - damage <= 0) {
                        if (damager instanceof Player) {
                            lastKilledEntity.put(player, (Player) damager);
                        }

                        player.damage(1000000);

                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                            @Override
                            public void run() {
                                player.setHealth(0);
                            }
                        }, 2);
                    } else {
                        player.setHealth(player.getHealth() - damage);
                    }
                }


                if (damager.getType() == EntityType.ZOMBIE) {
                    if (((LivingEntity) e.getDamager()).getEquipment().getChestplate() != null) {
                        if (((LivingEntity) e.getDamager()).getEquipment().getChestplate().hasItemMeta()) {
                            if (((LivingEntity) e.getDamager()).getEquipment().getChestplate().getItemMeta().hasDisplayName()) {
                                if (((LivingEntity) e.getDamager()).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant")) {


                                    double finalDamage = damage1;
                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                        @Override
                                        public void run() {
                                            player.setNoDamageTicks(0);
                                            player.damage(finalDamage);
                                            player.setVelocity(damager.getLocation().getDirection());
                                            player.getVelocity().setY(1);
                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                @Override
                                                public void run() {
                                                    player.setNoDamageTicks(0);
                                                    player.damage(finalDamage);
                                                    player.setVelocity(damager.getLocation().getDirection());
                                                    player.getVelocity().setY(1);
                                                }
                                            }, 6);
                                        }
                                    }, 6);
                                }
                            }
                        }
                    }
                }
            }
        }
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

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();

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
            if (ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA+"Coal Mine") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.GOLD+"Gold Mine") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA+"Deep Caverns")) {
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



            if (ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA+"Forest")) {
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

            if (ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA+"Farm") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA+"Mountain") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA+"The Barn") || ColeCrafterSlayers.currentLocation.get(player).equals(ChatColor.AQUA+"Mushroom Desert")) {
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
                            block.setType(material);
                            block.setBlockData(data);
                        }
                    }, 200);
                }
            }
        }

        NumberFormat numberFormat = NumberFormat.getInstance();

        numberFormat.setGroupingUsed(true);

        if (miningXPAdd > 0) {

            Objective combatXp = scoreboard.getObjective("miningXP");
            Objective combatLevel = scoreboard.getObjective("miningLevel");


            Score combatXpScore = combatXp.getScore(player);
            Score combatLevelScore = combatLevel.getScore(player);

            if (combatXpScore == null) {
                combatXpScore.setScore(0);
            }

            if (combatLevelScore == null) {
                combatLevelScore.setScore(0);
            }





            combatXpScore.setScore(combatXp.getScore(player).getScore() + miningXPAdd);




            String combatXpmessage = "§3+" + miningXPAdd + " Mining (" + numberFormat.format(combatXpScore.getScore()) + "/" + numberFormat.format(combatLevelScore.getScore() == 50 ? numberFormat.format(Math.ceil(100 * (Math.pow(1.26, 48)) + 200 * 50)) : Math.ceil(100 * (Math.pow(1.26, combatLevelScore.getScore()))) + 200 * (combatLevelScore.getScore())) + ")";
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

            if (combatXpScore.getScore() >= Math.ceil(100 * (Math.pow(1.26, combatLevelScore.getScore())) + 200 * (combatLevelScore.getScore())) && (combatLevelScore.getScore() < 50)) {
                player.sendMessage(ChatColor.DARK_AQUA + "--------------------------------------\n" + ChatColor.AQUA + "" + ChatColor.BOLD + " SKILL LEVEL UP" + ChatColor.RESET + ""
                        + ChatColor.DARK_AQUA + " Mining " + ChatColor.DARK_GRAY + combatLevelScore.getScore() + "→ " + ChatColor.DARK_AQUA + (combatLevelScore.getScore() + 1) + ChatColor.GREEN + "" + ChatColor.BOLD + "\n Rewards\n  " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "+" + ChatColor.GREEN + "0.5 ❈ Defense\n" + ChatColor.DARK_AQUA + "--------------------------------------\n");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 0);
                combatLevelScore.setScore(combatLevelScore.getScore() + 1);


                UUID uuid = UUID.fromString("de1c2a12-e421-41f9-8728-d8556e7fde60");

                for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
                    if (attribute.getName().equals("miningSkill")) {
                        player.getAttribute(Attribute.GENERIC_ARMOR).removeModifier(attribute);

                    }

                }
                player.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier(uuid, "miningSkill", 0.5*combatLevelScore.getScore(), AttributeModifier.Operation.ADD_NUMBER));

            }
        }

        if (farmingXPAdd > 0) {

            Objective combatXp = scoreboard.getObjective("farmingXP");
            Objective combatLevel = scoreboard.getObjective("farmingLevel");


            Score combatXpScore = combatXp.getScore(player);
            Score combatLevelScore = combatLevel.getScore(player);

            if (combatXpScore == null) {
                combatXpScore.setScore(0);
            }

            if (combatLevelScore == null) {
                combatLevelScore.setScore(0);
            }






            combatXpScore.setScore(combatXp.getScore(player).getScore() + farmingXPAdd);

            String combatXpmessage = "§3+" + farmingXPAdd  + " Farming (" + numberFormat.format(combatXpScore.getScore()) + "/" + numberFormat.format(combatLevelScore.getScore() == 50 ? Math.ceil(100 * (Math.pow(1.26, 48)) + 400 * 50) : Math.ceil(100 * (Math.pow(1.26, combatLevelScore.getScore()))) + 400 * (combatLevelScore.getScore())) + ")";
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

            if (combatXpScore.getScore() >= Math.ceil(100 * (Math.pow(1.26, combatLevelScore.getScore())) + 400 * (combatLevelScore.getScore())) && (combatLevelScore.getScore() < 50)) {
                player.sendMessage(ChatColor.DARK_AQUA + "--------------------------------------\n" + ChatColor.AQUA + "" + ChatColor.BOLD + " SKILL LEVEL UP" + ChatColor.RESET + ""
                        + ChatColor.DARK_AQUA + " Farming " + ChatColor.DARK_GRAY + combatLevelScore.getScore() + "→ " + ChatColor.DARK_AQUA + (combatLevelScore.getScore() + 1) + ChatColor.GREEN + "" + ChatColor.BOLD + "\n Rewards\n  " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "+" + ChatColor.RED + "0.2 ❤ Health\n" + ChatColor.DARK_AQUA + "--------------------------------------\n");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 0);
                combatLevelScore.setScore(combatLevelScore.getScore() + 1);


                UUID uuid = UUID.fromString("c455ce96-55c0-46c0-904f-f2dae31f710c");

                for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
                    if (attribute.getName().equals("farmingSkill")) {
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(attribute);

                    }

                }
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(new AttributeModifier(uuid, "farmingSkill", 0.2*combatLevelScore.getScore(), AttributeModifier.Operation.ADD_NUMBER));

            }
        }

        if (forgagingXPAdd > 0) {

            Objective combatXp = scoreboard.getObjective("foragingXP");
            Objective combatLevel = scoreboard.getObjective("foragingLevel");


            Score combatXpScore = combatXp.getScore(player);
            Score combatLevelScore = combatLevel.getScore(player);

            if (combatXpScore == null) {
                combatXpScore.setScore(0);
            }

            if (combatLevelScore == null) {
                combatLevelScore.setScore(0);
            }






            combatXpScore.setScore(combatXp.getScore(player).getScore() + forgagingXPAdd);

            String combatXpmessage = "§3+" + forgagingXPAdd  + " Foraging (" + numberFormat.format(combatXpScore.getScore()) + "/" + numberFormat.format(combatLevelScore.getScore() == 50 ? Math.ceil(100 * (Math.pow(1.26, 48)) + 400 * 50) : Math.ceil(100 * (Math.pow(1.26, combatLevelScore.getScore()))) + 400 * (combatLevelScore.getScore())) + ")";
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

            if (combatXpScore.getScore() >= Math.ceil(100 * (Math.pow(1.26, combatLevelScore.getScore())) + 400 * (combatLevelScore.getScore())) && (combatLevelScore.getScore() < 50)) {
                player.sendMessage(ChatColor.DARK_AQUA + "--------------------------------------\n" + ChatColor.AQUA + "" + ChatColor.BOLD + " SKILL LEVEL UP" + ChatColor.RESET + ""
                        + ChatColor.DARK_AQUA + " Foraging " + ChatColor.DARK_GRAY + combatLevelScore.getScore() + "→ " + ChatColor.DARK_AQUA + (combatLevelScore.getScore() + 1) + ChatColor.GREEN + "" + ChatColor.BOLD + "\n Rewards\n  " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "+" + ChatColor.RED + "0.25 ❁ Damage\n" + ChatColor.DARK_AQUA + "--------------------------------------\n");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 0);
                combatLevelScore.setScore(combatLevelScore.getScore() + 1);


                UUID uuid = UUID.fromString("1f40a901-c26e-4144-b02e-e704c609b9e6");

                for (AttributeModifier attribute :  player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getModifiers()) {
                    if (attribute.getName().equals("foragingSkill")) {
                        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(attribute);

                    }

                }
                player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(new AttributeModifier(uuid, "foragingSkill", 0.25*combatLevelScore.getScore(), AttributeModifier.Operation.ADD_NUMBER));

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
            if (foundValue.equals("SLIME") || foundValue.equals("MAGMA_CUBE_BOSS")) {
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

}
