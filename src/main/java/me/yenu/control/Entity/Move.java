package me.yenu.control.Entity;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;

public class Move extends BukkitCommand {

    public Move(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            World world = player.getWorld();
            world.spawn(player.getEyeLocation(), ItemDisplay.class, display -> {
                display.setItemStack(new ItemStack(Material.SNOWBALL));

                Transformation transformation = display.getTransformation();
            });
        }

        return false;
    }
}
