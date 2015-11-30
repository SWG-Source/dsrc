package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.space_combat;
import script.library.space_create;
import script.library.space_quest;
import script.library.space_utils;
import script.library.space_transition;
import script.library.ship_ai;
import script.library.utils;
import script.library.prose;
import script.library.group;
import script.library.static_item;

public class player_spacequest extends script.base_script
{
    public player_spacequest()
    {
    }
    public static final string_id SID_REPORT_CARGO = new string_id("space/cargo", "report_cargo");
    public static final string_id SID_REPORT_CARGO_GROUP = new string_id("space/cargo", "report_cargo_group");
    public static final string_id SID_INSPECT_ALREADY = new string_id("space/cargo", "inspect_already");
    public static final string_id SID_INSPECT_TOO_FAR = new string_id("space/cargo", "inspect_too_far");
    public static final string_id SID_INSPECTING = new string_id("space/cargo", "inspecting");
    public static final string_id SID_REPORT_TRANSFER = new string_id("space/cargo", "report_transfer");
    public static final string_id SID_REPORT_TRANSFER_GROUP = new string_id("space/cargo", "report_transfer_group");
    public static final string_id SID_TRANSFER_ALREADY = new string_id("space/cargo", "transfer_already");
    public static final string_id SID_TRANSFER_TOO_FAR = new string_id("space/cargo", "transfer_too_far");
    public static final string_id SID_TRANSFER_NOT_DISABLED = new string_id("space/cargo", "transfer_not_disabled");
    public static final string_id SID_TRANSFER_NOTHING = new string_id("space/cargo", "transfer_nothing");
    public static final string_id SID_TRANSFER_CANT = new string_id("space/cargo", "transfer_cant");
    public static final string_id SID_TRANSFERRING = new string_id("space/cargo", "transferring");
    public static final string_id SID_TARGET_REQUIRED = new string_id("space/cargo", "target_required");
    public static final string_id SID_CANNOT_DOCK = new string_id("space/cargo", "dock_cannot");
    public static final string_id SID_DOCKING_ALREADY = new string_id("space/cargo", "dock_already");
    public static final string_id SID_TARGET_DOCKING_ALREADY = new string_id("space/cargo", "dock_target_already");
    public static final string_id SID_DOCK_TOO_FAR = new string_id("space/cargo", "dock_too_far");
    public static final string_id SID_DOCKING = new string_id("space/cargo", "dock_started");
    public static final string_id SID_DOCK_ACHIEVED = new string_id("space/cargo", "dock_achieved");
    public static final string_id SID_DOCKING_COMPLETE = new string_id("space/cargo", "dock_complete");
    public static final string_id SID_DOCKING_GROUP = new string_id("space/cargo", "dock_started_group");
    public static final string_id SID_DOCKING_COMPLETE_GROUP = new string_id("space/cargo", "dock_complete_group");
    public static final string_id SID_ABORT_DOCKING = new string_id("space/cargo", "dock_abort");
    public static final string_id SID_NO_TARGET = new string_id("space/cargo", "no_target");
    public static final string_id SID_NOT_IN_SUUB = new string_id("space/cargo", "not_in_suub");
    public static final string_id LEFT_GROUP_QUEST = new string_id("space/quest", "left_group_quest");
    public static final String INSPECTION_BEGIN = "clienteffect/ship_inspect_begin.cef";
    public static final String INSPECTION_END = "clienteffect/ship_inspect_end.cef";
    public static final String CARGO_TRANSFER = "clienteffect/ship_cargo_transfer.cef";
    public static final String DOCK_START = "clienteffect/ship_dock_repair_01.cef";
    public static final String DOCK_END = "clienteffect/ship_dock_repair_02.cef";
    public int OnLogin(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, "inspecting");
        obj_id ship = space_transition.getContainingShip(self);
        if (isIdValid(ship))
        {
            String strChassisType = getShipChassisType(ship);
            if ((strChassisType != null) && strChassisType.equals("player_sorosuub_space_yacht"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        dictionary outparams = new dictionary();
        outparams.put("player", self);
        space_quest.notifyMissions(self, "initializedQuestPlayer", outparams);
        String[] delayedTriggers = getStringArrayObjVar(self, "delayedTriggers");
        if (delayedTriggers != null)
        {
            int cleanupCount = 0;
            int[] cleanupTriggers = new int[delayedTriggers.length];
            for (int i = 0; i < delayedTriggers.length; i++)
            {
                cleanupTriggers[i] = 0;
                java.util.StringTokenizer st = new java.util.StringTokenizer(delayedTriggers[i], ":");
                String questType = st.nextToken();
                String questName = st.nextToken();
                String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
                dictionary questInfo = dataTableGetRow(qTable, 0);
                if (questInfo == null)
                {
                    cleanupTriggers[i] = 1;
                    cleanupCount++;
                    continue;
                }
                if (!(getCurrentSceneName()).startsWith(questInfo.getString("questZone")))
                {
                    continue;
                }
                outparams = new dictionary();
                outparams.put("type", questType);
                outparams.put("name", questName);
                messageTo(self, "assignDelayedQuest", outparams, questInfo.getInt("questDelay"), false);
            }
            if (delayedTriggers.length - cleanupCount == 0)
            {
                removeObjVar(self, "delayedTriggers");
            }
            else 
            {
                String[] newTriggers = new String[delayedTriggers.length - cleanupCount];
                int j = 0;
                for (int i = 0; i < delayedTriggers.length; i++)
                {
                    if (cleanupTriggers[i] == 1)
                    {
                        continue;
                    }
                    newTriggers[j++] = delayedTriggers[i];
                }
                setObjVar(self, "delayedTriggers", newTriggers);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        space_quest.cleanupOnUnload(self);
        return SCRIPT_CONTINUE;
    }
    public int targetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (group.isGrouped(self))
        {
            obj_id gid = getGroupObject(self);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null)
            {
                for (int i = 0; i < members.length; i++)
                {
                    space_quest.notifyMissions(members[i], "handleShipDestroyed", params);
                }
            }
        }
        else 
        {
            space_quest.notifyMissions(self, "handleShipDestroyed", params);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAsteroidMined(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (group.isGrouped(self))
        {
            obj_id gid = getGroupObject(self);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null)
            {
                for (int i = 0; i < members.length; i++)
                {
                    space_quest.notifyMissions(members[i], "asteroidMined", params);
                }
            }
        }
        else 
        {
            space_quest.notifyMissions(self, "asteroidMined", params);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleShipSalvage(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (group.isGrouped(self))
        {
            obj_id gid = getGroupObject(self);
            obj_id[] members = space_utils.getSpaceGroupMemberIds(gid);
            if (members != null)
            {
                for (int i = 0; i < members.length; i++)
                {
                    space_quest.notifyMissions(members[i], "asteroidMined", params);
                }
            }
        }
        else 
        {
            space_quest.notifyMissions(self, "wasSalvaged", params);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("name", name);
        params.put("player", self);
        space_quest.notifyMissions(self, "arrivedAtLocation", params);
        return SCRIPT_CONTINUE;
    }
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary outparams = new dictionary();
        outparams.put("player", self);
        space_quest.notifyMissions(self, "playerShipDestroyed", outparams);
        return SCRIPT_CONTINUE;
    }
    public int doSpecialEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id mobj = params.getObjId("quest");
        if (!exists(mobj) || !isIdValid(mobj))
        {
            return SCRIPT_CONTINUE;
        }
        String questZone = getStringObjVar(mobj, space_quest.QUEST_ZONE);
        int triggerEvent = getIntObjVar(mobj, space_quest.QUEST_TRIGGER_EVENT);
        if (triggerEvent == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int triggerSC = getIntObjVar(mobj, space_quest.QUEST_TRIGGER_SC);
        String triggerArg = getStringObjVar(mobj, space_quest.QUEST_TRIGGER_ARG);
        String triggerArg2 = getStringObjVar(mobj, space_quest.QUEST_TRIGGER_ARG2);
        int triggerOn = getIntObjVar(mobj, space_quest.QUEST_TRIGGER_ON);
        int triggerEnds = getIntObjVar(mobj, space_quest.QUEST_TRIGGER_ENDS);
        String questName = getStringObjVar(mobj, space_quest.QUEST_NAME);
        String questType = getStringObjVar(mobj, space_quest.QUEST_TYPE);
        if (params.getBoolean("ends") || (triggerEnds == 1))
        {
            space_utils.notifyObject(mobj, "removeQuest", null);
        }
        if (isIdValid(mobj) && exists(mobj))
        {
            setObjVar(mobj, "pendingSplit", false);
        }
        switch (triggerEvent)
        {
            case 1:
            if (params.getBoolean("failure") && (triggerSC == 3))
            {
                splitQuest(self, triggerArg2, triggerOn, questName, questType, true);
            }
            else 
            {
                splitQuest(self, triggerArg, triggerOn, questName, questType, false);
            }
            break;
            default:
            break;
        }
        if (isIdValid(mobj) && exists(mobj))
        {
            removeObjVar(mobj, "trigger");
        }
        return SCRIPT_CONTINUE;
    }
    public void splitQuest(obj_id self, String triggerArg, int triggerOn, String questName, String questType, boolean failure) throws InterruptedException
    {
        if (failure)
        {
            space_quest.showQuestAlert(self, new string_id("spacequest/" + questType + "/" + questName, "split_quest_alert_fail"));
        }
        else 
        {
            space_quest.showQuestAlert(self, new string_id("spacequest/" + questType + "/" + questName, "split_quest_alert"));
        }
        play2dNonLoopingMusic(self, space_quest.MUSIC_QUEST_EVENT);
        space_combat.flightDroidVocalize(space_transition.getContainingShip(self), 3);
        java.util.StringTokenizer st = new java.util.StringTokenizer(triggerArg, ":");
        String newtype = st.nextToken();
        String newname = st.nextToken();
        space_quest.grantQuest(self, newtype, newname, true);
        dictionary webster = new dictionary();
        webster.put("splitQuestName", newname);
        webster.put("questName", questName);
        messageTo(self, "groundSpaceSplit", webster, 1, false);
        String groupString = "GROUP:0";
        if (group.isGrouped(self))
        {
            obj_id groupId = getGroupObject(self);
            int numMembers = getGroupSize(groupId);
            if (numMembers > 0)
            {
                groupString = "GROUP:" + numMembers;
            }
        }
        CustomerServiceLog("space_quest", "QUEST_SPLIT|V1|" + groupString + "|TIME:" + getGameTime() + "|PLAYER:" + self + "|TYPE:" + questType + "|NAME:" + questName + "|ZONE:" + getCurrentSceneName());
    }
    public int registerDelayedQuest(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String questType = params.getString(space_quest.QUEST_TYPE);
        String questName = params.getString(space_quest.QUEST_NAME);
        int delay = params.getInt(space_quest.QUEST_DELAY);
        String[] delayedTriggers = getStringArrayObjVar(self, "delayedTriggers");
        String[] newTriggers = null;
        if (delayedTriggers == null)
        {
            newTriggers = new String[1];
            newTriggers[0] = questType + ":" + questName;
        }
        else 
        {
            newTriggers = new String[delayedTriggers.length + 1];
            for (int i = 0; i < delayedTriggers.length; i++)
            {
                if (delayedTriggers[i].equals(questType + ":" + questName))
                {
                    return SCRIPT_CONTINUE;
                }
                newTriggers[i] = delayedTriggers[i];
            }
            newTriggers[delayedTriggers.length] = questType + ":" + questName;
        }
        setObjVar(self, "delayedTriggers", newTriggers);
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if ((getCurrentSceneName()).startsWith(questInfo.getString("questZone")))
        {
            dictionary outparams = new dictionary();
            outparams.put("type", questType);
            outparams.put("name", questName);
            messageTo(self, "assignDelayedQuest", outparams, questInfo.getInt("questDelay"), false);
        }
        return SCRIPT_CONTINUE;
    }
    public int assignDelayedQuest(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String questName = params.getString("name");
        String questType = params.getString("type");
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if (!(getCurrentSceneName()).startsWith(questInfo.getString("questZone")))
        {
            return SCRIPT_CONTINUE;
        }
        space_quest.showQuestAlert(self, new string_id("spacequest/" + questType + "/" + questName, "assigned_delayed"));
        space_quest.grantQuest(self, questType, questName, false, true);
        String[] delayedTriggers = getStringArrayObjVar(self, "delayedTriggers");
        if (delayedTriggers == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (delayedTriggers.length == 1)
        {
            removeObjVar(self, "delayedTriggers");
            return SCRIPT_CONTINUE;
        }
        String[] newTriggers = new String[delayedTriggers.length - 1];
        int j = 0;
        for (int i = 0; i < delayedTriggers.length; i++)
        {
            if (delayedTriggers[i].equals(questType + ":" + questName))
            {
                continue;
            }
            newTriggers[j++] = delayedTriggers[i];
        }
        setObjVar(self, "delayedTriggers", newTriggers);
        return SCRIPT_CONTINUE;
    }
    public int targetDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        space_quest.notifyMissions(self, "handleShipDisabled", params);
        return SCRIPT_CONTINUE;
    }
    public int cmdInspect(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (target == null)
        {
            sendQuestSystemMessage(self, SID_NO_TARGET);
            return SCRIPT_CONTINUE;
        }
        obj_id containing_ship = space_transition.getContainingShip(self);
        if (isIdValid(containing_ship))
        {
            String strChassisType = getShipChassisType(containing_ship);
            if (strChassisType.equals("player_sorosuub_space_yacht"))
            {
                sendQuestSystemMessage(self, SID_NOT_IN_SUUB);
                return SCRIPT_CONTINUE;
            }
        }
        if (space_transition.getContainingShip(self) == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "inspecting"))
        {
            sendQuestSystemMessage(self, SID_INSPECT_ALREADY);
            return SCRIPT_CONTINUE;
        }
        float dist = getDistance(space_transition.getContainingShip(self), target);
        if ((dist == -1) || (dist > 150))
        {
            sendQuestSystemMessage(self, SID_INSPECT_TOO_FAR);
            return SCRIPT_CONTINUE;
        }
        sendQuestSystemMessage(self, SID_INSPECTING);
        utils.setScriptVar(self, "inspecting", target);
        messageTo(self, "finishInspection", null, 5.f, false);
        playClientEffectObj(self, INSPECTION_BEGIN, self, "");
        return SCRIPT_CONTINUE;
    }
    public int finishInspection(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "inspecting"))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, INSPECTION_END, self, "");
        obj_id target = utils.getObjIdScriptVar(self, "inspecting");
        if (hasObjVar(target, "cargo"))
        {
            String cargo = getStringObjVar(target, "cargo");
            string_id cargo_id = new string_id("space/cargo", cargo);
            prose_package pp = prose.getPackage(SID_REPORT_CARGO, cargo_id);
            sendQuestSystemMessage(self, pp);
            space_quest._groupNotify(self, SID_REPORT_CARGO_GROUP, cargo_id);
            String strShipType = getStringObjVar(target, "ship.shipName");
            dictionary outparams = new dictionary();
            outparams.put("player", self);
            outparams.put("type", strShipType);
            outparams.put("cargo", cargo);
            space_quest.notifyMissions(self, "inspectedShip", outparams);
        }
        else 
        {
            string_id cargo_id = new string_id("space/cargo", "nothing");
            prose_package pp = prose.getPackage(SID_REPORT_CARGO, cargo_id);
            sendQuestSystemMessage(self, pp);
            space_quest._groupNotify(self, SID_REPORT_CARGO_GROUP, cargo_id);
        }
        utils.removeScriptVar(self, "inspecting");
        return SCRIPT_CONTINUE;
    }
    public int cmdDock(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (target == null)
        {
            sendQuestSystemMessage(self, SID_NO_TARGET);
            return SCRIPT_CONTINUE;
        }
        if (space_transition.getContainingShip(self) == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!space_utils.isShip(target))
        {
            return SCRIPT_CONTINUE;
        }
        String strChassisType = getShipChassisType(space_transition.getContainingShip(self));
        if (strChassisType.equals("player_sorosuub_space_yacht"))
        {
            sendQuestSystemMessage(self, SID_NOT_IN_SUUB);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(target, "piracyEventOwner"))
        {
            obj_id missionOwner = getObjIdObjVar(target, "piracyEventOwner");
            if (self != missionOwner)
            {
                sendSystemMessage(self, "You cannot dock with this target", null);
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(target, "escortIdArray"))
            {
                CustomerServiceLog("space_piracy", "Player " + missionOwner + "'s Piracy Event has encountered a problem. The target ship " + target + " is missing is list of escort ships.");
                return SCRIPT_CONTINUE;
            }
            obj_id[] escortShips = getObjIdArrayObjVar(target, "escortIdArray");
            for (int i = 0; i < escortShips.length; i++)
            {
                if (isIdValid(escortShips[i]) && exists(escortShips[i]))
                {
                    sendSystemMessage(self, "You must destroy all escort ships before you can dock.", null);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (utils.hasScriptVar(self, "docking"))
        {
            sendQuestSystemMessage(self, SID_ABORT_DOCKING);
            queueCommand(self, (-549690812), null, "", COMMAND_PRIORITY_IMMEDIATE);
            utils.removeScriptVar(self, "docking");
            return SCRIPT_CONTINUE;
        }
        if (_spaceUnitIsDocked(target))
        {
            sendQuestSystemMessage(self, SID_TARGET_DOCKING_ALREADY);
            return SCRIPT_CONTINUE;
        }
        boolean cargoTransfer = false;
        if (!utils.hasScriptVar(target, "dockable"))
        {
            if (hasObjVar(target, "isDisabled") && canTransferCargo(self, target))
            {
                cargoTransfer = true;
            }
            else 
            {
                sendQuestSystemMessage(self, SID_CANNOT_DOCK);
                return SCRIPT_CONTINUE;
            }
        }
        float dist = getDistance(space_transition.getContainingShip(self), target);
        if ((dist == -1) || (dist > 150))
        {
            sendQuestSystemMessage(self, SID_DOCK_TOO_FAR);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(target, "being_docked", 1);
        utils.setScriptVar(self, "docking", 1);
        if (cargoTransfer)
        {
            utils.setScriptVar(self, "transfer", 1);
        }
        playClientEffectObj(self, DOCK_START, self, "");
        sendQuestSystemMessage(self, SID_DOCKING);
        space_quest._groupNotify(self, SID_DOCKING_GROUP, null);
        ship_ai.unitDock(space_transition.getContainingShip(self), target, 15.f);
        dictionary outp = new dictionary();
        outp.put("target", target);
        space_quest.notifyMissions(self, "dockingStarted", outp);
        return SCRIPT_CONTINUE;
    }
    public int spaceUnitDocked(obj_id self, dictionary params) throws InterruptedException
    {
        sendQuestSystemMessage(self, SID_DOCK_ACHIEVED);
        return SCRIPT_CONTINUE;
    }
    public int spaceUnitUnDocked(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        dictionary outp = new dictionary();
        outp.put("target", target);
        space_quest.notifyMissions(self, "dockingComplete", outp);
        sendQuestSystemMessage(self, SID_DOCKING_COMPLETE);
        space_quest._groupNotify(self, SID_DOCKING_COMPLETE_GROUP, null);
        if (utils.hasScriptVar(self, "transfer"))
        {
            finishTransfer(self, target);
        }
        playClientEffectObj(self, DOCK_END, self, "");
        utils.removeScriptVar(target, "being_docked");
        utils.removeScriptVar(self, "docking");
        utils.removeScriptVar(self, "transfer");
        return SCRIPT_CONTINUE;
    }
    public boolean canTransferCargo(obj_id self, obj_id target) throws InterruptedException
    {
        if (!hasObjVar(target, "cargo"))
        {
            return false;
        }
        return true;
    }
    public void finishTransfer(obj_id self, obj_id target) throws InterruptedException
    {
        String cargo = getStringObjVar(target, "cargo");
        String cargo_type = getStringObjVar(target, "cargo_type");
        int[] can_xfer = dataTableGetIntColumn("datatables/spacequest/inspect_cargo/" + cargo_type + ".iff", "canTransfer");
        String[] cargo_types = dataTableGetStringColumn("datatables/spacequest/inspect_cargo/" + cargo_type + ".iff", "name");
        if (can_xfer != null)
        {
            for (int i = 0; i < can_xfer.length; i++)
            {
                if ((cargo_types[i].equals(cargo)) && (can_xfer[i] == 1))
                {
                    string_id cargo_id = new string_id("space/cargo", cargo);
                    prose_package pp = prose.getPackage(SID_REPORT_TRANSFER, cargo_id);
                    sendQuestSystemMessage(self, pp);
                    space_quest._groupNotify(self, SID_REPORT_TRANSFER_GROUP, cargo_id);
                    removeObjVar(target, "cargo");
                    if (cargo.equals("pirate_cargo"))
                    {
                        int difficulty = getIntObjVar(target, "difficulty");
                        if (difficulty <= 0 || difficulty > 5)
                        {
                            difficulty = 1;
                        }
                        obj_id pInv = utils.getInventoryContainer(self);
                        obj_id item = static_item.createNewItemFunction("item_interdiction_crate_01_01", pInv);
                        setObjVar(item, "difficulty", difficulty);
                        if (utils.isProfession(self, utils.SMUGGLER))
                        {
                            float underworldBonus = 10.f + ((float)difficulty * 10.f);
                            factions.addFactionStanding(self, "underworld", underworldBonus);
                        }
                        CustomerServiceLog("space_piracy", "Created 'Crate of Componenets in player " + self + "'s inventory (inventory OID: " + pInv);
                        return;
                    }
                    setObjVar(self, "cargo", cargo);
                    String strShipType = getStringObjVar(target, "ship.shipName");
                    dictionary outparams = new dictionary();
                    outparams.put("player", self);
                    outparams.put("cargo", cargo);
                    outparams.put("type", strShipType);
                    obj_id sourceSpawner = getObjIdObjVar(target, "objParent");
                    if (sourceSpawner != null)
                    {
                        String spawner_name = getStringObjVar(sourceSpawner, "strSpawnerName");
                        if (spawner_name != null)
                        {
                            outparams.put("spawner", spawner_name);
                        }
                    }
                    space_quest.notifyMissions(self, "recoveredCargo", outparams);
                    return;
                }
            }
        }
        sendQuestSystemMessage(self, SID_TRANSFER_CANT);
    }
    public int OnRemovedFromGroup(obj_id self, obj_id group) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "group_space_quest"))
        {
            utils.removeScriptVar(self, "group_space_quest");
            sendSystemMessage(self, LEFT_GROUP_QUEST);
        }
        return SCRIPT_CONTINUE;
    }
}
