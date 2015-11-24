package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.ai.ai_combat;

public class sequencer extends script.base_script
{
    public sequencer()
    {
    }
    public static final int STOP_SEQUENCE = -1;
    public static final int CHANGE_TABLE = -2;
    public static obj_id getSequenceObject(String strIdentifier) throws InterruptedException
    {
        return getSequenceObject(getSelf(), strIdentifier);
    }
    public static obj_id getSequenceObject(obj_id self, String strIdentifier) throws InterruptedException
    {
        obj_id objMaster = getMasterSequenceObject(self);
        if (!isIdValid(objMaster))
        {
            return null;
        }
        return utils.getObjIdScriptVar(objMaster, strIdentifier);
    }
    public static boolean registerSequenceObject(obj_id objObject, String strIdentifier) throws InterruptedException
    {
        obj_id objMaster = getMasterSequenceObject(objObject);
        if (!isIdValid(objMaster))
        {
            return false;
        }
        utils.setScriptVar(objObject, "objMasterSequenceObject", objMaster);
        utils.setScriptVar(objMaster, strIdentifier, objObject);
        return true;
    }
    public static boolean registerSequenceObject(obj_id objObject) throws InterruptedException
    {
        if (hasObjVar(objObject, "strSequenceIdentifier"))
        {
            return registerSequenceObject(objObject, getStringObjVar(objObject, "strSequenceIdentifier"));
        }
        else 
        {
            return false;
        }
    }
    public static boolean cleanUpSequenceObject(obj_id objObject) throws InterruptedException
    {
        if (hasObjVar(objObject, "strSequenceIdentifier"))
        {
            return cleanUpSequenceObject(objObject, getStringObjVar(objObject, "strSequenceIdentifier"));
        }
        else 
        {
            return false;
        }
    }
    public static boolean cleanUpSequenceObject(obj_id objObject, String strIdentifier) throws InterruptedException
    {
        obj_id objMaster = getMasterSequenceObject(objObject);
        return utils.removeScriptVar(objMaster, strIdentifier);
    }
    public static obj_id getMasterSequenceObject(obj_id objObject) throws InterruptedException
    {
        if (utils.hasScriptVar(objObject, "objMasterSequencerObject"))
        {
            return utils.getObjIdScriptVar(objObject, "objMasterSequenceObject");
        }
        obj_id objMaster = getTopMostContainer(objObject);
        LOG("npe", "objMaster is " + objMaster);
        if (!isIdValid(objMaster))
        {
            return objObject;
        }
        return objMaster;
    }
    public static boolean isValidSequenceIdentifier(String strIdentifier) throws InterruptedException
    {
        obj_id objMaster = getMasterSequenceObject(getSelf());
        if (isIdValid(objMaster))
        {
            return utils.hasScriptVar(objMaster, strIdentifier);
        }
        return false;
    }
    public static void walkToSequenceObject(obj_id objNPC, obj_id objSeq) throws InterruptedException
    {
        location locWalkTo = getLocation(objSeq);
        pathTo(objNPC, locWalkTo);
    }
    public static void walkToSequenceObject(obj_id objNPC, String strObject) throws InterruptedException
    {
        obj_id objSeq = getSequenceObject(strObject);
        walkToSequenceObject(objNPC, objSeq);
    }
    public static void runToSequenceObject(obj_id objNPC, obj_id objSeq) throws InterruptedException
    {
        location locWalkTo = getLocation(objSeq);
        setMovementRun(objNPC);
        pathTo(objNPC, locWalkTo);
    }
    public static void runToSequenceObject(obj_id objNPC, String strObject) throws InterruptedException
    {
        obj_id objSeq = getSequenceObject(strObject);
        runToSequenceObject(objNPC, objSeq);
    }
    public static void doCombatAnimation(obj_id objNPC, obj_id objSeq, String strAnimation) throws InterruptedException
    {
        if (isDead(objNPC))
        {
            return;
        }
        if (!exists(objNPC))
        {
            return;
        }
        if (isIdValid(objNPC) && isIdValid(objSeq))
        {
            attacker_results cbtAttackerResults = makeDummyAttackerResults(objNPC);
            defender_results[] cbtDefenderResults = makeDummyDefenderResults(objSeq);
            if (!isIdValid(cbtAttackerResults.id) || !isIdValid(cbtDefenderResults[0].id))
            {
                return;
            }
            doCombatResults(strAnimation, cbtAttackerResults, cbtDefenderResults);
        }
        return;
    }
    public static void doCombatAnimation(obj_id objNPC, String strObject, String strAnimation) throws InterruptedException
    {
        obj_id objSeq = getSequenceObject(strObject);
        doCombatAnimation(objSeq, objNPC, strAnimation);
    }
    public static void faceToSequenceObject(obj_id objNPC, String strObject) throws InterruptedException
    {
        obj_id objSeq = getSequenceObject(strObject);
        faceTo(objNPC, objSeq);
        return;
    }
    public static attacker_results makeDummyAttackerResults(obj_id objAttacker) throws InterruptedException
    {
        attacker_results cbtAttackerResults = new attacker_results();
        cbtAttackerResults.id = objAttacker;
        cbtAttackerResults.weapon = getCurrentWeapon(objAttacker);
        LOG("npe", "weapon is " + getCurrentWeapon(objAttacker));
        cbtAttackerResults.endPosture = getPosture(objAttacker);
        return cbtAttackerResults;
    }
    public static defender_results[] makeDummyDefenderResults(obj_id objDefender) throws InterruptedException
    {
        defender_results[] cbtDefenderResults = new defender_results[1];
        cbtDefenderResults[0] = new defender_results();
        cbtDefenderResults[0].id = objDefender;
        cbtDefenderResults[0].endPosture = 0;
        cbtDefenderResults[0].result = COMBAT_RESULT_HIT;
        cbtDefenderResults[0].damageAmount = 0;
        cbtDefenderResults[0].hitLocation = 0;
        return cbtDefenderResults;
    }
    public static defender_results[] makeDummyMissDefenderResults(obj_id objDefender) throws InterruptedException
    {
        defender_results[] cbtDefenderResults = new defender_results[1];
        cbtDefenderResults[0] = new defender_results();
        cbtDefenderResults[0].id = objDefender;
        cbtDefenderResults[0].endPosture = 0;
        cbtDefenderResults[0].result = COMBAT_RESULT_MISS;
        cbtDefenderResults[0].damageAmount = 0;
        cbtDefenderResults[0].hitLocation = 0;
        return cbtDefenderResults;
    }
    public static void continueEventSequence(String strContinueTag) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id objMaster = getMasterSequenceObject(self);
        dictionary dctParams = new dictionary();
        dctParams.put("strContinueTag", strContinueTag);
        messageTo(objMaster, "doEvents", dctParams, 0, false);
        return;
    }
    public static void stopSequence(obj_id objTarget) throws InterruptedException
    {
        messageTo(objTarget, "interruptSequence", null, 0, false);
        return;
    }
}
