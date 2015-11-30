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
import script.library.features;
import script.library.skill;
import script.library.space_quest;
import script.library.space_skill;
import script.library.utils;

public class imperial_master_trooper extends script.base_script
{
    public imperial_master_trooper()
    {
    }
    public static String c_stringFile = "conversation/imperial_master_trooper";
    public boolean imperial_master_trooper_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean imperial_master_trooper_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metNewbiePilot"));
    }
    public boolean imperial_master_trooper_condition_isImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean imperial_master_trooper_condition_isRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean imperial_master_trooper_condition_isPrivateerPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_neutral_novice");
    }
    public boolean imperial_master_trooper_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean imperial_master_trooper_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasShip(player));
    }
    public void imperial_master_trooper_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metNewbiePilot", true);
    }
    public void imperial_master_trooper_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery", "tatooine_newbie_1");
        space_quest.grantNewbieShip(player, "rebel");
    }
    public int imperial_master_trooper_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_33788e9b"))
        {
            if (imperial_master_trooper_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_4cf446c4");
                utils.removeScriptVar(player, "conversation.imperial_master_trooper.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_86ed2e98"))
        {
            if (imperial_master_trooper_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_a366a1ca");
                utils.removeScriptVar(player, "conversation.imperial_master_trooper.branchId");
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
            detachScript(self, "conversation.imperial_master_trooper");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Starship Pilot");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Starship Pilot");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.imperial_master_trooper");
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
        if (!imperial_master_trooper_condition_hasSpaceExpansion(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            doAnimationAction(player, "shrug_hands");
            string_id message = new string_id(c_stringFile, "s_28c5a74a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (imperial_master_trooper_condition_isRebelPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_326b2667");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (imperial_master_trooper_condition_isImperialPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7e51c66");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (imperial_master_trooper_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (imperial_master_trooper_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_33788e9b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_86ed2e98");
                }
                utils.setScriptVar(player, "conversation.imperial_master_trooper.branchId", 3);
                npcStartConversation(player, npc, "imperial_master_trooper", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (imperial_master_trooper_condition_isPrivateerPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9049d3d5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (imperial_master_trooper_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_75207c3");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("imperial_master_trooper"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.imperial_master_trooper.branchId");
        if (branchId == 3 && imperial_master_trooper_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.imperial_master_trooper.branchId");
        return SCRIPT_CONTINUE;
    }
}
