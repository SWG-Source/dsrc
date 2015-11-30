package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;

public class coa3ltactofficer extends script.base_script
{
    public coa3ltactofficer()
    {
    }
    public static String c_stringFile = "conversation/coa3ltactofficer";
    public boolean coa3ltactofficer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean coa3ltactofficer_condition_extraFloraMissionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.extraFlora") == 1);
    }
    public boolean coa3ltactofficer_condition_hasWonStory(obj_id player, obj_id npc) throws InterruptedException
    {
        return (badge.hasBadge(player, "event_coa3_imperial"));
    }
    public boolean coa3ltactofficer_condition_storyFloraMissionComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") > 302);
    }
    public boolean coa3ltactofficer_condition_talkedToCoordinator(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == 301);
    }
    public boolean coa3ltactofficer_condition_storyFloraMissionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == 302);
    }
    public boolean coa3ltactofficer_condition_temp(obj_id player, obj_id npc) throws InterruptedException
    {
        return false;
    }
    public void coa3ltactofficer_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void coa3ltactofficer_action_refreshExtraFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3ltactofficer_action_abortExtraFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "coa3.extraFlora");
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", -1);
        messageTo(player, "handleAbortMission", params, 0, false);
    }
    public void coa3ltactofficer_action_getExtraFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.extraFlora", 1);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3ltactofficer_action_getStoryFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 302);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3ltactofficer_action_refreshStoryFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 302);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3ltactofficer_action_abortStoryFloraMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 301);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 3);
        messageTo(player, "handleAbortMission", params, 0, false);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.coa3ltactofficer");
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
        detachScript(self, "npc.conversation.coa3ltactofficer");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (coa3ltactofficer_condition_extraFloraMissionActive(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_743c9f18");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_688721b0");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f2fe6350");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2e791c9d");
                }
                setObjVar(player, "conversation.coa3ltactofficer.branchId", 1);
                npcStartConversation(player, self, "coa3ltactofficer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3ltactofficer_condition_hasWonStory(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_958a6a44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1cb2157b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5166bfb2");
                }
                setObjVar(player, "conversation.coa3ltactofficer.branchId", 5);
                npcStartConversation(player, self, "coa3ltactofficer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3ltactofficer_condition_storyFloraMissionComplete(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_2c01da39");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1cb2157b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4e9f338");
                }
                setObjVar(player, "conversation.coa3ltactofficer.branchId", 8);
                npcStartConversation(player, self, "coa3ltactofficer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3ltactofficer_condition_talkedToCoordinator(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_1c693828");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f96e5eed");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e4daff30");
                }
                setObjVar(player, "conversation.coa3ltactofficer.branchId", 11);
                npcStartConversation(player, self, "coa3ltactofficer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3ltactofficer_condition_storyFloraMissionActive(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_3c57b03");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3ltactofficer_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ba1a3080");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f2fe6350");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2e791c9d");
                }
                setObjVar(player, "conversation.coa3ltactofficer.branchId", 14);
                npcStartConversation(player, self, "coa3ltactofficer", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3ltactofficer_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f64d217f");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (coa3ltactofficer_condition_temp(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_4fcdc5b8");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("coa3ltactofficer"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.coa3ltactofficer.branchId");
        if (branchId == 1 && response.equals("s_688721b0"))
        {
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_38f00205");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You still haven't destroyed that warehouse. Why is that?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_f2fe6350"))
        {
            coa3ltactofficer_action_refreshExtraFloraMission(player, self);
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_268282e6");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You still haven't destroyed that warehouse. Why is that?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_2e791c9d"))
        {
            coa3ltactofficer_action_abortExtraFloraMission(player, self);
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3d82703f");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You still haven't destroyed that warehouse. Why is that?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_1cb2157b"))
        {
            coa3ltactofficer_action_getExtraFloraMission(player, self);
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1f281089");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You've been a real hero lately, people have been buzzing about your work. If you're looking for something to do, there's plenty more warehouses to take care of! And I'm still offering a reward for turning in any Alderaanian Flora you come across.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_5166bfb2"))
        {
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_18c581cc");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You've been a real hero lately, people have been buzzing about your work. If you're looking for something to do, there's plenty more warehouses to take care of! And I'm still offering a reward for turning in any Alderaanian Flora you come across.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_1cb2157b"))
        {
            coa3ltactofficer_action_getExtraFloraMission(player, self);
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ed0f23a8");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you haven't completed the Coordinator's tasks, you should return to him. If you have, we know of more warehouses you can assault. Either way, you can hand me any Alderaanian Flora you have and I will happily reward you for it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_4e9f338"))
        {
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_18c581cc");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you haven't completed the Coordinator's tasks, you should return to him. If you have, we know of more warehouses you can assault. Either way, you can hand me any Alderaanian Flora you have and I will happily reward you for it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_f96e5eed"))
        {
            coa3ltactofficer_action_getStoryFloraMission(player, self);
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1b5eac32");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I hear that you've been working with the coordinator lately to try to put a stop to this whole Dead Eye thing. Good for you! Another way we can hamper the production of Dead Eye is by attacking their store houses directly. The more Alderaanian Flora we can keep out of their hands, the better. Would you be willing to help us out in this matter?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_e4daff30"))
        {
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_83258fb0");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I hear that you've been working with the coordinator lately to try to put a stop to this whole Dead Eye thing. Good for you! Another way we can hamper the production of Dead Eye is by attacking their store houses directly. The more Alderaanian Flora we can keep out of their hands, the better. Would you be willing to help us out in this matter?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_ba1a3080"))
        {
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e40779f7");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why haven't you destroyed that warehouse? Is something the matter?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_f2fe6350"))
        {
            coa3ltactofficer_action_refreshStoryFloraMission(player, self);
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_268282e6");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why haven't you destroyed that warehouse? Is something the matter?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_2e791c9d"))
        {
            coa3ltactofficer_action_abortStoryFloraMission(player, self);
            if (coa3ltactofficer_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6a582260");
                removeObjVar(player, "conversation.coa3ltactofficer.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why haven't you destroyed that warehouse? Is something the matter?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.coa3ltactofficer.branchId");
        return SCRIPT_CONTINUE;
    }
}
