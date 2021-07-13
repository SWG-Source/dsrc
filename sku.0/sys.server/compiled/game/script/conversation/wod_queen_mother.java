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
import script.library.conversation;
import script.library.groundquests;
import script.library.trial;
import script.library.utils;

public class wod_queen_mother extends script.base_script
{
    public wod_queen_mother()
    {
    }
    public static String c_stringFile = "conversation/wod_queen_mother";
    public boolean wod_queen_mother_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean wod_queen_mother_condition_spiderBossCurrentlyBusy(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] objects = trial.getObjectsInDungeonWithObjVar(npc, "event_wave_controller");
        if (objects.length < 1)
        {
            return true;
        }
        int wave = utils.getIntScriptVar(objects[0], "waveEventCurrentWave");
        return wave > 0;
    }
    public boolean wod_queen_mother_condition_hasQuestAndGroupSpiderBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        if (wod_queen_mother_condition_playerHasNoGroupSpiderBoss(player, npc))
        {
            return false;
        }
        if (wod_queen_mother_condition_spiderBossCurrentlyBusy(player, npc))
        {
            return false;
        }
        return groundquests.isTaskActive(player, "wod_queen_mother_boss_fight", "challengeMatron") || groundquests.isTaskActive(player, "wod_queen_mother_boss_fight_ns", "challengeMatron");
    }
    public boolean wod_queen_mother_condition_onReturnSpiderBossFight(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "wod_queen_mother_boss_fight", "challengeMatron") || groundquests.isTaskActive(player, "wod_queen_mother_boss_fight_ns", "challengeMatron");
    }
    public boolean wod_queen_mother_condition_onReturnEverybodyHatesSpiderclan(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "wod_themepark_ehs_1", "findSecret") || groundquests.isTaskActive(player, "wod_themepark_ns_ehs_1", "findSecret");
    }
    public boolean wod_queen_mother_condition_playerHasNoGroupSpiderBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id groupid = getGroupObject(player);
        if (isValidId(groupid))
        {
            return false;
        }
        return true;
    }
    public void wod_queen_mother_action_sendReturnedSignalSpiderBossFight(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] objects = trial.getObjectsInDungeonWithObjVar(npc, "event_wave_controller");
        if (objects.length < 1)
        {
            return;
        }
        faceTo(npc, player);
        groundquests.sendSignal(player, "challengedMatron");
        obj_id myGroup = getGroupObject(player);
        if (isValidId(myGroup))
        {
            obj_id[] members = getGroupMemberIds(myGroup);
            for (int i = 0; i < members.length; i++)
            {
                if (!isIdValid(members[i]) || !exists(members[i]))
                {
                    continue;
                }
                if (getDistance(members[i], npc) > 200)
                {
                    continue;
                }
                if (groundquests.isTaskActive(members[i], "wod_queen_mother_boss_fight", "challengeMatron") || groundquests.isTaskActive(members[i], "wod_queen_mother_boss_fight_ns", "challengeMatron"))
                {
                    groundquests.sendSignal(members[i], "challengedMatron");
                }
            }
        }
        dictionary dict = new dictionary();
        dict.put("player", player);
        messageTo(objects[0], "waveEventControllerNPCStart", dict, 0, false);
    }
    public void wod_queen_mother_action_sendReturnedSignalEverybodyHatesSpiderclan(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "foundSecret");
    }
    public int wod_queen_mother_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            wod_queen_mother_action_sendReturnedSignalEverybodyHatesSpiderclan(player, npc);
            if (wod_queen_mother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.wod_queen_mother.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_queen_mother_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (wod_queen_mother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_queen_mother_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                    }
                    utils.setScriptVar(player, "conversation.wod_queen_mother.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_queen_mother.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_queen_mother_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (wod_queen_mother_condition_playerHasNoGroupSpiderBoss(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_queen_mother_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    utils.setScriptVar(player, "conversation.wod_queen_mother.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_queen_mother.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (wod_queen_mother_condition_spiderBossCurrentlyBusy(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.wod_queen_mother.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (wod_queen_mother_condition_hasQuestAndGroupSpiderBoss(player, npc))
            {
                wod_queen_mother_action_sendReturnedSignalSpiderBossFight(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.wod_queen_mother.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_queen_mother_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (wod_queen_mother_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_23");
                utils.removeScriptVar(player, "conversation.wod_queen_mother.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.wod_queen_mother");
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
        detachScript(self, "conversation.wod_queen_mother");
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
        if (wod_queen_mother_condition_onReturnEverybodyHatesSpiderclan(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wod_queen_mother_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                }
                utils.setScriptVar(player, "conversation.wod_queen_mother.branchId", 1);
                npcStartConversation(player, npc, "wod_queen_mother", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wod_queen_mother_condition_onReturnSpiderBossFight(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wod_queen_mother_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                utils.setScriptVar(player, "conversation.wod_queen_mother.branchId", 3);
                npcStartConversation(player, npc, "wod_queen_mother", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wod_queen_mother_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_31");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("wod_queen_mother"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.wod_queen_mother.branchId");
        if (branchId == 1 && wod_queen_mother_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && wod_queen_mother_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && wod_queen_mother_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && wod_queen_mother_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.wod_queen_mother.branchId");
        return SCRIPT_CONTINUE;
    }
}
