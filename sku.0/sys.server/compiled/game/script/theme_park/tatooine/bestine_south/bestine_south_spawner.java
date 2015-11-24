package script.theme_park.tatooine.bestine_south;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class bestine_south_spawner extends script.base_script
{
    public bestine_south_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Bestine South Slums Spawner Script Attached");
        if (!hasObjVar(self, "trainer"))
        {
            spawnEveryone(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "Bestine South Slums Spawner Script Attached");
        if (!hasObjVar(self, "trainer"))
        {
            spawnEveryone(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        spawnEntertainer(self);
        spawnDancer(self);
        spawnMusician(self);
        setObjVar(self, "trainer", 1);
    }
    public void spawnEntertainer(obj_id self) throws InterruptedException
    {
        location entertainerloc = new location(-1041, 10, -3536, "tatooine");
        obj_id entertainer = create.object("trainer_dancer", entertainerloc);
        setObjVar(entertainer, "trainer", "entertainer");
        String name = getName(entertainer);
        setName(entertainer, "Entertainer " + name);
        setInvulnerable(entertainer, true);
        return;
    }
    public void spawnDancer(obj_id self) throws InterruptedException
    {
        location dancerloc = new location(-1031, 10, -3558, "tatooine");
        obj_id dancer = create.object("trainer_dancer", dancerloc);
        setObjVar(dancer, "trainer", "dancer");
        String name = getName(dancer);
        setName(dancer, "Dance Teacher " + name);
        setInvulnerable(dancer, true);
        return;
    }
    public void spawnMusician(obj_id self) throws InterruptedException
    {
        location musicianloc = new location(-1020, 10, -3535, "tatooine");
        obj_id musician = create.object("trainer_doctor", musicianloc);
        setObjVar(musician, "trainer", "musician");
        String name = getName(musician);
        setName(musician, "Music Teacher " + name);
        setInvulnerable(musician, true);
        return;
    }
}
