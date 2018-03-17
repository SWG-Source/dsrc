package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.pet_lib;
import script.library.buff;
import script.library.chat;
import script.library.prose;
import script.library.utils;
import script.library.xp;

public class smuggler_medic extends script.base_script
{
    public smuggler_medic()
    {
    }
    public static final float BUDDY_DURATION = 62.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleGreeting", null, 2.0f, false);
        messageTo(self, "handleRunAway", null, BUDDY_DURATION, false);
        messageTo(self, "castOnMaster", null, 1.0f, false);
        setObjVar(self, "ai.pet", true);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "runningaway"))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleNewTarget", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int castOnMaster(obj_id self, dictionary params) throws InterruptedException
    {
        if(!pet_lib.hasMaster(self)){
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            float healthPercent = (float)getAttrib(master, HEALTH) / (float)getMaxAttrib(master, HEALTH);
            int myLevel = getLevel(self);
            if (healthPercent < 0.50f)
            {
                if (myLevel < 10)
                {
                    queueCommand(self, (1178893546), master, "", COMMAND_PRIORITY_FRONT);
                }
                else if (myLevel < 30)
                {
                    queueCommand(self, (1258773043), master, "", COMMAND_PRIORITY_FRONT);
                }
                else if (myLevel < 58)
                {
                    queueCommand(self, (1338394500), master, "", COMMAND_PRIORITY_FRONT);
                }
                else if (myLevel < 78)
                {
                    queueCommand(self, (1367414657), master, "", COMMAND_PRIORITY_FRONT);
                }
                else if (myLevel < 91)
                {
                    queueCommand(self, (1430260278), master, "", COMMAND_PRIORITY_FRONT);
                }
            }
            else 
            {
                if (myLevel >= 46 && !buff.hasBuff(master, "me_buff_health"))
                {
                    queueCommand(self, (-1176506063), master, "", COMMAND_PRIORITY_FRONT);
                }
            }
        }
        messageTo(self, "castOnMaster", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleGreeting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            String name = getFirstName(master);
            int phraseId = rand(1, 6);
            String greeting = "smuggler_medic_greeting_" + phraseId;
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("combat_effects", greeting));
            pp = prose.setTU(pp, name);
            chat.chat(self, master, chat.CHAT_SAY, null, chat.ChatFlag_targetGroupOnly, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNewTarget(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            obj_id newTarget = getHateTarget(master);
            if (isIdValid(newTarget))
            {
                startCombat(self, newTarget);
            }
            else 
            {
                messageTo(self, "handleRunAway", null, 2.0f, false);
            }
        }
        else 
        {
            messageTo(self, "handleRunAway", null, 2.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRunAway(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            messageTo(self, "handleCleanUp", null, 15.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "runningaway"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            String name = getFirstName(master);
            int phraseId = rand(1, 6);
            String goodbye = "smuggler_medic_goodbye_" + phraseId;
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("combat_effects", goodbye));
            pp = prose.setTU(pp, name);
            chat.chat(self, master, chat.CHAT_SAY, null, chat.ChatFlag_targetGroupOnly, pp);
        }
        stopCombat(self);
        setMaster(self, null);
        setInvulnerable(self, true);
        if (isIdValid(master))
        {
            ai_lib.pathAwayFrom(self, master);
        }
        utils.setScriptVar(self, "runningaway", 1);
        messageTo(self, "handleCleanUp", null, 15.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id weapon = getCurrentWeapon(self);
        if (isIdValid(weapon) && !isDefaultWeapon(weapon))
        {
            destroyObject(weapon);
        }
        obj_id cInv = utils.getInventoryContainer(self);
        if (isIdValid(cInv))
        {
            utils.emptyContainerExceptStorytellerLoot(cInv);
        }
        ai_lib.clearCombatData();
        setObjVar(self, xp.VAR_LANDED_DEATHBLOW, attacker);
        if (!kill(self))
        {
            obj_id[] haters = getHateList(self);
            if (haters.length > 0)
            {
                for (int i = 0; i < haters.length; i++)
                {
                    removeHateTarget(haters[i], self);
                }
            }
            destroyObject(self);
        }
        clearCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
}
