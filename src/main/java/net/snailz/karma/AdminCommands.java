package net.snailz.karma;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements CommandExecutor{

    KarmaUserManager karmaUserManager;
    public AdminCommands(KarmaUserManager karmaUserManager){
        this.karmaUserManager = karmaUserManager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("karma")){
            if (args.length == 0){
                showHelpMenu(commandSender);
                return true;
            }
            if (args[0].equalsIgnoreCase("help")){
                showHelpMenu(commandSender);
                return true;
            }
            if (args[0].equalsIgnoreCase("reset")){
                String playerName = args[1];
                Player player = Bukkit.getPlayer(args[1]);
            }
        }
        return false;
    }

    private void showHelpMenu(CommandSender sender){
        sender.sendMessage("Karma Help Menu Not Yet Implemented");
    }
}
