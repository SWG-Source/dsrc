package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class qadroid extends script.base_script
{
    public qadroid()
    {
    }
    public static final String[] DROID_TEMPLATE_ARRAY = 
    {
        "object/tangible/deed/pet_deed/deed_mse_advanced_basic.iff"
    };
    public static final String[] STRING_ARRAY = 
    {
        "ai.diction",
        "creature_attribs.type"
    };
    public static final String[] STRING_VALUES = 
    {
        "droid_default",
        "mouse_droid_crafted"
    };
    public static final String[] ATTRIB_ARRAY = 
    {
        "combatModule",
        "crafting.creator.xpType",
        "crafting.repair_type",
        "creature_attribs.defenseValue",
        "creature_attribs.general_protection",
        "creature_attribs.level",
        "creature_attribs.maxConstitution",
        "creature_attribs.maxDamage",
        "creature_attribs.maxHealth",
        "creature_attribs.minDamage",
        "creature_attribs.toHitChance",
        "mechanism_quality",
        "module_data.bomb_level",
        "module_data.bomb_level_bonus",
        "pet.nonCombatDroid"
    };
    public static final int[] INT_VALUES = 
    {
        0,
        43,
        32,
        80,
        6000,
        30,
        0,
        205,
        4000,
        135,
        80,
        82,
        14,
        20,
        1
    };
    public static final String[] ATTRIB_DOUBLE_ARRAY = 
    {
        "crafting_components.decayRate",
        "creature_attribs.aggroBonus",
        "creature_attribs.critChance",
        "creature_attribs.critSave",
        "creature_attribs.scale",
        "creature_attribs.stateResist"
    };
    public static final String[] DOUBLE_VALUES = 
    {
        "46.740349",
        "0.000000",
        "0.000000",
        "0.000000",
        "1.000000",
        "0.000000"
    };
    public static final String SOURCE_SCHEMATIC = "crafting.source_schematic";
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id player = self;
        if (isGod(player))
        {
            if ((toLower(text)).equals("qadroid"))
            {
                obj_id inventory = utils.getInventoryContainer(player);
                obj_id[] invItems = getContents(inventory);
                if (invItems.length > 89)
                {
                    sendSystemMessageTestingOnly(player, "You do not have enough space for this droid.");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    for (int i = 0; i < DROID_TEMPLATE_ARRAY.length; i++)
                    {
                        obj_id newObj = createObject(DROID_TEMPLATE_ARRAY[i], inventory, "");
                        int SourceSchematic = 0;
                        attachScript(newObj, "npc.pet_deed.droid_deed");
                        boolean myBool = utils.setScriptVar(newObj, "crafting.creator.xp", 90);
                        sendSystemMessageTestingOnly(player, toString(newObj));
                        for (int j = 0; j < STRING_ARRAY.length; j++)
                        {
                            setObjVar(newObj, STRING_ARRAY[j], STRING_VALUES[j]);
                        }
                        for (int k = 0; k < ATTRIB_ARRAY.length; k++)
                        {
                            setObjVar(newObj, ATTRIB_ARRAY[k], INT_VALUES[k]);
                        }
                        for (int m = 0; m < ATTRIB_DOUBLE_ARRAY.length; m++)
                        {
                            setObjVar(newObj, ATTRIB_DOUBLE_ARRAY[m], DOUBLE_VALUES[m]);
                        }
                        setObjVar(newObj, SOURCE_SCHEMATIC, SourceSchematic);
                    }
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
