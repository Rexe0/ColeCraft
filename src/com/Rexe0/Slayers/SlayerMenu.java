package com.Rexe0.Slayers;

// Cybernetic Horror, Future, cyborg themed

import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.Swords.Common.AspectOfTheJerry;
import com.Rexe0.Items.Swords.Common.RogueSword;
import com.Rexe0.Items.Swords.Epic.EmberRod;
import com.Rexe0.Items.Swords.Legendary.ReaperScythe;
import com.Rexe0.Items.Swords.Rare.AspectOfTheEnd;
import com.Rexe0.Items.Swords.Uncommon.GrapplingHook;
import com.Rexe0.Items.Wands.WandOfHealing;
import com.Rexe0.Items.Wands.WandOfMending;
import com.Rexe0.Items.Wands.WandOfRejuvenation;
import com.Rexe0.Items.Wands.WandOfRestoration;
import com.Rexe0.Skull;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


@SuppressWarnings( "deprecation" )
public class SlayerMenu implements Listener {
    public static Inventory slayerMenu;
    public static HashMap<Player, Inventory> zombieSlayerMenu = new HashMap<>();
    public static HashMap<LivingEntity, Player> revenantHorrorPlayer = new HashMap<>();
    public static HashMap<Player, Inventory> bankerInv = new HashMap<>();



    public static void openBank(Player player) {
        bankerInv.put(player, Bukkit.createInventory(player, 36, ChatColor.DARK_GRAY+"Bank"));

        Inventory inv = bankerInv.get(player);


        

        long coins1 = ColeCrafterSlayers.getBank(player);


        NumberFormat numberFormat = NumberFormat.getInstance();

        numberFormat.setGroupingUsed(true);

        String formattedCoins = numberFormat.format(coins1);




        for (int i = 0; i < 36; i++) {

            if (i == 11) {
                ItemStack item = new ItemStack(Material.CHEST, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN+"Deposit Coins");
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY+"Current balance: "+ChatColor.GOLD+formattedCoins+".0");
                lore.add(ChatColor.GRAY+" ");
                lore.add(ChatColor.GRAY+"Store coins in the bank to");
                lore.add(ChatColor.GRAY+"keep them safe while you go");
                lore.add(ChatColor.GRAY+"on adventures!");
//                            lore.add(ChatColor.GRAY+" ");
//                            lore.add(ChatColor.GRAY+"You will earn "+ChatColor.AQUA+"2% "+ChatColor.GRAY+"interest every");
//                            lore.add(ChatColor.GRAY+"season for your first "+ChatColor.GOLD+"10 million");
//                            lore.add(ChatColor.GRAY+"banked coins.");
//                            lore.add(ChatColor.GRAY+" ");
//                            lore.add(ChatColor.GRAY+"Until interest: "+ChatColor.AQUA+"31h");
                lore.add(ChatColor.GRAY+" ");
                lore.add(ChatColor.YELLOW+"Click to make a deposit!");

                meta.setLore(lore);
                item.setItemMeta(meta);

                inv.setItem(i, item);
            } else if (i == 13) {
                ItemStack item = new ItemStack(Material.DISPENSER, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN+"Withdraw Coins");
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY+"Current balance: "+ChatColor.GOLD+formattedCoins+".0");
                lore.add(ChatColor.GRAY+" ");
                lore.add(ChatColor.GRAY+"Take your coins out of the");
                lore.add(ChatColor.GRAY+"bank in order to spend");
                lore.add(ChatColor.GRAY+"them.");
                lore.add(ChatColor.GRAY+" ");
                lore.add(ChatColor.YELLOW+"Click to withdraw coins!");

                meta.setLore(lore);
                item.setItemMeta(meta);

                inv.setItem(i, item);
            } else if (i == 31) {
                ItemStack item = new ItemStack(Material.BARRIER, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED+"Close");
                item.setItemMeta(meta);

                inv.setItem(i, item);
            } else if (i == 32) {
                ItemStack item = new ItemStack(Material.REDSTONE_TORCH, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN+"Information");
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY+"Keep your coins safe in the bank!");
                lore.add(ChatColor.GRAY+"You lose half the coins in your");
                lore.add(ChatColor.GRAY+"purse when dying in combat.");
                lore.add(ChatColor.GRAY+" ");
                lore.add(ChatColor.GRAY+"Balance limit: "+ChatColor.GOLD+"50 Million");
//                            lore.add(ChatColor.GRAY+" ");
//                            lore.add(ChatColor.GRAY+"The banker rewards you every 31");
//                            lore.add(ChatColor.GRAY+"hours with "+ChatColor.AQUA+"interest "+ChatColor.GRAY+"for the");
//                            lore.add(ChatColor.GRAY+"coins in your bank balance");
//                            lore.add(ChatColor.GRAY+" ");
//                            lore.add(ChatColor.GRAY+"Interest in: "+ChatColor.AQUA+"31h");
//                            lore.add(ChatColor.GRAY+"Projected: "+ChatColor.GOLD+"coins "+ChatColor.AQUA+"(2%)");

                meta.setLore(lore);
                item.setItemMeta(meta);

