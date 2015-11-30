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
import script.library.factions;

public class static_quests_record_keeper_tatooine extends script.base_script
{
    public static_quests_record_keeper_tatooine()
    {
    }
    public static String c_stringFile = "conversation/static_quests_record_keeper_tatooine";
    public boolean static_quests_record_keeper_tatooine_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean static_quests_record_keeper_tatooine_condition_static_quests_on_player(obj_id player, obj_id npc) throws InterruptedException
    {
        String datatable = "datatables/npc/static_quest/static_quest_records_tatooine.iff";
        String[] static_quest = dataTableGetStringColumn(datatable, "static_quest");
        for (int i = 0; i < static_quest.length; i++)
        {
            if (hasObjVar(player, "static." + static_quest[i]))
            {
                return true;
            }
        }
        return false;
    }
    public boolean static_quests_record_keeper_tatooine_condition_already_reset(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "static_quests_reset.tatooine"))
        {
            return true;
        }
        return false;
    }
    public void static_quests_record_keeper_tatooine_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void static_quests_record_keeper_tatooine_action_reset_gating(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "static_quests_reset.tatooine", true);
        String datatable = "datatables/npc/static_quest/static_quest_records_tatooine.iff";
        String[] static_quest = dataTableGetStringColumn(datatable, "static_quest");
        for (int i = 0; i < static_quest.length; i++)
        {
            if (hasObjVar(player, "static." + static_quest[i]))
            {
                removeObjVar(player, "static." + static_quest[i]);
            }
        }
        int gating = getIntObjVar(player, "static_quests_reset.tatooine");
        CustomerServiceLog("StaticQuestTatooine", "RESET OPTION: Static quests on Tatooine -- " + getName(player) + " (" + player + ") has opted to RESET his/her Tatooine static quest obj vars [" + gating + "] and START OVER");
    }
    public void static_quests_record_keeper_tatooine_action_set_redo(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "static_quests_reset.tatooine", true);
        CustomerServiceLog("StaticQuestTatooine", "RESET OPTION: Static quests on Tatooine -- " + getName(player) + " (" + player + ") has opted to NOT RESET his/her Tatooine static quest obj vars and CONTINUE");
    }
    public void static_quests_record_keeper_tatooine_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.static_quests_record_keeper_tatooine");
        }
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
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
        detachScript(self, "npc.conversation.static_quests_record_keeper_tatooine");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (static_quests_record_keeper_tatooine_condition_already_reset(player, self))
        {
            static_quests_record_keeper_tatooine_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_32bfa453");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (static_quests_record_keeper_tatooine_condition_static_quests_on_player(player, self))
        {
            static_quests_record_keeper_tatooine_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_5c12e6ea");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f51b07ff");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2e18346d");
                }
                setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 2);
                npcStartConversation(player, self, "static_quests_record_keeper_tatooine", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
        {
            static_quests_record_keeper_tatooine_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_9413f709");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("static_quests_record_keeper_tatooine"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
        if (branchId == 2 && response.equals("s_f51b07ff"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eef03120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_edb238bc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710595f4");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Greetings citizen. My records indicate that you have been assisting fellow citizens and officials around Tatooine. Unfortunately, our databank had a 'accident', and I need to ask you some questions to fill in the blanks.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_2e18346d"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b10b47");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Greetings citizen. My records indicate that you have been assisting fellow citizens and officials around Tatooine. Unfortunately, our databank had a 'accident', and I need to ask you some questions to fill in the blanks.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_edb238bc"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cd1a9013");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_12936cce");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710595f4");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We believe the Rebel scum attacked our database with a virus. It erased a lot of the progress that adventurers like yourself have done for citizens here on Tatooine.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_710595f4"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_71a41175");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We believe the Rebel scum attacked our database with a virus. It erased a lot of the progress that adventurers like yourself have done for citizens here on Tatooine.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_12936cce"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a87d342e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9ef848f9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710595f4");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'My job is to update these records to their former standard, if citizens so wish, or to blank them out completely and let them start over on a clean slate.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_710595f4"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_71a41175");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'My job is to update these records to their former standard, if citizens so wish, or to blank them out completely and let them start over on a clean slate.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_9ef848f9"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_884006f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b1f6ba02");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e61fd069");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc924cf1");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287908df");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d50e348");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I must recompile all of the data that was lost, if you so wish. For that reason, I must ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_710595f4"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_71a41175");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I must recompile all of the data that was lost, if you so wish. For that reason, I must ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_b1f6ba02"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e8ad81f2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14693cf1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can just erase your progress, you would save me a lot of time and you could go around and help citizens all over again, with them rewarding you appropriately. Otherwise, you can update my records with your old progress and go on as nothing happened. Remember that once you decide, you cannot change your mind. Shall I erase the information in your databank?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_e61fd069"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_83c0df9e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a76f22bf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can just erase your progress, you would save me a lot of time and you could go around and help citizens all over again, with them rewarding you appropriately. Otherwise, you can update my records with your old progress and go on as nothing happened. Remember that once you decide, you cannot change your mind. Shall I erase the information in your databank?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_cc924cf1"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ab18ccf6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0902f5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can just erase your progress, you would save me a lot of time and you could go around and help citizens all over again, with them rewarding you appropriately. Otherwise, you can update my records with your old progress and go on as nothing happened. Remember that once you decide, you cannot change your mind. Shall I erase the information in your databank?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_287908df"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3b67eeac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5db2d1c9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can just erase your progress, you would save me a lot of time and you could go around and help citizens all over again, with them rewarding you appropriately. Otherwise, you can update my records with your old progress and go on as nothing happened. Remember that once you decide, you cannot change your mind. Shall I erase the information in your databank?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_6d50e348"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6de63882");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can just erase your progress, you would save me a lot of time and you could go around and help citizens all over again, with them rewarding you appropriately. Otherwise, you can update my records with your old progress and go on as nothing happened. Remember that once you decide, you cannot change your mind. Shall I erase the information in your databank?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_14693cf1"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_83c0df9e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a76f22bf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your file, you will be able to go back and assist citizens on Tatooine again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the eyes of the public or any organizations you're assosciated with. Shall I reset your file?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_ff20b1f1"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ab18ccf6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0902f5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your file, you will be able to go back and assist citizens on Tatooine again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the eyes of the public or any organizations you're assosciated with. Shall I reset your file?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_e3c4a3d5"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3b67eeac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5db2d1c9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your file, you will be able to go back and assist citizens on Tatooine again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the eyes of the public or any organizations you're assosciated with. Shall I reset your file?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_bfd7fb6d"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6de63882");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your file, you will be able to go back and assist citizens on Tatooine again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the eyes of the public or any organizations you're assosciated with. Shall I reset your file?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_a76f22bf"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e8ad81f2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14693cf1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Then you will just continue where you were. Any missions for citizens that you are currently doing will stay as they are and citizens you're already done helping, will have records of that. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_ff20b1f1"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ab18ccf6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0902f5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Then you will just continue where you were. Any missions for citizens that you are currently doing will stay as they are and citizens you're already done helping, will have records of that. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_e3c4a3d5"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3b67eeac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5db2d1c9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Then you will just continue where you were. Any missions for citizens that you are currently doing will stay as they are and citizens you're already done helping, will have records of that. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_bfd7fb6d"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6de63882");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Then you will just continue where you were. Any missions for citizens that you are currently doing will stay as they are and citizens you're already done helping, will have records of that. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_d0902f5e"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                static_quests_record_keeper_tatooine_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_ae08bd89");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Keep in mind that once I reset your file, it cannot be undone. Are you sure that you want me to blank out your databank?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_1288f6ef"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3d27cb3d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b1f6ba02");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e61fd069");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc924cf1");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287908df");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d50e348");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Keep in mind that once I reset your file, it cannot be undone. Are you sure that you want me to blank out your databank?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_b1f6ba02"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e8ad81f2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14693cf1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_e61fd069"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_83c0df9e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a76f22bf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_cc924cf1"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ab18ccf6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0902f5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_287908df"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3b67eeac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5db2d1c9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_6d50e348"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6de63882");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_5db2d1c9"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                static_quests_record_keeper_tatooine_action_set_redo(player, self);
                string_id message = new string_id(c_stringFile, "s_b8ea8b67");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Remember that once you decide to update your files and continue as normal, you cannot change your mind. Are you sure that you want me to update your record?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_1288f6ef"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3d27cb3d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b1f6ba02");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e61fd069");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc924cf1");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_287908df");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d50e348");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Remember that once you decide to update your files and continue as normal, you cannot change your mind. Are you sure that you want me to update your record?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_b1f6ba02"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e8ad81f2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14693cf1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_e61fd069"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_83c0df9e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a76f22bf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_cc924cf1"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ab18ccf6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0902f5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_287908df"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3b67eeac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5db2d1c9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1288f6ef");
                    }
                    setObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_6d50e348"))
        {
            if (static_quests_record_keeper_tatooine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6de63882");
                removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long, my time is valuable.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.static_quests_record_keeper_tatooine.branchId");
        return SCRIPT_CONTINUE;
    }
}
