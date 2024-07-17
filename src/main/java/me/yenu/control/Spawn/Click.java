package me.yenu.control.Spawn;

import me.yenu.control.Control;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.nio.file.WatchKey;
import java.util.*;

public class Click implements Listener {

    private final Map<UUID, WitherSkeleton> spawnW = new HashMap<>();

    @EventHandler
    public void rclick(PlayerInteractEvent e) { // 쉬+우 시 위더소환 / 위더가 움직이는 명령을 받음
        Player p = e.getPlayer();
        World  world = p.getWorld();

        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
        ItemMeta itemMeta = handItem.getItemMeta();
        if (itemMeta == null || !itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "스태프")) return;


        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.isSneaking()) {
                WitherSkeleton witherSkeleton = world.spawn(p.getLocation(), WitherSkeleton.class, entity -> {
                    entity.setRemoveWhenFarAway(false);
                    entity.setAI(false);
                });

                spawnW.put(witherSkeleton.getUniqueId(), witherSkeleton); // 맵에 위더스켈레톤 객체 추가 / 참조 및 제어
            } else {
                Location location = p.getTargetBlockExact(50).getLocation();
                if(p.getTargetBlockExact(50) != null) {
                    for (WitherSkeleton witherSkeleton : spawnW.values()) {
                        if (witherSkeleton != null) {
                            move(witherSkeleton, location);
                        }
                    }
                }
            }
        }
    }

    private void move(WitherSkeleton witherSkeleton, Location location) {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (witherSkeleton.isDead() || witherSkeleton.getLocation().distance(location) < 1)  {
                    this.cancel();
                    return;
                }
                Vector direction = location.toVector().subtract(witherSkeleton.getLocation().toVector().normalize());
                witherSkeleton.setVelocity(direction);

            }
        }.runTaskTimer(Control.getInstance(), 0l, 1l); // 0틱 딜레이로 1틱마다 실행
    }

    @EventHandler
    public void LeftClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
        ItemMeta itemMeta = handItem.getItemMeta();
        if (itemMeta == null || !itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "스태프")) return;

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Entity entity = player.getTargetEntity(50);
            if (entity == null || entity instanceof Player || entity instanceof WitherSkeleton) return;

            player.sendMessage("1");
            if (player.isSneaking()) {
                if (entity == null || entity instanceof Player || entity instanceof WitherSkeleton) {

                    double radius = 5.0;
                    int num = spawnW.size();
                    int i = 0;

                    for (WitherSkeleton witherSkeleton : spawnW.values()) {
                        player.sendMessage("2");
                        if(witherSkeleton == null) continue;
                        player.sendMessage("3");
                        double angle = 2 * Math.PI * i / num;
                        Location location = entity.getLocation();
                        double x = entity.getX() + radius * Math.cos(angle);
                        double z = entity.getZ() + radius * Math.sin(angle);
                        Location dest = new Location(entity.getWorld(), x, location.getY(), z);

                        moveLocation(witherSkeleton, dest);
                        witherSkeleton.setAI(false);

                        witherSkeleton.teleport(dest);
                        witherSkeleton.teleport(witherSkeleton.getLocation().setDirection(location.toVector().subtract(dest.toVector())));
                        i++;
                    }
                } else {
                    for (WitherSkeleton witherSkeleton : spawnW.values()) {
                        if (witherSkeleton != null) {
                            attackTarget(witherSkeleton, entity);
                        }
                    }
                }
            }

        }
    }

    private void attackTarget(WitherSkeleton witherSkeleton, Entity target) {
        witherSkeleton.setAI(true); // AI 활성화
        witherSkeleton.setTarget((LivingEntity) target); // 목표 설정

        new BukkitRunnable() {
            @Override
            public void run() {
                // WitherSkeleton이 죽었거나 목표가 죽었거나 목표에 도달하면 실행 취소
                if (witherSkeleton.isDead() || target.isDead() || witherSkeleton.getLocation().distance(target.getLocation()) < 2) {
                    this.cancel();
                    return;
                }
                // 목표를 향해 이동
                Vector direction = target.getLocation().toVector().subtract(witherSkeleton.getLocation().toVector()).normalize();
                witherSkeleton.setVelocity(direction);
            }
        }.runTaskTimer(Control.getInstance(), 0L, 1L); // 0틱 딜레이로 1틱마다 실행
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setResourcePack("http://example.com/path/to/your/resourcepack.zip");
    }

