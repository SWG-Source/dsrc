package script.npc.skillteacher;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;
import script.library.city;
import script.library.utils;

public class trainer_spawner extends script.base_script
{
    public trainer_spawner()
    {
    }
    public static final boolean TRAINERS_OFF = true;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (TRAINERS_OFF)
        {
            return SCRIPT_CONTINUE;
        }
        String spawn = getStringObjVar(self, "spawns");
        if (spawn == null)
        {
            return SCRIPT_OVERRIDE;
        }
        if (getIntObjVar(self, "city_id") > 0)
        {
            city.validateSpecialStructure(self);
        }
        messageTo(self, "spawnTrainer", null, 1.f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (getIntObjVar(self, "city_id") > 0)
        {
            obj_id trainer = utils.getObjIdScriptVar(self, "trainer");
            destroyObject(trainer);
            city.removeSkillTrainer(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        if (getIntObjVar(self, "city_id") > 0)
        {
            obj_id trainer = utils.getObjIdScriptVar(self, "trainer");
            destroyObject(trainer);
            utils.removeScriptVar(self, "trainer");
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnTrainer(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "trainer"))
        {
            return SCRIPT_CONTINUE;
        }
        String spawn = getStringObjVar(self, "spawns");
        if (spawn == null)
        {
            return SCRIPT_OVERRIDE;
        }
        if (!isTrainerAllowed(spawn))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id trainer = create.object(spawn, getLocation(self));
        if (trainer == null || !isIdValid(trainer))
        {
            obj_id creator = getObjIdObjVar(self, "creator");
            sendSystemMessage(creator, new string_id("city/city", "st_fail"));
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        setInvulnerable(trainer, true);
        setCreatureStatic(trainer, true);
        ai_lib.setDefaultCalmBehavior(trainer, ai_lib.BEHAVIOR_SENTINEL);
        if (getIntObjVar(self, "city_id") > 0)
        {
            setYaw(trainer, getYaw(self));
            setObjVar(trainer, "spawner", self);
            utils.setScriptVar(self, "trainer", trainer);
            attachScript(trainer, "npc.skillteacher.civic_skillteacher");
            String spawn_r = "st" + spawn.substring(spawn.indexOf('_'), spawn.length());
        }
        return SCRIPT_CONTINUE;
    }
    public int requestDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id trainer = utils.getObjIdScriptVar(self, "trainer");
        destroyObject(trainer);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public boolean isTrainerAllowed(String strTrainer) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "noEliteTrainers"))
        {
            String[] strAllowedTrainers = 
            {
                "trainer_artisan",
                "trainer_brawler",
                "trainer_entertainer",
                "trainer_marksman",
                "trainer_medic",
                "trainer_scout"
            };
            for (int intI = 0; intI < strAllowedTrainers.length; intI++)
            {
                String strTest = strAllowedTrainers[intI];
                if (strTrainer.equals(strTest))
                {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
