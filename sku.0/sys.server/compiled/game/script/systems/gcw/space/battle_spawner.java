package script.systems.gcw.space;

import script.*;
import script.library.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class battle_spawner extends script.base_class {

    public static final String IMPERIAL_SHIP_TEMPLATE = "object/ship/imperial_lancer.iff";
    public static final String REBEL_SHIP_TEMPLATE = "object/ship/nebulon_frigate.iff";
    public static final String IMPERIAL_STATION_TEMPLATE = "object/ship/spacestation_imperial.iff";
    public static final String REBEL_STATION_TEMPLATE = "object/ship/spacestation_rebel.iff";
    public static float BATTLE_TIME_PREPATORY = 900.0f;  // 900 == 15 minutes
    public static float BATTLE_TIME_LENGTH = 3600.0f;  // 3600 == 60 minutes
    public static float MAX_SUPPORT_CRAFT = 15;
    public static final float HERO_SPAWN_CHANCE = 0.20f;  // 0.20f == 20% chance a hero will spawn
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
            "object/ship/tieaggressor_tier7.iff",
            "object/ship/tiebomber_tier7.iff",
            "object/ship/tiefighter_tier7.iff",
            "object/ship/tieinterceptor_tier7.iff",
            "object/ship/tieoppressor_tier7.iff"
    };
    public static final String[] REBEL_SUPPORT_CRAFT = {
            "object/ship/awing_tier7.iff",
            "object/ship/bwing_tier7.iff",
            "object/ship/xwing_tier7.iff",
            "object/ship/ywing_tier7.iff",
            "object/ship/z95_tier7.iff"
    };
    public static final String[][] IMPERIAL_HEROES = {
            {"object/ship/firespray_tier10.iff","Boba Fett"},
            {"object/ship/tieoppressor_tier9.iff","Maarek Steeke"},
            {"object/ship/tieinterceptor_tier9.iff","Soontir Fel"},
            {"object/ship/tieadvanced_tier10.iff","Darth Vader"},
            {"object/ship/tieadvanced_tier9.iff","Mara Jade"},
            {"object/ship/tiefighter_tier8.iff","Vindoo Barvel"},
            {"object/ship/tiefighter_tier8.iff","Turr Phennir"},
            {"object/ship/tieoppressor_tier9.iff","Iran Ryad"},
            {"object/ship/tieinterceptor_tier9.iff","DS-181-4"},
            {"object/ship/tiefighter_tier9.iff","Chiraneau"},
    };
    public static final String[][] REBEL_HEROES = {
            {"object/ship/xwing_tier10.iff","Luke Skywalker"},
            {"object/ship/yt1300_tier10.iff","Lando Calrissian"},
            {"object/ship/ywing_tier10.iff","Derek \"Hobbie\" Klivian"},
            {"object/ship/xwing_tier10.iff","Wedge Antilles"},
            {"object/ship/awing_tier9.iff","Tycho Celchu"},
            {"object/ship/xwing_tier9.iff","Keyan Farlander"},
            {"object/ship/xwing_tier9.iff","Tarrin Datch"},
            {"object/ship/awing_tier9.iff","Pash Cracken"},
            {"object/ship/bwing_tier9.iff","Jan Ors"},
            {"object/ship/xwing_tier9.iff","Arhul Narra"},
            {"object/ship/awing_tier9.iff","Arvel Crynyd"}
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

    // This script is the spawner for the Space GCW (GCW 2) battles.
    // It uses Tatooine to track battles (the controller) and places the following object vars
    // on the planet object to keep track of various aspects:
    //
    // space_gcw.<zone>.active = <0, 1>  -- <zone> is the space zone, 0 means inactive, 1 means active
    // space_gcw.participant.<battle_id>.<participant>  -- <battle_id> is the battle identifier, <participant> is the obj id of the player.


    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "registerWithController", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "registerWithController", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isIdValid(speaker) || !isGod(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        String[] words = split(text, ' ');
        dictionary params = new dictionary();
        switch(words[0]){
            case "start":
                sendSystemMessageTestingOnly(speaker, "Starting battle.");
                messageTo(self, "startBattle", null, 3, false);
                break;
            case "end":
                sendSystemMessageTestingOnly(speaker, "Ending battle.");
                params.put("type", "none");
                messageTo(self, "endBattle", null, 3, false);
                break;
            case "cleanup":
                sendSystemMessageTestingOnly(speaker, "Cleaning up.");
                params.put("type", "cleanup");
                messageTo(self, "endBattle", params, 3, false);
                break;
            case "reset":
                sendSystemMessageTestingOnly(speaker, "Cleaning up.");
                params.put("type", "reset");
                messageTo(self, "endBattle", params, 3, false);
                break;
            default:
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public int registerWithController(obj_id self, dictionary params) throws InterruptedException {
        obj_id controller = getPlanetByName("tatooine");
        location battleLocation = getLocation(self);
        setObjVar(self, "controller", controller);
        setObjVar(controller, "space_gcw." + battleLocation.area + ".spawner", self);
        getSettings();
        return SCRIPT_CONTINUE;
    }
    public int startBattle(obj_id self, dictionary params) {
        // make sure we're not already having a battle
        obj_id controller = getObjIdObjVar(self, "controller");
        String battleId = getLocation(self).area + "_" + getGameTime();
        if(getIntObjVar(controller, "space_gcw." + self.toString() + ".active") == 1) return SCRIPT_CONTINUE;
        String battleType = BATTLE_TYPES[rand(0,1)];

        setObjVar(self, "battle_type", battleType);
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
            defendingShip = createObjectHyperspace(IMPERIAL_SHIP_TEMPLATE, defenseSpawnPoint, null);
        }
        else{
            defendingShip = createObjectHyperspace(REBEL_SHIP_TEMPLATE, defenseSpawnPoint, null);
        }
        setObjVar(defendingShip, "role", "defense");
        setObjVar(defendingShip, "spawner", self);
        attachScript(defendingShip, "script.systems.gcw.space.capital_ship");

        // create configuration parameters that will be used throughout battle.
        params.put("controller", controller);
        params.put("defendingFaction", defendingFaction);
        params.put("battleLocation", spaceLocation);
        params.put("defendingShip", defendingShip);

        // spawn attacking capital ship
        messageTo(self, "spawnAttack", params, BATTLE_TIME_PREPATORY, false);

        return SCRIPT_CONTINUE;
    }
    public int spawnAttack(obj_id self, dictionary params) {
        // get spawn point for the attacker ship - defaults to 2500 m away from battle point (use pythag theorum, a2 + b2 = c2 where c == 2500 and b == 1000)
        location battleLocation = params.getLocation("battleLocation");
        transform attackSpawnPoint = transform.identity.setPosition_p(battleLocation.x - 1000.0f, battleLocation.y, battleLocation.z - 2291.0f);
        obj_id attackingShip;
        if(params.getString("defendingFaction").equalsIgnoreCase("rebel")){
            attackingShip = createObjectHyperspace(IMPERIAL_SHIP_TEMPLATE, attackSpawnPoint, null);
            params.put("attackingFaction", "imperial");
        }
        else{
            attackingShip = createObjectHyperspace(REBEL_SHIP_TEMPLATE, attackSpawnPoint, null);
            params.put("attackingFaction", "rebel");
        }
        setObjVar(attackingShip, "role", "attack");
        setObjVar(attackingShip, "spawner", self);
        params.put("attackingShip", attackingShip);

        // define the path
        transform[] flightPath = {
                transform.identity.setPosition_p(battleLocation.x - 500.0f, battleLocation.y, battleLocation.z - 1500.0f),
                transform.identity.setPosition_p(battleLocation.x - 250.0f, battleLocation.y, battleLocation.z - 900.0f),
                transform.identity.setPosition_p(battleLocation.x - 0.0f, battleLocation.y, battleLocation.z - 600.0f)
        };

        params.put("flightPath", flightPath);

        // move the attacking ship along the path
        _spaceUnitMoveTo(attackingShip, flightPath);

        messageTo(self, "spawnSupportCraft", params, 20.0f, false);

        // set timer to keep track of battle time - set config value for length of battle
        messageTo(self, "endBattle", params, BATTLE_TIME_LENGTH, false);

        return SCRIPT_CONTINUE;
    }
    public int spawnSupportCraft(obj_id self, dictionary params) throws InterruptedException {
        obj_id attackingShip = params.getObjId("attackingShip");
        obj_id defendingShip = params.getObjId("defendingShip");
        List<obj_id> attackCraft;
        List<obj_id> defenseCraft;

        // spawn offense & defense support craft
        defenseCraft = spawnSupportShips(defendingShip);
        attackCraft = spawnSupportShips(attackingShip);
        setObjVar(defendingShip, "supportCraft", defenseCraft);
        setObjVar(attackingShip, "supportCraft", attackCraft);

        // make sure all attacking craft have something to attack (this increases hate by a lot - "oooo, I so HATE that guy!").
        int spawnedShipCount = defenseCraft.size();
        for(int i=0; i < spawnedShipCount; i++){
            ship_ai.spaceAttack(attackCraft.get(i), defenseCraft.get(i));
        }
        return SCRIPT_CONTINUE;
    }
    public static List<obj_id> spawnSupportShips(obj_id motherShip) throws InterruptedException{
        String[] support_ship_types;
        String[][] hero_ships;
        if(shipGetSpaceFaction(motherShip) == 370444368){
            // rebel
            support_ship_types = REBEL_SUPPORT_CRAFT;
            hero_ships = REBEL_HEROES;
        }
        // imperial
        else{
            support_ship_types = IMPERIAL_SUPPORT_CRAFT;
            hero_ships = IMPERIAL_HEROES;
        }
        Vector<obj_id> shipList = getResizeableObjIdArrayObjVar(motherShip, "supportCraft");
        if(shipList == null) shipList = new Vector<>();
        boolean heroSpawned = false;
        obj_id ship;

        for (int i = shipList.size(); i < MAX_SUPPORT_CRAFT; i++) {
            if (heroSpawned || rand(0f, 1f) < HERO_SPAWN_CHANCE) {
                ship = createUnit(motherShip, support_ship_types[rand(0, support_ship_types.length - 1)], 200.0f);
                attachScript(ship, "systems.gcw.space.support_ship");
                setObjVar(ship, "motherShip", motherShip);
            } else {
                int rng = rand(0, hero_ships.length - 1);
                ship = createUnit(motherShip, hero_ships[rng][0], 100.0f);
                setName(ship, hero_ships[rng][1]);
                attachScript(ship, "systems.gcw.space.hero_ship");
                setObjVar(ship, "motherShip", motherShip);
                setObjVar(motherShip, "attackHeroSpawned", true);
                heroSpawned = true;
            }
            shipList.add(ship);
        }
        return shipList;
    }
    public static vector getRandomPosition(float radius) throws InterruptedException
    {
        return new vector(
                radius * 2.0f * (0.5f - (float)Math.random()),
                radius * 2.0f * (0.5f - (float)Math.random()),
                radius * 2.0f * (0.5f - (float)Math.random())
        );
    }
    public static obj_id createUnit(obj_id self, String unitName, float radius, vector position) throws InterruptedException
    {
        final vector randomPosition = new vector(getRandomPosition(radius));
        return space_create.createShip(
                unitName,
                transform.identity.move_p(
                        new vector(
                                position.x + randomPosition.x,
                                position.y + randomPosition.y,
                                position.z + randomPosition.z
                        )
                )
        );
    }
    public static obj_id createUnit(obj_id self, String unitName, float radius) throws InterruptedException
    {
        return createUnit(self, unitName, radius, vector.zero);
    }
    public int capitalShipDestroyed(obj_id self, dictionary params) throws InterruptedException {
        // end the battle
        messageTo(self, "endBattle", params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int endBattle(obj_id self, dictionary params) throws InterruptedException {
        // tell the controller we've ended the battle
        setObjVar(getObjIdObjVar(self, "controller"), "space_gcw." + self.toString() + ".active", 0);
        if(!params.containsKey("destroyedShip")){
            params.put("destroyedShip", params.getObjId("attackingShip"));
            params.put("losingFaction", params.getString("attackingFaction"));
        }
        distributeAwards(self, params);
        cleanup(params);
        reset();
        return SCRIPT_CONTINUE;
    }
    public void distributeAwards(obj_id self, dictionary params) throws InterruptedException {
        String battleType = getStringObjVar(self, "battle_type");
        obj_id controller = getObjIdObjVar(self, "controller");
        String battleId = getStringObjVar(self, "battle_id");

        String losingFaction = params.getString("losingFaction");
        String winningFaction = params.getString("losingFaction").equals("imperial") ? "imperial" : "rebel";
        boolean imperialsWon = !losingFaction.equals("imperial");
        boolean shipWasDefending = params.getString("role").equals("defense");
        obj_id destroyedShip = params.getObjId("destroyedShip");
        String shipTemplate = getTemplateName(destroyedShip);
        String shipType;
        String attackingFaction = !shipWasDefending ? losingFaction : winningFaction;
        switch (shipTemplate) {
            case IMPERIAL_SHIP_TEMPLATE:
                shipType = "Imperial capital ship";
                break;
            case REBEL_SHIP_TEMPLATE:
                shipType = "Rebel capital ship";
                break;
            case IMPERIAL_STATION_TEMPLATE:
                shipType = "Imperial Space Station";
                break;
            case REBEL_STATION_TEMPLATE:
                shipType = "Rebel Space Station";
                break;
            default:
                shipType = "Hulking Behemoth";
        }

        // keep track of players already awarded.
        List<obj_id> pointAwardedPlayers = new ArrayList<>();

        // get all single-member participants in this battle.
        obj_var_list participants = getObjVarList(controller, "space_gcw.participant." + battleId);
        int numParticipants = participants.getNumItems();

        attackingFaction = attackingFaction.equals("imperial") ? "Imperial" : "Rebel";

        // distribute to single pilots (non-pob or gunship)
        for (int i = 0; i < numParticipants; i++) {
            obj_var participantVar = participants.getObjVar(i);
            if (participantVar == null) continue;
            obj_id player = obj_id.getObjId(Long.parseLong(participantVar.getName()));

            // if player is not in the same system, continue on to the next player (no soup for YOU!)
            if (!getLocation(player).area.equals(getLocation(self).area)) continue;
            handlePlayerAwards(player, attackingFaction, imperialsWon, shipType, battleType, battleId, 1);
            pointAwardedPlayers.add(player);
        }

        // distribute to pob qualifiers
        pointAwardedPlayers.addAll(
                processMultiPassengerShip(
                        getObjVarList(controller, "space_gcw.pob.participant." + battleId),
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

        // distribute to gunship qualifiers
        pointAwardedPlayers.addAll(
                processMultiPassengerShip(
                        getObjVarList(controller, "space_gcw.gunship.participant." + battleId),
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
    }
    public List<obj_id> processMultiPassengerShip(obj_var_list qualifiedShips, int playerCeiling, obj_id self, List<obj_id> pointAwardedPlayers, String attackingFaction, boolean imperialsWon, String shipType, String battleType, String battleId) throws InterruptedException{
        int numQualifiedShips = qualifiedShips.getNumItems();

        // iterate through all qualified ships
        for(int i = 0; i < numQualifiedShips; i++){
            obj_var shipObjVar = qualifiedShips.getObjVar(i);
            if(shipObjVar == null) continue;

            obj_id ship = obj_id.getObjId(Long.parseLong(shipObjVar.getName()));

            // make sure this ship is still in the game.
            if(!isValidId(ship)) continue;
            List<obj_id> currentShipMembers = Arrays.asList(space_utils.getAllPlayersInShip(ship));
            // make sure players are still on this ship.
            if(currentShipMembers.size() == 0) continue;

            // if pob is not in the same system, continue on to the next gunship (no soup for YOU!)
            if(!getLocation(ship).area.equals(getLocation(self).area)) continue;

            obj_id[] players = shipObjVar.getObjIdArrayData();
            int playerCount = 0;
            for(obj_id player : players){
                if(currentShipMembers.contains(player)) playerCount++;
            }
            for(obj_id player : players){
                // make sure player is still in the ship and not somewhere else (i.e. their own ship) and make sure they haven't been given anything yet.
                if(!currentShipMembers.contains(player) || pointAwardedPlayers.contains(player)) continue;
                float pointAdjustment = (playerCount > playerCeiling ? playerCeiling / playerCount : 1);
                handlePlayerAwards(player, attackingFaction, imperialsWon, shipType, battleType, battleId, pointAdjustment);
                pointAwardedPlayers.add(player);
            }
        }
        return pointAwardedPlayers;
    }
    public void handlePlayerAwards(obj_id player, String attackingFaction, boolean imperialsWon, String shipType, String battleType, String battleId, float pointAdjustmentValue) throws InterruptedException {
        boolean playerIsImperial = factions.isImperial(player);
        boolean playerWasAttacking = playerIsImperial && attackingFaction.equals("Imperial");
        boolean playerWon = (playerIsImperial && imperialsWon) ||
                (!playerIsImperial && !imperialsWon);

        // distribute points to player
        gcw.grantModifiedGcwPoints(
                player,
                new Float(calculateAwardedPoints(playerWon, battleType) * pointAdjustmentValue).intValue(),
                battleType.equals(BATTLE_TYPE_PVP) ? gcw.GCW_POINT_TYPE_SPACE_PVP : gcw.GCW_POINT_TYPE_SPACE_PVE,
                battleId
        );

        // tell player they've won/lost
        sendSystemMessage(
                player,
                constructFinishedBattleMessage(
                        playerWasAttacking,
                        playerWon,
                        shipType,
                        attackingFaction,
                        playerIsImperial ? "Imperial" : "Rebel",
                        calculateAwardedTokens(
                                playerWon,
                                battleType,
                                playerIsImperial ? "Imperial" : "Rebel"
                        )
                ),
                null
        );

        // distribute tokens to player

    }
    public String constructFinishedBattleMessage(boolean playerWasAttacking, boolean playerWon, String shipType, String attackingFaction, String tokenFaction, int tokenReward) {
        // the following messages are for the capital ship battles only.
        // imperial defense win:  "You have succesfully helped defend the Imperial capital ship from the Rebel attack force."
        // rebel defense win: "You have successfully helped defend the Rebel capital ship from the Imperial attack force."
        // imperial defense loss:  "You have failed to defend the Imperial capital ship from the Rebel attack force."
        // rebel defense loss: "You have failed to defend the Rebel capital ship from the Imperial attack force."
        // imperial attack win: "You have successfully aided in destoying the Rebel capital ship."
        // imperial attack loss: "You have failed to destroy the Rebel capital ship."
        // rebel attack win: "You have successfully aided in destroying the Imperial capital ship."
        // rebel attack loss: "You have failed to destroy the Imperial capital ship."

        // the following messages are for the space station/capital ship battles only.
        // imperial defense win:  same as cap v cap
        // rebel defense win: same as cap v cap
        // imperial defense loss: "You have failed to defend the Imperial Space Station from the Rebel attack force."
        // rebel defense loss: "You have failed to defend the Rebel Space Station from the Imperial attack force."
        // imperial attack win: "You have successfully aided in destroying the Rebel Space Station."
        // rebel attack win: "You have successfully aided in destroying the Imperial Space Station."
        // imperial attack loss: "You have failed to destroy the Rebel Space Station"
        // rebel attack loss: "You have failed to destroy the Imperial Space Station."

        StringBuilder msg = new StringBuilder(playerWon ? "You have successfully" : "You have failed");
        msg.append(playerWasAttacking ? (playerWon ? " aided in destroying the " : " to destroy the ") : (playerWon ? " helped defend the " : " to defend the "));
        msg.append(shipType);
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
    public int calculateAwardedPoints(boolean wonBattle, String battleType) throws InterruptedException {
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
    public int calculateAwardedTokens(boolean wonBattle, String battleType, String faction) throws InterruptedException {
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
        // despawn ships

        // clean up objvars on controller

        // clean up objvars on self
        removeAllObjVars(getSelf());
    }
    public void reset() throws InterruptedException {

    }

    public void getSettings() throws InterruptedException {
        // get customized settings
        customPveBattleModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwPvETokenModifier"));
        if (customPveBattleModifier < 0) customPveBattleModifier = DEFAULT_PVE_TOKEN_MULTIPLIER;

        customPvpBattleModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwPvPTokenModifier"));
        if (customPvpBattleModifier < 0) customPvpBattleModifier = DEFAULT_PVP_TOKEN_MULTIPLIER;

        customWinTokenModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwWinTokenModifier"));
        if (customWinTokenModifier < 0) customWinTokenModifier = DEFAULT_WIN_TOKEN_MULTIPLIER;

        customLossTokenModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwLossTokenModifier"));
        if (customLossTokenModifier < 0) customLossTokenModifier = DEFAULT_LOSS_TOKEN_MULTIPLIER;

        customTokenAward = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwPvPTokenAward"));
        if (customTokenAward < 0) customTokenAward = DEFAULT_TOKEN_AWARD;

        customPointAward = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwPvPPointAward"));
        if (customPointAward < 0) customPointAward = DEFAULT_POINT_AWARD;

        customWinPointModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwWinPointModifier"));
        if (customWinPointModifier < 0) customWinPointModifier = DEFAULT_WIN_TOKEN_MULTIPLIER;

        customLossPointModifier = utils.stringToFloat(getConfigSetting("GameServer", "spaceGcwLossPointModifier"));
        if (customLossPointModifier < 0) customLossPointModifier = DEFAULT_LOSS_TOKEN_MULTIPLIER;

        pobPlayerCeiling = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwPobPlayerCeiling"));
        if (pobPlayerCeiling < 0) pobPlayerCeiling = DEFAULT_POB_PLAYER_CEILING;

        gunshipPlayerCeiling = utils.stringToInt(getConfigSetting("GameServer", "spaceGcwGunshipPlayerCeiling"));
        if (gunshipPlayerCeiling < 0) gunshipPlayerCeiling = DEFAULT_GUNSHIP_PLAYER_CEILING;
    }
}
