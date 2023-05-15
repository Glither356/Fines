package commands;

import fines.fines.Fines;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class invoicePlayerLIST implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = Fines.config;

        if(!(sender instanceof Player)) {
            sender.sendMessage("Вы не игрок!");
            return true;
        }

        if(!config.contains("Players."+args[0])) {
            sender.sendMessage(ChatColor.RED+ "У игрока нет инвойсов!");
            return true;
        }

        int tf = config.getInt("Players."+args[0]+".totalFines");
        List<Integer> totalFines = new ArrayList<>();

        for (int i = 1; i <= tf;){
            if(config.contains("Players."+args[0]+".fines."+i)){
                totalFines.add(i);
                i++;
            }
        }

        sender.sendMessage(ChatColor.GREEN+"Инвойсы игрока " + ChatColor.WHITE+args[0]);
        for(int i:
            totalFines){
            int Amount = config.getInt("Players."+args[0]+".fines."+i+".value");
            String reason = config.getString("Players."+args[0]+".fines."+i+".reason");

            sender.sendMessage(ChatColor.GREEN+"#"+i+":"+ChatColor.WHITE+" Кол-во аров для оплаты "+Amount+
                    ChatColor.GREEN+" Причина: " + ChatColor.WHITE+ reason);
        }

         return true;
    }
}
