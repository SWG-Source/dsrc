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

public class indigo_siyan extends script.base_script
{
    public indigo_siyan()
    {
    }
    public static String c_stringFile = "conversation/indigo_siyan";
    public boolean indigo_siyan_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean indigo_siyan_condition_Campaign(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.campaign"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.campaign");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean indigo_siyan_condition_Negative(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negativeq"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negativeq");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean indigo_siyan_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public void indigo_siyan_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.indigo_siyan");
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
        detachScript(self, "npc.conversation.indigo_siyan");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (indigo_siyan_condition_Campaign(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_bdba42ab");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (indigo_siyan_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_540ac7e9");
                }
                setObjVar(player, "conversation.indigo_siyan.branchId", 1);
                npcStartConversation(player, self, "indigo_siyan", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (indigo_siyan_condition_Negative(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e095f053");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (indigo_siyan_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                }
                setObjVar(player, "conversation.indigo_siyan.branchId", 4);
                npcStartConversation(player, self, "indigo_siyan", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (indigo_siyan_condition_nonoffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_88b121e8");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (indigo_siyan_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f10cc45e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (indigo_siyan_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                }
                setObjVar(player, "conversation.indigo_siyan.branchId", 7);
                npcStartConversation(player, self, "indigo_siyan", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("indigo_siyan"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.indigo_siyan.branchId");
        if (branchId == 1 && response.equals("s_540ac7e9"))
        {
            if (indigo_siyan_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7fe92f9e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (indigo_siyan_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81fdc59e");
                    }
                    setObjVar(player, "conversation.indigo_siyan.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.indigo_siyan.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Don't mind him. He can be a little pushy when it comes to these things. What he may not have told you is that you can go to Victor if you decide this campaign isn't for you. He'll probably have some task for you to do in order to join, but that's okay. Nothing too difficult, I'd hope. Anyway, let me see what sort of contacts I have for you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_81fdc59e"))
        {
            if (indigo_siyan_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_83beea8a");
                removeObjVar(player, "conversation.indigo_siyan.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let's see... There are several citizens that I can think of who have been affected by Sean's generosity. The Curator of the museum, for one. Sean's housemaid can tell you a story or two, and you can check over at the new market place. You only need one piece of evidence, so you can choose which suits you. Unfortunately, I don't have direct locations of them. You'll have to find them on your own. I hope this will help you in the long run.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_b9b27823"))
        {
            if (indigo_siyan_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fd9f62f9");
                removeObjVar(player, "conversation.indigo_siyan.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Poor Victor. He's such a nice man, but don't tell Sean I said that. Mind you, I'm reluctantly giving you this information. Don't share it with anyone. Let's see what I may have for you. Okay. There's a Hutt in one of the cantinas you can talk to. I know that one of his troopers isn't necessarily loyal either. He's around the capitol.  And you may be able to rummage around Victor's office for nasty tidbits as well. That's all I know.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_b9b27823"))
        {
            if (indigo_siyan_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_abf4a6a9");
                removeObjVar(player, "conversation.indigo_siyan.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome! Please, help yourself to tea or pastries! Isn't the election exciting? Are you here to vote for Sean? Oh, pardon me! I'm so rude. I shouldn't ask that. I'm Sean's secretary. I'm so glad to be working for him. He has so many great ideas. Please speak with him as soon as possible. He'll tell you all the things he has in store for Bestine.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.indigo_siyan.branchId");
        return SCRIPT_CONTINUE;
    }
}
