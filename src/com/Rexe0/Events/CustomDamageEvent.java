package com.Rexe0.Events;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.DefenseNerf;
import com.Rexe0.Slayers.SlayerMenu;
import com.Rexe0.Slayers.StatsCommand;
import net.minecraft.server.v1_16_R1.EntityLightning;
import net.minecraft.server.v1_16_R1.EntityTypes;
import net.minecraft.server.v1_16_R1.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_16_R1.Vec3D;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Random;

public class CustomDamageEvent extends Event implements Cancellable {
    private Entity damager;
    private Entity entity;
    private boolean isCancelled;
    private DamageType damageType;
    private DamageSource damageSource;
    private double amount = 0;
    private static final HandlerList handlers = new HandlerList();

    public CustomDamageEvent(Entity damager, Entity entity, DamageType damageType, DamageSource damageSource, double amount) {
        this.damager = damager;
        this.isCancelled = false;
        this.entity = entity;
        this.damageType = damageType;
        this.damageSource = damageSource;
        this.amount = amount;
    }


    public void execute() {

        if (this.damageSource == DamageSource.ENTITY) {

            Entity entity = this.entity;
            Entity theDamager = this.damager;

            NamespacedKey key2 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

            PersistentDataContainer container2 = entity.getPersistentDataContainer();


            String foundValue2 = null;
            if (container2.has(key2, PersistentDataType.STRING)) {
                foundValue2 = container2.get(key2, PersistentDataType.STRING);
            }

            if (foundValue2 != null) {
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        int health = (int) Math.ceil(((LivingEntity) entity).getHealth());
                        String customName = entity.getCustomName();

                        int maxHealth = (int) ((LivingEntity) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                        String color = health > maxHealth / 2 ? ChatColor.GREEN + "" : health <= maxHealth / 2 && health > maxHealth / 4 ? ChatColor.YELLOW + "" : health <= maxHealth / 4 ? ChatColor.RED + "" : "";

                        boolean hasHpBar = false;
                        for (int i = 0; i < customName.length(); i++) {
                            if (customName.charAt(i) == '❤') {
                                hasHpBar = true;
                                break;
                            }
                        }

                        if (!hasHpBar) {
                            entity.setCustomName(customName + " " + color + health + ChatColor.RED + "❤");
                        } else {
                            String[] splitName = customName.split(" ");
                            String finalCustomName = "";
                            for (String str : splitName) {
                                boolean isHp = false;
                                for (int i = 0; i < str.length(); i++) {
                                    if (str.charAt(i) == '❤') {
                                        isHp = true;
                                        break;
                                    }
                                }
                                if (!isHp) {
                                    finalCustomName += str + " ";
                                }


                            }

                            entity.setCustomName(finalCustomName + "" + color + health + ChatColor.RED + "❤");
                        }

                    }
                }, 1);

            }


            LivingEntity damager1 = null;
            if (theDamager instanceof Arrow) {
                damager1 = (LivingEntity) ((Arrow) theDamager).getShooter();
            } else if (theDamager instanceof Fireball) {
                damager1 = (LivingEntity) ((Fireball) theDamager).getShooter();
            } else if (theDamager instanceof LivingEntity) {
                damager1 = (LivingEntity) theDamager;
            }

