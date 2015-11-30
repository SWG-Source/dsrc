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

public class keanna_likyna extends script.base_script
{
    public keanna_likyna()
    {
    }
    public static String c_stringFile = "conversation/keanna_likyna";
    public boolean keanna_likyna_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean keanna_likyna_condition_Camp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.camp"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.camp");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean keanna_likyna_condition_NegQ(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negquests"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negquests");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean keanna_likyna_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public void keanna_likyna_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.keanna_likyna");
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
        detachScript(self, "npc.conversation.keanna_likyna");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (keanna_likyna_condition_Camp(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_398a575d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (keanna_likyna_condition__defaultCondition(player, self))
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
                setObjVar(player, "conversation.keanna_likyna.branchId", 1);
                npcStartConversation(player, self, "keanna_likyna", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (keanna_likyna_condition_NegQ(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8080f094");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (keanna_likyna_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_be0e4a12");
                }
                setObjVar(player, "conversation.keanna_likyna.branchId", 4);
                npcStartConversation(player, self, "keanna_likyna", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (keanna_likyna_condition_nonoffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_36d62208");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (keanna_likyna_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_b1103974");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (keanna_likyna_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d6ecfaf4");
                }
                setObjVar(player, "conversation.keanna_likyna.branchId", 7);
                npcStartConversation(player, self, "keanna_likyna", message, responses);
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
        if (!conversationId.equals("keanna_likyna"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.keanna_likyna.branchId");
        if (branchId == 1 && response.equals("s_e547fd1e"))
        {
            if (keanna_likyna_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c14a1c83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (keanna_likyna_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0cb487a");
                    }
                    setObjVar(player, "conversation.keanna_likyna.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.keanna_likyna.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'How exciting! I'm so glad you could join us. Evidence, hm? I know exactly the people you should talk to to get such a thing. How about... well, this may or may not work, but there's a terminal in here somewhere with job registries. I'm new, so I really don't know where it is.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_d0cb487a"))
        {
            if (keanna_likyna_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1f3a981f");
                removeObjVar(player, "conversation.keanna_likyna.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Also, you can try the orphanage director. She volunteers at the hospital. And... there's a woman in the slums area you can speak with as well. I don't have direct locations for them. I'm sorry. I really hope this can help you! You only need one piece of evidence.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_be0e4a12"))
        {
            if (keanna_likyna_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a80d9308");
                removeObjVar(player, "conversation.keanna_likyna.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'He's convinced you to do some dirty work, has he? That's unfortunate. I wish others would play fairly. Oh well. Let's see... I'm sure there's something in his office that'd be of use to you. And his teacher at the Commerce University might have something. Oh, I don't know... what else. Hm. He has an accountant as well. I'm out of ideas.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_d6ecfaf4"))
        {
            if (keanna_likyna_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b4ecef81");
                removeObjVar(player, "conversation.keanna_likyna.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Did that, still need to do that... Oh! I didn't see you there. The new election has created a huge work load for me! I'm practically oblivious to everything else. I'm Victor's secretary. I schedule his trips, meetings... just about everything! Are you going to vote for him? It's just so exciting! He has so many great changes in store for Bestine. You should speak with him and hear what he has to say.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.keanna_likyna.branchId");
        return SCRIPT_CONTINUE;
    }
}
