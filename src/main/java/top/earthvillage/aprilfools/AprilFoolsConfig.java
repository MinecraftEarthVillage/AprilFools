package top.earthvillage.aprilfools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class AprilFoolsConfig {

    public static void executeCommands(AprilFoolsPlugin plugin, Player executor, Player target, String loreGroup) {
        // 获取配置文件中的指令列表
        List<String> commands = (List<String>) plugin.getConfig().getList(loreGroup + ".commands");

        if (commands == null) {
            executor.sendMessage("未找到与此Lore组相关的指令！");
            return;
        }

        for (String command : commands) {
            // 替换变量
            command = command.replace("%玩家%", target.getName());  // 替换 %玩家%
            command = command.replace("%当前拿着道具的人%", executor.getName());  // 替换 %当前拿着道具的人%

            // 替换 %所有人% 为所有在线玩家
            if (command.contains("%所有人%")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String commandForAll = command.replace("%所有人%", player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandForAll);
                }
            } else {
                // 执行其他普通命令
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }
    }

    // 保存 Lore 和对应的指令
    public static void saveLoreCommands(AprilFoolsPlugin plugin, String loreGroup, List<String> commands) {
        plugin.getConfig().set(loreGroup + ".commands", commands);
        plugin.saveConfig();
    }
}
