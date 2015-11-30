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
import script.library.utils;

public class ep3_etyyy_harroom extends script.base_script
{
    public ep3_etyyy_harroom()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_harroom";
    public boolean ep3_etyyy_harroom_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_harroom_condition_needsUllerReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_hunt_sordaan_seek_harroom", "sordaan_ullerBetReward");
    }
    public boolean ep3_etyyy_harroom_condition_needsWallugaReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_hunt_sordaan_seek_harroom", "sordaan_wallugaBetReward");
    }
    public boolean ep3_etyyy_harroom_condition_needsMoufReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_hunt_sordaan_seek_harroom", "sordaan_moufBetReward");
    }
    public boolean ep3_etyyy_harroom_condition_needsWebweaverReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_hunt_sordaan_seek_harroom", "sordaan_webweaverBetReward");
    }
    public void ep3_etyyy_harroom_action_giveUllerReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_talkToHarroom");
        groundquests.grantQuest(player, "ep3_hunt_harroom_uller_reward", false);
    }
    public void ep3_etyyy_harroom_action_giveWallugaReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_talkToHarroom");
        groundquests.grantQuest(player, "ep3_hunt_harroom_walluga_reward", false);
    }
    public void ep3_etyyy_harroom_action_giveMoufReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_talkToHarroom");
        groundquests.grantQuest(player, "ep3_hunt_harroom_mouf_reward", false);
    }
    public void ep3_etyyy_harroom_action_giveWebweaverReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_talkToHarroom");
        groundquests.grantQuest(player, "ep3_hunt_harroom_webweaver_reward", false);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_etyyy_harroom");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.ep3_etyyy_harroom");
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
        if (ep3_etyyy_harroom_condition_needsUllerReward(player, npc))
        {
            ep3_etyyy_harroom_action_giveUllerReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_183");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_harroom_condition_needsWallugaReward(player, npc))
        {
            ep3_etyyy_harroom_action_giveWallugaReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_185");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_harroom_condition_needsMoufReward(player, npc))
        {
            ep3_etyyy_harroom_action_giveMoufReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_187");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_harroom_condition_needsWebweaverReward(player, npc))
        {
            ep3_etyyy_harroom_action_giveWebweaverReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_189");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_harroom_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_294");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_etyyy_harroom"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_harroom.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_harroom.branchId");
        return SCRIPT_CONTINUE;
    }
}
