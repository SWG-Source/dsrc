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
import script.library.utils;

public class sean_contact extends script.base_script
{
    public sean_contact()
    {
    }
    public static String c_stringFile = "conversation/sean_contact";
    public boolean sean_contact_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean sean_contact_condition_hasItem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionWinner"))
        {
            String winner = getStringObjVar(npc, "bestine.electionWinner");
            if ((winner.equals("Sean")) || (winner.equals("sean")))
            {
                if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_history_disk.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean sean_contact_condition_notInoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionWinner"))
        {
            String winner = getStringObjVar(npc, "bestine.electionWinner");
            if ((!winner.equals("Sean")) && (!winner.equals("sean")))
            {
                return true;
            }
        }
        return false;
    }
    public void sean_contact_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void sean_contact_action_Destroyed(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id disk = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/quest/sean_history_disk.iff");
        if (isIdValid(disk))
        {
            destroyObject(disk);
            setObjVar(player, "bestine.destroyed", true);
            removeObjVar(player, "bestine.mess");
            obj_id waypoint = getObjIdObjVar(player, "bestine.contactWaypoint");
            if (isIdValid(waypoint))
            {
                destroyWaypointInDatapad(waypoint, player);
            }
            removeObjVar(player, "bestine.contactWaypoint");
        }
    }
    public void sean_contact_action_cleanUpObjVars(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_history_disk.iff"))
        {
            if (hasObjVar(player, "bestine.mess"))
            {
                removeObjVar(player, "bestine.mess");
            }
            if (hasObjVar(player, "bestine.itemfound"))
            {
                removeObjVar(player, "bestine.itemfound");
            }
        }
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.sean_contact");
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
        detachScript(self, "npc.conversation.sean_contact");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (sean_contact_condition_hasItem(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_1e325cde");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sean_contact_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_829888a9");
                }
                setObjVar(player, "conversation.sean_contact.branchId", 1);
                npcStartConversation(player, self, "sean_contact", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sean_contact_condition_notInoffice(player, self))
        {
            sean_contact_action_cleanUpObjVars(player, self);
            string_id message = new string_id(c_stringFile, "s_f1baf7f");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (sean_contact_condition__defaultCondition(player, self))
        {
            sean_contact_action_cleanUpObjVars(player, self);
            string_id message = new string_id(c_stringFile, "s_c17cd032");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("sean_contact"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.sean_contact.branchId");
        if (branchId == 1 && response.equals("s_829888a9"))
        {
            if (sean_contact_condition__defaultCondition(player, self))
            {
                sean_contact_action_Destroyed(player, self);
                string_id message = new string_id(c_stringFile, "s_3e134731");
                removeObjVar(player, "conversation.sean_contact.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So he's got himself into more trouble I see. I just heard the news. Let me have that disk. He'll be grateful to you.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.sean_contact.branchId");
        return SCRIPT_CONTINUE;
    }
}
