package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;

public class travel_npc extends script.theme_park.newbie_tutorial.tutorial_base
{
    public travel_npc()
    {
    }
    public int handleInitiateDialog(obj_id self, dictionary params) throws InterruptedException
    {
        faceToBehavior(self, getPlayer(self));
        chat.chat(self, new string_id(NEWBIE_CONVO, "quarter_greeting"));
        doAnimationAction(self, "greet");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        obj_id term = getObjIdObjVar(bldg, TRAVEL_TERMINAL);
        makeStaticWaypoint(term);
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String quarter_1_go = "quarter_1_go";
        String limitStartingLocations = getConfigSetting("New_Player", "LimitStartingLocations");
        if (limitStartingLocations != null && limitStartingLocations.equals("true"))
        {
            quarter_1_go = "quarter_1_go_nochoice";
        }
        if (hasObjVar(speaker, "newbie.canUseTravelTerminal"))
        {
            chat.chat(self, new string_id(NEWBIE_CONVO, quarter_1_go));
            doAnimationAction(self, "point_right");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(speaker, "newbie.hasReleaseDocuments"))
        {
            chat.chat(self, new string_id(NEWBIE_CONVO, "quarter_nodocs"));
            doAnimationAction(self, "smack_self");
            return SCRIPT_CONTINUE;
        }
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(NEWBIE_CONVO, "quarter_1_start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(NEWBIE_CONVO, "quarter_1_reply_1");
        response[1] = new string_id(NEWBIE_CONVO, "quarter_1_reply_2");
        obj_id wayp = getObjIdObjVar(speaker, "newbie.hasReleaseDocuments");
        setObjVar(speaker, "newbie.canUseTravelTerminal", true);
        destroyWaypointInDatapad(wayp, speaker);
        removeObjVar(speaker, "newbie.hasReleaseDocuments");
        npcStartConversation(speaker, self, CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        String quarter_1_go = "quarter_1_go";
        String limitStartingLocations = getConfigSetting("New_Player", "LimitStartingLocations");
        if (limitStartingLocations != null && limitStartingLocations.equals("true"))
        {
            quarter_1_go = "quarter_1_go_nochoice";
        }
        if ((response.getAsciiId()).equals("quarter_1_reply_1") || (response.getAsciiId()).equals("quarter_1_reply_2"))
        {
            string_id message = new string_id(NEWBIE_CONVO, quarter_1_go);
            npcSpeak(player, message);
            doAnimationAction(self, "point_right");
            npcEndConversation(player);
        }
        return SCRIPT_CONTINUE;
    }
}
