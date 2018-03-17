package script.theme_park;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.ai_lib;
import script.library.chat;
import script.library.utils;
import script.ai.ai_combat;
import script.library.money;
import script.library.create;

public class quest_npc extends script.base_script
{
    public quest_npc()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setupSelf", null, 4, true);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "questNum");
        String CONVO = dataTableGetString(datatable, 1, questNum);
        String questID = dataTableGetString(datatable, 3, questNum);
        String type = dataTableGetString(datatable, 0, questNum);
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(speaker, questID + ".vip"))
        {
            obj_id courier = getObjIdObjVar(speaker, questID + ".vip");
            if (courier == self)
            {
                if (type.equals("rescue"))
                {
                    String reward = "npc_takeme_" + questNum;
                    string_id message = new string_id(CONVO, reward);
                    chat.chat(self, message);
                    dictionary parms = new dictionary();
                    parms.put("player", speaker);
                    messageTo(self, "followPlayer", parms, 0, true);
                    messageTo(speaker, "finishQuest", null, 0, true);
                    messageTo(self, "spawnExtras", null, 0, true);
                    return SCRIPT_OVERRIDE;
                }
                else if (type.equals("smuggle"))
                {
                    obj_id playerInv = utils.getInventoryContainer(speaker);
                    if (checkForItem(playerInv, speaker, questNum) == true)
                    {
                        String reward = "npc_smuggle_" + questNum;
                        string_id message = new string_id(CONVO, reward);
                        chat.chat(self, message);
                        dictionary parms = new dictionary();
                        parms.put("player", speaker);
                        messageTo(self, "giveReward", parms, 0, true);
                        return SCRIPT_OVERRIDE;
                    }
                    else 
                    {
                        string_id work = new string_id(CONVO, "gotowork");
                        chat.chat(self, work);
                        return SCRIPT_CONTINUE;
                    }
                }
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id work = new string_id(CONVO, "otherescort");
                chat.chat(self, work);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            string_id blah = new string_id(CONVO, "dontknowyou");
            chat.chat(self, blah);
            return SCRIPT_CONTINUE;
        }
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        obj_id player = getObjIdObjVar(self, "player");
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, 0, questNum);
        if (type.equals("destroy"))
        {
            messageTo(player, "finishQuest", null, 0, true);
            return SCRIPT_CONTINUE;
        }
        else if (type.equals("fetch"))
        {
            String reward = dataTableGetString(datatable, 4, questNum);
            obj_id playerInv = utils.getInventoryContainer(player);
            createObject(reward, playerInv, "");
            messageTo(player, "finishQuest", null, 0, true);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int followPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        ai_lib.aiFollow(self, player);
        return SCRIPT_CONTINUE;
    }
    public int stopFollowing(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/theme_park/" + datatable + ".iff";
        int questNum = getIntObjVar(self, "questNum");
        String questID = dataTableGetString(datatable, 3, questNum);
        String playerScript = dataTableGetString(datatable, 6, questNum);
        debugSpeakMsg(self, "Thanks for bringing me back.");
        obj_id player = params.getObjId("player");
        ai_lib.aiStopFollowing(self);
        detachScript(player, playerScript);
        setObjVar(player, questID + ".done", 1);
        messageTo(self, "cleanUp", null, 60, true);
        return SCRIPT_CONTINUE;
    }
    public int setupSelf(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        if (datatable == null)
        {
            setObjVar(self, "NO SCRIPT", 1);
            return SCRIPT_OVERRIDE;
        }
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, 0, questNum);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (type.equals("rescue") || type.equals("smuggle"))
        {
            attachScript(self, "npc.converse.npc_converse_menu");
        }
        return SCRIPT_CONTINUE;
    }
    public int saySomething(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        obj_id player = getObjIdObjVar(self, "player");
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, 0, questNum);
        String CONVO = dataTableGetString(datatable, 1, questNum);
        String something = "npc_breech_" + questNum;
        string_id message = new string_id(CONVO, something);
        chat.chat(self, message);
        if (type.equals("destroy") || type.equals("fetch"))
        {
            startCombat(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int giveReward(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        String datatable = getStringObjVar(player, "quest_table");
        int questNum = getIntObjVar(self, "quest");
        String playerScript = dataTableGetString(datatable, 6, questNum);
        String questID = dataTableGetString(datatable, 3, questNum);
        String reward = dataTableGetString(datatable, 4, questNum);
        if (reward.equals("credits"))
        {
            money.bankTo(money.ACCT_JABBA, player, 100);
        }
        else 
        {
            obj_id playerInv = utils.getInventoryContainer(player);
            createObject(reward, playerInv, "");
        }
        setObjVar(player, questID + ".done", 1);
        messageTo(player, "finishQuest", null, 0, true);
        detachScript(player, playerScript);
        return SCRIPT_CONTINUE;
    }
    public boolean checkForItem(obj_id inv, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(player, "quest_table");
        String giveMe = dataTableGetString(datatable, 5, questNum);
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
    public int spawnExtras(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "quest");
        location here = getLocation(self);
        obj_id player = getObjIdObjVar(self, "player");
        String columnName = dataTableGetColumnName(datatable, questNum);
        String[] rows = dataTableGetStringColumnNoDefaults(datatable, columnName);
        int numRows = rows.length;
        if (numRows > 9)
        {
            int x = 10;
            while (x <= numRows)
            {
                String spawn = dataTableGetString(datatable, x, questNum);
                int around = rand(5, 20);
                here.x = here.x + around;
                here.z = here.z + around;
                obj_id enemy = create.object(spawn, here);
                startCombat(enemy, player);
                x = x + 1;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
