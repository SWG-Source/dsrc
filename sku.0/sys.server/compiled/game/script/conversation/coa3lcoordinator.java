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
import script.library.factions;
import script.library.utils;

public class coa3lcoordinator extends script.base_script
{
    public coa3lcoordinator()
    {
    }
    public static String c_stringFile = "conversation/coa3lcoordinator";
    public boolean coa3lcoordinator_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean coa3lcoordinator_condition_hasSpokeCoordinatorOnce(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == 101);
    }
    public boolean coa3lcoordinator_condition_hasSpokeInfoOfficer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == 102);
    }
    public boolean coa3lcoordinator_condition_sentToTactOfficer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == 301 || getIntObjVar(player, "coa3.convTracker") == 302);
    }
    public boolean coa3lcoordinator_condition_completedFloraStoryMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") == 303);
    }
    public boolean coa3lcoordinator_condition_hasOffworldWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getIntObjVar(player, "coa3.convTracker") > 400);
    }
    public boolean coa3lcoordinator_condition_playerHasDisk(obj_id player, obj_id npc) throws InterruptedException
    {
        if (getIntObjVar(player, "coa3.convTracker") > 205 && getIntObjVar(player, "coa3.ConvTracker") < 209)
        {
            obj_id device = utils.getItemPlayerHasByTemplate(player, "object/tangible/theme_park/alderaan/act3/encoded_data_disk.iff");
            if (isIdValid(device))
            {
                destroyObject(device);
                return true;
            }
        }
        return false;
    }
    public boolean coa3lcoordinator_condition_lookoutMissionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((getIntObjVar(player, "coa3.convTracker") > 199) && (getIntObjVar(player, "coa3.convTracker") < 209) && (hasObjVar(player, "coa3.imperial")));
    }
    public boolean coa3lcoordinator_condition_hasFinishedStory(obj_id player, obj_id npc) throws InterruptedException
    {
        return (badge.hasBadge(player, "event_coa3_imperial"));
    }
    public boolean coa3lcoordinator_condition_hasCOA2Badge(obj_id player, obj_id npc) throws InterruptedException
    {
        return (badge.hasBadge(player, "event_coa2_imperial"));
    }
    public boolean coa3lcoordinator_condition_isNotEnemyFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        String pvpFaction = factions.getFaction(player);
        return (pvpFaction == null || !pvpFaction.equals("Rebel"));
    }
    public void coa3lcoordinator_action_getLookoutMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 200);
        removeObjVar(player, "coa3.lookoutLikeMeter");
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 1);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3lcoordinator_action_getOffworldWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 401);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 4);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3lcoordinator_action_refreshOffworldWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 401);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 4);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3lcoordinator_action_abortOffworldWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 303);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 4);
        messageTo(player, "handleAbortMission", params, 0, false);
    }
    public void coa3lcoordinator_action_diskToCoordinator(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 301);
        removeObjVar(player, "coa3.lookoutLikeMeter");
    }
    public void coa3lcoordinator_action_refreshLookoutMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 200);
        removeObjVar(player, "coa3.lookoutLikeMeter");
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 1);
        messageTo(npc, "messageStartMission", params, 0, false);
    }
    public void coa3lcoordinator_action_abortLookoutMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 200);
        removeObjVar(player, "coa3.lookoutLikeMeter");
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 1);
        messageTo(player, "handleAbortMission", params, 0, false);
    }
    public void coa3lcoordinator_action_spokeCoordinatorOnce(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 101);
    }
    public int coa3lcoordinator_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b4f92479"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ad11a99b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_efdb954e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                    }
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_39"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_43"))
        {
            coa3lcoordinator_action_getLookoutMission(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_efdb954e"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4724fd7d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19d2e135");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_31"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35"))
        {
            coa3lcoordinator_action_getLookoutMission(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19d2e135"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1fd11fc5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4ca03ddb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_28de3a91");
                    }
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_23"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            coa3lcoordinator_action_getLookoutMission(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4ca03ddb"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1f81bc3c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d55f2579");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36142d2b");
                    }
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_17"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_19");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_28de3a91"))
        {
            coa3lcoordinator_action_getLookoutMission(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26b400f9");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d55f2579"))
        {
            coa3lcoordinator_action_getLookoutMission(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_eae61031");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36142d2b"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37281366");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f35aaa26"))
        {
            coa3lcoordinator_action_getOffworldWaypoint(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70df24d2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4148ae14");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d819b34e");
                    }
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55"))
        {
            coa3lcoordinator_action_abortOffworldWaypoint(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4148ae14"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_458473c9");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d819b34e"))
        {
            coa3lcoordinator_action_abortOffworldWaypoint(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_740fcda6");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bc0e9972"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ecae16e7");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52cc7bda"))
        {
            coa3lcoordinator_action_refreshOffworldWaypoint(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cd603241");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_315ff574"))
        {
            coa3lcoordinator_action_abortOffworldWaypoint(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45cbfb7d");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a6f2327f"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7b9e2f5e"))
        {
            coa3lcoordinator_action_refreshLookoutMission(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8ee9343e");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80af7773"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cf35e3d5");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87"))
        {
            coa3lcoordinator_action_abortLookoutMission(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a3ea7bf6");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86f67673");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c4ae7e31"))
        {
            coa3lcoordinator_action_spokeCoordinatorOnce(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_976a6f0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f595b0cb");
                    }
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20eec74a"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4e122a6b");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f595b0cb"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_223c5db");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10e4e113"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_87b694b0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6922db62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                    }
                    utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_105"))
        {
            coa3lcoordinator_action_spokeCoordinatorOnce(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1b4eff7e");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_108"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4aecac09");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int coa3lcoordinator_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6922db62"))
        {
            coa3lcoordinator_action_spokeCoordinatorOnce(player, npc);
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8824a3ee");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_102"))
        {
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2c127c6b");
                utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
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
            detachScript(self, "conversation.coa3lcoordinator");
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
        detachScript(self, "conversation.coa3lcoordinator");
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
        if (coa3lcoordinator_condition_hasSpokeCoordinatorOnce(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6837ec87");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition_hasSpokeInfoOfficer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4ba81cc8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b4f92479");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                }
                utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 2);
                npcStartConversation(player, npc, "coa3lcoordinator", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition_sentToTactOfficer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_19d54dc3");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition_completedFloraStoryMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_fa0fdb09");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f35aaa26");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 18);
                npcStartConversation(player, npc, "coa3lcoordinator", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition_hasFinishedStory(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e08167af");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition_hasOffworldWaypoint(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_448b1792");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bc0e9972");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52cc7bda");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_315ff574");
                }
                utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 24);
                npcStartConversation(player, npc, "coa3lcoordinator", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition_playerHasDisk(player, npc))
        {
            coa3lcoordinator_action_diskToCoordinator(player, npc);
            string_id message = new string_id(c_stringFile, "s_fec795");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition_lookoutMissionActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_882bc6e0");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a6f2327f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7b9e2f5e");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_80af7773");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                }
                utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 29);
                npcStartConversation(player, npc, "coa3lcoordinator", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition_hasCOA2Badge(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_20ad68cd");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c4ae7e31");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20eec74a");
                }
                utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 37);
                npcStartConversation(player, npc, "coa3lcoordinator", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition_isNotEnemyFaction(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9fd43ddf");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3lcoordinator_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10e4e113");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                }
                utils.setScriptVar(player, "conversation.coa3lcoordinator.branchId", 41);
                npcStartConversation(player, npc, "coa3lcoordinator", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3lcoordinator_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c3194680");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("coa3lcoordinator"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.coa3lcoordinator.branchId");
        if (branchId == 2 && coa3lcoordinator_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && coa3lcoordinator_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && coa3lcoordinator_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && coa3lcoordinator_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && coa3lcoordinator_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && coa3lcoordinator_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && coa3lcoordinator_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && coa3lcoordinator_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && coa3lcoordinator_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && coa3lcoordinator_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && coa3lcoordinator_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && coa3lcoordinator_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && coa3lcoordinator_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && coa3lcoordinator_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && coa3lcoordinator_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && coa3lcoordinator_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.coa3lcoordinator.branchId");
        return SCRIPT_CONTINUE;
    }
}
