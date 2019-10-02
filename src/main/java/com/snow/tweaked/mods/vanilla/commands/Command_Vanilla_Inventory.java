package com.snow.tweaked.mods.vanilla.commands;

import com.snow.tweaked.annotation.TweakedCommand;
import com.snow.tweaked.api.ITweakedCommand;
import com.snow.tweaked.controllers.TweakedCommands;
import com.snow.tweaked.script.ScriptHelper;
import com.snow.tweaked.controllers.TweakedLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

import static com.snow.tweaked.Tweaked.LOG;

@TweakedCommand(value = "inventory", modid = "minecraft")
public class Command_Vanilla_Inventory implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return false;
	}
	
	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		//get player inventories
		InventoryPlayer inv = player.inventory;

        if(inv != null)
        {
        	//dump the message
    		LOG.dump("/tweaked inventory");

    		int total = 0;
    		for (ItemStack stack : inv.mainInventory)
			{
				if (!stack.isEmpty())
				{
					total++;

					LOG.dump(TweakedLogger.TAB + ScriptHelper.stackToScript(stack));
				}
			}

			if (total > 0)
			{
				TweakedCommands.sendMessage(player,"Dumped " + total + " stacks");
			}
			else
			{
				TweakedCommands.sendMessage(player, "Requires items in players inventory");
			}
        }
        else
        {
			TweakedCommands.sendMessage(player, "Requires items in players inventory");
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}
