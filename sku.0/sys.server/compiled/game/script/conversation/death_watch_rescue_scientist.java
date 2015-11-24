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
import script.library.money;

public class death_watch_rescue_scientist extends script.base_script
{
    public death_watch_rescue_scientist()
    {
    }
    public static String c_stringFile = "conversation/death_watch_rescue_scientist";
    public boolean death_watch_rescue_scientist_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean death_watch_rescue_scientist_condition_RebelCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null)
        {
            return false;
        }
        if (playerFaction.equals("Rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_rescue_scientist_condition_ImperialCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null)
        {
            return false;
        }
        if (playerFaction.equals("Imperial"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_rescue_scientist_condition_NeutralCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null)
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_rescue_scientist_condition_AlreadyRescuedCheck(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch_herald.neutralrescue"))
        {
            return true;
        }
        if (hasObjVar(player, "death_watch_herald.imperialrescue"))
        {
            return true;
        }
        if (hasObjVar(player, "death_watch_herald.rebelrescue"))
        {
            return true;
        }
        return false;
    }
    public void death_watch_rescue_scientist_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void death_watch_rescue_scientist_action_RewardRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch_herald.rebelfinish"))
        {
            if (!hasObjVar(player, "death_watch_herald.rebelrescue"))
            {
                factions.addFactionStanding(player, "Rebel", 30);
                setObjVar(player, "death_watch_herald.rebelrescue", true);
            }
        }
        return;
    }
    public void death_watch_rescue_scientist_action_RewardImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch_herald.imperialrescue"))
        {
            factions.addFactionStanding(player, "Imperial", 30);
            setObjVar(player, "death_watch_herald.imperialrescue", true);
        }
        return;
    }
    public void death_watch_rescue_scientist_action_RewardNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "death_watch_herald.neutralrescue"))
        {
            int credits = 487;
            if (credits != 0)
            {
                money.bankTo(money.ACCT_JABBA, player, credits);
                sendSystemMessage(player, credits + " credits have been deposited in your bank account.", null);
                setObjVar(player, "death_watch_herald.neutralrescue", true);
            }
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.death_watch_rescue_scientist");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Hetl Darkrunner");
        setInvulnerable(self, true);
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
        detachScript(self, "npc.conversation.death_watch_rescue_scientist");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (death_watch_rescue_scientist_condition_AlreadyRescuedCheck(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_bbc873d5");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_rescue_scientist_condition_RebelCheck(player, self))
        {
            death_watch_rescue_scientist_action_RewardRebel(player, self);
            string_id message = new string_id(c_stringFile, "s_d3b5ac87");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_rescue_scientist_condition_ImperialCheck(player, self))
        {
            death_watch_rescue_scientist_action_RewardImperial(player, self);
            string_id message = new string_id(c_stringFile, "s_8801c9a5");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_rescue_scientist_condition_NeutralCheck(player, self))
        {
            death_watch_rescue_scientist_action_RewardNeutral(player, self);
            string_id message = new string_id(c_stringFile, "s_b4b8c363");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("death_watch_rescue_scientist"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.death_watch_rescue_scientist.branchId");
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.death_watch_rescue_scientist.branchId");
        return SCRIPT_CONTINUE;
    }
}
