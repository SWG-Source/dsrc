package script.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;

public class serving_droid_control extends script.base_script
{
    public serving_droid_control()
    {
    }
    public static final String DROID_ID = "serving_droid.droid";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkdroidId", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            messageTo(self, "handleReceived", null, 6, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            messageTo(self, "handleLost", null, 6, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkDroidId(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(getObjIdObjVar(self, DROID_ID)))
        {
            detachScript(self, "structure.serving_droid_control");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleReceived(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) && !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, DROID_ID) && !exists(self))
        {
            detachScript(self, "structure.serving_droid_control");
        }
        obj_id droid = getObjIdObjVar(self, DROID_ID);
        if (!isValidId(droid) && !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(droid, "droid.moving") || utils.hasScriptVar(droid, "droid.settingPatrol"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = player_structure.getPlayersInBuilding(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (players.length > 1)
        {
            return SCRIPT_CONTINUE;
        }
        broadcastMessage("handleActivateDroid", new dictionary());
        return SCRIPT_CONTINUE;
    }
    public int handleLost(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) && !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, DROID_ID) && !exists(self))
        {
            detachScript(self, "structure.serving_droid_control");
        }
        obj_id droid = getObjIdObjVar(self, DROID_ID);
        if (!isValidId(droid) && !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(droid, "droid.settingPatrol"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = player_structure.getPlayersInBuilding(self);
        if (players == null || players.length == 0)
        {
            broadcastMessage("handleDeactivateDroid", new dictionary());
        }
        return SCRIPT_CONTINUE;
    }
}
