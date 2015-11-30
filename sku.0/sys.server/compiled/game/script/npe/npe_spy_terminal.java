package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.groundquests;
import script.library.create;
import script.library.chat;

public class npe_spy_terminal extends script.base_script
{
    public npe_spy_terminal()
    {
    }
    public static final string_id SID_MNU_HAX = new string_id("npe", "hax");
    public static final string_id SID_MNU_PART = new string_id("npe", "part");
    public static final string_id SID_MNU_SPLICE = new string_id("npe", "splice");
    public static final string_id SID_MNU_DUMP = new string_id("npe", "dump");
    public static final string_id SID_MNU_DROID = new string_id("npe", "droid");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        String myTemplate = getTemplateName(self);
        if (groundquests.isTaskActive(player, "npe_spy_try", "generator") && myTemplate.equals("object/tangible/terminal/npe_generator.iff"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_HAX);
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(player, "npe_spy_try", "placePart") && myTemplate.equals("object/tangible/terminal/npe_droid_production.iff"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_PART);
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(player, "npe_spy_try", "doIt") && myTemplate.equals("object/tangible/terminal/systems_control_terminal.iff"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_SPLICE);
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(player, "npe_smuggler_try", "dumpItem"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_DUMP);
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(player, "npe_spy_try", "triggerVolume") && myTemplate.equals("object/tangible/terminal/systems_control_terminal.iff"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_DROID);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            String myTemplate = getTemplateName(self);
            if (groundquests.isTaskActive(player, "npe_spy_try", "generator") && myTemplate.equals("object/tangible/terminal/npe_generator.iff"))
            {
                groundquests.sendSignal(player, "generator");
                sendSystemMessage(player, new string_id("npe", "core"));
                playClientEffectObj(self, "clienteffect/npe_generator_pwrdwn.cef", self, "");
                return SCRIPT_CONTINUE;
            }
            if (groundquests.isTaskActive(player, "npe_spy_try", "placePart") && myTemplate.equals("object/tangible/terminal/npe_droid_production.iff"))
            {
                groundquests.sendSignal(player, "placePart");
                sendSystemMessage(player, new string_id("npe", "machine"));
                sendSystemMessage(player, new string_id("npe", "machine2"));
                playClientEffectObj(self, "clienteffect/npe_quest_place_part.cef", self, "");
                return SCRIPT_CONTINUE;
            }
            if (groundquests.isTaskActive(player, "npe_spy_try", "doIt") && myTemplate.equals("object/tangible/terminal/systems_control_terminal.iff"))
            {
                groundquests.sendSignal(player, "doIt");
                sendSystemMessage(player, new string_id("npe", "splice1"));
                playClientEffectObj(self, "clienteffect/npe_systems_terminal.cef", self, "");
                return SCRIPT_CONTINUE;
            }
            if (groundquests.isTaskActive(player, "npe_smuggler_try", "dumpItem"))
            {
                groundquests.sendSignal(player, "dumpItem");
                sendSystemMessage(player, new string_id("npe", "dump1"));
                return SCRIPT_CONTINUE;
            }
            if (groundquests.isTaskActive(player, "npe_spy_try", "triggerVolume") && myTemplate.equals("object/tangible/terminal/systems_control_terminal.iff"))
            {
                groundquests.sendSignal(player, "stopEscort");
                sendSystemMessage(player, new string_id("npe", "droidmsg"));
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(player, new string_id("quest/groundquests", "retrieve_item_no_interest"));
        return SCRIPT_CONTINUE;
    }
    public void playWarningAndSpawn(obj_id player, obj_id self, String toSpawn) throws InterruptedException
    {
        location me = getLocation(player);
        chat.chat(self, "INTRUDER ALERT! INTRUDER ALERT!");
        me.x = me.x + 3;
        create.object(toSpawn, me);
        me.y = me.y + 1;
        create.object(toSpawn, me);
        return;
    }
    public void doWarning(obj_id self) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        Vector alarms = utils.getResizeableObjIdArrayScriptVar(building, "objAlarms");
        for (int i = 0; i < alarms.size(); i++)
        {
            if (isIdValid(((obj_id)alarms.get(i))))
            {
                setCondition(((obj_id)alarms.get(i)), CONDITION_ON);
                messageTo(self, "turnAlarmsOff", null, 10, false);
            }
        }
    }
    public int turnAlarmsOff(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        Vector alarms = utils.getResizeableObjIdArrayScriptVar(building, "objAlarms");
        for (int i = 0; i < alarms.size(); i++)
        {
            if (isIdValid(((obj_id)alarms.get(i))))
            {
                clearCondition(((obj_id)alarms.get(i)), CONDITION_ON);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
