package commands;

import fines.fines.Fines;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class invoice implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = Fines.config;

        if(!sender.hasPermission("fines.invoice.give")){
            sender.sendMessage(ChatColor.RED + "У вас нет прав!");
            return true;
        }

        if(args.length < 3) return false;

        String reason = " ";

        for (String i:
             args) {
            if(i == args[0] ||
               i == args[1]) continue;

            reason = String.join(" ", Arrays.asList(args).subList(2, args.length).toArray(new String[]{}));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 7);
        String output = sdf.format(c.getTime());

        if(!config.contains("Players."+args[0])){
            config.set("Players."+args[0]+".totalFines", 1);
            config.set("Players."+args[0]+".fines.1."+".value", Integer.parseInt(args[1]));
            config.set("Players."+args[0]+".fines.1."+".reason", reason);
            config.set("Players."+args[0]+".fines.1."+".data", output);
            config.set("Players."+args[0]+".fines.1."+".countOfRdoubling", 0);

            try {
                config.save(Fines.path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(Bukkit.getPlayer(args[0]) != null){
                Player p = Bukkit.getPlayer(args[0]);
                p.sendMessage(ChatColor.RED + "Вам выдан штраф! На сумму "+ChatColor.WHITE+args[1]
                        + ChatColor.RED + "Чтобы узнать свои штрафы пропишите команду /invoicelist");
            }

            sender.sendMessage(ChatColor.GREEN+"Инвойс успешно выдан игроку "+ChatColor.WHITE+args[0]);

            return true;
        }

        int totalFines = config.getInt("Players."+args[0]+".totalFines");
        totalFines += 1;

        config.set("Players."+args[0]+".fines"+"."+totalFines+".value", Integer.parseInt(args[1]));
        config.set("Players."+args[0]+".fines"+"."+totalFines+".reason", reason);
        config.set("Players."+args[0]+".fines"+"."+totalFines+".data", output);
        config.set("Players."+args[0]+".fines"+"."+totalFines+".countOfRdoubling", 0);

        config.set("Players."+args[0]+".totalFines", totalFines);

        try {
            config.save(Fines.path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(Bukkit.getPlayer(args[0]) != null){
            Player p = Bukkit.getPlayer(args[0]);
            p.sendMessage(ChatColor.RED + "Вам выдан штраф! На сумму "+ChatColor.WHITE+args[1] + "АР"
                                        + ChatColor.RED + " Чтобы узнать свои штрафы пропишите команду /invoicelist");
        }

        sender.sendMessage(ChatColor.GREEN+"Инвойс успешно выдан игроку "+ChatColor.WHITE+args[0]);

        return true;
    }
}
