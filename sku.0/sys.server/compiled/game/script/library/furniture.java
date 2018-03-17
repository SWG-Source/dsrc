package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class furniture extends script.base_script
{
    public furniture()
    {
    }
    public static final String VAR_CHAIR_OCCUPIED_BASE = "occupied";
    public static final String VAR_CHAIR_OCCUPIED_IDS = "occupied.ids";
    public static final String VAR_CHAIR_OCCUPIED_LIMIT = "occupied.limit";
    public static final String VAR_PLAYER_SEAT_BASE = "seat";
    public static final String VAR_PLAYER_SEAT_ID = "seat.id";
    public static final String SCRIPT_CHAIR = "item.furniture.chair";
    public static final String SCRIPT_PLAYER_SEATED = "player.player_seated";
    public static final string_id SID_SEAT_FULL = new string_id("error_message", "seat_full");
    public static boolean sit(obj_id player, obj_id chair) throws InterruptedException
    {
        if ((player == null) || (chair == null))
        {
            return false;
        }
        if (!hasObjVar(chair, VAR_CHAIR_OCCUPIED_LIMIT))
        {
            return false;
        }
        Vector occupants = getResizeableObjIdArrayObjVar(chair, VAR_CHAIR_OCCUPIED_IDS);
        int pos = 0;
        if ((occupants != null) && (occupants.size() != 0))
        {
            pos = occupants.size();
            if (pos >= getIntObjVar(chair, VAR_CHAIR_OCCUPIED_LIMIT))
            {
                sendSystemMessage(player, SID_SEAT_FULL);
                return false;
            }
        }
        if (sitOnObject(player, chair, pos))
        {
            occupants = utils.addElement(occupants, player);
            setObjVar(chair, VAR_CHAIR_OCCUPIED_IDS, occupants);
            setObjVar(player, VAR_PLAYER_SEAT_ID, chair);
            attachScript(player, SCRIPT_PLAYER_SEATED);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean unseat(obj_id player) throws InterruptedException
    {
        if (player == null)
        {
            return false;
        }
        if (!hasObjVar(player, VAR_PLAYER_SEAT_ID))
        {
            return false;
        }
        obj_id chair = getObjIdObjVar(player, VAR_PLAYER_SEAT_ID);
        Vector occupants = getResizeableObjIdArrayObjVar(chair, VAR_CHAIR_OCCUPIED_IDS);
        occupants = utils.removeElement(occupants, player);
        if (occupants == null || occupants.size() == 0)
        {
            return false;
        }
        boolean litmus = setObjVar(chair, VAR_CHAIR_OCCUPIED_IDS, occupants);
        removeObjVar(player, VAR_PLAYER_SEAT_BASE);
        detachScript(player, SCRIPT_PLAYER_SEATED);
        return litmus;
    }
}
