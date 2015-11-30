package script.poi.prisonbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.poi;
import script.library.combat;
import script.library.chat;
import script.library.ai_lib;
import script.library.scenario;
import script.library.group;

public class prisoner extends script.base_script
{
    public prisoner()
    {
    }
    public static final String LOG_NAME = "poiPrisonBreak Prisoner";
    public static final String frustrationEmotes[] = 
    {
        "scratch",
        "yawn",
        "tantrum",
        "cough",
        "curse",
        "steam"
    };
    public static final int BOMB_ARM_TIME = 20;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG(LOG_NAME, "Prisoner script attached");
        String oldFaction = factions.getFaction(self);
        setObjVar(self, "oldFaction", oldFaction);
        factions.setFaction(self, "Unattackable");
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterWaiting(obj_id self, modifiable_float time) throws InterruptedException
    {
        stop(self);
        messageTo(self, "emoteFrustration", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int emoteFrustration(obj_id self, dictionary params) throws InterruptedException
    {
        int dosocial = rand(1, 4);
        if (dosocial == 1)
        {
            int whichsocial = rand(0, 5);
            queueCommand(self, (1780871594), null, frustrationEmotes[whichsocial], COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        LOG(LOG_NAME, "Prisoner reached path destination");
        if (hasObjVar(self, "bomber"))
        {
            removeObjVar(self, "bomber");
            stop(self);
            messageTo(self, "squatDown", null, 2, false);
        }
        else if (hasObjVar(self, "returning"))
        {
            removeObjVar(self, "returning");
            stop(self);
            obj_id a = poi.findObject("antagonist_0");
            if ((a == null) || (a == obj_id.NULL_ID))
            {
                return SCRIPT_CONTINUE;
            }
            faceTo(self, a);
            messageTo(self, "reportBombSet1", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int squatDown(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(self, (28609318), self, "No params.", COMMAND_PRIORITY_FRONT);
        setPosture(self, POSTURE_CROUCHED);
        removeObjVar(self, "ai.combat.moveMode");
        poi.quickSay(self, chat.CHAT_BOAST, chat.MOOD_DEVIOUS, "a_minion_setbomb");
        messageTo(poiMaster, "enableBombedWall", null, 0, false);
        messageTo(self, "bombSet", null, BOMB_ARM_TIME, false);
        return SCRIPT_CONTINUE;
    }
    public int bombSet(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "dropbomb"))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(self, (-1465754503), self, "No params.", COMMAND_PRIORITY_FRONT);
        setPosture(self, POSTURE_UPRIGHT);
        removeObjVar(self, "POIbomber");
        messageTo(self, "moveBack", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int moveBack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id a = poi.findObject("antagonist_0");
        if ((a == null) || (a == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "returning", true);
        location bossloc = new location(getLocation(a));
        bossloc.x -= 1;
        ai_lib.aiPathTo(self, bossloc);
        return SCRIPT_CONTINUE;
    }
    public int reportBombSet1(obj_id self, dictionary params) throws InterruptedException
    {
        queueCommand(self, (1780871594), null, "salute", COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "reportBombSet2", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int reportBombSet2(obj_id self, dictionary params) throws InterruptedException
    {
        poi.quickSay(self, "a_minion_reportbomb");
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "restoreFaction", null, 4, false);
        messageTo(poiMaster, "blowBomb", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int wallDamaged(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "POIbomber"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "barkAttacked"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "barkAttacked", true);
        poi.quickSay(self, chat.CHAT_SWEAR, chat.MOOD_NERVOUS, "a_minion_attackedwhilebombing");
        obj_id found = poi.findObject("antagonist_0");
        if ((found == null) || (found == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(found, chat.CHAT_SHOUT, chat.MOOD_ENCOURAGING, "a_keepitup");
        setObjVar(self, "dropbomb", true);
        messageTo(self, "dropBomb", params, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int restoreFaction(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id antagonists[] = scenario.getTeamMembers(poiMaster, "antagonist");
        for (int i = 0; i < antagonists.length; i++)
        {
            obj_id found = antagonists[i];
            if ((found == null) || (found == obj_id.NULL_ID))
            {
                continue;
            }
            String oldFaction = getStringObjVar(found, "oldFaction");
            factions.setFaction(found, oldFaction);
        }
        return SCRIPT_CONTINUE;
    }
    public int dropBomb(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "POIbomber"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id attacker = params.getObjId("attacker");
        if ((attacker == null) || (attacker == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        if (group.isGroupObject(attacker))
        {
            obj_id[] members = getGroupMemberIds(attacker);
            if ((members == null) || (members.length == 0))
            {
            }
            else 
            {
                scenario.grantKillCredit(poiMaster, "antagonist", members);
            }
        }
        else 
        {
            obj_id[] attackers = new obj_id[1];
            attackers[0] = attacker;
            scenario.grantKillCredit(poiMaster, "antagonist", attackers);
        }
        poi.quickSay(self, chat.CHAT_SCREAM, chat.MOOD_PANICKED, "a_minion_dropbomb");
        queueCommand(self, (-1114832209), self, "No params.", COMMAND_PRIORITY_FRONT);
        setPosture(self, POSTURE_PRONE);
        messageTo(poiMaster, "blowDroppedBomb", null, 5, false);
        obj_id antagonists[] = scenario.getTeamMembers(poiMaster, "antagonist");
        for (int i = 0; i < antagonists.length; i++)
        {
            obj_id found = antagonists[i];
            if ((found == null) || (found == obj_id.NULL_ID))
            {
                continue;
            }
            String oldFaction = getStringObjVar(found, "oldFaction");
            factions.setFaction(found, oldFaction);
        }
        return SCRIPT_CONTINUE;
    }
}
