package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.*;

public class wod_kyrisa extends script.base_script
{
    public wod_kyrisa()
    {
    }
    public static final string_id BOSS_BEGIN = new string_id("theme_park/outbreak/outbreak", "boss_begin");
    public static final string_id BOSS_LEAVING = new string_id("theme_park/outbreak/outbreak", "boss_leaving");
    public static String c_stringFile = "conversation/wod_kyrisa";
    public boolean wod_kyrisa_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean wod_kyrisa_condition_onReturnKyrisaBossFight(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "wod_kyrisa_boss_fight", "challengeKyrisa") || groundquests.isTaskActive(player, "wod_kyrisa_boss_fight_ns", "challengeKyrisa");
    }
    public boolean wod_kyrisa_condition_onReturnWholeTruthII(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "wod_themepark_ns_whole_truth_02", "findCause") || groundquests.isTaskActive(player, "wod_themepark_sm_whole_truth_02", "findCause");
    }
    public boolean wod_kyrisa_condition_bossCurrentlyBusy(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getBooleanScriptVar(npc, "wod.busyNow") || !utils.getBooleanScriptVar(utils.getObjIdScriptVar(npc, "wod.cage_controller"), "wod.ready");
    }
    public boolean wod_kyrisa_condition_playerHasNoGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        if(isGod(player))
        {
            sendSystemMessageTestingOnly(player, "God Mode: You are bypassing the group requirement because you are in God Mode.");
            return false;
        }
        final obj_id groupId = getGroupObject(player);
        if (isValidId(groupId))
        {
            // The player must be grouped, there must be at least 2 players in the group,
            // the 2 minimum players must be within 30m, and all players within 30m must be active
            final obj_id[] members = getGroupMemberIds(groupId);
            int playerCount = 0;
            for (obj_id member : members)
            {
                if(isPlayer(member) && getDistance(player, npc) < 30f)
                {
                    if(!isPlayerActive(member))
                    {
                        sendSystemMessageTestingOnly(player, "All members of your group present to participate must be active and at the keyboard.");
                        return true;
                    }
                    playerCount++;
                }
            }
            if(playerCount < 2)
            {
                sendSystemMessageTestingOnly(player, "You must have at least 2 players in your group who are in the vicinity to participate.");
                return true;
            }
            else
            {
                return false;
            }
        }
        sendSystemMessageTestingOnly(player, "You must have a group of at least 2 players to participate.");
        return true;
    }
    public boolean wod_kyrisa_condition_hasQuestAndGroup(obj_id player, obj_id npc) throws InterruptedException
    {
        if (wod_kyrisa_condition_playerHasNoGroup(player, npc))
        {
            return false;
        }
        if (wod_kyrisa_condition_bossCurrentlyBusy(player, npc))
        {
            return false;
        }
        return groundquests.isTaskActive(player, "wod_kyrisa_boss_fight", "challengeKyrisa") || groundquests.isTaskActive(player, "wod_kyrisa_boss_fight_ns", "challengeKyrisa");
    }
    public void wod_kyrisa_action_sendReturnedSignalWholeTruthII(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "foundCause");
    }
    public void wod_kyrisa_action_sendReturnedSignalKyrisaChallenged(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary d = new dictionary();
        if(isGod(player) && !isIdValid(getGroupObject(player)))
        {
            d.put("groupId", obj_id.NULL_ID);
            d.put("groupMembers", new obj_id[]{player});
            utils.setScriptVar(npc, "wod.groupInProgress", obj_id.NULL_ID);
            utils.setScriptVar(npc, "wod.groupInProgressMembers", new obj_id[]{player});
            if (groundquests.isTaskActive(player, "wod_kyrisa_boss_fight", "challengeKyrisa") || groundquests.isTaskActive(player, "wod_kyrisa_boss_fight_ns", "challengeKyrisa"))
            {
                groundquests.sendSignal(player, "challengedKyrisa");
            }
            sendSystemMessage(player, BOSS_BEGIN);
        }
        else
        {
            final obj_id groupId = getGroupObject(player);
            final obj_id[] groupMembers = getGroupMemberIds(groupId);
            d.put("groupId", groupId);
            d.put("groupMembers", groupMembers);
            utils.setScriptVar(npc, "wod.groupInProgress", groupId);
            utils.setScriptVar(npc, "wod.groupInProgressMembers", groupMembers);
            for(obj_id member : groupMembers)
            {
                if(getDistance(npc, member) < 30f)
                {
                    if (groundquests.isTaskActive(member, "wod_kyrisa_boss_fight", "challengeKyrisa") || groundquests.isTaskActive(member, "wod_kyrisa_boss_fight_ns", "challengeKyrisa"))
                    {
                        groundquests.sendSignal(member, "challengedKyrisa");
                    }
                }
                sendSystemMessage(member, BOSS_BEGIN);
            }
        }
        final obj_id rancor = utils.getObjIdScriptVar(npc, "wod.corrupted_rancor_mutation");
        faceTo(npc, rancor);
        utils.setScriptVar(npc, "wod.busyNow", true);
        doAnimationAction(npc, anims.PLAYER_FORCE_LIGHTNING);
        doCombatResults("force_weaken_1_particle_level_1_medium", combat.makeDummyAttackerResults(npc, getDefaultWeapon(npc)), combat.makeDummyDefenderResults(rancor));
        messageTo(rancor, "startBossAttack", d, 1f, false);
        messageTo(utils.getObjIdScriptVar(npc, "wod.cage_controller"), "destroyCage", null, 2f, false);
    }
    public int wod_kyrisa_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7"))
        {
            if (wod_kyrisa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_kyrisa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    utils.setScriptVar(player, "conversation.wod_kyrisa.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_kyrisa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int wod_kyrisa_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            if (wod_kyrisa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_kyrisa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_15");
                    }
                    utils.setScriptVar(player, "conversation.wod_kyrisa.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_kyrisa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int wod_kyrisa_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_15"))
        {
            if (wod_kyrisa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_kyrisa_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.wod_kyrisa.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_kyrisa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int wod_kyrisa_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            wod_kyrisa_action_sendReturnedSignalWholeTruthII(player, npc);
            if (wod_kyrisa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_22");
                utils.removeScriptVar(player, "conversation.wod_kyrisa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int wod_kyrisa_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (wod_kyrisa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_kyrisa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                    }
                    utils.setScriptVar(player, "conversation.wod_kyrisa.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_kyrisa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int wod_kyrisa_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (wod_kyrisa_condition_playerHasNoGroup(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.wod_kyrisa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (wod_kyrisa_condition_bossCurrentlyBusy(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.wod_kyrisa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (wod_kyrisa_condition_hasQuestAndGroup(player, npc))
            {
                wod_kyrisa_action_sendReturnedSignalKyrisaChallenged(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.wod_kyrisa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.wod_kyrisa");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
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
        detachScript(self, "conversation.wod_kyrisa");
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
        if (wod_kyrisa_condition_onReturnWholeTruthII(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wod_kyrisa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7");
                }
                utils.setScriptVar(player, "conversation.wod_kyrisa.branchId", 1);
                npcStartConversation(player, npc, "wod_kyrisa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wod_kyrisa_condition_onReturnKyrisaBossFight(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_24");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wod_kyrisa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.wod_kyrisa.branchId", 6);
                npcStartConversation(player, npc, "wod_kyrisa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wod_kyrisa_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_35");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("wod_kyrisa"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.wod_kyrisa.branchId");
        if (branchId == 1 && wod_kyrisa_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && wod_kyrisa_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && wod_kyrisa_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && wod_kyrisa_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && wod_kyrisa_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && wod_kyrisa_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.wod_kyrisa.branchId");
        return SCRIPT_CONTINUE;
    }

    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if(isGod(objSpeaker)) {
            if (strText.equalsIgnoreCase("start"))
            {
                wod_kyrisa_action_sendReturnedSignalKyrisaChallenged(objSpeaker, self);
            }
        }
        return SCRIPT_CONTINUE;
    }

    public int endFightNoDefeat(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("wasDefeat", false);
        messageTo(utils.getObjIdScriptVar(self, "wod.cage_controller"), "resetCage", d, 0f, false);
        string_id[] taunts = new string_id[] {
                new string_id("npc_reaction/fancy", "gloat_9"),
                new string_id("npc_reaction/fancy", "gloat_10"),
                new string_id("npc_reaction/fancy", "gloat_14"),
                new string_id("npc_reaction/fancy", "gloat_15")
        };
        prose_package pp = new prose_package();
        pp.stringId = taunts[rand(0, taunts.length-1)];
        final obj_id[] members = utils.getObjIdArrayScriptVar(self, "wod.groupInProgressMembers");
        commPlayers(self, members, pp);
        for(obj_id player : members)
        {
            sendSystemMessage(player, BOSS_LEAVING);
            if(groundquests.isTaskActive(player, "wod_kyrisa_boss_fight", "defeatRancor"))
            {
                groundquests.sendSignal(player, "playerFailedBoss");
            }
            if(groundquests.isTaskActive(player, "wod_kyrisa_boss_fight_ns", "defeatRancor"))
            {
                groundquests.sendSignal(player, "playerFailedBoss");
            }
        }
        utils.removeScriptVar(self, "wod.groupInProgress");
        utils.removeScriptVar(self, "wod.groupInProgressMembers");
        utils.setScriptVar(self, "wod.busyNow", false);
        return SCRIPT_CONTINUE;
    }

    public int endFightWithDefeat(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("wasDefeat", true);
        utils.removeScriptVar(self, "wod.groupInProgress");
        utils.removeScriptVar(self, "wod.groupInProgressMembers");
        utils.setScriptVar(self, "wod.busyNow", false);
        messageTo(utils.getObjIdScriptVar(self, "wod.cage_controller"), "resetCage", d, 0f, false);
        return SCRIPT_CONTINUE;
    }

}
