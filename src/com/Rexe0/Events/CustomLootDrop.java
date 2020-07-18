package com.Rexe0.Events;


import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.DefenseNerf;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class CustomLootDrop extends Event implements Cancellable {
    private Player player;
    private Entity entity;
    private boolean isCancelled;
    private int firstChance;
    private int secondChance;
    private int lowestAmount;
    private int highestAmount;
    private ItemStack item;
    private String name;
    private Rarity rarity;
    private static final HandlerList handlers = new HandlerList();

    public CustomLootDrop(Player player, int firstChance, int secondChance, ItemStack item, String name, Rarity rarity, Entity entity, int lowestAmount, int highestAmount) {
        this.player = player;
        this.isCancelled = false;
        this.firstChance = firstChance;
        this.secondChance = secondChance;
        this.lowestAmount = lowestAmount;
        this.highestAmount = highestAmount;
        this.item = item;
        this.name = name;
        this.rarity = rarity;
        this.entity = entity;
    }

    public static String convertToRainbow(String string, boolean isBolded) {
        String finalString = "";
        ArrayList<Character> characters = new ArrayList<>();
        for (Character character : string.toCharArray()) {
            characters.add(character);
        }

        int j = 0;
        for (Character character : characters) {
            String color = "";
            switch (j) {
                case 0:
                    color = ChatColor.RED + "";
                    break;
                case 1:
                    color = ChatColor.GOLD + "";
                    break;
                case 2:
                    color = ChatColor.YELLOW + "";
                    break;
                case 3:
                    color = ChatColor.GREEN + "";
                    break;
                case 4:
                    color = ChatColor.AQUA + "";
                    break;
                case 5:
                    color = ChatColor.BLUE + "";
                    break;
                case 6:
                    color = ChatColor.DARK_PURPLE + "";
                    break;
                case 7:
                    color = ChatColor.LIGHT_PURPLE + "";
                    j = -1;
                    break;
            }

            if (isBolded) {
                finalString += color+ChatColor.BOLD+character;
            } else {
                finalString += color+character;
            }

            if (!character.equals(' ')) j++;
        }

        return finalString;
    }

    public void execute() {
        Random RNG = new Random();

        boolean telekinesis = false;
        for (ItemStack item : player.getInventory().getContents()) {
            String foundValue1 = DefenseNerf.getItemID(item);

            if (foundValue1 != null) {
                if (foundValue1.equals("TELEKINESIS_TALISMAN")) {
                    telekinesis = true;

                }
            }

        }

        int number = 0;
        if (rarity == Rarity.GUARANTEED) {

            number = RNG.nextInt((this.highestAmount - this.lowestAmount)+1);

            number += +this.lowestAmount;

            if (number > 0) {


                ItemStack item = this.item;
                item.setAmount(number);

                if (!telekinesis) {
                    Item itemDrop = this.entity.getWorld().dropItem(this.entity.getLocation(), item);
                    ArrayList<Item> itemReserved;
                    itemReserved = DefenseNerf.playerSpecificPickup.get(this.player);
                    if (itemReserved == null) {
                        itemReserved = new ArrayList<>();


                    }
                    itemReserved.add(itemDrop);
                    DefenseNerf.playerSpecificPickup.put(this.player, itemReserved);
                } else {
                    this.player.getInventory().addItem(item);
                }
            }



        } else {


            number = RNG.nextInt(this.secondChance) * 100;
            number += 100;

            float magicFind = (float) this.player.getAttribute(Attribute.GENERIC_LUCK).getValue();

            if (number <= (this.firstChance * 100) * (1 + (magicFind / 100))) {
                if (magicFind <= 0) {
                    if (this.rarity == Rarity.COMMON) {
                        this.player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "UNCOMMON DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")");
                    }
                    if (this.rarity == Rarity.UNCOMMON) {
                        this.player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "RARE DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")");
                    }
                    if (this.rarity == Rarity.RARE) {
                        this.player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "RARE DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")");
                    }
                    if (this.rarity == Rarity.VERY_RARE) {
                        this.player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "VERY RARE DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")");
                    }

                    if (this.rarity == Rarity.CRAZY_RARE) {
                        this.player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CRAZY RARE DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")");
                    }
                    if (this.rarity == Rarity.INSANELY_RARE) {
                        this.player.sendMessage(convertToRainbow("INSANELY RARE DROP! ", true) + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")");
                    }
                } else {


                    if (this.rarity == Rarity.COMMON) {
                        this.player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "UNCOMMON DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")" + ChatColor.AQUA + " (+" + magicFind + "% Magic Find)");
                    }
                    if (this.rarity == Rarity.UNCOMMON) {
                        this.player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "RARE DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")" + ChatColor.AQUA + " (+" + magicFind + "% Magic Find)");
                    }
                    if (this.rarity == Rarity.RARE) {
                        this.player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "RARE DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")" + ChatColor.AQUA + " (+" + magicFind + "% Magic Find)");
                    }
                    if (this.rarity == Rarity.VERY_RARE) {
                        this.player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "VERY RARE DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")" + ChatColor.AQUA + " (+" + magicFind + "% Magic Find)");
                    }

                    if (this.rarity == Rarity.CRAZY_RARE) {
                        this.player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CRAZY RARE DROP! " + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")" + ChatColor.AQUA + " (+" + magicFind + "% Magic Find)");
                    }
                    if (this.rarity == Rarity.INSANELY_RARE) {
                        this.player.sendMessage(convertToRainbow("INSANELY RARE DROP! ", true) + ChatColor.RESET + "" + ChatColor.GRAY + "(" + this.name + ChatColor.GRAY + ")" + ChatColor.AQUA + " (+" + magicFind + "% Magic Find)");
                    }
                }




                number = RNG.nextInt((this.highestAmount - this.lowestAmount)+1);

                number += +this.lowestAmount;

                ItemStack item = this.item;
                item.setAmount(number);

                Item itemDrop = this.entity.getWorld().dropItem(this.entity.getLocation(), item);
                ArrayList<Item> itemReserved;
                itemReserved = DefenseNerf.playerSpecificPickup.get(this.player);
                if (itemReserved == null) {
                    itemReserved = new ArrayList<>();


                }
                itemReserved.add(itemDrop);
                DefenseNerf.playerSpecificPickup.put(this.player, itemReserved);

                Player player1 = this.player;

                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                    @Override
                    public void run() {
                        player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 1.2f);
                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                            @Override
                            public void run() {
                                player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 1.5f);
                                ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                                    @Override
                                    public void run() {
                                        player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 1.8f);
                                    }
                                }, 5);
                            }
                        }, 5);
                    }
                }, 1);


            }


        }
    }


    public Player getPlayer() {
        return this.player;
    }

    public int getFirstChance() {
        return this.firstChance;
    }

    public int getSecondChance() {
        return this.secondChance;
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


    public enum Rarity {
        GUARANTEED, // White (No Message), Boss: Default chance usually 50 to 100%, Regular Mobs: Default chance usually 25 to 100%
        COMMON, // Light Green, Boss: Default chance usually 25 to 50%, Regular Mobs: Default chance usually 10 to 25%
        UNCOMMON, // Light blue, Boss: Default chance usually 10 to 25%,     Regular Mobs: Default chance usually 5 to 10%
        RARE, // Gold, Boss: Default chance usually 5 to 10%,     Regular Mobs: Default chance usually 0.5 to 5%
        VERY_RARE, // Dark Purple, Boss: Default chance usually 1 to 5%,     Regular Mobs: Default chance usually < 0.5%
        CRAZY_RARE, // Light Purple, Boss: Default chance usually < 1%,     Regular Mobs: Default chance usually < 0.01%
        INSANELY_RARE // Rainbow, Boss: Default chance usually < 0.01%,     Regular Mobs: Default chance usually < 0.001%
    }
}
