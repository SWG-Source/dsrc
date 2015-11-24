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
import script.library.groundquests;
import script.library.utils;

public class quest_hero_of_tatooine_hermit extends script.base_script
{
    public quest_hero_of_tatooine_hermit()
    {
    }
    public static String c_stringFile = "conversation/quest_hero_of_tatooine_hermit";
    public boolean quest_hero_of_tatooine_hermit_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_hasSquillSkull(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "quest_hero_of_tatooine_main", "hero_of_tatooine_main_02"))
        {
            if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/hero_of_tatooine/squill_skull.iff"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_BadgeAltruism(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!badge.hasBadge(player, "poi_factoryliberation"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_BadgeIntellect(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!badge.hasBadge(player, "poi_twoliars"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_BadgeCourage(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!badge.hasBadge(player, "poi_rabidbeast"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_BadgeHonor(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!badge.hasBadge(player, "poi_prisonbreak"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_questHasAllMarks(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.badgequests") || groundquests.hasCompletedTask(player, "quest_hero_of_tatooine_main", "hero_of_tatooine_main_02"))
        {
            if (badge.hasBadge(player, "poi_prisonbreak") && badge.hasBadge(player, "poi_factoryliberation") && badge.hasBadge(player, "poi_rabidbeast") && badge.hasBadge(player, "poi_twoliars"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_heroOfTatooineActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.task") || hasObjVar(player, "quest.hero_of_tatooine.badgequests"))
        {
            return true;
        }
        return groundquests.isQuestActive(player, "quest_hero_of_tatooine_main");
    }
    public boolean quest_hero_of_tatooine_hermit_condition_OnSkullQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "quest.hero_of_tatooine.task") || groundquests.isTaskActive(player, "quest_hero_of_tatooine_main", "hero_of_tatooine_main_skull");
    }
    public boolean quest_hero_of_tatooine_hermit_condition_heroOfTatooineComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.complete"))
        {
            return true;
        }
        if (badge.hasBadge(player, "poi_heromark"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_IsOwedItems(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "quest.hero_of_tatooine.owed");
    }
    public boolean quest_hero_of_tatooine_hermit_condition_IsInvFull(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        int free = getVolumeFree(inv);
        if (free <= 0)
        {
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_GrantOwed(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        if (hasObjVar(player, "quest.hero_of_tatooine.owed.altruism"))
        {
            obj_id mark_altruism = createObject("object/tangible/loot/quest/hero_of_tatooine/mark_altruism.iff", inv, "");
            if (isIdValid(mark_altruism))
            {
                removeObjVar(player, "quest.hero_of_tatooine.owed.altruism");
            }
            else 
            {
                return true;
            }
        }
        if (hasObjVar(player, "quest.hero_of_tatooine.owed.intellect"))
        {
            obj_id mark_intellect = createObject("object/tangible/loot/quest/hero_of_tatooine/mark_intellect.iff", inv, "");
            if (isIdValid(mark_intellect))
            {
                removeObjVar(player, "quest.hero_of_tatooine.owed.intellect");
            }
            else 
            {
                return true;
            }
        }
        if (hasObjVar(player, "quest.hero_of_tatooine.owed.honor"))
        {
            obj_id mark_honor = createObject("object/tangible/loot/quest/hero_of_tatooine/mark_honor.iff", inv, "");
            if (isIdValid(mark_honor))
            {
                removeObjVar(player, "quest.hero_of_tatooine.owed.honor");
            }
            else 
            {
                return true;
            }
        }
        if (hasObjVar(player, "quest.hero_of_tatooine.owed.hero"))
        {
            obj_id mark_hero = createObject("object/tangible/wearables/ring/ring_mark_hero.iff", inv, "");
            if (isIdValid(mark_hero))
            {
                removeObjVar(player, "quest.hero_of_tatooine.owed.hero");
            }
            else 
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_hermit_condition_collectingMarks(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "quest.hero_of_tatooine.badgequests") || groundquests.isTaskActive(player, "quest_hero_of_tatooine_main", "hero_of_tatooine_main_03");
    }
    public boolean quest_hero_of_tatooine_hermit_condition_alreadyHasAllMarks(obj_id player, obj_id npc) throws InterruptedException
    {
        return badge.hasBadge(player, "poi_prisonbreak") && badge.hasBadge(player, "poi_factoryliberation") && badge.hasBadge(player, "poi_rabidbeast") && badge.hasBadge(player, "poi_twoliars");
    }
    public void quest_hero_of_tatooine_hermit_action_QuestAccepted(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "quest_hero_of_tatooine_main");
    }
    public void quest_hero_of_tatooine_hermit_action_QuestCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.badgequests") || groundquests.isTaskActive(player, "quest_hero_of_tatooine_main", "hero_of_tatooine_main_04"))
        {
            obj_id inv = utils.getInventoryContainer(player);
            if (!isIdValid(inv))
            {
                return;
            }
            obj_id hero_mark = createObject("object/tangible/wearables/ring/ring_mark_hero.iff", inv, "");
            if (!isIdValid(hero_mark))
            {
                setObjVar(player, "quest.hero_of_tatooine.owed.hero", 1);
                sendSystemMessage(player, new string_id("quest/hero_of_tatooine/system_messages", "hero_inv_full"));
            }
            removeObjVar(player, "quest.hero_of_tatooine.badgequests");
            groundquests.sendSignal(player, "hero_of_tatooine_main_04");
            badge.grantBadge(player, "poi_heromark");
            CustomerServiceLog("quest", "HERO OF TATOOINE - %TU has acquired the Mark of the Hero", player);
        }
        return;
    }
    public void quest_hero_of_tatooine_hermit_action_TakeSquillSkull(obj_id player, obj_id npc) throws InterruptedException
    {
        if (isIdValid(player))
        {
            obj_id objInventory = utils.getInventoryContainer(player);
            if (isIdValid(objInventory))
            {
                obj_id[] objContents = utils.getContents(objInventory);
                if (objContents != null)
                {
                    for (int intI = 0; intI < objContents.length; intI++)
                    {
                        if (isIdValid(objContents[intI]))
                        {
                            obj_id inventoryItem = objContents[intI];
                            String strItemTemplate = getTemplateName(inventoryItem);
                            if (strItemTemplate.equals("object/tangible/loot/quest/hero_of_tatooine/squill_skull.iff"))
                            {
                                setObjVar(inventoryItem, "hero_of_tatooine_skull_to_hermit", true);
                                destroyObject(inventoryItem);
                                groundquests.sendSignal(player, "hero_of_tatooine_main_02");
                                if (badge.hasBadge(player, "poi_factoryliberation"))
                                {
                                    groundquests.sendSignal(player, "hero_of_tatooine_main_altruism");
                                }
                                if (badge.hasBadge(player, "poi_rabidbeast"))
                                {
                                    groundquests.sendSignal(player, "hero_of_tatooine_main_courage");
                                }
                                if (badge.hasBadge(player, "poi_prisonbreak"))
                                {
                                    groundquests.sendSignal(player, "hero_of_tatooine_main_honor");
                                }
                                if (badge.hasBadge(player, "poi_twoliars"))
                                {
                                    groundquests.sendSignal(player, "hero_of_tatooine_main_intellect");
                                }
                                removeObjVar(player, "quest.hero_of_tatooine.task");
                            }
                        }
                    }
                }
            }
        }
        return;
    }
    public void quest_hero_of_tatooine_hermit_action_face_to(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return;
    }
    public void quest_hero_of_tatooine_hermit_action_grantOwedItems(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        if (hasObjVar(player, "quest.hero_of_tatooine.owed.altruism"))
        {
            obj_id mark_altruism = createObject("object/tangible/loot/quest/hero_of_tatooine/mark_altruism.iff", inv, "");
            if (isIdValid(mark_altruism))
            {
                removeObjVar(player, "quest.hero_of_tatooine.owed.altruism");
            }
            else 
            {
                return;
            }
        }
        if (hasObjVar(player, "quest.hero_of_tatooine.owed.intellect"))
        {
            obj_id mark_intellect = createObject("object/tangible/loot/quest/hero_of_tatooine/mark_intellect.iff", inv, "");
            if (isIdValid(mark_intellect))
            {
                removeObjVar(player, "quest.hero_of_tatooine.owed.intellect");
            }
            else 
            {
                return;
            }
        }
        if (hasObjVar(player, "quest.hero_of_tatooine.owed.honor"))
        {
            obj_id mark_honor = createObject("object/tangible/loot/quest/hero_of_tatooine/mark_honor.iff", inv, "");
            if (isIdValid(mark_honor))
            {
                removeObjVar(player, "quest.hero_of_tatooine.owed.honor");
            }
            else 
            {
                return;
            }
        }
    }
    public int quest_hero_of_tatooine_hermit_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6441a2a6"))
        {
            if (quest_hero_of_tatooine_hermit_condition_IsInvFull(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ab4620a6");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_hero_of_tatooine_hermit_condition_GrantOwed(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5ff9d2ec");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_db6d4167");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cfb883ed"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e0ab34b6");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cd2c070a"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59283967");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48bfa35f");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_af540d0"))
        {
            if (quest_hero_of_tatooine_hermit_condition_alreadyHasAllMarks(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                quest_hero_of_tatooine_hermit_action_TakeSquillSkull(player, npc);
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_hermit_action_TakeSquillSkull(player, npc);
                string_id message = new string_id(c_stringFile, "s_bbb8e096");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1bb3475f");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cc62685c"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c6825098");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_503d468c"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194db552");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
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
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cbb8634f");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_da196589"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48bfa35f"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "worship");
                quest_hero_of_tatooine_hermit_action_QuestCompleted(player, npc);
                string_id message = new string_id(c_stringFile, "s_579bac17");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48bfa35f");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48bfa35f"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "worship");
                quest_hero_of_tatooine_hermit_action_QuestCompleted(player, npc);
                string_id message = new string_id(c_stringFile, "s_579bac17");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1bb3475f"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f013aca1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cb535214");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50c6e23d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_669b88aa");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_920f731c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d86dc24");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cb535214"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96fd32c3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_50c6e23d"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_856de06c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_669b88aa"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5e7d1e16");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_920f731c"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f9a4e3e3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d86dc24"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a5bd2f51");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9b27823"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f013aca1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cb535214");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50c6e23d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_669b88aa");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_920f731c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d86dc24");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f013aca1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cb535214");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50c6e23d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_669b88aa");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_920f731c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d86dc24");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f013aca1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cb535214");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50c6e23d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_669b88aa");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_920f731c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d86dc24");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f013aca1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cb535214");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50c6e23d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_669b88aa");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_920f731c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d86dc24");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8cb1f35d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cbb8634f"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b6a019cf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194db552");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
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
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cbb8634f");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194db552");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
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
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cbb8634f");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_56"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194db552");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
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
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cbb8634f");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194db552");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
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
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cbb8634f");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194db552");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeIntellect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeCourage(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeHonor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (quest_hero_of_tatooine_hermit_condition_BadgeAltruism(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
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
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cbb8634f");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1bb7b1eb"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b2929fdf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e9bd97f9");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e9bd97f9"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a85c56b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2d0b6a");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2d0b6a"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d2a7ff43");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f297414");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f297414"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d38cc2cb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bfb7334");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55937d94");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8bfb7334"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66fbfd08");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9b634554");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55937d94"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d22f1e74");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9b634554"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96a66dea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7abf0913");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7abf0913"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_342ec337");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cf3b7067");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cf3b7067"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1f8abb5f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8ecd39ce");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8ecd39ce"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6cd88f7f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_66270434");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66270434"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4fcf6919");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d111fbf4");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_hermit_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d111fbf4"))
        {
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_hermit_action_QuestAccepted(player, npc);
                string_id message = new string_id(c_stringFile, "s_599bebab");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
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
            detachScript(self, "conversation.quest_hero_of_tatooine_hermit");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
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
        detachScript(self, "conversation.quest_hero_of_tatooine_hermit");
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
        if (quest_hero_of_tatooine_hermit_condition_IsOwedItems(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_63076377");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6441a2a6");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 1);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_hermit", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_hermit_condition_heroOfTatooineComplete(player, npc))
        {
            doAnimationAction(npc, "bow2");
            quest_hero_of_tatooine_hermit_action_face_to(player, npc);
            string_id message = new string_id(c_stringFile, "s_7adceec5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cfb883ed");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 5);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_hermit", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_hermit_condition_heroOfTatooineActive(player, npc))
        {
            quest_hero_of_tatooine_hermit_action_face_to(player, npc);
            string_id message = new string_id(c_stringFile, "s_2a82993c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_hermit_condition_questHasAllMarks(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_hero_of_tatooine_hermit_condition_hasSquillSkull(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (quest_hero_of_tatooine_hermit_condition_OnSkullQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (quest_hero_of_tatooine_hermit_condition_collectingMarks(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cd2c070a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_af540d0");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cc62685c");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_503d468c");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da196589");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 7);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_hermit", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            quest_hero_of_tatooine_hermit_action_face_to(player, npc);
            string_id message = new string_id(c_stringFile, "s_48a263f0");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_hermit_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1bb7b1eb");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId", 28);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_hermit", message, responses);
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
        if (!conversationId.equals("quest_hero_of_tatooine_hermit"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
        if (branchId == 1 && quest_hero_of_tatooine_hermit_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && quest_hero_of_tatooine_hermit_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && quest_hero_of_tatooine_hermit_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && quest_hero_of_tatooine_hermit_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && quest_hero_of_tatooine_hermit_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && quest_hero_of_tatooine_hermit_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && quest_hero_of_tatooine_hermit_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && quest_hero_of_tatooine_hermit_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && quest_hero_of_tatooine_hermit_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && quest_hero_of_tatooine_hermit_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && quest_hero_of_tatooine_hermit_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && quest_hero_of_tatooine_hermit_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && quest_hero_of_tatooine_hermit_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && quest_hero_of_tatooine_hermit_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && quest_hero_of_tatooine_hermit_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && quest_hero_of_tatooine_hermit_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && quest_hero_of_tatooine_hermit_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && quest_hero_of_tatooine_hermit_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && quest_hero_of_tatooine_hermit_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && quest_hero_of_tatooine_hermit_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && quest_hero_of_tatooine_hermit_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && quest_hero_of_tatooine_hermit_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && quest_hero_of_tatooine_hermit_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && quest_hero_of_tatooine_hermit_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && quest_hero_of_tatooine_hermit_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && quest_hero_of_tatooine_hermit_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && quest_hero_of_tatooine_hermit_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && quest_hero_of_tatooine_hermit_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && quest_hero_of_tatooine_hermit_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_hermit.branchId");
        return SCRIPT_CONTINUE;
    }
}
