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
import script.library.npe;
import script.library.static_item;
import script.library.utils;

public class npe_profession_entertainer extends script.base_script
{
    public npe_profession_entertainer()
    {
    }
    public static String c_stringFile = "conversation/npe_profession_entertainer";
    public boolean npe_profession_entertainer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_profession_entertainer_condition_onTrainingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "npe_pointer_entertainer") || groundquests.isQuestActive(player, "npe_entertainer_1"));
    }
    public boolean npe_profession_entertainer_condition_finishedTrainingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_entertainer_1");
    }
    public boolean npe_profession_entertainer_condition_isEntertainer(obj_id player, obj_id npc) throws InterruptedException
    {
        String pTemplate = getSkillTemplate(player);
        if (pTemplate.indexOf("entertainer") > -1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean npe_profession_entertainer_condition_onEntertainer1quest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_prof_entertainer1");
    }
    public boolean npe_profession_entertainer_condition_finishedEntertainer1Quest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_prof_entertainer1");
    }
    public boolean npe_profession_entertainer_condition_onEntertainer1Task(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_prof_entertainer1", "returntomira");
    }
    public boolean npe_profession_entertainer_condition_onEntertainer2Task(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_prof_entertainer2", "returntomira");
    }
    public boolean npe_profession_entertainer_condition_onEntertainer2quest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_prof_entertainer2");
    }
    public boolean npe_profession_entertainer_condition_finishedEntertainer2Quest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_prof_entertainer2");
    }
    public boolean npe_profession_entertainer_condition_onOrCompletedEnt2Quest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "npe_prof_entertainer2");
    }
    public boolean npe_profession_entertainer_condition_needsHorn(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean needsItem = false;
        boolean hasItem = false;
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = static_item.getStaticItemName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("item_npe_smooth_slitherhorn_01_01"))
                {
                    hasItem = true;
                }
            }
        }
        if (groundquests.isTaskActive(player, "npe_prof_entertainer2", 0) && hasItem == false)
        {
            needsItem = true;
        }
        return needsItem;
    }
    public void npe_profession_entertainer_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void npe_profession_entertainer_action_dance(obj_id player, obj_id npc) throws InterruptedException
    {
        ai_lib.setMood(npc, "themepark_oola");
        dictionary webster = new dictionary();
        webster.put("npc", npc);
        messageTo(npc, "stopDancing", webster, 9, false);
    }
    public void npe_profession_entertainer_action_givePointerAnvar(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_pointer_entertainer");
        groundquests.sendSignal(player, "found_entertainer");
    }
    public void npe_profession_entertainer_action_signalEnt1Quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_prof_entertainer1_return");
    }
    public void npe_profession_entertainer_action_giveEntertainerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_prof_entertainer1");
        npe.giveEntXpPopUp(player);
    }
    public void npe_profession_entertainer_action_giveSecretaryPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_pointer_secretary");
    }
    public void npe_profession_entertainer_action_giveHanPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_job_pointer_han");
    }
    public void npe_profession_entertainer_action_signalEnt2Quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_prof_entertainer2_done");
        setObjVar(player, "npe.finishedTemplate", 1);
    }
    public void npe_profession_entertainer_action_giveEntertainer2Quest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "npe_prof_entertainer2");
        newbieTutorialSetToolbarElement(player, 7, "/StartMusic");
        newbieTutorialSetToolbarElement(player, 8, "/StopMusic");
        newbieTutorialHighlightUIElement(player, "/GroundHUD.Toolbar.volume.7", 5.0f);
        newbieTutorialHighlightUIElement(player, "/GroundHUD.Toolbar.volume.8", 5.0f);
    }
    public void npe_profession_entertainer_action_giveNewHorn(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id horn = static_item.createNewItemFunction("item_npe_smooth_slitherhorn_01_01", player);
        obj_id[] items = new obj_id[1];
        items[0] = horn;
        showLootBox(player, items);
    }
    public int npe_profession_entertainer_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_89"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "celebrate");
                string_id message = new string_id(c_stringFile, "s_58");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "feed_creature_medium");
                npe_profession_entertainer_action_signalEnt2Quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition_onOrCompletedEnt2Quest(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!npe_profession_entertainer_condition_onOrCompletedEnt2Quest(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_89"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                npe_profession_entertainer_action_giveNewHorn(player, npc);
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                npe_profession_entertainer_action_giveEntertainer2Quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "feed_creature_medium");
                npe_profession_entertainer_action_signalEnt1Quest(player, npc);
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition_onOrCompletedEnt2Quest(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!npe_profession_entertainer_condition_onOrCompletedEnt2Quest(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_89"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                npe_profession_entertainer_action_giveHanPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_72"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_76"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                npe_profession_entertainer_action_giveEntertainerQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_88"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_profession_entertainer_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
            {
                npe_profession_entertainer_action_givePointerAnvar(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
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
            detachScript(self, "conversation.npe_profession_entertainer");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Mira T'rani(Entertainer)");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Mira T'rani(Entertainer)");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.npe_profession_entertainer");
        return SCRIPT_CONTINUE;
    }
    public int stopDancing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id npc = params.getObjId("npc");
        ai_lib.setMood(npc, "default");
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
        if (npe_profession_entertainer_condition_finishedEntertainer2Quest(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_61");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_profession_entertainer_condition_onOrCompletedEnt2Quest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!npe_profession_entertainer_condition_onOrCompletedEnt2Quest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                }
                utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 14);
                npcStartConversation(player, npc, "npe_profession_entertainer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_profession_entertainer_condition_onEntertainer2Task(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_54");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 2);
                npcStartConversation(player, npc, "npe_profession_entertainer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_profession_entertainer_condition_needsHorn(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_63");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                }
                utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 6);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_profession_entertainer", null, pp, responses);
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
        if (npe_profession_entertainer_condition_onEntertainer2quest(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_53");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_profession_entertainer_condition_finishedEntertainer1Quest(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                }
                utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 9);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_profession_entertainer", null, pp, responses);
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
        if (npe_profession_entertainer_condition_onEntertainer1Task(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_29");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 12);
                npcStartConversation(player, npc, "npe_profession_entertainer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_profession_entertainer_condition_onEntertainer1quest(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_68");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_profession_entertainer_condition_finishedTrainingQuest(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_70");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 18);
                npcStartConversation(player, npc, "npe_profession_entertainer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_profession_entertainer_condition_onTrainingQuest(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_84");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_profession_entertainer_condition_isEntertainer(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_86");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_profession_entertainer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                }
                utils.setScriptVar(player, "conversation.npe_profession_entertainer.branchId", 23);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_profession_entertainer", null, pp, responses);
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
        if (npe_profession_entertainer_condition__defaultCondition(player, npc))
        {
            npe_profession_entertainer_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_96");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_profession_entertainer"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_profession_entertainer.branchId");
        if (branchId == 1 && npe_profession_entertainer_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && npe_profession_entertainer_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && npe_profession_entertainer_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && npe_profession_entertainer_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && npe_profession_entertainer_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && npe_profession_entertainer_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_profession_entertainer_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && npe_profession_entertainer_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && npe_profession_entertainer_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && npe_profession_entertainer_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && npe_profession_entertainer_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && npe_profession_entertainer_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && npe_profession_entertainer_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && npe_profession_entertainer_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && npe_profession_entertainer_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && npe_profession_entertainer_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && npe_profession_entertainer_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_profession_entertainer.branchId");
        return SCRIPT_CONTINUE;
    }
}
