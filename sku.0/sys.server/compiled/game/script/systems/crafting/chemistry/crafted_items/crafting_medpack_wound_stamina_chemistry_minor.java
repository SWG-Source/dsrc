package script.systems.crafting.chemistry.crafted_items;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_medpack_wound_stamina_chemistry_minor extends script.systems.crafting.chemistry.crafting_wound_stamina_chemical
{
    public crafting_medpack_wound_stamina_chemistry_minor()
    {
    }
    public static final String VERSION = "v0.00.00";
    public static final String[] REQUIRED_SKILLS = 
    {
        "science_medic_novice"
    };
    public static final String[] ASSEMBLY_SKILL_MODS = 
    {
        "medicine_assembly"
    };
    public static final String[] EXPERIMENT_SKILL_MODS = 
    {
        "basic_medic_experimentation",
        "medicine_complexity"
    };
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("power", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 4)
        }),
        new resource_weight("charges", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 4)
        }),
        new resource_weight("skillModMin", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 4)
        }),
        new resource_weight("hitPoints", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 2)
        })
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("power", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 4)
        }),
        new resource_weight("charges", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 4)
        }),
        new resource_weight("skillModMin", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 4)
        }),
        new resource_weight("hitPoints", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 2)
        })
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
    public resource_weight[] getResourceMaxResourceWeights() throws InterruptedException
    {
        return OBJ_MAX_ATTRIBUTE_RESOURCES;
    }
    public resource_weight[] getAssemblyResourceWeights() throws InterruptedException
    {
        return OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES;
    }
}
