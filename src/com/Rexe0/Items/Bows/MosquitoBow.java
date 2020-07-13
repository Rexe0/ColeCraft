package com.Rexe0.Items.Bows;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class MosquitoBow extends CustomItem {
    public MosquitoBow() {
        super(Material.BOW, 1, "Mosquito Bow", "LEGENDARY", 69.42f, "MOSQUITO_BOW", "BOW", 1500000);
    }

    public static void use(Action clickType, Player player) {

    }
}
