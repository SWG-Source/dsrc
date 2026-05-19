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
import script.library.utils;

public class wod_rubina extends script.base_script
{
    public wod_rubina()
    {
    }
 
    public static String c_stringFile = "conversation/wod_rubina";
    public static final String OBJVAR_WOD_PROLOGUE_QUESTS = "wod_prologue_quests";
    public static final String QUEST_RUBINA_GOTO_NS = "wod_rubina_goto_ns";
    public static final String QUEST_RUBINA_GOTO_SM = "wod_rubina_goto_sm";
    public static final int STATUS_FULL_SUPPORT = 7;
    public static final int STATUS_MOST_SUPPORT = 4;
    public static final int STATUS_SLIGHT_SUPPORT = 1;
    public static final String HELLO_DEARIE = "hello_dearie";
    public static final String NOT_IN_MY_PLANS = "not_in_my_plans";
    public static final String SUITING_PURPOSE = "suiting_purpose";
    public static final String SPEAK_TO_RUBINA_AGAIN = "speak_to_rubina_again";
    public static final String ASSIST_WITH_SMALL_TASKS = "assist_with_small_tasks";
    public static final String OK_GIVE_ME_A_TASK = "ok_give_me_a_task";
    public static final String MORE_INFORMATION = "more_information";
    public static final String HOW_AM_I_DOING = "how_am_i_doing";
    public static final String SMALL_TASKS_TASK_REPLY = "small_tasks_task_reply";
    public static final String SMALL_TASKS_INFO_REPLY = "task_more_info";
    public static final String CAN_STATUS_READY_NS = "clan_status_ready_ns";
    public static final String CAN_STATUS_READY_SM = "clan_status_ready_sm";
    public static final String CAN_STATUS_FULL_NS = "clan_status_full_ns";
    public static final String CAN_STATUS_FULL_SM = "clan_status_full_sm";
    public static final String CAN_STATUS_MOST_NS = "clan_status_most_ns";
    public static final String CAN_STATUS_MOST_SM = "clan_status_most_sm";
    public static final String CAN_STATUS_SLIGHT_NS = "clan_status_slight_ns";
    public static final String CAN_STATUS_SLIGHT_SM = "clan_status_slight_sm";
    public static final String CAN_STATUS_ONE_QUEST = "clan_status_one_quest";
    public static final String CAN_STATUS_BALANCE = "clan_status_balance";
    public static final String WHY_GAIN_FAVOR = "why_gain_favor";
    public static final String HELP_ONE_CLAN = "help_one_clan";
    public static final String HOW_CHANGE_CLANS = "how_change_clans";
    public static final String WHY_INFILTRATE_CLAN = "why_infiltrate_clan";
    public static final String WHO_WITCHES = "who_witches";
    public static final String WHO_SINGING_MOUNTAIN = "who_singing_mountain";
    public static final String WHO_NIGHTSISTERS = "who_nightsisters";
    public static final String WHO_SPIDER_CLAN = "who_spider_clan";
    public static final String WITCHES_USE_MAGIC = "witches_use_magic";
    public static final String WHAT_DO_AGAIN = "what_do_again";
    public static final String RUBINA_EXPLAINS_FAVOR = "rubina_explains_favor";
    public static final String RUBINA_EXPLAINS_CLAN_OPPOSITION = "rubina_explains_clan_opposition";
    public static final String RUBINA_EXPLAINS_HOW_CHANGE_CLANS = "rubina_explains_how_change_clans";
    public static final String RUBINA_EXPLAINS_INFILTRATION = "rubina_explains_infiltration";
    public static final String RUBINA_EXPLAINS_WITCHES = "rubina_explains_witches";
    public static final String RUBINA_EXPLAINS_SMC = "rubina_explains_smc";
    public static final String RUBINA_EXPLAINS_NS = "rubina_explains_ns";
    public static final String RUBINA_EXPLAINS_SPIDER_CLAN = "rubina_explains_spider_clan";
    public static final String RUBINA_EXPLAINS_MAGIC = "rubina_explains_magic";
    public static final String RUBINA_EXPLAINS_TASKS = "rubina_explains_tasks";
    public static final String TASK_MORE_INFO_REPLY = "task_more_info_reply";
    public static final String BRANCH_ID = "conversation.wod_rubina.branchId";
    public static final int BRANCH_INTRODUCTION = 1;
    public static final int BRANCH_SMALL_TASKS = 2;
    public static final int BRANCH_TASK_MORE_INFO = 3;


