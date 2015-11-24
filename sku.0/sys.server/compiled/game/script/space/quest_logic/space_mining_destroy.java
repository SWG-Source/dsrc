package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.space_utils;
import script.library.space_transition;

public class space_mining_destroy extends script.base_script
{
    public space_mining_destroy()
    {
    }
    public static final string_id SID_REMAINDER_UPDATE = new string_id("space/quest", "mining_remainder_update");
    public static final string_id SID_ABANDONED_DESTROY = new string_id("space/quest", "mining_abandoned");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        if ((questName == null) || (questType == null))
        {
            return SCRIPT_CONTINUE;
        }
        String qTable = "datatables/spacequest/" + questType + "/" + questName + ".iff";
        dictionary questInfo = dataTableGetRow(qTable, 0);
        if (questInfo == null)
        {
            sendSystemMessageTestingOnly(player, "Debug: Failed to open quest table " + qTable);
            return SCRIPT_CONTINUE;
        }
        String asteroids = questInfo.getString("asteroidType");
        int mineCount = questInfo.getInt("mineCount");
        setObjVar(self, "mineCount", mineCount);
        setObjVar(self, "asteroidType", asteroids);
        setObjVar(self, "currentCount", 0);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            questActivateTask(questid, 0, player);
        }
        questSetQuestTaskCounter(player, "spacequest/" + questType + "/" + questName, 1, "quest/groundquests:mine_counter", 0, mineCount);
        String questZone = getStringObjVar(self, space_quest.QUEST_ZONE);
        if ((getCurrentSceneName()).startsWith(questZone))
        {
            dictionary outparams = new dictionary();
            outparams.put("player", player);
            messageTo(self, "initializedQuestPlayer", outparams, 1.f, false);
        }
        setObjVar(self, "msgCount", 3);
        return SCRIPT_CONTINUE;
    }
    public int initializedQuestPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        String questZone = getStringObjVar(self, space_quest.QUEST_ZONE);
        obj_id player = params.getObjId("player");
        obj_id containing_ship = space_transition.getContainingShip(player);
        if (isIdValid(containing_ship))
        {
            String strChassisType = getShipChassisType(containing_ship);
            if ((strChassisType != null) && strChassisType.equals("player_sorosuub_space_yacht"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (!(getCurrentSceneName()).startsWith(questZone))
        {
            if (space_quest.isQuestInProgress(self))
            {
                space_quest.showQuestUpdate(self, SID_ABANDONED_DESTROY);
                space_quest.setQuestFailed(player, self, false);
            }
            return SCRIPT_CONTINUE;
        }
        obj_id questManager = getNamedObject(space_quest.QUEST_MANAGER);
        if (questManager == null)
        {
            return SCRIPT_CONTINUE;
        }
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            questActivateTask(questid, 1, player);
            if (questIsTaskActive(questid, 0, player))
            {
                questCompleteTask(questid, 0, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int asteroidMined(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        String typeMined = params.getString("resourceType");
        String typeDesired = getStringObjVar(self, "asteroidType");
        int mineCount = getIntObjVar(self, "mineCount");
        int currentCount = getIntObjVar(self, "currentCount");
        if (typeMined.equals(typeDesired))
        {
            int amt = params.getInt("resourceAmt");
            currentCount = currentCount + amt;
            setObjVar(self, "currentCount", currentCount);
            if (currentCount >= mineCount)
            {
                questCompleted(self);
            }
            String questName = getStringObjVar(self, space_quest.QUEST_NAME);
            String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
            questSetQuestTaskCounter(player, "spacequest/" + questType + "/" + questName, 1, "quest/groundquests:mine_counter", currentCount, mineCount);
            int msgCount = getIntObjVar(self, "msgCount");
            if (currentCount > (.50 * mineCount) && currentCount < (.75 * mineCount) && msgCount == 1)
            {
                setObjVar(self, "msgCount", 0);
                space_quest.showQuestUpdate(self, SID_REMAINDER_UPDATE, mineCount - currentCount);
            }
            else if (currentCount > (.25 * mineCount) && currentCount < (.50 * mineCount) && msgCount == 2)
            {
                setObjVar(self, "msgCount", 1);
                space_quest.showQuestUpdate(self, SID_REMAINDER_UPDATE, mineCount - currentCount);
            }
            else if (currentCount > (0) && currentCount < (.25 * mineCount) && msgCount == 3)
            {
                setObjVar(self, "msgCount", 2);
                space_quest.showQuestUpdate(self, SID_REMAINDER_UPDATE, mineCount - currentCount);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void questCompleted(obj_id self) throws InterruptedException
    {
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if (questid != 0)
        {
            if (questIsTaskActive(questid, 1, player))
            {
                questCompleteTask(questid, 1, player);
            }
        }
        space_quest.setQuestWon(player, self);
    }
    public int playerShipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "in_progress"))
        {
            obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
            space_quest.setQuestFailed(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int abortMission(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "noAbort"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        space_quest.setQuestAborted(player, self);
        return SCRIPT_CONTINUE;
    }
    public int removeQuest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, space_quest.QUEST_OWNER);
        String questName = getStringObjVar(self, space_quest.QUEST_NAME);
        String questType = getStringObjVar(self, space_quest.QUEST_TYPE);
        int questid = questGetQuestId("spacequest/" + questType + "/" + questName);
        if ((questid != 0) && questIsQuestActive(questid, player))
        {
            questCompleteQuest(questid, player);
        }
        space_quest._removeQuest(player, self);
        return SCRIPT_CONTINUE;
    }
}
