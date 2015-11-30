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

public class c_ddroid_selan extends script.base_script
{
    public c_ddroid_selan()
    {
    }
    public static String c_stringFile = "conversation/c_ddroid_selan";
    public boolean c_ddroid_selan_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_ddroid_selan_condition_selanStepReady(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_darndroid1");
        int selan = groundquests.getTaskId(questId, "selan");
        boolean onQuest = false;
        onQuest = questIsTaskActive(questId, selan, player);
        return onQuest;
    }
    public void c_ddroid_selan_action_passalong(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_darndroid1_selan");
        obj_id top = getTopMostContainer(player);
        location loc = getLocation(top);
        loc.x = -5026;
        loc.y = 0;
        loc.z = -2259;
        loc.area = "corellia";
        obj_id waypoint = createWaypointInDatapad(player, loc);
        setObjVar(player, "quest.general.quest/c_darndroid1.amarent", waypoint);
        setWaypointName(waypoint, "Amarent Loren");
        setWaypointActive(waypoint, true);
        obj_id waypoint1 = getObjIdObjVar(player, "quest.general.quest/c_darndroid1.moeniastarport");
        if (waypoint1 != null)
        {
            destroyWaypointInDatapad(waypoint1, player);
        }
        removeObjVar(player, "quest.general.quest/c_darndroid1.moeniastarport");
    }
    public void c_ddroid_selan_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public int c_ddroid_selan_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_215"))
        {
            if (c_ddroid_selan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_219");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_selan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_223");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_selan.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_selan.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_selan_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_223"))
        {
            if (c_ddroid_selan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_227");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_ddroid_selan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_231");
                    }
                    utils.setScriptVar(player, "conversation.c_ddroid_selan.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_ddroid_selan.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_ddroid_selan_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_231"))
        {
            if (c_ddroid_selan_condition__defaultCondition(player, npc))
            {
                c_ddroid_selan_action_passalong(player, npc);
                string_id message = new string_id(c_stringFile, "s_235");
                utils.removeScriptVar(player, "conversation.c_ddroid_selan.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.c_ddroid_selan");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Selan Ellison");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Selan Ellison");
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
        detachScript(self, "conversation.c_ddroid_selan");
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
        if (c_ddroid_selan_condition_selanStepReady(player, npc))
        {
            c_ddroid_selan_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_211");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_ddroid_selan_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_215");
                }
                utils.setScriptVar(player, "conversation.c_ddroid_selan.branchId", 1);
                npcStartConversation(player, npc, "c_ddroid_selan", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!c_ddroid_selan_condition_selanStepReady(player, npc))
        {
            c_ddroid_selan_action_faceplayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_239");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_ddroid_selan"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_ddroid_selan.branchId");
        if (branchId == 1 && c_ddroid_selan_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && c_ddroid_selan_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && c_ddroid_selan_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_ddroid_selan.branchId");
        return SCRIPT_CONTINUE;
    }
}
