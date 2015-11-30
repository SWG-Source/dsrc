package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;

public class evidence_terminal extends script.base_script
{
    public evidence_terminal()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String PASSKEYCODE = "object/intangible/data_item/warren_evidence_0";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasEncryptionKey(self, player))
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "got_evidence"));
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(SYSTEM_MESSAGES, "get_evidence"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasEncryptionKey(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 10.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_CONTINUE;
        }
        sui.msgbox(player, new string_id(SYSTEM_MESSAGES, "download_complete"));
        obj_id playerInv = utils.getPlayerDatapad(player);
        int evidencePiece = getIntObjVar(self, "warren.evidence");
        if (evidencePiece == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(playerInv))
        {
            obj_id passKey = createObject(PASSKEYCODE + evidencePiece + ".iff", playerInv, "");
            if (isIdValid(passKey))
            {
                setObjVar(passKey, "warren.evidence", evidencePiece);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasEncryptionKey(obj_id terminal, obj_id player) throws InterruptedException
    {
        int evidencePiece = getIntObjVar(terminal, "warren.evidence");
        if (evidencePiece == 0)
        {
            debugSpeakMsg(terminal, "I should have objvar warren.evidence set but I don't");
            return true;
        }
        obj_id playerInv = utils.getPlayerDatapad(player);
        if (playerInv == null)
        {
            return false;
        }
        obj_id[] contents = getContents(playerInv);
        if (contents == null)
        {
            return false;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "warren.evidence"))
            {
                if (getIntObjVar(contents[i], "warren.evidence") == evidencePiece)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
