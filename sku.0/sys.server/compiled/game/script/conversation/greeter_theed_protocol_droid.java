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
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class greeter_theed_protocol_droid extends script.base_script
{
    public greeter_theed_protocol_droid()
    {
    }
    public static String c_stringFile = "conversation/greeter_theed_protocol_droid";
    public boolean greeter_theed_protocol_droid_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean greeter_theed_protocol_droid_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metNewbiePilot"));
    }
    public boolean greeter_theed_protocol_droid_condition_speaksMonCal(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "language_mon_cal");
    }
    public boolean greeter_theed_protocol_droid_condition_isAnyPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        if (greeter_theed_protocol_droid_condition_isAnImperialPilot(player, npc))
        {
            return true;
        }
        if (greeter_theed_protocol_droid_condition_isPrivateerPilot(player, npc))
        {
            return true;
        }
        if (greeter_theed_protocol_droid_condition_isARebelPilot(player, npc))
        {
            return true;
        }
        return false;
    }
    public boolean greeter_theed_protocol_droid_condition_isAnImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isImperialPilot(player);
    }
    public boolean greeter_theed_protocol_droid_condition_isARebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isRebelPilot(player);
    }
    public boolean greeter_theed_protocol_droid_condition_isPrivateerPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isNeutralPilot(player);
    }
    public boolean greeter_theed_protocol_droid_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean greeter_theed_protocol_droid_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasShip(player));
    }
    public void greeter_theed_protocol_droid_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metNewbiePilot", true);
    }
    public void greeter_theed_protocol_droid_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery", "tatooine_newbie_1");
        space_quest.grantNewbieShip(player, "rebel");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.greeter_theed_protocol_droid");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        detachScript(self, "conversation.greeter_theed_protocol_droid");
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
        if (greeter_theed_protocol_droid_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_2124eaf5");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("greeter_theed_protocol_droid"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.greeter_theed_protocol_droid.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.greeter_theed_protocol_droid.branchId");
        return SCRIPT_CONTINUE;
    }
}
