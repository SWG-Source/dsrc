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
                    return -1f;
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
                    return -1f;
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
        return -1f;
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
        if (strAction.equals("combatanimation"))
        {
            if (isIdValid(objActor) && !isDead(objActor) && exists(objActor) && isIdValid(objTarget) && !isDead(objTarget) && exists(objTarget))
            {
                startCombat(objActor, objTarget);
                setTarget(objActor, objTarget);
                sequencer.doCombatAnimation(objActor, objTarget, strData1);
            }
        }
        else if (strAction.equals("combatanimationmultiple"))
        {
            String[] strTargets = split(strTarget, ',');
            obj_id[] objTargets = new obj_id[strTargets.length];
            for (int i = 0; i < strTargets.length; i++)
            {
                objTargets[i] = sequencer.getSequenceObject(strTargets[i]);
            }
            for (int i = 0; i < objTargets.length; i++)
            {
                if (isIdValid(objActor) && !isDead(objActor) && isIdValid(objTargets[i]) && !isDead(objTargets[i]) && exists(objTargets[i]) && exists(objActor))
                {
                    startCombat(objActor, objTargets[i]);
                    setTarget(objActor, objTargets[i]);
                    sequencer.doCombatAnimation(objActor, objTargets[i], strData1);
                    break;
                }
            }
        }
        else if (strAction.equals("moveto"))
        {
            if (strData1.equals("run"))
            {
                sequencer.runToSequenceObject(objActor, objTarget);
            }
            else if (strData1.equals("walk"))
            {
                sequencer.walkToSequenceObject(objActor, objTarget);
            }
        }
        else if (strAction.equals("faceto"))
        {
            faceTo(objActor, objTarget);
        }
        else if (strAction.equals("posture"))
        {
            if (strData1.equals("stand"))
            {
                setPosture(objActor, POSTURE_UPRIGHT);
                sequencer.doCombatAnimation(objActor, objTarget, "posture_scramble");
            }
            else if (strData1.equals("kneel"))
            {
                setPosture(objActor, POSTURE_CROUCHED);
                sequencer.doCombatAnimation(objActor, objTarget, "posture_scramble");
            }
            else if (strData1.equals("prone"))
            {
                setPosture(objActor, POSTURE_PRONE);
                sequencer.doCombatAnimation(objActor, objTarget, "posture_scramble");
            }
        }
        else if (strAction.equals("effect"))
        {
            playClientEffectLoc(objActor, strData1, getLocation(objTarget), 0);
        }
        else if (strAction.equals("playmusic"))
        {
            play2dNonLoopingMusic(objActor, strData1);
        }
        else if (strAction.equals("spacechat"))
        {
            if(objTarget != null && objActor != null && isValidId(objActor)) {
                String stf = getStringObjVar(self, "strSequenceTable");
                string_id strChat = new string_id(stf, strData1);
                if (strData2.equals("") || strData2 == null) {
                    strData2 = "sound/sys_comm_generic.snd";
                }
                npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "");
            }
        }
        else if (strAction.equals("tutorialcomm"))
        {
            if(objTarget != null && objActor != null && isValidId(objActor)) {
                String stf = getStringObjVar(self, "strSequenceTable");
                string_id strChat = new string_id(stf, strData1);
                if (strData2.equals("") || strData2 == null) {
                    strData2 = "sound/sys_comm_generic.snd";
                }
                npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "object/mobile/c_3po.iff");
            }
        }
        else if (strAction.equals("jabbacomm"))
        {
            if(objTarget != null && objActor != null && isValidId(objActor)) {
                String stf = getStringObjVar(self, "strSequenceTable");
                string_id strChat = new string_id(stf, strData1);
                if (strData2.equals("") || strData2 == null) {
                    strData2 = "sound/sys_comm_generic.snd";
                }
                npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "object/mobile/jabba_the_hutt.iff");
            }
        }
        else if (strAction.equals("hancomm"))
        {
            if(objTarget != null && objActor != null && isValidId(objActor)) {
                String stf = getStringObjVar(self, "strSequenceTable");
                string_id strChat = new string_id(stf, strData1);
                if (strData2.equals("") || strData2 == null) {
                    strData2 = "sound/sys_comm_generic.snd";
                }
                npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "object/mobile/npe/npe_han_solo.iff");
            }
        }
        else if (strAction.equals("vadercomm"))
        {
            if(objTarget != null && objActor != null && isValidId(objActor)) {
                String stf = getStringObjVar(self, "strSequenceTable");
                string_id strChat = new string_id(stf, strData1);
                if (strData2 == null || strData2.equals("")) {
                    strData2 = "sound/sys_comm_generic.snd";
                }
                npe.commTutorialPlayer(objActor, objTarget, intTime, strChat, strData2, "object/mobile/darth_vader.iff");
            }
        }
        else if (strAction.equals("wookieecomm"))
        {
            if(objTarget != null && objActor != null && isValidId(objActor)) {
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
        }
        else if (strAction.equals("spacechatship"))
        {
            if(objTarget != null) {
                String stf = getStringObjVar(self, "strSequenceTable");
                string_id strChat = new string_id(stf, strData1);
                space_utils.tauntPilot(objTarget, objActor, strChat);
            }
        }
        else if (strAction.equals("setanimationmood"))
        {
            setAnimationMood(objActor, strData1);
        }
        else if (strAction.equals("animation"))
        {
            doAnimationAction(objActor, strData1);
        }
        else if (strAction.equals("destroy"))
        {
            destroyObject(objTarget);
        }
        else if (strAction.equals("popup"))
        {
            String stf = getStringObjVar(self, "strSequenceTable");
            string_id strChat = new string_id(stf, strData1);
            addNotification(objActor, utils.packStringId(strChat), false, 0, 0, 0, strData2);
        }
        else if (strAction.equals("messageto"))
        {
            if (strTarget.equals("self"))
            {
                objTarget = self;
            }
            messageTo(objTarget, strData1, null, 0, true);
        }
        else if (strAction.equals("uihighlight"))
        {
        }
        else if (strAction.equals("setobjvar"))
        {
            setObjVar(objActor, strData1, strData2);
        }
        else if (strAction.equals("setscriptvar"))
        {
            utils.setScriptVar(objActor, strData1, strData2);
        }
        else if (strAction.equals("checkforitem"))
        {
            obj_id foundItem = utils.getItemPlayerHasByTemplate(objActor, strData1);
            if (isIdValid(foundItem))
            {
                messageTo(self, strData2, null, 0, true);
            }
        }
        else if (strAction.equals("say"))
        {
            String stf = getStringObjVar(self, "strSequenceTable");
            string_id strChat = new string_id(stf, strData1);
            chat.chat(objActor, strChat);
        }
        else if (strAction.equals("saysound"))
        {
            String stf = getStringObjVar(self, "strSequenceTable");
            string_id strChat = new string_id(stf, strData1);
            chat.chat(objActor, strChat);
            play2dNonLoopingSound(objTarget, strData2);
        }
        else if (strAction.equals("stop"))
        {
            intIndex = intIndex + 1;
            utils.setScriptVar(self, "intMainIndex", intIndex);
            return sequencer.STOP_SEQUENCE;
        }
        else if (strAction.equals("secondtable"))
        {
            utils.setScriptVar(self, "intSecondIndex", 0);
            utils.setScriptVar(self, "strSecondaryTable", strData1);
            intIndex = intIndex + 1;
            LOG("npe", "Setting main index to " + intIndex);
            utils.setScriptVar(self, "intMainIndex", intIndex);
            return sequencer.CHANGE_TABLE;
        }
        else if (strAction.equals("goto"))
        {
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
