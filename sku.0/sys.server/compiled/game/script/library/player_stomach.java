package script.library;

import script.obj_id;
import script.string_id;

public class player_stomach extends script.base_script
{
    public player_stomach()
    {
    }
    public static final int STOMACH_FOOD = 0;
    public static final int STOMACH_DRINK = 1;
    public static final int STOMACH_MAX = 2;
    public static final String DATATABLE_STOMACH = "datatables/player/stomach.iff";
    public static final String COL_NAME = "STOMACH_NAME";
    public static final String COL_MAX_VALUE = "MAX_STOMACH_VALUE";
    public static final String COL_DIGEST_RATE = "DIGEST_RATE";
    public static final String COL_DIGEST_VALUE = "DIGEST_VALUE";
    public static final String COL_STOMACH_VAR = "STOMACH_VAR";
    public static final String COL_STAMP_VAR = "STAMP_VAR";
    public static final String COL_MSG_FULL = "MSG_FULL";
    public static final String COL_TARGET_FULL = "MSG_TARGET_FULL";
    public static final String VAR_STOMACH_BASE = "stomach";
    public static final string_id SID_STOMACH_UNKNOWN = new string_id("error_message", "stomach_unknown");
    public static final String SCRIPT_TOXIC_BLOOD = "item.comestible.toxic_blood";
    public static boolean resetStomachs(obj_id player) throws InterruptedException
    {
        boolean isActive = true;
        for (int i = 0; i < STOMACH_MAX; i++)
        {
            if (setStomach(player, i, 0, getGameTime()) < 0)
            {
                isActive &= false;
            }
        }
        return isActive;
    }
    public static boolean isValidStomach(int idx) throws InterruptedException
    {
        return (idx >= STOMACH_FOOD) && (idx < STOMACH_MAX);
    }
    public static String getStomachVar(int stomach) throws InterruptedException
    {
        return dataTableGetString(DATATABLE_STOMACH, stomach, COL_STOMACH_VAR);
    }
    public static String getStampVar(int stomach) throws InterruptedException
    {
        return dataTableGetString(DATATABLE_STOMACH, stomach, COL_STAMP_VAR);
    }
    public static string_id getStomachFullMsg(int stomach) throws InterruptedException
    {
        return new string_id("error_message", dataTableGetString(DATATABLE_STOMACH, stomach, COL_MSG_FULL));
    }
    public static string_id getTargetFullMsg(int stomach) throws InterruptedException
    {
        return new string_id("error_message", dataTableGetString(DATATABLE_STOMACH, stomach, COL_TARGET_FULL));
    }
    public static int setStomach(obj_id player, int stomach, int vol, int stamp) throws InterruptedException
    {
        if (player == null)
        {
            return -1;
        }
        if (vol < 0)
        {
            vol = 0;
        }
        if (!isValidStomach(stomach))
        {
            return -1;
        }
        int maxSize = getStomachMaxSize(stomach);
        if (vol > maxSize)
        {
            vol = maxSize;
        }
        boolean litmus = setObjVar(player, getStomachVar(stomach), vol);
        litmus &= setObjVar(player, getStampVar(stomach), stamp);
        if (litmus)
        {
            return vol;
        }
        else 
        {
            return -1;
        }
    }
    public static boolean canAddToStomach(obj_id player, int stomach, int vol) throws InterruptedException
    {
        return true;
    }
    public static boolean canAddToStomach(obj_id player, int[] vol) throws InterruptedException
    {
        return true;
    }
    public static boolean addToStomach(obj_id player, int stomach, int vol) throws InterruptedException
    {
        return true;
    }
    public static boolean addToStomach(obj_id player, obj_id target, int stomach, int vol) throws InterruptedException
    {
        return true;
    }
    public static boolean addToStomach(obj_id player, obj_id target, int[] vol) throws InterruptedException
    {
        return true;
    }
    public static boolean addToStomach(obj_id player, int[] vol) throws InterruptedException
    {
        return addToStomach(player, player, vol);
    }
    public static int getStomach(obj_id player, int stomach) throws InterruptedException
    {
        if (player == null)
        {
            return -1;
        }
        if (!isValidStomach(stomach))
        {
            return -1;
        }
        int digestRate = getStomachDigestRate(stomach);
        int curVol = getIntObjVar(player, getStomachVar(stomach));
        int stamp = getIntObjVar(player, getStampVar(stomach));
        int digestCycles = (getGameTime() - stamp) / digestRate;

        curVol -= (digestCycles * getStomachDigestValue(stomach));
        if (curVol < 0)
        {
            curVol = 0;
        }
        curVol = setStomach(player, stomach, curVol, stamp + (digestCycles * digestRate));
        if (curVol < 0)
        {
            return -1;
        }
        return curVol;
    }
    public static boolean digestAll(obj_id player) throws InterruptedException
    {
        boolean litmus = true;
        for (int i = 0; i < STOMACH_MAX; i++)
        {
            if (getStomach(player, i) < 0)
            {
                litmus = false;
            }
        }
        return litmus;
    }
    public static boolean isStomachFull(obj_id player, int stomach) throws InterruptedException
    {
        return false;
    }
    public static boolean deactivateStomachs(obj_id player) throws InterruptedException
    {
        if (player == null)
        {
            return false;
        }
        removeObjVar(player, VAR_STOMACH_BASE);
        return true;
    }
    public static String[] getStomachName() throws InterruptedException
    {
        return dataTableGetStringColumn(DATATABLE_STOMACH, COL_NAME);
    }
    public static String getStomachName(int idx) throws InterruptedException
    {
        String[] s = getStomachName();
        if (s != null)
        {
            return s[idx];
        }
        return "";
    }
    public static int[] getStomachMaxSize() throws InterruptedException
    {
        return dataTableGetIntColumn(DATATABLE_STOMACH, COL_MAX_VALUE);
    }
    public static int getStomachMaxSize(int idx) throws InterruptedException
    {
        int[] i = getStomachMaxSize();
        if (i != null)
        {
            return i[idx];
        }
        return -1;
    }
    public static int[] getStomachDigestRate() throws InterruptedException
    {
        return dataTableGetIntColumn(DATATABLE_STOMACH, COL_DIGEST_RATE);
    }
    public static int getStomachDigestRate(int idx) throws InterruptedException
    {
        int[] i = getStomachDigestRate();
        if (i != null)
        {
            return i[idx];
        }
        return -1;
    }
    public static int[] getStomachDigestValue() throws InterruptedException
    {
        return dataTableGetIntColumn(DATATABLE_STOMACH, COL_DIGEST_VALUE);
    }
    public static int getStomachDigestValue(int idx) throws InterruptedException
    {
        int[] i = getStomachDigestValue();
        if (i != null)
        {
            return i[idx];
        }
        return -1;
    }
}
