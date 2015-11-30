package script.event.ep3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import java.util.StringTokenizer;

public class battle extends script.base_script
{
    public battle()
    {
    }
    public static final String[] VICTIM1 = 
    {
        "ep3_npc_wookiee_battleleader",
        "ep3_npc_wookiee_commando",
        "ep3_npc_wookiee_forest_stalker",
        "ep3_npc_wookiee_freedom_fighters"
    };
    public static final String[] AGGRESSOR1 = 
    {
        "ep3_slaver_blackscale_assault",
        "ep3_slaver_blackscale_guard",
        "ep3_slaver_blackscale_trooper"
    };
    public static final String[] VICTIM2 = 
    {
        "ep3_hracca_kkorrwrot",
        "ep3_hracca_kkorrwrot"
    };
    public static final String[] AGGRESSOR2 = 
    {
        "stormtrooper",
        "stormtrooper"
    };
    public static final String[] VICTIM3 = 
    {
        "ep3_rryatt_katarn",
        "ep3_rryatt_katarn"
    };
    public static final String[] AGGRESSOR3 = 
    {
        "stormtrooper",
        "stormtrooper"
    };
    public static final String[] VICTIM4 = 
    {
        "ep3_etyyy_mouf",
        "ep3_etyyy_mouf"
    };
    public static final String[] AGGRESSOR4 = 
    {
        "stormtrooper",
        "stormtrooper"
    };
    public static final String[] VICTIM5 = 
    {
        "ep3_etyyy_walluga",
        "ep3_etyyy_walluga"
    };
    public static final String[] AGGRESSOR5 = 
    {
        "stormtrooper",
        "stormtrooper"
    };
    public static final String[] VICTIM6 = 
    {
        "ep3_kachirho_uller",
        "ep3_kachirho_uller"
    };
    public static final String[] AGGRESSOR6 = 
    {
        "stormtrooper",
        "stormtrooper"
    };
    public static final String[] VICTIM7 = 
    {
        "ep3_etyyy_webweaver",
        "ep3_etyyy_webweaver"
    };
    public static final String[] AGGRESSOR7 = 
    {
        "stormtrooper",
        "stormtrooper"
    };
    public static final String[] VICTIM8 = 
    {
        "ep3_urnsoris_worker",
        "ep3_urnsoris_worker"
    };
    public static final String[] AGGRESSOR8 = 
    {
        "stormtrooper",
        "stormtrooper"
    };
    public static final String[] VICTIM9 = 
    {
        "ep3_rryatt_minstyngar_breeder",
        "ep3_rryatt_minstyngar_breeder"
    };
    public static final String[] AGGRESSOR9 = 
    {
        "stormtrooper",
        "stormtrooper"
    };
    public static final String[] VICTIM10 = 
    {
        "ep3_hracca_kkorrwrot",
        "ep3_rryatt_katarn",
        "ep3_etyyy_mouf",
        "ep3_etyyy_walluga",
        "ep3_kachirho_uller",
        "ep3_kachirho_uller",
        "ep3_etyyy_webweaver",
        "ep3_urnsoris_worker",
        "ep3_rryatt_minstyngar_breeder"
    };
    public static final String[] AGGRESSOR10 = 
    {
        "stormtrooper",
        "stormtrooper"
    };
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isGod(self))
        {
            sendSystemMessage(self, "You need to activate God Mode to start battles.", null);
            return SCRIPT_CONTINUE;
        }
        if ((toLower(strText)).startsWith("battle"))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                if (utils.hasScriptVar(self, "ep3demoSanityChecker"))
                {
                    sendSystemMessage(self, "You just started a battle. If you really want to start another wait a couple seconds and try again.", null);
                }
                String command = st.nextToken();
                String battleIndexStr = st.nextToken();
                int battleIndex = utils.stringToInt(battleIndexStr);
                if (battleIndex < 1 || battleIndex > 10)
                {
                    sendSystemMessage(self, "Battle index out of range. Valid ranges are between 1 and 2.", null);
                    return SCRIPT_CONTINUE;
                }
                startBattle(self, battleIndex);
                utils.setScriptVar(self, "ep3demoSanityChecker", 1);
                messageTo(self, "allowMoreBattles", null, 10, false);
                sendSystemMessage(self, "Battle commencing. Say stop battle at this location to end the battle.", null);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int allowMoreBattles(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "ep3demoSanityChecker");
        return SCRIPT_CONTINUE;
    }
    public void startBattle(obj_id self, int battleIndex) throws InterruptedException
    {
        float timeStamp = getGameTime();
        location here = getLocation(self);
        obj_id battleSpawner = create.object("object/tangible/poi/base/poi_base.iff", here);
        if (battleIndex == 1)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR1);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM1);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 4);
        }
        if (battleIndex == 2)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR2);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM2);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 1);
        }
        if (battleIndex == 3)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR3);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM3);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 1);
        }
        if (battleIndex == 4)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR4);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM4);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 1);
        }
        if (battleIndex == 5)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR5);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM5);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 1);
        }
        if (battleIndex == 6)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR6);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM6);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 1);
        }
        if (battleIndex == 7)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR7);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM7);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 1);
        }
        if (battleIndex == 8)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR8);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM8);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 1);
        }
        if (battleIndex == 9)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR9);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM9);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 1);
        }
        if (battleIndex == 10)
        {
            setObjVar(battleSpawner, "ep3demo.aggressorTemplate", AGGRESSOR10);
            setObjVar(battleSpawner, "ep3demo.victimTemplate", VICTIM10);
            setObjVar(battleSpawner, "ep3demo.aggressorNumber", 4);
            setObjVar(battleSpawner, "ep3demo.victimNumber", 1);
        }
        setObjVar(battleSpawner, "ep3demo.timeStamp", timeStamp);
        attachScript(battleSpawner, "event.ep3demo.battle_spawner");
        return;
    }
}
