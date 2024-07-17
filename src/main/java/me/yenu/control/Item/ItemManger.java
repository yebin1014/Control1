package me.yenu.control.Item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Arrays;

public class ItemManger {

    public static ItemStack itemStack(Material type, String s, String... args) {
        ItemStack stack = new ItemStack(type);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(s);
        meta.setLore(Arrays.asList(args));
        stack.setItemMeta(meta);
        return stack;
    }


    public static final ItemStack stick = itemStack(Material.STICK, ChatColor.AQUA + "스태프", ChatColor.WHITE + "쉬프트 우클릭 시 소환");
    public static final ItemStack paper = itemStack(Material.PAPER, ChatColor.WHITE + "", ChatColor.WHITE + "");

}
