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

public class bestine_artist01 extends script.base_script
{
    public bestine_artist01()
    {
    }
    public static String c_stringFile = "conversation/bestine_artist01";
    public boolean bestine_artist01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean bestine_artist01_condition_hasFeaturedArt_inEvent(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((bestine_artist01_condition_isActiveInEvent(player, npc)) && (bestine_artist01_condition_hasFeaturedArt(player, npc)));
    }
    public boolean bestine_artist01_condition_isActiveInEvent(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            if (hasObjVar(player, "bestine.museumEventPlayer"))
            {
                int playerEventNum = getIntObjVar(player, "bestine.museumEventPlayer");
                if (playerEventNum >= eventNum)
                {
                    String entry01 = "";
                    String entry02 = "";
                    String entry03 = "";
                    if (hasObjVar(npc, "bestine.museumEventEntry01"))
                    {
                        entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry02"))
                    {
                        entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry03"))
                    {
                        entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
                    }
                    if (entry01.equals("bestine_artist01"))
                    {
                        return true;
                    }
                    if (entry02.equals("bestine_artist01"))
                    {
                        return true;
                    }
                    if (entry03.equals("bestine_artist01"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean bestine_artist01_condition_hasFeaturedArt(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventWinner"))
        {
            String winner = getStringObjVar(npc, "bestine.museumEventWinner");
            if (winner.equals("bestine_artist01"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean bestine_artist01_condition_hasAlreadyVoted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            if (hasObjVar(player, "bestine.museumEventPlayerVoted"))
            {
                int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
                int playerEventNum = getIntObjVar(player, "bestine.museumEventPlayerVoted");
                if (playerEventNum >= eventNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean bestine_artist01_condition_alreadySpokenTo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            if (hasObjVar(player, "bestine.spokeToArtist01"))
            {
                int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
                int playerEventNum = getIntObjVar(player, "bestine.spokeToArtist01");
                if (playerEventNum >= eventNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void bestine_artist01_action_setHasSpokenTo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            int currentEventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            setObjVar(player, "bestine.spokeToArtist01", currentEventNum);
            if (hasObjVar(player, "museumEventPlayer"))
            {
                removeObjVar(player, "museumEventPlayer");
            }
        }
        return;
    }
    public int bestine_artist01_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40f54223"))
        {
            if (!bestine_artist01_condition_alreadySpokenTo(player, npc))
            {
                bestine_artist01_action_setHasSpokenTo(player, npc);
                string_id message = new string_id(c_stringFile, "s_908359f7");
                utils.removeScriptVar(player, "conversation.bestine_artist01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (bestine_artist01_condition_alreadySpokenTo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_378506ca");
                utils.removeScriptVar(player, "conversation.bestine_artist01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e7133472"))
        {
            if (bestine_artist01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62dd13f8");
                utils.removeScriptVar(player, "conversation.bestine_artist01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int bestine_artist01_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c061a6db"))
        {
            if (!bestine_artist01_condition_alreadySpokenTo(player, npc))
            {
                bestine_artist01_action_setHasSpokenTo(player, npc);
                string_id message = new string_id(c_stringFile, "s_f58736af");
                utils.removeScriptVar(player, "conversation.bestine_artist01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (bestine_artist01_condition_alreadySpokenTo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22e081ab");
                utils.removeScriptVar(player, "conversation.bestine_artist01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4a276e66"))
        {
            if (bestine_artist01_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e60617b5");
                utils.removeScriptVar(player, "conversation.bestine_artist01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.bestine_artist01");
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
        detachScript(self, "conversation.bestine_artist01");
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
        if (bestine_artist01_condition_hasFeaturedArt_inEvent(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21666bd5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!bestine_artist01_condition_hasAlreadyVoted(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (bestine_artist01_condition_hasAlreadyVoted(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40f54223");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e7133472");
                }
                utils.setScriptVar(player, "conversation.bestine_artist01.branchId", 1);
                npcStartConversation(player, npc, "bestine_artist01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (bestine_artist01_condition_isActiveInEvent(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7afc26f5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!bestine_artist01_condition_hasAlreadyVoted(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (bestine_artist01_condition_hasAlreadyVoted(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c061a6db");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4a276e66");
                }
                utils.setScriptVar(player, "conversation.bestine_artist01.branchId", 5);
                npcStartConversation(player, npc, "bestine_artist01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (bestine_artist01_condition_hasFeaturedArt(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b7a88f36");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (bestine_artist01_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_506f2192");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("bestine_artist01"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.bestine_artist01.branchId");
        if (branchId == 1 && bestine_artist01_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && bestine_artist01_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.bestine_artist01.branchId");
        return SCRIPT_CONTINUE;
    }
}
