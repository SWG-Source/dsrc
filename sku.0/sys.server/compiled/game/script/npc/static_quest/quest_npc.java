package script.npc.static_quest;

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
        createTriggerVolume(FACETO_VOLUME_NAME, 15.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "questNum");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String convoType = dataTableGetString(datatable, questNum, "quest_npc_convo");
        faceTo(self, speaker);
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(speaker, questID + ".vip"))
        {
            obj_id courier = getObjIdObjVar(speaker, questID + ".vip");
            if (courier == self)
            {
                if (type.equals("escort") || type.equals("arrest") || type.equals("rescue"))
                {
                    String reward = "npc_takeme_" + questNum;
                    string_id message = new string_id(CONVO, reward);
                    chat.chat(self, speaker, message);
                    dictionary parms = new dictionary();
                    parms.put("player", speaker);
                    messageTo(self, "followPlayer", parms, 0, true);
                    if (convoType.equals("terse"))
                    {
                        if (!hasObjVar(self, "SentFinishMsg"))
                        {
                            messageTo(speaker, "finishStaticQuest", null, 0, true);
                            setObjVar(self, "SentFinishMsg", 1);
                        }
                    }
                    if (convoType.equals("normal"))
                    {
                        String npcGreet = "npc_takeme_" + questNum;
                        String response1 = "player_more_1_" + questNum;
                        string_id greeting = new string_id(CONVO, npcGreet);
                        string_id response[] = new string_id[1];
                        response[0] = new string_id(CONVO, response1);
                        if (!hasObjVar(self, "SentFinishMsg"))
                        {
                            messageTo(speaker, "finishStaticQuest", null, 0, true);
                            setObjVar(self, "SentFinishMsg", 1);
                        }
                        npcStartConversation(speaker, self, "questConvo", greeting, response);
                    }
                    else if (convoType.equals("extended"))
                    {
                        String npcGreet = "npc_takeme_" + questNum;
                        String response1 = "player_more_1_" + questNum;
                        String response2 = "player_more_2_" + questNum;
                        string_id greeting = new string_id(CONVO, npcGreet);
                        string_id response[] = new string_id[2];
                        response[0] = new string_id(CONVO, response1);
                        response[1] = new string_id(CONVO, response2);
                        if (!hasObjVar(self, "SentFinishMsg"))
                        {
                            messageTo(speaker, "finishStaticQuest", null, 0, true);
                            setObjVar(self, "SentFinishMsg", 1);
                        }
                        npcStartConversation(speaker, self, "questConvo", greeting, response);
                    }
                    else if (convoType.equals("verbose"))
                    {
                        String npcGreet = "npc_1_" + questNum;
                        String response1 = "player_more_1_" + questNum;
                        String response2 = "player_more_2_" + questNum;
                        String response3 = "player_more_3_" + questNum;
                        string_id greeting = new string_id(CONVO, npcGreet);
                        string_id response[] = new string_id[3];
                        response[0] = new string_id(CONVO, response1);
                        response[1] = new string_id(CONVO, response2);
                        response[2] = new string_id(CONVO, response3);
                        if (!hasObjVar(self, "SentFinishMsg"))
                        {
                            messageTo(speaker, "finishStaticQuest", null, 0, true);
                            setObjVar(self, "SentFinishMsg", 1);
                        }
                        npcStartConversation(speaker, self, "questConvo", greeting, response);
                    }
                    else 
                    {
                        return SCRIPT_OVERRIDE;
                    }
                }
                else if (type.equals("smuggle") || type.equals("deliver"))
                {
                    obj_id playerInv = utils.getInventoryContainer(speaker);
                    if (checkForItem(playerInv, speaker, questNum) == true || hasObjVar(speaker, questID + ".done"))
                    {
                        if (!hasObjVar(speaker, questID + ".done"))
                        {
                            messageTo(speaker, "finishStaticQuest", null, 0, true);
                            setObjVar(self, "SentFinishMsg", 1);
                        }
                        if (convoType.equals("terse"))
                        {
                            string_id npcGreet = new string_id(CONVO, "npc_smuggle_" + questNum);
                            chat.chat(self, speaker, npcGreet);
                            return SCRIPT_CONTINUE;
                        }
                        if (convoType.equals("normal"))
                        {
                            String npcGreet = "npc_smuggle_" + questNum;
                            String response1 = "player_more_1_" + questNum;
                            string_id greeting = new string_id(CONVO, npcGreet);
                            string_id response[] = new string_id[1];
                            response[0] = new string_id(CONVO, response1);
                            npcStartConversation(speaker, self, "questConvo", greeting, response);
                        }
                        else if (convoType.equals("extended"))
                        {
                            String npcGreet = "npc_smuggle_" + questNum;
                            String response1 = "player_more_1_" + questNum;
                            String response2 = "player_more_2_" + questNum;
                            string_id greeting = new string_id(CONVO, npcGreet);
                            string_id response[] = new string_id[2];
                            response[0] = new string_id(CONVO, response1);
                            response[1] = new string_id(CONVO, response2);
                            npcStartConversation(speaker, self, "questConvo", greeting, response);
                        }
                        else if (convoType.equals("verbose"))
                        {
                            String npcGreet = "npc_smuggle_" + questNum;
                            String response1 = "player_more_1_" + questNum;
                            String response2 = "player_more_2_" + questNum;
                            String response3 = "player_more_3_" + questNum;
                            string_id greeting = new string_id(CONVO, npcGreet);
                            string_id response[] = new string_id[3];
                            response[0] = new string_id(CONVO, response1);
                            response[1] = new string_id(CONVO, response2);
                            response[2] = new string_id(CONVO, response3);
                            npcStartConversation(speaker, self, "questConvo", greeting, response);
                        }
                    }
                    else 
                    {
                        string_id work = new string_id(CONVO, "gotowork");
                        String working = getString(work);
                        if (working == null || working.equals(""))
                        {
                            work = new string_id(CONVO, "gotowork_" + questNum);
                        }
                        chat.chat(self, speaker, work);
                        return SCRIPT_CONTINUE;
                    }
                }
                else if (type.equals("retrieve"))
                {
                    obj_id playerInv = utils.getInventoryContainer(speaker);
                    if (!hasObjVar(speaker, questID + ".done"))
                    {
                        messageTo(speaker, "finishStaticQuest", null, 0, true);
                        setObjVar(self, "SentFinishMsg", 1);
                        String pickup = dataTableGetString(datatable, questNum, "retrieve_object");
                        if (pickup == null)
                        {
                            pickup = "none";
                        }
                        if (!pickup.equals("none"))
                        {
                            obj_id cargo = createObject(pickup, playerInv, "");
                            setObjVar(speaker, questID + ".deliver", cargo);
                        }
                        if (convoType.equals("terse"))
                        {
                            String npcGreet = "npc_smuggle_" + questNum;
                            string_id greeting = new string_id(CONVO, npcGreet);
                            chat.chat(self, speaker, greeting);
                            npcEndConversation(speaker);
                        }
                        if (convoType.equals("normal"))
                        {
                            String npcGreet = "npc_smuggle_" + questNum;
                            String response1 = "player_more_1_" + questNum;
                            string_id greeting = new string_id(CONVO, npcGreet);
                            string_id response[] = new string_id[1];
                            response[0] = new string_id(CONVO, response1);
                            npcStartConversation(speaker, self, "questConvo", greeting, response);
                        }
                        else if (convoType.equals("extended"))
                        {
                            String npcGreet = "npc_smuggle_" + questNum;
                            String response1 = "player_more_1_" + questNum;
                            String response2 = "player_more_2_" + questNum;
                            string_id greeting = new string_id(CONVO, npcGreet);
                            string_id response[] = new string_id[2];
                            response[0] = new string_id(CONVO, response1);
                            response[1] = new string_id(CONVO, response2);
                            npcStartConversation(speaker, self, "questConvo", greeting, response);
                        }
                        else if (convoType.equals("verbose"))
                        {
                            String npcGreet = "npc_smuggle_" + questNum;
                            String response1 = "player_more_1_" + questNum;
                            String response2 = "player_more_2_" + questNum;
                            String response3 = "player_more_3_" + questNum;
                            string_id greeting = new string_id(CONVO, npcGreet);
                            string_id response[] = new string_id[3];
                            response[0] = new string_id(CONVO, response1);
                            response[1] = new string_id(CONVO, response2);
                            response[2] = new string_id(CONVO, response3);
                            npcStartConversation(speaker, self, "questConvo", greeting, response);
                        }
                    }
                    else 
                    {
                        string_id work = new string_id(CONVO, "gotowork");
                        String working = getString(work);
                        if (working == null || working.equals(""))
                        {
                            work = new string_id(CONVO, "gotowork_" + questNum);
                        }
                        chat.chat(self, speaker, work);
                        return SCRIPT_CONTINUE;
                    }
                }
                else if (type.equals("rescue"))
                {
                    String reward = "npc_takeme_" + questNum;
                    string_id message = new string_id(CONVO, reward);
                    chat.chat(self, speaker, message);
                    if (convoType.equals("terse"))
                    {
                        messageTo(speaker, "finishStaticQuest", null, 0, true);
                        setObjVar(self, "SentFinishMsg", 1);
                    }
                    if (convoType.equals("normal"))
                    {
                        String npcGreet = "npc_takeme_" + questNum;
                        String response1 = "player_more_1_" + questNum;
                        string_id greeting = new string_id(CONVO, npcGreet);
                        string_id response[] = new string_id[1];
                        response[0] = new string_id(CONVO, response1);
                        messageTo(speaker, "finishStaticQuest", null, 0, true);
                        setObjVar(self, "SentFinishMsg", 1);
                        npcStartConversation(speaker, self, "questConvo", greeting, response);
                    }
                    else if (convoType.equals("extended"))
                    {
                        String npcGreet = "npc_takeme_" + questNum;
                        String response1 = "player_more_1_" + questNum;
                        String response2 = "player_more_2_" + questNum;
                        string_id greeting = new string_id(CONVO, npcGreet);
                        string_id response[] = new string_id[2];
                        response[0] = new string_id(CONVO, response1);
                        response[1] = new string_id(CONVO, response2);
                        messageTo(speaker, "finishStaticQuest", null, 0, true);
                        setObjVar(self, "SentFinishMsg", 1);
                        npcStartConversation(speaker, self, "questConvo", greeting, response);
                    }
                    else if (convoType.equals("verbose"))
                    {
                        String npcGreet = "npc_1_" + questNum;
                        String response1 = "player_more_1_" + questNum;
                        String response2 = "player_more_2_" + questNum;
                        String response3 = "player_more_3_" + questNum;
                        string_id greeting = new string_id(CONVO, npcGreet);
                        string_id response[] = new string_id[3];
                        response[0] = new string_id(CONVO, response1);
                        response[1] = new string_id(CONVO, response2);
                        response[2] = new string_id(CONVO, response3);
                        messageTo(speaker, "finishStaticQuest", null, 0, true);
                        setObjVar(self, "SentFinishMsg", 1);
                        npcStartConversation(speaker, self, "questConvo", greeting, response);
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
            string_id blah = new string_id(CONVO, "dontknowyou_" + questNum);
            chat.chat(self, speaker, blah);
            return SCRIPT_CONTINUE;
        }
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String response1 = "player_more_1_" + questNum;
        String response2 = "player_more_2_" + questNum;
        String response3 = "player_more_3_" + questNum;
        if ((response.getAsciiId()).equals(response1))
        {
            String npcAnswer1 = "npc_more_1_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer1);
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, response1));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response2))
        {
            String npcAnswer2 = "npc_more_2_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer2);
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, response2));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response3))
        {
            String npcAnswer3 = "npc_more_3_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer3);
            npcSpeak(player, message);
            if (type.equals("info"))
            {
                messageTo(player, "finishStaticQuest", null, 0, true);
                setObjVar(self, "SentFinishMsg", 1);
            }
            npcRemoveConversationResponse(player, new string_id(CONVO, response3));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        String type = dataTableGetString(getStringObjVar(self, "quest_table"), getIntObjVar(self, "questNum"), "quest_type");
        if (getBooleanObjVar(self, "isStaticObject") && type.equals("destroy"))
        {
            obj_id player = getObjIdObjVar(self, "player");
            messageTo(player, "finishStaticQuest", null, 0, true);
            setObjVar(self, "SentFinishMsg", 1);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        obj_id player = getObjIdObjVar(self, "player");
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        if (type.equals("destroy"))
        {
            messageTo(player, "finishStaticQuest", null, 0, true);
            setObjVar(self, "SentFinishMsg", 1);
            return SCRIPT_CONTINUE;
        }
        else if (type.equals("fetch"))
        {
            String reward = dataTableGetString(datatable, questNum, "retrieve_object");
            obj_id playerInv = utils.getInventoryContainer(player);
            obj_id cargo = createObjectOverloaded(reward, playerInv);
            setObjVar(player, questID + ".deliver", cargo);
            messageTo(player, "finishStaticQuest", null, 0, true);
            setObjVar(self, "SentFinishMsg", 1);
            return SCRIPT_CONTINUE;
        }
        else if (type.equals("rescue") || type.equals("escort") || type.equals("arrest") || type.equals("retrieve"))
        {
            if (questID != null && !questID.equals(""))
            {
                setObjVar(player, questID + ".failed", 1);
                string_id failed = new string_id("theme_park/messages", "generic_fail_message");
                String failure = getString(failed);
                sendSystemMessage(self, failure, null);
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
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";
        int questNum = getIntObjVar(self, "questNum");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        debugSpeakMsg(self, "So, this is it...");
        obj_id player = params.getObjId("player");
        ai_lib.aiStopFollowing(self);
        detachScript(player, playerScript);
        setInvulnerable(self, true);
        messageTo(self, "cleanUp", null, 30, true);
        if (questID != null && !questID.equals(""))
        {
            setObjVar(player, questID + ".done", 1);
        }
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

        boolean isStaticObject = getBooleanObjVar(self, "isStaticObject");
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);

        if(isStaticObject && type.equals("destroy")){
            setObjVar(self, "pvpCanAttack", 1);
            setMaxHitpoints(self, 30000);
            attachScript(self, "systems.npc_lair.lair_destruct");
            detachScript(self, "systems.gcw.gcw_vehicle_patrol");
            setInvulnerable(self, false);
        }
        else if (!isStaticObject && type.equals("info")
                || type.equals("rescue")
                || type.equals("smuggle")
                || type.equals("deliver")
                || type.equals("arrest")
                || type.equals("escort")
                || type.equals("retrieve"))
        {
            setWantSawAttackTriggers(self, false);
            attachScript(self, "npc.converse.npc_converse_menu");
            setCondition(self, CONDITION_INTERESTING);
            setInvulnerable(self, true);
        }
        messageTo(self, "spawnExtras", null, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int saySomething(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if(getBooleanObjVar(self, "isStaticObject")){
            return SCRIPT_CONTINUE;
        }
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String something = "npc_breech_" + questNum;
        string_id message = new string_id(CONVO, something);
        String check = getString(message);
        obj_id player = getObjIdObjVar(self, "player");
        if (check != null)
        {
            if (!check.equals(""))
            {
                if (ai_lib.isNpc(self))
                {
                    chat.chat(self, player, message);
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
            setObjVar(player, questID + ".done", 1);
            messageTo(player, "finishStaticQuest", null, 0, true);
            setObjVar(self, "SentFinishMsg", 1);
            detachScript(player, playerScript);
        }
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
    public int spawnExtras(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "questNum");
        location here = getLocation(self);
        for(int i = 1; i <= 4; i++) {
            String npcNum = "";
            if(i > 1) npcNum = npcNum + i;
            String spawn = dataTableGetString(datatable, questNum, "extra_npc" + npcNum);
            if (!spawn.equals("none")) {
                here.x = here.x + rand(5, 20);
                here.z = here.z + rand(5, 20);
                obj_id extra = create.object(spawn, here);
                if (isValidId(extra)) {
                    String disposition = dataTableGetString(datatable, questNum, "extra_npc" + npcNum + "_disposition");
                    if (disposition.equals("aggro")) {
                        startCombat(extra, player);
                    }
                }
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
            removeTriggerVolume(FACETO_VOLUME_NAME);
            faceTo(self, breacher);
        }
        return SCRIPT_CONTINUE;
    }
}
