package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.factions;
import script.library.gcw;
import script.library.locations;
import script.library.utils;

public class flip_terminal_spawner extends script.base_script
{
    public flip_terminal_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkTerminal", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void checkDestroy(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "terminal"))
        {
            return;
        }
        obj_id terminal = utils.getObjIdScriptVar(self, "terminal");
        if (isIdValid(terminal))
        {
            destroyObject(terminal);
        }
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        checkDestroy(self);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        checkDestroy(self);
        return SCRIPT_CONTINUE;
    }
    public int checkTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        float imp_r = gcw.getImperialPercentileByRegion(self);
        float reb_r = gcw.getRebelPercentileByRegion(self);
        int lastCheckTime = utils.getIntScriptVar(self, "lastCheckTime");
        if (getGameTime() - lastCheckTime < 5)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "lastCheckTime", getGameTime());
        obj_id terminal = getObjIdObjVar(self, "terminal");
        if (imp_r > reb_r)
        {
            if (isIdValid(terminal))
            {
                String faction = getStringObjVar(terminal, factions.FACTION);
                if (faction.equalsIgnoreCase("rebel"))
                {
                    destroyObject(terminal);
                    spawnTerminal(self, "imperial");
                }
            }
            else 
            {
                spawnTerminal(self, "imperial");
            }
        }
        else if (reb_r > imp_r)
        {
            if (isIdValid(terminal))
            {
                String faction = getStringObjVar(terminal, factions.FACTION);
                if (faction.equalsIgnoreCase("imperial"))
                {
                    destroyObject(terminal);
                    spawnTerminal(self, "rebel");
                }
            }
            else 
            {
                spawnTerminal(self, "rebel");
            }
        }
        messageTo(self, "checkTerminal", null, 3600.f, false);
        return SCRIPT_CONTINUE;
    }
    public void spawnTerminal(obj_id self, String faction) throws InterruptedException
    {
        obj_id terminal = createObject("object/tangible/terminal/terminal_mission_" + faction + ".iff", getLocation(self));
        if (!isIdValid(terminal))
        {
            return;
        }
        setYaw(terminal, getYaw(self));
        setObjVar(terminal, "spawner", self);
        setObjVar(self, "terminal", terminal);
        return;
    }
}
