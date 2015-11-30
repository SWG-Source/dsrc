package script.poi.factoryliberation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.poi;
import script.library.scenario;
import script.library.debug;
import script.library.theater;
import script.library.ai_lib;
import script.library.combat;
import script.library.factions;

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "factoryliberation";
    public static final String LOG_NAME = "poiFactoryLiberation Master";
    public static final String SCENARIO_THEATER = "object/building/poi/tatooine_factory_liberation.iff";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String SCRIPT_ANTAGONIST = BASE_PATH + ".antagonist";
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String SCRIPT_PRISONER = BASE_PATH + ".prisoner";
    public static final String SCRIPT_PIRATE = BASE_PATH + ".pirate";
    public static final String TBL = "datatables/poi/" + SCENARIO_NAME + "/setup.iff";
    public static final String MEDIATOR_EASY = "MEDIATOR_TEMPLATE_EASY";
    public static final String MEDIATOR_MEDIUM = "MEDIATOR_TEMPLATE_MEDIUM";
    public static final String MEDIATOR_HARD = "MEDIATOR_TEMPLATE_HARD";
    public static final String MEDIATOR_TYPE = "MEDIATOR_TYPE";
    public static final String ANTAGONIST_EASY = "ANTAGONIST_TEMPLATE_EASY";
    public static final String ANTAGONIST_MEDIUM = "ANTAGONIST_TEMPLATE_MEDIUM";
    public static final String ANTAGONIST_HARD = "ANTAGONIST_TEMPLATE_HARD";
    public static final String ANTAGONIST_TYPE = "ANTAGONIST_TYPE";
    public static final int DEFAULT_ANTAGONIST_COUNT = 1;
    public static final int antagonistCoordsX[] = 
    {
        8,
        -10,
        30,
        -20,
        30,
        0,
        35
    };
    public static final int antagonistCoordsZ[] = 
    {
        15,
        -15,
        13,
        15,
        -15,
        -20,
        0
    };
    public static final int MAX_DIFF = 70;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!scenario.initScenario(self, TBL))
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
        if (!scenario.initScenario(self, TBL))
        {
            scenario.cleanup(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int initScenario(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "wallHasBeenDestroyed", false);
        setObjVar(self, MEDIATOR_EASY, params.getString(MEDIATOR_EASY));
        setObjVar(self, MEDIATOR_MEDIUM, params.getString(MEDIATOR_MEDIUM));
        setObjVar(self, MEDIATOR_HARD, params.getString(MEDIATOR_HARD));
        setObjVar(self, ANTAGONIST_EASY, params.getString(ANTAGONIST_EASY));
        setObjVar(self, ANTAGONIST_MEDIUM, params.getString(ANTAGONIST_MEDIUM));
        setObjVar(self, ANTAGONIST_HARD, params.getString(ANTAGONIST_HARD));
        int difficultyLevel = getIntObjVar(self, "spawning.intDifficultyLevel");
        if (difficultyLevel == 0)
        {
            difficultyLevel = 5;
        }
        if ((difficultyLevel >= 5) && (difficultyLevel < 28))
        {
            setObjVar(self, ANTAGONIST_TYPE, getStringObjVar(self, ANTAGONIST_EASY));
            setObjVar(self, MEDIATOR_TYPE, getStringObjVar(self, MEDIATOR_EASY));
        }
        else if ((difficultyLevel >= 28) && (difficultyLevel < 48))
        {
            setObjVar(self, ANTAGONIST_TYPE, getStringObjVar(self, ANTAGONIST_MEDIUM));
            setObjVar(self, MEDIATOR_TYPE, getStringObjVar(self, MEDIATOR_MEDIUM));
        }
        else 
        {
            if (difficultyLevel > MAX_DIFF)
            {
                difficultyLevel = MAX_DIFF;
            }
            setObjVar(self, ANTAGONIST_TYPE, getStringObjVar(self, ANTAGONIST_HARD));
            setObjVar(self, MEDIATOR_TYPE, getStringObjVar(self, MEDIATOR_HARD));
        }
        scenario.createTeam(self, "antagonist", self.toString() + "_antagonist");
        scenario.createTeam(self, "mediator", self.toString() + "_mediator");
        if (!createTheater(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        if (!createAntagonist(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        if (!createAntagonistMinions(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        if (!createMediator(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        if (!createMediatorMinions(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "setTitles", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public boolean createTheater(obj_id self) throws InterruptedException
    {
        obj_id theater = poi.createObject(SCENARIO_THEATER, 0, 0);
        if ((theater == null) || (theater == obj_id.NULL_ID))
        {
            return false;
        }
        setObjVar(self, "theater", theater);
        return true;
    }
    public int handleTheaterComplete(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id donetheater = params.getObjId(theater.DICT_MASTER);
        attachWallScripts(self, donetheater);
        return SCRIPT_CONTINUE;
    }
    public void attachWallScripts(obj_id self, obj_id prison) throws InterruptedException
    {
        if ((prison == null) || (prison == obj_id.NULL_ID))
        {
            return;
        }
        obj_id[] children = getObjIdArrayObjVar(prison, theater.VAR_CHILDREN);
        if ((children == null) || (children.length == 0))
        {
            return;
        }
        else 
        {
            int j = 0;
            for (int i = 0; i < children.length; i++)
            {
                obj_id child = children[i];
                if ((child == null) || (child == obj_id.NULL_ID))
                {
                }
                else 
                {
                    String childname = getName(child);
                    if ((childname != null) && childname.equals("battlefield:barbed_wall"))
                    {
                        if (j == 0)
                        {
                            String convo = getStringObjVar(self, scenario.VAR_SCENARIO_CONVO);
                            if (convo.equals(""))
                            {
                                return;
                            }
                            string_id wallName = new string_id(convo, "weakened_wall_name");
                            setName(child, getString(wallName));
                            attachScript(child, "poi.factoryliberation.weakened_wall");
                        }
                        else 
                        {
                            attachScript(child, "poi.factoryliberation.invulnerable_wall");
                        }
                        setObjVar(child, POI_BASE_OBJECT, self);
                        j++;
                    }
                }
            }
        }
        return;
    }
    public boolean createAntagonist(obj_id self) throws InterruptedException
    {
        String type = getStringObjVar(self, ANTAGONIST_TYPE);
        String ident = "antagonist_0";
        location loc = getLocation(self);
        loc.x += -3;
        loc.z += 12;
        obj_id antagonist = scenario.createTeamNpc(self, "antagonist", type, ident, loc);
        if ((antagonist == null) || (antagonist == obj_id.NULL_ID))
        {
            return false;
        }
        attachScript(antagonist, SCRIPT_ANTAGONIST);
        ai_lib.setDefaultCalmBehavior(antagonist, ai_lib.BEHAVIOR_SENTINEL);
        return true;
    }
    public boolean createAntagonistMinions(obj_id self) throws InterruptedException
    {
        location baseloc = getLocation(self);
        String type = getStringObjVar(self, ANTAGONIST_TYPE);
        if (!type.equals(""))
        {
            for (int i = 0; i < 4; i++)
            {
                String ident = "antagonist_" + (i + 1);
                location loiterloc = new location(baseloc);
                loiterloc.x += antagonistCoordsX[i] - 20;
                loiterloc.z += antagonistCoordsZ[i];
                obj_id antagonist = scenario.createTeamNpc(self, "antagonist", type, ident, loiterloc);
                if ((antagonist == null) || (antagonist == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create antagonist minion.");
                }
                else 
                {
                    setYaw(antagonist, rand(0, 359));
                    attachScript(antagonist, SCRIPT_ANTAGONIST);
                    attachScript(antagonist, SCRIPT_PIRATE);
                    ai_lib.setDefaultCalmBehavior(antagonist, ai_lib.BEHAVIOR_LOITER);
                    ai_lib.setLoiterRanges(antagonist, 0, 2);
                }
            }
        }
        return true;
    }
    public boolean createMediator(obj_id self) throws InterruptedException
    {
        String type = getStringObjVar(self, MEDIATOR_TYPE);
        String ident = "mediator_0";
        location loc = getLocation(self);
        loc.x += 19;
        loc.z += 1;
        obj_id mediator = scenario.createTeamNpc(self, "mediator", type, ident, loc);
        if ((mediator == null) || (mediator == obj_id.NULL_ID))
        {
            return false;
        }
        attachScript(mediator, SCRIPT_MEDIATOR);
        attachScript(mediator, SCRIPT_PRISONER);
        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
        return true;
    }
    public boolean createMediatorMinions(obj_id self) throws InterruptedException
    {
        location baseloc = getLocation(self);
        String type = getStringObjVar(self, MEDIATOR_TYPE);
        if (!type.equals(""))
        {
            for (int i = 0; i < 3; i++)
            {
                String ident = "mediator_" + (i + 1);
                float xdelta = rand(1f, 3f);
                if (i % 2 == 1)
                {
                    xdelta *= -1f;
                }
                float zdelta = rand(1f, 3f);
                if (i % 2 == 0)
                {
                    zdelta *= -1f;
                }
                location loiterloc = new location(baseloc);
                loiterloc.x += xdelta + 26;
                loiterloc.z += zdelta - 1;
                obj_id mediator = scenario.createTeamNpc(self, "mediator", type, ident, loiterloc);
                if ((mediator == null) || (mediator == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create mediator minion.");
                }
                else 
                {
                    setYaw(mediator, rand(0, 359));
                    attachScript(mediator, SCRIPT_MEDIATOR);
                    attachScript(mediator, SCRIPT_PRISONER);
                    ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_LOITER);
                    ai_lib.setLoiterRanges(mediator, 0, 2);
                }
            }
        }
        return true;
    }
    public int wallDamaged(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(m, "celebrateWallLeader", null, 0, false);
        m = poi.findObject("mediator_1");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(m, "celebrateWall", null, 0, false);
        setObjVar(self, "wallDamaged", true);
        obj_id attacker = params.getObjId("attacker");
        if ((attacker == null) || (attacker == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] team = scenario.getTeamMembers(self, "antagonist");
        for (int i = 0; i < team.length; i++)
        {
            if (i == 0)
            {
                messageTo(team[i], "defendWall", null, 0, false);
            }
            if (ai_lib.isInCombat(team[i]))
            {
                continue;
            }
            ai_lib.addToMentalStateToward(team[i], attacker, FEAR, 50f);
            startCombat(team[i], attacker);
        }
        return SCRIPT_CONTINUE;
    }
    public int wallDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        boolean wallHasBeenDestroyed = getBooleanObjVar(self, "wallHasBeenDestroyed");
        if (wallHasBeenDestroyed)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "wallHasBeenDestroyed", true);
        obj_id[] team = scenario.getTeamMembers(self, "mediator");
        for (int i = 0; i < team.length; i++)
        {
            factions.setFaction(team[i], getStringObjVar(team[i], "oldFaction"));
            ai_lib.setIgnoreCombat(team[i], false);
        }
        return SCRIPT_CONTINUE;
    }
    public int setTitles(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        poi.setTitle(m, "m_title");
        obj_id a = poi.findObject("antagonist_0");
        if ((a == null) || (a == obj_id.NULL_ID) || ai_lib.isInCombat(a))
        {
            return SCRIPT_CONTINUE;
        }
        poi.setTitle(a, "a_title");
        return SCRIPT_CONTINUE;
    }
}
