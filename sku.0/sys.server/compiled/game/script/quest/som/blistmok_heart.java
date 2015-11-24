package script.quest.som;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;
import script.library.sui;
import script.library.mustafar;

public class blistmok_heart extends script.base_script
{
    public blistmok_heart()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id EXAMINE = new string_id(STF, "blistmok_heart_examine");
    public static final string_id DESTROY = new string_id(STF, "blistmok_heart_destroy");
    public static final string_id ALREADY = new string_id(STF, "blistmok_heart_already");
    public static final string_id UNABLE = new string_id(STF, "unable_to_examine");
    public static final string_id DECLINE = new string_id(STF, "quest_decline");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) != null)
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, EXAMINE);
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid == null)
            {
                return SCRIPT_CONTINUE;
            }
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (utils.getContainingPlayer(self) == null)
            {
                sendSystemMessage(player, UNABLE);
                return SCRIPT_CONTINUE;
            }
            if (!groundquests.isQuestActive(player, "som_blistmok_hunt_25"))
            {
                mustafar.activateQuestAcceptSUI(player, self);
            }
            else 
            {
                sendSystemMessage(player, ALREADY);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestOfferResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            groundquests.grantQuest(player, "som_blistmok_hunt_25");
            sendSystemMessage(player, DESTROY);
            destroyObject(self);
            break;
            case sui.BP_CANCEL:
            sendSystemMessage(player, DECLINE);
            break;
        }
        return SCRIPT_CONTINUE;
    }
}
