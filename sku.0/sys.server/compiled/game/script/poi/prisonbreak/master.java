package script.poi.prisonbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;
import script.library.npc;
import script.library.scenario;
import script.library.debug;
import script.library.theater;
import script.library.ai_lib;
import script.library.utils;

public class master extends script.theme_park.poi.base
{
    public master()
    {
    }
    public static final String SCENARIO_NAME = "prisonbreak";
    public static final String LOG_NAME = "poiPrisonBreak Master";
    public static final String SCENARIO_THEATER = "object/building/poi//tatooine_prison_break.iff";
    public static final String BASE_PATH = "poi." + SCENARIO_NAME;
    public static final String SCRIPT_ANTAGONIST = BASE_PATH + ".antagonist";
    public static final String SCRIPT_MEDIATOR = BASE_PATH + ".mediator";
    public static final String SCRIPT_PRISONER = BASE_PATH + ".prisoner";
    public static final String SCRIPT_CAPTOR = BASE_PATH + ".captor";
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
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
    public static final int mediatorCoordsX[] = 
    {
        -13,
        -16,
        -5,
        3
    };
    public static final int mediatorCoordsZ[] = 
    {
        0,
        -20,
        14,
        -18
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
                    LOG(LOG_NAME, "Child found:  Name = " + childname);
                    if (childname.equals("battlefield:barbed_wall"))
                    {
                        if (j == 0)
                        {
                            String convo = getStringObjVar(self, scenario.VAR_SCENARIO_CONVO);
                            if (convo.equals(""))
                            {
                                return;
                            }
                            attachScript(child, "poi.prisonbreak.bombed_wall");
                            location loc = getLocation(child);
                            setObjVar(self, "weakwallLoc", loc);
                            setObjVar(self, "weakwall", child);
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
        loc.x += 5;
        obj_id antagonist = scenario.createTeamNpc(self, "antagonist", type, ident, loc);
        if ((antagonist == null) || (antagonist == obj_id.NULL_ID))
        {
            return false;
        }
        poi.setTitle(antagonist, "a_title");
        attachScript(antagonist, SCRIPT_ANTAGONIST);
        attachScript(antagonist, SCRIPT_PRISONER);
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
                float xdelta = rand(2f, 5f);
                if (i % 2 == 1)
                {
                    xdelta *= -1f;
                }
                float zdelta = rand(2f, 5f);
                if (i % 2 == 0)
                {
                    zdelta *= -1f;
                }
                location loiterloc = new location(baseloc);
                loiterloc.x += xdelta - 2;
                loiterloc.z += zdelta - 2;
                obj_id antagonist = scenario.createTeamNpc(self, "antagonist", type, ident, loiterloc);
                if ((antagonist == null) || (antagonist == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create antagonist minion.");
                }
                else 
                {
                    setYaw(antagonist, rand(0, 359));
                    attachScript(antagonist, SCRIPT_ANTAGONIST);
                    attachScript(antagonist, SCRIPT_PRISONER);
                    ai_lib.setDefaultCalmBehavior(antagonist, ai_lib.BEHAVIOR_SENTINEL);
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
        loc.x += 1;
        loc.z += 20;
        obj_id mediator = scenario.createTeamNpc(self, "mediator", type, ident, loc);
        if ((mediator == null) || (mediator == obj_id.NULL_ID))
        {
            return false;
        }
        poi.setTitle(mediator, "m_title");
        attachScript(mediator, SCRIPT_MEDIATOR);
        attachScript(mediator, SCRIPT_CAPTOR);
        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
        return true;
    }
    public boolean createMediatorMinions(obj_id self) throws InterruptedException
    {
        location baseloc = getLocation(self);
        String type = getStringObjVar(self, MEDIATOR_TYPE);
        if (!type.equals(""))
        {
            for (int i = 0; i < 4; i++)
            {
                String ident = "mediator_" + (i + 1);
                location loiterloc = new location(baseloc);
                loiterloc.x += mediatorCoordsX[i];
                loiterloc.z += mediatorCoordsZ[i];
                obj_id mediator = scenario.createTeamNpc(self, "mediator", type, ident, loiterloc);
                if ((mediator == null) || (mediator == obj_id.NULL_ID))
                {
                    LOG(LOG_NAME, "Couldn't create mediator minion.");
                }
                else 
                {
                    setYaw(mediator, rand(0, 359));
                    attachScript(mediator, SCRIPT_MEDIATOR);
                    attachScript(mediator, SCRIPT_CAPTOR);
                    ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
                    ai_lib.setLoiterRanges(mediator, 0, 3);
                    if (i == 0)
                    {
                        poi.setTitle(mediator, "m_guard_title");
                        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
                        setObjVar(mediator, "guard", true);
                    }
                    else 
                    {
                        ai_lib.setDefaultCalmBehavior(mediator, ai_lib.BEHAVIOR_SENTINEL);
                        ai_lib.setLoiterRanges(mediator, 0, 3);
                    }
                }
            }
        }
        return true;
    }
    public int startAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id guard = poi.findObject("mediator_1");
        if ((guard == null) || (guard == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(guard, "goPee", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int guardGone(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("antagonist_0");
        if (!isIdValid(m))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(m, "attackWallLeader", null, 0, false);
        stop(m);
        obj_id a = poi.findObject("antagonist_1");
        if (!isIdValid(a))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(a, "bomber", true);
        setObjVar(a, "POIbomber", true);
        setObjVar(self, "bombPlan", true);
        location bombloc = getLocationObjVar(self, "weakwallLoc");
        if (bombloc == null)
        {
            return SCRIPT_CONTINUE;
        }
        bombloc.z += -1.5f;
        detachScript(a, SCRIPT_CONVERSE);
        setMovementRun(a);
        ai_lib.aiPathTo(a, bombloc);
        return SCRIPT_CONTINUE;
    }
    public int blowBomb(obj_id self, dictionary params) throws InterruptedException
    {
        location bombloc = getLocationObjVar(self, "weakwallLoc");
        bombloc.z += -1.5;
        obj_id players[] = getPlayerCreaturesInRange(bombloc, 40);
        for (int i = 0; i < players.length; i++)
        {
            if ((players[i] != null) && (players[i] != obj_id.NULL_ID))
            {
                playClientEffectLoc(players[i], "clienteffect/combat_grenade_large_01.cef", bombloc, 0);
            }
        }
        messageTo(self, "destroyBombedWall", null, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
    public int blowDroppedBomb(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(poi.getBaseObject(self), "playerSabotage", true);
        location bombloc = getLocationObjVar(self, "weakwallLoc");
        bombloc.x += 5;
        obj_id players[] = getPlayerCreaturesInRange(bombloc, 40);
        for (int i = 0; i < players.length; i++)
        {
            if ((players[i] != null) && (players[i] != obj_id.NULL_ID))
            {
                playClientEffectLoc(players[i], "clienteffect/combat_grenade_large_01.cef", bombloc, 0);
            }
        }
        messageTo(self, "destroyBombedWallDeath", null, 0.25f, false);
        return SCRIPT_CONTINUE;
    }
    public int destroyBombedWall(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bombedWall = getBombedWall(self);
        if ((bombedWall == null) || (bombedWall == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(bombedWall);
        obj_id m = poi.findObject("antagonist_0");
        if ((m == null) || (m == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(m, "attackEveryone", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int destroyBombedWallDeath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bombedWall = getBombedWall(self);
        if ((bombedWall == null) || (bombedWall == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(bombedWall);
        obj_id[] antagonists = scenario.getTeamMembers(self, "antagonist");
        if (antagonists == null)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < antagonists.length; i++)
        {
            obj_id found = antagonists[i];
            if ((found == null) || (found == obj_id.NULL_ID))
            {
                continue;
            }
            if (isIncapacitated(found) || isDead(found))
            {
                continue;
            }
            if (i % 2 == 1)
            {
                setHealth(found, -50);
            }
            else 
            {
                setHealth(found, getMaxHealth(found) / 3);
            }
        }
        obj_id m = poi.findObject("antagonist_0");
        if ((m == null) || (m == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary parms = new dictionary();
        parms.put("idiot", true);
        messageTo(m, "attackEveryone", parms, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleActorDeath(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id actor = params.getObjId(scenario.DICT_OBJID);
        String name = params.getString(scenario.DICT_NAME);
        String type = params.getString("type");
        String deadlist;
        if (type.equals("antagonist"))
        {
            deadlist = scenario.VAR_SCENARIO_DEAD_ANTAGONIST;
        }
        else 
        {
            deadlist = scenario.VAR_SCENARIO_DEAD_MEDIATOR;
        }
        Vector dead = getResizeableStringArrayObjVar(self, deadlist);
        dead = utils.addElement(dead, name);
        if (dead.size() > 0)
        {
            setObjVar(self, deadlist, dead);
        }
        else 
        {
            if (hasObjVar(self, deadlist))
            {
                removeObjVar(self, deadlist);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getBombedWall(obj_id self) throws InterruptedException
    {
        obj_id myTheater = getObjIdObjVar(self, "theater");
        if ((myTheater == null) || (myTheater == obj_id.NULL_ID))
        {
            return null;
        }
        obj_id[] children = getObjIdArrayObjVar(myTheater, theater.VAR_CHILDREN);
        if ((children == null) || (children.length == 0))
        {
            return null;
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
                    if (childname.equals("battlefield:barbed_wall"))
                    {
                        if (j == 0)
                        {
                            return child;
                        }
                        j++;
                    }
                }
            }
        }
        return null;
    }
    public int enableBombedWall(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id weakwall = getObjIdObjVar(self, "weakwall");
        if ((weakwall == null) || (weakwall == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(weakwall, "enableBombedWall", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int wallDamaged(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bomber = poi.findObject("antagonist_1");
        if ((bomber == null) || (bomber == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(bomber, "wallDamaged", params, 0, false);
        return SCRIPT_OVERRIDE;
    }
}
