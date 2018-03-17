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
import script.library.features;
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_skill;
import script.library.utils;

public class generic_newbie_pilot_info_emperors_retreat extends script.base_script
{
    public generic_newbie_pilot_info_emperors_retreat()
    {
    }
    public static String c_stringFile = "conversation/generic_newbie_pilot_info_emperors_retreat";
    public boolean generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean generic_newbie_pilot_info_emperors_retreat_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metNewbiePilot"));
    }
    public boolean generic_newbie_pilot_info_emperors_retreat_condition_hasWonAccessQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((space_flags.isSpaceTrack(player, space_flags.IMPERIAL_NABOO) || space_flags.isSpaceTrack(player, space_flags.PRIVATEER_NABOO)) && space_quest.hasWonQuest(player, "destroy_surpriseattack", "naboo_station_emperors_access_quest_6"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean generic_newbie_pilot_info_emperors_retreat_condition_hasSpaceAccess(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "space_access_imperial");
    }
    public boolean generic_newbie_pilot_info_emperors_retreat_condition_isImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean generic_newbie_pilot_info_emperors_retreat_condition_isRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean generic_newbie_pilot_info_emperors_retreat_condition_isPrivateerPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_neutral_novice");
    }
    public boolean generic_newbie_pilot_info_emperors_retreat_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean generic_newbie_pilot_info_emperors_retreat_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasShip(player));
    }
    public void generic_newbie_pilot_info_emperors_retreat_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metNewbiePilot", true);
    }
    public void generic_newbie_pilot_info_emperors_retreat_action_grantAccess(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "space_access_imperial", 34);
        space_quest.giveReward(player, "space_battle", "naboo_station_emperors_access_quest_1", 10000);
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1ad77c93"))
        {
            doAnimationAction(player, "embarrassed");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_407efa57");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f184aba");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c39cfcc5");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7630ee0a"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_6f34494e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0d78fed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3cc53bb1");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9045a59e"))
        {
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                generic_newbie_pilot_info_emperors_retreat_action_grantAccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_9bcb9771");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6403a01d");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f184aba"))
        {
            doAnimationAction(player, "taken_aback");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                generic_newbie_pilot_info_emperors_retreat_action_grantAccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_cbd17b72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4ed6cafa");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c39cfcc5"))
        {
            doAnimationAction(player, "refuse_offer_affection");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                generic_newbie_pilot_info_emperors_retreat_action_grantAccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_29db34e3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7d0bf8d");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4ed6cafa"))
        {
            doAnimationAction(player, "applause_excited");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_a4069638");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7d0bf8d"))
        {
            doAnimationAction(player, "embarrassed");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_b2fccb6d");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d0d78fed"))
        {
            doAnimationAction(player, "shakefist");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "squirm");
                string_id message = new string_id(c_stringFile, "s_1f041825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_421dc0c2");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3cc53bb1"))
        {
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                generic_newbie_pilot_info_emperors_retreat_action_grantAccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_9dd0fe3d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_421dc0c2"))
        {
            doAnimationAction(player, "backhand_threaten");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_d89d15b4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_745f3194");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_745f3194"))
        {
            doAnimationAction(player, "wave_finger_warning");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                generic_newbie_pilot_info_emperors_retreat_action_grantAccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_b7fac16c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aede40a6");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_aede40a6"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e1fb0b6a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de1e771");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_de1e771"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_8054081f");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_33");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "salute2");
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6403a01d"))
        {
            doAnimationAction(player, "thumb_up");
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_8c3b7af1");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_742f54a5"))
        {
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_27fddd0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6adec6b0");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ca992d2c"))
        {
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_c8f2f3db");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_emperors_retreat_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6adec6b0"))
        {
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_4d15b266");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
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
            detachScript(self, "conversation.generic_newbie_pilot_info_emperors_retreat");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Imperial Pilot");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Imperial Pilot");
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
        detachScript(self, "conversation.generic_newbie_pilot_info_emperors_retreat");
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
        if (generic_newbie_pilot_info_emperors_retreat_condition_hasSpaceAccess(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            string_id message = new string_id(c_stringFile, "s_c6cd8614");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (generic_newbie_pilot_info_emperors_retreat_condition_hasWonAccessQuest(player, npc))
        {
            doAnimationAction(npc, "salute2");
            string_id message = new string_id(c_stringFile, "s_421b07a8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1ad77c93");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7630ee0a");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9045a59e");
                }
                utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 2);
                npcStartConversation(player, npc, "generic_newbie_pilot_info_emperors_retreat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_1093ddb2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (generic_newbie_pilot_info_emperors_retreat_condition_hasSpaceShip(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (generic_newbie_pilot_info_emperors_retreat_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_742f54a5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ca992d2c");
                }
                utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId", 19);
                npcStartConversation(player, npc, "generic_newbie_pilot_info_emperors_retreat", message, responses);
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
        if (!conversationId.equals("generic_newbie_pilot_info_emperors_retreat"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
        if (branchId == 2 && generic_newbie_pilot_info_emperors_retreat_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && generic_newbie_pilot_info_emperors_retreat_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && generic_newbie_pilot_info_emperors_retreat_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && generic_newbie_pilot_info_emperors_retreat_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && generic_newbie_pilot_info_emperors_retreat_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && generic_newbie_pilot_info_emperors_retreat_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && generic_newbie_pilot_info_emperors_retreat_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && generic_newbie_pilot_info_emperors_retreat_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && generic_newbie_pilot_info_emperors_retreat_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && generic_newbie_pilot_info_emperors_retreat_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && generic_newbie_pilot_info_emperors_retreat_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && generic_newbie_pilot_info_emperors_retreat_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && generic_newbie_pilot_info_emperors_retreat_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && generic_newbie_pilot_info_emperors_retreat_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_emperors_retreat.branchId");
        return SCRIPT_CONTINUE;
    }
}
