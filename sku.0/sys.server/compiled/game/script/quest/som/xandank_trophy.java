package script.quest.som;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.create;
import script.library.trial;
import script.library.utils;

public class xandank_trophy extends script.base_script
{
    public xandank_trophy()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id TRACK = new string_id(STF, "xandank_trophy_track");
    public static final string_id TRACK_FOUND_ONE = new string_id(STF, "xandank_trophy_track_one");
    public static final string_id TRACK_FOUND_TWO = new string_id(STF, "xandank_trophy_track_two");
    public static final string_id TRACK_COMPLETE = new string_id(STF, "xandank_trophy_track_complete");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "som_xandank_trophey"))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, TRACK);
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
            if (hasObjVar(self, "xandank_trophy_one"))
            {
                if (groundquests.isTaskActive(player, "som_xandank_trophey", "xandank_trophy_two"))
                {
                    doAnimationAction(player, "search");
                    dictionary dict = new dictionary();
                    dict.put("target", player);
                    messageTo(self, "handleFindTrackOne", dict, 5f, false);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessage(player, TRACK_COMPLETE);
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasObjVar(self, "xandank_trophy_two"))
            {
                if (groundquests.isTaskActive(player, "som_xandank_trophey", "xandank_trophy_four"))
                {
                    doAnimationAction(player, "search");
                    dictionary dict = new dictionary();
                    dict.put("target", player);
                    messageTo(self, "handleFindTrackTwo", dict, 5f, false);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessage(player, TRACK_COMPLETE);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFindTrackOne(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("target");
        sendSystemMessage(player, TRACK_FOUND_ONE);
        groundquests.sendSignal(player, "xandank_trophy_signal_one");
        return SCRIPT_CONTINUE;
    }
    public int handleFindTrackTwo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("target");
        sendSystemMessage(player, TRACK_FOUND_TWO);
        groundquests.sendSignal(player, "xandank_trophy_signal_two");
        return SCRIPT_CONTINUE;
    }
}
