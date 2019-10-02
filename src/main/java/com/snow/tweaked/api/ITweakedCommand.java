package com.snow.tweaked.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public interface ITweakedCommand
{
    /**
     * Allows the command to be hidden from tab completions
     *
     * @return <b>true</b> if hidden
     */
    boolean isHidden();

    /**
     * Callback for when the command is executed
     */
    void execute(MinecraftServer server, EntityPlayer player, String[] args);

    /**
     * Get a list of options for when the user presses the TAB key
     */
    List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, @Nullable BlockPos targetPos);
}
