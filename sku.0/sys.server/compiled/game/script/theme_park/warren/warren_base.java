package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.advanced_turret;
import script.library.utils;
import script.library.create;

public class warren_base extends script.base_script
{
    public warren_base()
    {
    }
    public static final String ESCAPEE = "warren.escapee";
    public static final String ESCAPEE_CREATURENAME = "commoner";
    public static final String ESCAPEE_SCRIPT = "theme_park.warren.escapee";
    public static final String ESCAPED_WORKER_CREATURENAME = "commoner";
    public static final String ESCAPED_WORKER_SCRIPT = "theme_park.warren.escaped_worker";
    public static final String DYING_LOYALIST_CREATURENAME = "commoner";
    public static final String DYING_LOYALIST_SCRIPT = "theme_park.warren.dying_loyalist";
    public static final String PANICKED_RESEARCHER_CREATURENAME = "commoner";
    public static final String PANICKED_RESEARCHER_SCRIPT = "theme_park.warren.panicked_researcher";
    public static final String CELL_GUARD_CREATURENAME = "brigand_leader";
    public static final String CELL_GUARD_SCRIPT = "theme_park.warren.cell_guard";
    public static final String KNAG_CREATURENAME = "brigand_leader";
    public static final String KNAG_SCRIPT = "theme_park.warren.doctor_knag";
    public static final String MIRLA_CREATURENAME = "commoner";
    public static final String MIRLA_SCRIPT = "theme_park.warren.mirla";
    public static final int WARREN_VERSION = 3;
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String ELEVATOR_OBJID = "8575772";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleDelayedSetup", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedSetup(obj_id self, dictionary params) throws InterruptedException
    {
        intializeWarren(self, null);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (isGod(speaker) && text.equals("reset"))
        {
            debugSpeakMsg(speaker, "resetting the warren!");
            intializeWarren(self, speaker);
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnTurret(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, "warren.turret"))
        {
            obj_id oldTurret = getObjIdObjVar(bldg, "warren.turret");
            if (oldTurret.isLoaded())
            {
                destroyObject(oldTurret);
            }
        }
        int yaw = -92;
        obj_id smallroom24 = getCellId(bldg, "smallroom24");
        location loc = new location(22.46f, -20.0f, -70.97f, "dantooine", smallroom24);
        obj_id turret = advanced_turret.createTurret(loc, yaw, advanced_turret.TYPE_TOWER, advanced_turret.SIZE_SMALL, advanced_turret.DAMAGE_ELEMENTAL_HEAT, 1000, 3000, 10000, 10.0f, 2.0f, "");
        setObjVar(bldg, "warren.turret", turret);
        attachScript(turret, "theme_park.warren.warren_turret");
    }
    public void intializeWarren(obj_id bldg, obj_id god) throws InterruptedException
    {
        if (hasObjVar(bldg, ESCAPEE))
        {
            obj_id oldEscapee = getObjIdObjVar(bldg, ESCAPEE);
            if (isIdValid(oldEscapee) && oldEscapee.isLoaded())
            {
                return;
            }
        }
        spawnEscapee(bldg);
        spawnEscapedWorker(bldg);
        spawnCloneRelicsBox(bldg);
        spawnElevator(bldg);
        spawnDyingLoyalist(bldg);
        spawnPanickedResearcher(bldg);
        spawnKnag(bldg);
        spawnDebris(bldg);
        resetTurretCodeSequence(bldg);
        lockReactorCoreRoom(bldg);
        lockCell(bldg);
        spawnCellGuard(bldg);
        spawnMirla(bldg);
        attachCommandWarning(bldg);
    }
    public void resetTurretCodeSequence(obj_id bldg) throws InterruptedException
    {
        String turretCodeSequence = toUpper(generateRandomName("object/creature/player/rodian_male.iff"));
        setObjVar(bldg, "warren.turretCodeSequence", turretCodeSequence);
        String cellPassword = toUpper(generateRandomName("object/creature/player/rodian_male.iff"));
        setObjVar(bldg, "warren.cellPassword", cellPassword);
    }
    public void spawnEscapee(obj_id bldg) throws InterruptedException
    {
        location outLoc = getBuildingEjectLocation(bldg);
        outLoc.x += 80.0f;
        outLoc.z += 80.0f;
        obj_id escapee = create.object(ESCAPEE_CREATURENAME, outLoc);
        if (!isIdValid(escapee))
        {
            return;
        }
        setObjVar(bldg, ESCAPEE, escapee);
        setObjVar(escapee, "warren.bldg", bldg);
        attachScript(escapee, ESCAPEE_SCRIPT);
    }
    public void spawnCellGuard(obj_id bldg) throws InterruptedException
    {
        location spawnLoc = getLocation(bldg);
        spawnLoc.x = 29.35f;
        spawnLoc.y = -50.0f;
        spawnLoc.z = -149.01f;
        spawnLoc.cell = getCellId(bldg, "smallroom46");
        obj_id escapee = create.createNpc(CELL_GUARD_CREATURENAME, "warren_jerrd_sonclim.iff", spawnLoc);
        if (!isIdValid(escapee))
        {
            return;
        }
        setYaw(escapee, -90.0f);
        setObjVar(escapee, "warren.bldg", bldg);
        attachScript(escapee, CELL_GUARD_SCRIPT);
    }
    public int handleCellGuardDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnCellGuard(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnKnag(obj_id bldg) throws InterruptedException
    {
        location spawnLoc = getLocation(bldg);
        spawnLoc.x = 77.47f;
        spawnLoc.y = -68.0f;
        spawnLoc.z = 38.95f;
        spawnLoc.cell = getCellId(bldg, "smallroom66");
        obj_id escapee = create.createNpc(KNAG_CREATURENAME, "warren_knag_garhun.iff", spawnLoc);
        if (!isIdValid(escapee))
        {
            return;
        }
        setObjVar(escapee, "warren.bldg", bldg);
        attachScript(escapee, KNAG_SCRIPT);
    }
    public int handleKnagDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnKnag(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnMirla(obj_id bldg) throws InterruptedException
    {
        location spawnLoc = getLocation(bldg);
        spawnLoc.x = -58.56f;
        spawnLoc.y = -76f;
        spawnLoc.z = -35.24f;
        spawnLoc.cell = getCellId(bldg, "smallroom54");
        obj_id escapee = create.createNpc(MIRLA_CREATURENAME, "warren_mirla.iff", spawnLoc);
        if (!isIdValid(escapee))
        {
            return;
        }
        setObjVar(escapee, "warren.bldg", bldg);
        attachScript(escapee, MIRLA_SCRIPT);
    }
    public int handleMirlaDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnMirla(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnEscapedWorker(obj_id bldg) throws InterruptedException
    {
        location spawnLoc = getLocation(bldg);
        spawnLoc.x = -7.47f;
        spawnLoc.y = -20.0f;
        spawnLoc.z = -61.61f;
        spawnLoc.cell = getCellId(bldg, "smallroom9");
        obj_id escapee = create.createNpc(ESCAPED_WORKER_CREATURENAME, "warren_phy_hudgen.iff", spawnLoc);
        if (!isIdValid(escapee))
        {
            return;
        }
        setObjVar(escapee, "warren.bldg", bldg);
        attachScript(escapee, ESCAPED_WORKER_SCRIPT);
    }
    public void spawnCloneRelicsBox(obj_id self) throws InterruptedException
    {
        obj_id terminalRoom = getCellId(self, "entryhall");
        location computerloc = new location(-31.9f, -20.0f, -11.7f, "dantooine", terminalRoom);
        int yaw = 90;
        obj_id computer = create.object("object/tangible/quest/clone_relics_computer_terminal.iff", computerloc);
        setYaw(computer, yaw);
        return;
    }
    public void spawnElevator(obj_id bldg) throws InterruptedException
    {
        obj_id elevator = utils.stringToObjId(ELEVATOR_OBJID);
        detachScript(elevator, "systems.elevator.elevator");
        attachScript(elevator, "theme_park.warren.elevator_one");
        return;
    }
    public void spawnDyingLoyalist(obj_id bldg) throws InterruptedException
    {
        location spawnLoc = getLocation(bldg);
        spawnLoc.x = -57.54f;
        spawnLoc.y = -28.0f;
        spawnLoc.z = -91.04f;
        spawnLoc.cell = getCellId(bldg, "smallroom29");
        obj_id loyalist = create.createNpc(DYING_LOYALIST_CREATURENAME, "warren_dying_loyalist.iff", spawnLoc);
        if (!isIdValid(loyalist))
        {
            return;
        }
        setObjVar(loyalist, "warren.bldg", bldg);
        attachScript(loyalist, DYING_LOYALIST_SCRIPT);
    }
    public void spawnPanickedResearcher(obj_id bldg) throws InterruptedException
    {
        location spawnLoc = getLocation(bldg);
        spawnLoc.x = 29.68f;
        spawnLoc.y = -54.0f;
        spawnLoc.z = -116.8f;
        spawnLoc.cell = getCellId(bldg, "bigroom43");
        obj_id loyalist = create.createNpc(PANICKED_RESEARCHER_CREATURENAME, "warren_research_scientist.iff", spawnLoc);
        if (!isIdValid(loyalist))
        {
            return;
        }
        setObjVar(loyalist, "warren.bldg", bldg);
        attachScript(loyalist, PANICKED_RESEARCHER_SCRIPT);
    }
    public int handleLoyalistDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnDyingLoyalist(self);
        return SCRIPT_CONTINUE;
    }
    public int handleEscapeeDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnEscapee(self);
        return SCRIPT_CONTINUE;
    }
    public int handleEscapedWorkerDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnEscapedWorker(self);
        return SCRIPT_CONTINUE;
    }
    public int handleResearcherDied(obj_id self, dictionary params) throws InterruptedException
    {
        spawnPanickedResearcher(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnDebris(obj_id bldg) throws InterruptedException
    {
        if (hasObjVar(bldg, "warren.debrisObject"))
        {
            obj_id oldDebris = getObjIdObjVar(bldg, "warren.debrisObject");
            if (isIdValid(oldDebris))
            {
                if (hasScript(oldDebris, "object.destroy_message"))
                {
                    detachScript(oldDebris, "object.destroy_message");
                }
                destroyObject(oldDebris);
            }
        }
        location spawnLoc = getLocation(bldg);
        spawnLoc.x = -54.55f;
        spawnLoc.y = -68.0f;
        spawnLoc.z = .63f;
        spawnLoc.cell = getCellId(bldg, "smallroom52");
        obj_id oldDebrises[] = getContents(spawnLoc.cell);
        if (oldDebrises != null)
        {
            for (int i = 0; i < oldDebrises.length; i++)
            {
                if (hasScript(oldDebrises[i], "object.destroy_message"))
                {
                    detachScript(oldDebrises[i], "object.destroy_message");
                }
                destroyObject(oldDebrises[i]);
            }
        }
        obj_id debris = create.object("object/tangible/newbie_tutorial/debris.iff", spawnLoc);
        create.addDestroyMessage(debris, "handleRespawnDebris", 1800, bldg);
        setObjVar(bldg, "warren.debrisObject", debris);
    }
    public int handleRespawnDebris(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "warren.debrisObject");
        spawnDebris(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(destContainer))
        {
            return SCRIPT_CONTINUE;
        }
        if (!containsPlayer(self))
        {
            removeAllAllowed(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (isGod(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (isAllowed(self, item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pInv = utils.getInventoryContainer(item);
        obj_id[] contents = getContents(pInv);
        if (contents == null)
        {
            return SCRIPT_OVERRIDE;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (hasObjVar(contents[i], "warren.keycode"))
            {
                destroyObject(contents[i]);
                addAllowed(self, item);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(item, new string_id(SYSTEM_MESSAGES, "access_denied"));
        return SCRIPT_OVERRIDE;
    }
    public boolean containsPlayer(obj_id bldg) throws InterruptedException
    {
        obj_id[] contents = getContents(bldg);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; ++i)
            {
                if (isPlayer(contents[i]) || containsPlayer(contents[i]))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void addAllowed(obj_id bldg, obj_id player) throws InterruptedException
    {
        if (player.isLoaded() && isPlayer(player))
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "access_granted"));
        }
        utils.setScriptVar(player, "warren.isAllowed", true);
        String listName = "allowedList";
        obj_id group = getGroupObject(player);
        if (hasObjVar(bldg, listName))
        {
            obj_id[] allowedList = getObjIdArrayObjVar(bldg, listName);
            if (isIdValid(group))
            {
                allowedList = getObjIdArrayObjVar(bldg, listName);
                if (!utils.isObjIdInArray(allowedList, group))
                {
                    obj_id[] newAllowedList = new obj_id[allowedList.length + 1];
                    System.arraycopy(allowedList, 0, newAllowedList, 0, allowedList.length);
                    newAllowedList[allowedList.length] = group;
                    if (newAllowedList.length > 0)
                    {
                        setObjVar(bldg, listName, newAllowedList);
                    }
                }
            }
            return;
        }
        else if (isIdValid(group))
        {
            obj_id[] allowedList = new obj_id[1];
            allowedList[0] = group;
            setObjVar(bldg, listName, allowedList);
        }
    }
    public void removeAllAllowed(obj_id bldg) throws InterruptedException
    {
        removeObjVar(bldg, "allowedList");
        resetTurretCodeSequence(bldg);
    }
    public boolean isAllowed(obj_id bldg, obj_id player) throws InterruptedException
    {
        boolean playerIsAllowed = false;
        if (utils.hasScriptVar(player, "warren.isAllowed"))
        {
            playerIsAllowed = true;
        }
        String listName = "allowedList";
        if (!hasObjVar(bldg, listName))
        {
            return playerIsAllowed;
        }
        obj_id[] allowedList = getObjIdArrayObjVar(bldg, listName);
        obj_id group = getGroupObject(player);
        if (!isIdValid(group))
        {
            return playerIsAllowed;
        }
        if (utils.isObjIdInArray(allowedList, group))
        {
            if (!playerIsAllowed)
            {
                addAllowed(bldg, player);
            }
            return true;
        }
        else 
        {
            if (playerIsAllowed)
            {
                addAllowed(bldg, player);
                return true;
            }
        }
        return false;
    }
    public void lockReactorCoreRoom(obj_id bldg) throws InterruptedException
    {
        obj_id cell = getCellId(bldg, "smallroom44");
        if (!isIdValid(cell))
        {
            return;
        }
        if (!hasScript(cell, "theme_park.warren.reactor_core"))
        {
            attachScript(cell, "theme_park.warren.reactor_core");
        }
        return;
    }
    public void lockCell(obj_id bldg) throws InterruptedException
    {
        obj_id cell = getCellId(bldg, "smallroom47");
        if (!isIdValid(cell))
        {
            return;
        }
        if (!hasScript(cell, "theme_park.warren.inquisitor_cell"))
        {
            attachScript(cell, "theme_park.warren.inquisitor_cell");
        }
        return;
    }
    public void attachCommandWarning(obj_id bldg) throws InterruptedException
    {
        obj_id cell = getCellId(bldg, "smallroom78");
        if (!isIdValid(cell))
        {
            return;
        }
        if (!hasScript(cell, "theme_park.warren.command_warning"))
        {
            attachScript(cell, "theme_park.warren.command_warning");
        }
        return;
    }
    public int handlePlayerLogin(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isAllowed(self, player))
        {
            addAllowed(self, player);
        }
        return SCRIPT_CONTINUE;
    }
}
