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

public class coa3lgeneralveers extends script.base_script
{
    public coa3lgeneralveers()
    {
    }
    public static String c_stringFile = "conversation/coa3lgeneralveers";
    public boolean coa3lgeneralveers_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean coa3lgeneralveers_condition_spokeCoordinatorLast(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(player, "coa3.convTracker") == 401;
    }
    public boolean coa3lgeneralveers_condition_finalMissionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(player, "coa3.convTracker") == 402;
    }
    public boolean coa3lgeneralveers_condition_hasFinishedStory(obj_id player, obj_id npc) throws InterruptedException
    {
        return (badge.hasBadge(player, "event_coa3_imperial"));
    }
    public void coa3lgeneralveers_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void coa3lgeneralveers_action_getFinalMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 402);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 5);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3lgeneralveers_action_refreshFinalMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 402);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 5);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3lgeneralveers_action_abortFinalMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 401);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 5);
        messageTo(player, "handleAbortMission", params, 0, false);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.coa3lgeneralveers");
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
        detachScript(self, "npc.conversation.coa3lgeneralveers");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (coa3lgeneralveers_condition_hasFinishedStory(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_bea4597");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (coa3lgeneralveers_condition_spokeCoordinatorLast(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_1cfbefd4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fdba7430");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2977a9a3");
                }
                setObjVar(player, "conversation.coa3lgeneralveers.branchId", 2);
                npcStartConversation(player, self, "coa3lgeneralveers", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3lgeneralveers_condition_finalMissionActive(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_370a3d89");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_92d64a93");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6be1cd6b");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2cdb5dd0");
                }
                setObjVar(player, "conversation.coa3lgeneralveers.branchId", 5);
                npcStartConversation(player, self, "coa3lgeneralveers", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3lgeneralveers_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_4d8cedd4");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("coa3lgeneralveers"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.coa3lgeneralveers.branchId");
        if (branchId == 2 && response.equals("s_fdba7430"))
        {
            coa3lgeneralveers_action_getFinalMission(player, self);
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1a6ba35a");
                removeObjVar(player, "conversation.coa3lgeneralveers.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So you're the one we've been hearing so much about. I wanted to personally thank you for doing such a commendable job. The Empire needs more Soldiers like you. Thanks to your efforts we were able to find one of their research facilities. Destroying this facility would strike a serious blow against them and could significantly hamper their efforts. We need that facility destroyed and I think you're just the person for the job. Can I count on you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_2977a9a3"))
        {
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_efae2d69");
                removeObjVar(player, "conversation.coa3lgeneralveers.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So you're the one we've been hearing so much about. I wanted to personally thank you for doing such a commendable job. The Empire needs more Soldiers like you. Thanks to your efforts we were able to find one of their research facilities. Destroying this facility would strike a serious blow against them and could significantly hamper their efforts. We need that facility destroyed and I think you're just the person for the job. Can I count on you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_92d64a93"))
        {
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a486253d");
                removeObjVar(player, "conversation.coa3lgeneralveers.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What's the matter, don't know how to follow orders? Why haven't you destroyed the facility yet?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_6be1cd6b"))
        {
            coa3lgeneralveers_action_refreshFinalMission(player, self);
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b02864c6");
                removeObjVar(player, "conversation.coa3lgeneralveers.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What's the matter, don't know how to follow orders? Why haven't you destroyed the facility yet?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_2cdb5dd0"))
        {
            coa3lgeneralveers_action_abortFinalMission(player, self);
            if (coa3lgeneralveers_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4461239");
                removeObjVar(player, "conversation.coa3lgeneralveers.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What's the matter, don't know how to follow orders? Why haven't you destroyed the facility yet?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.coa3lgeneralveers.branchId");
        return SCRIPT_CONTINUE;
    }
}
