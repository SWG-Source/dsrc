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
import script.library.utils;

public class wod_omogg_rep extends script.base_script
{
    public wod_omogg_rep()
    {
    }
    public static String c_stringFile = "conversation/wod_omogg_rep";
    public boolean wod_omogg_rep_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean wod_omogg_rep_condition_onReturn1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "wod_prologue_walkabout_01", "returnToRep01");
    }
    public boolean wod_omogg_rep_condition_onReturn2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "wod_prologue_walkabout_02", "returnToRep02");
    }
    public boolean wod_omogg_rep_condition_finishedQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "wod_prologue_walkabout_01");
    }
    public boolean wod_omogg_rep_condition_onQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "wod_prologue_walkabout_01") || groundquests.isQuestActive(player, "wod_prologue_walkabout_02");
    }
    public boolean wod_omogg_rep_condition_finishedQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/wod_prologue_walkabout_02");
        int returned2Id = groundquests.getTaskId(questId, "returnToRep02");
        return questIsTaskComplete(questId, returned2Id, player);
    }
    public boolean wod_omogg_rep_condition_canGrantQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "wod_prologue_walkabout_01") && !groundquests.hasCompletedQuest(player, "wod_prologue_walkabout_01");
    }
    public void wod_omogg_rep_action_grantFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "wod_prologue_walkabout_01");
    }
    public void wod_omogg_rep_action_grantSecondQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "wod_prologue_walkabout_02");
    }
    public void wod_omogg_rep_action_sendReturnedSignal1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnToRep01");
    }
    public void wod_omogg_rep_action_sendReturnedSignal2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "returnToRep02");
    }
    public int wod_omogg_rep_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_21"))//Easy credits? Excuse me if I'm a little skeptical.
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");//All I need is for you to take a quick trip out to a couple of scenic spots not too far away. I'm collecting pictures, sales material actually, for the planet. I need pictures that will make Dathomir look like a beautiful and peaceful planet.
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_omogg_rep_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28");//Dathomir? Beautiful? Peaceful?
                    }
                    utils.setScriptVar(player, "conversation.wod_omogg_rep.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_omogg_rep_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28"))//Dathomir? Beautiful? Peaceful?
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30");//It's just a little harmless marketing.
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_omogg_rep_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wod_omogg_rep_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");//Why not? I'll take the job.
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");//I think I'll pass. Sounds like trouble.
                    }
                    utils.setScriptVar(player, "conversation.wod_omogg_rep.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_omogg_rep_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))//Why not? I'll take the job.
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                wod_omogg_rep_action_grantFirstQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_34");//Perfect. Your first stop will be some local waterfalls.
                utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))//I think I'll pass. Sounds like trouble.
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");//That's a shame. For some reason it's hard to find wildlife photographers here on Dathomir. I wonder why?
                utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_omogg_rep_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))//I got the holograms.
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                wod_omogg_rep_action_sendReturnedSignal1(player, npc);
                string_id message = new string_id(c_stringFile, "s_13");//Great. I hope there'll be enough holograms without monsters, witches and other horrors to put together a good looking brochure.
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_omogg_rep_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35");//Well, good luck.
                    }
                    utils.setScriptVar(player, "conversation.wod_omogg_rep.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_omogg_rep_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))//Well, good luck.
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");//Wait, I think I'll need some more pictures. Interested in a second trip?
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (wod_omogg_rep_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (wod_omogg_rep_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");//Yeah, why not?
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");//Not right now, maybe later.
                    }
                    utils.setScriptVar(player, "conversation.wod_omogg_rep.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_omogg_rep_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))//Yeah, why not?
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                wod_omogg_rep_action_grantSecondQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");//Great. Your first stop will be northwest of here.
                utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48"))//Not right now, maybe later.
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");//Maybe later, then.
                utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_omogg_rep_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))//Here's the holograms I recorded.
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                wod_omogg_rep_action_sendReturnedSignal2(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");//Not bad. We might be able to use some of these. 
                utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int wod_omogg_rep_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))//Yeah, why not?
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                wod_omogg_rep_action_grantSecondQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");//Great. Your first stop will be northwest of here.
                utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48"))//Not right now, maybe later.
        {
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");//Maybe later, then.
                utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
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
            detachScript(self, "conversation.wod_omogg_rep");
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
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.wod_omogg_rep");
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
        if (wod_omogg_rep_condition_canGrantQuest1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_19");//Hey you! Are you interested in making some easy credits?
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21");//Easy credits? Excuse me if I'm a little skeptical.
                }
                utils.setScriptVar(player, "conversation.wod_omogg_rep.branchId", 1);
                npcStartConversation(player, npc, "wod_omogg_rep", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wod_omogg_rep_condition_onReturn1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");//You're back.
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");//I got the holograms.
                }
                utils.setScriptVar(player, "conversation.wod_omogg_rep.branchId", 6);
                npcStartConversation(player, npc, "wod_omogg_rep", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wod_omogg_rep_condition_onReturn2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_23");//Welcome back.
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24");//Here's the holograms I recorded.
                }
                utils.setScriptVar(player, "conversation.wod_omogg_rep.branchId", 11);
                npcStartConversation(player, npc, "wod_omogg_rep", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wod_omogg_rep_condition_finishedQuest2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");//Thank you for your work. I don't have anything else for you to do, I need to get back to Coronet and get these holograms edited.
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (wod_omogg_rep_condition_onQuests(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_17");//You still have work to do. Return here when you got the pictures.
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (wod_omogg_rep_condition_finishedQuest1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_42");//So are you ready for another little trip?
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (wod_omogg_rep_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");//Yeah, why not?
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48");//Not right now, maybe later.
                }
                utils.setScriptVar(player, "conversation.wod_omogg_rep.branchId", 15);
                npcStartConversation(player, npc, "wod_omogg_rep", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (wod_omogg_rep_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_52");//I don't have anything to talk to you about.
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("wod_omogg_rep"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.wod_omogg_rep.branchId");
        if (branchId == 1 && wod_omogg_rep_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && wod_omogg_rep_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && wod_omogg_rep_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && wod_omogg_rep_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && wod_omogg_rep_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && wod_omogg_rep_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && wod_omogg_rep_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && wod_omogg_rep_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.wod_omogg_rep.branchId");
        return SCRIPT_CONTINUE;
    }
}