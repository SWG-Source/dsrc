package script.systems.missions.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.structure;
import script.library.factions;
import script.library.utils;
import script.library.loot;
import script.library.sui;
import script.library.slicing;
import script.library.prose;
import script.library.xp;

public class mission_terminal extends script.base_script
{
    public mission_terminal()
    {
    }
    public static final string_id SID_MNU_REDEEM = new string_id("sui", "mnu_redeem");
    public static final string_id SID_REDEEM_PROMPT = new string_id("sui", "redeem_data_item_prompt");
    public static final string_id SID_REDEEM_TITLE = new string_id("sui", "redeem_data_item_title");
    public static final string_id SID_SLICE = new string_id("slicing/slicing", "slice");
    public static final string_id SID_FAIL_SLICE = new string_id("slicing/slicing", "terminal_fail");
    public static final string_id SID_SUCCESS_SLICE = new string_id("slicing/slicing", "terminal_success");
    public static final string_id SID_NOT_YET = new string_id("slicing/slicing", "not_yet");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, structure.VAR_TERMINAL_HEADING))
        {
            setYaw(self, getFloatObjVar(self, structure.VAR_TERMINAL_HEADING));
        }
        removeObjVar(self, "slice_start");
        removeObjVar(self, "sliced_by");
        attachScript(self, "planet_map.map_loc_attach");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = -1;
        if (hasSkill(player, "class_smuggler_phase1_novice"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_SLICE);
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.MISSION_TERMINAL_LIST);
        if (mid == null)
        {
            mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid == null && (!isDead(player) && !isIncapacitated(player)))
            {
                mnu = mi.addRootMenu(menu_info_types.MISSION_TERMINAL_LIST, new string_id("", ""));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            if (!hasSkill(player, "class_smuggler_phase1_novice"))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(player, "slicing.terminal_time"))
            {
                int lastHackTime = utils.getIntScriptVar(player, "slicing.terminal_time");
                int curTime = getGameTime();
                if (curTime < lastHackTime + 120)
                {
                    int timeDiff = (lastHackTime + 120) - curTime;
                    prose_package pp = prose.getPackage(SID_NOT_YET, timeDiff);
                    sendSystemMessageProse(player, pp);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    utils.removeScriptVar(player, "slicing.terminal_time");
                }
            }
            slicing.startSlicing(player, self, "finishSlicing", "terminal");
        }
        return SCRIPT_CONTINUE;
    }
    public int finishSlicing(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int success = params.getInt("success");
        obj_id player = params.getObjId("player");
        if (success == 1)
        {
            sendSystemMessage(player, SID_SUCCESS_SLICE);
            utils.setScriptVar(player, "slicing.terminal", self);
            utils.setScriptVar(player, "slicing.terminal_bonus", 1.5f);
        }
        else 
        {
            sendSystemMessage(player, SID_FAIL_SLICE);
        }
        utils.setScriptVar(player, "slicing.terminal_time", getGameTime());
        return SCRIPT_CONTINUE;
    }
}
