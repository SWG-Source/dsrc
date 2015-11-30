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

public class death_watch_rebel_herald extends script.base_script
{
    public death_watch_rebel_herald()
    {
    }
    public static String c_stringFile = "conversation/death_watch_rebel_herald";
    public boolean death_watch_rebel_herald_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean death_watch_rebel_herald_condition_NonRebelCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null || !playerFaction.equals("Rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_rebel_herald_condition_RebelCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction.equals("Rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_rebel_herald_condition_ReturnCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch_herald.rebelquest"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_rebel_herald_condition_RescueObjvar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch_herald.rebelquest"))
        {
            if (hasObjVar(player, "death_watch_herald.rebelrescue"))
            {
                return true;
            }
            return false;
        }
        return false;
    }
    public boolean death_watch_rebel_herald_condition_RewardCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch_herald.rebelfinish"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_rebel_herald_condition_Questcomplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch_herald.rebelfinish"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_rebel_herald_condition_dungeonInactive(obj_id player, obj_id npc) throws InterruptedException
    {
        String isDungeonActive = getConfigSetting("Dungeon", "Death_Watch");
        if (isDungeonActive == null || isDungeonActive.equals("false") || isDungeonActive.equals("0"))
        {
            return true;
        }
        return false;
    }
    public void death_watch_rebel_herald_action_RescueandReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch_herald.rebelfinish"))
        {
            factions.addFactionStanding(player, "Rebel", 500);
            setObjVar(player, "death_watch_herald.rebelfinish", true);
        }
        return;
    }
    public void death_watch_rebel_herald_action_QuestRemove(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "death_watch_herald.rebelquest");
        removeObjVar(player, "death_watch_herald.rebelrescue");
    }
    public void death_watch_rebel_herald_action_QuestAccept(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "death_watch_herald.rebelquest", true);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.death_watch_rebel_herald");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setName(self, "Lutin Nightstalker");
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
        detachScript(self, "npc.conversation.death_watch_rebel_herald");
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
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (death_watch_rebel_herald_condition_dungeonInactive(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_91db32fc");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_rebel_herald_condition_Questcomplete(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_65aa1cd5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c1c188ab");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28f5f7c5");
                }
                setObjVar(player, "conversation.death_watch_rebel_herald.branchId", 2);
                npcStartConversation(player, self, "death_watch_rebel_herald", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_rebel_herald_condition_ReturnCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8361bfd1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_rebel_herald_condition_RescueObjvar(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9b06da0");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8d495981");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dc839e59");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f70821a3");
                }
                setObjVar(player, "conversation.death_watch_rebel_herald.branchId", 5);
                npcStartConversation(player, self, "death_watch_rebel_herald", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_rebel_herald_condition_NonRebelCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_95369855");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_rebel_herald_condition_RebelCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_bfe5e691");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a0822589");
                }
                setObjVar(player, "conversation.death_watch_rebel_herald.branchId", 12);
                npcStartConversation(player, self, "death_watch_rebel_herald", message, responses);
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
        if (!conversationId.equals("death_watch_rebel_herald"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.death_watch_rebel_herald.branchId");
        if (branchId == 2 && response.equals("s_c1c188ab"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2d4bb338");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank you for your help earlier. We are very grateful.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_28f5f7c5"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7089401b");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Thank you for your help earlier. We are very grateful.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_9b06da0"))
        {
            if (death_watch_rebel_herald_condition_RewardCheck(player, self))
            {
                death_watch_rebel_herald_action_RescueandReward(player, self);
                string_id message = new string_id(c_stringFile, "s_37b46abd");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!death_watch_rebel_herald_condition_RewardCheck(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f0d30da9");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You have returned! Were you successful in rescuing our scientist?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_8d495981"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2d06d9f7");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You have returned! Were you successful in rescuing our scientist?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_dc839e59"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e99ef1de");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You have returned! Were you successful in rescuing our scientist?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_f70821a3"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                death_watch_rebel_herald_action_QuestRemove(player, self);
                string_id message = new string_id(c_stringFile, "s_88a6fa7d");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You have returned! Were you successful in rescuing our scientist?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_a0822589"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_940cb841");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_rebel_herald_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62104343");
                    }
                    setObjVar(player, "conversation.death_watch_rebel_herald.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Finally, you've come! We've been waiting for you!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_62104343"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_aa852a2c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_rebel_herald_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_rebel_herald_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (death_watch_rebel_herald_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6f2c6f44");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_938e28a4");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_823eed0c");
                    }
                    setObjVar(player, "conversation.death_watch_rebel_herald.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Haven't you heard about the kidnapping? You are of the Alliance, aren't you? Are you here to help us?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_6f2c6f44"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_37890704");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_rebel_herald_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_rebel_herald_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c3cdba4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4f240e90");
                    }
                    setObjVar(player, "conversation.death_watch_rebel_herald.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'One of our neutral contacts has been kidnapped by the Death Watch operatives on Endor. We need someone to rescue him. Naturally, we can only trust a fellow member of the Alliance. You came at the perfect time and would be an excellent candidiate to help us in our cause. Will you help us? ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_938e28a4"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_afdd35a1");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'One of our neutral contacts has been kidnapped by the Death Watch operatives on Endor. We need someone to rescue him. Naturally, we can only trust a fellow member of the Alliance. You came at the perfect time and would be an excellent candidiate to help us in our cause. Will you help us? ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_823eed0c"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                death_watch_rebel_herald_action_QuestAccept(player, self);
                string_id message = new string_id(c_stringFile, "s_f4474b2c");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'One of our neutral contacts has been kidnapped by the Death Watch operatives on Endor. We need someone to rescue him. Naturally, we can only trust a fellow member of the Alliance. You came at the perfect time and would be an excellent candidiate to help us in our cause. Will you help us? ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_5c3cdba4"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                death_watch_rebel_herald_action_QuestAccept(player, self);
                string_id message = new string_id(c_stringFile, "s_f4474b2c");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. A reasonable request, indeed. The Death Watch Operatives are located on Endor in the northwestern part of the planet. Our scientist is somewhere within their hideout. I suspect he'll be heavily guarded. He needs to be rescued before he is executed. We need someone to help us and we're looking to you as our only hope.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_4f240e90"))
        {
            if (death_watch_rebel_herald_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_afdd35a1");
                removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Very well. A reasonable request, indeed. The Death Watch Operatives are located on Endor in the northwestern part of the planet. Our scientist is somewhere within their hideout. I suspect he'll be heavily guarded. He needs to be rescued before he is executed. We need someone to help us and we're looking to you as our only hope.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.death_watch_rebel_herald.branchId");
        return SCRIPT_CONTINUE;
    }
}
