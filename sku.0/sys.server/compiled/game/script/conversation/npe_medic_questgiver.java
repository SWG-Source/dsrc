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
import script.library.skill;
import script.library.space_quest;
import script.library.static_item;
import script.library.utils;

public class npe_medic_questgiver extends script.base_script
{
    public npe_medic_questgiver()
    {
    }
    public static String c_stringFile = "conversation/npe_medic_questgiver";
    public boolean npe_medic_questgiver_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_medic_questgiver_condition_isTaskCompleteBacta(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_medic", "npe_medic_return");
    }
    public boolean npe_medic_questgiver_condition_canTakeDna(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!groundquests.isQuestActiveOrComplete(player, "npe_side_dungeon_dna") && npe_medic_questgiver_condition_hasCompletedTemplate(player, npc));
    }
    public boolean npe_medic_questgiver_condition_isTaskCompleteDna(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_side_dungeon_dna", "npe_side_dungeon_dna_signal");
    }
    public boolean npe_medic_questgiver_condition_isMedicTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_medic", "npe_medic_heal_1");
    }
    public boolean npe_medic_questgiver_condition_hasCompletedMedicQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_medic");
    }
    public boolean npe_medic_questgiver_condition_isDnaTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_side_dungeon_dna", "npe_side_dungeon_dna_wait");
    }
    public boolean npe_medic_questgiver_condition_isAnyTaskActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (npe_medic_questgiver_condition_isMedicTaskActive(player, npc) || npe_medic_questgiver_condition_isDnaTaskActive(player, npc));
    }
    public boolean npe_medic_questgiver_condition_needsPack(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean needsItem = false;
        boolean hasItem = false;
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = static_item.getStaticItemName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("item_bactapack_01_01"))
                {
                    hasItem = true;
                }
            }
        }
        if (groundquests.isTaskActive(player, "npe_medic", "npe_medic_heal_1") && hasItem == false)
        {
            needsItem = true;
        }
        return needsItem;
    }
    public boolean npe_medic_questgiver_condition_hasCompletedSolo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_solo_profession");
    }
    public boolean npe_medic_questgiver_condition_needsToFinishTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!npe_medic_questgiver_condition_hasCompletedTemplate(player, npc) && npe_medic_questgiver_condition_isTaskCompleteBacta(player, npc));
    }
    public boolean npe_medic_questgiver_condition_pointedToHere(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_pointer_medic");
    }
    public boolean npe_medic_questgiver_condition_cantHelp(obj_id player, obj_id npc) throws InterruptedException
    {
        return !npe_medic_questgiver_condition_pointedToHere(player, npc) && !npe_medic_questgiver_condition_hasCompletedTemplate(player, npc);
    }
    public boolean npe_medic_questgiver_condition_hasCompletedTemplate(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "npe.finishedTemplate");
    }
    public void npe_medic_questgiver_action_giveMedicQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_medic");
        groundquests.sendSignal(player, "talked_to_aja");
        npe.giveHealPopUp(player, npc);
        boolean hasItem = false;
        obj_id[] playerStuff = getInventoryAndEquipment(player);
        for (int i = 0; i < playerStuff.length; i++)
        {
            String templateName = static_item.getStaticItemName(playerStuff[i]);
            if (templateName != null)
            {
                if (templateName.equals("item_bactapack_01_01"))
                {
                    hasItem = true;
                    newbieTutorialSetToolbarElement(player, 5, playerStuff[i]);
                    newbieTutorialHighlightUIElement(player, "/GroundHUD.Toolbar.volume.5", 5.0f);
                }
            }
        }
        if (hasItem == false)
        {
            obj_id bactaPack = static_item.createNewItemFunction("item_bactapack_01_01", player);
            obj_id[] items = new obj_id[1];
            items[0] = bactaPack;
            showLootBox(player, items);
            newbieTutorialSetToolbarElement(player, 5, bactaPack);
            dictionary dic = new dictionary();
            dic.put("player", player);
            dic.put("location", 5);
            dic.put("time", 5.0f);
            messageTo(player, "makeUiElementAnimate", dic, 0.25f, true);
        }
    }
    public void npe_medic_questgiver_action_giveSignalHealDone(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_medic_done_healing");
        groundquests.sendSignal(player, "finished_aja");
    }
    public void npe_medic_questgiver_action_giveNewPack(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id bactaPack = static_item.createNewItemFunction("item_bactapack_01_01", player);
        obj_id[] items = new obj_id[1];
        items[0] = bactaPack;
        showLootBox(player, items);
        newbieTutorialSetToolbarElement(player, 5, bactaPack);
        dictionary dic = new dictionary();
        dic.put("player", player);
        dic.put("location", 5);
        dic.put("time", 5.0f);
        messageTo(player, "makeUiElementAnimate", dic, 0.25f, true);
    }
    public void npe_medic_questgiver_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void npe_medic_questgiver_action_giveDnaQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "npe_side_dungeon_dna");
        npe.giveStationWaypoint(player);
        if (!hasObjVar(player, npe.QUEST_REWORK_VAR))
        {
            setObjVar(player, npe.QUEST_REWORK_VAR, npe.QUEST_ENUMERATION);
        }
    }
    public void npe_medic_questgiver_action_giveSignalDna(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_side_dungeon_dna_signal");
    }
    public int npe_medic_questgiver_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                npe_medic_questgiver_action_giveNewPack(player, npc);
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
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
    public int npe_medic_questgiver_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            doAnimationAction(player, "feed_creature_medium");
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                npe_medic_questgiver_action_giveSignalDna(player, npc);
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_questgiver_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_97");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic_questgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic_questgiver_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_100"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_questgiver_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                npe_medic_questgiver_action_giveDnaQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_56"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_questgiver_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_questgiver_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                npe_medic_questgiver_action_giveDnaQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_63");
                utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_questgiver_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_questgiver_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_104");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_questgiver_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_106"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                npe_medic_questgiver_action_giveSignalHealDone(player, npc);
                string_id message = new string_id(c_stringFile, "s_108");
                utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_questgiver_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_127");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_129");
                    }
                    utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_questgiver_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_129"))
        {
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                npe_medic_questgiver_action_giveMedicQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_131");
                utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
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
            detachScript(self, "conversation.npe_medic_questgiver");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Aja Sen (Medic)");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Aja Sen (Medic)");
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
        detachScript(self, "conversation.npe_medic_questgiver");
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
        if (npe_medic_questgiver_condition_needsPack(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_50");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 1);
                npcStartConversation(player, npc, "npe_medic_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic_questgiver_condition_isTaskCompleteDna(player, npc))
        {
            npe_medic_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_64");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 3);
                npcStartConversation(player, npc, "npe_medic_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic_questgiver_condition_canTakeDna(player, npc))
        {
            npe_medic_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_49");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                }
                utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 5);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "npe_medic_questgiver", null, pp, responses);
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
        if (npe_medic_questgiver_condition_isAnyTaskActive(player, npc))
        {
            npe_medic_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_29");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_medic_questgiver_condition_isTaskCompleteBacta(player, npc))
        {
            npe_medic_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_30");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 15);
                npcStartConversation(player, npc, "npe_medic_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic_questgiver_condition_pointedToHere(player, npc))
        {
            npe_medic_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_42");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic_questgiver_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                utils.setScriptVar(player, "conversation.npe_medic_questgiver.branchId", 18);
                npcStartConversation(player, npc, "npe_medic_questgiver", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic_questgiver_condition_needsToFinishTraining(player, npc))
        {
            npe_medic_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_medic_questgiver_condition_cantHelp(player, npc))
        {
            npe_medic_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_67");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_medic_questgiver_condition__defaultCondition(player, npc))
        {
            npe_medic_questgiver_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_94");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_medic_questgiver"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_medic_questgiver.branchId");
        if (branchId == 1 && npe_medic_questgiver_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && npe_medic_questgiver_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && npe_medic_questgiver_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && npe_medic_questgiver_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_medic_questgiver_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && npe_medic_questgiver_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && npe_medic_questgiver_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && npe_medic_questgiver_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && npe_medic_questgiver_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && npe_medic_questgiver_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && npe_medic_questgiver_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_medic_questgiver.branchId");
        return SCRIPT_CONTINUE;
    }
}
