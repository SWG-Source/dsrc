package script.npc.static_quest;

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
import script.library.factions;

public class quest_finish extends script.base_script
{
    public quest_finish()
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
        String convoType = dataTableGetString(datatable, questNum, "quest_npc_convo");
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(speaker, questID + ".vip"))
        {
            if (type.equals("rescue") || type.equals("escort") || type.equals("arrest"))
            {
                String restart = ("I'm sorry, could you say that one more time?");
                chat.chat(self, restart);
                attachScript(self, "npc.static_npc.quest_npc");
                detachScript(self, "npc.static_npc.quest_finish");
                return SCRIPT_OVERRIDE;
            }
            else if (type.equals("smuggle") || type.equals("deliver"))
            {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (checkForItem(playerInv, speaker, questNum) == true || hasObjVar(speaker, questID + ".done"))
                {
                    if (!hasObjVar(speaker, questID + ".done"))
                    {
                        messageTo(speaker, "finishStaticQuest", null, 0, true);
                        giveReward(self, speaker, questNum);
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
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        int questNum = getIntObjVar(self, "questNum");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String convoStyle = dataTableGetString(datatable, questNum, "quest_giver_convo");
        String response1 = "player_more_1_" + questNum;
        String response2 = "player_more_2_" + questNum;
        String response3 = "player_more_3_" + questNum;
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
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
                giveReward(self, player, questNum);
            }
            npcRemoveConversationResponse(player, new string_id(CONVO, response3));
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
        if (type.equals("destroy"))
        {
            messageTo(player, "finishStaticQuest", null, 0, true);
            giveReward(self, player, questNum);
            return SCRIPT_CONTINUE;
        }
        else if (type.equals("fetch"))
        {
            String reward = dataTableGetString(datatable, questNum, "retrieve_object");
            obj_id playerInv = utils.getInventoryContainer(player);
            createObject(reward, playerInv, "");
            messageTo(player, "finishStaticQuest", null, 0, true);
            giveReward(self, player, questNum);
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
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";
        int questNum = getIntObjVar(self, "questNum");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        debugSpeakMsg(self, "Thanks for bringing me back.");
        obj_id player = params.getObjId("player");
        ai_lib.aiStopFollowing(self);
        detachScript(player, playerScript);
        if (questID != null && !questID.equals(""))
        {
            setObjVar(player, questID + ".done", 1);
        }
        else 
        {
            debugSpeakMsg(player, "This mission didn't get completed...");
            return SCRIPT_OVERRIDE;
        }
        messageTo(self, "cleanUp", null, 30, true);
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
        String type = dataTableGetString(datatable, questNum, "quest_type");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (type.equals("rescue") || type.equals("smuggle") || type.equals("deliver") || type.equals("arrest") || type.equals("escort"))
        {
            attachScript(self, "npc.converse.npc_converse_menu");
        }
        messageTo(self, "spawnExtras", null, 0, true);
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
            setObjVar(player, questID + ".done", 1);
        }
        messageTo(player, "finishStaticQuest", null, 0, true);
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
    public void giveReward(obj_id self, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        int gating = getIntObjVar(player, gatingString);
        gating = gating + 1;
        setObjVar(player, gatingString, gating);
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("rescue") || type.equals("arrest") || type.equals("escort"))
        {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        String reward = dataTableGetString(datatable, questNum, "reward");
        String reward2 = dataTableGetString(datatable, questNum, "reward2");
        String reward3 = dataTableGetString(datatable, questNum, "reward3");
        String reward4 = dataTableGetString(datatable, questNum, "reward4");
        if (reward != null)
        {
            obj_id rewardObject = createObject(reward, playerInv, "");
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
            String objvar = dataTableGetString(datatable, questNum, "reward_objvar");
            int value = dataTableGetInt(datatable, questNum, "reward_objvar_value");
            if (objvar != null)
            {
                setObjVar(rewardObject, objvar, value);
            }
        }
        if (reward2 != null)
        {
            obj_id rewardObject2 = createObject(reward2, playerInv, "");
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
            String objvar2 = dataTableGetString(datatable, questNum, "reward2_objvar");
            int value2 = dataTableGetInt(datatable, questNum, "reward2_objvar_value");
            if (objvar2 != null)
            {
                setObjVar(rewardObject2, objvar2, value2);
            }
        }
        if (reward3 != null)
        {
            obj_id rewardObject3 = createObject(reward3, playerInv, "");
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
            String objvar3 = dataTableGetString(datatable, questNum, "reward3_objvar");
            int value3 = dataTableGetInt(datatable, questNum, "reward3_objvar_value");
            if (objvar3 != null)
            {
                setObjVar(rewardObject3, objvar3, value3);
            }
        }
        if (reward4 != null)
        {
            obj_id rewardObject4 = createObject(reward4, playerInv, "");
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
            String objvar4 = dataTableGetString(datatable, questNum, "reward4_objvar");
            int value4 = dataTableGetInt(datatable, questNum, "reward4_objvar_value");
            if (objvar4 != null)
            {
                setObjVar(rewardObject4, objvar4, value4);
            }
        }
        int credits = dataTableGetInt(datatable, questNum, "credits");
        if (credits != 0)
        {
            money.bankTo(money.ACCT_JABBA, player, credits);
            sendSystemMessage(player, credits + " credits have been deposited in your bank account.", null);
        }
        String factionReward = dataTableGetString(datatable, questNum, "faction reward");
        String factionReward2 = dataTableGetString(datatable, questNum, "faction_reward2");
        String factionReward3 = dataTableGetString(datatable, questNum, "faction_reward3");
        String factionReward4 = dataTableGetString(datatable, questNum, "faction_reward4");
        if (!factionReward.equals("none"))
        {
            int factionAmt = dataTableGetInt(datatable, questNum, "faction_reward_amount");
            if (factionAmt != 0)
            {
                factions.addFactionStanding(player, factionReward, factionAmt);
            }
        }
        if (!factionReward2.equals("none"))
        {
            int factionAmt2 = dataTableGetInt(datatable, questNum, "faction_reward2_amount");
            if (factionAmt2 != 0)
            {
                factions.addFactionStanding(player, factionReward2, factionAmt2);
            }
        }
        if (!factionReward3.equals("none"))
        {
            int factionAmt3 = dataTableGetInt(datatable, questNum, "faction_reward3_amount");
            if (factionAmt3 != 0)
            {
                factions.addFactionStanding(player, factionReward3, factionAmt3);
            }
        }
        if (!factionReward4.equals("none"))
        {
            int factionAmt4 = dataTableGetInt(datatable, questNum, "faction_reward4_amount");
            if (factionAmt4 != 0)
            {
                factions.addFactionStanding(player, factionReward4, factionAmt4);
            }
        }
        removeObjVar(player, questID);
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");
        if (hasScript(player, playerScript))
        {
            detachScript(player, playerScript);
        }
        return;
    }
}
