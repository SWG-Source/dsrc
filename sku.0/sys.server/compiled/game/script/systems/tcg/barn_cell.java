package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.tcg;
import script.library.utils;

public class barn_cell extends script.base_script
{
    public barn_cell()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (spawnRanchHand(self))
        {
            CustomerServiceLog("playerStructure", "Ranch Hand for barn: " + self + " located at: " + getLocation(self) + " Could not be created.");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, tcg.RANCHHAND_CELLCHECK))
        {
            if (validatedRanchHand(self))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (spawnRanchHand(self))
        {
            CustomerServiceLog("playerStructure", "Ranch Hand for barn: " + self + " located at: " + getLocation(self) + " Could not be created.");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (hasObjVar(self, tcg.RANCHHAND_CELLCHECK))
        {
            if (validatedRanchHand(self))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (isMob(item) && !isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        spawnRanchHand(self);
        return SCRIPT_CONTINUE;
    }
    public boolean spawnRanchHand(obj_id self) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        obj_id container = getContainedBy(self);
        if (player_structure.isPackedUp(container))
        {
            return false;
        }
        if (hasObjVar(self, tcg.RANCHHAND_CELLCHECK))
        {
            if (validatedRanchHand(self))
            {
                return false;
            }
        }
        obj_id structure = getTopMostContainer(self);
        if (!isValidId(structure) || !exists(structure))
        {
            return false;
        }
        String sceneName = getCurrentSceneName();
        if (sceneName == null || sceneName.equals(""))
        {
            return false;
        }
        location spot = new location(tcg.RANCH_HAND_X, tcg.RANCH_HAND_Y, tcg.RANCH_HAND_Z, sceneName, self);
        obj_id ranchHand = createObjectInCell(tcg.RANCH_HAND_TEMPLATE, structure, tcg.CELL_NAME, spot);
        if (!isValidId(ranchHand) || !exists(ranchHand))
        {
            return false;
        }
        setYaw(ranchHand, tcg.RANCH_HAND_HEADING);
        setObjVar(self, tcg.RANCHHAND_OBJECT, ranchHand);
        setObjVar(self, tcg.RANCHHAND_CELLCHECK, true);
        setObjVar(ranchHand, tcg.RANCHHAND_CELL, self);
        CustomerServiceLog("playerStructure", "Ranch Hand for barn: " + self + " located at: " + getLocation(self) + " spawned a ranchhand " + getName(ranchHand) + " ( " + ranchHand + " ) mob in Entry Cell of barn.");
        return true;
    }
    public boolean validatedRanchHand(obj_id self) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!hasObjVar(self, tcg.RANCHHAND_OBJECT))
        {
            return false;
        }
        obj_id ranchHand = getObjIdObjVar(self, tcg.RANCHHAND_OBJECT);
        if (!isValidId(ranchHand) || !exists(ranchHand))
        {
            return false;
        }
        location ranchHandLoc = getLocation(ranchHand);
        if (ranchHandLoc == null)
        {
            return false;
        }
        LOG("ranchhand", "barn_cell.validatedRanchHand ranchHandLoc.cell: " + ranchHandLoc.cell + " self:" + self);
        if (ranchHandLoc.cell != self)
        {
            destroyObject(ranchHand);
            return false;
        }
        return true;
    }
}
