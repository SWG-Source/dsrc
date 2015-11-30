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
import script.library.utils;

public class ep3_achonnko extends script.base_script
{
    public ep3_achonnko()
    {
    }
    public static String c_stringFile = "conversation/ep3_achonnko";
    public boolean ep3_achonnko_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_achonnko_condition_firstTimeUser(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!groundquests.hasCompletedQuest(player, "ep3_rryatt_trail_mastery"))
        {
            if (!groundquests.isQuestActive(player, "ep3_rryatt_trail_mastery"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_achonnko_condition_hasLevelOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_rryatt_trail_mastery", "levelOne") || groundquests.hasCompletedQuest(player, "ep3_rryatt_trail_mastery"));
    }
    public boolean ep3_achonnko_condition_hasLevelTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_rryatt_trail_mastery", "levelTwo") || groundquests.hasCompletedQuest(player, "ep3_rryatt_trail_mastery"));
    }
    public boolean ep3_achonnko_condition_hasLevelThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_rryatt_trail_mastery", "levelThree") || groundquests.hasCompletedQuest(player, "ep3_rryatt_trail_mastery"));
    }
    public boolean ep3_achonnko_condition_hasLevelFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_rryatt_trail_mastery", "levelFour") || groundquests.hasCompletedQuest(player, "ep3_rryatt_trail_mastery"));
    }
    public boolean ep3_achonnko_condition_wookieeNoob(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.canSpeakWookiee(player, npc);
    }
    public boolean ep3_achonnko_condition_canTakeCamoKitQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "outdoors_ranger_movement_03") && !hasSchematic(player, "object/draft_schematic/scout/item_camokit_kashyyyk.iff") && !groundquests.isQuestActive(player, "ep3_achonnko_camo_kit"));
    }
    public boolean ep3_achonnko_condition_canCompleteCamoKitQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "outdoors_ranger_movement_03") && !hasSchematic(player, "object/draft_schematic/scout/item_camokit_kashyyyk.iff") && groundquests.isTaskActive(player, "ep3_achonnko_camo_kit", "taskReturnToAchonnko"));
    }
    public void ep3_achonnko_action_grantMasteryQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/ep3_rryatt_trail_mastery");
    }
    public void ep3_achonnko_action_warpLevelTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        String level = getStringObjVar(npc, "trailMasteryTwo");
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("destination", level);
        params.put("zoneIn", true);
        messageTo(npc, "handleZoneTransitionRequest", params, 0, false);
    }
    public void ep3_achonnko_action_warpLevelThree(obj_id player, obj_id npc) throws InterruptedException
    {
        String level = getStringObjVar(npc, "trailMasteryThree");
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("destination", level);
        params.put("zoneIn", true);
        messageTo(npc, "handleZoneTransitionRequest", params, 0, false);
    }
    public void ep3_achonnko_action_warpLevelFour(obj_id player, obj_id npc) throws InterruptedException
    {
        String level = getStringObjVar(npc, "trailMasteryFour");
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("destination", level);
        params.put("zoneIn", true);
        messageTo(npc, "handleZoneTransitionRequest", params, 0, false);
    }
    public void ep3_achonnko_action_warpLevelFive(obj_id player, obj_id npc) throws InterruptedException
    {
        String level = getStringObjVar(npc, "trailMasteryFive");
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("destination", level);
        params.put("zoneIn", true);
        messageTo(npc, "handleZoneTransitionRequest", params, 0, false);
    }
    public void ep3_achonnko_action_doWookieeJibberJabber(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.emoteWookieeConfusion(player, npc);
    }
    public void ep3_achonnko_action_grantKashyyykCamoKit(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "signalReturnToAchonnko");
        grantSchematic(player, "object/draft_schematic/scout/item_camokit_kashyyyk.iff");
    }
    public void ep3_achonnko_action_grantCamoKitQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_achonnko_camo_kit");
    }
    public int ep3_achonnko_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                ep3_achonnko_action_grantKashyyykCamoKit(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
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
    public int ep3_achonnko_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_173"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_175");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_achonnko_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_177");
                    }
                    utils.setScriptVar(player, "conversation.ep3_achonnko.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_achonnko_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.ep3_achonnko.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_achonnko_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_177"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_179");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_achonnko_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_181");
                    }
                    utils.setScriptVar(player, "conversation.ep3_achonnko.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_achonnko_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_181"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                ep3_achonnko_action_grantMasteryQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_183");
                utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_achonnko_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_187"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                ep3_achonnko_action_warpLevelTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_189");
                utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_191"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                ep3_achonnko_action_warpLevelThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_193");
                utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_195"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                ep3_achonnko_action_warpLevelFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_197");
                utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_199"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                ep3_achonnko_action_warpLevelFive(player, npc);
                string_id message = new string_id(c_stringFile, "s_201");
                utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_203"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_205");
                utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_achonnko_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.ep3_achonnko.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_achonnko_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_achonnko_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.ep3_achonnko.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_achonnko_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                ep3_achonnko_action_grantCamoKitQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
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
            detachScript(self, "conversation.ep3_achonnko");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_achonnko");
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
        if (ep3_achonnko_condition_wookieeNoob(player, npc))
        {
            ep3_achonnko_action_doWookieeJibberJabber(player, npc);
            string_id message = new string_id(c_stringFile, "s_169");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_achonnko_condition_canCompleteCamoKitQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_achonnko_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29");
                }
                utils.setScriptVar(player, "conversation.ep3_achonnko.branchId", 2);
                npcStartConversation(player, npc, "ep3_achonnko", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_achonnko_condition_firstTimeUser(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_171");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_achonnko_condition_canTakeCamoKitQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_173");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                utils.setScriptVar(player, "conversation.ep3_achonnko.branchId", 4);
                npcStartConversation(player, npc, "ep3_achonnko", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_achonnko_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_185");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_achonnko_condition_hasLevelOne(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_achonnko_condition_hasLevelTwo(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_achonnko_condition_hasLevelThree(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_achonnko_condition_hasLevelFour(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (ep3_achonnko_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (ep3_achonnko_condition_canTakeCamoKitQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_187");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_191");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_195");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.ep3_achonnko.branchId", 8);
                npcStartConversation(player, npc, "ep3_achonnko", message, responses);
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
        if (!conversationId.equals("ep3_achonnko"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_achonnko.branchId");
        if (branchId == 2 && ep3_achonnko_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_achonnko_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && ep3_achonnko_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_achonnko_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_achonnko_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_achonnko_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_achonnko_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_achonnko.branchId");
        return SCRIPT_CONTINUE;
    }
}
