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
import script.library.groundquests;
import script.library.instance;
import script.library.utils;

public class heroic_sd_intro_imperial extends script.base_script
{
    public heroic_sd_intro_imperial()
    {
    }
    public static String c_stringFile = "conversation/heroic_sd_intro_imperial";
    public boolean heroic_sd_intro_imperial_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean heroic_sd_intro_imperial_condition_notImperialFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return !factions.isImperial(player);
    }
    public boolean heroic_sd_intro_imperial_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "star_destroyer_intro_imperial");
    }
    public boolean heroic_sd_intro_imperial_condition_returningQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "star_destroyer_intro_imperial", "star_destroyer_intro_07") || groundquests.hasCompletedQuest(player, "star_destroyer_intro_imperial");
    }
    public void heroic_sd_intro_imperial_action_grantIntroQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "star_destroyer_intro_imperial");
        String questUtilityScript = "theme_park.dathomir.aurilia.star_destroyer_intro_player";
        if (!hasScript(player, questUtilityScript))
        {
            attachScript(player, questUtilityScript);
        }
        return;
    }
    public void heroic_sd_intro_imperial_action_sendIntroSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "star_destroyer_intro_07");
        if (!instance.isFlaggedForInstance(player, "heroic_star_destroyer"))
        {
            instance.flagPlayerForInstance(player, "heroic_star_destroyer");
        }
    }
    public int heroic_sd_intro_imperial_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (heroic_sd_intro_imperial_condition__defaultCondition(player, npc))
            {
                heroic_sd_intro_imperial_action_grantIntroQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_14");
                utils.removeScriptVar(player, "conversation.heroic_sd_intro_imperial.branchId");
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
            detachScript(self, "conversation.heroic_sd_intro_imperial");
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
        detachScript(self, "conversation.heroic_sd_intro_imperial");
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
        if (heroic_sd_intro_imperial_condition_notImperialFaction(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (heroic_sd_intro_imperial_condition_returningQuest(player, npc))
        {
            heroic_sd_intro_imperial_action_sendIntroSignal(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (heroic_sd_intro_imperial_condition_onQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (heroic_sd_intro_imperial_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (heroic_sd_intro_imperial_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                utils.setScriptVar(player, "conversation.heroic_sd_intro_imperial.branchId", 4);
                npcStartConversation(player, npc, "heroic_sd_intro_imperial", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("heroic_sd_intro_imperial"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.heroic_sd_intro_imperial.branchId");
        if (branchId == 4 && heroic_sd_intro_imperial_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.heroic_sd_intro_imperial.branchId");
        return SCRIPT_CONTINUE;
    }
}
