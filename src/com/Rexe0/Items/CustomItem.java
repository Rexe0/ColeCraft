package com.Rexe0.Items;

import com.Rexe0.ColeCrafterSlayers;
import com.Rexe0.CustomCrafting.CustomRecipe;
import com.Rexe0.Items.Accessories.*;
import com.Rexe0.Items.Armor.Agriculture.*;
import com.Rexe0.Items.Armor.ElegantTuxedo.*;
import com.Rexe0.Items.Armor.Ember.*;
import com.Rexe0.Items.Armor.FarmSuit.*;
import com.Rexe0.Items.Armor.HardenedDiamond.*;
import com.Rexe0.Items.Armor.Blaze.*;
import com.Rexe0.Items.Armor.FrozenBlaze.*;
import com.Rexe0.Items.Armor.Magma.*;
import com.Rexe0.Items.Armor.Netherite.*;
import com.Rexe0.Items.Armor.Speedster.*;
import com.Rexe0.Items.Armor.Lapis.*;
import com.Rexe0.Items.Armor.Miner.*;
import com.Rexe0.Items.Armor.Perfect.*;
import com.Rexe0.Items.Armor.Pieces.SlimeHat;
import com.Rexe0.Items.Armor.Pieces.SpiderBoots;
import com.Rexe0.Items.Armor.Pieces.WaterHydraHead;
import com.Rexe0.Items.Armor.Revenant.*;
import com.Rexe0.Items.Armor.SuperiorDragon.*;
import com.Rexe0.Items.Armor.Vanilla.*;
import com.Rexe0.Items.Bows.*;
import com.Rexe0.Items.Materials.*;
import com.Rexe0.Items.SpecialItems.*;
import com.Rexe0.Items.SummoningItems.*;
import com.Rexe0.Items.Swords.Common.*;
import com.Rexe0.Items.Swords.Epic.*;
import com.Rexe0.Items.Swords.Legendary.*;
import com.Rexe0.Items.Swords.Rare.*;
import com.Rexe0.Items.Swords.Uncommon.*;
import com.Rexe0.Items.Tools.*;
import com.Rexe0.Items.Wands.*;
import com.Rexe0.Skull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class CustomItem extends ItemStack {


    private String name = "";
    private String itemID;
    private int value;
    public CustomItem(Material material, int amount, String name, String rarity, float baseDamage, int speed, int defense, float health, float attackSpeed, float magicFind, String itemID, String itemType, int value) {
        this.setType(material);
        this.setAmount(amount);
        ItemMeta meta = this.getItemMeta();
        this.name = name;

        this.value = value;

        this.itemID = itemID;


        String rarityColor = rarity.equalsIgnoreCase("COMMON") ? ChatColor.WHITE+"" : rarity.equalsIgnoreCase("UNCOMMON") ? ChatColor.GREEN+"" :
                rarity.equalsIgnoreCase("RARE") ? ChatColor.BLUE+"" : rarity.equalsIgnoreCase("EPIC") ? ChatColor.DARK_PURPLE+"" : rarity.equalsIgnoreCase("LEGENDARY") ? ChatColor.GOLD+"" :
                rarity.equalsIgnoreCase("SPECIAL") ? ChatColor.LIGHT_PURPLE+"" : ChatColor.GRAY+"";

        meta.setDisplayName(ChatColor.RESET+""+rarityColor+name);
        ArrayList<String> Lore = new ArrayList<>();
        boolean damageAndDefence = (baseDamage != 0 && (defense != 0 || health != 0));
        if (baseDamage != 0) Lore.add(ChatColor.GRAY+"Damage: "+ChatColor.RED+"+"+baseDamage);
        if (attackSpeed != 0) Lore.add(ChatColor.GRAY+"Attack Speed: "+ChatColor.RED+"+"+attackSpeed+"%");
        if (damageAndDefence) Lore.add(" ");
        if (defense != 0) Lore.add(ChatColor.GRAY+"Defense: "+ChatColor.GREEN+"+"+defense);
        if (health != 0) Lore.add(ChatColor.GRAY+"Health: "+ChatColor.GREEN+"+"+health);
        if (speed != 0) Lore.add(ChatColor.GRAY+"Speed: "+ChatColor.GREEN+"+"+speed+"%");
        if (magicFind != 0) Lore.add(ChatColor.GRAY+"Magic Find: "+ChatColor.GREEN+"+"+magicFind);

        if (itemType.equalsIgnoreCase("SWORD")) {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", baseDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "attackSpeed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));

        }
        if (itemType.equalsIgnoreCase("HELMET")) {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", baseDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "health", health, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", defense, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "speed", (speed/100f), AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD));
            meta.addAttributeModifier(Attribute.GENERIC_LUCK, new AttributeModifier(UUID.randomUUID(), "magicFind", magicFind, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "attackSpeed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));

        }
        if (itemType.equalsIgnoreCase("CHESTPLATE")) {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", baseDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "health", health, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", defense, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "speed", (speed/100f), AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_LUCK, new AttributeModifier(UUID.randomUUID(), "magicFind", magicFind, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "attackSpeed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));


        }
        if (itemType.equalsIgnoreCase("LEGGINGS")) {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", baseDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "health", health, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", defense, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "speed", (speed/100f), AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_LUCK, new AttributeModifier(UUID.randomUUID(), "magicFind", magicFind, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "attackSpeed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));

        }
        if (itemType.equalsIgnoreCase("BOOTS")) {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", baseDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "health", health, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "defense", defense, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "speed", (speed/100f), AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_LUCK, new AttributeModifier(UUID.randomUUID(), "magicFind", magicFind, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "attackSpeed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));


        }
        if (itemID.equals("SLIME_HAT")) {
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "knockbackResistance", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));

        }



        // Nbt tags
        NamespacedKey ItemIDKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemID");

        meta.getPersistentDataContainer().set(ItemIDKey, PersistentDataType.STRING, itemID);

        NamespacedKey rarityKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "rarity");

        meta.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, rarity);

        NamespacedKey ItemDamageKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemDamage");

        meta.getPersistentDataContainer().set(ItemDamageKey, PersistentDataType.FLOAT, baseDamage);

        NamespacedKey ItemHPBDamageKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "hpbDamage");

        meta.getPersistentDataContainer().set(ItemHPBDamageKey, PersistentDataType.INTEGER, 0);

        NamespacedKey ItemTypeKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "itemType");

        meta.getPersistentDataContainer().set(ItemTypeKey, PersistentDataType.STRING, itemType.toUpperCase());

        NamespacedKey HPBKey = new NamespacedKey(ColeCrafterSlayers.getInstance(), "hotPotatoBooks");

        meta.getPersistentDataContainer().set(HPBKey, PersistentDataType.INTEGER, 0);

        Lore.add(" ");
        switch (itemID) {
            case "ROGUE_SWORD":
                Lore.add(ChatColor.GOLD+"Item Ability: Speed Boost "+ChatColor.YELLOW+""+ChatColor.BOLD+"RIGHT CLICK");
                Lore.add(ChatColor.GRAY+"Increases your movement "+ChatColor.WHITE+"✦");
                Lore.add(ChatColor.WHITE+"Speed"+ChatColor.GRAY+" by "+ChatColor.GREEN+"+40%"+ChatColor.GRAY+" for "+ChatColor.GREEN+"30");
                Lore.add(ChatColor.GRAY+"seconds");
                Lore.add(ChatColor.DARK_GRAY+"Cooldown: "+ChatColor.DARK_AQUA+"40s");
                break;
            case "UNDEAD_SWORD":
                Lore.add(ChatColor.GRAY+"Deals "+ChatColor.GREEN+"+100%"+ChatColor.GRAY+" to");
                Lore.add(ChatColor.GRAY+"Skeletons, Withers, ");
                Lore.add(ChatColor.GRAY+"Zombies and Zombie");
                Lore.add(ChatColor.GRAY+"Pigmen");
                break;
            case "REVENANT_FALCHION":
                Lore.add(ChatColor.GRAY+"Deals "+ChatColor.GREEN+"+150%"+ChatColor.GRAY+" against");
                Lore.add(ChatColor.GRAY+"Zombies");
                break;
            case "REAPER_FALCHION":
                Lore.add(ChatColor.GRAY+"Deals "+ChatColor.GREEN+"+200%"+ChatColor.GRAY+" against");
                Lore.add(ChatColor.GRAY+"Zombies");
                break;
            case "END_SWORD":
                Lore.add(ChatColor.GRAY+"Deals "+ChatColor.GREEN+"+100%"+ChatColor.GRAY+" to");
                Lore.add(ChatColor.GRAY+"Ender Dragons, Endermen, ");
                Lore.add(ChatColor.GRAY+"and Endermites");
                break;
            case "CLEAVER":
                Lore.add(ChatColor.GOLD+"Item Ability: Cleave ");
                Lore.add(ChatColor.GRAY+"When hitting an entity, your");
                Lore.add(ChatColor.GRAY+"sweep attack will deal");
                Lore.add(ChatColor.GREEN+"80% "+ChatColor.GRAY+"of the damage you deal.");
                break;
            case "ASPECT_OF_THE_DRAGONS":
                Lore.add(ChatColor.GOLD+"Item Ability: Dragon Rage "+ChatColor.YELLOW+""+ChatColor.BOLD+"RIGHT CLICK");
                Lore.add(ChatColor.GRAY+"All Monsters in front of you");
                Lore.add(ChatColor.GRAY+"take"+ChatColor.GREEN+" 20 "+ChatColor.GRAY+"damage. Hit");
                Lore.add(ChatColor.GRAY+"monsters take large knockback.");
                break;
            case "FLAMING_SWORD":
                Lore.add(ChatColor.GRAY+"Ignites enemies for "+ChatColor.GREEN+"3"+ChatColor.GRAY+" seconds");
                break;
            case "ASPECT_OF_THE_JERRY":
                Lore.add(ChatColor.GOLD+"Item Ability: Parley "+ChatColor.YELLOW+""+ChatColor.BOLD+"RIGHT CLICK");
                Lore.add(ChatColor.GRAY+"Channel your inner Jerry");
                break;
            case "EMBER_ROD":
                Lore.add(ChatColor.GOLD+"Item Ability: Fire Blast "+ChatColor.YELLOW+""+ChatColor.BOLD+"RIGHT CLICK");
                Lore.add(ChatColor.GRAY+"Shoot 3 Fireballs in rapid");
                Lore.add(ChatColor.GRAY+"succession in front of you!");
                Lore.add(ChatColor.DARK_GRAY+"Cooldown: "+ChatColor.DARK_AQUA+"20s");
                break;
            case "REAPER_SCYTHE":
                Lore.add(ChatColor.GOLD+"Item Ability: Glean "+ChatColor.YELLOW+""+ChatColor.BOLD+"RIGHT CLICK");
                Lore.add(ChatColor.GRAY+"Deals "+ChatColor.GREEN+"50%"+ChatColor.GRAY+" of all nearby");
                Lore.add(ChatColor.GRAY+"monster's current health as");
                Lore.add(ChatColor.RED+"damage"+ChatColor.GRAY+". 10% of the damage");
                Lore.add(ChatColor.GRAY+"dealt is restored to");
                Lore.add(ChatColor.GRAY+"you as health.");
                Lore.add(ChatColor.DARK_GRAY+"Cooldown: "+ChatColor.DARK_AQUA+"60s");
                Lore.add(" ");
                Lore.add(ChatColor.GRAY+"Deals "+ChatColor.GREEN+"+250%"+ChatColor.GRAY+" against");
                Lore.add(ChatColor.GRAY+"Zombies");
                break;
            case "LAPIS_HELMET":
            case "LAPIS_CHESTPLATE":
            case "LAPIS_LEGGINGS":
            case "LAPIS_BOOTS":
                Lore.add(ChatColor.GOLD+"Full Set Bonus: Health ");
                Lore.add(ChatColor.GRAY+"Increases the wearers maximum");
                Lore.add(ChatColor.RED+"❤ Health"+ChatColor.GRAY+" by "+ChatColor.GREEN+"6");
                break;
            case "ELEGANT_TUXEDO_CHESTPLATE":
            case "ELEGANT_TUXEDO_LEGGINGS":
            case "ELEGANT_TUXEDO_BOOTS":
                Lore.add(ChatColor.GOLD+"Full Set Bonus: Dashing!");
                Lore.add(ChatColor.GRAY+"Max health set to"+ChatColor.RED+" 25❤"+ChatColor.GRAY+". Deal");
                Lore.add(ChatColor.RED+"+150%"+ChatColor.GRAY+" more damage!");
                break;
            case "SUPERIOR_DRAGON_BOOTS":
            case "SUPERIOR_DRAGON_HELMET":
            case "SUPERIOR_DRAGON_CHESTPLATE":
            case "SUPERIOR_DRAGON_LEGGINGS":
                Lore.add(ChatColor.GOLD+"Full Set Bonus: Superior Blood");
                Lore.add(ChatColor.GRAY+"Increases the"+ChatColor.RED+" damage"+ChatColor.GRAY+" of the");
                Lore.add(ChatColor.GOLD+"Aspect of the Dragons"+ChatColor.GRAY+" by "+ChatColor.GREEN+"10");
                break;
            case "REVENANT_CHESTPLATE":
            case "REVENANT_LEGGINGS":
            case "REVENANT_BOOTS":
                Lore.add(ChatColor.GOLD+"Piece Bonus: Zombie Bulwark");
                Lore.add(ChatColor.GRAY+"Reduces damage from Zombies by "+ChatColor.GREEN+"10%");
                Lore.add(ChatColor.GRAY+"and gain"+ChatColor.GREEN+" +80 Defense "+ChatColor.GRAY+"against Zombies");
                break;

            case "DEAD_BUSH_OF_LOVE":
                Lore.add(ChatColor.GRAY+"This item was given to the kind");
                Lore.add(ChatColor.GRAY+"souls who helped so much testing");
                Lore.add(ChatColor.GRAY+"ColeCraft SkyBlock! Much love "+ChatColor.RED+"❤");
                break;
            case "SPIDER_BOOTS":
                Lore.add(ChatColor.GOLD+"Item Ability: Double Jump");
                Lore.add(ChatColor.GRAY+"Allows you to double jump with sneak!");
                break;
            case "DEVOUR_RING":
                Lore.add(ChatColor.GRAY+"Heals the wearer "+ChatColor.GREEN+"2%");
                Lore.add(ChatColor.GRAY+"of the damage they do ");
                break;
            case "SOUL_SEEKER_RING":
                Lore.add(ChatColor.GRAY+"Heals the wearer "+ChatColor.GREEN+"3%");
                Lore.add(ChatColor.GRAY+"of the damage they do ");
                break;
            case "SOUL_EATER_RING":
                Lore.add(ChatColor.GRAY+"Heals the wearer "+ChatColor.GREEN+"4%");
                Lore.add(ChatColor.GRAY+"of the damage they do ");
                break;
            case "FRENCH_BREAD":
                Lore.add(ChatColor.GRAY+"It is known that scientists and");
                Lore.add(ChatColor.GRAY+"other experts always find ways");
                Lore.add(ChatColor.GRAY+"to disagree on every subject");
                Lore.add(ChatColor.GRAY+"known to mankind. However,");
                Lore.add(ChatColor.GRAY+"they can all agree on one thing -");
                Lore.add(ChatColor.GRAY+"that this is the greatest meal");
                Lore.add(ChatColor.GRAY+"ever created in history.");
                Lore.add(ChatColor.GRAY+" ");
                Lore.add(ChatColor.GRAY+"All stats are increased by "+ChatColor.GREEN+"3%");
                break;
            case "LE_BAGUETTE":
                Lore.add(ChatColor.GRAY+"It is known that scientists and");
                Lore.add(ChatColor.GRAY+"other experts always find ways");
                Lore.add(ChatColor.GRAY+"to disagree on every subject");
                Lore.add(ChatColor.GRAY+"known to mankind. However,");
                Lore.add(ChatColor.GRAY+"they can all agree on one thing -");
                Lore.add(ChatColor.GRAY+"that this is the greatest meal");
                Lore.add(ChatColor.GRAY+"ever created in history.");
                Lore.add(ChatColor.GRAY+" ");
                Lore.add(ChatColor.GRAY+"All stats are increased by "+ChatColor.GREEN+"4%");
                break;
            case "FRENCH_SANDWICH":
                Lore.add(ChatColor.GRAY+"It is known that scientists and");
                Lore.add(ChatColor.GRAY+"other experts always find ways");
                Lore.add(ChatColor.GRAY+"to disagree on every subject");
                Lore.add(ChatColor.GRAY+"known to mankind. However,");
                Lore.add(ChatColor.GRAY+"they can all agree on one thing -");
                Lore.add(ChatColor.GRAY+"that this is the greatest sandwich");
                Lore.add(ChatColor.GRAY+"ever created in history.");
                Lore.add(ChatColor.GRAY+" ");
                Lore.add(ChatColor.GRAY+"All stats are increased by "+ChatColor.GREEN+"5%");
                Lore.add(ChatColor.GRAY+" ");
                Lore.add(ChatColor.DARK_GRAY+""+ChatColor.ITALIC+"Dedicated to sqndw1ch");
                break;
            case "ZOMBIE_HEART":
            case "CRYSTALLIZED_HEART":
            case "REVIVED_HEART":
                Lore.add(ChatColor.GOLD+"Item Ability: Healing Boost");
                Lore.add(ChatColor.GRAY+"All healing you receive is doubled!");
                break;
            case "REAPER_MASK":
                Lore.add(ChatColor.GOLD+"Item Ability: Disgusting Healing");
                Lore.add(ChatColor.GRAY+"All healing you receive is");
                Lore.add(ChatColor.GREEN+"2.5x"+ChatColor.GRAY+" more effective!");
                break;
            case "ZOMBIE_TALISMAN":
                Lore.add(ChatColor.GRAY+"Reduce damage taken from");
                Lore.add(ChatColor.GRAY+"Zombies by "+ChatColor.GREEN+"5%");
                break;
            case "ZOMBIE_RING":
                Lore.add(ChatColor.GRAY+"Reduce damage taken from");
                Lore.add(ChatColor.GRAY+"Zombies by "+ChatColor.GREEN+"10%");
                break;
            case "ZOMBIE_ARTIFACT":
                Lore.add(ChatColor.GRAY+"Reduce damage taken from");
                Lore.add(ChatColor.GRAY+"Zombies by "+ChatColor.GREEN+"15%");
                break;
            case "ZOMBIE_GEMSTONE":
                Lore.add(ChatColor.GRAY+"Reduce damage taken from");
                Lore.add(ChatColor.GRAY+"Zombies by "+ChatColor.GREEN+"20%");
                break;
            case "ZOMBIE_CROWN":
                Lore.add(ChatColor.GRAY+"Reduce damage taken from");
                Lore.add(ChatColor.GRAY+"Zombies by "+ChatColor.GREEN+"25%");
                break;
            case "FIRST_STRIKE_BEAD":
                Lore.add(ChatColor.GRAY+"Melee strikes deal"+ChatColor.GREEN+" 50% "+ChatColor.GRAY+"more");
                Lore.add(ChatColor.GRAY+"damage to monsters at full health.");
                break;
            case "GIANT_KILLER_NECKLACE":
                Lore.add(ChatColor.GRAY+"Melee strikes deal"+ChatColor.GREEN+" 25% "+ChatColor.GRAY+"more");
                Lore.add(ChatColor.GRAY+"damage to monsters that have");
                Lore.add(ChatColor.GRAY+"more health than you.");
                break;
            case "THUNDERLORD_ARTIFACT":
                Lore.add(ChatColor.GRAY+"Every 3 melee strikes monsters with");
                Lore.add(ChatColor.GRAY+"lighting dealing"+ChatColor.GREEN+" 50% "+ChatColor.GRAY+"of your damage.");
                break;
            case "WAND_OF_HEALING":
                Lore.add(ChatColor.GOLD+"Item Ability: Small Heal");
                Lore.add(ChatColor.GRAY+"Heal "+ChatColor.RED+"10❤ Health");
                Lore.add(ChatColor.DARK_GRAY+"Cooldown: "+ChatColor.DARK_AQUA+"15s");
                break;
            case "WAND_OF_MENDING":
                Lore.add(ChatColor.GOLD+"Item Ability: Medium Heal");
                Lore.add(ChatColor.GRAY+"Heal "+ChatColor.RED+"15❤ Health");
                Lore.add(ChatColor.DARK_GRAY+"Cooldown: "+ChatColor.DARK_AQUA+"18s");
                break;
            case "WAND_OF_RESTORATION":
                Lore.add(ChatColor.GOLD+"Item Ability: Large Heal");
                Lore.add(ChatColor.GRAY+"Heal "+ChatColor.RED+"20❤ Health");
                Lore.add(ChatColor.DARK_GRAY+"Cooldown: "+ChatColor.DARK_AQUA+"24s");
                break;
            case "WAND_OF_REJUVENATION":
                Lore.add(ChatColor.GOLD+"Item Ability: Massive Heal");
                Lore.add(ChatColor.GRAY+"Heal "+ChatColor.RED+"30❤ Health");
                Lore.add(ChatColor.DARK_GRAY+"Cooldown: "+ChatColor.DARK_AQUA+"35s");
                break;
            case "MOSQUITO_BOW":
                Lore.add(ChatColor.GOLD+"Item Ability: Nasty Bite");
                Lore.add(ChatColor.GRAY+"Fully charged shots while");
                Lore.add(ChatColor.GRAY+"sneaking deal "+ChatColor.RED+"+19%");
                Lore.add(ChatColor.GRAY+"more damage but weakens");
                Lore.add(ChatColor.GRAY+"your armor by "+ChatColor.GREEN+"11%"+ChatColor.GRAY+".");
                break;
            case "RUNAANS_BOW":
                Lore.add(ChatColor.GOLD+"Item Ability: Lunar Shadow");
                Lore.add(ChatColor.GRAY+"Shoots an additional "+ChatColor.GREEN+"2"+ChatColor.GRAY+" arrows.");
                Lore.add(ChatColor.GRAY+"Each arrow has a chance to duplicate.");
                break;
            case "FROZEN_SCYTHE":
                Lore.add(ChatColor.GOLD+"Item Ability: Ice Bolt");
                Lore.add(ChatColor.GRAY+"Shoots a bolt forwards that deals");
                Lore.add(ChatColor.RED+"10 Damage "+ChatColor.GRAY+"and briefly freezes");
                Lore.add(ChatColor.GRAY+"any enemies hit with the projectile.");
                break;
            case "HURRICANE_BOW":
                Lore.add(ChatColor.GOLD+"Item Ability: Tempest");
                Lore.add(ChatColor.GRAY+"Shoots an additional");
                Lore.add(ChatColor.GREEN+"4"+ChatColor.GRAY+" arrows.");
                Lore.add(ChatColor.GRAY+"Each arrow has a "+ChatColor.GREEN+"5%"+ChatColor.GRAY+" chance");
                Lore.add(ChatColor.GRAY+"to strike the target with");
                Lore.add(ChatColor.GRAY+"lightning dealing double damage");
                break;
            case "GRAPPLING_HOOK":
                Lore.add(ChatColor.GRAY+"Travel around in style using");
                Lore.add(ChatColor.GRAY+"this Grappling Hook");
                Lore.add(ChatColor.DARK_GRAY+"2 Second Cooldown");
                break;
            case "JUNGLE_AXE":
                Lore.add(ChatColor.GRAY+"A powerful Wooden Axe which");
                Lore.add(ChatColor.GRAY+"can break multiple logs in a");
                Lore.add(ChatColor.GRAY+"single hit!");
                Lore.add(ChatColor.DARK_GRAY+"Cooldown: "+ChatColor.GREEN+"2s");
                break;
            case "TREECAPITATOR":
                Lore.add(ChatColor.GRAY+"A forceful Gold Axe which");
                Lore.add(ChatColor.GRAY+"can break a large amount of logs");
                Lore.add(ChatColor.GRAY+"in a single hit!");
                Lore.add(ChatColor.DARK_GRAY+"Cooldown: "+ChatColor.GREEN+"2s");
                break;
            case "HUNG_THE_FISH":
                Lore.add(ChatColor.GRAY+"The floppy fish may");
                Lore.add(ChatColor.GRAY+"seem harmless but");
                Lore.add(ChatColor.GRAY+"it blackmails other happy fishies");
                Lore.add(ChatColor.GRAY+"of their flappy capes.");
                Lore.add(ChatColor.GRAY+" ");
                Lore.add(ChatColor.DARK_GRAY+""+ChatColor.ITALIC+"Dedicated to f1shs");
                break;
            case "NEW_YEAR_CAKE":

                String day = ""+ Bukkit.getScoreboardManager().getMainScoreboard().getObjective("year").getScore("Time").getScore();

                switch (day.substring(day.length()-1)) {
                    case "1":
                        day = day+"st";
                        break;
                    case "2":
                        day = day+"nd";
                        break;
                    case "3":
                        day = day+"rd";
                        break;
                    default:
                        day = day+"th";
                        break;
                }
                Lore.add(ChatColor.GRAY+"Given to every player as a");
                Lore.add(ChatColor.GRAY+"celebration for the "+day);
                Lore.add(ChatColor.GRAY+"Skyblock year!");

                meta.setDisplayName(ChatColor.RESET+""+rarityColor+day+" Skyblock "+name);

                break;
            case "MINER_HELMET":
            case "MINER_CHESTPLATE":
            case "MINER_LEGGINGS":
            case "MINER_BOOTS":
                Lore.add(ChatColor.GRAY+"Each piece of this armour");
                Lore.add(ChatColor.GRAY+"dramatically increases your");
                Lore.add(ChatColor.GRAY+"defense bonus when inside of a");
                Lore.add(ChatColor.GRAY+"mine");
                break;
            case "MAGMA_HELMET":
            case "MAGMA_CHESTPLATE":
            case "MAGMA_LEGGINGS":
            case "MAGMA_BOOTS":
                Lore.add(ChatColor.GRAY+"Each piece of this armour");
                Lore.add(ChatColor.GRAY+"dramatically increases your");
                Lore.add(ChatColor.GRAY+"health bonus when inside of a");
                Lore.add(ChatColor.GRAY+"the blazing fortress");
                break;
            case "FARM_SUIT_HELMET":
            case "FARM_SUIT_CHESTPLATE":
            case "FARM_SUIT_LEGGINGS":
            case "FARM_SUIT_BOOTS":
                Lore.add(ChatColor.GOLD+"Full Set Bonus: Honest Work");
                Lore.add(ChatColor.GRAY+"Increases your "+ChatColor.WHITE+"✦ Speed");
                Lore.add(ChatColor.GRAY+"by "+ChatColor.GREEN+"20%"+ChatColor.GRAY+" while worn");
                Lore.add(ChatColor.GRAY+"in any farming area.");
                break;
            case "AGRICULTURE_HELMET":
            case "AGRICULTURE_CHESTPLATE":
            case "AGRICULTURE_LEGGINGS":
            case "AGRICULTURE_BOOTS":
                Lore.add(ChatColor.GOLD+"Full Set Bonus: Farm Aura");
                Lore.add(ChatColor.GRAY+"Increases your "+ChatColor.WHITE+"✦ Speed");
                Lore.add(ChatColor.GRAY+"by "+ChatColor.GREEN+"50%"+ChatColor.GRAY+" and all nearby");
                Lore.add(ChatColor.GRAY+"player's "+ChatColor.WHITE+"✦ Speed"+ChatColor.GRAY+" by");
                Lore.add(ChatColor.GRAY+""+ChatColor.GREEN+"20%"+ChatColor.GRAY+" in any farming area.");

                break;
            case "BLAZE_HELMET":
            case "BLAZE_CHESTPLATE":
            case "BLAZE_LEGGINGS":
            case "BLAZE_BOOTS":
                Lore.add(ChatColor.GOLD+"Full Set Bonus: Blazing Aura");
                Lore.add(ChatColor.GRAY+"Damages mobs within "+ChatColor.GREEN+"8"+ChatColor.GRAY+" blocks");
                Lore.add(ChatColor.GRAY+"for "+ChatColor.GREEN+"3%"+ChatColor.GRAY+" of their max"+ChatColor.RED+" ❤");
                Lore.add(ChatColor.RED+"Health"+ChatColor.GRAY+" plus " +ChatColor.RED+"0.5 Damage "+ChatColor.GRAY+"per second");
                Lore.add(ChatColor.GRAY+" ");
                Lore.add(ChatColor.GRAY+"Max "+ChatColor.RED+"10"+ChatColor.GRAY+" damage/s");
                break;
            case "FROZEN_BLAZE_HELMET":
            case "FROZEN_BLAZE_CHESTPLATE":
            case "FROZEN_BLAZE_LEGGINGS":
            case "FROZEN_BLAZE_BOOTS":
                Lore.add(ChatColor.GOLD+"Full Set Bonus: Blazing Aura");
                Lore.add(ChatColor.GRAY+"Damages mobs within "+ChatColor.GREEN+"8"+ChatColor.GRAY+" blocks");
                Lore.add(ChatColor.GRAY+"for "+ChatColor.GREEN+"4%"+ChatColor.GRAY+" of their max"+ChatColor.RED+" ❤");
                Lore.add(ChatColor.RED+"Health"+ChatColor.GRAY+" plus " +ChatColor.RED+"1 Damage "+ChatColor.GRAY+"per second and");
                Lore.add(ChatColor.GRAY+"reduces their"+ChatColor.WHITE+" ✦ Speed"+ChatColor.GRAY+" by 45%.");
                Lore.add(ChatColor.GRAY+" ");
                Lore.add(ChatColor.GRAY+"Max "+ChatColor.RED+"20"+ChatColor.GRAY+" damage/s");
                Lore.add(ChatColor.GRAY+" ");
                Lore.add(ChatColor.GOLD+"Secondary Full Set Bonus: Frozen Death");
                Lore.add(ChatColor.GRAY+"Increases Frozen Scythe's "+ChatColor.RED+"Damage "+ChatColor.GRAY+"by "+ChatColor.RED+"3");
                Lore.add(ChatColor.GRAY+"and decrease the cooldown of the weapon.");
                break;
            case "SPEEDSTER_HELMET":
            case "SPEEDSTER_CHESTPLATE":
            case "SPEEDSTER_LEGGINGS":
            case "SPEEDSTER_BOOTS":
                Lore.add(ChatColor.GOLD+"Full Set Bonus: Sugar Speed");
                Lore.add(ChatColor.GRAY+"Gain "+ChatColor.GREEN+"+20%"+ChatColor.WHITE+" ✦ Speed");
                break;
            case "MOLTEN_CORE":
                Lore.add(ChatColor.GRAY+"The core of a giant cubic");
                Lore.add(ChatColor.GRAY+"creature that dwells on the");
                Lore.add(ChatColor.GRAY+"surface of the underworld...");
                Lore.add(ChatColor.DARK_GRAY+""+ChatColor.ITALIC+"Summons the Magma Cube Boss");
                break;
        }
        Lore.add(" ");
        Lore.add(rarityColor+ChatColor.BOLD+""+rarity.toUpperCase()+" "+itemType.toUpperCase());
        meta.setLore(Lore);

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        this.setItemMeta(meta);

        if (itemID.equals("LAPIS_HELMET")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/824c6ff1714eb2c3b844d46d2e5ea2f26d273a33eaaa744abf645b060b47d7", this));
        } else if (itemID.equals("SUPERIOR_DRAGON_HELMET")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/7558efbe66976099cfd62760d9e05170d2bb8f51e68829ab8a051c48cbc415cb", this));
        } else if (itemID.equals("EMBER_MASK")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/38d199e23c17488a01a07005a41fe330b1d22b04e99943c9f8c897d156e9555a", this));
        } else if (itemID.equals("FRENCH_BREAD")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/51997da64043b284822115643a654fdc4e8a7226664b48a4e1dbb557b5c0fe14", this));
        } else if (itemID.equals("LE_BAGUETTE")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/cdbea38271075736e4d6a98ba4c784bbe7c2d1d697fcb2cff8a74cdd9ec8d", this));
        } else if (itemID.equals("FRENCH_SANDWICH")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/3b4597519df1d1b91fb5bdf32443b5cf55d0d6019603e9cfcb0493278b7b1fa4", this));
        } else if (itemID.equals("DEVOUR_RING")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/8a7d1a52e8c8ddaaf0c428e1fe4901ef33eff251f289de2716395cad20bf309b", this));
        } else if (itemID.equals("SOUL_SEEKER_RING")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/8beee8b5e18397f7814770c09e91d481c3f8c610f5f005e324be35c23bd86bd1", this));
        } else if (itemID.equals("SOUL_EATER_RING")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/19cc72daaf8cc7c739b6e016f9ac77b39259a86c3bdc9f3b0c90d9e78eb32519", this));
        } else if (itemID.equals("ZOMBIE_HEART")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/71d7c816fc8c636d7f50a93a0ba7aaeff06c96a561645e9eb1bef391655c531", this));
        } else if (itemID.equals("CRYSTALLIZED_HEART")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/87dfb7c1ee4de31f54931eac5c657c145e4fa7fa09e3f52b1788a682b65ac75", this));
        } else if (itemID.equals("ZOMBIE_TALISMAN")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/56fc854bb84cf4b7697297973e02b79bc10698460b51a639c60e5e417734e11", this));
        } else if (itemID.equals("ZOMBIE_RING")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/55d996cb5a8e5a71a274275f46944b944eeeacd2e1cadef918b05b879a03336f", this));
        } else if (itemID.equals("ZOMBIE_ARTIFACT")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/c3fb4e5db97f479c66a42bbd8a7d781daf201a8ddaf77afcf4aef87779aa8b4", this));
        } else if (itemID.equals("REAPER_MASK")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/1fc0184473fe882d2895ce7cbc8197bd40ff70bf10d3745de97b6c2a9c5fc78f", this));
        } else if (itemID.equals("REVIVED_HEART")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/4a61b4f8b070e4bc30a86b2290db6e57e2681c44e0250d1906a89adb8fc455b1", this));
        } else if (itemID.equals("FIRST_STRIKE_BEAD")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/84e24bd00ef3c10b545ed7bfe79bc9e3940b49df2a990607211ea42031219c0c", this));
        } else if (itemID.equals("GIANT_KILLER_NECKLACE")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/d28bfe4a03ea7f3d4f93a00f8d87dfe2b1cd29c40f2f24fe45905f2df705ad31", this));
        } else if (itemID.equals("THUNDERLORD_ARTIFACT")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/4267f7118d2e295461cc9904b2987bf9c6153b98ef185b331185b2497554d3ce", this));
        } else if (itemID.equals("BLAZE_HELMET")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/b78ef2e4cf2c41a2d14bfde9caff10219f5b1bf5b35a49eb51c6467882cb5f0", this));
        } else if (itemID.equals("FROZEN_BLAZE_HELMET")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/55a13bb48e3595b55de8dd6943fc38db5235371278c695bd453e49a0999", this));
        } else if (itemID.equals("SLIME_HAT")) {
            this.setItemMeta(Skull.getMeta("http://textures.minecraft.net/texture/a20e84d32d1e9c919d3fdbb53f2b37ba274c121c57b2810e5a472f40dacf004f", this));
        }


