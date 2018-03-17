package script.poi.gangwar;

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

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "gangwar";
    public static final String LOG_NAME = "poiGangWar Master";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String SCRIPT_ANTAGONIST = BASE_PATH + ".antagonist";
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
    public static final String TAUNTS = "TAUNTS";
    public static final int DEFAULT_ANTAGONIST_COUNT = 1;
    public static final int mediatorCoordsX[] = 
    {
        -13,
        -12,
        -5,
        3
    };
    public static final int mediatorCoordsZ[] = 
    {
        0,
        -15,
        13,
        -15
    };
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
        setObjVar(self, TAUNTS, params.getString(TAUNTS));
        scenario.createTeam(self, "mediator", self.toString() + "_mediator");
        scenario.createTeam(self, "antagonist", self.toString() + "_antagonist");
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
        return SCRIPT_CONTINUE;
    }
    public boolean createAntagonist(obj_id self) throws InterruptedException
    {
        String type = getStringObjVar(self, ANTAGONIST_LEADER_TYPE);
        String ident = "antagonist_0";
        location loc = getLocation(self);
        obj_id antagonist = scenario.createTeamNpc(self, "antagonist", type, ident, loc);
        if ((antagonist == null) || (antagonist == obj_id.NULL_ID))
        {
            return false;
        }
        ai_lib.setDefaultCalmBehavior(antagonist, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(antagonist, SCRIPT_ANTAGONIST);
        setObjVar(antagonist, "ai.rangedOnly", true);
        return true;
    }
    public boolean createAntagonistMinions(obj_id self) throws InterruptedException
    {
        location baseloc = getLocation(self);
        String type = getStringObjVar(self, ANTAGONIST_THUG_TYPE);
        String beasttype = getStringObjVar(self, ANTAGONIST_BEAST_TYPE);
        if (!type.equals(""))
        {
            for (int i = 0; i < 4; i++)
            {
                String ident = "antagonist_" + (i + 1);
                float zdelta = -2;
                if (i == 0)
                {
                    zdelta = -5;
                }
                else if ((i == 1) || (i == 4))
                {
                    zdelta = -1;
                }
                float xdelta = -5 + i * 2;
                location loiterloc = new location(baseloc);
                loiterloc.x += xdelta;
                loiterloc.z += zdelta;
                obj_id antagonist;
                if ((i == 2) && !beasttype.equals("none"))
                {
                    antagonist = scenario.createTeamNpc(self, "antagonist", beasttype, ident, loiterloc);
                }
                else 
                {
                    antagonist = scenario.createTeamNpc(self, "antagonist", type, ident, loiterloc);
                }
                if ((antagonist == null) || (antagonist == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create antagonist minion.");
                }
                else 
                {
                    ai_lib.setDefaultCalmBehavior(antagonist, ai_lib.BEHAVIOR_SENTINEL);
                    attachScript(antagonist, SCRIPT_ANTAGONIST);
                    setObjVar(antagonist, "ai.rangedOnly", true);
                    if (i == 0)
                    {
                        setObjVar(antagonist, "ai.grenadeType", "object/weapon/ranged/grenade/grenade_fragmentation");
                    }
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
        loc.z += 4;
        obj_id mediator = scenario.createTeamNpc(self, "mediator", type, ident, loc);
        if ((mediator == null) || (mediator == obj_id.NULL_ID))
        {
            return false;
        }
        setYaw(mediator, 180);
        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(mediator, SCRIPT_MEDIATOR);
        setObjVar(mediator, "ai.rangedOnly", true);
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
                float xdelta = -5 + i * 2;
                float zdelta = 6;
                if (i == 0)
                {
                    zdelta = 9;
                }
                else if ((i == 1) || (i == 4))
                {
                    zdelta = 5;
                }
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
                    setObjVar(mediator, "ai.rangedOnly", true);
                    if (i == 0)
                    {
                        setObjVar(mediator, "ai.grenadeType", "object/weapon/ranged/grenade/grenade_fragmentation");
                    }
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
        if ((deadcount + 1) == antagonists.length)
        {
            celebrateVictory(mediators, "m_victory");
            return SCRIPT_CONTINUE;
        }
        deadcount = scenario.getDeadTeamMemberCount(self, "mediator");
        if ((deadcount + 1) == mediators.length)
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
