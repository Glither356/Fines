package commands;

import fines.fines.Fines;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class invoicelist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = Fines.config;
        Player p = (Player) sender;

        if(!(sender instanceof Player)) {
            sender.sendMessage("Вы не игрок!");
            return true;
        }

        if(!config.contains("Players."+p.getName())) {
            sender.sendMessage(ChatColor.RED+ "У вас штрафов нет!");
            return true;
        }

        int tf = config.getInt("Players."+p.getName()+".totalFines");
        List<Integer> totalFines = new ArrayList<>();

        for (int i = 1; i <= tf;){
            if(config.contains("Players."+p.getName()+".fines."+i)){
                totalFines.add(i);
                i++;
            }
        }

        sender.sendMessage(ChatColor.GREEN+"Ваши инвойсы:");
        for(int i:
            totalFines){
            int Amount = config.getInt("Players."+p.getName()+".fines."+i+".value");
            String reason = config.getString("Players."+p.getName()+".fines."+i+".reason");

            sender.sendMessage(ChatColor.GREEN+"#"+i+":"+ChatColor.WHITE+" Кол-во аров для оплаты "+Amount+
                    ChatColor.GREEN+" Причина: " + ChatColor.WHITE+ reason);
        }

        return true;
    }
}
