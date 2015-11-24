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
import script.library.fs_quests;
import script.library.quests;

public class fs_intro_oldman_initial extends script.base_script
{
    public fs_intro_oldman_initial()
    {
    }
    public static String c_stringFile = "conversation/fs_intro_oldman_initial";
    public boolean fs_intro_oldman_initial_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_intro_oldman_initial_condition_Intro(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id owner = null;
        if (hasObjVar(npc, "old_man_initial.holder"))
        {
            owner = getObjIdObjVar(npc, "old_man_initial.holder");
        }
        else 
        {
            owner = getObjIdObjVar(npc, "old_man_final.holder");
        }
        if (owner == player)
        {
            int questStage = getIntObjVar(player, "fs_kickoff_stage");
            if (questStage == 2)
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_intro_oldman_initial_condition_Exit(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id owner = null;
        if (hasObjVar(npc, "old_man_initial.holder"))
        {
            owner = getObjIdObjVar(npc, "old_man_initial.holder");
        }
        else 
        {
            owner = getObjIdObjVar(npc, "old_man_final.holder");
        }
        if (owner == player)
        {
            int questStage = getIntObjVar(player, "fs_kickoff_stage");
            if (questStage == 9)
            {
                return true;
            }
        }
        return false;
    }
    public boolean fs_intro_oldman_initial_condition_None(obj_id player, obj_id npc) throws InterruptedException
    {
        int stage = 0;
        if (hasObjVar(player, "fs_kickoff_stage"))
        {
            stage = getIntObjVar(player, "fs_kickoff_stage");
        }
        obj_id owner = null;
        if (hasObjVar(npc, "old_man_initial.holder"))
        {
            owner = getObjIdObjVar(npc, "old_man_initial.holder");
        }
        else 
        {
            owner = getObjIdObjVar(npc, "old_man_final.holder");
        }
        if (owner != player)
        {
            return true;
        }
        if ((stage != 9) && (stage != 2))
        {
            return true;
        }
        return false;
    }
    public void fs_intro_oldman_initial_action_action0001(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests.setStage(player, 0);
        fs_quests.setDelay(player, 0);
        fs_quests.oldManDepart(player, npc, 1);
        return;
    }
    public void fs_intro_oldman_initial_action_action0002(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests.setStage(player, 0);
        fs_quests.setDelay(player, 0);
        fs_quests.oldManDepart(player, npc, 1);
        return;
    }
    public void fs_intro_oldman_initial_action_action0003(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate("old_man_force_crystal", player, null);
        sendSystemMessage(player, new string_id("quest/force_sensitive/intro", "crystal_message"));
        fs_quests.oldManDepart(player, npc, 0);
        fs_quests.setStage(player, 3);
        int stage = getIntObjVar(player, "fs_kickoff_stage");
        fs_quests.setDelay(player, stage);
        return;
    }
    public void fs_intro_oldman_initial_action_action0004(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests.setStage(player, 9);
        fs_quests.setDelay(player, 13);
        fs_quests.oldManDepart(player, npc, 2);
        return;
    }
    public void fs_intro_oldman_initial_action_action0005(obj_id player, obj_id npc) throws InterruptedException
    {
        quests.activate("fs_theater_final", player, null);
        fs_quests.setStage(player, 10);
        fs_quests.oldManDepart(player, npc, 0);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_intro_oldman_initial");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
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
        detachScript(self, "npc.conversation.fs_intro_oldman_initial");
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
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (fs_intro_oldman_initial_condition_Intro(player, self))
        {
            doAnimationAction(self, "greet");
            string_id message = new string_id(c_stringFile, "s_de9da426");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_501448da");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fb9e0b93");
                }
                setObjVar(player, "conversation.fs_intro_oldman_initial.branchId", 1);
                npcStartConversation(player, self, "fs_intro_oldman_initial", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_intro_oldman_initial_condition_Exit(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_958f442");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fbd984c8");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_477068d1");
                }
                setObjVar(player, "conversation.fs_intro_oldman_initial.branchId", 9);
                npcStartConversation(player, self, "fs_intro_oldman_initial", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_intro_oldman_initial_condition_None(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_c7146ea8");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("fs_intro_oldman_initial"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
        if (branchId == 1 && response.equals("s_501448da"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7f8604a5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a3291a29");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6b88daf2");
                    }
                    setObjVar(player, "conversation.fs_intro_oldman_initial.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. I have been meaning to speak with you for some time. Do you have a moment?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_fb9e0b93"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_383079e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e0a90395");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aa6a1756");
                    }
                    setObjVar(player, "conversation.fs_intro_oldman_initial.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. I have been meaning to speak with you for some time. Do you have a moment?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_a3291a29"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cf55fe01");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b81ceeb4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c6f70929");
                    }
                    setObjVar(player, "conversation.fs_intro_oldman_initial.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I was wondering if you had ever considered the possibility that you might be connected with the Force?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_6b88daf2"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eb24a286");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b81ceeb4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c6f70929");
                    }
                    setObjVar(player, "conversation.fs_intro_oldman_initial.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I was wondering if you had ever considered the possibility that you might be connected with the Force?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_b81ceeb4"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                fs_intro_oldman_initial_action_action0003(player, self);
                string_id message = new string_id(c_stringFile, "s_3aab754");
                removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So you sense it as well.  I have a Force crystal here if you are interested in discovering for certain.  Perhaps you could hold onto it and keep it safe? ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_c6f70929"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                fs_intro_oldman_initial_action_action0002(player, self);
                string_id message = new string_id(c_stringFile, "s_83e20fb8");
                removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So you sense it as well.  I have a Force crystal here if you are interested in discovering for certain.  Perhaps you could hold onto it and keep it safe? ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_b81ceeb4"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                fs_intro_oldman_initial_action_action0003(player, self);
                string_id message = new string_id(c_stringFile, "s_3aab754");
                removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmmmm.  Perhaps I misjudged you. I have a Force crystal here if you are interested in discovering for certain. Perhaps you could hold onto it and keep it safe?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_c6f70929"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                fs_intro_oldman_initial_action_action0002(player, self);
                string_id message = new string_id(c_stringFile, "s_83e20fb8");
                removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hmmmm.  Perhaps I misjudged you. I have a Force crystal here if you are interested in discovering for certain. Perhaps you could hold onto it and keep it safe?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_e0a90395"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "wave1");
                fs_intro_oldman_initial_action_action0001(player, self);
                string_id message = new string_id(c_stringFile, "s_419f8694");
                removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you sure?  It's a fairly urgent matter.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_aa6a1756"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7f8604a5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a3291a29");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6b88daf2");
                    }
                    setObjVar(player, "conversation.fs_intro_oldman_initial.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Are you sure?  It's a fairly urgent matter.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_fbd984c8"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_31a054f9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9641e6e");
                    }
                    setObjVar(player, "conversation.fs_intro_oldman_initial.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello again, my friend.  Thank you for all of your help at the village.  The time has come for you to hunt down and destroy Mellichae. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_477068d1"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                fs_intro_oldman_initial_action_action0004(player, self);
                string_id message = new string_id(c_stringFile, "s_ac22e1a3");
                removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello again, my friend.  Thank you for all of your help at the village.  The time has come for you to hunt down and destroy Mellichae. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_9641e6e"))
        {
            if (fs_intro_oldman_initial_condition__defaultCondition(player, self))
            {
                fs_intro_oldman_initial_action_action0005(player, self);
                string_id message = new string_id(c_stringFile, "s_f5c5ace0");
                removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Mellichae is the leader of the Sith Shadows.  He is responsible for the attacks at the village.  Scouts have reported he has set up camp near here.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_intro_oldman_initial.branchId");
        return SCRIPT_CONTINUE;
    }
}
