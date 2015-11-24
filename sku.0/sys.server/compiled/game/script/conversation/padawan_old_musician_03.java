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

public class padawan_old_musician_03 extends script.base_script
{
    public padawan_old_musician_03()
    {
    }
    public static String c_stringFile = "conversation/padawan_old_musician_03";
    public boolean padawan_old_musician_03_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean padawan_old_musician_03_condition_isTrialPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        String trialName = jedi_trials.getJediTrialName(player);
        if (trialName != null && trialName.equals("old_musician"))
        {
            return (!hasObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_02") && hasObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_01"));
        }
        return false;
    }
    public void padawan_old_musician_03_action_sendBackToBeginning(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "jedi_trials.padawan_trials.temp.spokeToTarget_02", true);
        messageTo(player, "handleSetBeginLoc", null, 1, false);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.padawan_old_musician_03");
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
        detachScript(self, "npc.conversation.padawan_old_musician_03");
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
        if (padawan_old_musician_03_condition_isTrialPlayer(player, self))
        {
            doAnimationAction(self, "wave2");
            string_id message = new string_id(c_stringFile, "s_16e413e5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (padawan_old_musician_03_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a8d0cb35");
                }
                setObjVar(player, "conversation.padawan_old_musician_03.branchId", 1);
                npcStartConversation(player, self, "padawan_old_musician_03", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (padawan_old_musician_03_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_4ef8ad4d");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("padawan_old_musician_03"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.padawan_old_musician_03.branchId");
        if (branchId == 1 && response.equals("s_a8d0cb35"))
        {
            if (padawan_old_musician_03_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_8da4188a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cbc0ca2b");
                    }
                    setObjVar(player, "conversation.padawan_old_musician_03.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.padawan_old_musician_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello friend.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_cbc0ca2b"))
        {
            if (padawan_old_musician_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a25995cf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6be6a4b5");
                    }
                    setObjVar(player, "conversation.padawan_old_musician_03.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.padawan_old_musician_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'No kidding?  Grizzy Two Tone?  It's been quite a few years since I've heard that name.  Samston, a member of Grizzy's band used to hang out in this here Cantina.  He used to sit in that chair right across from me.  We were good friends Samston and I. He had many a good stories of Grizzy and the rest of 'The Blue...' something... can't quite recall the bands name.  It was a sad day when Samston passed away. ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_6be6a4b5"))
        {
            if (padawan_old_musician_03_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8dc8e232");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3585e154");
                    }
                    setObjVar(player, "conversation.padawan_old_musician_03.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.padawan_old_musician_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It's been a good ten years since Samston passed away.  As far as I know he has never talked with Grizzy after the whole fiasco between the two of them over that Twi'lek dancer.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_3585e154"))
        {
            if (padawan_old_musician_03_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_285ac110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (padawan_old_musician_03_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_afbed875");
                    }
                    setObjVar(player, "conversation.padawan_old_musician_03.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.padawan_old_musician_03.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh just some girl the two of them fought over for quite some time.  ' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_afbed875"))
        {
            if (padawan_old_musician_03_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "beckon");
                padawan_old_musician_03_action_sendBackToBeginning(player, self);
                string_id message = new string_id(c_stringFile, "s_77c9e846");
                removeObjVar(player, "conversation.padawan_old_musician_03.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You are going to need a shovel if you want to talk to her friend, and I'm afraid she isn't going to be saying much.  She passed a few years back herself.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.padawan_old_musician_03.branchId");
        return SCRIPT_CONTINUE;
    }
}
