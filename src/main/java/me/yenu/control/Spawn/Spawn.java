package me.yenu.control.Spawn;

import me.yenu.control.Item.ItemManger;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Ravager;

public class Spawn extends BukkitCommand{

    public Spawn(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            World world = player.getWorld();
            Location du = player.getLocation();
            player.getInventory().addItem(ItemManger.stick);
            world.spawn(du, Ravager.class, entity -> {
                entity.setAI(false);
            });
            // 좀비가 플레이어 시야좌표에 소환 바닐라좀비

        }
        return false;
    }


}


