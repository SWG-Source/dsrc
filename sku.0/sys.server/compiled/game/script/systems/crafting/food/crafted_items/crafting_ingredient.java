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

public class crafting_ingredient extends script.systems.crafting.food.crafting_base_ingredient
{
    public crafting_ingredient()
    {
    }
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("hitPoints", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("health_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("health_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("str_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("str_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("con_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("con_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("action_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("action_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("quick_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("quick_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("stam_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("stam_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("mind_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("mind_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("focus_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("focus_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("will_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("will_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 1),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 1)
        }),
        new resource_weight("quantity", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
        }),
        new resource_weight("filling", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        })
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = 
    {
        new resource_weight("hitPoints", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
        }),
        new resource_weight("health_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 4)
        }),
        new resource_weight("health_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 4),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2)
        }),
        new resource_weight("str_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 4)
        }),
        new resource_weight("str_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 4),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2)
        }),
        new resource_weight("con_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 4)
        }),
        new resource_weight("con_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 4),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2)
        }),
        new resource_weight("action_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 4)
        }),
        new resource_weight("action_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 4),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2)
        }),
        new resource_weight("quick_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 4)
        }),
        new resource_weight("quick_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 4),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2)
        }),
        new resource_weight("stam_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 4)
        }),
        new resource_weight("stam_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 4),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2)
        }),
        new resource_weight("mind_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 4)
        }),
        new resource_weight("mind_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 4),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2)
        }),
        new resource_weight("focus_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 4)
        }),
        new resource_weight("focus_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 4),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2)
        }),
        new resource_weight("will_e", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 2),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 3),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 4)
        }),
        new resource_weight("will_dur", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_FLAVOR, 4),
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 2),
            new resource_weight.weight(craftinglib.RESOURCE_POTENTIAL_ENERGY, 2)
        }),
        new resource_weight("quantity", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1)
        }),
        new resource_weight("filling", new resource_weight.weight[]
        {
            new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
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
}
