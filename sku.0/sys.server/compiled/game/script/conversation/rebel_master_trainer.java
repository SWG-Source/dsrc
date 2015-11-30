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
import script.library.factions;
import script.library.prose;
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.static_item;
import script.library.utils;

public class rebel_master_trainer extends script.base_script
{
    public rebel_master_trainer()
    {
    }
    public static String c_stringFile = "conversation/rebel_master_trainer";
    public boolean rebel_master_trainer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean rebel_master_trainer_condition_isReadyforMasterRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_flags.isRebelPilot(player))
        {
            return false;
        }
        if (hasSkill(player, "pilot_rebel_navy_starships_04") && hasSkill(player, "pilot_rebel_navy_procedures_04") && hasSkill(player, "pilot_rebel_navy_weapons_04") && hasSkill(player, "pilot_rebel_navy_droid_04"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_isReadyforMasterPrivateer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_flags.isNeutralPilot(player))
        {
            return false;
        }
        if (hasSkill(player, "pilot_neutral_starships_04") && hasSkill(player, "pilot_neutral_procedures_04") && hasSkill(player, "pilot_neutral_weapons_04") && hasSkill(player, "pilot_neutral_droid_04"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_isImperailPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean rebel_master_trainer_condition_isRSFPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.isSpaceTrack(player, space_flags.PRIVATEER_NABOO))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_isWorkingForEmpire(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasSpaceFlag(player, "master_mission_imperial"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_hasWonMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "destroy", "master_rebel_1") && !space_quest.hasReceivedReward(player, "destroy", "master_rebel_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_hasWonMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "destroy", "master_rebel_2") && !space_quest.hasReceivedReward(player, "destroy", "master_rebel_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_hasFailedMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy", "master_rebel_1") || space_quest.hasAbortedQuest(player, "destroy", "master_rebel_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_hasFailedMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy", "master_rebel_2") || space_quest.hasAbortedQuest(player, "destroy", "master_rebel_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean rebel_master_trainer_condition_hasMasterSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_rebel_navy_master");
    }
    public boolean rebel_master_trainer_condition_isPrivateer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean rebel_master_trainer_condition_hasReceivedRewardOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "destroy", "master_rebel_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_hasOtherMasterSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_neutral_master");
    }
    public boolean rebel_master_trainer_condition_isCorsec(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.isSpaceTrack(player, space_flags.PRIVATEER_CORELLIA))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_isSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.isSpaceTrack(player, space_flags.PRIVATEER_TATOOINE))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_hasReveivedRewardNeut(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "destroy", "master_rebel_1") && hasSkill(player, "pilot_neutral_starships_04"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_isOnMasterQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "destroy", "master_rebel_1") || space_quest.hasQuest(player, "destroy", "master_rebel_2"));
    }
    public boolean rebel_master_trainer_condition_isNotReadyforRebelMaster(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_flags.isRebelPilot(player))
        {
            return false;
        }
        if (!hasSkill(player, "pilot_rebel_navy_starships_04") || !hasSkill(player, "pilot_rebel_navy_procedures_04") || !hasSkill(player, "pilot_rebel_navy_weapons_04") || !hasSkill(player, "pilot_rebel_navy_droid_04"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_isNotReadyforPrivateerMaster(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_flags.isNeutralPilot(player))
        {
            return false;
        }
        if (!hasSkill(player, "pilot_neutral_starships_04") || !hasSkill(player, "pilot_neutral_procedures_04") || !hasSkill(player, "pilot_neutral_weapons_04") || !hasSkill(player, "pilot_neutral_droid_04"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean rebel_master_trainer_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean rebel_master_trainer_condition_hasRebelMasterNoMedal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "master_rebel_medal_recieved"))
        {
            return false;
        }
        return hasSkill(player, "pilot_rebel_navy_master");
    }
    public boolean rebel_master_trainer_condition_hasNeutralMasterNoMedal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "master_neutral_medal_recieved"))
        {
            return false;
        }
        return hasSkill(player, "pilot_neutral_master");
    }
    public void rebel_master_trainer_action_setWorkingForRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "master_mission_rebel", true);
    }
    public void rebel_master_trainer_action_grantMasterSkillBoxRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_rebel_navy_master");
    }
    public void rebel_master_trainer_action_grantMasterSkillBoxNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_neutral_master");
    }
    public void rebel_master_trainer_action_giveRewardOneRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy", "master_rebel_1"))
        {
            faceTo(npc, player);
            space_quest.giveReward(player, "destroy", "master_rebel_1", 25000, "object/tangible/wearables/jacket/jacket_ace_rebel.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 200.0f);
        }
    }
    public void rebel_master_trainer_action_giveRewardTwoRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy", "master_rebel_2"))
        {
            faceTo(npc, player);
            space_quest.giveReward(player, "destroy", "master_rebel_2", 50000, "object/tangible/wearables/helmet/helmet_fighter_rebel_ace.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 400.0f);
            if (getGender(player) == GENDER_MALE)
            {
                if (getSpecies(player) == SPECIES_ITHORIAN)
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/ith_necklace_ace_pilot_rebel_m.iff", player);
                    setBioLink(medal, player);
                }
                else if (getSpecies(player) == SPECIES_WOOKIEE)
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_ace_pilot_rebel_wke_m.iff", player);
                    setBioLink(medal, player);
                }
                else 
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_ace_pilot_rebel_m.iff", player);
                    setBioLink(medal, player);
                }
            }
            else 
            {
                if (getSpecies(player) == SPECIES_ITHORIAN)
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/ith_necklace_ace_pilot_rebel_f.iff", player);
                    setBioLink(medal, player);
                }
                else if (getSpecies(player) == SPECIES_WOOKIEE)
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_trando_ace_pilot_rebel_f.iff", player);
                    setBioLink(medal, player);
                }
                else 
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_trando_ace_pilot_rebel_f.iff", player);
                    setBioLink(medal, player);
                }
            }
            space_flags.setSpaceFlag(player, "master_pilot_medal_recieved", true);
        }
    }
    public void rebel_master_trainer_action_grantMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "master_rebel_1");
    }
    public void rebel_master_trainer_action_grantMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "master_rebel_2");
    }
    public void rebel_master_trainer_action_giveRewardOnePriv(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy", "master_rebel_1"))
        {
            faceTo(npc, player);
            space_quest.giveReward(player, "destroy", "master_rebel_1", 25000, "object/tangible/wearables/jacket/jacket_ace_privateer.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 200.0f);
        }
    }
    public void rebel_master_trainer_action_giveRewardTwoPriv(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy", "master_rebel_2"))
        {
            faceTo(npc, player);
            space_quest.giveReward(player, "destroy", "master_rebel_2", 50000, "object/tangible/wearables/helmet/helmet_fighter_privateer_ace.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 400.0f);
            if (getGender(player) == GENDER_MALE)
            {
                if (getSpecies(player) == SPECIES_ITHORIAN)
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/ith_necklace_ace_pilot_neutral_m.iff", player);
                    setBioLink(medal, player);
                }
                else if (getSpecies(player) == SPECIES_WOOKIEE)
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_ace_pilot_neutral_wke_m.iff", player);
                    setBioLink(medal, player);
                }
                else 
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_ace_pilot_neutral_m.iff", player);
                    setBioLink(medal, player);
                }
            }
            else 
            {
                if (getSpecies(player) == SPECIES_ITHORIAN)
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/ith_necklace_ace_pilot_neutral_f.iff", player);
                    setBioLink(medal, player);
                }
                else if (getSpecies(player) == SPECIES_WOOKIEE)
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_trando_ace_pilot_neutral_f.iff", player);
                    setBioLink(medal, player);
                }
                else 
                {
                    obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_trando_ace_pilot_neutral_f.iff", player);
                    setBioLink(medal, player);
                }
            }
            space_flags.setSpaceFlag(player, "master_pilot_medal_recieved", true);
        }
    }
    public void rebel_master_trainer_action_grantMedalNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "master_neutral_medal_recieved", 1);
        obj_id item = static_item.createNewItemFunction("item_quest_reward_neutral_pilot_medal_01_01", player);
        string_id name = new string_id("static_item_n", "item_quest_reward_neutral_pilot_medal_01_01");
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("quest/ground/system_message", "placed_in_inventory"));
        pp = prose.setTO(pp, name);
        sendQuestSystemMessage(player, pp);
    }
    public void rebel_master_trainer_action_grantMedalRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "master_rebel_medal_recieved", 1);
        obj_id item = static_item.createNewItemFunction("item_quest_reward_rebel_pilot_medal_01_01", player);
        string_id name = new string_id("static_item_n", "item_quest_reward_rebel_pilot_medal_01_01");
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("quest/ground/system_message", "placed_in_inventory"));
        pp = prose.setTO(pp, name);
        sendQuestSystemMessage(player, pp);
    }
    public String rebel_master_trainer_tokenTO_tokenTO0001(obj_id player, obj_id npc) throws InterruptedException
    {
        return new String();
    }
    public int rebel_master_trainer_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a667be99"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_5fa0c79");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
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
    public int rebel_master_trainer_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d4d47c04"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_1c113900");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce45643c");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_eb02d65"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_fe42648c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c13f9a11");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ce45643c"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                rebel_master_trainer_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_becf889");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e374b3bf");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e374b3bf"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "snap_finger2");
                string_id message = new string_id(c_stringFile, "s_a2b72d37");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
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
    public int rebel_master_trainer_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c13f9a11"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                rebel_master_trainer_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_8c56411b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f799f9ec");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f799f9ec"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_3924b90e");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4c9055c9"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_77a163bd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e228ef89");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ed30fff8"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a79ccb10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c788dadf");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e228ef89"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58559f7e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46ecd1ec");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46ecd1ec"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                rebel_master_trainer_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_39516a4");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c788dadf"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                rebel_master_trainer_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_cab1f8c7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b6b25f13");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b6b25f13"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1ffdcc4b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87d4bfc2");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_87d4bfc2"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_5bdc459b");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1da9fe53"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_d09ace00");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f6a8711");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 29);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
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
    public int rebel_master_trainer_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3f6a8711"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_3ccc33fb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d7967cc");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8d7967cc"))
        {
            if (rebel_master_trainer_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e87785ac");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5869e64b");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (rebel_master_trainer_condition_isPrivateer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_583fa6fb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_499");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5869e64b"))
        {
            rebel_master_trainer_action_giveRewardTwoRebel(player, npc);
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                rebel_master_trainer_action_grantMasterSkillBoxRebel(player, npc);
                string_id message = new string_id(c_stringFile, "s_28d79ee3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c67e8169");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c67e8169"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ef3cc459");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
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
    public int rebel_master_trainer_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_499"))
        {
            rebel_master_trainer_action_giveRewardTwoPriv(player, npc);
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                rebel_master_trainer_action_grantMasterSkillBoxNeutral(player, npc);
                string_id message = new string_id(c_stringFile, "s_6c42736d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_377d1a96");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_377d1a96"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_be631237");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
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
    public int rebel_master_trainer_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d02bfc3b"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c4d08624");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f00f629f");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f00f629f"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_575c9eb2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7cbd5de5");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7cbd5de5"))
        {
            if (rebel_master_trainer_condition_isPrivateer(player, npc))
            {
                rebel_master_trainer_action_giveRewardOnePriv(player, npc);
                string_id message = new string_id(c_stringFile, "s_6ff4c6e1");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                rebel_master_trainer_action_giveRewardOneRebel(player, npc);
                string_id message = new string_id(c_stringFile, "s_7e8aa338");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c68b1dd3"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_837c0670");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca1a4aa4");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ffeb2f4c"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_429ad94c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca1a4aa4");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ca1a4aa4"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_74cca111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1c7c6c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a6fb8def");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c1c7c6c"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_826f20e2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7a052bd2");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a6fb8def"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_938d38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7a052bd2");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7a052bd2"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9e5b1d54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cba67818");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cba67818"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_da1503ff");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bd6bf77");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5bd6bf77"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                rebel_master_trainer_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_17652085");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7a052bd2"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9e5b1d54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cba67818");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ca1a4aa4"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_74cca111");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1c7c6c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a6fb8def");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9a0798b3"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_36256b98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37157452");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89f9ea03");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_81f189ef"))
        {
            doAnimationAction(player, "salute2");
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_677c6a2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ad709e77");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37157452"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7660776e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44bd3318");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_89f9ea03"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_6f27454c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_559");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44bd3318"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_7bf46fb2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32062da9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c9d51f4");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32062da9"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_5919485e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85b8ffbd");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4c9d51f4"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_69741346");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
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
    public int rebel_master_trainer_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85b8ffbd"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_1668a97b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22d87576");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22d87576"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86a40b9e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_834a890e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c97cb0b8");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_834a890e"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e0c18ed4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47e9fbfd");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c97cb0b8"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_a21308bb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d976791");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47e9fbfd"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_ff2c163e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d976791");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6d976791"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_63214db1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fd64638");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1fd64638"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_875c631e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2cbd4ffa");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2cbd4ffa"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                rebel_master_trainer_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3e83f2ea");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6d976791"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_63214db1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fd64638");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_559"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_7bf46fb2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32062da9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c9d51f4");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ad709e77"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_7bf46fb2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32062da9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c9d51f4");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2acaca5d"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_133c89f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26bd9f23");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24a07bd4");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dc2fa6d8"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_d7e71a00");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e8d6a9e7");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26bd9f23"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3f7dd52e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52917b0d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e7f9b26f");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_24a07bd4"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "snap_finger2");
                string_id message = new string_id(c_stringFile, "s_a7be18a9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78ce5241");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52917b0d"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_da2cf079");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8f3f7918");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e7f9b26f"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_ecd272f1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8f3f7918");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8f3f7918"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_16531693");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8964c03a");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8964c03a"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c5b25047");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_174c76a5");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_174c76a5"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_65a12a87");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c10e0254");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35c0388d");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c10e0254"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_4bdfa49b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_579");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35c0388d"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4ebc69dd");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
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
    public int rebel_master_trainer_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_579"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                rebel_master_trainer_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_581");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76d5f218");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76d5f218"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_6daab4e1");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8f3f7918"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_16531693");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8964c03a");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch79(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78ce5241"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3f7dd52e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52917b0d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e7f9b26f");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e8d6a9e7"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_d8da45e7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aa01a084");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ee795f66");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 81);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_aa01a084"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_684ff0a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3d93239d");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ee795f66"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_84b47be3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3d93239d");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3d93239d"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_6be5ab4e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ad6ffe");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92ad6ffe"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_cd70bed3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_602");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_602"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_77cd7306");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_605");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch85(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_605"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_efc8c4f5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_647683fc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d646c11");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 86);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_647683fc"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                rebel_master_trainer_action_setWorkingForRebel(player, npc);
                string_id message = new string_id(c_stringFile, "s_609");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_611");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d646c11"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_618");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
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
    public int rebel_master_trainer_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_611"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                rebel_master_trainer_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_613");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d1111dc8");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d1111dc8"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_d41a131");
                utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int rebel_master_trainer_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3d93239d"))
        {
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_6be5ab4e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92ad6ffe");
                    }
                    utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.rebel_master_trainer");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_REBEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_REBEL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.rebel_master_trainer");
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
        if (rebel_master_trainer_condition_isWorkingForEmpire(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_dc822762");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_isImperailPilot(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_d6d7f7f9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_isRSFPilot(player, npc))
        {
            doAnimationAction(npc, "slow_down");
            string_id message = new string_id(c_stringFile, "s_f2bb94ef");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_isNotReadyforRebelMaster(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_efd059ff");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a667be99");
                }
                utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 4);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "rebel_master_trainer", null, pp, responses);
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
        if (rebel_master_trainer_condition_isNotReadyforPrivateerMaster(player, npc))
        {
            doAnimationAction(npc, "slow_down");
            string_id message = new string_id(c_stringFile, "s_7cd01fd0");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_hasRebelMasterNoMedal(player, npc))
        {
            doAnimationAction(npc, "salute1");
            rebel_master_trainer_action_grantMedalRebel(player, npc);
            string_id message = new string_id(c_stringFile, "s_449");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_hasNeutralMasterNoMedal(player, npc))
        {
            doAnimationAction(npc, "salute1");
            rebel_master_trainer_action_grantMedalNeutral(player, npc);
            string_id message = new string_id(c_stringFile, "s_168");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_hasOtherMasterSkill(player, npc))
        {
            doAnimationAction(npc, "point_to_self");
            string_id message = new string_id(c_stringFile, "s_98d92abb");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_hasMasterSkill(player, npc))
        {
            doAnimationAction(npc, "point_to_self");
            string_id message = new string_id(c_stringFile, "s_bfaef3d8");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_isOnMasterQuest(player, npc))
        {
            doAnimationAction(npc, "point_away");
            string_id message = new string_id(c_stringFile, "s_21d9c824");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_isOnQuest(player, npc))
        {
            doAnimationAction(npc, "point_away");
            string_id message = new string_id(c_stringFile, "s_fd815e48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_hasFailedMissionTwo(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_7826431d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d4d47c04");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_eb02d65");
                }
                utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 13);
                npcStartConversation(player, npc, "rebel_master_trainer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_hasFailedMissionOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7f725bea");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4c9055c9");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ed30fff8");
                }
                utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 20);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "rebel_master_trainer", null, pp, responses);
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
        if (rebel_master_trainer_condition_hasWonMissionTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e3714e1d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1da9fe53");
                }
                utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 28);
                npcStartConversation(player, npc, "rebel_master_trainer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_hasWonMissionOne(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_c78b33ad");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d02bfc3b");
                }
                utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 37);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "rebel_master_trainer", null, pp, responses);
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
        if (rebel_master_trainer_condition_hasReceivedRewardOne(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_1c9d33ed");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c68b1dd3");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ffeb2f4c");
                }
                utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 42);
                npcStartConversation(player, npc, "rebel_master_trainer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (rebel_master_trainer_condition_isReadyforMasterRebel(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_46b27b0c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rebel_master_trainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9a0798b3");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_81f189ef");
                }
                utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 51);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "rebel_master_trainer", null, pp, responses);
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
        if (rebel_master_trainer_condition_isReadyforMasterPrivateer(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_41910783");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rebel_master_trainer_condition_isSmuggler(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rebel_master_trainer_condition_isCorsec(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2acaca5d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dc2fa6d8");
                }
                utils.setScriptVar(player, "conversation.rebel_master_trainer.branchId", 67);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "rebel_master_trainer", null, pp, responses);
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
        if (rebel_master_trainer_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "slow_down");
            string_id message = new string_id(c_stringFile, "s_c2eca562");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("rebel_master_trainer"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.rebel_master_trainer.branchId");
        if (branchId == 4 && rebel_master_trainer_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && rebel_master_trainer_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && rebel_master_trainer_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && rebel_master_trainer_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && rebel_master_trainer_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && rebel_master_trainer_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && rebel_master_trainer_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && rebel_master_trainer_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && rebel_master_trainer_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && rebel_master_trainer_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && rebel_master_trainer_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && rebel_master_trainer_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && rebel_master_trainer_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && rebel_master_trainer_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && rebel_master_trainer_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && rebel_master_trainer_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && rebel_master_trainer_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && rebel_master_trainer_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && rebel_master_trainer_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && rebel_master_trainer_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && rebel_master_trainer_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && rebel_master_trainer_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && rebel_master_trainer_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && rebel_master_trainer_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && rebel_master_trainer_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && rebel_master_trainer_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && rebel_master_trainer_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && rebel_master_trainer_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && rebel_master_trainer_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && rebel_master_trainer_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && rebel_master_trainer_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && rebel_master_trainer_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && rebel_master_trainer_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && rebel_master_trainer_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && rebel_master_trainer_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && rebel_master_trainer_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && rebel_master_trainer_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && rebel_master_trainer_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && rebel_master_trainer_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && rebel_master_trainer_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && rebel_master_trainer_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && rebel_master_trainer_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && rebel_master_trainer_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && rebel_master_trainer_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && rebel_master_trainer_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && rebel_master_trainer_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && rebel_master_trainer_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && rebel_master_trainer_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && rebel_master_trainer_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && rebel_master_trainer_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && rebel_master_trainer_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && rebel_master_trainer_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && rebel_master_trainer_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && rebel_master_trainer_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 79 && rebel_master_trainer_handleBranch79(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && rebel_master_trainer_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && rebel_master_trainer_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && rebel_master_trainer_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && rebel_master_trainer_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && rebel_master_trainer_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 85 && rebel_master_trainer_handleBranch85(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && rebel_master_trainer_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && rebel_master_trainer_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && rebel_master_trainer_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && rebel_master_trainer_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.rebel_master_trainer.branchId");
        return SCRIPT_CONTINUE;
    }
}
