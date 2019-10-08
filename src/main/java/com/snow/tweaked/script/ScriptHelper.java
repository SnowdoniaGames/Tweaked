package com.snow.tweaked.script;

import com.snow.tweaked.api.ITweakedTest;
import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IFunction;
import com.snow.tweaked.api.script.IToken;
import com.snow.tweaked.controllers.TweakedConfig;
import com.snow.tweaked.controllers.TweakedAnnotations;
import com.snow.tweaked.script.arguments.*;
import com.snow.tweaked.script.functions.FunctionAction;
import com.snow.tweaked.script.functions.FunctionDefine;
import com.snow.tweaked.script.functions.FunctionFor;
import com.snow.tweaked.script.functions.FunctionPrint;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static com.snow.tweaked.Tweaked.LOG;

public class ScriptHelper
{
    private static boolean initComplete = false;
    public enum TokenType
    {
        LINE_SEPARATOR,
        LINE_BREAK,
        WHITESPACE,
        QUOTE,
        COLON,
        COMMA,
        PERIOD,
        VAR_OPEN,
        VAR_CLOSE,
        LIST_OPEN,
        LIST_CLOSE,
        IGNORE,
        COMMENT,
        LOGIC_OPEN,
        LOGIC_CLOSE,
    }

    public static String fileName;
    public static int lineCount = 0;
    public static HashMap<Character, TokenType> tokenMap = new HashMap<>();
    public static List<IArgument> argMap = new ArrayList<>();
    public static List<IFunction> functionMap = new ArrayList<>();
    public static void initMaps()
    {
        if (!initComplete)
        {
            tokenMap.put('\t', TokenType.IGNORE);
            tokenMap.put(' ', TokenType.WHITESPACE);
            tokenMap.put('#', TokenType.COMMENT);
            tokenMap.put('\r', TokenType.LINE_SEPARATOR);
            tokenMap.put('\n', TokenType.LINE_SEPARATOR);
            tokenMap.put(';', TokenType.LINE_BREAK);
            tokenMap.put(':', TokenType.COLON);
            tokenMap.put('(', TokenType.VAR_OPEN);
            tokenMap.put(')', TokenType.VAR_CLOSE);
            tokenMap.put('[', TokenType.LIST_OPEN);
            tokenMap.put(']', TokenType.LIST_CLOSE);
            tokenMap.put('{', TokenType.LOGIC_OPEN);
            tokenMap.put('}', TokenType.LOGIC_CLOSE);
            tokenMap.put('"', TokenType.QUOTE);
            tokenMap.put(',', TokenType.COMMA);
            tokenMap.put('.', TokenType.PERIOD);

            argMap.add(new ArgAll());
            argMap.add(new ArgNull());
            argMap.add(new ArgVar());
            argMap.add(new ArgLogicVar());
            argMap.add(new ArgItem());
            argMap.add(new ArgOre());
            argMap.add(new ArgFluid());
            argMap.add(new ArgString());
            argMap.add(new ArgInteger());
            argMap.add(new ArgFloat());
            argMap.add(new ArgIdentifier());

            functionMap.add(new FunctionPrint());
            functionMap.add(new FunctionDefine());
            functionMap.add(new FunctionFor());
            functionMap.add(new FunctionAction());

            initComplete = true;
        }
    }

    public static void throwScriptError(String filename, Integer lineCount, String error)
    {
        if (filename == null)
        {
            LOG.warn(lineCount == null ? "Compile Error : " + error : "[" + lineCount + "] Compile Error : " + error);
        }
        else
        {
            LOG.warn(lineCount == null ? "[" + filename + "] Compile Error : " + error : "[" + filename + ":" + lineCount + "] Compile Error : " + error);
        }
    }

