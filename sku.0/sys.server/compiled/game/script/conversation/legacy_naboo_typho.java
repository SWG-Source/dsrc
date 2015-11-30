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

public class legacy_naboo_typho extends script.base_script
{
    public legacy_naboo_typho()
    {
    }
    public static String c_stringFile = "conversation/legacy_naboo_typho";
    public boolean legacy_naboo_typho_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean legacy_naboo_typho_condition_finishedSabe(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/legacy_naboo_helping_the_handmaidens"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_narglatchClawsActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/legacy_naboo_the_keren_incident", "narglatchClaws"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_narglatchClawsFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/legacy_naboo_the_keren_incident", "narglatchClaws"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_veermokClawsActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/legacy_naboo_the_keren_incident", "veermokClaws"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_veermokClawsFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/legacy_naboo_the_keren_incident", "veermokClaws"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_stolenGoodsFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/legacy_naboo_the_keren_incident", "stolenGoods"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_stolenGoodsActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/legacy_naboo_the_keren_incident", "stolenGoods"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_unusualWeaponsFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/legacy_naboo_the_keren_incident", "unusualWeapons"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_unusualWeaponsActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/legacy_naboo_the_keren_incident", "unusualWeapons"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_findBaseActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/legacy_naboo_the_keren_incident", "findBase"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_goToBaseActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/legacy_naboo_the_keren_incident", "goToBase"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_killAssassinActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/legacy_naboo_the_keren_incident", "killAssassin"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_recoverDataActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest/legacy_naboo_the_keren_incident", "recoverData"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_recoverDataFinished(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedTask(player, "quest/legacy_naboo_the_keren_incident", "recoverData"))
        {
            return true;
        }
        return false;
    }
    public boolean legacy_naboo_typho_condition_completedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "quest/legacy_naboo_the_keren_incident"))
        {
            return true;
        }
        return false;
    }
    public void legacy_naboo_typho_action_grantTyphoQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest/legacy_naboo_the_keren_incident");
    }
    public void legacy_naboo_typho_action_signalNarglatchClaws(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "narglatchClaws");
    }
    public void legacy_naboo_typho_action_signalVeermokClaws(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "veermokClaws");
    }
    public void legacy_naboo_typho_action_signalStolenGoods(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "stolenGoods");
    }
    public void legacy_naboo_typho_action_signalUnusualWeapons(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "unusualWeapons");
    }
    public void legacy_naboo_typho_action_signalComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "encryptedData");
    }
    public int legacy_naboo_typho_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_signalUnusualWeapons(player, npc);
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_signalStolenGoods(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_signalVeermokClaws(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_signalNarglatchClaws(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_signalNarglatchClaws(player, npc);
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_72"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_108"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_grantTyphoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_106"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_grantTyphoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_grantTyphoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                    }
                    utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_104"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_grantTyphoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int legacy_naboo_typho_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                legacy_naboo_typho_action_grantTyphoQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_100"))
        {
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
                chat.chat(npc, player, message);
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
            detachScript(self, "conversation.legacy_naboo_typho");
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
        detachScript(self, "conversation.legacy_naboo_typho");
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
        if (legacy_naboo_typho_condition_completedQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_70");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_recoverDataFinished(player, npc))
        {
            legacy_naboo_typho_action_signalComplete(player, npc);
            string_id message = new string_id(c_stringFile, "s_67");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_recoverDataActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_68");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_killAssassinActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_69");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_goToBaseActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_66");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_findBaseActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_65");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_unusualWeaponsFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_62");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 7);
                npcStartConversation(player, npc, "legacy_naboo_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_unusualWeaponsActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_61");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_stolenGoodsFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_55");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 10);
                npcStartConversation(player, npc, "legacy_naboo_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_stolenGoodsActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_56");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_veermokClawsFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_47");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 14);
                npcStartConversation(player, npc, "legacy_naboo_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_veermokClawsActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_narglatchClawsFinished(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_39");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 17);
                npcStartConversation(player, npc, "legacy_naboo_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_narglatchClawsActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition_finishedSabe(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_31");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (legacy_naboo_typho_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                utils.setScriptVar(player, "conversation.legacy_naboo_typho.branchId", 21);
                npcStartConversation(player, npc, "legacy_naboo_typho", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (legacy_naboo_typho_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_110");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("legacy_naboo_typho"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.legacy_naboo_typho.branchId");
        if (branchId == 7 && legacy_naboo_typho_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && legacy_naboo_typho_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && legacy_naboo_typho_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && legacy_naboo_typho_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && legacy_naboo_typho_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && legacy_naboo_typho_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && legacy_naboo_typho_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && legacy_naboo_typho_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && legacy_naboo_typho_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && legacy_naboo_typho_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && legacy_naboo_typho_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && legacy_naboo_typho_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && legacy_naboo_typho_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && legacy_naboo_typho_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.legacy_naboo_typho.branchId");
        return SCRIPT_CONTINUE;
    }
}
