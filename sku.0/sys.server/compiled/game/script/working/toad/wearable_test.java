package script.working.toad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.hue;

public class wearable_test extends script.base_script
{
    public wearable_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (speaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        if (text.equals("test"))
        {
            here.x = here.x + 2;
            obj_id npc1 = createObject("object/mobile/human_male.iff", here);
            here.x = here.x + 2;
            obj_id npc2 = createObject("object/mobile/human_male.iff", here);
            here.x = here.x + 2;
            obj_id npc3 = createObject("object/mobile/human_female.iff", here);
            here.x = here.x + 2;
            obj_id npc4 = createObject("object/mobile/human_female.iff", here);
            dressNpc(npc1, "datatables/npc_customization/clothing/all_shirts_pants.iff");
            dressNpc(npc2, "datatables/npc_customization/clothing/all_shirts_pants.iff");
            dressNpc(npc3, "datatables/npc_customization/clothing/all_shirts_pants.iff");
            dressNpc(npc4, "datatables/npc_customization/clothing/all_shirts_pants.iff");
        }
        if (text.equals("jacket"))
        {
            here.x = here.x + 2;
            obj_id npc1 = createObject("object/mobile/human_male.iff", here);
            here.x = here.x + 2;
            obj_id npc2 = createObject("object/mobile/human_male.iff", here);
            here.x = here.x + 2;
            obj_id npc3 = createObject("object/mobile/human_female.iff", here);
            here.x = here.x + 2;
            obj_id npc4 = createObject("object/mobile/human_female.iff", here);
            dressNpc(npc1, "datatables/npc_customization/clothing/shirt_pants_jacket.iff");
            dressNpc(npc2, "datatables/npc_customization/clothing/shirt_pants_jacket.iff");
            dressNpc(npc3, "datatables/npc_customization/clothing/shirt_pants_jacket.iff");
            dressNpc(npc4, "datatables/npc_customization/clothing/shirt_pants_jacket.iff");
        }
        return SCRIPT_CONTINUE;
    }
    public void dressNpc(obj_id npc, String clothesTable) throws InterruptedException
    {
        clothesTable = clothesTable;
        int columns = dataTableGetNumColumns(clothesTable);
        if (columns == 0)
        {
            clothesTable = "datatables/npc_customization/townperson_male_clothes.iff";
            columns = dataTableGetNumColumns(clothesTable);
        }
        int intX = 1;
        while (intX <= columns)
        {
            String[] clothesList = dataTableGetStringColumnNoDefaults(clothesTable, intX - 1);
            int clothesNum = rand(0, clothesList.length - 1);
            String clothes = clothesList[clothesNum];
            int columnNum = intX - 1;
            if (clothes == null)
            {
                debugServerConsoleMsg(npc, "LIBRARY.CREATE DRESSNPC ERROR: row was " + clothesNum + " and column was " + columnNum);
            }
            else if (clothes.equals(""))
            {
                debugServerConsoleMsg(npc, "LIBRARY.CREATE DRESSNPC ERROR: row was " + clothesNum + " and column was " + columnNum);
            }
            else 
            {
                obj_id newClothes = createObject(clothes, npc, "");
                if (newClothes == null)
                {
                    debugServerConsoleMsg(npc, "LIBRARY.CREATE DRESSNPC ERROR: unable to create " + clothes + " for npc " + getName(npc) + " objid: " + npc);
                }
            }
            intX = intX + 1;
        }
        hue.randomizeObject(npc);
    }
}
