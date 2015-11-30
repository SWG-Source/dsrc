package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.pclib;
import script.library.prose;
import script.library.utils;

public class hero_mark extends script.base_script
{
    public hero_mark()
    {
    }
    public static final string_id SID_MENU_RESTORE = new string_id("quest/hero_of_tatooine/system_messages", "menu_restore");
    public static final string_id SID_RESTORE_MSG = new string_id("quest/hero_of_tatooine/system_messages", "restore_msg");
    public static final string_id SID_RESTORE_NOT_YET = new string_id("quest/hero_of_tatooine/system_messages", "restore_not_yet");
    public static final string_id SID_RESTORE_NOT_EQUIPPED = new string_id("quest/hero_of_tatooine/system_messages", "restore_not_equipped");
    public static final string_id SID_RESTORE_NOT_DEAD = new string_id("quest/hero_of_tatooine/system_messages", "restore_not_dead");
    public static final string_id SID_RESTORE = new string_id("quest/hero_of_tatooine/system_messages", "restore");
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "effect";
        attribs[idx] = utils.packStringId(SID_RESTORE);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "charges";
        if (hasObjVar(self, "charges"))
        {
            int charges = getIntObjVar(self, "charges");
            attribs[idx] = " " + charges;
        }
        else 
        {
            attribs[idx] = " 0";
        }
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "lastUsed"))
        {
            names[idx] = "time_remaining";
            int last_used = getIntObjVar(self, "lastUsed");
            int secRemain = 82800 - (getGameTime() - last_used);
            attribs[idx] = " " + parseTimeMsg(parseTimeRemaining(secRemain), false);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "charges", 50);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(self, "charges"))
        {
            int id = mi.addRootMenu(menu_info_types.SERVER_MENU10, SID_MENU_RESTORE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU10)
        {
            if (!hasObjVar(self, "charges"))
            {
                return SCRIPT_CONTINUE;
            }
            int charges = getIntObjVar(self, "charges");
            if (!utils.isEquipped(self))
            {
                sendSystemMessage(player, SID_RESTORE_NOT_EQUIPPED);
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, "lastUsed"))
            {
                int last_used = getIntObjVar(self, "lastUsed");
                if ((getGameTime() - last_used) < 82800)
                {
                    int secRemain = 82800 - (getGameTime() - last_used);
                    prose_package pp = prose.getPackage(SID_RESTORE_NOT_YET, parseTimeMsg(parseTimeRemaining(secRemain), true));
                    sendSystemMessageProse(player, pp);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    removeObjVar(self, "lastUsed");
                }
            }
            if (!ai_lib.isDead(player))
            {
                sendSystemMessage(player, SID_RESTORE_NOT_DEAD);
                return SCRIPT_CONTINUE;
            }
            String hardpoint = "";
            if (getObjectInSlot(player, "ring_l") == self)
            {
                hardpoint = "hold_l";
            }
            else if (getObjectInSlot(player, "ring_r") == self)
            {
                hardpoint = "hold_r";
            }
            else 
            {
                sendSystemMessage(player, SID_RESTORE_NOT_EQUIPPED);
                return SCRIPT_CONTINUE;
            }
            pclib.clearEffectsForDeath(player);
            setAttrib(player, HEALTH, 200);
            playClientEffectObj(player, "clienteffect/item_ring_hero_mark.cef", player, hardpoint);
            sendSystemMessage(player, SID_RESTORE_MSG);
            messageTo(player, "handlePlayerResuscitated", null, 0, true);
            charges--;
            if (charges <= 0)
            {
                removeObjVar(self, "charges");
            }
            else 
            {
                setObjVar(self, "charges", charges);
            }
            setObjVar(self, "lastUsed", getGameTime());
        }
        return SCRIPT_CONTINUE;
    }
    public int[] parseTimeRemaining(int seconds) throws InterruptedException
    {
        int[] time = 
        {
            0,
            0,
            0
        };
        int minutes = seconds / 60;
        time[2] = seconds % 60;
        if (minutes == 0)
        {
            return time;
        }
        int hours = minutes / 60;
        time[1] = minutes % 60;
        if (hours == 0)
        {
            return time;
        }
        time[0] = hours;
        return time;
    }
    public String parseTimeMsg(int[] time, boolean verbose) throws InterruptedException
    {
        String h = "h";
        String m = "m";
        String s = "s";
        if (verbose)
        {
            if (time[0] == 1)
            {
                h = " hour";
            }
            else 
            {
                h = " hours";
            }
            if (time[1] == 1)
            {
                m = " minute";
            }
            else 
            {
                m = " minutes";
            }
            if (time[2] == 1)
            {
                s = " second";
            }
            else 
            {
                s = " seconds";
            }
        }
        String ret = "";
        if (time[0] > 0)
        {
            ret += time[0] + h + " ";
        }
        if (time[1] > 0)
        {
            ret += time[1] + m + " ";
        }
        if (time[2] > 0)
        {
            ret += time[2] + s;
        }
        return ret;
    }
}
