package script.poi.mysteriousdisk;

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
import script.library.combat;
import script.ai.ai_combat;

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "mysteriousdisk";
    public static final String LOG_NAME = "poiMysteriousDisk Master";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String SCRIPT_ANTAGONIST = BASE_PATH + ".antagonist";
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String TBL = "datatables/poi/" + SCENARIO_NAME + "/setup.iff";
    public static final String MEDIATOR_TYPE = "MEDIATOR_TYPE";
    public static final String ANTAGONIST_TYPE = "ANTAGONIST_TYPE";
    public static final int DEFAULT_ANTAGONIST_COUNT = 1;
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
        setObjVar(self, MEDIATOR_TYPE, params.getString(MEDIATOR_TYPE));
        setObjVar(self, ANTAGONIST_TYPE, params.getString(ANTAGONIST_TYPE));
        scenario.createTeam(self, "mediator", self.toString() + "_mediator");
        scenario.createTeam(self, "antagonist", self.toString() + "_antagonist");
        if (!createMediator(self))
        {
            scenario.cleanup(self);
            return SCRIPT_CONTINUE;
        }
        createAntagonists(self);
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
        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_WANDER);
        attachScript(mediator, SCRIPT_MEDIATOR);
        setObjVar(mediator, "ai.rangedOnly", true);
        return true;
    }
    public boolean createAntagonists(obj_id self) throws InterruptedException
    {
        location baseloc = getLocation(self);
        String type = getStringObjVar(self, ANTAGONIST_TYPE);
        if (!type.equals(""))
        {
            for (int i = 0; i < 2; i++)
            {
                String ident = "antagonist_" + (i + 1);
                location loiterloc = new location(baseloc);
                loiterloc.x += 25;
                loiterloc.z += 25;
                obj_id antagonist = scenario.createTeamNpc(self, "antagonist", type, ident, loiterloc);
                if ((antagonist == null) || (antagonist == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create antagonist minion.");
                }
                else 
                {
                    setYaw(antagonist, rand(0, 359));
                    ai_lib.setDefaultCalmBehavior(antagonist, ai_lib.BEHAVIOR_LOITER);
                    setObjVar(antagonist, "ai.rangedOnly", true);
                    attachScript(antagonist, SCRIPT_ANTAGONIST);
                }
            }
        }
        return true;
    }
    public int startFight(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "fighting"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "fighting", true);
        messageTo(self, "dieDieMyDarling", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int dieDieMyDarling(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id med = poi.findObject(scenario.MEDIATOR + "_0");
        if ((med == null) || (med == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id s1 = poi.findObject(scenario.ANTAGONIST + "_1");
        if ((s1 == null) || (s1 == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id s2 = poi.findObject(scenario.ANTAGONIST + "_2");
        if ((s2 == null) || (s2 == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(s1, "a_theresheis");
        startCombat(s1, med);
        startCombat(s2, med);
        return SCRIPT_CONTINUE;
    }
    public int handleActorDeath(obj_id self, dictionary params) throws InterruptedException
    {
        scenario.complete(self);
        obj_id med = poi.findObject(scenario.MEDIATOR + "_0");
        if ((med == null) || (med == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id s1 = poi.findObject(scenario.ANTAGONIST + "_1");
        if ((s1 == null) || (s1 == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(s1, "commentOnKill", null, 2, false);
        setObjVar(s1, "lookingForDisk", true);
        location loc = getLocation(med);
        loc.x -= 1;
        ai_lib.aiPathTo(s1, loc);
        return SCRIPT_CONTINUE;
    }
}
