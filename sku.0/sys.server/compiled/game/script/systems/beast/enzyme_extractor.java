package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;

public class enzyme_extractor extends script.base_script
{
    public enzyme_extractor()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int options = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("beast", "extract_type_3_enzyme"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        obj_id enzyme = null;
        obj_id target = getIntendedTarget(player);
        if (item == menu_info_types.ITEM_USE)
        {
            int returnCode = beast_lib.getEnzymeExtractionReturnCode(player, target);
            if (returnCode > -1)
            {
                sendSystemMessage(player, beast_lib.ENZYME_EXTRACTION_ERRORS[returnCode]);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, new string_id("beast", "enzyme_extraction_success"));
            }
            float craftedPurity = getFloatObjVar(self, "crafting.enzyme_purity");
            float craftedMutagen = getFloatObjVar(self, "crafting.enzyme_mutagen");
            enzyme = beast_lib.generateTypeThreeEnzyme(player, getIntendedTarget(player), craftedPurity, craftedMutagen, beast_lib.getBeastTrait(player, getIntendedTarget(player)));
            if (isIdValid(enzyme))
            {
                int count = getCount(self);
                count--;
                if (count < 1)
                {
                    destroyObject(self);
                }
                else 
                {
                    setCount(self, count);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
