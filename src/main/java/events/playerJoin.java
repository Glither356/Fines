package events;

import fines.fines.Fines;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class playerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws ParseException {
        FileConfiguration config = Fines.config;
        Player p = e.getPlayer();

        if(!config.contains("Players."+p.getName())){
            return;
        }

        int tf = config.getInt("Players."+p.getName()+".totalFines");
        List<Integer> totalFines = new ArrayList<>();

        for (int i = 1; i <= tf;){
            if(config.contains("Players."+p.getName()+".fines."+i)){
                totalFines.add(i);
                i++;
            }
        }

        int Amount;

        for (int i:
            totalFines){

            Amount = config.getInt("Players."+p.getName()+".fines."+i+".value");

            p.sendMessage(ChatColor.RED+"У вас не оплаченный инвойс на сумму "
                    + ChatColor.GOLD +Amount
                    +ChatColor.RED+"\n Напишите команду /invoiceshelp, чтобы узнать подробности");

            switch (config.getInt("Players."+p.getName()+".fines."+i+".countOfRdoubling")){
                case 2:
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c2 = Calendar.getInstance();
                    c2.setTime(new Date());

                    if(Objects.equals(config.getString("Players." + p.getName() + ".fines." + i + ".data"), sdf2.format(c2.getTime()))){
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:ban " + p.getName() + " не выплата инвойса");
                        config.set("Players."+p.getName()+"."+i+".countOfRdoubling", 3);
                    }

                    break;

                case 1:
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.DATE, 7);
                    String output = sdf.format(c.getTime());

                    if(Objects.equals(config.getString("Players." + p.getName() + ".fines." + i + ".data"), sdf.format(c.getTime()))){
                        config.set("Players."+p.getName()+".fines."+i+".countOfRdoubling", 2);
                        config.set("Players."+p.getName()+".fines."+i+".value",
                                config.getInt("Players."+p.getName()+".fines."+i+".value")*2);

                        config.set("Players."+p.getName()+".fines."+i+".data", output);
                    }

                    break;

            }
        }

    }
}
