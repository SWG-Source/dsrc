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

public class heraldnaboo extends script.base_script
{
    public heraldnaboo()
    {
    }
    public static String c_stringFile = "conversation/heraldnaboo";
    public boolean heraldnaboo_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void heraldnaboo_action_waypoint1(obj_id player, obj_id npc) throws InterruptedException
    {
        location weapon = new location(-6396, 0, -3196);
        obj_id waypoint = createWaypointInDatapad(player, weapon);
        setWaypointName(waypoint, "Research Facility");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldnaboo_action_waypoint2(obj_id player, obj_id npc) throws InterruptedException
    {
        location mauler = new location(2982, 0, 1113);
        obj_id waypoint = createWaypointInDatapad(player, mauler);
        setWaypointName(waypoint, "Mauler Encampment");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        doAnimationAction(npc, "cover_mouth");
    }
    public void heraldnaboo_action_waypoint3(obj_id player, obj_id npc) throws InterruptedException
    {
        location gungan = new location(4759, 0, -3896);
        obj_id waypoint = createWaypointInDatapad(player, gungan);
        setWaypointName(waypoint, "Recent Battle Site");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        doAnimationAction(npc, "slump_head");
    }
    public void heraldnaboo_action_no(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shake_head_no");
    }
    public void heraldnaboo_action_nod(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "nod_head_once");
    }
    public void heraldnaboo_action_implore(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "implore");
    }
    public void heraldnaboo_action_wave(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "wave1");
    }
    public void heraldnaboo_action_curtsey(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "curtsey1");
    }
    public void heraldnaboo_action_dismiss(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "wave_on_dismissing");
    }
    public void heraldnaboo_action_shush(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shush");
    }
    public int heraldnaboo_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3243aa0a"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_curtsey(player, npc);
                string_id message = new string_id(c_stringFile, "s_68fd1f22");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7fc11314"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_99e4afa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd9053a8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d65aab98");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fd9053a8"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_726bbe64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_385b2f92");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bdc2cc97");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d65aab98"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84b85cd6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_385b2f92"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ec04baa6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c213fa93");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87b9da1d");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bdc2cc97"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_shush(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa7e3331");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83237923");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8858a3fe");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c213fa93"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4b942e33");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87b9da1d"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_261bbc0d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7431a8fb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3bd15da");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7ad7fe8f");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7431a8fb"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_37c26930");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d3bd15da"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_36d2fdef");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7ad7fe8f"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint3(player, npc);
                string_id message = new string_id(c_stringFile, "s_ba21d00c");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83237923"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_nod(player, npc);
                string_id message = new string_id(c_stringFile, "s_2ca149bc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d241258b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce76e0f");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8858a3fe"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_implore(player, npc);
                string_id message = new string_id(c_stringFile, "s_7c64e921");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9575185c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c029271f");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d241258b"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_wave(player, npc);
                string_id message = new string_id(c_stringFile, "s_a96ea3c0");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ce76e0f"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b7659e9e");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b7659e9e"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint3(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9575185c"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cd8dc9");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c029271f"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_47");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldnaboo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                    }
                    utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_57"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint3(player, npc);
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldnaboo_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint1(player, npc);
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint2(player, npc);
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_71"))
        {
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                heraldnaboo_action_waypoint3(player, npc);
                string_id message = new string_id(c_stringFile, "s_73");
                utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
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
            detachScript(self, "conversation.heraldnaboo");
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
        detachScript(self, "conversation.heraldnaboo");
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
        if (heraldnaboo_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_df58f20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (heraldnaboo_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (heraldnaboo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3243aa0a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7fc11314");
                }
                utils.setScriptVar(player, "conversation.heraldnaboo.branchId", 1);
                npcStartConversation(player, npc, "heraldnaboo", message, responses);
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
        if (!conversationId.equals("heraldnaboo"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.heraldnaboo.branchId");
        if (branchId == 1 && heraldnaboo_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && heraldnaboo_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && heraldnaboo_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && heraldnaboo_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && heraldnaboo_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && heraldnaboo_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && heraldnaboo_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && heraldnaboo_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && heraldnaboo_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && heraldnaboo_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && heraldnaboo_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.heraldnaboo.branchId");
        return SCRIPT_CONTINUE;
    }
}
