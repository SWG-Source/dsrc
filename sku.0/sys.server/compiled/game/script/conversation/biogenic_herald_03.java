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

public class biogenic_herald_03 extends script.base_script
{
    public biogenic_herald_03()
    {
    }
    public static String c_stringFile = "conversation/biogenic_herald_03";
    public boolean biogenic_herald_03_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean biogenic_herald_03_condition_not_rebel(obj_id player, obj_id npc) throws InterruptedException
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
    public void biogenic_herald_03_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void biogenic_herald_03_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.biogenic_herald_03");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "npc.conversation.biogenic_herald_03");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (biogenic_herald_03_condition_not_rebel(player, self))
        {
            biogenic_herald_03_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_ededde72");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (biogenic_herald_03_condition__defaultCondition(player, self))
        {
            biogenic_herald_03_action_face_to(player, self);
            string_id message = new string_id(c_stringFile, "s_569d6129");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (biogenic_herald_03_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c2f063ae");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e35ee05f");
                }
                setObjVar(player, "conversation.biogenic_herald_03.branchId", 2);
                npcStartConversation(player, self, "biogenic_herald_03", message, responses);
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
        if (!conversationId.equals("biogenic_herald_03"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.biogenic_herald_03.branchId");
        if (branchId == 2 && response.equals("s_c2f063ae"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7fd67d96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9e41444d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b3ba542c");
                    }
                    setObjVar(player, "conversation.biogenic_herald_03.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. You are sympathetic to the Rebellion are you not? I have information that may be of interest to you if you are willing to hear it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_e35ee05f"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8ae9c189");
                removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello. You are sympathetic to the Rebellion are you not? I have information that may be of interest to you if you are willing to hear it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_9e41444d"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1711fd92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b10b66c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f8365ece");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1b751405");
                    }
                    setObjVar(player, "conversation.biogenic_herald_03.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, a couple of Imperial types were in here not long ago, and I overheard their conversation. It sounded like they were talking about something going down on Yavin IV.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_b3ba542c"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8ae9c189");
                removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, a couple of Imperial types were in here not long ago, and I overheard their conversation. It sounded like they were talking about something going down on Yavin IV.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_2b10b66c"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d6d8b3ab");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f8365ece");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1b751405");
                    }
                    setObjVar(player, "conversation.biogenic_herald_03.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I didn't catch everything that they were saying, but I did hear something about an Imperial agent overseeing a bio-genetics research facility. And something about... Geo, uh... Geonosians, yea. That's it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_f8365ece"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2991cadd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b10b66c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1b751405");
                    }
                    setObjVar(player, "conversation.biogenic_herald_03.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I didn't catch everything that they were saying, but I did hear something about an Imperial agent overseeing a bio-genetics research facility. And something about... Geo, uh... Geonosians, yea. That's it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_1b751405"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a5ee5a5b");
                removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I didn't catch everything that they were saying, but I did hear something about an Imperial agent overseeing a bio-genetics research facility. And something about... Geo, uh... Geonosians, yea. That's it.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_f8365ece"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2991cadd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1b751405");
                    }
                    setObjVar(player, "conversation.biogenic_herald_03.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, they didn't. In fact, I don't even think they know where it is exactly. Sounds like it's a big secret, but they said something about asking the locals. Something about a Mining Outpost, too.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_1b751405"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5ea784e0");
                removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, they didn't. In fact, I don't even think they know where it is exactly. Sounds like it's a big secret, but they said something about asking the locals. Something about a Mining Outpost, too.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_1b751405"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5ea784e0");
                removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm really not sure. It got a little loud in here and I couldn't make out what they were saying, but one of them looked really mad. If you ask me something big is about to go down.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_2b10b66c"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3d816e72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (biogenic_herald_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1b751405");
                    }
                    setObjVar(player, "conversation.biogenic_herald_03.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm really not sure. It got a little loud in here and I couldn't make out what they were saying, but one of them looked really mad. If you ask me something big is about to go down.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_1b751405"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a5ee5a5b");
                removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm really not sure. It got a little loud in here and I couldn't make out what they were saying, but one of them looked really mad. If you ask me something big is about to go down.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_1b751405"))
        {
            if (biogenic_herald_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5ea784e0");
                removeObjVar(player, "conversation.biogenic_herald_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No, they didn't. In fact, I don't even think they know where it is exactly, except for on Yavin IV. Sounds like it's a big secret, but they said something about asking the locals. Something about a Mining Outpost, too.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.biogenic_herald_03.branchId");
        return SCRIPT_CONTINUE;
    }
}
