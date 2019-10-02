package com.snow.tweaked.mods.vanilla.commands;

import com.snow.tweaked.annotation.TweakedCommand;
import com.snow.tweaked.api.ITweakedCommand;
import com.snow.tweaked.controllers.TweakedCommands;
import com.snow.tweaked.mods.vanilla.Tweaked_Vanilla;
import com.snow.tweaked.script.ScriptHelper;
import com.snow.tweaked.controllers.TweakedLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.snow.tweaked.Tweaked.LOG;

@TweakedCommand(value = "recipe", modid = "minecraft")
public class Command_Vanilla_Recipe implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return false;
	}
	
	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		//get held item
        ItemStack heldItem = player.getHeldItemMainhand();

        // Tries to get name of held item first
        if(!heldItem.isEmpty()) 
        {
        	//dump the message
    		LOG.dump("/tweaked recipe");
        	
    		//double check we have recipes loaded
			if (Tweaked_Vanilla.RECIPE_REGISTRY == null)
			{
				LOG.warn("Warning : Recipe Registry Missing");
				return;
			}
			
			//convert stacks into recipe names
			List<String> recipeList = new ArrayList<>();
			for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
			{
				if (ItemStack.areItemsEqual(heldItem, recipe.getValue().getRecipeOutput()))
				{
					recipeList.addAll(ScriptHelper.recipeToScript(recipe.getValue(), true));
				}
			}

			//make outputs
			if (recipeList.isEmpty())
			{
				TweakedCommands.sendMessage(player, "No recipes found");
				
				//dump the message
	            LOG.dump(TweakedLogger.TAB + "No recipes found");
			}

			//reply
			TweakedCommands.sendMessage(player, recipeList.size() + " recipes have been dumped");
			
			//dump the message
            LOG.dump(TweakedLogger.TAB + "Found " + recipeList.size() + " recipes :");
			
			for (String s : recipeList)
			{
				//dump the message
	            LOG.dump(TweakedLogger.DOUBLE_TAB + s);
			}
        }
        else
        {
			TweakedCommands.sendMessage(player, "Requires an item in the main hand");
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}
