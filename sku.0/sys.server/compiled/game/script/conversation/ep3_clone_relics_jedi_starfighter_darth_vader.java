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
import script.library.groundquests;
import script.library.pclib;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class ep3_clone_relics_jedi_starfighter_darth_vader extends script.base_script
{
    public ep3_clone_relics_jedi_starfighter_darth_vader()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_jedi_starfighter_darth_vader";
    public boolean ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_jedi_starfighter_darth_vader_condition_failedSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "space_battle", "ep3_clone_relics_jedi_starfighter_4") || space_quest.hasAbortedQuest(player, "space_battle", "ep3_clone_relics_jedi_starfighter_4") || space_quest.hasFailedQuest(player, "space_battle", "ep3_clone_relics_jedi_starfighter_5") || space_quest.hasAbortedQuest(player, "space_battle", "ep3_clone_relics_jedi_starfighter_5"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_clone_relics_jedi_starfighter_darth_vader_condition_completedSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "space_battle", "ep3_clone_relics_jedi_starfighter_5") && !space_quest.hasReceivedReward(player, "space_battle", "ep3_clone_relics_jedi_starfighter_5"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_clone_relics_jedi_starfighter_darth_vader_condition_onSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "space_battle", "ep3_clone_relics_jedi_starfighter_4"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_darth_vader_condition_finished(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "space_battle", "ep3_clone_relics_jedi_starfighter_5"));
    }
    public boolean ep3_clone_relics_jedi_starfighter_darth_vader_condition_onQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_clone_relics_jedi_starfighter_4"));
    }
    public void ep3_clone_relics_jedi_starfighter_darth_vader_action_signalTalkedOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToVader1");
    }
    public void ep3_clone_relics_jedi_starfighter_darth_vader_action_grantRewardTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 3000.0f);
        space_quest.giveReward(player, "space_battle", "ep3_clone_relics_jedi_starfighter_5", 0);
        setObjVar(player, "doneWithVader", 1);
        utils.setScriptVar(player, "limitedVaderPass", 1);
    }
    public void ep3_clone_relics_jedi_starfighter_darth_vader_action_performKillPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        attacker_results attackerResults = new attacker_results();
        defender_results[] defenderResults = new defender_results[1];
        attackerResults.id = npc;
        attackerResults.weapon = null;
        defenderResults[0] = new defender_results();
        defenderResults[0].id = player;
        defenderResults[0].endPosture = getPosture(player);
        defenderResults[0].result = COMBAT_RESULT_HIT;
        doCombatResults("force_lightning_1_particle_level_5_medium", attackerResults, defenderResults);
        dictionary outparams = new dictionary();
        outparams.put("player", player);
        outparams.put("npc", npc);
        messageTo(npc, "playerKnockedOut", outparams, 3, false);
    }
    public int playerKnockedOut(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        pclib.killPlayer(player, npc, false);
        messageTo(npc, "playerDies", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int playerDies(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        pclib.coupDeGrace(player, npc, false, false);
        return SCRIPT_CONTINUE;
    }
    public void ep3_clone_relics_jedi_starfighter_darth_vader_action_grantLimitedPass(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "limitedVaderPass", 1);
    }
    public void ep3_clone_relics_jedi_starfighter_darth_vader_action_grantSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_battle", "ep3_clone_relics_jedi_starfighter_4");
    }
    public int ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_716"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "bow5");
                ep3_clone_relics_jedi_starfighter_darth_vader_action_grantRewardTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_718");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_722"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "bow5");
                ep3_clone_relics_jedi_starfighter_darth_vader_action_grantSpaceMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_724");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_726"))
        {
            doAnimationAction(player, "squirm");
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_jedi_starfighter_darth_vader_action_performKillPlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_728");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_733"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_735");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_737");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_737"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                string_id message = new string_id(c_stringFile, "s_739");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_741");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId", 10);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_741"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_743");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_745");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_745"))
        {
            doAnimationAction(player, "bow5");
            ep3_clone_relics_jedi_starfighter_darth_vader_action_signalTalkedOne(player, npc);
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
            {
                ep3_clone_relics_jedi_starfighter_darth_vader_action_grantSpaceMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_747");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_darth_vader"));
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_darth_vader"));
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader");
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
        if (ep3_clone_relics_jedi_starfighter_darth_vader_condition_completedSpaceMission(player, npc))
        {
            doAnimationAction(player, "bow5");
            string_id message = new string_id(c_stringFile, "s_714");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_716");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId", 1);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_darth_vader", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_darth_vader_condition_finished(player, npc))
        {
            doAnimationAction(player, "bow5");
            ep3_clone_relics_jedi_starfighter_darth_vader_action_grantLimitedPass(player, npc);
            string_id message = new string_id(c_stringFile, "s_21");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_darth_vader_condition_failedSpaceMission(player, npc))
        {
            doAnimationAction(player, "bow5");
            string_id message = new string_id(c_stringFile, "s_720");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_722");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_726");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId", 4);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_darth_vader", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_darth_vader_condition_onSpaceMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_730");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_darth_vader_condition_onQuestFour(player, npc))
        {
            doAnimationAction(player, "bow5");
            string_id message = new string_id(c_stringFile, "s_1106");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_733");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId", 8);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_clone_relics_jedi_starfighter_darth_vader", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_jedi_starfighter_darth_vader_condition__defaultCondition(player, npc))
        {
            doAnimationAction(player, "squirm");
            ep3_clone_relics_jedi_starfighter_darth_vader_action_performKillPlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_749");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_clone_relics_jedi_starfighter_darth_vader"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId");
        if (branchId == 1 && ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_clone_relics_jedi_starfighter_darth_vader_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_jedi_starfighter_darth_vader.branchId");
        return SCRIPT_CONTINUE;
    }
}
