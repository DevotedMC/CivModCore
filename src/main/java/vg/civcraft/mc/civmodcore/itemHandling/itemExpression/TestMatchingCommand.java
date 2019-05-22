package vg.civcraft.mc.civmodcore.itemHandling.itemExpression;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class TestMatchingCommand implements CommandExecutor {
	public TestMatchingCommand(Map<String, ItemExpression> itemExpressions) {
		this.itemExpressions = itemExpressions;
	}

	Map<String, ItemExpression> itemExpressions;

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String name, String[] args) {
		if (args.length != 1)
			return false;

		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage(ChatColor.RED + "This command can only be used by players.");
			return true;
		}

		Player player = (Player) commandSender;

		String ieName = args[0];
		ItemExpression expression = itemExpressions.get(ieName);
		ItemStack item = player.getInventory().getItemInMainHand();

		if (expression.matches(item)) {
			commandSender.sendMessage("Matches!");
		} else {
			commandSender.sendMessage("Does not match.");
		}

		return true;
	}
}
