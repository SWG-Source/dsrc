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
import script.library.factions;

public class corellia_imperial_dark_trooper extends script.base_script
{
    public corellia_imperial_dark_trooper()
    {
    }
    public static String c_stringFile = "conversation/corellia_imperial_dark_trooper";
    public boolean corellia_imperial_dark_trooper_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public int corellia_imperial_dark_trooper_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c7770eea"))
        {
            if (corellia_imperial_dark_trooper_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dd12949d");
                removeObjVar(player, "conversation.corellia_imperial_dark_trooper.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_eb9887db"))
        {
            if (corellia_imperial_dark_trooper_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9cea209b");
                removeObjVar(player, "conversation.corellia_imperial_dark_trooper.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dacdfa1f"))
        {
            if (corellia_imperial_dark_trooper_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5036d464");
                removeObjVar(player, "conversation.corellia_imperial_dark_trooper.branchId");
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
            detachScript(self, "conversation.corellia_imperial_dark_trooper");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
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
        detachScript(self, "conversation.corellia_imperial_dark_trooper");
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
        if (corellia_imperial_dark_trooper_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a0b913c2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_dark_trooper_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_imperial_dark_trooper_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corellia_imperial_dark_trooper_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c7770eea");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_eb9887db");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dacdfa1f");
                }
                setObjVar(player, "conversation.corellia_imperial_dark_trooper.branchId", 1);
                npcStartConversation(player, npc, "corellia_imperial_dark_trooper", message, responses);
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
        if (!conversationId.equals("corellia_imperial_dark_trooper"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = getIntObjVar(player, "conversation.corellia_imperial_dark_trooper.branchId");
        if (branchId == 1 && corellia_imperial_dark_trooper_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.corellia_imperial_dark_trooper.branchId");
        return SCRIPT_CONTINUE;
    }
}
