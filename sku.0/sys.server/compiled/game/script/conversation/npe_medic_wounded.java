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
import script.library.npe;
import script.library.utils;

public class npe_medic_wounded extends script.base_script
{
    public npe_medic_wounded()
    {
    }
    public static String c_stringFile = "conversation/npe_medic_wounded";
    public boolean npe_medic_wounded_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_medic_wounded_condition_isFirstToHeal(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean iAmFirst = false;
        if (groundquests.hasCompletedTask(player, "npe_medic", "npe_medic_heal_1"))
        {
            return iAmFirst;
        }
        if (groundquests.isQuestActive(player, "npe_medic"))
        {
            if (hasObjVar(npc, "quest.taskName"))
            {
                String taskName = getStringObjVar(npc, "quest.taskName");
                if (taskName.equals("npe_medic_heal_1"))
                {
                    iAmFirst = true;
                }
            }
        }
        return iAmFirst;
    }
    public boolean npe_medic_wounded_condition_wasHealed1(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean wasHealed = false;
        String taskName = getStringObjVar(npc, "quest.taskName");
        if (taskName.equals("npe_medic_heal_1"))
        {
            if (groundquests.hasCompletedTask(player, "npe_medic", "npe_medic_heal_1"))
            {
                wasHealed = true;
            }
        }
        return wasHealed;
    }
    public void npe_medic_wounded_action_sendSignalHealed(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_medic_heal_worker_1");
        return;
    }
    public void npe_medic_wounded_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void npe_medic_wounded_action_giveComm(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.removeScriptVar(player, "npe.heal");
        npe.giveHealPopUp(player, npc);
    }
    public int npe_medic_wounded_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (npe_medic_wounded_condition__defaultCondition(player, npc))
            {
                npe_medic_wounded_action_giveComm(player, npc);
                string_id message = new string_id(c_stringFile, "s_18");
                utils.removeScriptVar(player, "conversation.npe_medic_wounded.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_medic_wounded_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (npe_medic_wounded_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.npe_medic_wounded.branchId");
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
            detachScript(self, "conversation.npe_medic_wounded");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Injured Mechanic");
        setPosture(self, POSTURE_SITTING);
        setAnimationMood(self, "meditating");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Injured Mechanic");
        setPosture(self, POSTURE_SITTING);
        setAnimationMood(self, "meditating");
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
        detachScript(self, "conversation.npe_medic_wounded");
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
        if (npe_medic_wounded_condition_isFirstToHeal(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic_wounded_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                }
                utils.setScriptVar(player, "conversation.npe_medic_wounded.branchId", 1);
                npcStartConversation(player, npc, "npe_medic_wounded", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic_wounded_condition_wasHealed1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_16");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_medic_wounded_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                }
                utils.setScriptVar(player, "conversation.npe_medic_wounded.branchId", 3);
                npcStartConversation(player, npc, "npe_medic_wounded", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_medic_wounded_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_40");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_medic_wounded"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_medic_wounded.branchId");
        if (branchId == 1 && npe_medic_wounded_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && npe_medic_wounded_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_medic_wounded.branchId");
        return SCRIPT_CONTINUE;
    }
}
