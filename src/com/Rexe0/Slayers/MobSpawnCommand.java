package com.Rexe0.Slayers;

import com.Rexe0.ColeCrafterSlayers;
import net.minecraft.server.v1_16_R1.ChatMessageType;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MobSpawnCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.getName().equals("rexe0")) {
                if (args.length >= 1) {

                    Player player = (Player) sender;

                    FileConfiguration Config = ColeCrafterSlayers.getInstance().getMobSpawnConfig();


                    int number = 0;
                    for (int i = 0; i < 50; i++) {
                        if (Config.get(args[0] + "." + i) == null) {
                            number = i;
                            break;
                        }
                    }

                    double X = player.getLocation().getX();
                    double Y = player.getLocation().getY();
                    double Z = player.getLocation().getZ();

                    String coords = "["+X+", "+Y+", "+Z+"]";


                    Config.set(args[0] + "." + number, coords);

                    try {
                        Config.save(ColeCrafterSlayers.getInstance().getMobSpawnFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    sender.sendMessage(ChatColor.RED + "Specify the arguments. /mobspawn <mobID>");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You are not rexe0.");
            }
        }
        return true;
    }
}
