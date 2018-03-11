package script.systems.gcw.space;

import script.*;
import script.library.*;

import java.util.*;

public class battle_spawner extends script.base_class {
    public static final String CONTROLLER_SCRIPT = "systems.gcw.space.battle_controller";
    public static final String IMPERIAL_SHIP_TEMPLATE = "imperial_lancer";
    public static final String REBEL_SHIP_TEMPLATE = "nebulon_frigate";
    private static final String HERO_PILOT_DATA = "datatables/npc/space/space_gcw_hero.iff";
    public static float DEFAULT_BATTLE_TIME_PREPATORY = 900.0f;  // 900 == 15 minutes
    public static float DEFAULT_BATTLE_TIME_LENGTH = 3600.0f;  // 3600 == 60 minutes
    public static float MAX_SUPPORT_CRAFT = 30;
    public static float MAX_SUPPORT_SPAWN = 60;
    public static final double HERO_SPAWN_CHANCE = 0.20d;  // 0.20d == 20% chance a hero will spawn
    public static final float DEFAULT_PVP_POINT_MULTIPLIER = 2.0f;
    public static final float DEFAULT_PVE_POINT_MULTIPLIER = 1.0f;
    public static final float DEFAULT_PVP_TOKEN_MULTIPLIER = 2.0f;
    public static final float DEFAULT_PVE_TOKEN_MULTIPLIER = 1.0f;
    public static final float DEFAULT_WIN_POINT_MULTIPLIER = 2.0f;
    public static final float DEFAULT_LOSS_POINT_MULTIPLIER = 1.0f;
    public static final float DEFAULT_WIN_TOKEN_MULTIPLIER = 2.0f;
    public static final float DEFAULT_LOSS_TOKEN_MULTIPLIER = 1.0f;
    public static final int DEFAULT_POB_PLAYER_CEILING = 4;
    public static final int DEFAULT_GUNSHIP_PLAYER_CEILING = 10;
    public static final int DEFAULT_TOKEN_AWARD = 25;
    public static final int DEFAULT_POINT_AWARD = 2500;

    public static final String[] IMPERIAL_SUPPORT_CRAFT = {
            "tieaggressor_tier7",
            "tiebomber_tier7",
            "tiefighter_tier7",
            "tieinterceptor_tier7",
            "tieoppressor_tier7"
    };
    public static final String[] REBEL_SUPPORT_CRAFT = {
            "awing_tier7",
            "bwing_tier7",
            "xwing_tier7",
            "ywing_tier7",
            "z95_tier7"
    };
    public static final String[] IMPERIAL_HEROES = {
            "darth_vader",
            "maarek_stele",
            "chiraneau",
            "cive_rashon",
            "ds1814",
            "iran_ryad",
            "soontir_fel",
            "turr_phennir",
            "vindoo_barvel",
            "mara_jade"
    };
    public static final String[] REBEL_HEROES = {
            "arhul_narra",
            "arvel_crynyd",
            "derek_klivian",
            "jan_ors",
            "keyan_farlander",
            "pash_cracken",
            "tarrin_datch",
            "tycho_celchu",
            "wedge_antilles",
            "luke_skywalker"
    };

    public static final String BATTLE_TYPE_PVP = "pvp";
    public static final String BATTLE_TYPE_PVE = "pve";

    public static final String[] BATTLE_TYPES = {
            BATTLE_TYPE_PVE,
            BATTLE_TYPE_PVP
    };

    public float customPveBattleModifier;
    public float customPvpBattleModifier;
    public float customWinTokenModifier;
    public float customLossTokenModifier;
    public float customWinPointModifier;
    public float customLossPointModifier;
    public int pobPlayerCeiling;
    public int gunshipPlayerCeiling;
    public int customTokenAward;
    public int customPointAward;
    public float battleTimeLength;
    public float prepatoryTimeLength;

