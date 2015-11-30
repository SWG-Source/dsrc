package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class troopertest extends script.base_script
{
    public troopertest()
    {
    }
    public obj_id makeStormtrooper(obj_id player, location here) throws InterruptedException
    {
        String gender = "male";
        String species = "object/creature/player/human_";
        String name = "stormtrooper";
        species = species + gender + ".iff";
        obj_id npc = createObject(species, here);
        obj_id clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_boots.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make boots ");
        }
        clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_bicep_l.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make 1 ");
        }
        clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_bicep_r.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make 2 ");
        }
        clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_bracer_l.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make 3 ");
        }
        clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_bracer_r.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make 4 ");
        }
        clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_chest_plate.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make 5 ");
        }
        clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_gloves.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make 6 ");
        }
        clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_helmet.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make 7 ");
        }
        clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_leggings.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make 8 ");
        }
        clothes1 = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_utility_belt.iff", npc, "");
        if (clothes1 == null)
        {
            debugConsoleMsg(player, "I wasn't able to make 9 ");
        }
        if (false)
        {
            String clothes_table = "datatables/npc_customization/stormtrooper_clothes.iff";
            int columns = dataTableGetNumColumns(clothes_table);
            int intX = 1;
            while (intX <= columns)
            {
                debugConsoleMsg(player, "This is some clothes");
                int clothesRows = dataTableGetNumRows(clothes_table);
                int randrow = rand(1, clothesRows);
                String clothes = dataTableGetString(clothes_table, randrow, intX - 1);
                debugConsoleMsg(player, "Creating " + clothes);
                obj_id newClothes = createObject(clothes, npc, "");
                if (newClothes == null)
                {
                    debugConsoleMsg(player, "I wasn't able to make " + clothes);
                }
                intX = intX + 1;
            }
        }
        return npc;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("test"))
        {
            location l = getLocation(self);
            obj_id test = makeStormtrooper(self, l);
        }
        return SCRIPT_CONTINUE;
    }
}
