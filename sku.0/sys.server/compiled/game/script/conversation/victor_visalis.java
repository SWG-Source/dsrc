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
import script.library.utils;
import script.library.weapons;

public class victor_visalis extends script.base_script
{
    public victor_visalis()
    {
    }
    public static String c_stringFile = "conversation/victor_visalis";
    public boolean victor_visalis_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean victor_visalis_condition_CampObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (victor_visalis_condition_votedVictorCurrentElection(player, npc))
        {
            return false;
        }
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.camp"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.camp");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_OpponentObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (victor_visalis_condition_votedVictorCurrentElection(player, npc))
        {
            return false;
        }
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.opponent"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.opponent");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_votedVictorCurrentElection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.votedVictor"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.votedVictor");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_NoroomObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.victor_noroom"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.victor_noroom");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_voted(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = 1;
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionEnded");
        }
        else if (hasObjVar(npc, "bestine.electionStarted"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionStarted") - 1;
        }
        if (hasObjVar(player, "bestine.votedVictor"))
        {
            int electionPlayerIsIn = getIntObjVar(player, "bestine.votedVictor");
            if (electionPlayerIsIn <= electionNum)
            {
                return true;
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_hasEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questn_gpapers.iff"))
        {
            return true;
        }
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questn_tdisk.iff"))
        {
            return true;
        }
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questn_alog.iff"))
        {
            return true;
        }
        return false;
    }
    public boolean victor_visalis_condition_NegativeqsOBJ(obj_id player, obj_id npc) throws InterruptedException
    {
        if (victor_visalis_condition_votedVictorCurrentElection(player, npc))
        {
            return false;
        }
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negquests"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negquests");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_notInOffice_noElection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.electionWinner"))
            {
                String winner = getStringObjVar(npc, "bestine.electionWinner");
                if ((!winner.equals("victor")) && (!winner.equals("Victor")))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_InOffice_noElection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.electionWinner"))
            {
                String winner = getStringObjVar(npc, "bestine.electionWinner");
                if ((winner.equals("victor")) || (winner.equals("Victor")))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasNoInvRoom = false;
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space < 1)
            {
                hasNoInvRoom = true;
            }
        }
        return hasNoInvRoom;
    }
    public boolean victor_visalis_condition_newElectionStarted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            return true;
        }
        return false;
    }
    public boolean victor_visalis_condition_alreadyReceivedElectionReward(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = 1;
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionEnded");
        }
        else if (hasObjVar(npc, "bestine.electionStarted"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionStarted") - 1;
        }
        if (hasObjVar(player, "bestine.rewardgiven"))
        {
            int electionPlayerIsIn = getIntObjVar(player, "bestine.rewardgiven");
            if (electionPlayerIsIn <= electionNum)
            {
                return true;
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_votedSeanCurrentElection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.votedSean"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.votedSean");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_inOffice(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionWinner"))
        {
            String winner = getStringObjVar(npc, "bestine.electionWinner");
            if ((winner.equals("victor")) || (winner.equals("Victor")))
            {
                return true;
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_CheckOnTuskenQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.tuskenquest"))
        {
            if (hasObjVar(player, "bestine.tuskenquestdone"))
            {
                if (hasObjVar(player, "bestine.tuskengotreward"))
                {
                    return true;
                }
                else 
                {
                    int playerQuestNum = getIntObjVar(player, "bestine.tuskengotreward");
                    int currentQuestNum = 0;
                    if (hasObjVar(npc, "bestine.electionStarted"))
                    {
                        currentQuestNum = getIntObjVar(npc, "bestine.electionStarted") - 1;
                    }
                    else if (hasObjVar(npc, "bestine.electionEnded"))
                    {
                        currentQuestNum = getIntObjVar(npc, "bestine.electionEnded");
                    }
                    if (playerQuestNum < currentQuestNum)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_CompleteTuskenQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "bestine.tuskengotreward");
    }
    public boolean victor_visalis_condition_CheckforStones(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/carved_stone.iff"))
        {
            return true;
        }
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/smooth_stone.iff"))
        {
            return true;
        }
        return false;
    }
    public boolean victor_visalis_condition_CheckforTuskenHead(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/tusken_head.iff"))
        {
            if (hasObjVar(player, "bestine.tuskenquest"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_CheckForTuskeReward(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = 1;
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionEnded");
        }
        else if (hasObjVar(npc, "bestine.electionStarted"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionStarted") - 1;
        }
        if (hasObjVar(player, "bestine.tuskengotreward"))
        {
            int electionPlayerRewarded = getIntObjVar(player, "bestine.tuskengotreward");
            if (electionPlayerRewarded <= electionNum)
            {
                return true;
            }
        }
        return false;
    }
    public boolean victor_visalis_condition_ChkTuskenQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.tuskenquest"))
        {
            return true;
        }
        return false;
    }
    public void victor_visalis_action_OnTuskenQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.tuskengotreward"))
        {
            removeObjVar(player, "bestine.tuskengotreward");
        }
        setObjVar(player, "bestine.tuskenquest", true);
        location site = new location(-3960, 0, 6233);
        obj_id waypoint1 = createWaypointInDatapad(player, site);
        setWaypointName(waypoint1, "Fort Tusken");
        setWaypointColor(waypoint1, "blue");
        setWaypointVisible(waypoint1, true);
        setWaypointActive(waypoint1, true);
        setObjVar(player, "bestine.tuskenWaypoint", waypoint1);
        setObjVar(player, "bestine.tuskenquest", true);
    }
    public void victor_visalis_action_NoRoom(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.victor_noroom", true);
    }
    public void victor_visalis_action_WayPointTusken(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.tuskenWaypoint"))
        {
            obj_id waypoint = getObjIdObjVar(player, "bestine.tuskenWaypoint");
            if (isIdValid(waypoint))
            {
                return;
            }
            else 
            {
                setObjVar(player, "bestine.tuskenquest", true);
                location site = new location(-3960, 0, 6233);
                obj_id waypoint1 = createWaypointInDatapad(player, site);
                setWaypointName(waypoint1, "Fort Tusken");
                setWaypointColor(waypoint1, "blue");
                setWaypointVisible(waypoint1, true);
                setWaypointActive(waypoint1, true);
                setObjVar(player, "bestine.tuskenWaypoint", waypoint1);
                setObjVar(player, "bestine.tuskenquest", true);
            }
        }
    }
    public void victor_visalis_action_NegativeQs(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.negquests", electionNum);
        removeObjVar(player, "bestine.opponent");
        if (hasObjVar(player, "bestine.searched"))
        {
            removeObjVar(player, "bestine.searched");
        }
        if (hasObjVar(player, "bestine.already_searched"))
        {
            removeObjVar(player, "bestine.already_searched");
        }
    }
    public void victor_visalis_action_RemoveNeg(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "bestine.negquests");
    }
    public void victor_visalis_action_giveDiskAndJoinCampaign(obj_id player, obj_id npc) throws InterruptedException
    {
        victor_visalis_action_removeEvidence(player, npc);
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.camp", electionNum);
        setObjVar(player, "bestine.rival", electionNum);
        if (hasObjVar(player, "bestine.opponent"))
        {
            removeObjVar(player, "bestine.opponent");
        }
        if (hasObjVar(player, "bestine.campaign"))
        {
            removeObjVar(player, "bestine.campaign");
        }
        if (hasObjVar(player, "bestine.negquests"))
        {
            removeObjVar(player, "bestine.negquests");
        }
        if (hasObjVar(player, "bestine.victor_noroom"))
        {
            removeObjVar(player, "bestine.victor_noroom");
        }
        if (hasObjVar(player, "bestine.searched"))
        {
            removeObjVar(player, "bestine.searched");
        }
        if (hasObjVar(player, "bestine.already_searched"))
        {
            removeObjVar(player, "bestine.already_searched");
        }
        String CAMPAIGN = "object/tangible/loot/quest/victor_campaign_disk.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(CAMPAIGN, playerInv, "");
            }
        }
        return;
    }
    public void victor_visalis_action_Givereward(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = 1;
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionEnded");
        }
        else if (hasObjVar(npc, "bestine.electionStarted"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionStarted") - 1;
        }
        String commonReward = "object/tangible/wearables/necklace/bestine_quest_badge.iff";
        String mediumReward = "object/tangible/furniture/all/bestine_quest_imp_banner.iff";
        String rareReward = "object/weapon/melee/sword/bestine_quest_sword.iff";
        String reward = commonReward;
        int chance = rand(1, 5000);
        if (chance == 1)
        {
            reward = rareReward;
        }
        if ((chance >= 2) && (chance <= 2500))
        {
            reward = mediumReward;
        }
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(reward, playerInv, "");
                if (isIdValid(item))
                {
                    setObjVar(player, "bestine.rewardgiven", electionNum);
                    removeObjVar(player, "bestine.votedVictor");
                    return;
                }
            }
        }
        return;
    }
    public void victor_visalis_action_RemoveTuskenQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "bestine.tuskenquest");
        if (hasObjVar(player, "bestine.tuskenWaypoint"))
        {
            obj_id waypoint = getObjIdObjVar(player, "bestine.tuskenWaypoint");
            if (isIdValid(waypoint))
            {
                destroyWaypointInDatapad(waypoint, player);
            }
            removeObjVar(player, "bestine.tuskenquest");
        }
    }
    public void victor_visalis_action_GiveTuskenReward(obj_id player, obj_id npc) throws InterruptedException
    {
        String REWARD = "object/weapon/ranged/rifle/rifle_victor_tusken.iff";
        if (isIdValid(player))
        {
            obj_id objInventory = utils.getInventoryContainer(player);
            if (isIdValid(objInventory))
            {
                obj_id[] objContents = utils.getContents(objInventory);
                if (objContents != null)
                {
                    for (int intI = 0; intI < objContents.length; intI++)
                    {
                        if (isIdValid(objContents[intI]))
                        {
                            String strItemTemplate = getTemplateName(objContents[intI]);
                            if (strItemTemplate.equals("object/tangible/loot/quest/tusken_head.iff"))
                            {
                                destroyObject(objContents[intI]);
                                obj_id item = weapons.createWeapon(REWARD, objInventory, rand(0.8f, 1.1f));
                                if (hasObjVar(player, "bestine.tuskenquest"))
                                {
                                    removeObjVar(player, "bestine.tuskenquest");
                                }
                                if (hasObjVar(player, "bestine.tuskenquestdone"))
                                {
                                    removeObjVar(player, "bestine.tuskenquestdone");
                                }
                                int currentQuestNum = 0;
                                if (hasObjVar(npc, "bestine.electionStarted"))
                                {
                                    currentQuestNum = getIntObjVar(npc, "bestine.electionStarted") - 1;
                                }
                                else if (hasObjVar(npc, "bestine.electionEnded"))
                                {
                                    currentQuestNum = getIntObjVar(npc, "bestine.electionEnded");
                                }
                                setObjVar(player, "bestine.tuskengotreward", currentQuestNum);
                                return;
                            }
                        }
                    }
                }
            }
        }
        return;
    }
    public void victor_visalis_action_removeEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(player);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory);
            if (objContents != null)
            {
                for (int intI = 0; intI < objContents.length; intI++)
                {
                    String strItemTemplate = getTemplateName(objContents[intI]);
                    if (strItemTemplate.equals("object/tangible/loot/quest/sean_questn_gpapers.iff"))
                    {
                        destroyObject(objContents[intI]);
                        return;
                    }
                    if (strItemTemplate.equals("object/tangible/loot/quest/sean_questn_tdisk.iff"))
                    {
                        destroyObject(objContents[intI]);
                        return;
                    }
                    if (strItemTemplate.equals("object/tangible/loot/quest/sean_questn_alog.iff"))
                    {
                        destroyObject(objContents[intI]);
                        return;
                    }
                }
            }
        }
        return;
    }
    public int victor_visalis_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c82e9a2f"))
        {
            if (!victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_giveDiskAndJoinCampaign(player, npc);
                string_id message = new string_id(c_stringFile, "s_1b6edbfe");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_NoRoom(player, npc);
                string_id message = new string_id(c_stringFile, "s_a58217e0");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ee26e33e"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28586fd5");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_798f58f7"))
        {
            if (victor_visalis_condition_voted(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73f65b89");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c74fd908");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_700330a5"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41012fd2");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8999ec9b"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cf6147b3");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dea56128"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b826b85a"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                victor_visalis_action_NegativeQs(player, npc);
                string_id message = new string_id(c_stringFile, "s_e6a20ca5");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67dbab18"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6ac98e49"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c4e7b68");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9e0196ed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c46daeb");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_da9a29e9"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52a836a3");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9e0196ed"))
        {
            if (!victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_giveDiskAndJoinCampaign(player, npc);
                string_id message = new string_id(c_stringFile, "s_572eae57");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_NoRoom(player, npc);
                string_id message = new string_id(c_stringFile, "s_eafac099");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5c46daeb"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d987b9fe");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_edafb11b"))
        {
            if (victor_visalis_condition_ChkTuskenQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f594d753");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_CompleteTuskenQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ce06af62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!victor_visalis_condition_CheckforStones(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition_CheckforStones(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30e8118");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_380817dd");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!victor_visalis_condition_CheckOnTuskenQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6775ddea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_390b2857");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e9a49e3");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6641e79e"))
        {
            if (!victor_visalis_condition_CheckForTuskeReward(player, npc))
            {
                victor_visalis_action_GiveTuskenReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_df711673");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_CheckForTuskeReward(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81eb46c0");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42c394e0"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                victor_visalis_action_WayPointTusken(player, npc);
                string_id message = new string_id(c_stringFile, "s_269510f1");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cbb4f307"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                victor_visalis_action_WayPointTusken(player, npc);
                string_id message = new string_id(c_stringFile, "s_e955e18");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f70821a3"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                victor_visalis_action_RemoveTuskenQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_7ce36b97");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8d1a9cd2");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_82af0027"))
        {
            if (victor_visalis_condition_alreadyReceivedElectionReward(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24f8534b");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_noInventorySpace(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_adc1f0dc");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_Givereward(player, npc);
                string_id message = new string_id(c_stringFile, "s_cc9cab18");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_de1eacb3"))
        {
            if (victor_visalis_condition_votedVictorCurrentElection(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_578e3372");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_votedSeanCurrentElection(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5bccff58");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e8aff3cd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition_voted(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_435f07d4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bae6b22d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6a9a15d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a50f0d3");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87b97dc");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30e8118"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26684a50");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_380817dd"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1c1db559");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_390b2857"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113169f8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23e4ded2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_177e9cb7");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e9a49e3"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e52ed44");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_23e4ded2"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3516c87");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_177e9cb7"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                victor_visalis_action_OnTuskenQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_72a766a9");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_435f07d4"))
        {
            if (victor_visalis_condition_alreadyReceivedElectionReward(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24f8534b");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_noInventorySpace(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_adc1f0dc");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_Givereward(player, npc);
                string_id message = new string_id(c_stringFile, "s_cc9cab18");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bae6b22d"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70973a58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_540ac7e9");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d6a9a15d"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_146");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_148");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1a50f0d3"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_172");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87b97dc"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70973a58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_540ac7e9");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_144"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_146");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_148");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_170"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_172");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_208"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_210");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_540ac7e9"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_486664dd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1285849e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_140");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1285849e"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c34a7f30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_106"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dcd4d8d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_109");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_140"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_142");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6c1a2811");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59c941ec");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5492e753");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d041eb82");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9201d81b");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5492e753"))
        {
            if (!victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_giveDiskAndJoinCampaign(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0c249bb");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_NoRoom(player, npc);
                string_id message = new string_id(c_stringFile, "s_99");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d041eb82"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c135d871");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9201d81b"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_109"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fc5485d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a76ad142");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6cf7afee");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a76ad142"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6cf7afee"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_115"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_117");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_119"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_123");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_129");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_123"))
        {
            if (!victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_giveDiskAndJoinCampaign(player, npc);
                string_id message = new string_id(c_stringFile, "s_125");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_NoRoom(player, npc);
                string_id message = new string_id(c_stringFile, "s_127");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_129"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_131");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_133"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_135");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_148"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_150");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_152"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_154");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_166");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_156"))
        {
            if (!victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_giveDiskAndJoinCampaign(player, npc);
                string_id message = new string_id(c_stringFile, "s_158");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_NoRoom(player, npc);
                string_id message = new string_id(c_stringFile, "s_160");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_162"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_166"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_168");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_174"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_178");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_204");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_178"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_204"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_206");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_182"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_184");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_186"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_188");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (victor_visalis_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_196");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_200");
                    }
                    utils.setScriptVar(player, "conversation.victor_visalis.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int victor_visalis_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_190"))
        {
            if (!victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_giveDiskAndJoinCampaign(player, npc);
                string_id message = new string_id(c_stringFile, "s_192");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (victor_visalis_condition_noInventorySpace(player, npc))
            {
                victor_visalis_action_NoRoom(player, npc);
                string_id message = new string_id(c_stringFile, "s_194");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_196"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_198");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_200"))
        {
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_202");
                utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
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
            detachScript(self, "conversation.victor_visalis");
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
        detachScript(self, "conversation.victor_visalis");
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
        if (victor_visalis_condition_NoroomObj(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ca4e3819");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c82e9a2f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ee26e33e");
                }
                utils.setScriptVar(player, "conversation.victor_visalis.branchId", 1);
                npcStartConversation(player, npc, "victor_visalis", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_visalis_condition_CampObj(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c2b746c2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_798f58f7");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_700330a5");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8999ec9b");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dea56128");
                }
                utils.setScriptVar(player, "conversation.victor_visalis.branchId", 5);
                npcStartConversation(player, npc, "victor_visalis", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_visalis_condition_OpponentObj(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26e91e91");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b826b85a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67dbab18");
                }
                utils.setScriptVar(player, "conversation.victor_visalis.branchId", 11);
                npcStartConversation(player, npc, "victor_visalis", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_visalis_condition_NegativeqsOBJ(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4e29a0ab");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_visalis_condition_hasEvidence(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6ac98e49");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da9a29e9");
                }
                utils.setScriptVar(player, "conversation.victor_visalis.branchId", 14);
                npcStartConversation(player, npc, "victor_visalis", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_visalis_condition_inOffice(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a19e3981");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_visalis_condition_CheckforTuskenHead(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (victor_visalis_condition_ChkTuskenQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (victor_visalis_condition_ChkTuskenQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (victor_visalis_condition_ChkTuskenQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (victor_visalis_condition_CheckforStones(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (victor_visalis_condition_voted(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            boolean hasResponse7 = false;
            if (victor_visalis_condition_newElectionStarted(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse7 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_edafb11b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6641e79e");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42c394e0");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cbb4f307");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f70821a3");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82af0027");
                }
                if (hasResponse7)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_de1eacb3");
                }
                utils.setScriptVar(player, "conversation.victor_visalis.branchId", 20);
                npcStartConversation(player, npc, "victor_visalis", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (victor_visalis_condition_notInOffice_noElection(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_57218e96");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_visalis_condition_votedVictorCurrentElection(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_82dcbdca");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_visalis_condition_votedSeanCurrentElection(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_66e44ee");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (victor_visalis_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_aa1a9de6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (victor_visalis_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_144");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_170");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_208");
                }
                utils.setScriptVar(player, "conversation.victor_visalis.branchId", 46);
                npcStartConversation(player, npc, "victor_visalis", message, responses);
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
        if (!conversationId.equals("victor_visalis"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.victor_visalis.branchId");
        if (branchId == 1 && victor_visalis_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && victor_visalis_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && victor_visalis_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && victor_visalis_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && victor_visalis_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && victor_visalis_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && victor_visalis_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && victor_visalis_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && victor_visalis_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && victor_visalis_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && victor_visalis_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && victor_visalis_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && victor_visalis_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && victor_visalis_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && victor_visalis_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && victor_visalis_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && victor_visalis_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && victor_visalis_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && victor_visalis_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && victor_visalis_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && victor_visalis_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && victor_visalis_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && victor_visalis_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && victor_visalis_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && victor_visalis_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && victor_visalis_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && victor_visalis_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && victor_visalis_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && victor_visalis_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.victor_visalis.branchId");
        return SCRIPT_CONTINUE;
    }
}
