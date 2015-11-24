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
import script.library.utils;

public class death_watch_treadwell extends script.base_script
{
    public death_watch_treadwell()
    {
    }
    public static String c_stringFile = "conversation/death_watch_treadwell";
    public boolean death_watch_treadwell_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean death_watch_treadwell_condition_hasBattery(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                
                {
                    if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/drill_battery.iff"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void death_watch_treadwell_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void death_watch_treadwell_action_fixBattery(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/treadwell_chatter_02.cef", getLocation(npc), 0f);
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "handleCleanBattery", params, 1f, false);
        return;
    }
    public void death_watch_treadwell_action_vocalize_01(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/treadwell_chatter_01.cef", getLocation(npc), 0f);
        return;
    }
    public void death_watch_treadwell_action_vocalize_02(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/treadwell_chatter_02.cef", getLocation(npc), 0f);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.death_watch_treadwell");
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
        detachScript(self, "npc.conversation.death_watch_treadwell");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (death_watch_treadwell_condition__defaultCondition(player, self))
        {
            death_watch_treadwell_action_vocalize_01(player, self);
            string_id message = new string_id(c_stringFile, "s_9208410d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_treadwell_condition_hasBattery(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_treadwell_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4fcd015f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8e9e4ef9");
                }
                setObjVar(player, "conversation.death_watch_treadwell.branchId", 1);
                npcStartConversation(player, self, "death_watch_treadwell", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("death_watch_treadwell"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.death_watch_treadwell.branchId");
        if (branchId == 1 && response.equals("s_4fcd015f"))
        {
            if (death_watch_treadwell_condition__defaultCondition(player, self))
            {
                death_watch_treadwell_action_fixBattery(player, self);
                string_id message = new string_id(c_stringFile, "s_9208410d");
                removeObjVar(player, "conversation.death_watch_treadwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_8e9e4ef9"))
        {
            if (death_watch_treadwell_condition__defaultCondition(player, self))
            {
                death_watch_treadwell_action_vocalize_02(player, self);
                string_id message = new string_id(c_stringFile, "s_9208410d");
                removeObjVar(player, "conversation.death_watch_treadwell.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '...' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.death_watch_treadwell.branchId");
        return SCRIPT_CONTINUE;
    }
}
