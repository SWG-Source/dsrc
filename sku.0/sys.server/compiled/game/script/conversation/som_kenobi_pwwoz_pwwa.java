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
import script.library.groundquests;
import script.library.jedi;
import script.library.utils;

public class som_kenobi_pwwoz_pwwa extends script.base_script
{
    public som_kenobi_pwwoz_pwwa()
    {
    }
    public static String c_stringFile = "conversation/som_kenobi_pwwoz_pwwa";
    public boolean som_kenobi_pwwoz_pwwa_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean som_kenobi_pwwoz_pwwa_condition_playerJedi(obj_id player, obj_id npc) throws InterruptedException
    {
        return jedi.isForceSensitive(player);
    }
    public boolean som_kenobi_pwwoz_pwwa_condition_foundCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_kenobi_samaritan_1", "returnPwwoz");
    }
    public boolean som_kenobi_pwwoz_pwwa_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "som_kenobi_samaritan_1");
    }
    public boolean som_kenobi_pwwoz_pwwa_condition_keptCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "som_kenobi_samaritan_2") || groundquests.hasCompletedQuest(player, "som_kenobi_samaritan_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean som_kenobi_pwwoz_pwwa_condition_gaveCrystal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "som_kenobi_samaritan_1") && !groundquests.hasCompletedQuest(player, "som_kenobi_samaritan_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean som_kenobi_pwwoz_pwwa_condition_killPwwoz(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "som_kenobi_samaritan_1", "killIthorian");
    }
    public void som_kenobi_pwwoz_pwwa_action_giveQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "som_kenobi_samaritan_1");
    }
    public void som_kenobi_pwwoz_pwwa_action_removeInvuln(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "giveCrystal");
        setInvulnerable(npc, false);
        clearCondition(npc, CONDITION_INTERESTING);
    }
    public void som_kenobi_pwwoz_pwwa_action_attack(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "giveCrystal");
        startCombat(npc, player);
        clearCondition(npc, CONDITION_CONVERSABLE);
    }
    public void som_kenobi_pwwoz_pwwa_action_signalKeep(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "keepCrystal");
    }
    public void som_kenobi_pwwoz_pwwa_action_secondAttack(obj_id player, obj_id npc) throws InterruptedException
    {
        clearCondition(npc, CONDITION_CONVERSABLE);
        startCombat(npc, player);
    }
    public void som_kenobi_pwwoz_pwwa_action_removeInvuln2(obj_id player, obj_id npc) throws InterruptedException
    {
        setInvulnerable(npc, false);
        clearCondition(npc, CONDITION_INTERESTING);
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            som_kenobi_pwwoz_pwwa_action_removeInvuln2(player, npc);
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                som_kenobi_pwwoz_pwwa_action_secondAttack(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_227"))
        {
            doAnimationAction(player, "hug_tandem");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_229");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_230");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_231");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_228"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_237");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_239");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_230"))
        {
            doAnimationAction(player, "goodbye");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_236");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_231"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_232");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_233");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_233"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_234");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_238"))
        {
            doAnimationAction(player, "goodbye");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_240");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_239"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_242");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_244");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_244"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_246");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_174"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                string_id message = new string_id(c_stringFile, "s_178");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (som_kenobi_pwwoz_pwwa_condition_playerJedi(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_179");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_180");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_181");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_175"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_187");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (som_kenobi_pwwoz_pwwa_condition_playerJedi(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_189");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_191");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_193");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_176"))
        {
            doAnimationAction(player, "slow_down");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "squirm");
                string_id message = new string_id(c_stringFile, "s_205");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (som_kenobi_pwwoz_pwwa_condition_playerJedi(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_206");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_208");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_177"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_212");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (som_kenobi_pwwoz_pwwa_condition_playerJedi(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_214");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_216");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_218");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_179"))
        {
            doAnimationAction(player, "sigh_deeply");
            som_kenobi_pwwoz_pwwa_action_removeInvuln(player, npc);
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "heavy_cough_vomit");
                som_kenobi_pwwoz_pwwa_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_180"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                som_kenobi_pwwoz_pwwa_action_signalKeep(player, npc);
                string_id message = new string_id(c_stringFile, "s_185");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_181"))
        {
            doAnimationAction(player, "wave_finger_warning");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "twitch");
                string_id message = new string_id(c_stringFile, "s_182");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_183");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_184");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_183"))
        {
            doAnimationAction(player, "sigh_deeply");
            som_kenobi_pwwoz_pwwa_action_removeInvuln(player, npc);
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "heavy_cough_vomit");
                som_kenobi_pwwoz_pwwa_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_184"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                som_kenobi_pwwoz_pwwa_action_signalKeep(player, npc);
                string_id message = new string_id(c_stringFile, "s_185");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_189"))
        {
            doAnimationAction(player, "sigh_deeply");
            som_kenobi_pwwoz_pwwa_action_removeInvuln(player, npc);
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "heavy_cough_vomit");
                som_kenobi_pwwoz_pwwa_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_191"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                som_kenobi_pwwoz_pwwa_action_signalKeep(player, npc);
                string_id message = new string_id(c_stringFile, "s_185");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_193"))
        {
            doAnimationAction(player, "wave_finger_warning");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "twitch");
                som_kenobi_pwwoz_pwwa_action_signalKeep(player, npc);
                string_id message = new string_id(c_stringFile, "s_195");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            doAnimationAction(player, "sigh_deeply");
            som_kenobi_pwwoz_pwwa_action_removeInvuln(player, npc);
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "heavy_cough_vomit");
                som_kenobi_pwwoz_pwwa_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_207"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                som_kenobi_pwwoz_pwwa_action_signalKeep(player, npc);
                string_id message = new string_id(c_stringFile, "s_185");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_208"))
        {
            doAnimationAction(player, "wave_finger_warning");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_209");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_210");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_211");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_210"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                som_kenobi_pwwoz_pwwa_action_signalKeep(player, npc);
                string_id message = new string_id(c_stringFile, "s_185");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_211"))
        {
            doAnimationAction(player, "sigh_deeply");
            som_kenobi_pwwoz_pwwa_action_removeInvuln(player, npc);
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "heavy_cough_vomit");
                som_kenobi_pwwoz_pwwa_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_214"))
        {
            doAnimationAction(player, "sigh_deeply");
            som_kenobi_pwwoz_pwwa_action_removeInvuln(player, npc);
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "heavy_cough_vomit");
                som_kenobi_pwwoz_pwwa_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_216"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                som_kenobi_pwwoz_pwwa_action_signalKeep(player, npc);
                string_id message = new string_id(c_stringFile, "s_185");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_218"))
        {
            doAnimationAction(player, "wave_finger_warning");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_220");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_224");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_222"))
        {
            doAnimationAction(player, "shake_head_no");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                som_kenobi_pwwoz_pwwa_action_signalKeep(player, npc);
                string_id message = new string_id(c_stringFile, "s_185");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_224"))
        {
            doAnimationAction(player, "sigh_deeply");
            som_kenobi_pwwoz_pwwa_action_removeInvuln(player, npc);
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "heavy_cough_vomit");
                som_kenobi_pwwoz_pwwa_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_115");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_112"))
        {
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_151");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_153");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_113"))
        {
            doAnimationAction(player, "shakefist");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                string_id message = new string_id(c_stringFile, "s_114");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_117");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_118"))
        {
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_120"))
        {
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_123");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_122"))
        {
            doAnimationAction(player, "slow_down");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                som_kenobi_pwwoz_pwwa_action_giveQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_124");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_123"))
        {
            doAnimationAction(player, "slow_down");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                doAnimationAction(player, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_125");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_153"))
        {
            doAnimationAction(player, "laugh");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_155");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_157");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_157"))
        {
            doAnimationAction(player, "nod");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_159");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_161");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_161"))
        {
            doAnimationAction(player, "wtf");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_163");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_165");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_169");
                    }
                    utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int som_kenobi_pwwoz_pwwa_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_165"))
        {
            doAnimationAction(player, "slow_down");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                doAnimationAction(player, "refuse_offer_affection");
                som_kenobi_pwwoz_pwwa_action_giveQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_167");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_169"))
        {
            doAnimationAction(player, "shake_head_disgust");
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                doAnimationAction(player, "cuckoo");
                string_id message = new string_id(c_stringFile, "s_171");
                utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
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
            detachScript(self, "conversation.som_kenobi_pwwoz_pwwa");
        }
        ai_lib.setMood(self, "nervous");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setMood(self, "nervous");
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.som_kenobi_pwwoz_pwwa");
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
        if (som_kenobi_pwwoz_pwwa_condition_killPwwoz(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_82");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 1);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "som_kenobi_pwwoz_pwwa", null, pp, responses);
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
        if (som_kenobi_pwwoz_pwwa_condition_keptCrystal(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            doAnimationAction(player, "sigh_deeply");
            string_id message = new string_id(c_stringFile, "s_225");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_pwwoz_pwwa_condition_gaveCrystal(player, npc))
        {
            doAnimationAction(npc, "bow");
            string_id message = new string_id(c_stringFile, "s_226");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_227");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_228");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 4);
                npcStartConversation(player, npc, "som_kenobi_pwwoz_pwwa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_pwwoz_pwwa_condition_foundCrystal(player, npc))
        {
            doAnimationAction(npc, "celebrate");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_173");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_174");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_175");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_176");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_177");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 13);
                npcStartConversation(player, npc, "som_kenobi_pwwoz_pwwa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_pwwoz_pwwa_condition_onQuest(player, npc))
        {
            doAnimationAction(npc, "stamp_feet");
            string_id message = new string_id(c_stringFile, "s_172");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "bow");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_110");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (som_kenobi_pwwoz_pwwa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_113");
                }
                utils.setScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId", 25);
                npcStartConversation(player, npc, "som_kenobi_pwwoz_pwwa", message, responses);
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
        if (!conversationId.equals("som_kenobi_pwwoz_pwwa"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
        if (branchId == 1 && som_kenobi_pwwoz_pwwa_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && som_kenobi_pwwoz_pwwa_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && som_kenobi_pwwoz_pwwa_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && som_kenobi_pwwoz_pwwa_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && som_kenobi_pwwoz_pwwa_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && som_kenobi_pwwoz_pwwa_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && som_kenobi_pwwoz_pwwa_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && som_kenobi_pwwoz_pwwa_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && som_kenobi_pwwoz_pwwa_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && som_kenobi_pwwoz_pwwa_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && som_kenobi_pwwoz_pwwa_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && som_kenobi_pwwoz_pwwa_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && som_kenobi_pwwoz_pwwa_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && som_kenobi_pwwoz_pwwa_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && som_kenobi_pwwoz_pwwa_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && som_kenobi_pwwoz_pwwa_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && som_kenobi_pwwoz_pwwa_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && som_kenobi_pwwoz_pwwa_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && som_kenobi_pwwoz_pwwa_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && som_kenobi_pwwoz_pwwa_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && som_kenobi_pwwoz_pwwa_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && som_kenobi_pwwoz_pwwa_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && som_kenobi_pwwoz_pwwa_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.som_kenobi_pwwoz_pwwa.branchId");
        return SCRIPT_CONTINUE;
    }
}
