package script.theme_park.alderaan.act2;

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
import script.library.factions;
import script.library.badge;
import script.library.chat;

public class rebel_drall_camp extends script.base_script
{
    public rebel_drall_camp()
    {
    }
    public static final String IMPERIAL_SHARED_STF = "theme_park/alderaan/act2/shared_imperial_missions";
    public static final string_id MISSION_COMPLETE = new string_id(IMPERIAL_SHARED_STF, "mission_complete");
    public static final String[] TEMPLATE_LIST = 
    {
        "object/building/poi/generic_flatten_large.iff",
        "object/static/vehicle/player_shuttle.iff",
        "object/static/structure/corellia/corl_tent_large.iff",
        "object/static/structure/corellia/corl_tent_large.iff",
        "object/static/structure/general/streetlamp_medium_style_01.iff",
        "object/static/structure/general/streetlamp_medium_style_01.iff",
        "object/static/structure/general/streetlamp_medium_style_01.iff",
        "object/static/structure/general/streetlamp_medium_style_01.iff",
        "object/static/structure/general/streetlamp_medium_style_01.iff",
        "coa2_drall_guard",
        "coa2_drall_guard",
        "coa2_drall_guard",
        "coa2_drall_guard",
        "coa2_drall_guard",
        "coa2_drall_scientist",
        "coa2_drall_scientist",
        "coa2_drall_scientist",
        "coa2_drall_scientist",
        "coa2_drall_scientist"
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
            0.00f,
            0.0f,
            -2.40f,
            0.0f
        },
        
        {
            11.21f,
            0.0f,
            13.97f,
            -39.53f
        },
        
        {
            14.00f,
            0.0f,
            -12.59f,
            0.0f
        },
        
        {
            12.00f,
            0.0f,
            0.43f,
            0.0f
        },
        
        {
            2.30f,
            0.0f,
            12.10f,
            -33.80f
        },
        
        {
            2.83f,
            0.0f,
            -14.45f,
            48.13f
        },
        
        {
            21.37f,
            0.0f,
            -10.84f,
            106.57f
        },
        
        {
            16.32f,
            0.0f,
            18.79f,
            54.43f
        },
        
        {
            -1.13f,
            0.0f,
            -18.25f,
            0.0f
        },
        
        {
            22.67f,
            0.0f,
            -6.85f,
            0.0f
        },
        
        {
            21.41f,
            0.0f,
            20.33f,
            0.0f
        },
        
        {
            -2.83f,
            0.0f,
            -21.80f,
            0.0f
        },
        
        {
            7.19f,
            0.0f,
            0.43f,
            0.0f
        },
        
        {
            12.91f,
            0.0f,
            -9.87f,
            0.0f
        },
        
        {
            15.62f,
            0.0f,
            0.60f,
            0.0f
        },
        
        {
            17.74f,
            0.0f,
            -14.46f,
            0.0f
        },
        
        {
            7.67f,
            0.0f,
            12.16f,
            0.0f
        },
        
        {
            15.14f,
            0.0f,
            12.90f,
            0.0f
        }
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("index", 0);
        messageTo(self, "spawnNextObject", params, 2, false);
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
            setObjVar(newObject, "coa2.imperial.drall_camp", self);
            Vector objectList = getResizeableObjIdArrayObjVar(self, "coa2.imperial.obj_list");
            if (objectList == null)
            {
                objectList = new Vector();
            }
            utils.addElement(objectList, newObject);
            if (objectList.size() > 0)
            {
                setObjVar(self, "coa2.imperial.obj_list", objectList);
            }
            if (ai_lib.isNpc(newObject))
            {
                int count = utils.getIntScriptVar(self, "coa2.imperial.guards");
                count++;
                utils.setScriptVar(self, "coa2.imperial.guards", count);
                ai_lib.setDefaultCalmBehavior(newObject, ai_lib.BEHAVIOR_LOITER);
                ai_lib.setLoiterRanges(newObject, 10f, 48f);
                if (count > 5)
                {
                    String name = getName(newObject);
                    setName(newObject, name + " (a Drall scientist)");
                    ai_lib.setDefaultCalmBehavior(newObject, ai_lib.BEHAVIOR_SENTINEL);
                }
            }
        }
        objectNum++;
        if (objectNum < TEMPLATE_LIST.length)
        {
            params.put("index", objectNum);
            messageTo(self, "spawnNextObject", params, 2, false);
        }
        else 
        {
            initCamp();
        }
        return SCRIPT_CONTINUE;
    }
    public void initCamp() throws InterruptedException
    {
        obj_id self = getSelf();
        setObjVar(self, "coa2.imperial.numGuards", 10);
    }
    public int guardKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int numGuards = getIntObjVar(self, "coa2.imperial.numGuards");
        numGuards--;
        setObjVar(self, "coa2.imperial.numGuards", numGuards);
        if (numGuards == 0)
        {
            obj_id player = getObjIdObjVar(self, "coa2.imperial.playerId");
            if (isIdValid(player))
            {
                setObjVar(player, "coa2.progress", -10);
                factions.awardFactionStanding(player, "Imperial", 500);
                sendSystemMessage(player, MISSION_COMPLETE);
                badge.grantBadge(player, "event_coa2_imperial");
                playMusic(player, "sound/music_darth_vader_theme.snd");
                removeObjVar(player, "coa2.imperial");
            }
            messageTo(self, "cleanup", null, 15 * 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        Vector objectList = getResizeableObjIdArrayObjVar(self, "coa2.imperial.obj_list");
        for (int i = 0; i < objectList.size(); i++)
        {
            if (isIdValid(((obj_id)objectList.get(i))))
            {
                destroyObject(((obj_id)objectList.get(i)));
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
