package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.pclib;
import script.library.fs_dyn_village;
import script.library.locations;
import script.library.ai_lib;
import script.library.quests;
import script.library.factions;

public class fs_quests extends script.base_script
{
    public fs_quests()
    {
    }
    public static final String DATATABLE_SKILL_BRANCH = "datatables/quest/force_sensitive/skill_branch.iff";
    public static final String VAR_BRANCHES_UNLOCKED = "fs_quest.branches_unlocked";
    public static final String VAR_FREE_UNLOCK_USED = "fs_quest.free_unlock_used";
    public static final String VAR_VILLAGE_ELIGIBLE = "fs_quest.village_eligible";
    public static final String VAR_VILLAGE_COMPLETE = "fs_quest.village_complete";
    public static final String VAR_QUEST_ACCEPTED = "fs_quest.quest_accepted";
    public static final String VAR_QUEST_COMPLETED = "fs_quest.quest_completed";
    public static final String SCRIPT_VAR_BRANCH_SELECT_SUI = "fs_quest.branch_select_sui";
    public static final String SCRIPT_VAR_BRANCH_SELECT_LIST = "fs_quest.branch_select_list";
    public static final String SCRIPT_VAR_REMOVE_TASK_SUI = "fs_quest.remove_task_sui";
    public static final String SCRIPT_VAR_REMOVE_TASK_LIST = "fs_quest.remove_task_list";
    public static final String SCRIPT_VAR_REMOVE_TASK_PLAYER = "fs_quest.remove_task_player";
    public static final String SCRIPT_VAR_UNLOCK_BRANCH_SUI = "fs_quest.unlock_branch_sui";
    public static final String SCRIPT_VAR_UNLOCK_BRANCH_PLAYER = "fs_quest.unlock_branch_player";
    public static void badgeLeadin(obj_id player) throws InterruptedException
    {
        int randomNumber = rand(1, 250);
        if (randomNumber == 250)
        {
        }
        return;
    }
    public static void setDelay(obj_id player, int stage) throws InterruptedException
    {
        setObjVar(player, "fs_timestamp", getGameTime());
        int randomTimeDelay = 0;
        int visits = 0;
        switch (stage)
        {
            case 0:
            
            {
                fs_quests.advanceStage(player);
                String config = getConfigSetting("GameServer", "forceSensitiveIntroDelayFloor");
                int delayFloor = utils.stringToInt(config);
                if (delayFloor <= 0)
                {
                    delayFloor = 86400;
                }
                config = getConfigSetting("GameServer", "forceSensitiveIntroDelayCeiling");
                int delayCeiling = utils.stringToInt(config);
                if (delayCeiling <= 0)
                {
                    delayCeiling = 172800;
                }
                randomTimeDelay = rand(delayFloor, delayCeiling);
                if (!hasObjVar(player, "fs_oldman_visits"))
                {
                    setObjVar(player, "fs_oldman_visits", 1);
                }
                visits = getIntObjVar(player, "fs_oldman_visits");
                if (visits == 2)
                {
                    randomTimeDelay = randomTimeDelay + 172800;
                }
                if (visits == 3)
                {
                    randomTimeDelay = randomTimeDelay + 604800;
                }
                if (visits == 4)
                {
                    randomTimeDelay = randomTimeDelay + 1209600;
                }
                if (visits == 5)
                {
                    randomTimeDelay = randomTimeDelay + 2419200;
                }
                if (visits >= 6)
                {
                    randomTimeDelay = randomTimeDelay + 4838400;
                }
                visits = visits + 1;
                setObjVar(player, "fs_oldman_visits", visits);
                break;
            }
            case 3:
            
            {
                String config = getConfigSetting("GameServer", "forceSensitiveIntroDelayFloor");
                int delayFloor = utils.stringToInt(config);
                if (delayFloor <= 0)
                {
                    delayFloor = 3600;
                }
                config = getConfigSetting("GameServer", "forceSensitiveIntroDelayCeiling");
                int delayCeiling = utils.stringToInt(config);
                if (delayCeiling <= 0)
                {
                    delayCeiling = 86400;
                }
                randomTimeDelay = rand(delayFloor, delayCeiling);
                break;
            }
            case 13:
            randomTimeDelay = rand(15600, 86400);
            break;
            default:
            break;
        }
        setObjVar(player, "fs_delay", randomTimeDelay);
        return;
    }
    public static boolean checkDelay(obj_id player) throws InterruptedException
    {
        int currentTime = getGameTime();
        int startTime = getIntObjVar(player, "fs_timestamp");
        int delayTime = getIntObjVar(player, "fs_delay");
        if (currentTime > (startTime + delayTime))
        {
            return true;
        }
        return false;
    }
    public static boolean isVillageEligible(obj_id player) throws InterruptedException
    {
        return false;
    }
    public static boolean makeVillageEligible(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.makeVillageEligible -- player is invalid.");
            return false;
        }
        factions.setFactionStanding(player, "sith_shadow", factions.FACTION_RATING_MIN);
        setObjVar(player, VAR_VILLAGE_ELIGIBLE, 1);
        return true;
    }
    public static boolean isEndQuestEligible(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.isEndQuestEligible -- player is invalid.");
            return false;
        }
        return hasObjVar(player, VAR_VILLAGE_COMPLETE);
    }
    public static int getBranchesLearned(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.getBranchesLearned -- player is invalid.");
            return -1;
        }
        int learned_branches = 0;
        for (int i = 0; i < 16; i++)
        {
            String branch = getBranchFromId(i);
            if (branch != null)
            {
                if (hasSkill(player, branch + "_04"))
                {
                    learned_branches++;
                }
            }
        }
        return learned_branches;
    }
    public static int getBranchId(String branch) throws InterruptedException
    {
        if (branch == null)
        {
            LOG("force_sensitive", "fs_quests.getBranchId -- branch is null.");
            return -1;
        }
        int index = dataTableSearchColumnForString(branch, "branch", DATATABLE_SKILL_BRANCH);
        if (index == -1)
        {
            LOG("force_sensitive", "fs_quests.getBranchId -- can't find branch " + branch);
            return -1;
        }
        return dataTableGetInt(DATATABLE_SKILL_BRANCH, index, "id");
    }
    public static String getBranchFromId(int id) throws InterruptedException
    {
        if (id < 0)
        {
            LOG("force_sensitive", "fs_quests.getBranchFromId -- id is not valid.");
            return null;
        }
        int index = dataTableSearchColumnForInt(id, "id", DATATABLE_SKILL_BRANCH);
        if (index == -1)
        {
            LOG("force_sensitive", "fs_quests.getBranchFromId -- can't find id " + id);
            return null;
        }
        return dataTableGetString(DATATABLE_SKILL_BRANCH, index, "branch");
    }
    public static String getBranchFromSkill(String skill) throws InterruptedException
    {
        if (skill == null)
        {
            LOG("force_sensitive", "fs_quests.getBranchFromSkill -- skill is null.");
            return null;
        }
        int index = dataTableSearchColumnForString(skill, "skill", DATATABLE_SKILL_BRANCH);
        if (index == -1)
        {
            LOG("force_sensitive", "fs_quests.getBranchFromSkill -- can't find skill " + skill);
            return null;
        }
        return dataTableGetString(DATATABLE_SKILL_BRANCH, index, "branch");
    }
    public static boolean hasUnlockedBranch(obj_id player, String branch) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.hasUnlockedBranch -- player is invalid");
            return false;
        }
        if (branch == null)
        {
            LOG("force_sensitive", "fs_quests.hasUnlockedBranch -- branch is null");
            return false;
        }
        if (branch.equals("master") || branch.equals("novice"))
        {
            return true;
        }
        int branches_unlocked = 0;
        if (hasObjVar(player, VAR_BRANCHES_UNLOCKED))
        {
            branches_unlocked = getIntObjVar(player, VAR_BRANCHES_UNLOCKED);
        }
        else 
        {
            return false;
        }
        int pos = getBranchId(branch);
        if (pos < 0)
        {
            LOG("force_sensitive", "fs_quests.hasUnlockedBranch -- can't find id for " + branch);
            return false;
        }
        return utils.checkBit(branches_unlocked, pos);
    }
    public static boolean hasUnlockedBranch(obj_id player, int id) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.hasUnlockedBranch -- player is invalid");
            return false;
        }
        int branches_unlocked = 0;
        if (hasObjVar(player, VAR_BRANCHES_UNLOCKED))
        {
            branches_unlocked = getIntObjVar(player, VAR_BRANCHES_UNLOCKED);
        }
        else 
        {
            return false;
        }
        return utils.checkBit(branches_unlocked, id);
    }
    public static boolean unlockBranch(obj_id player, String branch) throws InterruptedException
    {
        int branches_unlocked = 0;
        if (hasObjVar(player, VAR_BRANCHES_UNLOCKED))
        {
            branches_unlocked = getIntObjVar(player, VAR_BRANCHES_UNLOCKED);
        }
        int index = dataTableSearchColumnForString(branch, "branch", DATATABLE_SKILL_BRANCH);
        if (index == -1)
        {
            LOG("force_sensitive", "fs_quests.unlockBranch -- can't find branch " + branch);
            return false;
        }
        int pos = getBranchId(branch);
        if (pos < 0)
        {
            LOG("force_sensitive", "fs_quests.unlockBranch -- can't find id for " + branch);
            return false;
        }
        branches_unlocked = utils.setBit(branches_unlocked, pos);
        setObjVar(player, VAR_BRANCHES_UNLOCKED, branches_unlocked);
        string_id branch_sid = utils.unpackString("@quest/force_sensitive/utils:" + branch);
        String branch_str = localize(branch_sid);
        prose_package pp = prose.getPackage(new string_id("quest/force_sensitive/utils", "branch_selected_unlock"), branch_str);
        sendSystemMessageProse(player, pp);
        return true;
    }
    public static boolean setQuestAccepted(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.setQuestAccepted -- player is invalid");
            return false;
        }
        if (hasScript(player, "systems.fs_quest.fs_quest_player"))
        {
            return false;
        }
        else 
        {
            attachScript(player, "systems.fs_quest.fs_quest_player");
        }
        fs_dyn_village.getRegisteredIntegerFromClusterWideData(fs_dyn_village.CLUSTER_INT_KEY_PHASE_UID, "msgSetQuestPhaseId", player);
        CustomerServiceLog("fs_quests", "%TU has accepted an FS quest.", player, null);
        sendSystemMessage(player, new string_id("quest/force_sensitive/utils", "quest_accepted"));
        return true;
    }
    public static boolean setQuestCompleted(obj_id player, String questName) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.setQuestCompleted-- player is invalid");
            return false;
        }
        if (!hasScript(player, "systems.fs_quest.fs_quest_player"))
        {
            attachScript(player, "systems.fs_quest.fs_quest_player");
        }
        int phase = 0;
        if (!hasObjVar(player, VAR_QUEST_ACCEPTED))
        {
            setQuestAccepted(player);
        }
        else 
        {
            phase = getIntObjVar(player, VAR_QUEST_ACCEPTED);
        }
        CustomerServiceLog("fs_quests", "Marking %TU as having completed a quest (" + questName + ") for phase " + phase + ".", player, null);
        setObjVar(player, VAR_QUEST_COMPLETED, questName);
        return true;
    }
    public static boolean hasQuestCompleted(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.hasQuestCompleted -- player is invalid");
            return false;
        }
        return hasObjVar(player, VAR_QUEST_COMPLETED);
    }
    public static boolean hasQuestAccepted(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.hasQuestAccepted -- player is invalid");
            return false;
        }
        return hasObjVar(player, fs_quests.VAR_QUEST_ACCEPTED);
    }
    public static boolean hasFreeUnlockBranches(obj_id player) throws InterruptedException
    {
        if (hasJediSlot(player))
        {
            return false;
        }
        int free_unlock = getFreeUnlockBranches(player);
        if (free_unlock > 0)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static int getFreeUnlockBranches(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.getFreeUnlockBranches -- player is invalid");
            return -1;
        }
        int skills_remaining = 0;
        if (hasObjVar(player, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS))
        {
            skills_remaining = getStringArrayObjVar(player, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS).length;
        }
        else if (!hasObjVar(player, "jedi.postponeGrant"))
        {
            skills_remaining = 8;
        }
        int max_free_unlock = 0;
        switch (skills_remaining)
        {
            case 0:
            max_free_unlock = 6;
            break;
            case 1:
            max_free_unlock = 5;
            break;
            case 2:
            max_free_unlock = 4;
            break;
            case 3:
            max_free_unlock = 3;
            break;
            case 4:
            max_free_unlock = 2;
            break;
            case 5:
            max_free_unlock = 1;
            break;
        }
        int unlock_used = 0;
        if (hasObjVar(player, VAR_FREE_UNLOCK_USED))
        {
            unlock_used = getIntObjVar(player, VAR_FREE_UNLOCK_USED);
        }
        int unlock_remaining = max_free_unlock - unlock_used;
        CustomerServiceLog("fs_quests", "%TU has " + unlock_remaining + " unlocked free unlocks remaining.", player, null);
        return unlock_remaining;
    }
    public static boolean showBranchUnlockSUI(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.showBranchUnlockSUI -- player is invalid");
            return false;
        }
        int free_branches = getFreeUnlockBranches(player);
        if (free_branches < 1)
        {
            sendSystemMessage(player, new string_id("quests/force_sensitive/utils", "no_free_branches"));
            return false;
        }
        int branches_unlocked = 0;
        if (hasObjVar(player, VAR_BRANCHES_UNLOCKED))
        {
            branches_unlocked = getIntObjVar(player, VAR_BRANCHES_UNLOCKED);
        }
        Vector branches_available = new Vector();
        branches_available.setSize(0);
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        for (int i = 0; i < 16; i++)
        {
            if (!hasUnlockedBranch(player, i))
            {
                String branch = getBranchFromId(i);
                if (branch != null)
                {
                    branches_available.add(branch);
                    dsrc.add("@quest/force_sensitive/utils:" + branch);
                }
            }
        }
        if (branches_available.size() > 0)
        {
            String prompt = "Select the branch that you wish to unlock.\n Free branch unlocks remaining: " + free_branches;
            int pid = sui.listbox(player, player, prompt, sui.OK_CANCEL, "@quest/force_sensitive/utils:branch_select_title", dsrc, "msgBranchSelected");
            utils.setScriptVar(player, SCRIPT_VAR_BRANCH_SELECT_SUI, pid);
            utils.setScriptVar(player, SCRIPT_VAR_BRANCH_SELECT_LIST, branches_available);
            if (!hasScript(player, "quest.force_sensitive.fs_xp_convert"))
            {
                attachScript(player, "quest.force_sensitive.fs_xp_convert");
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id("quests/force_sensitive/utils", "no_available_branches"));
        }
        return true;
    }
    public static boolean isValidLocationForEncounter(location loc) throws InterruptedException
    {
        String area = toLower(loc.area);
        if (area.equals("dathomir") || area.startsWith("kashyyyk") || area.startsWith("space_"))
        {
            return false;
        }
        return true;
    }
    public static boolean canBeginEncounter(obj_id player) throws InterruptedException
    {
        location here = getLocation(player);
        if (!isValidLocationForEncounter(here))
        {
            return false;
        }
        if (locations.isInCity(here))
        {
            return false;
        }
        obj_id building = getTopMostContainer(player);
        if (building != player)
        {
            return false;
        }
        if (isIdValid(getMountId(player)))
        {
            return false;
        }
        return true;
    }
    public static void advanceStage(obj_id player) throws InterruptedException
    {
        int stage = getIntObjVar(player, "fs_kickoff_stage");
        int nextStage = stage + 1;
        setObjVar(player, "fs_kickoff_stage", nextStage);
        return;
    }
    public static void decreaseStage(obj_id player) throws InterruptedException
    {
        int stage = getIntObjVar(player, "fs_kickoff_stage");
        int nextStage = stage - 1;
        setObjVar(player, "fs_kickoff_stage", nextStage);
        return;
    }
    public static void setStage(obj_id player, int stage) throws InterruptedException
    {
        setObjVar(player, "fs_kickoff_stage", stage);
        return;
    }
    public static void oldManDepart(obj_id player, obj_id npc, int flag) throws InterruptedException
    {
        if ((isIdValid(player)) && (exists(player)))
        {
            if ((isIdValid(npc)) && (exists(npc)))
            {
                ai_lib.aiStopFollowing(npc);
            }
        }
        if ((isIdValid(player)) && (exists(player)))
        {
            if ((isIdValid(npc)) && (exists(npc)))
            {
                ai_lib.pathAwayFrom(npc, player);
            }
        }
        int dataRow = 0;
        if (flag == 0)
        {
            messageTo(npc, "cleanUp", null, 8, false);
            return;
        }
        if (flag == 1)
        {
            quests.deactivate("old_man_initial", player);
            dataRow = quests.getQuestId("old_man_initial");
        }
        if (flag == 2)
        {
            quests.deactivate("old_man_exit", player);
            dataRow = quests.getQuestId("old_man_exit");
        }
        clearCompletedQuest(player, dataRow);
        messageTo(npc, "cleanUp", null, 8, false);
        return;
    }
    public static boolean clearFSData(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        String datatable = "datatables/player/quests.iff";
        int numRows = dataTableGetNumRows(datatable);
        int iter = 0;
        for (iter = 0; iter < numRows; ++iter)
        {
            clearCompletedQuest(player, iter);
            if (isQuestActive(player, iter))
            {
                String questName = quests.getDataEntry(iter, "NAME");
                quests.deactivate(questName, player);
            }
        }
        if (hasObjVar(player, "fs_quest"))
        {
            removeObjVar(player, "fs_quest");
        }
        if (hasObjVar(player, "fs_kickoff_stage"))
        {
            removeObjVar(player, "fs_kickoff_stage");
        }
        if (hasObjVar(player, "conversation"))
        {
            removeObjVar(player, "conversation");
        }
        if (hasObjVar(player, "quest"))
        {
            removeObjVar(player, "quest");
        }
        if (hasScript(player, "systems.fs_quest.fs_quest_player"))
        {
            detachScript(player, "systems.fs_quest.fs_quest_player");
        }
        if (hasScript(player, "quest.force_sensitive.fs_kickoff"))
        {
            detachScript(player, "quest.force_sensitive.fs_kickoff");
        }
        if (hasTheaterAssigned(player))
        {
            unassignTheaterFromPlayer(player);
        }
        setJediState(player, JEDI_STATE_NONE);
        return true;
    }
    public static boolean resetFSTask(obj_id player, obj_id owner) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.resetFSTask -- player is invalid");
            return false;
        }
        if (!isIdValid(owner))
        {
            LOG("force_sensitive", "fs_quests.resetFSTask -- owner is invalid");
            return false;
        }
        String datatable = "datatables/player/quests.iff";
        int rows = dataTableGetNumRows(datatable);
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        Vector tasks = new Vector();
        tasks.setSize(0);
        for (int i = 0; i < rows; i++)
        {
            String quest_name = quests.getDataEntry(i, "NAME");
            if (isQuestActive(player, i))
            {
                dsrc.add(quest_name + " *");
                tasks.add(new Integer(i));
            }
            else if (isQuestComplete(player, i))
            {
                dsrc.add(quest_name);
                tasks.add(new Integer(i));
            }
        }
        if (dsrc.size() > 0)
        {
            if (utils.hasScriptVar(owner, SCRIPT_VAR_REMOVE_TASK_SUI))
            {
                forceCloseSUIPage(utils.getIntScriptVar(owner, SCRIPT_VAR_REMOVE_TASK_SUI));
            }
            String prompt = "Select the task you wish to reset. * denotes an active task.";
            int pid = sui.listbox(owner, owner, prompt, sui.OK_CANCEL, "Reset FS Task", dsrc, "msgResetFSTask", true, false);
            utils.setScriptVar(owner, SCRIPT_VAR_REMOVE_TASK_SUI, pid);
            utils.setScriptVar(owner, SCRIPT_VAR_REMOVE_TASK_PLAYER, player);
            utils.setBatchScriptVar(owner, SCRIPT_VAR_REMOVE_TASK_LIST, tasks);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean unlockBranchSUI(obj_id player, obj_id owner) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("force_sensitive", "fs_quests.unlockBranchSUI -- player is invalid");
            return false;
        }
        if (!isIdValid(owner))
        {
            LOG("force_sensitive", "fs_quests.unlockBranchSUI -- owner is invalid");
            return false;
        }
        String[] branches = 
        {
            "force_sensitive_combat_prowess_ranged_accuracy",
            "force_sensitive_combat_prowess_ranged_speed",
            "force_sensitive_combat_prowess_melee_accuracy",
            "force_sensitive_combat_prowess_melee_speed",
            "force_sensitive_enhanced_reflexes_ranged_defense",
            "force_sensitive_enhanced_reflexes_melee_defense",
            "force_sensitive_enhanced_reflexes_vehicle_control",
            "force_sensitive_enhanced_reflexes_survival",
            "force_sensitive_crafting_mastery_experimentation",
            "force_sensitive_crafting_mastery_assembly",
            "force_sensitive_crafting_mastery_repair",
            "force_sensitive_crafting_mastery_technique",
            "force_sensitive_heightened_senses_healing",
            "force_sensitive_heightened_senses_surveying",
            "force_sensitive_heightened_senses_persuasion",
            "force_sensitive_heightened_senses_luck"
        };
        String[] dsrc = new String[branches.length];
        for (int i = 0; i < branches.length; i++)
        {
            dsrc[i] = localize(new string_id("quest/force_sensitive/utils", branches[i]));
            if (hasUnlockedBranch(player, i))
            {
                if (dsrc[i] != null)
                {
                    dsrc[i] += " *";
                }
            }
        }
        String prompt = "Select the branch you wish to unlock. * denotes that the target already has the branch.";
        int pid = sui.listbox(owner, owner, prompt, sui.OK_CANCEL, "Branch Unlock", dsrc, "msgCSUnlockBranch");
        utils.setScriptVar(owner, SCRIPT_VAR_UNLOCK_BRANCH_SUI, pid);
        utils.setScriptVar(owner, SCRIPT_VAR_UNLOCK_BRANCH_PLAYER, player);
        return true;
    }
}
