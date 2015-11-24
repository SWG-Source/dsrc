package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.pet_lib;

public class engine extends script.base_script
{
    public engine()
    {
    }
    public static final String MSGS = "dungeon/corvette";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        string_id engine_name = new string_id(MSGS, "engine_name");
        String name = getString(engine_name);
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
        string_id textMsg = new string_id(MSGS, "engine_settings");
        String TEXT = getString(textMsg);
        string_id titleMsg = new string_id(MSGS, "engine_title");
        String TITLE = getString(titleMsg);
        obj_id self = getSelf();
        int a = 0;
        int b = 0;
        int c = 0;
        String one = "";
        String two = "";
        String three = "";
        if (!hasObjVar(self, "a"))
        {
            a = rand(40, 60);
            b = a + 35;
            c = a - 10;
            setObjVar(self, "a", a);
            setObjVar(self, "b", b);
            setObjVar(self, "c", c);
        }
        else 
        {
            a = getIntObjVar(self, "a");
            b = getIntObjVar(self, "b");
            c = getIntObjVar(self, "c");
        }
        one = "" + a;
        two = "" + b;
        three = "" + c;
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, pcd, player, "CorvetteEngine");
        setSUIProperty(pid, "", "Size", "250,175");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, TEXT);
        sui.msgboxButtonSetup(pid, sui.YES_NO_CANCEL);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, sui.PROP_TEXT, one);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, three);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, two);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "OnPress", "RevertWasPressed=1\r\nparent.btnOk.press=t");
        subscribeToSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "RevertWasPressed");
        sui.showSUIPage(pid);
        return pid;
    }
    public int CorvetteEngine(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (hasObjVar(self, "destroyed"))
        {
            string_id blewup = new string_id(MSGS, "blew_up");
            sendSystemMessage(player, blewup);
            return SCRIPT_CONTINUE;
        }
        int a = getIntObjVar(self, "a");
        int b = getIntObjVar(self, "b");
        int c = getIntObjVar(self, "c");
        String one = "" + a;
        String two = "" + b;
        String three = "" + c;
        obj_id top = getTopMostContainer(self);
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            if (revert != null && !revert.equals(""))
            {
                string_id setting = new string_id(MSGS, "engine_set");
                string_id result = new string_id(MSGS, "engine_result");
                sendSystemMessage(player, setting);
                sendSystemMessage(player, "" + one + "%", null);
                setObjVar(top, "engine", a);
                sendSystemMessage(player, result);
            }
            else 
            {
                string_id setting = new string_id(MSGS, "engine_set");
                sendSystemMessage(player, setting);
                sendSystemMessage(player, "" + three + "%", null);
                setObjVar(top, "engine", c);
                checkForDestroy(player, top);
            }
            return SCRIPT_CONTINUE;
            case sui.BP_CANCEL:
            string_id setting = new string_id(MSGS, "engine_set");
            string_id result = new string_id(MSGS, "engine_result");
            sendSystemMessage(player, setting);
            sendSystemMessage(player, "" + two + "%", null);
            setObjVar(top, "engine", b);
            sendSystemMessage(player, result);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void checkForDestroy(obj_id player, obj_id top) throws InterruptedException
    {
        obj_id self = getSelf();
        string_id blewup = new string_id(MSGS, "blew_up");
        if (hasObjVar(self, "destroyed"))
        {
            sendSystemMessage(player, blewup);
            return;
        }
        int fuel = getIntObjVar(top, "fuel");
        int hypr = getIntObjVar(top, "hyperdrive");
        String type = getStringObjVar(top, "space_dungeon.quest_type");
        if (type.equals("neutral_destroy") || type.equals("imperial_destroy") || type.equals("rebel_destroy"))
        {
            if (hasObjVar(top, "fuel") && hasObjVar(top, "hyperdrive"))
            {
                if (fuel > hypr)
                {
                    sendSystemMessage(player, blewup);
                    removeObjVar(top, "engine");
                    if (hasObjVar(top, "current_players"))
                    {
                        Vector players = utils.getResizeableObjIdBatchObjVar(top, "current_players");
                        if (players == null)
                        {
                            return;
                        }
                        int numPlayers = players.size();
                        if (numPlayers == 0)
                        {
                            return;
                        }
                        for (int i = 0; i < numPlayers; i++)
                        {
                            messageTo(((obj_id)players.get(i)), "youWin", null, 1, false);
                            setObjVar(self, "destroyed", 1);
                        }
                    }
                }
            }
            else 
            {
                string_id calc = new string_id(MSGS, "calculations");
                sendSystemMessage(player, calc);
            }
        }
        else 
        {
            string_id normal = new string_id(MSGS, "engine_result");
            string_id fuel_level = new string_id(MSGS, "fuel_is");
            string_id hypr_level = new string_id(MSGS, "hyperdrive_is");
            sendSystemMessage(player, normal);
            sendSystemMessage(player, fuel_level);
            sendSystemMessage(player, "" + fuel, null);
            sendSystemMessage(player, hypr_level);
            sendSystemMessage(player, "" + hypr, null);
        }
        return;
    }
}
