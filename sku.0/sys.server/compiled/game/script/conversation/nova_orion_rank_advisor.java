package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.groundquests;
import script.library.township;
import script.library.utils;

public class nova_orion_rank_advisor extends script.base_script
{
    public nova_orion_rank_advisor()
    {
    }
    public static String c_stringFile = "conversation/nova_orion_rank_advisor";
    public boolean nova_orion_rank_advisor_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nova_orion_rank_advisor_condition_isRank4(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollectionSlot(player, "nova_rank_01_04") || hasCompletedCollectionSlot(player, "orion_rank_01_04"));
    }
    public boolean nova_orion_rank_advisor_condition_isRank2Orion(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, township.OBJVAR_NOVA_ORION_FACTION))
        {
            return false;
        }
        String faction = getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION);
        return (hasCompletedCollectionSlot(player, "nova_orion_rank_02") && faction.equals("orion"));
    }
    public boolean nova_orion_rank_advisor_condition_hasEnoughCrystalsRank3(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        return count >= township.RANK_THREE_AMOUNT;
    }
    public boolean nova_orion_rank_advisor_condition_readyRank3(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "no_rank2_04") && (!hasCompletedCollectionSlot(player, "orion_rank_01_03") || !hasCompletedCollectionSlot(player, "nova_rank_01_03")));
    }
    public boolean nova_orion_rank_advisor_condition_isRank2Nova(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, township.OBJVAR_NOVA_ORION_FACTION))
        {
            return false;
        }
        String faction = getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION);
        return (hasCompletedCollectionSlot(player, "nova_orion_rank_02") && faction.equals("nova"));
    }
    public boolean nova_orion_rank_advisor_condition_orionReadyRank4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, township.OBJVAR_NOVA_ORION_FACTION))
        {
            return false;
        }
        String faction = getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION);
        return (groundquests.hasCompletedQuest(player, "orion_rank3_04") && faction.equals("orion") && !hasCompletedCollectionSlot(player, "orion_rank_01_04"));
    }
    public boolean nova_orion_rank_advisor_condition_readyRank2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "nova_orion_rank1_03") && nova_orion_rank_advisor_condition_hasLarnedAboutCrystals(player, npc));
    }
    public boolean nova_orion_rank_advisor_condition_novaReadyRank4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, township.OBJVAR_NOVA_ORION_FACTION))
        {
            return false;
        }
        String faction = getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION);
        return (groundquests.hasCompletedQuest(player, "nova_rank3_05") && faction.equals("nova") && !hasCompletedCollectionSlot(player, "nova_rank_01_04"));
    }
    public boolean nova_orion_rank_advisor_condition_notReadyRank4Nova(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, township.OBJVAR_NOVA_ORION_FACTION))
        {
            return false;
        }
        String faction = getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION);
        return (hasCompletedCollectionSlot(player, "nova_rank_01_03") && faction.equals("nova") && !groundquests.hasCompletedQuest(player, "nova_rank3_05"));
    }
    public boolean nova_orion_rank_advisor_condition_needsRank1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!hasCompletedCollectionSlot(player, "nova_orion_rank_01") && groundquests.hasCompletedQuest(player, "nova_orion_intro_01"));
    }
    public boolean nova_orion_rank_advisor_condition_hasLarnedAboutCrystals(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, township.OBJVAR_KNOWS_CRYSTAL);
    }
    public boolean nova_orion_rank_advisor_condition_notReadyRank4Orion(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, township.OBJVAR_NOVA_ORION_FACTION))
        {
            return false;
        }
        String faction = getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION);
        return (hasCompletedCollectionSlot(player, "orion_rank_01_03") && faction.equals("orion") && !groundquests.hasCompletedQuest(player, "orion_rank3_04"));
    }
    public boolean nova_orion_rank_advisor_condition_hasEnoughCrystalsRank2(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        return count >= township.RANK_TWO_AMOUNT;
    }
    public boolean nova_orion_rank_advisor_condition_hasEnoughCrystalsRank4(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        return count >= township.RANK_FOUR_AMOUNT;
    }
    public boolean nova_orion_rank_advisor_condition_notReadyRank3(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.hasCompletedQuest(player, "no_rank2_04") && hasCompletedCollectionSlot(player, "nova_orion_rank_02"));
    }
    public boolean nova_orion_rank_advisor_condition_notReadyRank2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.hasCompletedQuest(player, "nova_orion_rank1_03") && hasCompletedCollectionSlot(player, "nova_orion_rank_01"));
    }
    public boolean nova_orion_rank_advisor_condition_needsToLearnCrystals(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!hasObjVar(player, township.OBJVAR_KNOWS_CRYSTAL) && groundquests.hasCompletedQuest(player, "nova_orion_rank1_03"));
    }
    public void nova_orion_rank_advisor_action_grantRank1(obj_id player, obj_id npc) throws InterruptedException
    {
        modifyCollectionSlotValue(player, "nova_orion_rank_01", 1);
    }
    public void nova_orion_rank_advisor_action_setLearnedCrystals(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, township.OBJVAR_KNOWS_CRYSTAL, true);
    }
    public void nova_orion_rank_advisor_action_grantRank2(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        obj_id crystal = utils.getStaticItemInInventory(player, township.MIDLITHE_CRYSTAL);
        count -= township.RANK_TWO_AMOUNT;
        if (count <= 0)
        {
            destroyObject(crystal);
        }
        else 
        {
            setCount(crystal, count);
        }
        modifyCollectionSlotValue(player, "nova_orion_rank_02", 1);
    }
    public void nova_orion_rank_advisor_action_grantRank3Orion(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        obj_id crystal = utils.getStaticItemInInventory(player, township.MIDLITHE_CRYSTAL);
        count -= township.RANK_THREE_AMOUNT;
        if (count <= 0)
        {
            destroyObject(crystal);
        }
        else 
        {
            setCount(crystal, count);
        }
        modifyCollectionSlotValue(player, township.ORION_TRACKER_SLOT_NAME, township.RANK_THREE_AMOUNT);
    }
    public void nova_orion_rank_advisor_action_grantRank3Nova(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        obj_id crystal = utils.getStaticItemInInventory(player, township.MIDLITHE_CRYSTAL);
        count -= township.RANK_THREE_AMOUNT;
        if (count <= 0)
        {
            destroyObject(crystal);
        }
        else 
        {
            setCount(crystal, count);
        }
        modifyCollectionSlotValue(player, township.NOVA_TRACKER_SLOT_NAME, township.RANK_THREE_AMOUNT);
    }
    public void nova_orion_rank_advisor_action_grantRank4Nova(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        obj_id crystal = utils.getStaticItemInInventory(player, township.MIDLITHE_CRYSTAL);
        count -= township.RANK_FOUR_AMOUNT;
        if (count <= 0)
        {
            destroyObject(crystal);
        }
        else 
        {
            setCount(crystal, count);
        }
        modifyCollectionSlotValue(player, township.NOVA_TRACKER_SLOT_NAME, township.RANK_FOUR_AMOUNT);
    }
    public void nova_orion_rank_advisor_action_grantRank4Orion(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        obj_id crystal = utils.getStaticItemInInventory(player, township.MIDLITHE_CRYSTAL);
        count -= township.RANK_FOUR_AMOUNT;
        if (count <= 0)
        {
            destroyObject(crystal);
        }
        else 
        {
            setCount(crystal, count);
        }
        modifyCollectionSlotValue(player, township.ORION_TRACKER_SLOT_NAME, township.RANK_FOUR_AMOUNT);
    }
    public String nova_orion_rank_advisor_tokenTO_rank3JobTitle(obj_id player, obj_id npc) throws InterruptedException
    {
        String rank3Job = "Technician";
        if ((getStringObjVar(player, township.OBJVAR_NOVA_ORION_FACTION)).equals("orion"))
        {
            rank3Job = "Researcher";
        }
        return rank3Job;
    }
    public int nova_orion_rank_advisor_tokenDI_rank2Amount(obj_id player, obj_id npc) throws InterruptedException
    {
        return township.RANK_TWO_AMOUNT;
    }
    public int nova_orion_rank_advisor_tokenDI_rank2Short(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        return township.RANK_TWO_AMOUNT - count;
    }
    public int nova_orion_rank_advisor_tokenDI_rank3Amount(obj_id player, obj_id npc) throws InterruptedException
    {
        return township.RANK_THREE_AMOUNT;
    }
    public int nova_orion_rank_advisor_tokenDI_rank3Short(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        return township.RANK_THREE_AMOUNT - count;
    }
    public int nova_orion_rank_advisor_tokenDI_rank4Amount(obj_id player, obj_id npc) throws InterruptedException
    {
        return township.RANK_FOUR_AMOUNT;
    }
    public int nova_orion_rank_advisor_tokenDI_rank4Short(obj_id player, obj_id npc) throws InterruptedException
    {
        int count = township.getMidlitheCrystalCount(player);
        return township.RANK_FOUR_AMOUNT - count;
    }
    public int nova_orion_rank_advisor_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_99"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 5);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank4Amount(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank4Amount(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (!nova_orion_rank_advisor_condition_hasEnoughCrystalsRank4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank4Short(player, npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                nova_orion_rank_advisor_action_grantRank4Orion(player, npc);
                string_id message = new string_id(c_stringFile, "s_105");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_102"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_103");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_106"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_107");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
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
    public int nova_orion_rank_advisor_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_90"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_91");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 11);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank4Amount(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank4Amount(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (!nova_orion_rank_advisor_condition_hasEnoughCrystalsRank4(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank4Short(player, npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                nova_orion_rank_advisor_action_grantRank4Nova(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_98");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
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
    public int nova_orion_rank_advisor_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_69"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 18);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(nova_orion_rank_advisor_tokenTO_rank3JobTitle(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(nova_orion_rank_advisor_tokenTO_rank3JobTitle(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 20);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank3Amount(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank3Amount(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            if (!nova_orion_rank_advisor_condition_hasEnoughCrystalsRank3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank3Short(player, npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (nova_orion_rank_advisor_condition_isRank2Nova(player, npc))
            {
                nova_orion_rank_advisor_action_grantRank3Nova(player, npc);
                string_id message = new string_id(c_stringFile, "s_79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (nova_orion_rank_advisor_condition_isRank2Orion(player, npc))
            {
                nova_orion_rank_advisor_action_grantRank3Orion(player, npc);
                string_id message = new string_id(c_stringFile, "s_109");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
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
    public int nova_orion_rank_advisor_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_114");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
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
    public int nova_orion_rank_advisor_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (!nova_orion_rank_advisor_condition_hasEnoughCrystalsRank2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank2Short(player, npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                nova_orion_rank_advisor_action_grantRank2(player, npc);
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_59"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
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
    public int nova_orion_rank_advisor_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_77");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_108");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_116");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_118"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                nova_orion_rank_advisor_action_setLearnedCrystals(player, npc);
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 37);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank2Amount(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = nova_orion_rank_advisor_tokenDI_rank2Amount(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_122"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_124");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_126"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_128");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_134");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_136");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_140");
                    }
                    utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int nova_orion_rank_advisor_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_136"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                nova_orion_rank_advisor_action_grantRank1(player, npc);
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_140"))
        {
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_142");
                utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
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
            detachScript(self, "conversation.nova_orion_rank_advisor");
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
        detachScript(self, "conversation.nova_orion_rank_advisor");
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
        if (nova_orion_rank_advisor_condition_isRank4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_115");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank_advisor_condition_notReadyRank4Nova(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_87");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank_advisor_condition_notReadyRank4Orion(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_86");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank_advisor_condition_orionReadyRank4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_88");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_99");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 4);
                npcStartConversation(player, npc, "nova_orion_rank_advisor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank_advisor_condition_novaReadyRank4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_89");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 10);
                npcStartConversation(player, npc, "nova_orion_rank_advisor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank_advisor_condition_notReadyRank3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_67");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank_advisor_condition_readyRank3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_68");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 17);
                npcStartConversation(player, npc, "nova_orion_rank_advisor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank_advisor_condition_notReadyRank2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_53");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_rank_advisor_condition_readyRank2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_46");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 28);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "nova_orion_rank_advisor", null, pp, responses);
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
        if (nova_orion_rank_advisor_condition_needsToLearnCrystals(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_63");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 33);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "nova_orion_rank_advisor", null, pp, responses);
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
        if (nova_orion_rank_advisor_condition_needsRank1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_130");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                }
                utils.setScriptVar(player, "conversation.nova_orion_rank_advisor.branchId", 40);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "nova_orion_rank_advisor", null, pp, responses);
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
        if (nova_orion_rank_advisor_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_144");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("nova_orion_rank_advisor"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
        if (branchId == 4 && nova_orion_rank_advisor_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && nova_orion_rank_advisor_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && nova_orion_rank_advisor_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && nova_orion_rank_advisor_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && nova_orion_rank_advisor_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && nova_orion_rank_advisor_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && nova_orion_rank_advisor_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && nova_orion_rank_advisor_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && nova_orion_rank_advisor_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && nova_orion_rank_advisor_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && nova_orion_rank_advisor_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && nova_orion_rank_advisor_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && nova_orion_rank_advisor_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && nova_orion_rank_advisor_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && nova_orion_rank_advisor_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && nova_orion_rank_advisor_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && nova_orion_rank_advisor_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && nova_orion_rank_advisor_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && nova_orion_rank_advisor_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && nova_orion_rank_advisor_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && nova_orion_rank_advisor_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && nova_orion_rank_advisor_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.nova_orion_rank_advisor.branchId");
        return SCRIPT_CONTINUE;
    }
}