    // This script is the spawner for the Space GCW (GCW 2) battles.
    // It uses Tatooine to track battles (the controller) and places the following object vars
    // on the planet object to keep track of various aspects:
    //
    // space_gcw.<zone>.active = <0, 1>  -- <zone> is the space zone, 0 means inactive, 1 means active
    // space_gcw.participant.<battle_id>.<participant>  -- <battle_id> is the battle identifier, <participant> is the obj id of the player.

    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "registerWithController", null, 30.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int registerWithController(obj_id self, dictionary params) throws InterruptedException {
        obj_id controller = getPlanetByName("tatooine");
        removeObjVar(controller, "space_gcw");
        if(!hasScript(controller, CONTROLLER_SCRIPT)) attachScript(controller, CONTROLLER_SCRIPT);
        location battleLocation = getLocation(self);
        setObjVar(self, "controller", controller);
        setObjVar(controller, "space_gcw." + battleLocation.area + ".spawner", self);
        getSettings();
        return SCRIPT_CONTINUE;
    }
    public int startSpaceGCWBattle(obj_id self, dictionary params) throws InterruptedException {
        // make sure we're not already having a battle
        if(!battleIsNotActive()) return SCRIPT_CONTINUE;

        LOG("space_gcw", "In startSpaceGCWBattle... beginning battle creation.");
        obj_id controller = params.getObjId("controller");

        int gameTime = getGameTime();
        String battleId = getLocation(self).area + "_" + gameTime;

        // controller should have chosen the battle type.
        setObjVar(self, "battle_id", battleId);
        setObjVar(self, "battle_type", params.getString("battle_type"));
        setObjVar(self, "last_battle_time", gameTime);
        setObjVar(controller, "space_gcw." + self + ".lastBattleTime", gameTime);
        params.put("battle_id", battleId);

        // tell the controller we've started the battle
        setObjVar(controller, "space_gcw." + self.toString() + ".active", 1);

        location spaceLocation = getLocation(self);
        String planetName = spaceLocation.area.substring(6);

        // get defending faction
        String defendingFaction = (getGcwGroupImperialScorePercentile(planetName + "_pvp") >= 50 ? "imperial" : "rebel");

        // spawn defender capital ship
        transform defenseSpawnPoint = transform.identity.setPosition_p(spaceLocation.x, spaceLocation.y, spaceLocation.z);
        obj_id defendingShip;

        if(defendingFaction.equalsIgnoreCase("imperial")){
            defendingShip = space_create.createShipHyperspace(IMPERIAL_SHIP_TEMPLATE, defenseSpawnPoint);
            setObjVar(self, "defendingFaction", "imperial");
            setObjVar(self, "attackingFaction", "rebel");
        }
        else{
            defendingShip = space_create.createShipHyperspace(REBEL_SHIP_TEMPLATE, defenseSpawnPoint);
            setObjVar(self, "defendingFaction", "rebel");
            setObjVar(self, "attackingFaction", "imperial");
        }
        LOG("space_gcw", "In startSpaceGCWBattle... just created defending ship(" + defendingShip + ").");
        setObjVar(defendingShip, "intNoPlayerDamage", 1);
        if(params.getString("battle_type").equals(BATTLE_TYPE_PVP)){
            setObjVar(defendingShip, "intPvPDamageOnly",1);
        }
        setObjVar(defendingShip, "role", "defense");
        setObjVar(defendingShip, "spawner", self);
        setObjVar(self, "defendingShip", defendingShip);
        attachScript(defendingShip, "systems.gcw.space.capital_ship");

        makeComponentsUntargetable(defendingShip);

        // create configuration parameters that will be used throughout battle.
        params.put("controller", controller);
        params.put("defendingFaction", defendingFaction);
        params.put("battleLocation", spaceLocation);
        params.put("defendingShip", defendingShip);

        // spawn attacking capital ship
        LOG("space_gcw", "In startSpaceGCWBattle... starting battle in " + prepatoryTimeLength / 60.0f + " minutes.");
        messageTo(self, "spawnAttack", params, prepatoryTimeLength, false);

        return SCRIPT_CONTINUE;
    }
    public void makeComponentsUntargetable(obj_id ship) {
        LOG("space_gcw", "In makeComponentsUntargetable... now starting to disable component targeting for (" + ship + ").");
        int[] shipChassisSlots = getShipChassisSlots(ship);
        for (int shipChassisSlot : shipChassisSlots) {
            if (isShipSlotInstalled(ship, shipChassisSlot)) {
                setShipSlotTargetable(ship, shipChassisSlot, false);
            }
        }
    }
    public int spawnAttack(obj_id self, dictionary params) throws InterruptedException {
        LOG("space_gcw", "In spawnAttack... beginning to spawn attack craft.");
        // get spawn point for the attacker ship - defaults to 2500 m away from battle point (use pythag theorum, a2 + b2 = c2 where c == 2500 and b == 1000)
        location battleLocation = params.getLocation("battleLocation");
        transform attackSpawnPoint = transform.identity.setPosition_p(battleLocation.x - 1000.0f, battleLocation.y, battleLocation.z - 2291.0f);
        obj_id attackingShip;
        if(params.getString("defendingFaction").equalsIgnoreCase("rebel")){
            attackingShip = space_create.createShipHyperspace(IMPERIAL_SHIP_TEMPLATE, attackSpawnPoint);
            LOG("space_gcw", "In spawnAttack... just created attack capital ship (" + IMPERIAL_SHIP_TEMPLATE + ":" + attackingShip + ").");
            params.put("attackingFaction", "imperial");
        }
        else{
            attackingShip = space_create.createShipHyperspace(REBEL_SHIP_TEMPLATE, attackSpawnPoint);
            LOG("space_gcw", "In spawnAttack... just created attack capital ship (" + REBEL_SHIP_TEMPLATE + ":" + attackingShip + ").");
            params.put("attackingFaction", "rebel");
        }
        if(params.getString("battle_type").equals(BATTLE_TYPE_PVP)){
            setObjVar(attackingShip, "intPvPDamageOnly",1);
        }
        removeObjVar(params.getObjId("defendingShip"), "intNoPlayerDamage");
        setObjVar(attackingShip, "role", "attack");
        setObjVar(attackingShip, "spawner", self);
        setObjVar(self, "attackingShip", attackingShip);
        attachScript(attackingShip, "systems.gcw.space.capital_ship");
        params.put("attackingShip", attackingShip);

        makeComponentsUntargetable(attackingShip);

        // define the path
        transform[] flightPath = {
                transform.identity.setPosition_p(battleLocation.x - 925.0f, battleLocation.y, battleLocation.z - 1500.0f),
                transform.identity.setPosition_p(battleLocation.x - 850.0f, battleLocation.y, battleLocation.z - 750.0f),
                transform.identity.setPosition_p(battleLocation.x - 800.0f, battleLocation.y, battleLocation.z - 0.0f)
        };

        // move the attacking ship along the path
        _spaceUnitMoveTo(attackingShip, flightPath);

        LOG("space_gcw", "In spawnAttack... will spawn support craft in 20 seconds.");
        messageTo(self, "spawnSupportCraft", params, 20.0f, false);

        // set timer to keep track of battle time - set config value for length of battle
        messageTo(self, "endBattle", params, battleTimeLength, false);

        return SCRIPT_CONTINUE;
    }
    public int spawnSupportCraft(obj_id self, dictionary params) throws InterruptedException {
        LOG("space_gcw", "In spawnSupportCraft... now starting to spawn supporting craft.");
        obj_id attackingShip = params.getObjId("attackingShip");
        obj_id defendingShip = params.getObjId("defendingShip");
        LOG("space_gcw", "In spawnSupportCraft... attacking ship is obj id " + attackingShip + " and defending ship is obj id " + defendingShip + ".");

        // spawn offense & defense support craft
        Vector defenseCraft = spawnSupportShips(defendingShip);
        Vector attackCraft = spawnSupportShips(attackingShip);
        setObjVar(defendingShip, "supportCraft", defenseCraft);
        setObjVar(attackingShip, "supportCraft", attackCraft);

        // make sure all attacking craft have something to attack (this increases hate by a lot - "oooo, I so HATE that guy!").
        int spawnedShipCount = defenseCraft.size();
        for(int i=0; i < spawnedShipCount; i++){
            obj_id attacker = (obj_id) attackCraft.get(i);
            obj_id defender = (obj_id) defenseCraft.get(i);
            LOG("space_gcw","In spawnSupportCraft... forcing ship " + attacker + " to attack ship " + defender + ".");
            if(attacker != null && defender != null)
                ship_ai.spaceAttack(attacker, defender);
        }
        return SCRIPT_CONTINUE;
    }
    private static boolean battleIsNotActive(){
        obj_id self = getSelf();
        obj_id controller = getObjIdObjVar(self, "controller");
        return getIntObjVar(controller, "space_gcw." + self.toString() + ".active") == 0;
    }
    public static Vector spawnSupportShips(obj_id motherShip) throws InterruptedException{
        int totalSpawnedThisFight = 0;
        if(hasObjVar(motherShip, "totalSpawnedSupportCraft")){
            totalSpawnedThisFight = getIntObjVar(motherShip, "totalSpawnedSupportCraft");
            if(totalSpawnedThisFight >= MAX_SUPPORT_SPAWN)
                return getResizeableObjIdArrayObjVar(motherShip, "supportCraft");
        }
        String[] support_ship_types;
        String[] hero_pilots;
        if(shipGetSpaceFaction(motherShip) == 370444368){
            // rebel
            support_ship_types = REBEL_SUPPORT_CRAFT;
            hero_pilots = REBEL_HEROES;
        }
        // imperial
        else{
            support_ship_types = IMPERIAL_SUPPORT_CRAFT;
            hero_pilots = IMPERIAL_HEROES;
        }
        Vector shipList = getResizeableObjIdArrayObjVar(motherShip, "supportCraft");
        if(shipList == null) shipList = new Vector<>();
        boolean heroSpawned = false;
        if(hasObjVar(motherShip, "heroSpawned") || (hasObjVar(motherShip, "heroSpawnTime") && getIntObjVar(motherShip, "heroSpawnTime") > (getGameTime() - (60 * 10)))){
            // a hero is only eligible to spawn once every 10 minutes.
            heroSpawned = true;
        }
        obj_id ship;

        for (int i = shipList.size(); i < MAX_SUPPORT_CRAFT; i++) {
            if (heroSpawned || Math.random() > HERO_SPAWN_CHANCE) {
                ship = createUnit(motherShip, support_ship_types[rand(0, support_ship_types.length - 1)], 200.0f);
                attachScript(ship, "systems.gcw.space.support_ship");
                setObjVar(ship, "motherShip", motherShip);
                LOG("space_gcw", "In spawnSupportShips... just spawned a regular ship (" + ship + ":" + getTemplateName(ship) + ") #" + (i+1) + " / " + MAX_SUPPORT_CRAFT + " ships.");
            } else {
                dictionary pilot = choosePilot(hero_pilots);
                if(pilot == null){
                    LOG("space_gcw", "Could not find an ace pilot to spawn!");
                    continue;
                }
                ship = createUnit(motherShip, pilot.getString("ship_template"), 100.0f);
                setName(ship, new string_id("space/ship_names", pilot.getString("ship_name")));
                attachScript(ship, "systems.gcw.space.hero_ship");
                setObjVar(ship, "motherShip", motherShip);
                setObjVar(ship, "ace_name", pilot.getString("hero_name"));
                setObjVar(motherShip, "heroSpawned", true);
                setObjVar(motherShip, "heroSpawnTime", getGameTime());
                heroSpawned = true;
                LOG("space_gcw", "In spawnSupportShips... just spawned a hero ship (" + ship + ":" + pilot.getString("hero_name") + ") #" + (i+1) + " / " + MAX_SUPPORT_CRAFT + " ships.");
            }

            transform[] trPatrolPoints = ship_ai.createPatrolPathLoiter(getTransform_o2p(motherShip), 500.0f, 1000.0f);
            ship_ai.spacePatrol(ship, trPatrolPoints);
            totalSpawnedThisFight++;
            shipList.add(ship);
        }
        LOG("space_gcw", "Returning array of ships of size " + shipList.size());
        setObjVar(motherShip, "totalSpawnedSupportCraft", totalSpawnedThisFight);
        return shipList;
    }
    private static dictionary choosePilot(String[] pilots){
        int rng = rand(0, pilots.length - 1);
        String pilot = pilots[rng];
        String[] pilotNames = dataTableGetStringColumn(HERO_PILOT_DATA, "hero_name");
        if(pilotNames == null){
            LOG("space_gcw", "In battle_spawner.choosePilot... we didn't get ANY pilot names from our datatable (" + HERO_PILOT_DATA + ")!");
            return null;
        }
        else if(pilotNames.length == 0){
            LOG("space_gcw", "In battle_spawner.choosePilot... crap - somehow we couldn't find a pilot to spawn.");
            return null;
        }
        for(int i=0; i < pilotNames.length; i++){
            if(pilot.equals(pilotNames[i])) return dataTableGetRow(HERO_PILOT_DATA, i);
        }
        return null;
    }
    public static obj_id createUnit(obj_id centralObject, String unitName, float radius) throws InterruptedException
    {
        return space_create.createShip(
                unitName,
                space_utils.getRandomPositionInSphere(getTransform_o2p(centralObject), 100.0f, radius, true)
        );
    }
    public int capitalShipDestroyed(obj_id self, dictionary params) {
        if(battleIsNotActive()) return SCRIPT_CONTINUE;
        // end the battle
        LOG("space_gcw", "Capital Ship Destroyed... ending the battle.");
        messageTo(self, "endBattle", params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int endBattle(obj_id self, dictionary params) throws InterruptedException {
        LOG("space_gcw", "In endBattle... ending the battle.");

        if(!params.containsKey("destroyedShip")){
            LOG("space_gcw", "In endBattle... reason - time ran out - making the attackers the losing side.");
            params.put("destroyedShip", params.getObjId("attackingShip"));
            params.put("losingFaction", params.getString("attackingFaction"));
            params.put("losingRole", "attack");
            params.put("supportCraft", getResizeableObjIdArrayObjVar(params.getObjId("destroyedShip"), "supportCraft"));
        }
        else{
            LOG("space_gcw", "In endBattle... reason - " + params.getString("losingFaction") + " " + params.getString("losingRole") + " capital ship destroyed.");
        }
        setObjVar(self, "last_battle_type", getObjVar(self, "battle_type"));
        distributeAwards(self, params);
        cleanup(params);
        return SCRIPT_CONTINUE;
    }
    public void distributeAwards(obj_id self, dictionary params) throws InterruptedException {
        LOG("space_gcw", "In distributeAwards... distributing the awards to the participants.");
        String battleType = getStringObjVar(self, "battle_type");
        String battleId = getStringObjVar(self, "battle_id");

        String losingFaction = params.getString("losingFaction");
        boolean imperialsWon = !losingFaction.equals("imperial");
        String shipType = " capital ship";
        String attackingFaction = getStringObjVar(self, "attackingFaction");

        // keep track of players already awarded.
        List<obj_id> pointAwardedPlayers = new ArrayList<>();

        // get all single-member participants in this battle.

        LOG("space_gcw", "In distributeAwards... now checking single ship participants.");
        // distribute to pob qualifiers
        pointAwardedPlayers.addAll(
            processShip(
                getObjVarList(self, "space_gcw.participant." + battleId),
                pobPlayerCeiling,
                self,
                pointAwardedPlayers,
                attackingFaction,
                imperialsWon,
                shipType,
                battleType,
                battleId
            )
        );

        LOG("space_gcw", "In distributeAwards... now checking gunship participants.");
        // distribute to gunship qualifiers
        pointAwardedPlayers.addAll(
            processShip(
                getObjVarList(self, "space_gcw.gunship.participant." + battleId),
                gunshipPlayerCeiling,
                self,
                pointAwardedPlayers,
                attackingFaction,
                imperialsWon,
                shipType,
                battleType,
                battleId
            )
        );

        LOG("space_gcw", "In distributeAwards... now checking POB participants.");
        // distribute to pob qualifiers
        processShip(
            getObjVarList(self, "space_gcw.pob.participant." + battleId),
            pobPlayerCeiling,
            self,
            pointAwardedPlayers,
            attackingFaction,
            imperialsWon,
            shipType,
            battleType,
            battleId
        );
    }
    public List<obj_id> processShip(obj_var_list qualifiedShips, int playerCeiling, obj_id self, List<obj_id> pointAwardedPlayers, String attackingFaction, boolean imperialsWon, String shipType, String battleType, String battleId) throws InterruptedException{
        LOG("space_gcw", "In processShip... now checking ship participants.");
        if(qualifiedShips == null || qualifiedShips.getNumItems() == 0){
            LOG("space_gcw", "In processShip... no participating ships of this type were found.");
            return Collections.EMPTY_LIST;
        }
        int numQualifiedShips = qualifiedShips.getNumItems();
        LOG("space_gcw", "In processShip... processing " + numQualifiedShips + " ships.");

        // iterate through all qualified ships
        for(int i = 0; i < numQualifiedShips; i++){
            obj_var shipObjVar = qualifiedShips.getObjVar(i);
            if(shipObjVar == null) continue;

            obj_id ship = obj_id.getObjId(Long.parseLong(shipObjVar.getName()));
            LOG("space_gcw", "In processShip... now processing ship " + ship + ".");

            // make sure this ship is still in the game.
            if(!isValidId(ship)){
                LOG("space_gcw", "In processShip... ship " + ship + " is no longer valid (destroyed? zoned out?) so skipping.");
                continue;
            }
            obj_id[] currentShipMembers = space_utils.getAllPlayersInShip(ship);
            int playerCount = currentShipMembers.length;
            // make sure players are still on this ship.
            if(playerCount == 0){
                LOG("space_gcw", "In processShip... no valid players were found on this ship.");
                continue;
            }
            LOG("space_gcw", "In processShip... found " + playerCount + " player(s) on board ship " + ship + ".");

            // if pob is not in the same system, continue on to the next gunship (no soup for YOU!)
            if(!getLocation(ship).area.equals(getLocation(self).area)){
                LOG("space_gcw", "In processShip... ship " + ship + " is no longer in the same zone as the space battle - skipping.");
                continue;
            }

            LOG("space_gcw", "In processShip... now processing " + playerCount + " VALID players on board ship " + ship + " (i.e. not ejected, still on board ship, etc).");
            for(obj_id player : currentShipMembers){
                if(!factions.isImperialorImperialHelper(player) && !factions.isRebelorRebelHelper(player)) continue;
                LOG("space_gcw", "In processShip... now processing player " + player + ".");
                // make sure the player haven't been given anything yet.
                if(pointAwardedPlayers.contains(player)){
                    LOG("space_gcw", "In processShip... player " + player + " has already been granted awards for this battle - skipping.");
                    continue;
                }
                float pointAdjustment = (playerCount > playerCeiling ? playerCeiling / playerCount : 1);
                handlePlayerAwards(player, attackingFaction, imperialsWon, shipType, battleType, battleId, pointAdjustment);
                pointAwardedPlayers.add(player);
            }
        }
        return pointAwardedPlayers;
    }
    public void handlePlayerAwards(obj_id player, String attackingFaction, boolean imperialsWon, String shipType, String battleType, String battleId, float pointAdjustmentValue) throws InterruptedException {
        LOG("space_gcw", "In handlePlayerAwards... now awarding GCW points.");
        boolean playerIsImperial = factions.isImperialorImperialHelper(player);
        boolean playerWasAttacking = (playerIsImperial && attackingFaction.equals("imperial")) || (factions.isRebelorRebelHelper(player) && attackingFaction.equals("rebel"));
        boolean playerWon = (playerIsImperial && imperialsWon) ||
                (!playerIsImperial && !imperialsWon);

        // distribute points to player
        int awardedPoints = new Float(calculateAwardedPoints(playerWon, battleType) * pointAdjustmentValue).intValue();
        LOG("space_gcw", "In handlePlayerAwards... giving " + awardedPoints + " GCW points to player \"" + getPlayerName(player) + "\" (" + player + ").");
        gcw.grantModifiedGcwPoints(
                player,
                awardedPoints,
                battleType.equals(BATTLE_TYPE_PVP) ? gcw.GCW_POINT_TYPE_SPACE_PVP : gcw.GCW_POINT_TYPE_SPACE_PVE,
                battleId
        );

        int awardedTokens = calculateAwardedTokens(
                playerWon,
                battleType,
                playerIsImperial ? "Imperial" : "Rebel"
        );

        // tell player they've won/lost
        sendSystemMessage(
                player,
                constructFinishedBattleMessage(
                        playerWasAttacking,
                        playerWon,
                        imperialsWon ? "Imperial" : "Rebel",
                        imperialsWon ? "Rebel" : "Imperial",
                        shipType,
                        attackingFaction,
                        playerIsImperial ? "Imperial" : "Rebel",
                        awardedTokens
                ),
                null
        );


        LOG("space_gcw", "In handlePlayerAwards... now giving out coins to players.");
        // distribute tokens to player
        if(awardGcwTokens(player, awardedTokens, playerIsImperial ? "imperial" : "rebel")) {
            LOG("space_gcw", "In handlePlayerAwards... giving " + awardedTokens + " " + (playerIsImperial ? "Imperial" : "Rebel") + " space gcw tokens to player \"" + getPlayerName(player) + "\" (" + player + ").");
        }
        else{
            LOG("space_gcw", "In handlePlayerAwards... unable to reward player \"" + getPlayerName(player) + "\" (" + player + ") any space GCW tokens!");
        }

    }
    public boolean awardGcwTokens(obj_id player, int quantity, String factionType) throws InterruptedException{
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (isIdValid(inventory)) {
            obj_id givenItem = static_item.createNewItemFunction("item_" + factionType + "_station_token_01_01", inventory, quantity);
            return isValidId(givenItem);
        }
        return false;
    }
    public String constructFinishedBattleMessage(boolean playerWasAttacking, boolean playerWon, String winningFaction, String losingFaction, String shipType, String attackingFaction, String tokenFaction, int tokenReward) {
        // the following messages are for the capital ship battles only.
        // imperial defense win:  "You have succesfully helped defend the Imperial capital ship from the Rebel attack force."
        // rebel defense win: "You have successfully helped defend the Rebel capital ship from the Imperial attack force."
        // imperial defense loss:  "You have failed to defend the Imperial capital ship from the Rebel attack force."
        // rebel defense loss: "You have failed to defend the Rebel capital ship from the Imperial attack force."
        // imperial attack win: "You have successfully aided in destoying the Rebel capital ship."
        // imperial attack loss: "You have failed to destroy the Rebel capital ship."
        // rebel attack win: "You have successfully aided in destroying the Imperial capital ship."
        // rebel attack loss: "You have failed to destroy the Imperial capital ship."

        // the following messages are for the capital ship battles only.
        // imperial defense win:  same as cap v cap
        // rebel defense win: same as cap v cap

        StringBuilder msg = new StringBuilder(playerWon ? "You have successfully" : "You have failed");
        msg.append(playerWasAttacking ? (playerWon ? " aided in destroying the " : " to destroy the ") : (playerWon ? " helped defend the " : " to defend the "));
        msg.append(playerWasAttacking ? (playerWon ? losingFaction : winningFaction) : (playerWon ? winningFaction : losingFaction))
                .append(shipType);
        msg.append(!playerWasAttacking ? " from the " + attackingFaction + " attack force" : "");
        msg.append(". ");
        if(tokenReward > 0) {
            msg.append("You have been rewarded ");
            msg.append(tokenReward);
            msg.append(" ");
            msg.append(tokenFaction);
            msg.append(" Station Tokens for your ");
            msg.append(playerWon ? "victory." : "participation.");
        }

        return msg.toString();
    }
    public int calculateAwardedPoints(boolean wonBattle, String battleType) {
        if(!wonBattle){
            if(battleType.equals(BATTLE_TYPE_PVE)){
                return new Float(customPointAward * customPveBattleModifier * customLossPointModifier).intValue();
            }
            else{
                return new Float(customPointAward * customPvpBattleModifier * customLossPointModifier).intValue();
            }
        }
        else{
            if(battleType.equals(BATTLE_TYPE_PVE)){
                return new Float(customPointAward * customPveBattleModifier * customWinPointModifier).intValue();
            }
            else{
                return new Float(customPointAward * customPvpBattleModifier * customWinPointModifier).intValue();
            }
        }

    }
    public int calculateAwardedTokens(boolean wonBattle, String battleType, String faction) {
        if(!wonBattle){
            if(battleType.equals(BATTLE_TYPE_PVE)){
                return new Float(customTokenAward * customPveBattleModifier * customLossTokenModifier).intValue();
            }
            else{
                return new Float(customTokenAward * customPvpBattleModifier * customLossTokenModifier).intValue();
            }
        }
        else{
            if(battleType.equals(BATTLE_TYPE_PVE)){
                return new Float(customTokenAward * customPveBattleModifier * customWinTokenModifier).intValue();
            }
            else{
                return new Float(customTokenAward * customPvpBattleModifier * customWinTokenModifier).intValue();
            }
        }

    }
    public void cleanup(dictionary params) throws InterruptedException {
        LOG("space_gcw", "In cleanup... now cleaning up the battle.");
        obj_id self = getSelf();

        // despawn ships
        obj_id attackingShip = params.getObjId("attackingShip");
        obj_id defendingShip = params.getObjId("defendingShip");
        LOG("space_gcw", "In cleanup... attacking ship is (" + attackingShip + ") and defending ship is (" + defendingShip + ").");

        LOG("space_gcw", "In cleanup... despawning attack support craft...");
        Vector supportShips;
        if(isValidId(attackingShip)) {
            supportShips = getResizeableObjIdArrayObjVar(attackingShip, "supportCraft");
        }
        else{
            supportShips = params.getResizeableObjIdArray("supportCraft");
        }
        if(supportShips != null) {
            for (Object ship : supportShips) {
                LOG("space_gcw", "...despawning attack ship (" + ship + ")");
                if(isValidId((obj_id) ship)){
                    detachAllScripts((obj_id) ship);
                    destroyObjectHyperspace((obj_id) ship);
                }
            }
        }
        LOG("space_gcw", "In cleanup... despawning defense support craft...");
        if(isValidId(defendingShip)) {
            supportShips = getResizeableObjIdArrayObjVar(defendingShip, "supportCraft");
        }
        else{
            supportShips = params.getResizeableObjIdArray("supportCraft");
        }
        if(supportShips != null) {
            for (Object ship : supportShips) {
                LOG("space_gcw", "...despawning defense ship (" + ship + ")");
                if(isValidId((obj_id) ship)){
                    detachAllScripts((obj_id) ship);
                    destroyObjectHyperspace((obj_id) ship);
                }
            }
        }
        LOG("space_gcw", "In cleanup... despawning attack capital ship...");

        if(isValidId(attackingShip)){
            detachAllScripts(attackingShip);
            destroyObjectHyperspace(attackingShip);
        }

        // set obj vars on controller for next battle
        obj_id controller = params.getObjId("controller");
        String losingFaction = params.getString("losingFaction");
        String losingRole = params.getString("losingRole");
        String defendingFaction;
        if(losingFaction.equals("imperial") && losingRole.equals("attack")){
            defendingFaction = "rebel";
        }
        else if(losingFaction.equals("imperial") && losingRole.equals("defense")){
            defendingFaction = "imperial";
        }
        else if(losingFaction.equals("rebel") && losingRole.equals("attack")){
            defendingFaction = "imperial";
        }
        else{
            defendingFaction = "rebel";
        }
        setObjVar(controller, "space_gcw." + self.toString() + ".lastWinningFaction", losingFaction.equals("imperial") ? "rebel" : "imperial");
        setObjVar(controller, "space_gcw." + self.toString() + ".lastDefendingFaction", defendingFaction);
        setObjVar(controller, "space_gcw." + self.toString() + ".lastWinningRole", losingRole.equals("attack") ? "defense" : "attack");

        if(isValidId(defendingShip)){
            messageTo(self, "despawnDefendingShip", params, 60.0f, false);
        }
        else{
            // tell the controller we've ended the battle
            setObjVar(controller, "space_gcw." + self.toString() + ".active", 0);
        }

        // clean up objvars on self
        int lastRunTime = getIntObjVar(self, "last_battle_time");
        removeAllObjVars(self);
        setObjVar(self, "last_battle_time", lastRunTime);
        setObjVar(self, "controller", controller);
    }

    public int despawnDefendingShip(obj_id self, dictionary params) {
        // tell the controller we've ended the battle
        obj_id controller = params.getObjId("controller");
        setObjVar(controller, "space_gcw." + self.toString() + ".active", 0);

        obj_id defendingShip = params.getObjId("defendingShip");
        detachAllScripts(defendingShip);
        LOG("space_gcw", "In cleanup... despawning defending capital ship...");
        destroyObjectHyperspace(defendingShip);
        return SCRIPT_CONTINUE;
    }

    public void getSettings() throws InterruptedException {
        LOG("space_gcw", "Getting Settings...");
        // get customized settings
        customPveBattleModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwPvETokenModifier"));
        if (customPveBattleModifier < 0) customPveBattleModifier = DEFAULT_PVE_TOKEN_MULTIPLIER;

        customPvpBattleModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwPvPTokenModifier"));
        if (customPvpBattleModifier < 0) customPvpBattleModifier = DEFAULT_PVP_TOKEN_MULTIPLIER;

        customWinTokenModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwWinTokenModifier"));
        if (customWinTokenModifier < 0) customWinTokenModifier = DEFAULT_WIN_TOKEN_MULTIPLIER;

        customLossTokenModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwLossTokenModifier"));
        if (customLossTokenModifier < 0) customLossTokenModifier = DEFAULT_LOSS_TOKEN_MULTIPLIER;

        customTokenAward = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwTokenAward"));
        if (customTokenAward < 0) customTokenAward = DEFAULT_TOKEN_AWARD;

        customPointAward = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwPointAward"));
        if (customPointAward < 0) customPointAward = DEFAULT_POINT_AWARD;

        customWinPointModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwWinPointModifier"));
        if (customWinPointModifier < 0) customWinPointModifier = DEFAULT_WIN_TOKEN_MULTIPLIER;

        customLossPointModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwLossPointModifier"));
        if (customLossPointModifier < 0) customLossPointModifier = DEFAULT_LOSS_TOKEN_MULTIPLIER;

        pobPlayerCeiling = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwPobPlayerCeiling"));
        if (pobPlayerCeiling < 0) pobPlayerCeiling = DEFAULT_POB_PLAYER_CEILING;

        gunshipPlayerCeiling = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwGunshipPlayerCeiling"));
        if (gunshipPlayerCeiling < 0) gunshipPlayerCeiling = DEFAULT_GUNSHIP_PLAYER_CEILING;

        battleTimeLength = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwLengthOfBattle"));
        if (battleTimeLength <= 0) battleTimeLength = DEFAULT_BATTLE_TIME_LENGTH;

        prepatoryTimeLength = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwPrepatoryTime"));
        if (prepatoryTimeLength <= 0) prepatoryTimeLength = DEFAULT_BATTLE_TIME_PREPATORY;
        LOG("space_gcw", "Done getting settings...");
    }
}
