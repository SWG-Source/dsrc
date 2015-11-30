package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;

public class mandalorian_crafting extends script.base_script
{
    public mandalorian_crafting()
    {
    }
    public static final String MSGS = "dungeon/death_watch";
    public int OnDeath(obj_id self, obj_id killer, obj_id corpse_id) throws InterruptedException
    {
        detachScript(self, "theme_park.dungeon.death_watch_bunker.mandalorian_crafting");
        return SCRIPT_CONTINUE;
    }
    public int secondTime(obj_id self, dictionary params) throws InterruptedException
    {
        string_id terminal = new string_id(MSGS, "use_terminal");
        sendSystemMessage(self, terminal);
        setObjVar(self, "dw_craft.second", 1);
        dictionary webster = new dictionary();
        webster.put("player", self);
        messageTo(self, "finalTenSeconds", webster, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int firstBox(obj_id self, dictionary params) throws InterruptedException
    {
        string_id terminal = new string_id(MSGS, "use_terminal");
        sendSystemMessage(self, terminal);
        setObjVar(self, "dw_craft.first", 1);
        dictionary webster = new dictionary();
        webster.put("player", self);
        messageTo(self, "thirtySecondsLeft", webster, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
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
            if (hasObjVar(self, "dw_craft.halted"))
            {
                string_id fail = new string_id(MSGS, "failed");
                sendSystemMessage(self, fail);
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(player, "dw_craft.second") && !hasObjVar(player, "dw_craft.first"))
            {
                notYet(self, player);
            }
            else if (hasObjVar(player, "dw_craft.first"))
            {
                testSui(self, player);
            }
            else 
            {
                finishSui(self, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int testSui(obj_id self, obj_id player) throws InterruptedException
    {
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "readableText");
        string_id textString = new string_id(MSGS, "continue_manufacturing");
        String text = getString(textString);
        string_id titleString = new string_id(MSGS, "continue_button");
        String title = getString(titleString);
        setObjVar(player, "dw_craft.opened_test", 1);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "CONTINUE");
        sui.showSUIPage(pid);
        return pid;
    }
    public int finishSui(obj_id self, obj_id player) throws InterruptedException
    {
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "completeText");
        string_id textString = new string_id(MSGS, "finish_manufacturing");
        String text = getString(textString);
        string_id titleString = new string_id(MSGS, "finish_button");
        String title = getString(titleString);
        setObjVar(player, "dw_craft.opened_finish", 1);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "COMPLETE");
        sui.showSUIPage(pid);
        return pid;
    }
    public int readableText(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        removeObjVar(player, "dw_craft.first");
        messageTo(player, "secondTime", null, 10, false);
        string_id hardening = new string_id(MSGS, "alum_process_begun");
        sendSystemMessage(player, hardening);
        return SCRIPT_CONTINUE;
    }
    public int notYet(obj_id self, obj_id player) throws InterruptedException
    {
        string_id notready = new string_id(MSGS, "crafting_not_yet");
        sendSystemMessage(player, notready);
        return 0;
    }
    public int completeText(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        obj_id droid = getObjIdObjVar(self, "droid");
        removeObjVar(player, "dw_craft.second");
        dictionary webster = new dictionary();
        webster.put("player", player);
        messageTo(droid, "craftItem", webster, 10, false);
        string_id aerate = new string_id(MSGS, "aeration_process_begun");
        sendSystemMessage(player, aerate);
        return SCRIPT_CONTINUE;
    }
    public int thirtySecondsLeft(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, new string_id(MSGS, "thirty_seconds"));
        obj_id player = params.getObjId("player");
        if (!hasObjVar(player, "dw_craft.opened_test"))
        {
            string_id tooLong = new string_id(MSGS, "took_too_long");
            sendSystemMessage(player, tooLong);
            setObjVar(self, "dw_craft.halted", 1);
            obj_id droid = getObjIdObjVar(self, "dw_craft.droid");
            finishCraftingSession(self, player, droid);
        }
        return SCRIPT_CONTINUE;
    }
    public void finishCraftingSession(obj_id self, obj_id player, obj_id droid) throws InterruptedException
    {
        removeObjVar(droid, "have");
        removeObjVar(droid, "making");
        removeObjVar(player, "dw_craft");
        detachScript(player, "theme_park.dungeon.death_watch_bunker.mandalorian_crafting");
        return;
    }
    public int finalTenSeconds(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!hasObjVar(player, "dw_craft.opened_finish"))
        {
            string_id tooLong = new string_id(MSGS, "took_too_long");
            sendSystemMessage(player, tooLong);
            setObjVar(self, "dw_craft.halted", 1);
            setObjVar(self, "dw_craft.halted", 1);
            obj_id droid = getObjIdObjVar(self, "dw_craft.droid");
            finishCraftingSession(self, player, droid);
        }
        return SCRIPT_CONTINUE;
    }
}
