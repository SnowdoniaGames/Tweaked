package com.snow.tweaked.mods.vanilla.commands;

import com.snow.tweaked.annotation.TweakedCommand;
import com.snow.tweaked.api.ITweakedCommand;
import com.snow.tweaked.controllers.TweakedCommands;
import com.snow.tweaked.controllers.TweakedLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Collections;
import java.util.List;

import static com.snow.tweaked.Tweaked.LOG;

@TweakedCommand(value = "fluid", modid = "minecraft")
public class Command_Vanilla_Fluid implements ITweakedCommand
{
    @Override
    public boolean isHidden()
    {
        return false;
    }

    @Override
    public void execute(MinecraftServer server, EntityPlayer player, String[] args)
    {
        //dump the message
        LOG.dump("/tweaked fluid");

        //get all registered fluids
        for (String out : FluidRegistry.getRegisteredFluids().keySet())
        {
            //create the component
            TweakedCommands.sendMessage(player, out);

            //dump the message
            LOG.dump(TweakedLogger.TAB + out);
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
    {
        return Collections.emptyList();
    }

}
