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
import script.library.conversation;
import script.library.groundquests;
import script.library.loot;
import script.library.space_quest;
import script.library.utils;

public class ep3_kachirho_lolo extends script.base_script
{
    public ep3_kachirho_lolo()
    {
    }
    public static String c_stringFile = "conversation/ep3_kachirho_lolo";
    public boolean ep3_kachirho_lolo_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_kachirho_lolo_condition_isReadyForLolo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ep3_kachirho_trando_rifle", "talkToLolo");
    }
    public boolean ep3_kachirho_lolo_condition_hasBowcasterPieces(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "inspect", "ep3_wke_bowcaster_crafting");
    }
    public boolean ep3_kachirho_lolo_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_kachirho_trando_rifle");
    }
    public boolean ep3_kachirho_lolo_condition_hasFailedSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "inspect", "ep3_wke_bowcaster_crafting");
    }
    public boolean ep3_kachirho_lolo_condition_hasMadeBowcaster(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_kachirho_trando_rifle", "craftBowcaster");
    }
    public boolean ep3_kachirho_lolo_condition_cannotSpeakWke(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "combat_smuggler_underworld_01"))
        {
            return false;
        }
        if (hasSkill(player, "social_language_wookiee_comprehend"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean ep3_kachirho_lolo_condition_hasCompletedSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuest(player, "inspect", "ep3_wke_bowcaster_crafting");
    }
    public void ep3_kachirho_lolo_action_grantSpaceMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "inspect", "ep3_wke_bowcaster_crafting");
    }
    public void ep3_kachirho_lolo_action_grantBarrelSchematic(obj_id player, obj_id npc) throws InterruptedException
    {
        grantSchematic(player, "object/draft_schematic/weapon/component/projectile_rifle_barrel_faux_bowcaster.iff");
        grantSchematic(player, "object/draft_schematic/weapon/appearance/weapon_appearance_rifle_faux_bowcaster.iff");
    }
    public void ep3_kachirho_lolo_action_vocalizeMed(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
    }
    public void ep3_kachirho_lolo_action_grantCraftingComponent(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        if (isIdValid(pInv))
        {
            obj_id objItem = createObjectOverloaded("object/tangible/component/weapon/quest/projectile_feed_mechanism_faux_bowcaster.iff", pInv);
            loot.randomizeComponent(objItem, 175, pInv);
            space_quest.setQuestRewarded(player, "inspect", "ep3_wke_bowcaster_crafting");
        }
    }
    public int ep3_kachirho_lolo_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_255"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_257");
                utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_256"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                ep3_kachirho_lolo_action_grantSpaceMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_258");
                utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_105"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                ep3_kachirho_lolo_action_grantSpaceMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_108");
                utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_106"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_107");
                utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_93"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_97");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_99");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                    }
                    utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_kachirho_lolo_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_103"))
        {
            ep3_kachirho_lolo_action_grantBarrelSchematic(player, npc);
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                ep3_kachirho_lolo_action_grantSpaceMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
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
            detachScript(self, "conversation.ep3_kachirho_lolo");
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
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_kachirho_lolo");
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
        if (ep3_kachirho_lolo_condition_cannotSpeakWke(player, npc))
        {
            ep3_kachirho_lolo_action_vocalizeMed(player, npc);
            string_id message = new string_id(c_stringFile, "s_87");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_lolo_condition_hasCompletedSpace(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_254");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                }
                utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 2);
                npcStartConversation(player, npc, "ep3_kachirho_lolo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_lolo_condition_hasBowcasterPieces(player, npc))
        {
            ep3_kachirho_lolo_action_grantCraftingComponent(player, npc);
            string_id message = new string_id(c_stringFile, "s_88");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_lolo_condition_hasFailedSpaceMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_89");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                }
                utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 6);
                npcStartConversation(player, npc, "ep3_kachirho_lolo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_lolo_condition_isOnMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_90");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_lolo_condition_isReadyForLolo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_91");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.ep3_kachirho_lolo.branchId", 10);
                npcStartConversation(player, npc, "ep3_kachirho_lolo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_kachirho_lolo_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_100");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_kachirho_lolo"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
        if (branchId == 2 && ep3_kachirho_lolo_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_kachirho_lolo_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_kachirho_lolo_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_kachirho_lolo_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_kachirho_lolo_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && ep3_kachirho_lolo_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_kachirho_lolo_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_kachirho_lolo_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_kachirho_lolo_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_kachirho_lolo_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_kachirho_lolo_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && ep3_kachirho_lolo_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_kachirho_lolo_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_kachirho_lolo.branchId");
        return SCRIPT_CONTINUE;
    }
}
