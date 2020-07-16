package com.Rexe0.Events;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.DefenseNerf;
import com.Rexe0.Items.CustomItem;
import com.Rexe0.Items.Materials.CustomMaterial;
import net.minecraft.server.v1_16_R1.Block;
import net.minecraft.server.v1_16_R1.BlockPosition;
import net.minecraft.server.v1_16_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntityDestroy;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomBossDeathEvent extends Event implements Cancellable {
    private Player lastHit;
    private HashMap<Player, Integer> spawner;
    private HashMap<Player, Float> damagePerPlayer;
    private Entity entity;
    private boolean isCancelled;
    private BossType bossType;
    private static final HandlerList handlers = new HandlerList();

    public CustomBossDeathEvent(Player lastHit, BossType bossType, Entity entity, HashMap<Player, Integer> spawner, HashMap<Player, Float> damagePerPlayer) {
        this.lastHit = lastHit;
        this.isCancelled = false;
        this.bossType = bossType;
        this.entity = entity;
        this.spawner = spawner;
        this.damagePerPlayer = damagePerPlayer;
    }



    public void execute() {
        Player first = null;
        Player second = null;
        Player third = null;
        Player forth = null;
        float damage1 = 0;



        for (Player player : Bukkit.getOnlinePlayers()) {
            this.damagePerPlayer.putIfAbsent(player, 0f);
            float damage = this.damagePerPlayer.get(player);
            if (damage > damage1) {
                damage1 = damage;
                first = (Player) player;
            }

        }
        damage1 = 0;

        if (first != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                this.damagePerPlayer.putIfAbsent(player, 0f);
                float damage = this.damagePerPlayer.get(player);
                if (damage > damage1 && !first.equals(player)) {
                    damage1 = damage;
                    second = (Player) player;
                }

            }
        }

        damage1 = 0;

        if (second != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                this.damagePerPlayer.putIfAbsent(player, 0f);
                float damage = this.damagePerPlayer.get(player);
                if (damage > damage1 && !first.equals(player) && !second.equals(player)) {
                    damage1 = damage;
                    third = (Player) player;
                }

            }
        }

        damage1 = 0;

        if (third != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                this.damagePerPlayer.putIfAbsent(player, 0f);
                float damage = this.damagePerPlayer.get(player);
                if (damage > damage1 && !first.equals(player) && !second.equals(player) && !third.equals(player)) {
                    damage1 = damage;
                    forth = (Player) player;
                }

            }
        }
        String BossName = "";
        if (this.bossType == BossType.MAGMA_CUBE_BOSS) {
            BossName = "MAGMA CUBE BOSS";
        }



        for (Player p : Bukkit.getOnlinePlayers()) {
            float damage = this.damagePerPlayer.get(p);

            if (second == null) {
                p.sendMessage((ChatColor.GREEN + "" + ChatColor.BOLD + "-----------------------------------------\n" + ChatColor.GOLD + "" + ChatColor.BOLD + "              "+BossName+" DOWN!\n \n" + ChatColor.RESET + "                  " + ChatColor.GOLD + this.lastHit.getName() + ChatColor.GRAY + " dealt the final blow.\n \n" + ChatColor.YELLOW + ChatColor.BOLD + "              1st Damager" + ChatColor.GRAY + " - " + ChatColor.GOLD + first.getName() + " " + ChatColor.GREEN + this.damagePerPlayer.get(first) + ChatColor.YELLOW + "\n \n              Your Damage: " + ChatColor.GREEN + damage + ChatColor.GREEN + "\n \n" + ChatColor.BOLD + "-----------------------------------------"));
            } else if (third == null) {
                p.sendMessage((ChatColor.GREEN + "" + ChatColor.BOLD + "-----------------------------------------\n" + ChatColor.GOLD + "" + ChatColor.BOLD + "              "+BossName+" DOWN!\n \n" + ChatColor.RESET + "                  " + ChatColor.GOLD + this.lastHit.getName() + ChatColor.GRAY + " dealt the final blow.\n \n" + ChatColor.YELLOW + ChatColor.BOLD + "              1st Damager" + ChatColor.GRAY + " - " + ChatColor.GOLD + first.getName() + " " + ChatColor.GREEN + this.damagePerPlayer.get(first) + ChatColor.GOLD + ChatColor.BOLD + "\n              2nd Damager" + ChatColor.GRAY + " - " + ChatColor.GOLD + second.getName() + " " + ChatColor.GREEN + this.damagePerPlayer.get(second) + ChatColor.YELLOW + "\n \n              Your Damage: " + ChatColor.GREEN + damage + ChatColor.GREEN + "\n \n" + ChatColor.BOLD + "-----------------------------------------"));
            } else {
                p.sendMessage((ChatColor.GREEN + "" + ChatColor.BOLD + "-----------------------------------------\n" + ChatColor.GOLD + "" + ChatColor.BOLD + "              "+BossName+" DOWN!\n \n" + ChatColor.RESET + "                  " + ChatColor.GOLD + this.lastHit.getName() + ChatColor.GRAY + " dealt the final blow.\n \n" + ChatColor.YELLOW + ChatColor.BOLD + "              1st Damager" + ChatColor.GRAY + " - " + ChatColor.GOLD + first.getName() + " " + ChatColor.GREEN + this.damagePerPlayer.get(first) + ChatColor.GOLD + ChatColor.BOLD + "\n              2nd Damager" + ChatColor.GRAY + " - " + ChatColor.GOLD + second.getName() + " " + ChatColor.GREEN + this.damagePerPlayer.get(second) + ChatColor.RED + ChatColor.BOLD + "\n              3rd Damager" + ChatColor.GRAY + " - " + ChatColor.GOLD + third.getName() + " " + ChatColor.GREEN + this.damagePerPlayer.get(third) + ChatColor.YELLOW + "\n \n              Your Damage: " + ChatColor.GREEN + damage + ChatColor.GREEN + "\n \n" + ChatColor.BOLD + "-----------------------------------------"));

            }
        }

        HashMap<Player, ArrayList<ItemStack>> loot = new HashMap<>();
        if (this.bossType == BossType.MAGMA_CUBE_BOSS) {
            HashMap<Player, Short> totalWeight = new HashMap<>();

            for (Map.Entry<Player, Integer> entry : this.spawner.entrySet()) {
                totalWeight.put(entry.getKey(), (short) 100);
            }

            for (Map.Entry<Player, Float> entry : this.damagePerPlayer.entrySet()) {
                totalWeight.putIfAbsent(entry.getKey(), (short) 0);
                int numbers = 0;
                if (first != null) numbers = 1;


                if (second != null) numbers = 2;


                if (third != null) numbers = 3;


                if (forth != null) numbers = 4;


                if (first.equals(entry.getKey()) && numbers >= 1) {
                    totalWeight.put(entry.getKey(), (short) (totalWeight.get(entry.getKey())+150));
                } else if (second.equals(entry.getKey()) && numbers >= 2) {
                    totalWeight.put(entry.getKey(), (short) (totalWeight.get(entry.getKey())+100));
                } else if (third.equals(entry.getKey()) && numbers >= 3) {
                    totalWeight.put(entry.getKey(), (short) (totalWeight.get(entry.getKey())+75));
                } else if (forth.equals(entry.getKey()) && numbers >= 4) {
                    totalWeight.put(entry.getKey(), (short) (totalWeight.get(entry.getKey())+50));
                } else {
                    totalWeight.put(entry.getKey(), (short) (totalWeight.get(entry.getKey())+25));
                }
            }



            for (Map.Entry<Player, Short> entry : totalWeight.entrySet()) {

                short currentWeight = entry.getValue();

                ArrayList<ItemStack> loot1 = new ArrayList<>();

                Random rand = new Random();
                int n = rand.nextInt(100);

                n += 1;

                if (currentWeight >= 160 && n <= 5) {
                    currentWeight -= 160;

                    loot1.add(CustomItem.getItemClass("EMBER_CHESTPLATE"));

                }

                if (currentWeight >= 140 && (n > 5 && n <= 15)) {
                    currentWeight -= 140;
                    loot1.add(CustomItem.getItemClass("EMBER_LEGGINGS"));
                }

                if (currentWeight >= 100 && (n > 15 && n <= 30)) {
                    currentWeight -= 100;
                    loot1.add(CustomItem.getItemClass("EMBER_ROD"));
                }
                if (currentWeight >= 100 && (n > 30 && n <= 50)) {
                    currentWeight -= 100;
                    loot1.add(CustomItem.getItemClass("EMBER_MASK"));
                }

                if (currentWeight >= 80 && (n > 50 && n <= 80)) {
                    currentWeight -= 80;
                    loot1.add(CustomItem.getItemClass("EMBER_BOOTS"));
                }


                byte amount = 0;
                if (currentWeight <= 100) {
                    amount = (byte) Math.floor(currentWeight / 25);
                } else {
                    amount = (byte) (4 + Math.floor((currentWeight - 100) / 50));
                }

                ItemStack item = CustomMaterial.getItemClass("EMBER_FRAGMENT");
                item.setAmount(amount);


                loot1.add(item);

                ItemStack magmaCream = CustomMaterial.getItemClass("MAGMA_CREAM");
                magmaCream.setAmount(rand.nextInt(4)+1);

                loot1.add(magmaCream);



                loot.put(entry.getKey(), loot1);

            }





        }

        Location deathLoc = entity.getLocation();

        Location loc = null;

        for (int i = deathLoc.getBlockY(); i > 0; i--) {
            if (entity.getWorld().getBlockAt(deathLoc.getBlockX(), i, deathLoc.getBlockZ()).getType().isSolid()) {
                loc = new Location(deathLoc.getWorld(),deathLoc.getBlockX(), i, deathLoc.getBlockZ());
                break;
            }
        }


        if (loc != null) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                int r = 7;
                int cx = loc.getBlockX();
                int cy = loc.getBlockY();
                int cz = loc.getBlockZ();
                World w = loc.getWorld();
                int rSquared = r * r;
                for (int x = cx - r; x <= cx +r; x++) {
                    for (int z = cz - r; z <= cz +r; z++) {
                        if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
                            Location loc1 = new Location(loc.getWorld(), x, cy, z);

                            new BukkitRunnable() {
                                @Override
                                public void run() {

                                    BlockPosition blockPosition = new BlockPosition(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
                                    PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld) loc1.getWorld()).getHandle(), blockPosition);
                                    net.minecraft.server.v1_16_R1.Block nmsBlock = CraftMagicNumbers.getBlock(Material.YELLOW_TERRACOTTA);
                                    packet.block = nmsBlock.getBlockData();
                                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                }
                            }.runTaskAsynchronously(ColeCrafterSlayers.getInstance());
                        }
                    }
                }
                Location finalLoc = loc;
                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        for (int x = cx - r; x <= cx +r; x++) {
                            for (int z = cz - r; z <= cz +r; z++) {
                                if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
                                    Location loc1 = new Location(finalLoc.getWorld(), x, cy, z);

                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {

                                            BlockPosition blockPosition = new BlockPosition(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
                                            Material mat = w.getBlockAt(new Location(w, loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ())).getType();
                                            PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld) loc1.getWorld()).getHandle(), blockPosition);
                                            net.minecraft.server.v1_16_R1.Block nmsBlock = CraftMagicNumbers.getBlock(mat);
                                            packet.block = nmsBlock.getBlockData();
                                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                        }
                                    }.runTaskAsynchronously(ColeCrafterSlayers.getInstance());
                                }
                            }
                        }
                    }
                }, 600);



                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        p.sendBlockChange(finalLoc, Material.ORANGE_STAINED_GLASS.createBlockData());
                    }
                }, 5);

                p.sendBlockChange(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()-1,loc.getBlockZ()), Material.BEACON.createBlockData());

                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        Location loc1 = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY()-2, loc.getBlockZ() + z);
                        /*
                         * Created by ColonelHedgehog.
                         * 11/11/15
                         */
                        new BukkitRunnable() {
                            @Override
                            public void run() {

                                BlockPosition blockPosition = new BlockPosition(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
                                PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld) loc1.getWorld()).getHandle(), blockPosition);
                                net.minecraft.server.v1_16_R1.Block nmsBlock = CraftMagicNumbers.getBlock(Material.IRON_BLOCK);
                                packet.block = nmsBlock.getBlockData();
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            }
                        }.runTaskAsynchronously(ColeCrafterSlayers.getInstance());

                    }
                }
            }




            for (Map.Entry<Player, ArrayList<ItemStack>> entry : loot.entrySet()) {
                entry.getKey().sendMessage(entry.getKey().getName());
                ArrayList<Item> reservedItems = new ArrayList<>();
                if (DefenseNerf.playerSpecificPickup.get(entry.getKey()) != null) reservedItems = DefenseNerf.playerSpecificPickup.get(entry.getKey());

                for (ItemStack item : entry.getValue()) {
                    entry.getKey().sendMessage(item.getItemMeta().getDisplayName());
                    Item item1 = loc.getWorld().dropItem(loc, item);





                    String foundValue = DefenseNerf.getItemID(item);
                    if (foundValue == null) continue;
                    String idLowerCase = foundValue.toLowerCase();
                    String[] idToWords = idLowerCase.split("_");
                    String finalID = "";
                    for (String str : idToWords) {
                        String chara = str.substring(0, 1);
                        chara = chara.toUpperCase();
                        finalID = finalID +" "+(chara + "" + str.substring(1));
                    }

                    String rarity = DefenseNerf.getItemRarity(item);

                    if (rarity == null) continue;
                    String rarityColor = rarity.equalsIgnoreCase("COMMON") ? ChatColor.WHITE+"" : rarity.equalsIgnoreCase("UNCOMMON") ? ChatColor.GREEN+"" :
                            rarity.equalsIgnoreCase("RARE") ? ChatColor.BLUE+"" : rarity.equalsIgnoreCase("EPIC") ? ChatColor.DARK_PURPLE+"" : rarity.equalsIgnoreCase("LEGENDARY") ? ChatColor.GOLD+"" :
                                    rarity.equalsIgnoreCase("SPECIAL") ? ChatColor.LIGHT_PURPLE+"" : ChatColor.GRAY+"";

                    item1.setCustomName(ChatColor.GOLD+""+ChatColor.MAGIC+"I"+ChatColor.RESET+" "+rarityColor+item1.getItemStack().getAmount()+"x"+finalID+ChatColor.GOLD+""+ChatColor.MAGIC+"I");

                    item1.setCustomNameVisible(true);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (!(p.equals(entry.getKey()))) {
                            ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                @Override
                                public void run() {
                                    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(item1.getEntityId());


                                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                    entry.getKey().sendMessage("Session 1 - "+p.getName());
                                }
                            }, 1);

                        }
                    }

                    reservedItems.add(item1);
                }
                DefenseNerf.playerSpecificPickup.put(entry.getKey(), reservedItems);

            }
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), ("execute positioned "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" run spreadplayers "+loc.getBlockX()+" "+loc.getBlockZ()+" "+1+" "+4+" "+false+" @e[type=item,distance=..2]"));

        }


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


    public enum BossType {
        MAGMA_CUBE_BOSS
    }
}
