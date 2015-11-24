package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.attrib;
import script.library.chat;
import script.library.create;
import script.library.factions;
import script.library.groundquests;
import script.library.stealth;
import script.library.trial;
import script.library.utils;

public class scientist_pathing extends script.base_script
{
    public scientist_pathing()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String SCRIPT_LOG = "outbreak_pathing";
    public static final String PATHING_NODE = "pathing_node";
    public static final String SPAWNER_PATH = "spawner_path";
    public static final String SUCCESS_SIGNAL = "success_signal";
    public static final String FAIL_SIGNAL = "fail_signal";
    public static final String UPDATE_SIGNAL = "update_signal";
    public static final String STORMTROOPER_RETREAT = "stormtrooper_retreat";
    public static final int RADIUS = 100;
    public static final String STRINGFILE = "theme_park/outbreak/outbreak";
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (!hasObjVar(self, "invln"))
        {
            return SCRIPT_CONTINUE;
        }
        setAttrib(self, HEALTH, getMaxAttrib(self, HEALTH));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        if (!hasObjVar(self, "invln"))
        {
            return SCRIPT_CONTINUE;
        }
        setHitpoints(self, getMaxHitpoints(self));
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "guard"))
        {
            return SCRIPT_CONTINUE;
        }
        int randTaunt = rand(1, 5);
        if (randTaunt < 0)
        {
            return SCRIPT_CONTINUE;
        }
        string_id strSpam = new string_id(STRINGFILE, "stormtrooper_guard_combat_0" + randTaunt);
        chat.chat(self, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (!hasObjVar(self, "guard"))
        {
            return SCRIPT_CONTINUE;
        }
        int randTaunt = rand(1, 5);
        if (randTaunt < 0)
        {
            return SCRIPT_CONTINUE;
        }
        string_id strSpam = new string_id(STRINGFILE, "stormtrooper_guard_death_0" + randTaunt);
        chat.chat(self, strSpam);
        messageTo(self, "blowUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int blowUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] playerTargets = trial.getValidPlayersInRadius(self, 7);
        obj_id[] npcTargets = getNPCsInRange(getLocation(self), 10);
        playClientEffectLoc(self, "clienteffect/combat_explosion_lair_large.cef", getLocation(self), 0.4f);
        if (playerTargets != null && playerTargets.length > 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.blowUp() Valid player in radius found.");
            for (int i = 0; i < playerTargets.length; i++)
            {
                CustomerServiceLog("outbreak_themepark", "beast_pathing.blowUp() Valid player #" + i + " is about to be incapped.");
                setPosture(playerTargets[i], POSTURE_INCAPACITATED);
                int damageAmount = getAttrib(playerTargets[i], HEALTH) + 1000;
                damage(playerTargets[i], DAMAGE_KINETIC, HIT_LOCATION_BODY, damageAmount);
            }
        }
        if (npcTargets != null && npcTargets.length > 0)
        {
            CustomerServiceLog("outbreak_themepark", "beast_pathing.blowUp() Valid NPCs in radius.");
            for (int a = 0; a < npcTargets.length; a++)
            {
                if ((factions.getFaction(npcTargets[a])).equals("afflicted"))
                {
                    setPosture(npcTargets[a], POSTURE_INCAPACITATED);
                }
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "nomi_rhane"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getObjIdObjVar(self, "owner");
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (groundquests.isTaskActive(owner, "outbreak_quest_facility_05_imperial", "coverSurvivors") || groundquests.isTaskActive(owner, "outbreak_quest_facility_05_rebel", "coverSurvivors") || groundquests.isTaskActive(owner, "outbreak_quest_facility_05_neutral", "coverSurvivors"))
        {
            groundquests.sendSignal(owner, "allDoneSurvivors");
            utils.removeScriptVar(owner, "survivorsRescued");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "survivor_pathing.OnMovePathComplete() initialized.");
        if (!hasObjVar(self, "guard"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "givenPath"))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "secondPath"))
        {
            return SCRIPT_CONTINUE;
        }
        string_id strSpam = new string_id(STRINGFILE, "stormtrooper_guard_go");
        chat.chat(self, strSpam);
        messageTo(self, "startMovingGuardAfterDelay", null, 15, false);
        return SCRIPT_CONTINUE;
    }
    public int startMovingGuardAfterDelay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] wayPointList = getAllObjectsWithObjVar(getLocation(self), RADIUS, STORMTROOPER_RETREAT);
        if (wayPointList == null || wayPointList.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "survivor_pathing.OnMovePathComplete() the NPC failed to find any objects within " + RADIUS + " of " + getLocation(self));
            return SCRIPT_CONTINUE;
        }
        location[] wayPtLocs = new location[wayPointList.length];
        for (int i = 0; i < wayPointList.length; i++)
        {
            int orderNumber = getIntObjVar(wayPointList[i], STORMTROOPER_RETREAT) - 1;
            if (orderNumber < 0)
            {
                continue;
            }
            wayPtLocs[orderNumber] = getLocation(wayPointList[i]);
        }
        if (wayPtLocs == null || wayPtLocs.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "survivor_pathing.OnMovePathComplete() unable to get a list of locations for hte NPC");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "survivor_pathing.OnMovePathComplete() Stormtrooper making retreat.");
        patrolOnce(self, wayPtLocs, 0);
        setObjVar(self, "secondPath", 1);
        return SCRIPT_CONTINUE;
    }
    public int cleanUpNpcTimer(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "scientist_pathing.cleanUpNpcTimer() Initalized Message Handler.");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG(SCRIPT_LOG, msg);
        }
        return true;
    }
}
