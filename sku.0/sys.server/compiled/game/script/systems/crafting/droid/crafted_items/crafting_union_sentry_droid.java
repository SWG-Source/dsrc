package script.systems.crafting.droid.crafted_items;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_union_sentry_droid extends script.systems.crafting.droid.crafting_base_droid
{
    public crafting_union_sentry_droid()
    {
    }
    public static final String VERSION = "v0.00.00";
    public static final String[] REQUIRED_SKILLS = 
    {
        "crafting_droidengineer_novice"
    };
    public static final String[] ASSEMBLY_SKILL_MODS = 
    {
        "droid_assembly"
    };
    public static final String[] EXPERIMENT_SKILL_MODS = 
    {
        "droid_experimentation"
    };
    public static final String[] CUSTOMIZATION_SKILL_MODS = 
    {
        "droid_customization"
    };
    public static final String CREATURE_NAME = "union_sentry_droid_crafted";
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("armorEffectiveness", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("armor_toughness", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("mechanism_quality", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("power_level", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
        }),
        new resource_weight("storage_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("data_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("personality_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("medical_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("crafting_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("repair_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("decayRate", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("cmbt_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("armor_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        })
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("armorEffectiveness", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("armor_toughness", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("mechanism_quality", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("power_level", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
        }),
        new resource_weight("storage_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("data_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("personality_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("medical_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("crafting_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("repair_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("decayRate", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("cmbt_module", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("armor_module", new resource_weight.weight[]
        {
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
    public String getCreatureName() throws InterruptedException
    {
        return CREATURE_NAME;
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
            int[] mods = getSkillStatisticModifiers(player, skills);
            for (int i = 0; i < mods.length; ++i)
            {
                playerCustomizationMod += mods[i];
            }
        }
        if (playerCustomizationMod < 32)
        {
            playerCustomizationMod = 32;
        }
        if (playerCustomizationMod > 64)
        {
            playerCustomizationMod = 64;
        }
        for (int i = 0; i < customizations.length; ++i)
        {
            customizations[i].maxValue = (int)(playerCustomizationMod);
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
