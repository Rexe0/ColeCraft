package com.Rexe0.Events;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.DefenseNerf;
import com.Rexe0.Items.CustomItem;
import com.Rexe0.Items.Materials.CustomMaterial;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

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
                if (damage > damage1) {
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
                if (damage > damage1) {
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
                if (damage > damage1) {
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
                p.sendMessage((ChatColor.GREEN + "" + ChatColor.BOLD + "-----------------------------------------\n" + ChatColor.GOLD + "" + ChatColor.BOLD + "              "+BossName+" DOWN!\n \n" + ChatColor.RESET + "                  " + ChatColor.GOLD + this.lastHit.getName() + ChatColor.GRAY + " dealt the final blow.\n \n" + ChatColor.YELLOW + ChatColor.BOLD + "              1st Damager" + ChatColor.GRAY + " - " + ChatColor.GOLD + first.getName() + " " + ChatColor.GREEN + this.damagePerPlayer.get(first) + ChatColor.GOLD + ChatColor.BOLD + "\n              2nd Damager" + ChatColor.GRAY + " - " + ChatColor.GOLD + second.getName() + " " + ChatColor.GREEN + this.damagePerPlayer.get(second) + ChatColor.GOLD + ChatColor.BOLD + "\n              3rd Damager" + ChatColor.GRAY + " - " + ChatColor.GOLD + third.getName() + " " + ChatColor.GREEN + this.damagePerPlayer.get(third) + ChatColor.YELLOW + "\n \n              Your Damage: " + ChatColor.GREEN + damage + ChatColor.GREEN + "\n \n" + ChatColor.BOLD + "-----------------------------------------"));

            }
        }

        HashMap<Player, ArrayList<ItemStack>> loot = new HashMap<>();
        if (this.bossType == BossType.MAGMA_CUBE_BOSS) {
            HashMap<Player, Short> totalWeight = new HashMap<>();

            for (Map.Entry<Player, Integer> entry : this.spawner.entrySet()) {
                totalWeight.put(entry.getKey(), (short) 100);
            }

            for (Map.Entry<Player, Float> entry : this.damagePerPlayer.entrySet()) {
                if (first.equals(entry.getKey())) {
                    totalWeight.put(entry.getKey(), (short) (totalWeight.get(entry.getKey())+150));
                } else if (second.equals(entry.getKey())) {
                    totalWeight.put(entry.getKey(), (short) (totalWeight.get(entry.getKey())+100));
                } else if (third.equals(entry.getKey())) {
                    totalWeight.put(entry.getKey(), (short) (totalWeight.get(entry.getKey())+75));
                } else if (forth.equals(entry.getKey())) {
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
            }
        }


        if (loc != null) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                int radius = 7;
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY(), loc.getBlockX() + z).setType(Material.YELLOW_TERRACOTTA);
                    }
                }

                p.sendBlockChange(loc, Material.ORANGE_STAINED_GLASS.createBlockData());
                p.sendBlockChange(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()-2,loc.getBlockZ()), Material.BEACON.createBlockData());

                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY()-2, loc.getBlockX() + z).setType(Material.IRON_BLOCK);

                    }
                }
            }


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
