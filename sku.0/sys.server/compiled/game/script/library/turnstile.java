package script.library;

import script.dictionary;
import script.obj_id;
import script.prose_package;
import script.string_id;

import java.util.Vector;

public class turnstile extends script.base_script
{
    public turnstile()
    {
    }
    public static final int TURNSTILE_DEFAULT_FEE = 5;
    public static final int TURNSTILE_DEFAULT_TIME = 0;
    public static final int TURNSTILE_DEFAULT_GRACE = 30;
    public static final int TURNSTILE_CLEANUP_HEARTBEAT = 21600;
    public static final String TURNSTILE_GREETING_SHORT_DEFAULT = "Pay cover charge for <venue name here>?";
    public static final String TURNSTILE_GREETING_LONG_DEFAULT = "Establishment Description Here.";
    public static final String VAR_TURNSTILE_BASE = "turnstile";
    public static final String VAR_TURNSTILE_FEE = "turnstile.fee";
    public static final String VAR_TURNSTILE_TIME = "turnstile.time";
    public static final String VAR_TURNSTILE_PATRON_BASE = "turnstile.patrons";
    public static final String VAR_TURNSTILE_PATRON_IDS = "turnstile.patrons.ids";
    public static final String VAR_TURNSTILE_PATRON_STAMPS = "turnstile.patrons.entry_times";
    public static final String VAR_TURNSTILE_GREETING_SHORT = "turnstile.greeting.short";
    public static final String VAR_TURNSTILE_GREETING_LONG = "turnstile.greeting.long";
    public static final String SCRIPT_TURNSTILE_CLEANUP = "turnstile.turnstile_cleanup";
    public static final string_id SID_WAIT_TURNSTILE = new string_id("player_structure", "turnstile_wait");
    public static boolean addTurnstile(obj_id building, int fee, int time) throws InterruptedException
    {
        if (building == null)
        {
            return false;
        }
        if (hasTurnstile(building))
        {
            return false;
        }
        setObjVar(building, VAR_TURNSTILE_FEE, fee);
        setObjVar(building, VAR_TURNSTILE_TIME, time);
        setObjVar(building, VAR_TURNSTILE_GREETING_SHORT, TURNSTILE_GREETING_SHORT_DEFAULT);
        setObjVar(building, VAR_TURNSTILE_GREETING_LONG, TURNSTILE_GREETING_LONG_DEFAULT);
        if (!hasScript(building, SCRIPT_TURNSTILE_CLEANUP))
        {
            attachScript(building, SCRIPT_TURNSTILE_CLEANUP);
        }
        obj_id[] contents = player_structure.getObjectsInBuilding(building);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (hasScript(content, "terminal.vendor")) {
                    setEntranceCharge(content, fee);
                }
            }
        }
        return true;
    }
    public static boolean canAddTurnstile(obj_id player, obj_id building) throws InterruptedException
    {
        if (utils.hasScriptVar(building, "turnstile_delay.time"))
        {
            int removeTime = utils.getIntScriptVar(building, "turnstile_delay.time");
            int curTime = getGameTime();
            if (curTime - removeTime > 900)
            {
                utils.removeObjVar(building, "turnstile_delay.time");
            }
            else 
            {
                prose_package pp = prose.getPackage(SID_WAIT_TURNSTILE, ((removeTime + 900 - curTime) / 60) + 1);
                sendSystemMessageProse(player, pp);
                return false;
            }
        }
        return true;
    }
    public static int getFee(obj_id building) throws InterruptedException
    {
        if (building == null)
        {
            return 0;
        }
        if (!hasTurnstile(building))
        {
            return 0;
        }
        return getIntObjVar(building, VAR_TURNSTILE_FEE);
    }
    public static int getTime(obj_id building) throws InterruptedException
    {
        if (building == null)
        {
            return 0;
        }
        if (!hasTurnstile(building))
        {
            return 0;
        }
        return getIntObjVar(building, VAR_TURNSTILE_TIME);
    }
    public static boolean hasTurnstile(obj_id building) throws InterruptedException {
        return building != null && hasObjVar(building, VAR_TURNSTILE_BASE);
    }
    public static boolean removeTurnstile(obj_id building) throws InterruptedException
    {
        if (building == null)
        {
            return false;
        }
        if (!hasTurnstile(building))
        {
            return false;
        }
        removeObjVar(building, VAR_TURNSTILE_BASE);
        utils.setScriptVar(building, "turnstile_delay.time", getGameTime());
        obj_id[] contents = player_structure.getObjectsInBuilding(building);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (hasScript(content, "terminal.vendor")) {
                    setEntranceCharge(content, 0);
                }
            }
        }
        return true;
    }
    public static boolean addPatron(obj_id building, obj_id player) throws InterruptedException
    {
        if ((building == null) || (player == null))
        {
            return false;
        }
        if (!hasTurnstile(building))
        {
            return false;
        }
        debugServerConsoleMsg(building, "turnstile.addPatron: getting objvars");
        Vector patrons = getResizeableObjIdArrayObjVar(building, VAR_TURNSTILE_PATRON_IDS);
        Vector timestamps = getResizeableIntArrayObjVar(building, VAR_TURNSTILE_PATRON_STAMPS);
        if ((patrons != null) && (timestamps != null))
        {
            debugServerConsoleMsg(building, "turnstile.addPatron: checking player position");
            if (utils.getElementPositionInArray(patrons, player) > -1)
            {
                debugServerConsoleMsg(building, "turnstile.addPatron: player is already a patron");
                return false;
            }
        }
        debugServerConsoleMsg(building, "turnstile.addPatron: adding player to new patron list");
        patrons = utils.addElement(patrons, player);
        debugServerConsoleMsg(building, "turnstile.addPatron: adding timestamp for new patron");
        int timedelta = TURNSTILE_DEFAULT_GRACE + getIntObjVar(building, VAR_TURNSTILE_TIME);
        int endTime = getGameTime() + timedelta;
        debugServerConsoleMsg(building, "turnstile.addPatron: end time = " + endTime);
        timestamps = utils.addElement(timestamps, endTime);
        dictionary params = new dictionary();
        params.put("building", building);
        messageTo(player, "turnstileExpire", params, timedelta, false);
        debugServerConsoleMsg(building, "turnstile.addPatron: resetting objvars");
        if (patrons.size() > 0 && timestamps.size() > 0)
        {
            setObjVar(building, VAR_TURNSTILE_PATRON_IDS, patrons);
            setObjVar(building, VAR_TURNSTILE_PATRON_STAMPS, timestamps);
        }
        return true;
    }
    public static boolean removePatron(obj_id building, obj_id player) throws InterruptedException
    {
        if ((building == null) || (player == null))
        {
            return false;
        }
        if (!hasTurnstile(building))
        {
            return false;
        }
        Vector patrons = getResizeableObjIdArrayObjVar(building, VAR_TURNSTILE_PATRON_IDS);
        Vector timestamps = getResizeableIntArrayObjVar(building, VAR_TURNSTILE_PATRON_STAMPS);
        int arrayPosition = utils.getElementPositionInArray(patrons, player);
        if (arrayPosition == -1)
        {
            return false;
        }
        patrons = utils.removeElementAt(patrons, arrayPosition);
        timestamps = utils.removeElementAt(timestamps, arrayPosition);
        if (patrons == null || patrons.size() == 0)
        {
            removeObjVar(building, VAR_TURNSTILE_PATRON_IDS);
        }
        else 
        {
            setObjVar(building, VAR_TURNSTILE_PATRON_IDS, patrons);
        }
        if (timestamps == null || timestamps.size() == 0)
        {
            removeObjVar(building, VAR_TURNSTILE_PATRON_STAMPS);
        }
        else 
        {
            setObjVar(building, VAR_TURNSTILE_PATRON_STAMPS, timestamps);
        }
        return true;
    }
    public static boolean isPatron(obj_id building, obj_id player) throws InterruptedException
    {
        if ((building == null) || (player == null))
        {
            return false;
        }
        if (!hasTurnstile(building))
        {
            return false;
        }
        obj_id[] patrons = getObjIdArrayObjVar(building, VAR_TURNSTILE_PATRON_IDS);
        int arrayPosition = utils.getElementPositionInArray(patrons, player);
        return arrayPosition != -1;
    }
    public static boolean hasExpired(obj_id building, obj_id player) throws InterruptedException
    {
        if ((building == null) || (player == null))
        {
            return false;
        }
        if (!hasTurnstile(building))
        {
            return false;
        }
        obj_id[] patrons = getObjIdArrayObjVar(building, VAR_TURNSTILE_PATRON_IDS);
        int[] timestamps = getIntArrayObjVar(building, VAR_TURNSTILE_PATRON_STAMPS);
        if(timestamps == null){
            return false;
        }
        int arrayPosition = utils.getElementPositionInArray(patrons, player);
        if (arrayPosition == -1 || arrayPosition >= timestamps.length)
        {
            return false;
        }
        return timestamps[arrayPosition] < getGameTime();
    }
    public static boolean cleanupExpiredPatrons(obj_id building) throws InterruptedException
    {
        if (building == null)
        {
            return false;
        }
        if (!hasTurnstile(building))
        {
            return false;
        }
        obj_id[] patrons = getObjIdArrayObjVar(building, VAR_TURNSTILE_PATRON_IDS);
        int[] timestamps = getIntArrayObjVar(building, VAR_TURNSTILE_PATRON_STAMPS);
        Vector expired = new Vector();
        expired.setSize(0);
        if (timestamps == null || patrons == null)
        {
            return false;
        }
        int arrayLength = timestamps.length;
        if (arrayLength > patrons.length)
        {
            arrayLength = patrons.length;
        }
        for (int i = 0; i < arrayLength; i++)
        {
            if (timestamps[i] < getGameTime())
            {
                expired = utils.addElement(expired, patrons[i]);
            }
        }
        for (Object anExpired : expired) {
            removePatron(building, ((obj_id) anExpired));
        }
        return true;
    }
    public static boolean expirePatron(obj_id building, obj_id player) throws InterruptedException
    {
        if ((building == null) || (player == null))
        {
            return false;
        }
        if (!hasTurnstile(building))
        {
            return false;
        }
        if (isPatron(building, player))
        {
            removePatron(building, player);
        }
        else 
        {
            return false;
        }
        Vector patrons = getResizeableObjIdArrayObjVar(building, VAR_TURNSTILE_PATRON_IDS);
        Vector timestamps = getResizeableIntArrayObjVar(building, VAR_TURNSTILE_PATRON_STAMPS);
        patrons = utils.addElement(patrons, player);
        timestamps = utils.addElement(timestamps, getGameTime() - 60);
        if (patrons.size() > 0 && timestamps.size() > 0)
        {
            setObjVar(building, VAR_TURNSTILE_PATRON_IDS, patrons);
            setObjVar(building, VAR_TURNSTILE_PATRON_STAMPS, timestamps);
        }
        return true;
    }
}
