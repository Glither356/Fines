package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class invoiceHELP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Вы не игрок");
            return true;
        }

        sender.sendMessage("Дабы оплатить штраф. Вам нужно прийти на спавн ..." +
                "зайти в комнату. Взять книгу, написать айди штрафа и сумму АР. После в течении суток вам снимут штраф");

        return true;
    }
}
