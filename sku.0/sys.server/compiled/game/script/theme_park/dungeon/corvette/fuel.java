package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.pet_lib;

public class fuel extends script.base_script
{
    public fuel()
    {
    }
    public static final String MSGS = "dungeon/corvette";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        string_id fuel_name = new string_id(MSGS, "fuel_name");
        String name = getString(fuel_name);
        setName(self, name);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (hasObjVar(player, "corl_corvette.imperial_destroy_mission") || hasObjVar(player, "corl_corvette.rebel_destroy_mission") || hasObjVar(player, "corl_corvette.neutral_destroy_mission"))
            {
                showEngineSettings(self, player);
            }
            else 
            {
                string_id wrongMission = new string_id(MSGS, "does_nothing");
                sendSystemMessage(player, wrongMission);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int showEngineSettings(obj_id pcd, obj_id player) throws InterruptedException
    {
        string_id textMsg = new string_id(MSGS, "fuel_settings");
        String TEXT = getString(textMsg);
        string_id titleMsg = new string_id(MSGS, "fuel_title");
        String TITLE = getString(titleMsg);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, pcd, player, "CorvetteEngine");
        setSUIProperty(pid, "", "Size", "250,175");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, TEXT);
        sui.msgboxButtonSetup(pid, sui.YES_NO_CANCEL);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, sui.PROP_TEXT, "30");
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "50");
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "80");
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "OnPress", "RevertWasPressed=1\r\nparent.btnOk.press=t");
        subscribeToSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "RevertWasPressed");
        sui.showSUIPage(pid);
        return pid;
    }
    public int CorvetteEngine(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        int hypr = getIntObjVar(top, "hyperdrive");
        int engine = getIntObjVar(top, "engine");
        switch (bp)
        {
            case sui.BP_OK:
            string_id engineIs = new string_id(MSGS, "engine_is");
            string_id hyperIs = new string_id(MSGS, "hyperdrive_is");
            string_id result = new string_id(MSGS, "engine_result");
            if (revert != null && !revert.equals(""))
            {
                string_id fuel30 = new string_id(MSGS, "fuel_30");
                sendSystemMessage(player, fuel30);
                setObjVar(top, "fuel", 30);
                if (engine == 0)
                {
                    sendSystemMessage(player, hyperIs);
                    sendSystemMessage(player, "" + hypr, null);
                    sendSystemMessage(player, result);
                }
                else 
                {
                    sendSystemMessage(player, hyperIs);
                    sendSystemMessage(player, "" + hypr, null);
                    sendSystemMessage(player, engineIs);
                    sendSystemMessage(player, "" + engine, null);
                }
                checkForDestroy(player, top, 30);
            }
            else 
            {
                string_id fuel50 = new string_id(MSGS, "fuel_50");
                sendSystemMessage(player, fuel50);
                setObjVar(top, "fuel", 50);
                if (engine == 0)
                {
                    sendSystemMessage(player, hyperIs);
                    sendSystemMessage(player, "" + hypr, null);
                    sendSystemMessage(player, result);
                }
                else 
                {
                    sendSystemMessage(player, hyperIs);
                    sendSystemMessage(player, "" + hypr, null);
                    sendSystemMessage(player, engineIs);
                    sendSystemMessage(player, "" + engine, null);
                }
                checkForDestroy(player, top, 50);
            }
            return SCRIPT_CONTINUE;
            case sui.BP_CANCEL:
            string_id fuel80 = new string_id(MSGS, "fuel_80");
            sendSystemMessage(player, fuel80);
            setObjVar(top, "fuel", 80);
            string_id engine_is = new string_id(MSGS, "engine_is");
            string_id hyper_is = new string_id(MSGS, "hyperdrive_is");
            string_id result_is = new string_id(MSGS, "engine_result");
            if (engine == 0)
            {
                sendSystemMessage(player, hyper_is);
                sendSystemMessage(player, "" + hypr, null);
                sendSystemMessage(player, result_is);
            }
            else 
            {
                sendSystemMessage(player, hyper_is);
                sendSystemMessage(player, "" + hypr, null);
                sendSystemMessage(player, engine_is);
                sendSystemMessage(player, "" + engine, null);
            }
            checkForDestroy(player, top, 80);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void checkForDestroy(obj_id player, obj_id top, int fuel) throws InterruptedException
    {
        int hypr = getIntObjVar(top, "hyperdrive");
        if (fuel > hypr)
        {
            string_id reset = new string_id(MSGS, "engine_reset");
            sendSystemMessage(player, reset);
        }
        return;
    }
}
