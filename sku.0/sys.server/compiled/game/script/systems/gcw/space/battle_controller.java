package script.systems.gcw.space;

import script.dictionary;
import script.library.player_structure;
import script.library.utils;
import script.obj_id;

public class battle_controller extends script.base_script {

    public final int DEFAULT_TATOOINE_DELAY = 3;
    public final int DEFAULT_CORELLIA_DELAY = 3;
    public final int DEFAULT_DANTOOINE_DELAY = 3;
    public final int DEFAULT_LOK_DELAY = 3;
    public final int DEFAULT_NABOO_DELAY = 3;

    public final int DEFAULT_TATOOINE_STAGGER = 0;
    public final int DEFAULT_CORELLIA_STAGGER = 2;
    public final int DEFAULT_DANTOOINE_STAGGER = 0;
    public final int DEFAULT_LOK_STAGGER = 2;
    public final int DEFAULT_NABOO_STAGGER = 4;

    public final String[][] BATTLE_SCENES = {
            {"space_tatooine", battle_spawner.BATTLE_TYPE_PVE, "6", "0"},
            {"space_corellia", battle_spawner.BATTLE_TYPE_PVE, "6", "2"},
            {"space_dantooine", battle_spawner.BATTLE_TYPE_PVP, "6", "0"},
            {"space_lok", battle_spawner.BATTLE_TYPE_PVP, "6", "2"},
            {"space_naboo", battle_spawner.BATTLE_TYPE_PVE, "6", "4"}
    };
    public boolean tatooineActive = true;
    public boolean corelliaActive = true;
    public boolean dantooineActive = true;
    public boolean lokActive = true;
    public boolean nabooActive = true;

    public int tatooineDelay = 3;
    public int corelliaDelay = 3;
    public int dantooineDelay = 3;
    public int lokDelay = 3;
    public int nabooDelay = 3;

    public int tatooineStagger = 0;
    public int corelliaStagger = 2;
    public int dantooineStagger = 0;
    public int lokStagger = 2;
    public int nabooStagger = 4;

    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("gcw_space", "battle_controller.OnInitialize: The space battle sequencer object is starting for the fist time.");

