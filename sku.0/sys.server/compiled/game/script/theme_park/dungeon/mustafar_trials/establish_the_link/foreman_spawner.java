package script.theme_park.dungeon.mustafar_trials.establish_the_link;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.trial;

public class foreman_spawner extends script.base_script
{
    public foreman_spawner()
    {
    }
    public static final boolean LOGGING = false;
    public static final String FOREMAN = "som_link_lava_beetle_foreman";
    public static final String SOLDIER = "som_link_lava_beetle_defender";
    public static final String[] offSet = 
    {
        "-10:-10",
        "-10:10",
        "10:-10",
        "10:10"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        spawnEventMobs(self);
        utils.sendSystemMessagePob(trial.getTop(self), trial.UPLINK_FOREMAN_SPAWN);
        return SCRIPT_CONTINUE;
    }
    public int foremanDied(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "foremanDead", true);
        obj_id controller = trial.getTop(self);
        messageTo(controller, "linkEventWon", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public void spawnEventMobs(obj_id self) throws InterruptedException
    {
        obj_id[] eventMobs = new obj_id[5];
        location spawnLoc = getLocation(self);
        obj_id foreman = create.object(FOREMAN, spawnLoc);
        if (!isIdValid(foreman))
        {
            doLogging("spawnActors", "Tried to create invalid creature(" + foreman + ")");
            return;
        }
        utils.setScriptVar(foreman, trial.PARENT, self);
        attachScript(foreman, "theme_park.dungeon.mustafar_trials.establish_the_link.foreman");
        obj_id creature = obj_id.NULL_ID;
        for (int i = 0; i < 4; i++)
        {
            String locString = offSet[i];
            String[] parse = split(locString, ':');
            float locX = spawnLoc.x + utils.stringToFloat(parse[0]);
            float locZ = spawnLoc.z + utils.stringToFloat(parse[1]);
            location soldierSpawn = new location(locX, spawnLoc.y, locZ, spawnLoc.area, spawnLoc.cell);
            creature = create.object(SOLDIER, soldierSpawn);
            if (!isIdValid(creature))
            {
                doLogging("spawnActors", "Tried to create invalid creature(" + creature + ")");
                return;
            }
            attachScript(creature, "theme_park.dungeon.mustafar_trials.establish_the_link.foreman_guard");
            eventMobs[i] = creature;
            utils.setScriptVar(creature, trial.PARENT, self);
        }
        ai_lib.establishAgroLink(foreman, eventMobs);
    }
    public int guardDied(obj_id self, dictionary params) throws InterruptedException
    {
        int deadGuards = 0;
        if (utils.hasScriptVar(self, "deadGuards"))
        {
            deadGuards = utils.getIntScriptVar(self, "deadGuards");
        }
        deadGuards += 1;
        validateEvent(self);
        return SCRIPT_CONTINUE;
    }
    public void validateEvent(obj_id self) throws InterruptedException
    {
        boolean foremanDead = false;
        boolean guardsDead = false;
        if (utils.hasScriptVar(self, "foremanDead"))
        {
            foremanDead = utils.getBooleanScriptVar(self, "foremanDead");
        }
        if (utils.hasScriptVar(self, "deadGuards"))
        {
            int guards = utils.getIntScriptVar(self, "deadGuards");
            if (guards >= 4)
            {
                guardsDead = true;
            }
        }
        if (foremanDead && guardsDead)
        {
            winEvent(self);
        }
    }
    public void winEvent(obj_id self) throws InterruptedException
    {
        obj_id dungeon = trial.getTop(self);
        if (isIdValid(dungeon))
        {
            messageTo(dungeon, "foremanEventWon", null, 5, false);
        }
        else 
        {
            doLogging("winEvent", "Failed to get top");
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/foreman_drone_spawner/" + section, message);
        }
    }
}
