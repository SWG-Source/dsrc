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
import script.library.create;
import script.library.groundquests;
import script.library.utils;

public class stap_quest_jawa_droid extends script.base_script
{
    public stap_quest_jawa_droid()
    {
    }
    public static String c_stringFile = "conversation/stap_quest_jawa_droid";
    public boolean stap_quest_jawa_droid_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean stap_quest_jawa_droid_condition_stapQuestActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "lifeday_stap_1", "talkJawaDroid"))
        {
            return true;
        }
        return false;
    }
    public void stap_quest_jawa_droid_action_spawnJawa(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.hasScriptVar(npc, "jawaSpawned.jawa1"))
        {
            location jawa1 = new location(-6163.81f, 22.51f, 1829.93f, getCurrentSceneName(), null);
            location jawa2 = new location(-6161.84f, 22.51f, 1827.90f, getCurrentSceneName(), null);
            location jawa3 = new location(-6159.10f, 22.51f, 1827.56f, getCurrentSceneName(), null);
            location jawa4 = new location(-6156.18f, 22.52f, 1828.24f, getCurrentSceneName(), null);
            location jawa5 = new location(-6153.86f, 22.58f, 1829.93f, getCurrentSceneName(), null);
            obj_id jawaI = create.object("lifeday_jawa", jawa1);
            obj_id jawaII = create.object("lifeday_jawa", jawa2);
            obj_id jawaIII = create.object("lifeday_jawa", jawa3);
            obj_id jawaIV = create.object("lifeday_jawa", jawa4);
            obj_id jawaV = create.object("lifeday_jawa", jawa5);
            ai_lib.setDefaultCalmBehavior(jawaI, ai_lib.BEHAVIOR_SENTINEL);
            ai_lib.setDefaultCalmBehavior(jawaII, ai_lib.BEHAVIOR_SENTINEL);
            ai_lib.setDefaultCalmBehavior(jawaIII, ai_lib.BEHAVIOR_SENTINEL);
            ai_lib.setDefaultCalmBehavior(jawaIV, ai_lib.BEHAVIOR_SENTINEL);
            ai_lib.setDefaultCalmBehavior(jawaV, ai_lib.BEHAVIOR_SENTINEL);
            setYaw(jawaI, 33.0f);
            setYaw(jawaII, 23.0f);
            setYaw(jawaIII, 1.0f);
            setYaw(jawaIV, -26.0f);
            setYaw(jawaV, -52.0f);
            utils.setScriptVar(npc, "jawaSpawned.jawa1", jawaI);
            utils.setScriptVar(npc, "jawaSpawned.jawa2", jawaII);
            utils.setScriptVar(npc, "jawaSpawned.jawa3", jawaIII);
            utils.setScriptVar(npc, "jawaSpawned.jawa4", jawaIV);
            utils.setScriptVar(npc, "jawaSpawned.jawa5", jawaV);
            dictionary params = new dictionary();
            params.put("npc", npc);
            messageTo(npc, "cleanupjawa", params, 30.0f, false);
        }
    }
    public int cleanupjawa(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id npc = params.getObjId("npc");
        if (utils.hasScriptVar(npc, "jawaSpawned.jawa1"))
        {
            obj_id jawa1 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa1");
            obj_id jawa2 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa2");
            obj_id jawa3 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa3");
            obj_id jawa4 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa4");
            obj_id jawa5 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa5");
            if (exists(jawa1) && isIdValid(jawa1))
            {
                destroyObject(jawa1);
            }
            if (exists(jawa2) && isIdValid(jawa2))
            {
                destroyObject(jawa2);
            }
            if (exists(jawa3) && isIdValid(jawa3))
            {
                destroyObject(jawa3);
            }
            if (exists(jawa4) && isIdValid(jawa4))
            {
                destroyObject(jawa4);
            }
            if (exists(jawa5) && isIdValid(jawa5))
            {
                destroyObject(jawa5);
            }
            utils.removeScriptVar(npc, "jawaSpawned.jawa1");
            utils.removeScriptVar(npc, "jawaSpawned.jawa2");
            utils.removeScriptVar(npc, "jawaSpawned.jawa3");
            utils.removeScriptVar(npc, "jawaSpawned.jawa4");
            utils.removeScriptVar(npc, "jawaSpawned.jawa5");
        }
        return SCRIPT_CONTINUE;
    }
    public void stap_quest_jawa_droid_action_finishTask(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedJawa");
        if (utils.hasScriptVar(npc, "jawaSpawned.jawa1"))
        {
            obj_id jawa1 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa1");
            obj_id jawa2 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa2");
            obj_id jawa3 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa3");
            obj_id jawa4 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa4");
            obj_id jawa5 = utils.getObjIdScriptVar(npc, "jawaSpawned.jawa5");
            if (exists(jawa1) && isIdValid(jawa1))
            {
                destroyObject(jawa1);
            }
            if (exists(jawa2) && isIdValid(jawa2))
            {
                destroyObject(jawa2);
            }
            if (exists(jawa3) && isIdValid(jawa3))
            {
                destroyObject(jawa3);
            }
            if (exists(jawa4) && isIdValid(jawa4))
            {
                destroyObject(jawa4);
            }
            if (exists(jawa5) && isIdValid(jawa5))
            {
                destroyObject(jawa5);
            }
            utils.removeScriptVar(npc, "jawaSpawned.jawa1");
            utils.removeScriptVar(npc, "jawaSpawned.jawa2");
            utils.removeScriptVar(npc, "jawaSpawned.jawa3");
            utils.removeScriptVar(npc, "jawaSpawned.jawa4");
            utils.removeScriptVar(npc, "jawaSpawned.jawa5");
        }
    }
    public int stap_quest_jawa_droid_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    utils.setScriptVar(player, "conversation.stap_quest_jawa_droid.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stap_quest_jawa_droid.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stap_quest_jawa_droid_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stap_quest_jawa_droid.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stap_quest_jawa_droid.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stap_quest_jawa_droid_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            doAnimationAction(player, "shake_head_no");
            if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stap_quest_jawa_droid.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stap_quest_jawa_droid.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stap_quest_jawa_droid_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            doAnimationAction(player, "gesticulate_wildly");
            if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.stap_quest_jawa_droid.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.stap_quest_jawa_droid.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int stap_quest_jawa_droid_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shiver");
                stap_quest_jawa_droid_action_finishTask(player, npc);
                string_id message = new string_id(c_stringFile, "s_48");
                utils.removeScriptVar(player, "conversation.stap_quest_jawa_droid.branchId");
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
            detachScript(self, "conversation.stap_quest_jawa_droid");
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
        detachScript(self, "conversation.stap_quest_jawa_droid");
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
        if (stap_quest_jawa_droid_condition_stapQuestActive(player, npc))
        {
            doAnimationAction(player, "greet");
            stap_quest_jawa_droid_action_spawnJawa(player, npc);
            string_id message = new string_id(c_stringFile, "s_13");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                }
                utils.setScriptVar(player, "conversation.stap_quest_jawa_droid.branchId", 1);
                npcStartConversation(player, npc, "stap_quest_jawa_droid", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (stap_quest_jawa_droid_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "taken_aback");
            string_id message = new string_id(c_stringFile, "s_40");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("stap_quest_jawa_droid"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.stap_quest_jawa_droid.branchId");
        if (branchId == 1 && stap_quest_jawa_droid_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && stap_quest_jawa_droid_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && stap_quest_jawa_droid_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && stap_quest_jawa_droid_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && stap_quest_jawa_droid_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.stap_quest_jawa_droid.branchId");
        return SCRIPT_CONTINUE;
    }
}
