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

public class theme_park_record_keeper_jabba extends script.base_script
{
    public theme_park_record_keeper_jabba()
    {
    }
    public static String c_stringFile = "conversation/theme_park_record_keeper_jabba";
    public boolean theme_park_record_keeper_jabba_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean theme_park_record_keeper_jabba_condition_theme_park_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "theme_park_reset.jabba"))
        {
            if (hasObjVar(player, "theme_park_jabba"))
            {
                int gating = getIntObjVar(player, "theme_park_jabba");
                if (gating >= 49)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean theme_park_record_keeper_jabba_condition_theme_park_in_progress(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "theme_park_reset.jabba"))
        {
            if (hasObjVar(player, "theme_park_jabba"))
            {
                int gating = getIntObjVar(player, "theme_park_jabba");
                if (gating > 0)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean theme_park_record_keeper_jabba_condition_already_reset(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "theme_park_reset.jabba"))
        {
            return true;
        }
        return false;
    }
    public void theme_park_record_keeper_jabba_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void theme_park_record_keeper_jabba_action_reset_gating(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "theme_park_reset.jabba", true);
        int gating = getIntObjVar(player, "theme_park_jabba");
        removeObjVar(player, "theme_park_jabba");
        CustomerServiceLog("ThemePark", "RESET OPTION: Jabba's Palace -- " + getName(player) + " (" + player + ") has opted to RESET his/her theme park obj var [" + gating + "] and START OVER");
    }
    public void theme_park_record_keeper_jabba_action_set_redo(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "theme_park_reset.jabba", true);
        CustomerServiceLog("ThemePark", "RESET OPTION: Jabba's Palace -- " + getName(player) + " (" + player + ") has opted to NOT RESET his/her theme park obj var and CONTINUE");
    }
    public void theme_park_record_keeper_jabba_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.theme_park_record_keeper_jabba");
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
        detachScript(self, "npc.conversation.theme_park_record_keeper_jabba");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (theme_park_record_keeper_jabba_condition_already_reset(player, self))
        {
            theme_park_record_keeper_jabba_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_adccd297");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_jabba_condition_theme_park_complete(player, self))
        {
            theme_park_record_keeper_jabba_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_3be84186");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 2);
                npcStartConversation(player, self, "theme_park_record_keeper_jabba", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_jabba_condition_theme_park_in_progress(player, self))
        {
            theme_park_record_keeper_jabba_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_3be84186");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 19);
                npcStartConversation(player, self, "theme_park_record_keeper_jabba", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
        {
            theme_park_record_keeper_jabba_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_3ae9c534");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("theme_park_record_keeper_jabba"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
        if (branchId == 2 && response.equals("s_f51b07ff"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_34f17e66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, I do need to speak with you. I have questions that you need to answer.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_2e18346d"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f3139d0");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, I do need to speak with you. I have questions that you need to answer.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_edb238bc"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c7528c3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_866cf013");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710595f4");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Due to an unfortunate incident caused by an irresponsible little droid, CZ-4, all of Lord Jabba's employment and accounting records were erased.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f3139d0");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Due to an unfortunate incident caused by an irresponsible little droid, CZ-4, all of Lord Jabba's employment and accounting records were erased.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_866cf013"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c1ff278a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am sure you can imagine the turmoil it caused. Those on Jabba's payroll are no longer getting paid, and those in debt to him, were free of their obligations. Needless to say, the CZ droid responsible was... disciplined.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f3139d0");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am sure you can imagine the turmoil it caused. Those on Jabba's payroll are no longer getting paid, and those in debt to him, were free of their obligations. Needless to say, the CZ droid responsible was... disciplined.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_9ef848f9"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5cc1a230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3cf75f2d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6e88ecc1");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I must recompile all of the data that was lost. For that reason, I must ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f3139d0");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I must recompile all of the data that was lost. For that reason, I must ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_3cf75f2d"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_72907ccb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It would seem that you have performed great services for Jabba in the past, and he has rewarded you well. However, if you wish, I can reset your employment history in our records. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b193a14c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fd35e09");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93e226b7");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It would seem that you have performed great services for Jabba in the past, and he has rewarded you well. However, if you wish, I can reset your employment history in our records. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_6e88ecc1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6d63d30d");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It would seem that you have performed great services for Jabba in the past, and he has rewarded you well. However, if you wish, I can reset your employment history in our records. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b193a14c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fd35e09");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93e226b7");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist Jabba again as you have done in the past. But don't worry, you will be rewarded again, and it will in no way reduce your standing in the eyes of Jabba himself. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_4fd35e09"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_jabba_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_630c8b2");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_93e226b7"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6d63d30d");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_4fd35e09"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_jabba_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_630c8b2");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_93e226b7"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6d63d30d");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_f51b07ff"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_34f17e66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, I do need to speak with you. I have questions that you need to answer.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_2e18346d"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f3139d0");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, I do need to speak with you. I have questions that you need to answer.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_edb238bc"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c7528c3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_866cf013");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710595f4");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Due to an unfortunate incident caused by an irresponsible little droid, CZ-4, all of Lord Jabba's employment and accounting records were erased.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f3139d0");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Due to an unfortunate incident caused by an irresponsible little droid, CZ-4, all of Lord Jabba's employment and accounting records were erased.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_866cf013"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c1ff278a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am sure you can imagine the turmoil it caused. Those on Jabba's payroll are no longer getting paid, and those in debt to him, were free of their obligations. Needless to say, the CZ droid responsible was... disciplined.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f3139d0");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am sure you can imagine the turmoil it caused. Those on Jabba's payroll are no longer getting paid, and those in debt to him, were free of their obligations. Needless to say, the CZ droid responsible was... disciplined.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_9ef848f9"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5e8caf64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a76f22bf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14693cf1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I must recompile all of the data that was lost. For that reason, I must ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f3139d0");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I must recompile all of the data that was lost. For that reason, I must ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eac2e473");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you may serve Lord Jabba again. Otherwise, you can continue your service to him where you left off. Remember that once you decide, you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_741d0966");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you may serve Lord Jabba again. Otherwise, you can continue your service to him where you left off. Remember that once you decide, you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e11b3d7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fd35e09");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you may serve Lord Jabba again. Otherwise, you can continue your service to him where you left off. Remember that once you decide, you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_badb4859");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7565bbd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you may serve Lord Jabba again. Otherwise, you can continue your service to him where you left off. Remember that once you decide, you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_555b76aa");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you may serve Lord Jabba again. Otherwise, you can continue your service to him where you left off. Remember that once you decide, you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_741d0966");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist Lord Jabba again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the eyes of Jabba the Hutt. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e11b3d7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fd35e09");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist Lord Jabba again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the eyes of Jabba the Hutt. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_badb4859");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7565bbd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist Lord Jabba again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the eyes of Jabba the Hutt. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_555b76aa");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist Lord Jabba again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the eyes of Jabba the Hutt. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eac2e473");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. That person will then tell you how you may best serve Jabba. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e11b3d7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fd35e09");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. That person will then tell you how you may best serve Jabba. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_badb4859");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7565bbd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. That person will then tell you how you may best serve Jabba. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_555b76aa");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. That person will then tell you how you may best serve Jabba. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_4fd35e09"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_jabba_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_630c8b2");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_ebce22a9"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_960270ff");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a76f22bf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14693cf1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eac2e473");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_741d0966");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e11b3d7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fd35e09");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_badb4859");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7565bbd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_555b76aa");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_f7565bbd"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_jabba_action_set_redo(player, self);
                string_id message = new string_id(c_stringFile, "s_faf7c09a");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Remember that once you decide to continue, you cannot change your mind. Are you sure that you want to continue your services to Jabba where you left off?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_ebce22a9"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_960270ff");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a76f22bf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14693cf1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff20b1f1");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Remember that once you decide to continue, you cannot change your mind. Are you sure that you want to continue your services to Jabba where you left off?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eac2e473");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_741d0966");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e11b3d7c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fd35e09");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_badb4859");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f7565bbd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_jabba_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_555b76aa");
                removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. But do not take long.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.theme_park_record_keeper_jabba.branchId");
        return SCRIPT_CONTINUE;
    }
}
