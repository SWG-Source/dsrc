package script.theme_park.tatooine.mos_taike;

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

public class cantina_receiver extends script.base_script
{
    public cantina_receiver()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String datatable = "datatables/theme_park/mos_taike_cantina.iff";
        String CONVO = "theme_park_mos_taike/cantina_owner";
        int questNum = getIntObjVar(self, "quest");
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(speaker, "mos_taike.cantina.receiver"))
        {
            obj_id courier = getObjIdObjVar(speaker, "mos_taike.cantina.receiver");
            if (courier == self)
            {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (checkForItem(playerInv) == true)
                {
                    String reward = "npc_thanks_" + (questNum);
                    string_id message = new string_id(CONVO, reward);
                    chat.chat(self, message);
                    messageTo(speaker, "finishQuest", null, 0, true);
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    string_id work = new string_id(CONVO, "gotowork");
                    chat.chat(self, work);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                string_id cant = new string_id(CONVO, "cantwork");
                chat.chat(self, cant);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            String npcGreet = "npc_1_" + questNum;
            String response1 = "player_1_" + questNum;
            String response2 = "player_2_" + questNum;
            String response3 = "player_3_" + questNum;
            string_id greeting = new string_id(CONVO, npcGreet);
            string_id response[] = new string_id[3];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            response[2] = new string_id(CONVO, response3);
            npcStartConversation(speaker, self, "questConvo", greeting, response);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkForItem(obj_id inv) throws InterruptedException
    {
        String datatable = "datatables/theme_park/mos_taike_cantina.iff";
        int questNum = getIntObjVar(getSelf(), "quest");
        String giveMe = dataTableGetString(datatable, 0, questNum - 1);
        boolean hadIt = false;
        obj_id[] contents = getContents(inv);
        for (int i = 0; i < contents.length; i++)
        {
            String itemInInventory = getTemplateName(contents[i]);
            if (itemInInventory.equals(giveMe))
            {
                destroyObject(contents[i]);
                hadIt = true;
            }
        }
        return hadIt;
    }
}
