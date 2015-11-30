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
import script.library.space_flags;
import script.library.utils;

public class lok_rebel_trainer_2_smuggler extends script.base_script
{
    public lok_rebel_trainer_2_smuggler()
    {
    }
    public static String c_stringFile = "conversation/lok_rebel_trainer_2_smuggler";
    public boolean lok_rebel_trainer_2_smuggler_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lok_rebel_trainer_2_smuggler_condition_isRightPlayer1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.getIntSpaceFlag(player, "viopaSmuggler") == 1)
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_smuggler_condition_isRightPlayer2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.getIntSpaceFlag(player, "viopaSmuggler") == 3)
        {
            return true;
        }
        return false;
    }
    public void lok_rebel_trainer_2_smuggler_action_face(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void lok_rebel_trainer_2_smuggler_action_addSmuggler2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "viopaSmuggler", 4);
    }
    public void lok_rebel_trainer_2_smuggler_action_addSmuggler1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "viopaSmuggler", 2);
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fca23d59"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_a3c154e6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6bb0cb94");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a46f0bb8"))
        {
            doAnimationAction(player, "point_accusingly");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_ed7e2471");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9280ab0");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6bb0cb94"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "whisper");
                string_id message = new string_id(c_stringFile, "s_8dc05a21");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50e93f2d");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50e93f2d"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nervous");
                lok_rebel_trainer_2_smuggler_action_addSmuggler1(player, npc);
                string_id message = new string_id(c_stringFile, "s_871202ae");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9280ab0"))
        {
            doAnimationAction(player, "slow_down");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nervous");
                string_id message = new string_id(c_stringFile, "s_d36158bb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5132e66");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d5132e66"))
        {
            doAnimationAction(player, "whisper");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "whisper");
                string_id message = new string_id(c_stringFile, "s_53a57295");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6541ff8b");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6541ff8b"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nervous");
                string_id message = new string_id(c_stringFile, "s_5dd70a2c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3103c633");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3103c633"))
        {
            doAnimationAction(player, "slow_down");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                lok_rebel_trainer_2_smuggler_action_addSmuggler1(player, npc);
                string_id message = new string_id(c_stringFile, "s_1106d288");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_88fb9bcb"))
        {
            doAnimationAction(player, "point_accusingly");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_bf37655b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73e609b7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f911cddf");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e28286fc"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73e609b7"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9da8a554");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d7fe1a24");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_edb238bc");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f911cddf"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_99dcc230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d7fe1a24"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_4e307ac2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2387d3d7");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_edb238bc"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                lok_rebel_trainer_2_smuggler_action_addSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_f7deed3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6789e90e");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2387d3d7"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                lok_rebel_trainer_2_smuggler_action_addSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_f7deed3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6789e90e");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6789e90e"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                string_id message = new string_id(c_stringFile, "s_cf66be5a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b5dd2ed8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29b10239");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b5dd2ed8"))
        {
            doAnimationAction(player, "slit_throat");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_d0098db3");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_29b10239"))
        {
            doAnimationAction(player, "belly_laugh");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                string_id message = new string_id(c_stringFile, "s_f39484e1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6b133072");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6b133072"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_37");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                lok_rebel_trainer_2_smuggler_action_addSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_f7deed3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6789e90e");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                lok_rebel_trainer_2_smuggler_action_addSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_f7deed3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6789e90e");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "fishing_cast");
                string_id message = new string_id(c_stringFile, "s_66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                lok_rebel_trainer_2_smuggler_action_addSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_f7deed3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6789e90e");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                lok_rebel_trainer_2_smuggler_action_addSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_f7deed3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6789e90e");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_74"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                lok_rebel_trainer_2_smuggler_action_addSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_f7deed3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6789e90e");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_smuggler_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                lok_rebel_trainer_2_smuggler_action_addSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_f7deed3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6789e90e");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
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
            detachScript(self, "conversation.lok_rebel_trainer_2_smuggler");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Vrak");
        ai_lib.setDefaultCalmMood(self, "nervous");
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Vrak");
        ai_lib.setDefaultCalmMood(self, "nervous");
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
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
        detachScript(self, "conversation.lok_rebel_trainer_2_smuggler");
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
        if (lok_rebel_trainer_2_smuggler_condition_isRightPlayer1(player, npc))
        {
            lok_rebel_trainer_2_smuggler_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_c5fc3d7a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fca23d59");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a46f0bb8");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 1);
                npcStartConversation(player, npc, "lok_rebel_trainer_2_smuggler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_smuggler_condition_isRightPlayer2(player, npc))
        {
            doAnimationAction(npc, "nervous");
            lok_rebel_trainer_2_smuggler_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_9b75f1f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_88fb9bcb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e28286fc");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId", 10);
                npcStartConversation(player, npc, "lok_rebel_trainer_2_smuggler", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_smuggler_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            lok_rebel_trainer_2_smuggler_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_b02a22c2");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("lok_rebel_trainer_2_smuggler"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
        if (branchId == 1 && lok_rebel_trainer_2_smuggler_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && lok_rebel_trainer_2_smuggler_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && lok_rebel_trainer_2_smuggler_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && lok_rebel_trainer_2_smuggler_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && lok_rebel_trainer_2_smuggler_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && lok_rebel_trainer_2_smuggler_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && lok_rebel_trainer_2_smuggler_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && lok_rebel_trainer_2_smuggler_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && lok_rebel_trainer_2_smuggler_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && lok_rebel_trainer_2_smuggler_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && lok_rebel_trainer_2_smuggler_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && lok_rebel_trainer_2_smuggler_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && lok_rebel_trainer_2_smuggler_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && lok_rebel_trainer_2_smuggler_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && lok_rebel_trainer_2_smuggler_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && lok_rebel_trainer_2_smuggler_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && lok_rebel_trainer_2_smuggler_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && lok_rebel_trainer_2_smuggler_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && lok_rebel_trainer_2_smuggler_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && lok_rebel_trainer_2_smuggler_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && lok_rebel_trainer_2_smuggler_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2_smuggler.branchId");
        return SCRIPT_CONTINUE;
    }
}
