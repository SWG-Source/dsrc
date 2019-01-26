package script.theme_park.heroic.echo_base;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

public class echo_controller extends script.base_script
{
    public echo_controller()
    {
    }
    public static final boolean LOGGING = true;
    public int updateCloneLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!params.containsKey("cloneLocation"))
        {
            return SCRIPT_CONTINUE;
        }
        String cloneLoc = params.getString("cloneLocation");
        String[] parse = split(cloneLoc, '|');
        float locx = utils.stringToFloat(parse[0]);
        float locy = utils.stringToFloat(parse[1]);
        float locz = utils.stringToFloat(parse[2]);
        String cellName = parse[3];
        String myTemplate = getTemplateName(self);
        dictionary facilityData = dataTableGetRow(structure.DATATABLE_CLONING_FACILITY_RESPAWN, myTemplate);
        facilityData.put("X", locx);
        facilityData.put("Y", locy);
        facilityData.put("Z", locz);
        facilityData.put("CELL", cellName);
        dictionary dict = new dictionary();
        dict.put("facilityData", facilityData);
        messageTo(self, "registerCloningFacility", dict, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int createClientPath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = utils.getPlayersInBuildoutRow(self);
        obj_id[] itemPathTo = trial.getObjectsInInstanceBySpawnId(self, params.getString("endLocSpawnId"));
        if (itemPathTo == null)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < players.length; ++i)
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int buff_atat(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] atats = trial.getObjectsInInstanceByObjVar(self, "isAtat");
        buff.applyBuff(atats, "hoth_atat_shield");
        return SCRIPT_CONTINUE;
    }
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVarTree(self, "quest_tracker");
        return SCRIPT_CONTINUE;
    }
    public int p1_end(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "quest_tracker.p1_ended", 1);
        return SCRIPT_CONTINUE;
    }
    public int rebel_p1_update(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "quest_tracker.p1_ended"))
        {
            return SCRIPT_CONTINUE;
        }
        String thisUpdate = params.getString("objective");
        utils.setScriptVar(self, "quest_tracker.rebel." + thisUpdate, 1);
        return SCRIPT_CONTINUE;
    }
    public int imperial_p1_update(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "quest_tracker.p1_ended"))
        {
            return SCRIPT_CONTINUE;
        }
        String thisUpdate = params.getString("objective");
        utils.setScriptVar(self, "quest_tracker.imperial." + thisUpdate, 1);
        return SCRIPT_CONTINUE;
    }
    public int p2_end(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "quest_tracker.p2_ended", 1);
        return SCRIPT_CONTINUE;
    }
    public int rebel_p2_update(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "quest_tracker.p2_ended"))
        {
            return SCRIPT_CONTINUE;
        }
        String thisUpdate = params.getString("objective");
        utils.setScriptVar(self, "quest_tracker.rebel." + thisUpdate, 1);
        return SCRIPT_CONTINUE;
    }
    public int imperial_p2_update(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "quest_tracker.p2_ended"))
        {
            return SCRIPT_CONTINUE;
        }
        String thisUpdate = params.getString("objective");
        utils.setScriptVar(self, "quest_tracker.imperial." + thisUpdate, 1);
        return SCRIPT_CONTINUE;
    }
    public int rebel_p3_update(obj_id self, dictionary params) throws InterruptedException
    {
        String thisUpdate = params.getString("objective");
        utils.setScriptVar(self, "quest_tracker.rebel." + thisUpdate, 1);
        return SCRIPT_CONTINUE;
    }
    public int imperial_p3_update(obj_id self, dictionary params) throws InterruptedException
    {
        String thisUpdate = params.getString("objective");
        utils.setScriptVar(self, "quest_tracker.imperial." + thisUpdate, 1);
        return SCRIPT_CONTINUE;
    }
    public int wampa_boss_died(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "quest_tracker.wampa_boss_dead", 1);
        return SCRIPT_CONTINUE;
    }
    public int conclude_rebel_p3(obj_id self, dictionary params) throws InterruptedException
    {
        int tokens = getRebelTokenCountByVictory();
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        String instanceName = "instance-" + instance.getInstanceName(self);
        if (tokens > 0)
        {
            dictionary dict = new dictionary();
            dict.put("tokenIndex", 5);
            dict.put("tokenCount", tokens);
            utils.messageTo(players, "handleAwardtoken", dict, 0, false);
        }
        obj_id group = getGroupObject(players[0]);
        int calendarTime = getCalendarTime();
        String realTime = getCalendarTimeStringLocal(calendarTime);
        CustomerServiceLog(instanceName, "Echo Base Rebel Defeated(" + self + ") by group_id (" + group + ") at " + realTime);
        CustomerServiceLog(instanceName, "Group (" + group + ") consists of: ");
        for (int i = 0; i < players.length; ++i)
        {
            String strProfession = skill.getProfessionName(getSkillTemplate(players[i]));
            CustomerServiceLog(instanceName, "Group (" + group + ") member " + i + " " + getFirstName(players[i]) + "'s(" + players[i] + ") profession is " + strProfession + ".");
        }
        CustomerServiceLog(instanceName, "Players earned " + tokens + " tokens");
        CustomerServiceLog(instanceName, "Objective Destroy 4 AT-AT: " + utils.getIntScriptVar(self, "quest_tracker.rebel.at_minor"));
        CustomerServiceLog(instanceName, "Objective Destroy 6 AT-AT: " + utils.getIntScriptVar(self, "quest_tracker.rebel.at_major"));
        CustomerServiceLog(instanceName, "Objective Destroy Commmand: " + utils.getIntScriptVar(self, "quest_tracker.rebel.command_destroy"));
        CustomerServiceLog(instanceName, "Objective Evacuate Equipment: " + utils.getIntScriptVar(self, "quest_tracker.rebel.equipment"));
        CustomerServiceLog(instanceName, "Objective Evacuate Thermal: " + utils.getIntScriptVar(self, "quest_tracker.rebel.thermal"));
        CustomerServiceLog(instanceName, "Objective Evacuate Medical: " + utils.getIntScriptVar(self, "quest_tracker.rebel.medical"));
        CustomerServiceLog(instanceName, "Objective Command Personel Evacuate: " + utils.getIntScriptVar(self, "quest_tracker.rebel.command_escape"));
        CustomerServiceLog(instanceName, "Objective Non-Essential Personel Evacuate: " + utils.getIntScriptVar(self, "quest_tracker.rebel.nonescape"));
        CustomerServiceLog(instanceName, "Objective Launch 3 Transport: " + utils.getIntScriptVar(self, "quest_tracker.rebel.xport_minor"));
        CustomerServiceLog(instanceName, "Objective Launch 5 Transport: " + utils.getIntScriptVar(self, "quest_tracker.rebel.xport_major"));
        CustomerServiceLog(instanceName, "OPTIONAL Wampa Boss Defeated: " + utils.getIntScriptVar(self, "quest_tracker.wampa_boss_dead"));
        CustomerServiceLog(instanceName, "\n");
        winSuiDisplay(players, "rebel");
        return SCRIPT_CONTINUE;
    }
    public int conclude_imperial_p3(obj_id self, dictionary params) throws InterruptedException
    {
        int tokens = getImperialTokenCountByVictory();
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        String instanceName = "instance-" + instance.getInstanceName(self);
        if (tokens > 0)
        {
            dictionary dict = new dictionary();
            dict.put("tokenIndex", 5);
            dict.put("tokenCount", tokens);
            utils.messageTo(players, "handleAwardtoken", dict, 0, false);
        }
        obj_id group = getGroupObject(players[0]);
        int calendarTime = getCalendarTime();
        String realTime = getCalendarTimeStringLocal(calendarTime);
        CustomerServiceLog(instanceName, "Echo Base Imperial Defeated(" + self + ") by group_id (" + group + ") at " + realTime);
        CustomerServiceLog(instanceName, "Group (" + group + ") consists of: ");
        for (int i = 0; i < players.length; ++i)
        {
            String strProfession = skill.getProfessionName(getSkillTemplate(players[i]));
            CustomerServiceLog(instanceName, "Group (" + group + ") member " + i + " " + getFirstName(players[i]) + "'s(" + players[i] + ") profession is " + strProfession + ".");
        }
        CustomerServiceLog(instanceName, "Players earned " + tokens + " tokens");
        CustomerServiceLog(instanceName, "Objective Lost 1 AT-AT: " + utils.getIntScriptVar(self, "quest_tracker.imperial.fail_major"));
        CustomerServiceLog(instanceName, "Objective Lost 4 AT-AT: " + utils.getIntScriptVar(self, "quest_tracker.imperial.fail_minor"));
        CustomerServiceLog(instanceName, "Objective Captured Commmand: " + utils.getIntScriptVar(self, "quest_tracker.imperial.command_capture"));
        CustomerServiceLog(instanceName, "Objective Captured Hangar: " + utils.getIntScriptVar(self, "quest_tracker.imperial.hangar_capture"));
        CustomerServiceLog(instanceName, "Objective Ion Cap Destroyed: " + utils.getIntScriptVar(self, "quest_tracker.imperial.ion_cap_destroyed"));
        CustomerServiceLog(instanceName, "Objective Food Destroyed: " + utils.getIntScriptVar(self, "quest_tracker.imperial.food_destroy"));
        CustomerServiceLog(instanceName, "Objective Medical Destroyed: " + utils.getIntScriptVar(self, "quest_tracker.imperial.medical_destroy"));
        CustomerServiceLog(instanceName, "Objective Equipment Destroyed: " + utils.getIntScriptVar(self, "quest_tracker.imperial.equipment_destroy"));
        CustomerServiceLog(instanceName, "Objective Destroy 3 Transport: " + utils.getIntScriptVar(self, "quest_tracker.imperial.p3_minor"));
        CustomerServiceLog(instanceName, "Objective Destroy All Transport: " + utils.getIntScriptVar(self, "quest_tracker.imperial.p3_major"));
        CustomerServiceLog(instanceName, "OPTIONAL Wampa Boss Defeated: " + utils.getIntScriptVar(self, "quest_tracker.wampa_boss_dead"));
        CustomerServiceLog(instanceName, "\n");
        winSuiDisplay(players, "imperial");
        return SCRIPT_CONTINUE;
    }
    public void winSuiDisplay(obj_id[] players, String version) throws InterruptedException
    {
        if (players == null || players.length <= 0)
        {
            return;
        }
        if (version == null || version.length() <= 0)
        {
            return;
        }
        obj_id self = getSelf();
        if (version.equals("rebel"))
        {
            int AtatMinor = utils.getIntScriptVar(self, "quest_tracker.rebel.at_minor");
            int AtatMajor = utils.getIntScriptVar(self, "quest_tracker.rebel.at_major");
            boolean p1VictoryMinor = false;
            boolean p1VictoryMajor = false;
            if (AtatMajor != 1)
            {
                if (AtatMinor == 1)
                {
                    p1VictoryMinor = true;
                }
            }
            else 
            {
                p1VictoryMajor = true;
            }
            int p1Tokens = 0;
            if (p1VictoryMajor)
            {
                p1Tokens = 3;
            }
            else if (p1VictoryMinor)
            {
                p1Tokens = 1;
            }
            int winCount = 0;
            boolean p2VictoryMinor = false;
            boolean p2VictoryMajor = false;
            int command_destroy = utils.getIntScriptVar(self, "quest_tracker.rebel.command_destroy");
            if (command_destroy == 1)
            {
                ++winCount;
            }
            int equipment = utils.getIntScriptVar(self, "quest_tracker.rebel.equipment");
            if (equipment == 1)
            {
                ++winCount;
            }
            int thermal = utils.getIntScriptVar(self, "quest_tracker.rebel.thermal");
            if (thermal == 1)
            {
                ++winCount;
            }
            int medical = utils.getIntScriptVar(self, "quest_tracker.rebel.medical");
            if (medical == 1)
            {
                ++winCount;
            }
            int command_escape = utils.getIntScriptVar(self, "quest_tracker.rebel.command_escape");
            if (command_escape == 1)
            {
                ++winCount;
            }
            int nonescape = utils.getIntScriptVar(self, "quest_tracker.rebel.nonescape");
            if (nonescape == 1)
            {
                ++winCount;
            }
            if (winCount < 6)
            {
                if (winCount >= 3)
                {
                    p2VictoryMinor = true;
                }
            }
            else 
            {
                p2VictoryMajor = true;
            }
            int p2Tokens = 0;
            if (p2VictoryMajor)
            {
                p2Tokens = 3;
            }
            else if (p2VictoryMinor)
            {
                p2Tokens = 1;
            }
            int xport_minor = utils.getIntScriptVar(self, "quest_tracker.rebel.xport_minor");
            int xport_major = utils.getIntScriptVar(self, "quest_tracker.rebel.xport_major");
            boolean p3VictoryMinor = false;
            boolean p3VictoryMajor = false;
            if (xport_major != 1)
            {
                if (xport_minor == 1)
                {
                    p3VictoryMinor = true;
                }
            }
            else 
            {
                p3VictoryMajor = true;
            }
            int p3Tokens = 0;
            if (p3VictoryMajor)
            {
                p3Tokens = 3;
            }
            else if (p3VictoryMinor)
            {
                p3Tokens = 1;
            }
            String message = " \\#FFFFFFNorth BattleField: (\\#FBEC5D" + p1Tokens + " \\#FFFFFFtokens)\n";
            if (!p1VictoryMajor && !p1VictoryMinor)
            {
                message += " \\#FF0000You failed to stop the Imperial Walkers\n";
            }
            else if (p1VictoryMajor)
            {
                message += " \\#00FF00Your Heroic efforts on the North Battlefield saved many lives.\n";
            }
            else if (p1VictoryMinor)
            {
                message += " \\#FF6103You did your best to stop those Imperial Walkers.\n";
            }
            message += " \\#FFFFFFEcho Base Interior: (\\#FBEC5D" + p2Tokens + " \\#FFFFFFtokens)\n";
            if (!p2VictoryMajor && !p2VictoryMinor)
            {
                message += " \\#FF0000Despite your best efforts, not many personnel escaped.\n";
            }
            else if (p2VictoryMajor)
            {
                message += " \\#00FF00Your Coordinated retreat allowed everyone to escape.\n";
            }
            else if (p2VictoryMinor)
            {
                message += " \\#FF6103You manged to get most of the personnel out.\n";
            }
            if (command_destroy == 1)
            {
                message += " \t\\#00FF00Destroyed Command Center\n";
            }
            else 
            {
                message += " \t\\#FF0000Destroyed Command Center\n";
            }
            if (equipment == 1)
            {
                message += " \t\\#00FF00Equipment Evacuated\n";
            }
            else 
            {
                message += " \t\\#FF0000Equipment Evacuated\n";
            }
            if (thermal == 1)
            {
                message += " \t\\#00FF00Thermal Generator Personnel Evacuated\n";
            }
            else 
            {
                message += " \t\\#FF0000Thermal Generator Personnel Evacuated\n";
            }
            if (medical == 1)
            {
                message += " \t\\#00FF00Medical Personnel Evacuated\n";
            }
            else 
            {
                message += " \t\\#FF0000Medical Personnel Evacuated\n";
            }
            if (command_escape == 1)
            {
                message += " \t\\#00FF00Command Personnel Evacuated\n";
            }
            else 
            {
                message += " \t\\#FF0000Command Personnel Evacuated\n";
            }
            if (nonescape == 1)
            {
                message += " \t\\#00FF00Non-Essential Personnel Evacuated\n";
            }
            else 
            {
                message += " \t\\#FF0000Non-Essential Personnel Evacuated\n";
            }
            message += " \\#FFFFFFEvacuation Area: (\\#FBEC5D" + p3Tokens + " \\#FFFFFFtokens)\n";
            if (!p3VictoryMajor && !p3VictoryMinor)
            {
                message += " \\#FF0000Despite your best efforts, none of the transport got away.\n";
            }
            else if (p3VictoryMajor)
            {
                message += " \\#00FF00All transports were loaded and got away safely.\n";
            }
            else if (p3VictoryMinor)
            {
                message += " \\#FF6103You did your best to get as many transports away as you could.";
            }
            for (obj_id player : players) {
                sui.msgbox(self, player, message, sui.OK_ONLY, "Alliance: Echo Base", "noHandler");
            }
        }
        else 
        {
            int fail_major = utils.getIntScriptVar(self, "quest_tracker.imperial.fail_major");
            int fail_minor = utils.getIntScriptVar(self, "quest_tracker.imperial.fail_minor");
            boolean p1VictoryMinor = false;
            boolean p1VictoryMajor = false;
            if (fail_major != 0)
            {
                if (fail_minor == 0)
                {
                    p1VictoryMinor = true;
                }
            }
            else 
            {
                p1VictoryMajor = true;
            }
            int p1Tokens = 0;
            if (p1VictoryMajor)
            {
                p1Tokens = 3;
            }
            else if (p1VictoryMinor)
            {
                p1Tokens = 1;
            }
            int winCount = 0;
            boolean p2VictoryMinor = false;
            boolean p2VictoryMajor = false;
            int command_capture = utils.getIntScriptVar(self, "quest_tracker.imperial.command_capture");
            if (command_capture == 1)
            {
                ++winCount;
            }
            int hangar_capture = utils.getIntScriptVar(self, "quest_tracker.imperial.hangar_capture");
            if (hangar_capture == 1)
            {
                ++winCount;
            }
            int ion_cap_destroyed = utils.getIntScriptVar(self, "quest_tracker.imperial.ion_cap_destroyed");
            if (ion_cap_destroyed == 1)
            {
                ++winCount;
            }
            int food_destroy = utils.getIntScriptVar(self, "quest_tracker.imperial.food_destroy");
            if (food_destroy == 1)
            {
                ++winCount;
            }
            int medical_destroy = utils.getIntScriptVar(self, "quest_tracker.imperial.medical_destroy");
            if (medical_destroy == 1)
            {
                ++winCount;
            }
            int equipment_destroy = utils.getIntScriptVar(self, "quest_tracker.imperial.equipment_destroy");
            if (equipment_destroy == 1)
            {
                ++winCount;
            }
            if (winCount < 6)
            {
                if (winCount >= 3)
                {
                    p2VictoryMinor = true;
                }
            }
            else 
            {
                p2VictoryMajor = true;
            }
            int p2Tokens = 0;
            if (p2VictoryMajor)
            {
                p2Tokens = 3;
            }
            else if (p2VictoryMinor)
            {
                p2Tokens = 1;
            }
            int xport_minor = utils.getIntScriptVar(self, "quest_tracker.imperial.p3_minor");
            int xport_major = utils.getIntScriptVar(self, "quest_tracker.imperial.p3_major");
            boolean p3VictoryMinor = false;
            boolean p3VictoryMajor = false;
            if (xport_major != 1)
            {
                if (xport_minor == 1)
                {
                    p3VictoryMinor = true;
                }
            }
            else 
            {
                p3VictoryMajor = true;
            }
            int p3Tokens = 0;
            if (p3VictoryMajor)
            {
                p3Tokens = 3;
            }
            else if (p3VictoryMinor)
            {
                p3Tokens = 1;
            }
            String message = " \\#FFFFFFNorth BattleField: (\\#FBEC5D" + p1Tokens + " \\#FFFFFFtokens)\n";
            if (!p1VictoryMajor && !p1VictoryMinor)
            {
                message += " \\#FF0000Field of Battle won with heavy casualties\n\n";
            }
            else if (p1VictoryMajor)
            {
                message += " \\#00FF00Field of Battle won with no casualties.\n\n";
            }
            else if (p1VictoryMinor)
            {
                message += " \\#FF6103Field of Battle won with few casualties.\n\n";
            }
            message += " \\#FFFFFFEcho Base Interior: (\\#FBEC5D" + p2Tokens + " \\#FFFFFFtokens)\n";
            if (!p2VictoryMajor && !p2VictoryMinor)
            {
                message += " \\#FF0000You failed to stop the Rebels from evacuating key personnel and supplies.\n";
            }
            else if (p2VictoryMajor)
            {
                message += " \\#00FF00The Rebels were unable to evacuate any key personnel or supplies.\n";
            }
            else if (p2VictoryMinor)
            {
                message += " \\#FF6103The Rebels were only able to escape with a few Key personnel and supplies\n";
            }
            if (command_capture == 1)
            {
                message += " \t\\#00FF00Captured Command Center\n";
            }
            else 
            {
                message += " \t\\#FF0000Captured Command Center\n";
            }
            if (hangar_capture == 1)
            {
                message += " \t\\#00FF00Main Hangar Captured\n";
            }
            else 
            {
                message += " \t\\#FF0000Main Hangar Captured\n";
            }
            if (ion_cap_destroyed == 1)
            {
                message += " \t\\#00FF00Ion Cannon Capacitor Destroyed\n";
            }
            else 
            {
                message += " \t\\#FF0000Ion Cannon Capacitor Destroyed\n";
            }
            if (food_destroy == 1)
            {
                message += " \t\\#00FF00Food Supplies Destroyed \n";
            }
            else 
            {
                message += " \t\\#FF0000Food Supplies Destroyed \n";
            }
            if (medical_destroy == 1)
            {
                message += " \t\\#00FF00Medical Supplies Destroyed\n";
            }
            else 
            {
                message += " \t\\#FF0000Medical Supplies Destroyed\n";
            }
            if (equipment_destroy == 1)
            {
                message += " \t\\#00FF00Equipment Stores Destroyed\n\n";
            }
            else 
            {
                message += " \t\\#FF0000Equipment Stores Destroyed\n\n";
            }
            message += " \\#FFFFFFEvacuation Area: (\\#FBEC5D" + p3Tokens + " \\#FFFFFFtokens)\n";
            if (!p3VictoryMajor && !p3VictoryMinor)
            {
                message += " \\#FF0000You failed in your task, all transports got away.\n";
            }
            else if (p3VictoryMajor)
            {
                message += " \\#00FF00You performed greatly for your Empire, no transports escaped.\n";
            }
            else if (p3VictoryMinor)
            {
                message += " \\#FF6103You destroyed many of the transports before they could take off.";
            }
            for (obj_id player : players) {
                sui.msgbox(self, player, message, sui.OK_ONLY, "Imperial: Echo Base", "noHandler");
            }
        }
    }
    public int ping(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("sender");
        String[] p1_obj = 
        {
            "at_minor",
            "at_major"
        };
        int[] p1_obj_int = new int[2];
        String[] p2_obj = 
        {
            "command_destroy",
            "equipment",
            "thermal",
            "medical",
            "command_escape",
            "nonescape"
        };
        int[] p2_obj_int = new int[6];
        String[] p3_obj = 
        {
            "xport_minor",
            "xport_major"
        };
        int[] p3_obj_int = new int[2];
        String[] imp_p1_obj = 
        {
            "fail_major",
            "fail_minor"
        };
        int[] imp_p1_obj_int = new int[2];
        String[] imp_p2_obj = 
        {
            "command_capture",
            "hangar_capture",
            "ion_cap_destroyed",
            "food_destroy",
            "medical_destroy",
            "equipment_destroy"
        };
        int[] imp_p2_obj_int = new int[6];
        String[] imp_p3_obj = 
        {
            "p3_minor",
            "p3_major"
        };
        int[] imp_p3_obj_int = new int[2];
        String message = "";
        dictionary dict = new dictionary();
        for (int i = 0; i < p1_obj.length; i++)
        {
            p1_obj_int[i] = utils.getIntScriptVar(self, "quest_tracker.rebel." + p1_obj[i]);
        }
        for (int i = 0; i < p2_obj.length; i++)
        {
            p2_obj_int[i] = utils.getIntScriptVar(self, "quest_tracker.rebel." + p2_obj[i]);
        }
        for (int i = 0; i < p3_obj.length; i++)
        {
            p3_obj_int[i] = utils.getIntScriptVar(self, "quest_tracker.rebel." + p3_obj[i]);
        }
        resendPingToPlayer(player, "Rebel Quest Objectives");
        for (int i = 0; i < p1_obj.length; i++)
        {
            resendPingToPlayer(player, "This: " + p1_obj[i] + " status: " + p1_obj_int[i]);
        }
        for (int i = 0; i < p2_obj.length; i++)
        {
            resendPingToPlayer(player, "This: " + p2_obj[i] + " status: " + p2_obj_int[i]);
        }
        for (int i = 0; i < p3_obj.length; i++)
        {
            resendPingToPlayer(player, "This: " + p3_obj[i] + " status: " + p3_obj_int[i]);
        }
        resendPingToPlayer(player, "Imperial Quest Objectives");
        for (int i = 0; i < imp_p1_obj.length; i++)
        {
            imp_p1_obj_int[i] = utils.getIntScriptVar(self, "quest_tracker.imperial." + imp_p1_obj[i]);
        }
        for (int i = 0; i < imp_p2_obj.length; i++)
        {
            imp_p2_obj_int[i] = utils.getIntScriptVar(self, "quest_tracker.imperial." + imp_p2_obj[i]);
        }
        for (int i = 0; i < imp_p3_obj.length; i++)
        {
            imp_p3_obj_int[i] = utils.getIntScriptVar(self, "quest_tracker.imperial." + imp_p3_obj[i]);
        }
        for (int i = 0; i < imp_p1_obj.length; i++)
        {
            resendPingToPlayer(player, "This: " + imp_p1_obj[i] + " status: " + imp_p1_obj_int[i]);
        }
        for (int i = 0; i < imp_p2_obj.length; i++)
        {
            resendPingToPlayer(player, "This: " + imp_p2_obj[i] + " status: " + imp_p2_obj_int[i]);
        }
        for (int i = 0; i < imp_p3_obj.length; i++)
        {
            resendPingToPlayer(player, "This: " + imp_p3_obj[i] + " status: " + imp_p3_obj_int[i]);
        }
        resendPingToPlayer(player, "Rebel Tokens = " + getRebelTokenCountByVictory());
        resendPingToPlayer(player, "Imperial Tokens = " + getImperialTokenCountByVictory());
        return SCRIPT_CONTINUE;
    }
    public void resendPingToPlayer(obj_id player, String message) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("message", message);
        messageTo(player, "instance_ping_response", dict, 0.0f, false);
    }
    public int getRebelTokenCountByVictory() throws InterruptedException
    {
        obj_id self = getSelf();
        int p1_eval = 0;
        int p2_eval = 0;
        int p3_eval = 0;
        int p1_token = 0;
        int p2_token = 0;
        int p3_token = 0;
        String[] p1_obj = 
        {
            "at_minor",
            "at_major"
        };
        String[] p2_obj = 
        {
            "command_destroy",
            "equipment",
            "thermal",
            "medical",
            "command_escape",
            "nonescape"
        };
        String[] p3_obj = 
        {
            "xport_minor",
            "xport_major"
        };
        for (String s2 : p1_obj) {
            p1_eval += utils.getIntScriptVar(self, "quest_tracker.rebel." + s2);
        }
        for (String s1 : p2_obj) {
            p2_eval += utils.getIntScriptVar(self, "quest_tracker.rebel." + s1);
        }
        for (String s : p3_obj) {
            p3_eval += utils.getIntScriptVar(self, "quest_tracker.rebel." + s);
        }
        if (p1_eval > 0)
        {
            p1_token = p1_eval == 1 ? 1 : 3;
        }
        if (p2_eval > 2)
        {
            p2_token = p2_eval < 6 ? 1 : 3;
        }
        if (p3_eval > 0)
        {
            p3_token = p3_eval == 1 ? 1 : 3;
        }
        return p1_token + p2_token + p3_token;
    }
    public int getImperialTokenCountByVictory() throws InterruptedException
    {
        obj_id self = getSelf();
        int p1_eval = 0;
        int p2_eval = 0;
        int p3_eval = 0;
        int p1_token = 0;
        int p2_token = 0;
        int p3_token = 0;
        String[] p1_obj = 
        {
            "fail_major",
            "fail_minor"
        };
        String[] p2_obj = 
        {
            "command_capture",
            "hangar_capture",
            "ion_cap_destroyed",
            "food_destroy",
            "medical_destroy",
            "equipment_destroy"
        };
        String[] p3_obj = 
        {
            "p3_minor",
            "p3_major"
        };
        for (String s2 : p1_obj) {
            p1_eval += utils.getIntScriptVar(self, "quest_tracker.imperial." + s2);
        }
        for (String s1 : p2_obj) {
            p2_eval += utils.getIntScriptVar(self, "quest_tracker.imperial." + s1);
        }
        for (String s : p3_obj) {
            p3_eval += utils.getIntScriptVar(self, "quest_tracker.imperial." + s);
        }
        if (p1_eval > 0)
        {
            p1_token = p1_eval == 1 ? 1 : 0;
        }
        else 
        {
            p1_token = 3;
        }
        if (p2_eval > 2)
        {
            p2_token = p2_eval < 6 ? 1 : 3;
        }
        if (p3_eval > 0)
        {
            p3_token = p3_eval == 2 ? 3 : 1;
        }
        return p1_token + p2_token + p3_token;
    }
    public int grantP2Waypoint(obj_id self, dictionary params) throws InterruptedException
    {
        location myLoc = getLocation(self);
        myLoc.x += 63;
        myLoc.y += 50;
        myLoc.z += 318;
        location wpLoc = new location(myLoc.x, myLoc.y, myLoc.z);
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            obj_id waypoint = createWaypointInDatapad(player, wpLoc);
            setWaypointName(waypoint, "Echo Base");
            setWaypointColor(waypoint, "blue");
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int make_p3_end_wp(obj_id self, dictionary params) throws InterruptedException
    {
        location myLoc = getLocation(self);
        myLoc.x -= 466;
        myLoc.y += 93;
        myLoc.z -= 844;
        location wpLoc = new location(myLoc.x, myLoc.y, myLoc.z);
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            obj_id waypoint = createWaypointInDatapad(player, wpLoc);
            setWaypointName(waypoint, "Extraction Point");
            setWaypointColor(waypoint, "orange");
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int crateTaken(obj_id self, dictionary params) throws InterruptedException
    {
        String type = params.getString("type");
        obj_id[] crates = trial.getObjectsInInstanceBySpawnId(self, type);
        if (crates == null || crates.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id crate : crates) {
            if (exists(crate)) {
                trial.cleanupObject(crate);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/theme_park/heroic/echo_base/echo_controller/" + section, message);
        }
    }
}