//
    }

    public CustomItem(Material material, int amount, String name, String rarity, float baseDamage, int defense, float health, String itemID, String itemType, int value) {
        this(material, amount, name, rarity, baseDamage, 0, defense, health, itemID, itemType, value);
    }

    public CustomItem(Material material, int amount, String name, String rarity, float baseDamage, int speed, int defense, float health, String itemID, String itemType, int value) {
        this(material, amount, name, rarity, baseDamage, 0, defense, health, 0, 0, itemID, itemType, value);
    }
    public CustomItem(Material material, int amount, String name, String rarity, float baseDamage, String itemID, String itemType, int value) {
        this(material, amount, name, rarity, baseDamage, 0, 0, itemID, itemType, value);

    }

    // TODO: Make item ids not hard-coded
//    public static final List<Class<?>> getClassesInPackage(String packageName) {
//        String path = packageName.replaceAll("\\.", File.separator);
//        List<Class<?>> classes = new ArrayList<>();
//        String[] classPathEntries = System.getProperty("java.class.path").split(
//                System.getProperty("path.separator")
//        );
//
//        String name;
//        for (String classpathEntry : classPathEntries) {
//            if (classpathEntry.endsWith(".jar")) {
//                File jar = new File(classpathEntry);
//                try {
//                    JarInputStream is = new JarInputStream(new FileInputStream(jar));
//                    JarEntry entry;
//                    while((entry = is.getNextJarEntry()) != null) {
//                        name = entry.getName();
//                        if (name.endsWith(".class")) {
//                            if (name.contains(path) && name.endsWith(".class")) {
//                                String classPath = name.substring(0, entry.getName().length() - 6);
//                                classPath = classPath.replaceAll("[\\|/]", ".");
//                                classes.add(Class.forName(classPath));
//                            }
//                        }
//                    }
//                } catch (Exception ex) {
//                    // Silence is gold
//                }
//            } else {
//                try {
//                    File base = new File(classpathEntry + File.separatorChar + path);
//                    for (File file : base.listFiles()) {
//                        name = file.getName();
//                        if (name.endsWith(".class")) {
//                            name = name.substring(0, name.length() - 6);
//                            classes.add(Class.forName(packageName + "." + name));
//                        }
//                    }
//                } catch (Exception ex) {
//                    // Silence is gold
//                }
//            }
//        }
//
//        return classes;
//    }


