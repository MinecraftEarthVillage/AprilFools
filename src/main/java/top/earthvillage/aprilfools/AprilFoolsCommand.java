package top.earthvillage.aprilfools;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class AprilFoolsCommand implements CommandExecutor {

    private final AprilFoolsPlugin plugin;

    public AprilFoolsCommand(AprilFoolsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 检查发送命令的是否是玩家
        if (sender instanceof Player && sender.isOp()) {
            Player player = (Player) sender;

            // 判断是否有正确的 lore 组名
            if (args.length == 1) {
                String loreGroup = args[0];
                Map<String, Object> groupConfig = plugin.getConfig().getConfigurationSection(loreGroup) != null
                        ? plugin.getConfig().getConfigurationSection(loreGroup).getValues(false)
                        : null;

                if (groupConfig != null && groupConfig.containsKey("lore") && groupConfig.containsKey("commands")) {
                    // 获取配置的 Lore 和指令
                    List<String> lore = (List<String>) groupConfig.get("lore");
                    List<String> commands = (List<String>) groupConfig.get("commands");

                    // 创建一个木棍物品
                    ItemStack aprilFoolsItem = new ItemStack(Material.STICK);
                    ItemMeta meta = aprilFoolsItem.getItemMeta();

                    if (meta != null) {
                        // 设置物品的 Lore
                        meta.setLore(lore);

                        // 设置物品的 ItemMeta（将修改应用到物品上）
                        aprilFoolsItem.setItemMeta(meta);
                    }

                    // 将物品添加到玩家的背包
                    player.getInventory().addItem(aprilFoolsItem);

                    // 给玩家发送消息
                    player.sendMessage("愚人节快乐！你已获得 " + loreGroup + " 的特殊道具！");

                    // 保存该指令组
                    AprilFoolsConfig.saveLoreCommands(plugin, loreGroup, commands);

                    return true;
                } else {
                    player.sendMessage("指定的Lore组不存在或配置不完整！");
                    return false;
                }
            } else {
                player.sendMessage("请输入有效的Lore组名，例如：/愚人节快乐 第一组");
                return false;
            }
        } else {
            sender.sendMessage("你没有权限使用此命令");
            return false;
        }
    }
}
