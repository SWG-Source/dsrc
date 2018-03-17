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
import script.library.fs_quests;
import script.library.utils;
import script.library.xp;

public class fs_experience_converter extends script.base_script
{
    public fs_experience_converter()
    {
    }
    public static String c_stringFile = "conversation/fs_experience_converter";
    public boolean fs_experience_converter_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_experience_converter_condition_canConvertXP(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.isVillageEligible(player);
    }
    public boolean fs_experience_converter_condition_isConvertEligible(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.hasFreeUnlockBranches(player);
    }
    public void fs_experience_converter_action_showSUICrafting(obj_id player, obj_id npc) throws InterruptedException
    {
        xp.displayForceSensitiveXP(player, "crafting");
    }
    public void fs_experience_converter_action_showSUICombat(obj_id player, obj_id npc) throws InterruptedException
    {
        xp.displayForceSensitiveXP(player, "combat");
    }
    public void fs_experience_converter_action_showSUISenses(obj_id player, obj_id npc) throws InterruptedException
    {
        xp.displayForceSensitiveXP(player, "senses");
    }
    public void fs_experience_converter_action_showSUIReflexes(obj_id player, obj_id npc) throws InterruptedException
    {
        xp.displayForceSensitiveXP(player, "reflex");
    }
    public void fs_experience_converter_action_closeConversionSUI(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "force_sensitive.sui_pid"))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, "force_sensitive.sui_pid"));
        }
        if (utils.hasScriptVar(player, fs_quests.SCRIPT_VAR_BRANCH_SELECT_SUI))
        {
            forceCloseSUIPage(utils.getIntScriptVar(player, fs_quests.SCRIPT_VAR_BRANCH_SELECT_SUI));
        }
    }
    public void fs_experience_converter_action_showSUIUnlock(obj_id player, obj_id npc) throws InterruptedException
    {
        fs_quests.showBranchUnlockSUI(player);
    }
    public String fs_experience_converter_tokenTO_branchList(obj_id player, obj_id npc) throws InterruptedException
    {
        String branch_list = "\nNothing";
        boolean has_branch = false;
        for (int i = 0; i < 16; i++)
        {
            if (fs_quests.hasUnlockedBranch(player, i))
            {
                String branch = fs_quests.getBranchFromId(i);
                String branch_name = localize(new string_id("quest/force_sensitive/utils", branch));
                if (branch != null)
                {
                    if (!has_branch)
                    {
                        branch_list = "\n" + branch_name;
                        has_branch = true;
                    }
                    else 
                    {
                        branch_list += "\n" + branch_name;
                    }
                }
            }
        }
        return branch_list;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_experience_converter");
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
        detachScript(self, "npc.conversation.fs_experience_converter");
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
        if (fs_experience_converter_condition_canConvertXP(player, self))
        {
            doAnimationAction(self, "beckon");
            fs_experience_converter_action_closeConversionSUI(player, self);
            string_id message = new string_id(c_stringFile, "s_962f82a6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (fs_experience_converter_condition_isConvertEligible(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_73ab6ff4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76fa10e4");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_968c3ff6");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2734c210");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69a7c6ca");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e4c01185");
                }
                setObjVar(player, "conversation.fs_experience_converter.branchId", 1);
                npcStartConversation(player, self, "fs_experience_converter", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!fs_experience_converter_condition_canConvertXP(player, self))
        {
            doAnimationAction(self, "sigh_deeply");
            string_id message = new string_id(c_stringFile, "s_37fbfae6");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("fs_experience_converter"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_experience_converter.branchId");
        if (branchId == 1 && response.equals("s_73ab6ff4"))
        {
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                fs_experience_converter_action_showSUICrafting(player, self);
                string_id message = new string_id(c_stringFile, "s_7542cfea");
                removeObjVar(player, "conversation.fs_experience_converter.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome, friend! Come here and tell me what insight I may provide for you today?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_76fa10e4"))
        {
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                fs_experience_converter_action_showSUICombat(player, self);
                string_id message = new string_id(c_stringFile, "s_7542cfea");
                removeObjVar(player, "conversation.fs_experience_converter.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome, friend! Come here and tell me what insight I may provide for you today?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_968c3ff6"))
        {
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                fs_experience_converter_action_showSUISenses(player, self);
                string_id message = new string_id(c_stringFile, "s_7542cfea");
                removeObjVar(player, "conversation.fs_experience_converter.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome, friend! Come here and tell me what insight I may provide for you today?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_2734c210"))
        {
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                fs_experience_converter_action_showSUIReflexes(player, self);
                string_id message = new string_id(c_stringFile, "s_7542cfea");
                removeObjVar(player, "conversation.fs_experience_converter.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome, friend! Come here and tell me what insight I may provide for you today?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_69a7c6ca"))
        {
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                fs_experience_converter_action_showSUIUnlock(player, self);
                string_id message = new string_id(c_stringFile, "s_433c2a58");
                removeObjVar(player, "conversation.fs_experience_converter.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome, friend! Come here and tell me what insight I may provide for you today?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_e4c01185"))
        {
            if (fs_experience_converter_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7abe0196");
                removeObjVar(player, "conversation.fs_experience_converter.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_experience_converter_tokenTO_branchList(player, self));
                npcSpeak(player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome, friend! Come here and tell me what insight I may provide for you today?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_experience_converter.branchId");
        return SCRIPT_CONTINUE;
    }
}
