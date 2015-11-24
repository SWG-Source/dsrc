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
import script.library.utils;

public class ep3_trandoshan_harwakokok_zssik_05 extends script.base_script
{
    public ep3_trandoshan_harwakokok_zssik_05()
    {
    }
    public static String c_stringFile = "conversation/ep3_trandoshan_harwakokok_zssik_05";
    public boolean ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_trandoshan_harwakokok_zssik_05_condition_isOnTask01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_trando_orooroo_zssik_08", "talkToHarwakokok");
    }
    public boolean ep3_trandoshan_harwakokok_zssik_05_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_trando_harwakokok_zssik_09");
    }
    public boolean ep3_trandoshan_harwakokok_zssik_05_condition_isOnTask02(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_trando_harwakokok_zssik_09", "killedScratch");
    }
    public boolean ep3_trandoshan_harwakokok_zssik_05_condition_hasCompletedQuest01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_trando_harwakokok_zssik_09");
    }
    public boolean ep3_trandoshan_harwakokok_zssik_05_condition_cannotSpeakWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "combat_smuggler_underworld_01"))
        {
            return false;
        }
        if (hasSkill(player, "social_language_wookiee_comprehend"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public void ep3_trandoshan_harwakokok_zssik_05_action_giveMission(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
        groundquests.grantQuest(player, "ep3_trando_harwakokok_zssik_09");
    }
    public void ep3_trandoshan_harwakokok_zssik_05_action_doSignal01(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "completedHarwakokok");
    }
    public void ep3_trandoshan_harwakokok_zssik_05_action_doSignal02(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
        groundquests.sendSignal(player, "returnToOrooroo");
    }
    public void ep3_trandoshan_harwakokok_zssik_05_action_vocalizeMed(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
    }
    public void ep3_trandoshan_harwakokok_zssik_05_action_vocalizeLong(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_6sec.cef", player, "");
    }
    public void ep3_trandoshan_harwakokok_zssik_05_action_vocalizeShort(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_2sec.cef", player, "");
    }
    public int ep3_trandoshan_harwakokok_zssik_05_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_890"))
        {
            ep3_trandoshan_harwakokok_zssik_05_action_doSignal01(player, npc);
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                ep3_trandoshan_harwakokok_zssik_05_action_doSignal02(player, npc);
                string_id message = new string_id(c_stringFile, "s_892");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_harwakokok_zssik_05_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_898"))
        {
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                ep3_trandoshan_harwakokok_zssik_05_action_vocalizeLong(player, npc);
                string_id message = new string_id(c_stringFile, "s_900");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_902");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_harwakokok_zssik_05_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_902"))
        {
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                ep3_trandoshan_harwakokok_zssik_05_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_904");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_906");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_harwakokok_zssik_05_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_906"))
        {
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                ep3_trandoshan_harwakokok_zssik_05_action_vocalizeShort(player, npc);
                string_id message = new string_id(c_stringFile, "s_908");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_910");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_harwakokok_zssik_05_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_910"))
        {
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                ep3_trandoshan_harwakokok_zssik_05_action_vocalizeMed(player, npc);
                string_id message = new string_id(c_stringFile, "s_912");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_914");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_harwakokok_zssik_05_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_914"))
        {
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                ep3_trandoshan_harwakokok_zssik_05_action_vocalizeLong(player, npc);
                string_id message = new string_id(c_stringFile, "s_916");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_918");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_harwakokok_zssik_05_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_918"))
        {
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                ep3_trandoshan_harwakokok_zssik_05_action_vocalizeMed(player, npc);
                string_id message = new string_id(c_stringFile, "s_920");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_922");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_926");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_harwakokok_zssik_05_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_922"))
        {
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                ep3_trandoshan_harwakokok_zssik_05_action_giveMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_924");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_926"))
        {
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                ep3_trandoshan_harwakokok_zssik_05_action_vocalizeMed(player, npc);
                string_id message = new string_id(c_stringFile, "s_928");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
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
            detachScript(self, "conversation.ep3_trandoshan_harwakokok_zssik_05");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Harwakokok the Mighty");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Harwakokok the Mighty");
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
        detachScript(self, "conversation.ep3_trandoshan_harwakokok_zssik_05");
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
        if (ep3_trandoshan_harwakokok_zssik_05_condition_cannotSpeakWookiee(player, npc))
        {
            ep3_trandoshan_harwakokok_zssik_05_action_vocalizeLong(player, npc);
            string_id message = new string_id(c_stringFile, "s_122");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_harwakokok_zssik_05_condition_hasCompletedQuest01(player, npc))
        {
            doAnimationAction(npc, "bow");
            ep3_trandoshan_harwakokok_zssik_05_action_vocalizeMed(player, npc);
            string_id message = new string_id(c_stringFile, "s_886");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_harwakokok_zssik_05_condition_isOnTask02(player, npc))
        {
            doAnimationAction(npc, "pose_proudly");
            ep3_trandoshan_harwakokok_zssik_05_action_vocalizeMed(player, npc);
            string_id message = new string_id(c_stringFile, "s_888");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_890");
                }
                utils.setScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId", 3);
                npcStartConversation(player, npc, "ep3_trandoshan_harwakokok_zssik_05", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_harwakokok_zssik_05_condition_isOnQuest(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            ep3_trandoshan_harwakokok_zssik_05_action_vocalizeMed(player, npc);
            string_id message = new string_id(c_stringFile, "s_894");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_harwakokok_zssik_05_condition_isOnTask01(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            ep3_trandoshan_harwakokok_zssik_05_action_vocalizeShort(player, npc);
            string_id message = new string_id(c_stringFile, "s_896");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_898");
                }
                utils.setScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId", 6);
                npcStartConversation(player, npc, "ep3_trandoshan_harwakokok_zssik_05", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_harwakokok_zssik_05_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            ep3_trandoshan_harwakokok_zssik_05_action_vocalizeShort(player, npc);
            string_id message = new string_id(c_stringFile, "s_930");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_trandoshan_harwakokok_zssik_05"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
        if (branchId == 3 && ep3_trandoshan_harwakokok_zssik_05_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_trandoshan_harwakokok_zssik_05_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_trandoshan_harwakokok_zssik_05_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_trandoshan_harwakokok_zssik_05_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_trandoshan_harwakokok_zssik_05_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_trandoshan_harwakokok_zssik_05_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_trandoshan_harwakokok_zssik_05_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_trandoshan_harwakokok_zssik_05_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_trandoshan_harwakokok_zssik_05.branchId");
        return SCRIPT_CONTINUE;
    }
}
