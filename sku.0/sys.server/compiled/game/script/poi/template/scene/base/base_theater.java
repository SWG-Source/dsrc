package script.poi.template.scene.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;
import script.library.theater;
import script.library.factions;

public class base_theater extends script.base_script
{
    public base_theater()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, theater.VAR_STAMP, getGameTime());
        if (hasObjVar(self, factions.FACTION))
        {
            factions.setFaction(self, factions.getFaction(self));
        }
        messageTo(self, theater.HANDLE_THEATER_SETUP, null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        theater.cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (self.isBuildoutObject() == false)
        {
            int now = getGameTime();
            int stamp = getIntObjVar(self, theater.VAR_STAMP);
            int expireTime = stamp + theater.PERSIST_TIME;
            if (hasObjVar(self, theater.VAR_PERSIST_TIME))
            {
                int persistTime = getIntObjVar(self, theater.VAR_PERSIST_TIME);
                expireTime = stamp + persistTime;
            }
            if (expireTime < now)
            {
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        theater.cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int startTheaterFromBuildout(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "intPrespawned");
        removeObjVar(self, "theater.children");
        removeObjVar(self, "theater.objMobSpawners");
        removeObjVar(self, "theater.objObjectiveSpawners");
        removeObjVar(self, "theater.persist");
        removeObjVar(self, "theater.stamp");
        theater.spawnDatatableOffsetQueued(self, 0);
        return SCRIPT_CONTINUE;
    }
    public int handleTheaterSetup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, theater.VAR_THEATER_BASE) && !hasObjVar(self, theater.VAR_CHILDREN))
        {
            theater.spawnDatatableOffsetQueued(self, 0);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTheaterComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, poi.POI_BASE))
        {
            obj_id master = poi.getBaseObject(self);
            if ((master == null) || (master == obj_id.NULL_ID))
            {
            }
            else 
            {
                params.put(theater.DICT_MASTER, self);
                messageTo(master, theater.HANDLE_THEATER_COMPLETE, params, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int theaterFinished(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, poi.POI_BASE))
        {
            obj_id master = poi.getBaseObject(self);
            if ((master != null) && (master != obj_id.NULL_ID))
            {
                LOG("theater", master + ": theater finished " + self);
                dictionary dctParams = new dictionary();
                dctParams.put("objTheater", self);
                messageTo(master, "theaterFinished", dctParams, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int continueTheater(obj_id self, dictionary params) throws InterruptedException
    {
        int intIndex = params.getInt("intIndex");
        theater.spawnDatatableOffsetQueued(self, intIndex);
        return SCRIPT_CONTINUE;
    }
}
