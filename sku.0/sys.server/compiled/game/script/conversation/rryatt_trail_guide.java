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

public class rryatt_trail_guide extends script.base_script
{
    public rryatt_trail_guide()
    {
    }
    public static String c_stringFile = "conversation/rryatt_trail_guide";
    public boolean rryatt_trail_guide_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void rryatt_trail_guide_action_zoneToNextLevel(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("zoneIn", false);
        if (groundquests.isQuestActive(player, "ep3_rryatt_trail_mastery"))
        {
            String level = getStringObjVar(npc, "zoneLine");
            if (level.equals("rryattOne_rryattTwo"))
            {
                groundquests.sendSignal(player, "signalLevelOne");
            }
            if (level.equals("rryattTwo_rryattThree"))
            {
                groundquests.sendSignal(player, "signalLevelTwo");
            }
            if (level.equals("rryattThree_rryattFour"))
            {
                groundquests.sendSignal(player, "signalLevelThree");
            }
            if (level.equals("rryattFour_rryattFive"))
            {
                groundquests.sendSignal(player, "signalLevelFour");
            }
        }
        setObjVar(player, "lastRryattTransition", npc);
        messageTo(npc, "handleZoneTransitionRequest", params, 1, false);
    }
    public void rryatt_trail_guide_action_returnToZoneIn(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("zoneIn", true);
        params.put("destination", "empty");
        messageTo(npc, "handleZoneTransitionRequest", params, 0, false);
        setObjVar(player, "lastRryattTransition", npc);
    }
    public int rryatt_trail_guide_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (rryatt_trail_guide_condition__defaultCondition(player, npc))
            {
                rryatt_trail_guide_action_zoneToNextLevel(player, npc);
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.rryatt_trail_guide.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_67"))
        {
            if (rryatt_trail_guide_condition__defaultCondition(player, npc))
            {
                rryatt_trail_guide_action_returnToZoneIn(player, npc);
                string_id message = new string_id(c_stringFile, "s_69");
                utils.removeScriptVar(player, "conversation.rryatt_trail_guide.branchId");
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
            detachScript(self, "conversation.rryatt_trail_guide");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        messageTo(self, "removeRryattTrailGuideAiScripts", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        messageTo(self, "removeRryattTrailGuideAiScripts", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int removeRryattTrailGuideAiScripts(obj_id self, dictionary params) throws InterruptedException
    {
        String[] aiScripts = 
        {
            "ai.ai",
            "ai.creature_combat",
            "systems.combat.combat_actions"
        };
        for (int i = 0; i < aiScripts.length; i++)
        {
            if (hasScript(self, aiScripts[i]))
            {
                detachScript(self, aiScripts[i]);
            }
        }
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
        detachScript(self, "conversation.rryatt_trail_guide");
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
        if (rryatt_trail_guide_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_61");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (rryatt_trail_guide_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (rryatt_trail_guide_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                }
                utils.setScriptVar(player, "conversation.rryatt_trail_guide.branchId", 1);
                npcStartConversation(player, npc, "rryatt_trail_guide", message, responses);
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
        if (!conversationId.equals("rryatt_trail_guide"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.rryatt_trail_guide.branchId");
        if (branchId == 1 && rryatt_trail_guide_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.rryatt_trail_guide.branchId");
        return SCRIPT_CONTINUE;
    }
}
