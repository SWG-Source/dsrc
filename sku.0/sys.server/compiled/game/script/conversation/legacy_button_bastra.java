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
import script.library.features;
import script.library.groundquests;
import script.library.space_quest;
import script.library.utils;

public class legacy_button_bastra extends script.base_script
{
    public legacy_button_bastra()
    {
    }
    public static String c_stringFile = "conversation/legacy_button_bastra";
    public boolean legacy_button_bastra_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean legacy_button_bastra_condition_onButtonImp(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean OnTask = hasObjVar(player, "legacy.faction.imperialPath");
        return OnTask;
    }
    public boolean legacy_button_bastra_condition_onButtonReb(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "legacy.faction.rebelPath");
    }
    public boolean legacy_button_bastra_condition_failedTosche(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_start");
        int questId2 = questGetQuestId("quest/legacy_button_jawatracks_reb");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (!(questIsQuestActive(questId2, player)) && !(questIsQuestComplete(questId2, player)));
        return OnTask;
    }
    public boolean legacy_button_bastra_condition_failedValarian(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "quest/legacy_button_jawatracks_reb_pt3") || groundquests.hasCompletedQuest(player, "quest/legacy_button_jawatracks_reb_pt3_v2")) && (!groundquests.isQuestActiveOrComplete(player, "quest/legacy_button_reb_valarian") && !groundquests.isQuestActiveOrComplete(player, "quest/legacy_button_reb_valarian_v2"));
    }
    public boolean legacy_button_bastra_condition_onButtonCompleteReb(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId2 = questGetQuestId("quest/legacy_button_reb_pt2");
        boolean OnTask = questIsQuestComplete(questId2, player);
        return OnTask;
    }
    public boolean legacy_button_bastra_condition_failedDarklighter(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((groundquests.hasCompletedQuest(player, "legacy_button_reb_valarian") || groundquests.hasCompletedQuest(player, "quest/legacy_button_jawatracks_reb_pt3_v2")) && !groundquests.isQuestActiveOrComplete(player, "legacy_button_reb_darklighter"));
    }
    public boolean legacy_button_bastra_condition_failedSlicer(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_reb_darklighter");
        int questId2 = questGetQuestId("quest/legacy_button_reb_pt2");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (!(questIsQuestActive(questId2, player)) && !(questIsQuestComplete(questId2, player)));
        return OnTask;
    }
    public boolean legacy_button_bastra_condition_finishTogether(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "legacy_together") || groundquests.hasCompletedQuest(player, "legacy_together_2"));
    }
    public boolean legacy_button_bastra_condition_FailedKickReb(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasCompleted = false;
        if (groundquests.isQuestActiveOrComplete(player, "naboo_kadaraa_tipping_the_balance_1"))
        {
            hasCompleted = true;
        }
        if (groundquests.hasCompletedQuest(player, "quest/legacy_naboo_kick_reb"))
        {
            hasCompleted = true;
        }
        return hasCompleted;
    }
    public boolean legacy_button_bastra_condition_failedWatto(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/legacy_button_reb_pt2");
        int questId2 = questGetQuestId("quest/legacy_watto_pointer");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (!(questIsQuestActive(questId2, player)) && !(questIsQuestComplete(questId2, player)));
        return OnTask;
    }
    public boolean legacy_button_bastra_condition_onButtonStart(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "quest/legacy_button_start");
    }
    public void legacy_button_bastra_action_signalRebelButton(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "legacy.faction.rebelPath", 1);
        groundquests.grantQuest(player, "legacy_button_jawatracks_reb");
    }
    public void legacy_button_bastra_action_grantValarian(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_button_reb_valarian_v2");
    }
    public void legacy_button_bastra_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void legacy_button_bastra_action_grantDarklighter(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_button_reb_darklighter");
    }
    public void legacy_button_bastra_action_grantTosche(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_button_jawatracks_reb");
    }
    public void legacy_button_bastra_action_grantSlicer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_button_reb_pt2");
    }
    public void legacy_button_bastra_action_reGrantKickReb(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_naboo_kick_reb");
    }
    public void legacy_button_bastra_action_grantWatto(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "legacy_watto_pointer");
    }
    public void legacy_button_bastra_action_signalGoToComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "legacy_button_start_launch_e3");
    }
    public int legacy_button_bastra_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_73"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_bastra_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_bastra.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_bastra_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                legacy_button_bastra_action_reGrantKickReb(player, npc);
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_bastra_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                legacy_button_bastra_action_grantWatto(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_bastra_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                legacy_button_bastra_action_grantTosche(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                legacy_button_bastra_action_grantValarian(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                legacy_button_bastra_action_grantDarklighter(player, npc);
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                legacy_button_bastra_action_grantSlicer(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_bastra_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            legacy_button_bastra_action_signalGoToComplete(player, npc);
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                legacy_button_bastra_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_bastra_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_bastra.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_116"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "adjust");
                legacy_button_bastra_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_117");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_bastra_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_89");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_bastra_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_button_bastra_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (legacy_button_bastra_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_bastra.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_bastra_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                legacy_button_bastra_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_93");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_bastra_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_bastra.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_99"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_left");
                string_id message = new string_id(c_stringFile, "s_39");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_button_bastra_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.legacy_button_bastra.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_bastra_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                legacy_button_bastra_action_signalRebelButton(player, npc);
                string_id message = new string_id(c_stringFile, "s_97");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_button_bastra_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
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
            detachScript(self, "conversation.legacy_button_bastra");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Carh'la Bastra");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Carh'la Bastra");
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
        detachScript(self, "conversation.legacy_button_bastra");
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
        if (legacy_button_bastra_condition_onButtonImp(player, npc))
        {
            doAnimationAction(npc, "threaten");
            string_id message = new string_id(c_stringFile, "s_145");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_bastra_condition_finishTogether(player, npc))
        {
            doAnimationAction(npc, "shake_head_no");
            legacy_button_bastra_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_70");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_bastra_condition_FailedKickReb(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                }
                utils.setScriptVar(player, "conversation.legacy_button_bastra.branchId", 2);
                npcStartConversation(player, npc, "legacy_button_bastra", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_bastra_condition_onButtonCompleteReb(player, npc))
        {
            doAnimationAction(npc, "threaten");
            legacy_button_bastra_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_101");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_bastra_condition_failedWatto(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                }
                utils.setScriptVar(player, "conversation.legacy_button_bastra.branchId", 6);
                npcStartConversation(player, npc, "legacy_button_bastra", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_bastra_condition_onButtonReb(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            legacy_button_bastra_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_144");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_bastra_condition_failedTosche(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (legacy_button_bastra_condition_failedValarian(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (legacy_button_bastra_condition_failedDarklighter(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (legacy_button_bastra_condition_failedSlicer(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                }
                utils.setScriptVar(player, "conversation.legacy_button_bastra.branchId", 8);
                npcStartConversation(player, npc, "legacy_button_bastra", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_button_bastra_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "cough_polite");
            legacy_button_bastra_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_805");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_button_bastra_condition_onButtonStart(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_button_bastra_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                }
                utils.setScriptVar(player, "conversation.legacy_button_bastra.branchId", 14);
                npcStartConversation(player, npc, "legacy_button_bastra", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("legacy_button_bastra"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.legacy_button_bastra.branchId");
        if (branchId == 2 && legacy_button_bastra_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && legacy_button_bastra_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && legacy_button_bastra_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && legacy_button_bastra_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && legacy_button_bastra_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && legacy_button_bastra_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && legacy_button_bastra_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && legacy_button_bastra_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && legacy_button_bastra_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.legacy_button_bastra.branchId");
        return SCRIPT_CONTINUE;
    }
}
