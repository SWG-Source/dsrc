package script.systems.crafting.bio_engineer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.debug;

public class crafting_bio_food_component extends script.systems.crafting.crafting_base
{
    public crafting_bio_food_component()
    {
    }
    public static final String VERSION = "v1.00.00";
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
    public static final String[] CUSTOMIZATION_SKILL_MODS = 
    {
        "bio_engineer_customization"
    };
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("add_filling", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("add_flavor", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("add_nutrition", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("add_quantity", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        })
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("add_filling", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 5),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 3)
        }),
        new resource_weight("add_flavor", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 5),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 3)
        }),
        new resource_weight("add_nutrition", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 5),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 3)
        }),
        new resource_weight("add_quantity", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 5),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 3)
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
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        float secret_bonus = 1.f;
        int filling = 0;
        int flavor = 0;
        int nutrition = 0;
        int quantity = 0;
        int faction = 0;
        obj_id self = getSelf();
        if (hasObjVar(self, "crafting_components.secrets"))
        {
            secret_bonus = getFloatObjVar(self, "crafting_components.secrets.secret_bonus");
            faction = getIntObjVar(self, "crafting_components.secrets.faction");
        }
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("add_filling"))
                {
                    filling = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("add_flavor"))
                {
                    flavor = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("add_nutrition"))
                {
                    nutrition = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("add_quantity"))
                {
                    quantity = (int)itemAttributes[i].currentValue;
                }
            }
        }
        if (faction != 0)
        {
            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_faction", faction);
        }
        if (filling != 0)
        {
            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_filling", (int)(filling * secret_bonus));
        }
        if (flavor != 0)
        {
            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_flavor", (int)(flavor * secret_bonus));
        }
        if (nutrition != 0)
        {
            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_nutrition", (int)(nutrition * secret_bonus));
        }
        if (quantity != 0)
        {
            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_quantity", (int)(quantity * secret_bonus));
        }
    }
}