                inv.setItem(i, item);
            } else {
                inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            }
        }
        bankerInv.put(player, inv);
        player.openInventory(bankerInv.get(player));
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ColeCrafterSlayers.getInstance().setupScoreboard(e.getPlayer());
    }


    @EventHandler
    public void onGrapple(PlayerFishEvent e) {

        ItemStack item = e.getPlayer().getEquipment().getItemInMainHand();

        Player entity = e.getPlayer();

        FishHook fishHook = e.getHook();
        if (item == null) return;
        if (!item.hasItemMeta()) return;
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemID");
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if (!(e.getState() == PlayerFishEvent.State.REEL_IN)) return;

        String foundValue = null;
        if(container.has(key, PersistentDataType.STRING)) {
            foundValue = container.get(key, PersistentDataType.STRING);
        }

        if (foundValue != null) {
            if (foundValue.equals("GRAPPLING_HOOK")) {
                GrapplingHook.grappleCooldown.putIfAbsent(entity, false);
                if (!GrapplingHook.grappleCooldown.get(entity)) {
                    GrapplingHook.grappleCooldown.put(entity, true);
                    Location origin = entity.getLocation();
                    origin.setY(entity.getLocation().getY() - 2);


                    Location target1 = fishHook.getLocation();
                    target1.setY(fishHook.getLocation().getY());
                    Vector target = target1.toVector();
                    origin.setDirection((target.subtract(origin.toVector())));


                    entity.setVelocity(origin.getDirection());

                    if (origin.getDirection().getX() > 0) {
                        origin.getDirection().setX(origin.getDirection().getX() + 10);
                    } else if (origin.getDirection().getX() < 0) {
                        origin.getDirection().setX(origin.getDirection().getX() - 10);
                    }
                    if (origin.getDirection().getZ() > 0) {
                        origin.getDirection().setZ(origin.getDirection().getZ() + 10);
                    } else if (origin.getDirection().getZ() < 0) {
                        origin.getDirection().setZ(origin.getDirection().getZ() - 10);
                    }


                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            Vector originVector = origin.getDirection();
                            originVector = origin.getDirection().multiply(3);
                            originVector.setY(0.5);
                            entity.setVelocity(originVector);
                        }
                    }, 1);

                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            GrapplingHook.grappleCooldown.put(entity, false);
                        }
                    }, 40);

                } else {
                    entity.sendMessage(ChatColor.RED+"This is on cooldown.");
                }
            }
        }

    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if(e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.FARMLAND)
            e.setCancelled(true);
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.CRAFTING_TABLE)) {
                e.setCancelled(true);
                e.getPlayer().performCommand("craft");
            }
            if (e.getClickedBlock().getType().equals(Material.ANVIL)) {
                e.setCancelled(true);
                e.getPlayer().performCommand("anvil");
            }
        }
        ItemStack item = e.getItem();
        if (item == null) return;
        if (!item.hasItemMeta()) return;
        NamespacedKey key = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemID");
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();



        String foundValue = null;
        if(container.has(key, PersistentDataType.STRING)) {
            foundValue = container.get(key, PersistentDataType.STRING);
        }

        if (foundValue == null) return;


        if (foundValue.equals("ROGUE_SWORD")) {
            RogueSword.use(e.getAction(), e.getPlayer());
        }

        if (foundValue.equals("ASPECT_OF_THE_JERRY")) {
            AspectOfTheJerry.use(e.getAction(), e.getPlayer());
        }

        if (foundValue.equals("EMBER_ROD")) {
            EmberRod.use(e.getAction(), e.getPlayer());
        }

        if (foundValue.equals("WAND_OF_HEALING")) {
            WandOfHealing.use(e.getAction(), e.getPlayer());
        }

        if (foundValue.equals("WAND_OF_MENDING")) {
            WandOfMending.use(e.getAction(), e.getPlayer());
        }

        if (foundValue.equals("WAND_OF_RESTORATION")) {
            WandOfRestoration.use(e.getAction(), e.getPlayer());
        }

        if (foundValue.equals("WAND_OF_REJUVENATION")) {
            WandOfRejuvenation.use(e.getAction(), e.getPlayer());
        }

        if (foundValue.equals("ASPECT_OF_THE_END")) {
            AspectOfTheEnd.use(e.getAction(), e.getPlayer());
        }


        if (foundValue.equals("REAPER_SCYTHE")) {
            ReaperScythe.use(e.getAction(), e.getPlayer());
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.setCancelled(true);
            }
        }

    }




    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(zombieSlayerMenu.get(e.getWhoClicked()))) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getType() == Material.ROTTEN_FLESH) {
                    if (e.getCurrentItem().hasItemMeta()) {
                        if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Revenant Horror I")) {
                                Player player = (Player) e.getWhoClicked();

                                ItemStack diamondStack = player.getItemInHand();

                                if (diamondStack.getType() == Material.DIAMOND) {
                                    if (diamondStack.getAmount() == 1) {
                                        player.setItemInHand(new ItemStack(Material.AIR));
                                    } else if (diamondStack.getAmount() > 1) {
                                        diamondStack.setAmount(diamondStack.getAmount()-1);
                                        player.setItemInHand(diamondStack);
                                    }
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                                    player.sendMessage(ChatColor.DARK_PURPLE +""+ChatColor.BOLD+"Slayer Quest Started!");
                                    player.sendMessage(ChatColor.RED + "Boss spawning in 10 seconds");

                                    player.closeInventory();

                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                        @Override
                                        public void run() {
                                            player.getLocation().getWorld().spawnParticle(Particle.SPELL_WITCH, player.getLocation(), 400, 0.3, 0.5, 0.3);
                                            player.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 300, 0.5, 1, 0.5);
                                            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1, 1);

                                            LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                            ((Zombie)revenantHorror).setBaby(false);


                                            revenantHorrorPlayer.put(revenantHorror, player);

                                            revenantHorror.setCustomNameVisible(true);
                                            revenantHorror.setCustomName(ChatColor.RED+"☠"+ChatColor.AQUA+" Revenant Horror "+ChatColor.GREEN+"30/30"+ChatColor.RED+"❤");

                                            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
                                            ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                            meta.setDisplayName("Revenant");
                                            chestplate.setItemMeta(meta);

                                            revenantHorror.getEquipment().setChestplate(chestplate);
                                            revenantHorror.getEquipment().setChestplateDropChance(0);

                                            ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                            leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                            revenantHorror.getEquipment().setLeggings(leggings);
                                            revenantHorror.getEquipment().setLeggingsDropChance(0);

                                            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                                            boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                                            revenantHorror.getEquipment().setBoots(boots);
                                            revenantHorror.getEquipment().setBootsDropChance(0);

                                            ItemStack weapon = new ItemStack(Material.DIAMOND_HOE);
                                            weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                                            revenantHorror.getEquipment().setItemInMainHand(weapon);
                                            revenantHorror.getEquipment().setItemInMainHandDropChance(0);



                                            revenantHorror.getEquipment().setHelmet(Skull.getSkull("http://textures.minecraft.net/texture/d8bee23b5c726ae8e3d021e8b4f7525619ab102a4e04be983b61414349aaac67"));
                                            revenantHorror.getEquipment().setHelmetDropChance(0);


                                            revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
                                            revenantHorror.setHealth(30);

                                            revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
                                            revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(100);
                                            revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                            revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()*1.5);

                                            NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "REVENANT_HORROR");


                                            NamespacedKey ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 10);
                                        }
                                    }, 200);
                                } else {
                                    player.sendMessage(ChatColor.RED+"You cannot afford this. (Hold the diamonds)");
                                }
                            }
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW+"Revenant Horror II")) {
                                Player player = (Player) e.getWhoClicked();

                                ItemStack diamondStack = player.getItemInHand();

                                if (diamondStack.getType() == Material.DIAMOND && diamondStack.getAmount() >= 4) {
                                    if (diamondStack.getAmount() == 4) {
                                        player.setItemInHand(new ItemStack(Material.AIR));
                                    } else if (diamondStack.getAmount() > 4) {
                                        diamondStack.setAmount(diamondStack.getAmount()-4);
                                        player.setItemInHand(diamondStack);
                                    }
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                                    player.sendMessage(ChatColor.DARK_PURPLE +""+ChatColor.BOLD+"Slayer Quest Started!");
                                    player.sendMessage(ChatColor.RED + "Boss spawning in 10 seconds");

                                    player.closeInventory();

                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                        @Override
                                        public void run() {
                                            player.getLocation().getWorld().spawnParticle(Particle.SPELL_WITCH, player.getLocation(), 400, 0.3, 0.5, 0.3);
                                            player.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 300, 0.5, 1, 0.5);
                                            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1, 1);

                                            LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                            ((Zombie)revenantHorror).setBaby(false);


                                            revenantHorrorPlayer.put(revenantHorror, player);

                                            revenantHorror.setCustomNameVisible(true);
                                            revenantHorror.setCustomName(ChatColor.RED+"☠"+ChatColor.AQUA+" Revenant Horror "+ChatColor.GREEN+"90/90"+ChatColor.RED+"❤");

                                            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
                                            ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                            meta.setDisplayName("Revenant");
                                            chestplate.setItemMeta(meta);

                                            revenantHorror.getEquipment().setChestplate(chestplate);
                                            revenantHorror.getEquipment().setChestplateDropChance(0);

                                            ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                            leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                                            revenantHorror.getEquipment().setLeggings(leggings);
                                            revenantHorror.getEquipment().setLeggingsDropChance(0);

                                            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                                            boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                                            revenantHorror.getEquipment().setBoots(boots);
                                            revenantHorror.getEquipment().setBootsDropChance(0);

                                            ItemStack weapon = new ItemStack(Material.DIAMOND_HOE);
                                            weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                            revenantHorror.getEquipment().setItemInMainHand(weapon);
                                            revenantHorror.getEquipment().setItemInMainHandDropChance(0);



                                            revenantHorror.getEquipment().setHelmet(Skull.getSkull("http://textures.minecraft.net/texture/d8bee23b5c726ae8e3d021e8b4f7525619ab102a4e04be983b61414349aaac67"));
                                            revenantHorror.getEquipment().setHelmetDropChance(0);


                                            revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(90);
                                            revenantHorror.setHealth(90);

                                            revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(5);
                                            revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(100);
                                            revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                            revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()*1.5);

                                            NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "REVENANT_HORROR");


                                            NamespacedKey ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 70);
                                        }
                                    }, 200);
                                } else {
                                    player.sendMessage(ChatColor.RED+"You cannot afford this. (Hold the diamonds)");
                                }
                            }
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"Revenant Horror III")) {
                                Player player = (Player) e.getWhoClicked();

                                ItemStack diamondStack = player.getItemInHand();

                                if (diamondStack.getType() == Material.DIAMOND && diamondStack.getAmount() >= 16) {
                                    if (diamondStack.getAmount() == 16) {
                                        player.setItemInHand(new ItemStack(Material.AIR));
                                    } else if (diamondStack.getAmount() > 16) {
                                        diamondStack.setAmount(diamondStack.getAmount()-16);
                                        player.setItemInHand(diamondStack);
                                    }
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                                    player.sendMessage(ChatColor.DARK_PURPLE +""+ChatColor.BOLD+"Slayer Quest Started!");
                                    player.sendMessage(ChatColor.RED + "Boss spawning in 10 seconds");

                                    player.closeInventory();

                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                        @Override
                                        public void run() {
                                            player.getLocation().getWorld().spawnParticle(Particle.SPELL_WITCH, player.getLocation(), 400, 0.3, 0.5, 0.3);
                                            player.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 300, 0.5, 1, 0.5);
                                            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, 1);



                                            LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                            ((Zombie)revenantHorror).setBaby(false);


                                            revenantHorrorPlayer.put(revenantHorror, player);

                                            revenantHorror.setCustomNameVisible(true);
                                            revenantHorror.setCustomName(ChatColor.RED+"☠"+ChatColor.AQUA+" Revenant Horror "+ChatColor.GREEN+"300/300"+ChatColor.RED+"❤");

                                            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
                                            ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                            meta.setDisplayName("Revenant");
                                            chestplate.setItemMeta(meta);

                                            revenantHorror.getEquipment().setChestplate(chestplate);
                                            revenantHorror.getEquipment().setChestplateDropChance(0);

                                            ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                            leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                                            ItemMeta lmeta = leggings.getItemMeta();
                                            lmeta.setDisplayName("0");
                                            leggings.setItemMeta(lmeta);

                                            revenantHorror.getEquipment().setLeggings(leggings);
                                            revenantHorror.getEquipment().setLeggingsDropChance(0);

                                            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                                            boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                                            revenantHorror.getEquipment().setBoots(boots);
                                            revenantHorror.getEquipment().setBootsDropChance(0);

                                            ItemStack weapon = new ItemStack(Material.DIAMOND_HOE);
                                            weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                            revenantHorror.getEquipment().setItemInMainHand(weapon);
                                            revenantHorror.getEquipment().setItemInMainHandDropChance(0);



                                            revenantHorror.getEquipment().setHelmet(Skull.getSkull("http://textures.minecraft.net/texture/d8bee23b5c726ae8e3d021e8b4f7525619ab102a4e04be983b61414349aaac67"));
                                            revenantHorror.getEquipment().setHelmetDropChance(0);


                                            revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(300);
                                            revenantHorror.setHealth(300);

                                            revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
                                            revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(100);
                                            revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                            revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()*1.5);

                                            NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "REVENANT_HORROR");


                                            NamespacedKey ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 310);

                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (revenantHorror.isValid()) {
                                                        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                                                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
                                                        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 80, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                        meta.setDisplayName("Revenant");
                                                        meta.setUnbreakable(true);
                                                        meta.setColor(Color.fromRGB(255, 0, 0));
                                                        chestplate.setItemMeta(meta);

                                                        revenantHorror.getEquipment().setChestplate(chestplate);

                                                        revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                                                        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, 1);

                                                        Location loc = revenantHorror.getLocation();
                                                        loc.setY(loc.getY()+2);
                                                        ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(revenantHorror.getWorld().getName()).spawn(loc, ArmorStand.class);

                                                        as.setCustomName(ChatColor.DARK_RED+"Enraged!");


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

                                                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                                chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
                                                                ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                meta.setDisplayName("Revenant");
                                                                chestplate.setItemMeta(meta);

                                                                revenantHorror.getEquipment().setChestplate(chestplate);

                                                                revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);

                                                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        if (revenantHorror.isValid()) {
                                                                            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                                                                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
                                                                            LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 80, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                            meta.setDisplayName("Revenant");
                                                                            meta.setUnbreakable(true);
                                                                            meta.setColor(Color.fromRGB(255, 0, 0));
                                                                            chestplate.setItemMeta(meta);

                                                                            revenantHorror.getEquipment().setChestplate(chestplate);

                                                                            revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                                                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, 1);

                                                                            Location loc = revenantHorror.getLocation();
                                                        loc.setY(loc.getY()+2);
                                                        ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(revenantHorror.getWorld().getName()).spawn(loc, ArmorStand.class);

                                                                            as.setCustomName(ChatColor.DARK_RED+"Enraged!");


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

                                                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                                                    chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
                                                                                    ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                                    meta.setDisplayName("Revenant");
                                                                                    chestplate.setItemMeta(meta);

                                                                                    revenantHorror.getEquipment().setChestplate(chestplate);

                                                                                    revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
                                                                                }
                                                                            }, 200);
                                                                        }
                                                                    }
                                                                }, 600);
                                                            }
                                                        }, 200);
                                                    }
                                                }
                                            }, 600);
                                        }
                                    }, 200);
                                } else {
                                    player.sendMessage(ChatColor.RED + "You cannot afford this. (Hold the diamonds)");
                                }
                            }
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_RED+"Revenant Horror IV")) {
                                Player player = (Player) e.getWhoClicked();

                                ItemStack diamondStack = player.getItemInHand();

                                if (diamondStack.getType() == Material.DIAMOND && diamondStack.getAmount() >= 32) {
                                    if (diamondStack.getAmount() == 32) {
                                        player.setItemInHand(new ItemStack(Material.AIR));
                                    } else if (diamondStack.getAmount() > 32) {
                                        diamondStack.setAmount(diamondStack.getAmount()-32);
                                        player.setItemInHand(diamondStack);
                                    }
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                                    player.sendMessage(ChatColor.DARK_PURPLE +""+ChatColor.BOLD+"Slayer Quest Started!");
                                    player.sendMessage(ChatColor.RED + "Boss spawning in 10 seconds");

                                    player.closeInventory();

                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                        @Override
                                        public void run() {
                                            player.getLocation().getWorld().spawnParticle(Particle.SPELL_WITCH, player.getLocation(), 400, 0.3, 0.5, 0.3);
                                            player.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 300, 0.5, 1, 0.5);
                                            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, 1);



                                            LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                            ((Zombie)revenantHorror).setBaby(false);


                                            revenantHorrorPlayer.put(revenantHorror, player);

                                            revenantHorror.setCustomNameVisible(true);
                                            revenantHorror.setCustomName(ChatColor.RED+"☠"+ChatColor.AQUA+" Revenant Horror "+ChatColor.GREEN+"900/900"+ChatColor.RED+"❤");

                                            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
                                            ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                            meta.setDisplayName("Revenant");
                                            chestplate.setItemMeta(meta);

                                            revenantHorror.getEquipment().setChestplate(chestplate);
                                            revenantHorror.getEquipment().setChestplateDropChance(0);

                                            ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                            leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                                            ItemMeta lmeta = leggings.getItemMeta();
                                            lmeta.setDisplayName("0");
                                            leggings.setItemMeta(lmeta);

                                            revenantHorror.getEquipment().setLeggings(leggings);
                                            revenantHorror.getEquipment().setLeggingsDropChance(0);

                                            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                                            boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                                            revenantHorror.getEquipment().setBoots(boots);
                                            revenantHorror.getEquipment().setBootsDropChance(0);

                                            ItemStack weapon = new ItemStack(Material.DIAMOND_HOE);
                                            weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                            revenantHorror.getEquipment().setItemInMainHand(weapon);
                                            revenantHorror.getEquipment().setItemInMainHandDropChance(0);



                                            revenantHorror.getEquipment().setHelmet(Skull.getSkull("http://textures.minecraft.net/texture/d8bee23b5c726ae8e3d021e8b4f7525619ab102a4e04be983b61414349aaac67"));
                                            revenantHorror.getEquipment().setHelmetDropChance(0);


                                            revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(900);
                                            revenantHorror.setHealth(900);

                                            revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(30);
                                            revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(100);
                                            revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                            revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()*1.5);

                                            NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "REVENANT_HORROR");


                                            NamespacedKey ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 610);

                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (revenantHorror.isValid()) {
                                                        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                                                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
                                                        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 80, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                        meta.setDisplayName("Revenant");
                                                        meta.setUnbreakable(true);
                                                        meta.setColor(Color.fromRGB(255, 0, 0));
                                                        chestplate.setItemMeta(meta);

                                                        revenantHorror.getEquipment().setChestplate(chestplate);

                                                        revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(60);
                                                        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, 1);

                                                        Location loc = revenantHorror.getLocation();
                                                        loc.setY(loc.getY()+2);
                                                        ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(revenantHorror.getWorld().getName()).spawn(loc, ArmorStand.class);

                                                        as.setCustomName(ChatColor.DARK_RED+"Enraged!");


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

                                                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                                chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
                                                                ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                meta.setDisplayName("Revenant");
                                                                chestplate.setItemMeta(meta);

                                                                revenantHorror.getEquipment().setChestplate(chestplate);

                                                                revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(30);

                                                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        if (revenantHorror.isValid()) {
                                                                            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                                                                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
                                                                            LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 80, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                            meta.setDisplayName("Revenant");
                                                                            meta.setUnbreakable(true);
                                                                            meta.setColor(Color.fromRGB(255, 0, 0));
                                                                            chestplate.setItemMeta(meta);

                                                                            revenantHorror.getEquipment().setChestplate(chestplate);

                                                                            revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(60);
                                                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, 1);

                                                                            Location loc = revenantHorror.getLocation();
                                                                            loc.setY(loc.getY()+2);
                                                                            ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(revenantHorror.getWorld().getName()).spawn(loc, ArmorStand.class);

                                                                            as.setCustomName(ChatColor.DARK_RED+"Enraged!");


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

                                                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                                                    chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
                                                                                    ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                                    meta.setDisplayName("Revenant");
                                                                                    chestplate.setItemMeta(meta);

                                                                                    revenantHorror.getEquipment().setChestplate(chestplate);

                                                                                    revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(30);
                                                                                }
                                                                            }, 200);
                                                                        }
                                                                    }
                                                                }, 600);
                                                            }
                                                        }, 200);
                                                    }
                                                }
                                            }, 600);
                                        }
                                    }, 200);
                                } else {
                                    player.sendMessage(ChatColor.RED + "You cannot afford this. (Hold the diamonds)");
                                }
                            }
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE+"Revenant Horror V")) {
                                Player player = (Player) e.getWhoClicked();

                                ItemStack diamondStack = player.getItemInHand();

                                if (diamondStack.getType() == Material.DIAMOND && diamondStack.getAmount() >= 64) {
                                    if (diamondStack.getAmount() == 64) {
                                        player.setItemInHand(new ItemStack(Material.AIR));
                                    } else if (diamondStack.getAmount() > 64) {
                                        diamondStack.setAmount(diamondStack.getAmount()-64);
                                        player.setItemInHand(diamondStack);
                                    }
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                                    player.sendMessage(ChatColor.DARK_PURPLE +""+ChatColor.BOLD+"Slayer Quest Started!");
                                    player.sendMessage(ChatColor.RED + "Boss spawning in 10 seconds");

                                    player.closeInventory();

                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                        @Override
                                        public void run() {
                                            player.getLocation().getWorld().spawnParticle(Particle.SPELL_WITCH, player.getLocation(), 400, 0.3, 0.5, 0.3);
                                            player.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 300, 0.5, 1, 0.5);
                                            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, 1);



                                            LivingEntity revenantHorror = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

                                            ((Zombie)revenantHorror).setBaby(false);


                                            revenantHorrorPlayer.put(revenantHorror, player);

                                            revenantHorror.setCustomNameVisible(true);
                                            revenantHorror.setCustomName(ChatColor.RED+"☠"+ChatColor.AQUA+" Revenant Horror "+ChatColor.GREEN+"2100/2100"+ChatColor.RED+"❤");

                                            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9);
                                            ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                            meta.setDisplayName("Revenant");
                                            chestplate.setItemMeta(meta);

                                            revenantHorror.getEquipment().setChestplate(chestplate);
                                            revenantHorror.getEquipment().setChestplateDropChance(0);

                                            ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                                            leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                                            ItemMeta lmeta = leggings.getItemMeta();
                                            lmeta.setDisplayName("0");
                                            leggings.setItemMeta(lmeta);

                                            revenantHorror.getEquipment().setLeggings(leggings);
                                            revenantHorror.getEquipment().setLeggingsDropChance(0);

                                            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                                            boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                                            revenantHorror.getEquipment().setBoots(boots);
                                            revenantHorror.getEquipment().setBootsDropChance(0);

                                            ItemStack weapon = new ItemStack(Material.DIAMOND_HOE);
                                            weapon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

                                            revenantHorror.getEquipment().setItemInMainHand(weapon);
                                            revenantHorror.getEquipment().setItemInMainHandDropChance(0);



                                            revenantHorror.getEquipment().setHelmet(Skull.getSkull("http://textures.minecraft.net/texture/d8bee23b5c726ae8e3d021e8b4f7525619ab102a4e04be983b61414349aaac67"));
                                            revenantHorror.getEquipment().setHelmetDropChance(0);


                                            revenantHorror.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2100);
                                            revenantHorror.setHealth(2100);

                                            revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(40);
                                            revenantHorror.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(100);
                                            revenantHorror.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.9);

                                            revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(revenantHorror.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()*1.5);

                                            NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobID");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, "REVENANT_HORROR");


                                            NamespacedKey ItemIDKey1 = new NamespacedKey(ColeCrafterSlayers.getInstance(), "mobLevel");

                                            revenantHorror.getPersistentDataContainer().set(ItemIDKey1, PersistentDataType.INTEGER, 890);

                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (revenantHorror.isValid()) {
                                                        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                                                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9);
                                                        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 80, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                        meta.setDisplayName("Revenant");
                                                        meta.setUnbreakable(true);
                                                        meta.setColor(Color.fromRGB(255, 0, 0));
                                                        chestplate.setItemMeta(meta);

                                                        revenantHorror.getEquipment().setChestplate(chestplate);

                                                        revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(80);
                                                        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, 1);

                                                        Location loc = revenantHorror.getLocation();
                                                        loc.setY(loc.getY()+2);
                                                        ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(revenantHorror.getWorld().getName()).spawn(loc, ArmorStand.class);

                                                        as.setCustomName(ChatColor.DARK_RED+"Enraged!");


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

                                                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                                chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9);
                                                                ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                meta.setDisplayName("Revenant");
                                                                chestplate.setItemMeta(meta);

                                                                revenantHorror.getEquipment().setChestplate(chestplate);

                                                                revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(40);

                                                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        if (revenantHorror.isValid()) {
                                                                            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                                                                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9);
                                                                            LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 80, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                            meta.setDisplayName("Revenant");
                                                                            meta.setUnbreakable(true);
                                                                            meta.setColor(Color.fromRGB(255, 0, 0));
                                                                            chestplate.setItemMeta(meta);

                                                                            revenantHorror.getEquipment().setChestplate(chestplate);

                                                                            revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(80);
                                                                            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, 1);

                                                                            Location loc = revenantHorror.getLocation();
                                                                            loc.setY(loc.getY()+2);
                                                                            ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(revenantHorror.getWorld().getName()).spawn(loc, ArmorStand.class);

                                                                            as.setCustomName(ChatColor.DARK_RED+"Enraged!");


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


                                                                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                                                    chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9);
                                                                                    ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                                    meta.setDisplayName("Revenant");
                                                                                    chestplate.setItemMeta(meta);

                                                                                    revenantHorror.getEquipment().setChestplate(chestplate);

                                                                                    revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(40);

                                                                                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            if (revenantHorror.isValid()) {
                                                                                                ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                                                                                                chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9);
                                                                                                LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 80, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 80, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                                                meta.setDisplayName("Revenant");
                                                                                                meta.setUnbreakable(true);
                                                                                                meta.setColor(Color.fromRGB(255, 0, 0));
                                                                                                chestplate.setItemMeta(meta);

                                                                                                revenantHorror.getEquipment().setChestplate(chestplate);

                                                                                                revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(80);
                                                                                                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, 1);

                                                                                                Location loc = revenantHorror.getLocation();
                                                                                                loc.setY(loc.getY()+2);
                                                                                                ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(revenantHorror.getWorld().getName()).spawn(loc, ArmorStand.class);

                                                                                                as.setCustomName(ChatColor.DARK_RED+"Enraged!");


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


                                                                                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                                                                                    @Override
                                                                                                    public void run() {
                                                                                                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                                                                                                        chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9);
                                                                                                        ItemMeta meta = chestplate.getItemMeta();
                                                                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", 100, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

                                                                                                        meta.setDisplayName("Revenant");
                                                                                                        chestplate.setItemMeta(meta);

                                                                                                        revenantHorror.getEquipment().setChestplate(chestplate);

                                                                                                        revenantHorror.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(40);
                                                                                                    }
                                                                                                }, 400);
                                                                                            }
                                                                                        }
                                                                                    }, 1200);
                                                                                }
                                                                            }, 200);
                                                                        }
                                                                    }
                                                                }, 600);
                                                            }
                                                        }, 200);
                                                    }
                                                }
                                            }, 600);
                                        }
                                    }, 200);
                                } else {
                                    player.sendMessage(ChatColor.RED + "You cannot afford this. (Hold the diamonds)");
                                }
                            }
                        }
                    }
                }
                e.setCancelled(true);
            }
        }
        if (e.getInventory().equals(slayerMenu)) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getType() == Material.ROTTEN_FLESH) {
                    zombieSlayerMenu.putIfAbsent((Player) e.getWhoClicked(), Bukkit.createInventory(e.getWhoClicked(), 54, ChatColor.DARK_GRAY+"Revenant Horror"));

                    for (int i = 0; i < 54; i++) {
                        if ((i >= 0 && i < 11) || (i > 15 && i < 28) || (i == 29 || i == 30 || i == 32 || i == 33) || (i > 34)) {
                            ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName("");
                            item.setItemMeta(meta);
                            zombieSlayerMenu.get(e.getWhoClicked()).setItem(i, item);
                        }
                        if (i == 11) {
                            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(ChatColor.GREEN+"Revenant Horror I");
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.DARK_GRAY+"Beginner");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Health: "+ChatColor.RED+"30❤");
                            lore.add(ChatColor.GRAY+"Damage: "+ChatColor.RED+"2"+ChatColor.GRAY+" per hit");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.RED+"Life Steal");
                            lore.add(ChatColor.GRAY+"Drains your health every few seconds,");
                            lore.add(ChatColor.GRAY+"healing the boss.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Reward: "+ChatColor.LIGHT_PURPLE+"5 Zombie Slayer XP");
                            lore.add(ChatColor.DARK_GRAY+"  + Boss Drops");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Cost to start: "+ChatColor.AQUA+"1 Diamond");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.YELLOW+"Click to slay!");
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            zombieSlayerMenu.get(e.getWhoClicked()).setItem(i, item);
                        }

                        if (i == 12) {
                            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(ChatColor.YELLOW+"Revenant Horror II");
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.DARK_GRAY+"Strong");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Health: "+ChatColor.RED+"90❤");
                            lore.add(ChatColor.GRAY+"Damage: "+ChatColor.RED+"5"+ChatColor.GRAY+" per hit");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.RED+"Life Steal");
                            lore.add(ChatColor.GRAY+"Drains your health every few seconds,");
                            lore.add(ChatColor.GRAY+"healing the boss.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GREEN+"Pestilence");
                            lore.add(ChatColor.GRAY+"Deals AOE damage every second,");
                            lore.add(ChatColor.GRAY+"shredding armor by 25%.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Reward: "+ChatColor.LIGHT_PURPLE+"25 Zombie Slayer XP");
                            lore.add(ChatColor.DARK_GRAY+"  + Boss Drops");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Cost to start: "+ChatColor.AQUA+"4 Diamond");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.YELLOW+"Click to slay!");
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            zombieSlayerMenu.get(e.getWhoClicked()).setItem(i, item);
                        }

                        if (i == 13) {
                            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(ChatColor.RED+"Revenant Horror III");
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.DARK_GRAY+"Challenging");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Health: "+ChatColor.RED+"300❤");
                            lore.add(ChatColor.GRAY+"Damage: "+ChatColor.RED+"10"+ChatColor.GRAY+" per hit");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.RED+"Life Steal");
                            lore.add(ChatColor.GRAY+"Drains your health every few seconds,");
                            lore.add(ChatColor.GRAY+"healing the boss.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GREEN+"Pestilence");
                            lore.add(ChatColor.GRAY+"Deals AOE damage every second,");
                            lore.add(ChatColor.GRAY+"shredding armor by 25%.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.DARK_RED+"Enrage");
                            lore.add(ChatColor.GRAY+"Gets real mad once in a while. ");
                            lore.add(ChatColor.GRAY+"doubling its damage.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Reward: "+ChatColor.LIGHT_PURPLE+"100 Zombie Slayer XP");
                            lore.add(ChatColor.DARK_GRAY+"  + Boss Drops");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Cost to start: "+ChatColor.AQUA+"16 Diamond");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.YELLOW+"Click to slay!");
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            zombieSlayerMenu.get(e.getWhoClicked()).setItem(i, item);
                        }

                        if (i == 14) {
                            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(ChatColor.DARK_RED+"Revenant Horror IV");
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.DARK_GRAY+"Deadly");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Health: "+ChatColor.RED+"900❤");
                            lore.add(ChatColor.GRAY+"Damage: "+ChatColor.RED+"30"+ChatColor.GRAY+" per hit");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.RED+"Life Steal");
                            lore.add(ChatColor.GRAY+"Drains your health every few seconds,");
                            lore.add(ChatColor.GRAY+"healing the boss.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GREEN+"Pestilence");
                            lore.add(ChatColor.GRAY+"Deals AOE damage every second,");
                            lore.add(ChatColor.GRAY+"shredding armor by 50%.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.DARK_RED+"Enrage");
                            lore.add(ChatColor.GRAY+"Gets real mad once in a while. ");
                            lore.add(ChatColor.GRAY+"tripling its damage.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Reward: "+ChatColor.LIGHT_PURPLE+"500 Zombie Slayer XP");
                            lore.add(ChatColor.DARK_GRAY+"  + Boss Drops");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Cost to start: "+ChatColor.AQUA+"32 Diamond");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.YELLOW+"Click to slay!");
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            zombieSlayerMenu.get(e.getWhoClicked()).setItem(i, item);
                        }

                        if (i == 15) {
                            ItemStack item = new ItemStack(Material.ROTTEN_FLESH);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(ChatColor.DARK_PURPLE+"Revenant Horror V");
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.DARK_GRAY+"Ungodly");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Health: "+ChatColor.RED+"2100❤");
                            lore.add(ChatColor.GRAY+"Damage: "+ChatColor.RED+"40"+ChatColor.GRAY+" per hit");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.RED+"Life Steal");
                            lore.add(ChatColor.GRAY+"Drains your health every few seconds,");
                            lore.add(ChatColor.GRAY+"healing the boss.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GREEN+"Pestilence");
                            lore.add(ChatColor.GRAY+"Deals AOE damage every second,");
                            lore.add(ChatColor.GRAY+"shredding armor by 50%.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.DARK_RED+"Enrage");
                            lore.add(ChatColor.GRAY+"Gets real mad once in a while,");
                            lore.add(ChatColor.GRAY+"tripling its damage.");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.DARK_GREEN+"Rotten Wounds");
                            lore.add(ChatColor.GRAY+"Below 75% health, all healing for");
                            lore.add(ChatColor.GRAY+"players nearby is reduced by 90%");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Reward: "+ChatColor.LIGHT_PURPLE+"1000 Zombie Slayer XP");
                            lore.add(ChatColor.DARK_GRAY+"  + Boss Drops");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.GRAY+"Cost to start: "+ChatColor.AQUA+"64 Diamond");
                            lore.add(ChatColor.GRAY+" ");
                            lore.add(ChatColor.YELLOW+"Click to slay!");
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            zombieSlayerMenu.get(e.getWhoClicked()).setItem(i, item);
                        }
                    }

                    e.getWhoClicked().openInventory(zombieSlayerMenu.get(e.getWhoClicked()));
                }
            }
            e.setCancelled(true);
        }
    }
}
