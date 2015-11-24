package script.systems.crafting.food;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.consumable;
import script.library.player_stomach;
import script.library.bio_engineer;
import script.library.debug;

public class crafting_food_additive extends script.systems.crafting.crafting_base
{
    public crafting_food_additive()
    {
    }
    public static final String VERSION = "v1.00.00";
    public static final String FOOD_DATA = "datatables/food/food_data.iff";
    public static final String[] REQUIRED_SKILLS = 
    {
        "crafting_artisan_novice"
    };
    public static final String[] ASSEMBLY_SKILL_MODS = 
    {
        "general_assembly"
    };
    public static final String[] EXPERIMENT_SKILL_MODS = 
    {
        "general_experimentation"
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
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("nutrition", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
        }),
        new resource_weight("flavor", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 1)
        }),
        new resource_weight("quantity", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 1)
        }),
        new resource_weight("filling", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
        })
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("nutrition", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
        }),
        new resource_weight("flavor", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 1)
        }),
        new resource_weight("quantity", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 1)
        }),
        new resource_weight("filling", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 3),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
        })
    };
    public resource_weight[] getResourceMaxResourceWeights() throws InterruptedException
    {
        return OBJ_MAX_ATTRIBUTE_RESOURCES;
    }
    public resource_weight[] getAssemblyResourceWeights() throws InterruptedException
    {
        return OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES;
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
            }
        }
        obj_id self = getSelf();
        if (hasObjVar(self, "crafting_components.bio_component"))
        {
            if (hasObjVar(self, "crafting_components.bio_component.add_faction"))
            {
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_faction", getIntObjVar(self, "crafting_components.bio_component.add_faction"));
            }
            if (hasObjVar(self, "crafting_components.bio_component.add_filling"))
            {
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_filling", getIntObjVar(self, "crafting_components.bio_component.add_filling"));
            }
            if (hasObjVar(self, "crafting_components.bio_component.add_flavor"))
            {
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_flavor", getIntObjVar(self, "crafting_components.bio_component.add_flavor"));
            }
            if (hasObjVar(self, "crafting_components.bio_component.add_nutrition"))
            {
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_nutrition", getIntObjVar(self, "crafting_components.bio_component.add_nutrition"));
            }
            if (hasObjVar(self, "crafting_components.bio_component.add_quantity"))
            {
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".add_quantity", getIntObjVar(self, "crafting_components.bio_component.add_quantity"));
            }
        }
    }
}
