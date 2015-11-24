package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.space_combat;
import script.library.space_content;
import script.library.space_crafting;
import script.library.space_transition;
import script.library.space_quest;
import script.library.space_utils;
import script.library.prose;
import script.library.utils;

public class convoy_ship extends script.base_script
{
    public convoy_ship()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
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
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        obj_id quest = getObjIdObjVar(self, "quest");
        if (!exists(quest))
        {
            return SCRIPT_CONTINUE;
        }
        if (!space_quest.isOnThisGroupQuest(quest, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(quest, "underway"))
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(quest, space_quest.QUEST_NAME);
        String questType = getStringObjVar(quest, space_quest.QUEST_TYPE);
        string_id greeting = new string_id("spacequest/" + questType + "/" + questName, "ship_greeting");
        string_id[] responses = new string_id[1];
        responses[0] = new string_id("spacequest/" + questType + "/" + questName, "get_moving");
        npcStartConversation(player, npc, "convoy_ship", greeting, responses);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (!exists(quest))
        {
            return SCRIPT_CONTINUE;
        }
        String questName = getStringObjVar(quest, space_quest.QUEST_NAME);
        String questType = getStringObjVar(quest, space_quest.QUEST_TYPE);
        if (response.equals("get_moving"))
        {
            if (!hasObjVar(quest, "convoy_arrived"))
            {
                prose_package pp = prose.getPackage(new string_id("spacequest/" + questType + "/" + questName, "no_convoy"), 0);
                dogfightTauntPlayer(self, player, pp);
                return SCRIPT_CONTINUE;
            }
            else if (!hasObjVar(quest, "underway"))
            {
                prose_package pp = prose.getPackage(new string_id("spacequest/" + questType + "/" + questName, "lets_move"), 0);
                dogfightTauntPlayer(self, player, pp);
                space_utils.notifyObject(quest, "startConvoyPathing", null);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                prose_package pp = prose.getPackage(new string_id("spacequest/" + questType + "/" + questName, "were_moving"), 0);
                dogfightTauntPlayer(self, player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int registerDestination(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id quest = params.getObjId("quest");
        location loc = params.getLocation("loc");
        addLocationTarget3d("dest", loc, 1000);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (name.equals("dest"))
        {
            dictionary params = new dictionary();
            params.put("ship", self);
            space_utils.notifyObject(quest, "shipArrived", params);
        }
        return SCRIPT_CONTINUE;
    }
    public int objectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        dictionary outparams = new dictionary();
        outparams.put("ship", self);
        space_utils.notifyObject(quest, "convoyShipDestroyed", outparams);
        return SCRIPT_CONTINUE;
    }
    public int OnShieldsDepleted(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "shieldsComplain"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "shieldsComplain", 1);
        obj_id quest = getObjIdObjVar(self, "quest");
        String questName = getStringObjVar(quest, space_quest.QUEST_NAME);
        String questType = getStringObjVar(quest, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(quest, space_quest.QUEST_OWNER);
        string_id warning = new string_id("spacequest/" + questType + "/" + questName, "shields_depleted");
        prose_package pp = prose.getPackage(warning, 0);
        space_quest.groupTaunt(self, player, pp);
        return SCRIPT_CONTINUE;
    }
    public int OnHullNearlyDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "hullComplain"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "hullComplain", 1);
        obj_id quest = getObjIdObjVar(self, "quest");
        String questName = getStringObjVar(quest, space_quest.QUEST_NAME);
        String questType = getStringObjVar(quest, space_quest.QUEST_TYPE);
        obj_id player = getObjIdObjVar(quest, space_quest.QUEST_OWNER);
        string_id warning = new string_id("spacequest/" + questType + "/" + questName, "hull_half");
        prose_package pp = prose.getPackage(warning, 0);
        space_quest.groupTaunt(self, player, pp);
        return SCRIPT_CONTINUE;
    }
}
