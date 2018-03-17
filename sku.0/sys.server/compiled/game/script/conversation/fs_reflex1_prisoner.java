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
import script.library.quests;

public class fs_reflex1_prisoner extends script.base_script
{
    public fs_reflex1_prisoner()
    {
    }
    public static String c_stringFile = "conversation/fs_reflex1_prisoner";
    public boolean fs_reflex1_prisoner_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_reflex1_prisoner_condition_combatNotReady(obj_id player, obj_id npc) throws InterruptedException
    {
        if (ai_lib.isInCombat(npc))
        {
            return true;
        }
        if (!hasObjVar(npc, "player"))
        {
            return true;
        }
        return false;
    }
    public boolean fs_reflex1_prisoner_condition_notCorrectPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "player"))
        {
            obj_id target = getObjIdObjVar(npc, "player");
            if (target != player)
            {
                return true;
            }
        }
        else 
        {
            return true;
        }
        return false;
    }
    public boolean fs_reflex1_prisoner_condition_isSafe(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "safe"))
        {
            return true;
        }
        return false;
    }
    public boolean fs_reflex1_prisoner_condition_isInProgress(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "in_progress"))
        {
            return true;
        }
        return false;
    }
    public boolean fs_reflex1_prisoner_condition_hasFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.fs_reflex1.failed"))
        {
            return true;
        }
        return false;
    }
    public void fs_reflex1_prisoner_action_complete_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        setInvulnerable(npc, false);
        setObjVar(player, "quest.fs_reflex_rescue_quest_03.target", npc);
        setObjVar(player, "quest.fs_reflex_rescue_quest_04.parameter", 96.0f);
        quests.complete("fs_reflex_rescue_quest_02", player, true);
        setObjVar(npc, "in_progress", 1);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_reflex1_prisoner");
        }
        factions.setFaction(self, "fs_villager", false);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        factions.setFaction(self, "fs_villager", false);
        setInvulnerable(self, true);
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
        detachScript(self, "npc.conversation.fs_reflex1_prisoner");
        return SCRIPT_CONTINUE;
    }
    public int handleTaskDestroyOnArrival(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleTaskArrived(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "safe", 1);
        return SCRIPT_CONTINUE;
    }
    public int handleSetupPrisoner(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        setObjVar(self, "player", player);
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
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (fs_reflex1_prisoner_condition_notCorrectPlayer(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e4480cdc");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_reflex1_prisoner_condition_isSafe(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_1150de7c");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_reflex1_prisoner_condition_isInProgress(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e9ddb716");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_reflex1_prisoner_condition_hasFailed(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_780bc309");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_reflex1_prisoner_condition_combatNotReady(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f77ac89f");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (fs_reflex1_prisoner_condition__defaultCondition(player, self))
        {
            fs_reflex1_prisoner_action_complete_quest(player, self);
            string_id message = new string_id(c_stringFile, "s_2a6f5720");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("fs_reflex1_prisoner"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_reflex1_prisoner.branchId");
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_reflex1_prisoner.branchId");
        return SCRIPT_CONTINUE;
    }
}
