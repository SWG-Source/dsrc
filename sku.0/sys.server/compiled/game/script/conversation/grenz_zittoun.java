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
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class grenz_zittoun extends script.base_script
{
    public grenz_zittoun()
    {
    }
    public static String c_stringFile = "conversation/grenz_zittoun";
    public boolean grenz_zittoun_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean grenz_zittoun_condition_isElligibleHideoutBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_boss_1") && groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_hideout");
    }
    public boolean grenz_zittoun_condition_hasReturnedHideoutBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isTaskActive(player, "u16_nym_themepark_pirate_boss_1", "returnKillHideoutBossComplete");
    }
    public boolean grenz_zittoun_condition_hasHideoutBossNotComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isQuestActive(player, "u16_nym_themepark_pirate_boss_1");
    }
    public boolean grenz_zittoun_condition_isElligibleMineBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_mine_boss") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_boss_1") && groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_mine");
    }
    public boolean grenz_zittoun_condition_isElligibleLabBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_lab_boss") && groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_research_facility") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_mine_boss");
    }
    public boolean grenz_zittoun_condition_hasReturnedMineBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isTaskActive(player, "u16_nym_themepark_mine_boss", "returnKillMineBossComplete");
    }
    public boolean grenz_zittoun_condition_hasReturnedLabBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isTaskActive(player, "u16_nym_themepark_lab_boss", "returnKillLabBossComplete");
    }
    public boolean grenz_zittoun_condition_hasMineBossNotComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isQuestActive(player, "u16_nym_themepark_mine_boss");
    }
    public boolean grenz_zittoun_condition_hasLabBossNotComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return groundquests.isQuestActive(player, "u16_nym_themepark_lab_boss");
    }
    public boolean grenz_zittoun_condition_hasntDoneHideout(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_pirate_hideout");
    }
    public boolean grenz_zittoun_condition_hasntDoneMine(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_mine") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_hideout") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_boss_1");
    }
    public boolean grenz_zittoun_condition_hasntDoneLab(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActiveOrComplete(player, "u16_nym_themepark_research_facility") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_mine") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_mine_boss");
    }
    public boolean grenz_zittoun_condition_hasCompletedPirateBossCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollection(player, "kill_nyms_themepark_boss_hideout") && groundquests.isQuestActive(player, "u16_nym_themepark_pirate_boss_1");
    }
    public boolean grenz_zittoun_condition_hasCompletedMinerBossCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollection(player, "kill_nyms_themepark_boss_mine") && groundquests.isQuestActive(player, "u16_nym_themepark_mine_boss");
    }
    public boolean grenz_zittoun_condition_hasCompletedLabBossCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasCompletedCollection(player, "kill_nyms_themepark_boss_lab") && groundquests.isQuestActive(player, "u16_nym_themepark_lab_boss");
    }
    public boolean grenz_zittoun_condition_hasCompletedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "u16_nym_themepark_lab_boss");
    }
    public boolean grenz_zittoun_condition_hasIncompletePirateBossCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return !hasCompletedCollection(player, "kill_nyms_themepark_boss_hideout") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_pirate_boss_1");
    }
    public boolean grenz_zittoun_condition_hasIncompleteMinerBossCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return !hasCompletedCollection(player, "kill_nyms_themepark_boss_mine") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_mine_boss");
    }
    public boolean grenz_zittoun_condition_hasIncompleteLabBossCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        return !hasCompletedCollection(player, "kill_nyms_themepark_boss_lab") && groundquests.hasCompletedQuest(player, "u16_nym_themepark_lab_boss");
    }
    public void grenz_zittoun_action_grantHideoutBossQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "u16_nym_themepark_pirate_boss_1");
        if (!hasCompletedCollectionSlot(player, "kill_nyms_themepark_boss_hideout_activate"))
        {
            modifyCollectionSlotValue(player, "kill_nyms_themepark_boss_hideout_activate", 1);
        }
    }
    public void grenz_zittoun_action_completeHideoutBossQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasCompletedKillHideoutBoss");
        if (!hasCompletedCollectionSlot(player, "kill_nyms_themepark_boss_1"))
        {
            modifyCollectionSlotValue(player, "kill_nyms_themepark_boss_1", 1);
        }
    }
    public void grenz_zittoun_action_grantMineBossQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "u16_nym_themepark_mine_boss");
        if (!hasCompletedCollectionSlot(player, "kill_nyms_themepark_boss_mine_activate"))
        {
            modifyCollectionSlotValue(player, "kill_nyms_themepark_boss_mine_activate", 1);
        }
    }
    public void grenz_zittoun_action_completeMineBossQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasCompletedKillMineBoss");
        if (!hasCompletedCollectionSlot(player, "u16_nym_themepark_mine_boss"))
        {
            modifyCollectionSlotValue(player, "u16_nym_themepark_mine_boss", 1);
        }
    }
    public void grenz_zittoun_action_grantLabBossQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "u16_nym_themepark_lab_boss");
        if (!hasCompletedCollectionSlot(player, "kill_nyms_themepark_boss_lab_activate"))
        {
            modifyCollectionSlotValue(player, "kill_nyms_themepark_boss_lab_activate", 1);
        }
    }
    public void grenz_zittoun_action_completeLabBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasCompletedKillLabBoss");
        if (!hasCompletedCollectionSlot(player, "u16_nym_themepark_lab_boss"))
        {
            modifyCollectionSlotValue(player, "u16_nym_themepark_lab_boss", 1);
        }
    }
    public void grenz_zittoun_action_BruteForceCompletePirateBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        if (grenz_zittoun_condition_hasCompletedPirateBossCollection(player, npc))
        {
            if (groundquests.isQuestActive(player, "u16_nym_themepark_pirate_boss_1"))
            {
                CustomerServiceLog("nyms_themepark", "NPC Conversation - Brute Force completing quest: u16_nym_themepark_pirate_boss_1 for player: " + player + " so they do not remailn bugged.");
                int questid = questGetQuestId("quest/u16_nym_themepark_pirate_boss_1");
                if ((questid != 0) && questIsQuestActive(questid, player))
                {
                    questCompleteQuest(questid, player);
                }
            }
        }
    }
    public void grenz_zittoun_action_BruteForceCompleteMineBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        if (grenz_zittoun_condition_hasCompletedMinerBossCollection(player, npc))
        {
            if (groundquests.isQuestActive(player, "u16_nym_themepark_mine_boss"))
            {
                CustomerServiceLog("nyms_themepark", "NPC Conversation - Brute Force completing quest: u16_nym_themepark_mine_boss for player: " + player + " so they do not remailn bugged.");
                int questid = questGetQuestId("quest/u16_nym_themepark_mine_boss");
                if ((questid != 0) && questIsQuestActive(questid, player))
                {
                    questCompleteQuest(questid, player);
                }
            }
        }
    }
    public void grenz_zittoun_action_BruteForceCompleteLabBoss(obj_id player, obj_id npc) throws InterruptedException
    {
        if (grenz_zittoun_condition_hasCompletedLabBossCollection(player, npc))
        {
            if (groundquests.isQuestActive(player, "u16_nym_themepark_lab_boss"))
            {
                CustomerServiceLog("nyms_themepark", "NPC Conversation - Brute Force completing quest: u16_nym_themepark_lab_boss for player: " + player + " so they do not remailn bugged.");
                int questid = questGetQuestId("quest/u16_nym_themepark_lab_boss");
                if ((questid != 0) && questIsQuestActive(questid, player))
                {
                    questCompleteQuest(questid, player);
                }
            }
        }
    }
    public void grenz_zittoun_action_verifyAndCorrectErrors(obj_id player, obj_id npc) throws InterruptedException
    {
        if (grenz_zittoun_condition_hasCompletedLabBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompleteLabBoss(player, npc);
        }
        if (grenz_zittoun_condition_hasCompletedMinerBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompleteMineBoss(player, npc);
        }
        if (grenz_zittoun_condition_hasCompletedPirateBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompletePirateBoss(player, npc);
        }
        if (grenz_zittoun_condition_hasIncompleteLabBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompleteLabBossCollection(player, npc);
        }
        if (grenz_zittoun_condition_hasIncompleteMinerBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompleteMineBossCollection(player, npc);
        }
        if (grenz_zittoun_condition_hasIncompletePirateBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompletePirateBossCollection(player, npc);
        }
    }
    public void grenz_zittoun_action_BruteForceCompleteLabBossCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "kill_nyms_themepark_boss_lab_activate"))
        {
            modifyCollectionSlotValue(player, "kill_nyms_themepark_boss_4", 1);
            modifyCollectionSlotValue(player, "kill_nyms_themepark_boss_5", 1);
        }
    }
    public void grenz_zittoun_action_BruteForceCompleteMineBossCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "kill_nyms_themepark_boss_mine_activate"))
        {
            modifyCollectionSlotValue(player, "kill_nyms_themepark_boss_2", 1);
            modifyCollectionSlotValue(player, "kill_nyms_themepark_boss_3", 1);
        }
    }
    public void grenz_zittoun_action_BruteForceCompletePirateBossCollection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollectionSlot(player, "kill_nyms_themepark_boss_hideout_activate"))
        {
            modifyCollectionSlotValue(player, "kill_nyms_themepark_boss_1", 1);
        }
    }
    public String grenz_zittoun_tokenTO_firstName(obj_id player, obj_id npc) throws InterruptedException
    {
        return new String(getFirstName(player));
    }
    public int grenz_zittoun_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            doAnimationAction(player, "flex_biceps");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 3);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            doAnimationAction(player, "flex3");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "handshake_tandem");
                grenz_zittoun_action_completeLabBoss(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            doAnimationAction(player, "shake_head_no");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_67");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_69");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 11);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_70"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "elbow");
                string_id message = new string_id(c_stringFile, "s_71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            doAnimationAction(player, "belly_laugh");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "he_dies");
                string_id message = new string_id(c_stringFile, "s_73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 13);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                grenz_zittoun_action_grantLabBossQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_75"))
        {
            doAnimationAction(player, "yawn");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            doAnimationAction(player, "nod_head_once");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                grenz_zittoun_action_completeMineBossQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            doAnimationAction(player, "apologize");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87"))
        {
            doAnimationAction(player, "check_wrist_device");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 27);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                grenz_zittoun_action_grantMineBossQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow5");
                grenz_zittoun_action_completeHideoutBossQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            doAnimationAction(player, "tap_head");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_103");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_107"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_109");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_127"))
        {
            doAnimationAction(player, "check_wrist_device");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_131");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nervous");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 39);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "whisper");
                string_id message = new string_id(c_stringFile, "s_117");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_119"))
        {
            doAnimationAction(player, "cover_mouth");
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_123");
                    }
                    utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int grenz_zittoun_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_123"))
        {
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                grenz_zittoun_action_grantHideoutBossQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_125");
                utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.grenz_zittoun");
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
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.grenz_zittoun");
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
        if (grenz_zittoun_condition_hasCompletedAll(player, npc))
        {
            grenz_zittoun_action_verifyAndCorrectErrors(player, npc);
            string_id message = new string_id(c_stringFile, "s_139");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_hasReturnedLabBoss(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_81");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 2);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "grenz_zittoun", null, pp, responses);
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
        if (grenz_zittoun_condition_hasCompletedLabBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompleteLabBoss(player, npc);
            string_id message = new string_id(c_stringFile, "s_135");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_hasLabBossNotComplete(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_78");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 6);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "grenz_zittoun", null, pp, responses);
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
        if (grenz_zittoun_condition_hasIncompleteMinerBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompleteMineBossCollection(player, npc);
            string_id message = new string_id(c_stringFile, "s_141");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_isElligibleLabBoss(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_63");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 9);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "grenz_zittoun", null, pp, responses);
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
        if (grenz_zittoun_condition_hasntDoneLab(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_129");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_hasReturnedMineBoss(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_60");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 18);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "grenz_zittoun", null, pp, responses);
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
        if (grenz_zittoun_condition_hasCompletedMinerBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompleteMineBoss(player, npc);
            string_id message = new string_id(c_stringFile, "s_134");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_hasMineBossNotComplete(player, npc))
        {
            doAnimationAction(npc, "cough_polite");
            string_id message = new string_id(c_stringFile, "s_57");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 21);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcStartConversation(player, npc, "grenz_zittoun", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_hasIncompletePirateBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompletePirateBossCollection(player, npc);
            string_id message = new string_id(c_stringFile, "s_140");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_isElligibleMineBoss(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                }
                utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 24);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "grenz_zittoun", null, pp, responses);
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
        if (grenz_zittoun_condition_hasntDoneMine(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_128");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_hasReturnedHideoutBoss(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_92");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                }
                utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 32);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "grenz_zittoun", null, pp, responses);
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
        if (grenz_zittoun_condition_hasCompletedPirateBossCollection(player, npc))
        {
            grenz_zittoun_action_BruteForceCompletePirateBoss(player, npc);
            string_id message = new string_id(c_stringFile, "s_133");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_hasHideoutBossNotComplete(player, npc))
        {
            doAnimationAction(npc, "cough_polite");
            string_id message = new string_id(c_stringFile, "s_99");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                }
                utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 35);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "grenz_zittoun", null, pp, responses);
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
        if (grenz_zittoun_condition_isElligibleHideoutBoss(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_105");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (grenz_zittoun_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                }
                utils.setScriptVar(player, "conversation.grenz_zittoun.branchId", 37);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                npcStartConversation(player, npc, "grenz_zittoun", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition_hasntDoneHideout(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_136");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            pp.other.set(grenz_zittoun_tokenTO_firstName(player, npc));
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (grenz_zittoun_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            string_id message = new string_id(c_stringFile, "s_138");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("grenz_zittoun"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.grenz_zittoun.branchId");
        if (branchId == 2 && grenz_zittoun_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && grenz_zittoun_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && grenz_zittoun_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && grenz_zittoun_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && grenz_zittoun_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && grenz_zittoun_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && grenz_zittoun_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && grenz_zittoun_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && grenz_zittoun_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && grenz_zittoun_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && grenz_zittoun_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && grenz_zittoun_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && grenz_zittoun_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && grenz_zittoun_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && grenz_zittoun_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && grenz_zittoun_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && grenz_zittoun_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && grenz_zittoun_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && grenz_zittoun_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && grenz_zittoun_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && grenz_zittoun_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.grenz_zittoun.branchId");
        return SCRIPT_CONTINUE;
    }
}
