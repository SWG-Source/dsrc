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

public class victor_questp_slums extends script.base_script
{
    public victor_questp_slums()
    {
    }
    public static String c_stringFile = "conversation/victor_questp_slums";
    public boolean victor_questp_slums_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean victor_questp_slums_condition_CampObjCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.camp"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.camp");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (!hasObjVar(player, "bestine.victor_slums_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean victor_questp_slums_condition_alreadyHasEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questp_testimony.iff");
    }
    public boolean victor_questp_slums_condition_hasNoRoomObjvar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.camp"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.camp");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (hasObjVar(player, "bestine.victor_slums_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean victor_questp_slums_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public boolean victor_questp_slums_condition_noIventorySpace(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasNoInvRoom = false;
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space < 1)
            {
                hasNoInvRoom = true;
            }
        }
        return hasNoInvRoom;
    }
    public void victor_questp_slums_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void victor_questp_slums_action_Noroom7(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.noroom7", true);
    }
    public void victor_questp_slums_action_giveTestimony(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.victor_slums_noroom"))
        {
            removeObjVar(player, "bestine.victor_slums_noroom");
        }
        String TESTIMONY = "object/tangible/loot/quest/victor_questp_testimony.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(TESTIMONY, playerInv, "");
                if (isIdValid(item))
                {
                    return;
                }
            }
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.victor_questp_slums");
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
        detachScript(self, "npc.conversation.victor_questp_slums");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (victor_questp_slums_condition_nonoffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f8cdce4");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_questp_slums_condition_alreadyHasEvidence(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_4117e563");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_questp_slums_condition_hasNoRoomObjvar(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_b6d2944c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_questp_slums_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_questp_slums_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ee6997ba");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76cc19da");
                }
                setObjVar(player, "conversation.victor_questp_slums.branchId", 3);
                npcStartConversation(player, self, "victor_questp_slums", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_questp_slums_condition_CampObjCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f25283c7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_questp_slums_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_questp_slums_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8611417a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aa91e763");
                }
                setObjVar(player, "conversation.victor_questp_slums.branchId", 7);
                npcStartConversation(player, self, "victor_questp_slums", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_questp_slums_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_dcb207ac");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("victor_questp_slums"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.victor_questp_slums.branchId");
        if (branchId == 3 && response.equals("s_ee6997ba"))
        {
            if (!victor_questp_slums_condition_noIventorySpace(player, self))
            {
                victor_questp_slums_action_giveTestimony(player, self);
                string_id message = new string_id(c_stringFile, "s_b8dac377");
                removeObjVar(player, "conversation.victor_questp_slums.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (victor_questp_slums_condition_noIventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_585b9214");
                removeObjVar(player, "conversation.victor_questp_slums.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm glad you've returned. Did you want your evidence now? I hope you have enough room.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_76cc19da"))
        {
            if (victor_questp_slums_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6ec1e03e");
                removeObjVar(player, "conversation.victor_questp_slums.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm glad you've returned. Did you want your evidence now? I hope you have enough room.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_8611417a"))
        {
            if (victor_questp_slums_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_222d159b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_questp_slums_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.victor_questp_slums.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.victor_questp_slums.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I knew you'd come. The story should be told to everyone. It has a good moral to it, I think.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_aa91e763"))
        {
            if (victor_questp_slums_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5a5e0913");
                removeObjVar(player, "conversation.victor_questp_slums.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I knew you'd come. The story should be told to everyone. It has a good moral to it, I think.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_540ac7e9"))
        {
            if (victor_questp_slums_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_41e66644");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_questp_slums_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_questp_slums_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5f19466");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9ac4d51");
                    }
                    setObjVar(player, "conversation.victor_questp_slums.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.victor_questp_slums.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Listen closely. The Sand People have been attacking the outskirts of the city. For some reason, the Empire has hidden this fact from those who do not live in the slums... not to panic everyone else, I guess. It's hard to live here. One particular night, a couple of months back, the Tuskens came. They killed several of us here. They took my son. I--I just don't want to think about it... about what could have happened that night.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_d5f19466"))
        {
            if (victor_questp_slums_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_41330c9c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_questp_slums_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                    }
                    setObjVar(player, "conversation.victor_questp_slums.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.victor_questp_slums.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm just a poor woman. I'm not significant in this life, or to this city. But Victor Visalis heard of my misfortune and took pity on me. He took his own soldiers and destroyed the Tuskens. He returned my son to me! Can you believe that? I'm so grateful.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_a9ac4d51"))
        {
            if (victor_questp_slums_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_76f08368");
                removeObjVar(player, "conversation.victor_questp_slums.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm just a poor woman. I'm not significant in this life, or to this city. But Victor Visalis heard of my misfortune and took pity on me. He took his own soldiers and destroyed the Tuskens. He returned my son to me! Can you believe that? I'm so grateful.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_67e6df55"))
        {
            if (!victor_questp_slums_condition_noIventorySpace(player, self))
            {
                victor_questp_slums_action_giveTestimony(player, self);
                string_id message = new string_id(c_stringFile, "s_22977a6d");
                removeObjVar(player, "conversation.victor_questp_slums.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (victor_questp_slums_condition_noIventorySpace(player, self))
            {
                victor_questp_slums_action_Noroom7(player, self);
                string_id message = new string_id(c_stringFile, "s_4f9d0087");
                removeObjVar(player, "conversation.victor_questp_slums.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I will help you as well as I can. What do you need? Oh? I would do anything for Victor. I'd be happy to write my account of the happenings for you. Luckily, I know how to write. Victor funded my education as well. He's done so much for us. Give me a second..' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.victor_questp_slums.branchId");
        return SCRIPT_CONTINUE;
    }
}
