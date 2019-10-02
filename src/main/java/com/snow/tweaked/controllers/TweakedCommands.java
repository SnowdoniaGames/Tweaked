package com.snow.tweaked.controllers;

import com.snow.tweaked.api.ITweakedCommand;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

public class TweakedCommands extends CommandBase
{
    private List<String> commandList = null;

    /**
     * Gets the name of the command, this is what the player must type in
     */
    @Override
    public String getName()
    {
        return "tweaked";
    }

    /**
     * Aliases that can be used, we allow the shorthand 'tw' to be used.
     */
    @Override
    public List<String> getAliases()
    {
        return Collections.singletonList("tw");
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel()
    {
        return 3;
    }

    /**
     * Gets the usage string for the command.
     */
    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.tweaked.usage";
    }

    /**
     * Callback for when the command is executed
     */
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args)
    {
        if (sender.getCommandSenderEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();

            if (args.length == 0)
            {
                player.sendMessage(new TextComponentString("Welcome to Tweaked!"));
            }
            else
            {
                String name = args[0];
                if (!name.isEmpty())
                {
                    ITweakedCommand command = TweakedAnnotations.COMMANDS.get(name.toLowerCase());
                    if (command != null)
                    {
                        command.execute(server, player, args);
                    }
                }
            }
        }
    }

    /**
     * Get a list of options for when the user presses the TAB key
     */
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (commandList == null)
        {
            commandList = new ArrayList<>();
            for (Entry<String, ITweakedCommand> entry : TweakedAnnotations.COMMANDS.entrySet())
            {
                if (!entry.getValue().isHidden())
                {
                    commandList.add(entry.getKey());
                }
            }
        }

        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, commandList);
        }
        else if (args.length == 2)
        {
            if (sender.getCommandSenderEntity() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();

                String name = args[0];
                if (!name.isEmpty())
                {
                    ITweakedCommand command = TweakedAnnotations.COMMANDS.get(name);
                    if (command != null)
                    {
                        return command.getTabCompletions(server, player, args, targetPos);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    public static void sendMessage(EntityPlayer player, String message)
    {
        //create text component
        TextComponentString txtComponent = new TextComponentString(message);

        //send to player
        player.sendMessage(txtComponent);
    }

}
