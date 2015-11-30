package script.systems.crafting.food.crafted_items;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_chef_ingredient extends script.systems.crafting.food.crafted_items.crafting_ingredient
{
    public crafting_chef_ingredient()
    {
    }
    public static final String[] REQUIRED_SKILLS = 
    {
        "crafting_chef_novice"
    };
    public static final String[] ASSEMBLY_SKILL_MODS = 
    {
        "food_assembly"
    };
    public static final String[] EXPERIMENT_SKILL_MODS = 
    {
        "food_experimentation"
    };
    public String[] getRequiredSkills() throws InterruptedException
    {
        return REQUIRED_SKILLS;
    }
    public String[] getAssemblySkillMods() throws InterruptedException
    {
        return ASSEMBLY_SKILL_MODS;
    }
    public String[] getExperimentSkillMods() throws InterruptedException
    {
        return EXPERIMENT_SKILL_MODS;
    }
}
