package com.Rexe0.CustomCrafting;

import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Items.CustomItem;
import com.Rexe0.Items.Materials.CustomMaterial;
import com.Rexe0.Mobs.CustomMob;
import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeCommand implements CommandExecutor {

    static HashMap<Player, Inventory> recipeGUI = new HashMap<>();
    public static HashMap<Player, Integer> recipeBookPage = new HashMap<>();
    public static HashMap<Player, Integer> recipeBookIndex = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {


                Player player = (Player) sender;

                Inventory inv = recipeGUI.get(player);

                if (inv == null) {
                    recipeGUI.put(player, Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "Recipe Book"));

                    inv = recipeGUI.get(player);

                }

                recipeBookPage.put(player, 0);


                HashMap<Integer, CustomRecipe> recipeWithSlot = new HashMap<>();
                int j = 10;
                recipeBookIndex.put(player, 0);
                int k = recipeBookIndex.get(player);
                ArrayList<ItemStack> alreadyDoneItems = new ArrayList<>();
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

                recipeBookIndex.put(player, k+1);


                int h = 0;
                for (int l = 0; l < CustomRecipe.customRecipes.size() ; l++) {
                    if (CustomRecipe.customRecipes.get(l) == null) break;


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

                    h++;
                }




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
                    } else if (i == 53 && (h > (recipeBookPage.get(player)+1)*28 )) {
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


                } else {
                ItemStack resultItem = CustomItem.getItemClass(args[0].toUpperCase());

                ItemStack resultMaterial = CustomMaterial.getItemClass(args[0].toUpperCase());

                ItemStack result = null;

                if (resultItem == null && resultMaterial == null) {
                    sender.sendMessage(ChatColor.RED+"Not a valid item ID.");
                    return true;
                }
                if (resultItem == null) {
                    result = resultMaterial;
                }
                if (resultMaterial == null) {
                    result = resultItem;
                }

                Player player = (Player) sender;

                Inventory inv = recipeGUI.get(player);

                if (inv == null) {
                    recipeGUI.put(player, Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY+"Recipe Book"));

                    inv = recipeGUI.get(player);



                }
                for (byte i = 0; i < 54; i++) {
                    if ((i >= 0 && i <= 9) || (i > 12 && i < 19) || (i > 21 && i < 24) || (i > 24 && i < 28) || (i > 30 && i < 45)) {
                        inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
                    }
                    if ((i >= 45 && i <= 48 ) || (i >= 50 && i <= 53)) {
                        inv.setItem(i, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
                    } else if (i == 49) {
                        ItemStack item = new ItemStack(Material.BOOK, 1);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(ChatColor.GREEN+"Recipe Book");
                        item.setItemMeta(meta);
                        inv.setItem(i, item);
                    }


                }



                CustomRecipe recipe = null;
                for (int i = 0; i < CustomRecipe.customRecipes.size(); i++) {
                    if (ColeCrafterSlayers.isEqual(CustomRecipe.customRecipes.get(i).getResult(), result)) {
                        recipe = CustomRecipe.customRecipes.get(i);
                    }
                }

                if (recipe == null) {
                    player.sendMessage(ChatColor.RED+"No recipe for that item found.");
                    return true;
                }

                result.setAmount(recipe.getAmount());
                inv.setItem(24, result);
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

                inv.setItem(10, item9);
                inv.setItem(11, item10);
                inv.setItem(12, item11);
                inv.setItem(19, item12);
                inv.setItem(20, item13);
                inv.setItem(21, item14);
                inv.setItem(28, item15);
                inv.setItem(29, item16);
                inv.setItem(30, item17);

                player.openInventory(inv);



            }
        }
        return true;
    }
}
