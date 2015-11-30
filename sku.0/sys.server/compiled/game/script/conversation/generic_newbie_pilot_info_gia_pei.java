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
import script.library.features;
import script.library.skill;
import script.library.space_quest;
import script.library.space_skill;
import script.library.utils;

public class generic_newbie_pilot_info_gia_pei extends script.base_script
{
    public generic_newbie_pilot_info_gia_pei()
    {
    }
    public static String c_stringFile = "conversation/generic_newbie_pilot_info_gia_pei";
    public boolean generic_newbie_pilot_info_gia_pei_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean generic_newbie_pilot_info_gia_pei_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metNewbiePilot"));
    }
    public boolean generic_newbie_pilot_info_gia_pei_condition_isImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean generic_newbie_pilot_info_gia_pei_condition_isRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean generic_newbie_pilot_info_gia_pei_condition_isPrivateerPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_neutral_novice");
    }
    public boolean generic_newbie_pilot_info_gia_pei_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean generic_newbie_pilot_info_gia_pei_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasShip(player));
    }
    public void generic_newbie_pilot_info_gia_pei_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metNewbiePilot", true);
    }
    public void generic_newbie_pilot_info_gia_pei_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery", "tatooine_newbie_1");
        space_quest.grantNewbieShip(player, "rebel");
    }
    public int generic_newbie_pilot_info_gia_pei_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96dd8566"))
        {
            if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ea09eddd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_gia_pei_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (generic_newbie_pilot_info_gia_pei_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_170ed80d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e05e22e2");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d88625cb");
                    }
                    setObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d14f41b0"))
        {
            if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_126a14e0");
                removeObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9288900f"))
        {
            if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow");
                string_id message = new string_id(c_stringFile, "s_c8f2f3db");
                removeObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_gia_pei_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_170ed80d"))
        {
            if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_77266474");
                removeObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e05e22e2"))
        {
            if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_b7b44868");
                removeObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d88625cb"))
        {
            if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_56866a7");
                removeObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId");
                npcSpeak(player, message);
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
            detachScript(self, "conversation.generic_newbie_pilot_info_gia_pei");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Gi'a Pei");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Gi'a Pei");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.generic_newbie_pilot_info_gia_pei");
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
        if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_1db66446");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (generic_newbie_pilot_info_gia_pei_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96dd8566");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d14f41b0");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9288900f");
                }
                setObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId", 1);
                npcStartConversation(player, npc, "generic_newbie_pilot_info_gia_pei", message, responses);
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
        if (!conversationId.equals("generic_newbie_pilot_info_gia_pei"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = getIntObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId");
        if (branchId == 1 && generic_newbie_pilot_info_gia_pei_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && generic_newbie_pilot_info_gia_pei_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.generic_newbie_pilot_info_gia_pei.branchId");
        return SCRIPT_CONTINUE;
    }
}
