package script.working;

import script.deltadictionary;
import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

import java.util.StringTokenizer;
import java.util.Vector;

public class balancetest extends script.base_script
{
    public balancetest()
    {
    }
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String STAT_BALANCE_TABLE = "datatables/mob/stat_balance.iff";
    public static final boolean CONST_FLAG_DO_LOGGING = true;
    public int checkDifficulty(obj_id self, dictionary params) throws InterruptedException
    {
        int intRawDifficulty = getLevel(self);
        sendSystemMessageTestingOnly(self, "getLevel returned " + intRawDifficulty);
        if (intRawDifficulty == 0)
        {
            sendSystemMessageTestingOnly(self, "you lost your difficulty!");
            deltadictionary dctScriptVars = self.getScriptVars();
            sendSystemMessageTestingOnly(self, "Scriptvars are " + dctScriptVars.toString());
        }
        messageTo(self, "checkDifficulty", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("working/balancetest/" + section, message);
        }
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        debugLogging("//// OnHearSpeech: ", "////>>>> Enter");
        if (objSpeaker != self)
        {
            debugLogging("//// OnHearSpeech: ", "////>>>> 1");
            return SCRIPT_CONTINUE;
        }
        if (!strText.contains("b "))
        {
            debugLogging("//// OnHearSpeech: ", "////>>>> 2 - index is: " + strText.indexOf("b "));
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = new StringTokenizer(strText);
        int numArgs = st.countTokens();
        String actionUnlock = st.nextToken();
        String actionName = st.nextToken();
        switch (actionName) {
            case "help":
                sendSystemMessageTestingOnly(self, "REMINDER: key phrases for balancetest are: or 'b wipe'  or 'b spawn <creatureName>' or 'b newlevel <level>'");
                sendSystemMessageTestingOnly(self, " 'b clev <creature level>' or 'b wdam <mindamage> <maxdamage>' (can enter just one damage value to set both same)' ");
                sendSystemMessageTestingOnly(self, " 'b newlevel <newlevel> will mod all current and following creatures. A <newlevel> value of 0 will remove a stored level delta variable ");
                break;
            case "clev": {
                debugLogging("//// OnHearSpeech: ", "////>>>> 3");
                if (numArgs < 3) {
                    sendSystemMessageTestingOnly(self, "You've got to have at least TWO arguments to spawn a creature  'b clev <creature level> ");
                    debugLogging("//// OnHearSpeech: ", "////>>>> 4");
                    return SCRIPT_CONTINUE;
                }
                String levelStr = st.nextToken();
                int level = utils.stringToInt(levelStr);
                sendSystemMessageTestingOnly(self, "creature level selected: " + level);
                dictionary creatureDict = null;
                Vector validCreatureNames = new Vector();
                validCreatureNames.setSize(0);
                int[] creatureTabLevels = dataTableGetIntColumn(CREATURE_TABLE, "BaseLevel");
                for (int i = 0; i < creatureTabLevels.length; i++) {
                    if (creatureTabLevels[i] == level) {
                        creatureDict = dataTableGetRow(CREATURE_TABLE, i);
                        float aggressiveness = creatureDict.getFloat("aggressive");
                        String creatureName = creatureDict.getString("creatureName");
                        debugLogging("//// OnHearSpeech: ", "////>>>> Checking a creature: " + creatureName + " its aggressiveness is: " + aggressiveness);
                        if (aggressiveness > 0.0) {
                            utils.addElement(validCreatureNames, creatureName);
                        }
                    }
                }
                debugLogging("//// OnHearSpeech: ", "////>>>> TOTAL MATCHES FOUND IS: " + validCreatureNames.size());
                if (validCreatureNames.size() > 0) {
                    debugLogging("//// OnHearSpeech: ", "////>>>> 7");
                    int matchNumber = 1;
                    if (validCreatureNames.size() > 1) {
                        matchNumber = rand(1, validCreatureNames.size());
                    }
                    creatureDict = dataTableGetRow(CREATURE_TABLE, ((String) validCreatureNames.get(matchNumber - 1)));
                } else {
                    sendSystemMessageTestingOnly(self, "no matches in creatures.tab found for level: " + level);
                    return SCRIPT_CONTINUE;
                }
                if (creatureDict == null) {
                    sendSystemMessageTestingOnly(self, "no creatures found at level: " + level);
                    return SCRIPT_CONTINUE;
                } else {
                    sendSystemMessageTestingOnly(self, "creature selected for spawn is: " + creatureDict.getString("creatureName"));
                }
                String creatureName = creatureDict.getString("creatureName");
                utils.setScriptVar(self, "clev_creature", creatureName);
                utils.setScriptVar(self, "clev_creature_level", level);
                break;
            }
            case "spawn": {
                debugLogging("//// OnHearSpeech: ", "////>>>> 8");
                if (!utils.hasScriptVar(self, "clev_creature")) {
                    sendSystemMessageTestingOnly(self, "no you haven't selected a creature to spawn");
                    return SCRIPT_CONTINUE;
                }
                int creaturelevel = utils.getIntScriptVar(self, "clev_creature_level");
                String creatureName = utils.getStringScriptVar(self, "clev_creature");
                sendSystemMessageTestingOnly(self, "we're going to try to spawn the level " + creaturelevel + " creature, the " + creatureName);
                int newCreatureLevel = 0;
                if (utils.hasScriptVar(self, "newCreatureLevel")) {
                    newCreatureLevel = utils.getIntScriptVar(self, "newCreatureLevel");
                    sendSystemMessageTestingOnly(self, " and going to re-level the creature to be " + newCreatureLevel);
                }
                int numCreatures = 1;
                if (numArgs == 3) {
                    String numberOfCreaturesStr = st.nextToken();
                    int numberOfCreatures = utils.stringToInt(numberOfCreaturesStr);
                    if (numberOfCreatures == 9) {
                        numCreatures = rand(1, 10);
                    } else {
                        numCreatures = numberOfCreatures;
                    }
                }
                obj_id[] spawnedCreatures = new obj_id[numCreatures];
                location here = getLocation(self);
                for (int i = 0; i < numCreatures; i++) {
                    location spawnLoc = locations.getGoodLocationAroundLocation(here, 1.0f, 1.0f, 4.0f, 4.0f);
                    if (spawnLoc == null) {
                        sendSystemMessageTestingOnly(self, " spawnLoc was NULL ");
                        return SCRIPT_CONTINUE;
                    }
                    obj_id mob = create.createCreature(creatureName, spawnLoc, creaturelevel, true, false);
                    if (!isIdValid(mob)) {
                        sendSystemMessageTestingOnly(self, "invalid objid on the creature we just tried to spawn. OH NO!");
                    } else {
                        spawnedCreatures[i] = mob;
                        if (newCreatureLevel > 0) {
                            reLevelMob(mob, newCreatureLevel);
                        }
                    }
                }
                if (spawnedCreatures != null) {
                    utils.setScriptVar(self, "spawnedCreatures", spawnedCreatures);
                }
                break;
            }
            case "wdam":
                debugLogging("//// OnHearSpeech: ", "////>>>> 9");
                String damageStr = st.nextToken();
                int damageValue = utils.stringToInt(damageStr);
                obj_id weapon = getCurrentWeapon(self);
                if (!isIdValid(weapon)) {
                    sendSystemMessageTestingOnly(self, "bad weapon - objId was: " + weapon);
                }
                if (getWeaponType(weapon) != WEAPON_TYPE_UNARMED) {
                    setWeaponMinDamage(weapon, damageValue);
                    setWeaponMaxDamage(weapon, damageValue);
                    weapons.setWeaponData(weapon);
                }
                break;
            case "newlevel":
                debugLogging("//// OnHearSpeech: ", "////>>>> 10");
                String reLevelStr = st.nextToken();
                int newLevel = utils.stringToInt(reLevelStr);
                if (newLevel == 0) {
                    if (utils.hasScriptVar(self, "newCreatureLevel")) {
                        sendSystemMessageTestingOnly(self, "Removed existing creature level delta value");
                        utils.removeScriptVar(self, "newCreatureLevel");
                    }
                } else {
                    if (utils.setScriptVar(self, "newCreatureLevel", newLevel)) {
                        sendSystemMessageTestingOnly(self, "All creatures spawned will now be set to level " + newLevel);
                    }
                }
                if (utils.hasScriptVar(self, "spawnedCreatures")) {
                    obj_id[] mobsToRelevel = utils.getObjIdArrayScriptVar(self, "spawnedCreatures");
                    int reLeveledMobCounter = 0;
                    for (obj_id obj_id : mobsToRelevel) {
                        if (isIdValid(obj_id) && !ai_lib.aiIsDead(obj_id)) {
                            reLevelMob(obj_id, newLevel);
                            reLeveledMobCounter++;
                        }
                    }
                    if (reLeveledMobCounter > 0) {
                        sendSystemMessageTestingOnly(self, "A total of " + reLeveledMobCounter + " creatures were re-set to be level " + newLevel);
                    }
                }
                break;
            case "wipe":
                debugLogging("//// OnHearSpeech: ", "////>>>> 11");
                if (!utils.hasScriptVar(self, "spawnedCreatures")) {
                    sendSystemMessageTestingOnly(self, "No apparent creature to destroy. Couldn't find obj_id scripvar. OH NO!");
                } else {
                    obj_id[] mobsToDestroy = utils.getObjIdArrayScriptVar(self, "spawnedCreatures");
                    for (obj_id obj_id : mobsToDestroy) {
                        if (isIdValid(obj_id)) {
                            destroyObject(obj_id);
                        }
                    }
                }
                break;
            default:
                sendSystemMessageTestingOnly(self, "valid trigger phrases for balancetest script are: 'wdam #' for weapon damage value and 'clev #' for creature level.");
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public void reLevelMob(obj_id mob, int level) throws InterruptedException
    {
        obj_id self = getSelf();
        String creatureName = utils.getStringScriptVar(self, "clev_creature");
        dictionary creatureDict = utils.dataTableGetRow(CREATURE_TABLE, creatureName);
        create.initializeCreature(mob, creatureName, creatureDict, level);
        return;
    }
}
