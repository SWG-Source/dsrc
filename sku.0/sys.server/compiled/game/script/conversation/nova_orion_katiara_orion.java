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
import script.library.chat;
import script.library.groundquests;
import script.library.township;
import script.library.utils;

public class nova_orion_katiara_orion extends script.base_script
{
    public nova_orion_katiara_orion()
    {
    }
    public static String c_stringFile = "conversation/nova_orion_katiara_orion";
    public boolean nova_orion_katiara_orion_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean nova_orion_katiara_orion_condition_isMyPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id myPlayer = utils.getObjIdScriptVar(npc, "myPlayer");
        if (isIdValid(myPlayer))
        {
            if (myPlayer == player)
            {
                return true;
            }
        }
        else 
        {
            obj_id parent = utils.getObjIdScriptVar(npc, "objParent");
            if (isIdValid(parent))
            {
                dictionary params = new dictionary();
                params.put("player", player);
                messageTo(parent, "cleanupNoFinaleEvent", params, 1, false);
            }
            else 
            {
                messageTo(npc, "emergencySelfCleanUp", null, 1, false);
            }
        }
        return false;
    }
    public void nova_orion_katiara_orion_action_finalReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "nova_orion_rank6_finale_02");
        buff.applyBuff(player, "nova_orion_rank6_lucky_salvage");
        setObjVar(player, township.NOVA_ORION_FINALE_COMPLETED, true);
        npcEndConversation(player);
        obj_id parent = utils.getObjIdScriptVar(npc, "objParent");
        if (isIdValid(parent))
        {
            dictionary params = new dictionary();
            params.put("player", player);
            messageTo(parent, "cleanupNoFinaleEvent", params, 3, false);
        }
        else 
        {
            messageTo(npc, "emergencySelfCleanUp", null, 1, false);
        }
        return;
    }
    public int nova_orion_katiara_orion_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            doAnimationAction(player, "bow");
            if (nova_orion_katiara_orion_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave1");
                nova_orion_katiara_orion_action_finalReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_8");
                utils.removeScriptVar(player, "conversation.nova_orion_katiara_orion.branchId");
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
            detachScript(self, "conversation.nova_orion_katiara_orion");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int emergencySelfCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
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
        detachScript(self, "conversation.nova_orion_katiara_orion");
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
        if (nova_orion_katiara_orion_condition_isMyPlayer(player, npc))
        {
            doAnimationAction(npc, "applause_excited");
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (nova_orion_katiara_orion_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.nova_orion_katiara_orion.branchId", 1);
                npcStartConversation(player, npc, "nova_orion_katiara_orion", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (nova_orion_katiara_orion_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("nova_orion_katiara_orion"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.nova_orion_katiara_orion.branchId");
        if (branchId == 1 && nova_orion_katiara_orion_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.nova_orion_katiara_orion.branchId");
        return SCRIPT_CONTINUE;
    }
}
