package top.earthvillage.aprilfools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class AprilFoolsPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // 注册指令和事件监听
        getCommand("愚人节快乐").setExecutor(new AprilFoolsCommand(this));
        Bukkit.getPluginManager().registerEvents(this, this);
        // 确保插件目录和配置文件被创建
        saveDefaultConfig();  // 这一行代码会创建默认的 config.yml 文件
    }

    @Override
    public void onDisable() {
        // 插件禁用时的清理操作
    }

    // 监听玩家右键点击实体事件
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickedEntity = event.getRightClicked(); // 获取点击的实体

        // 获取玩家手持物品的 ItemMeta
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta meta = itemInHand.getItemMeta();

        if (meta != null && meta.hasLore()) {
            // 获取物品的 Lore
            List<String> lore = meta.getLore();

            // 检查物品的 Lore 是否包含特定的标识，例如 "愚人节棒子"
            if (lore.contains("愚人节快乐！")) {
                // 如果点击的是玩家，执行对应指令
                if (clickedEntity instanceof Player) {
                    Player target = (Player) clickedEntity;
                    AprilFoolsConfig.executeCommands(this, player, target);
                } else {
                    // 如果点击的是非玩家实体，执行自己触发的指令
                    AprilFoolsConfig.executeCommands(this, player, player);
                }
            }
        }
    }
}
