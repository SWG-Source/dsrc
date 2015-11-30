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

public class som_kenobi_moral_exec extends script.base_script
{
    public som_kenobi_moral_exec()
    {
    }
    public static String c_stringFile = "conversation/som_kenobi_moral_exec";
    public boolean som_kenobi_moral_exec_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean som_kenobi_moral_exec_condition_defected(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "som_kenobi_moral_choice_1", "switchedSides");
    }
    public boolean som_kenobi_moral_exec_condition_haveCore(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_kenobi_moral_choice_1", "gotCore");
    }
    public boolean som_kenobi_moral_exec_condition_done(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "som_kenobi_moral_choice_1");
    }
    public boolean som_kenobi_moral_exec_condition_levelTooLow(obj_id player, obj_id npc) throws InterruptedException
    {
        int playerLevel = getLevel(player);
        if (playerLevel > 60)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean som_kenobi_moral_exec_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "som_kenobi_moral_choice_1");
    }
    public void som_kenobi_moral_exec_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_kenobi_moral_choice_1");
    }
    public void som_kenobi_moral_exec_action_reward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "givenCore");
    }
    public int som_kenobi_moral_exec_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_142"))
        {
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_145");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_144"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_156");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                som_kenobi_moral_exec_action_reward(player, npc);
                string_id message = new string_id(c_stringFile, "s_148");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_150");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_150"))
        {
            doAnimationAction(player, "shrug_hands");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumb_up");
                string_id message = new string_id(c_stringFile, "s_152");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_160"))
        {
            doAnimationAction(player, "shrug_hands");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                som_kenobi_moral_exec_action_reward(player, npc);
                string_id message = new string_id(c_stringFile, "s_162");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_165");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_165"))
        {
            doAnimationAction(player, "point_accusingly");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_169");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_112"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_63");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_113"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_114"))
        {
            doAnimationAction(player, "dismiss");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_61");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_99"))
        {
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_103");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_127");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_136");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                som_kenobi_moral_exec_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_130");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_136"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            doAnimationAction(player, "shrug_hands");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_65");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                som_kenobi_moral_exec_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            doAnimationAction(player, "dismiss");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_moral_exec_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_91");
                utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
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
            detachScript(self, "conversation.som_kenobi_moral_exec");
        }
        setCondition(self, CONDITION_INTERESTING);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_INTERESTING);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.som_kenobi_moral_exec");
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
        if (som_kenobi_moral_exec_condition_done(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_171");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_exec_condition_haveCore(player, npc))
        {
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_140");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_142");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_144");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 2);
                npcStartConversation(player, npc, "som_kenobi_moral_exec", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_exec_condition_defected(player, npc))
        {
            doAnimationAction(npc, "stamp_feet");
            string_id message = new string_id(c_stringFile, "s_45");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_exec_condition_onQuest(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_139");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_exec_condition_levelTooLow(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_110");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_113");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_moral_exec.branchId", 11);
                npcStartConversation(player, npc, "som_kenobi_moral_exec", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_moral_exec_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("som_kenobi_moral_exec"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
        if (branchId == 2 && som_kenobi_moral_exec_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && som_kenobi_moral_exec_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && som_kenobi_moral_exec_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && som_kenobi_moral_exec_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && som_kenobi_moral_exec_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && som_kenobi_moral_exec_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && som_kenobi_moral_exec_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && som_kenobi_moral_exec_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && som_kenobi_moral_exec_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && som_kenobi_moral_exec_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && som_kenobi_moral_exec_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && som_kenobi_moral_exec_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && som_kenobi_moral_exec_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.som_kenobi_moral_exec.branchId");
        return SCRIPT_CONTINUE;
    }
}
