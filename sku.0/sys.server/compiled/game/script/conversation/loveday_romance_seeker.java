package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.callable;
import script.library.chat;
import script.library.conversation;
import script.library.groundquests;
import script.library.holiday;
import script.library.utils;

public class loveday_romance_seeker extends script.base_script
{
    public loveday_romance_seeker()
    {
    }
    public static String c_stringFile = "conversation/loveday_romance_seeker";
    public boolean loveday_romance_seeker_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean loveday_romance_seeker_condition_matchIncomplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (getMaster(npc) != player)
        {
            return false;
        }
        return groundquests.isTaskActive(player, "loveday_playing_cupid", "loveday_playing_cupid_01");
    }
    public boolean loveday_romance_seeker_condition_matchComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (getMaster(npc) != player)
        {
            return false;
        }
        return groundquests.isTaskActive(player, "loveday_playing_cupid", "loveday_playing_cupid_02");
    }
    public boolean loveday_romance_seeker_condition_doesntHaveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (getMaster(npc) != player)
        {
            return false;
        }
        return !groundquests.isQuestActiveOrComplete(player, "loveday_playing_cupid");
    }
    public boolean loveday_romance_seeker_condition_wasRejected(obj_id player, obj_id npc) throws InterruptedException
    {
        return buff.hasBuff(player, "loveday_rejected");
    }
    public void loveday_romance_seeker_action_grantLovedayQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (holiday.hasLovedayRomanceSeekerCompanion(player))
        {
            obj_id romanceSeekerCompanionControlDevice = holiday.getLovedayRomanceSeekerCompanionControlDevice(player);
            if (isIdValid(romanceSeekerCompanionControlDevice))
            {
                destroyObject(romanceSeekerCompanionControlDevice);
            }
        }
        groundquests.clearQuest(player, "loveday_playing_cupid");
        groundquests.grantQuest(player, "loveday_playing_cupid");
        holiday.grantLovedayRomanceSeekerCompanion(player);
        if (hasObjVar(player, "loveday.eligiblePlayingCupid"))
        {
            removeObjVar(player, "loveday.eligiblePlayingCupid");
        }
        return;
    }
    public void loveday_romance_seeker_action_sendCompletionSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id romanceSeekerCompanionControlDevice = holiday.getLovedayRomanceSeekerCompanionControlDevice(player);
        if (isIdValid(romanceSeekerCompanionControlDevice))
        {
            destroyObject(romanceSeekerCompanionControlDevice);
        }
        groundquests.sendSignal(player, "loveday_playing_cupid_02");
        int now = getCalendarTime();
        int secondsUntil = secondsUntilNextDailyTime(4, 0, 0);
        int then = now + secondsUntil;
        setObjVar(player, "loveday.eligiblePlayingCupid", then);
    }
    public String loveday_romance_seeker_tokenTO_trait_01(obj_id player, obj_id npc) throws InterruptedException
    {
        String file = "event/love_day";
        obj_id controlDevice = callable.getCallableCD(npc);
        String message = getString(new string_id(file, "trait_" + getStringObjVar(controlDevice, "playingCupid.trait_01")));
        return message;
    }
    public String loveday_romance_seeker_tokenTO_trait_05(obj_id player, obj_id npc) throws InterruptedException
    {
        String file = "event/love_day";
        obj_id controlDevice = callable.getCallableCD(npc);
        String message = getString(new string_id(file, "trait_" + getStringObjVar(controlDevice, "playingCupid.trait_05")));
        return message;
    }
    public String loveday_romance_seeker_tokenTO_trait_04(obj_id player, obj_id npc) throws InterruptedException
    {
        String file = "event/love_day";
        obj_id controlDevice = callable.getCallableCD(npc);
        String message = getString(new string_id(file, "trait_" + getStringObjVar(controlDevice, "playingCupid.trait_04")));
        return message;
    }
    public String loveday_romance_seeker_tokenTO_trait_03(obj_id player, obj_id npc) throws InterruptedException
    {
        String file = "event/love_day";
        obj_id controlDevice = callable.getCallableCD(npc);
        String message = getString(new string_id(file, "trait_" + getStringObjVar(controlDevice, "playingCupid.trait_03")));
        return message;
    }
    public String loveday_romance_seeker_tokenTO_trait_02(obj_id player, obj_id npc) throws InterruptedException
    {
        String file = "event/love_day";
        obj_id controlDevice = callable.getCallableCD(npc);
        String message = getString(new string_id(file, "trait_" + getStringObjVar(controlDevice, "playingCupid.trait_02")));
        return message;
    }
    public int loveday_romance_seeker_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (loveday_romance_seeker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_romance_seeker_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.loveday_romance_seeker.branchId", 4);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_01(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_romance_seeker.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_01(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_32"))
        {
            if (loveday_romance_seeker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.loveday_romance_seeker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_romance_seeker_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34"))
        {
            if (loveday_romance_seeker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_romance_seeker_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                    }
                    utils.setScriptVar(player, "conversation.loveday_romance_seeker.branchId", 5);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_02(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_romance_seeker.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_02(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_romance_seeker_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36"))
        {
            if (loveday_romance_seeker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_romance_seeker_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.loveday_romance_seeker.branchId", 6);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_03(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_romance_seeker.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_03(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_romance_seeker_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (loveday_romance_seeker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_romance_seeker_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                    }
                    utils.setScriptVar(player, "conversation.loveday_romance_seeker.branchId", 7);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_04(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_romance_seeker.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_04(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_romance_seeker_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40"))
        {
            if (loveday_romance_seeker_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (loveday_romance_seeker_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.loveday_romance_seeker.branchId", 8);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_05(player, npc));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.loveday_romance_seeker.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(loveday_romance_seeker_tokenTO_trait_05(player, npc));
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int loveday_romance_seeker_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            if (loveday_romance_seeker_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.loveday_romance_seeker.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.loveday_romance_seeker");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_HOLIDAY_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_HOLIDAY_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.loveday_romance_seeker");
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
        if (loveday_romance_seeker_condition_wasRejected(player, npc))
        {
            doAnimationAction(npc, "embarrassed");
            string_id message = new string_id(c_stringFile, "s_22");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_romance_seeker_condition_matchComplete(player, npc))
        {
            doAnimationAction(npc, "celebrate");
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_romance_seeker_condition_matchIncomplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (loveday_romance_seeker_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (loveday_romance_seeker_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                }
                utils.setScriptVar(player, "conversation.loveday_romance_seeker.branchId", 3);
                npcStartConversation(player, npc, "loveday_romance_seeker", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (loveday_romance_seeker_condition_doesntHaveQuest(player, npc))
        {
            doAnimationAction(npc, "weeping");
            string_id message = new string_id(c_stringFile, "s_29");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (loveday_romance_seeker_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "implore");
            string_id message = new string_id(c_stringFile, "s_27");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("loveday_romance_seeker"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.loveday_romance_seeker.branchId");
        if (branchId == 3 && loveday_romance_seeker_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && loveday_romance_seeker_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && loveday_romance_seeker_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && loveday_romance_seeker_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && loveday_romance_seeker_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && loveday_romance_seeker_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.loveday_romance_seeker.branchId");
        return SCRIPT_CONTINUE;
    }
}
