package script.systems.battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;

public class battlefield_spawner extends script.base_script
{
    public battlefield_spawner()
    {
    }
    public static final String VAR_NUMBER_SPAWNS = "battlefield.number_spawns";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, battlefield.VAR_SPAWNER_CURRENT_POPULATION, 0);
        setObjVar(self, battlefield.VAR_SPAWNER_CURRENT, 0);
        int pulse = getIntObjVar(self, battlefield.VAR_SPAWNER_PULSE);
        messageTo(self, "msgBattlefieldSpawn", null, pulse, false);
        return SCRIPT_CONTINUE;
    }
    public int msgBattlefieldSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield_spawner::msgBattlefieldSpawn -- " + self);
        int num_spawns = 0;
        if (hasObjVar(self, VAR_NUMBER_SPAWNS))
        {
            num_spawns = getIntObjVar(self, VAR_NUMBER_SPAWNS);
        }
        int max_spawns = getIntObjVar(self, battlefield.VAR_SPAWNER_MAX);
        int pulse = getIntObjVar(self, battlefield.VAR_SPAWNER_PULSE);
        region bf = battlefield.getBattlefield(self);
        if (bf == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        battlefield.createRandomSpawn(master_object, self);
        num_spawns--;
        if (num_spawns < max_spawns)
        {
            messageTo(self, "msgBattlefieldSpawn", null, pulse, false);
        }
        return SCRIPT_CONTINUE;
    }
}
