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
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class mtp_meatlump_king extends script.base_script
{
    public mtp_meatlump_king()
    {
    }
    public static String c_stringFile = "conversation/mtp_meatlump_king";
    public boolean mtp_meatlump_king_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_meatlump_king_condition_readyforAct2Story(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActiveOrComplete(player, "mtp_hideout_pointer") && !groundquests.isQuestActiveOrComplete(player, "mtp_meatlump_king_story");
    }
    public boolean mtp_meatlump_king_condition_blurb01(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 1;
    }
    public boolean mtp_meatlump_king_condition_blurb02(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 2;
    }
    public boolean mtp_meatlump_king_condition_blurb03(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 3;
    }
    public boolean mtp_meatlump_king_condition_blurb04(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 4;
    }
    public boolean mtp_meatlump_king_condition_blurb05(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 5;
    }
    public boolean mtp_meatlump_king_condition_blurb06(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 6;
    }
    public boolean mtp_meatlump_king_condition_blurb07(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 7;
    }
    public boolean mtp_meatlump_king_condition_blurb08(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 8;
    }
    public boolean mtp_meatlump_king_condition_blurb09(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 9;
    }
    public boolean mtp_meatlump_king_condition_blurb10(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 10;
    }
    public boolean mtp_meatlump_king_condition_blurb11(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 11;
    }
    public boolean mtp_meatlump_king_condition_blurb12(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 12;
    }
    public boolean mtp_meatlump_king_condition_blurb_rare(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "mtp_meatlumpKingBlurb") == 9999;
    }
    public void mtp_meatlump_king_action_grantAct2Story(obj_id player, obj_id npc) throws InterruptedException
    {
        chat.thinkTo(player, player, new string_id("theme_park/corellia/quest", "mtp_king_story_quest"));
        groundquests.grantQuest(player, "mtp_meatlump_king_story");
        return;
    }
    public void mtp_meatlump_king_action_setEmperorAppearance(obj_id player, obj_id npc) throws InterruptedException
    {
        location here = getLocation(npc);
        playClientEffectLoc(getPlayerCreaturesInRange(here, 100.0f), "clienteffect/jedi_master_cloak_good.cef", here, 0.0f);
        setObjectAppearance(npc, "object/mobile/shared_palpatine.iff");
        messageTo(npc, "handlePointToSelf", null, 1, false);
        messageTo(npc, "handleResetMeatlumpKingAppearance", null, 5, false);
    }
    public int mtp_meatlump_king_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (mtp_meatlump_king_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_meatlump_king_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_meatlump_king_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.mtp_meatlump_king.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_meatlump_king.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_meatlump_king_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11"))
        {
            if (mtp_meatlump_king_condition__defaultCondition(player, npc))
            {
                mtp_meatlump_king_action_grantAct2Story(player, npc);
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.mtp_meatlump_king.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_14"))
        {
            if (mtp_meatlump_king_condition__defaultCondition(player, npc))
            {
                mtp_meatlump_king_action_grantAct2Story(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.mtp_meatlump_king.branchId");
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
            detachScript(self, "conversation.mtp_meatlump_king");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int handlePointToSelf(obj_id self, dictionary params) throws InterruptedException
    {
        doAnimationAction(self, "point_to_self");
        return SCRIPT_CONTINUE;
    }
    public int handleResetMeatlumpKingAppearance(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        playClientEffectLoc(getPlayerCreaturesInRange(here, 100.0f), "clienteffect/jedi_master_cloak_good.cef", here, 0.0f);
        revertObjectAppearance(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        int blurb = rand(0, 12);
        if (rand(0, 999) == 99)
        {
            blurb = 9999;
        }
        utils.setScriptVar(player, "mtp_meatlumpKingBlurb", blurb);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.mtp_meatlump_king");
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
        if (mtp_meatlump_king_condition_readyforAct2Story(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_meatlump_king_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                utils.setScriptVar(player, "conversation.mtp_meatlump_king.branchId", 1);
                npcStartConversation(player, npc, "mtp_meatlump_king", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb_rare(player, npc))
        {
            mtp_meatlump_king_action_setEmperorAppearance(player, npc);
            string_id message = new string_id(c_stringFile, "s_43");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb12(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_30");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb11(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_29");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb10(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb09(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_27");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb08(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb07(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_25");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb06(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_31");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb05(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_33");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb04(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_35");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb03(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb02(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_39");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition_blurb01(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_41");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_meatlump_king_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "huge");
            string_id message = new string_id(c_stringFile, "s_44");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("mtp_meatlump_king"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_meatlump_king.branchId");
        if (branchId == 1 && mtp_meatlump_king_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && mtp_meatlump_king_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_meatlump_king.branchId");
        return SCRIPT_CONTINUE;
    }
}
