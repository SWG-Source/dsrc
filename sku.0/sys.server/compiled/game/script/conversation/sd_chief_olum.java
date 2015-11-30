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
import script.library.trial;
import script.library.utils;

public class sd_chief_olum extends script.base_script
{
    public sd_chief_olum()
    {
    }
    public static String c_stringFile = "conversation/sd_chief_olum";
    public boolean sd_chief_olum_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean sd_chief_olum_condition_hasTalked(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "talked");
    }
    public void sd_chief_olum_action_setFaceToPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(npc));
        prose_package pp = new prose_package();
        pp.target.set(player);
        pp.stringId = new string_id("spam", "olum_greeting");
        utils.sendSystemMessageProse(players, pp);
        faceTo(npc, player);
    }
    public void sd_chief_olum_action_kickoffSdEvent(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary dict = trial.getSessionDict(trial.getTop(npc));
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", "olum_path");
        messageTo(trial.getTop(npc), "triggerFired", dict, 0.0f, false);
        setObjVar(npc, "talked", 1);
        detachScript(npc, "conversation.sd_chief_olum");
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(npc));
        prose_package pp = new prose_package();
        pp.target.set(player);
        pp.stringId = new string_id("spam", "olum_follow_me");
        utils.sendSystemMessageProse(players, pp);
    }
    public void sd_chief_olum_action_playerPosition(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(npc));
        prose_package pp = new prose_package();
        pp.target.set(player);
        pp.stringId = new string_id("spam", "olum_player_position");
        utils.sendSystemMessageProse(players, pp);
    }
    public void sd_chief_olum_action_olumQuiteRight(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(npc));
        prose_package pp = new prose_package();
        pp.target.set(player);
        pp.stringId = new string_id("spam", "olum_quite_right");
        utils.sendSystemMessageProse(players, pp);
    }
    public void sd_chief_olum_action_playerTurnAgainst(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(npc));
        prose_package pp = new prose_package();
        pp.target.set(player);
        pp.stringId = new string_id("spam", "olum_player_turn_against");
        utils.sendSystemMessageProse(players, pp);
    }
    public void sd_chief_olum_action_olumNotProblem(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(npc));
        prose_package pp = new prose_package();
        pp.target.set(player);
        pp.stringId = new string_id("spam", "olum_not_problem");
        utils.sendSystemMessageProse(players, pp);
    }
    public void sd_chief_olum_action_playerLeadWay(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(npc));
        prose_package pp = new prose_package();
        pp.target.set(player);
        pp.stringId = new string_id("spam", "olum_player_lead_way");
        utils.sendSystemMessageProse(players, pp);
    }
    public int sd_chief_olum_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            sd_chief_olum_action_playerPosition(player, npc);
            if (sd_chief_olum_condition__defaultCondition(player, npc))
            {
                sd_chief_olum_action_olumQuiteRight(player, npc);
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sd_chief_olum_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.sd_chief_olum.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.sd_chief_olum.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int sd_chief_olum_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            sd_chief_olum_action_playerTurnAgainst(player, npc);
            if (sd_chief_olum_condition__defaultCondition(player, npc))
            {
                sd_chief_olum_action_olumNotProblem(player, npc);
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sd_chief_olum_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.sd_chief_olum.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.sd_chief_olum.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int sd_chief_olum_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            sd_chief_olum_action_playerLeadWay(player, npc);
            if (sd_chief_olum_condition__defaultCondition(player, npc))
            {
                sd_chief_olum_action_kickoffSdEvent(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.sd_chief_olum.branchId");
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
            detachScript(self, "conversation.sd_chief_olum");
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
        detachScript(self, "conversation.sd_chief_olum");
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
        if (sd_chief_olum_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            sd_chief_olum_action_setFaceToPlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sd_chief_olum_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                utils.setScriptVar(player, "conversation.sd_chief_olum.branchId", 1);
                npcStartConversation(player, npc, "sd_chief_olum", message, responses);
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
        if (!conversationId.equals("sd_chief_olum"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.sd_chief_olum.branchId");
        if (branchId == 1 && sd_chief_olum_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && sd_chief_olum_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && sd_chief_olum_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.sd_chief_olum.branchId");
        return SCRIPT_CONTINUE;
    }
}
