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
import script.library.utils;

public class sean_trenwell extends script.base_script
{
    public sean_trenwell()
    {
    }
    public static String c_stringFile = "conversation/sean_trenwell";
    public boolean sean_trenwell_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean sean_trenwell_condition_RivalObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (sean_trenwell_condition_votedSeanCurrentElection(player, npc))
        {
            return false;
        }
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.rival"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.rival");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_NoRoom(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.sean_noroom"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.sean_noroom");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_inOffice_newElection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            if (!hasObjVar(player, "bestine.votedSean") && !hasObjVar(player, "bestine.votedVictor"))
            {
                return true;
            }
            else 
            {
                int currentElection = getIntObjVar(npc, "bestine.electionStarted");
                int electionVotedIn = 0;
                if (hasObjVar(player, "bestine.votedSean"))
                {
                    electionVotedIn = getIntObjVar(player, "bestine.votedSean");
                }
                if (hasObjVar(player, "bestine.votedVictor"))
                {
                    electionVotedIn = getIntObjVar(player, "bestine.votedVictor");
                }
                if (electionVotedIn != currentElection)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_checkForVoteReward(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = 1;
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionEnded");
        }
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionStarted") - 1;
        }
        if (hasObjVar(player, "bestine.votedSean"))
        {
            int electionPlayerIsIn = getIntObjVar(player, "bestine.votedSean");
            if (electionPlayerIsIn <= electionNum)
            {
                return true;
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_Campaign(obj_id player, obj_id npc) throws InterruptedException
    {
        if (sean_trenwell_condition_votedSeanCurrentElection(player, npc))
        {
            return false;
        }
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.campaign"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.campaign");
                if (electionPlayerIsIn >= electionNum)
                {
                    if (!hasObjVar(player, "bestine.sean_noroom"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_NegquestsObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (sean_trenwell_condition_votedSeanCurrentElection(player, npc))
        {
            return false;
        }
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.negativeq"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.negativeq");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_Evidence1(obj_id player, obj_id npc) throws InterruptedException
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
                    if (strItemTemplate.equals("object/tangible/loot/quest/victor_questn_hlist.iff"))
                    {
                        destroyObject(objContents[intI]);
                        return true;
                    }
                    if (strItemTemplate.equals("object/tangible/loot/quest/victor_questn_dseal.iff"))
                    {
                        destroyObject(objContents[intI]);
                        return true;
                    }
                    if (strItemTemplate.equals("object/tangible/loot/quest/victor_questn_journal.iff"))
                    {
                        destroyObject(objContents[intI]);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_Itemfound(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.itemfound"))
        {
            if (hasObjVar(npc, "bestine.electionWinner"))
            {
                String winner = getStringObjVar(npc, "bestine.electionWinner");
                if ((winner.equals("sean")) || (winner.equals("Sean")))
                {
                    if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_history_disk.iff"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_Diskdestroyed(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "bestine.destroyed");
    }
    public boolean sean_trenwell_condition_Hutt(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "bestine.hutt");
    }
    public boolean sean_trenwell_condition_Mess1(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "bestine.mess");
    }
    public boolean sean_trenwell_condition_find1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.find"))
        {
            if (hasObjVar(npc, "bestine.electionWinner"))
            {
                String winner = getStringObjVar(npc, "bestine.electionWinner");
                if ((winner.equals("sean")) || (winner.equals("Sean")))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_hquestObj(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "bestine.hquest");
    }
    public boolean sean_trenwell_condition_notInOffice_noElection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionEnded"))
        {
            if (hasObjVar(npc, "bestine.electionWinner"))
            {
                String winner = getStringObjVar(npc, "bestine.electionWinner");
                if ((!winner.equals("sean")) && (!winner.equals("Sean")))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_inOffice(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionWinner"))
        {
            String winner = getStringObjVar(npc, "bestine.electionWinner");
            if ((winner.equals("sean")) || (winner.equals("Sean")))
            {
                return true;
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean sean_trenwell_condition_CheckRewardGiven(obj_id player, obj_id npc) throws InterruptedException
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
        if (hasObjVar(player, "bestine.rewardgivensean"))
        {
            int electionPlayerRewarded = getIntObjVar(player, "bestine.rewardgivensean");
            if (electionPlayerRewarded <= electionNum)
            {
                return true;
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_hasEvidence(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questn_hlist.iff"))
        {
            return true;
        }
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questn_dseal.iff"))
        {
            return true;
        }
        if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/victor_questn_journal.iff"))
        {
            return true;
        }
        return false;
    }
    public boolean sean_trenwell_condition_hasNoRoomHistoryReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "bestine.sean_historyreward_noroom");
    }
    public boolean sean_trenwell_condition_votedSeanCurrentElection(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.votedSean"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.votedSean");
                if (electionPlayerIsIn == electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_tellAboutHistoryQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "bestine.hquest"))
        {
            if (!hasObjVar(player, "bestine.find"))
            {
                if (!hasObjVar(player, "bestine.itemfound"))
                {
                    if (!hasObjVar(player, "bestine.mess"))
                    {
                        if (!hasObjVar(player, "bestine.destroyed"))
                        {
                            if (!hasObjVar(player, "bestine.sean_historyreward_noroom"))
                            {
                                if (!hasObjVar(player, "bestine.completedSeanHistroyQuest"))
                                {
                                    return true;
                                }
                                else 
                                {
                                    int playerQuestNum = getIntObjVar(player, "bestine.completedSeanHistroyQuest");
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
                    }
                }
            }
        }
        return false;
    }
    public boolean sean_trenwell_condition_votedVictorCurrentElection(obj_id player, obj_id npc) throws InterruptedException
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
    public void sean_trenwell_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void sean_trenwell_action_giveDiskandJoinCampaign(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.campaign", electionNum);
        setObjVar(player, "bestine.opponent", electionNum);
        if (hasObjVar(player, "bestine.rival"))
        {
            removeObjVar(player, "bestine.rival");
        }
        if (hasObjVar(player, "bestine.camp"))
        {
            removeObjVar(player, "bestine.camp");
        }
        if (hasObjVar(player, "bestine.negquests"))
        {
            removeObjVar(player, "bestine.negquests");
        }
        if (hasObjVar(player, "bestine.sean_noroom"))
        {
            removeObjVar(player, "bestine.sean_noroom");
        }
        if (hasObjVar(player, "bestine.searched"))
        {
            removeObjVar(player, "bestine.searched");
        }
        String DISK = "object/tangible/loot/quest/sean_campaign_disk.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(DISK, playerInv, "");
            }
        }
        return;
    }
    public void sean_trenwell_action_NQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.negativeq", electionNum);
        removeObjVar(player, "bestine.rival");
        if (hasObjVar(player, "bestine.searched"))
        {
            removeObjVar(player, "bestine.searched");
        }
        if (hasObjVar(player, "bestine.already_searched"))
        {
            removeObjVar(player, "bestine.already_searched");
        }
    }
    public void sean_trenwell_action_NoRoomObj(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = getIntObjVar(npc, "bestine.electionStarted");
        setObjVar(player, "bestine.sean_noroom", electionNum);
    }
    public void sean_trenwell_action_noRoomForHistoryReward(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.sean_historyreward_noroom", 1);
        removeObjVar(player, "bestine.destroyed");
    }
    public void sean_trenwell_action_GiveMainReward(obj_id player, obj_id npc) throws InterruptedException
    {
        int electionNum = 1;
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionStarted") - 1;
        }
        else if (hasObjVar(npc, "bestine.electionEnded"))
        {
            electionNum = getIntObjVar(npc, "bestine.electionEnded");
        }
        String commonReward = "object/tangible/furniture/all/bestine_quest_statue.iff";
        String mediumReward = "object/tangible/furniture/modern/bestine_quest_rug.iff";
        String rareReward = "object/tangible/painting/bestine_quest_painting.iff";
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
                    setObjVar(player, "bestine.rewardgivensean", electionNum);
                    removeObjVar(player, "bestine.votedSean");
                    return;
                }
            }
        }
        return;
    }
    public void sean_trenwell_action_Hquest(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.hquest", true);
        if (hasObjVar(player, "bestine.completedSeanHistroyQuest"))
        {
            removeObjVar(player, "bestine.completedSeanHistroyQuest");
        }
        location site = new location(-758, 10, -3907);
        obj_id waypoint = createWaypointInDatapad(player, site);
        setWaypointName(waypoint, "Historical Site");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        setObjVar(player, "bestine.historianWaypoint", waypoint);
    }
    public void sean_trenwell_action_mess(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.mess", true);
        removeObjVar(player, "bestine.itemfound");
        location contact = new location(-1448, 10, -3765);
        obj_id waypoint = createWaypointInDatapad(player, contact);
        setWaypointName(waypoint, "Sean's Contact");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        setObjVar(player, "bestine.contactWaypoint", waypoint);
    }
    public void sean_trenwell_action_GiveHistoryReward(obj_id player, obj_id npc) throws InterruptedException
    {
        String REWARD = "object/tangible/painting/bestine_history_quest_painting.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(REWARD, playerInv, "");
                if (isIdValid(item))
                {
                    if (hasObjVar(player, "bestine.destroyed"))
                    {
                        removeObjVar(player, "bestine.destroyed");
                    }
                    if (hasObjVar(player, "bestine.sean_historyreward_noroom"))
                    {
                        removeObjVar(player, "bestine.sean_historyreward_noroom");
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
                    setObjVar(player, "bestine.completedSeanHistroyQuest", currentQuestNum);
                    return;
                }
            }
        }
        return;
    }
    public void sean_trenwell_action_removeNoroomObjVar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.sean_noroom"))
        {
            removeObjVar(player, "bestine.sean_noroom");
        }
    }
    public void sean_trenwell_action_resetMessWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "bestine.contactWaypoint"))
        {
            location contact = new location(-1448, 10, -3765);
            obj_id waypoint = createWaypointInDatapad(player, contact);
            setWaypointName(waypoint, "Sean's Contact");
            setWaypointColor(waypoint, "blue");
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
            setObjVar(player, "bestine.contactWaypoint", waypoint);
        }
    }
    public void sean_trenwell_action_removeHquest(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "bestine.hquest");
        if (hasObjVar(player, "bestine.historianWaypoint"))
        {
            obj_id waypoint = getObjIdObjVar(player, "bestine.historianWaypoint");
            if (isIdValid(waypoint))
            {
                destroyWaypointInDatapad(waypoint, player);
            }
            removeObjVar(player, "bestine.historianWaypoint");
        }
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.sean_trenwell");
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
        detachScript(self, "npc.conversation.sean_trenwell");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (sean_trenwell_condition_Hutt(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_2810bcdc");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_trenwell_condition_NoRoom(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_17a8ddce");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ff7aeed7");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5ef87080");
                }
                setObjVar(player, "conversation.sean_trenwell.branchId", 2);
                npcStartConversation(player, self, "sean_trenwell", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_trenwell_condition_Campaign(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_47a2ec4d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d1f385de");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e7ffff2");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8b63bfe1");
                }
                setObjVar(player, "conversation.sean_trenwell.branchId", 6);
                npcStartConversation(player, self, "sean_trenwell", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_trenwell_condition_NegquestsObj(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_181e49f6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_trenwell_condition_hasEvidence(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_338ea1e3");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da9a29e9");
                }
                setObjVar(player, "conversation.sean_trenwell.branchId", 11);
                npcStartConversation(player, self, "sean_trenwell", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_trenwell_condition_RivalObj(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_68788cf5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82e87c1c");
                }
                setObjVar(player, "conversation.sean_trenwell.branchId", 17);
                npcStartConversation(player, self, "sean_trenwell", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_trenwell_condition_inOffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_7f70fe38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_trenwell_condition_hasNoRoomHistoryReward(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_trenwell_condition_Diskdestroyed(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (sean_trenwell_condition_Mess1(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (sean_trenwell_condition_Itemfound(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (sean_trenwell_condition_find1(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (sean_trenwell_condition_hquestObj(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (sean_trenwell_condition_tellAboutHistoryQuest(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            boolean hasResponse7 = false;
            if (sean_trenwell_condition_votedSeanCurrentElection(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse7 = true;
            }
            boolean hasResponse8 = false;
            if (sean_trenwell_condition_inOffice_newElection(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse8 = true;
            }
            boolean hasResponse9 = false;
            if (sean_trenwell_condition_checkForVoteReward(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse9 = true;
            }
            boolean hasResponse10 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse10 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9fa98668");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a3734170");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49368633");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_238ef4f6");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a068d8f2");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_35bc21ba");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6a0bba8c");
                }
                if (hasResponse7)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_811e4ed1");
                }
                if (hasResponse8)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3ab76f84");
                }
                if (hasResponse9)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7eabd120");
                }
                if (hasResponse10)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_81f9db80");
                }
                setObjVar(player, "conversation.sean_trenwell.branchId", 22);
                npcStartConversation(player, self, "sean_trenwell", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_trenwell_condition_votedSeanCurrentElection(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_64c62370");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_trenwell_condition_votedVictorCurrentElection(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_4c2afce6");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_trenwell_condition_notInOffice_noElection(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_6718926b");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_trenwell_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_52a14d49");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (sean_trenwell_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6ba0df1d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1ee4d2a");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d6a9a15d");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bab1f93a");
                }
                setObjVar(player, "conversation.sean_trenwell.branchId", 53);
                npcStartConversation(player, self, "sean_trenwell", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("sean_trenwell"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.sean_trenwell.branchId");
        if (branchId == 2 && response.equals("s_ff7aeed7"))
        {
            if (!sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_giveDiskandJoinCampaign(player, self);
                string_id message = new string_id(c_stringFile, "s_51c418fd");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (sean_trenwell_condition_noInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8784f1db");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You were here before, weren't you? About joining my campaign?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_5ef87080"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                sean_trenwell_action_removeNoroomObjVar(player, self);
                string_id message = new string_id(c_stringFile, "s_ee8c0e93");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You were here before, weren't you? About joining my campaign?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_d1f385de"))
        {
            if (sean_trenwell_condition_checkForVoteReward(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7fa991c4");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!sean_trenwell_condition_checkForVoteReward(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_70b3691e");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's nice to see you again! How did the voting go?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_e7ffff2"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a17fdb9a");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's nice to see you again! How did the voting go?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_8b63bfe1"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bb6b1f4b");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's nice to see you again! How did the voting go?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_338ea1e3"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a0d033e3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_641d98d5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fe8dd35a");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ahh, welcome. I remember sending you out to find dirt on that no-good Victor. Well, did you find any?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_da9a29e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_aa0406b");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ahh, welcome. I remember sending you out to find dirt on that no-good Victor. Well, did you find any?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_641d98d5"))
        {
            if (!sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_giveDiskandJoinCampaign(player, self);
                string_id message = new string_id(c_stringFile, "s_51c418fd");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_NoRoomObj(player, self);
                string_id message = new string_id(c_stringFile, "s_9adf6cf2");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'This is excellent! I can't believe he would be so daft to do such things! You can now join my campaign. That is what you wanted... right?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_fe8dd35a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a4a25b0f");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'This is excellent! I can't believe he would be so daft to do such things! You can now join my campaign. That is what you wanted... right?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_b826b85a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_67295aa1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a8b339fd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e5ba263");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm sorry to see that another one of Bestine's citizens prefers Defense over Commerce. I'm sure he tricked you into joining his campaign.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_82e87c1c"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_bad1d1bb");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm sorry to see that another one of Bestine's citizens prefers Defense over Commerce. I'm sure he tricked you into joining his campaign.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_a8b339fd"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                sean_trenwell_action_NQuests(player, self);
                string_id message = new string_id(c_stringFile, "s_2bc6a2f8");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's not so easy. You can't just join my campaign with the drop of a hat. To prove yourself, you'll need to dig up some dirt on that no-good Victor. Speak with my secretary for all the details. What do you think?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_1e5ba263"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_82b273a8");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's not so easy. You can't just join my campaign with the drop of a hat. To prove yourself, you'll need to dig up some dirt on that no-good Victor. Speak with my secretary for all the details. What do you think?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_9fa98668"))
        {
            if (sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_noRoomForHistoryReward(player, self);
                string_id message = new string_id(c_stringFile, "s_64f4dd40");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_GiveHistoryReward(player, self);
                string_id message = new string_id(c_stringFile, "s_b893398e");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_a3734170"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7fc36aab");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a907268e");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_49368633"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5ae436c2");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_238ef4f6"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2e38174d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b30bd73c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8a36ac7");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_a068d8f2"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ca646df4");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_35bc21ba"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d349c913");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059c36e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3ba540c");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_6a0bba8c"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_dab387e8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4ff8cd88");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_811e4ed1"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f1b007b2");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_3ab76f84"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c6d1b21");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ba0df1d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ee4d2a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6a9a15d");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bab1f93a");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_7eabd120"))
        {
            if (sean_trenwell_condition_CheckRewardGiven(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c27445f0");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_GiveMainReward(player, self);
                string_id message = new string_id(c_stringFile, "s_f81b7edd");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (sean_trenwell_condition_noInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_88b2f5bd");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && response.equals("s_81f9db80"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c1c018c3");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Now that I'm in office, I can finally set some of my ideas into action. The marketplace could definitely use some attention, and I should make sure everything at the museum is going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_a907268e"))
        {
            if (sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_noRoomForHistoryReward(player, self);
                string_id message = new string_id(c_stringFile, "s_2e05a27f");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_GiveHistoryReward(player, self);
                string_id message = new string_id(c_stringFile, "s_bc42a527");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'And has the disk been dealt with?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_b30bd73c"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9d0c2f9e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81a382e3");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well returned! Did you find anything?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_c8a36ac7"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2d1057e6");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well returned! Did you find anything?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && response.equals("s_81a382e3"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8dc34668");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fcf4d778");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What did you find? Can I see it? Does it mention my ancestor? Let me have it, please.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_fcf4d778"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                sean_trenwell_action_mess(player, self);
                string_id message = new string_id(c_stringFile, "s_5caf6cfd");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh no. This isn't good. If the governor finds out that I'm related to such a person, she may just kick me out of office. Here, take it back. Tell you what. If you find some way to destroy it, I'll give you something. Just... get rid of it!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && response.equals("s_1059c36e"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f47244aa");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'See if you can find anything of value. Did you speak to my historian? Speak to her when you get to the site.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && response.equals("s_3ba540c"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                sean_trenwell_action_removeHquest(player, self);
                string_id message = new string_id(c_stringFile, "s_75491d63");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'See if you can find anything of value. Did you speak to my historian? Speak to her when you get to the site.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && response.equals("s_4ff8cd88"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d485fca4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9a679e7f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e35bbba");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Indeed I do! History is a passion of mine, especially since my ancestors were among the original settlers of Bestine. I've gotten enough funding to hire a historian. We made an exciting new discovery just last week! A terrible desert storm came through and kicked up loose sand and beneath was a wrecked ship. We think it may be the Red-Sin Valon, the vessel used to transport the early settlers to Tatooine. It's so exciting!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && response.equals("s_9a679e7f"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_61503fc8");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes. Anyway, lately we've experienced brutal attacks from the Sand People. My historian is having a hard time of it, and I'm not sure her efforts can continue. She doesn't possess a brawler's spirit or the skill of a marksman. She's just a scholar. It's depressing to think that our attempts to restore the Red-Sin Valon have been fruitless. Do you think you can help? You seem like an adventurer. Or am I wrong?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && response.equals("s_1e35bbba"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a5cd533e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b45b3794");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ac8d04c");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes. Anyway, lately we've experienced brutal attacks from the Sand People. My historian is having a hard time of it, and I'm not sure her efforts can continue. She doesn't possess a brawler's spirit or the skill of a marksman. She's just a scholar. It's depressing to think that our attempts to restore the Red-Sin Valon have been fruitless. Do you think you can help? You seem like an adventurer. Or am I wrong?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && response.equals("s_b45b3794"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                sean_trenwell_action_Hquest(player, self);
                string_id message = new string_id(c_stringFile, "s_78b31931");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Just my luck! I could really use your help. The Red-Sin Valon find is phenomenal in of itself, but what if there's more? I want you to bring back any information you can. There must be some sort of log of the events surrounding the early settlement attempts. I'd really like for you to find it. Do you think you can do that?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && response.equals("s_2ac8d04c"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_dc13d9c7");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Just my luck! I could really use your help. The Red-Sin Valon find is phenomenal in of itself, but what if there's more? I want you to bring back any information you can. There must be some sort of log of the events surrounding the early settlement attempts. I'd really like for you to find it. Do you think you can do that?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && response.equals("s_6ba0df1d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3a06f79e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's correct and so far our efforts seem to be going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && response.equals("s_1ee4d2a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_df1ac18d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ba0df1d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6a9a15d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bab1f93a");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's correct and so far our efforts seem to be going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && response.equals("s_d6a9a15d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a7627a7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's correct and so far our efforts seem to be going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && response.equals("s_bab1f93a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91cc7d0");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That's correct and so far our efforts seem to be going well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && response.equals("s_6ba0df1d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3a06f79e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All this talk about the Tuskens, I can't-Oh! I didn't notice that you had entered my office. Welcome! It definitely is a horrible day outside, unbearably hot indeed. How may I help you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && response.equals("s_1ee4d2a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_df1ac18d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ba0df1d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6a9a15d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bab1f93a");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All this talk about the Tuskens, I can't-Oh! I didn't notice that you had entered my office. Welcome! It definitely is a horrible day outside, unbearably hot indeed. How may I help you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && response.equals("s_d6a9a15d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a7627a7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All this talk about the Tuskens, I can't-Oh! I didn't notice that you had entered my office. Welcome! It definitely is a horrible day outside, unbearably hot indeed. How may I help you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && response.equals("s_bab1f93a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91cc7d0");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'All this talk about the Tuskens, I can't-Oh! I didn't notice that you had entered my office. Welcome! It definitely is a horrible day outside, unbearably hot indeed. How may I help you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && response.equals("s_540ac7e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8bdcb5a3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2b0fbc2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6a9a15d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bab1f93a");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am humbled by your request. Currently, Bestine's markets are inadequate. I have already completed the construction of one market place. If I win this election, I'll put my efforts into attracting new merchants to these areas to help Bestine's economy.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && response.equals("s_f2b0fbc2"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_df1ac18d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ba0df1d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6a9a15d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bab1f93a");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Additionally, I plan to hire historians to uncover the history of Bestine. Artisans will definitely appreciate the new renovations done to the museum. It will give them a chance to place their creations on display.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && response.equals("s_d6a9a15d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d15b93e5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Additionally, I plan to hire historians to uncover the history of Bestine. Artisans will definitely appreciate the new renovations done to the museum. It will give them a chance to place their creations on display.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && response.equals("s_bab1f93a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91cc7d0");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Additionally, I plan to hire historians to uncover the history of Bestine. Artisans will definitely appreciate the new renovations done to the museum. It will give them a chance to place their creations on display.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && response.equals("s_6ba0df1d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_928992d9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Tuskens... My rival is centered on the slight possibility that they might launch an attack against Bestine. I find that highly doubtful. We have enough Troopers walking around as it is and they can easily defend us if needed. The Tuskens aren't brave or stupid enough to come near Bestine. We need to concentrate on the economy by introducing more markets!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && response.equals("s_d6a9a15d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d15b93e5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Tuskens... My rival is centered on the slight possibility that they might launch an attack against Bestine. I find that highly doubtful. We have enough Troopers walking around as it is and they can easily defend us if needed. The Tuskens aren't brave or stupid enough to come near Bestine. We need to concentrate on the economy by introducing more markets!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && response.equals("s_bab1f93a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91cc7d0");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Tuskens... My rival is centered on the slight possibility that they might launch an attack against Bestine. I find that highly doubtful. We have enough Troopers walking around as it is and they can easily defend us if needed. The Tuskens aren't brave or stupid enough to come near Bestine. We need to concentrate on the economy by introducing more markets!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && response.equals("s_540ac7e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91135631");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6a9a15d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bab1f93a");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am humbled by your request. Currently, Bestine's markets are inadequate. I have already completed the construction of one market place. If I win this election, I'll put my efforts into attracting new merchants to these areas to help Bestine's economy. Perhaps it will bring about new schematics for our crafters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && response.equals("s_d6a9a15d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d15b93e5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I plan to feature the culture of this area. I intend to hire historians to uncover the history of Bestine. I would also like to see more funding for the museum so that we can bring more artwork to our city. This would give artisans a chance to place their creations on display and share them with a larger audience.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && response.equals("s_bab1f93a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91cc7d0");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I plan to feature the culture of this area. I intend to hire historians to uncover the history of Bestine. I would also like to see more funding for the museum so that we can bring more artwork to our city. This would give artisans a chance to place their creations on display and share them with a larger audience.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && response.equals("s_540ac7e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30eb7833");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ef067f71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c46daeb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dea56128");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent news! How does the process go - Oh! You should let the governor, Tour Aryon, know of any evidence of a good deed I have done for the people of Bestine. Take it to her! I have done much for the people of Bestine, so I have confidence that our governor will be impressed. Of course, if you decide I am not the right candidate to represent you, you can always join my rival's campaign instead. Though I can't imagine how that would be possible.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && response.equals("s_540ac7e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30eb7833");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ef067f71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c46daeb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dea56128");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent news! How does the process go - Oh! You should let the governor, Tour Aryon, know of any evidence of a good deed I have done for the people of Bestine. Take it to her! I have done much for the people of Bestine, so I have confidence that our governor will be impressed. Of course, if you decide I am not the right candidate to represent you, you can always join my rival's campaign instead. Though I can't imagine how that would be possible.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && response.equals("s_540ac7e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30eb7833");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ef067f71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c46daeb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dea56128");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent news! How does the process go - Oh! You should let the governor, Tour Aryon, know of any evidence of a good deed I have done for the people of Bestine. Take it to her! I have done much for the people of Bestine, so I have confidence that our governor will be impressed. Of course, if you decide I am not the right candidate to represent you, you can always join my rival's campaign instead. Though I can't imagine how that would be possible.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && response.equals("s_6ba0df1d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_928992d9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Tuskens... My rival is centered on the slight possibility that they might launch an attack against Bestine. I find that highly doubtful. We have enough Troopers walking around as it is and they can easily defend us if needed. The Tuskens aren't brave or stupid enough to come near Bestine. We need to concentrate on the economy by introducing more markets!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && response.equals("s_d6a9a15d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d15b93e5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Tuskens... My rival is centered on the slight possibility that they might launch an attack against Bestine. I find that highly doubtful. We have enough Troopers walking around as it is and they can easily defend us if needed. The Tuskens aren't brave or stupid enough to come near Bestine. We need to concentrate on the economy by introducing more markets!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && response.equals("s_bab1f93a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91cc7d0");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The Tuskens... My rival is centered on the slight possibility that they might launch an attack against Bestine. I find that highly doubtful. We have enough Troopers walking around as it is and they can easily defend us if needed. The Tuskens aren't brave or stupid enough to come near Bestine. We need to concentrate on the economy by introducing more markets!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && response.equals("s_540ac7e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8bdcb5a3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6a9a15d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bab1f93a");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am humbled by your request. Currently, Bestine's markets are inadequate. I have already completed the construction of one market place. If I win this election, I'll put my efforts into attracting new merchants to these areas to help Bestine's economy. Perhaps it will bring about new schematics for our crafters.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && response.equals("s_d6a9a15d"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d15b93e5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.sean_trenwell.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Additionally, I plan to hire historians to uncover the history of Bestine. Artisans will definitely appreciate the new renovations done to the museum. It will give them a chance to place their creations on display.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && response.equals("s_bab1f93a"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91cc7d0");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Additionally, I plan to hire historians to uncover the history of Bestine. Artisans will definitely appreciate the new renovations done to the museum. It will give them a chance to place their creations on display.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && response.equals("s_540ac7e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30eb7833");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ef067f71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c46daeb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dea56128");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent news! How does the process go - Oh! You should let the governor, Tour Aryon, know of any evidence of a good deed I have done for the people of Bestine. Take it to her! I have done much for the people of Bestine, so I have confidence that our governor will be impressed. Of course, if you decide I am not the right candidate to represent you, you can always join my rival's campaign instead. Though I can't imagine how that would be possible.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && response.equals("s_540ac7e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30eb7833");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ef067f71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c46daeb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dea56128");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent news! How does the process go - Oh! You should let the governor, Tour Aryon, know of any evidence of a good deed I have done for the people of Bestine. Take it to her! I have done much for the people of Bestine, so I have confidence that our governor will be impressed. Of course, if you decide I am not the right candidate to represent you, you can always join my rival's campaign instead. Though I can't imagine how that would be possible.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && response.equals("s_540ac7e9"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_30eb7833");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (sean_trenwell_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ef067f71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c46daeb");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dea56128");
                    }
                    setObjVar(player, "conversation.sean_trenwell.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sean_trenwell.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent news! How does the process go... Oh! You should let the governor, Tour Aryon, know of any evidence of a good deed I have done for the people of Bestine. Take it to her! I have done much for the people of Bestine, so I have confidence that our governor will be impressed. Of course, if you decide I am not the right candidate to represent you, you can always join my rival's campaign instead. Though I can't imagine how that would be possible.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && response.equals("s_ef067f71"))
        {
            if (!sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_giveDiskandJoinCampaign(player, self);
                string_id message = new string_id(c_stringFile, "s_51c418fd");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (sean_trenwell_condition_noInventorySpace(player, self))
            {
                sean_trenwell_action_NoRoomObj(player, self);
                string_id message = new string_id(c_stringFile, "s_9adf6cf2");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'After which, if you discover his twisted ideas aren't for you... and I won't be surprised when you do... you'll need to bring back some sort of negative tidbit about him in order for you to be let back into my campaign. Do you get it? I'll just give you a disk to further explain the details.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && response.equals("s_5c46daeb"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_483c9216");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'After which, if you discover his twisted ideas aren't for you... and I won't be surprised when you do... you'll need to bring back some sort of negative tidbit about him in order for you to be let back into my campaign. Do you get it? I'll just give you a disk to further explain the details.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && response.equals("s_dea56128"))
        {
            if (sean_trenwell_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91cc7d0");
                removeObjVar(player, "conversation.sean_trenwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'After which, if you discover his twisted ideas aren't for you... and I won't be surprised when you do... you'll need to bring back some sort of negative tidbit about him in order for you to be let back into my campaign. Do you get it? I'll just give you a disk to further explain the details.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.sean_trenwell.branchId");
        return SCRIPT_CONTINUE;
    }
}