//    @EventHandler
//    public void ShiftLeft(PlayerInteractEvent e) {
//        Player player = e.getPlayer();
//
//        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
//        ItemMeta itemMeta = handItem.getItemMeta();
//        if (itemMeta == null || !itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "스태프")) return;
//
//        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
//            Entity entity1 = player.getTargetEntity(50);
//            player.sendMessage("2");
//            if (player.isSneaking()) {
//                if (entity1 == null || entity1 instanceof Player || entity1 instanceof WitherSkeleton) {
//                   double radius = 5.0;
//                   int num = spawnW.size();
//                   int i = 0;
//
//                   for (WitherSkeleton witherSkeleton : spawnW.values()) {
//                       player.sendMessage("1");
//                       if(witherSkeleton == null) continue;
//                       double angle = 2 * Math.PI * i / num;
//                       Location location = entity1.getLocation();
//                       double x = entity1.getX() + radius * Math.cos(angle);
//                       double z = entity1.getZ() + radius * Math.sin(angle);
//                       Location dest = new Location(entity1.getWorld(), x, location.getY(), z);
//
//                       moveLocation(witherSkeleton, dest);
//
//                       witherSkeleton.teleport(dest);
//                       witherSkeleton.teleport(witherSkeleton.getLocation().setDirection(location.toVector().subtract(dest.toVector())));
//                       i++;
//                   }
//                }
//            }
//        }
//    }

    private void moveLocation(WitherSkeleton skeleton, Location location) { //포위
        new BukkitRunnable() {
            @Override
            public void run() {
                if (skeleton.isDead() || skeleton.getLocation().distance(location) < 1) {
                    this.cancel();
                    return;
                }
                Vector direction = location.toVector().subtract(skeleton.getLocation().toVector()).normalize();
                skeleton.setVelocity(direction);

                skeleton.teleport(skeleton.getLocation().setDirection(location.toVector().subtract(skeleton.getLocation().toVector())));
            }
        }.runTaskTimer(Control.getInstance(), 0L, 1L);
    }
}

//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent e) {
//        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
//
//            Player player = e.getPlayer();
//            ItemStack handItem = player.getInventory().getItemInMainHand();
//            ItemMeta itemMeta = handItem.getItemMeta();
//
//            player.sendMessage("1");
//            if (itemMeta == null || !itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "스태프")) return;
//
//            RayTraceResult result = player.rayTraceEntities(5);
//            if (result == null || result.getHitEntity() == null) return;
//
//            Entity targetEntity = result.getHitEntity();
//            if (player.isSneaking()) {
//                player.sendMessage("2");
//                if (targetEntity instanceof Player || targetEntity instanceof WitherSkeleton) return;
//                player.sendMessage("3");
//
//                double radius = 5.0;
//                int num = spawnW.size();
//                int i = 0;
//
//                for (WitherSkeleton witherSkeleton : spawnW.values()) {
//                    if (witherSkeleton == null) continue; // null 체크 추가
//                    double angle = 2 * Math.PI * i / num;
//                    Location targetLocation = targetEntity.getLocation();
//                    double x = targetLocation.getX() + radius * Math.cos(angle);
//                    double z = targetLocation.getZ() + radius * Math.sin(angle);
//                    Location dest = new Location(targetEntity.getWorld(), x, targetLocation.getY(), z);
//
//                    moveLocation(witherSkeleton, dest);
//
//                    witherSkeleton.teleport(dest);
//                    witherSkeleton.teleport(witherSkeleton.getLocation().setDirection(targetLocation.toVector().subtract(dest.toVector())));
//                    i++;
//                }
//            }
//        }
//    }
//}
