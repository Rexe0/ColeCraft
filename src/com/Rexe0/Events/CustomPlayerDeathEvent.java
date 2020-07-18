package com.Rexe0.Events;


import com.Rexe0.ColeCrafterSlayers;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.text.NumberFormat;

public class CustomPlayerDeathEvent extends Event implements Cancellable {
    private Entity entity;
    private DeathReason reason;
    private boolean isCancelled;
    private Entity damager;
    private static final HandlerList handlers = new HandlerList();

    public CustomPlayerDeathEvent(Entity entity, DeathReason reason, Entity damager) {
        this.reason = reason;
        this.entity = entity;
        this.isCancelled = false;
        this.damager = damager;

    }

    public void execute() {

        if (this.entity instanceof Player && !(this.entity.hasMetadata("NPC"))) {

            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = manager.getMainScoreboard();

            long coins = ColeCrafterSlayers.getCoins((Player) this.entity);



            if (ColeCrafterSlayers.currentLocation.get((Player) this.entity).equals(ChatColor.AQUA+"Deep Caverns")) {
                this.entity.teleport(new Location(Bukkit.getWorld("hub"),  -3, 157, -488.5, 180, 0));

            } else if (ColeCrafterSlayers.currentLocation.get((Player) this.entity).equals(ChatColor.GOLD+"Gold Mine")) {
                this.entity.teleport(new Location(Bukkit.getWorld("hub"),  -4.5f, 74f, -272.5f, 180, 0));

            } else if (ColeCrafterSlayers.currentLocation.get((Player) this.entity).equals(ChatColor.AQUA+"The Barn")) {
                this.entity.teleport(new Location(Bukkit.getWorld("hub"),  115.5, 71, -206.5, -135, 0));

            } else if (ColeCrafterSlayers.currentLocation.get((Player) this.entity).equals(ChatColor.AQUA+"Mushroom Desert")) {
                this.entity.teleport(new Location(Bukkit.getWorld("hub"),  153.5, 77, -361.5, -135, 0));

            } else if (ColeCrafterSlayers.currentLocation.get((Player) this.entity).equals(ChatColor.RED+"Blazing Fortress")) {
                this.entity.teleport(new Location(Bukkit.getWorld("hub"),  -310.0, 83, -380.5, 180, 0));
            } else if (ColeCrafterSlayers.currentLocation.get((Player) this.entity).equals(ChatColor.RED+"Spiders Den")) {
                this.entity.teleport(new Location(Bukkit.getWorld("hub"),  -201.5, 84, -232.5, 135, 0));
            } else {
                this.entity.teleport(new Location(Bukkit.getWorld("hub"), -3, 70, -85));
            }
//

            if (this.reason == DeathReason.ENVIRONMENT) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage( ChatColor.RED+"☠ "+ChatColor.GOLD+this.entity.getName()+ChatColor.GRAY+" died");
                }
            } else if (this.reason == DeathReason.PLAYER) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (this.damager instanceof Player) {
                        player.sendMessage(ChatColor.RED + "☠ " + ChatColor.GOLD + this.entity.getName() + ChatColor.GRAY + " was killed by " + this.damager.getName() + ".");
                    } else {
                        player.sendMessage(ChatColor.RED + "☠ " + ChatColor.GOLD + this.entity.getName() + ChatColor.GRAY + " was killed by " + this.damager.getCustomName() + ".");
                    }
                }

            }

            NumberFormat numberFormat = NumberFormat.getInstance();

            numberFormat.setGroupingUsed(true);

            String formattedCoins = numberFormat.format(coins/2);

            this.entity.sendMessage(ChatColor.RED+"You died and lost "+formattedCoins+" coins!");

            ColeCrafterSlayers.setCoins((Player) this.entity, coins/2);

            this.entity.sendMessage(ChatColor.RED+"");
            ((Player) this.entity).setHealth(((Player) this.entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            ((Player) this.entity).playSound(this.entity.getLocation(), Sound.BLOCK_ANVIL_LAND, 2, 2);

        } else {
            if (this.entity.hasMetadata("NPC")) {
                this.entity.remove();
            } else {
                ((LivingEntity) this.entity).damage(100000);
            }
        }




    }


    public Entity getEntity() {
        return this.entity;
    }

    public DeathReason getReason() {
        return this.reason;
    }

    public Entity getDamager() {
        return this.damager;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    public enum DeathReason {
        ENVIRONMENT,
        PLAYER,
        MINION
    }
}
