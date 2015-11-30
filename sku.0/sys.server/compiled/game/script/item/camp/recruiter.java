package script.item.camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class recruiter extends script.base_script
{
    public recruiter()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleSpawnRecruiter", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id recruiter = getObjIdObjVar(self, "recruiter");
        if (isIdValid(recruiter))
        {
            destroyObject(recruiter);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnRecruiter(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id parent = getObjIdObjVar(self, "theater.parent");
        if (!isIdValid(parent))
        {
            return SCRIPT_CONTINUE;
        }
        float yaw = getYaw(self);
        String faction = getStringObjVar(self, "faction");
        String spawn = "";
        if (faction == null || faction.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (faction.equalsIgnoreCase("rebel"))
        {
            spawn = "rebel_recruiter";
        }
        else if (faction.equalsIgnoreCase("imperial"))
        {
            spawn = "imperial_recruiter";
        }
        if (spawn == null || spawn.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id recruiter = create.object(spawn, getLocation(self));
        if (!isIdValid(recruiter))
        {
            return SCRIPT_CONTINUE;
        }
        setYaw(recruiter, yaw);
        int items = (int)getFloatObjVar(parent, "modules." + faction);
        setObjVar(recruiter, "item_limit", items);
        setObjVar(self, "recruiter", recruiter);
        return SCRIPT_CONTINUE;
    }
}
