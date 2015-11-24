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
import script.library.utils;

public class c_newbie_melee_combat extends script.base_script
{
    public c_newbie_melee_combat()
    {
    }
    public static String c_stringFile = "conversation/c_newbie_melee_combat";
    public boolean c_newbie_melee_combat_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_newbie_melee_combat_condition_sentByMayor(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "c_melee_combat_trainer", "newbie_goto_peawp_02") || groundquests.hasCompletedQuest(player, "c_melee_combat_trainer");
    }
    public boolean c_newbie_melee_combat_condition_completedPeawpQuests(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "newbie_mayor_vaigon_shinn", "mayor_vaigon_shinn_03") || groundquests.isTaskActive(player, "newbie_mayor_vaigon_shinn", "mayor_vaigon_shinn_04") || groundquests.hasCompletedQuest(player, "newbie_mayor_vaigon_shinn"));
    }
    public boolean c_newbie_melee_combat_condition_returningViagonShinn(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "newbie_mayor_vaigon_shinn", "mayor_vaigon_shinn_02") || groundquests.hasCompletedQuest(player, "newbie_mayor_vaigon_shinn");
    }
    public boolean c_newbie_melee_combat_condition_onSwoopers(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "newbie_mayor_hooligans_swoopers") || groundquests.isQuestActive(player, "newbie_mayor_hooligans_swoopers_2"));
    }
    public boolean c_newbie_melee_combat_condition_returningSwoopers(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "newbie_mayor_hooligans_swoopers", "mayor_hooligans_swoopers_02") || groundquests.hasCompletedQuest(player, "newbie_mayor_hooligans_swoopers") || groundquests.hasCompletedQuest(player, "newbie_mayor_hooligans_swoopers_2"));
    }
    public boolean c_newbie_melee_combat_condition_onScavengers(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "newbie_mayor_hooligans_water_thief", "mayor_water_thief_00") || groundquests.isTaskActive(player, "newbie_mayor_hooligans_water_thief_2", "mayor_water_thief_00"));
    }
    public boolean c_newbie_melee_combat_condition_returningWaterThief(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "newbie_mayor_hooligans_water_thief", "mayor_water_thief_02") || groundquests.hasCompletedQuest(player, "newbie_mayor_hooligans_water_thief") || groundquests.hasCompletedQuest(player, "newbie_mayor_hooligans_water_thief_2"));
    }
    public boolean c_newbie_melee_combat_condition_onWaterThief(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "newbie_mayor_hooligans_water_thief", "mayor_water_thief_00") || groundquests.hasCompletedTask(player, "newbie_mayor_hooligans_water_thief_2", "mayor_water_thief_00"));
    }
    public boolean c_newbie_melee_combat_condition_returningGunrunners(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "newbie_mayor_hooligans_gunrunners", "mayor_gunrunner_02") || groundquests.hasCompletedQuest(player, "newbie_mayor_hooligans_gunrunners") || groundquests.hasCompletedQuest(player, "newbie_mayor_hooligans_gunrunners_2"));
    }
    public boolean c_newbie_melee_combat_condition_onGunrunners(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "newbie_mayor_hooligans_gunrunners") || groundquests.isQuestActive(player, "newbie_mayor_hooligans_gunrunners_2"));
    }
    public boolean c_newbie_melee_combat_condition_onDroids(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "newbie_mayor_hooligans_criminals", "mayor_criminals_00") || groundquests.isTaskActive(player, "newbie_mayor_hooligans_criminals_2", "mayor_criminals_00"));
    }
    public boolean c_newbie_melee_combat_condition_returningCriminals(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "newbie_mayor_hooligans_criminals", "mayor_criminals_02") || groundquests.hasCompletedQuest(player, "newbie_mayor_hooligans_criminals_2") || groundquests.hasCompletedQuest(player, "newbie_mayor_hooligans_criminals"));
    }
    public boolean c_newbie_melee_combat_condition_onCriminals(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "newbie_mayor_hooligans_criminals", "mayor_criminals_00") || groundquests.hasCompletedTask(player, "newbie_mayor_hooligans_criminals_2", "mayor_criminals_00"));
    }
    public boolean c_newbie_melee_combat_condition_returningMuggers(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "newbie_mayor_shinn_muggers", "mayor_shinn_mugger_02") || groundquests.hasCompletedQuest(player, "newbie_mayor_shinn_muggers") || groundquests.hasCompletedQuest(player, "newbie_mayor_shinn_muggers_2"));
    }
    public boolean c_newbie_melee_combat_condition_onMuggers(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "newbie_mayor_shinn_muggers") || groundquests.isQuestActive(player, "newbie_mayor_shinn_muggers_2"));
    }
    public boolean c_newbie_melee_combat_condition_onVaigonShinn(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "newbie_mayor_vaigon_shinn");
    }
    public void c_newbie_melee_combat_action_sendToMayor(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mayor_vaigon_shinn_02");
    }
    public void c_newbie_melee_combat_action_signal_pickingPocket(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "peawpsPocketPicked");
    }
    public void c_newbie_melee_combat_action_grantSwoopers(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "newbie_mayor_hooligans_swoopers_2");
    }
    public void c_newbie_melee_combat_action_grantScavengers(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "newbie_mayor_hooligans_scavengers");
    }
    public void c_newbie_melee_combat_action_grantWaterThief(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "newbie_mayor_hooligans_water_thief_2");
    }
    public void c_newbie_melee_combat_action_grantGunrunners(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "newbie_mayor_hooligans_gunrunners_2");
    }
    public void c_newbie_melee_combat_action_grantCriminals(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "newbie_mayor_hooligans_criminals_2");
    }
    public void c_newbie_melee_combat_action_grantMuggers(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "newbie_mayor_shinn_muggers_2");
    }
    public void c_newbie_melee_combat_action_grantVaigonShin(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "newbie_mayor_vaigon_shinn");
    }
    public void c_newbie_melee_combat_action_endSwoopers(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mayor_hooligans_swoopers_02");
    }
    public void c_newbie_melee_combat_action_endWaterThief(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mayor_water_thief_02");
    }
    public void c_newbie_melee_combat_action_endGunrunners(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mayor_gunrunner_02");
    }
    public void c_newbie_melee_combat_action_endCriminals(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mayor_criminals_02");
    }
    public void c_newbie_melee_combat_action_endMuggers(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "mayor_shinn_mugger_02");
    }
    public void c_newbie_melee_combat_action_endFromMayor(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "newbie_goto_peawp_02");
    }
    public int c_newbie_melee_combat_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                c_newbie_melee_combat_action_sendToMayor(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                c_newbie_melee_combat_action_grantVaigonShin(player, npc);
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            doAnimationAction(player, "nervous");
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_97");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99");
                    }
                    utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_99"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                c_newbie_melee_combat_action_grantMuggers(player, npc);
                string_id message = new string_id(c_stringFile, "s_101");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_93"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                c_newbie_melee_combat_action_grantCriminals(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                c_newbie_melee_combat_action_grantGunrunners(player, npc);
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                c_newbie_melee_combat_action_grantWaterThief(player, npc);
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_newbie_melee_combat_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                c_newbie_melee_combat_action_grantSwoopers(player, npc);
                string_id message = new string_id(c_stringFile, "s_87");
                utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
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
            detachScript(self, "conversation.c_newbie_melee_combat");
        }
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, new string_id("npc_name", "peawp_rdawc"));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, new string_id("npc_name", "peawp_rdawc"));
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
        detachScript(self, "conversation.c_newbie_melee_combat");
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
        if (c_newbie_melee_combat_condition_completedPeawpQuests(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "greet");
            string_id message = new string_id(c_stringFile, "s_204");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 1);
                npcStartConversation(player, npc, "c_newbie_melee_combat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_returningViagonShinn(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_19");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 3);
                npcStartConversation(player, npc, "c_newbie_melee_combat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_onVaigonShinn(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_returningMuggers(player, npc))
        {
            c_newbie_melee_combat_action_endMuggers(player, npc);
            string_id message = new string_id(c_stringFile, "s_47");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 6);
                npcStartConversation(player, npc, "c_newbie_melee_combat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_onMuggers(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_49");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_returningCriminals(player, npc))
        {
            c_newbie_melee_combat_action_endCriminals(player, npc);
            string_id message = new string_id(c_stringFile, "s_50");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 9);
                npcStartConversation(player, npc, "c_newbie_melee_combat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_onCriminals(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_51");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 12);
                npcStartConversation(player, npc, "c_newbie_melee_combat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_onDroids(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_53");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_returningGunrunners(player, npc))
        {
            c_newbie_melee_combat_action_endGunrunners(player, npc);
            string_id message = new string_id(c_stringFile, "s_29");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 15);
                npcStartConversation(player, npc, "c_newbie_melee_combat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_onGunrunners(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_returningWaterThief(player, npc))
        {
            c_newbie_melee_combat_action_endWaterThief(player, npc);
            string_id message = new string_id(c_stringFile, "s_39");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 18);
                npcStartConversation(player, npc, "c_newbie_melee_combat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_onWaterThief(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_40");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_onScavengers(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_63");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_returningSwoopers(player, npc))
        {
            c_newbie_melee_combat_action_endSwoopers(player, npc);
            string_id message = new string_id(c_stringFile, "s_65");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                }
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 22);
                npcStartConversation(player, npc, "c_newbie_melee_combat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_onSwoopers(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_205");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition_sentByMayor(player, npc))
        {
            c_newbie_melee_combat_action_endFromMayor(player, npc);
            string_id message = new string_id(c_stringFile, "s_206");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.c_newbie_melee_combat.branchId", 25);
                npcStartConversation(player, npc, "c_newbie_melee_combat", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_newbie_melee_combat_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_64");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_newbie_melee_combat"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
        if (branchId == 1 && c_newbie_melee_combat_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && c_newbie_melee_combat_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && c_newbie_melee_combat_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_newbie_melee_combat_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && c_newbie_melee_combat_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && c_newbie_melee_combat_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && c_newbie_melee_combat_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && c_newbie_melee_combat_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && c_newbie_melee_combat_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && c_newbie_melee_combat_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && c_newbie_melee_combat_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_newbie_melee_combat.branchId");
        return SCRIPT_CONTINUE;
    }
}
