package com.snow.tweaked.mods.vanilla.commands;

import com.snow.tweaked.annotation.TweakedCommand;
import com.snow.tweaked.api.ITweakedCommand;
import com.snow.tweaked.controllers.TweakedCommands;
import com.snow.tweaked.script.ScriptHelper;
import com.snow.tweaked.controllers.TweakedLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

import static com.snow.tweaked.Tweaked.LOG;

@TweakedCommand(value = "ore", modid = "minecraft")
public class Command_Vanilla_Ore implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return false;
	}
	
	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		if (args.length >= 2)
		{
			String oreName = args[1];

			if (OreDictionary.doesOreNameExist(oreName))
			{
				//dump the message
				LOG.dump("/tweaked ore " + oreName);

				NonNullList<ItemStack> ores = OreDictionary.getOres(oreName);
				for (ItemStack ore : ores)
				{
					String out = ScriptHelper.stackToScript(ore);

					//reply
					TweakedCommands.sendMessage(player, out);

					//dump the message
					LOG.dump(TweakedLogger.TAB + out);
				}
			}
			else
			{
				TweakedCommands.sendMessage(player, "Ore name doesn't exist");
			}
		}
		else
		{
			//no dict specified, do held item scan
			ItemStack heldItem = player.getHeldItemMainhand();

			// Tries to get name of held item first
			if(!heldItem.isEmpty())
			{
				//dump the message
				LOG.dump("/tweaked ore");

				int[] idArray = OreDictionary.getOreIDs(heldItem);

				if (idArray.length == 0)
				{
					//create the text component
					TweakedCommands.sendMessage(player, "No matching ore dictionary");

					//dump the message
					LOG.dump(TweakedLogger.TAB + "No matching ore dictionary");

					return;
				}

				//create output from ore items
				for (int id : idArray)
				{
					//get the ore name
					String out = OreDictionary.getOreName(id);

					//reply
					TweakedCommands.sendMessage(player, out);

					//dump the message
					LOG.dump(TweakedLogger.TAB + out);
				}
			}
			else
			{
				TweakedCommands.sendMessage(player, "Requires an item in the main hand");
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}
