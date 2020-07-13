package com.Rexe0.Items.Accessories;


import com.Rexe0.Items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class TelekenisisTalisman extends CustomItem {
    public TelekenisisTalisman() {
        super(Material.SCUTE, 1, "Telekinesis Talisman", "UNCOMMON", 0, "TELEKINESIS_TALISMAN", "Accessory", 200);
    }

    public static void use(Action clickType, Player player) {

    }
}
