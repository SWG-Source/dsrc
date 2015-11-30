package script.theme_park.rebel;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class rtp_luke_force_altar extends script.base_script
{
    public rtp_luke_force_altar()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "forceCrystalSpawned"))
        {
            removeObjVar(self, "forceCrystalSpawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "forceCrystalSpawned"))
        {
            removeObjVar(self, "forceCrystalSpawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestFlavorObject(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "forceCrystalSpawned"))
        {
            String e11Carbine = "object/tangible/quest/rebel/rtp_luke_altar_force_crystal.iff";
            location here = getLocation(self);
            here.y = here.y + 0;
            obj_id objTemplate = createObject(e11Carbine, here);
            setObjVar(self, "forceCrystalSpawned", objTemplate);
            setObjVar(objTemplate, "objParent", self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestFlavorObjectCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "forceCrystalSpawned"))
        {
            obj_id forceCrystal = getObjIdObjVar(self, "forceCrystalSpawned");
            removeObjVar(self, "forceCrystalSpawned");
            if (isIdValid(forceCrystal))
            {
                destroyObject(forceCrystal);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
