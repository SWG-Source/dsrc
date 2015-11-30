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
import script.library.conversation;
import script.library.groundquests;
import script.library.skill;
import script.library.utils;

public class ep3_clone_relics_clone_trooper_walda extends script.base_script
{
    public ep3_clone_relics_clone_trooper_walda()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_clone_trooper_walda";
    public boolean ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_clone_trooper_walda_condition_onQuestNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_clone_trooper_neutral"));
    }
    public boolean ep3_clone_relics_clone_trooper_walda_condition_needHuttese(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_clone_trooper_mort_neutral", "talkToTeacher"));
    }
    public void ep3_clone_relics_clone_trooper_walda_action_doneWithTeacher(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToTeacher");
        skill.grantSkillToPlayer(player, "social_language_hutt");
    }
    public int ep3_clone_relics_clone_trooper_walda_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_161"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_168");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_170");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId", 2);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_walda_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_170"))
        {
            if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_172");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_174");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_walda_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_174"))
        {
            if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_176");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_178");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_walda_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_178"))
        {
            doAnimationAction(player, "slow_down");
            if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_walda_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_182"))
        {
            doAnimationAction(player, "huh");
            if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                ep3_clone_relics_clone_trooper_walda_action_doneWithTeacher(player, npc);
                string_id message = new string_id(c_stringFile, "s_184");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId");
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
            detachScript(self, "conversation.ep3_clone_relics_clone_trooper_walda");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_walda"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_walda"));
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
        detachScript(self, "conversation.ep3_clone_relics_clone_trooper_walda");
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
        if (ep3_clone_relics_clone_trooper_walda_condition_needHuttese(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            string_id message = new string_id(c_stringFile, "s_157");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_161");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId", 1);
                npcStartConversation(player, npc, "ep3_clone_relics_clone_trooper_walda", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_clone_trooper_walda_condition_onQuestNeutral(player, npc))
        {
            doAnimationAction(npc, "laugh");
            doAnimationAction(player, "tiphat");
            string_id message = new string_id(c_stringFile, "s_186");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_clone_trooper_walda_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_188");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_clone_relics_clone_trooper_walda"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId");
        if (branchId == 1 && ep3_clone_relics_clone_trooper_walda_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && ep3_clone_relics_clone_trooper_walda_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_clone_relics_clone_trooper_walda_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_clone_relics_clone_trooper_walda_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_clone_relics_clone_trooper_walda_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_walda.branchId");
        return SCRIPT_CONTINUE;
    }
}
