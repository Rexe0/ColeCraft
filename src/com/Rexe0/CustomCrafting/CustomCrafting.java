package com.Rexe0.CustomCrafting;

import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.DefenseNerf;
import com.Rexe0.Slayers.CalenderCommand;
import com.Rexe0.Slayers.SlayerMenu;
import com.Rexe0.Slayers.StatsCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomCrafting implements Listener, CommandExecutor {
    public static HashMap<Player, Inventory> craftingGUI = new HashMap<>();

    @EventHandler
    public static void onInvClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) return;
        if (e.getClickedInventory() == null) return;
        if (e.getInventory().equals(RecipeCommand.recipeGUI.get(player))) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    boolean showNewPage = false;
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Previous Page")) {
                        RecipeCommand.recipeBookPage.put(player, RecipeCommand.recipeBookPage.get(player)-1);
                        showNewPage = true;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Recipe Book")) {
                        ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.dispatchCommand(player, "viewrecipe");
                            }
                        }, 1);
                        Bukkit.dispatchCommand(player, "viewrecipe");
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Next Page")) {
                        RecipeCommand.recipeBookPage.put(player, RecipeCommand.recipeBookPage.get(player)+1);
                        showNewPage = true;
                    }
                    if (showNewPage) {
                        Inventory inv = RecipeCommand.recipeGUI.get(player);
                        HashMap<Player, Integer> recipeBookPage = RecipeCommand.recipeBookPage;


                        HashMap<Integer, CustomRecipe> recipeWithSlot = new HashMap<>();
                        int j = 10;

                        ArrayList<ItemStack> alreadyDoneItems = new ArrayList<>();

                        RecipeCommand.recipeBookIndex.putIfAbsent(player, 0);
                        int k = RecipeCommand.recipeBookIndex.get(player);
                        int h = 0;
                        int m = 0;
                        for (int l = 0; m <= 27*(RecipeCommand.recipeBookPage.get(player)); l++) {
                            if (l == CustomRecipe.customRecipes.size()) break;
                            if (CustomRecipe.customRecipes.get(l) == null) break;
                            h++;


                            CustomRecipe recipe = CustomRecipe.customRecipes.get(l);
                            boolean continueNext = false;
                            for (ItemStack item : alreadyDoneItems) {
                                if (ColeCrafterSlayers.isEqual(item, recipe.getResult())) {
                                    continueNext = true;
                                    break;
                                }

                            }
                            if (recipe.isHidden()) {
                                continueNext = true;
                            }
                            if (continueNext) {
                                continue;
                            }

                            alreadyDoneItems.add(recipe.getResult());

                            m++;
                        }




                        k = h;


                        alreadyDoneItems = new ArrayList<>();


                        for (; k < CustomRecipe.customRecipes.size(); k++) {

                            CustomRecipe recipe = CustomRecipe.customRecipes.get(k);
                            boolean continueNext = false;
                            for (ItemStack item : alreadyDoneItems) {
                                if (ColeCrafterSlayers.isEqual(item, recipe.getResult())) {
                                    continueNext = true;
                                    break;
                                }

                            }
                            if (recipe.isHidden()) {
                                continueNext = true;
                            }
                            if (continueNext) {
                                continue;
                            }
                            recipeWithSlot.put(j, recipe);
                            alreadyDoneItems.add(recipe.getResult());
                            j++;
                            boolean breakOut = false;
                            switch (j) {
                                case 17:
                                    j = 19;
                                    break;
                                case 26:
                                    j = 28;
                                    break;
                                case 35:
                                    j = 37;
                                    break;
                                case 44:
                                    breakOut = true;
                                    break;
                            }

                            if (breakOut) break;
                        }

                        RecipeCommand.recipeBookIndex.put(player, k+1);



                        for (int i = 0; i < 54; i++) {
                            if ((i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 44)) {
                                if (recipeWithSlot.get(i) == null) {
                                    inv.setItem(i, new ItemStack(Material.AIR));
                                    continue;
                                }
                                CustomRecipe theRecipe = recipeWithSlot.get(i);
                                ItemStack item = theRecipe.getResult();
                                item.setAmount(theRecipe.getAmount());
                                inv.setItem(i, item);

                            } else if (i == 49) {
                                ItemStack item = new ItemStack(Material.BARRIER, 1);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.RED+"Close");
                                item.setItemMeta(meta);
                                inv.setItem(i, item);
                            } else if (i == 45 && recipeBookPage.get(player) != 0) {
                                ItemStack back = new ItemStack(Material.ARROW, 1);

                                ItemMeta meta = back.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN+"Previous Page");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.YELLOW+"Page "+(recipeBookPage.get(player)));
                                meta.setLore(lore);
                                back.setItemMeta(meta);
                                inv.setItem(i, back);
                            } else if (i == 53 && (CustomRecipe.customRecipes.size() > (recipeBookPage.get(player)+1)*28 )) {
                                ItemStack back = new ItemStack(Material.ARROW, 1);

                                ItemMeta meta = back.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN+"Next Page");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.YELLOW+"Page "+(recipeBookPage.get(player)+2));
                                meta.setLore(lore);
                                back.setItemMeta(meta);
                                inv.setItem(i, back);
                            } else {
                                inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
                            }
                        }

                        player.openInventory(inv);

                    } if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"Close")) {

                        player.closeInventory();
                    }

                    String foundValue = DefenseNerf.getItemID(e.getCurrentItem());
                    if (foundValue != null) {

                        Bukkit.dispatchCommand(player, "viewrecipe "+foundValue);
                    }
                }
            }

            e.setCancelled(true);
        }


        if (e.getInventory().equals(SlayerMenu.bankerInv.get(player))) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Your whole purse")) {
         

                        long bank = ColeCrafterSlayers.getBank(player);
                        long purse = ColeCrafterSlayers.getCoins(player);

                        if ((bank + purse) > 50000000) {

                            long coinsDep = 50000000-bank;

                            String coinsDep1 = coinsDep+"";



                            String coinsDeposited = coinsDep < 1000 ? coinsDep1 : coinsDep >= 1000 && coinsDep < 1000000 ? coinsDep1.substring(0, coinsDep1.length()-3)+"k" : coinsDep >= 1000000 && coinsDep < 1000000000 ? coinsDep1.substring(0, coinsDep1.length()-6)+"M" : coinsDep1.substring(0, coinsDep1.length()-9)+"B";


                            ColeCrafterSlayers.setCoins(player, (bank+purse)-50000000);
                            ColeCrafterSlayers.setBank(player, 50000000);

                            String currentBank1 = bank+"";

                            String currentBank = bank < 1000 ? currentBank1 : bank >= 1000 && bank < 1000000 ? currentBank1.substring(0, currentBank1.length()-3)+"k" : bank >= 1000000 && bank < 1000000000 ? currentBank1.substring(0, currentBank1.length()-6)+"M" : currentBank1.substring(0, currentBank1.length()-9)+"B";
                            


                            player.sendMessage(ChatColor.GREEN+"Deposited "+ChatColor.GOLD+coinsDeposited+" coins"+ChatColor.GREEN+"! There's now "+ChatColor.GOLD+currentBank+" coins"+ChatColor.GREEN+" in the account!");


                        } else {
                            long coinsDep = purse;

                            String coinsDep1 = coinsDep+"";


                            String coinsDeposited = coinsDep < 1000 ? coinsDep1 : coinsDep >= 1000 && coinsDep < 1000000 ? coinsDep1.substring(0, coinsDep1.length()-3)+"k" : coinsDep >= 1000000 && coinsDep < 1000000000 ? coinsDep1.substring(0, coinsDep1.length()-6)+"M" : coinsDep1.substring(0, coinsDep1.length()-9)+"B";


                            ColeCrafterSlayers.setBank(player,bank+purse);
                            ColeCrafterSlayers.setCoins(player,0);

                            String currentBank1 = bank+"";
                            String currentBank = bank < 1000 ? currentBank1 : bank >= 1000 && bank < 1000000 ? currentBank1.substring(0, currentBank1.length()-3)+"k" : bank >= 1000000 && bank < 1000000000 ? currentBank1.substring(0, currentBank1.length()-6)+"M" : currentBank1.substring(0, currentBank1.length()-9)+"B";


                            player.sendMessage(ChatColor.GREEN+"Deposited "+ChatColor.GOLD+coinsDeposited+" coins"+ChatColor.GREEN+"! There's now "+ChatColor.GOLD+currentBank+" coins"+ChatColor.GREEN+" in the account!");


                        }

                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                        SlayerMenu.openBank(player);
                    }

                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Half your purse")) {
                        long bank = ColeCrafterSlayers.getBank(player);
                        long purse = ColeCrafterSlayers.getCoins(player);

                        if ((bank + purse/2) > 50000000) {

                            long coinsDep = purse/2;

                            String coinsDep1 = coinsDep+"";



                            String coinsDeposited = coinsDep < 1000 ? coinsDep1 : coinsDep >= 1000 && coinsDep < 1000000 ? coinsDep1.substring(0, coinsDep1.length()-3)+"k" : coinsDep >= 1000000 && coinsDep < 1000000000 ? coinsDep1.substring(0, coinsDep1.length()-6)+"M" : coinsDep1.substring(0, coinsDep1.length()-9)+"B";



                            ColeCrafterSlayers.setCoins(player,purse-(50000000-bank));
                            ColeCrafterSlayers.setBank(player,50000000);

                            String currentBank1 = bank+"";

                            String currentBank = bank < 1000 ? currentBank1 : bank >= 1000 && bank < 1000000 ? currentBank1.substring(0, currentBank1.length()-3)+"k" : bank >= 1000000 && bank < 1000000000 ? currentBank1.substring(0, currentBank1.length()-6)+"M" : currentBank1.substring(0, currentBank1.length()-9)+"B";


                            player.sendMessage(ChatColor.GREEN+"Deposited "+ChatColor.GOLD+coinsDeposited+" coins"+ChatColor.GREEN+"! There's now "+ChatColor.GOLD+currentBank+" coins"+ChatColor.GREEN+" in the account!");

                        } else {
                            long coinsDep = purse/2;

                            String coinsDep1 = coinsDep+"";


                            String coinsDeposited = coinsDep < 1000 ? coinsDep1 : coinsDep >= 1000 && coinsDep < 1000000 ? coinsDep1.substring(0, coinsDep1.length()-3)+"k" : coinsDep >= 1000000 && coinsDep < 1000000000 ? coinsDep1.substring(0, coinsDep1.length()-6)+"M" : coinsDep1.substring(0, coinsDep1.length()-9)+"B";



                            ColeCrafterSlayers.setBank(player,bank+purse/2);
                            ColeCrafterSlayers.setCoins(player,purse/2);

                            String currentBank1 = bank+"";

                            String currentBank = bank < 1000 ? currentBank1 : bank >= 1000 && bank < 1000000 ? currentBank1.substring(0, currentBank1.length()-3)+"k" : bank >= 1000000 && bank < 1000000000 ? currentBank1.substring(0, currentBank1.length()-6)+"M" : currentBank1.substring(0, currentBank1.length()-9)+"B";


                            player.sendMessage(ChatColor.GREEN+"Deposited "+ChatColor.GOLD+coinsDeposited+" coins"+ChatColor.GREEN+"! There's now "+ChatColor.GOLD+currentBank+" coins"+ChatColor.GREEN+" in the account!");


                        }

                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                        SlayerMenu.openBank(player);
                    }

                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Everything in the account")) {
                        long bank = ColeCrafterSlayers.getBank(player);
                        long purse = ColeCrafterSlayers.getCoins(player);


                        long coinsDep = bank;

                        String coinsDep1 = coinsDep+"";


                        String coinsDeposited = coinsDep < 1000 ? coinsDep1 : coinsDep >= 1000 && coinsDep < 1000000 ? coinsDep1.substring(0, coinsDep1.length()-3)+"k" : coinsDep >= 1000000 && coinsDep < 1000000000 ? coinsDep1.substring(0, coinsDep1.length()-6)+"M" : coinsDep1.substring(0, coinsDep1.length()-9)+"B";




                        ColeCrafterSlayers.setCoins(player,bank+purse);
                        ColeCrafterSlayers.setBank(player,0);

                        String currentBank1 = bank+"";

                        String currentBank = bank < 1000 ? currentBank1 : bank >= 1000 && bank < 1000000 ? currentBank1.substring(0, currentBank1.length()-3)+"k" : bank >= 1000000 && bank < 1000000000 ? currentBank1.substring(0, currentBank1.length()-6)+"M" : currentBank1.substring(0, currentBank1.length()-9)+"B";



                        player.sendMessage(ChatColor.GREEN+"Withdrew "+ChatColor.GOLD+coinsDeposited+" coins"+ChatColor.GREEN+"! There's now "+ChatColor.GOLD+currentBank+" coins"+ChatColor.GREEN+" left in the account!");



                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                        SlayerMenu.openBank(player);
                    }

                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Half the account")) {
                        long bank = ColeCrafterSlayers.getBank(player);
                        long purse = ColeCrafterSlayers.getCoins(player);

                        long coinsDep = bank/2;

                        String coinsDep1 = coinsDep+"";


                        String coinsDeposited = coinsDep < 1000 ? coinsDep1 : coinsDep >= 1000 && coinsDep < 1000000 ? coinsDep1.substring(0, coinsDep1.length()-3)+"k" : coinsDep >= 1000000 && coinsDep < 1000000000 ? coinsDep1.substring(0, coinsDep1.length()-6)+"M" : coinsDep1.substring(0, coinsDep1.length()-9)+"B";


                        ColeCrafterSlayers.setBank(player,bank/2);
                        ColeCrafterSlayers.setCoins(player,bank/2+purse);

                        String currentBank1 = bank+"";

                        String currentBank = bank < 1000 ? currentBank1 : bank >= 1000 && bank < 1000000 ? currentBank1.substring(0, currentBank1.length()-3)+"k" : bank >= 1000000 && bank < 1000000000 ? currentBank1.substring(0, currentBank1.length()-6)+"M" : currentBank1.substring(0, currentBank1.length()-9)+"B";




                        player.sendMessage(ChatColor.GREEN+"Withdrew "+ChatColor.GOLD+coinsDeposited+" coins"+ChatColor.GREEN+"! There's now "+ChatColor.GOLD+currentBank+" coins"+ChatColor.GREEN+" left in the account!");


                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                        SlayerMenu.openBank(player);
                    }


                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Withdraw 20%")) {
                        long bank = ColeCrafterSlayers.getBank(player);
                        long purse = ColeCrafterSlayers.getCoins(player);

                        long coinsDep = bank/5;

                        String coinsDep1 = coinsDep+"";


                        String coinsDeposited = coinsDep < 1000 ? coinsDep1 : coinsDep >= 1000 && coinsDep < 1000000 ? coinsDep1.substring(0, coinsDep1.length()-3)+"k" : coinsDep >= 1000000 && coinsDep < 1000000000 ? coinsDep1.substring(0, coinsDep1.length()-6)+"M" : coinsDep1.substring(0, coinsDep1.length()-9)+"B";





                        ColeCrafterSlayers.setCoins(player,(bank/5)+purse);
                        ColeCrafterSlayers.setBank(player,(bank/5)*4);

                        String currentBank1 = bank+"";

                        String currentBank = bank < 1000 ? currentBank1 : bank >= 1000 && bank < 1000000 ? currentBank1.substring(0, currentBank1.length()-3)+"k" : bank >= 1000000 && bank < 1000000000 ? currentBank1.substring(0, currentBank1.length()-6)+"M" : currentBank1.substring(0, currentBank1.length()-9)+"B";


                        player.sendMessage(ChatColor.GREEN+"Withdrew "+ChatColor.GOLD+coinsDeposited+" coins"+ChatColor.GREEN+"! There's now "+ChatColor.GOLD+currentBank+" coins"+ChatColor.GREEN+" left in the account!");



                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                        SlayerMenu.openBank(player);
                    }
















                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Deposit Coins")) {
                        SlayerMenu.bankerInv.put(player, Bukkit.createInventory(player, 36, ChatColor.DARK_GRAY + "Bank Deposit"));

                        Inventory inv =  SlayerMenu.bankerInv.get(player);


                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        Scoreboard scoreboard = manager.getMainScoreboard();

                        long coins1 = ColeCrafterSlayers.getBank(player);
                        long purse = ColeCrafterSlayers.getCoins(player);

                        NumberFormat numberFormat = NumberFormat.getInstance();

                        numberFormat.setGroupingUsed(true);

                        String formattedCoins = numberFormat.format(coins1);

                        String formattedPurse = numberFormat.format(purse);

                        String formattedPurse1 = numberFormat.format(purse/2);

                        for (int i = 0; i < 36; i++) {

                            if (i == 11) {
                                ItemStack item = new ItemStack(Material.CHEST, 64);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + "Your whole purse");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.DARK_GRAY + "Bank deposit");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.GRAY + "Current balance: "+ChatColor.GOLD+formattedCoins+".0");
                                lore.add(ChatColor.GRAY + "Amount to deposit: "+ChatColor.GOLD+formattedPurse+".0");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.YELLOW + "Click to deposit coins!");

                                meta.setLore(lore);
                                item.setItemMeta(meta);

                                inv.setItem(i, item);
                            } else if (i == 13) {
                                ItemStack item = new ItemStack(Material.CHEST, 32);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + "Half your purse");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.DARK_GRAY + "Bank deposit");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.GRAY + "Current balance: "+ChatColor.GOLD+formattedCoins+".0");
                                lore.add(ChatColor.GRAY + "Amount to deposit: "+ChatColor.GOLD+formattedPurse1+".0");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.YELLOW + "Click to deposit coins!");

                                meta.setLore(lore);
                                item.setItemMeta(meta);

                                inv.setItem(i, item);
                            } else if (i == 15) {
                                ItemStack item = new ItemStack(Material.OAK_SIGN, 1);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + "Specific Amount");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.GRAY + "Current balance: "+ChatColor.GOLD+formattedCoins+".0");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.YELLOW + "Click to deposit coins!");

                                meta.setLore(lore);
                                item.setItemMeta(meta);

                                inv.setItem(i, item);
                            } else {
                                inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                            }
                        }
                        SlayerMenu.bankerInv.put(player, inv);
                        player.openInventory(SlayerMenu.bankerInv.get(player));
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Withdraw Coins")) {
                        SlayerMenu.bankerInv.put(player, Bukkit.createInventory(player, 36, ChatColor.DARK_GRAY + "Bank Withdrawal"));

                        Inventory inv =  SlayerMenu.bankerInv.get(player);


                        long coins1 = ColeCrafterSlayers.getBank(player);
                        long purse = ColeCrafterSlayers.getCoins(player);

                        NumberFormat numberFormat = NumberFormat.getInstance();

                        numberFormat.setGroupingUsed(true);

                        String formattedCoins = numberFormat.format(coins1);


                        String formattedPurse1 = numberFormat.format(coins1/2);
                        String formattedPurse2 = numberFormat.format(coins1/5);

                        for (int i = 0; i < 36; i++) {

                            if (i == 10) {
                                ItemStack item = new ItemStack(Material.DISPENSER, 64);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + "Everything in the account");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.DARK_GRAY + "Bank withdrawal");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.GRAY + "Current balance: "+ChatColor.GOLD+formattedCoins+".0");
                                lore.add(ChatColor.GRAY + "Amount to withdraw: "+ChatColor.GOLD+formattedCoins+".0");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.YELLOW + "Click to withdraw coins!");

                                meta.setLore(lore);
                                item.setItemMeta(meta);

                                inv.setItem(i, item);
                            } else if (i == 12) {
                                ItemStack item = new ItemStack(Material.DISPENSER, 32);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + "Half the account");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.DARK_GRAY + "Bank withdrawal");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.GRAY + "Current balance: "+ChatColor.GOLD+formattedCoins+".0");
                                lore.add(ChatColor.GRAY + "Amount to withdraw: "+ChatColor.GOLD+formattedPurse1+".0");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.YELLOW + "Click to withdraw coins!");

                                meta.setLore(lore);
                                item.setItemMeta(meta);

                                inv.setItem(i, item);
                            } else if (i == 14) {
                                ItemStack item = new ItemStack(Material.DISPENSER, 1);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + "Withdraw 20%");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.DARK_GRAY + "Bank withdrawal");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.GRAY + "Current balance: "+ChatColor.GOLD+formattedCoins+".0");
                                lore.add(ChatColor.GRAY + "Amount to withdraw: "+ChatColor.GOLD+formattedPurse2+".0");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.YELLOW + "Click to withdraw coins!");

                                meta.setLore(lore);
                                item.setItemMeta(meta);

                                inv.setItem(i, item);
                            } else if (i == 16) {
                                ItemStack item = new ItemStack(Material.OAK_SIGN, 1);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + "Specific Amount");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.GRAY + "Current balance: "+ChatColor.GOLD+formattedCoins+".0");
                                lore.add(ChatColor.GRAY + " ");
                                lore.add(ChatColor.YELLOW + "Click to withdraw coins!");

                                meta.setLore(lore);
                                item.setItemMeta(meta);

                                inv.setItem(i, item);
                            } else {
                                inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                            }
                        }
                        SlayerMenu.bankerInv.put(player, inv);
                        player.openInventory(SlayerMenu.bankerInv.get(player));
                    }
                }
            }
            e.setCancelled(true);
        }


        for (ItemStack item : e.getInventory().getContents()) {
            if (item == null) continue;
            if (item.hasItemMeta()) {
                if (item.getItemMeta().hasDisplayName()) {
                    if (item.getItemMeta().getDisplayName().equals(ChatColor.RED+"Sell Item")) {
                        e.setCancelled(true);
                    }
                }
            }
        }
        if (CalenderCommand.calendar.get(player) != null) {
            Map.Entry<Byte, Inventory> inv = null;
            for (Map.Entry<Byte, Inventory> inv1 : CalenderCommand.calendar.get(player).entrySet()) {
                if (e.getInventory().equals(inv1.getValue())) {
                    inv = inv1;
                    e.setCancelled(true);
                }
            }
            if (inv != null) {
                if (e.getCurrentItem().hasItemMeta()) {
                    if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        Scoreboard scoreboard = manager.getMainScoreboard();

                        Score day = scoreboard.getObjective("day").getScore("Time");
                        Score month = scoreboard.getObjective("month").getScore("Time");
                        Score year = scoreboard.getObjective("year").getScore("Time");


                        String month1 = "";


                        HashMap<Player, HashMap<Byte, Inventory>> calendar = CalenderCommand.calendar;
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Previous Page")) {


                            byte month2 = (byte) (inv.getKey() - 1);


                            switch (month2) {
                                case 1:
                                    month1 = "Early Spring";
                                    break;
                                case 2:
                                    month1 = "Spring";
                                    break;
                                case 3:
                                    month1 = "Late Spring";
                                    break;
                                case 4:
                                    month1 = "Early Summer";
                                    break;
                                case 5:
                                    month1 = "Summer";
                                    break;
                                case 6:
                                    month1 = "Late Summer";
                                    break;
                                case 7:
                                    month1 = "Early Autumn";
                                    break;
                                case 8:
                                    month1 = "Autumn";
                                    break;
                                case 9:
                                    month1 = "Late Autumn";
                                    break;
                                case 10:
                                    month1 = "Early Winter";
                                    break;
                                case 11:
                                    month1 = "Winter";
                                    break;
                                case 12:
                                    month1 = "Late Winter";
                                    break;

                            }

                            calendar.putIfAbsent(player, new HashMap<>());

                            calendar.get(player).putIfAbsent((byte) (month2), Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "" + month1 + ", Year " + year.getScore()));

                            Inventory monthPage = calendar.get(player).get((byte) (month2));


                            int j = 1;
                            for (int i = 0; i < 54; i++) {
                                if ((i > 0 && i < 8) || (i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 40)) {
                                    ItemStack noEvent = new ItemStack(Material.PAPER, 1);
                                    ItemMeta meta = noEvent.getItemMeta();
                                    ArrayList<String> lore = new ArrayList<>();

                                    boolean eventExists = false;

                                    if (j <= 31 && j >= 29 && month2 == 12) {
                                        noEvent.setType(Material.CAKE);

                                        lore.add(ChatColor.GRAY + "All Day: " + ChatColor.LIGHT_PURPLE + "New Year Celebration");
                                        eventExists = true;
                                    }
                                    if (!eventExists) {
                                        lore.add(ChatColor.GRAY + "No events");
                                    } else {
                                        noEvent.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                                    }
                                    if (j == day.getScore() && month2 == month.getScore()) {
                                        noEvent.setType(Material.LIME_DYE);
                                    }
                                    noEvent.setAmount(j);

                                    meta.setDisplayName(ChatColor.GREEN + "Day " + j);

                                    meta.setLore(lore);
                                    noEvent.setItemMeta(meta);
                                    monthPage.setItem(i, noEvent);
                                    j++;
                                } else if (i == 45 && month2 > 1) {
                                    ItemStack back = new ItemStack(Material.ARROW, 1);

                                    ItemMeta meta = back.getItemMeta();
                                    meta.setDisplayName(ChatColor.GREEN + "Previous Page");
                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(ChatColor.YELLOW + "Page " + (month2 - 1));
                                    meta.setLore(lore);
                                    back.setItemMeta(meta);
                                    monthPage.setItem(i, back);
                                } else if (i == 53 && month2 < 12) {
                                    ItemStack back = new ItemStack(Material.ARROW, 1);

                                    ItemMeta meta = back.getItemMeta();
                                    meta.setDisplayName(ChatColor.GREEN + "Next Page");
                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(ChatColor.YELLOW + "Page " + (month2 + 1));
                                    meta.setLore(lore);
                                    back.setItemMeta(meta);
                                    monthPage.setItem(i, back);
                                } else {
                                    monthPage.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                }

                            }

                            player.openInventory(monthPage);
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Next Page")) {


                            byte month2 = (byte) (inv.getKey() + 1);


                            switch (month2) {
                                case 1:
                                    month1 = "Early Spring";
                                    break;
                                case 2:
                                    month1 = "Spring";
                                    break;
                                case 3:
                                    month1 = "Late Spring";
                                    break;
                                case 4:
                                    month1 = "Early Summer";
                                    break;
                                case 5:
                                    month1 = "Summer";
                                    break;
                                case 6:
                                    month1 = "Late Summer";
                                    break;
                                case 7:
                                    month1 = "Early Autumn";
                                    break;
                                case 8:
                                    month1 = "Autumn";
                                    break;
                                case 9:
                                    month1 = "Late Autumn";
                                    break;
                                case 10:
                                    month1 = "Early Winter";
                                    break;
                                case 11:
                                    month1 = "Winter";
                                    break;
                                case 12:
                                    month1 = "Late Winter";
                                    break;

                            }

                            calendar.putIfAbsent(player, new HashMap<>());

                            calendar.get(player).putIfAbsent((byte) (month2), Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "" + month1 + ", Year " + year.getScore()));

                            Inventory monthPage = calendar.get(player).get((byte) (month2));


                            int j = 1;
                            for (int i = 0; i < 54; i++) {
                                if ((i > 0 && i < 8) || (i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 40)) {
                                    ItemStack noEvent = new ItemStack(Material.PAPER, 1);
                                    ItemMeta meta = noEvent.getItemMeta();
                                    ArrayList<String> lore = new ArrayList<>();

                                    boolean eventExists = false;

                                    if (j <= 31 && j >= 29 && month2 == 12) {
                                        noEvent.setType(Material.CAKE);
                                        noEvent.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                        lore.add(ChatColor.GRAY + "All Day: " + ChatColor.LIGHT_PURPLE + "New Year Celebration");
                                        eventExists = true;
                                    }
                                    if (!eventExists) {
                                        lore.add(ChatColor.GRAY + "No events");
                                    }
                                    if (j == day.getScore() && month2 == month.getScore()) {
                                        noEvent.setType(Material.LIME_DYE);
                                    }
                                    noEvent.setAmount(j);

                                    meta.setDisplayName(ChatColor.GREEN + "Day " + j);

                                    meta.setLore(lore);
                                    noEvent.setItemMeta(meta);
                                    monthPage.setItem(i, noEvent);
                                    j++;
                                } else if (i == 45 && month2 > 1) {
                                    ItemStack back = new ItemStack(Material.ARROW, 1);

                                    ItemMeta meta = back.getItemMeta();
                                    meta.setDisplayName(ChatColor.GREEN + "Previous Page");
                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(ChatColor.YELLOW + "Page " + (month2 - 1));
                                    meta.setLore(lore);
                                    back.setItemMeta(meta);
                                    monthPage.setItem(i, back);
                                } else if (i == 53 && month2 < 12) {
                                    ItemStack back = new ItemStack(Material.ARROW, 1);

                                    ItemMeta meta = back.getItemMeta();
                                    meta.setDisplayName(ChatColor.GREEN + "Next Page");
                                    ArrayList<String> lore = new ArrayList<>();
                                    lore.add(ChatColor.YELLOW + "Page " + (month2 + 1));
                                    meta.setLore(lore);
                                    back.setItemMeta(meta);
                                    monthPage.setItem(i, back);
                                } else {
                                    monthPage.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                                }

                            }

                            player.openInventory(monthPage);
                        }
                    }
                }
            }
        }

        for (Inventory inv : StatsCommand.statsInventorys) {
            if (e.getInventory().equals(inv)) {
                e.setCancelled(true);
            }
        }
        if (e.getClickedInventory().equals(craftingGUI.get(player))) {
            if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE || e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) {
                e.setCancelled(true);
            }



            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Exit")) {

                        player.closeInventory();
                        e.setCancelled(true);
                    }
                }
            }
            if (e.getCurrentItem().getType() == Material.BARRIER) {
                e.setCancelled(true);
            }


            if (e.getSlot() == 24 && e.getInventory().getItem(e.getSlot()).getType() == Material.AIR) {
                e.setCancelled(true);
            }


            if (e.getSlot() == 24 && !(e.isCancelled())) {


                if (e.isShiftClick()) {
                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            e.getInventory().setItem(24, new ItemStack(Material.BARRIER));
                        }
                    }, 1);
                    ColeCrafterSlayers.consumeCraft(player, e.getInventory(), ColeCrafterSlayers.craftingAmounts.get(player), true, ColeCrafterSlayers.craftingResult.get(player));
                } else {
                    ColeCrafterSlayers.scheduleSyncDelayedTask(new Runnable() {
                        @Override
                        public void run() {
                            e.getInventory().setItem(24, new ItemStack(Material.BARRIER));
                        }
                    }, 1);
                    ColeCrafterSlayers.consumeCraft(player, e.getInventory(), ColeCrafterSlayers.craftingAmounts.get(player), false, ColeCrafterSlayers.craftingResult.get(player));
                }
            }

        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            craftingGUI.putIfAbsent(player, Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY+"Craft Item"));

            Inventory inv = craftingGUI.get(player);

            for (byte i = 0; i < 54; i++) { 
                if ((i >= 0 && i <= 9) || (i > 12 && i < 19) || (i > 21 && i < 24) || (i > 24 && i < 28) || (i > 30 && i < 45)) {
                    inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
                }
                if ((i >= 45 && i <= 48 ) || (i >= 50 && i <= 53)) {
                    inv.setItem(i, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
                }
                if (i == 49) {
                    ItemStack item = new ItemStack(Material.BARRIER, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.RED+"Close");
                    item.setItemMeta(meta);
                    inv.setItem(i, item);
                }


            }

            player.openInventory(inv);


        }
        return true;
    }
}
