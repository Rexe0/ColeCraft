package com.Rexe0.CustomCrafting;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomRecipe {
    private ItemStack result;
    private int amount;
    public String patterns;
    private boolean isHidden;
    public HashMap<Character, ItemStack> ingredients = new HashMap<>();
    public HashMap<Character, Integer> ingredientsAmount = new HashMap<>();
    public static ArrayList<CustomRecipe> customRecipes = new ArrayList<>();

    public int getAmount() {
        return this.amount;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public CustomRecipe(ItemStack item, int amount, boolean isHidden) {
        this.result = item;
        this.amount = amount;
        this.isHidden = isHidden;

    }

    public void addPatterns(String pattern, String pattern1, String pattern2) {
        this.patterns = pattern+pattern1+pattern2;
    }

    public void setIngredient(char character, ItemStack item, int amount) {
        this.ingredients.put(character, item);
        this.ingredientsAmount.put(character, amount);
    }
}
