package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_quests;
import script.library.fs_dyn_village;
import script.library.quests;

public class fs_quest_player extends script.base_script
{
    public fs_quest_player()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "systems.fs_quest.fs_quest_player");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, fs_quests.VAR_QUEST_ACCEPTED))
        {
            removeObjVar(self, fs_quests.VAR_QUEST_ACCEPTED);
        }
        if (hasObjVar(self, fs_quests.VAR_QUEST_COMPLETED))
        {
            removeObjVar(self, fs_quests.VAR_QUEST_COMPLETED);
        }
        messageTo(self, "msgQuestAbortPhaseChange", null, 0.0f, false);
        CustomerServiceLog("fs_quests", "Removing FS quest accepted/completed flag from %TU.", self, null);
        return SCRIPT_CONTINUE;
    }
    public int msgValidateFSQuestPhase(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID))
        {
            LOG(fs_dyn_village.LOG_CHAN, "fs_quest_player::msgValidateFSQuestPhase: -> Error getting current phase from cluster data.  Potentially on wrong quest phase.");
            return SCRIPT_CONTINUE;
        }
        int curUid = params.getInt(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID);
        int curPhase = fs_dyn_village.getPhaseFromVersionUid(curUid);
        checkFsQuestCraftingBug(curPhase);
        if (hasObjVar(self, fs_quests.VAR_QUEST_ACCEPTED))
        {
            int quest_phase_uid = getIntObjVar(self, fs_quests.VAR_QUEST_ACCEPTED);
            if (quest_phase_uid != curUid)
            {
                if (hasSurveyQuest(self))
                {
                    if (curPhase != 2)
                    {
                        resetQuest(self, 2);
                    }
                    if (curPhase != 3)
                    {
                        resetQuest(self, 3);
                    }
                }
                detachScript(self, "systems.fs_quest.fs_quest_player");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgSetQuestPhaseId(obj_id self, dictionary params) throws InterruptedException
    {
        boolean rslt = false;
        String key = "";
        if (params.containsKey("success"))
        {
            rslt = params.getBoolean("success");
        }
        if (rslt)
        {
            int val = -1;
            if (params.containsKey(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID))
            {
                val = params.getInt(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID);
                if (val > 0)
                {
                    setObjVar(self, fs_quests.VAR_QUEST_ACCEPTED, val);
                    CustomerServiceLog("fs_quests", "Marking %TU as having accept a quest for phase uid " + val + ".", self, null);
                }
            }
            else 
            {
                LOG("force_sensitive", "**** COULD NOT FIND PHASE UID for player " + self + " quest will abort on next login. ****");
            }
        }
        else 
        {
            LOG("force_sensitive", "fs_quest_player.msgSetQuestPhaseId -- Failed to find integer '" + key + "' cluster wide data.  Value might not have been registered yet.");
        }
        return SCRIPT_CONTINUE;
    }
    public void resetQuest(obj_id self, int phase) throws InterruptedException
    {
        for (int i = 1; i <= 8; i++)
        {
            String name = "survey_phase" + phase + "_0" + i;
            int questId = quests.getQuestId(name);
            if (quests.isComplete(name, self))
            {
                clearCompletedQuest(self, questId);
            }
            else if (quests.isActive(name, self))
            {
                quests.deactivate(name, self);
            }
            if (hasObjVar(self, "quest." + name + ".waypoint"))
            {
                obj_id waypoint = getObjIdObjVar(self, "quest." + name + ".waypoint");
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, self);
                }
            }
            if (hasObjVar(self, "quest." + name))
            {
                removeObjVar(self, "quest." + name);
            }
            if (hasObjVar(self, name))
            {
                removeObjVar(self, name);
            }
        }
    }
    public boolean hasSurveyQuest(obj_id player) throws InterruptedException
    {
        boolean yesNo = false;
        for (int x = 1; x < 9; x++)
        {
            String quest = "survey_phase2_0" + x;
            boolean onQuest = quests.isActive(quest, player);
            if (onQuest = true)
            {
                yesNo = true;
            }
        }
        for (int y = 1; y < 9; y++)
        {
            String quest = "survey_phase3_0" + y;
            boolean onQuest = quests.isActive(quest, player);
            if (onQuest = true)
            {
                yesNo = true;
            }
        }
        return yesNo;
    }
    public void checkFsQuestCraftingBug(int phase) throws InterruptedException
    {
        obj_id self = getSelf();
        if (phase == 2)
        {
            if (hasScript(self, "player.player_community_crafting"))
            {
                if (!hasScript(self, "quest.force_sensitive.fs_craft_village_defenses"))
                {
                    attachScript(self, "quest.force_sensitive.fs_craft_village_defenses");
                }
                messageTo(self, "handleVerifyQuestStatus", null, 0.1f, false);
            }
        }
        else if (phase == 3)
        {
            if (hasScript(self, "player.player_community_crafting"))
            {
                if (!hasScript(self, "quest.force_sensitive.fs_craft_village_shields"))
                {
                    attachScript(self, "quest.force_sensitive.fs_craft_village_shields");
                }
                messageTo(self, "handleVerifyQuestStatus", null, 0.1f, false);
            }
        }
    }
}
