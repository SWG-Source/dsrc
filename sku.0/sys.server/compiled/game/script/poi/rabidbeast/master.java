package script.poi.rabidbeast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;
import script.library.scenario;
import script.library.ai_lib;
import script.library.group;
import script.library.combat;
import script.ai.ai_combat;

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "rabidbeast";
    public static final String LOG_NAME = "poiRabidBeast Master";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String MEDIATOR_TYPE = "MEDIATOR_TYPE";
    public static final String SCRIPT_ANTAGONIST = BASE_PATH + ".antagonist";
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final int MAX_ITERATIONS = 30;
    public static final String CREATURE_WHERE = "where";
    public static final String CREATURE_LEVEL = "level";
    public static final String CREATURE_NAME = "creatureName";
    public static final String CREATURE_TAME = "canTame";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        if (!scenario.initScenario(self, "datatables/poi/" + SCENARIO_NAME + "/" + planet + "_setup.iff"))
        {
            scenario.cleanup(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        scenario.cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        scenario.cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanupScenario(obj_id self, dictionary params) throws InterruptedException
    {
        scenario.cleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int initScenario(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        String planet = getCurrentSceneName();
        String theater = params.getString("THEATER_TEMPLATE");
        setObjVar(self, "theaterTemplate", theater);
        String mtype = params.getString("MEDIATOR_TEMPLATE");
        setObjVar(self, MEDIATOR_TYPE, mtype);
        dataTableOpen(CREATURE_TABLE);
        String creature_where[] = dataTableGetStringColumn(CREATURE_TABLE, CREATURE_WHERE);
        String creature_name[] = dataTableGetStringColumn(CREATURE_TABLE, CREATURE_NAME);
        int creature_level[] = dataTableGetIntColumn(CREATURE_TABLE, CREATURE_LEVEL);
        float creature_tamable[] = dataTableGetFloatColumn(CREATURE_TABLE, CREATURE_TAME);
        int planet_entries = 0;
        for (int i = 0; i < creature_name.length; i++)
        {
            if (creature_where[i].equals(planet))
            {
                planet_entries++;
            }
        }
        int indexstart = rand(0, planet_entries - 1);
        planet_entries = 0;
        for (int i = 0; i < creature_name.length; i++)
        {
            if (creature_where[i].equals(planet))
            {
                if (planet_entries == indexstart)
                {
                    indexstart = i;
                    break;
                }
                planet_entries++;
            }
        }
        int difficultyLevel = getIntObjVar(self, "spawning.intDifficultyLevel");
        if (difficultyLevel == 0)
        {
            difficultyLevel = 5;
        }
        int minDifficultyLevel = difficultyLevel - 30;
        int i = indexstart + 1;
        while (i != indexstart)
        {
            if (creature_where[i].equals(planet) && (creature_level[i] <= difficultyLevel) && (creature_level[i] >= minDifficultyLevel) && (creature_tamable[i] >= .25f))
            {
                setObjVar(self, "creatureName", creature_name[i]);
                setObjVar(self, "creatureLevel", creature_level[i]);
                break;
            }
            i++;
            if (i == creature_name.length)
            {
                i = 0;
            }
        }
        if (!hasObjVar(self, "creatureName"))
        {
            messageTo(self, scenario.HANDLER_DESTROY_SELF, null, 3, true);
            return SCRIPT_CONTINUE;
        }
        scenario.createTeam(self, "antagonist", self.toString() + "_antagonist");
        scenario.createTeam(self, "mediator", self.toString() + "_mediator");
        if (!createTheaters(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean createTheaters(obj_id self) throws InterruptedException
    {
        obj_id theater = poi.createObject(getStringObjVar(self, "theaterTemplate"), 0, 0);
        setObjVar(self, "theater", theater);
        return true;
    }
    public int handleTheaterComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (!createMediator(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnNpcMobiles(obj_id self) throws InterruptedException
    {
        int difficultyLevel = getIntObjVar(self, "spawning.intDifficultyLevel");
        if (difficultyLevel == 0)
        {
            difficultyLevel = 5;
        }
        int spawnedLevels = 0;
        int creatureLevel = getIntObjVar(self, "creatureLevel");
        int mobileNumber = 0;
        int iterations = 0;
        String creature = getStringObjVar(self, "creatureName");
        while ((difficultyLevel >= spawnedLevels) && (iterations < MAX_ITERATIONS))
        {
            iterations++;
            obj_id mobile = spawnMobile(self, mobileNumber, creature);
            if (mobile != null)
            {
                setObjVar(self, "npc_lair.mobile." + mobileNumber, creature);
                mobileNumber++;
                spawnedLevels += creatureLevel;
                ai_lib.setIgnoreCombat(mobile, true);
            }
        }
    }
    public obj_id spawnMobile(obj_id self, int index, String name) throws InterruptedException
    {
        location loiterloc = new location(getLocation(self));
        loiterloc.x += rand(-20f, 20f);
        loiterloc.z += rand(-20f, 20f);
        obj_id mobile = scenario.createTeamNpc(self, "antagonist", name, "antagonist_" + index, loiterloc);
        if (mobile != null)
        {
            poi.setDestroyMessage(mobile, "handleCreatureDeath", 1);
            attachScript(mobile, SCRIPT_ANTAGONIST);
            return mobile;
        }
        return null;
    }
    public boolean createMediator(obj_id self) throws InterruptedException
    {
        String type = getStringObjVar(self, MEDIATOR_TYPE);
        String ident = "mediator_0";
        location loc = getLocation(self);
        loc.x += 4;
        obj_id mediator = scenario.createTeamNpc(self, "mediator", type, ident, loc);
        if ((mediator == null) || (mediator == obj_id.NULL_ID))
        {
            return false;
        }
        attachScript(mediator, SCRIPT_MEDIATOR);
        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
        return true;
    }
    public int startAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id mediator = poi.findObject("mediator_0");
        if ((mediator == null) || (mediator == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId("target");
        spawnNpcMobiles(self);
        obj_id[] antagonists = scenario.getTeamMembers(self, "antagonist");
        if (antagonists == null)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < antagonists.length; i++)
        {
            obj_id a = antagonists[i];
            if ((a == null) || (a == obj_id.NULL_ID))
            {
                continue;
            }
            if (group.isGroupObject(target))
            {
                obj_id[] members = getGroupMemberIds(target);
                if ((members == null) || (members.length == 0))
                {
                }
                else 
                {
                    int j = rand(0, members.length - 1);
                    startCombat(a, members[j]);
                }
            }
            else 
            {
                startCombat(a, target);
            }
            if (i == 0)
            {
                startCombat(mediator, a);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleActorDeath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id mediator = poi.findObject("mediator_0");
        if ((mediator == null) || (mediator == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(mediator) || isDead(mediator))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] antagonists = scenario.getTeamMembers("antagonist");
        if (antagonists == null)
        {
            return SCRIPT_CONTINUE;
        }
        int deadcount = scenario.getDeadTeamMemberCount(self, "antagonist");
        if ((deadcount + 1) == antagonists.length)
        {
            messageTo(mediator, "allDead", null, 2, true);
        }
        return SCRIPT_CONTINUE;
    }
}