    @SuppressWarnings("UnnecessaryContinue")
    public static void loadScripts()
    {
        //create scripts directory
        File scriptDir = new File(TweakedConfig.tweakedDir + File.separator + "scripts");
        if (!scriptDir.exists())
        {
            if (!scriptDir.mkdirs())
            {
                LOG.warn("Error creating scripting directory");
            }
        }

        //parse script files
        for (File f : listFiles(scriptDir))
        {
            if (f.getName().endsWith(".tweak"))
            {
                fileName = f.getName();

                //parse file
                try
                {
                    byte[] readIn = Files.readAllBytes(f.toPath());

                    List<IToken> compileList;

                    Compiler compiler = new Compiler();
                    compileList = compiler.compile(readIn);
                    if (compileList == null) continue;

                    Assembler assembler = new Assembler();
                    compileList = assembler.assemble(compileList);
                    if (compileList == null) continue;
                }
                catch (IOException e)
                {
                    LOG.debug("Failed to parse script");
                }
            }
        }
    }

    @SuppressWarnings("UnnecessaryContinue")
    public static void loadTests()
    {
        //create test directory
        File scriptDir = new File(TweakedConfig.tweakedDir + File.separator + "tests");
        if (!scriptDir.exists())
        {
            if (!scriptDir.mkdirs())
            {
                LOG.warn("Error creating test directory");
            }
        }

        //clean directory, testing always starts from scratch
        for (File f : ScriptHelper.listFiles(scriptDir))
        {
            if (!f.delete())
            {
                LOG.warn("Error deleting test directory");
            }
        }

        //generate test files
        for (ITweakedTest test : TweakedAnnotations.TESTS)
        {
            //write variables
            for (String s : test.getVariables())
            {
                try
                {
                    Path path = Paths.get(scriptDir.getPath() + File.separator + test.getFilename() + ".tweak");
                    Files.write(path, Collections.singletonList(s), Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                }
                catch (IOException e)
                {
                    LOG.warn("Unable to write test : " + s);
                }
            }
        }

        for (ITweakedTest test : TweakedAnnotations.TESTS)
        {
            //write actions
            for (String s : test.getActions())
            {
                try
                {
                    Path path = Paths.get(scriptDir.getPath() + File.separator + test.getFilename() + ".tweak");
                    Files.write(path, Collections.singletonList(s), Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                }
                catch (IOException e)
                {
                    LOG.warn("Unable to write test : " + s);
                }
            }
        }

        //parse script files
        for (File f : listFiles(scriptDir))
        {
            if (f.getName().endsWith(".tweak"))
            {
                fileName = f.getName();

                //parse file
                try
                {
                    byte[] readIn = Files.readAllBytes(f.toPath());

                    List<IToken> compileList;

                    Compiler compiler = new Compiler();
                    compileList = compiler.compile(readIn);
                    if (compileList == null) continue;

                    Assembler assembler = new Assembler();
                    compileList = assembler.assemble(compileList);
                    if (compileList == null) continue;
                }
                catch (IOException e)
                {
                    LOG.debug("Failed to parse script");
                }
            }
        }
    }

    private static Collection<File> listFiles(File dir)
    {
        Set<File> fileTree = new HashSet<>();
        if (dir == null || dir.listFiles() == null)
        {
            return fileTree;
        }

        for (File entry : Objects.requireNonNull(dir.listFiles()))
        {
            if (entry.isFile()) fileTree.add(entry);
            else fileTree.addAll(listFiles(entry));
        }
        return fileTree;
    }

    /**
     * A special item match that only checks nbt data if specified by the script item.
     * This allows users to specify all items by having no nbt, or more specific items with nbt
     *
     * @param scriptItem The itemstack that was generated by a Tweaked script
     * @param otherItem  The itemstack we are comparing it to
     * @return True if the items match
     */
    public static boolean areScriptItemsEqual(ItemStack scriptItem, ItemStack otherItem)
    {
        if (scriptItem.hasTagCompound())
        {
            return ItemStack.areItemStacksEqual(scriptItem, otherItem);
        }
        else
        {
            return ItemStack.areItemsEqualIgnoreDurability(scriptItem, otherItem);
        }
    }

    /**
     * A fluid match that can be used to compare if two fluids are the same.
     * Unlike the similar item method, this will ignore the amount of fluid and nbt data
     *
     * @param scriptFluid The fluidstack that was generated by a Tweaked script
     * @param otherFluid The fluidstack we are comparing it to
     * @return True if the fluids match
     */
    public static boolean areScriptFluidsEqual(FluidStack scriptFluid, FluidStack otherFluid)
    {
        return scriptFluid.getFluid() == otherFluid.getFluid();
    }

    public static String stackToScript(ItemStack stackIn)
    {
        StringBuilder sb = new StringBuilder();

        ItemStack stack = stackIn.copy();

        //main item
        sb.append("item(").append(stack.getItem().getRegistryName()).append(")");

        //metadata
        if (stack.getMetadata() != 0)
        {
            sb.append(".meta(").append(stack.getMetadata()).append(")");
        }

        //count
        if (stack.getCount() > 1)
        {
            sb.append(".count(").append(stack.getCount()).append(")");
        }

        //get nbt data
        int nbt = stack.getTagCompound() == null ? 0 : stack.getTagCompound().getSize();

        //enchants (taken out of nbt)
        if (nbt > 0)
        {
            NBTTagList enchList = stack.getEnchantmentTagList();
            if (enchList.tagCount() > 0)
            {
                for (int i = 0; i < enchList.tagCount(); ++i)
                {
                    NBTTagCompound enchTag = enchList.getCompoundTagAt(i);
                    int id = enchTag.getShort("id");
                    int level = enchTag.getShort("lvl");

                    Enchantment enchant = Enchantment.getEnchantmentByID(id);
                    if (enchant != null)
                    {
                        ResourceLocation location = Enchantment.REGISTRY.getNameForObject(enchant);
                        if (location != null)
                        {
                            sb.append(".enchant(").append(location).append(":").append(level).append(")");
                        }
                    }
                }
                stack.getTagCompound().removeTag("ench");
            }

            //refresh nbt
            nbt = stack.getTagCompound() == null ? 0 : stack.getTagCompound().getSize();
        }

        //remaining nbt
        if (nbt > 0)
        {
            if (stack.getTagCompound().getSize() > 0)
            {
                sb.append(".nbt(").append(stack.getTagCompound().toString()).append(")");
            }
        }

        return sb.toString();
    }

    /**
     * Parses a Minecraft ITweakedIngredient into a Tweaked ITweakedIngredient
     *
     * @param ingredient The ingredient
     * @return array of strings containing either a null, dict or all its related stacks
     */
    private static String[] ingredientToScripts(Ingredient ingredient)
    {
        if (ingredient.getMatchingStacks().length == 0)
        {
            //empty
            return new String[] { "null" };
        }
        else
        {
            //get first item
            ItemStack[] stackList = ingredient.getMatchingStacks();

            if (ingredient instanceof OreIngredient)
            {
                //this is a ballache but imo the best way, try to find an oredict that matches all stacks
                List<Integer> idList = null;
                for (ItemStack stack : ingredient.getMatchingStacks())
                {
                    //get ids
                    int[] in = OreDictionary.getOreIDs(stack);

                    //convert them to Integer
                    Integer[] ids = new Integer[in.length];
                    for (int i = 0; i < in.length; i++)
                    {
                        ids[i] = in[i];
                    }

                    if (idList == null)
                    {
                        idList = Arrays.asList(ids);
                    }
                    else
                    {
                        idList.retainAll(Arrays.asList(ids));
                    }
                }

                //if its empty then we don't have a match, return all the items
                if (idList == null || idList.isEmpty())
                {
                    String[] strs = new String[stackList.length];

                    //convert them to strings
                    for (int i = 0; i < stackList.length; i++)
                    {
                        strs[i] = stackToScript(stackList[i]);
                    }

                    return strs;
                }

                //if not, return the first oredict the matches, hopefully it's the most relevant
                return new String[]{"ore(" + OreDictionary.getOreName(idList.get(0)) + ")"};
            }
            else
            {
                String[] strs = new String[stackList.length];

                //convert them to strings
                for (int i = 0; i < stackList.length; i++)
                {
                    strs[i] = stackToScript(stackList[i]);
                }

                return strs;
            }
        }
    }

    /**
     * Parses a recipe into all its related Tweaked recipes
     *
     * @param recipe The recipe
     * @param debug  Whether to print debug information such as recipe type
     * @return A list of all related Tweaked recipes
     */
    public static List<String> recipeToScript(IRecipe recipe, boolean debug)
    {
        if (recipe instanceof ShapedRecipes)
        {
            return shapedToScript((ShapedRecipes) recipe, debug);
        }
        else if (recipe instanceof ShapedOreRecipe)
        {
            return shapedOreToScript((ShapedOreRecipe) recipe, debug);
        }
        else if (recipe instanceof ShapelessRecipes)
        {
            return shapelessToScript((ShapelessRecipes) recipe, debug);
        }
        else if (recipe instanceof ShapelessOreRecipe)
        {
            return shapelessOreToScript((ShapelessOreRecipe) recipe, debug);
        }

        return new ArrayList<>(Collections.singleton(recipe.toString()));
    }

    /**
     * Parses a shaped recipe into all its related Tweaked recipes
     *
     * @param recipe The recipe
     * @return A list of all related Tweaked recipes
     */
    private static List<String> shapedToScript(ShapedRecipes recipe, boolean debug)
    {
        List<String> ret = new ArrayList<>();
        StringBuilder out = new StringBuilder();

        //inputs
        List<String[]> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients())
        {
            inputs.add(ingredientToScripts(ingredient));
        }

        //we have to go through each ingredient, and build a recipe for all possibilities
        List<String[]> permutations = new ArrayList<>();
        generatePermutations(inputs, permutations, 0, null);

        for (String[] permutation : permutations)
        {
            //debug
            if (debug)
            {
                out.append("recipes.shaped : ");
            }

            //recipe name
            out.append("\"").append(recipe.getRegistryName()).append("\"");
            out.append(", ");

            //output
            out.append(stackToScript(recipe.getRecipeOutput()));
            out.append(", ");

            int recipeWidth = recipe.getRecipeWidth();
            int recipeHeight = recipe.getRecipeHeight();
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (i < recipeHeight && j < recipeWidth) out.append(permutation[(i * recipe.getRecipeWidth()) + j]);
                    else out.append("null");

                    if (j < 2) out.append(", ");
                }
                if (i < 2) out.append(", ");
            }
            out.append(";");

            ret.add(out.toString());
            out = new StringBuilder();
        }

