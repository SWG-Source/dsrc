package script.theme_park.meatlump;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.create;
import script.library.group;
import script.library.utils;

public class quest_ragtag_attack_event extends script.base_script
{
    public quest_ragtag_attack_event()
    {
    }
    public static final String OWNER_OBJVAR = "meatlump.owner";
    public static final String COMLINK_OBJVAR = "meatlump.comlink";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanUpGuard", null, 1000, true);
        messageTo(self, "attackPlayer", null, 1, false);
        detachScript(self, "npc.converse.npc_convo");
        detachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int cleanUpGuard(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int attackPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, OWNER_OBJVAR);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!group.isGrouped(player))
        {
            startCombat(self, player);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id groupObj = getGroupObject(player);
            if (!isValidId(groupObj) || !exists(groupObj))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id[] members = getGroupMemberIds(groupObj);
            if ((members == null) || (members.length == 0))
            {
                return SCRIPT_CONTINUE;
            }
            boolean success = getRandomGroupMember(self, player, members);
            if (!success && members.length >= 4)
            {
                boolean secondTry = getRandomGroupMember(self, player, members);
                if (!secondTry)
                {
                    startCombat(self, player);
                    CustomerServiceLog("meatlumpEvent", "ERROR: Mob: " + self + " " + getName(self) + " is attacking: " + player + " " + getName(player) + " by default. This means the function failed to have the mob attach a group member" + " [ attackPlayer() ]");
                }
            }
            else 
            {
                startCombat(self, player);
                CustomerServiceLog("meatlumpEvent", "Mob: " + self + " " + getName(self) + " is attacking: " + player + " " + getName(player) + ". [ attackPlayer() ]");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean getRandomGroupMember(obj_id self, obj_id player, obj_id[] members) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if ((members == null) || (members.length == 0))
        {
            return false;
        }
        int j = rand(0, members.length - 1);
        if (getDistance(getLocation(self), getLocation(members[j])) < 100)
        {
            startCombat(self, members[j]);
            CustomerServiceLog("treasureMap", "Mob: " + self + " " + getName(self) + " is attacking: " + members[j] + " " + getName(members[j]) + " located: " + getLocation(members[j]) + ". [ getRandomGroupMember() ]");
            return true;
        }
        return false;
    }
    public int barkAttack(obj_id self, dictionary params) throws InterruptedException
    {
        chat.chat(self, chat.CHAT_SHOUT, chat.MOOD_ANGRY, new string_id("meatlump/meatlump", "bark_ragtag"));
        return SCRIPT_CONTINUE;
    }
}
