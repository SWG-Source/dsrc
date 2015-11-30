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
import script.library.space_combat;
import script.library.space_content;
import script.library.space_crafting;
import script.library.space_dungeon;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_transition;
import script.library.utils;

public class station_avatar_platform extends script.base_script
{
    public station_avatar_platform()
    {
    }
    public static String c_stringFile = "conversation/station_avatar_platform";
    public boolean station_avatar_platform_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean station_avatar_platform_condition_hasAuthorization(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_trando_hssissk_zssik_10") || groundquests.isQuestActive(player, "ep3_imp_cmd_richards");
    }
    public boolean station_avatar_platform_condition_returnToAvatar(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_avatar_return");
    }
    public boolean station_avatar_platform_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public void station_avatar_platform_action_launchAttack(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_surpriseattack", "ep3_avatar_defensive_attack");
    }
    public void station_avatar_platform_action_land(obj_id player, obj_id npc) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        space_dungeon.sendGroupToDungeonWithoutTicket(player, "avatar_platform", planet, planet, "quest_type", npc);
    }
    public int station_avatar_platform_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (station_avatar_platform_condition__defaultCondition(player, npc))
            {
                station_avatar_platform_action_land(player, npc);
                string_id message = new string_id(c_stringFile, "s_85");
                utils.removeScriptVar(player, "conversation.station_avatar_platform.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_83"))
        {
            if (station_avatar_platform_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.station_avatar_platform.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_avatar_platform_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (station_avatar_platform_condition__defaultCondition(player, npc))
            {
                station_avatar_platform_action_land(player, npc);
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.station_avatar_platform.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (station_avatar_platform_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.station_avatar_platform.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_avatar_platform_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (station_avatar_platform_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.station_avatar_platform.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            if (station_avatar_platform_condition__defaultCondition(player, npc))
            {
                station_avatar_platform_action_launchAttack(player, npc);
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.station_avatar_platform.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_station_tatooine.iff");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_station_tatooine.iff");
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
        detachScript(self, "conversation.station_avatar_platform");
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
        if (station_avatar_platform_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_60");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_avatar_platform_condition_returnToAvatar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_81");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_avatar_platform_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_avatar_platform_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                }
                utils.setScriptVar(player, "conversation.station_avatar_platform.branchId", 2);
                npcStartConversation(player, npc, "station_avatar_platform", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (station_avatar_platform_condition_hasAuthorization(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_62");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_avatar_platform_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_avatar_platform_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                }
                utils.setScriptVar(player, "conversation.station_avatar_platform.branchId", 5);
                npcStartConversation(player, npc, "station_avatar_platform", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (station_avatar_platform_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_72");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_avatar_platform_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_avatar_platform_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                }
                utils.setScriptVar(player, "conversation.station_avatar_platform.branchId", 8);
                npcStartConversation(player, npc, "station_avatar_platform", message, responses);
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
        if (!conversationId.equals("station_avatar_platform"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.station_avatar_platform.branchId");
        if (branchId == 2 && station_avatar_platform_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && station_avatar_platform_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && station_avatar_platform_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.station_avatar_platform.branchId");
        return SCRIPT_CONTINUE;
    }
}
