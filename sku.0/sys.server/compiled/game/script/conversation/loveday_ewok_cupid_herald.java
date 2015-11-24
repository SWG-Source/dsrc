package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class loveday_ewok_cupid_herald extends script.base_script
{
    public loveday_ewok_cupid_herald()
    {
    }
    public static String c_stringFile = "conversation/loveday_ewok_cupid_herald";
    public boolean loveday_ewok_cupid_herald_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.loveday_ewok_cupid_herald");
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
    public int prepareForDespawn(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        playClientEffectLoc(getPlayerCreaturesInRange(here, 100.0f), "appearance/pt_efol_hearts_large_noloop.prt", here, 1.0f);
        messageTo(self, "handleDestroySelf", null, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDestroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 20f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                npcEndConversation(players[i]);
            }
        }
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
        clearCondition(self, CONDITION_SPACE_INTERESTING);
        detachScript(self, "conversation.loveday_ewok_cupid_herald");
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
        if (loveday_ewok_cupid_herald_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("loveday_ewok_cupid_herald"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.loveday_ewok_cupid_herald.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.loveday_ewok_cupid_herald.branchId");
        return SCRIPT_CONTINUE;
    }
}
