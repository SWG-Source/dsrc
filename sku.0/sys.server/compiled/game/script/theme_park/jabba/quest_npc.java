package script.theme_park.jabba;

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
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(speaker, questID + ".vip"))
        {
            obj_id courier = getObjIdObjVar(speaker, questID + ".vip");
            if (courier == self)
            {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (type.equals("escort") || type.equals("arrest"))
                {
                    String reward = "npc_takeme_" + questNum;
                    string_id message = new string_id(CONVO, reward);
                    chat.chat(self, message);
                    dictionary parms = new dictionary();
                    parms.put("player", speaker);
                    messageTo(self, "followPlayer", parms, 2, true);
                    messageTo(speaker, "finishJabbaQuest", null, 2, true);
                    return SCRIPT_OVERRIDE;
                }
                else if (type.equals("smuggle") || type.equals("deliver"))
                {
                    if (checkForItem(playerInv, speaker, questNum) == true)
                    {
                        String reward = "npc_smuggle_" + questNum;
                        string_id message = new string_id(CONVO, reward);
                        chat.chat(self, message);
                        dictionary parms = new dictionary();
                        parms.put("player", speaker);
                        messageTo(speaker, "finishJabbaQuest", null, 1, true);
                        return SCRIPT_OVERRIDE;
                    }
                    else 
                    {
                        string_id work = new string_id(CONVO, "gotowork");
                        chat.chat(self, work);
                        return SCRIPT_CONTINUE;
                    }
                }
                else if (type.equals("retrieve"))
                {
                    String reward = "npc_smuggle_" + questNum;
                    string_id message = new string_id(CONVO, reward);
                    chat.chat(self, message);
                    messageTo(speaker, "finishJabbaQuest", null, 0, true);
                    String retrieveObject = dataTableGetString(datatable, questNum, "retrieve_object");
                    if (retrieveObject == null)
                    {
                        retrieveObject = "none";
                    }
                    if (!retrieveObject.equals("none"))
                    {
                        createObject(retrieveObject, playerInv, "");
                    }
                    return SCRIPT_OVERRIDE;
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
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("destroy"))
        {
            messageTo(player, "finishJabbaQuest", null, 0, true);
            return SCRIPT_CONTINUE;
        }
        else if (type.equals("fetch"))
        {
            String reward = dataTableGetString(datatable, questNum, "retrieve_object");
            if (reward == null)
            {
                reward = "none";
            }
            if (!reward.equals("none"))
            {
                obj_id playerInv = utils.getInventoryContainer(player);
                createObject(reward, playerInv, "");
            }
            else 
            {
                string_id badItem = new string_id("theme_park/messages", "no_item_message");
                String nospawn = getString(badItem);
                sendSystemMessage(self, nospawn, null);
            }
            messageTo(player, "finishJabbaQuest", null, 0, true);
            return SCRIPT_CONTINUE;
        }
        else if (type.equals("escort") || type.equals("arrest"))
        {
            if (questID != null && !questID.equals(""))
            {
                int playerQuest = getIntObjVar(player, "theme_park_jabba");
                if (questNum == playerQuest)
                {
                    obj_id escortee = getObjIdObjVar(player, questID + ".vip");
                    if (escortee == self)
                    {
                        setObjVar(player, questID + ".failed", 1);
                        string_id failed = new string_id("theme_park/messages", "generic_fail_message");
                        String failure = getString(failed);
                        sendSystemMessage(self, failure, null);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int followPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        setMaster(self, player);
        ai_lib.aiFollow(self, player);
        return SCRIPT_CONTINUE;
    }
    public int stopFollowing(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "questNum");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        debugSpeakMsg(self, "Thanks for bringing me back.");
        obj_id player = params.getObjId("player");
        ai_lib.aiStopFollowing(self);
        detachScript(player, playerScript);
        messageTo(self, "cleanUp", null, 60, true);
        if (questID != null && !questID.equals(""))
        {
            setObjVar(player, questID + ".done", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int setupSelf(obj_id self, dictionary params) throws InterruptedException
    {
        setWantSawAttackTriggers(self, false);
        String datatable = getStringObjVar(self, "quest_table");
        if (datatable == null)
        {
            setObjVar(self, "NO SCRIPT", 1);
            return SCRIPT_OVERRIDE;
        }
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (type.equals("rescue") || type.equals("smuggle") || type.equals("deliver") || type.equals("escort") || type.equals("arrest") || type.equals("retrieve"))
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
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String something = "npc_breech_" + questNum;
        string_id message = new string_id(CONVO, something);
        if (message != null)
        {
            if (ai_lib.isNpc(self))
            {
                String messageCheck = getString(message);
                if (messageCheck != null)
                {
                    if (!messageCheck.equals(""))
                    {
                        chat.chat(self, message);
                    }
                }
            }
        }
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
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String reward = dataTableGetString(datatable, questNum, "reward");
        if (reward == null)
        {
            reward = "none";
        }
        if (reward.equals("credits"))
        {
            money.bankTo(money.ACCT_JABBA, player, 100);
        }
        else 
        {
            if (!reward.equals("none"))
            {
                obj_id playerInv = utils.getInventoryContainer(player);
                createObject(reward, playerInv, "");
            }
        }
        if (questID != null && !questID.equals(""))
        {
            setObjVar(player, questID + ".done", 1);
            messageTo(player, "finishJabbaQuest", null, 0, true);
        }
        detachScript(player, playerScript);
        return SCRIPT_CONTINUE;
    }
    public boolean checkForItem(obj_id inv, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(player, "quest_table");
        String giveMe = dataTableGetString(datatable, questNum, "deliver_object");
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
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        obj_id player = getObjIdObjVar(self, "player");
        int questNum = getIntObjVar(self, "questNum");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (!hasObjVar(player, questID + ".done"))
        {
            if (questID != null && !questID.equals(""))
            {
                int playerQuest = getIntObjVar(player, "theme_park_jabba");
                if (playerQuest == questNum)
                {
                    setObjVar(player, questID + ".failed", 1);
                    string_id failed = new string_id("theme_park/messages", "generic_fail_message");
                    String failure = getString(failed);
                    sendSystemMessage(self, failure, null);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
