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

public class fs_outpost_master extends script.base_script
{
    public fs_outpost_master()
    {
    }
    public void requestRegistration(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, fs_dyn_village.OBJVAR_MY_MASTER_OBJECT) || !hasObjVar(self, fs_counterstrike.OBJVAR_CAMP_NAME))
        {
            trace.log("fs_quest", "fs_outpost_master::OnInit:- > Missing Objvars.  ***DESTROYING*** camp.");
            destroyObject(self);
            return;
        }
        obj_id campMaster = getObjIdObjVar(self, fs_dyn_village.OBJVAR_MY_MASTER_OBJECT);
        dictionary d = new dictionary();
        d.put("theatreid", self);
        d.put("theatreloc", getLocation(self));
        String name = getStringObjVar(self, fs_counterstrike.OBJVAR_CAMP_NAME);
        removeObjVar(self, fs_counterstrike.OBJVAR_CAMP_NAME);
        if (name == null)
        {
            name = "!ERROR!";
        }
        d.put("theatreName", name);
        messageTo(campMaster, "msgRequestTheatreRegistration", d, 0.0f, false);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "msgSelfDestruct", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int msgCheckForMasterAgain(obj_id self, dictionary params) throws InterruptedException
    {
        fs_dyn_village.getRegisteredObjIdFromClusterWideData(fs_dyn_village.CLUSTER_OBJID_KEY_MASTER, "msgGotMasterIdResponse", self);
        return SCRIPT_CONTINUE;
    }
    public int msgGotMasterIdResponse(obj_id self, dictionary params) throws InterruptedException
    {
        boolean rslt = false;
        String key = "";
        if (params.containsKey("key"))
        {
            key = params.getString("key");
        }
        if (params.containsKey("success"))
        {
            rslt = params.getBoolean("success");
        }
        if (rslt)
        {
            if (params.containsKey(key))
            {
                requestRegistration(self);
                return SCRIPT_CONTINUE;
            }
        }
        messageTo(self, "msgCheckForMasterAgain", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        trace.log("fs_quest", "=*=*=*=*=*=*= PHASE 3 THEATRE (" + self + ") BEING DESTROYED =*=*=*=*=*=*=");
        if (hasObjVar(self, fs_dyn_village.OBJVAR_MY_MASTER_OBJECT))
        {
            obj_id mymaster = getObjIdObjVar(self, fs_dyn_village.OBJVAR_MY_MASTER_OBJECT);
            dictionary d = new dictionary();
            d.put("sender", self);
            messageTo(mymaster, "msgNotifyTheatreDestroyed", d, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgSelfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        trace.log("fs_quest", "fs_outpost_master::OnInit:- > Got self destruct message. *** DESTROYING *** camp.");
        Vector existingDroids = new Vector();
        if (hasObjVar(self, fs_counterstrike.OBJVAR_CAMP_DROIDS))
        {
            existingDroids = utils.getResizeableObjIdBatchObjVar(self, fs_counterstrike.OBJVAR_CAMP_DROIDS);
        }
        for (int x = 0; x < existingDroids.size(); x++)
        {
            messageTo((obj_id)existingDroids.get(x), "msgSilentSelfDestruct", null, 0.0f, false);
        }
        fs_counterstrike.resetCamp(self, false);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (isGod(whoTriggeredMe))
        {
            sendSystemMessageTestingOnly(whoTriggeredMe, "god breach, granted.");
            return SCRIPT_CONTINUE;
        }
        if (hasScript(whoTriggeredMe, "systems.fs_quest.fs_outpost_master"))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(whoTriggeredMe, new string_id("fs_quest_village", "expel_shield"));
        expelFromTriggerVolume(self, fs_counterstrike.SHIELD_TR_VOLUME, whoTriggeredMe);
        return SCRIPT_CONTINUE;
    }
    public int msgDoLootSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        fs_counterstrike.doCampLootSpawn(self);
        return SCRIPT_CONTINUE;
    }
    public int msgReplyTheatreRegistration(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("success"))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_outpost_master::msgRequestTheatreInfo: -> Params missing vital param 'success'. Not registered with master. Self destructing.", self, trace.TL_ERROR_LOG);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        boolean success = params.getBoolean("success");
        if (!success)
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_outpost_master::msgRequestTheatreInfo: -> FAILED to register with master. Self destructing.", self, trace.TL_WARNING);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("name"))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_outpost_master::msgRequestTheatreInfo: -> Params missing vital param 'name'. Not registered with master. Self destructing.", self, trace.TL_ERROR_LOG);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        String name = params.getString("name");
        trace.log(fs_dyn_village.LOG_CHAN, "Camp " + self + " got positive registration reply.  Name is ###" + name + "###");
        setObjVar(self, fs_counterstrike.OBJVAR_CAMP_NAME, name);
        if (!isObjectPersisted(self))
        {
            persistObject(self);
        }
        LOG("fs_quest", "Setting up camp " + self);
        fs_counterstrike.setupCamp(self, true);
        fs_counterstrike.doCampLootSpawn(self);
        fs_counterstrike.resetCamp(self, true);
        fs_counterstrike.spawnSurveillanceDroids(self);
        return SCRIPT_CONTINUE;
    }
    public int msgSurveillandeDroidDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("sender"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id sender = params.getObjId("sender");
        fs_counterstrike.droidDied(self, sender);
        return SCRIPT_CONTINUE;
    }
    public int msgPowerDownShield(obj_id self, dictionary params) throws InterruptedException
    {
        fs_counterstrike.powerDownShield();
        fs_counterstrike.doCampDefenseSpawn(self, true);
        trace.log(fs_dyn_village.LOG_CHAN, "Setting up camp for reset in " + fs_counterstrike.getCampResetTime() + " seconds.");
        messageTo(self, "msgResetCamp", null, fs_counterstrike.getCampResetTime(), false);
        return SCRIPT_CONTINUE;
    }
    public int msgDoCampDefenseSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        fs_counterstrike.doCampDefenseSpawn(self, true);
        return SCRIPT_CONTINUE;
    }
    public int msgResetCamp(obj_id self, dictionary params) throws InterruptedException
    {
        fs_counterstrike.resetCamp(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTheaterCreated(obj_id self, obj_id[] objects, obj_id player, obj_id creator) throws InterruptedException
    {
        utils.setBatchObjVar(self, fs_counterstrike.OBJVAR_CAMP_OBJS, objects);
        if (!isIdValid(creator))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_outpost_master::OnTheaterCreated; -> MasterId is invalid. Not registering camp iwth village master.", self, trace.TL_ERROR_LOG);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, fs_dyn_village.OBJVAR_MY_MASTER_OBJECT, creator);
        dictionary d = new dictionary();
        d.put("theatreid", self);
        d.put("theatreloc", getLocation(self));
        String name = getTheaterName(self);
        if (name == null)
        {
            name = "!ERROR!";
        }
        d.put("theatreName", name);
        messageTo(creator, "msgRequestTheatreRegistration", d, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
}