            LivingEntity damager = damager1;
            if (damager instanceof Player) {
                if (entity instanceof Player) {
                    setCancelled(true);
                    return;
                }

                Player player = (Player) damager;
                ItemStack heldItem = player.getEquipment().getItemInMainHand();


                double attackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue();


                if (entity instanceof LivingEntity) {
                    attackSpeed = (((LivingEntity) entity).getMaximumNoDamageTicks() - ((((LivingEntity) entity).getMaximumNoDamageTicks() / 2f) * (attackSpeed / 100)));
                }


                double finalAttackSpeed = attackSpeed;
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        if (entity instanceof LivingEntity) {


                            ((LivingEntity) entity).setNoDamageTicks((int) finalAttackSpeed);
                        }
                    }
                }, 1);


                ItemStack heldItem1 = damager.getEquipment().getItemInMainHand();

                if (theDamager instanceof Fireball) {
                    setDamage(damager.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() / 6);
                }

                if (theDamager instanceof Arrow) {
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
                                    String foundValue3 = DefenseNerf.getItemType(heldItem1);
                                    if (foundValue3 != null) {

                                        if (foundValue3.equals("BOW")) {
                                            int powerLevel = heldItem1.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
                                            foundValue += foundValue1;
                                            foundValue += damager.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
                                            foundValue *= 1 + (0.08 * powerLevel);
                                            setDamage(foundValue);
                                        } else {
                                            int powerLevel = heldItem1.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
                                            foundValue += foundValue1;
                                            foundValue *= 1 + (0.08 * powerLevel);
                                            setDamage(foundValue * 0.75);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }


                float damageMultiplier = 0f;


                if (theDamager.getType() == EntityType.ARROW) {
                    if (theDamager.getCustomName() != null) {
                        if (theDamager.getCustomName().equals("MOSQUITO_1")) {
                            damageMultiplier += 0.19;
                            DefenseNerf.pestilence.putIfAbsent((Player) damager, 0f);
                            if (DefenseNerf.pestilence.get(player) > 11) {
                                DefenseNerf.pestilence.put((Player) damager, 0.11f);
                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (DefenseNerf.pestilence.get(player) <= 11) {
                                            DefenseNerf.pestilence.put((Player) damager, 0f);
                                        }
                                    }
                                }, 100);
                            }

                        }

                        if (theDamager.getCustomName().equals("SCORPION_1")) {
                            damageMultiplier += 9.0;
                        }

                        if (theDamager.getCustomName().equals("HURRICANE")) {
                            if (entity instanceof LivingEntity) {
                                if (!((LivingEntity) entity).isInvulnerable()) {
                                    Random rand = new Random();
                                    int n = rand.nextInt(20);
                                    if (n == 0) {
                                        double damage = getDamage();
                                        ((LivingEntity) entity).damage(damage, damager);

                                        EntityLightning lightning = new EntityLightning(EntityTypes.LIGHTNING_BOLT, ((CraftWorld) entity.getWorld()).getHandle());

                                        Vec3D vec = new Vec3D(0, 0, 0);

                                        PacketPlayOutSpawnEntity lightningPacket = new PacketPlayOutSpawnEntity(lightning.getId(), lightning.getUniqueID(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), 0f, 0f, EntityTypes.LIGHTNING_BOLT, 0, vec);
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
                        String foundValue = DefenseNerf.getItemID(heldItem);


                        int sharpnessLevel = heldItem.getEnchantmentLevel(Enchantment.DAMAGE_ALL);

                        damageMultiplier += (0.05 * sharpnessLevel);

                        if (entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.SKELETON || entity.getType() == EntityType.HUSK || entity.getType() == EntityType.STRAY || entity.getType() == EntityType.DROWNED || entity.getType() == EntityType.WITHER_SKELETON || entity.getType() == EntityType.ZOMBIFIED_PIGLIN || entity.getType() == EntityType.PHANTOM || entity.getType() == EntityType.ZOMBIE) {
                            int smiteLevel = heldItem.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);

                            damageMultiplier += (0.1 * smiteLevel);

                            if (foundValue != null) {
                                if (foundValue.equals("UNDEAD_SWORD")) {
                                    damageMultiplier += 1.0f;
                                }
                            }
                        }

                        if (entity.getType() == EntityType.ZOMBIE) {
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

                        if (entity.getType() == EntityType.ENDER_DRAGON || entity.getType() == EntityType.ENDERMAN || entity.getType() == EntityType.ENDERMITE) {
                            if (foundValue != null) {
                                if (foundValue.equals("END_SWORD")) {
                                    damageMultiplier += 1.0f;
                                }
                            }
                        }

                        if (entity.getType() == EntityType.SPIDER || entity.getType() == EntityType.CAVE_SPIDER || entity.getType() == EntityType.SILVERFISH) {

                            int arthropodsLevel = heldItem.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);

                            damageMultiplier += (0.1 * arthropodsLevel);
                        }


                        if (entity instanceof LivingEntity) {
                            boolean firstStrike = false;
                            boolean giantKiller = false;
                            boolean thunderLord = false;
                            for (ItemStack item : ((Player) damager).getInventory().getContents()) {
                                String foundValue1 = DefenseNerf.getItemID(item);

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

                            double maxHealth = ((LivingEntity) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                            double maxHealthPlayer = ((LivingEntity) damager).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                            if (firstStrike) {
                                if (((LivingEntity) entity).getHealth() == maxHealth) {
                                    damageMultiplier += 0.5;
                                }
                            }
                            if (giantKiller) {
                                if (maxHealth > maxHealthPlayer) {
                                    damageMultiplier += 0.25;
                                }
                            }

                            if (thunderLord) {
                                if (!((LivingEntity) entity).isInvulnerable()) {
                                    DefenseNerf.thunderlordHits.putIfAbsent(entity, 0);
                                    DefenseNerf.thunderlordHits.put(entity, DefenseNerf.thunderlordHits.get(entity) + 1);
                                    if (DefenseNerf.thunderlordHits.get(entity) == 3) {
                                        double damage = ((LivingEntity) theDamager).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
                                        ((LivingEntity) entity).damage(damage / 2, theDamager);


                                        EntityLightning lightning = new EntityLightning(EntityTypes.LIGHTNING_BOLT, ((CraftWorld) entity.getWorld()).getHandle());

                                        Vec3D vec = new Vec3D(0, 0, 0);

                                        PacketPlayOutSpawnEntity lightningPacket = new PacketPlayOutSpawnEntity(lightning.getId(), lightning.getUniqueID(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), 0f, 0f, EntityTypes.LIGHTNING_BOLT, 0, vec);
                                        ((CraftPlayer) ((Player) damager)).getHandle().playerConnection.sendPacket(lightningPacket);

                                        DefenseNerf.thunderlordHits.put(entity, 0);
                                    }
                                }
                            }


                            int damageBonus = 0;


                            if (DefenseNerf.isFullSet(player, "SUPERIOR_DRAGON_HELMET", "SUPERIOR_DRAGON_CHESTPLATE", "SUPERIOR_DRAGON_LEGGINGS", "SUPERIOR_DRAGON_BOOTS") && foundValue.equals("ASPECT_OF_THE_DRAGONS")) {
                                damageBonus += 10;
                            }

                            if (DefenseNerf.isFullSet(player, null, "ELEGANT_TUXEDO_CHESTPLATE", "ELEGANT_TUXEDO_LEGGINGS", "ELEGANT_TUXEDO_BOOTS")) {
                                damageMultiplier += 1.5;
                            }


                            setDamage((getDamage() + damageBonus) * (1 + damageMultiplier));
                            if (foundValue != null) {
                                if (foundValue.equals("FLAMING_SWORD")) {
                                    entity.setFireTicks(60);
                                }
                            }
                        }
                    }
                }


            }
            if (entity instanceof LivingEntity) {


                if (damager != null) {


                    LivingEntity player = (LivingEntity) entity;
                    if (damager instanceof LivingEntity) {
                        if (SlayerMenu.revenantHorrorPlayer.get(theDamager) != null) {
                            if (((LivingEntity) theDamager).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant")) {
                                if (SlayerMenu.revenantHorrorPlayer.get(theDamager).equals(player)) {
                                    DefenseNerf.antiNotHit.put((LivingEntity) damager, 0);
                                }
                            }
                        }
                    }


                    if (theDamager instanceof Arrow) {
                        if (damager instanceof Player) {
                            ((Player) damager).playSound(damager.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 2f, 0.8f);
                        }
                    }

                    double damage = getDamage();
                    double defensePoints = 0;


                    if (player instanceof Player) DefenseNerf.pestilence.putIfAbsent((Player) player, 0f);


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

                        ItemMeta pantsMeta = null;

                        ItemMeta bootsMeta = null;

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
                            String foundValue = DefenseNerf.getItemID(item);

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

                    if (this.damageType == DamageType.NORMAL) {
                        if (player instanceof Player) {

                            damage = damage * (1 - (defensePoints * (1 - DefenseNerf.pestilence.get(player)) / (20 + defensePoints * (1 - DefenseNerf.pestilence.get(player)))));


                        } else {
                            damage = damage * (1 - (defensePoints / (20 + defensePoints)));

                        }
                    } else if (this.damageType == DamageType.TRUE) {
                        double DamageReductionStat = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue();

                        damage = damage * (1 - (DamageReductionStat / (DamageReductionStat + 20)));

                    }


                    double damage1 = 0;
                    if (player instanceof Player) {
                        damage1 = damage / 4;

                    }


                    String foundValue1 = ColeCrafterSlayers.getMobType(player);

                    if (foundValue1 != null) {
                        if (foundValue1.equals("CREATURE")) {
                            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                            if (damage > maxHealth / 20) {
                                double extraDamage = damage - maxHealth / 20;
                                damage = maxHealth / 20 + (extraDamage / 20);
                            }

                            if (damage > maxHealth / 10) {
                                damage = maxHealth / 10;
                            }
                        }
                        if (foundValue1.equals("BOSS")) {
                            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                            if (damage > maxHealth / 50) {
                                double extraDamage = damage - maxHealth / 50;
                                damage = maxHealth / 50 + (extraDamage / 10);
                            }

                            if (damage > maxHealth / 25) {
                                damage = maxHealth / 25;
                            }
                        }
                    }


                    if (damager instanceof Player) {
                        byte devourRingType = 0;
                        for (ItemStack item : ((Player) damager).getInventory().getContents()) {
                            String foundValue = DefenseNerf.getItemID(item);

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


                    Location location = new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY() + 0.7, entity.getLocation().getZ());
                    ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(entity.getWorld().getName()).spawn(location, ArmorStand.class);
                    String damageAsString = Float.toString((float) (Math.ceil(damage * 100) / 100));
                    if (this.damageType == DamageType.TRUE) {
                        as.setCustomName(ChatColor.WHITE + damageAsString);
                    } else {
                        if (damager instanceof Player)
                            as.setCustomName(ChatColor.WHITE + "✧" + damageAsString.charAt(0) + ChatColor.YELLOW + damageAsString.charAt(1) + ChatColor.GOLD + damageAsString.charAt(2) + ChatColor.RED + damageAsString.substring(3) + ChatColor.WHITE + "✧");
                        else as.setCustomName(ChatColor.GRAY + damageAsString);
                    }

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


                    String foundValue = ColeCrafterSlayers.getMobID(player);

                    if (foundValue != null) {
                        if (foundValue.equals("MAGMA_CUBE_BOSS")) {
                            if (damager instanceof Player) {
                                DefenseNerf.magmaBossDamage.putIfAbsent((Player) damager, 0f);
                                float damage2 = DefenseNerf.magmaBossDamage.get(damager);
                                DefenseNerf.magmaBossDamage.put((Player) damager, (float) (damage2 + damage));
                            }
                        }
                        if (foundValue.equals("WITHERED_ARMOR")) {
                            if (damager instanceof Player) {
                                ((Player) damager).playSound(damager.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 2, 0);
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


                            player.damage(1000000);

                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                @Override
                                public void run() {
                                    player.setHealth(0);
                                }
                            }, 2);
                        } else {
                            if (damager instanceof Player) {
                                DefenseNerf.lastKilledEntity.put(player, (Player) damager);
                            }

                            player.setHealth(player.getHealth() - damage);
                        }
                    }


                    if (damager.getType() == EntityType.ZOMBIE) {
                        if (((LivingEntity) theDamager).getEquipment().getChestplate() != null) {
                            if (((LivingEntity) theDamager).getEquipment().getChestplate().hasItemMeta()) {
                                if (((LivingEntity) theDamager).getEquipment().getChestplate().getItemMeta().hasDisplayName()) {
                                    if (((LivingEntity) theDamager).getEquipment().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("Revenant")) {


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
        } else if (this.damageSource == DamageSource.FALL_DAMAGE) {
            if (this.entity instanceof LivingEntity) {
                LivingEntity player = (LivingEntity) this.entity;
                ItemStack boots = null;
                if (player instanceof Player) {
                    PlayerInventory inv = ((Player) player).getInventory();
                    boots = inv.getBoots();
                } else {
                    EntityEquipment inv = player.getEquipment();
                    boots = inv.getBoots();
                }

                int featherFallingLevel = 0;
                if (boots != null) {
                    featherFallingLevel = boots.getEnchantmentLevel(Enchantment.PROTECTION_FALL);

                }
                double maxHealth = Math.floor(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                float multiplier = (float) (0.04*getDamage());
                multiplier -= 0.2*featherFallingLevel;
                if (multiplier < 0) multiplier = 0;
                double damage = maxHealth*multiplier;


                Location location = new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY() + 0.7, entity.getLocation().getZ());
                ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(entity.getWorld().getName()).spawn(location, ArmorStand.class);
                String damageAsString = Float.toString((float) (Math.ceil(damage * 100) / 100));

                as.setCustomName(ChatColor.GRAY + damageAsString);

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

                if (player instanceof Player) {
                    if (player.getHealth() - damage <= 0) {
                        CustomPlayerDeathEvent event = new CustomPlayerDeathEvent(player, CustomPlayerDeathEvent.DeathReason.ENVIRONMENT, null);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                    } else {
                        player.setHealth(player.getHealth() - damage);
                    }
                } else {
                    if (player.getHealth() - damage <= 0) {

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

            }
        }
    }


    public double getDamage() {
        return this.amount;
    }

    public void setDamage(double damage) {
        this.amount = damage;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    public enum DamageType {
        NORMAL, // Displays as white to red gradient. Affected by everything
        TRUE // Displays as white numbers. Ignores Defence and Damage Reduction
    }

    public enum DamageSource {
        ENTITY,
        FALL_DAMAGE,
        FIRE
    }
}
