package com.Rexe0.Mobs;

import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.CustomItem;
import com.Rexe0.Items.Swords.Common.GoldenSword;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;
import java.util.UUID;


public class CustomMob {
    public static LivingEntity spawnMob(String mobID, Location loc) {
        switch (mobID.toUpperCase()) {
            case "LAPIS_ZOMBIE":
                Zombie lapisZombie = loc.getWorld().spawn(loc, Zombie.class);
                lapisZombie.setBaby(false);


                lapisZombie.getEquipment().setHelmet(new ItemStack(Material.BLUE_STAINED_GLASS, 1));
                ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setUnbreakable(true);
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                meta.setColor(Color.fromRGB(0, 0, 255));

                item.setItemMeta(meta);

                lapisZombie.getEquipment().setChestplate(item);


                item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
                meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setUnbreakable(true);
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                meta.setColor(Color.fromRGB(0, 0, 255));
                item.setItemMeta(meta);

                lapisZombie.getEquipment().setLeggings(item);

                item = new ItemStack(Material.LEATHER_BOOTS, 1);
                meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setUnbreakable(true);
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                meta.setColor(Color.fromRGB(0, 0, 255));
                item.setItemMeta(meta);


                lapisZombie.getEquipment().setBoots(item);

                lapisZombie.getEquipment().setHelmetDropChance(0);
                lapisZombie.getEquipment().setChestplateDropChance(0);
                lapisZombie.getEquipment().setLeggingsDropChance(0);
                lapisZombie.getEquipment().setBootsDropChance(0);

                NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                lapisZombie.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "LAPIS_ZOMBIE");


                NamespacedKey ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                lapisZombie.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 7);

