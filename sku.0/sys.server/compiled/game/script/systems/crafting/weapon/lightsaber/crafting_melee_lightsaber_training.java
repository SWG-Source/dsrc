package script.systems.crafting.weapon.lightsaber;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_melee_lightsaber_training extends script.systems.crafting.weapon.crafting_base_lightsaber
{
    public crafting_melee_lightsaber_training()
    {
    }
    public static final String VERSION = "v0.00.00";
    public static final String[] REQUIRED_SKILLS = 
    {
        "class_forcesensitive_phase1_novice"
    };
    public static final String[] ASSEMBLY_SKILL_MODS = 
    {
        "jedi_saber_assembly"
    };
    public static final String[] EXPERIMENT_SKILL_MODS = 
    {
        "jedi_saber_experimentation"
    };
    public static final String[] APPEARANCES = 
    {
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s1_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s2_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s3_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s4_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s5_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s6_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s7_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s8_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s9_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s10_training.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_s11_training.iff"
    };
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("minDamage", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_CONDUCTIVITY, 1)
        }),
        new resource_weight("maxDamage", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_CONDUCTIVITY, 1)
        })
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES;
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
    public String[] getAppearances(obj_id player, draft_schematic.slot[] slots) throws InterruptedException
    {
        return APPEARANCES;
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
