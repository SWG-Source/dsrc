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
    public static final String HELLO_DEARIE = "hello_dearie";
    public static final String NOT_IN_MY_PLANS = "not_in_my_plans";
    public static final String SUITING_PURPOSE = "suiting_purpose";
    public static final String SPEAK_TO_RUBINA_AGAIN = "speak_to_rubina_again";
    public static final String BRANCH_ID = "conversation.wod_rubina.branchId";
    public static final int BRANCH_INTRODUCTION = 1;


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
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, BRANCH_ID);
        return SCRIPT_CONTINUE;
    }
}
