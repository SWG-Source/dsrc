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

public class theme_park_record_keeper_rebel extends script.base_script
{
    public theme_park_record_keeper_rebel()
    {
    }
    public static String c_stringFile = "conversation/theme_park_record_keeper_rebel";
    public boolean theme_park_record_keeper_rebel_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean theme_park_record_keeper_rebel_condition_theme_park_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "theme_park_reset.rebel"))
        {
            if (hasObjVar(player, "theme_park_rebel"))
            {
                int gating = getIntObjVar(player, "theme_park_rebel");
                if (gating >= 49)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean theme_park_record_keeper_rebel_condition_theme_park_in_progress(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "theme_park_reset.rebel"))
        {
            if (hasObjVar(player, "theme_park_rebel"))
            {
                int gating = getIntObjVar(player, "theme_park_rebel");
                if (gating > 0)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean theme_park_record_keeper_rebel_condition_already_reset(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "theme_park_reset.rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean theme_park_record_keeper_rebel_condition_not_rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null || playerFaction.equals(""))
        {
            playerFaction = "neutral";
        }
        if (!playerFaction.equals(factions.FACTION_REBEL))
        {
            return true;
        }
        return false;
    }
    public void theme_park_record_keeper_rebel_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void theme_park_record_keeper_rebel_action_reset_gating(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "theme_park_reset.rebel", true);
        int gating = getIntObjVar(player, "theme_park_rebel");
        removeObjVar(player, "theme_park_rebel");
        CustomerServiceLog("ThemePark", "RESET OPTION: Rebel -- " + getName(player) + " (" + player + ") has opted to RESET his/her theme park obj var [" + gating + "] and START OVER");
    }
    public void theme_park_record_keeper_rebel_action_set_redo(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "theme_park_reset.rebel", true);
        CustomerServiceLog("ThemePark", "RESET OPTION: Rebel -- " + getName(player) + " (" + player + ") has opted to NOT RESET his/her theme park obj var and CONTINUE");
    }
    public void theme_park_record_keeper_rebel_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.theme_park_record_keeper_rebel");
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
        detachScript(self, "npc.conversation.theme_park_record_keeper_rebel");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (theme_park_record_keeper_rebel_condition_not_rebel(player, self))
        {
            theme_park_record_keeper_rebel_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_b1f9a8ac");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_rebel_condition_already_reset(player, self))
        {
            theme_park_record_keeper_rebel_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_5a47438c");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_rebel_condition_theme_park_complete(player, self))
        {
            theme_park_record_keeper_rebel_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_37420d49");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 3);
                npcStartConversation(player, self, "theme_park_record_keeper_rebel", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_rebel_condition_theme_park_in_progress(player, self))
        {
            theme_park_record_keeper_rebel_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_37420d49");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 20);
                npcStartConversation(player, self, "theme_park_record_keeper_rebel", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
        {
            theme_park_record_keeper_rebel_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_3547d92c");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("theme_park_record_keeper_rebel"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
        if (branchId == 3 && response.equals("s_f51b07ff"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ce26bb71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, good. I'm glad you stopped by, now I don't have to go looking for you. I need to ask you some questions if you have a moment to spare.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_2e18346d"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b78f6030");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, good. I'm glad you stopped by, now I don't have to go looking for you. I need to ask you some questions if you have a moment to spare.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_edb238bc"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_19da6a67");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We almost had a major catastrophe. The data disks that we use to keep all information on our allies that have performed services for our cause were stolen recently by an Imperial spy.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b78f6030");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We almost had a major catastrophe. The data disks that we use to keep all information on our allies that have performed services for our cause were stolen recently by an Imperial spy.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_866cf013"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a45bb0ec");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We don't know who the spy was, but we were able to destroy his ship as it took off. He was killed in the explosion, but unfortunately the data disks he stole were destroyed as well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b78f6030");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We don't know who the spy was, but we were able to destroy his ship as it took off. He was killed in the explosion, but unfortunately the data disks he stole were destroyed as well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_b7023094"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3315cf03");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6e88ecc1");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, it's up to me to recompile all the data that was on those disks, and I need to ask you a question or two regarding your employment history.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b78f6030");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, it's up to me to recompile all the data that was on those disks, and I need to ask you a question or two regarding your employment history.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_3cf75f2d"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_48d3face");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Normally, those who have helped us so willingly in the past are not called on to do so again. However, if you wish, I can reset your service history with the Rebellion. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_9bc93d72"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11f0619b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc1c8663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93e226b7");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Normally, those who have helped us so willingly in the past are not called on to do so again. However, if you wish, I can reset your service history with the Rebellion. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_6e88ecc1"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ea196d60");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Normally, those who have helped us so willingly in the past are not called on to do so again. However, if you wish, I can reset your service history with the Rebellion. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_9bc93d72"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11f0619b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc1c8663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93e226b7");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Rebellion again as you have done in the past. But don't worry, you will be rewarded again, and it will in no way reduce your standing in the rank of the Rebellion. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_dc1c8663"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_rebel_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_7666799");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your service history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_93e226b7"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ea196d60");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your service history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_dc1c8663"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_rebel_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_7666799");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your service history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_93e226b7"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ea196d60");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your service history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_f51b07ff"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ce26bb71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, good. I'm glad you stopped by, now I don't have to go looking for you. I need to ask you some questions if you have a moment to spare.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && response.equals("s_2e18346d"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b78f6030");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, good. I'm glad you stopped by, now I don't have to go looking for you. I need to ask you some questions if you have a moment to spare.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_edb238bc"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_19da6a67");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We almost had a major catastrophe. The data disks that we use to keep all information on our allies that have performed services for our cause were stolen recently by an Imperial spy.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b78f6030");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We almost had a major catastrophe. The data disks that we use to keep all information on our allies that have performed services for our cause were stolen recently by an Imperial spy.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_866cf013"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a45bb0ec");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We don't know who the spy was, but we were able to destroy his ship as it took off. He was killed in the explosion, but unfortunately the data disks he stole were destroyed as well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b78f6030");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We don't know who the spy was, but we were able to destroy his ship as it took off. He was killed in the explosion, but unfortunately the data disks he stole were destroyed as well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_b7023094"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2eadaa06");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, it's up to me to recompile all the data that was on those disks, and I need to ask you a question or two regarding your employment history.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && response.equals("s_710595f4"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b78f6030");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, it's up to me to recompile all the data that was on those disks, and I need to ask you a question or two regarding your employment history.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_deeb1462");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your service history and you can assist the Rebellion again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3d89228f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your service history and you can assist the Rebellion again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_9bc93d72"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11f0619b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc1c8663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your service history and you can assist the Rebellion again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your service history and you can assist the Rebellion again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you like, I can reset your service history and you can assist the Rebellion again. Otherwise, you can continue your services where you left off. Remember that once you decide, you cannot change your mind. Do you want me to reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3d89228f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Rebellion again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the rank of the Rebellion. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_9bc93d72"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11f0619b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc1c8663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Rebellion again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the rank of the Rebellion. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Rebellion again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the rank of the Rebellion. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If I reset your history, you will be able to go back and assist the Rebellion again as you have done in the past. But don't worry, you will be rewarded, and it will in no way reduce your standing in the rank of the Rebellion. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_deeb1462");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. That person will give you further instructions then. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_9bc93d72"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11f0619b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc1c8663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. That person will give you further instructions then. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. That person will give you further instructions then. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you choose to continue from where you left off, you will simply return to the person which last gave you a job. That person will give you further instructions then. But remember that once you decide you cannot change your mind. Shall I reset your history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_dc1c8663"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_rebel_action_reset_gating(player, self);
                string_id message = new string_id(c_stringFile, "s_7666799");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your service history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_ebce22a9"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3c440d33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but keep in mind that once I reset your history, it cannot be undone. Are you sure that you want me to reset your service history?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_deeb1462");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
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
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3d89228f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_9bc93d72"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11f0619b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc1c8663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
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
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
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
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && response.equals("s_f7565bbd"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                theme_park_record_keeper_rebel_action_set_redo(player, self);
                string_id message = new string_id(c_stringFile, "s_45b87508");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but remember that once you decide to continue, you cannot change your mind. Are you sure that you want to continue your service where you left off?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && response.equals("s_ebce22a9"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3c440d33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright, but remember that once you decide to continue, you cannot change your mind. Are you sure that you want to continue your service where you left off?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && response.equals("s_a76f22bf"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_deeb1462");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && response.equals("s_14693cf1"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3d89228f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bc93d72");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e3c4a3d5");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd7fb6d");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && response.equals("s_9bc93d72"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11f0619b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc1c8663");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebce22a9");
                    }
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && response.equals("s_e3c4a3d5"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_49d330ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && response.equals("s_bfd7fb6d"))
        {
            if (theme_park_record_keeper_rebel_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b845961");
                removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I understand completely. Take all the time you need.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.theme_park_record_keeper_rebel.branchId");
        return SCRIPT_CONTINUE;
    }
}
