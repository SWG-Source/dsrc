package script.systems.gcw.space;

import script.dictionary;
import script.library.player_structure;
import script.library.utils;
import script.obj_id;

public class battle_controller extends script.base_script {

    private static final boolean TATOOINE_ACTIVE = utils.checkConfigFlag("GameServer", "spaceGcwTatooineActive");
    private static final boolean CORELLIA_ACTIVE = utils.checkConfigFlag("GameServer", "spaceGcwCorelliaActive");
    private static final boolean DANTOOINE_ACTIVE = utils.checkConfigFlag("GameServer", "spaceGcwDantooineActive");
    private static final boolean LOK_ACTIVE = utils.checkConfigFlag("GameServer", "spaceGcwLokActive");
    private static final boolean NABOO_ACTIVE = utils.checkConfigFlag("GameServer", "spaceGcwNabooActive");
    private static final int TATOOINE_DELAY = utils.getIntConfigSetting("GameServer", "spaceGcwTatooineDelay", 3);
    private static final int CORELLIA_DELAY = utils.getIntConfigSetting("GameServer", "spaceGcwCorelliaDelay", 3);
    private static final int DANTOOINE_DELAY = utils.getIntConfigSetting("GameServer", "spaceGcwDantooineDelay", 3);
    private static final int LOK_DELAY = utils.getIntConfigSetting("GameServer", "spaceGcwLokDelay", 3);
    private static final int NABOO_DELAY = utils.getIntConfigSetting("GameServer", "spaceGcwNabooDelay", 3);
    private static final int TATOOINE_STAGGER = utils.getIntConfigSetting("GameServer", "spaceGcwTatooineStagger", 0);
    private static final int CORELLIA_STAGGER = utils.getIntConfigSetting("GameServer", "spaceGcwCorelliaStagger", 2);
    private static final int DANTOOINE_STAGGER = utils.getIntConfigSetting("GameServer", "spaceGcwDantooineStagger", 0);
    private static final int LOK_STAGGER = utils.getIntConfigSetting("GameServer", "spaceGcwLokStagger", 2);
    private static final int NABOO_STAGGER = utils.getIntConfigSetting("GameServer", "spaceGcwNabooStagger", 4);
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
                return TATOOINE_DELAY;
            case "space_corellia":
                return CORELLIA_DELAY;
            case "space_dantooine":
                return DANTOOINE_DELAY;
            case "space_lok":
                return LOK_DELAY;
            case "space_naboo":
                return NABOO_DELAY;
            default:
                return 0;
        }
    }
    public boolean isSceneActive(String zone) {
        switch(zone){
            case "space_tatooine":
                return TATOOINE_ACTIVE;
            case "space_corellia":
                return CORELLIA_ACTIVE;
            case "space_dantooine":
                return DANTOOINE_ACTIVE;
            case "space_lok":
                return LOK_ACTIVE;
            case "space_naboo":
                return NABOO_ACTIVE;
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
                return TATOOINE_STAGGER;
            case "space_corellia":
                return CORELLIA_STAGGER;
            case "space_dantooine":
                return DANTOOINE_STAGGER;
            case "space_lok":
                return LOK_STAGGER;
            case "space_naboo":
                return NABOO_STAGGER;
        }
        return stagger;
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
