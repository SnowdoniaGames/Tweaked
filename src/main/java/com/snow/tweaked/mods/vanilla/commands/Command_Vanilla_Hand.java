package com.snow.tweaked.mods.vanilla.commands;

import com.snow.tweaked.annotation.TweakedCommand;
import com.snow.tweaked.api.ITweakedCommand;
import com.snow.tweaked.controllers.TweakedCommands;
import com.snow.tweaked.script.ScriptHelper;
import com.snow.tweaked.controllers.TweakedLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

import static com.snow.tweaked.Tweaked.LOG;

@TweakedCommand(value = "hand", modid = "minecraft")
public class Command_Vanilla_Hand implements ITweakedCommand
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
            LOG.dump("/tweaked hand");

            //create a script version of the stack
            String out = ScriptHelper.stackToScript(heldItem);

            //reply
            TweakedCommands.sendMessage(player, out);

            //dump the message
            LOG.dump(TweakedLogger.TAB + out);
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
