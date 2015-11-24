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

public class som_mustafar_junk_dealer extends script.base_script
{
    public som_mustafar_junk_dealer()
    {
    }
    public static String c_stringFile = "conversation/som_mustafar_junk_dealer";
    public boolean som_mustafar_junk_dealer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean som_mustafar_junk_dealer_condition_check_inv(obj_id player, obj_id npc) throws InterruptedException
    {
        String datatable = "datatables/npc/junk_dealer/junk_dealer.iff";
        String item = "";
        String itemTemplate = "";
        int itemLength = dataTableGetNumRows(datatable);
        int x = 0;
        int y = 0;
        obj_id inventory = utils.getInventoryContainer(player);
        if (isIdValid(inventory))
        {
            obj_id[] contents = utils.getContents(inventory, true);
            if (contents != null)
            {
                for (x = 0; x < contents.length; x++)
                {
                    y = 0;
                    if (hasObjVar(contents[x], "junkDealer.intPrice"))
                    {
                        return true;
                    }
                    if (!isCrafted(contents[x]))
                    {
                        itemTemplate = getTemplateName(contents[x]);
                        while (y < itemLength)
                        {
                            item = dataTableGetString(datatable, y, "items");
                            if (itemTemplate.equals(item))
                            {
                                return true;
                            }
                            y++;
                        }
                    }
                }
            }
        }
        return false;
    }
    public void som_mustafar_junk_dealer_action_start_dealing(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "startDealing", params, 1.0f, false);
    }
    public int som_mustafar_junk_dealer_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (som_mustafar_junk_dealer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "beckon");
                som_mustafar_junk_dealer_action_start_dealing(player, npc);
                string_id message = new string_id(c_stringFile, "s_8");
                utils.removeScriptVar(player, "conversation.som_mustafar_junk_dealer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10"))
        {
            doAnimationAction(player, "shrug_hands");
            if (som_mustafar_junk_dealer_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                string_id message = new string_id(c_stringFile, "s_12");
                utils.removeScriptVar(player, "conversation.som_mustafar_junk_dealer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.som_mustafar_junk_dealer");
        }
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
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
        detachScript(self, "conversation.som_mustafar_junk_dealer");
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
        if (som_mustafar_junk_dealer_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "bow2");
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (som_mustafar_junk_dealer_condition_check_inv(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (som_mustafar_junk_dealer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                }
                utils.setScriptVar(player, "conversation.som_mustafar_junk_dealer.branchId", 1);
                npcStartConversation(player, npc, "som_mustafar_junk_dealer", message, responses);
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
        if (!conversationId.equals("som_mustafar_junk_dealer"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.som_mustafar_junk_dealer.branchId");
        if (branchId == 1 && som_mustafar_junk_dealer_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.som_mustafar_junk_dealer.branchId");
        return SCRIPT_CONTINUE;
    }
}
