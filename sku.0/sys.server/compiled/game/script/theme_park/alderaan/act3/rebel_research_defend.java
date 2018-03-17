package script.theme_park.alderaan.act3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.utils;
import script.library.chat;
import script.library.factions;
import script.library.locations;
import script.library.badge;
import script.ai.ai_combat;

public class rebel_research_defend extends script.base_script
{
    public rebel_research_defend()
    {
    }
    public static final String[] TEMPLATE_LIST = 
    {
        "object/building/poi/generic_flatten_medium.iff",
        "object/tangible/furniture/imperial/data_terminal_s1.iff",
        "coa3_rebel_research_captain",
        "coa3_rebel_research_captain",
        "coa3_rebel_research_guard",
        "coa3_rebel_research_guard",
        "coa3_rebel_research_guard",
        "coa3_rebel_research_guard",
        "coa3_rebel_research_guard",
        "coa3_rebel_research_guard"
    };
    public static final float[][] LOCATION_LIST = 
    {
        
        {
            0.00f,
            0.0f,
            0.0f,
            0.0f
        },
        
        {
            -8.00f,
            0.0f,
            3.40f,
            -90.0f
        },
        
        {
            -10.00f,
            0.0f,
            3.00f,
            -90.0f
        },
        
        {
            -10.00f,
            0.0f,
            -3.00f,
            -90.0f
        },
        
        {
            -15.00f,
            0.0f,
            10.00f,
            -90.0f
        },
        
        {
            -15.00f,
            0.0f,
            -10.00f,
            -90.0f
        },
        
        {
            12.00f,
            0.0f,
            15.00f,
            45.0f
        },
        
        {
            12.50f,
            0.0f,
            -15.00f,
            135.0f
        },
        
        {
            -7.00f,
            0.0f,
            20.00f,
            -45.0f
        },
        
        {
            -7.00f,
            0.0f,
            -20.00f,
            -135.0f
        }
    };
    public static final String REBEL_SHARED_STF = "theme_park/alderaan/act3/shared_rebel_missions";
    public static final string_id MISSION_COMPLETE = new string_id(REBEL_SHARED_STF, "mission_complete");
    public static final string_id MISSION_FAILED = new string_id(REBEL_SHARED_STF, "mission_failed");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("index", 0);
        messageTo(self, "spawnNextObject", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnNextObject(obj_id self, dictionary params) throws InterruptedException
    {
        int objectNum = params.getInt("index");
        location loc = getLocation(self);
        loc.x += LOCATION_LIST[objectNum][0];
        loc.y += LOCATION_LIST[objectNum][1];
        loc.z += LOCATION_LIST[objectNum][2];
        obj_id newObject = create.object(TEMPLATE_LIST[objectNum], loc);
        if (isIdValid(newObject))
        {
            setYaw(newObject, LOCATION_LIST[objectNum][3]);
            setObjVar(newObject, "coa3.rebel.facility", self);
            Vector objectList = getResizeableObjIdArrayObjVar(self, "coa3.rebel.obj_list");
            if (objectList == null)
            {
                objectList = new Vector();
            }
            utils.addElement(objectList, newObject);
            if (objectList.size() > 0)
            {
                setObjVar(self, "coa3.rebel.obj_list", objectList);
            }
            if (ai_lib.isNpc(newObject))
            {
                int count = utils.getIntScriptVar(self, "coa3.rebel.guards");
                count++;
                utils.setScriptVar(self, "coa3.rebel.guards", count);
                obj_id player = getObjIdObjVar(self, "coa3.rebel.playerId");
                setObjVar(newObject, "coa3.rebel.playerId", player);
                if (count < 5)
                {
                    ai_lib.setDefaultCalmBehavior(newObject, ai_lib.BEHAVIOR_SENTINEL);
                }
            }
        }
        objectNum++;
        if (objectNum < TEMPLATE_LIST.length)
        {
            params.put("index", objectNum);
            messageTo(self, "spawnNextObject", params, 0, false);
        }
        else 
        {
            initFacility();
        }
        return SCRIPT_CONTINUE;
    }
    public void initFacility() throws InterruptedException
    {
        obj_id self = getSelf();
        setObjVar(self, "coa3.rebel.numGuards", 8);
        int waitTime = rand(60, 90);
        messageTo(self, "assaultWave", null, waitTime, false);
    }
    public int guardKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int numGuards = getIntObjVar(self, "coa3.rebel.numGuards");
        numGuards--;
        setObjVar(self, "coa3.rebel.numGuards", numGuards);
        if (getIntObjVar(self, "coa3.rebel.success") == 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (numGuards == 0)
        {
            obj_id player = getObjIdObjVar(self, "coa3.rebel.playerId");
            sendSystemMessage(player, MISSION_FAILED);
            setObjVar(self, "coa3.rebel.failure", 1);
            explodeFacility(self, player);
            messageTo(self, "cleanup", params, 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void explodeFacility(obj_id self, obj_id player) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectLoc(player, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        death.x = death.x + 5;
        playClientEffectLoc(player, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        death.z = death.z + 5;
        playClientEffectLoc(player, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        death.x = death.x - 10;
        playClientEffectLoc(player, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
        death.z = death.z - 10;
        playClientEffectLoc(player, "clienteffect/combat_explosion_lair_large.cef", death, 10f);
    }
    public int cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        Vector objectList = getResizeableObjIdArrayObjVar(self, "coa3.rebel.obj_list");
        for (int i = 0; i < objectList.size(); i++)
        {
            if (isIdValid(((obj_id)objectList.get(i))))
            {
                destroyObject(((obj_id)objectList.get(i)));
            }
        }
        Vector enemyList = getResizeableObjIdArrayObjVar(self, "coa3.rebel.enemyList");
        if (enemyList != null)
        {
            for (int i = 0; i < enemyList.size(); i++)
            {
                if (isIdValid(((obj_id)enemyList.get(i))))
                {
                    destroyObject(((obj_id)enemyList.get(i)));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int assaultWave(obj_id self, dictionary params) throws InterruptedException
    {
        if (getIntObjVar(self, "coa3.rebel.failure") == 1)
        {
            return SCRIPT_CONTINUE;
        }
        int waveNum = getIntObjVar(self, "coa3,rebel.waveNum");
        Vector enemyList = getResizeableObjIdArrayObjVar(self, "coa3.rebel.enemyList");
        if (enemyList == null)
        {
            enemyList = new Vector();
        }
        spawnNewWave(self, enemyList, waveNum);
        setObjVar(self, "coa3,rebel.waveNum", ++waveNum);
        if (waveNum < 3)
        {
            int waitTime = rand(5, 8) * 60;
            messageTo(self, "assaultWave", null, waitTime, false);
        }
        else 
        {
            messageTo(self, "handleVictory", null, 15 * 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int attackerKilled(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id attacker = params.getObjId("attacker");
        Vector enemyList = getResizeableObjIdArrayObjVar(self, "coa3.rebel.enemyList");
        if (enemyList != null)
        {
            enemyList = utils.removeElement(enemyList, attacker);
            if (enemyList.size() > 0)
            {
                setObjVar(self, "coa3.rebel.enemyList", enemyList);
            }
            else 
            {
                removeObjVar(self, "coa3.rebel.enemyList");
                int waveNum = getIntObjVar(self, "coa3,rebel.waveNum");
                if (waveNum >= 3)
                {
                    messageTo(self, "handleVictory", params, 120, false);
                }
            }
        }
        else 
        {
            int waveNum = getIntObjVar(self, "coa3,rebel.waveNum");
            if (waveNum >= 3)
            {
                messageTo(self, "handleVictory", params, 120, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleVictory(obj_id self, dictionary params) throws InterruptedException
    {
        if (getIntObjVar(self, "coa3.rebel.failure") == 1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, "coa3.rebel.playerId");
        setObjVar(self, "coa3.rebel.success", 1);
        setObjVar(player, "coa3.rebel.finalMission", 1);
        CustomerServiceLog("Badge", getName(player) + "(" + player + ") has recieved badge event_coa3_rebel");
        factions.awardFactionStanding(player, "Rebel", 750);
        sendSystemMessage(player, MISSION_COMPLETE);
        badge.grantBadge(player, "event_coa3_rebel");
        playMusic(player, "sound/music_lando_theme.snd");
        obj_id tatooine = getPlanetByName("tatooine");
        int winCount = getIntObjVar(tatooine, "coa3.rebelWinCount");
        winCount++;
        setObjVar(tatooine, "coa3.rebelWinCount", winCount);
        messageTo(player, "createReturnMission", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public void spawnNewWave(obj_id self, Vector enemyList, int waveNum) throws InterruptedException
    {
        int enemyWeakCount[] = 
        {
            3,
            4,
            5
        };
        int enemyStrongCount[] = 
        {
            1,
            2,
            3
        };
        obj_id player = getObjIdObjVar(self, "coa3.rebel.playerId");
        for (int i = 0; i < enemyWeakCount[waveNum]; i++)
        {
            location loc = getSpawnLoc(self);
            if (loc != null)
            {
                obj_id newEnemy = create.object("coa3_rebel_research_attacker1", loc);
                if (isIdValid(newEnemy))
                {
                    setObjVar(newEnemy, "coa3.rebel.facility", self);
                    setObjVar(newEnemy, "coa3.rebel.playerId", player);
                    utils.addElement(enemyList, newEnemy);
                }
            }
        }
        for (int i = 0; i < enemyStrongCount[waveNum]; i++)
        {
            location loc = getSpawnLoc(self);
            if (loc != null)
            {
                obj_id newEnemy = create.object("coa3_rebel_research_attacker2", loc);
                if (isIdValid(newEnemy))
                {
                    setObjVar(newEnemy, "coa3.rebel.facility", self);
                    setObjVar(newEnemy, "coa3.rebel.playerId", player);
                    utils.addElement(enemyList, newEnemy);
                }
            }
        }
        setObjVar(self, "coa3.rebel.enemyList", enemyList);
    }
    public location getSpawnLoc(obj_id self) throws InterruptedException
    {
        location center = getLocation(self);
        location spawnLoc = null;
        int x = 0;
        while (x < 10)
        {
            location loc = utils.getRandomLocationInRing(center, 100, 150);
            spawnLoc = locations.getGoodLocationAroundLocation(loc, 5f, 5f, 50f, 50f);
            if (spawnLoc != null)
            {
                break;
            }
            x += 1;
        }
        return spawnLoc;
    }
}
