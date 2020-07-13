package com.Rexe0;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class Skull {
    public static ItemStack getSkull(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        if(url.isEmpty())return head;


        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static SkullMeta getMeta(String url, ItemStack item) {
        ItemStack head = item;


        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        return headMeta;
    }

    public static SkullMeta getMeta(String url, ItemStack item, UUID uuid) {
        ItemStack head = item;


        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(uuid, null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        return headMeta;
    }
}


// Vector toTarget = this.target.getLocation().clone().add(new Vector(0.0D, 0.5D, 0.0D)).subtract(this.arrow.getLocation()).toVector();
//    Vector dirVelocity = this.arrow.getVelocity().clone().normalize();
//    Vector dirToTarget = toTarget.clone().normalize();
//    double angle = dirVelocity.angle(dirToTarget);
//    double newSpeed = 0.9D * speed + 0.14D;
//    if (this.target instanceof Player && this.arrow
//      .getLocation()
//      .distance(this.target.getLocation()) < 8.0D) {
//      Player player = (Player)this.target;
//      if (player.isBlocking())
//        newSpeed = speed * 0.6D;
//    }
//    if (angle < 0.12D) {
//      newVelocity = dirVelocity.clone().multiply(newSpeed);
//    } else {
//      Vector newDir = dirVelocity.clone().multiply((angle - this.MaxRotationAngle) / angle).add(dirToTarget.clone().multiply(this.MaxRotationAngle / angle));
//      newDir.normalize();
//      newVelocity = newDir.clone().multiply(newSpeed);
//    }
//    this.arrow.setVelocity(newVelocity.add(new Vector(0.0D, 0.03D, 0.0D)));
