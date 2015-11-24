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
import script.library.npe;
import script.library.space_quest;
import script.library.static_item;
import script.library.utils;

public class npe_brawler extends script.base_script
{
    public npe_brawler()
    {
    }
    public static String c_stringFile = "conversation/npe_brawler";
    public boolean npe_brawler_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_brawler_condition_isTaskActiveAny(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_brawler_2hand", "kill") || groundquests.isTaskActive(player, "npe_brawler_1hand", "kill") || groundquests.isTaskActive(player, "npe_brawler_unarmed", "kill") || groundquests.isTaskActive(player, "npe_brawler_polearm", "kill"));
    }
    public boolean npe_brawler_condition_isTaskCompleteUnarmed(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_brawler_unarmed", "wait");
    }
    public boolean npe_brawler_condition_isTaskComplete1hand(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_brawler_1hand", "wait");
    }
    public boolean npe_brawler_condition_isTaskComplete2hand(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_brawler_2hand", "wait");
    }
    public boolean npe_brawler_condition_isTaskCompletePolearm(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_brawler_polearm", "wait");
    }
    public boolean npe_brawler_condition_isTaskActiveStory1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_brawler_1", "flits") || groundquests.isTaskActive(player, "npe_brawler_1", "elevator_up") || groundquests.isTaskActive(player, "npe_brawler_1", "scyks") && !groundquests.hasCompletedQuest(player, "npe_brawler_1"));
    }
    public boolean npe_brawler_condition_hasCompletedTaskStory1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_brawler_1", "return");
    }
    public boolean npe_brawler_condition_hasCompletedStory1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_brawler_1");
    }
    public boolean npe_brawler_condition_isTaskActiveStory2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_brawler_2", "tiarsis_talk") || groundquests.isTaskActive(player, "npe_brawler_2", "killdude") || groundquests.isTaskActive(player, "npe_brawler_2", "npe_brawler_tiasris_wait"));
    }
    public boolean npe_brawler_condition_hasCompletedTaskSmugglers(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_brawler_2", "npe_smuggler_finish");
    }
    public boolean npe_brawler_condition_hasCompletedStory2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_brawler_2");
    }
    public boolean npe_brawler_condition_isTaskActiveStory4(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_brawler_4a", "stims") || groundquests.isTaskActive(player, "npe_brawler_4a", "boss") && !groundquests.hasCompletedQuest(player, "npe_brawler_4a"));
    }
    public boolean npe_brawler_condition_hasCompletedTaskStory4(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_brawler_4a", "npe_brawler4_2h_finish");
    }
    public boolean npe_brawler_condition_hasCompletedStory4(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_brawler_4a");
    }
    public boolean npe_brawler_condition_isTaskActiveSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_brawler_2", "elev_down") || groundquests.isTaskActive(player, "npe_brawler_2", "waiting"));
    }
    public boolean npe_brawler_condition_hasCompletedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return (npe_brawler_condition_hasFinished1Hand(player, npc) && npe_brawler_condition_hasFinished2Hand(player, npc) && npe_brawler_condition_hasFinishedPolearm(player, npc) && npe_brawler_condition_hasFinishedUnarmed(player, npc));
    }
    public boolean npe_brawler_condition_cantHelp(obj_id player, obj_id npc) throws InterruptedException
    {
        return !npe_brawler_condition_hasCompletedTemplate(player, npc);
    }
    public boolean npe_brawler_condition_hasCompletedTemplate(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "npe.finishedTemplate");
    }
    public boolean npe_brawler_condition_hasFinishedUnarmed(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_brawler_unarmed");
    }
    public boolean npe_brawler_condition_hasFinishedPolearm(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_brawler_polearm");
    }
    public boolean npe_brawler_condition_hasFinished1Hand(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_brawler_1hand");
    }
    public boolean npe_brawler_condition_hasFinished2Hand(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_brawler_2hand");
    }
    public void npe_brawler_action_givePolearmQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_brawler_polearm");
        groundquests.sendSignal(player, "talked_to_rydel");
        npe.givePolePopUp(player, npc);
        npe.giveAutoPopUp(player, npc);
        boolean hasItem = false;
        obj_id[] weapons = new obj_id[1];
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = static_item.getStaticItemName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("weapon_polearm_02_02"))
                {
                    hasItem = true;
                }
            }
        }
        if (hasItem == false)
        {
            obj_id polearm = static_item.createNewItemFunction("weapon_polearm_02_02", player);
            weapons[0] = polearm;
            showLootBox(player, weapons);
        }
    }
    public void npe_brawler_action_giveUnarmedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_brawler_unarmed");
        groundquests.sendSignal(player, "talked_to_rydel");
        npe.giveUnarmPopUp(player, npc);
        npe.giveAutoPopUp(player, npc);
    }
    public void npe_brawler_action_give1handquest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_brawler_1hand");
        groundquests.sendSignal(player, "talked_to_rydel");
        npe.give1hPopUp(player, npc);
        npe.giveAutoPopUp(player, npc);
        boolean hasItem = false;
        obj_id[] weapons = new obj_id[1];
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = static_item.getStaticItemName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("weapon_sword_1h_02_02"))
                {
                    hasItem = true;
                }
            }
        }
        if (hasItem == false)
        {
            obj_id sword = static_item.createNewItemFunction("weapon_sword_1h_02_02", player);
            weapons[0] = sword;
            showLootBox(player, weapons);
        }
    }
    public void npe_brawler_action_give2handquest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_brawler_2hand");
        groundquests.sendSignal(player, "talked_to_rydel");
        npe.give2hPopUp(player, npc);
        npe.giveAutoPopUp(player, npc);
        boolean hasItem = false;
        obj_id[] weapons = new obj_id[1];
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = static_item.getStaticItemName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("weapon_sword_2h_02_02"))
                {
                    hasItem = true;
                }
            }
        }
        if (hasItem == false)
        {
            obj_id sword = static_item.createNewItemFunction("weapon_sword_2h_02_02", player);
            weapons[0] = sword;
            showLootBox(player, weapons);
        }
    }
    public void npe_brawler_action_giveStory1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "npe_brawler_1");
    }
    public void npe_brawler_action_giveSingalUnarmed(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_brawler_unarmed");
        groundquests.sendSignal(player, "finished_rydel");
        npe.sendDelayed3poPopup(player, 3, 11, "sound/c3po_29.snd", "npe", "pop_credits", "npe.credits");
    }
    public void npe_brawler_action_giveSignal1hand(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_brawler_1hand");
        groundquests.sendSignal(player, "finished_rydel");
        npe.sendDelayed3poPopup(player, 3, 11, "sound/c3po_29.snd", "npe", "pop_credits", "npe.credits");
    }
    public void npe_brawler_action_giveSignal2hand(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_brawler_2hand");
        groundquests.sendSignal(player, "finished_rydel");
        npe.sendDelayed3poPopup(player, 3, 11, "sound/c3po_29.snd", "npe", "pop_credits", "npe.credits");
    }
    public void npe_brawler_action_giveSignalPolearm(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_brawler_polearm");
        groundquests.sendSignal(player, "finished_rydel");
        npe.sendDelayed3poPopup(player, 3, 11, "sound/c3po_29.snd", "npe", "pop_credits", "npe.credits");
    }
    public void npe_brawler_action_giveSignalStory1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_brawler_killstuff");
        npe.sendDelayed3poPopup(player, 3, 11, "sound/c3po_29.snd", "npe", "pop_credits", "npe.credits");
    }
    public void npe_brawler_action_giveStory2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "npe_brawler_2");
    }
    public void npe_brawler_action_grantStory4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "npe_brawler_4a");
    }
    public void npe_brawler_action_giveSignalSmuggs(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_brawler_smugglers");
        npe.sendDelayed3poPopup(player, 3, 11, "sound/c3po_29.snd", "npe", "pop_credits", "npe.credits");
    }
    public void npe_brawler_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void npe_brawler_action_giveSignalStory4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_brawler4a_done");
        npe.sendDelayed3poPopup(player, 3, 11, "sound/c3po_29.snd", "npe", "pop_credits", "npe.credits");
    }
    public int npe_brawler_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_260"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                string_id message = new string_id(c_stringFile, "s_261");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
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
    public int npe_brawler_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_262"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_263");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_brawler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                    }
                    utils.setScriptVar(player, "conversation.npe_brawler.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_264"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "feed_creature_medium");
                npe_brawler_action_giveSignalStory4(player, npc);
                string_id message = new string_id(c_stringFile, "s_265");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_256"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                string_id message = new string_id(c_stringFile, "s_257");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_249"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_250");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_brawler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_251");
                    }
                    utils.setScriptVar(player, "conversation.npe_brawler.branchId", 9);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
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
    public int npe_brawler_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_251"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_grantStory4(player, npc);
                string_id message = new string_id(c_stringFile, "s_252");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_238"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_239");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_110");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_241"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveSignalSmuggs(player, npc);
                string_id message = new string_id(c_stringFile, "s_242");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_234"))
        {
            doAnimationAction(player, "bow");
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveStory2(player, npc);
                string_id message = new string_id(c_stringFile, "s_235");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_221"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveSignalStory1(player, npc);
                string_id message = new string_id(c_stringFile, "s_222");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_218"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                string_id message = new string_id(c_stringFile, "s_219");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_160"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveSingalUnarmed(player, npc);
                string_id message = new string_id(c_stringFile, "s_161");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_164"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveSignalPolearm(player, npc);
                string_id message = new string_id(c_stringFile, "s_165");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_156"))
        {
            doAnimationAction(player, "bow");
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveSignal2hand(player, npc);
                string_id message = new string_id(c_stringFile, "s_157");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
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
    public int npe_brawler_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_152"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveSignal1hand(player, npc);
                string_id message = new string_id(c_stringFile, "s_153");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
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
    public int npe_brawler_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_117"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                string_id message = new string_id(c_stringFile, "s_119");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_103"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_105");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_brawler_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_brawler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                    }
                    utils.setScriptVar(player, "conversation.npe_brawler.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_88");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!npe_brawler_condition_hasFinishedUnarmed(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!npe_brawler_condition_hasFinished2Hand(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!npe_brawler_condition_hasFinished1Hand(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (!npe_brawler_condition_hasFinishedPolearm(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_113");
                    }
                    utils.setScriptVar(player, "conversation.npe_brawler.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_344"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveStory1(player, npc);
                string_id message = new string_id(c_stringFile, "s_347");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_107"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_109");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_brawler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_386");
                    }
                    utils.setScriptVar(player, "conversation.npe_brawler.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_386"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_387");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_brawler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    utils.setScriptVar(player, "conversation.npe_brawler.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_brawler_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_389"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveStory1(player, npc);
                string_id message = new string_id(c_stringFile, "s_391");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
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
    public int npe_brawler_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_giveUnarmedQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_99"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_give2handquest(player, npc);
                string_id message = new string_id(c_stringFile, "s_101");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_104"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_give1handquest(player, npc);
                string_id message = new string_id(c_stringFile, "s_111");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_113"))
        {
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                npe_brawler_action_givePolearmQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
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
            detachScript(self, "conversation.npe_brawler");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Rydel Delan");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Rydel Delan");
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
        detachScript(self, "conversation.npe_brawler");
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
        if (npe_brawler_condition_hasCompletedStory4(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_259");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_260");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 1);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_hasCompletedTaskStory4(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_258");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 3);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_brawler", null, pp, responses);
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
        if (npe_brawler_condition_isTaskActiveStory4(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_255");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 6);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_hasCompletedStory2(player, npc))
        {
            doAnimationAction(npc, "nod");
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_247");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_249");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 8);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_isTaskActiveStory2(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_236");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 11);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_isTaskActiveSmuggler(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_334");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 13);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_hasCompletedTaskSmugglers(player, npc))
        {
            doAnimationAction(npc, "bow");
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_240");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_241");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 15);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_hasCompletedStory1(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_217");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 17);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_hasCompletedTaskStory1(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_216");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_221");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 19);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_isTaskActiveStory1(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_215");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_218");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 21);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_isTaskCompleteUnarmed(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_94");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 23);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_isTaskCompletePolearm(player, npc))
        {
            doAnimationAction(npc, "nod");
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_93");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_164");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 25);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_isTaskComplete2hand(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_92");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 27);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_isTaskComplete1hand(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_91");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_152");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 29);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_isTaskActiveAny(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_115");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 31);
                npcStartConversation(player, npc, "npe_brawler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition_hasCompletedTemplate(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_97");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_brawler_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!npe_brawler_condition_hasCompletedAll(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                }
                utils.setScriptVar(player, "conversation.npe_brawler.branchId", 33);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_brawler", null, pp, responses);
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
        if (npe_brawler_condition_cantHelp(player, npc))
        {
            npe_brawler_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_141");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_brawler_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_143");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_brawler"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_brawler.branchId");
        if (branchId == 1 && npe_brawler_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && npe_brawler_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && npe_brawler_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && npe_brawler_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && npe_brawler_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_brawler_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && npe_brawler_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && npe_brawler_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && npe_brawler_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && npe_brawler_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && npe_brawler_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && npe_brawler_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && npe_brawler_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && npe_brawler_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && npe_brawler_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && npe_brawler_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && npe_brawler_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && npe_brawler_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && npe_brawler_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && npe_brawler_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && npe_brawler_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && npe_brawler_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_brawler.branchId");
        return SCRIPT_CONTINUE;
    }
}
