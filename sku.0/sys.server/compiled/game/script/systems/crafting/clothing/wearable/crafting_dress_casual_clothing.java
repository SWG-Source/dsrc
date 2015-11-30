package script.systems.crafting.clothing.wearable;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_dress_casual_clothing extends script.systems.crafting.clothing.crafting_base_clothing
{
    public crafting_dress_casual_clothing()
    {
    }
    public static final String VERSION = "v0.00.00";
    public static final String[] REQUIRED_SKILLS = 
    {
        "crafting_tailor_novice"
    };
    public static final String[] ASSEMBLY_SKILL_MODS = 
    {
        "clothing_assembly"
    };
    public static final String[] EXPERIMENT_SKILL_MODS = 
    {
        "clothing_experimentation",
        "clothing_complexity"
    };
    public static final String[] CUSTOMIZATION_SKILL_MODS = 
    {
        "clothing_customization"
    };
    public static final String[] APPEARANCES = 
    {
        "object/tangible/wearables/dress/shared_dress_s05.iff",
        "object/tangible/wearables/dress/shared_dress_s06.iff",
        "object/tangible/wearables/dress/shared_dress_s07.iff",
        "object/tangible/wearables/dress/shared_dress_s09.iff",
        "object/tangible/wearables/dress/shared_dress_s10.iff",
        "object/tangible/wearables/dress/shared_dress_s12.iff",
        "object/tangible/wearables/dress/shared_dress_s26.iff",
        "object/tangible/wearables/dress/shared_dress_s27.iff",
        "object/tangible/wearables/dress/shared_dress_s29.iff",
        "object/tangible/wearables/dress/shared_dress_s30.iff"
    };
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("hitPoints", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_CONDUCTIVITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_COLD_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_HEAT_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        })
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("hitPoints", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_CONDUCTIVITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_COLD_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 4),
            new resource_weight.weight(craftinglib.RESOURCE_HEAT_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
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
    public String[] getCustomizationSkillMods() throws InterruptedException
    {
        return CUSTOMIZATION_SKILL_MODS;
    }
    public String[] getAppearances(obj_id player, draft_schematic.slot[] slots) throws InterruptedException
    {
        return APPEARANCES;
    }
    public draft_schematic.custom[] getCustomizations(obj_id player, draft_schematic.custom[] customizations) throws InterruptedException
    {
        if (customizations == null || customizations.length == 0)
        {
            return null;
        }
        String[] skills = getCustomizationSkillMods();
        float playerCustomizationMod = 0;
        if (skills != null)
        {
            int[] mods = getEnhancedSkillStatisticModifiers(player, skills);
            for (int i = 0; i < mods.length; ++i)
            {
                playerCustomizationMod += mods[i];
            }
        }
        if (playerCustomizationMod > 255)
        {
            playerCustomizationMod = 255;
        }
        for (int i = 0; i < customizations.length; ++i)
        {
            customizations[i].maxValue = (int)(playerCustomizationMod);
        }
        if (customizations.length > 0 && playerCustomizationMod < 101)
        {
            for (int i = 1; i < customizations.length; ++i)
            {
                customizations[i] = null;
            }
        }
        else if (customizations.length > 0 && playerCustomizationMod < 201)
        {
            for (int i = 2; i < customizations.length; ++i)
            {
                customizations[i] = null;
            }
        }
        return customizations;
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
