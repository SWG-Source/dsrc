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
import script.library.groundquests;
import script.library.pclib;
import script.library.utils;

public class ep3_clone_relics_clone_trooper_palpatine extends script.base_script
{
    public ep3_clone_relics_clone_trooper_palpatine()
    {
    }
    public static String c_stringFile = "conversation/ep3_clone_relics_clone_trooper_palpatine";
    public boolean ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_clone_relics_clone_trooper_palpatine_condition_onTaskTalkEmperor(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_clone_trooper_mort_imperial", "talkToEmperor"));
    }
    public boolean ep3_clone_relics_clone_trooper_palpatine_condition_onTaskMorkov(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_clone_relics_clone_trooper_mort_imperial", "killMorkov"));
    }
    public void ep3_clone_relics_clone_trooper_palpatine_action_emperorDone(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToEmperor");
        setObjVar(player, "doneWithEmperor", 1);
    }
    public void ep3_clone_relics_clone_trooper_palpatine_action_deathOfPlayer(obj_id player, obj_id npc) throws InterruptedException
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
    public int ep3_clone_relics_clone_trooper_palpatine_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_174"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_178");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_palpatine_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_178"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_palpatine_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_182"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_184");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId", 5);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_palpatine_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_186"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_188");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_194");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_palpatine_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_190"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                doAnimationAction(player, "bow5");
                ep3_clone_relics_clone_trooper_palpatine_action_emperorDone(player, npc);
                string_id message = new string_id(c_stringFile, "s_192");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_194"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_196");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_198");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_200");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId", 8);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_palpatine_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_198"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                doAnimationAction(player, "bow5");
                ep3_clone_relics_clone_trooper_palpatine_action_emperorDone(player, npc);
                string_id message = new string_id(c_stringFile, "s_192");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_200"))
        {
            doAnimationAction(player, "shake_head_no");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_202");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_204");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_206");
                    }
                    utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId", 9);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_clone_relics_clone_trooper_palpatine_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_204"))
        {
            doAnimationAction(player, "nod_head_once");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                doAnimationAction(player, "bow5");
                ep3_clone_relics_clone_trooper_palpatine_action_emperorDone(player, npc);
                string_id message = new string_id(c_stringFile, "s_192");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_206"))
        {
            doAnimationAction(player, "apologize");
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "squirm");
                ep3_clone_relics_clone_trooper_palpatine_action_deathOfPlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_208");
                utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_clone_relics_clone_trooper_palpatine");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_palpatine"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, new string_id("ep3/npc_names", "clone_relics_palpatine"));
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
        detachScript(self, "conversation.ep3_clone_relics_clone_trooper_palpatine");
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
        if (ep3_clone_relics_clone_trooper_palpatine_condition_onTaskMorkov(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            doAnimationAction(player, "bow5");
            string_id message = new string_id(c_stringFile, "s_170");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (ep3_clone_relics_clone_trooper_palpatine_condition_onTaskTalkEmperor(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            doAnimationAction(player, "bow5");
            string_id message = new string_id(c_stringFile, "s_172");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_174");
                }
                utils.setScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId", 2);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "ep3_clone_relics_clone_trooper_palpatine", null, pp, responses);
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
        if (ep3_clone_relics_clone_trooper_palpatine_condition__defaultCondition(player, npc))
        {
            doAnimationAction(player, "squirm");
            ep3_clone_relics_clone_trooper_palpatine_action_deathOfPlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1554");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_clone_relics_clone_trooper_palpatine"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
        if (branchId == 2 && ep3_clone_relics_clone_trooper_palpatine_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_clone_relics_clone_trooper_palpatine_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_clone_relics_clone_trooper_palpatine_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_clone_relics_clone_trooper_palpatine_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_clone_relics_clone_trooper_palpatine_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_clone_relics_clone_trooper_palpatine_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_clone_relics_clone_trooper_palpatine_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_clone_relics_clone_trooper_palpatine.branchId");
        return SCRIPT_CONTINUE;
    }
}
