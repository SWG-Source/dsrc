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

public class ep3_trandoshan_negal_teklon extends script.base_script
{
    public ep3_trandoshan_negal_teklon()
    {
    }
    public static String c_stringFile = "conversation/ep3_trandoshan_negal_teklon";
    public boolean ep3_trandoshan_negal_teklon_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_trandoshan_negal_teklon_condition_hasCompletedTask01(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_trando_borantok_01", "talktoNegal");
    }
    public boolean ep3_trandoshan_negal_teklon_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_trando_borantok_01");
    }
    public boolean ep3_trandoshan_negal_teklon_condition_hasCompletedTasks(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_trando_borantok_01", "talktoNegal");
    }
    public void ep3_trandoshan_negal_teklon_action_doSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hideBodies");
        groundquests.grantQuest(player, "ep3_trando_borantok_02");
    }
    public int ep3_trandoshan_negal_teklon_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1171"))
        {
            if (ep3_trandoshan_negal_teklon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_1173");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_negal_teklon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1175");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_negal_teklon_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1175"))
        {
            if (ep3_trandoshan_negal_teklon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_1177");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_negal_teklon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1179");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_negal_teklon_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1179"))
        {
            if (ep3_trandoshan_negal_teklon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_1181");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_trandoshan_negal_teklon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1183");
                    }
                    utils.setScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_trandoshan_negal_teklon_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1183"))
        {
            if (ep3_trandoshan_negal_teklon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                ep3_trandoshan_negal_teklon_action_doSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_1185");
                utils.removeScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId");
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
            detachScript(self, "conversation.ep3_trandoshan_negal_teklon");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Negal Tek'lon");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Negal Tek'lon");
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
        detachScript(self, "conversation.ep3_trandoshan_negal_teklon");
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
        if (ep3_trandoshan_negal_teklon_condition_hasCompletedTasks(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_1167");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_negal_teklon_condition_hasCompletedTask01(player, npc))
        {
            doAnimationAction(npc, "whisper");
            string_id message = new string_id(c_stringFile, "s_1169");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_trandoshan_negal_teklon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1171");
                }
                utils.setScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId", 2);
                npcStartConversation(player, npc, "ep3_trandoshan_negal_teklon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_negal_teklon_condition_isOnMission(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_1187");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_trandoshan_negal_teklon_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_1189");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_trandoshan_negal_teklon"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId");
        if (branchId == 2 && ep3_trandoshan_negal_teklon_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_trandoshan_negal_teklon_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_trandoshan_negal_teklon_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_trandoshan_negal_teklon_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_trandoshan_negal_teklon.branchId");
        return SCRIPT_CONTINUE;
    }
}
