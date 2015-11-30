package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.jedi_trials;
import script.library.locations;
import script.library.prose;
import script.library.quests;
import script.library.utils;

public class fs_crafting4_tracking_device extends script.base_script
{
    public fs_crafting4_tracking_device()
    {
    }
    public static final string_id SID_MENU_USE_TRACKING = new string_id("quest/force_sensitive/fs_crafting", "tracking_device_menu_use");
    public static final string_id SID_NEEDS_TRACKING_DATA = new string_id("quest/force_sensitive/fs_crafting", "tracking_device_needs_data");
    public static final string_id SID_WRONG_PLANET_MSG = new string_id("quest/force_sensitive/fs_crafting", "tracking_device_wrong_planet");
    public static final string_id SID_CANNOT_FIND_LOC = new string_id("quest/force_sensitive/fs_crafting", "tracking_device_no_loc");
    public static final string_id SID_TARGET_LOCATED = new string_id("quest/force_sensitive/fs_crafting", "tracking_device_target_located");
    public static final string_id SID_TARGET_RELOCATED = new string_id("quest/force_sensitive/fs_crafting", "tracking_device_target_relocated");
    public static final string_id SID_NOT_ON_QUEST = new string_id("quest/force_sensitive/fs_crafting", "tracking_device_not_on_quest");
    public static final string_id SID_TRACKING_DEVICE_DESTROYED = new string_id("quest/force_sensitive/fs_crafting", "crafting4_tracking_device_destroyed");
    public static final string_id SID_TRACKING_DEVICE_DESTROYED_OVER = new string_id("quest/force_sensitive/fs_crafting", "crafting4_tracking_device_destroyed_over");
    public static final String NEEDS_TRACKING_DATA_OBJVAR = "fs_crafting4.tracking.needsTrackingData";
    public static final String TARGET_PLANET_OBJVAR = "fs_crafting4.tracking.targetPlanet";
    public static final String TARGET_LOC_OBJVAR = "fs_crafting4.tracking.targetLoc";
    public static final String TARGET_WAYPOINT_OBJVAR = "fs_crafting4.tracking.targetWaypoint";
    public static final String FS_CRAFTING4_PLAYER_SCRIPT = "quest.force_sensitive.fs_crafting4_player";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitialSetup", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleInitialSetup(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, NEEDS_TRACKING_DATA_OBJVAR, true);
        obj_id owner = utils.getContainingPlayer(self);
        if (isIdValid(owner))
        {
            setObjVar(self, "owner", owner);
            quests.complete("fs_crafting4_quest_02", owner, true);
            String custLogMsg = "FS Phase 4 Crafting Quest: Player %TU has completed the tracking device loot kit and now has the tracking device.";
            CustomerServiceLog("FS_Phase4_Crafting", custLogMsg, owner);
        }
        else 
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "cleaningUp"))
        {
            obj_id owner = getObjIdObjVar(self, "owner");
            if (isIdValid(owner))
            {
                if (!quests.isComplete("fs_crafting4_quest_06", owner))
                {
                    if (hasScript(owner, FS_CRAFTING4_PLAYER_SCRIPT))
                    {
                        sendSystemMessage(owner, SID_TRACKING_DEVICE_DESTROYED);
                    }
                    else 
                    {
                        sendSystemMessage(owner, SID_TRACKING_DEVICE_DESTROYED_OVER);
                    }
                    quests.deactivate("fs_crafting4_quest_05", owner);
                    quests.deactivate("fs_crafting4_quest_04", owner);
                    quests.deactivate("fs_crafting4_quest_03", owner);
                    quests.deactivate("fs_crafting4_quest_02", owner);
                    int id = quests.getQuestId("fs_crafting4_quest_05");
                    clearCompletedQuest(owner, id);
                    id = quests.getQuestId("fs_crafting4_quest_04");
                    clearCompletedQuest(owner, id);
                    id = quests.getQuestId("fs_crafting4_quest_03");
                    clearCompletedQuest(owner, id);
                    id = quests.getQuestId("fs_crafting4_quest_02");
                    clearCompletedQuest(owner, id);
                    id = quests.getQuestId("fs_crafting4_quest_01");
                    clearCompletedQuest(owner, id);
                    quests.activate("fs_crafting4_quest_01", owner, null);
                    String custLogMsg = "FS Phase 4 Crafting Quest: Player %TU has destroyed their tracking device loot kit and are being reset to quest step 01.";
                    CustomerServiceLog("FS_Phase4_Crafting", custLogMsg, owner);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "owner");
        if (isIdValid(owner) && player != owner)
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_MENU_USE_TRACKING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getObjIdObjVar(self, "owner");
        if (isIdValid(owner) && player != owner)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (!isOnQuest(self, player))
            {
                sendSystemMessage(player, SID_NOT_ON_QUEST);
            }
            else 
            {
                if (hasObjVar(self, NEEDS_TRACKING_DATA_OBJVAR))
                {
                    sendSystemMessage(player, SID_NEEDS_TRACKING_DATA);
                }
                else 
                {
                    location here = getLocation(player);
                    String curPlanet = here.area;
                    if (!hasObjVar(player, TARGET_PLANET_OBJVAR))
                    {
                        String newTargetPlanet = jedi_trials.chooseRandomPlanet(player);
                        setObjVar(player, TARGET_PLANET_OBJVAR, newTargetPlanet);
                        string_id sid_newTargetPlanet = new string_id("jedi_trials", newTargetPlanet);
                        prose_package sid_message = prose.getPackage(SID_WRONG_PLANET_MSG, sid_newTargetPlanet);
                        sendSystemMessageProse(player, sid_message);
                    }
                    else if (!curPlanet.equals(getStringObjVar(player, TARGET_PLANET_OBJVAR)))
                    {
                        String targetPlanet = getStringObjVar(player, TARGET_PLANET_OBJVAR);
                        string_id sid_targetPlanet = new string_id("jedi_trials", targetPlanet);
                        prose_package sid_message = prose.getPackage(SID_WRONG_PLANET_MSG, sid_targetPlanet);
                        sendSystemMessageProse(player, sid_message);
                    }
                    else if (!hasObjVar(player, TARGET_LOC_OBJVAR))
                    {
                        location newTargetLoc = locations.getRandomGoodLocation(here, 900.0f, 1800.0f, 32.0f);
                        if (newTargetLoc != null)
                        {
                            if (!locations.isInMissionCity(newTargetLoc))
                            {
                                setObjVar(player, TARGET_LOC_OBJVAR, newTargetLoc);
                                obj_id waypoint = createWaypointInDatapad(player, newTargetLoc);
                                setWaypointName(waypoint, "Downed Satellite");
                                setWaypointActive(waypoint, true);
                                setObjVar(player, TARGET_WAYPOINT_OBJVAR, waypoint);
                                requestLocation(player, "crafting4_target_downed_satellite", newTargetLoc, 300.0f, 32.0f, true, false);
                                messageTo(player, "handleSetLocationTarget", null, 1, false);
                                sendSystemMessage(player, SID_TARGET_LOCATED);
                            }
                            else 
                            {
                                sendSystemMessage(player, SID_CANNOT_FIND_LOC);
                            }
                        }
                        else 
                        {
                            sendSystemMessage(player, SID_CANNOT_FIND_LOC);
                        }
                    }
                    else 
                    {
                        if (hasObjVar(player, TARGET_WAYPOINT_OBJVAR))
                        {
                            obj_id oldWaypoint = getObjIdObjVar(player, TARGET_WAYPOINT_OBJVAR);
                            removeObjVar(player, TARGET_WAYPOINT_OBJVAR);
                            if (isIdValid(oldWaypoint))
                            {
                                destroyWaypointInDatapad(oldWaypoint, player);
                            }
                        }
                        location targetLoc = getLocationObjVar(player, TARGET_LOC_OBJVAR);
                        if (targetLoc != null)
                        {
                            obj_id newWaypoint = createWaypointInDatapad(player, targetLoc);
                            setWaypointName(newWaypoint, "Downed Satellite");
                            setWaypointActive(newWaypoint, true);
                            setObjVar(player, TARGET_WAYPOINT_OBJVAR, newWaypoint);
                            messageTo(player, "handleSetLocationTarget", null, 1, false);
                            sendSystemMessage(player, SID_TARGET_RELOCATED);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isOnQuest(obj_id self, obj_id player) throws InterruptedException
    {
        if (hasScript(player, FS_CRAFTING4_PLAYER_SCRIPT))
        {
            boolean hasQuest = false;
            String[] validQuests = 
            {
                "fs_crafting4_quest_03",
                "fs_crafting4_quest_04"
            };
            for (int i = 0; i < validQuests.length; i++)
            {
                if (quests.isActive(validQuests[i], player))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
