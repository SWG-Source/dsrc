package script.systems.crafting.weapon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_melee_lightsaber_one_handed extends script.systems.crafting.weapon.crafting_base_lightsaber
{
    public crafting_melee_lightsaber_one_handed()
    {
    }
    public static final String VERSION = "v0.00.00";
    public static final String[] REQUIRED_SKILLS = 
    {
        "jedi_padawan_novice"
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
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s1.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s2.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s3.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s4.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s5.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s6.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s7.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s8.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s9.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s10.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s11.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s12.iff",
        "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s13.iff"
    };
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("minDamage", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
        }),
        new resource_weight("maxDamage", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
        }),
        new resource_weight("attackSpeed", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
        }),
        new resource_weight("woundChance", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
        }),
        new resource_weight("forceCost", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
        }),
        new resource_weight("attackCost", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
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
