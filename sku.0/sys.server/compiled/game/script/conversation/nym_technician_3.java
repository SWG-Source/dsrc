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
import script.library.space_flags;
import script.library.space_quest;

public class nym_technician_3 extends script.base_script
{
    public nym_technician_3()
    {
    }
    public static String c_stringFile = "conversation/nym_technician_3";
    public boolean nym_technician_3_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nym_technician_3_condition_isOnStormSquadronQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "ss.findingAgent");
    }
    public boolean nym_technician_3_condition_isHuntingShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "ss.huntingShip");
    }
    public boolean nym_technician_3_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "assassinate", "imperial_ss_3");
    }
    public boolean nym_technician_3_condition_questDone(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuest(player, "assassinate", "imperial_ss_3");
    }
    public boolean nym_technician_3_condition_failedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "assassinate", "imperial_ss_3") || space_quest.hasAbortedQuest(player, "assassinate", "imperial_ss_3"));
    }
    public void nym_technician_3_action_grantAssassinateMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "imperial_ss_3");
    }
    public int nym_technician_3_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_248faf8a"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                nym_technician_3_action_grantAssassinateMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_e8060558");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d65e601b");
                    }
                    setObjVar(player, "conversation.nym_technician_3.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_3.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_3_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d65e601b"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a8162c2");
                removeObjVar(player, "conversation.nym_technician_3.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_3_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8804f6ee"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_544505bd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ebf6580");
                    }
                    setObjVar(player, "conversation.nym_technician_3.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_3.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67a590c3"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54711fd3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88d98675");
                    }
                    setObjVar(player, "conversation.nym_technician_3.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_3.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62f865dd"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cf08b4c6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cbc1d532");
                    }
                    setObjVar(player, "conversation.nym_technician_3.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_3.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f49a789f"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6da31acb");
                removeObjVar(player, "conversation.nym_technician_3.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7002e5c4"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_debe2412");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d5276b");
                    }
                    setObjVar(player, "conversation.nym_technician_3.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_3.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_3_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1ebf6580"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7e5b1e91");
                removeObjVar(player, "conversation.nym_technician_3.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_3_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_88d98675"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5fcdd10d");
                removeObjVar(player, "conversation.nym_technician_3.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_3_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cbc1d532"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6e00532f");
                removeObjVar(player, "conversation.nym_technician_3.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_3_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4d5276b"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                nym_technician_3_action_grantAssassinateMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_69e50906");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_3_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52fbe706");
                    }
                    setObjVar(player, "conversation.nym_technician_3.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_3.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_3_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52fbe706"))
        {
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c1013aa7");
                removeObjVar(player, "conversation.nym_technician_3.branchId");
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
            detachScript(self, "conversation.nym_technician_3");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.nym_technician_3");
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
        if (nym_technician_3_condition_questDone(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e32d36db");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nym_technician_3_condition_failedQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e32d36db");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nym_technician_3_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_248faf8a");
                }
                setObjVar(player, "conversation.nym_technician_3.branchId", 2);
                npcStartConversation(player, npc, "nym_technician_3", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nym_technician_3_condition_isOnQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e32d36db");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nym_technician_3_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_76b02ea3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nym_technician_3_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (nym_technician_3_condition_isOnStormSquadronQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (nym_technician_3_condition_isOnStormSquadronQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (nym_technician_3_condition_isOnStormSquadronQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (nym_technician_3_condition_isHuntingShip(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8804f6ee");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67a590c3");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_62f865dd");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f49a789f");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7002e5c4");
                }
                setObjVar(player, "conversation.nym_technician_3.branchId", 6);
                npcStartConversation(player, npc, "nym_technician_3", message, responses);
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
        if (!conversationId.equals("nym_technician_3"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = getIntObjVar(player, "conversation.nym_technician_3.branchId");
        if (branchId == 2 && nym_technician_3_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && nym_technician_3_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && nym_technician_3_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && nym_technician_3_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && nym_technician_3_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && nym_technician_3_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && nym_technician_3_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && nym_technician_3_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.nym_technician_3.branchId");
        return SCRIPT_CONTINUE;
    }
}
