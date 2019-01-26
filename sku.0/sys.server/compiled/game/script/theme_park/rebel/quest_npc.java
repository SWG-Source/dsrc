package script.theme_park.rebel;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.library.utils;
import script.location;
import script.obj_id;
import script.string_id;

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
        String gatingString = "theme_park_rebel";
        String questID = "rebel_hideout";
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
                switch (type) {
                    case "escort":
                    case "arrest": {
                        String reward = "npc_takeme_" + questNum;
                        string_id message = new string_id(CONVO, reward);
                        chat.chat(self, message);
                        dictionary parms = new dictionary();
                        parms.put("player", speaker);
                        messageTo(self, "followPlayer", parms, 3, true);
                        messageTo(speaker, "finishRebelQuest", null, 1, true);
                        return SCRIPT_OVERRIDE;
                    }
                    case "smuggle":
                    case "deliver":
                        if (checkForItem(playerInv, speaker, questNum) == true) {
                            String reward = "npc_smuggle_" + questNum;
                            string_id message = new string_id(CONVO, reward);
                            chat.chat(self, message);
                            dictionary parms = new dictionary();
                            parms.put("player", speaker);
                            messageTo(speaker, "finishRebelQuest", null, 1, true);
                            return SCRIPT_OVERRIDE;
                        } else {
                            string_id work = new string_id(CONVO, "gotowork");
                            chat.chat(self, work);
                            return SCRIPT_CONTINUE;
                        }
                    case "retrieve": {
                        String reward = "npc_smuggle_" + questNum;
                        string_id message = new string_id(CONVO, reward);
                        chat.chat(self, message);
                        messageTo(speaker, "finishRebelQuest", null, 0, true);
                        String retrieveObject = dataTableGetString(datatable, questNum, "retrieve_object");
                        if (retrieveObject == null) {
                            retrieveObject = "none";
                        }
                        if (!retrieveObject.equals("none")) {
                            createObject(retrieveObject, playerInv, "");
                        }
                        return SCRIPT_OVERRIDE;
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
            string_id blah = new string_id(CONVO, "dontknowyou_" + questNum);
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
        switch (type) {
            case "destroy":
                messageTo(player, "finishRebelQuest", null, 0, true);
                return SCRIPT_CONTINUE;
            case "fetch":
                String reward = dataTableGetString(datatable, questNum, "retrieve_object");
                obj_id playerInv = utils.getInventoryContainer(player);
                createObject(reward, playerInv, "");
                messageTo(player, "finishRebelQuest", null, 1, true);
                return SCRIPT_CONTINUE;
            case "rescue":
            case "escort":
            case "arrest":
                if (questID != null && !questID.equals("")) {
                    int playerQuest = getIntObjVar(player, "theme_park_rebel");
                    if (questNum == playerQuest) {
                        setObjVar(player, questID + ".failed", 1);
                        string_id failed = new string_id("theme_park/messages", "generic_fail_message");
                        String failure = getString(failed);
                        sendSystemMessage(self, failure, null);
                    }
                }
                break;
            case "retrieve":
            case "smuggle":
            case "deliver":
                if (questID != null && !questID.equals("")) {
                    int playerQuest = getIntObjVar(player, "theme_park_rebel");
                    if (questNum == playerQuest) {
                        if (!hasObjVar(player, questID + ".done")) {
                            setObjVar(player, questID + ".failed", 1);
                            string_id killed = new string_id("theme_park/messages", "rebel_quest_npc_killed");
                            string_id failed = new string_id("theme_park/messages", "generic_fail_message");
                            String killure = getString(killed);
                            String failure = getString(failed);
                            sendSystemMessage(player, killure, null);
                            sendSystemMessage(player, failure, null);
                        }
                    }
                }
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public int followPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        setMaster(self, player);
        ai_lib.aiFollow(self, player);
        location home = getLocationObjVar(self, "home");
        addLocationTarget("home", home, 20);
        return SCRIPT_CONTINUE;
    }
    public int stopFollowing(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "questNum");
        String gatingString = "theme_park_rebel";
        String questID = "rebel_hideout";
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        debugSpeakMsg(self, "Thanks for bringing me back.");
        obj_id player = params.getObjId("player");
        ai_lib.aiStopFollowing(self);
        detachScript(player, playerScript);
        if (questID != null && !questID.equals(""))
        {
            setObjVar(player, questID + ".done", 1);
        }
        messageTo(self, "cleanUp", null, 60, true);
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
        if (hasScript(self, "npc.faction_recruiter.recruiter_setup"))
        {
            detachScript(self, "npc.faction_recruiter.recruiter_setup");
            clearCondition(self, CONDITION_CONVERSABLE);
        }
        if (type.equals("escort") || type.equals("smuggle") || type.equals("deliver") || type.equals("arrest") || type.equals("retrieve") || type.equals("rescue"))
        {
            setInvulnerable(self, true);
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
        if (ai_lib.isNpc(self))
        {
            String something = "npc_breech_" + questNum;
            string_id message = new string_id(CONVO, something);
            chat.chat(self, message);
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
        String questID = "rebel_hideout";
        String reward = dataTableGetString(datatable, questNum, "reward");
        if (reward != null)
        {
            obj_id playerInv = utils.getInventoryContainer(player);
            createObject(reward, playerInv, "");
        }
        if (questID != null && !questID.equals(""))
        {
            setObjVar(player, questID + ".done", 1);
            messageTo(player, "finishRebelQuest", null, 1, true);
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
        for (obj_id content : contents) {
            String itemInInventory = getTemplateName(content);
            if (itemInInventory.equals(giveMe)) {
                destroyObject(content);
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
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("home"))
        {
            String datatable = getStringObjVar(self, "quest_table");
            datatable = "datatables/theme_park/" + datatable + ".iff";
            int questNum = getIntObjVar(self, "questNum");
            String gatingString = "theme_park_rebel";
            String questID = "rebel_hideout";
            String playerScript = dataTableGetString(datatable, questNum, "player_script");
            debugSpeakMsg(self, "Thanks for bringing me back.  This is where I belong.");
            obj_id player = getObjIdObjVar(self, "player");
            ai_lib.aiStopFollowing(self);
            detachScript(player, playerScript);
            if (questID != null && !questID.equals(""))
            {
                setObjVar(player, questID + ".done", 1);
            }
            messageTo(self, "cleanUp", null, 60, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        obj_id player = getObjIdObjVar(self, "player");
        int questNum = getIntObjVar(self, "questNum");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("destroy"))
        {
            messageTo(player, "finishRebelQuest", null, 0, true);
            return SCRIPT_CONTINUE;
        }
        else if (type.equals("fetch"))
        {
            String reward = dataTableGetString(datatable, questNum, "retrieve_object");
            obj_id playerInv = utils.getInventoryContainer(player);
            createObject(reward, playerInv, "");
            messageTo(player, "finishRebelQuest", null, 1, true);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
