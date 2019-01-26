package script.npe;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

import java.util.Vector;

public class han_solo_experience_manager extends script.base_script
{
    public han_solo_experience_manager()
    {
    }
    public static final String STF = "npe/han_solo_experience";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkForDestroy", null, 300.0f, false);
        return SCRIPT_CONTINUE;
    }
    public boolean containsPlayer(obj_id bldg) throws InterruptedException
    {
        obj_id[] contents = getContents(bldg);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (isPlayer(content) || containsPlayer(content)) {
                    return true;
                }
            }
        }
        return false;
    }
    public int checkForDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (!containsPlayer(self))
        {
            destroyObject(self);
        }
        messageTo(self, "checkForDestroy", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int setupArea(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        sequencer.registerSequenceObject(player, "player");
        LOG("hangar_event", "Setup sequence has begun for hangar event in dungeon  " + self + " for player " + player);
        utils.setScriptVar(self, "npe.cratesLeft", 2);
        obj_id hallwayCell = getCellId(self, "hall4");
        obj_id medBayCell = getCellId(self, "medicalroom");
        permissionsMakePrivate(hallwayCell);
        obj_id[] crates = new obj_id[5];
        int crateCount = 0;
        if (isIdValid(medBayCell))
        {
            obj_id[] objContents = getContents(medBayCell);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    String strItemTemplate = getTemplateName(objContent);
                    if (strItemTemplate.equals("object/tangible/npe/npe_target_crate.iff")) {
                        crates[crateCount] = objContent;
                        crateCount++;
                    }
                }
            }
        }
        utils.setScriptVar(self, "objArrayCrates", crates);
        if (utils.hasScriptVar(self, "objAlarms"))
        {
            Vector alarms = utils.getResizeableObjIdArrayScriptVar(self, "objAlarms");
            for (Object alarm : alarms) {
                if (isIdValid(((obj_id) alarm))) {
                    setCondition(((obj_id) alarm), CONDITION_ON);
                    LOG("npe_alarm", "Condition is on for alarm " + ((obj_id) alarm));
                }
            }
        }
        obj_id hanStart = sequencer.getSequenceObject("hanStart");
        location hanLoc = getLocation(hanStart);
        obj_id droidStart = sequencer.getSequenceObject("medDroid");
        location droidLoc = getLocation(droidStart);
        obj_id medDroid = create.object("object/mobile/21b_surgical_droid.iff", droidLoc);
        utils.setScriptVar(self, "objMedDroid", medDroid);
        sequencer.registerSequenceObject(medDroid, "medDroid");
        setInvulnerable(medDroid, true);
        obj_id droidInvis = create.object("object/tangible/npe/npe_node.iff", hanLoc);
        setName(droidInvis, "C-3P0");
        sequencer.registerSequenceObject(droidInvis, "droid");
        setObjVar(droidInvis, "convo.appearance", "object/mobile/c_3po.iff");
        utils.setScriptVar(self, "objDroidInvis", droidInvis);
        LOG("hangar_event", "Important objects have been placed:  meddroid is " + medDroid + " invis 3po is " + droidInvis);
        return SCRIPT_CONTINUE;
    }
    public int setuphan(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        LOG("hangar_event", "Hangar event has reached the Han setup phase.  Dungeon is " + self + " and player is " + player);
        obj_id hanStart = sequencer.getSequenceObject("hanStart");
        location hanLoc = getLocation(hanStart);
        obj_id hanSolo = create.object("object/mobile/npe/npe_han_solo.iff", hanLoc);
        setObjVar(hanSolo, "isHanSolo", true);
        setYaw(hanSolo, -2);
        utils.setScriptVar(self, "objHanSolo", hanSolo);
        sequencer.registerSequenceObject(hanSolo, "hanSolo");
        createObject("object/weapon/ranged/pistol/pistol_dl44.iff", hanSolo, "hold_r");
        setBaseRunSpeed(hanSolo, (getBaseRunSpeed(hanSolo) / 2) + 1);
        obj_id chewieStart = sequencer.getSequenceObject("chewieStart");
        location chewieLoc = getLocation(chewieStart);
        obj_id chewie = create.object("object/mobile/npe/npe_chewbacca.iff", chewieLoc);
        setYaw(chewie, -2);
        utils.setScriptVar(self, "objChewie", chewie);
        sequencer.registerSequenceObject(chewie, "chewie");
        createObject("object/weapon/ranged/rifle/rifle_bowcaster.iff", chewie, "hold_r");
        setBaseRunSpeed(chewie, (getBaseRunSpeed(chewie) / 2) + 1);
        obj_id r2Start = sequencer.getSequenceObject("r2Start");
        location r2Loc = getLocation(r2Start);
        obj_id r2 = create.object("object/mobile/npe/npe_r2_d7.iff", r2Loc);
        setYaw(r2, -2);
        utils.setScriptVar(self, "objR2", r2);
        sequencer.registerSequenceObject(r2, "r2");
        setInvulnerable(r2, true);
        setBaseRunSpeed(r2, (getBaseRunSpeed(r2) / 2) + 1);
        LOG("hangar_event", "Important actors have been placed:  hanSolo is " + hanSolo + "  chewie is " + chewie + " and r2 is " + r2 + ".  Player is " + player);
        return SCRIPT_CONTINUE;
    }
    public int setuphuttwave1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        obj_id hutt1Start = sequencer.getSequenceObject("tutorialHutt1");
        location hutt1Loc = getLocation(hutt1Start);
        obj_id hutt1 = create.object("npe_stormtrooper_medbay", hutt1Loc);
        setYaw(hutt1, 13);
        utils.setScriptVar(self, "objHutt1", hutt1);
        utils.setScriptVar(hutt1, "objParent", self);
        attachScript(hutt1, "npe.hutt_minion");
        createObject("object/weapon/ranged/carbine/carbine_e11.iff", hutt1, "hold_r");
        sequencer.registerSequenceObject(hutt1, "hutt1");
        setInvulnerable(hutt1, true);
        ai_lib.setDefaultCalmBehavior(hutt1, ai_lib.BEHAVIOR_SENTINEL);
        obj_id hutt2Start = sequencer.getSequenceObject("tutorialHutt2");
        location hutt2Loc = getLocation(hutt2Start);
        obj_id hutt2 = create.object("npe_stormtrooper_medbay", hutt2Loc);
        setYaw(hutt2, 14);
        utils.setScriptVar(self, "objHutt2", hutt2);
        utils.setScriptVar(hutt2, "objParent", self);
        attachScript(hutt2, "npe.hutt_minion");
        createObject("object/weapon/ranged/carbine/carbine_e11.iff", hutt2, "hold_r");
        sequencer.registerSequenceObject(hutt2, "hutt2");
        setInvulnerable(hutt2, true);
        ai_lib.setDefaultCalmBehavior(hutt2, ai_lib.BEHAVIOR_SENTINEL);
        obj_id hutt3Start = sequencer.getSequenceObject("tutorialHutt3");
        location hutt3Loc = getLocation(hutt3Start);
        obj_id hutt3 = create.object("npe_stormtrooper_medbay", hutt3Loc);
        setYaw(hutt3, 12);
        utils.setScriptVar(self, "objHutt3", hutt3);
        utils.setScriptVar(hutt3, "objParent", self);
        attachScript(hutt3, "npe.hutt_minion");
        createObject("object/weapon/ranged/carbine/carbine_e11.iff", hutt3, "hold_r");
        sequencer.registerSequenceObject(hutt3, "hutt3");
        setInvulnerable(hutt3, true);
        ai_lib.setDefaultCalmBehavior(hutt3, ai_lib.BEHAVIOR_SENTINEL);
        obj_id han1 = sequencer.getSequenceObject("han1");
        messageTo(han1, "setupTriggerVolume", null, 0, false);
        LOG("hangar_event", "Hutts were set up.  Hutt 1 is " + hutt1 + " Hutt 2 is " + hutt2 + " Hutt 3 is " + hutt3 + ".  Player is " + player);
        return SCRIPT_CONTINUE;
    }
    public int setuphanpath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id han1 = sequencer.getSequenceObject("han1");
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        createClientPathAdvanced(player, getLocation(player), getLocation(han1), "default");
        return SCRIPT_CONTINUE;
    }
    public int showJournal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        newbieTutorialHighlightUIElement(player, "/GroundHUD.Quest", 7.0f);
        return SCRIPT_CONTINUE;
    }
    public int showHelper(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        newbieTutorialHighlightUIElement(player, "/GroundHUD.QuestHelper", 7.0f);
        npe.giveQuestHelperPopUp(player);
        npe.giveQuestHelperPopUp2(player);
        return SCRIPT_CONTINUE;
    }
    public int makehuttwave1attack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id hutt1 = sequencer.getSequenceObject("hutt1");
        obj_id hutt2 = sequencer.getSequenceObject("hutt2");
        obj_id hutt3 = sequencer.getSequenceObject("hutt3");
        setInvulnerable(hutt1, false);
        setInvulnerable(hutt2, false);
        setInvulnerable(hutt3, false);
        return SCRIPT_CONTINUE;
    }
    public int setuphuttwave2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        obj_id hutt1Start = sequencer.getSequenceObject("huttstart1");
        location hutt1Loc = getLocation(hutt1Start);
        obj_id hutt1 = create.object("npe_hutt_minion", hutt1Loc);
        setYaw(hutt1, 177);
        utils.setScriptVar(self, "objHutt5", hutt1);
        utils.setScriptVar(hutt1, "objParent", self);
        attachScript(hutt1, "npe.hutt_minion");
        createObject("object/weapon/ranged/pistol/pistol_cdef.iff", hutt1, "hold_r");
        sequencer.registerSequenceObject(hutt1, "hutt5");
        utils.setScriptVar(hutt1, "npe.invuln", true);
        ai_lib.setDefaultCalmBehavior(hutt1, ai_lib.BEHAVIOR_SENTINEL);
        obj_id hutt2Start = sequencer.getSequenceObject("huttstart2");
        location hutt2Loc = getLocation(hutt2Start);
        obj_id hutt2 = create.object("npe_hutt_minion", hutt2Loc);
        setYaw(hutt2, 177);
        utils.setScriptVar(self, "objHutt6", hutt2);
        utils.setScriptVar(hutt2, "objParent", self);
        attachScript(hutt2, "npe.hutt_minion");
        createObject("object/weapon/ranged/pistol/pistol_cdef.iff", hutt2, "hold_r");
        sequencer.registerSequenceObject(hutt2, "hutt6");
        utils.setScriptVar(hutt2, "npe.invuln", true);
        ai_lib.setDefaultCalmBehavior(hutt2, ai_lib.BEHAVIOR_SENTINEL);
        obj_id hutt3Start = sequencer.getSequenceObject("huttstart3");
        location hutt3Loc = getLocation(hutt3Start);
        obj_id hutt3 = create.object("npe_hutt_minion", hutt3Loc);
        setYaw(hutt3, 177);
        utils.setScriptVar(self, "objHutt7", hutt3);
        utils.setScriptVar(hutt3, "objParent", self);
        attachScript(hutt3, "npe.hutt_minion");
        createObject("object/weapon/ranged/pistol/pistol_cdef.iff", hutt3, "hold_r");
        sequencer.registerSequenceObject(hutt3, "hutt7");
        utils.setScriptVar(hutt3, "npe.invuln", true);
        ai_lib.setDefaultCalmBehavior(hutt3, ai_lib.BEHAVIOR_SENTINEL);
        obj_id hutt4Start = sequencer.getSequenceObject("huttstart4");
        location hutt4Loc = getLocation(hutt4Start);
        obj_id hutt4 = create.object("npe_hutt_minion", hutt4Loc);
        setYaw(hutt4, 177);
        utils.setScriptVar(self, "objHutt8", hutt4);
        utils.setScriptVar(hutt4, "objParent", self);
        attachScript(hutt4, "npe.hutt_minion");
        createObject("object/weapon/ranged/pistol/pistol_cdef.iff", hutt4, "hold_r");
        sequencer.registerSequenceObject(hutt4, "hutt8");
        utils.setScriptVar(hutt4, "npe.invuln", true);
        ai_lib.setDefaultCalmBehavior(hutt4, ai_lib.BEHAVIOR_SENTINEL);
        obj_id hutt5Start = sequencer.getSequenceObject("huttstart5");
        location hutt5Loc = getLocation(hutt1Start);
        obj_id hutt5 = create.object("npe_hutt_minion", hutt5Loc);
        setYaw(hutt5, 177);
        utils.setScriptVar(self, "objHutt9", hutt5);
        utils.setScriptVar(hutt5, "objParent", self);
        attachScript(hutt5, "npe.hutt_minion");
        createObject("object/weapon/ranged/pistol/pistol_cdef.iff", hutt5, "hold_r");
        sequencer.registerSequenceObject(hutt5, "hutt9");
        utils.setScriptVar(hutt5, "npe.invuln", true);
        ai_lib.setDefaultCalmBehavior(hutt5, ai_lib.BEHAVIOR_SENTINEL);
        obj_id hutt6Start = sequencer.getSequenceObject("huttstart6");
        location hutt6Loc = getLocation(hutt2Start);
        obj_id hutt6 = create.object("npe_hutt_minion", hutt6Loc);
        setYaw(hutt6, 177);
        utils.setScriptVar(self, "objHutt10", hutt6);
        utils.setScriptVar(hutt6, "objParent", self);
        attachScript(hutt6, "npe.hutt_minion");
        createObject("object/weapon/ranged/pistol/pistol_cdef.iff", hutt6, "hold_r");
        sequencer.registerSequenceObject(hutt6, "hutt10");
        utils.setScriptVar(hutt6, "npe.invuln", true);
        ai_lib.setDefaultCalmBehavior(hutt6, ai_lib.BEHAVIOR_SENTINEL);
        LOG("hangar_event", "Second Hutts were set up.  Hutt 1 is " + hutt1 + " Hutt 2 is " + hutt2 + " Hutt 3 is " + hutt3 + " Hutt 4 is " + hutt4 + " Hutt 5 is " + hutt5 + " Hutt 6 is " + hutt6 + ".  Player is " + player);
        return SCRIPT_CONTINUE;
    }
    public int endEncounter(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        LOG("hangar_event", "End Encounter has been called for dungeon " + self + " and player " + player);
        utils.removeScriptVar(self, "huttMinionsDead");
        utils.removeScriptVar(self, "npe.hangarEventStarted");
        destroyObject(utils.getObjIdScriptVar(self, "objHanSolo"));
        destroyObject(utils.getObjIdScriptVar(self, "objChewie"));
        destroyObject(utils.getObjIdScriptVar(self, "objJolka"));
        destroyObject(utils.getObjIdScriptVar(self, "objDayan"));
        destroyObject(utils.getObjIdScriptVar(self, "objHutt1"));
        destroyObject(utils.getObjIdScriptVar(self, "objHutt2"));
        destroyObject(utils.getObjIdScriptVar(self, "objHutt3"));
        destroyObject(utils.getObjIdScriptVar(self, "objDroid"));
        obj_id[] contents = getBuildingContents(self);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (!isPlayer(content) && isMob(content)) {
                    destroyObject(content);
                }
            }
        }
        LOG("hangar_event", "End Encounter has finished for dungeon " + self + " and player " + player);
        if (!containsPlayer(self))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int huttMinionDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "huttMinionsDead"))
        {
            utils.setScriptVar(self, "huttMinionsDead", 1);
        }
        else 
        {
            int dead = utils.getIntScriptVar(self, "huttMinionsDead");
            dead++;
            utils.setScriptVar(self, "huttMinionsDead", dead);
            if (dead >= 3)
            {
                messageTo(self, "allMinionsDead", null, 0, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int droidconverse1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = sequencer.getSequenceObject("droid");
        attachScript(droid, "conversation.npe_tutorial_droid_1");
        return SCRIPT_CONTINUE;
    }
    public int conversed1(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = sequencer.getSequenceObject("droid");
        detachScript(droid, "conversation.npe_tutorial_droid_1");
        messageTo(self, "continueMainTable", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int giveterminalquest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        groundquests.grantQuest(player, "quest/npe_medbay_1");
        messageTo(self, "showJournal", null, 0, false);
        messageTo(self, "showHelper", null, 8, false);
        return SCRIPT_CONTINUE;
    }
    public int allMinionsDead(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        utils.removeScriptVar(self, "huttMinionsDead");
        xp.grantXpByTemplate(player, 10);
        messageTo(self, "continueMainTable", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int checkmovement(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        obj_id triggerObj = sequencer.getSequenceObject("playerStart");
        if (!isInTriggerVolume(triggerObj, "npePlayerMoved", player))
        {
            messageTo(self, "continueMainTable", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishedtutorial(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        obj_id triggerLoc = sequencer.getSequenceObject("jolkaFalcon");
        attachScript(triggerLoc, "npe.npe_player_end_object");
        createClientPathAdvanced(player, getLocation(player), getLocation(triggerLoc), "default");
        LOG("hangar_event", "Hangar event has finished for dungeon " + self + " and player " + player);
        return SCRIPT_CONTINUE;
    }
    public int setuptrigger(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id triggerObj = sequencer.getSequenceObject("playerStart");
        messageTo(triggerObj, "setupTriggerVolume", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int setupdroidconversation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id medDroid = sequencer.getSequenceObject("medDroid");
        obj_id player = sequencer.getSequenceObject("player");
        attachScript(medDroid, "conversation.npe_tutorial_droid_1");
        createClientPathAdvanced(player, getLocation(player), getLocation(medDroid), "default");
        return SCRIPT_CONTINUE;
    }
    public int setupwalktrigger(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id triggerObj = sequencer.getSequenceObject("walkPoint");
        obj_id player = sequencer.getSequenceObject("player");
        attachScript(triggerObj, "npe.npe_walk_point_object");
        messageTo(triggerObj, "setupWalkTrigger", null, 0, false);
        createClientPathAdvanced(player, getLocation(player), getLocation(triggerObj), "default");
        return SCRIPT_CONTINUE;
    }
    public int setupcabinet(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id cabinet = sequencer.getSequenceObject("cabinet");
        obj_id player = sequencer.getSequenceObject("player");
        utils.setScriptVar(cabinet, "cabinetReady", true);
        createClientPathAdvanced(player, getLocation(player), getLocation(cabinet), "default");
        return SCRIPT_CONTINUE;
    }
    public int setupinv(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sequencer.getSequenceObject("player");
        utils.setScriptVar(player, "npe.invCheck", true);
        newbieTutorialRequest(player, "openInventory");
        return SCRIPT_CONTINUE;
    }
    public int setuppistol(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sequencer.getSequenceObject("player");
        utils.setScriptVar(player, "npe.pistolReady", true);
        return SCRIPT_CONTINUE;
    }
    public int setupterminal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, "objTerminal");
        obj_id player = sequencer.getSequenceObject("player");
        setInvulnerable(terminal, false);
        createClientPathAdvanced(player, getLocation(player), getLocation(terminal), "default");
        return SCRIPT_CONTINUE;
    }
    public int setupcrates(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] crates = utils.getObjIdArrayScriptVar(self, "objArrayCrates");
        for (obj_id crate : crates) {
            if (isIdValid(crate)) {
                setInvulnerable(crate, false);
            }
        }
        obj_id player = sequencer.getSequenceObject("player");
        createClientPathAdvanced(player, getLocation(player), getLocation(crates[0]), "default");
        return SCRIPT_CONTINUE;
    }
    public int crateDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sequencer.getSequenceObject("player");
        int crates = utils.getIntScriptVar(self, "npe.cratesLeft");
        crates--;
        utils.setScriptVar(self, "npe.cratesLeft", crates);
        if (crates == 0)
        {
            destroyClientPath(player);
            xp.grantXpByTemplate(player, 10);
            messageTo(self, "continueMainTable", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int terminalDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id hallwayCell = getCellId(self, "hall4");
        permissionsMakePublic(hallwayCell);
        obj_id player = sequencer.getSequenceObject("player");
        destroyClientPath(player);
        xp.grantXpByTemplate(player, 10);
        groundquests.sendSignal(player, "doorTerminalWasDestroyed");
        messageTo(self, "continueMainTable", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            if (!utils.hasScriptVar(self, "npe.hangarEventStarted"))
            {
                messageTo(self, "endEncounter", null, 0, false);
                LOG("hangar_event", "OnReceivedItem has been called for dungeon " + self + " and player " + item);
                utils.setScriptVar(self, "npe.hangarEventStarted", true);
                obj_id hallwayCell = getCellId(self, "hall4");
                permissionsMakePrivate(hallwayCell);
                messageTo(self, "setupArea", null, 5, false);
                utils.setScriptVar(self, "npe.player", item);
                attachScript(item, "npe.han_solo_experience_player");
                npe.resetPlayerForNpe(item);
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            if (!utils.hasScriptVar(self, "npe.hangarEventStarted"))
            {
                LOG("hangar_event", "OnLostItem has been called for dungeon " + self + " and player " + item);
                utils.removeScriptVar(self, "npe.player");
                utils.removeScriptVar(self, "intSecondIndex");
                utils.removeScriptVar(self, "strSecondaryTable");
                utils.removeScriptVar(self, "intMainIndex");
                messageTo(self, "endEncounter", null, 1, false);
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getBuildingContents(obj_id objObject) throws InterruptedException
    {
        Vector objContents = new Vector();
        objContents.setSize(0);
        obj_id[] objCells = getContents(objObject);
        for (obj_id objCell : objCells) {
            obj_id[] objTestContents = getContents(objCell);
            if ((objTestContents != null) && (objTestContents.length > 0)) {
                for (obj_id objTestContent : objTestContents) {
                    objContents = utils.addElement(objContents, objTestContent);
                }
            }
        }
        obj_id[] _objContents = new obj_id[0];
        if (objContents != null)
        {
            _objContents = new obj_id[objContents.size()];
            objContents.toArray(_objContents);
        }
        return _objContents;
    }
}
