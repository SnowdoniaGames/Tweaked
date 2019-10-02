package com.snow.tweaked.mods.jei.tests;

import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedTest;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class Test_JEI
{
    //**************************************************************************************//
    //										hide											//
    //**************************************************************************************//

    @TweakedTest(modid = "jei")
    public static class Test_JEI_Hide implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "jei";
        }

        @Override
        public String getTestDescription()
        {
            return "jei - hide";
        }

        @Override
        public String[] getVariables()
        {
            return new String[0];
        }

        @Override
        public String[] getActions()
        {
            return new String[] { "jei.hide : item(minecraft:cobblestone);" };
        }

        @Override
        public boolean runTest(World world)
        {
            //manual test, auto-pass
            return true;
        }
    }
}
