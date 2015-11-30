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
import script.library.create;
import script.library.groundquests;
import script.library.utils;

public class mtp_ragtag_ames_missd extends script.base_script
{
    public mtp_ragtag_ames_missd()
    {
    }
    public static String c_stringFile = "conversation/mtp_ragtag_ames_missd";
    public boolean mtp_ragtag_ames_missd_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_ragtag_ames_missd_condition_canSpeak(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "mtp_hideout_ragtag") && groundquests.isTaskActive(player, "mtp_hideout_ragtag", "beatUpRagTag");
    }
    public boolean mtp_ragtag_ames_missd_condition_beatAnita(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest/mtp_hideout_ragtag", "speakAmesAnitaDone");
    }
    public boolean mtp_ragtag_ames_missd_condition_beatBox(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest/mtp_hideout_ragtag", "speakAmesBoxDone");
    }
    public boolean mtp_ragtag_ames_missd_condition_waveEventUnderway(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(npc, "waveEventCurrentWave"))
        {
            int wave = utils.getIntScriptVar(npc, "waveEventCurrentWave");
            if (wave > 0)
            {
                return true;
            }
        }
        return false;
    }
    public boolean mtp_ragtag_ames_missd_condition_doneWithAmes(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "quest/mtp_hideout_ragtag", "speakAmesBoxDone");
    }
    public void mtp_ragtag_ames_missd_action_rumbleFish(obj_id player, obj_id npc) throws InterruptedException
    {
        npcEndConversation(player);
        groundquests.sendSignal(player, "ragTagBeatUp");
        int mobLevel = getLevel(player);
        setLevel(npc, mobLevel + 10);
        setInvulnerable(npc, false);
        startCombat(npc, player);
        clearCondition(npc, CONDITION_CONVERSABLE);
    }
    public void mtp_ragtag_ames_missd_action_spawnAnita(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "fightAnita");
        dictionary webster = new dictionary();
        webster.put("player", player);
        webster.put("playerLevel", getLevel(player) - 10);
        messageTo(npc, "waveEventControllerNPCStart", webster, 1, false);
        return;
    }
    public void mtp_ragtag_ames_missd_action_spawnBox(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "fightBox");
        dictionary webster = new dictionary();
        webster.put("player", player);
        webster.put("playerLevel", getLevel(player) - 5);
        messageTo(npc, "waveEventControllerNPCStart", webster, 1, false);
        return;
    }
    public void mtp_ragtag_ames_missd_action_sendCompletion(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "spokenAmes");
    }
    public int mtp_ragtag_ames_missd_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60"))
        {
            if (mtp_ragtag_ames_missd_condition_waveEventUnderway(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                mtp_ragtag_ames_missd_action_spawnAnita(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73"))
        {
            if (mtp_ragtag_ames_missd_condition_waveEventUnderway(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                mtp_ragtag_ames_missd_action_spawnBox(player, npc);
                string_id message = new string_id(c_stringFile, "s_74");
                utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_77");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                    }
                    utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                    }
                    utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cover_eyes");
                doAnimationAction(player, "fakepunch");
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_ragtag_ames_missd_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                doAnimationAction(player, "slit_throat");
                mtp_ragtag_ames_missd_action_sendCompletion(player, npc);
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
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
            detachScript(self, "conversation.mtp_ragtag_ames_missd");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.mtp_ragtag_ames_missd");
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
        if (mtp_ragtag_ames_missd_condition_doneWithAmes(player, npc))
        {
            doAnimationAction(npc, "apologize");
            string_id message = new string_id(c_stringFile, "s_45");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_ragtag_ames_missd_condition_waveEventUnderway(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_35");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_ragtag_ames_missd_condition_canSpeak(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8");
                }
                utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 3);
                npcStartConversation(player, npc, "mtp_ragtag_ames_missd", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_ragtag_ames_missd_condition_beatAnita(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_72");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                }
                utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 12);
                npcStartConversation(player, npc, "mtp_ragtag_ames_missd", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_ragtag_ames_missd_condition_beatBox(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_75");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                }
                utils.setScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId", 15);
                npcStartConversation(player, npc, "mtp_ragtag_ames_missd", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_ragtag_ames_missd_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("mtp_ragtag_ames_missd"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
        if (branchId == 3 && mtp_ragtag_ames_missd_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && mtp_ragtag_ames_missd_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && mtp_ragtag_ames_missd_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && mtp_ragtag_ames_missd_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && mtp_ragtag_ames_missd_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && mtp_ragtag_ames_missd_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && mtp_ragtag_ames_missd_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && mtp_ragtag_ames_missd_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && mtp_ragtag_ames_missd_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && mtp_ragtag_ames_missd_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && mtp_ragtag_ames_missd_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && mtp_ragtag_ames_missd_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_ragtag_ames_missd.branchId");
        return SCRIPT_CONTINUE;
    }
}
