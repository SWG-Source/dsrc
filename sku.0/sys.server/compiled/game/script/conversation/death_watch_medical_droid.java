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

public class death_watch_medical_droid extends script.base_script
{
    public death_watch_medical_droid()
    {
    }
    public static String c_stringFile = "conversation/death_watch_medical_droid";
    public boolean death_watch_medical_droid_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean death_watch_medical_droid_condition_hasObjVarMedicine(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "death_watch.drill_01"))
        {
            obj_id[] objContents = utils.getContents(player, true);
            if (objContents != null)
            {
                for (int intI = 0; intI < objContents.length; intI++)
                {
                    String strItemTemplate = getTemplateName(objContents[intI]);
                    if (strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/crazed_miner_medicine.iff"))
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public boolean death_watch_medical_droid_condition_noInvSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasNoInvRoom = false;
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space < 1)
            {
                hasNoInvRoom = true;
            }
        }
        return hasNoInvRoom;
    }
    public void death_watch_medical_droid_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void death_watch_medical_droid_action_giveMedicine(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id[] objContents = utils.getContents(player, true);
        if (objContents != null)
        {
            for (int intI = 0; intI < objContents.length; intI++)
            {
                String strItemTemplate = getTemplateName(objContents[intI]);
                if (!strItemTemplate.equals("object/tangible/dungeon/death_watch_bunker/crazed_miner_medicine.iff"))
                {
                    obj_id playerInv = getObjectInSlot(player, "inventory");
                    if (isIdValid(playerInv))
                    {
                        obj_id item = createObject("object/tangible/dungeon/death_watch_bunker/crazed_miner_medicine.iff", playerInv, "");
                        if (isIdValid(item))
                        {
                            return;
                        }
                    }
                }
            }
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.death_watch_medical_droid");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(self, true);
        setName(self, "G12-4J (a medical droid)");
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
        detachScript(self, "npc.conversation.death_watch_medical_droid");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (death_watch_medical_droid_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_22f2cc9b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_medical_droid_condition_hasObjVarMedicine(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_medical_droid_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ab36bcfc");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82d3782a");
                }
                setObjVar(player, "conversation.death_watch_medical_droid.branchId", 1);
                npcStartConversation(player, self, "death_watch_medical_droid", message, responses);
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
        if (!conversationId.equals("death_watch_medical_droid"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.death_watch_medical_droid.branchId");
        if (branchId == 1 && response.equals("s_ab36bcfc"))
        {
            if (!death_watch_medical_droid_condition_noInvSpace(player, self))
            {
                death_watch_medical_droid_action_giveMedicine(player, self);
                string_id message = new string_id(c_stringFile, "s_6a6a070d");
                removeObjVar(player, "conversation.death_watch_medical_droid.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (death_watch_medical_droid_condition_noInvSpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_46d286cc");
                removeObjVar(player, "conversation.death_watch_medical_droid.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome. I am medical droid designation G12-4J. How may I be of service to you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_82d3782a"))
        {
            if (death_watch_medical_droid_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_52a263b9");
                removeObjVar(player, "conversation.death_watch_medical_droid.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome. I am medical droid designation G12-4J. How may I be of service to you?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.death_watch_medical_droid.branchId");
        return SCRIPT_CONTINUE;
    }
}
