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

public class nym_technician_1 extends script.base_script
{
    public nym_technician_1()
    {
    }
    public static String c_stringFile = "conversation/nym_technician_1";
    public boolean nym_technician_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nym_technician_1_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "ss.findingAgent");
    }
    public boolean nym_technician_1_condition_isHuntingShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "ss.huntingShip");
    }
    public boolean nym_technician_1_condition_hasCompletedMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "assassinate", "imperial_ss_3");
    }
    public boolean nym_technician_1_condition_hasReceivedReward2(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "assassinate", "imperial_ss_3");
    }
    public boolean nym_technician_1_condition_hasFailedKimogilaClaw(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "assassinate", "imperial_ss_3") || space_quest.hasAbortedQuest(player, "assassinate", "imperial_ss_3"));
    }
    public void nym_technician_1_action_setHuntingShip(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.removeSpaceFlag(player, "ss.findingAgent");
        space_flags.setSpaceFlag(player, "ss.huntingShip", 1);
    }
    public void nym_technician_1_action_setAssassinateMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "imperial_ss_3");
    }
    public int nym_technician_1_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7d114e8"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                nym_technician_1_action_setAssassinateMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_ccc1622");
                removeObjVar(player, "conversation.nym_technician_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fdee07ad"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51f0669b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b8779a78");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b8779a78"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a84adf58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90422eb5");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90422eb5"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39510e77");
                removeObjVar(player, "conversation.nym_technician_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ced0fec8"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_aae05f6b");
                removeObjVar(player, "conversation.nym_technician_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67a590c3"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8d9eefec");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67a590c3");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62f865dd"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dce481f1");
                removeObjVar(player, "conversation.nym_technician_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35de4fcc"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_135c7b04");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_169df3bb");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67a590c3"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66c37cce");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37dad091");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37dad091"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42e8bcae");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_395d4c4");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_395d4c4"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ed4d7aa6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_536a83dc");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_536a83dc"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9518acb5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c70b951e");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c70b951e"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d724a599");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81e175a9");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81e175a9"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                nym_technician_1_action_setHuntingShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b4b03fd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e15acd61");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e15acd61"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3e5e55b6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nym_technician_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c3323fd");
                    }
                    setObjVar(player, "conversation.nym_technician_1.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.nym_technician_1.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5c3323fd"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_273b318b");
                removeObjVar(player, "conversation.nym_technician_1.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nym_technician_1_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_169df3bb"))
        {
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2d587639");
                removeObjVar(player, "conversation.nym_technician_1.branchId");
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
            detachScript(self, "conversation.nym_technician_1");
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
        detachScript(self, "conversation.nym_technician_1");
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
        if (nym_technician_1_condition_hasFailedKimogilaClaw(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e727edeb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nym_technician_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7d114e8");
                }
                setObjVar(player, "conversation.nym_technician_1.branchId", 1);
                npcStartConversation(player, npc, "nym_technician_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nym_technician_1_condition_hasReceivedReward2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3345f404");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nym_technician_1_condition_hasCompletedMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3345f404");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nym_technician_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fdee07ad");
                }
                setObjVar(player, "conversation.nym_technician_1.branchId", 4);
                npcStartConversation(player, npc, "nym_technician_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nym_technician_1_condition_isHuntingShip(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3345f404");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nym_technician_1_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ccc723ff");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nym_technician_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (nym_technician_1_condition_isOnMission(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (nym_technician_1_condition_isOnMission(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (nym_technician_1_condition_isOnMission(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ced0fec8");
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_35de4fcc");
                }
                setObjVar(player, "conversation.nym_technician_1.branchId", 9);
                npcStartConversation(player, npc, "nym_technician_1", message, responses);
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
        if (!conversationId.equals("nym_technician_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = getIntObjVar(player, "conversation.nym_technician_1.branchId");
        if (branchId == 1 && nym_technician_1_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && nym_technician_1_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && nym_technician_1_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && nym_technician_1_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && nym_technician_1_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && nym_technician_1_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && nym_technician_1_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && nym_technician_1_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && nym_technician_1_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && nym_technician_1_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && nym_technician_1_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && nym_technician_1_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && nym_technician_1_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && nym_technician_1_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.nym_technician_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
