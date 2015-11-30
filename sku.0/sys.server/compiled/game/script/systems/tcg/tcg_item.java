package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.tcg;

public class tcg_item extends script.base_script
{
    public tcg_item()
    {
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (isIdValid(utils.getContainingPlayer(sourceContainer)))
        {
            sourceContainer = utils.getContainingPlayer(sourceContainer);
        }
        if (isIdValid(utils.getContainingPlayer(destContainer)))
        {
            destContainer = utils.getContainingPlayer(destContainer);
        }
        String sourceName = "None";
        String destName = "None";
        String transfererName = "None";
        if (isIdValid(sourceContainer))
        {
            sourceName = getName(sourceContainer);
        }
        if (isIdValid(destContainer))
        {
            destName = getName(destContainer);
        }
        if (isIdValid(transferer))
        {
            transfererName = getName(transferer);
        }
        CustomerServiceLog("tcg_transfer", ",Card ID: " + self + ",Card Name: " + getName(self) + ",Source Container: " + sourceContainer + ",Source Name: " + sourceName + ",Destination Container: " + destContainer + ",Destination Name: " + destName + ",Transferer: " + transferer + ",Transferer Name: " + transfererName);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id containingPlayer = utils.getContainingPlayer(self);
        if (isIdValid(containingPlayer) && containingPlayer == player)
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid != null)
            {
                mid.setServerNotify(true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(player) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id whoContains = utils.getContainingPlayer(self);
        if (whoContains != player)
        {
            sendSystemMessage(player, new string_id("spam", "not_in_inventory_generic"));
            return SCRIPT_CONTINUE;
        }
        int featureId = getIntObjVar(self, "featureID");
        if (featureId <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            adjustSwgTcgAccountFeatureId(player, self, featureId, 1);
            if (hasObjVar(player, "qa_tcg"))
            {
                debugSpeakMsg(player, "Item used.");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
