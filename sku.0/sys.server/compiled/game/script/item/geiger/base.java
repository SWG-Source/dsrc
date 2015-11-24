package script.item.geiger;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.geiger;
import script.library.utils;
import script.library.sui;

public class base extends script.base_script
{
    public base()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenuOrServerNotify(menu_info_types.ITEM_USE, null);
        mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("quest/quest_journal/fs_quests_sad", "instructions_title"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("geiger", "OnObjectMenuSelect");
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id inv = utils.getDatapad(player);
            if (isIdValid(inv) && utils.isNestedWithin(self, inv))
            {
                LOG("geiger", "enqueing command");
                openWindow(player, self);
                return SCRIPT_CONTINUE;
            }
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            sui.msgbox(player, new string_id("quest/quest_journal/fs_quests_sad", "instructions_text"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getObjIdObjVar(self, geiger.OBJVAR_GEIGER_PLAYER);
        utils.removeObjVar(player, geiger.OBJVAR_GEIGER_OBJECT);
        return SCRIPT_CONTINUE;
    }
    public int updateGeiger(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("geiger", "geiger updateGeiger");
        if (!hasObjVar(self, geiger.OBJVAR_GEIGER_PLAYER))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdObjVar(self, geiger.OBJVAR_GEIGER_PLAYER);
        if (!hasObjVar(player, geiger.OBJVAR_GEIGER_PID))
        {
            return SCRIPT_CONTINUE;
        }
        int pid = utils.getIntObjVar(player, geiger.OBJVAR_GEIGER_PID);
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        int paramPid = params.getInt("pid");
        if (paramPid != pid)
        {
            return SCRIPT_CONTINUE;
        }
        geiger.updateGeiger(player, self);
        messageTo(self, "updateGeiger", params, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int geigerCallback(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("geiger", "geigerCallback");
        String widgetName = params.getString("eventWidgetName");
        obj_id player = utils.getObjIdObjVar(self, geiger.OBJVAR_GEIGER_PLAYER);
        int pid = utils.getIntObjVar(player, geiger.OBJVAR_GEIGER_PID);
        if (widgetName.equalsIgnoreCase("") || widgetName.equalsIgnoreCase("%buttonOK%"))
        {
            LOG("geiger", "geigerCallback removing pid");
            utils.removeObjVar(player, geiger.OBJVAR_GEIGER_PID);
            forceCloseSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void openWindow(obj_id player, obj_id geigerObj) throws InterruptedException
    {
        if (utils.hasObjVar(player, geiger.OBJVAR_GEIGER_PID))
        {
            forceCloseSUIPage(utils.getIntObjVar(player, geiger.OBJVAR_GEIGER_PID));
        }
        int pid = createSUIPage("/PDA.Questpopup", geigerObj, player, "geigerCallback");
        utils.setObjVar(player, geiger.OBJVAR_GEIGER_PID, pid);
        if (pid < 0)
        {
            return;
        }
        subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOK%", "geigerCallback");
        setSUIProperty(pid, "%viewer%", "SetObject", "" + geigerObj);
        float value = geiger.calculateGeigerNumber(player);
        setSUIProperty(pid, "%info%", "Text", "\n\nEnemy Threat Level:  Detecting...");
        setSUIProperty(pid, "%title%", "Text", "Radiation Sensor");
        setSUIAssociatedObject(pid, player);
        showSUIPage(pid);
        dictionary pidDict = new dictionary();
        pidDict.put("pid", pid);
        messageTo(geigerObj, "updateGeiger", pidDict, 3.0f, false);
    }
}
