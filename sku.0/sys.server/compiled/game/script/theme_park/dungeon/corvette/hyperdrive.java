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

public class hyperdrive extends script.base_script
{
    public hyperdrive()
    {
    }
    public static final String MSGS = "dungeon/corvette";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        string_id hypr = new string_id(MSGS, "hyper_name");
        String name = getString(hypr);
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
        string_id textMsg = new string_id(MSGS, "hyperdrive_settings");
        String TEXT = getString(textMsg);
        string_id titleMsg = new string_id(MSGS, "hyperdrive_title");
        String TITLE = getString(titleMsg);
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, pcd, player, "CorvetteEngine");
        setSUIProperty(pid, "", "Size", "250,175");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, TEXT);
        sui.msgboxButtonSetup(pid, sui.YES_NO_CANCEL);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, sui.PROP_TEXT, "40");
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "20");
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "60");
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
        int fuel = getIntObjVar(top, "fuel");
        int engine = getIntObjVar(top, "engine");
        switch (bp)
        {
            case sui.BP_OK:
            string_id engineIs = new string_id(MSGS, "engine_is");
            string_id fuelIs = new string_id(MSGS, "fuel_is");
            string_id result = new string_id(MSGS, "engine_result");
            if (revert != null && !revert.equals(""))
            {
                string_id hypr40 = new string_id(MSGS, "hypr_40");
                sendSystemMessage(player, hypr40);
                setObjVar(top, "hyperdrive", 40);
                if (engine == 0)
                {
                    sendSystemMessage(player, fuelIs);
                    sendSystemMessage(player, "" + fuel, null);
                    sendSystemMessage(player, result);
                }
                else 
                {
                    sendSystemMessage(player, fuelIs);
                    sendSystemMessage(player, "" + fuel, null);
                    sendSystemMessage(player, engineIs);
                    sendSystemMessage(player, "" + engine, null);
                }
                checkForDestroy(player, top, 40);
            }
            else 
            {
                string_id hypr20 = new string_id(MSGS, "hypr_20");
                sendSystemMessage(player, hypr20);
                setObjVar(top, "hyperdrive", 20);
                if (engine == 0)
                {
                    sendSystemMessage(player, fuelIs);
                    sendSystemMessage(player, "" + fuel, null);
                    sendSystemMessage(player, result);
                }
                else 
                {
                    sendSystemMessage(player, fuelIs);
                    sendSystemMessage(player, "" + fuel, null);
                    sendSystemMessage(player, engineIs);
                    sendSystemMessage(player, "" + engine, null);
                }
                checkForDestroy(player, top, 20);
            }
            return SCRIPT_CONTINUE;
            case sui.BP_CANCEL:
            string_id hypr60 = new string_id(MSGS, "hypr_60");
            sendSystemMessage(player, hypr60);
            setObjVar(top, "hyperdrive", 60);
            string_id engine_is = new string_id(MSGS, "engine_is");
            string_id fuel_is = new string_id(MSGS, "fuel_is");
            string_id result_is = new string_id(MSGS, "engine_result");
            if (engine == 0)
            {
                sendSystemMessage(player, fuel_is);
                sendSystemMessage(player, "" + fuel, null);
                sendSystemMessage(player, result_is);
            }
            else 
            {
                sendSystemMessage(player, fuel_is);
                sendSystemMessage(player, "" + fuel, null);
                sendSystemMessage(player, engine_is);
                sendSystemMessage(player, "" + engine, null);
            }
            checkForDestroy(player, top, 60);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void checkForDestroy(obj_id player, obj_id top, int hyper) throws InterruptedException
    {
        int fuel = getIntObjVar(top, "fuel");
        if (fuel > hyper)
        {
            string_id reset = new string_id(MSGS, "engine_reset");
            sendSystemMessage(player, reset);
        }
        return;
    }
}
