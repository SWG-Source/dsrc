package script.poi.tusken_attack;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.scenario;
import script.library.poi;
import script.library.locations;

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "tusken_attack";
    public static final String LOG_NAME = "poiTuskenAttack Master";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String SCRIPT_ANTAGONIST = BASE_PATH + ".antagonist";
    public static final String SCRIPT_BEAST = BASE_PATH + ".beast";
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String TBL = "datatables/poi/" + SCENARIO_NAME + "/setup.iff";
    public static final String MEDIATOR_THUG_TYPE = "MEDIATOR_THUG_TYPE";
    public static final String MEDIATOR_LEADER_TYPE = "MEDIATOR_LEADER_TYPE";
    public static final String MEDIATOR_BEAST_TYPE = "MEDIATOR_BEAST_TYPE";
    public static final String MEDIATOR_COUNT = "MEDIATOR_COUNT";
    public static final String ANTAGONIST_THUG_TYPE = "ANTAGONIST_THUG_TYPE";
    public static final String ANTAGONIST_LEADER_TYPE = "ANTAGONIST_LEADER_TYPE";
    public static final String ANTAGONIST_BEAST_TYPE = "ANTAGONIST_BEAST_TYPE";
    public static final String ANTAGONIST_COUNT = "ANTAGONIST_COUNT";
    public static final String THEATER_FILE = "THEATER_FILE";
    public static final int DEFAULT_ANTAGONIST_COUNT = 1;
    public static final int MAX_DIFF = 70;
    public static final String joyEmotes[] = 
    {
        "cheer",
        "applaud",
        "laugh",
        "yes",
        "glow"
    };
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
        setObjVar(self, MEDIATOR_THUG_TYPE, params.getString(MEDIATOR_THUG_TYPE));
        setObjVar(self, MEDIATOR_LEADER_TYPE, params.getString(MEDIATOR_LEADER_TYPE));
        setObjVar(self, MEDIATOR_BEAST_TYPE, params.getString(MEDIATOR_BEAST_TYPE));
        setObjVar(self, MEDIATOR_COUNT, params.getInt(MEDIATOR_COUNT));
        setObjVar(self, ANTAGONIST_THUG_TYPE, params.getString(ANTAGONIST_THUG_TYPE));
        setObjVar(self, ANTAGONIST_LEADER_TYPE, params.getString(ANTAGONIST_LEADER_TYPE));
        setObjVar(self, ANTAGONIST_BEAST_TYPE, params.getString(ANTAGONIST_BEAST_TYPE));
        setObjVar(self, ANTAGONIST_COUNT, params.getInt(ANTAGONIST_COUNT));
        setObjVar(self, THEATER_FILE, params.getString(THEATER_FILE));
        scenario.createTeam(self, "mediator", self.toString() + "_mediator");
        scenario.createTeam(self, "antagonist", self.toString() + "_antagonist");
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
        obj_id a = poi.findObject("antagonist_0");
        messageTo(a, "startAttack", null, 90, false);
        return SCRIPT_CONTINUE;
    }
    public boolean createTheater(obj_id self) throws InterruptedException
    {
        String theater_file = getStringObjVar(self, THEATER_FILE);
        if (theater_file == null || theater_file.equals(""))
        {
            return false;
        }
        obj_id theater = poi.createObject(theater_file, 0, 0);
        if ((theater == null) || (theater == obj_id.NULL_ID))
        {
            return false;
        }
        setObjVar(self, "theater", theater);
        return true;
    }
    public int handleTheaterComplete(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public boolean createAntagonist(obj_id self) throws InterruptedException
    {
        String type = getStringObjVar(self, ANTAGONIST_LEADER_TYPE);
        String ident = "antagonist_0";
        location baseloc = getLocation(self);
        baseloc.z += 60;
        location loc = null;
        int i = 0;
        while (i < 10)
        {
            loc = locations.getGoodLocationAroundLocation(baseloc, 10, 10, 75, 25);
            if (loc != null)
            {
                break;
            }
            i++;
        }
        if (loc == null)
        {
            return false;
        }
        obj_id antagonist = scenario.createTeamNpc(self, "antagonist", type, ident, loc);
        if ((antagonist == null) || (antagonist == obj_id.NULL_ID))
        {
            return false;
        }
        ai_lib.setDefaultCalmBehavior(antagonist, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(antagonist, SCRIPT_ANTAGONIST);
        return true;
    }
    public boolean createAntagonistMinions(obj_id self) throws InterruptedException
    {
        location baseloc = getLocation(self);
        location leaderloc = getLocation(scenario.getRandomTeamMember(self, "antagonist"));
        String type = getStringObjVar(self, ANTAGONIST_THUG_TYPE);
        String beasttype = getStringObjVar(self, ANTAGONIST_BEAST_TYPE);
        int count = getIntObjVar(self, ANTAGONIST_COUNT);
        if (!type.equals(""))
        {
            for (int i = 0; i < count; i++)
            {
                String ident = "antagonist_" + (i + 1);
                location minionloc = new location(leaderloc);
                minionloc.x += (rand(-6, 6) * 5);
                minionloc.z += (rand(-6, 6) * 5);
                obj_id minion = scenario.createTeamNpc(self, "antagonist", type, ident, minionloc);
                if ((minion == null) || (minion == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create antagonist minion.");
                }
                else 
                {
                    ai_lib.setDefaultCalmBehavior(minion, ai_lib.BEHAVIOR_SENTINEL);
                    attachScript(minion, SCRIPT_ANTAGONIST);
                }
            }
            for (int i = -1; i <= 1; i++)
            {
                String ident = "antagonist_" + (count + i + 2);
                location beastloc = new location(leaderloc);
                beastloc.x += 5;
                beastloc.z += 5;
                float xdelta = baseloc.x - beastloc.x;
                float zdelta = baseloc.z - beastloc.z;
                beastloc.x += ((10 * i) * (xdelta / zdelta));
                beastloc.z += (10 * i);
                obj_id beast = scenario.createTeamNpc(self, "antagonist", beasttype, ident, beastloc);
                if ((beast == null) || (beast == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create antagonist beast.");
                }
                else 
                {
                    ai_lib.setDefaultCalmBehavior(beast, ai_lib.BEHAVIOR_SENTINEL);
                    attachScript(beast, SCRIPT_BEAST);
                }
            }
        }
        return true;
    }
    public boolean createMediator(obj_id self) throws InterruptedException
    {
        String type = getStringObjVar(self, MEDIATOR_LEADER_TYPE);
        String ident = "mediator_0";
        location loc = getLocation(self);
        loc.z += 15;
        obj_id mediator = scenario.createTeamNpc(self, "mediator", type, ident, loc);
        if ((mediator == null) || (mediator == obj_id.NULL_ID))
        {
            return false;
        }
        setYaw(mediator, 180);
        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(mediator, SCRIPT_MEDIATOR);
        return true;
    }
    public boolean createMediatorMinions(obj_id self) throws InterruptedException
    {
        location baseloc = getLocation(self);
        String type = getStringObjVar(self, MEDIATOR_THUG_TYPE);
        String beasttype = getStringObjVar(self, MEDIATOR_BEAST_TYPE);
        if (!type.equals(""))
        {
            int count = getIntObjVar(self, MEDIATOR_COUNT);
            for (int i = 0; i < count; i++)
            {
                String ident = "mediator_" + (i + 1);
                float xdelta = rand(-15, 15) * 2;
                float zdelta = 15 + (rand(-3, 5) * 2);
                location loiterloc = new location(baseloc);
                loiterloc.x += xdelta;
                loiterloc.z += zdelta;
                obj_id mediator;
                if ((i == 4) && !beasttype.equals("none"))
                {
                    mediator = scenario.createTeamNpc(self, "mediator", beasttype, ident, loiterloc);
                }
                else 
                {
                    mediator = scenario.createTeamNpc(self, "mediator", type, ident, loiterloc);
                }
                if ((mediator == null) || (mediator == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create mediator minion.");
                }
                else 
                {
                    setYaw(mediator, 180);
                    ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
                    attachScript(mediator, SCRIPT_MEDIATOR);
                }
            }
        }
        return true;
    }
    public int handleActorDeath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] antagonists = scenario.getTeamMembers("antagonist");
        if (antagonists == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] mediators = scenario.getTeamMembers("mediator");
        if (mediators == null)
        {
            return SCRIPT_CONTINUE;
        }
        int deadcount = scenario.getDeadTeamMemberCount(self, "antagonist");
        if ((deadcount) == antagonists.length)
        {
            celebrateVictory(mediators, "m_victory");
            return SCRIPT_CONTINUE;
        }
        deadcount = scenario.getDeadTeamMemberCount(self, "mediator");
        if ((deadcount) == mediators.length)
        {
            celebrateVictory(antagonists, "a_victory");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void celebrateVictory(obj_id[] team, String comment) throws InterruptedException
    {
        int j = 0;
        for (int i = 0; i < team.length; i++)
        {
            if (isIncapacitated(team[i]) || isDead(team[i]))
            {
                continue;
            }
            String skeleton = dataTableGetString("datatables/ai/species.iff", ai_lib.aiGetSpecies(team[i]), "Skeleton");
            if (!skeleton.equals("human"))
            {
                continue;
            }
            ai_lib.setDefaultCalmBehavior(team[i], ai_lib.BEHAVIOR_WANDER);
            stop(team[i]);
            int whichsocial = rand(0, 4);
            queueCommand(team[i], (1780871594), null, joyEmotes[whichsocial], COMMAND_PRIORITY_DEFAULT);
            messageTo(team[i], "resumeDefaultCalmBehavior", null, 3, false);
            if (j == 0)
            {
                poi.quickSay(team[i], comment);
            }
            j++;
        }
    }
}
