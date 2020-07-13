package com.Rexe0.NPCs;

import com.Rexe0.Slayers.SlayerMenu;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomNPC implements Listener {
    public static NPC createNPC(EntityType type, String name) {

        return CitizensAPI.getNPCRegistry().createNPC(type, name);
    }

    public static void setSkin(String skinURL, NPC npc) {
        int npcID = npc.getId();
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), ("npc select "+npcID));
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), ("npc skin --url "+skinURL));
    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent e) {
        NPC npc = e.getNPC();
        if (npc.getName().equals("Banker")) {
            SlayerMenu.openBank(e.getClicker());
        }
        if (npc.getName().equals(ChatColor.DARK_PURPLE+"Maddox the Slayer")) {

            e.getClicker().openInventory(SlayerMenu.slayerMenu);
        }
    }

}
