package top.earthvillage.aprilfools;



import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AprilFoolsCommand implements CommandExecutor {

    private final AprilFoolsPlugin plugin;

    public AprilFoolsCommand(AprilFoolsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 检查发送命令的是否是玩家
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // 创建一个普通木棍物品
            ItemStack aprilFoolsItem = new ItemStack(Material.STICK);

            // 获取物品的 ItemMeta
            ItemMeta meta = aprilFoolsItem.getItemMeta();

            if (meta != null) {
                // 创建一个 List<String> 来作为物品的 Lore
                List<String> lore = new ArrayList<>();
                lore.add("愚人节快乐！");  // 第一行描述
                lore.add("这个物品用于恶搞你的朋友");  // 第二行描述
                lore.add("使用时会触发有趣的效果");  // 第三行描述

                // 设置物品的 Lore
                meta.setLore(lore);

                // 设置物品的 ItemMeta（将修改应用到物品上）
                aprilFoolsItem.setItemMeta(meta);
            }

            // 将物品添加到玩家的背包
            player.getInventory().addItem(aprilFoolsItem);

            // 给玩家发送消息
            player.sendMessage("愚人节快乐！你已获得特殊道具！");

            return true;
        } else {
            sender.sendMessage("只有玩家可以使用该指令！");
            return false;
        }
    }

}
