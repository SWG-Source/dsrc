package script.npc.pet_deed;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.beast_lib;
import script.library.bio_engineer;
import script.library.create;
import script.library.hue;
import script.library.incubator;
import script.library.pet_lib;
import script.library.sui;
import script.library.utils;

public class pet_deed extends script.base_script
{
    public pet_deed()
    {
    }
    public static final String MENU_FILE = "pet/pet_menu";
    public static final string_id SID_CONVERT_PET_ITEM_TO_DNA = new string_id("incubator", "convert_pet_item_to_dna");
    public static final string_id SID_CONVERT_PROMPT = new string_id("incubator", "convert_pet_item_prompt");
    public static final string_id SID_CONVERT_TITLE = new string_id("incubator", "convert_pet_item_title");
    public static final string_id SID_REPORT_PET_CONVERSION_FAIL = new string_id("incubator", "report_pet_conversion_fail");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        float scaleAdjust = 1;
        if (hasObjVar(self, "creature_attribs.scale"))
        {
            scaleAdjust = getFloatObjVar(self, "creature_attribs.scale");
        }
        float baseScale = getScale(self);
        float realScale = baseScale * scaleAdjust;
        setScale(self, realScale);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithinAPlayer(self))
        {
            if (beast_lib.isBeastMaster(player))
            {
                int management_root = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_CONVERT_PET_ITEM_TO_DNA);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1 && utils.isNestedWithinAPlayer(self))
        {
            if (beast_lib.isBeastMaster(player))
            {
                int pid = sui.msgbox(self, player, "@" + SID_CONVERT_PROMPT, sui.YES_NO, "@" + SID_CONVERT_TITLE, "handlerSuiConvertPetItemToDna");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerSuiConvertPetItemToDna(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        obj_id petItem = self;
        if (beast_lib.isBeastMaster(player))
        {
            if (incubator.convertDeedIntoPetItem(player, self))
            {
                if (incubator.convertPetItemToDna(player, petItem))
                {
                    CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was converted into a dna object, for player " + getFirstName(player) + "(" + player + "). PetItem(" + petItem + ") will now be destroyed.");
                    destroyObject(petItem);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was NOT converted into a dna object, for player " + getFirstName(player) + "(" + player + "). PetItem(" + petItem + ") will NOT be destroyed.");
                    sendSystemMessage(player, SID_REPORT_PET_CONVERSION_FAIL);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
