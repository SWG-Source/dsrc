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

public class bestine_artist06 extends script.base_script
{
    public bestine_artist06()
    {
    }
    public static String c_stringFile = "conversation/bestine_artist06";
    public boolean bestine_artist06_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean bestine_artist06_condition_hasFeaturedArt(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventWinner"))
        {
            String winner = getStringObjVar(npc, "bestine.museumEventWinner");
            if (winner.equals("bestine_artist06"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean bestine_artist06_condition_isActiveInEvent(obj_id player, obj_id npc) throws InterruptedException
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
                    if (entry01.equals("bestine_artist06"))
                    {
                        return true;
                    }
                    if (entry02.equals("bestine_artist06"))
                    {
                        return true;
                    }
                    if (entry03.equals("bestine_artist06"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean bestine_artist06_condition_hasFeaturedArt_EventActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((bestine_artist06_condition_isActiveInEvent(player, npc)) && (bestine_artist06_condition_hasFeaturedArt(player, npc)));
    }
    public boolean bestine_artist06_condition_hasSpokenTo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            if (hasObjVar(player, "bestine.spokeToArtist06"))
            {
                int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
                int playerEventNum = getIntObjVar(player, "bestine.spokeToArtist06");
                if (playerEventNum >= eventNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean bestine_artist06_condition_hasAlreadyVoted(obj_id player, obj_id npc) throws InterruptedException
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
    public void bestine_artist06_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void bestine_artist06_action_setHasSpokenTo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            int currentEventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            setObjVar(player, "bestine.spokeToArtist06", currentEventNum);
            if (hasObjVar(player, "museumEventPlayer"))
            {
                removeObjVar(player, "museumEventPlayer");
            }
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.bestine_artist06");
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
        detachScript(self, "npc.conversation.bestine_artist06");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (bestine_artist06_condition_hasFeaturedArt_EventActive(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_21352168");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!bestine_artist06_condition_hasAlreadyVoted(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (bestine_artist06_condition_hasAlreadyVoted(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a13d05db");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1433b9d4");
                }
                setObjVar(player, "conversation.bestine_artist06.branchId", 1);
                npcStartConversation(player, self, "bestine_artist06", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (bestine_artist06_condition_isActiveInEvent(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8a659170");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!bestine_artist06_condition_hasAlreadyVoted(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (bestine_artist06_condition_hasAlreadyVoted(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32722caf");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3a301702");
                }
                setObjVar(player, "conversation.bestine_artist06.branchId", 5);
                npcStartConversation(player, self, "bestine_artist06", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (bestine_artist06_condition_hasFeaturedArt(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_bf3a373");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (bestine_artist06_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_54e93392");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("bestine_artist06"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.bestine_artist06.branchId");
        if (branchId == 1 && response.equals("s_a13d05db"))
        {
            if (!bestine_artist06_condition_hasSpokenTo(player, self))
            {
                bestine_artist06_action_setHasSpokenTo(player, self);
                string_id message = new string_id(c_stringFile, "s_23697fe2");
                removeObjVar(player, "conversation.bestine_artist06.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (bestine_artist06_condition_hasSpokenTo(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_66605741");
                removeObjVar(player, "conversation.bestine_artist06.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you wish to have your eyes opened with new visions of the world around you, then your first step should be to go to the Bestine Museum and see my painting on display there.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_1433b9d4"))
        {
            if (bestine_artist06_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30df2fe8");
                removeObjVar(player, "conversation.bestine_artist06.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you wish to have your eyes opened with new visions of the world around you, then your first step should be to go to the Bestine Museum and see my painting on display there.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_32722caf"))
        {
            if (!bestine_artist06_condition_hasSpokenTo(player, self))
            {
                bestine_artist06_action_setHasSpokenTo(player, self);
                string_id message = new string_id(c_stringFile, "s_29026317");
                removeObjVar(player, "conversation.bestine_artist06.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (bestine_artist06_condition_hasSpokenTo(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_582eefe3");
                removeObjVar(player, "conversation.bestine_artist06.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Expand you mind, my friend. There's more to see than what your eyes tell you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_3a301702"))
        {
            if (bestine_artist06_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7154d52c");
                removeObjVar(player, "conversation.bestine_artist06.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Expand you mind, my friend. There's more to see than what your eyes tell you.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.bestine_artist06.branchId");
        return SCRIPT_CONTINUE;
    }
}
