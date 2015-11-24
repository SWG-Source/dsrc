package script.systems.skills.stealth;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.stealth;
import script.library.prose;

public class trigger_remote extends script.base_script
{
    public trigger_remote()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        stealth.onGetTrapAttributes(self, player, names, attribs, getFirstFreeIndex(names));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!utils.testItemClassRequirements(player, self, true, "trap.trigger."))
        {
            LOG("stealth", "Failed trigger ability requirement check");
            return SCRIPT_CONTINUE;
        }
        if (!utils.testItemLevelRequirements(player, self, true, "trap.trigger."))
        {
            LOG("stealth", "Failed trigger skill requirement check");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, stealth.TRAP_MY_TRAP))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid2 = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid2 != null)
        {
            mid2.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!utils.isNestedWithin(self, player))
            {
                return SCRIPT_OVERRIDE;
            }
            if (!utils.testItemClassRequirements(player, self, true, "trap.trigger."))
            {
                LOG("stealth", "Failed trigger ability requirement check");
                return SCRIPT_CONTINUE;
            }
            if (!utils.testItemLevelRequirements(player, self, true, "trap.trigger."))
            {
                LOG("stealth", "Failed trigger skill requirement check");
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(self, stealth.TRAP_MY_TRAP))
            {
                sendSystemMessage(player, new string_id("spam", "not_keyed"));
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(player, new string_id("spam", "remote_punched"));
            if (utils.hasScriptVar(self, "detonateSent"))
            {
                int when = utils.getIntScriptVar(self, "detonateSent");
                int diff = getGameTime() - when;
                if (diff < 5)
                {
                    prose_package pp = prose.getPackage(new string_id("spam", "remote_reset"));
                    pp = prose.setDI(pp, 5 - diff);
                    sendSystemMessageProse(player, pp);
                    return SCRIPT_CONTINUE;
                }
            }
            utils.setScriptVar(self, "detonateSent", getGameTime());
            dictionary sender = new dictionary();
            sender.put("sender", self);
            messageTo(getObjIdObjVar(self, stealth.TRAP_MY_TRAP), "msgTryRemoteDetonate", sender, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDetonated(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (isIdValid(player))
        {
            sendSystemMessage(player, new string_id("spam", "remote_detonate"));
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int trapDisarmed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (isIdValid(player))
        {
            sendSystemMessage(player, new string_id("spam", "remote_disarmed"));
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
