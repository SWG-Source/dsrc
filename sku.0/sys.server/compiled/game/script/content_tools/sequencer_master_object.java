package script.content_tools;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.string_id;

public class sequencer_master_object extends script.base_script
{
    public sequencer_master_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int doEvents(obj_id self, dictionary params) throws InterruptedException
    {
        String strIndexScriptVar = "";
        String strFileName = "";
        if (utils.hasScriptVar(self, "intSequenceStopped"))
        {
            if (utils.hasScriptVar(self, "intSequenceContinue"))
            {
                utils.removeScriptVar(self, "intSequenceStopped");
                utils.removeScriptVar(self, "intSequenceContinue");
            }
            else 
            {
                debugSpeakMsg(self, "I have STOPPED!");
                utils.removeScriptVar(self, "intSequenceStopped");
                return SCRIPT_CONTINUE;
            }
        }
        if (params != null)
        {
            int intMessageStamp = params.getInt("intSecondaryTableStamp");
            int intMyStamp = utils.getIntScriptVar(self, "intSecondaryTableStamp");
            if (intMessageStamp != intMyStamp)
            {
                return SCRIPT_CONTINUE;
            }
        }
        else if (utils.hasScriptVar(self, "intSecondaryTableStamp"))
        {
            return SCRIPT_CONTINUE;
        }
        float fltDelay = runSequenceEvents(self);
        if (fltDelay > -1)
        {
            if (fltDelay == 0)
            {
                return SCRIPT_CONTINUE;
            }
            dictionary dctParams = null;
            if (utils.hasScriptVar(self, "strSecondaryTable"))
            {
                dctParams = new dictionary();
                int intTime = getGameTime();
                utils.setScriptVar(self, "intSecondaryTableStamp", intTime);
                dctParams.put("intSecondaryTableStamp", intTime);
            }
            messageTo(self, "doEvents", dctParams, fltDelay, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int interruptSequence(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "intSequenceStopped", 1);
        stopSequence(self);
        return SCRIPT_CONTINUE;
    }
    public void stopSequence(obj_id self) throws InterruptedException
    {
        String strTable = "";
        String strIndexScriptVar = "";
        if (utils.hasScriptVar(self, "strSecondaryTable"))
        {
            strIndexScriptVar = "intSecondIndex";
            strTable = utils.getStringScriptVar(self, "strSecondaryTable");
        }
        else 
        {
            strIndexScriptVar = "intMainIndex";
            strTable = getStringObjVar(self, "strSequenceTable");
        }
        String strFileName = "datatables/sequencer/event_sequence/" + strTable + ".iff";
        float[] fltTimes = dataTableGetFloatColumn(strFileName, "fltTime");
        int intIndex = utils.getIntScriptVar(self, strIndexScriptVar);
        if (intIndex > 0)
        {
            intIndex = intIndex - 1;
        }
        dictionary dctEventToRun = dataTableGetRow(strFileName, intIndex);
        String strAction = toLower(dctEventToRun.getString("strAction"));
        if (strAction.equals("moveto"))
        {
            utils.setScriptVar(self, strIndexScriptVar, intIndex);
        }
    }
    public float runSequenceEvents(obj_id self) throws InterruptedException
    {
        String strIndexScriptVar = "";
        String strTable = "";
        if (utils.hasScriptVar(self, "strSecondaryTable"))
        {
            strIndexScriptVar = "intSecondIndex";
            strTable = utils.getStringScriptVar(self, "strSecondaryTable");
        }
        else 
        {
            strIndexScriptVar = "intMainIndex";
            strTable = getStringObjVar(self, "strSequenceTable");
        }
        String strFileName = "datatables/sequencer/event_sequence/" + strTable + ".iff";
        LOG("npe", "strFileName is " + strFileName);
        boolean noMoreEvents = false;
        float[] fltTimes = dataTableGetFloatColumn(strFileName, "fltTime");
        int intIndex = utils.getIntScriptVar(self, strIndexScriptVar);
        float fltCurrentEventTime = fltTimes[intIndex];
        while (!noMoreEvents)
        {
            dictionary dctEventToRun = dataTableGetRow(strFileName, intIndex);
            intIndex = processEvents(dctEventToRun, intIndex);
            if (intIndex == sequencer.CHANGE_TABLE)
            {
                if (utils.hasScriptVar(self, "strSecondaryTable"))
                {
                    strFileName = "datatables/sequencer/event_sequence/" + utils.getStringScriptVar(self, "strSecondaryTable") + ".iff";
                    fltTimes = dataTableGetFloatColumn(strFileName, "fltTime");
                    intIndex = 0;
                    strIndexScriptVar = "intSecondIndex";
                }
            }
            else 
            {
                if (intIndex == sequencer.STOP_SEQUENCE)
                {
                    return -1.0f;
                }
            }
            float fltNextEvent = -1;
            if (intIndex + 1 < fltTimes.length - 1)
            {
                fltNextEvent = fltTimes[intIndex + 1];
            }
            if (fltCurrentEventTime != fltNextEvent)
            {
                noMoreEvents = true;
                if (intIndex > fltTimes.length - 1)
                {
                    return -1.0f;
                }
            }
        }
        LOG("npe", "updating " + strIndexScriptVar + " to " + intIndex);
        utils.setScriptVar(getSelf(), strIndexScriptVar, intIndex);
        float fltDelay = 0;
        if (intIndex == 0)
        {
            fltDelay = fltTimes[intIndex];
        }
        else 
        {
            LOG("npe", "fltTimes[intIndex] is " + fltTimes[intIndex]);
            LOG("npe", "fltTimes[intIndex-1] is " + fltTimes[intIndex - 1]);
            fltDelay = fltTimes[intIndex] - fltTimes[intIndex - 1];
        }
        if (intIndex < fltTimes.length)
        {
            LOG("npe", "Returning " + fltDelay);
            return fltDelay;
        }
        LOG("npe", "Returning " + fltDelay);
        return -1.0f;
    }
    public int processEvents(dictionary dctEventToRun, int intIndex) throws InterruptedException
    {
        obj_id self = getSelf();
        if(!isIdValid(self) || !exists(self) || self == null || self == obj_id.NULL_ID){
            return SCRIPT_CONTINUE;
        }
        String strActor = dctEventToRun.getString("strActor");
        String strAction = toLower(dctEventToRun.getString("strAction"));
        String strTarget = dctEventToRun.getString("strTarget");
        String strData1 = toLower(dctEventToRun.getString("strData1"));
        String strData2 = toLower(dctEventToRun.getString("strData2"));
        int intTime = dctEventToRun.getInt("intTime");
        obj_id objTarget = null;
        obj_id objActor = null;
        if (!strActor.equals(""))
        {
            objActor = sequencer.getSequenceObject(strActor);
        }
        if (!strTarget.equals(""))
        {
            objTarget = sequencer.getSequenceObject(strTarget);
        }
        LOG("han_solo_event", "Doing event line --- Actor is " + strActor + ", ID is " + objActor + " doing action " + strAction + " at target " + strTarget + ", ID " + objTarget);
        switch (strAction) {
            case "combatanimation":
                if (isIdValid(objActor) && !isDead(objActor) && exists(objActor) && isIdValid(objTarget) && !isDead(objTarget) && exists(objTarget)) {
                    startCombat(objActor, objTarget);
                    setTarget(objActor, objTarget);
                    sequencer.doCombatAnimation(objActor, objTarget, strData1);
                }
                break;
            case "combatanimationmultiple":
                String[] strTargets = split(strTarget, ',');
                obj_id[] objTargets = new obj_id[strTargets.length];
                for (int i = 0; i < strTargets.length; i++) {
                    objTargets[i] = sequencer.getSequenceObject(strTargets[i]);
                }
                for (obj_id objTarget1 : objTargets) {
                    if (isIdValid(objActor) && !isDead(objActor) && isIdValid(objTarget1) && !isDead(objTarget1) && exists(objTarget1) && exists(objActor)) {
                        startCombat(objActor, objTarget1);
                        setTarget(objActor, objTarget1);
                        sequencer.doCombatAnimation(objActor, objTarget1, strData1);
                        break;
                    }
                }
                break;
            case "moveto":
                if (strData1.equals("run")) {
                    sequencer.runToSequenceObject(objActor, objTarget);
                } else if (strData1.equals("walk")) {
                    sequencer.walkToSequenceObject(objActor, objTarget);
                }
                break;
            case "faceto":
                faceTo(objActor, objTarget);
                break;
            case "posture":
                switch (strData1) {
                    case "stand":
                        setPosture(objActor, POSTURE_UPRIGHT);
                        sequencer.doCombatAnimation(objActor, objTarget, "posture_scramble");
                        break;
                    case "kneel":
                        setPosture(objActor, POSTURE_CROUCHED);
                        sequencer.doCombatAnimation(objActor, objTarget, "posture_scramble");
                        break;
                    case "prone":
                        setPosture(objActor, POSTURE_PRONE);
                        sequencer.doCombatAnimation(objActor, objTarget, "posture_scramble");
                        break;
                }
                break;
            case "effect":
                playClientEffectLoc(objActor, strData1, getLocation(objTarget), 0);
                break;
            case "playmusic":
                play2dNonLoopingMusic(objActor, strData1);
                break;
            case "spacechat":
                if (objTarget != null && objActor != null && isValidId(objActor)) {
                    String stf = getStringObjVar(self, "strSequenceTable");
                    string_id strChat = new string_id(stf, strData1);
                    if (strData2.equals("") || strData2 == null) {
                        strData2 = "sound/sys_comm_generic.snd";
                    }
                    npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "");
                }
                break;
            case "tutorialcomm":
                if (objTarget != null && objActor != null && isValidId(objActor)) {
                    String stf = getStringObjVar(self, "strSequenceTable");
                    string_id strChat = new string_id(stf, strData1);
                    if (strData2.equals("") || strData2 == null) {
                        strData2 = "sound/sys_comm_generic.snd";
                    }
                    npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "object/mobile/c_3po.iff");
                }
                break;
            case "jabbacomm":
                if (objTarget != null && objActor != null && isValidId(objActor)) {
                    String stf = getStringObjVar(self, "strSequenceTable");
                    string_id strChat = new string_id(stf, strData1);
                    if (strData2.equals("") || strData2 == null) {
                        strData2 = "sound/sys_comm_generic.snd";
                    }
                    npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "object/mobile/jabba_the_hutt.iff");
                }
                break;
            case "hancomm":
                if (objTarget != null && objActor != null && isValidId(objActor)) {
                    String stf = getStringObjVar(self, "strSequenceTable");
                    string_id strChat = new string_id(stf, strData1);
                    if (strData2.equals("") || strData2 == null) {
                        strData2 = "sound/sys_comm_generic.snd";
                    }
                    npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "object/mobile/npe/npe_han_solo.iff");
                }
                break;
            case "vadercomm":
                if (objTarget != null && objActor != null && isValidId(objActor)) {
                    String stf = getStringObjVar(self, "strSequenceTable");
                    string_id strChat = new string_id(stf, strData1);
                    if (strData2 == null || strData2.equals("")) {
                        strData2 = "sound/sys_comm_generic.snd";
                    }
                    npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "object/mobile/darth_vader.iff");
                }
                break;
            case "wookieecomm":
                if (objTarget != null && objActor != null && isValidId(objActor)) {
                    String stf = getStringObjVar(self, "strSequenceTable");
                    string_id strChat = new string_id(stf, strData1);
                    if (getSpecies(objTarget) == SPECIES_WOOKIEE) {
                        strChat = new string_id(stf, strData1 + "_w");
                    }
                    if (strData2.equals("") || strData2 == null) {
                        strData2 = "sound/sys_comm_generic.snd";
                    }
                    npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "object/mobile/npe/npe_chewbacca.iff");
                }
                break;
            case "spacechatship":
                if (objTarget != null) {
                    String stf = getStringObjVar(self, "strSequenceTable");
                    string_id strChat = new string_id(stf, strData1);
                    space_utils.tauntPilot(objTarget, objActor, strChat);
                }
                break;
            case "setanimationmood":
                setAnimationMood(objActor, strData1);
                break;
            case "animation":
                doAnimationAction(objActor, strData1);
                break;
            case "destroy":
                destroyObject(objTarget);
                break;
            case "popup": {
                String stf = getStringObjVar(self, "strSequenceTable");
                string_id strChat = new string_id(stf, strData1);
                addNotification(objActor, utils.packStringId(strChat), false, 0, 0, 0, strData2);
                break;
            }
            case "messageto":
                if (strTarget.equals("self")) {
                    objTarget = self;
                }
                messageTo(objTarget, strData1, null, 0, true);
                break;
            case "uihighlight":
                break;
            case "setobjvar":
                setObjVar(objActor, strData1, strData2);
                break;
            case "setscriptvar":
                utils.setScriptVar(objActor, strData1, strData2);
                break;
            case "checkforitem":
                obj_id foundItem = utils.getItemPlayerHasByTemplate(objActor, strData1);
                if (isIdValid(foundItem)) {
                    messageTo(self, strData2, null, 0, true);
                }
                break;
            case "say": {
                String stf = getStringObjVar(self, "strSequenceTable");
                string_id strChat = new string_id(stf, strData1);
                chat.chat(objActor, strChat);
                break;
            }
            case "saysound": {
                String stf = getStringObjVar(self, "strSequenceTable");
                string_id strChat = new string_id(stf, strData1);
                chat.chat(objActor, strChat);
                play2dNonLoopingSound(objTarget, strData2);
                break;
            }
            case "stop":
                intIndex = intIndex + 1;
                utils.setScriptVar(self, "intMainIndex", intIndex);
                return sequencer.STOP_SEQUENCE;
            case "secondtable":
                utils.setScriptVar(self, "intSecondIndex", 0);
                utils.setScriptVar(self, "strSecondaryTable", strData1);
                intIndex = intIndex + 1;
                LOG("npe", "Setting main index to " + intIndex);
                utils.setScriptVar(self, "intMainIndex", intIndex);
                return sequencer.CHANGE_TABLE;
            case "goto":
                intIndex = utils.stringToInt(strData1);
                LOG("npe", "Going to " + intIndex);
                return intIndex;
        }
        intIndex = intIndex + 1;
        return intIndex;
    }
    public int continueMainTable(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("npe", "CLEANUP OF SECONDARY TABLE!");
        utils.removeScriptVar(self, "strSecondaryTable");
        utils.removeScriptVar(self, "intSecondIndex");
        utils.removeScriptVar(self, "intSecondaryTableStamp");
        messageTo(self, "doEvents", null, 0, false);
        return SCRIPT_CONTINUE;
    }
}
