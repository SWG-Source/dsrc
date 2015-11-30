package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.restuss_event;

public class stage_one_watcher extends script.base_script
{
    public stage_one_watcher()
    {
    }
    public static final String FACTION_REBEL = "completed_factions.rebel";
    public static final String FACTION_IMPERIAL = "completed_factions.imperial";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, FACTION_REBEL))
        {
            setObjVar(self, FACTION_REBEL, false);
        }
        if (!hasObjVar(self, FACTION_IMPERIAL))
        {
            setObjVar(self, FACTION_IMPERIAL, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int updateStageData(obj_id self, dictionary params) throws InterruptedException
    {
        String finishedFaction = params.getString("faction");
        setWinningFaction(self);
        if (finishedFaction.equals("imperial"))
        {
            setObjVar(self, FACTION_IMPERIAL, true);
        }
        if (finishedFaction.equals("rebel"))
        {
            setObjVar(self, FACTION_REBEL, true);
        }
        if (getBooleanObjVar(self, FACTION_IMPERIAL) && getBooleanObjVar(self, FACTION_REBEL))
        {
            sendStartNotification(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int setWinningFaction(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, restuss_event.WINNING_FACTION))
        {
            return getIntObjVar(self, restuss_event.WINNING_FACTION);
        }
        boolean rebelWin = getBooleanObjVar(self, FACTION_REBEL);
        boolean imperialWin = getBooleanObjVar(self, FACTION_IMPERIAL);
        if (!rebelWin && !imperialWin)
        {
            return -1;
        }
        if (rebelWin && !imperialWin)
        {
            setObjVar(self, restuss_event.WINNING_FACTION, restuss_event.WINNER_REBEL);
        }
        if (!rebelWin && imperialWin)
        {
            setObjVar(self, restuss_event.WINNING_FACTION, restuss_event.WINNER_IMPERIAL);
        }
        return -1;
    }
    public void sendStartNotification(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "sent_signal_one"))
        {
            return;
        }
        CustomerServiceLog("RESTUSS_EVENT", "RESTUSS EVENT - STAGE TWO IS READY TO BEGIN");
        setObjVar(self, "sent_signal_one", true);
        getClusterWideData("event", "stage_two_crier*", true, self);
    }
    public int beginEventNotification(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "sent_signal_two"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "sent_signal_two", true);
        getClusterWideData("event", "stage_two_crier*", true, self);
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] dungeon_data, int lock_key) throws InterruptedException
    {
        if (dungeon_data == null || dungeon_data.length == 0)
        {
            LOG("doLogging", "Dungeon data was null or empty");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "sent_signal_one") && hasObjVar(self, "sent_signal_two"))
        {
            for (int i = 0; i < dungeon_data.length; i++)
            {
                obj_id crier_id = dungeon_data[i].getObjId("dungeon_id");
                messageTo(crier_id, "beginMessage", null, 0, false);
            }
        }
        if (hasObjVar(self, "sent_signal_one") && !hasObjVar(self, "sent_signal_two"))
        {
            for (int i = 0; i < dungeon_data.length; i++)
            {
                obj_id crier_id = dungeon_data[i].getObjId("dungeon_id");
                messageTo(crier_id, "beginSpawn", null, 0, false);
            }
        }
        releaseClusterWideDataLock(manage_name, lock_key);
        return SCRIPT_CONTINUE;
    }
}
