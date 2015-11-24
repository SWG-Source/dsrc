package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_create;
import script.library.space_transition;
import script.library.space_utils;
import script.library.utils;

public class load_test extends script.base_script
{
    public load_test()
    {
    }
    public static final String VAR_AI_SHIPS = "load_test.aiShips";
    public static final String[] REBEL_PILOT = 
    {
        "pilot_rebel_navy",
        "pilot_rebel_navy_novice",
        "pilot_rebel_navy_starships_01",
        "pilot_rebel_navy_weapons_01",
        "pilot_rebel_navy_procedures_01",
        "pilot_rebel_navy_droid_01",
        "pilot_rebel_navy_starships_02",
        "pilot_rebel_navy_weapons_02",
        "pilot_rebel_navy_procedures_02",
        "pilot_rebel_navy_droid_02",
        "pilot_rebel_navy_weapons_03",
        "pilot_rebel_navy_starships_03",
        "pilot_rebel_navy_procedures_03",
        "pilot_rebel_navy_droid_03",
        "pilot_rebel_navy_starships_04",
        "pilot_rebel_navy_weapons_04",
        "pilot_rebel_navy_procedures_04",
        "pilot_rebel_navy_droid_04",
        "pilot_rebel_navy_master"
    };
    public static final String[] IMPERIAL_PILOT = 
    {
        "pilot_imperial_navy",
        "pilot_imperial_navy_novice",
        "pilot_imperial_navy_starships_01",
        "pilot_imperial_navy_weapons_01",
        "pilot_imperial_navy_procedures_01",
        "pilot_imperial_navy_droid_01",
        "pilot_imperial_navy_starships_02",
        "pilot_imperial_navy_weapons_02",
        "pilot_imperial_navy_procedures_02",
        "pilot_imperial_navy_droid_02",
        "pilot_imperial_navy_weapons_03",
        "pilot_imperial_navy_starships_03",
        "pilot_imperial_navy_procedures_03",
        "pilot_imperial_navy_droid_03",
        "pilot_imperial_navy_starships_04",
        "pilot_imperial_navy_weapons_04",
        "pilot_imperial_navy_procedures_04",
        "pilot_imperial_navy_droid_04",
        "pilot_imperial_navy_master"
    };
    public static final String[] NEUTRAL_PILOT = 
    {
        "pilot_neutral",
        "pilot_neutral_novice",
        "pilot_neutral_starships_01",
        "pilot_neutral_weapons_01",
        "pilot_neutral_procedures_01",
        "pilot_neutral_droid_01",
        "pilot_neutral_starships_02",
        "pilot_neutral_weapons_02",
        "pilot_neutral_procedures_02",
        "pilot_neutral_droid_02",
        "pilot_neutral_weapons_03",
        "pilot_neutral_starships_03",
        "pilot_neutral_procedures_03",
        "pilot_neutral_droid_03",
        "pilot_neutral_starships_04",
        "pilot_neutral_weapons_04",
        "pilot_neutral_procedures_04",
        "pilot_neutral_droid_04",
        "pilot_neutral_master"
    };
    public static boolean loadClientSupportEnabled() throws InterruptedException
    {
        String value = getConfigSetting("GameServer", "loadClientSupportEnabled");
        if (value != null && (value.equals("1") || value.equals("true") || value.equals("yes")))
        {
            return true;
        }
        return false;
    }
    public static void handleLoadClientSetup(obj_id clientCreature, String optionsString) throws InterruptedException
    {
        String shipName = "xwing";
        String spaceSceneName = "space_tatooine";
        int aiShipCount = 0;
        String aiShipName = "xwing";
        boolean battlefield = false;
        int team = rand(0, 1);
        if (team == 0)
        {
            team = (370444368);
        }
        else 
        {
            team = (-615855020);
        }
        String[] options = split(optionsString, ' ');
        if (options != null)
        {
            for (int i = 1; i < options.length; ++i)
            {
                String[] optionBits = split(options[i], '=');
                if (optionBits != null)
                {
                    if (optionBits.length == 2 && optionBits[0].equalsIgnoreCase("shipName"))
                    {
                        shipName = optionBits[1];
                    }
                    else if (optionBits.length == 2 && optionBits[0].equalsIgnoreCase("spaceSceneName"))
                    {
                        spaceSceneName = optionBits[1];
                    }
                    else if (optionBits.length == 2 && optionBits[0].equalsIgnoreCase("aiShipCount"))
                    {
                        aiShipCount = utils.stringToInt(optionBits[1]);
                    }
                    else if (optionBits.length == 2 && optionBits[0].equalsIgnoreCase("aiShipName"))
                    {
                        aiShipName = optionBits[1];
                    }
                    else if (optionBits.length == 1 && optionBits[0].equalsIgnoreCase("battlefield"))
                    {
                        battlefield = true;
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(clientCreature, "loadClientSetup: Bad option [" + options[i] + "] specified.");
                        return;
                    }
                }
            }
        }
        if (battlefield)
        {
            if (team == (370444368))
            {
                shipName = "xwing";
            }
            else 
            {
                shipName = "tieinterceptor";
            }
            aiShipCount = 0;
            spaceSceneName = "space_heavy1";
        }
        if (isSpaceScene())
        {
            if (!isIdValid(space_transition.getContainingShip(clientCreature)))
            {
                space_utils.destroyShipControlDevices(clientCreature, false);
                space_utils.createShipControlDevice(clientCreature, shipName, false);
            }
            obj_id clientShip = space_transition.getContainingShip(clientCreature);
            if (isIdValid(clientShip) && aiShipCount > 0)
            {
                if (!hasScript(clientShip, "space.load_test.load_test_player"))
                {
                    attachScript(clientShip, "space.load_test.load_test_player");
                }
                obj_id[] aiShips = new obj_id[aiShipCount];
                obj_id lastShip = clientShip;
                for (int i = 0; i < aiShipCount; ++i)
                {
                    obj_id aiShip = space_create.createShip(aiShipName, (getTransform_o2p(lastShip)).move_l(new vector(0.f, 0.f, -15.f)));
                    if (isIdValid(aiShip))
                    {
                        utils.setScriptVar(aiShip, "followTarget", lastShip);
                        attachScript(aiShip, "space.load_test.load_test_ai");
                        lastShip = aiShip;
                        aiShips[i] = aiShip;
                    }
                }
                dictionary d = clientShip.getScriptDictionary();
                d.put(VAR_AI_SHIPS, aiShips);
            }
        }
        else 
        {
            space_utils.destroyShipControlDevices(clientCreature, false);
            obj_id shipControlDevice = space_utils.createShipControlDevice(clientCreature, shipName, false);
            if (isIdValid(shipControlDevice))
            {
                obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevice);
                space_transition.setLaunchInfo(clientCreature, ship, 0, getLocation(getTopMostContainer(clientCreature)));
                if (battlefield)
                {
                    setObjVar(ship, "intBattlefieldTeam", team);
                    obj_id w = createWaypointInDatapad(clientCreature, new location(-6000.f, 0.f, 0.f, "space_heavy1"));
                    setWaypointName(w, "Rebel Base");
                    setWaypointActive(w, true);
                    w = createWaypointInDatapad(clientCreature, new location(6000.f, 0.f, 0.f, "space_heavy1"));
                    setWaypointActive(w, true);
                    setWaypointName(w, "Imperial Base");
                    if (team == (370444368))
                    {
                        setRebelSkills(clientCreature);
                        float x = -5000.f + rand(-100, 100);
                        float y = 0.f + rand(-100, 100);
                        float z = 0.f + rand(-100, 100);
                        warpPlayer(clientCreature, spaceSceneName, x, y, z, null, x, y, z);
                    }
                    else 
                    {
                        setImperialSkills(clientCreature);
                        float x = 6000.f + rand(-100, 100);
                        float y = -1200.f + rand(-100, 100);
                        float z = 600.f + rand(-100, 100);
                        warpPlayer(clientCreature, spaceSceneName, x, y, z, null, x, y, z);
                    }
                }
                else 
                {
                    warpPlayer(clientCreature, spaceSceneName, 10.f, 0.f, 10.f, null, 10.f, 0.f, 10.f);
                }
            }
        }
    }
    public static void setRebelSkills(obj_id creature) throws InterruptedException
    {
        revokeSkills(creature, IMPERIAL_PILOT);
        revokeSkills(creature, NEUTRAL_PILOT);
        grantSkills(creature, REBEL_PILOT);
    }
    public static void setImperialSkills(obj_id creature) throws InterruptedException
    {
        revokeSkills(creature, REBEL_PILOT);
        revokeSkills(creature, NEUTRAL_PILOT);
        grantSkills(creature, IMPERIAL_PILOT);
    }
    public static void grantSkills(obj_id creature, String[] skills) throws InterruptedException
    {
        for (int i = 0; i < skills.length; ++i)
        {
            grantSkill(creature, skills[i]);
        }
    }
    public static void revokeSkills(obj_id creature, String[] skills) throws InterruptedException
    {
        for (int i = skills.length - 1; i >= 0; --i)
        {
            revokeSkill(creature, skills[i]);
        }
    }
}
