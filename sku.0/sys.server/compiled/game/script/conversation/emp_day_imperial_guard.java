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
import script.library.utils;

public class emp_day_imperial_guard extends script.base_script
{
    public emp_day_imperial_guard()
    {
    }
    public static String c_stringFile = "conversation/emp_day_imperial_guard";
    public boolean emp_day_imperial_guard_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean emp_day_imperial_guard_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return false;
        }
        if (whichFaction.equals("Rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean emp_day_imperial_guard_condition_allowed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "event.emp_day.prisoner"))
        {
            return false;
        }
        int emp_day_imperial_guard_condition_allowed = getIntObjVar(player, "event.emp_day.prisoner");
        return (emp_day_imperial_guard_condition_allowed == 0);
    }
    public boolean emp_day_imperial_guard_condition_winner(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "event.emp_day.prisoner"))
        {
            return false;
        }
        int emp_day_imperial_guard_condition_allowed = getIntObjVar(player, "event.emp_day.prisoner");
        return (emp_day_imperial_guard_condition_allowed == 1);
    }
    public boolean emp_day_imperial_guard_condition_loser(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "event.emp_day.prisoner"))
        {
            return false;
        }
        int emp_day_imperial_guard_condition_allowed = getIntObjVar(player, "event.emp_day.prisoner");
        return (emp_day_imperial_guard_condition_allowed == 2);
    }
    public boolean emp_day_imperial_guard_condition_rewarded(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "event.emp_day.prisoner"))
        {
            return false;
        }
        int emp_day_imperial_guard_condition_allowed = getIntObjVar(player, "event.emp_day.prisoner");
        return (emp_day_imperial_guard_condition_allowed == 3);
    }
    public void emp_day_imperial_guard_action_winReward(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id createdObject = createObject("object/tangible/furniture/all/bestine_quest_imp_banner.iff", playerInventory, "");
        if (isIdValid(createdObject))
        {
            setObjVar(player, "event.emp_day.prisoner", 3);
            factions.addFactionStanding(player, "Imperial", 1000);
        }
        else 
        {
            sendSystemMessage(player, new string_id("quest/lifeday/lifeday", "full_inv"));
        }
    }
    public void emp_day_imperial_guard_action_loseReward(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "event.emp_day.prisoner", 3);
        factions.addFactionStanding(player, "Imperial", 1000);
    }
    public int emp_day_imperial_guard_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_502"))
        {
            if (emp_day_imperial_guard_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_504");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_guard.branchId");
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
            detachScript(self, "conversation.emp_day_imperial_guard");
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
        detachScript(self, "conversation.emp_day_imperial_guard");
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
        if (emp_day_imperial_guard_condition_isRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_494");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_guard_condition_winner(player, npc))
        {
            emp_day_imperial_guard_action_winReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_496");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_guard_condition_loser(player, npc))
        {
            emp_day_imperial_guard_action_loseReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_498");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_guard_condition_allowed(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_500");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_imperial_guard_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_502");
                }
                utils.setScriptVar(player, "conversation.emp_day_imperial_guard.branchId", 4);
                npcStartConversation(player, npc, "emp_day_imperial_guard", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_guard_condition_rewarded(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_506");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!emp_day_imperial_guard_condition_allowed(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_508");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("emp_day_imperial_guard"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.emp_day_imperial_guard.branchId");
        if (branchId == 4 && emp_day_imperial_guard_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.emp_day_imperial_guard.branchId");
        return SCRIPT_CONTINUE;
    }
}
