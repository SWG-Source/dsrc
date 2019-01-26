package script.theme_park.tatooine.mos_taike;

import script.*;
import script.library.*;

public class young_guard_fetch extends script.base_script
{
    public young_guard_fetch()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        persistObject(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String datatable = "datatables/theme_park/mos_taike_young_guard.iff";
        String CONVO = "theme_park_mos_taike/young_guard";
        int questNum = getIntObjVar(speaker, "mos_taike.young_guard_quest");
        if (questNum == 0)
        {
            questNum = 1;
            setObjVar(speaker, "mos_taike.young_guard_quest", questNum);
        }
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(speaker, "mos_taike.young_guard.young_guard"))
        {
            obj_id boss = getObjIdObjVar(speaker, "mos_taike.young_guard.young_guard");
            if (boss == self)
            {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (checkForItem(self, playerInv) == true || hasObjVar(speaker, "mos_taike.young_guard.questDone"))
                {
                    String reward = "npc_reward_" + questNum;
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
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        String datatable = "datatables/theme_park/mos_taike.iff";
        String CONVO = "theme_park_mos_taike/young_guard";
        int questNum = getIntObjVar(player, "mos_taike.young_guard_quest");
        String response1 = "player_1_" + questNum;
        String response2 = "player_2_" + questNum;
        String response3 = "player_3_" + questNum;
        if ((response.getAsciiId()).equals(response1))
        {
            location target = getTargetLocation(self);
            if (target == null)
            {
                String noLoc = "npc_noloc_" + questNum;
                string_id message = new string_id(CONVO, noLoc);
                chat.chat(self, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String npcAnswer1 = "npc_2_" + questNum;
                string_id message = new string_id(CONVO, npcAnswer1);
                npcSpeak(player, message);
                npcEndConversation(player);
                setObjVar(player, "mos_taike.young_guard.young_guard", self);
                setObjVar(player, "mos_taike.young_guard.fetchLoc", target);
                attachScript(player, "theme_park.tatooine.mos_taike.player_fetch");
                setObjVar(player, "mos_taike.young_guard.guardLoc", getLocation(self));
                return SCRIPT_CONTINUE;
            }
        }
        if ((response.getAsciiId()).equals(response2))
        {
            String npcAnswer2 = "npc_3_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer2);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response3))
        {
            npcAddConversationResponse(player, new string_id(CONVO, response1));
            npcAddConversationResponse(player, new string_id(CONVO, response2));
            npcRemoveConversationResponse(player, new string_id(CONVO, response3));
            String npcAnswer3 = "npc_4_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer3);
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public location getTargetLocation(obj_id self) throws InterruptedException
    {
        location target = new location();
        int x = 0;
        while (x < 10)
        {
            location here = getLocation(self);
            region quest = locations.getCityRegion(here);
            location questLoc = locations.getGoodLocationOutsideOfRegion(quest, 100.0f, 100.0f, 100.0f);
            if (questLoc != null)
            {
                target = questLoc;
            }
            x = x + 1;
        }
        return target;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id giver) throws InterruptedException
    {
        String datatable = "datatables/theme_park/mos_taike_young_guard.iff";
        String CONVO = "theme_park_mos_taike/young_guard";
        if (!hasObjVar(giver, "mos_taike.young_guard.young_guard"))
        {
            debugSpeakMsg(self, "I don't know you.");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id boss = getObjIdObjVar(giver, "mos_taike.young_guard.young_guard");
            if (boss != self)
            {
                debugSpeakMsg(self, "You work for someone else.");
                return SCRIPT_OVERRIDE;
            }
        }
        int questNum = 1;
        questNum = getIntObjVar(self, "quest");
        questNum = questNum - 1;
        String giveMe = dataTableGetString(datatable, 2, questNum - 1);
        String itemName = getTemplateName(item);
        if (item == null)
        {
            return SCRIPT_OVERRIDE;
        }
        else if (itemName.equals(giveMe))
        {
            questNum = questNum + 1;
            String reward = "npc_reward_" + questNum;
            string_id message = new string_id(CONVO, reward);
            chat.chat(self, message);
            dictionary parms = new dictionary();
            parms.put("player", giver);
            messageTo(self, "giveReward", parms, 0, true);
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            string_id notit = new string_id(CONVO, "not_it");
            chat.chat(self, notit);
            return SCRIPT_OVERRIDE;
        }
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        destroyObject(item);
        return SCRIPT_CONTINUE;
    }
    public int giveReward(obj_id self, dictionary params) throws InterruptedException
    {
        String datatable = "datatables/theme_park/mos_taike_young_guard.iff";
        String CONVO = "theme_park_mos_taike/young_guard";
        obj_id player = params.getObjId("player");
        int questNum = getIntObjVar(player, "mos_taike.young_guard_quest");
        String reward = dataTableGetString(datatable, 3, questNum - 1);
        if (reward.equals("credits"))
        {
            money.bankTo(money.ACCT_JABBA, player, 300);
            removeObjVar(player, "mos_taike.young_guard");
            detachScript(player, "theme_park.tatooine.mos_taike.player_fetch");
            setObjVar(player, "mos_taike.young_guard_quest", questNum + 1);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id playerInv = utils.getInventoryContainer(player);
            createObject(reward, playerInv, "");
            removeObjVar(player, "mos_taike.young_guard");
            detachScript(player, "theme_park.tatooine.mos_taike.player_fetch");
            setObjVar(player, "mos_taike.young_guard_quest", questNum + 1);
            return SCRIPT_CONTINUE;
        }
    }
    public boolean checkForItem(obj_id self, obj_id inv) throws InterruptedException
    {
        String datatable = "datatables/theme_park/mos_taike_young_guard.iff";
        int questNum = 1;
        questNum = getIntObjVar(self, "quest");
        questNum = questNum - 1;
        String giveMe = dataTableGetString(datatable, 2, questNum - 1);
        boolean hadIt = false;
        obj_id[] contents = getContents(inv);
        for (obj_id content : contents) {
            String itemInInventory = getTemplateName(content);
            if (itemInInventory.equals(giveMe)) {
                destroyObject(content);
                hadIt = true;
            } else {
                hadIt = false;
            }
        }
        return hadIt;
    }
}
