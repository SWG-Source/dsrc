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
import script.library.money;
import script.library.utils;

public class heraldtatooine2 extends script.base_script
{
    public heraldtatooine2()
    {
    }
    public static String c_stringFile = "conversation/heraldtatooine2";
    public boolean heraldtatooine2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean heraldtatooine2_condition_cashyes50(obj_id player, obj_id npc) throws InterruptedException
    {
        int cash = getTotalMoney(player);
        if (cash >= 50)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean heraldtatooine2_condition_cashyes30(obj_id player, obj_id npc) throws InterruptedException
    {
        int cash = getTotalMoney(player);
        if (cash >= 30)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean heraldtatooine2_condition_cashyes15(obj_id player, obj_id npc) throws InterruptedException
    {
        int cash = getTotalMoney(player);
        if (cash >= 15)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean heraldtatooine2_condition_cashyes60(obj_id player, obj_id npc) throws InterruptedException
    {
        int cash = getTotalMoney(player);
        if (cash >= 60)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void heraldtatooine2_action_getcash50(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        money.requestPayment(player, npc, 50, "pass_fail", params, true);
        location rebel = new location(-784, 0, -4451);
        obj_id waypoint = createWaypointInDatapad(player, rebel);
        setWaypointName(waypoint, "Rebel Military Base");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldtatooine2_action_getcash30(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        money.requestPayment(player, npc, 30, "pass_fail", params, true);
        location rebel = new location(-1084, 0, -4751);
        obj_id waypoint = createWaypointInDatapad(player, rebel);
        setWaypointName(waypoint, "Rebel Military Base");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldtatooine2_action_getcash30a(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        money.requestPayment(player, npc, 15, "pass_fail", params, true);
        location tusken = new location(-1493, 0, -208);
        obj_id waypoint = createWaypointInDatapad(player, tusken);
        setWaypointName(waypoint, "Tusken Bunker");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldtatooine2_action_getcash60(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        money.requestPayment(player, npc, 60, "pass_fail", params, true);
        location hutt = new location(5021, 0, 547);
        obj_id waypoint = createWaypointInDatapad(player, hutt);
        setWaypointName(waypoint, "Hutt Hideout");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void heraldtatooine2_action_beckon(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "beckon");
    }
    public void heraldtatooine2_action_dismiss(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "wave_on_dismissing");
    }
    public int heraldtatooine2_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72ac291d"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_19ca58d8");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42bcb8b6"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9ac6e8f5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0c6151b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_edb238bc");
                    }
                    utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldtatooine2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d0c6151b"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c599cc9e");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_edb238bc"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f026df25");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7f115744");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d322c6b");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d1f219dd");
                    }
                    utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldtatooine2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7f115744"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5ee77a06");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34cffb60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4ff8cd88");
                    }
                    utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d322c6b"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ea388738");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c41d4bce");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f2044f4a");
                    }
                    utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d1f219dd"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5abff6d6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c885bfb0");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8be640b8");
                    }
                    utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldtatooine2_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34cffb60"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6b184c6");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4ff8cd88"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74ed5d50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c3dbbcab");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30c59b36");
                    }
                    utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldtatooine2_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c3dbbcab"))
        {
            if (heraldtatooine2_condition_cashyes60(player, npc))
            {
                heraldtatooine2_action_getcash60(player, npc);
                string_id message = new string_id(c_stringFile, "s_32a1ecd");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_241afd0a");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30c59b36"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3a28c7c4");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldtatooine2_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c41d4bce"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b81c8c9");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2044f4a"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17daabb0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4440ce87");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81a382e3");
                    }
                    utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b05cb415"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6cf09e8e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d9903b42");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fb859e03");
                    }
                    utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldtatooine2_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4440ce87"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                heraldtatooine2_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_88788d03");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_81a382e3"))
        {
            if (heraldtatooine2_condition_cashyes50(player, npc))
            {
                heraldtatooine2_action_getcash50(player, npc);
                string_id message = new string_id(c_stringFile, "s_c799721b");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                heraldtatooine2_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_1c6f5661");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldtatooine2_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d9903b42"))
        {
            if (heraldtatooine2_condition_cashyes30(player, npc))
            {
                heraldtatooine2_action_getcash30(player, npc);
                string_id message = new string_id(c_stringFile, "s_aae6686e");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                heraldtatooine2_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_38bef1fe");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fb859e03"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6892af1b");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldtatooine2_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c885bfb0"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                heraldtatooine2_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_c9a787f0");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8be640b8"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3d3f67a4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63855d3f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_177f6371");
                    }
                    utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int heraldtatooine2_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63855d3f"))
        {
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c9ff39ea");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_177f6371"))
        {
            if (heraldtatooine2_condition_cashyes30(player, npc))
            {
                heraldtatooine2_action_getcash30a(player, npc);
                string_id message = new string_id(c_stringFile, "s_5d7da0");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                heraldtatooine2_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3dda380");
                utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
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
            detachScript(self, "conversation.heraldtatooine2");
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
        detachScript(self, "conversation.heraldtatooine2");
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
        if (heraldtatooine2_condition__defaultCondition(player, npc))
        {
            heraldtatooine2_action_beckon(player, npc);
            string_id message = new string_id(c_stringFile, "s_13fcbd87");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (heraldtatooine2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (heraldtatooine2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_72ac291d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_42bcb8b6");
                }
                utils.setScriptVar(player, "conversation.heraldtatooine2.branchId", 1);
                npcStartConversation(player, npc, "heraldtatooine2", message, responses);
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
        if (!conversationId.equals("heraldtatooine2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.heraldtatooine2.branchId");
        if (branchId == 1 && heraldtatooine2_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && heraldtatooine2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && heraldtatooine2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && heraldtatooine2_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && heraldtatooine2_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && heraldtatooine2_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && heraldtatooine2_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && heraldtatooine2_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && heraldtatooine2_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && heraldtatooine2_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.heraldtatooine2.branchId");
        return SCRIPT_CONTINUE;
    }
}
