package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.sequencer;
import script.library.chat;
import script.library.ship_ai;
import script.library.space_create;
import script.library.space_utils;
import script.library.groundquests;
import script.library.npe;
import script.library.xp;
import script.library.dump;

public class millennium_falcon_manager extends script.base_script
{
    public millennium_falcon_manager()
    {
    }
    public static final String STF = "npe/millennium_falcon";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "intInvincible", 1);
        return SCRIPT_CONTINUE;
    }
    public obj_id containsPlayer(obj_id bldg) throws InterruptedException
    {
        obj_id[] contents = getBuildingContents(bldg);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; ++i)
            {
                if (isPlayer(contents[i]))
                {
                    return contents[i];
                }
            }
        }
        return null;
    }
    public int playerTimeoutAdvanceToStation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("objPlayer");
        obj_id contents = containsPlayer(self);
        if (utils.isEquipped(player))
        {
            contents = player;
        }
        if (isIdValid(player) && isIdValid(contents))
        {
            if (contents == player)
            {
                string_id message = new string_id("npe_millennium_falcon", "timed_out_falcon");
                sendSystemMessage(player, message);
                messageTo(self, "endEncounter", null, 4, false);
                playCutScene(player, "video/npe_station.bik");
                utils.removeScriptVar(player, "npe.falconEventStarted");
                utils.removeScriptVar(player, "npe.readyForTurret");
                utils.removeScriptVar(player, "npe.readyForHyperspace");
                utils.removeScriptVar(player, "npe.finishedTurret");
                utils.removeScriptVar(player, "firstTurret");
                detachScript(player, "npe.npe_falcon_player");
                attachScript(player, "npe.npe_station_player");
                npe.movePlayerFromFalconToSharedStation(player);
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int setupArea(obj_id self, dictionary params) throws InterruptedException
    {
        location locSelf = getLocation(self);
        utils.setScriptVar(self, "shipsRemaining", 3);
        location point1 = (location)locSelf.clone();
        point1.x += 200;
        point1.y += 40;
        obj_id objPoint1 = create.object("object/tangible/npe/npe_node.iff", point1);
        utils.setScriptVar(self, "objPoint1", objPoint1);
        location point2 = (location)locSelf.clone();
        point2.z += 200;
        point2.y += 40;
        obj_id objPoint2 = create.object("object/tangible/npe/npe_node.iff", point2);
        utils.setScriptVar(self, "objPoint2", objPoint2);
        location point3 = (location)locSelf.clone();
        point3.x += -200;
        point3.y += 40;
        obj_id objPoint3 = create.object("object/tangible/npe/npe_node.iff", point3);
        utils.setScriptVar(self, "objPoint3", objPoint3);
        location point4 = (location)locSelf.clone();
        point4.z += -200;
        point4.y += 40;
        obj_id objPoint4 = create.object("object/tangible/npe/npe_node.iff", point4);
        utils.setScriptVar(self, "objPoint4", objPoint4);
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        sequencer.registerSequenceObject(player, "player");
        setYaw(player, 62);
        obj_id hanStart = sequencer.getSequenceObject("hanStart");
        location hanLoc = getLocation(hanStart);
        obj_id hanSolo = create.object("object/mobile/npe/npe_han_solo.iff", hanLoc);
        setYaw(hanSolo, -110);
        utils.setScriptVar(self, "objHanSolo", hanSolo);
        sequencer.registerSequenceObject(hanSolo, "hanSolo");
        setBaseRunSpeed(hanSolo, (getBaseRunSpeed(hanSolo) / 2));
        obj_id chewieStart = sequencer.getSequenceObject("hanEnd");
        location chewieLoc = getLocation(chewieStart);
        obj_id chewie = create.object("object/mobile/npe/npe_chewbacca.iff", chewieLoc);
        setAnimationMood(chewie, "npc_sitting_chair");
        utils.setScriptVar(self, "objChewie", chewie);
        sequencer.registerSequenceObject(chewie, "chewie");
        obj_id droidStart = sequencer.getSequenceObject("jolkaStart");
        location droidLoc = getLocation(droidStart);
        obj_id droid = create.object("object/mobile/c_3po.iff", droidLoc);
        setYaw(droid, -71);
        utils.setScriptVar(self, "objDroid", droid);
        sequencer.registerSequenceObject(droid, "droid");
        setInvulnerable(droid, true);
        detachScript(droid, "npc.celebrity.c3po");
        detachScript(droid, "theme_park.rebel.quest_convo");
        attachScript(droid, "conversation.npe_falcon_c3po");
        obj_id dayanStart = sequencer.getSequenceObject("dayanStart");
        location dayanLoc = getLocation(dayanStart);
        obj_id dayan = create.object("object/mobile/npe/npe_r2_d7.iff", dayanLoc);
        setYaw(dayan, -88);
        utils.setScriptVar(self, "objDayan", dayan);
        sequencer.registerSequenceObject(dayan, "dayan");
        setInvulnerable(dayan, true);
        obj_id jabbaInvis = create.object("object/tangible/npe/npe_node.iff", hanLoc);
        setName(jabbaInvis, "Darth Vader");
        sequencer.registerSequenceObject(jabbaInvis, "vader");
        setObjVar(jabbaInvis, "convo.appearance", "object/mobile/darth_vader.iff");
        utils.setScriptVar(self, "objVader", jabbaInvis);
        return SCRIPT_CONTINUE;
    }
    public int phaseTwo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id hanSolo = sequencer.getSequenceObject("hanSolo");
        destroyObject(hanSolo);
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        obj_id hanEndObj = sequencer.getSequenceObject("chewieStart");
        location hanLoc = getLocation(hanEndObj);
        obj_id hanSolo2 = create.object("object/mobile/npe/npe_han_solo.iff", hanLoc);
        setAnimationMood(hanSolo2, "npc_sitting_chair");
        sequencer.registerSequenceObject(hanSolo2, "hanSolo");
        return SCRIPT_CONTINUE;
    }
    public int makehanconverse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id hanSolo = utils.getObjIdScriptVar(self, "objHanSolo");
        attachScript(hanSolo, "conversation.npe_han_solo_falcon");
        return SCRIPT_CONTINUE;
    }
    public int makehanconverse2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id hanSolo = utils.getObjIdScriptVar(self, "objHanSolo");
        attachScript(hanSolo, "conversation.npe_han_solo_falcon2");
        return SCRIPT_CONTINUE;
    }
    public int makejolkaconverse(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int setupturretpath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        obj_id han = sequencer.getSequenceObject("hanTurret3");
        return SCRIPT_CONTINUE;
    }
    public int setupcockpitpath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        obj_id loc = sequencer.getSequenceObject("hanEnd");
        return SCRIPT_CONTINUE;
    }
    public int playerTalkedToHan(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id hanSolo = utils.getObjIdScriptVar(self, "objHanSolo");
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        detachScript(hanSolo, "conversation.npe_han_solo_falcon");
        xp.grantXpByTemplate(player, 10);
        messageTo(self, "continueMainTable", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int playerTalkedToHan2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id hanSolo = utils.getObjIdScriptVar(self, "objHanSolo");
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        detachScript(hanSolo, "conversation.npe_han_solo_falcon2");
        xp.grantXpByTemplate(player, 10);
        messageTo(self, "continueMainTable", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int endEncounter(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        utils.removeScriptVar(player, "npe.falconEventStarted");
        destroyObject(utils.getObjIdScriptVar(self, "objHanSolo"));
        destroyObject(utils.getObjIdScriptVar(self, "objChewie"));
        destroyObject(utils.getObjIdScriptVar(self, "objDroid"));
        destroyObject(utils.getObjIdScriptVar(self, "objDayan"));
        destroyObject(utils.getObjIdScriptVar(self, "objVader"));
        destroyObject(utils.getObjIdScriptVar(self, "objPoint1"));
        destroyObject(utils.getObjIdScriptVar(self, "objPoint2"));
        destroyObject(utils.getObjIdScriptVar(self, "objPoint3"));
        destroyObject(utils.getObjIdScriptVar(self, "objPoint4"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip1"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip2"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip3"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip4"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip5"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip6"));
        obj_id[] contents = getBuildingContents(self);
        utils.setScriptVar(self, "intMainIndex", 0);
        utils.removeScriptVar(self, "intSecondIndex");
        if (contents != null)
        {
            for (int intI = 0; intI < contents.length; intI++)
            {
                if (!isPlayer(contents[intI]) && isMob(contents[intI]))
                {
                    destroyObject(contents[intI]);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanOutDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] contents = getBuildingContents(self);
        if (contents != null)
        {
            for (int intI = 0; intI < contents.length; intI++)
            {
                if (!isPlayer(contents[intI]) && isMob(contents[intI]))
                {
                    destroyObject(contents[intI]);
                }
            }
        }
        destroyObject(utils.getObjIdScriptVar(self, "objPoint1"));
        destroyObject(utils.getObjIdScriptVar(self, "objPoint2"));
        destroyObject(utils.getObjIdScriptVar(self, "objPoint3"));
        destroyObject(utils.getObjIdScriptVar(self, "objPoint4"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip1"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip2"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip3"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip4"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip5"));
        destroyObject(utils.getObjIdScriptVar(self, "objShip6"));
        utils.removeScriptVar(self, "npe.player");
        utils.setScriptVar(self, "intMainIndex", 0);
        utils.removeScriptVar(self, "intSecondIndex");
        utils.setScriptVar(self, "shipsRemaining", 3);
        return SCRIPT_CONTINUE;
    }
    public int spawnShips(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id point1 = utils.getObjIdScriptVar(self, "objPoint1");
        obj_id point2 = utils.getObjIdScriptVar(self, "objPoint2");
        obj_id point3 = utils.getObjIdScriptVar(self, "objPoint3");
        obj_id point4 = utils.getObjIdScriptVar(self, "objPoint4");
        transform spawn1 = getTransform_o2w(point1);
        transform spawn2 = getTransform_o2w(point2);
        transform spawn3 = getTransform_o2w(point3);
        transform spawn4 = getTransform_o2w(point4);
        transform[] patrol = new transform[4];
        patrol[0] = spawn2;
        patrol[1] = spawn4;
        patrol[2] = spawn1;
        patrol[3] = spawn3;
        obj_id ship1 = space_create.createShip("npe_tie_fighter_falcon", spawn2);
        attachScript(ship1, "npe.npe_turret_ship");
        utils.setScriptVar(ship1, "objParent", self);
        utils.setScriptVar(self, "objShip1", ship1);
        obj_id ship2 = space_create.createShip("npe_tie_fighter_falcon", spawn2);
        attachScript(ship2, "npe.npe_turret_ship");
        utils.setScriptVar(ship2, "objParent", self);
        utils.setScriptVar(self, "objShip2", ship2);
        obj_id ship3 = space_create.createShip("npe_tie_fighter_falcon", spawn2);
        attachScript(ship3, "npe.npe_turret_ship");
        utils.setScriptVar(ship3, "objParent", self);
        utils.setScriptVar(self, "objShip3", ship3);
        obj_id ship4 = space_create.createShip("npe_tie_fighter_falcon", spawn2);
        attachScript(ship4, "npe.npe_turret_ship");
        utils.setScriptVar(ship4, "objParent", self);
        utils.setScriptVar(self, "objShip4", ship4);
        obj_id ship5 = space_create.createShip("npe_tie_fighter_falcon", spawn2);
        attachScript(ship5, "npe.npe_turret_ship");
        utils.setScriptVar(ship5, "objParent", self);
        utils.setScriptVar(self, "objShip5", ship5);
        obj_id ship6 = space_create.createShip("npe_tie_fighter_falcon", spawn2);
        attachScript(ship6, "npe.npe_turret_ship");
        utils.setScriptVar(ship6, "objParent", self);
        utils.setScriptVar(self, "objShip6", ship6);
        int squad = ship_ai.squadCreateSquadId();
        ship_ai.unitSetSquadId(ship1, squad);
        ship_ai.unitSetAttackOrders(ship1, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        ship_ai.unitSetSquadId(ship2, squad);
        ship_ai.unitSetAttackOrders(ship2, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        ship_ai.unitSetSquadId(ship3, squad);
        ship_ai.unitSetAttackOrders(ship3, ship_ai.ATTACK_ORDERS_HOLD_FIRE);
        ship_ai.squadSetLeader(squad, ship1);
        ship_ai.squadSetFormation(squad, ship_ai.FORMATION_DELTA);
        ship_ai.squadSetFormationSpacing(squad, 1);
        ship_ai.squadPatrol(squad, patrol);
        return SCRIPT_CONTINUE;
    }
    public int shipDied(obj_id self, dictionary params) throws InterruptedException
    {
        int remaining = utils.getIntScriptVar(self, "shipsRemaining");
        remaining -= 1;
        utils.setScriptVar(self, "shipsRemaining", remaining);
        obj_id droid = sequencer.getSequenceObject(self, "droid");
        obj_id hanSolo = sequencer.getSequenceObject(self, "hanSolo");
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        String stf = getStringObjVar(self, "strSequenceTable");
        if (remaining == 2)
        {
            string_id strChat = new string_id(stf, "han_kill_1");
            npe.commTutorialPlayer(hanSolo, player, 3, strChat, "sound/vo_han_solo_5c.snd", "object/mobile/npe/npe_han_solo.iff");
        }
        if (remaining == 1)
        {
            string_id strChat2 = new string_id(stf, "han_kill_2");
            space_utils.tauntPlayer(player, hanSolo, strChat2);
            npe.commTutorialPlayer(hanSolo, player, 3, strChat2, "sound/vo_han_solo_7c.snd", "object/mobile/npe/npe_han_solo.iff");
        }
        if (remaining == 0)
        {
            if (isIdValid(player))
            {
                xp.grantXpByTemplate(player, 10);
            }
            messageTo(self, "continueMainTable", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int finishedturret(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        utils.setScriptVar(player, "npe.finishedTurret", true);
        return SCRIPT_CONTINUE;
    }
    public int transitionSequence(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        obj_id hanSolo = sequencer.getSequenceObject(self, "hanSolo");
        String stf = getStringObjVar(self, "strSequenceTable");
        string_id strHanChat = new string_id(stf, "han_punch_it");
        chat.chat(hanSolo, strHanChat);
        play2dNonLoopingSound(player, "sound/vo_han_solo_9c.snd");
        messageTo(self, "hyperspaceFalcon", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int hyperspaceFalcon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        if (checkGod(player))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(player, "npe.falconEventStarted");
        utils.removeScriptVar(player, "npe.readyForTurret");
        utils.removeScriptVar(player, "npe.readyForHyperspace");
        utils.removeScriptVar(player, "npe.finishedTurret");
        utils.removeScriptVar(player, "firstTurret");
        detachScript(player, "npe.npe_falcon_player");
        attachScript(player, "npe.npe_station_player");
        location selfLoc = getLocation(self);
        String scene = getCurrentSceneName();
        hyperspacePlayerToLocation(self, scene, selfLoc.x, selfLoc.y, selfLoc.z, null, 0, 0, 0, "movePlayerToStation", false);
        setPosture(player, POSTURE_UPRIGHT);
        return SCRIPT_CONTINUE;
    }
    public int movePlayerToStation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "npe.player");
        messageTo(self, "endEncounter", null, 2, false);
        playCutScene(player, "video/npe_station.bik");
        npe.movePlayerFromFalconToSharedStation(player);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer) && !isPlayer(item))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        LOG("npe", "Received " + item);
        LOG("falcon_event", "ON RECEIVED ITEM GOES OFF!");
        if (isPlayer(item))
        {
            if (utils.hasScriptVar(item, "npe.readyforhyperspace"))
            {
                if (getLocation(item).cell == getCellId(self, "cockpit"))
                {
                    messageTo(self, "continueMainTable", null, 0, false);
                    messageTo(self, "transitionSequence", null, 0, false);
                }
            }
            if (!utils.hasScriptVar(item, "npe.falconEventStarted"))
            {
                utils.setScriptVar(item, "npe.falconEventStarted", true);
                messageTo(self, "endEncounter", null, 0, false);
                messageTo(self, "setupArea", null, 1, false);
                utils.setScriptVar(self, "npe.player", item);
                utils.setScriptVar(self, "intMainIndex", 0);
                utils.removeScriptVar(self, "intSecondIndex");
                utils.removeScriptVar(self, "strSecondaryTable");
                utils.removeScriptVar(self, "intSecondaryTableStamp");
                dictionary dctParams = new dictionary();
                dctParams.put("objPlayer", item);
                messageTo(self, "playerTimeoutAdvanceToStation", dctParams, 570, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            if (!utils.hasScriptVar(item, "npe.falconEventStarted"))
            {
                LOG("falcon_event", "ON LOST ITEM GOES OFF!");
                utils.removeScriptVar(self, "npe.player");
                utils.removeScriptVar(self, "intSecondIndex");
                utils.removeScriptVar(self, "strSecondaryTable");
                utils.setScriptVar(self, "intMainIndex", 0);
                messageTo(self, "endEncounter", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getBuildingContents(obj_id objObject) throws InterruptedException
    {
        Vector objContents = new Vector();
        objContents.setSize(0);
        obj_id[] objCells = getContents(objObject);
        for (int intI = 0; intI < objCells.length; intI++)
        {
            obj_id[] objTestContents = getContents(objCells[intI]);
            if ((objTestContents != null) && (objTestContents.length > 0))
            {
                for (int intJ = 0; intJ < objTestContents.length; intJ++)
                {
                    objContents = utils.addElement(objContents, objTestContents[intJ]);
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
    public boolean checkGod(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            sendSystemMessageTestingOnly(self, "Please turn off god mode when moving between npe locations. God mode and instances do not get along");
            return true;
        }
        return false;
    }
    public int clearhate(obj_id self, dictionary params) throws InterruptedException
    {
        ship_ai.unitRemoveFromAllAttackTargetLists(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        LOG("npe_falcon", "Falcon was destroyed.  Here come the logs!");
        LOG("npe_falcon", "FALCON OBJVARS WHEN DESTROYED: " + getPackedObjvars(self));
        LOG("npe_falcon", "FALCON SCRIPTS WHEN DESTROYED: " + utils.getPackedScripts(self));
        LOG("npe_falcon", "FALCON SCRIPTVARS WHEN DESTROYED: " + dump.getReadableScriptVars(self));
        Thread.dumpStack();
        return SCRIPT_CONTINUE;
    }
}
