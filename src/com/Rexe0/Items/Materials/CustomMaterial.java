package com.Rexe0.Items.Materials;

import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.Skull;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

public class CustomMaterial extends ItemStack {
    private String itemID;
    private int value;

    public CustomMaterial(Material material, int amount, String name, String rarity, String itemID, int value) {
        super(material, amount);
        ItemMeta meta = this.getItemMeta();

        this.itemID = itemID;

        this.value = value;



        String rarityColor = rarity.equalsIgnoreCase("COMMON") ? ChatColor.WHITE+"" : rarity.equalsIgnoreCase("UNCOMMON") ? ChatColor.GREEN+"" :
                rarity.equalsIgnoreCase("RARE") ? ChatColor.BLUE+"" : rarity.equalsIgnoreCase("EPIC") ? ChatColor.DARK_PURPLE+"" : rarity.equalsIgnoreCase("LEGENDARY") ? ChatColor.GOLD+"" :
                        rarity.equalsIgnoreCase("SPECIAL") ? ChatColor.LIGHT_PURPLE+"" : ChatColor.GRAY+"";

        meta.setDisplayName(ChatColor.RESET+""+rarityColor+name);


        ArrayList<String> Lore = new ArrayList<>();


        NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemID");

        meta.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, itemID);

        NamespacedKey rarityKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "rarity");

        meta.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, rarity);

        NamespacedKey ItemTypeKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemType");

        meta.getPersistentDataContainer().set(ItemTypeKey, PersistentDataType.STRING, "MATERIAL");


        switch (itemID) {
            case "ENDER_ESSENCE":
                Lore.add(ChatColor.GRAY+"Charged with the fury");
                Lore.add(ChatColor.GRAY+"of "+ChatColor.DARK_PURPLE+"The End Lord");
                break;
        }

        Lore.add(" ");
        Lore.add(rarityColor+ChatColor.BOLD+""+rarity.toUpperCase()+" MATERIAL");
        meta.setLore(Lore);

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        this.setItemMeta(meta);

        if (itemID.equals("EMBER_FRAGMENT")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/5b8f21edb71169080c0dd42935ee00242c8cc9bb91659ab700489af9b7c58e17", this, UUID.fromString("f5e57d77-3149-4913-a7e8-716ab0224c25")));
        } else if (itemID.equals("BEHEADED_HORROR")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/dbad99ed3c820b7978190ad08a934a68dfa90d9986825da1c97f6f21f49ad626", this));
        }
    }

    public static CustomMaterial getItemClass(String itemID) {
        switch (itemID.toUpperCase()) {
            case "GOLD_INGOT":
                return new GoldIngot();
            case "IRON_INGOT":
                return new IronIngot();
            case "ENCHANTED_IRON_INGOT":
                return new EnchantedIron();
            case "DIAMOND":
                return new Diamond();
            case "COBBLESTONE":
                return new Cobblestone();
            case "OAK_PLANKS":
                return new OakPlanks();
            case "STICK":
                return new Stick();
            case "GHAST_TEAR":
                return new GhastTear();
            case "ENCHANTED_GHAST_TEAR":
                return new EnchantedGhastTear();
            case "ENCHANTED_DIAMOND":
                return new EnchantedDiamond();
            case "ENCHANTED_DIAMOND_BLOCK":
                return new EnchantedDiamondBlock();
            case "ENCHANTED_STRING":
                return new EnchantedString();
            case "STRING":
                return new StringItem();
            case "REVENANT_FLESH":
                return new RevenantFlesh();
            case "REVENANT_VISCERA":
                return new RevenantViscera();
            case "SCYTHE_BLADE":
                return new ScytheBlade();
            case "ENDER_ESSENCE":
                return new EnderEssence();
            case "EMBER_FRAGMENT":
                return new EmberFragment();
            case "RAW_CHICKEN":
                return new Chicken();
            case "RAW_SALMON":
                return new RawSalmon();
            case "ROTTED_TOOTH":
                return new RottedTooth();
            case "BEHEADED_HORROR":
                return new BeheadedHorror();
            case "ROTTEN_FLESH":
                return new RottenFlesh();
            case "ENCHANTED_ROTTEN_FLESH":
                return new EnchantedRottenFlesh();
            case "UNDEAD_CATALYST":
                return new UndeadCatalyst();
            case "REVENANT_CATALYST":
                return new RevenantCatalyst();
            case "GUNPOWDER":
                return new Gunpowder();
            case "EMERALD":
                return new Emerald();
            case "ENCHANTED_EMERALD":
                return new EnchantedEmerald();
            case "BONE":
                return new Bone();
            case "ENCHANTED_BONE":
                return new EnchantedBone();
            case "ENCHANTED_EMERALD_BLOCK":
                return new EnchantedEmeraldBlock();
            case "DIAMOND_BLOCK":
                return new DiamondBlock();
            case "OAK_LOG":
                return new OakLog();
            case "WHEAT":
                return new Wheat();
            case "CARROT":
                return new Carrot();
            case "POTATO":
                return new Potato();
            case "OBSIDIAN":
                return new Obsidian();
            case "HAY_BALE":
                return new HayBale();
            case "ENCHANTED_HAY_BALE":
                return new EnchantedHayBale();
            case "ENCHANTED_POTATO":
                return new EnchantedPotato();
            case "ENCHANTED_BAKED_POTATO":
                return new EnchantedBakedPotato();
            case "SUGAR_CANE":
                return new SugarCane();
            case "ENCHANTED_SUGAR":
                return new EnchantedSugar();
            case "ENCHANTED_SUGAR_CANE":
                return new EnchantedSugarCane();
            case "PAPER":
                return new Paper();
            case "COAL":
                return new Coal();
            case "ENCHANTED_COAL":
                return new EnchantedCoal();
            case "PUMPKIN":
                return new Pumpkin();
            case "MELON_SLICE":
                return new MelonSlice();
            case "REDSTONE_DUST":
                return new RedstoneDust();
            case "LAPIS_LAZULI":
                return new LapisLazuli();
            case "BLAZE_ROD":
                return new BlazeRod();
            case "ENCHANTED_BLAZE_POWDER":
                return new EnchantedBlazePowder();
            case "ENCHANTED_BLAZE_ROD":
                return new EnchantedBlazeRod();
            case "ICE":
                return new Ice();
            case "ENCHANTED_ICE":
                return new EnchantedIce();
            case "ENCHANTED_PACKED_ICE":
                return new EnchantedPackedIce();
            case "SLIME_BALL":
                return new SlimeBall();
            case "MAGMA_CREAM":
                return new MagmaCream();
            case "ENCHANTED_MAGMA_CREAM":
                return new EnchantedMagmaCream();
            case "NETHERITE_SCRAP":
                return new NetheriteScrap();
            case "MUSHROOM":
                return new Mushroom();
            case "ENCHANTED_MUSHROOM":
                return new EnchantedMushroom();
            default:
                return null;
        }
    }
    public String getItemID() {
        return this.itemID;
    }

    public int getValue() {
        return this.value;
    }
}