        return ret;
    }

    /**
     * Parses a shaped ore recipe into all its related Tweaked recipes
     *
     * @param recipe The recipe
     * @return A list of all related Tweaked recipes
     */
    private static List<String> shapedOreToScript(ShapedOreRecipe recipe, boolean debug)
    {
        List<String> ret = new ArrayList<>();
        StringBuilder out = new StringBuilder();

        //inputs
        List<String[]> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients())
        {
            inputs.add(ingredientToScripts(ingredient));
        }

        //we have to go through each ingredient, and build a recipe for all possibilities
        List<String[]> permutations = new ArrayList<>();
        generatePermutations(inputs, permutations, 0, null);

        for (String[] permutation : permutations)
        {
            //debug
            if (debug)
            {
                out.append("recipes.shaped : ");
            }

            //recipe name
            out.append("\"").append(recipe.getRegistryName()).append("\"");
            out.append(", ");

            //output
            out.append(stackToScript(recipe.getRecipeOutput()));
            out.append(", ");

            int recipeWidth = recipe.getRecipeWidth();
            int recipeHeight = recipe.getRecipeHeight();
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (i < recipeHeight && j < recipeWidth) out.append(permutation[(i * recipe.getRecipeWidth()) + j]);
                    else out.append("null");

                    if (j < 2) out.append(", ");
                }
                if (i < 2) out.append(", ");
            }
            out.append(";");

            ret.add(out.toString());
            out = new StringBuilder();
        }

        return ret;
    }

    /**
     * Parses a shapeless recipe into all its related Tweaked recipes
     *
     * @param recipe The recipe
     * @return A list of all related Tweaked recipes
     */
    private static List<String> shapelessToScript(ShapelessRecipes recipe, boolean debug)
    {
        List<String> ret = new ArrayList<>();
        StringBuilder out = new StringBuilder();

        //inputs
        List<String[]> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients())
        {
            inputs.add(ingredientToScripts(ingredient));
        }

        //we have to go through each ingredient, and build a recipe for all possibilities
        List<String[]> permutations = new ArrayList<>();
        generatePermutations(inputs, permutations, 0, null);

        for (String[] permutation : permutations)
        {
            //debug
            if (debug)
            {
                out.append("recipes.shapeless : ");
            }

            //recipe name
            out.append("\"").append(recipe.getRegistryName()).append("\"");
            out.append(", ");

            //output
            out.append(stackToScript(recipe.getRecipeOutput()));
            out.append(", ");

            for (int i = 0; i < permutation.length; i++)
            {
                out.append(permutation[i]);
                if ((i + 1) != permutation.length) out.append(", ");
            }
            out.append(";");

            ret.add(out.toString());
            out = new StringBuilder();
        }

        return ret;
    }

    /**
     * Parses a shapeless ore recipe into all its related Tweaked recipes
     *
     * @param recipe The recipe
     * @return A list of all related Tweaked recipes
     */
    private static List<String> shapelessOreToScript(ShapelessOreRecipe recipe, boolean debug)
    {
        List<String> ret = new ArrayList<>();
        StringBuilder out = new StringBuilder();

        //inputs
        List<String[]> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients())
        {
            inputs.add(ingredientToScripts(ingredient));
        }

        //we have to go through each ingredient, and build a recipe for all possibilities
        List<String[]> permutations = new ArrayList<>();
        generatePermutations(inputs, permutations, 0, null);

        for (String[] permutation : permutations)
        {
            //debug
            if (debug)
            {
                out.append("recipes.shapeless : ");
            }

            //recipe name
            out.append("\"").append(recipe.getRegistryName()).append("\"");
            out.append(", ");

            //output
            out.append(stackToScript(recipe.getRecipeOutput()));
            out.append(", ");

            for (int i = 0; i < permutation.length; i++)
            {
                out.append(permutation[i]);
                if ((i + 1) != permutation.length) out.append(", ");
            }
            out.append(";");

            ret.add(out.toString());
            out = new StringBuilder();
        }

        return ret;
    }

    /**
     * Special function used to generate a list of all possible recipe input permutations when given the inputs in individual arrays
     *
     * @param inputs  The inputs, each one being an array of valid items
     * @param result  The outputs, each one being an array of a valid permutation
     * @param depth   How deep we have scanned (start at 0)
     * @param current The current outputs (start as null)
     */
    private static void generatePermutations(List<String[]> inputs, List<String[]> result, int depth, String[] current)
    {
        if (current == null) current = new String[inputs.size()];

        if (depth == inputs.size())
        {
            result.add(Arrays.copyOf(current, inputs.size()));
            return;
        }

        for (int i = 0; i < inputs.get(depth).length; ++i)
        {
            current[depth] = inputs.get(depth)[i];
            generatePermutations(inputs, result, depth + 1, current);
        }
    }

    public static String fluidToScript(FluidStack stackIn)
    {
        StringBuilder sb = new StringBuilder();

        FluidStack stack = stackIn.copy();

        //main item
        sb.append("fluid(").append(stack.getFluid().getName()).append(")");

        //count
        if (stack.amount != 1000)
        {
            sb.append(".count(").append(stack.amount).append(")");
        }

        //get nbt data
        int nbt = stack.tag == null ? 0 : stack.tag.getSize();
        if (nbt > 0)
        {
            sb.append(".nbt(").append(stack.tag.toString()).append(")");
        }

        return sb.toString();
    }
}
