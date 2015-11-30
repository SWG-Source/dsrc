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
import script.library.features;
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class greeter_dantooine_brother_rebel extends script.base_script
{
    public greeter_dantooine_brother_rebel()
    {
    }
    public static String c_stringFile = "conversation/greeter_dantooine_brother_rebel";
    public boolean greeter_dantooine_brother_rebel_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean greeter_dantooine_brother_rebel_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metNewbiePilot"));
    }
    public boolean greeter_dantooine_brother_rebel_condition_spokeWithFern(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(player, "spokeWithFernYarrow");
    }
    public boolean greeter_dantooine_brother_rebel_condition_isAnImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isImperialPilot(player);
    }
    public boolean greeter_dantooine_brother_rebel_condition_isARebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isRebelPilot(player);
    }
    public boolean greeter_dantooine_brother_rebel_condition_isPrivateerPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isNeutralPilot(player);
    }
    public boolean greeter_dantooine_brother_rebel_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean greeter_dantooine_brother_rebel_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasShip(player));
    }
    public void greeter_dantooine_brother_rebel_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metNewbiePilot", true);
    }
    public void greeter_dantooine_brother_rebel_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery", "tatooine_newbie_1");
        space_quest.grantNewbieShip(player, "rebel");
    }
    public int greeter_dantooine_brother_rebel_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91cb4419"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_6bbf120e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (greeter_dantooine_brother_rebel_condition_spokeWithFern(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e52fbe2d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ad02b6b3");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85e41490");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69796047");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_dantooine_brother_rebel_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e52fbe2d"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_b20f640e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2356ea5");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ad02b6b3"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_af88e52c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2356ea5");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85e41490"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cedb568");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93511893");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69796047"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shiver");
                string_id message = new string_id(c_stringFile, "s_19aad16e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2356ea5");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_dantooine_brother_rebel_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f2356ea5"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_a4b89de2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93511893");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_dantooine_brother_rebel_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f2356ea5"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_a4b89de2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93511893");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_dantooine_brother_rebel_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_93511893"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_9b136077");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1b178396");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_dantooine_brother_rebel_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1b178396"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d434d6d3");
                utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_dantooine_brother_rebel_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_93511893"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_9b136077");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1b178396");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int greeter_dantooine_brother_rebel_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f2356ea5"))
        {
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_a4b89de2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93511893");
                    }
                    utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.greeter_dantooine_brother_rebel");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        detachScript(self, "conversation.greeter_dantooine_brother_rebel");
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
        if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "stamp_feet");
            string_id message = new string_id(c_stringFile, "s_7ea269a5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (greeter_dantooine_brother_rebel_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_91cb4419");
                }
                utils.setScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId", 1);
                npcStartConversation(player, npc, "greeter_dantooine_brother_rebel", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("greeter_dantooine_brother_rebel"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
        if (branchId == 1 && greeter_dantooine_brother_rebel_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && greeter_dantooine_brother_rebel_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && greeter_dantooine_brother_rebel_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && greeter_dantooine_brother_rebel_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && greeter_dantooine_brother_rebel_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && greeter_dantooine_brother_rebel_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && greeter_dantooine_brother_rebel_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && greeter_dantooine_brother_rebel_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.greeter_dantooine_brother_rebel.branchId");
        return SCRIPT_CONTINUE;
    }
}
