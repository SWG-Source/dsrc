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

public class survivor_01 extends script.base_script
{
    public survivor_01()
    {
    }
    public static String c_stringFile = "conversation/survivor_01";
    public boolean survivor_01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean survivor_01_condition_condition0001(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(npc, "randNum") == 1;
    }
    public boolean survivor_01_condition_condition0002(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(npc, "randNum") == 2;
    }
    public boolean survivor_01_condition_isMySpawner(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return (getObjIdObjVar(npc, "myEscort") == player);
    }
    public boolean survivor_01_condition_condition0003(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(npc, "randNum") == 3;
    }
    public boolean survivor_01_condition_condition0004(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(npc, "randNum") == 4;
    }
    public boolean survivor_01_condition_condition0005(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(npc, "randNum") == 5;
    }
    public void survivor_01_action_runforit(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        survivor_01_action_randomBlurb(player, npc);
        messageTo(npc, "startSurvivorPathing", null, 0.0f, false);
    }
    public void survivor_01_action_randomBlurb(obj_id player, obj_id npc) throws InterruptedException
    {
        int randNum = rand(1, 5);
        if (randNum <= 0 || randNum > 5)
        {
            return;
        }
        setObjVar(npc, "randNum", randNum);
    }
    public void survivor_01_action_removeNeverSpoken(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(npc, "neverSpokenTo");
    }
    public int survivor_01_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            survivor_01_action_runforit(player, npc);
            if (survivor_01_condition_condition0001(player, npc))
            {
                survivor_01_action_removeNeverSpoken(player, npc);
                string_id message = new string_id(c_stringFile, "s_97");
                utils.removeScriptVar(player, "conversation.survivor_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (survivor_01_condition_condition0002(player, npc))
            {
                survivor_01_action_removeNeverSpoken(player, npc);
                string_id message = new string_id(c_stringFile, "s_7");
                utils.removeScriptVar(player, "conversation.survivor_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (survivor_01_condition_condition0003(player, npc))
            {
                survivor_01_action_removeNeverSpoken(player, npc);
                string_id message = new string_id(c_stringFile, "s_8");
                utils.removeScriptVar(player, "conversation.survivor_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (survivor_01_condition_condition0004(player, npc))
            {
                survivor_01_action_removeNeverSpoken(player, npc);
                string_id message = new string_id(c_stringFile, "s_9");
                utils.removeScriptVar(player, "conversation.survivor_01.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (survivor_01_condition_condition0005(player, npc))
            {
                survivor_01_action_removeNeverSpoken(player, npc);
                string_id message = new string_id(c_stringFile, "s_10");
                utils.removeScriptVar(player, "conversation.survivor_01.branchId");
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
            detachScript(self, "conversation.survivor_01");
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
        detachScript(self, "conversation.survivor_01");
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
        if (survivor_01_condition_isMySpawner(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_73");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (survivor_01_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.survivor_01.branchId", 1);
                npcStartConversation(player, npc, "survivor_01", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (survivor_01_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_60");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("survivor_01"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.survivor_01.branchId");
        if (branchId == 1 && survivor_01_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.survivor_01.branchId");
        return SCRIPT_CONTINUE;
    }
}
