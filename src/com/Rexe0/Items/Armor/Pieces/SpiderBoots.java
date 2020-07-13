package com.Rexe0.Items.Armor.Pieces;

import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;


public class SpiderBoots extends CustomItem {
    public static HashMap<Player, Boolean> spiderBootsCD = new HashMap<>();

    public SpiderBoots() {
        super(Material.IRON_BOOTS, 1, "Spider Boots", "RARE", 0, 9, 0, "SPIDER_BOOTS", "BOOTS", 35000);

    }


}