    public int wod_rubina_handleIntroduction(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals(SUITING_PURPOSE))
        {
            string_id message = new string_id(c_stringFile, SPEAK_TO_RUBINA_AGAIN);
            groundquests.sendSignal(player, "speakWithRubina");
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, message);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }

    public int wod_rubina_handleSmallTasks(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        string_id message = null;
        if (response.equals(OK_GIVE_ME_A_TASK))
        {
            message = new string_id(c_stringFile, SMALL_TASKS_TASK_REPLY);
        }
        else if (response.equals(MORE_INFORMATION))
        {
            string_id taskMoreInfo = new string_id(c_stringFile, SMALL_TASKS_INFO_REPLY);
            string_id responses[] = new string_id[9];
            responses[0] = new string_id(c_stringFile, WHY_GAIN_FAVOR);
            responses[1] = new string_id(c_stringFile, HOW_CHANGE_CLANS);
            responses[2] = new string_id(c_stringFile, WHY_INFILTRATE_CLAN);
            responses[3] = new string_id(c_stringFile, WHO_WITCHES);
            responses[4] = new string_id(c_stringFile, WHO_SINGING_MOUNTAIN);
            responses[5] = new string_id(c_stringFile, WHO_NIGHTSISTERS);
            responses[6] = new string_id(c_stringFile, WHO_SPIDER_CLAN);
            responses[7] = new string_id(c_stringFile, WITCHES_USE_MAGIC);
            responses[8] = new string_id(c_stringFile, WHAT_DO_AGAIN);
            utils.setScriptVar(player, BRANCH_ID, BRANCH_TASK_MORE_INFO);
            npcSpeak(player, taskMoreInfo);
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(HOW_AM_I_DOING))
        {
            message = wod_rubina_getStatusReply(player, npc);
        }
        if (message != null)
        {
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, message);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }

    public string_id wod_rubina_getStatusReply(obj_id player, obj_id npc) throws InterruptedException
    {
        int questCount = 0;
        if (hasObjVar(player, OBJVAR_WOD_PROLOGUE_QUESTS))
        {
            questCount = getIntObjVar(player, OBJVAR_WOD_PROLOGUE_QUESTS);
        }
        if (questCount > STATUS_FULL_SUPPORT && !groundquests.hasCompletedQuest(player, QUEST_RUBINA_GOTO_NS))
        {
            return new string_id(c_stringFile, CAN_STATUS_READY_NS);
        }
        if (questCount < -STATUS_FULL_SUPPORT && !groundquests.hasCompletedQuest(player, QUEST_RUBINA_GOTO_SM))
        {
            return new string_id(c_stringFile, CAN_STATUS_READY_SM);
        }
        if (questCount < -STATUS_FULL_SUPPORT)
        {
            return new string_id(c_stringFile, CAN_STATUS_FULL_SM);
        }
        if (questCount > STATUS_FULL_SUPPORT)
        {
            return new string_id(c_stringFile, CAN_STATUS_FULL_NS);
        }
        if (questCount < -STATUS_MOST_SUPPORT)
        {
            return new string_id(c_stringFile, CAN_STATUS_MOST_SM);
        }
        if (questCount > STATUS_MOST_SUPPORT)
        {
            return new string_id(c_stringFile, CAN_STATUS_MOST_NS);
        }
        if (questCount < -STATUS_SLIGHT_SUPPORT)
        {
            return new string_id(c_stringFile, CAN_STATUS_SLIGHT_SM);
        }
        if (questCount > STATUS_SLIGHT_SUPPORT)
        {
            return new string_id(c_stringFile, CAN_STATUS_SLIGHT_NS);
        }
        if (questCount == STATUS_SLIGHT_SUPPORT || questCount == -STATUS_SLIGHT_SUPPORT)
        {
            return new string_id(c_stringFile, CAN_STATUS_ONE_QUEST);
        }
        return new string_id(c_stringFile, CAN_STATUS_BALANCE);
    }

    public int wod_rubina_handleTaskMoreInfo(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals(WHY_GAIN_FAVOR))
        {
            string_id message = new string_id(c_stringFile, RUBINA_EXPLAINS_FAVOR);
            string_id responses[] = new string_id[1];
            responses[0] = new string_id(c_stringFile, HELP_ONE_CLAN);
            npcSpeak(player, message);
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(HELP_ONE_CLAN))
        {
            string_id message = new string_id(c_stringFile, RUBINA_EXPLAINS_CLAN_OPPOSITION);
            string_id responses[] = new string_id[1];
            responses[0] = new string_id(c_stringFile, HOW_CHANGE_CLANS);
            npcSpeak(player, message);
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(HOW_CHANGE_CLANS))
        {
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, new string_id(c_stringFile, RUBINA_EXPLAINS_HOW_CHANGE_CLANS));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(WHY_INFILTRATE_CLAN))
        {
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, new string_id(c_stringFile, RUBINA_EXPLAINS_INFILTRATION));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(WHO_WITCHES))
        {
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, new string_id(c_stringFile, RUBINA_EXPLAINS_WITCHES));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(WHO_SINGING_MOUNTAIN))
        {
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, new string_id(c_stringFile, RUBINA_EXPLAINS_SMC));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(WHO_NIGHTSISTERS))
        {
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, new string_id(c_stringFile, RUBINA_EXPLAINS_NS));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(WHO_SPIDER_CLAN))
        {
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, new string_id(c_stringFile, RUBINA_EXPLAINS_SPIDER_CLAN));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(WITCHES_USE_MAGIC))
        {
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, new string_id(c_stringFile, RUBINA_EXPLAINS_MAGIC));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals(WHAT_DO_AGAIN))
        {
            utils.removeScriptVar(player, BRANCH_ID);
            npcEndConversationWithMessage(player, new string_id(c_stringFile, RUBINA_EXPLAINS_TASKS));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }

    public int wod_rubina_endTaskMoreInfo(obj_id player) throws InterruptedException
    {
        utils.removeScriptVar(player, BRANCH_ID);
        npcEndConversationWithMessage(player, new string_id(c_stringFile, TASK_MORE_INFO_REPLY));
        return SCRIPT_CONTINUE;
    }

    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.wod_rubina");
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
		setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }

    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
		setCondition(self, CONDITION_INTERESTING);
        detachScript(self, "conversation.wod_rubina");
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
        if (groundquests.isTaskActive(player, "wod_prologue_walkabout_02", "speakWithRubina"))
        {
            string_id message = new string_id(c_stringFile, HELLO_DEARIE);
            string_id responses[] = new string_id[1];
            responses[0] = new string_id(c_stringFile, SUITING_PURPOSE);
            utils.setScriptVar(player, BRANCH_ID, BRANCH_INTRODUCTION);
            npcStartConversation(player, npc, "wod_rubina", message, responses);
            return SCRIPT_CONTINUE;
        }
        if (groundquests.hasCompletedQuest(player, "wod_prologue_walkabout_02"))
        {
            string_id message = new string_id(c_stringFile, ASSIST_WITH_SMALL_TASKS);
            string_id responses[] = new string_id[3];
            responses[0] = new string_id(c_stringFile, OK_GIVE_ME_A_TASK);
            responses[1] = new string_id(c_stringFile, MORE_INFORMATION);
            responses[2] = new string_id(c_stringFile, HOW_AM_I_DOING);
            utils.setScriptVar(player, BRANCH_ID, BRANCH_SMALL_TASKS);
            npcStartConversation(player, npc, "wod_rubina", message, responses);
            return SCRIPT_CONTINUE;
        }
        string_id message = new string_id(c_stringFile, NOT_IN_MY_PLANS);
        chat.chat(npc, player, message);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("wod_rubina"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;

        int branchId = utils.getIntScriptVar(player, BRANCH_ID);
        if (branchId == BRANCH_INTRODUCTION && wod_rubina_handleIntroduction(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == BRANCH_SMALL_TASKS && wod_rubina_handleSmallTasks(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == BRANCH_TASK_MORE_INFO && wod_rubina_handleTaskMoreInfo(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, BRANCH_ID);
        return SCRIPT_CONTINUE;
    }
}
