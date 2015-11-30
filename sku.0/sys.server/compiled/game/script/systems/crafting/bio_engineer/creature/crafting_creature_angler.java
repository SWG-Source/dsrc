package script.systems.crafting.bio_engineer.creature;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.bio_engineer;

public class crafting_creature_angler extends script.systems.crafting.bio_engineer.crafting_base_creature
{
    public crafting_creature_angler()
    {
    }
    public static final String VERSION = "v0.00.00";
    public static final String[] REQUIRED_SKILLS = 
    {
        "outdoors_bio_engineer_novice"
    };
    public static final String[] ASSEMBLY_SKILL_MODS = 
    {
        "medicine_assembly"
    };
    public static final String[] EXPERIMENT_SKILL_MODS = 
    {
        "medicine_experimentation",
        "medicine_complexity"
    };
    public static final String[] CUSTOMIZATION_SKILL_MODS = 
    {
        "bio_engineer_customization"
    };
    public static final String CREATURE_NAME = "bio_engineered_angler";
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("hitpoints", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
        })
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("hitpoints", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
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
    public draft_schematic.custom[] getCustomizations(obj_id player, draft_schematic.custom[] customizations) throws InterruptedException
    {
        if (customizations == null || customizations.length == 0)
        {
            return null;
        }
        float playerCustomizationMod = 255;
        for (int i = 0; i < customizations.length; ++i)
        {
            customizations[i].maxValue = (int)(playerCustomizationMod);
        }
        return customizations;
    }
    public String getCreatureName() throws InterruptedException
    {
        return CREATURE_NAME;
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
