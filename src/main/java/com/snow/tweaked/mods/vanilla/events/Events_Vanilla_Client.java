package com.snow.tweaked.mods.vanilla.events;

import com.snow.tweaked.mods.vanilla.Tweaked_Vanilla;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.client.util.SearchTree;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.snow.tweaked.Tweaked.LOG;

public class Events_Vanilla_Client
{
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpenEvent(GuiOpenEvent event) 
	{
        Minecraft minecraft = Minecraft.getMinecraft();
        if(minecraft.player != null && !Tweaked_Vanilla.RECIPEBOOK_FIXED)
        {
        	//only do this once
	        Tweaked_Vanilla.RECIPEBOOK_FIXED = true;
            
            //rebuild the recipes table used by the recipe book
            RecipeBookClient.rebuildTable();
            
            //update the search tree
            minecraft.populateSearchTreeManager();
            ((SearchTree<?>) minecraft.getSearchTreeManager().get(SearchTreeManager.ITEMS)).recalculate();
            ((SearchTree<?>) minecraft.getSearchTreeManager().get(SearchTreeManager.RECIPES)).recalculate();
            
            LOG.debug("RecipeBook fixes applied");
        }
	}
}
