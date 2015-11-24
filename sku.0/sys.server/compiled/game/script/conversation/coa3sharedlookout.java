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

public class coa3sharedlookout extends script.base_script
{
    public coa3sharedlookout()
    {
    }
    public static String c_stringFile = "conversation/coa3sharedlookout";
    public boolean coa3sharedlookout_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean coa3sharedlookout_condition_lookoutTrial1(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        return (getIntObjVar(player, "coa3.convTracker") == 200);
    }
    public boolean coa3sharedlookout_condition_lookoutTrial2(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        return (getIntObjVar(player, "coa3.convTracker") == 201);
    }
    public boolean coa3sharedlookout_condition_lookoutTrial3(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        return (getIntObjVar(player, "coa3.convTracker") == 202);
    }
    public boolean coa3sharedlookout_condition_lookoutTrialPass(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        if (getIntObjVar(player, "coa3.lookoutLikeMeter") >= 2 && getIntObjVar(player, "coa3.convTracker") == 203)
        {
            return true;
        }
        if (getIntObjVar(player, "coa3.convTracker") == 204)
        {
            return true;
        }
        return false;
    }
    public boolean coa3sharedlookout_condition_lookoutTrialFail(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        if (getIntObjVar(player, "coa3.lookoutLikeMeter") < 2 && getIntObjVar(player, "coa3.convTracker") == 203)
        {
            return true;
        }
        return false;
    }
    public boolean coa3sharedlookout_condition_lookoutMissionWait(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        return (getIntObjVar(player, "coa3.convTracker") == 205);
    }
    public boolean coa3sharedlookout_condition_lookoutMissionActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((getObjIdObjVar(player, "coa3.imperial.missionNpcId") != npc) && (getObjIdObjVar(player, "coa3.rebel.missionNpcId") != npc))
        {
            return false;
        }
        return (getIntObjVar(player, "coa3.convTracker") == 206);
    }
    public void coa3sharedlookout_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void coa3sharedlookout_action_StartLikeMeterNeg(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.lookoutLikeMeter", -1);
        setObjVar(player, "coa3.convTracker", 201);
    }
    public void coa3sharedlookout_action_StartLikeMeterPos(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.lookoutLikeMeter", 1);
        setObjVar(player, "coa3.convTracker", 201);
    }
    public void coa3sharedlookout_action_StartLikeMeterNeut(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.lookoutLikeMeter", 0);
        setObjVar(player, "coa3.convTracker", 201);
    }
    public void coa3sharedlookout_action_decLikeMeterTrial2(obj_id player, obj_id npc) throws InterruptedException
    {
        int likeMeter = getIntObjVar(player, "coa3.lookoutLikeMeter");
        setObjVar(player, "coa3.lookoutLikeMeter", (likeMeter - 1));
        setObjVar(player, "coa3.convTracker", 202);
    }
    public void coa3sharedlookout_action_incLikeMeterTrial2(obj_id player, obj_id npc) throws InterruptedException
    {
        int likeMeter = getIntObjVar(player, "coa3.lookoutLikeMeter");
        setObjVar(player, "coa3.lookoutLikeMeter", (likeMeter + 1));
        setObjVar(player, "coa3.convTracker", 202);
    }
    public void coa3sharedlookout_action_likeMeterTrialBonus(obj_id player, obj_id npc) throws InterruptedException
    {
        int likeMeter = getIntObjVar(player, "coa3.lookoutLikeMeter");
        setObjVar(player, "coa3.lookoutLikeMeter", (likeMeter + 1));
    }
    public void coa3sharedlookout_action_neutLikeMeterTrial3(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 203);
    }
    public void coa3sharedlookout_action_decLikeMeterTrial3(obj_id player, obj_id npc) throws InterruptedException
    {
        int likeMeter = getIntObjVar(player, "coa3.lookoutLikeMeter");
        setObjVar(player, "coa3.lookoutLikeMeter", (likeMeter - 1));
        setObjVar(player, "coa3.convTracker", 203);
    }
    public void coa3sharedlookout_action_incLikeMeterTrial3(obj_id player, obj_id npc) throws InterruptedException
    {
        int likeMeter = getIntObjVar(player, "coa3.lookoutLikeMeter");
        setObjVar(player, "coa3.lookoutLikeMeter", (likeMeter + 1));
        setObjVar(player, "coa3.convTracker", 203);
    }
    public void coa3sharedlookout_action_passLookoutTrial(obj_id player, obj_id npc) throws InterruptedException
    {
        removeObjVar(player, "coa3.lookoutLikeMeter");
        setObjVar(player, "coa3.convTracker", 204);
    }
    public void coa3sharedlookout_action_failLookoutTrial(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "handleAttackPlayer", params, 0, false);
    }
    public void coa3sharedlookout_action_lookoutAcceptMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 206);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 2);
        messageTo(npc, "handleStartCaravanMission", params, 0, false);
    }
    public void coa3sharedlookout_action_lookoutWaitMissionAccept(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 205);
    }
    public void coa3sharedlookout_action_lookoutRefreshMission(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 2);
        messageTo(npc, "handleStartCaravanMission", params, 0, false);
    }
    public void coa3sharedlookout_action_lookoutAbortMission(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "coa3.convTracker", 205);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("npc", npc);
        params.put("value", 2);
        messageTo(npc, "handleAbortCaravanMission", params, 0, false);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.coa3sharedlookout");
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
        detachScript(self, "npc.conversation.coa3sharedlookout");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (coa3sharedlookout_condition_lookoutTrial1(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_74f0143a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_70614d9f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f9883674");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dbe291a4");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b1f8b064");
                }
                setObjVar(player, "conversation.coa3sharedlookout.branchId", 1);
                npcStartConversation(player, self, "coa3sharedlookout", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedlookout_condition_lookoutTrial2(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_6273debb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8aa225b4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bcb9ba12");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43af83df");
                }
                setObjVar(player, "conversation.coa3sharedlookout.branchId", 9);
                npcStartConversation(player, self, "coa3sharedlookout", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedlookout_condition_lookoutTrial3(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e6e3a9f7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5d8182f2");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fcb2a3f3");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_46c66c6d");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b3049ce7");
                }
                setObjVar(player, "conversation.coa3sharedlookout.branchId", 16);
                npcStartConversation(player, self, "coa3sharedlookout", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedlookout_condition_lookoutTrialPass(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_eff5d25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                }
                setObjVar(player, "conversation.coa3sharedlookout.branchId", 24);
                npcStartConversation(player, self, "coa3sharedlookout", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedlookout_condition_lookoutTrialFail(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_eff5d25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                }
                setObjVar(player, "conversation.coa3sharedlookout.branchId", 29);
                npcStartConversation(player, self, "coa3sharedlookout", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedlookout_condition_lookoutMissionWait(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_82fdf085");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69fbc643");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da6c5185");
                }
                setObjVar(player, "conversation.coa3sharedlookout.branchId", 31);
                npcStartConversation(player, self, "coa3sharedlookout", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedlookout_condition_lookoutMissionActive(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8cf78df2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (coa3sharedlookout_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_869a3fda");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b4e5d3ef");
                }
                setObjVar(player, "conversation.coa3sharedlookout.branchId", 34);
                npcStartConversation(player, self, "coa3sharedlookout", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (coa3sharedlookout_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_3029f004");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("coa3sharedlookout"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.coa3sharedlookout.branchId");
        if (branchId == 1 && response.equals("s_70614d9f"))
        {
            coa3sharedlookout_action_StartLikeMeterNeg(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8c75fac0");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ya talkin' to me? Who sent ya?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_f9883674"))
        {
            coa3sharedlookout_action_StartLikeMeterPos(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c6ca8aca");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e1e1e0ac");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90b36308");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb54f6cc");
                    }
                    setObjVar(player, "conversation.coa3sharedlookout.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ya talkin' to me? Who sent ya?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_dbe291a4"))
        {
            coa3sharedlookout_action_StartLikeMeterNeg(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fac80094");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ya talkin' to me? Who sent ya?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_b1f8b064"))
        {
            coa3sharedlookout_action_StartLikeMeterNeut(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b6a2f31");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ya talkin' to me? Who sent ya?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_e1e1e0ac"))
        {
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_74476a3d");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ya do? I can't give ya anything, what I have is mine. But take a rest if ya need it. So what sort of work do you do do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_90b36308"))
        {
            coa3sharedlookout_action_likeMeterTrialBonus(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4ae032b9");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ya do? I can't give ya anything, what I have is mine. But take a rest if ya need it. So what sort of work do you do do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_bb54f6cc"))
        {
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7eb4d42");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ya do? I can't give ya anything, what I have is mine. But take a rest if ya need it. So what sort of work do you do do?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_8aa225b4"))
        {
            coa3sharedlookout_action_decLikeMeterTrial2(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8dccb521");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're a chatty one. Then again, it gets a little lonely bein' out here so it's nice to have someone to chat with. But let's just keep it to small talk okay?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_bcb9ba12"))
        {
            coa3sharedlookout_action_decLikeMeterTrial2(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_33d80544");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're a chatty one. Then again, it gets a little lonely bein' out here so it's nice to have someone to chat with. But let's just keep it to small talk okay?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_43af83df"))
        {
            coa3sharedlookout_action_incLikeMeterTrial2(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_efba54e2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4957d505");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f33a89f5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5b9f69a4");
                    }
                    setObjVar(player, "conversation.coa3sharedlookout.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You're a chatty one. Then again, it gets a little lonely bein' out here so it's nice to have someone to chat with. But let's just keep it to small talk okay?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_4957d505"))
        {
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_93295f8c");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I like gamblin'. Mostly Sabacc, ya know? I stay away from those Lugjack machines. Ya may as well be throwin' credits into the Sarlacc Pit playin' that game. That's a game for old women and clueless nobles who can't appreciate the subtleties of a real man's game. You like Sabacc?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_f33a89f5"))
        {
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_24813e89");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I like gamblin'. Mostly Sabacc, ya know? I stay away from those Lugjack machines. Ya may as well be throwin' credits into the Sarlacc Pit playin' that game. That's a game for old women and clueless nobles who can't appreciate the subtleties of a real man's game. You like Sabacc?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_5b9f69a4"))
        {
            coa3sharedlookout_action_likeMeterTrialBonus(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_48a4f87f");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I like gamblin'. Mostly Sabacc, ya know? I stay away from those Lugjack machines. Ya may as well be throwin' credits into the Sarlacc Pit playin' that game. That's a game for old women and clueless nobles who can't appreciate the subtleties of a real man's game. You like Sabacc?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_5d8182f2"))
        {
            coa3sharedlookout_action_neutLikeMeterTrial3(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_68196cce");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So... What do you like to do for fun?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_fcb2a3f3"))
        {
            coa3sharedlookout_action_decLikeMeterTrial3(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1cace673");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So... What do you like to do for fun?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_46c66c6d"))
        {
            coa3sharedlookout_action_incLikeMeterTrial3(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1d45a1b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a31bb6a5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ab11e3a9");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_789d30e1");
                    }
                    setObjVar(player, "conversation.coa3sharedlookout.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So... What do you like to do for fun?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_b3049ce7"))
        {
            coa3sharedlookout_action_decLikeMeterTrial3(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c8af5b42");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So... What do you like to do for fun?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_a31bb6a5"))
        {
            coa3sharedlookout_action_likeMeterTrialBonus(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4fe0cc64");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You too huh? Well it's great we have something in common! Too bad we don't have cards or we could have us a game. Say, what's your favorite Sabacc variant?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_ab11e3a9"))
        {
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e0ec2153");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You too huh? Well it's great we have something in common! Too bad we don't have cards or we could have us a game. Say, what's your favorite Sabacc variant?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_789d30e1"))
        {
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_be39516b");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You too huh? Well it's great we have something in common! Too bad we don't have cards or we could have us a game. Say, what's your favorite Sabacc variant?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && response.equals("s_1c8bddbb"))
        {
            coa3sharedlookout_action_passLookoutTrial(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e9a05b92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (coa3sharedlookout_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39c1d4f4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c2f4605");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b3ee988");
                    }
                    setObjVar(player, "conversation.coa3sharedlookout.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Say. There's something I need to ask you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_39c1d4f4"))
        {
            coa3sharedlookout_action_lookoutAcceptMission(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f513f012");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm in sort of a jam right now. You wouldn't happen to be good at fixin' things would ya? One of the speeders in our caravan has a busted 100-GU anti-gravity unit and it ain't goin' no where until it gets repaired. Jabba's got a lot of money tied up in this shipment so I'm sure he'd pay ya well for your trouble!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_2c2f4605"))
        {
            coa3sharedlookout_action_lookoutWaitMissionAccept(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4e7b2666");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm in sort of a jam right now. You wouldn't happen to be good at fixin' things would ya? One of the speeders in our caravan has a busted 100-GU anti-gravity unit and it ain't goin' no where until it gets repaired. Jabba's got a lot of money tied up in this shipment so I'm sure he'd pay ya well for your trouble!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && response.equals("s_2b3ee988"))
        {
            coa3sharedlookout_action_lookoutWaitMissionAccept(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_41d587bc");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm in sort of a jam right now. You wouldn't happen to be good at fixin' things would ya? One of the speeders in our caravan has a busted 100-GU anti-gravity unit and it ain't goin' no where until it gets repaired. Jabba's got a lot of money tied up in this shipment so I'm sure he'd pay ya well for your trouble!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_1c8bddbb"))
        {
            coa3sharedlookout_action_failLookoutTrial(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8591381a");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Say. There's something I need to ask you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_69fbc643"))
        {
            coa3sharedlookout_action_lookoutAcceptMission(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7d2ae36c");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So, can ya go and make those repairs then?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_da6c5185"))
        {
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fdb57c0");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'So, can ya go and make those repairs then?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && response.equals("s_869a3fda"))
        {
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e9f1af41");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hey, shouldn't you be out making those repairs? We have to get that caravan moving pronto!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && response.equals("s_b4e5d3ef"))
        {
            coa3sharedlookout_action_lookoutRefreshMission(player, self);
            if (coa3sharedlookout_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2fe8f4b5");
                removeObjVar(player, "conversation.coa3sharedlookout.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hey, shouldn't you be out making those repairs? We have to get that caravan moving pronto!' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.coa3sharedlookout.branchId");
        return SCRIPT_CONTINUE;
    }
}
