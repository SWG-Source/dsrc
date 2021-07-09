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

    // Configuration Array:
    // 1. Zone.
    // 2. Initial Battle Type.
    public static final String[][] BATTLE_SCENES = {
            {"space_tatooine", battle_spawner.BATTLE_TYPE_PVE},
            {"space_corellia", battle_spawner.BATTLE_TYPE_PVE},
            {"space_dantooine", battle_spawner.BATTLE_TYPE_PVP},
            {"space_lok", battle_spawner.BATTLE_TYPE_PVP},
            {"space_naboo", battle_spawner.BATTLE_TYPE_PVE}
    };
    public boolean tatooineActive = true;
    public boolean corelliaActive = true;
    public boolean dantooineActive = true;
    public boolean lokActive = true;
    public boolean nabooActive = true;

    public int tatooineDelay = DEFAULT_TATOOINE_DELAY;
    public int corelliaDelay = DEFAULT_CORELLIA_DELAY;
    public int dantooineDelay = DEFAULT_DANTOOINE_DELAY;
    public int lokDelay = DEFAULT_LOK_DELAY;
    public int nabooDelay = DEFAULT_NABOO_DELAY;

    public int tatooineStagger = DEFAULT_TATOOINE_STAGGER;
    public int corelliaStagger = DEFAULT_CORELLIA_STAGGER;
    public int dantooineStagger = DEFAULT_DANTOOINE_STAGGER;
    public int lokStagger = DEFAULT_LOK_STAGGER;
    public int nabooStagger = DEFAULT_NABOO_STAGGER;

    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("space_gcw", "battle_controller.OnInitialize: The space battle sequencer object is starting for the fist time.");

        removeObjVar(self, "space_gcw");

        // get minutes to the top of the hour so we can kick off a regularly scheduled battle status check.
        int calendarTime = getCalendarTime();
        int[] convertedCalendarTime = player_structure.convertSecondsTime(calendarTime);
        //float minutesToTopOfHour = 59 - convertedCalendarTime[2];
        float minutesToTopOfHour = 0;
        getSettings();
        LOG("space_gcw", "battle_controller.OnInitialize: Starting check for battle status in roughly " + minutesToTopOfHour + " minutes.");
        // check battle status at the top of the hour
        messageTo(self, "checkBattleStatus", null, minutesToTopOfHour * 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    private String craftPendingBattleMessage(String defendingFaction, String defendingShipType, String zone, String attackingFaction, int time){
        return "The " + defendingFaction + " " + defendingShipType + " in the " + zone + " will be coming under attack from the " + attackingFaction + " capital ship in " + time + " minutes.";
    }
    private void handlePendingBattleWarning(obj_id spawner, String scene) {
        int timeUntilBattle = timeUntilMessageTo(spawner, "startSpaceGCWBattle");

        // if time until the next battle is between 15 minutes and 14 minutes, send system wide message.
        if(timeUntilBattle > (15 * 60)){
            return;
        }

        String msg = null;
        obj_id controller = getSelf();
        String lastDefendingFaction;
        if(hasObjVar(controller, "space_gcw." + spawner + ".lastDefendingFaction")){
            lastDefendingFaction = getStringObjVar(controller, "space_gcw." + spawner + ".lastDefendingFaction");
        }
        else{
            lastDefendingFaction = "imperial";
        }
        String defendingFaction = lastDefendingFaction.equals("imperial") ? "Imperial" : "Rebel";
        String attackingFaction = defendingFaction.equals("Imperial") ? "Rebel" : "Imperial";
        String defendingShipType = "Capital Ship";

        if(timeUntilBattle < (15 * 60) && timeUntilBattle >= (14 * 60)){
            msg = craftPendingBattleMessage(defendingFaction, defendingShipType, scene, attackingFaction, 15);
        }
        // if time until the next battle is between 10 minutes and 9 minutes, send system wide message.
        else if(timeUntilBattle < (10 * 60) && timeUntilBattle >= (9 * 60)){
            msg = craftPendingBattleMessage(defendingFaction, defendingShipType, scene, attackingFaction, 10);
        }
        // if time until the next battle is between 15 minutes and 14 minutes, send system wide message.
        else if(timeUntilBattle < (5 * 60) && timeUntilBattle >= (4 * 60)){
            msg = craftPendingBattleMessage(defendingFaction, defendingShipType, scene, attackingFaction, 5);
        }
        if(msg != null) {
            chatSendToRoom(getGameChatCode() + "." + getGalaxyName() + ".Pilot", msg, null);
        }
    }
    public int checkBattleStatus(obj_id self, dictionary params) {
        LOG("space_gcw", "battle_controller.checkBattleStatus: The space battle sequencer object is checking the battle status of all zones.");
        for(String[] scene : BATTLE_SCENES){
            // try implementation that just fires off a check to the spawner itself... do this because of the messageTo authoritative issues.

            obj_id spawner = getObjIdObjVar(self, "space_gcw." + scene[0] + ".spawner");
            if(spawner == null){
                continue;
            }
            if(hasObjVar(self, "space_gcw." + scene[0] + ".nextBattleStart")){
                LOG("space_gcw", "battle_controller.checkBattleStatus: Scene " + scene[0] + " already has a timer set (firing in " + (getIntObjVar(self, "space_gcw." + scene[0] + ".nextBattleStart") - getCalendarTime()) + " seconds) so skipping this zone.");
                handlePendingBattleWarning(spawner, scene[0]);
                continue;
            }
            boolean sceneEnabled = isSceneActive(scene[0]);
            LOG("space_gcw", "battle_controller.checkBattleStatus: The space battle sequencer object is checking if zone " + scene[0] + " is enabled: " + (sceneEnabled ? "Enabled" : "Disabled"));
            if(!sceneEnabled) continue;
            boolean battleIsActive = getIntObjVar(self, "space_gcw." + spawner + ".active") == 1;
            LOG("space_gcw", "battle_controller.checkBattleStatus: The space battle sequencer object is checking if battle for zone " + scene[0] + " is currently in progress: " + (battleIsActive ? "IN PROGRESS" : "NOT IN PROGRESS"));
            if(battleIsActive) continue;

            int secondsUntilNextBattle;
            if(hasObjVar(self, "space_gcw." + scene[0] + ".lastBattleTime")) {
                secondsUntilNextBattle = getSecondsUntilNextBattle(scene, getIntObjVar(self, "space_gcw." + scene[0] + ".lastBattleTime"));
            }
            else{
                secondsUntilNextBattle = getStagger(scene[0]) * 60 * 60;
            }

            dictionary battleDetails = new dictionary();
            String battleType = getStringObjVar(self, "space_gcw." + spawner + ".lastBattleType");
            if(battleType != null) {
                if (battleType.equals(battle_spawner.BATTLE_TYPE_PVE)) {
                    battleType = battle_spawner.BATTLE_TYPE_PVP;
                } else {
                    battleType = battle_spawner.BATTLE_TYPE_PVE;
                }
            }
            else{
                battleType = scene[1];
            }
            battleDetails.put("battle_type", battleType);
            battleDetails.put("controller", self);

            setObjVar(self, "space_gcw." + spawner + ".lastBattleType", battleType);

            if(secondsUntilNextBattle == 0){
                LOG("space_gcw", "battle_controller.checkBattleStatus: The space battle sequencer object is starting a " + scene[1] + " battle in zone " + scene[0] + ".");
            }
            else {
                LOG("space_gcw", "battle_controller.checkBattleStatus: The space battle sequencer is not starting a battle in zone " + scene[0] + " because it has " + String.format("%.0f", secondsUntilNextBattle / 60.0f) + " minutes until the next battle.");
            }
            setObjVar(self, "space_gcw." + scene[0] + ".nextBattleStart", secondsUntilNextBattle + getCalendarTime());
            messageTo(spawner, "startSpaceGCWBattle", battleDetails, secondsUntilNextBattle, false);
        }

        LOG("space_gcw", "battle_controller.checkBattleStatus: The space battle sequencer object will check battle status again in 60 seconds.");
        // check battle status every hour from now.
        messageTo(self, "checkBattleStatus", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }

    public int getSecondsUntilNextBattle(String[] scene, int lastBattleTime){
        int secondsUntilNext = getHoursBetweenBattles(scene[0]) * 60 * 60;
        int gameTime = getCalendarTime();
        int nextBattleTime = lastBattleTime + secondsUntilNext;

        if(nextBattleTime > gameTime){
            return nextBattleTime - gameTime;
        }
        return 0;
    }
    public int getHoursBetweenBattles(String scene){
        switch(scene){
            case "space_tatooine":
                return tatooineDelay;
            case "space_corellia":
                return corelliaDelay;
            case "space_dantooine":
                return dantooineDelay;
            case "space_lok":
                return lokDelay;
            case "space_naboo":
                return nabooDelay;
            default:
                return 0;
        }
    }
    public boolean isSceneActive(String zone) {
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

    /*
    * Stagger is the amount of time (in hours) to stagger the battles.
      * ex:
      * 1. If a battle starts at 1PM and the stagger is 2 (hours) then the next battle will occur at 3PM.
      * 2. If the stagger is 0, the next battle will start as soon as the first one finishes.
     */
    public int getStagger(String zone) {
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
        LOG("space_gcw", "battle_controller.getSettings: The space battle sequencer object is getting settings.");

        // check which zones are active
        tatooineActive = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwTatooineActive")) > 0;
        corelliaActive = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwCorelliaActive")) > 0;
        dantooineActive = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwDantooineActive")) > 0;
        lokActive = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwLokActive")) > 0;
        nabooActive = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwNabooActive")) > 0;

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
        LOG("space_gcw", "battle_controller.getSettings: The space battle sequencer object has finished getting settings.");

    }
    /*
     * Helper method for command:
     *          /spaceBattleStatus <zone>
     *
     * Method to get the amount of time (in seconds) until the next scheduled battle.
     * If no timer is present (i.e. battle is not scheduled) or the zone is not valid (no battle in the zone), a value of -1 will be returned.
     * If the battle is currently active, a value of -2 will be returned.
     *
     * Otherwise, the remaning time until the start of the next battle (in seconds) will be returned.
     */
    public static int getTimeUntilNextBattleForZone(String zone){
        obj_id controller = getPlanetByName("tatooine");
        for (String[] scene : battle_controller.BATTLE_SCENES){
            if(scene[0].equals(zone)){
                obj_id spawner = getObjIdObjVar(controller, "space_gcw." + scene[0] + ".spawner");
                
                if(getIntObjVar(controller, "space_gcw." + spawner + ".active") == 1){
                    return -2;
                }
                else if(hasObjVar(controller, "space_gcw." + scene[0] + ".nextBattleStart")) {
                    return getIntObjVar(controller, "space_gcw." + scene[0] + ".nextBattleStart") - getCalendarTime();
                }
                return -1;
            }
        }
        return -1;
    }
    /*
     * Helper method for command:
     *          /spaceBattleStatus <zone>
     *
     * Method to get the last battle type (PvP or PvE) for the given zone.
     *
     */
    public static String getLastBattleType(obj_id self, String zone){
        for (String[] scene : battle_controller.BATTLE_SCENES) {
            if (scene[0].equals(zone)) {
                obj_id spawner = getObjIdObjVar(self, "space_gcw." + scene[0] + ".spawner");
                if (hasObjVar(spawner, "last_battle_type")) {
                    return getStringObjVar(spawner, "last_battle_type");
                }
                else if (scene[1].equals(battle_spawner.BATTLE_TYPE_PVE)) {
                    return battle_spawner.BATTLE_TYPE_PVP;
                }
            }
        }
        return "";
    }
    /*
     * Helper method for command:
     *          /spaceBattleStatus <zone>
     *
     * Method to get the current battle type (PvP or PvE) for the given zone.
     *
     */
    public static String getCurrentBattleType(obj_id self, String zone){
        for (String[] scene : battle_controller.BATTLE_SCENES) {
            if (scene[0].equals(zone)) {
                obj_id spawner = getObjIdObjVar(self, "space_gcw." + scene[0] + ".spawner");
                if (hasObjVar(spawner, "battle_type")) {
                    return getStringObjVar(spawner, "battle_type");
                }
                else if (scene[1].equals(battle_spawner.BATTLE_TYPE_PVE)) {
                    return battle_spawner.BATTLE_TYPE_PVE;
                }
                else {
                    return battle_spawner.BATTLE_TYPE_PVP;
                }
            }
        }
        return "";
    }
}
