package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.fs_counterstrike;
import script.library.fs_dyn_village;
import script.library.trace;
import script.library.money;
import script.library.jedi;
import script.library.utils;
import script.library.fs_quests;
import script.library.factions;
import script.library.group;

public class fs_cs_player extends script.base_script
{
    public fs_cs_player()
    {
    }
    public static final float HELP_SPAWN_TIME_MIN = 180;
    public static final float HELP_SPAWN_TIME_MAX = 300;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!quests.isActive("fs_cs_intro", self))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_cs_player::msgGotPhaseResponse: -> Player logged out while on quest.  Resetting to start.", self, trace.TL_CS_LOG);
            fs_counterstrike.resetPlayerToStart(self, null);
            sendSystemMessage(self, new string_id("fs_quest_village", "quest_logout"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnForceSensitiveQuestCompleted(obj_id self, String questName, boolean succeeded) throws InterruptedException
    {
        if (questName.equals("fs_cs_kill5_guards") && succeeded)
        {
            if (group.isGrouped(self))
            {
                obj_id[] gang = null;
                gang = getGroupMemberIds(getGroupObject(self));
                if ((gang != null) && (gang.length > 0))
                {
                    for (int g = 0; g < gang.length; g++)
                    {
                        obj_id thisGuy = gang[g];
                        if (isIdValid(thisGuy) && exists(thisGuy) && thisGuy != self && quests.isActive("fs_cs_intro", thisGuy))
                        {
                            sendSystemMessage(thisGuy, new string_id("fs_quest_village", "groupmate_powered_down"));
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        factions.setFactionStanding(self, "sith_shadow_nonaggro", factions.FACTION_RATING_MIN);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        factions.setFactionStanding(self, "sith_shadow_nonaggro", 0);
        return SCRIPT_CONTINUE;
    }
    public int msgShieldPowerup(obj_id self, dictionary params) throws InterruptedException
    {
        fs_counterstrike.resetPlayerToStart(self, null);
        sendSystemMessage(self, new string_id("fs_quest_village", "mission_time_failed"));
        return SCRIPT_CONTINUE;
    }
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, new string_id("fs_quest_village", "combat_quest_failed_timeout"));
        fs_counterstrike.resetPlayer(self);
        detachScript(self, "systems.fs_quest.fs_cs_player");
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        if (quests.isActive("fs_cs_escort_commander_pri", self))
        {
            if (hasObjVar(self, "fs_cs.myCapturedCommander"))
            {
                obj_id commander = getObjIdObjVar(self, "fs_cs.myCapturedCommander");
                if (isIdValid(commander) && exists(commander))
                {
                    messageTo(commander, "msgPrimaryEscortAbandoned", null, 0.0f, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerDeath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id questGiver = null;
        if (quests.isActive("fs_cs_kill5_guards", self))
        {
            questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_kill_guards");
            quests.complete("fs_cs_kill5_guards", self, false);
            sendSystemMessage(self, new string_id("fs_quest_village", "fs_cs_last_chance_detail"));
            if (!hasObjVar(self, fs_counterstrike.OBJVAR_MY_CAMP_ID))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id camp = getObjIdObjVar(self, fs_counterstrike.OBJVAR_MY_CAMP_ID);
            fs_counterstrike.createCommander(camp);
        }
        else if (quests.isActive("fs_cs_escort_commander_pri", self))
        {
            if (hasObjVar(self, "fs_cs.myCapturedCommander"))
            {
                obj_id commander = getObjIdObjVar(self, "fs_cs.myCapturedCommander");
                if (isIdValid(commander) && exists(commander))
                {
                    messageTo(commander, "msgPrimaryEscortDied", null, 0.0f, false);
                }
            }
            else 
            {
                trace.log(fs_dyn_village.LOG_CHAN, "fs_cs_player::handlePlayerDeath:-> Missing " + fs_counterstrike.OBJVAR_CAMP_SHIELD_KILLER + " on player " + self + ". Failing quest.", self, trace.TL_ERROR_LOG);
                questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_escort_commander_pri");
                quests.complete("fs_cs_escort_commander_pri", self, false);
                fs_counterstrike.resetPlayerToStart(self, questGiver);
                sendSystemMessage(self, new string_id("fs_quest_village", "fs_primary_escort_fail"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgCommanderDied(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id questGiver = null;
        obj_id sender = null;
        if (params.containsKey("sender"))
        {
            sender = params.getObjId("sender");
        }
        obj_id myCommander = null;
        if (hasObjVar(self, "fs_cs.myCapturedCommander"))
        {
            myCommander = getObjIdObjVar(self, "fs_cs.myCapturedCommander");
        }
        if (sender != null && myCommander != null && sender != myCommander)
        {
            if (hasObjVar(self, "fs_cs_debug"))
            {
                sendSystemMessageTestingOnly(self, "QA Warning: Commander reporting death to us had ID of" + sender + ", but I think I'm escorting commander with ID of " + myCommander + ", so I'm ignoring the message.");
            }
            return SCRIPT_CONTINUE;
        }
        if (quests.isActive("fs_cs_escort_commander_pri", self))
        {
            questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_escort_commander_pri");
            quests.complete("fs_cs_escort_commander_pri", self, false);
        }
        else if (quests.isActive("fs_cs_escort_commander_sec", self))
        {
            questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_escort_commander_sec");
            quests.complete("fs_cs_escort_commander_sec", self, false);
        }
        else if (quests.isActive("fs_cs_ensure_capture", self))
        {
            questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_ensure_capture");
            quests.complete("fs_cs_ensure_capture", self, false);
        }
        else if (quests.isActive("fs_cs_last_chance", self))
        {
            questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_last_chance");
            quests.complete("fs_cs_last_chance", self, false);
        }
        fs_counterstrike.resetPlayerToStart(self, questGiver);
        sendSystemMessage(self, new string_id("fs_quest_village", "commander_died_toxins"));
        return SCRIPT_CONTINUE;
    }
    public int msgCommanderFree(obj_id self, dictionary params) throws InterruptedException
    {
        boolean fail = params.getBoolean("questFailure");
        if (!fail)
        {
            sendSystemMessage(self, new string_id("fs_quest_village", "commander_is_free"));
        }
        else 
        {
            obj_id questGiver = null;
            if (quests.isActive("fs_cs_escort_commander_pri", self))
            {
                questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_escort_commander_pri");
                quests.complete("fs_cs_escort_commander_pri", self, false);
                fs_counterstrike.resetPlayerToStart(self, questGiver);
                sendSystemMessage(self, new string_id("fs_quest_village", "fs_primary_escort_fail"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void giveCrystal(obj_id player) throws InterruptedException
    {
        if (!jedi.forceCreateColorCrystal(player, 29))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_cs_player::giveCrystal: -> Failed to give player lightsaber crystal.  Crystal=null.", player, trace.TL_ERROR_LOG | trace.TL_CS_LOG);
        }
    }
    public int msgQuestComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (quests.isActive("fs_cs_ensure_capture", self))
        {
            quests.complete("fs_cs_ensure_capture", self, true);
        }
        if (quests.isActive("fs_cs_escort_commander_sec", self))
        {
            quests.complete("fs_cs_escort_commander_sec", self, true);
            quests.activate("fs_cs_quest_done_notifyonly", self, null);
            quests.complete("fs_cs_quest_done_notifyonly", self, true);
        }
        boolean teamComplete = false;
        if (params.containsKey("teamComplete"))
        {
            teamComplete = params.getBoolean("teamComplete");
        }
        fs_quests.unlockBranch(self, "force_sensitive_combat_prowess_melee_accuracy");
        giveCrystal(self);
        sendSystemMessage(self, new string_id("fs_quest_village", "quest_complete"));
        if (teamComplete)
        {
            obj_id createdObject = createObjectInInventoryAllowOverload("object/tangible/item/quest/force_sensitive/fs_buff_item.iff", self);
            if (createdObject != null)
            {
                setObjVar(createdObject, "createdFor", self);
                setObjVar(createdObject, "item.time.reuse_time", 345600);
                setObjVar(createdObject, "item.buff.type", 6);
                setObjVar(createdObject, "item.buff.value", 900);
                setObjVar(createdObject, "item.buff.duration", 5400);
            }
            else 
            {
                trace.log(fs_dyn_village.LOG_CHAN, "fs_cs_player::giveCrystal: -> Failed to give player mind buff crystal.  Crystal=null.", self, trace.TL_ERROR_LOG | trace.TL_CS_LOG);
            }
            sendSystemMessage(self, new string_id("fs_quest_village", "teamwork_bonus"));
        }
        fs_counterstrike.destroyCommanderRescueWaypoint(self);
        removeObjVar(self, "fs_cs");
        fs_quests.setQuestCompleted(self, "Phase 3 Combat: Assult on the enemy base");
        quests.complete("fs_cs_quest_done", self, true);
        detachScript(self, "quest.task.fs_quest_cs.quest_done");
        detachScript(self, "systems.fs_quest.fs_cs_player");
        return SCRIPT_CONTINUE;
    }
    public int msgCommanderCaptured(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("capturedBy") || !params.containsKey("commander"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id capturedBy = params.getObjId("capturedBy");
        obj_id commander = params.getObjId("commander");
        obj_id questGiver = null;
        LOG("fs_quest", "fs_cs_player::msgCommanderCaptured: -> capturedBy=" + capturedBy);
        LOG("fs_quest", "fs_cs_player::msgCommanderCaptured: -> fs_cs_ensure_capture isActive=" + quests.isActive("fs_cs_ensure_capture", self));
        if (capturedBy == self && (quests.isActive("fs_cs_last_chance", self) || quests.isActive("fs_cs_ensure_capture", self) || quests.isActive("fs_cs_escort_commander_sec", self)))
        {
            if (quests.isActive("fs_cs_last_chance", self))
            {
                questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_last_chance");
                quests.complete("fs_cs_last_chance", self, true);
            }
            else if (quests.isActive("fs_cs_escort_commander_sec", self))
            {
                questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_escort_commander_sec");
                quests.complete("fs_cs_escort_commander_sec", self, true);
            }
            else 
            {
                questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_ensure_capture");
                quests.complete("fs_cs_ensure_capture", self, true);
            }
            makePlayerEscort(self, commander);
            quests.activate("fs_cs_escort_commander_pri", self, questGiver);
            setObjVar(self, "fs_cs.myCapturedCommander", commander);
            sendSystemMessage(self, new string_id("fs_quest_village", "fs_cs_capd_commander"));
        }
        else if (capturedBy != self && quests.isActive("fs_cs_last_chance", self))
        {
            messageTo(commander, "msgShieldKillerOuster", null, 0.0f, false);
            questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_last_chance");
            quests.complete("fs_cs_last_chance", self, false);
            removeObjVar(self, "fs_cs");
            fs_counterstrike.resetPlayerToStart(self, questGiver);
            sendSystemMessage(self, new string_id("fs_quest_village", "fs_cs_commander_cap_other"));
        }
        else if (capturedBy != self && quests.isActive("fs_cs_ensure_capture", self))
        {
            questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_ensure_capture");
            quests.complete("fs_cs_ensure_capture", self, true);
            removeObjVar(self, "fs_cs");
            quests.activate("fs_cs_escort_commander_sec", self, questGiver);
            sendSystemMessage(self, new string_id("fs_quest_village", "fs_cs_escort_commander_sec"));
            givePlayerCommanderWaypoint(self);
        }
        else if (capturedBy == self && quests.isActive("fs_cs_intro", self))
        {
            questGiver = fs_counterstrike.getCurrentQuestGiver(self, "fs_cs_intro");
            quests.complete("fs_cs_intro", self, true);
            makePlayerEscort(self, commander);
            quests.activate("fs_cs_escort_commander_pri", self, questGiver);
            setObjVar(self, "fs_cs.myCapturedCommander", commander);
            sendSystemMessage(self, new string_id("fs_quest_village", "fs_cs_capd_commander_via_group"));
        }
        else 
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_cs_player::msgCommanderCaptured: -> Unhandled condition for commander capture.", self, trace.TL_ERROR_LOG);
        }
        return SCRIPT_CONTINUE;
    }
    public void givePlayerCommanderWaypoint(obj_id player) throws InterruptedException
    {
        location loc = null;
        if (fs_counterstrike.isShortCommanderRescue())
        {
            location locTest = getLocation(player);
            loc = new location(locTest.x, locTest.y, locTest.z + 1000);
        }
        else 
        {
            loc = new location(5315.0f, 78.0f, -4136.0f);
        }
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointActive(waypoint, true);
        setWaypointName(waypoint, "Return the Commander");
        setObjVar(player, "fs_cs.returnCommanderWaypoint", waypoint);
        return;
    }
    public void makePlayerEscort(obj_id player, obj_id commander) throws InterruptedException
    {
        String objvarname = "quest." + "fs_cs_escort_commander_pri" + ".target";
        setObjVar(player, objvarname, commander);
        objvarname = "quest." + "fs_cs_escort_commander_pri" + ".parameter";
        location loc = null;
        if (fs_counterstrike.isShortCommanderRescue())
        {
            location locTest = getLocation(commander);
            loc = new location(locTest.x, locTest.y, locTest.z + 1000);
        }
        else 
        {
            loc = new location(5315.0f, 78.0f, -4136.0f);
        }
        setObjVar(player, objvarname, loc);
        setObjVar(fs_counterstrike.getMyOutpostId(commander), fs_counterstrike.OBJVAR_CAMP_COMMANDER_TAKER, player);
        setObjVar(fs_counterstrike.getMyOutpostId(commander), fs_counterstrike.OBJVAR_STOP_DEFENSE_SPAWN, true);
        setObjVar(commander, "fs_cs.primaryEscort", player);
        givePlayerCommanderWaypoint(player);
    }
}
