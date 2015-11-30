package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.content;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class legacy_naboo_lt_col_typho extends script.base_script
{
    public legacy_naboo_lt_col_typho()
    {
    }
    public static String c_stringFile = "conversation/legacy_naboo_lt_col_typho";
    public boolean legacy_naboo_lt_col_typho_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/typho_police_one"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_didMarineWorkOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/typho_marine_one"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_didPilotWorkOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/typho_pilot_one"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_worksForTypho(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/typho_police_one") || (groundquests.hasCompletedQuest(player, "quest/typho_marine_one")) || (groundquests.hasCompletedQuest(player, "quest/typho_pilot_one")))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_donePiloting(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/typho_pilot_two"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_donePolicing(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/typho_police_two"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_doneMarining(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/typho_marine_two"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_doneAll(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/typho_police_two") && groundquests.hasCompletedQuest(player, "quest/typho_marine_two") && groundquests.hasCompletedQuest(player, "quest/typho_pilot_two"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_finishingPoliceOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/typho_police_one", "waitForTypho"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_finishingPoliceTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/typho_police_two", "killedDoogal"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_finishingPilotOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/typho_pilot_one", "finishingPilotOne"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_finishingPilotTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/typho_pilot_two", "starwingEliminated"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_finishingMarineOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/typho_marine_one", "weaponsCacheDestroyed"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_finishingMarineTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/typho_marine_two", "killedMaulerLeaders"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_isWorkingForTypho(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "quest/typho_police_one") || groundquests.isQuestActive(player, "quest/typho_marine_one") || groundquests.isQuestActive(player, "quest/typho_pilot_one") || groundquests.isQuestActive(player, "quest/typho_police_two") || groundquests.isQuestActive(player, "quest/typho_marine_two") || groundquests.isQuestActive(player, "quest/typho_pilot_two"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_readyForTypho(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "naboo_kadaraa_tipping_the_balance_2");
    }
    public boolean legacy_naboo_lt_col_typho_condition_readyForPooja(obj_id player, obj_id npc) throws InterruptedException
    {
        if (badge.hasBadge(player, "bdg_content_rsf_clearance_4") && !badge.hasBadge(player, "bdg_content_rsf_clearance_5"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_lt_col_typho_condition_failedPilotOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "typho_pilot_one_failed");
    }
    public boolean legacy_naboo_lt_col_typho_condition_failedPilotTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "typho_pilot_two_failed");
    }
    public void legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/typho_police_one");
    }
    public void legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/typho_police_two");
    }
    public void legacy_naboo_lt_col_typho_action_grantMarineOneQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/typho_marine_one");
    }
    public void legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/typho_marine_two");
    }
    public void legacy_naboo_lt_col_typho_action_grantPilotOneQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/typho_pilot_one");
    }
    public void legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/typho_pilot_two");
    }
    public void legacy_naboo_lt_col_typho_action_sendPoliceOneSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "completedPoliceOne");
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_4"))
        {
            content.grantRsfSecurityClearance(player);
        }
    }
    public void legacy_naboo_lt_col_typho_action_sendPoliceTwoSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "killedDoogal");
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_4"))
        {
            content.grantRsfSecurityClearance(player);
        }
    }
    public void legacy_naboo_lt_col_typho_action_sendMarineOneSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "destroyedMaulerWeapons");
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_4"))
        {
            content.grantRsfSecurityClearance(player);
        }
    }
    public void legacy_naboo_lt_col_typho_action_sendMarineTwoSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "killedMaulerLeaders");
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_4"))
        {
            content.grantRsfSecurityClearance(player);
        }
    }
    public void legacy_naboo_lt_col_typho_action_sendPilotOneSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "completedPatrolMission");
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_4"))
        {
            content.grantRsfSecurityClearance(player);
        }
    }
    public void legacy_naboo_lt_col_typho_action_sendPilotTwoSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "completedAssassinMission");
        if (!badge.hasBadge(player, "bdg_content_rsf_clearance_4"))
        {
            content.grantRsfSecurityClearance(player);
        }
    }
    public void legacy_naboo_lt_col_typho_action_endPointerQuestToTypho(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "naboo_keren_goto_typho"))
        {
            groundquests.sendSignal(player, "keren_goto_typho");
        }
    }
    public void legacy_naboo_lt_col_typho_action_sendToPooja(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/naboo_theed_goto_pooja");
    }
    public void legacy_naboo_lt_col_typho_action_sendFailedPilotOneSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "failedTyphoPilotOne");
    }
    public void legacy_naboo_lt_col_typho_action_sendFailedPilotTwoSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "failedTyphoPilotTwo");
    }
    public int legacy_naboo_lt_col_typho_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPilotWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didMarineWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPilotWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didMarineWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPilotWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didMarineWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPilotWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didMarineWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPilotWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didMarineWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPilotWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didMarineWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPilotWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didMarineWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPilotWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didMarineWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_sendToPooja(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPilotWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didPoliceWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition_didMarineWorkOne(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineTwoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_lt_col_typho_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPilotOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantPoliceOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_59"))
        {
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_lt_col_typho_action_grantMarineOneQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_63");
                utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.legacy_naboo_lt_col_typho");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.legacy_naboo_lt_col_typho");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (legacy_naboo_lt_col_typho_condition_doneAll(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_33");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 1);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_finishingPilotTwo(player, npc))
        {
            legacy_naboo_lt_col_typho_action_sendPilotTwoSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (!legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 11);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_finishingPilotOne(player, npc))
        {
            legacy_naboo_lt_col_typho_action_sendPilotOneSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_35");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (!legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 11);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_finishingPoliceTwo(player, npc))
        {
            legacy_naboo_lt_col_typho_action_sendPoliceTwoSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (!legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 11);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_finishingPoliceOne(player, npc))
        {
            legacy_naboo_lt_col_typho_action_sendPoliceOneSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (!legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 11);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_finishingMarineTwo(player, npc))
        {
            legacy_naboo_lt_col_typho_action_sendMarineTwoSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (!legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 11);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_finishingMarineOne(player, npc))
        {
            legacy_naboo_lt_col_typho_action_sendMarineOneSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_39");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (!legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 11);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_failedPilotTwo(player, npc))
        {
            legacy_naboo_lt_col_typho_action_sendFailedPilotTwoSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_67");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (!legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 11);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_failedPilotOne(player, npc))
        {
            legacy_naboo_lt_col_typho_action_sendFailedPilotOneSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_68");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (!legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 11);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_isWorkingForTypho(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_40");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_worksForTypho(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_13");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition_readyForPooja(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePiloting(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!legacy_naboo_lt_col_typho_condition_donePolicing(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (!legacy_naboo_lt_col_typho_condition_doneMarining(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 11);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition_readyForTypho(player, npc))
        {
            legacy_naboo_lt_col_typho_action_endPointerQuestToTypho(player, npc);
            string_id message = new string_id(c_stringFile, "s_49");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId", 22);
                npcStartConversation(player, npc, "legacy_naboo_lt_col_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_lt_col_typho_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_66");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("legacy_naboo_lt_col_typho"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
        if (branchId == 1 && legacy_naboo_lt_col_typho_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && legacy_naboo_lt_col_typho_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && legacy_naboo_lt_col_typho_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && legacy_naboo_lt_col_typho_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && legacy_naboo_lt_col_typho_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && legacy_naboo_lt_col_typho_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && legacy_naboo_lt_col_typho_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && legacy_naboo_lt_col_typho_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && legacy_naboo_lt_col_typho_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && legacy_naboo_lt_col_typho_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && legacy_naboo_lt_col_typho_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.legacy_naboo_lt_col_typho.branchId");
        return SCRIPT_CONTINUE;
    }
}
