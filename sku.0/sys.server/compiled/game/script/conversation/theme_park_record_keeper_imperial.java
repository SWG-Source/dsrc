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

public class theme_park_record_keeper_imperial extends script.base_script
{
    public theme_park_record_keeper_imperial()
    {
    }
    public static String c_stringFile = "conversation/theme_park_record_keeper_imperial";
    public boolean theme_park_record_keeper_imperial_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean theme_park_record_keeper_imperial_condition_theme_park_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "theme_park_reset.imperial"))
        {
            if (hasObjVar(player, "theme_park_imperial"))
            {
                int gating = getIntObjVar(player, "theme_park_imperial");
                if (gating >= 39)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean theme_park_record_keeper_imperial_condition_theme_park_in_progress(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "theme_park_reset.imperial"))
        {
            if (hasObjVar(player, "theme_park_imperial"))
            {
                int gating = getIntObjVar(player, "theme_park_imperial");
                if (gating > 0)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean theme_park_record_keeper_imperial_condition_already_reset(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "theme_park_reset.imperial"))
        {
            return true;
        }
        return false;
    }
    public boolean theme_park_record_keeper_imperial_condition_not_imperial(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null || playerFaction.equals(""))
        {
            playerFaction = "neutral";
        }
        if (!playerFaction.equals(factions.FACTION_IMPERIAL))
        {
            return true;
        }
        return false;
    }
    public void theme_park_record_keeper_imperial_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void theme_park_record_keeper_imperial_action_reset_gating(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "theme_park_reset.imperial", true);
        int gating = getIntObjVar(player, "theme_park_imperial");
        removeObjVar(player, "theme_park_imperial");
        CustomerServiceLog("ThemePark", "RESET OPTION: Imperial -- " + getName(player) + " (" + player + ") has opted to RESET his/her theme park obj var [" + gating + "] and START OVER");
    }
    public void theme_park_record_keeper_imperial_action_set_redo(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "theme_park_reset.imperial", true);
        CustomerServiceLog("ThemePark", "RESET OPTION: Imperial -- " + getName(player) + " (" + player + ") has opted to NOT RESET his/her theme park obj var and CONTINUE");
    }
    public void theme_park_record_keeper_imperial_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.theme_park_record_keeper_imperial");
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
        detachScript(self, "npc.conversation.theme_park_record_keeper_imperial");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (theme_park_record_keeper_imperial_condition_not_imperial(player, self))
        {
            theme_park_record_keeper_imperial_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_cc3c10d2");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_imperial_condition_already_reset(player, self))
        {
            theme_park_record_keeper_imperial_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_13f4f4e9");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_imperial_condition_theme_park_complete(player, self))
        {
            theme_park_record_keeper_imperial_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_906058f6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 3);
                npcStartConversation(player, self, "theme_park_record_keeper_imperial", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_imperial_condition_theme_park_in_progress(player, self))
        {
            theme_park_record_keeper_imperial_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_7837b05f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 18);
                npcStartConversation(player, self, "theme_park_record_keeper_imperial", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
        {
            theme_park_record_keeper_imperial_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_8886fdb");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("theme_park_record_keeper_imperial"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
        if (branchId == 3 && response.equals("s_f51b07ff"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c2663b63");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc551f39");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710595f4");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. Yes, I recognize you. Who wouldn't? You have done great services for the Empire. Actually I'm glad you stopped by, as I need to ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_2e18346d"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_26df7262");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. Yes, I recognize you. Who wouldn't? You have done great services for the Empire. Actually I'm glad you stopped by, as I need to ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_cc551f39"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bc41b885");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b7023094");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710595f4");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You see, I've performed a grievous error, and many of the Imperial employment records were lost in a major computer crash. Given the rash nature of Lord Vader, I'm quite lucky to be alive actually.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_26df7262");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You see, I've performed a grievous error, and many of the Imperial employment records were lost in a major computer crash. Given the rash nature of Lord Vader, I'm quite lucky to be alive actually.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_b7023094"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5c8a1b58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, in order to keep my head on my shoulders, I have to recompile all the data in a timely fashion, and I need to ask you a question or two regarding your employment history.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_26df7262");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, in order to keep my head on my shoulders, I have to recompile all the data in a timely fashion, and I need to ask you a question or two regarding your employment history.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_3cf75f2d"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_37160714");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Normally, those held in such high esteem as yourself are not asked to perform such services as you have done in the past. However, if you wish, I can reset your employment history with the Empire. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b193a14c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Normally, those held in such high esteem as yourself are not asked to perform such services as you have done in the past. However, if you wish, I can reset your employment history with the Empire. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_6e88ecc1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ea196d60");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Normally, those held in such high esteem as yourself are not asked to perform such services as you have done in the past. However, if you wish, I can reset your employment history with the Empire. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b193a14c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Empire again as you have done in the past. But don't worry, you will be rewarded again, and it will in no way reduce your standing in the rank of the Empire. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_4fd35e09"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_imperial_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_f1105bbd");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_93e226b7"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ea196d60");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_4fd35e09"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_imperial_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_f1105bbd");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_93e226b7"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ea196d60");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_f51b07ff"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c2663b63");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cc551f39");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710595f4");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. Ah yes, I know who you are. You have helped the Empire to a degree in the past. Actually I'm glad you stopped by, as I need to ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_2e18346d"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_26df7262");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. Ah yes, I know who you are. You have helped the Empire to a degree in the past. Actually I'm glad you stopped by, as I need to ask you some questions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_cc551f39"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bc41b885");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b7023094");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_710595f4");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You see, I've performed a grievous error, and many of the Imperial employment records were lost in a major computer crash. Given the rash nature of Lord Vader, I'm quite lucky to be alive actually.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_26df7262");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You see, I've performed a grievous error, and many of the Imperial employment records were lost in a major computer crash. Given the rash nature of Lord Vader, I'm quite lucky to be alive actually.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_b7023094"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ac97bdc2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, in order to keep my head on my shoulders, I have to recompile all the data in a timely fashion, and I need to ask you a question or two regarding your employment history.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_26df7262");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, in order to keep my head on my shoulders, I have to recompile all the data in a timely fashion, and I need to ask you a question or two regarding your employment history.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ce56f943");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you can begin your service again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2380d732");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you can begin your service again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b193a14c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you can begin your service again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you can begin your service again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your employment history and you can begin your service again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2380d732");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Empire again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the rank of the Empire. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b193a14c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Empire again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the rank of the Empire. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Empire again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the rank of the Empire. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Empire again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the rank of the Empire. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ce56f943");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. You will then serve however he or she deems best. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b193a14c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. You will then serve however he or she deems best. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. You will then serve however he or she deems best. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. You will then serve however he or she deems best. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_4fd35e09"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_imperial_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_f1105bbd");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_ebce22a9"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3c440d33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your employment history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ce56f943");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2380d732");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b193a14c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_f7565bbd"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_imperial_action_set_redo(player, self);
                string_id message = new string_id(c_stringFile, "s_f2538a47");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but remember that once you decide to continue, you cannot change your mind. Are you sure that you want to continue your service where you left off?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_ebce22a9"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3c440d33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but remember that once you decide to continue, you cannot change your mind. Are you sure that you want to continue your service where you left off?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ce56f943");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2380d732");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_ff20b1f1"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b193a14c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_imperial_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.theme_park_record_keeper_imperial.branchId");
        return SCRIPT_CONTINUE;
    }
}
