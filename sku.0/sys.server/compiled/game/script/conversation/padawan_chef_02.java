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
import script.library.jedi_trials;
import script.library.utils;

public class padawan_chef_02 extends script.base_script
{
    public padawan_chef_02()
    {
    }
    public static String c_stringFile = "conversation/padawan_chef_02";
    public boolean padawan_chef_02_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean padawan_chef_02_condition_isTrialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            String trialName = jedi_trials.getJediTrialName(player);
            if (trialName != null && trialName.equals("chef"))
            {
                return !hasObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01");
            }
        }
        return false;
    }
    public void padawan_chef_02_action_spokeToNpc(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01", true);
        stopCombat(npc);
        setInvulnerable(npc, true);
        messageTo(player, "handleSetBeginLoc", null, 1, false);
        return;
    }
    public void padawan_chef_02_action_npcAttacks(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary webster = new dictionary();
        webster.put("player", player);
        messageTo(npc, "handleQuestStartAttack", webster, 1, false);
        return;
    }
    public int padawan_chef_02_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c1e21c08"))
        {
            if (padawan_chef_02_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78cca73a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_chef_02_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_chef_02_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca28f99d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_928c6a6a");
                    }
                    utils.setScriptVar(player, "conversation.padawan_chef_02.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.padawan_chef_02.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int padawan_chef_02_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ca28f99d"))
        {
            doAnimationAction(player, "slit_throat");
            if (padawan_chef_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "threaten_combat");
                padawan_chef_02_action_npcAttacks(player, npc);
                string_id message = new string_id(c_stringFile, "s_867ceb54");
                utils.removeScriptVar(player, "conversation.padawan_chef_02.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_928c6a6a"))
        {
            if (padawan_chef_02_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "embarrassed");
                padawan_chef_02_action_spokeToNpc(player, npc);
                string_id message = new string_id(c_stringFile, "s_2ad2bbf9");
                utils.removeScriptVar(player, "conversation.padawan_chef_02.branchId");
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
            detachScript(self, "conversation.padawan_chef_02");
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
        if (!isIncapacitated(self))
        {
            int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
            menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
            menuInfoData.setServerNotify(false);
            setCondition(self, CONDITION_CONVERSABLE);
        }
        else 
        {
            clearCondition(self, CONDITION_CONVERSABLE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.padawan_chef_02");
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
        if (padawan_chef_02_condition_isTrialPlayer(player, npc))
        {
            doAnimationAction(npc, "nervous");
            string_id message = new string_id(c_stringFile, "s_35494a44");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_chef_02_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c1e21c08");
                }
                utils.setScriptVar(player, "conversation.padawan_chef_02.branchId", 1);
                npcStartConversation(player, npc, "padawan_chef_02", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_chef_02_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "rub_belly");
            string_id message = new string_id(c_stringFile, "s_7ca121b2");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("padawan_chef_02"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.padawan_chef_02.branchId");
        if (branchId == 1 && padawan_chef_02_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && padawan_chef_02_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.padawan_chef_02.branchId");
        return SCRIPT_CONTINUE;
    }
}
