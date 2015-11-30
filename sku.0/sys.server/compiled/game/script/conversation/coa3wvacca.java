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

public class coa3wvacca extends script.base_script
{
    public coa3wvacca()
    {
    }
    public static String c_stringFile = "conversation/coa3wvacca";
    public boolean coa3wvacca_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean coa3wvacca_condition_hasCOA2Badge(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") < -400 && badge.hasBadge(player, "event_coa2_rebel"));
    }
    public boolean coa3wvacca_condition_notReadyToTalk(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") > -400);
    }
    public boolean coa3wvacca_condition_isReadyToTalk(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") < -400);
    }
    public void coa3wvacca_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.coa3wvacca");
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
        detachScript(self, "npc.conversation.coa3wvacca");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (coa3wvacca_condition_notReadyToTalk(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8e697de4");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (coa3wvacca_condition_hasCOA2Badge(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_913b747e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3wvacca_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11d4164");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bbc96393");
                }
                setObjVar(player, "conversation.coa3wvacca.branchId", 2);
                npcStartConversation(player, self, "coa3wvacca", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3wvacca_condition_isReadyToTalk(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_be18866d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3wvacca_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11d4164");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bbc96393");
                }
                setObjVar(player, "conversation.coa3wvacca.branchId", 11);
                npcStartConversation(player, self, "coa3wvacca", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3wvacca_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8e697de4");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("coa3wvacca"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.coa3wvacca.branchId");
        if (branchId == 2 && response.equals("s_11d4164"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11b13f7a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2b7f750");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're the one who sent me the message from the Princess. I really have to thank you. I was deluding myself for far too long about the Empire. I'm glad I was able to give Dead Eye to the Rebellion .' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_bbc96393"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ba517076");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96405da8");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're the one who sent me the message from the Princess. I really have to thank you. I was deluding myself for far too long about the Empire. I'm glad I was able to give Dead Eye to the Rebellion .' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_b2b7f750"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d24fb27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bbc96393");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Right now we have some stores of Alderaanian Flora, but my home planet was the last place it grew. Once it runs out Dead Eye will be useless.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_bbc96393"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ba517076");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96405da8");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If we can find an alternative flora resource, we can try to make another version. We have a number of research stations scattered about a number of planets testing various samples.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_96405da8"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6d583b5");
                removeObjVar(player, "conversation.coa3wvacca.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'After reading the message from Princess Leia I realized that I had blinded myself to the evils of the Empire. After further communications I had my eyes opened to the real reason Alderaan was destroyed.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_96405da8"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6d583b5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11d4164");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'After reading the message from Princess Leia I realized that I had blinded myself to the evils of the Empire. After further communications I had my eyes opened to the real reason Alderaan was destroyed.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_11d4164"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11b13f7a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2b7f750");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It wasn't because of Rebel activity. It was a whim. A cold, heartless, whim of Grand Moff Tarkin. He wanted a demonstration of the Empire's power, so he killed an entire planet. I now know the truth, so I couldn't work for them anymore.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_b2b7f750"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d24fb27");
                removeObjVar(player, "conversation.coa3wvacca.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Right now we have some stores of Alderaanian Flora, but my home planet was the last place it grew. Once it runs out Dead Eye will be useless.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_11d4164"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11b13f7a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2b7f750");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, you're the one who is helping us fight the Empire for Dead Eye.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_bbc96393"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ba517076");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96405da8");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, you're the one who is helping us fight the Empire for Dead Eye.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_b2b7f750"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d24fb27");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bbc96393");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Right now we have some stores of Alderaanian Flora, but my home planet was the last place it grew. Once it runs out Dead Eye will be useless.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_bbc96393"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ba517076");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96405da8");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If we can find an alternative flora resource, we can try to make another version. We have a number of research stations scattered about a number of planets testing various samples.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_96405da8"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6d583b5");
                removeObjVar(player, "conversation.coa3wvacca.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'After reading the message from Princess Leia I realized that I had blinded myself to the evils of the Empire. After further communications I had my eyes opened to the real reason Alderaan was destroyed.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_96405da8"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6d583b5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11d4164");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'After reading the message from Princess Leia I realized that I had blinded myself to the evils of the Empire. After further communications I had my eyes opened to the real reason Alderaan was destroyed.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_11d4164"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11b13f7a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3wvacca_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2b7f750");
                    }
                    setObjVar(player, "conversation.coa3wvacca.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3wvacca.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It wasn't because of Rebel activity. It was a whim. A cold, heartless, whim of Grand Moff Tarkin. He wanted a demonstration of the Empire's power, so he killed an entire planet. I now know the truth, so I couldn't work for them anymore.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_b2b7f750"))
        {
            if (coa3wvacca_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d24fb27");
                removeObjVar(player, "conversation.coa3wvacca.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Right now we have some stores of Alderaanian Flora, but my home planet was the last place it grew. Once it runs out Dead Eye will be useless.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.coa3wvacca.branchId");
        return SCRIPT_CONTINUE;
    }
}
