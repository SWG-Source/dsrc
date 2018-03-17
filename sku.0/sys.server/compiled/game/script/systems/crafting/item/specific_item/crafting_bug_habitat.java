package script.systems.crafting.item.specific_item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_bug_habitat extends script.systems.crafting.crafting_base
{
    public crafting_bug_habitat()
    {
    }
    public static final String VERSION = "v0.00.00";
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
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public int OnFinalizeSchematic(obj_id self, obj_id player, obj_id prototype, draft_schematic schematic) throws InterruptedException
    {
        draft_schematic.slot[] schslots = schematic.getSlots();
        int type = 0;
        for (int i = 0; i < schslots.length; i++)
        {
            if (schslots[i] != null)
            {
                for (int j = 0; j < schslots[i].ingredients.length; j++)
                {
                    if (hasObjVar(schslots[i].ingredients[j].ingredient, "bugsample"))
                    {
                        type = getIntObjVar(schslots[i].ingredients[j].ingredient, "bugsample");
                        setObjVar(prototype, "bugsample", type);
                    }
                }
            }
        }
        string_id nameid = getNameStringId(prototype);
        String name = localize(nameid);
        switch (type)
        {
            case 1:
            name = name + " (Bats)";
            break;
            case 2:
            name = name + " (Bees)";
            break;
            case 3:
            name = name + " (Butterflies)";
            break;
            case 4:
            name = name + " (Flies)";
            break;
            case 5:
            name = name + " (Glowzees)";
            break;
            case 6:
            name = name + " (Moths)";
            break;
        }
        setName(prototype, name);
        draft_schematic.attribute[] objectAttribs = schematic.getAttribs();
        dictionary craftingValuesDictionary = new dictionary();
        calcAndSetPrototypeProperties(prototype, objectAttribs, craftingValuesDictionary);
        setSchematicAttributes(self, objectAttribs);
        removeObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME);
        return SCRIPT_CONTINUE;
    }
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES = 
    {
    };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES = 
    {
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
