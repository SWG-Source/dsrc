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
import script.library.utils;

public class seans_historian extends script.base_script
{
    public seans_historian()
    {
    }
    public static String c_stringFile = "conversation/seans_historian";
    public boolean seans_historian_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean seans_historian_condition_historyQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.hquest"))
        {
            if (hasObjVar(npc, "bestine.electionWinner"))
            {
                String winner = getStringObjVar(npc, "bestine.electionWinner");
                if ((winner.equals("sean")) || (winner.equals("Sean")))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean seans_historian_condition_hasFind(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.find"))
        {
            if (hasObjVar(npc, "bestine.electionWinner"))
            {
                String winner = getStringObjVar(npc, "bestine.electionWinner");
                if ((winner.equals("sean")) || (winner.equals("Sean")))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean seans_historian_condition_hasItem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_history_disk.iff"))
        {
            return true;
        }
        return false;
    }
    public boolean seans_historian_condition_inoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionWinner"))
        {
            String winner = getStringObjVar(npc, "bestine.electionWinner");
            if ((winner.equals("sean")) || (winner.equals("Sean")))
            {
                return true;
            }
        }
        return false;
    }
    public void seans_historian_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void seans_historian_action_setFind(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.find", true);
        removeObjVar(player, "bestine.hquest");
        if (hasObjVar(player, "bestine.searched"))
        {
            removeObjVar(player, "bestine.searched");
        }
        if (hasObjVar(player, "bestine.already_searched"))
        {
            removeObjVar(player, "bestine.already_searched");
        }
    }
    public void seans_historian_action_ItemfoundObj(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.itemfound", true);
        removeObjVar(player, "bestine.find");
        if (hasObjVar(player, "bestine.hquest"))
        {
            removeObjVar(player, "bestine.hquest");
        }
        obj_id waypoint = getObjIdObjVar(player, "bestine.historianWaypoint");
        if (isIdValid(waypoint))
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        removeObjVar(player, "bestine.historianWaypoint");
    }
    public void seans_historian_action_cleanUpOldVars(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_history_disk.iff"))
        {
            if (hasObjVar(player, "bestine.mess"))
            {
                removeObjVar(player, "bestine.mess");
            }
            if (hasObjVar(player, "bestine.itemfound"))
            {
                removeObjVar(player, "bestine.itemfound");
            }
        }
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.seans_historian");
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
        detachScript(self, "npc.conversation.seans_historian");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (seans_historian_condition_historyQuest(player, self))
        {
            seans_historian_action_cleanUpOldVars(player, self);
            string_id message = new string_id(c_stringFile, "s_65275fb4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (seans_historian_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_647601e7");
                }
                setObjVar(player, "conversation.seans_historian.branchId", 1);
                npcStartConversation(player, self, "seans_historian", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (seans_historian_condition_hasFind(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8d32acbf");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (seans_historian_condition_hasItem(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (seans_historian_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b30bd73c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9c1a8dd7");
                }
                setObjVar(player, "conversation.seans_historian.branchId", 6);
                npcStartConversation(player, self, "seans_historian", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (seans_historian_condition_inoffice(player, self))
        {
            seans_historian_action_cleanUpOldVars(player, self);
            string_id message = new string_id(c_stringFile, "s_79636e28");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (seans_historian_condition__defaultCondition(player, self))
        {
            seans_historian_action_cleanUpOldVars(player, self);
            string_id message = new string_id(c_stringFile, "s_3d3d4bab");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("seans_historian"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.seans_historian.branchId");
        if (branchId == 1 && response.equals("s_647601e7"))
        {
            if (seans_historian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1f6355d4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (seans_historian_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e547fd1e");
                    }
                    setObjVar(player, "conversation.seans_historian.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.seans_historian.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah! You must be the new assistant. Welcome to the crash site of the Red-Sin Valon. Before you start poking around, maybe you'll want to hear a little history about the site first. It will give you a little sensitivity about an already-delicate situation.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_e547fd1e"))
        {
            if (seans_historian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4d42d7da");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (seans_historian_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (seans_historian_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ec0adb6");
                    }
                    setObjVar(player, "conversation.seans_historian.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.seans_historian.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Red-Sin Valon carried the first of the new settlers to Tatooine. The captain of the ship and her crew members helped found Bestine. Despite the early difficulties--a severe economic depression, lack of water and so forth--Bestine survived and became the thriving city we see today. There's so much more depth to it...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_67e6df55"))
        {
            if (seans_historian_condition__defaultCondition(player, self))
            {
                seans_historian_action_setFind(player, self);
                string_id message = new string_id(c_stringFile, "s_2fceb7e3");
                removeObjVar(player, "conversation.seans_historian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Anyway, you should check the wreckage to see what you find. If you do find anything, you can bring it to me. I'm required to screen whatever is found. We don't need to excite Sean Trenwell over nothing, you know what I mean? Go ahead and look around.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_5ec0adb6"))
        {
            if (seans_historian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fcdb7174");
                removeObjVar(player, "conversation.seans_historian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Anyway, you should check the wreckage to see what you find. If you do find anything, you can bring it to me. I'm required to screen whatever is found. We don't need to excite Sean Trenwell over nothing, you know what I mean? Go ahead and look around.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_b30bd73c"))
        {
            if (seans_historian_condition__defaultCondition(player, self))
            {
                seans_historian_action_ItemfoundObj(player, self);
                string_id message = new string_id(c_stringFile, "s_27540308");
                removeObjVar(player, "conversation.seans_historian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Did you find anything?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_9c1a8dd7"))
        {
            if (seans_historian_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ed53cd9c");
                removeObjVar(player, "conversation.seans_historian.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Did you find anything?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.seans_historian.branchId");
        return SCRIPT_CONTINUE;
    }
}