                lapisZombie.setCustomNameVisible(true);
                lapisZombie.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv7"+ChatColor.DARK_GRAY+"] "+ChatColor.BLUE+"Lapis Zombie");

                lapisZombie.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));

                lapisZombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(3);

                return lapisZombie;
            case "ZOMBIE_PIGMAN":
                PigZombie pigman = loc.getWorld().spawn(loc, PigZombie.class);
                pigman.setBaby(false);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                pigman.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "ZOMBIE_PIGMAN");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                pigman.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 10);

                pigman.setCustomNameVisible(true);
                pigman.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv10"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Pigman");

                pigman.getEquipment().setItemInMainHand(CustomItem.getItemClass("FLAMING_SWORD"));
                pigman.getEquipment().setItemInMainHandDropChance(0);

                pigman.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(25);
                pigman.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(5);
                pigman.setHealth(25);


                return pigman;
            case "SLIME":
                Slime slime = loc.getWorld().spawn(loc, Slime.class);

                Random rand = new Random();
                int n = rand.nextInt(3);

                slime.setSize(n+3);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                slime.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "SLIME");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                slime.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, (n == 0 ? 5 : n == 1 ? 10 : n == 2 ? 15 : 1));

                slime.setCustomNameVisible(true);
                slime.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv"+(n == 0 ? 5 : n == 1 ? 10 : n == 2 ? 15 : 1)+ChatColor.DARK_GRAY+"] "+ChatColor.GREEN+"Slime");

                slime.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue((n == 0 ? 8 : n == 1 ? 15 : n == 2 ? 25 : 1));
                slime.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue((n == 0 ? 5 : n == 1 ? 7 : n == 2 ? 10 : 1));
                slime.setHealth((n == 0 ? 8 : n == 1 ? 15 : n == 2 ? 25 : 1));




                return slime;
            case "MINER_ZOMBIE":
                Zombie miner_zombie = loc.getWorld().spawn(loc, Zombie.class);
                miner_zombie.setBaby(false);

                item = new ItemStack(Material.DIAMOND_HELMET, 1);
                ItemMeta meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                miner_zombie.getEquipment().setHelmet(item);


                item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                miner_zombie.getEquipment().setChestplate(item);


                item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                miner_zombie.getEquipment().setLeggings(item);

                item = new ItemStack(Material.DIAMOND_BOOTS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                item.setItemMeta(meta1);


                miner_zombie.getEquipment().setBoots(item);

                miner_zombie.getEquipment().setHelmetDropChance(0);
                miner_zombie.getEquipment().setChestplateDropChance(0);
                miner_zombie.getEquipment().setLeggingsDropChance(0);
                miner_zombie.getEquipment().setBootsDropChance(0);



                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                miner_zombie.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "MINER_ZOMBIE");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                miner_zombie.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 15);

                miner_zombie.setCustomNameVisible(true);
                miner_zombie.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv15"+ChatColor.DARK_GRAY+"] "+ChatColor.AQUA+"Zombie");

                miner_zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(25);
                miner_zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(13);
                miner_zombie.setHealth(25);




                return miner_zombie;
            case "MINER_SKELETON":
                Skeleton miner_skele = loc.getWorld().spawn(loc, Skeleton.class);


                item = new ItemStack(Material.DIAMOND_HELMET, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                miner_skele.getEquipment().setHelmet(item);


                item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                miner_skele.getEquipment().setChestplate(item);


                item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                miner_skele.getEquipment().setLeggings(item);

                item = new ItemStack(Material.DIAMOND_BOOTS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                item.setItemMeta(meta1);


                miner_skele.getEquipment().setBoots(item);

                miner_skele.getEquipment().setHelmetDropChance(0);
                miner_skele.getEquipment().setChestplateDropChance(0);
                miner_skele.getEquipment().setLeggingsDropChance(0);
                miner_skele.getEquipment().setBootsDropChance(0);



                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                miner_skele.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "MINER_SKELETON");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                miner_skele.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 15);

                miner_skele.setCustomNameVisible(true);
                miner_skele.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv15"+ChatColor.DARK_GRAY+"] "+ChatColor.AQUA+"Skeleton");

                miner_skele.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(25);
                miner_skele.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
                miner_skele.setHealth(25);




                return miner_skele;
            case "MAGMA_CUBE_BOSS":
                MagmaCube magmaCubeBoss = loc.getWorld().spawn(loc, MagmaCube.class);



                magmaCubeBoss.setSize(23);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                magmaCubeBoss.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "MAGMA_CUBE_BOSS");

                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobType");

                magmaCubeBoss.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "CREATURE");

                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                magmaCubeBoss.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 100);

                magmaCubeBoss.setCustomNameVisible(true);
                magmaCubeBoss.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv100"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Magma Cube Boss");

                magmaCubeBoss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(300);
                magmaCubeBoss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                magmaCubeBoss.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(20);
                magmaCubeBoss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.05);
                magmaCubeBoss.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(200);
                magmaCubeBoss.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                magmaCubeBoss.setRemoveWhenFarAway(false);
                magmaCubeBoss.setHealth(300);




                return magmaCubeBoss;
            default:
                return null;

            case "STRONG_MINER_ZOMBIE":
                miner_zombie = loc.getWorld().spawn(loc, Zombie.class);
                miner_zombie.setBaby(false);


                item = new ItemStack(Material.DIAMOND_BLOCK, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1);

                miner_zombie.getEquipment().setHelmet(item);


                item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1);

                miner_zombie.getEquipment().setChestplate(item);


                item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1);

                miner_zombie.getEquipment().setLeggings(item);

                item = new ItemStack(Material.DIAMOND_BOOTS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                item.setItemMeta(meta1);
                item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1);

                miner_zombie.getEquipment().setBoots(item);

                miner_zombie.getEquipment().setHelmetDropChance(0);
                miner_zombie.getEquipment().setChestplateDropChance(0);
                miner_zombie.getEquipment().setLeggingsDropChance(0);
                miner_zombie.getEquipment().setBootsDropChance(0);



                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                miner_zombie.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "STRONG_MINER_ZOMBIE");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                miner_zombie.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 20);

                miner_zombie.setCustomNameVisible(true);
                miner_zombie.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv20"+ChatColor.DARK_GRAY+"] "+ChatColor.AQUA+"Zombie");

                miner_zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
                miner_zombie.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);
                miner_zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(15);
                miner_zombie.setHealth(30);




                return miner_zombie;
            case "STRONG_MINER_SKELETON":
                miner_skele = loc.getWorld().spawn(loc, Skeleton.class);


                item = new ItemStack(Material.DIAMOND_BLOCK, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1);

                miner_skele.getEquipment().setHelmet(item);


                item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1);

                miner_skele.getEquipment().setChestplate(item);


                item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1);

                miner_skele.getEquipment().setLeggings(item);

                item = new ItemStack(Material.DIAMOND_BOOTS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                item.setItemMeta(meta1);
                item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1);

                miner_skele.getEquipment().setBoots(item);

                miner_skele.getEquipment().setHelmetDropChance(0);
                miner_skele.getEquipment().setChestplateDropChance(0);
                miner_skele.getEquipment().setLeggingsDropChance(0);
                miner_skele.getEquipment().setBootsDropChance(0);



                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                miner_skele.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "STRONG_MINER_SKELETON");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                miner_skele.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 20);

                miner_skele.setCustomNameVisible(true);
                miner_skele.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv20"+ChatColor.DARK_GRAY+"] "+ChatColor.AQUA+"Skeleton");

                miner_skele.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
                miner_skele.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);
                miner_skele.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(13);
                miner_skele.setHealth(30);




                return miner_skele;

            case "WOLF":
                Wolf wolf = loc.getWorld().spawn(loc, Wolf.class);
                wolf.setAngry(true);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                wolf.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "WOLF");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                wolf.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 15);

                wolf.setCustomNameVisible(true);
                wolf.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv15"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Wolf");

                wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(25);
                wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(5);
                wolf.setHealth(25);

                return wolf;

            case "OLD_WOLF":
                wolf = loc.getWorld().spawn(loc, Wolf.class);
                wolf.setAngry(true);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                wolf.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "OLD_WOLF");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                wolf.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 50);

                wolf.setCustomNameVisible(true);
                wolf.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv50"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Old Wolf");

                wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(80);
                wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                wolf.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
                wolf.setHealth(80);


                return wolf;
            case "SPEEDY_SPIDER":
                Spider spider = loc.getWorld().spawn(loc, Spider.class);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                spider.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "SPEEDY_SPIDER");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                spider.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 4);

                spider.setCustomNameVisible(true);
                spider.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv4"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Speedy Spider");

                spider.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(16);
                spider.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(3);
                spider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
                spider.setHealth(16);


                return spider;
            case "SPIKY_SPIDER":
                spider = loc.getWorld().spawn(loc, Spider.class);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                spider.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "SPIKY_SPIDER");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                spider.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 3);

                spider.setCustomNameVisible(true);
                spider.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv3"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Spiky Spider");

                spider.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(16);
                spider.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
                spider.setHealth(16);


                item = new ItemStack(Material.DIAMOND_BOOTS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                item.setItemMeta(meta1);

                item.addUnsafeEnchantment(Enchantment.THORNS, 12);

                spider.getEquipment().setBoots(item);

                spider.getEquipment().setBootsDropChance(0);


                return spider;
            case "SPLITTER_SPIDER":
                spider = loc.getWorld().spawn(loc, Spider.class);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                spider.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "SPLITTER_SPIDER");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                spider.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 2);

                spider.setCustomNameVisible(true);
                spider.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv2"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Splitter Spider");

                spider.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(16);
                spider.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
                spider.setHealth(16);



                return spider;
            case "SAVAGE_SPIDER":
                spider = loc.getWorld().spawn(loc, Spider.class);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                spider.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "SAVAGE_SPIDER");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                spider.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 10);

                spider.setCustomNameVisible(true);
                spider.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv10"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Savage Spider");

                spider.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50);
                spider.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
                spider.setHealth(50);



                return spider;
            case "SILVERFISH":
                Silverfish silverfish = loc.getWorld().spawn(loc, Silverfish.class);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                silverfish.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "SILVERFISH");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                silverfish.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 1);

                silverfish.setCustomNameVisible(true);
                silverfish.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv1"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Silverfish");

                silverfish.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(5);
                silverfish.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);

                silverfish.setHealth(5);
                silverfish.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(0.25);



                return silverfish;
            case "BLAZE":
                Blaze blaze = loc.getWorld().spawn(loc, Blaze.class);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                blaze.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "BLAZE");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                blaze.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 25);

                blaze.setCustomNameVisible(true);
                blaze.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv25"+ChatColor.DARK_GRAY+"] "+ChatColor.GOLD+"Blaze");

                blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
                blaze.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                blaze.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(0.1);
                blaze.setHealth(40);


                return blaze;
            case "MAGMA_CUBE":
                MagmaCube magmaCube = loc.getWorld().spawn(loc, MagmaCube.class);

                rand = new Random();
                n = rand.nextInt(3);

                magmaCube.setSize(n+5);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                magmaCube.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "MAGMA_CUBE");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                int determineLevel = n == 0 ? 10 : n == 1 ? 20 : n == 2 ? 30 : 1;
                magmaCube.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, determineLevel);

                magmaCube.setCustomNameVisible(true);
                magmaCube.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv"+determineLevel +ChatColor.DARK_GRAY+"] "+ChatColor.GOLD+"Magma Cube");

                magmaCube.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue((n == 0 ? 16 : n == 1 ? 30 : n == 2 ? 50 : 1));
                magmaCube.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue((n == 0 ? 10 : n == 1 ? 14 : n == 2 ? 20 : 1));
                magmaCube.setHealth((n == 0 ? 16 : n == 1 ? 30 : n == 2 ? 50 : 1));




                return magmaCube;
            case "WITHERED_BONES":
                WitherSkeleton witherSkele = loc.getWorld().spawn(loc, WitherSkeleton.class);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                witherSkele.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "WITHERED_BONES");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                witherSkele.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 20);

                witherSkele.setCustomNameVisible(true);
                witherSkele.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv20"+ChatColor.DARK_GRAY+"] "+ChatColor.DARK_GRAY+"Withered Bones");

                witherSkele.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(25);
                witherSkele.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(30);
                witherSkele.setHealth(25);


                return witherSkele;
            case "WITHERED_ARMOR":
                witherSkele = loc.getWorld().spawn(loc, WitherSkeleton.class);




                ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                witherSkele.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "WITHERED_ARMOR");


                ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                witherSkele.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 20);

                witherSkele.setCustomNameVisible(true);
                witherSkele.setCustomName(ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"Lv32"+ChatColor.DARK_GRAY+"] "+ChatColor.DARK_GRAY+"Withered Armor");

                witherSkele.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50);
                witherSkele.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(0.9);
                witherSkele.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
                witherSkele.setHealth(50);



                item = new ItemStack(Material.NETHERITE_CHESTPLATE, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


                item.setItemMeta(meta1);

                witherSkele.getEquipment().setChestplate(item);




                item = new ItemStack(Material.NETHERITE_BOOTS, 1);
                meta1 = item.getItemMeta();
                meta1.setUnbreakable(true);
                meta1.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

                item.setItemMeta(meta1);

                witherSkele.getEquipment().setChestplateDropChance(0);

                witherSkele.getEquipment().setBootsDropChance(0);




                witherSkele.getEquipment().setBoots(item);


                return witherSkele;
        }
    }
}
