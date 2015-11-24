package script.poi.twoliars;

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
    public static final String SCENARIO_NAME = "twoliars";
    public static final String LOG_NAME = "poiTwoLiars Master";
    public static final String SCENARIO_THEATER = "object/building/poi/tatooine_fugitive_camp_medium.iff";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String MEDIATOR_TYPE = "MEDIATOR_TYPE";
    public static final String ANTAGONIST_TYPE = "ANTAGONIST_TYPE";
    public static final String SCRIPT_ANTAGONIST = BASE_PATH + ".antagonist";
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String TBL = "datatables/poi/" + SCENARIO_NAME + "/setup.iff";
    public static final int mediatorCoordsX[] = 
    {
        8,
        -5,
        -2,
        0,
        -6,
        -3
    };
    public static final int mediatorCoordsZ[] = 
    {
        7,
        -6,
        6,
        4,
        6,
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
        String atype = params.getString("ANTAGONIST_TEMPLATE");
        setObjVar(self, ANTAGONIST_TYPE, atype);
        scenario.createTeam(self, "mediator", self.toString() + "_mediator");
        scenario.createTeam(self, "antagonist", self.toString() + "_antagonist");
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
        setObjVar(self, "theater", theater);
        return true;
    }
    public int handleTheaterComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (!createAntagonist(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        if (!createMediators(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "setTitles", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public boolean createAntagonist(obj_id self) throws InterruptedException
    {
        String type = getStringObjVar(self, ANTAGONIST_TYPE);
        String ident = "antagonist_0";
        location loc = getLocation(self);
        obj_id antagonist = scenario.createTeamNpc(self, "antagonist", type, ident, loc);
        if ((antagonist == null) || (antagonist == obj_id.NULL_ID))
        {
            return false;
        }
        attachScript(antagonist, SCRIPT_ANTAGONIST);
        ai_lib.setDefaultCalmBehavior(antagonist, ai_lib.BEHAVIOR_SENTINEL);
        return true;
    }
    public boolean createMediators(obj_id self) throws InterruptedException
    {
        location baseloc = getLocation(self);
        String type = getStringObjVar(self, MEDIATOR_TYPE);
        if (!type.equals(""))
        {
            int coord_idx[] = new int[6];
            for (int i = 0; i < 6; i++)
            {
                coord_idx[i] = i;
            }
            int statement_idx[] = new int[6];
            for (int i = 0; i < 6; i++)
            {
                statement_idx[i] = i;
            }
            for (int i = 5; i >= 0; i--)
            {
                String ident = "mediator_" + (i + 1);
                int index = rand(0, i);
                int idx = coord_idx[index];
                coord_idx[index] = coord_idx[i];
                coord_idx[i] = -1;
                int sindex = rand(0, i);
                int sidx = statement_idx[sindex];
                statement_idx[sindex] = statement_idx[i];
                statement_idx[i] = -1;
                location loiterloc = new location(baseloc);
                loiterloc.x += mediatorCoordsX[idx];
                loiterloc.z += mediatorCoordsZ[idx];
                obj_id mediator = scenario.createTeamNpc(self, "mediator", type, ident, loiterloc);
                if ((mediator == null) || (mediator == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create mediator minion.");
                }
                else 
                {
                    setYaw(mediator, rand(0, 359));
                    attachScript(mediator, SCRIPT_MEDIATOR);
                    ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
                    setObjVar(mediator, "m_index", i);
                    setObjVar(self, "m_" + i, getName(mediator));
                    setObjVar(mediator, "s_index", sidx);
                }
            }
        }
        return true;
    }
    public int setTitles(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        poi.setTitle(m, "m_title");
        return SCRIPT_CONTINUE;
    }
}
