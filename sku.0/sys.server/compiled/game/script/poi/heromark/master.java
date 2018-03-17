package script.poi.heromark;

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
import script.ai.ai_combat;

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "heromark";
    public static final String LOG_NAME = "poiHeroMark Master";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String MEDIATOR_TYPE = "MEDIATOR_TYPE";
    public static final String SCENARIO_THEATER = "object/building/poi/camp/tatooine_traders_camp_small.iff";
    public static final String TBL = "datatables/poi/" + SCENARIO_NAME + "/setup.iff";
    public static final int mediatorCoordsX[] = 
    {
        8,
        -2,
        6,
        -3,
        -5,
        0
    };
    public static final int mediatorCoordsZ[] = 
    {
        2,
        0,
        1,
        -10,
        1,
        -6
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
        String mtype = params.getString("MEDIATOR_TEMPLATE");
        setObjVar(self, MEDIATOR_TYPE, mtype);
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
        if (!createMediator(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean createMediator(obj_id self) throws InterruptedException
    {
        String type = getStringObjVar(self, MEDIATOR_TYPE);
        String ident = "mediator_0";
        location loc = getLocation(self);
        obj_id mediator = scenario.createTeamNpc(self, "mediator", type, ident, loc);
        if ((mediator == null) || (mediator == obj_id.NULL_ID))
        {
            return false;
        }
        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(mediator, SCRIPT_MEDIATOR);
        return true;
    }
}
