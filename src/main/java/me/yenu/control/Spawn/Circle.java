package me.yenu.control.Spawn;

import me.yenu.control.Control;
import me.yenu.control.Item.ItemManger;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.print.Paper;


public class Circle implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return false;
    }


//    @Override
//    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
//        if (commandSender instanceof Player player) {
//
//            World world = player.getWorld();
//            Location location = player.getLocation();
//            Vector direction = location.getDirection().normalize();
//
//            Location spawnlocation = location.add(direction.multiply(5));
//
//            ItemDisplay display = world.spawn(spawnlocation, ItemDisplay.class);
//            display.setItemStack(ItemManger.paper);
//
//                float offsetY = 0.f;
//                Bukkit.getScheduler().scheduleSyncRepeatingTask(Control.instance, () -> {
//                    Transformation transformation = display.getTransformation();
//                    transformation.getTranslation().add(5, offsetY, 0);
//                    display.setTransformation(transformation);
//                }, 0, 20);
//
//                display.setCustomNameVisible(true);
//                display.setCustomName("");
//
//                Location location1 = (Location) player.getWorld();
//            };
//        }return false;
//    }
    }
