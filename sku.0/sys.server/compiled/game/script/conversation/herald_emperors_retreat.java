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

public class herald_emperors_retreat extends script.base_script
{
    public herald_emperors_retreat()
    {
    }
    public static String c_stringFile = "conversation/herald_emperors_retreat";
    public boolean herald_emperors_retreat_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void herald_emperors_retreat_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void herald_emperors_retreat_action_waypoint1(obj_id player, obj_id npc) throws InterruptedException
    {
        location retreat = new location(2461, 292, -3925);
        obj_id waypoint = createWaypointInDatapad(player, retreat);
        setWaypointName(waypoint, "Emperor's Retreat");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.herald_emperors_retreat");
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
        detachScript(self, "npc.conversation.herald_emperors_retreat");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (herald_emperors_retreat_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_9f21b05a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (herald_emperors_retreat_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (herald_emperors_retreat_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ff1f31d2");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_183025b5");
                }
                setObjVar(player, "conversation.herald_emperors_retreat.branchId", 1);
                npcStartConversation(player, self, "herald_emperors_retreat", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("herald_emperors_retreat"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.herald_emperors_retreat.branchId");
        if (branchId == 1 && response.equals("s_ff1f31d2"))
        {
            if (herald_emperors_retreat_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_68f3ba7b");
                removeObjVar(player, "conversation.herald_emperors_retreat.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'An onslaught of recent activity at the Emperor's Retreat has the Rebel Alliance raising their eyebrows. Could the Empire be planning a large attack? Maybe you should check it out.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_183025b5"))
        {
            if (herald_emperors_retreat_condition__defaultCondition(player, self))
            {
                herald_emperors_retreat_action_waypoint1(player, self);
                string_id message = new string_id(c_stringFile, "s_ccdcac3a");
                removeObjVar(player, "conversation.herald_emperors_retreat.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'An onslaught of recent activity at the Emperor's Retreat has the Rebel Alliance raising their eyebrows. Could the Empire be planning a large attack? Maybe you should check it out.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.herald_emperors_retreat.branchId");
        return SCRIPT_CONTINUE;
    }
}
