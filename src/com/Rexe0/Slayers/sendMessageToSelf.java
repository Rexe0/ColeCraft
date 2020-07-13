package com.Rexe0.Slayers;

import com.Rexe0.DefenseNerf;
import com.Rexe0.Items.Materials.CustomMaterial;
import net.minecraft.server.v1_16_R1.ChatMessageType;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class sendMessageToSelf implements CommandExecutor {
    public static HashMap<Player, Boolean> canBuild = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.getName().equals("rexe0")) {
                canBuild.putIfAbsent((Player) sender, false);

                if (!canBuild.get(sender)) {
                    canBuild.put((Player) sender, true);
                    sender.sendMessage(ChatColor.GREEN+"You can now build.");
                } else {
                    canBuild.put((Player) sender, false);
                    sender.sendMessage(ChatColor.GREEN+"You no longer build.");
                }

                if (args.length >= 1) {
                    if (args[0].toLowerCase().equals("special_zealot")) {


                        String message = "§r§aA special §r§5Zealot §r§ahas spawned nearby!§r";

                        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}"), ChatMessageType.CHAT, UUID.randomUUID());

                        ((CraftPlayer) sender).getHandle().playerConnection.sendPacket(packet);
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You are not rexe0.");
            }
        }
        return true;
    }
}
