package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;

public class mission_npc extends script.theme_park.newbie_tutorial.tutorial_base
{
    public mission_npc()
    {
    }
    public int handleYellAtNewbie(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        faceToBehavior(self, player);
        doAnimationAction(self, "point_accusingly");
        if (!hasObjVar(self, "newbie.hasGivenMission"))
        {
            chat.chat(self, new string_id(NEWBIE_CONVO, "mission_hail"));
        }
        else 
        {
            chat.chat(self, new string_id(NEWBIE_CONVO, "mission_repeat"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        removeStaticWaypoint(self);
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        utils.setScriptVar(speaker, "newbie.doneTalking", true);
        chat.setBadMood(self);
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(NEWBIE_CONVO, "mission_1_start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(NEWBIE_CONVO, "mission_1_reply_1");
        response[1] = new string_id(NEWBIE_CONVO, "mission_1_reply_2");
        npcStartConversation(speaker, self, CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("mission_1_reply_1") || (response.getAsciiId()).equals("mission_1_reply_2"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "mission_1_waypoint");
            npcSpeak(player, message);
            doAnimationAction(self, "manipulate_medium");
            messageTo(player, "handleGetTravelWaypoint", null, 0, false);
            npcEndConversation(player);
            setObjVar(self, "newbie.hasGivenMission", true);
        }
        return SCRIPT_CONTINUE;
    }
}
