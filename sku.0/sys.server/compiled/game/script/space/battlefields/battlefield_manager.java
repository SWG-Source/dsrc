package script.space.battlefields;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.group;
import script.library.space_utils;
import script.library.space_create;
import script.library.load_test;
import script.library.prose;
import script.library.ship_ai;
import script.library.space_combat;
import script.library.space_transition;
import script.library.utils;
import script.library.space_battlefield;
import java.lang.Math;

public class battlefield_manager extends script.base_script
{
    public battlefield_manager()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("space", "manager preloading");
        messageTo(self, "nextPhase", null, 2, false);
        setObjVar(self, "intSpawnersDeactivated", 1);
        setObjVar(self, "intPhase", 2);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int bootStrapZone(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space", "preload compltede");
        return SCRIPT_CONTINUE;
    }
    public int shipHyperspacing(obj_id self, dictionary params) throws InterruptedException
    {
        int intPhase = getIntObjVar(self, "intPhase");
        if (intPhase == space_battlefield.PHASE_CONTROL)
        {
            obj_id objShip = params.getObjId("objShip");
            int intFaction = shipGetSpaceFaction(objShip);
            if (intFaction == (-615855020))
            {
                int intCount = getIntObjVar(self, "intImperialShipCount");
                intCount = intCount + 1;
                setObjVar(self, "intImperialShipCount", intCount);
            }
        }
        if (intPhase == space_battlefield.PHASE_ESCORT)
        {
            obj_id objShip = params.getObjId("objShip");
            int intFaction = shipGetSpaceFaction(objShip);
            if (intFaction == (-615855020))
            {
                int intCount = getIntObjVar(self, "intImperialShipCount");
                intCount = intCount + 10;
                setObjVar(self, "intImperialShipCount", intCount);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int nextPhase(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "intPhase", 2);
        space_battlefield.doDestroyerPhase(self);
        return SCRIPT_CONTINUE;
    }
    public int resetBattlefield(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space", "RESETTING");
        space_battlefield.resetSpaceBattlefield(self);
        return SCRIPT_CONTINUE;
    }
}
