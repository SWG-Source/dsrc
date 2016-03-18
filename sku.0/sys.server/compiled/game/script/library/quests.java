package script.library;

import script.*;

import java.util.Vector;

public class quests extends script.base_script
{
    public quests()
    {
    }
    public static final int IS_ON_QUEST = 1;
    public static final int COMPLETED_QUEST = 2;
    public static final int FAILED_QUEST = 3;
    public static void setQuestStatus(obj_id player, String questId, int questStatus) throws InterruptedException
    {
        setObjVar(player, "themeparkQuestFlags." + questId, questStatus);
        if ((questStatus == quests.COMPLETED_QUEST) || (questStatus == quests.FAILED_QUEST))
        {
            clearAllWaypoints(player, questId);
            removeObjVar(player, questId);
        }
    }
    public static boolean hasQuest(obj_id player, String QUEST_ID) throws InterruptedException
    {
        return getQuestStatus(player, QUEST_ID) == IS_ON_QUEST;
    }
    public static void removeQuestStatus(obj_id player, String questId) throws InterruptedException
    {
        removeObjVar(player, "themeparkQuestFlags." + questId);
        clearAllWaypoints(player, questId);
        removeObjVar(player, questId);
    }
    public static int getQuestStatus(obj_id player, String questId) throws InterruptedException
    {
        if (!hasObjVar(player, "themeparkQuestFlags." + questId))
        {
            return 0;
        }
        return getIntObjVar(player, "themeparkQuestFlags." + questId);
    }
    public static void completeQuest(obj_id player, String questId) throws InterruptedException
    {
        setQuestStatus(player, questId, quests.COMPLETED_QUEST);
    }
    public static boolean hasCompletedQuest(obj_id player, String questId) throws InterruptedException
    {
        return (getQuestStatus(player, questId) == quests.COMPLETED_QUEST);
    }
    public static void setQuestFlag(obj_id player, String questId, String flagname, int value) throws InterruptedException
    {
        setObjVar(player, questId + ".Flags." + flagname, value);
    }
    public static int getQuestFlag(obj_id player, String questId, String flagname) throws InterruptedException
    {
        if (!hasObjVar(player, questId + ".Flags." + flagname))
        {
            return 0;
        }
        return getIntObjVar(player, questId + ".Flags." + flagname);
    }
    public static void removeQuestFlag(obj_id player, String questId, String flagname) throws InterruptedException
    {
        removeObjVar(player, questId + ".Flags." + flagname);
    }
    public static final int CAN_INFILTRATE = 1;
    public static final int COMPLETED_INFILTRATE = 2;
    public static boolean canInfiltrate(obj_id player, String questId) throws InterruptedException
    {
        return hasObjVar(player, "themeparkQuestFlags.INFILTRATION." + questId);
    }
    public static void setInfiltrateFlag(obj_id player, String questId, int canInfiltrate) throws InterruptedException
    {
        if (canInfiltrate == CAN_INFILTRATE || canInfiltrate == COMPLETED_INFILTRATE)
        {
            setObjVar(player, "themeparkQuestFlags.INFILTRATION." + questId, canInfiltrate);
        }
        else 
        {
            removeObjVar(player, "themeparkQuestFlags.INFILTRATION." + questId);
        }
    }
    public static void removeInfiltrateFlag(obj_id player, String questId) throws InterruptedException
    {
        removeObjVar(player, "themeparkQuestFlags.INFILTRATION." + questId);
    }
    public static int getInfiltrateFlag(obj_id player, String questId) throws InterruptedException
    {
        return getIntObjVar(player, "themeparkQuestFlags.INFILTRATION." + questId);
    }
    public static obj_id addQuestLocationTarget(obj_id player, String questId, String name, location loc, float radius) throws InterruptedException
    {
        addLocationTarget(name, loc, radius);
        obj_id waypoint = createWaypointInDatapad(player, loc);
        if (!isIdValid(waypoint))
        {
            return null;
        }
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        setObjVar(player, questId + ".waypoint." + name, waypoint);
        return waypoint;
    }
    public static obj_id addThemeParkWaypoint(obj_id player, String questId, String name, location loc, float radius, String display, String file, String entry) throws InterruptedException
    {
        addLocationTarget(name, loc, radius);
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setWaypointActive(waypoint, true);
        setObjVar(player, questId + ".waypoint." + name, waypoint);
        setObjVar(player, "game_created", 1);
        setWaypointName(waypoint, display);
        setObjVar(player, questId + ".entry", entry);
        setObjVar(player, questId + ".file", file);
        // can't set objvars on waypoints I suppose.
        //setObjVar(waypoint, "player", player);
        setObjVar(player, "questID", questId);
        return waypoint;
    }
    public static void clearQuestLocationTarget(obj_id player, String questId, String name) throws InterruptedException
    {
        if (hasObjVar(player, questId + ".waypoint." + name))
        {
            obj_id waypoint = getObjIdObjVar(player, questId + ".waypoint." + name);
            setWaypointVisible(waypoint, false);
            setWaypointActive(waypoint, false);
            destroyObject(waypoint);
            removeLocationTarget(name);
            removeObjVar(player, questId + ".waypoint." + name);
        }
    }
    public static void clearAllWaypoints(obj_id player, String questId) throws InterruptedException
    {
        if (!hasObjVar(player, questId + ".waypoint"))
        {
            return;
        }
        obj_var_list waypointList = getObjVarList(player, questId + ".waypoint");
        if (waypointList == null)
        {
            return;
        }
        int count = waypointList.getNumItems();
        obj_var waypointVar;
        obj_id waypoint;
        for (int i = 0; i < count; ++i)
        {
            waypointVar = waypointList.getObjVar(i);
            waypoint = waypointVar.getObjIdData();
            setWaypointVisible(waypoint, false);
            setWaypointActive(waypoint, false);
            destroyObject(waypoint);
        }
        removeObjVar(player, questId + ".waypoint");
    }
    public static obj_id spawnAttacker(String msgHandlerName, String template, location loc, obj_id target) throws InterruptedException
    {
        obj_id attacker = createObject(template, loc);
        setObjVar(attacker, "quests.target", target);
        setObjVar(attacker, "quests.msgHandlerName", msgHandlerName);
        attachScript(attacker, "theme_park.utils.npcdeath");
        return attacker;
    }
    public static obj_id spawnAttacker(String template, location loc, obj_id target) throws InterruptedException
    {
        return createObject(template, loc);
    }
    public static obj_id[] spawnAttackers(String msgHandlerName, int number, String template, location loc, obj_id target) throws InterruptedException
    {
        obj_id[] attackers = new obj_id[number];
        location newloc;
        for (int i = 0; i < number; i++)
        {
            newloc = new location(loc);
            newloc.x = loc.x + rand(-2, +2);
            newloc.z = loc.z + rand(-2, +2);
            attackers[i] = spawnAttacker(msgHandlerName, template, newloc, target);
        }
        return attackers;
    }
    public static obj_id[] spawnAttackers(int number, String template, location loc, obj_id target) throws InterruptedException
    {
        obj_id[] attackers = new obj_id[number];
        location newloc;
        for (int i = 0; i < number; i++)
        {
            newloc = new location(loc);
            newloc.x = loc.x + rand(-2, +2);
            newloc.z = loc.z + rand(-2, +2);
            attackers[i] = spawnAttacker(template, newloc, target);
        }
        return attackers;
    }
    public static location chooseNearbyLocation(obj_id self) throws InterruptedException
    {
        location loc = new location(getLocation(self));
        switch (rand(1, 4))
        {
            case 1:
            loc.x = loc.x + 20;
            break;
            case 2:
            loc.x = loc.x - 20;
            break;
            case 3:
            loc.y = loc.y + 20;
            break;
            case 4:
            loc.y = loc.y - 20;
            break;
        }
        return loc;
    }
    public static location getTargetLocation(obj_id self) throws InterruptedException
    {
        location target = null;
        int x = 0;
        location questLoc;
        while (x < 10)
        {
            questLoc = locations.getGoodLocationOutsideOfRegion(locations.getCityRegion(getLocation(self)), 100f, 100f, 100f);
            if (questLoc != null)
            {
                target = questLoc;
            }
            x = x + 1;
        }
        return target;
    }
    public static String getConvoType(obj_id self) throws InterruptedException
    {
        String convoType;
        String type = "businessman";
        if (!checkForSerendipity(self))
        {
            if (!hasObjVar(self, "questType"))
            {
                switch (rand(1, 4))
                {
                    case 1:
                        type = "businessman";
                        break;
                    case 2:
                        type = "criminal";
                        break;
                    case 3:
                        type = "noble";
                        break;
                    case 4:
                        type = "scientist";
                        break;
                }
                convoType = "npc_mission/static_" + type + "_deliver";
            }
            else 
            {
                type = getStringObjVar(self, "questType");
                convoType = "npc_mission/static_" + type + "_deliver";
            }
            setObjVar(self, "questType", type);
            return convoType;
        }
        else 
        {
            type = getCreatureName(self);
            convoType = "npc_mission/static_" + type + "_deliver";
            setObjVar(self, "questType", type);
            return convoType;
        }
    }
    public static String getDataTableName(obj_id self) throws InterruptedException
    {
        String datatable;
        String type = "businessman";
        if (!checkForSerendipity(self))
        {
            if (!hasObjVar(self, "questType"))
            {
                switch (rand(1, 4))
                {
                    case 1:
                        type = "businessman";
                        break;
                    case 2:
                        type = "criminal";
                        break;
                    case 3:
                        type = "noble";
                        break;
                    case 4:
                        type = "scientist";
                        break;
                }
                datatable = "datatables/npc/static_quest/" + type + "_deliveries.iff";
            }
            else 
            {
                type = getStringObjVar(self, "questType");
                datatable = "datatables/npc/static_quest/" + type + "_deliveries.iff";
            }
            setObjVar(self, "questType", type);
            return datatable;
        }
        else 
        {
            type = getCreatureName(self);
            setObjVar(self, "questType", type);
            datatable = "datatables/npc/static_quest/" + type + "_deliveries.iff";
            return datatable;
        }
    }
    public static String getType(obj_id self) throws InterruptedException
    {
        String type = "businessman";
        if (!checkForSerendipity(self))
        {
            if (!hasObjVar(self, "questType"))
            {
                switch (rand(1, 4))
                {
                    case 1:
                        type = "businessman";
                        break;
                    case 2:
                        type = "criminal";
                        break;
                    case 3:
                        type = "noble";
                        break;
                    case 4:
                        type = "scientist";
                        break;
                }
            }
            else 
            {
                type = getStringObjVar(self, "questType");
            }
            return type;
        }
        else 
        {
            type = getCreatureName(self);
        }
        return type;
    }
    public static boolean checkForSerendipity(obj_id self) throws InterruptedException
    {
        final String npcType = getCreatureName(self);
        if (npcType.equals("noble") || npcType.equals("scientist") || npcType.equals("criminal") || npcType.equals("businessman"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean checkForItem(obj_id inv) throws InterruptedException
    {
        String giveMe = dataTableGetString(quests.getDataTableName(getSelf()), 0, getIntObjVar(getSelf(), "quest"));
        boolean hadIt = false;
        for (obj_id content : getContents(inv)) {
            if (getTemplateName(content).equals(giveMe)) {
                destroyObject(content);
                hadIt = true;
            }
        }
        return hadIt;
    }
    public static location getThemeParkLocation(obj_id self) throws InterruptedException
    {
        location target = null;

        obj_id bldg;
        location building;
        location questLoc;
        region[] rgnFoos;
        region[] rgnTest;
        region quest;

        int x = 0;
        while (x < 10)
        {
            bldg = getTopMostContainer(self);
            if (bldg == null)
            {
                return null;
            }
            building = getLocation(bldg);
            rgnFoos = getRegionsWithGeographicalAtPoint(building, regions.GEO_CITY);
            if (rgnFoos == null)
            {
                rgnTest = getRegionsAtPoint(building);
                if (rgnTest == null)
                {
                    return null;
                }
                else 
                {
                    rgnFoos = rgnTest;
                }
            }
            quest = rgnFoos[0];
            if (quest == null)
            {
                quest = locations.getCityRegion(building);
                if (quest == null)
                {
                    return null;
                }
            }
            questLoc = locations.getGoodLocationOutsideOfRegion(quest, 64f, 64f, 100f, false, true);
            if (questLoc != null)
            {
                target = questLoc;
            }
            x++;
        }
        if (target != null)
        {
            float xCoord = target.x;
            float zCoord = target.z;
            target.y = getHeightAtLocation(xCoord, zCoord);
        }
        return target;
    }
    public static void giveThemeParkReward(obj_id self, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = "datatables/theme_park/" + getStringObjVar(self, "quest_table") + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        String messageCONVO = "theme_park/messages";
        int gating = getIntObjVar(player, gatingString);
        if (group.isGrouped(player))
        {
            Vector party = group.getPCMembersInRange(player);
            if (party != null)
            {
                for (int i = 0; i < party.size(); i++)
                {
                    int partyMemberGating = getIntObjVar((obj_id)party.elementAt(i), gatingString);
                    if (partyMemberGating == 0)
                    {
                        partyMemberGating = 1;
                    }
                    if (gating == partyMemberGating)
                    {
                        gating = gating + 1;
                        setObjVar(player, gatingString, gating);
                        setObjVar((obj_id)party.elementAt(i), gatingString, gating);
                    }
                }
            }
        }
        else 
        {
            gating = gating + 1;
            setObjVar(player, gatingString, gating);
        }
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("rescue") || type.equals("escort"))
        {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }
        int badgeReward = dataTableGetInt(datatable, questNum, "badge_reward");
        String badgeName = getCollectionSlotName(badgeReward);
        if ((badgeReward != 0) && (badgeName != null) && (badgeName.length() > 0))
        {
            if (!badge.hasBadge(player, badgeName))
            {
                badge.grantBadge(player, badgeName);
            }
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        String reward = dataTableGetString(datatable, questNum, "reward");
        String reward2 = dataTableGetString(datatable, questNum, "reward2");
        String reward3 = dataTableGetString(datatable, questNum, "reward3");
        String reward4 = dataTableGetString(datatable, questNum, "reward4");
        if (reward != null && !reward.equals("") && !reward.equals("none"))
        {
            obj_id rewardObject = createObject(reward, playerInv, "");
            string_id gift = new string_id(messageCONVO, "theme_park_reward");
            sendSystemMessage(player, gift);
            String objvar = dataTableGetString(datatable, questNum, "reward_objvar");
            int value = dataTableGetInt(datatable, questNum, "reward_objvar_value");
            if (objvar != null)
            {
                setObjVar(rewardObject, objvar, value);
            }
        }
        if (reward2 != null && !reward2.equals("") && !reward2.equals("none"))
        {
            obj_id rewardObject2 = createObject(reward2, playerInv, "");
            string_id gift2 = new string_id(messageCONVO, "theme_park_reward");
            sendSystemMessage(player, gift2);
            String objvar2 = dataTableGetString(datatable, questNum, "reward2_objvar");
            int value2 = dataTableGetInt(datatable, questNum, "reward2_objvar_value");
            if (objvar2 != null)
            {
                setObjVar(rewardObject2, objvar2, value2);
            }
        }
        if (reward3 != null && !reward3.equals("") && reward2 != null && !reward2.equals("none"))
        {
            obj_id rewardObject3 = createObject(reward3, playerInv, "");
            string_id gift3 = new string_id(messageCONVO, "theme_park_reward");
            sendSystemMessage(player, gift3);
            String objvar3 = dataTableGetString(datatable, questNum, "reward3_objvar");
            int value3 = dataTableGetInt(datatable, questNum, "reward3_objvar_value");
            if (objvar3 != null)
            {
                setObjVar(rewardObject3, objvar3, value3);
            }
        }
        if (reward4 != null && !reward4.equals("") && reward2 != null && !reward2.equals("none"))
        {
            obj_id rewardObject4 = createObject(reward4, playerInv, "");
            string_id gift4 = new string_id(messageCONVO, "theme_park_reward");
            sendSystemMessage(player, gift4);
            String objvar4 = dataTableGetString(datatable, questNum, "reward4_objvar");
            int value4 = dataTableGetInt(datatable, questNum, "reward4_objvar_value");
            if (objvar4 != null)
            {
                setObjVar(rewardObject4, objvar4, value4);
            }
        }
        int credits = dataTableGetInt(datatable, questNum, "credits");
        if (credits != 0)
        {
            string_id fake = new string_id();
            dictionary params = new dictionary();
            group.systemPayoutToGroup("reasonID", player, credits, fake, "returnHandler", params);
            string_id credsMessage = new string_id(messageCONVO, "theme_park_credits");
            sendSystemMessage(player, credsMessage);
        }
        String factionReward = dataTableGetString(datatable, questNum, "faction reward");
        String factionReward2 = dataTableGetString(datatable, questNum, "faction_reward2");
        String factionReward3 = dataTableGetString(datatable, questNum, "faction_reward3");
        String factionReward4 = dataTableGetString(datatable, questNum, "faction_reward4");
        if (!factionReward.equals("none"))
        {
            int factionAmt = dataTableGetInt(datatable, questNum, "faction_reward_amount");
            if (factionAmt != 0)
            {
                factions.addFactionStanding(player, factionReward, factionAmt);
            }
        }
        if (!factionReward2.equals("none"))
        {
            int factionAmt2 = dataTableGetInt(datatable, questNum, "faction_reward2_amount");
            if (factionAmt2 != 0)
            {
                factions.addFactionStanding(player, factionReward2, factionAmt2);
            }
        }
        if (!factionReward3.equals("none"))
        {
            int factionAmt3 = dataTableGetInt(datatable, questNum, "faction_reward3_amount");
            if (factionAmt3 != 0)
            {
                factions.addFactionStanding(player, factionReward3, factionAmt3);
            }
        }
        if (!factionReward4.equals("none"))
        {
            int factionAmt4 = dataTableGetInt(datatable, questNum, "faction_reward4_amount");
            if (factionAmt4 != 0)
            {
                factions.addFactionStanding(player, factionReward4, factionAmt4);
            }
        }
        obj_id waypoint = getObjIdObjVar(player, questID + ".waypointhome");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(player, questID);
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");
        if (hasScript(player, playerScript))
        {
            detachScript(player, playerScript);
        }
    }
    public static int getQuestId(String questName) throws InterruptedException
    {
        return dataTableSearchColumnForString(questName, 0, "datatables/player/quests.iff");
    }
    public static String getParentQuestName(String questName) throws InterruptedException
    {
        int questRow = getQuestId(questName);
        if (questRow > -1)
        {
            return dataTableGetString("datatables/player/quests.iff", questRow, "PARENT");
        }
        return null;
    }
    public static boolean isComplete(String questName, obj_id player) throws InterruptedException
    {
        return isQuestComplete(player, getQuestId(questName));
    }
    public static boolean canActivate(String questName, obj_id player) throws InterruptedException
    {
        boolean result = false;
        if (!isActive(questName, player))
        {
            String parentQuestName = getParentQuestName(questName);
            if (parentQuestName != null && parentQuestName.length() > 0)
            {
                if (isComplete(parentQuestName, player))
                {
                    if (!isComplete(questName, player))
                    {
                        LOG("newquests", (questName + " is available because the parent quest has been completed and " + questName + " has not been completed"));
                        result = true;
                    }
                    else 
                    {
                        LOG("newquests", (questName + " has already been completed"));
                    }
                }
                else 
                {
                    LOG("newquests", (questName + " has a parent, " + parentQuestName + " but the parent quest is not complete"));
                }
            }
            else 
            {
                if (!isActive(questName, player))
                {
                    if (!isComplete(questName, player))
                    {
                        result = true;
                    }
                }
            }
        }
        else 
        {
            LOG("newquests", (questName + " is active"));
        }
        LOG("newquests", ("canActivate(" + questName + ", " + player + ") returned " + result));
        return result;
    }
    public static boolean isActive(String questName, obj_id player) throws InterruptedException
    {
        boolean result = false;
        int questRow = getQuestId(questName);
        if (questRow > -1)
        {
            result = isQuestActive(player, questRow);
        }
        return result;
    }
    public static void activate(String questName, obj_id player, obj_id questGiver) throws InterruptedException
    {
        if (questName == null)
        {
            return;
        }
        if (questName.length() < 1)
        {
            return;
        }
        LOG("newquests", "attempting to activate quest " + questName + " for player " + player + " npc " + questGiver);
        String datatable = "datatables/player/quests.iff";
        int questRow = getQuestId(questName);
        if (questRow > -1)
        {
            LOG("newquests", "found " + questName + " id=" + questRow);
            if (canActivate(questName, player))
            {
                String scriptName = dataTableGetString(datatable, questRow, "ATTACH_SCRIPT");
                if (scriptName != null && scriptName.length() > 0)
                {
                    LOG("newquests", "attaching script " + scriptName + " to player " + player + " for quest " + questName);
                    attachScript(player, scriptName);
                }
                LOG("newquests", "activating quest " + questName + " for player " + player);
                if (questGiver != null)
                {
                    String objvarname = "questlib." + questName + ".quest_giver";
                    LOG("newquests", "quests: activate setObjVar(" + player + ", " + objvarname + ", " + questGiver + ")");
                    setObjVar(player, objvarname, questGiver);
                    questGiver = getObjIdObjVar(player, objvarname);
                    LOG("newquests", "quests: activate, checking getObjIdObVar for quest giver. questGiver = " + questGiver);
                }
                activateQuest(player, questRow);
                int row = dataTableSearchColumnForString(questName, "NAME", "datatables/player/quests.iff");
                int isVisible = dataTableGetInt("datatables/player/quests.iff", row, "SHOW_SYSTEM_MESSAGES");
                if (isVisible != 0)
                {
                    string_id sid = new string_id("quest/quests", "quest_journal_updated");
                    sendSystemMessage(player, sid);
                }
            }
            else 
            {
                LOG("newquests", "quest " + questName + " for player " + player + " could not be activated");
            }
        }
        else 
        {
            LOG("newquests", "could not activate quest " + questName + " for player " + player + " because the quest could not be found in " + datatable);
        }
    }
    public static void complete(String questName, obj_id player, boolean succeeded) throws InterruptedException
    {
        LOG("newquest", "quests: complete(" + questName + ", " + player + ", " + succeeded + ")");
        int row = dataTableSearchColumnForString(questName, "NAME", "datatables/player/quests.iff");
        int isVisible = dataTableGetInt("datatables/player/quests.iff", row, "SHOW_SYSTEM_MESSAGES");
        if (isVisible != 0)
        {
            String entry = getDataEntry(questName, "JOURNAL_ENTRY_SUMMARY");
            if (entry != null && entry.length() > 0)
            {
                String[] entries = split(entry, ':');
                if (entries.length > 1)
                {
                    string_id qid = utils.unpackString(entry);
                    sendSystemMessage(player, qid);
                }
            }
            if (succeeded)
            {
                string_id sid = new string_id("quest/quests", "task_complete");
                sendSystemMessage(player, sid);
            }
            else 
            {
                string_id sid = new string_id("quest/quests", "task_failure");
                sendSystemMessage(player, sid);
            }
        }
        obj_id questGiver = null;
        String objvarname = "questlib." + questName + ".quest_giver";
        if (hasObjVar(player, objvarname))
        {
            questGiver = getObjIdObjVar(player, objvarname);
            LOG("newquests", "quests: hasObjVar(" + player + ", " + objvarname + ") = true. setting questGiver=" + questGiver);
        }
        else 
        {
            LOG("newquests", "quests: hasObjVar(" + player + ", " + objvarname + ") = false");
        }
        deactivate(questName, player);
        completeQuest(player, getQuestId(questName));
        Object[] params = new Object[3];
        params[0] = player;
        params[1] = questName;
        params[2] = new Boolean(succeeded);
        script_entry.runScripts("OnForceSensitiveQuestCompleted", params);
        if (hasObjVar(player, "questlib." + questName))
        {
            removeObjVar(player, "questlib." + questName);
        }
        String[] nextTasks = null;
        if (succeeded)
        {
            String taskList = getDataEntry(questName, "TASK_ON_COMPLETE");
            nextTasks = split(taskList, ';');
        }
        else 
        {
            String taskList = getDataEntry(questName, "TASK_ON_FAIL");
            nextTasks = split(taskList, ';');
        }
        if (nextTasks != null && nextTasks.length > 0)
        {
            int iter = 0;
            for (iter = 0; iter < nextTasks.length; ++iter)
            {
                activate(nextTasks[iter], player, questGiver);
            }
        }
        if (hasObjVar(player, "quest." + questName))
        {
            removeObjVar(player, "quest." + questName);
        }
    }
    public static void deactivate(String questName, obj_id player) throws InterruptedException
    {
        String datatable = "datatables/player/quests.iff";
        int questRow = getQuestId(questName);
        if (questRow > -1)
        {
            String scriptName = dataTableGetString(datatable, questRow, "ATTACH_SCRIPT");
            if (hasScript(player, scriptName))
            {
                int i = 0;
                int columnCount = dataTableGetNumRows(datatable);
                for (i = 0; i < columnCount; ++i)
                {
                    if (i != questRow)
                    {
                        String s = dataTableGetString(datatable, i, "ATTACH_SCRIPT");
                        if (s != null && s.equals(scriptName) && isQuestActive(player, i))
                        {
                            LOG("newquests", "not detaching script " + scriptName + " beacuse quest " + i + " is currently active and uses " + scriptName);
                            return;
                        }
                    }
                }
                LOG("newquests", "deatching script " + scriptName + " from player " + player);
                detachScript(player, scriptName);
            }
            deactivateQuest(player, questRow);
        }
    }
    public static String[] getActiveQuestsWithScript(String scriptName, obj_id player) throws InterruptedException
    {
        Vector result = new Vector();
        String datatable = "datatables/player/quests.iff";
        int it = 0;
        int columnCount = dataTableGetNumRows(datatable);
        for (it = 0; it < columnCount; ++it)
        {
            String s = dataTableGetString(datatable, it, "ATTACH_SCRIPT");
            if (s != null)
            {
                if (s.equals(scriptName))
                {
                    String taskName = dataTableGetString(datatable, it, "NAME");
                    if (isActive(taskName, player))
                    {
                        result.add(taskName);
                    }
                }
            }
        }
        String[] _result = new String[0];
        _result = new String[result.size()];
        result.toArray(_result);
        return _result;
    }
    public static String getDataEntry(String questName, String columnName) throws InterruptedException
    {
        String result = null;
        int questRow = getQuestId(questName);
        if (questRow > -1)
        {
            result = dataTableGetString("datatables/player/quests.iff", questRow, columnName);
        }
        return result;
    }
    public static String getDataEntry(int questId, String columnName) throws InterruptedException
    {
        String result = null;
        if (questId > -1)
        {
            result = dataTableGetString("datatables/player/quests.iff", questId, columnName);
        }
        return result;
    }
    public static location getTheaterLocationTarget(obj_id self, int questRow) throws InterruptedException
    {
        location result = null;
        String questName = quests.getDataEntry(questRow, "NAME");
        if (questName == null || questName.equals("") || !isActive(questName, self))
        {
            return null;
        }
        String target = "";
        float radius = 64.0f;
        float parameter = 1024.0f;
        location waitForPlanetWarp = null;
        boolean haveParameter = false;
        if (hasObjVar(self, "quest." + questName + ".parameter"))
        {
            radius = getFloatObjVar(self, "quest." + questName + ".parameter");
        }
        if (hasObjVar(self, "quest." + questName + ".target"))
        {
            target = getStringObjVar(self, "quest." + questName + ".target");
        }
        else 
        {
            String parameterString = quests.getDataEntry(questRow, "PARAMETER");
            if (parameterString != null && !parameterString.equals(""))
            {
                LOG("newquests", "GET THEATER LOCATION TARGET: parsing parameter string: " + parameterString);
                String[] params = split(parameterString, ':');
                for (int i = 0; i < params.length; i++)
                {
                    if (Character.isDigit(params[i].charAt(0)))
                    {
                        parameter = utils.stringToFloat(params[i]);
                        haveParameter = true;
                        LOG("newquests", "GET THEATER LOCATION TARGET: search radius parameter found: " + parameter);
                    }
                }
            }
            target = quests.getDataEntry(questRow, "TARGET");
        }
        if (hasObjVar(self, "theaterRecoveryTarget"))
        {
            result = getLocationObjVar(self, "theaterRecoveryTarget");
            if (result != null)
            {
                addLocationTarget(questName, result, radius);
                return result;
            }
        }
        if (target == null || target.equals(""))
        {
            return null;
        }
        boolean havePlanet = false;
        String[] tokens = split(target, ':');
        String planet = null;
        if (tokens.length > 1)
        {
            havePlanet = true;
        }
        if (havePlanet)
        {
            if (!tokens[0].contains(".iff"))
            {
                planet = tokens[0];
            }
            else 
            {
                planet = tokens[1];
            }
        }
        if (haveParameter && (parameter < 0))
        {
            parameter = -parameter;
            parameter = rand(parameter - 200.0f, parameter + 200.0f);
        }
        if (!havePlanet && !haveParameter)
        {
            int attempts = 0;
            while (result == null && attempts < 15)
            {
                ++attempts;
                result = locations.getRandomGoodLocation(getLocation(self), 800.0f, 1500.0f, 32.0f);
                if (result != null)
                {
                    LOG("newquests", "GET THEATER LOCATION TARGET: no planet/no parameter - good location attempt: " + result.toString());
                }
                else 
                {
                    LOG("newquests", "GET THEATER LOCATION TARGET: no planet/no parameter - good location attempt: null");
                }
            }
        }
        else if (!havePlanet)
        {
            int attempts = 0;
            while (result == null && attempts < 15)
            {
                ++attempts;
                result = locations.getRandomGoodLocation(getLocation(self), parameter - 200.0f, parameter + 600.0f, 32.0f);
                if (result != null)
                {
                    LOG("newquests", "GET THEATER LOCATION TARGET: no planet/parameter - good location attempt: " + result.toString());
                }
                else 
                {
                    LOG("newquests", "GET THEATER LOCATION TARGET: no planet/parameter - good location attempt: null");
                }
            }
        }
        else if (haveParameter)
        {
            String currentScene = getCurrentSceneName();
            if (currentScene != null && currentScene.equals(planet))
            {
                int attempts = 0;
                while (result == null && attempts < 15)
                {
                    ++attempts;
                    result = locations.getRandomGoodLocation(getLocation(self), parameter - 200.0f, parameter + 600.0f, 32.0f);
                    if (result != null)
                    {
                        LOG("newquests", "GET THEATER LOCATION TARGET: planet/parameter - good location attempt: " + result.toString());
                    }
                    else 
                    {
                        LOG("newquests", "GET THEATER LOCATION TARGET: planet/parameter - good location attempt: null");
                    }
                }
            }
            else 
            {
                LOG("newquests", "GET THEATER LOCATION TARGET: planet/parameter - good location fall through: waiting for planet warp");
                waitForPlanetWarp = new location(0.0f, 0.0f, 0.0f, planet);
            }
        }
        else {
            String currentScene = getCurrentSceneName();
            if (currentScene != null && currentScene.equals(planet))
            {
                int attempts = 0;
                while (result == null && attempts < 15)
                {
                    ++attempts;
                    result = locations.getRandomGoodLocation(getLocation(self), 800.0f, 1500.0f, 32.0f);
                    if (result != null)
                    {
                        LOG("newquests", "GET THEATER LOCATION TARGET: planet/no parameter - good location attempt: " + result.toString());
                    }
                    else 
                    {
                        LOG("newquests", "GET THEATER LOCATION TARGET: planet/no parameter - good location attempt: null");
                    }
                }
            }
            else 
            {
                LOG("newquests", "GET THEATER LOCATION TARGET: planet/parameter - good location fall through: waiting for planet warp");
                waitForPlanetWarp = new location(0.0f, 0.0f, 0.0f, planet);
            }
        }
        if (result != null)
        {
            region r = battlefield.getBattlefield(result);
            if (r != null)
            {
                if (isGod(self))
                {
                    sendSystemMessageTestingOnly(self, "GOD MODE - WARNING: The good location chosen for the theater lies within a battlefield");
                }
                LOG("newquests", "the good location chosen for the theater lies within a battlefield: " + result.toString());
            }
            LOG("newquests", "GET THEATER LOCATION TARGET: good location found: " + result.toString());
            setObjVar(self, "quest." + questName + ".selected_location", result);
            if (!hasObjVar(self, "theaterRecoveryTarget"))
            {
                if ((questName.equals("fs_theater_final")) || (questName.equals("fs_theater_camp")))
                {
                    setObjVar(self, "theaterRecoveryTarget", result);
                }
            }
            addLocationTarget(questName, result, radius);
        }
        else if (waitForPlanetWarp != null)
        {
            setObjVar(self, "quest." + questName + ".generate", waitForPlanetWarp);
        }
        return result;
    }
    public static location getLocationTarget(obj_id self, int questRow) throws InterruptedException
    {
        location result = null;
        String questName = quests.getDataEntry(questRow, "NAME");
        if (questName != null && questName.length() > 0 && isActive(questName, self))
        {
            LOG("newquests", "initializing location task " + questName);
            float radius = 64.0f;
            boolean waitForPlanetWarp = false;
            if (hasObjVar(self, "quest." + questName + ".parameter"))
            {
                radius = getFloatObjVar(self, "quest." + questName + ".parameter");
                LOG("newquests", "the target location radius is overridden by an object variable. radius = " + radius);
            }
            else 
            {
                LOG("newquests", "default target radius of 64 meters will be used");
            }
            if (hasObjVar(self, "quest." + questName + ".target"))
            {
                result = getLocationObjVar(self, "quest." + questName + ".target");
                if (result != null)
                {
                    LOG("newquests", "location target for " + questName + " is overridden by an object variable. new location is " + result);
                }
                else 
                {
                    LOG("newquests", "location target for " + questName + " was supposed to be overridden, but the location could not be retrieved from the location object variable quest." + questName + ".target");
                }
            }
            else 
            {
                String planetName = null;
                obj_id cell = null;
                boolean haveCell = false;
                float x = 0.0f;
                boolean haveX = false;
                float z = 0.0f;
                boolean haveZ = false;
                float parameter = 1024.0f;
                boolean haveParameter = false;
                boolean haveTarget = false;
                String parameterString = quests.getDataEntry(questRow, "PARAMETER");
                if (parameterString != null && parameterString.length() > 0)
                {
                    LOG("newquests", "location parsing PARAMETER=" + parameterString);
                    parameter = utils.stringToFloat(parameterString);
                    haveParameter = true;
                }
                LOG("newquests", "location PARAMETER set to " + parameter);
                String locationString = quests.getDataEntry(questRow, "TARGET");
                if (locationString != null && locationString.length() > 0)
                {
                    haveTarget = true;
                    LOG("newquests", "location parsing TARGET \"" + locationString + "\"");
                    String[] tokens = split(locationString, ':');
                    if (tokens.length > 0)
                    {
                        LOG("newquests", "location parsing " + tokens.length + " tokens");
                        String s = tokens[0];
                        if (!Character.isDigit(s.charAt(0)))
                        {
                            planetName = tokens[0];
                            LOG("newquests", "location parsed " + tokens[0] + " as the target planet");
                            if (tokens.length > 2)
                            {
                                LOG("newquests", "location parsing " + tokens[1] + " as x coordinate");
                                x = utils.stringToFloat(tokens[1]);
                                haveX = true;
                                LOG("newquests", "location parsing " + tokens[2] + " as z coordinate");
                                z = utils.stringToFloat(tokens[2]);
                                haveZ = true;
                                if (tokens.length > 3)
                                {
                                    LOG("newquests", "location parsing " + tokens[3] + " as a cell ID");
                                    cell = utils.stringToObjId(tokens[3]);
                                    haveCell = true;
                                }
                            }
                        }
                        else 
                        {
                            LOG("newquests", "location first token is a number");
                            if (tokens.length > 1)
                            {
                                x = utils.stringToFloat(tokens[0]);
                                z = utils.stringToFloat(tokens[1]);
                            }
                        }
                    }
                }
                if (!haveTarget && !haveParameter)
                {
                    int attempts = 0;
                    while (result == null && attempts < 15)
                    {
                        ++attempts;
                        result = locations.getRandomGoodLocation(getLocation(self), 800.0f, 1200.0f, 32.0f);
                        LOG("newquests", "location generating a random location about 1 kilometer away " + result);
                    }
                }
                else if (!haveTarget)
                {
                    result = locations.getRandomGoodLocation(getLocation(self), parameter - 100.0f, parameter + 100.0f, 32.0f);
                    LOG("newquests", "location generating a random location between " + (parameter - 100.0f) + " and " + (parameter + 100.0f) + " meters away");
                }
                else if (planetName != null && !haveX && haveParameter)
                {
                    String currentScene = getCurrentSceneName();
                    if (currentScene != null && currentScene.equals(planetName))
                    {
                        result = locations.getRandomGoodLocation(getLocation(self), parameter - 100.0f, parameter + 100.0f, 32.0f);
                        LOG("newquests", "location generating a random location on " + planetName + " between " + (parameter - 100.0f) + " and " + (parameter + 100.0f) + " meters away");
                    }
                    else 
                    {
                        waitForPlanetWarp = true;
                        LOG("newquests", "location generating a random location on " + planetName + " between " + (parameter - 100.0f) + " and " + (parameter + 100.0f) + " meters away");
                    }
                }
                else if (planetName != null && !haveX)
                {
                    String currentScene = getCurrentSceneName();
                    if (currentScene != null && currentScene.equals(planetName))
                    {
                        result = locations.getRandomGoodLocation(getLocation(self), parameter - 800.0f, 1200.0f, 32.0f);
                        LOG("newquests", "location generating a random location on " + planetName + " between 800 and 1200 meters away");
                    }
                    else 
                    {
                        waitForPlanetWarp = true;
                        LOG("newquests", "location a location will be generated when the player travels to " + planetName);
                    }
                }
                else if (planetName != null && !haveCell && !haveParameter)
                {
                    result = new location();
                    result.x = x;
                    result.z = z;
                    result.area = planetName;
                    LOG("newquests", "location generating a location at " + result);
                }
                else if (planetName != null && !haveCell)
                {
                    result = new location();
                    result.x = x;
                    result.z = z;
                    result.area = planetName;
                    radius = parameter;
                    LOG("newquests", "location generating a location at " + result);
                }
                else if (planetName != null && !haveParameter)
                {
                    result = new location();
                    result.x = x;
                    result.z = z;
                    result.area = planetName;
                    result.cell = cell;
                    LOG("newquests", "location generating a location at " + result);
                }
                else if (planetName != null)
                {
                    result = new location();
                    result.x = x;
                    result.z = z;
                    result.area = planetName;
                    result.cell = cell;
                    radius = parameter;
                    LOG("newquests", "location generating a location at " + result);
                }
            }
            if (result != null)
            {
                setObjVar(self, "quest." + questName + ".selected_location", result);
                addLocationTarget(questName, result, radius);
                LOG("newquests", "location adding location target at " + result);
            }
            else if (waitForPlanetWarp)
            {
                setObjVar(self, "quest." + questName + ".generate", waitForPlanetWarp);
            }
        }
        return result;
    }
    public static boolean isMyQuest(int questRow, String scriptName) throws InterruptedException
    {
        boolean result = false;
        String s = getDataEntry(questRow, "ATTACH_SCRIPT");
        if ((s != null) && (scriptName != null) && (s.equals(scriptName)))
        {
            result = true;
        }
        return result;
    }
    public static obj_id getTargetForQuest(obj_id self, String questName) throws InterruptedException
    {
        String objvarname = "encounter.target." + questName;
        obj_id result = self.getScriptVars().getObjId(objvarname);
        LOG("newquests", "quest target " + objvarname + "=" + result + " self=" + self);
        return result;
    }
    public static int getQuestIdForTarget(obj_id self, obj_id target) throws InterruptedException
    {
        int result = -1;
        String objvarname = "questname";
        if (isIdValid(target))
        {
            if (hasObjVar(target, objvarname))
            {
                String encounterQuestName = getStringObjVar(target, objvarname);
                if (encounterQuestName != null && encounterQuestName.length() > 0)
                {
                    int numRows = dataTableGetNumRows("datatables/player/quests.iff");
                    int iter = 0;
                    for (iter = 0; iter < numRows; ++iter)
                    {
                        if (isQuestActive(self, iter))
                        {
                            LOG("newquests", "destroy - checking active quest " + iter);
                            if (quests.isMyQuest(iter, "quest.task.destroy"))
                            {
                                LOG("newquest", "destroy - active quest " + iter + " is a destroy task");
                                String questName = quests.getDataEntry(iter, "NAME");
                                String targetName = null;
                                objvarname = "quest." + questName + ".target";
                                if (hasObjVar(self, objvarname))
                                {
                                    targetName = getStringObjVar(self, objvarname);
                                    LOG("newquests", "destroy - target is overriden by objvar " + objvarname + " target=" + targetName);
                                }
                                else 
                                {
                                    targetName = quests.getDataEntry(iter, "TARGET");
                                    LOG("newquests", "destroy - using data table target name " + targetName);
                                }
                                if (targetName != null && targetName.length() > 0)
                                {
                                    if (targetName.equals(encounterQuestName))
                                    {
                                        objvarname = targetName + ".holder";
                                        LOG("newquests", "destroy - target is a quest target, maybe for this player, checking to see if the target has a holder objvar (" + objvarname + ")");
                                        if (hasObjVar(target, objvarname))
                                        {
                                            obj_id holder = getObjIdObjVar(target, objvarname);
                                            LOG("newquests", "destroy - target has the " + objvarname + " objvar. It is set to " + holder);
                                            if (holder == self)
                                            {
                                                LOG("newquests", "destroy - target is ours");
                                                result = iter;
                                                break;
                                            }
                                        }
                                        else 
                                        {
                                            LOG("newquests", "destroy - target does not have the " + objvarname + " objvar");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else 
                {
                    LOG("newquests", "destroy - encounterName could not be determined");
                }
            }
            else 
            {
                LOG("newquests", "the " + objvarname + " objvar was not present");
            }
        }
        else 
        {
            LOG("newquests", "destroy - target id " + target + " is not valid");
        }
        return result;
    }
    public static boolean doEncounterSpawn(obj_id self, int questRow) throws InterruptedException
    {
        boolean success = false;
        LOG("newquests", "encounter activating");
        String questName = quests.getDataEntry(questRow, "NAME");
        String objvarname = "quest." + questName + ".target";
        String creatureName = null;
        if (hasObjVar(self, objvarname))
        {
            creatureName = getStringObjVar(self, objvarname);
            LOG("newquests", "encounter - target is overridden by " + objvarname + " (" + creatureName + ")");
        }
        else 
        {
            creatureName = quests.getDataEntry(questRow, "TARGET");
            LOG("newquests", "encounter - target is " + creatureName);
        }
        LOG("newquests", "encounter - getting a spawn location");
        float yaw = getYaw(self);
        location spawnLocation = utils.getLocationInArc(self, yaw - 30, yaw + 30, 48.0f);
        location l = locations.getGoodLocationAroundLocation(spawnLocation, 8, 8, 64, 64, false, true);
        if (l != null)
        {
            LOG("newquests", "encounter - found a location " + l);
            if (creatureName != null && creatureName.length() > 0)
            {
                LOG("newquests", "encounter - attempting to spawn " + creatureName);
                obj_id target = create.createCreature(creatureName, l, true);
                if (target != null)
                {
                    LOG("newquests", "encounter - " + creatureName + " spawned");
                    objvarname = "questname";
                    setObjVar(target, objvarname, questName);
                    objvarname = questName + ".holder";
                    setObjVar(target, objvarname, self);
                    deltadictionary scriptVars = self.getScriptVars();
                    objvarname = "encounter.target." + questName;
                    scriptVars.put(objvarname, target);
                    LOG("newquests", "encounter - adding " + target + " to " + objvarname + " script variable");
                    success = true;
                }
            }
        }
        else 
        {
            LOG("newquests", "encounter - failed to get a spawn location");
        }
        return success;
    }
    public static obj_id createSpawner(String spawner, location loc, String datatable) throws InterruptedException
    {
        return createSpawner(spawner, loc, datatable, null);
    }
    public static obj_id createSpawner(String spawner, location loc, String datatable, obj_id parent) throws InterruptedException
    {
        if (spawner == null || spawner.length() < 1)
        {
            LOG("quest", "quests.createSpawner -- spawner is null or empty");
            return null;
        }
        if (loc == null)
        {
            LOG("quest", "quests.createSpawner -- loc is null");
            return null;
        }
        if (datatable == null || datatable.length() < 1)
        {
            LOG("quest", "quests.createSpawner -- datatable is null or empty");
            return null;
        }
        int index = dataTableSearchColumnForString(spawner, "type", datatable);
        if (index == -1)
        {
            LOG("quest", "quests.createSpawner -- can't find spawner " + spawner + " within datatable " + datatable);
            return null;
        }
        dictionary row = dataTableGetRow(datatable, index);
        if (row == null)
        {
            LOG("quest", "quests.createSpawner -- can't find data in row " + index + " for datatable " + datatable);
            return null;
        }
        int pulse = row.getInt("pulse");
        int max_spawn = row.getInt("max_spawn");
        int max_population = row.getInt("max_population");
        int expire = row.getInt("expire");
        obj_id spawner_object = createObject("object/tangible/spawning/quest_spawner.iff", loc);
        if (!isIdValid(spawner_object))
        {
            LOG("quest", "quests.createSpawner -- can't create spawner object.");
            return null;
        }
        setObjVar(spawner_object, "quest_spawner.type", spawner);
        setObjVar(spawner_object, "quest_spawner.pulse", pulse);
        setObjVar(spawner_object, "quest_spawner.max_spawn", max_spawn);
        setObjVar(spawner_object, "quest_spawner.max_pop", max_population);
        if (expire > 1)
        {
            setObjVar(spawner_object, "quest_spawner.time_expired", expire + getGameTime());
        }
        else 
        {
            setObjVar(spawner_object, "quest_spawner.time_expired", 0);
        }
        setObjVar(spawner_object, "quest_spawner.datatable", datatable);
        if (parent != null)
        {
            setObjVar(spawner_object, "quest_spawner.parent", parent);
        }
        return spawner_object;
    }
    public static obj_id getQuestGiver(String questName, obj_id player) throws InterruptedException
    {
        obj_id result = null;
        String objvarname = "questlib." + questName + ".quest_giver";
        if (hasObjVar(player, objvarname))
        {
            result = getObjIdObjVar(player, objvarname);
        }
        return result;
    }
    public static obj_id getQuestGiver(int questRow, obj_id player) throws InterruptedException
    {
        return getQuestGiver(getDataEntry(questRow, "NAME"), player);
    }
    public static boolean safeHasObjVar(obj_id self, String objvarName) throws InterruptedException
    {
        obj_var ov = getObjVar(self, objvarName);
        return ov != null && ((ov.getName()).equals(objvarName));
    }
}
