package commands;

import fines.fines.Fines;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class invoiceremove implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = Fines.config;

        if(!(sender instanceof Player)){
            sender.sendMessage("Вы не игрок");
            return true;
        }

        if(!(sender.hasPermission("fines.invoice.remove"))){
            sender.sendMessage(ChatColor.RED+"У вас нет прав!");
            return true;
        }

        if(args.length < 2) return false;

        int totalFines = config.getInt("Players."+args[0]+".totalFines");
        totalFines -= 1;

        config.set("Players."+args[0]+".fines."+args[1], null);

        config.set("Players."+args[0]+".totalFines", totalFines);

        //sender.sendMessage(list.toString());

        try {
            config.save(Fines.path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sender.sendMessage(ChatColor.GREEN+"Инвойс был удален!");

        return true;
    }
}
