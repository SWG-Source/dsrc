package script.npc.random_quest;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

public class quest_npc extends script.base_script
{
    public quest_npc()
    {
    }
    public static final String FACETO_VOLUME_NAME = "faceToTriggerVolume";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setupSelf", null, 4, true);
        createTriggerVolume(FACETO_VOLUME_NAME, 8.0f, true);
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
                if (type.equals("rescue") || type.equals("escort") || type.equals("arrest"))
                {
                    String reward = "npc_takeme_" + questNum;
                    string_id message = new string_id(CONVO, reward);
                    chat.chat(self, speaker, message);
                    dictionary parms = new dictionary();
                    parms.put("player", speaker);
                    messageTo(self, "followPlayer", parms, 2, true);
                    messageTo(speaker, "finishRandomQuest", null, 2, true);
                    messageTo(self, "spawnExtras", null, 2, true);
                    return SCRIPT_OVERRIDE;
                }
                else if (type.equals("smuggle") || type.equals("deliver"))
                {
                    obj_id playerInv = utils.getInventoryContainer(speaker);
                    if (checkForItem(playerInv, speaker, questNum) == true)
                    {
                        String reward = "npc_smuggle_" + questNum;
                        string_id message = new string_id(CONVO, reward);
                        chat.chat(self, speaker, message);
                        messageTo(speaker, "finishRandomQuest", null, 2, true);
                        return SCRIPT_OVERRIDE;
                    }
                    else 
                    {
                        string_id work = new string_id(CONVO, "gotowork");
                        chat.chat(self, speaker, work);
                        return SCRIPT_CONTINUE;
                    }
                }
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id work = new string_id(CONVO, "otherescort");
                chat.chat(self, speaker, work);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            string_id blah = new string_id(CONVO, "dontknowyou");
            chat.chat(self, speaker, blah);
            return SCRIPT_CONTINUE;
        }
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        obj_id player = getObjIdObjVar(self, "player");
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("destroy"))
        {
            messageTo(player, "finishRandomQuest", null, 2, true);
            return SCRIPT_CONTINUE;
        }
        else if (type.equals("fetch"))
        {
            String reward = dataTableGetString(datatable, questNum, "retrieve_object");
            obj_id playerInv = utils.getInventoryContainer(player);
            createObject(reward, playerInv, "");
            messageTo(player, "finishRandomQuest", null, 2, true);
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
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        debugSpeakMsg(self, "Thanks for bringing me back.");
        obj_id player = params.getObjId("player");
        ai_lib.aiStopFollowing(self);
        detachScript(player, playerScript);
        if (questID != null && !questID.equals(""))
        {
            if (questID != null && !questID.equals(""))
            {
                setObjVar(player, questID + ".done", 1);
                messageTo(self, "cleanUp", null, 30, true);
            }
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
        if (type.equals("rescue") || type.equals("smuggle") || type.equals("deliver") || type.equals("arrest") || type.equals("escort"))
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
        chat.chat(self, player, message);
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
        if (reward.equals("credits"))
        {
            money.bankTo(money.ACCT_JABBA, player, 100);
        }
        else 
        {
            obj_id playerInv = utils.getInventoryContainer(player);
            createObject(reward, playerInv, "");
        }
        if (questID != null && !questID.equals(""))
        {
            if (questID != null && !questID.equals(""))
            {
                setObjVar(player, questID + ".done", 1);
                messageTo(player, "finishRandomQuest", null, 2, true);
                detachScript(player, playerScript);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkForItem(obj_id inv, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(player, "quest_table");
        String giveMe = dataTableGetString(datatable, questNum, "deliver_object");
        boolean hadIt = false;
        obj_id[] contents = getContents(inv);
        for (obj_id content : contents) {
            String itemInInventory = getTemplateName(content);
            if (itemInInventory.equals(giveMe)) {
                destroyObject(content);
                hadIt = true;
            }
        }
        return hadIt;
    }
    public int spawnExtras(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "questNum");
        location here = getLocation(self);
        String spawn = dataTableGetString(datatable, questNum, "extra_npc");
        String spawn2 = dataTableGetString(datatable, questNum, "extra_npc2");
        String spawn3 = dataTableGetString(datatable, questNum, "extra_npc3");
        String spawn4 = dataTableGetString(datatable, questNum, "extra_npc4");
        if (!spawn.equals("none"))
        {
            here.x = here.x + rand(5, 20);
            here.z = here.z + rand(5, 20);
            obj_id extra = create.object(spawn, here);
            String disposition = dataTableGetString(datatable, questNum, "extra_npc_disposition");
            if (disposition.equals("aggro"))
            {
                startCombat(extra, player);
            }
        }
        if (!spawn2.equals("none"))
        {
            here.x = here.x + rand(5, 20);
            here.z = here.z + rand(5, 20);
            obj_id extra2 = create.object(spawn2, here);
            String disposition2 = dataTableGetString(datatable, questNum, "extra_npc2_disposition");
            if (disposition2.equals("aggro"))
            {
                startCombat(extra2, player);
            }
        }
        if (!spawn3.equals("none"))
        {
            here.x = here.x + rand(5, 20);
            here.z = here.z + rand(5, 20);
            obj_id extra3 = create.object(spawn3, here);
            String disposition3 = dataTableGetString(datatable, questNum, "extra_npc3_disposition");
            if (disposition3.equals("aggro"))
            {
                startCombat(extra3, player);
            }
        }
        if (!spawn4.equals("none"))
        {
            here.x = here.x + rand(5, 20);
            here.z = here.z + rand(5, 20);
            obj_id extra4 = create.object(spawn4, here);
            String disposition4 = dataTableGetString(datatable, questNum, "extra_npc4_disposition");
            if (disposition4.equals("aggro"))
            {
                startCombat(extra4, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (!volumeName.equals(FACETO_VOLUME_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (isInNpcConversation(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (canSee(self, breacher))
        {
            faceTo(self, breacher);
        }
        return SCRIPT_CONTINUE;
    }
}
