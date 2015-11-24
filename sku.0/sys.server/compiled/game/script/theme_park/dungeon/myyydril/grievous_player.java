package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.groundquests;

public class grievous_player extends script.base_script
{
    public grievous_player()
    {
    }
    public static final boolean isLogging = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        groundquests.sendSignal(self, "signalLornRetrieveCompleted");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        doLogging("OnLogin", "On Login fired. Top most container is (" + getTemplateName(top) + "/" + top + ")");
        if (top == null || top == self)
        {
            detachScript(self, "theme_park.dungeon.myyydril.grievous_player");
            return SCRIPT_CONTINUE;
        }
        messageTo(top, "validatePlayersInEvent", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleGrievousTimerUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        String message = params.getString("message");
        sendSystemMessageTestingOnly(self, message);
        return SCRIPT_CONTINUE;
    }
    public int cleanupEjectedPlayers(obj_id self, dictionary params) throws InterruptedException
    {
        doLogging("cleanupEjectedPlayers", "Recieved message to clean up players");
        string_id session_end = new string_id("dungeon/myyydril", "encounter_ended");
        sendSystemMessage(self, session_end);
        detachScript(self, "theme_park.dungeon.myyydril.grievous_player");
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (isLogging)
        {
            LOG("grievous_encounter_lock/grievous_player/" + section, message);
        }
    }
}
