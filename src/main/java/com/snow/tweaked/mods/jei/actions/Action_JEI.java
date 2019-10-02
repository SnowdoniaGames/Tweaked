package com.snow.tweaked.mods.jei.actions;

import com.snow.tweaked.annotation.TweakedAction;
import com.snow.tweaked.lib.ActionAbstract;
import com.snow.tweaked.mods.jei.Tweaked_JEI;
import com.snow.tweaked.script.arguments.ArgItem;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.snow.tweaked.Tweaked.*;

public class Action_JEI {

    public static Action_JEI_Hide HIDE = null;


    //**************************************************************************************//
    //										hide											//
    //**************************************************************************************//

    @TweakedAction(value = "jei.hide", modid = "jei")
    public static class Action_JEI_Hide extends ActionAbstract
    {
        public List<ItemStack> STACKS = new ArrayList<>();

        public Action_JEI_Hide()
        {
            HIDE = this;
        }

        public void build(ArgItem item)
        {
            STACKS.add(item.stack);
        }

        @Override
        protected void run()
        {
            //hide stacks
            if (!STACKS.isEmpty())
            {
                Tweaked_JEI.proxy.hideIngredients(STACKS);

                //debug
                LOG.debug("jei.hide : " + STACKS.size() + " items");
            }

            //cleanup
            STACKS = null;
        }
    }

}