//     In method
//    String idLowerCase = itemID.toLowerCase();
//    String[] idToWords = idLowerCase.split("_");
//    String idToClass = "com.Rexe0.";
//    for (String str : idToWords) {
//        String chara = str.substring(0, 1);
//        chara = chara.toUpperCase();
//        idToClass = idToClass+(chara+""+str.substring(1));
//    }
//
//    try {
//        Class<? extends CustomItem> itemClass = Class.forName(idToClass).asSubclass(CustomItem.class);
//        return itemClass;
//    } catch (ClassNotFoundException e) {
//        return null;
//    }

    public static CustomItem getItemClass(String itemID) {




        switch (itemID.toUpperCase()) {

            case "ROGUE_SWORD":
                return new RogueSword();
            case "UNDEAD_SWORD":
                return new UndeadSword();
            case "REVENANT_FALCHION":
                return new RevenantFalchion();
            case "REAPER_FALCHION":
                return new ReaperFalchion();
            case "END_SWORD":
                return new EndSword();
            case "CLEAVER":
                return new Cleaver();
            case "FLAMING_SWORD":
                return new FlamingSword();
            case "ASPECT_OF_THE_JERRY":
                return new AspectOfTheJerry();
            case "WOODEN_SWORD":
                return new WoodenSword();
            case "GOLDEN_SWORD":
                return new GoldenSword();
            case "STONE_SWORD":
                return new StoneSword();
            case "IRON_SWORD":
                return new IronSword();
            case "DIAMOND_SWORD":
                return new DiamondSword();
            case "DIAMOND_HELMET":
                return new DiamondHelmet();
            case "DIAMOND_CHESTPLATE":
                return new DiamondChestplate();
            case "DIAMOND_LEGGINGS":
                return new DiamondLeggings();
            case "DIAMOND_BOOTS":
                return new DiamondBoots();
            case "SILVER_FANG":
                return new SilverFang();
            case "LAPIS_BOOTS":
                return new LapisBoots();
            case "LAPIS_LEGGINGS":
                return new LapisLeggings();
            case "LAPIS_CHESTPLATE":
                return new LapisChestplate();
            case "LAPIS_HELMET":
                return new LapisHelmet();
            case "DEAD_BUSH_OF_LOVE":
                return new DeadBushOfLove();
            case "HARDENED_DIAMOND_HELMET":
                return new HardenedDiamondHelmet();
            case "HARDENED_DIAMOND_CHESTPLATE":
                return new HardenedDiamondChestplate();
            case "HARDENED_DIAMOND_LEGGINGS":
                return new HardenedDiamondLeggings();
            case "HARDENED_DIAMOND_BOOTS":
                return new HardenedDiamondBoots();
            case "SPIDER_BOOTS":
                return new SpiderBoots();
            case "SUPERIOR_DRAGON_BOOTS":
                return new SuperiorDragonBoots();
            case "SUPERIOR_DRAGON_HELMET":
                return new SuperiorDragonHelmet();
            case "SUPERIOR_DRAGON_CHESTPLATE":
                return new SuperiorDragonChestplate();
            case "SUPERIOR_DRAGON_LEGGINGS":
                return new SuperiorDragonLeggings();
            case "ASPECT_OF_THE_DRAGONS":
                return new AspectOfTheDragons();
            case "REAPER_SCYTHE":
                return new ReaperScythe();
            case "ELEGANT_TUXEDO_CHESTPLATE":
                return new ElegantTuxedoChestplate();
            case "ELEGANT_TUXEDO_LEGGINGS":
                return new ElegantTuxedoPants();
            case "ELEGANT_TUXEDO_BOOTS":
                return new ElegantTuxedoBoots();
            case "ASPECT_OF_THE_END":
                return new AspectOfTheEnd();
            case "EMBER_ROD":
                return new EmberRod();
            case "EMBER_MASK":
                return new EmberMask();
            case "EMBER_BOOTS":
                return new EmberBoots();
            case "EMBER_LEGGINGS":
                return new EmberLeggings();
            case "EMBER_CHESTPLATE":
                return new EmberChestplate();
            case "FRENCH_BREAD":
                return new FrenchBread();
            case "DEVOUR_RING":
                return new DevourRing();
            case "SOUL_SEEKER_RING":
                return new SoulSeekerRing();
            case "SOUL_EATER_RING":
                return new SoulEaterRing();
            case "GRAPPLING_HOOK":
                return new GrapplingHook();
            case "ZOMBIE_HEART":
                return new ZombiesHeart();
            case "CRYSTALLIZED_HEART":
                return new CrystalizedHeart();
            case "REVIVED_HEART":
                return new RevivedHeart();
            case "REAPER_MASK":
                return new ReaperMask();
            case "ZOMBIE_TALISMAN":
                return new ZombieTalisman();
            case "ZOMBIE_RING":
                return new ZombieRing();
            case "ZOMBIE_ARTIFACT":
                return new ZombieArtifact();
            case "FIRST_STRIKE_BEAD":
                return new FirstStrikeBead();
            case "GIANT_KILLER_NECKLACE":
                return new GiantKillerNecklace();
            case "THUNDERLORD_ARTIFACT":
                return new ThunderlordArtifact();
            case "WAND_OF_HEALING":
                return new WandOfHealing();
            case "WAND_OF_MENDING":
                return new WandOfMending();
            case "WAND_OF_RESTORATION":
                return new WandOfRestoration();
            case "WAND_OF_REJUVENATION":
                return new WandOfRejuvenation();
            case "REVENANT_CHESTPLATE":
                return new RevenantChestplate();
            case "REVENANT_LEGGINGS":
                return new RevenantLeggings();
            case "REVENANT_BOOTS":
                return new RevenantBoots();
            case "BOW":
                return new Bow();
            case "DECENT_BOW":
                return new DecentBow();
            case "RUNAANS_BOW":
                return new RunaansBow();
            case "HURRICANE_BOW":
                return new HurricaneBow();
            case "WATER_HYDRA_HEAD":
                return new WaterHydraHead();
            case "AIMING_ARTIFACT":
                return new AimingArtifact();
            case "PERFECT_BOOTS_1":
                return new PerfectBoots1();
            case "PERFECT_BOOTS_2":
                return new PerfectBoots2();
            case "PERFECT_BOOTS_3":
                return new PerfectBoots3();
            case "PERFECT_CHESTPLATE_1":
                return new PerfectChestplate1();
            case "PERFECT_CHESTPLATE_2":
                return new PerfectChestplate2();
            case "PERFECT_CHESTPLATE_3":
                return new PerfectChestplate3();
            case "PERFECT_CHESTPLATE_4":
                return new PerfectChestplate4();
            case "PERFECT_CHESTPLATE_5":
                return new PerfectChestplate5();
            case "PERFECT_CHESTPLATE_6":
                return new PerfectChestplate6();
            case "PERFECT_CHESTPLATE_7":
                return new PerfectChestplate7();
            case "MOSQUITO_BOW":
                return new MosquitoBow();
            case "SCORPION_BOW":
                return new ScorpionBow();
            case "HOT_POTATO_BOOK":
                return new HotPotatoBook();
            case "DIAMOND_PICKAXE":
                return new DiamondPickaxe();
            case "GOLDEN_PICKAXE":
                return new GoldenPickaxe();
            case "IRON_PICKAXE":
                return new IronPickaxe();
            case "STONE_PICKAXE":
                return new StonePickaxe();
            case "WOODEN_PICKAXE":
                return new WoodenPickaxe();
            case "DIAMOND_AXE":
                return new DiamondAxe();
            case "GOLDEN_AXE":
                return new GoldenAxe();
            case "IRON_AXE":
                return new IronAxe();
            case "STONE_AXE":
                return new StoneAxe();
            case "WOODEN_AXE":
                return new WoodenAxe();
            case "JUNGLE_AXE":
                return new JungleAxe();
            case "TREECAPITATOR":
                return new Treecapitator();
            case "NEW_YEAR_CAKE":
                return new NewYearCake();
            case "TELEKINESIS_TALISMAN":
                return new TelekenisisTalisman();
            case "MINER_BOOTS":
                return new MinerBoots();
            case "MINER_LEGGINGS":
                return new MinerLeggings();
            case "MINER_CHESTPLATE":
                return new MinerChestplate();
            case "MINER_HELMET":
                return new MinerHelmet();
            case "LE_BAGUETTE":
                return new LeBaguette();
            case "FRENCH_SANDWICH":
                return new FrenchSandwich();
            case "HUNG_THE_FISH":
                return new HungTheFish();
            case "FARM_SUIT_CHESTPLATE":
                return new FarmSuitChestplate();
            case "FARM_SUIT_HELMET":
                return new FarmSuitHelmet();
            case "FARM_SUIT_BOOTS":
                return new FarmSuitBoots();
            case "FARM_SUIT_LEGGINGS":
                return new FarmSuitLeggings();
            case "AGRICULTURE_CHESTPLATE":
                return new AgricultureChestplate();
            case "AGRICULTURE_HELMET":
                return new AgricultureHelmet();
            case "AGRICULTURE_BOOTS":
                return new AgricultureBoots();
            case "AGRICULTURE_LEGGINGS":
                return new AgricultureLeggings();
            case "BLAZE_CHESTPLATE":
                return new BlazeChestplate();
            case "BLAZE_HELMET":
                return new BlazeHelmet();
            case "BLAZE_BOOTS":
                return new BlazeBoots();
            case "BLAZE_LEGGINGS":
                return new BlazeLeggings();
            case "FROZEN_BLAZE_CHESTPLATE":
                return new FrozenBlazeChestplate();
            case "FROZEN_BLAZE_HELMET":
                return new FrozenBlazeHelmet();
            case "FROZEN_BLAZE_BOOTS":
                return new FrozenBlazeBoots();
            case "FROZEN_BLAZE_LEGGINGS":
                return new FrozenBlazeLeggings();
            case "SPEEDSTER_CHESTPLATE":
                return new SpeedsterChestplate();
            case "SPEEDSTER_HELMET":
                return new SpeedsterHelmet();
            case "SPEEDSTER_BOOTS":
                return new SpeedsterBoots();
            case "SPEEDSTER_LEGGINGS":
                return new SpeedsterLeggings();
            case "MOLTEN_CORE":
                return new MoltenCore();
            case "FROZEN_SCYTHE":
                return new FrozenScythe();
            case "NETHERITE_CHESTPLATE":
                return new NetheriteChestplate();
            case "NETHERITE_HELMET":
                return new NetheriteHelmet();
            case "NETHERITE_BOOTS":
                return new NetheriteBoots();
            case "NETHERITE_LEGGINGS":
                return new NetheriteLeggings();
            case "MAGMA_CHESTPLATE":
                return new MagmaChestplate();
            case "MAGMA_HELMET":
                return new MagmaHelmet();
            case "MAGMA_BOOTS":
                return new MagmaBoots();
            case "MAGMA_LEGGINGS":
                return new MagmaLeggings();
            default:
                return null;
        }
    }

    public static void loadRecipes() {
        CustomRecipe cleaver = new CustomRecipe(new Cleaver(), 1, false);
        cleaver.addPatterns("%%%", " &%", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("GOLD_INGOT"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondSword(), 1, false);
        cleaver.addPatterns(" % ", " % ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new GoldenSword(), 1, false);
        cleaver.addPatterns(" % ", " % ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("GOLD_INGOT"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new IronSword(), 1, false);
        cleaver.addPatterns(" % ", " % ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("IRON_INGOT"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new StoneSword(), 1, false);
        cleaver.addPatterns(" % ", " % ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("COBBLESTONE"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new WoodenSword(), 1, false);
        cleaver.addPatterns(" % ", " % ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("OAK_PLANKS"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondPickaxe(), 1, false);
        cleaver.addPatterns("%%%", " & ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new GoldenPickaxe(), 1, false);
        cleaver.addPatterns("%%%", " & ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("GOLD_INGOT"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);


        cleaver = new CustomRecipe(new IronPickaxe(), 1, false);
        cleaver.addPatterns("%%%", " & ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("IRON_INGOT"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new StonePickaxe(), 1, false);
        cleaver.addPatterns("%%%", " & ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("COBBLESTONE"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new WoodenPickaxe(), 1, false);
        cleaver.addPatterns("%%%", " & ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("OAK_PLANKS"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new WoodenAxe(), 1, false);
        cleaver.addPatterns("%% ", "%& ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("OAK_PLANKS"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new StoneAxe(), 1, false);
        cleaver.addPatterns("%% ", "%& ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("COBBLESTONE"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new IronAxe(), 1, false);
        cleaver.addPatterns("%% ", "%& ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("IRON_INGOT"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new GoldenAxe(), 1, false);
        cleaver.addPatterns("%% ", "%& ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("GOLD_INGOT"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondAxe(), 1, false);
        cleaver.addPatterns("%% ", "%& ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);



        cleaver = new CustomRecipe(new Diamond(), 9, false);
        cleaver.addPatterns("   ", " & ", "   ");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("DIAMOND_BLOCK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondBlock(), 1, false);
        cleaver.addPatterns("&&&", "&&&", "&&&");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);




        cleaver = new CustomRecipe(new Stick(), 4, false);
        cleaver.addPatterns("   ", " % ", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("OAK_PLANKS"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new Stick(), 4, false);
        cleaver.addPatterns(" % ", " % ", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("OAK_PLANKS"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new OakPlanks(), 4, false);
        cleaver.addPatterns("   ", " % ", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("OAK_LOG"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondHelmet(), 1, false);
        cleaver.addPatterns("%%%", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondHelmet(), 1, false);
        cleaver.addPatterns("   ", "%%%", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondChestplate(), 1, false);
        cleaver.addPatterns("% %", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondLeggings(), 1, false);
        cleaver.addPatterns("%%%", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondBoots(), 1, false);
        cleaver.addPatterns("% %", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DiamondBoots(), 1, false);
        cleaver.addPatterns("   ", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedGhastTear(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("GHAST_TEAR"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new SilverFang(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_GHAST_TEAR"), 5);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedDiamond(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("DIAMOND"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedMushroom(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("MUSHROOM"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedDiamondBlock(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedEmerald(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("EMERALD"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedEmeraldBlock(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_EMERALD"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedString(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("STRING"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedBone(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("BONE"), 4);
        CustomRecipe.customRecipes.add(cleaver);


        cleaver = new CustomRecipe(new EnchantedIron(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("IRON_INGOT"), 4);
        CustomRecipe.customRecipes.add(cleaver);


        cleaver = new CustomRecipe(new SpiderBoots(), 1, false);
        cleaver.addPatterns("   ", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_STRING"), 64);
        CustomRecipe.customRecipes.add(cleaver);




        cleaver = new CustomRecipe(new HardenedDiamondHelmet(), 1, false);
        cleaver.addPatterns("%%%", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new HardenedDiamondHelmet(), 1, false);
        cleaver.addPatterns("   ", "%%%", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new HardenedDiamondChestplate(), 1, false);
        cleaver.addPatterns("% %", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new HardenedDiamondLeggings(), 1, false);
        cleaver.addPatterns("%%%", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new HardenedDiamondBoots(), 1, false);
        cleaver.addPatterns("% %", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new HardenedDiamondBoots(), 1, false);
        cleaver.addPatterns("   ", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new RevenantViscera(), 1, true);
        cleaver.addPatterns(" % ", "%&%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("REVENANT_FLESH"), 32);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("ENCHANTED_STRING"), 16);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new ReaperScythe(), 1, true);
        cleaver.addPatterns("%&$", "  $", "  $");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("SCYTHE_BLADE"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("REVENANT_VISCERA"), 64);
        cleaver.setIngredient('$', CustomItem.getItemClass("REAPER_FALCHION"), 1);
        CustomRecipe.customRecipes.add(cleaver);


        cleaver = new CustomRecipe(new EmberMask(), 1, false);
        cleaver.addPatterns("%%%", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("EMBER_FRAGMENT"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EmberMask(), 1, false);
        cleaver.addPatterns("   ", "%%%", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("EMBER_FRAGMENT"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EmberChestplate(), 1, false);
        cleaver.addPatterns("% %", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("EMBER_FRAGMENT"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EmberLeggings(), 1, false);
        cleaver.addPatterns("%%%", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("EMBER_FRAGMENT"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EmberBoots(), 1, false);
        cleaver.addPatterns("% %", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("EMBER_FRAGMENT"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EmberBoots(), 1, false);
        cleaver.addPatterns("   ", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("EMBER_FRAGMENT"), 1);
        CustomRecipe.customRecipes.add(cleaver);





        cleaver = new CustomRecipe(new DevourRing(), 1, true);
        cleaver.addPatterns(" % ", "&$&", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("RAW_CHICKEN"), 2);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("RAW_SALMON"), 4);
        cleaver.setIngredient('$', CustomMaterial.getItemClass("REVENANT_VISCERA"), 39);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new SoulSeekerRing(), 1, true);
        cleaver.addPatterns("   ", "$%$", "   ");
        cleaver.setIngredient('%', CustomItem.getItemClass("DEVOUR_RING"), 1);
        cleaver.setIngredient('$', CustomMaterial.getItemClass("REVENANT_VISCERA"), 50);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new SoulEaterRing(), 1, true);
        cleaver.addPatterns("%%%", "%$%", " & ");
        cleaver.setIngredient('%', CustomItem.getItemClass("DEVOUR_RING"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("SOUL_SEEKER_RING"), 1);
        cleaver.setIngredient('$', CustomMaterial.getItemClass("ROTTED_TOOTH"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedRottenFlesh(), 1, true);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ROTTEN_FLESH"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new ZombiesHeart(), 1, true);
        cleaver.addPatterns("%%%", "% %", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_ROTTEN_FLESH"), 8);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new CrystalizedHeart(), 1, true);
        cleaver.addPatterns("%%%", "%&%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 8);
        cleaver.setIngredient('&', CustomItem.getItemClass("ZOMBIE_HEART"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new RevivedHeart(), 1, true);
        cleaver.addPatterns("%%%", "%&%", "%%%");
        cleaver.setIngredient('%', CustomItem.getItemClass("ZOMBIE_HEART"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("CRYSTALLIZED_HEART"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new ReaperMask(), 1, true);
        cleaver.addPatterns("% %", "&$#", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("REVENANT_VISCERA"), 32);
        cleaver.setIngredient('$', CustomMaterial.getItemClass("ENCHANTED_STRING"), 32);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("BEHEADED_HORROR"), 1);
        cleaver.setIngredient('#', CustomItem.getItemClass("REVIVED_HEART"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new ZombieTalisman(), 1, false);
        cleaver.addPatterns("%%%", "%&%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ROTTEN_FLESH"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STRING"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new ZombieRing(), 1, true);
        cleaver.addPatterns("   ", "%&%", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("REVENANT_FLESH"), 32);
        cleaver.setIngredient('&', CustomItem.getItemClass("ZOMBIE_TALISMAN"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new ZombieArtifact(), 1, true);
        cleaver.addPatterns("%$%", "^&^", "%$%");
        cleaver.setIngredient('$', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 8);
        cleaver.setIngredient('^', CustomMaterial.getItemClass("REVENANT_VISCERA"), 24);
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_IRON_INGOT"), 8);
        cleaver.setIngredient('&', CustomItem.getItemClass("ZOMBIE_RING"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new RevenantFalchion(), 1, true);
        cleaver.addPatterns(" # ", " & ", " % ");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("UNDEAD_CATALYST"), 1);
        cleaver.setIngredient('%', CustomItem.getItemClass("UNDEAD_SWORD"), 1);
        cleaver.setIngredient('#', CustomItem.getItemClass("CRYSTALLIZED_HEART"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new ReaperFalchion(), 1, true);
        cleaver.addPatterns(" # ", " & ", " % ");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("REVENANT_CATALYST"), 1);
        cleaver.setIngredient('%', CustomItem.getItemClass("REVENANT_FALCHION"), 1);
        cleaver.setIngredient('#', CustomItem.getItemClass("REVIVED_HEART"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FirstStrikeBead(), 1, false);
        cleaver.addPatterns(" # ", "#&#", "###");
        cleaver.setIngredient('&', CustomItem.getItemClass("IRON_SWORD"), 1);
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new GiantKillerNecklace(), 1, false);
        cleaver.addPatterns(" # ", "#&#", "###");
        cleaver.setIngredient('&', CustomItem.getItemClass("SILVER_FANG"), 1);
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new ThunderlordArtifact(), 1, false);
        cleaver.addPatterns(" # ", "#&#", "###");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("GUNPOWDER"), 1);
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_GHAST_TEAR"), 5);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new WandOfHealing(), 1, true);
        cleaver.addPatterns("  #", " % ", "#  ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("STICK"), 1);
        cleaver.setIngredient('#', CustomMaterial.getItemClass("REVENANT_FLESH"), 8);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new WandOfMending(), 1, true);
        cleaver.addPatterns("  #", " % ", "&  ");
        cleaver.setIngredient('%', CustomItem.getItemClass("WAND_OF_HEALING"), 1);
        cleaver.setIngredient('#', CustomMaterial.getItemClass("REVENANT_VISCERA"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("REVENANT_FLESH"), 32);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new WandOfRestoration(), 1, true);
        cleaver.addPatterns("  #", " % ", "&  ");
        cleaver.setIngredient('%', CustomItem.getItemClass("WAND_OF_MENDING"), 1);
        cleaver.setIngredient('#', CustomMaterial.getItemClass("REVENANT_VISCERA"), 16);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("REVENANT_FLESH"), 64);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new RevenantChestplate(), 1, true);
        cleaver.addPatterns("% %", "%%%", "&&&");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("REVENANT_VISCERA"), 10);
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 16);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new RevenantLeggings(), 1, true);
        cleaver.addPatterns("&&&", "% %", "% %");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("REVENANT_VISCERA"), 10);
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 16);
        CustomRecipe.customRecipes.add(cleaver);


        cleaver = new CustomRecipe(new RevenantBoots(), 1, true);
        cleaver.addPatterns("& &", "% %", "   ");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("REVENANT_VISCERA"), 10);
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 16);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new Bow(), 1, false);
        cleaver.addPatterns(" &%", "& %", " &%");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        cleaver.setIngredient('%', CustomMaterial.getItemClass("STRING"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new DecentBow(), 1, false);
        cleaver.addPatterns(" &%", "& %", " &%");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 32);
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_STRING"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new AimingArtifact(), 1, false);
        cleaver.addPatterns(" # ", "#&#", "###");
        cleaver.setIngredient('&', CustomItem.getItemClass("DECENT_BOW"), 1);
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 20);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new HurricaneBow(), 1, false);
        cleaver.addPatterns(" &%", "& %", " &%");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("ENCHANTED_BONE"), 32);
        cleaver.setIngredient('%', CustomMaterial.getItemClass("STRING"), 20);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new RunaansBow(), 1, false);
        cleaver.addPatterns(" &%", "& %", " &%");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("ENCHANTED_BONE"), 64);
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_STRING"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new GrapplingHook(), 1, false);
        cleaver.addPatterns("&& ", "&% ", "  %");
        cleaver.setIngredient('&', CustomMaterial.getItemClass("ENCHANTED_STRING"), 2);
        cleaver.setIngredient('%', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedPotato(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("POTATO"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedBakedPotato(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_POTATO"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new HotPotatoBook(), 1, false);
        cleaver.addPatterns("   ", " %%", " %&");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("PAPER"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("ENCHANTED_BAKED_POTATO"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedMagmaCream(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("MAGMA_CREAM"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new HayBale(), 1, false);
        cleaver.addPatterns("%%%", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("WHEAT"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedHayBale(), 1, false);
        cleaver.addPatterns("%%%", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("HAY_BALE"), 8);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FrenchBread(), 1, false);
        cleaver.addPatterns("  %", " % ", "%  ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_HAY_BALE"), 64);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new LeBaguette(), 1, false);
        cleaver.addPatterns("%%%", "%&%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_HAY_BALE"), 64);
        cleaver.setIngredient('&', CustomItem.getItemClass("FRENCH_BREAD"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FrenchSandwich(), 1, false);
        cleaver.addPatterns("%%%", "%&%", "%%%");
        cleaver.setIngredient('%', CustomItem.getItemClass("FRENCH_BREAD"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("LE_BAGUETTE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedSugar(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("SUGAR_CANE"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedSugarCane(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_SUGAR"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new Paper(), 1, false);
        cleaver.addPatterns("   ", "%%%", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("SUGAR_CANE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedCoal(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("COAL"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedBlazePowder(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("BLAZE_ROD"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedBlazeRod(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_BLAZE_POWDER"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedIce(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ICE"), 4);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new EnchantedPackedIce(), 1, false);
        cleaver.addPatterns(" % ", "%%%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_ICE"), 4);
        CustomRecipe.customRecipes.add(cleaver);



        cleaver = new CustomRecipe(new TelekenisisTalisman(), 1, false);
        cleaver.addPatterns(" % ", "%&%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("HAY_BALE"), 3);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("ENCHANTED_DIAMOND"), 1);
        CustomRecipe.customRecipes.add(cleaver);




        cleaver = new CustomRecipe(new FarmSuitBoots(), 1, false);
        cleaver.addPatterns("% %", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("HAY_BALE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FarmSuitHelmet(), 1, false);
        cleaver.addPatterns("%%%", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("HAY_BALE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FarmSuitChestplate(), 1, false);
        cleaver.addPatterns("% %", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("HAY_BALE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FarmSuitLeggings(), 1, false);
        cleaver.addPatterns("%%%", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("HAY_BALE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new SpeedsterHelmet(), 1, false);
        cleaver.addPatterns("%%%", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_SUGAR_CANE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new SpeedsterChestplate(), 1, false);
        cleaver.addPatterns("% %", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_SUGAR_CANE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new SpeedsterLeggings(), 1, false);
        cleaver.addPatterns("%%%", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_SUGAR_CANE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new SpeedsterBoots(), 1, false);
        cleaver.addPatterns("% %", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_SUGAR_CANE"), 1);
        CustomRecipe.customRecipes.add(cleaver);



        cleaver = new CustomRecipe(new SlimeHat(), 1, false);
        cleaver.addPatterns("%%%", "% %", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("SLIME_BALL"), 1);
        CustomRecipe.customRecipes.add(cleaver);



        cleaver = new CustomRecipe(new BlazeHelmet(), 1, false);
        cleaver.addPatterns("%%%", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_BLAZE_ROD"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new BlazeChestplate(), 1, false);
        cleaver.addPatterns("% %", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_BLAZE_ROD"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new BlazeLeggings(), 1, false);
        cleaver.addPatterns("%%%", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_BLAZE_ROD"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new BlazeBoots(), 1, false);
        cleaver.addPatterns("% %", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_BLAZE_ROD"), 1);
        CustomRecipe.customRecipes.add(cleaver);









        cleaver = new CustomRecipe(new FrozenBlazeHelmet(), 1, false);
        cleaver.addPatterns("%%%", "%&%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_PACKED_ICE"), 8);
        cleaver.setIngredient('&', CustomItem.getItemClass("BLAZE_HELMET"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FrozenBlazeChestplate(), 1, false);
        cleaver.addPatterns("%%%", "%&%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_PACKED_ICE"), 8);
        cleaver.setIngredient('&', CustomItem.getItemClass("BLAZE_CHESTPLATE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FrozenBlazeLeggings(), 1, false);
        cleaver.addPatterns("%%%", "%&%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_PACKED_ICE"), 8);
        cleaver.setIngredient('&', CustomItem.getItemClass("BLAZE_LEGGINGS"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FrozenBlazeBoots(), 1, false);
        cleaver.addPatterns("%%%", "%&%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_PACKED_ICE"), 8);
        cleaver.setIngredient('&', CustomItem.getItemClass("BLAZE_BOOTS"), 1);
        CustomRecipe.customRecipes.add(cleaver);



        cleaver = new CustomRecipe(new AgricultureHelmet(), 1, false);
        cleaver.addPatterns("%%%", "%&%", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_HAY_BALE"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("FARM_SUIT_HELMET"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new AgricultureChestplate(), 1, false);
        cleaver.addPatterns("%&%", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_HAY_BALE"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("FARM_SUIT_CHESTPLATE"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new AgricultureLeggings(), 1, false);
        cleaver.addPatterns("%%%", "%&%", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_HAY_BALE"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("FARM_SUIT_LEGGINGS"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new AgricultureBoots(), 1, false);
        cleaver.addPatterns("%&%", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_HAY_BALE"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("FARM_SUIT_BOOTS"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new MoltenCore(), 1, false);
        cleaver.addPatterns("%$%", "%&%", " % ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("MAGMA_CREAM"), 8);
        cleaver.setIngredient('$', CustomMaterial.getItemClass("ENCHANTED_BLAZE_POWDER"), 1);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("ENCHANTED_MAGMA_CREAM"), 2);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new FrozenScythe(), 1, false);
        cleaver.addPatterns("%% ", " & ", " & ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_PACKED_ICE"), 32);
        cleaver.setIngredient('&', CustomMaterial.getItemClass("STICK"), 1);
        CustomRecipe.customRecipes.add(cleaver);


        cleaver = new CustomRecipe(new NetheriteHelmet(), 1, false);
        cleaver.addPatterns("%%%", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("NETHERITE_SCRAP"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new NetheriteChestplate(), 1, false);
        cleaver.addPatterns("% %", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("NETHERITE_SCRAP"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new NetheriteLeggings(), 1, false);
        cleaver.addPatterns("%%%", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("NETHERITE_SCRAP"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new NetheriteBoots(), 1, false);
        cleaver.addPatterns("% %", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("NETHERITE_SCRAP"), 1);
        CustomRecipe.customRecipes.add(cleaver);




        cleaver = new CustomRecipe(new MagmaHelmet(), 1, false);
        cleaver.addPatterns("%%%", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_MAGMA_CREAM"), 12);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new MagmaChestplate(), 1, false);
        cleaver.addPatterns("% %", "%%%", "%%%");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_MAGMA_CREAM"), 12);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new MagmaLeggings(), 1, false);
        cleaver.addPatterns("%%%", "% %", "% %");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_MAGMA_CREAM"), 12);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new MagmaBoots(), 1, false);
        cleaver.addPatterns("% %", "% %", "   ");
        cleaver.setIngredient('%', CustomMaterial.getItemClass("ENCHANTED_MAGMA_CREAM"), 12);
        CustomRecipe.customRecipes.add(cleaver);










        // Perfect
        cleaver = new CustomRecipe(new PerfectBoots1(), 1, true);
        cleaver.addPatterns("# #", "# #", "   ");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new PerfectBoots2(), 1, true);
        cleaver.addPatterns(" # ", "#&#", " # ");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("PERFECT_BOOTS_1"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new PerfectBoots3(), 1, true);
        cleaver.addPatterns(" # ", "#&#", " # ");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("PERFECT_BOOTS_2"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new PerfectChestplate1(), 1, true);
        cleaver.addPatterns("# #", "###", "###");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new PerfectChestplate2(), 1, true);
        cleaver.addPatterns(" # ", "#&#", " # ");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("PERFECT_CHESTPLATE_1"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new PerfectChestplate3(), 1, true);
        cleaver.addPatterns(" # ", "#&#", " # ");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("PERFECT_CHESTPLATE_2"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new PerfectChestplate4(), 1, true);
        cleaver.addPatterns(" # ", "#&#", " # ");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("PERFECT_CHESTPLATE_3"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new PerfectChestplate5(), 1, true);
        cleaver.addPatterns(" # ", "#&#", " # ");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("PERFECT_CHESTPLATE_4"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new PerfectChestplate6(), 1, true);
        cleaver.addPatterns(" # ", "#&#", " # ");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("PERFECT_CHESTPLATE_5"), 1);
        CustomRecipe.customRecipes.add(cleaver);

        cleaver = new CustomRecipe(new PerfectChestplate7(), 1, true);
        cleaver.addPatterns(" # ", "#&#", " # ");
        cleaver.setIngredient('#', CustomMaterial.getItemClass("ENCHANTED_DIAMOND_BLOCK"), 1);
        cleaver.setIngredient('&', CustomItem.getItemClass("PERFECT_CHESTPLATE_6"), 1);
        CustomRecipe.customRecipes.add(cleaver);
    }

    public String getItemID() {
        return this.itemID;
    }

    public int getValue() {
        return this.value;
    }
}
