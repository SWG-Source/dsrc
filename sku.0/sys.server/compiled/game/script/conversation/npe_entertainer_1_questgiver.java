package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.npe;
import script.library.space_quest;
import script.library.utils;

public class npe_entertainer_1_questgiver extends script.base_script
{
    public npe_entertainer_1_questgiver()
    {
    }
    public static String c_stringFile = "conversation/npe_entertainer_1_questgiver";
    public boolean npe_entertainer_1_questgiver_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_entertainer_1_questgiver_condition_playerCompletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_entertainer_1");
    }
    public boolean npe_entertainer_1_questgiver_condition_playerStartedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_entertainer_1");
    }
    public boolean npe_entertainer_1_questgiver_condition_playerFinishedMainTask(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_entertainer_1", "talktoqg2");
    }
    public boolean npe_entertainer_1_questgiver_condition_playerOnOtherTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "npe_new_artisan_quest") || groundquests.isQuestActive(player, "npe_brawler_1hand") || groundquests.isQuestActive(player, "npe_brawler_2hand") || groundquests.isQuestActive(player, "npe_brawler_polearm") || groundquests.isQuestActive(player, "npe_brawler_unarmed") || groundquests.isQuestActive(player, "npe_scout_1") || groundquests.isQuestActive(player, "npe_marksman_carbine") || groundquests.isQuestActive(player, "npe_marksman_pistol") || groundquests.isQuestActive(player, "npe_marksman_rifle") || groundquests.isQuestActive(player, "npe_medic") || space_quest.hasQuest(player, "patrol", "npe_training_1") || space_quest.hasQuest(player, "patrol", "npe_training_2") || space_quest.hasQuest(player, "escort", "npe_training_3")) && groundquests.isQuestActive(player, "npe_solo_profession");
    }
    public boolean npe_entertainer_1_questgiver_condition_notEntertainer(obj_id player, obj_id npc) throws InterruptedException
    {
        String pTemplate = getSkillTemplate(player);
        if (pTemplate.indexOf("entertainer") > -1)
        {
            return false;
        }
        else if (hasObjVar(player, "npe.finishedTemplate"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean npe_entertainer_1_questgiver_condition_startingconversation(obj_id player, obj_id npc) throws InterruptedException
    {
        String pTemplate = getSkillTemplate(player);
        if (pTemplate.indexOf("entertainer") > -1)
        {
            return true;
        }
        else if (!hasObjVar(player, "npe.finishedTemplate"))
        {
            return false;
        }
        else 
        {
            return false;
        }
    }
    public void npe_entertainer_1_questgiver_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void npe_entertainer_1_questgiver_action_signalReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_ent1_reward");
        groundquests.grantQuest(player, "npe_han_comm_entertainer", false);
    }
    public void npe_entertainer_1_questgiver_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        String pTemplate = getSkillTemplate(player);
        groundquests.grantQuest(player, "npe_entertainer_1");
        groundquests.sendSignal(player, "talked_to_anvar");
        npe.givePerformPopUp(player, npc);
        newbieTutorialSetToolbarElement(player, 9, "/StartDance");
        newbieTutorialSetToolbarElement(player, 10, "/StopDance");
        newbieTutorialHighlightUIElement(player, "/GroundHUD.Toolbar.volume.9", 5.0f);
        newbieTutorialHighlightUIElement(player, "/GroundHUD.Toolbar.volume.10", 5.0f);
        if (pTemplate.indexOf("entertainer") > -1)
        {
            newbieTutorialSetToolbarElement(player, 4, "/flourish+1");
            newbieTutorialSetToolbarElement(player, 5, "/flourish+2");
            newbieTutorialSetToolbarElement(player, 6, "/flourish+3");
            newbieTutorialSetToolbarElement(player, 7, "/flourish+4");
            newbieTutorialSetToolbarElement(player, 16, "/flourish+5");
            newbieTutorialSetToolbarElement(player, 17, "/flourish+6");
            newbieTutorialSetToolbarElement(player, 18, "/flourish+7");
            newbieTutorialSetToolbarElement(player, 19, "/flourish+8");
            newbieTutorialHighlightUIElement(player, "/GroundHUD.Toolbar.volume.4", 5.0f);
        }
    }
    public int npe_entertainer_1_questgiver_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "celebrate1");
                npe_entertainer_1_questgiver_action_signalReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36"))
        {
            if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_entertainer_1_questgiver_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    utils.setScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_entertainer_1_questgiver_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_entertainer_1_questgiver_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                npe_entertainer_1_questgiver_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId");
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
            detachScript(self, "conversation.npe_entertainer_1_questgiver");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Anvar Keyis");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Anvar Keyis");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.npe_entertainer_1_questgiver");
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
        if (npe_entertainer_1_questgiver_condition_playerCompletedQuest(player, npc))
        {
            npe_entertainer_1_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_39");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (npe_entertainer_1_questgiver_condition_playerStartedQuest(player, npc))
        {
            npe_entertainer_1_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_30");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_entertainer_1_questgiver_condition_playerFinishedMainTask(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                }
                utils.setScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId", 2);
                npcStartConversation(player, npc, "npe_entertainer_1_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_entertainer_1_questgiver_condition_notEntertainer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_43");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_entertainer_1_questgiver_condition_startingconversation(player, npc))
        {
            doAnimationAction(npc, "greet");
            npe_entertainer_1_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_14");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId", 6);
                npcStartConversation(player, npc, "npe_entertainer_1_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_entertainer_1_questgiver_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_46");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_entertainer_1_questgiver"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId");
        if (branchId == 2 && npe_entertainer_1_questgiver_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && npe_entertainer_1_questgiver_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && npe_entertainer_1_questgiver_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && npe_entertainer_1_questgiver_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_entertainer_1_questgiver.branchId");
        return SCRIPT_CONTINUE;
    }
}