        // get minutes to the top of the hour so we can kick off a regularly scheduled battle status check.
        int calendarTime = getCalendarTime();
        int[] convertedCalendarTime = player_structure.convertSecondsTime(calendarTime);
        int minutesToTopOfHour = 59 - convertedCalendarTime[2];
        getSettings();
        // check battle status at the top of the hour
        messageTo(self, "checkBattleStatus", null, minutesToTopOfHour, false);
        return SCRIPT_CONTINUE;
    }
    public int checkBattleStatus(obj_id self, dictionary params) throws InterruptedException{
        CustomerServiceLog("gcw_space", "battle_controller.checkBattleStatus: The space battle sequencer object is checking the battle status of all zones.");
        for(String[] scene : BATTLE_SCENES){
            obj_id spawner = getObjIdObjVar(self, "space_gcw." + scene[0] + ".spawner");
            if(spawner == null || !isSceneActive(scene[0])) continue;

            boolean battleIsActive = getIntObjVar(self, "space_gcw." + spawner + ".active") == 1;

            CustomerServiceLog("gcw_space", "battle_controller.checkBattleStatus: The space battle sequencer object is checking zone " + scene[0] + ": " + (battleIsActive ? "Active" : "Inactive"));

            int hoursUntilNext;
            int stagger = getStagger(scene[0]);

            if(hasObjVar(spawner, "space_gcw.lastRunInterval")){
                hoursUntilNext = Integer.parseInt(scene[2]) - (getIntObjVar(spawner, "space_gcw.lastRunInterval") % Integer.parseInt(scene[2]));
            }
            else{
                hoursUntilNext = Integer.parseInt(scene[3]) + stagger;
            }
            if(!battleIsActive && hoursUntilNext == 0 && !hasObjVar(spawner, "space_gcw.lastBattleTime")){
                CustomerServiceLog("gcw_space", "battle_controller.checkBattleStatus: The space battle sequencer object is starting a " + scene[1]+ " battle in zone " + scene[0] + " for the first time.");
                dictionary battleDetails = new dictionary();
                battleDetails.put("battle_type", scene[1]);
                setObjVar(spawner, "space_gcw.lastBattleTime", getGameTime());
                setObjVar(spawner, "space_gcw.lastRunInterval", stagger + Integer.parseInt(scene[2]));
                messageTo(spawner, "startBattle", battleDetails, stagger, false);
            }
            else if(!battleIsActive && hoursUntilNext == 0 && hasObjVar(spawner, "space_gcw.lastBattleTime")) {
                String nextType;
                if(getStringObjVar(spawner, "space_gcw.lastBattleType").equals(battle_spawner.BATTLE_TYPE_PVE)){
                    nextType = battle_spawner.BATTLE_TYPE_PVP;
                }
                else{
                    nextType = battle_spawner.BATTLE_TYPE_PVE;
                }
                CustomerServiceLog("gcw_space", "battle_controller.checkBattleStatus: The space battle sequencer object is starting a " + nextType + " battle in zone " + scene[0] + ".");
                dictionary battleDetails = new dictionary();
                battleDetails.put("battle_type", nextType);
                setObjVar(spawner, "space_gcw.lastBattleTime", getGameTime());
                setObjVar(spawner, "space_gcw.lastRunInterval", stagger + Integer.parseInt(scene[2]));
                messageTo(spawner, "startBattle", battleDetails, 0.0f, false);
            }
            else{
                CustomerServiceLog("gcw_space", "battle_controller.checkBattleStatus: The space battle sequencer is not starting a battle in zone " + scene[0] + " because it " + (battleIsActive ? "is already" : "it isn't") + "active or because it has " + hoursUntilNext + " hours until the next battle.");
            }
        }

        // check battle status every hour from now.
        messageTo(self, "checkBattleStatus", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }

    public boolean isSceneActive(String zone)throws InterruptedException{
        switch(zone){
            case "space_tatooine":
                return tatooineActive;
            case "space_corellia":
                return corelliaActive;
            case "space_dantooine":
                return dantooineActive;
            case "space_lok":
                return lokActive;
            case "space_naboo":
                return nabooActive;
        }
        return true;
    }

    public int getStagger(String zone)throws InterruptedException{
        int stagger = 0;
        switch(zone){
            case "space_tatooine":
                return tatooineStagger;
            case "space_corellia":
                return corelliaStagger;
            case "space_dantooine":
                return dantooineStagger;
            case "space_lok":
                return lokStagger;
            case "space_naboo":
                return nabooStagger;
        }
        return stagger;
    }

    public void getSettings() throws InterruptedException {
        // get customized settings
        tatooineActive = getConfigSetting("GameServer", "spaceGcwTatooineActive").equals("1");
        corelliaActive = getConfigSetting("GameServer", "spaceGcwCorelliaActive").equals("1");
        dantooineActive = getConfigSetting("GameServer", "spaceGcwDantooineActive").equals("1");
        lokActive = getConfigSetting("GameServer", "spaceGcwLokActive").equals("1");
        nabooActive = getConfigSetting("GameServer", "spaceGcwNabooActive").equals("1");

        tatooineDelay = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwTatooineDelay"));
        if(tatooineDelay < 0) tatooineDelay = DEFAULT_TATOOINE_DELAY;
        corelliaDelay = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwCorelliaDelay"));
        if(corelliaDelay < 0) corelliaDelay = DEFAULT_CORELLIA_DELAY;
        dantooineDelay = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwDantooineDelay"));
        if(dantooineDelay < 0) dantooineDelay = DEFAULT_DANTOOINE_DELAY;
        lokDelay = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwLokDelay"));
        if(lokDelay < 0) lokDelay = DEFAULT_LOK_DELAY;
        nabooDelay = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwNabooDelay"));
        if(nabooDelay < 0) nabooDelay = DEFAULT_NABOO_DELAY;

        tatooineStagger = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwTatooineStagger"));
        if(tatooineStagger < 0) tatooineStagger = DEFAULT_TATOOINE_STAGGER;
        corelliaStagger = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwCorelliaStagger"));
        if(corelliaStagger < 0) corelliaStagger = DEFAULT_CORELLIA_STAGGER;
        dantooineStagger = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwDantooineStagger"));
        if(dantooineStagger < 0) dantooineStagger = DEFAULT_DANTOOINE_STAGGER;
        lokStagger = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwLokStagger"));
        if(lokStagger < 0) lokStagger = DEFAULT_LOK_STAGGER;
        nabooStagger = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwNabooStagger"));
        if(nabooStagger < 0) nabooStagger = DEFAULT_NABOO_STAGGER;

    }
}
