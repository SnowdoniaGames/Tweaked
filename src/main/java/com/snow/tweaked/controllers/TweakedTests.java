package com.snow.tweaked.controllers;

import com.snow.tweaked.api.ITweakedTest;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.snow.tweaked.Tweaked.LOG;

public class TweakedTests
{
    private static int passCount = 0;
    private static int failCount = 0;

    private static final List<String> passes = new ArrayList<>();
    private static final List<String> failures = new ArrayList<>();

    public static void run(World world)
    {
        //run the tests
        for (ITweakedTest test : TweakedAnnotations.TESTS)
        {
            if (test.runTest(world))
            {
                passCount++;
                passes.add(test.getTestDescription());
            }
            else
            {
                failCount++;
                failures.add(test.getTestDescription());
            }
        }

        //output results
        boolean passed = failCount == 0;

        LOG.print("***************************************************************************************************");
        LOG.print("TWEAKED TEST RESULTS : " + (passed ? "PASSED" : "FAILED"));
        LOG.print(" ");
        LOG.print("Tests Passed : " + passCount);
        LOG.print("Tests Failed : " + failCount);
        if (!passes.isEmpty())
        {
            //sort alphabetically
            Collections.sort(passes);

            LOG.print(" ");
            LOG.print("Passed Tests : ");
            for (String s : passes)
            {
                LOG.print(TweakedLogger.TAB + s);
            }
        }
        if (!failures.isEmpty())
        {
            //sort alphabetically
            Collections.sort(failures);

            LOG.print(" ");
            LOG.print("Failed Tests : ");
            for (String s : failures)
            {
                LOG.print(TweakedLogger.TAB + s);
            }
        }
        LOG.print("***************************************************************************************************");
    }
}
