package com.Rexe0.Slayers;

import com.Rexe0.Items.Materials.CustomMaterial;
import com.Rexe0.NPCs.CustomNPC;
import com.Rexe0.Shops.CustomShop;
import com.Rexe0.Skull;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.commands.NPCCommands;
import net.citizensnpcs.trait.SkinTrait;
import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagList;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;


public class MaddoxCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.getName().equals("rexe0")) {
                if (args.length < 1) {
                    sender.sendMessage(ChatColor.RED+"Please specify the name of the npc you would like to spawn.");
                } else {
                    String npcName = args[0].toUpperCase();
                    if (npcName.equals("MADDOX")) {
                        Location loc = ((Player) sender).getLocation();
                        NPC npc = CustomNPC.createNPC(EntityType.PLAYER, ChatColor.DARK_PURPLE+"Maddox the Slayer");
                        npc.setProtected(true);
                        final SkinTrait trait = npc.getTrait(SkinTrait.class);
                        trait.setFetchDefaultSkin(false);
                        trait.setShouldUpdateSkins(false);



//
                        CustomNPC.setSkin("http://textures.minecraft.net/texture/947bbf2afce8da4a24c23860ed27e2d08150fe06c2dfefb5b13ac4b4140c6fc", npc);
                        npc.spawn(loc);
                    } else if (npcName.equals("BANKER")) {
                        Location loc = new Location(Bukkit.getWorld("hub"), 20.5, 71, -40.5, 90, 0);
                        NPC npc = CustomNPC.createNPC(EntityType.PLAYER, "Banker");
                        npc.setProtected(true);
                        final SkinTrait trait = npc.getTrait(SkinTrait.class);
                        trait.setFetchDefaultSkin(false);
                        trait.setShouldUpdateSkins(false);



//
                        CustomNPC.setSkin("http://textures.minecraft.net/texture/3e358fcc84387c6f6a93e91f17c63e1df6ee104a96aaf75c59fe31fadc86b5fd", npc);
                        npc.spawn(loc);

                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED+"You are not Rexe0");
            }
        } else {
            sender.sendMessage(ChatColor.RED+"You are not a player.");
        }
        return true;
    }
}
