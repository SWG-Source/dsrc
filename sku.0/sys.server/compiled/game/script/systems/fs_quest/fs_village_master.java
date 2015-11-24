package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trace;
import script.library.fs_dyn_village;
import script.library.fs_counterstrike;
import script.library.utils;

public class fs_village_master extends script.base_script
{
    public fs_village_master()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int msgEnemySpawnPulse(obj_id self, dictionary params) throws InterruptedException
    {
        fs_dyn_village.doEnemySpawnPulse(self, "msgEnemySpawnPulse");
        return SCRIPT_CONTINUE;
    }
    public int msgIdRegistered(obj_id self, dictionary params) throws InterruptedException
    {
        boolean rslt = false;
        if (params.containsKey("success"))
        {
            rslt = params.getBoolean("success");
        }
        trace.log(fs_dyn_village.LOG_CHAN, "fs_village_master::msgIdRegistered: -> Registered Master ObjId with Cluster Wide Data: " + rslt);
        return SCRIPT_CONTINUE;
    }
    public int msgRequestTheatreRegistration(obj_id self, dictionary params) throws InterruptedException
    {
        trace.log(fs_dyn_village.LOG_CHAN, "fs_village_master::msgRequestTheatreRegistration: -> Got registration request");
        if (!params.containsKey("theatreid") || !params.containsKey("theatreloc") || !params.containsKey("theatreName"))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_village_master::msgRequestTheatreRegistration: -> missing one or more crucial params to register theatre.  Registration not done.  Theatre will self destruct on next server restart.", self, trace.TL_ERROR_LOG);
            return SCRIPT_CONTINUE;
        }
        obj_id theatre = params.getObjId("theatreid");
        location loc = params.getLocation("theatreloc");
        String name = params.getString("theatreName");
        trace.log(fs_dyn_village.LOG_CHAN, "Theatre " + theatre + " requests registration at loc " + loc.toString());
        String theatreName = fs_counterstrike.setLocForCamp(self, theatre, loc, name);
        dictionary d = new dictionary();
        if (theatreName.length() > 0)
        {
            d.put("success", true);
            d.put("name", theatreName);
        }
        else 
        {
            d.put("success", false);
        }
        messageTo(theatre, "msgReplyTheatreRegistration", d, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int msgNotifyTheatreDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("sender"))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_village_master::msgNotifyTheatreDestroyed: ->Village notified us of death, but return address not included in note.  Can't respond. ", self, trace.TL_ERROR_LOG);
            return SCRIPT_CONTINUE;
        }
        fs_counterstrike.theatreDestroyed(self, params.getObjId("sender"));
        return SCRIPT_CONTINUE;
    }
    public int msgIntRegisteredUID(obj_id self, dictionary params) throws InterruptedException
    {
        boolean rslt = false;
        if (params.containsKey("success"))
        {
            rslt = params.getBoolean("success");
        }
        trace.log(fs_dyn_village.LOG_CHAN, "fs_village_master::msgIntRegistered: -> Registered Integer with Cluster Wide Data: " + rslt);
        return SCRIPT_CONTINUE;
    }
    public int msgIntRegisteredPhase(obj_id self, dictionary params) throws InterruptedException
    {
        boolean rslt = false;
        if (params.containsKey("success"))
        {
            rslt = params.getBoolean("success");
        }
        trace.log(fs_dyn_village.LOG_CHAN, "fs_village_master::msgIntRegistered: -> Registered Integer with Cluster Wide Data: " + rslt);
        return SCRIPT_CONTINUE;
    }
}
