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
import script.library.utils;

public class ep3_rodian_hunter extends script.base_script
{
    public ep3_rodian_hunter()
    {
    }
    public static String c_stringFile = "conversation/ep3_rodian_hunter";
    public boolean ep3_rodian_hunter_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_rodian_hunter_condition_hasFinishedQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_1");
        return questIsQuestComplete(questId, player);
    }
    public boolean ep3_rodian_hunter_condition_hasFinishedQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_2");
        return questIsQuestComplete(questId, player);
    }
    public boolean ep3_rodian_hunter_condition_hasFinishedQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_3");
        return questIsQuestComplete(questId, player);
    }
    public boolean ep3_rodian_hunter_condition_isOnQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_1");
        return questIsQuestActive(questId, player);
    }
    public boolean ep3_rodian_hunter_condition_isOnQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_2");
        return questIsQuestActive(questId, player);
    }
    public boolean ep3_rodian_hunter_condition_hasFinished_WookieQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_wookiee_benefactor_2");
        return questIsQuestComplete(questId, player);
    }
    public void ep3_rodian_hunter_action_giveQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_1");
        questActivateQuest(questId, player, npc);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_hunter_action_giveQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_2");
        questActivateQuest(questId, player, npc);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_medium.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_hunter_action_clearQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/ep3_rodian_hunter_1");
        int questId2 = questGetQuestId("quest/ep3_rodian_hunter_2");
        int questId3 = questGetQuestId("quest/ep3_rodian_hunter_3");
        questClearQuest(questId1, player);
        questClearQuest(questId2, player);
        questClearQuest(questId3, player);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_hunter_action_speak_SHORT(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_hunter_action_speak_MEDIUM(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_medium.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_hunter_action_speak_LONG(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_long.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_hunter_action_giveQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_3");
        questActivateQuest(questId, player, npc);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_hunter_action_giveQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_4");
        questActivateQuest(questId, player, npc);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public void ep3_rodian_hunter_action_giveQuestFive(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/ep3_rodian_hunter_5");
        questActivateQuest(questId, player, npc);
        playClientEffectLoc(player, "clienteffect/voc_npc_cnv_rodian_short.cef", getLocation(npc), 0f);
    }
    public int ep3_rodian_hunter_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2f1410df"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                ep3_rodian_hunter_action_speak_LONG(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa986495");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2fac86c");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2fac86c"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                ep3_rodian_hunter_action_speak_SHORT(player, npc);
                string_id message = new string_id(c_stringFile, "s_1a09ac5e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cba7ab48");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cba7ab48"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_rodian_hunter_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_a80d9308");
                utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ac3b13f5"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                ep3_rodian_hunter_action_speak_MEDIUM(player, npc);
                string_id message = new string_id(c_stringFile, "s_50c166a3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d5b8557");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4d5b8557"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_rodian_hunter_action_speak_SHORT(player, npc);
                string_id message = new string_id(c_stringFile, "s_d28436cd");
                utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ff3c855b"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "apologize");
                ep3_rodian_hunter_action_speak_MEDIUM(player, npc);
                string_id message = new string_id(c_stringFile, "s_4e4fe804");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9301eff1");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9301eff1"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_rodian_hunter_action_speak_SHORT(player, npc);
                string_id message = new string_id(c_stringFile, "s_34824b35");
                utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8be640b8"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                ep3_rodian_hunter_action_speak_LONG(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3bd19bc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1d144f53");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1d144f53"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slump_head");
                ep3_rodian_hunter_action_speak_MEDIUM(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe77e31e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d24a2285");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d24a2285"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_rodian_hunter_action_giveQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_81fdc59e");
                utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b1bd5fe2"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_rodian_hunter_action_speak_SHORT(player, npc);
                string_id message = new string_id(c_stringFile, "s_de3892ae");
                utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a4e70cc0"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "manipulate_medium");
                ep3_rodian_hunter_action_clearQuests(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa2e7213");
                utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b84b366c"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                ep3_rodian_hunter_action_speak_LONG(player, npc);
                string_id message = new string_id(c_stringFile, "s_e9a0750f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34f24948");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34f24948"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                ep3_rodian_hunter_action_speak_MEDIUM(player, npc);
                string_id message = new string_id(c_stringFile, "s_88467f34");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e42b260e");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e42b260e"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                ep3_rodian_hunter_action_giveQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe631380");
                utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7e4fd412"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f20fccbf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_583053f2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78fecb0e");
                    }
                    utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_rodian_hunter_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_583053f2"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_rodian_hunter_action_giveQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_f53e4c0c");
                utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78fecb0e"))
        {
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                ep3_rodian_hunter_action_giveQuestFive(player, npc);
                string_id message = new string_id(c_stringFile, "s_7d09a75e");
                utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_rodian_hunter");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Bazeedo");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Bazeedo");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_rodian_hunter");
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
        if (!ep3_rodian_hunter_condition_hasFinishedQuest1(player, npc))
        {
            doAnimationAction(npc, "greet");
            ep3_rodian_hunter_action_speak_MEDIUM(player, npc);
            string_id message = new string_id(c_stringFile, "s_31d7e474");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2f1410df");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 1);
                npcStartConversation(player, npc, "ep3_rodian_hunter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rodian_hunter_condition_isOnQuest1(player, npc))
        {
            doAnimationAction(npc, "shrug_hands");
            ep3_rodian_hunter_action_speak_SHORT(player, npc);
            string_id message = new string_id(c_stringFile, "s_225d3518");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ac3b13f5");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 5);
                npcStartConversation(player, npc, "ep3_rodian_hunter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!ep3_rodian_hunter_condition_hasFinished_WookieQuest1(player, npc))
        {
            doAnimationAction(npc, "greet");
            ep3_rodian_hunter_action_speak_SHORT(player, npc);
            string_id message = new string_id(c_stringFile, "s_b26194cb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ff3c855b");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 8);
                npcStartConversation(player, npc, "ep3_rodian_hunter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!ep3_rodian_hunter_condition_hasFinishedQuest2(player, npc))
        {
            doAnimationAction(npc, "implore");
            ep3_rodian_hunter_action_speak_MEDIUM(player, npc);
            string_id message = new string_id(c_stringFile, "s_cf298387");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8be640b8");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 11);
                npcStartConversation(player, npc, "ep3_rodian_hunter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rodian_hunter_condition_isOnQuest2(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            ep3_rodian_hunter_action_speak_SHORT(player, npc);
            string_id message = new string_id(c_stringFile, "s_d2849e62");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b1bd5fe2");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a4e70cc0");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 15);
                npcStartConversation(player, npc, "ep3_rodian_hunter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!ep3_rodian_hunter_condition_hasFinishedQuest3(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            ep3_rodian_hunter_action_speak_LONG(player, npc);
            string_id message = new string_id(c_stringFile, "s_b21a191");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b84b366c");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 18);
                npcStartConversation(player, npc, "ep3_rodian_hunter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            ep3_rodian_hunter_action_speak_SHORT(player, npc);
            string_id message = new string_id(c_stringFile, "s_d0306922");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_rodian_hunter_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7e4fd412");
                }
                utils.setScriptVar(player, "conversation.ep3_rodian_hunter.branchId", 22);
                npcStartConversation(player, npc, "ep3_rodian_hunter", message, responses);
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
        if (!conversationId.equals("ep3_rodian_hunter"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
        if (branchId == 1 && ep3_rodian_hunter_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && ep3_rodian_hunter_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_rodian_hunter_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_rodian_hunter_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_rodian_hunter_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_rodian_hunter_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_rodian_hunter_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_rodian_hunter_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_rodian_hunter_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ep3_rodian_hunter_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_rodian_hunter_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_rodian_hunter_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_rodian_hunter_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_rodian_hunter_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_rodian_hunter_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_rodian_hunter_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_rodian_hunter.branchId");
        return SCRIPT_CONTINUE;
    }
}
