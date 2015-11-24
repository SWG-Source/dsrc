package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;

public class officer extends script.theme_park.newbie_tutorial.tutorial_base
{
    public officer()
    {
    }
    public int initiateConvo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getPlayer(self);
        if (player != null)
        {
            faceToBehavior(self, player);
        }
        doAnimationAction(self, "wave_hail");
        chat.chat(self, new string_id(NEWBIE_CONVO, "off_1_greeting"));
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
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(NEWBIE_CONVO, "off_1_start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(NEWBIE_CONVO, "off_1_reply_1");
        response[1] = new string_id(NEWBIE_CONVO, "off_1_reply_2");
        npcStartConversation(speaker, self, CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("off_1_reply_1"))
        {
            string_id message = new string_id(NEWBIE_CONVO, "off_1_gotrain");
            npcSpeak(player, message);
            doAnimationAction(self, "point_right");
            npcEndConversation(player);
        }
        else if ((response.getAsciiId()).equals("off_1_reply_2"))
        {
            doAnimationAction(self, "laugh_pointing");
            string_id message = new string_id(NEWBIE_CONVO, "off_1_gotrain2");
            npcSpeak(player, message);
            npcEndConversation(player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEndNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id trainer = getObjIdObjVar(getTopMostContainer(self), TRAINER);
        faceToBehavior(trainer, player);
        makeStaticWaypoint(trainer);
        chat.chat(trainer, new string_id(NEWBIE_CONVO, "trainer_grunt"));
        return SCRIPT_CONTINUE;
    }
}
