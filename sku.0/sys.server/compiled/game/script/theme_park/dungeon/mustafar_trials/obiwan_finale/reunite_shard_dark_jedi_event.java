package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.buff;

public class reunite_shard_dark_jedi_event extends script.base_script
{
    public reunite_shard_dark_jedi_event()
    {
    }
    public static final String STF_INQ_MSGS = "mustafar/inquisitor_event";
    public static final string_id DARK_SPAWN = new string_id(STF_INQ_MSGS, "dark_trooper_spawn");
    public static final string_id INQ_ATTACK = new string_id(STF_INQ_MSGS, "inquisitor_attack");
    public static final String TRIGGER_VOLUME_JEDI = "jedi_interest_volume";
    public static final float JEDI_INTEREST_RADIUS = 35f;
    public static final boolean CONST_FLAG_DO_LOGGING = true;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        launchObjectTriggerVolumeInitializer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        launchObjectTriggerVolumeInitializer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        debugLogging("OnTriggerVolumeEntered: ", " entered.");
        if (isPlayer(breacher) && !isIncapacitated(breacher))
        {
            if (volumeName.equals(TRIGGER_VOLUME_JEDI))
            {
                if (!hasObjVar(self, "noRespawn"))
                {
                    if (canCallEnemies(self))
                    {
                        spawnEnemies(self, self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/reunite_jedi_event/" + section, message);
        }
    }
    public void launchObjectTriggerVolumeInitializer(obj_id self) throws InterruptedException
    {
        if (!hasTriggerVolume(self, TRIGGER_VOLUME_JEDI))
        {
            createTriggerVolume(TRIGGER_VOLUME_JEDI, JEDI_INTEREST_RADIUS, true);
        }
        else 
        {
            obj_id[] denizens = getTriggerVolumeContents(self, TRIGGER_VOLUME_JEDI);
            for (int i = 0; i < denizens.length; i++)
            {
                if (isPlayer(denizens[i]) && !isIncapacitated(denizens[i]))
                {
                    if (canCallEnemies(self))
                    {
                        spawnEnemies(self, self);
                        return;
                    }
                }
            }
        }
        return;
    }
    public obj_id callInquisitor(obj_id self, obj_id landmark) throws InterruptedException
    {
        debugLogging("//***// callInquisitor: ", "////>>>> entered.");
        location currentLoc = getLocation(self);
        float landmarkDistanceOffset = 0f;
        if (isIdValid(landmark))
        {
            location landmarkLoc = getLocation(landmark);
            landmarkDistanceOffset = getDistance(self, landmark);
            debugLogging("callInquisitor: ", "landmark location is valid.");
        }
        float distance = landmarkDistanceOffset + 30f;
        location spawnLoc = utils.findLocInFrontOfTarget(self, distance);
        obj_id inquisitor = create.object("som_kenobi_reunite_inquisitorium_hunter", spawnLoc);
        if (!isIdValid(inquisitor))
        {
            debugLogging("callInquisitor: ", "inquisitor obj_id that we created is not valid");
            return null;
        }
        ai_lib.setDefaultCalmBehavior(inquisitor, ai_lib.BEHAVIOR_SENTINEL);
        faceTo(inquisitor, self);
        setObjVar(inquisitor, "inquisitor", 1);
        return inquisitor;
    }
    public obj_id callDark_trooper_1(obj_id self, obj_id landmark) throws InterruptedException
    {
        debugLogging("//***// callDark_trooper_1: ", "////>>>> entered.");
        location currentLoc = getLocation(self);
        float landmarkDistanceOffset = 0f;
        if (isIdValid(landmark))
        {
            location landmarkLoc = getLocation(landmark);
            landmarkDistanceOffset = getDistance(self, landmark);
            debugLogging("callDark_trooper_1: ", "landmark location is valid.");
        }
        float distance = landmarkDistanceOffset + 30f;
        location spawnLoc = utils.findLocInFrontOfTarget(self, distance);
        obj_id dark_trooper_1 = create.object("som_kenobi_reunite_dark_trooper", spawnLoc);
        if (!isIdValid(dark_trooper_1))
        {
            debugLogging("callDark_trooper_1: ", "dark_trooper_1 obj_id that we created is not valid");
            return null;
        }
        ai_lib.setDefaultCalmBehavior(dark_trooper_1, ai_lib.BEHAVIOR_LOITER);
        faceTo(dark_trooper_1, self);
        setObjVar(dark_trooper_1, "inquisitor", 1);
        return dark_trooper_1;
    }
    public obj_id callDark_trooper_2(obj_id self, obj_id landmark) throws InterruptedException
    {
        debugLogging("//***// callDark_trooper_2: ", "////>>>> entered.");
        location currentLoc = getLocation(self);
        float landmarkDistanceOffset = 0f;
        if (isIdValid(landmark))
        {
            location landmarkLoc = getLocation(landmark);
            landmarkDistanceOffset = getDistance(self, landmark);
            debugLogging("callDark_trooper_2: ", "landmark location is valid.");
        }
        float distance = landmarkDistanceOffset + 30f;
        location spawnLoc = utils.findLocInFrontOfTarget(self, distance);
        obj_id dark_trooper_2 = create.object("som_kenobi_reunite_dark_trooper", spawnLoc);
        if (!isIdValid(dark_trooper_2))
        {
            debugLogging("callDark_trooper_2: ", "dark_trooper_1 obj_id that we created is not valid");
            return null;
        }
        ai_lib.setDefaultCalmBehavior(dark_trooper_2, ai_lib.BEHAVIOR_LOITER);
        faceTo(dark_trooper_2, self);
        setObjVar(dark_trooper_2, "inquisitor", 1);
        return dark_trooper_2;
    }
    public boolean canCallEnemies(obj_id self) throws InterruptedException
    {
        debugLogging("canCallEnemies: ", " entered.");
        String planetName = getCurrentSceneName();
        if (!planetName.startsWith("mustafar"))
        {
            return false;
        }
        if (!enemiesAlreadyPresent(self))
        {
            debugLogging("canCallEnemies: ", " No enemies in range. ok to call him.");
            return true;
        }
        return false;
    }
    public boolean enemiesAlreadyPresent(obj_id self) throws InterruptedException
    {
        debugLogging("enemiesAlreadyPresent: ", " entered.");
        location currentLoc = getLocation(self);
        obj_id enemyObject = getFirstObjectWithObjVar(currentLoc, 100f, "inquisitor");
        if (isIdValid(enemyObject))
        {
            debugLogging("enemyAlreadyPresent: ", "found a pre-existing inquisitor object.");
            return true;
        }
        debugLogging("enemyAlreadyPresent: ", "there is no pre-existing inquisitor object");
        return false;
    }
    public void spawnEnemies(obj_id self, obj_id landmark) throws InterruptedException
    {
        obj_id inquisitor = callInquisitor(self, landmark);
        obj_id dark_trooper_1 = callDark_trooper_1(self, landmark);
        obj_id dark_trooper_2 = callDark_trooper_2(self, landmark);
        dictionary inqs = new dictionary();
        inqs.put("inquisitor", inquisitor);
        inqs.put("dark_trooper_1", dark_trooper_1);
        inqs.put("dark_trooper_2", dark_trooper_2);
        chat.publicChat(dark_trooper_1, inquisitor, DARK_SPAWN);
        messageTo(self, "noHate", inqs, 5, false);
        messageTo(self, "inquisitorAttack", inqs, 7, false);
        messageTo(self, "dontRespawn", null, 480, false);
        setObjVar(self, "noRespawn", 1);
        return;
    }
    public int noHate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id inquisitor = params.getObjId("inquisitor");
        obj_id dark_trooper_1 = params.getObjId("dark_trooper_1");
        obj_id dark_trooper_2 = params.getObjId("dark_trooper_2");
        clearHateList(inquisitor);
        clearHateList(dark_trooper_1);
        clearHateList(dark_trooper_2);
        return SCRIPT_CONTINUE;
    }
    public int inquisitorAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id inquisitor = params.getObjId("inquisitor");
        obj_id dark_trooper_1 = params.getObjId("dark_trooper_1");
        obj_id dark_trooper_2 = params.getObjId("dark_trooper_2");
        chat.publicChat(inquisitor, dark_trooper_1, INQ_ATTACK);
        addHate(inquisitor, self, 1000f);
        addHate(dark_trooper_1, self, 1000f);
        addHate(dark_trooper_2, self, 1000f);
        addHate(self, dark_trooper_2, 5000f);
        startCombat(inquisitor, self);
        startCombat(dark_trooper_1, self);
        startCombat(dark_trooper_2, self);
        return SCRIPT_CONTINUE;
    }
    public int dontRespawn(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "noRespawn");
        return SCRIPT_CONTINUE;
    }
}
