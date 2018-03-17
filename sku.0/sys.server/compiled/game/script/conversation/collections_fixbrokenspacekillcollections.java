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
import script.library.collection;
import script.library.utils;

public class collections_fixbrokenspacekillcollections extends script.base_script
{
    public collections_fixbrokenspacekillcollections()
    {
    }
    public static String c_stringFile = "conversation/collections_fixbrokenspacekillcollections";
    public boolean collections_fixbrokenspacekillcollections_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier1Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_rebel_tier_01") && getCollectionSlotValue(player, "kill_space_rebel_icon_tier1") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier1Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_imperial_tier_01") && getCollectionSlotValue(player, "kill_space_imperial_icon_tier1") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier1Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_blacksun_tier_01") && getCollectionSlotValue(player, "kill_space_blacksun_icon_tier1") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier1Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_droid_tier_01") && getCollectionSlotValue(player, "kill_space_droid_icon_tier1") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier1Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_pirate_tier_01") && getCollectionSlotValue(player, "kill_space_pirate_icon_tier1") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier1Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_void_tier_01") && getCollectionSlotValue(player, "kill_space_void_icon_tier1") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier2Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_blacksun_tier_02") && getCollectionSlotValue(player, "kill_space_blacksun_icon_tier2") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier3Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_blacksun_tier_03") && getCollectionSlotValue(player, "kill_space_blacksun_icon_tier3") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier4Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_blacksun_tier_04") && getCollectionSlotValue(player, "kill_space_blacksun_icon_tier4") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier5Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_blacksun_tier_05") && getCollectionSlotValue(player, "kill_space_blacksun_icon_tier5") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier2Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_droid_tier_02") && getCollectionSlotValue(player, "kill_space_droid_icon_tier2") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier3Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_droid_tier_03") && getCollectionSlotValue(player, "kill_space_droid_icon_tier3") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier4Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_droid_tier_04") && getCollectionSlotValue(player, "kill_space_droid_icon_tier4") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier5Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_droid_tier_05") && getCollectionSlotValue(player, "kill_space_droid_icon_tier5") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier2Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_pirate_tier_02") && getCollectionSlotValue(player, "kill_space_pirate_icon_tier2") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier3Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_pirate_tier_03") && getCollectionSlotValue(player, "kill_space_pirate_icon_tier3") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier4Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_pirate_tier_04") && getCollectionSlotValue(player, "kill_space_pirate_icon_tier4") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier5Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_pirate_tier_05") && getCollectionSlotValue(player, "kill_space_pirate_icon_tier5") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier2Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_void_tier_02") && getCollectionSlotValue(player, "kill_space_void_icon_tier2") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier3Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_void_tier_03") && getCollectionSlotValue(player, "kill_space_void_icon_tier3") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier4Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_void_tier_04") && getCollectionSlotValue(player, "kill_space_void_icon_tier4") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier5Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_void_tier_05") && getCollectionSlotValue(player, "kill_space_void_icon_tier5") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier2Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_rebel_tier_02") && getCollectionSlotValue(player, "kill_space_rebel_icon_tier2") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier3Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_rebel_tier_03") && getCollectionSlotValue(player, "kill_space_rebel_icon_tier3") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier4Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_rebel_tier_04") && getCollectionSlotValue(player, "kill_space_rebel_icon_tier4") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier5Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_rebel_tier_05") && getCollectionSlotValue(player, "kill_space_rebel_icon_tier5") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier2Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_imperial_tier_02") && getCollectionSlotValue(player, "kill_space_imperial_icon_tier2") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier3Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_imperial_tier_03") && getCollectionSlotValue(player, "kill_space_imperial_icon_tier3") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier4Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_imperial_tier_04") && getCollectionSlotValue(player, "kill_space_imperial_icon_tier4") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier5Broken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasCompletedCollection(player, "kill_space_imperial_tier_05") && getCollectionSlotValue(player, "kill_space_imperial_icon_tier5") == 0);
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isBlackSunBroken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier1Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier2Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier3Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier4Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier5Broken(player, npc));
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isRebelBroken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier1Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier2Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier3Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier4Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier5Broken(player, npc));
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isImperialBroken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier1Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier2Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier3Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier4Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier5Broken(player, npc));
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isDroidBroken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier1Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier2Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier3Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier4Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier5Broken(player, npc));
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isPirateBroken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier1Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier2Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier3Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier4Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier5Broken(player, npc));
    }
    public boolean collections_fixbrokenspacekillcollections_condition_isVoidBroken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier1Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier2Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier3Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier4Broken(player, npc) || collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier5Broken(player, npc));
    }
    public boolean collections_fixbrokenspacekillcollections_condition_nothingIsBroken(obj_id player, obj_id npc) throws InterruptedException
    {
        return (collections_fixbrokenspacekillcollections_condition_isBlackSunBroken(player, npc) || collections_fixbrokenspacekillcollections_condition_isDroidBroken(player, npc) || collections_fixbrokenspacekillcollections_condition_isPirateBroken(player, npc) || collections_fixbrokenspacekillcollections_condition_isVoidBroken(player, npc) || collections_fixbrokenspacekillcollections_condition_isRebelBroken(player, npc) || collections_fixbrokenspacekillcollections_condition_isImperialBroken(player, npc));
    }
    public void collections_fixbrokenspacekillcollections_action_fixBlackSunTier1Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_blacksun_tier_01");
    }
    public void collections_fixbrokenspacekillcollections_action_fixBlackSunTier2Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_blacksun_tier_02");
    }
    public void collections_fixbrokenspacekillcollections_action_fixBlackSunTier3Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_blacksun_tier_03");
    }
    public void collections_fixbrokenspacekillcollections_action_fixBlackSunTier4Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_blacksun_tier_04");
    }
    public void collections_fixbrokenspacekillcollections_action_fixBlackSunTier5Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_blacksun_tier_05");
    }
    public void collections_fixbrokenspacekillcollections_action_fixDroidTier1Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_droid_tier_01");
    }
    public void collections_fixbrokenspacekillcollections_action_fixDroidTier2Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_droid_tier_02");
    }
    public void collections_fixbrokenspacekillcollections_action_fixDroidTier3Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_droid_tier_03");
    }
    public void collections_fixbrokenspacekillcollections_action_fixDroidTier4Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_droid_tier_04");
    }
    public void collections_fixbrokenspacekillcollections_action_fixDroidTier5Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_droid_tier_05");
    }
    public void collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier1Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_pirate_tier_01");
    }
    public void collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier2Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_pirate_tier_02");
    }
    public void collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier3Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_pirate_tier_03");
    }
    public void collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier4Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_pirate_tier_04");
    }
    public void collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier5Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_pirate_tier_05");
    }
    public void collections_fixbrokenspacekillcollections_action_fixImperialTier1Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_imperial_tier_01");
    }
    public void collections_fixbrokenspacekillcollections_action_fixImperialTier2Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_imperial_tier_02");
    }
    public void collections_fixbrokenspacekillcollections_action_fixImperialTier3Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_imperial_tier_03");
    }
    public void collections_fixbrokenspacekillcollections_action_fixImperialTier4Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_imperial_tier_04");
    }
    public void collections_fixbrokenspacekillcollections_action_fixImperialTier5Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_imperial_tier_05");
    }
    public void collections_fixbrokenspacekillcollections_action_fixRebelTier1Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_rebel_tier_01");
    }
    public void collections_fixbrokenspacekillcollections_action_fixRebelTier2Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_rebel_tier_02");
    }
    public void collections_fixbrokenspacekillcollections_action_fixRebelTier3Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_rebel_tier_03");
    }
    public void collections_fixbrokenspacekillcollections_action_fixRebelTier4Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_rebel_tier_04");
    }
    public void collections_fixbrokenspacekillcollections_action_fixRebelTier5Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_rebel_tier_05");
    }
    public void collections_fixbrokenspacekillcollections_action_fixVoidWingTier1Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_void_tier_01");
    }
    public void collections_fixbrokenspacekillcollections_action_fixVoidWingTier2Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_void_tier_02");
    }
    public void collections_fixbrokenspacekillcollections_action_fixVoidWingTier3Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_void_tier_03");
    }
    public void collections_fixbrokenspacekillcollections_action_fixVoidWingTier4Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_void_tier_04");
    }
    public void collections_fixbrokenspacekillcollections_action_fixVoidWingTier5Collection(obj_id player, obj_id npc) throws InterruptedException
    {
        collection.revokeThenGrantCollection(player, "kill_space_void_tier_05");
    }
    public int collections_fixbrokenspacekillcollections_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_118"))
        {
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_fixbrokenspacekillcollections_condition_isRebelBroken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_fixbrokenspacekillcollections_condition_isImperialBroken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (collections_fixbrokenspacekillcollections_condition_isBlackSunBroken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (collections_fixbrokenspacekillcollections_condition_isDroidBroken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (collections_fixbrokenspacekillcollections_condition_isPirateBroken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (collections_fixbrokenspacekillcollections_condition_isVoidBroken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (!collections_fixbrokenspacekillcollections_condition_nothingIsBroken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_178");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_202");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_250");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_274");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    utils.setScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_113"))
        {
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_fixbrokenspacekillcollections_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_141");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier1Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier2Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier3Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier4Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (collections_fixbrokenspacekillcollections_condition_isRebelKillCollectionTier5Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_143");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_166");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_170");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_174");
                    }
                    utils.setScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_178"))
        {
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier1Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier2Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier3Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier4Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (collections_fixbrokenspacekillcollections_condition_isImperialKillCollectionTier5Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_194");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_198");
                    }
                    utils.setScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_202"))
        {
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier1Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier2Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier3Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier4Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (collections_fixbrokenspacekillcollections_condition_isBlackSunKillCollectionTier5Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_206");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_210");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_214");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_218");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_226"))
        {
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_228");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier1Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier2Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier3Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier4Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (collections_fixbrokenspacekillcollections_condition_isDroidKillCollectionTier5Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_230");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_246");
                    }
                    utils.setScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_250"))
        {
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_252");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier1Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier2Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier3Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier4Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (collections_fixbrokenspacekillcollections_condition_isHiddenDaggersKillCollectionTier5Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_254");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_258");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_262");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_266");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_270");
                    }
                    utils.setScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_274"))
        {
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_276");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier1Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier2Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier3Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier4Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (collections_fixbrokenspacekillcollections_condition_isVoidWingKillCollectionTier5Broken(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_278");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_282");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_286");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_290");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_294");
                    }
                    utils.setScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_297"))
        {
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_299");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_fixbrokenspacekillcollections_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_143"))
        {
            collections_fixbrokenspacekillcollections_action_fixRebelTier1Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                collections_fixbrokenspacekillcollections_action_fixRebelTier1Collection(player, npc);
                string_id message = new string_id(c_stringFile, "s_145");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_162"))
        {
            collections_fixbrokenspacekillcollections_action_fixRebelTier2Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_166"))
        {
            collections_fixbrokenspacekillcollections_action_fixRebelTier3Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_168");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_170"))
        {
            collections_fixbrokenspacekillcollections_action_fixRebelTier4Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_172");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_174"))
        {
            collections_fixbrokenspacekillcollections_action_fixRebelTier5Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_fixbrokenspacekillcollections_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_182"))
        {
            collections_fixbrokenspacekillcollections_action_fixImperialTier1Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_184");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_186"))
        {
            collections_fixbrokenspacekillcollections_action_fixImperialTier2Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_188");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_190"))
        {
            collections_fixbrokenspacekillcollections_action_fixImperialTier3Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_192");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_194"))
        {
            collections_fixbrokenspacekillcollections_action_fixImperialTier4Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_196");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_198"))
        {
            collections_fixbrokenspacekillcollections_action_fixImperialTier5Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_200");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_fixbrokenspacekillcollections_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            collections_fixbrokenspacekillcollections_action_fixBlackSunTier1Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_208");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_210"))
        {
            collections_fixbrokenspacekillcollections_action_fixBlackSunTier2Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_212");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_214"))
        {
            collections_fixbrokenspacekillcollections_action_fixBlackSunTier3Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_216");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_218"))
        {
            collections_fixbrokenspacekillcollections_action_fixBlackSunTier4Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_220");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_222"))
        {
            collections_fixbrokenspacekillcollections_action_fixBlackSunTier5Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_224");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_fixbrokenspacekillcollections_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_230"))
        {
            collections_fixbrokenspacekillcollections_action_fixDroidTier1Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_232");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_234"))
        {
            collections_fixbrokenspacekillcollections_action_fixDroidTier2Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_236");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_238"))
        {
            collections_fixbrokenspacekillcollections_action_fixDroidTier3Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_240");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_242"))
        {
            collections_fixbrokenspacekillcollections_action_fixDroidTier4Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_244");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_246"))
        {
            collections_fixbrokenspacekillcollections_action_fixDroidTier5Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_248");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_fixbrokenspacekillcollections_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_254"))
        {
            collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier1Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_256");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_258"))
        {
            collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier2Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_260");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_262"))
        {
            collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier3Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_264");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_266"))
        {
            collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier4Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_268");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_270"))
        {
            collections_fixbrokenspacekillcollections_action_fixHiddenDaggersTier5Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_272");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int collections_fixbrokenspacekillcollections_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_278"))
        {
            collections_fixbrokenspacekillcollections_action_fixVoidWingTier1Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_280");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_282"))
        {
            collections_fixbrokenspacekillcollections_action_fixVoidWingTier2Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_284");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_286"))
        {
            collections_fixbrokenspacekillcollections_action_fixVoidWingTier3Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_288");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_290"))
        {
            collections_fixbrokenspacekillcollections_action_fixVoidWingTier4Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_292");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_294"))
        {
            collections_fixbrokenspacekillcollections_action_fixVoidWingTier5Collection(player, npc);
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_296");
                utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
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
            detachScript(self, "conversation.collections_fixbrokenspacekillcollections");
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
        detachScript(self, "conversation.collections_fixbrokenspacekillcollections");
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
        if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (collections_fixbrokenspacekillcollections_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_113");
                }
                utils.setScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId", 1);
                npcStartConversation(player, npc, "collections_fixbrokenspacekillcollections", message, responses);
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
        if (!conversationId.equals("collections_fixbrokenspacekillcollections"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
        if (branchId == 1 && collections_fixbrokenspacekillcollections_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && collections_fixbrokenspacekillcollections_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && collections_fixbrokenspacekillcollections_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && collections_fixbrokenspacekillcollections_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && collections_fixbrokenspacekillcollections_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && collections_fixbrokenspacekillcollections_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && collections_fixbrokenspacekillcollections_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && collections_fixbrokenspacekillcollections_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.collections_fixbrokenspacekillcollections.branchId");
        return SCRIPT_CONTINUE;
    }
}
