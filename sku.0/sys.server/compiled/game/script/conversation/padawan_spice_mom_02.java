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

public class padawan_spice_mom_02 extends script.base_script
{
    public padawan_spice_mom_02()
    {
    }
    public static String c_stringFile = "conversation/padawan_spice_mom_02";
    public boolean padawan_spice_mom_02_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean padawan_spice_mom_02_condition_isTrialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id trialPlayer = getObjIdObjVar(npc, jedi_trials.PADAWAN_TRIAL_PLAYER_OBJVAR);
        if (player == trialPlayer)
        {
            String trialName = jedi_trials.getJediTrialName(player);
            if (trialName != null && trialName.equals("spice_mom"))
            {
                return !hasObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01");
            }
        }
        return false;
    }
    public void padawan_spice_mom_02_action_spokeToNpc(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01", true);
        messageTo(player, "handleSetBeginLoc", null, 1, false);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.padawan_spice_mom_02");
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
        detachScript(self, "npc.conversation.padawan_spice_mom_02");
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
        if (padawan_spice_mom_02_condition_isTrialPlayer(player, self))
        {
            doAnimationAction(self, "beckon");
            string_id message = new string_id(c_stringFile, "s_168212d8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_spice_mom_02_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (padawan_spice_mom_02_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d0812b8d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1f593aa5");
                }
                setObjVar(player, "conversation.padawan_spice_mom_02.branchId", 1);
                npcStartConversation(player, self, "padawan_spice_mom_02", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_spice_mom_02_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_b56527e1");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("padawan_spice_mom_02"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.padawan_spice_mom_02.branchId");
        if (branchId == 1 && response.equals("s_d0812b8d"))
        {
            if (padawan_spice_mom_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e66080c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_spice_mom_02_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (padawan_spice_mom_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_798612aa");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6168a073");
                    }
                    setObjVar(player, "conversation.padawan_spice_mom_02.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.padawan_spice_mom_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What are you lookin' for?  ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_1f593aa5"))
        {
            if (padawan_spice_mom_02_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_a04364ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_spice_mom_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bdb90e46");
                    }
                    setObjVar(player, "conversation.padawan_spice_mom_02.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.padawan_spice_mom_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What are you lookin' for?  ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_798612aa"))
        {
            if (padawan_spice_mom_02_condition__defaultCondition(player, self))
            {
                padawan_spice_mom_02_action_spokeToNpc(player, self);
                string_id message = new string_id(c_stringFile, "s_f5bb1061");
                removeObjVar(player, "conversation.padawan_spice_mom_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You sure about that?  I've got what every man and woman in the Galaxy needs.  Name your pleasure, my friend.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_6168a073"))
        {
            doAnimationAction(player, "shake_head_disgust");
            if (padawan_spice_mom_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a0730d59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_spice_mom_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca7eddd3");
                    }
                    setObjVar(player, "conversation.padawan_spice_mom_02.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.padawan_spice_mom_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You sure about that?  I've got what every man and woman in the Galaxy needs.  Name your pleasure, my friend.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_ca7eddd3"))
        {
            doAnimationAction(player, "point_accusingly");
            if (padawan_spice_mom_02_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_6242972e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_spice_mom_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_625ba59c");
                    }
                    setObjVar(player, "conversation.padawan_spice_mom_02.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.padawan_spice_mom_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Sola sent you didn't she?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_625ba59c"))
        {
            doAnimationAction(player, "standing_placate");
            if (padawan_spice_mom_02_condition__defaultCondition(player, self))
            {
                padawan_spice_mom_02_action_spokeToNpc(player, self);
                string_id message = new string_id(c_stringFile, "s_350fcbe3");
                removeObjVar(player, "conversation.padawan_spice_mom_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Whatever you say friend. I'm not going to argue. I'll say this though... shouldn't be so quick to judge people you barely know.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_bdb90e46"))
        {
            if (padawan_spice_mom_02_condition__defaultCondition(player, self))
            {
                padawan_spice_mom_02_action_spokeToNpc(player, self);
                string_id message = new string_id(c_stringFile, "s_2c96cac3");
                removeObjVar(player, "conversation.padawan_spice_mom_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You aren't in law enforcement now are you?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.padawan_spice_mom_02.branchId");
        return SCRIPT_CONTINUE;
    }
}
