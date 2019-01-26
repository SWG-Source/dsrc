package script.space.combat;

import script.location;
import script.obj_id;

public class combat_ship_security_terminal extends script.base_script
{
    public combat_ship_security_terminal()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String strCellToLock = getStringObjVar(self, "strCellToLock");
        if (strCellToLock == null)
        {
            LOG("space", "no cell to lock?!@?!?@");
            return SCRIPT_CONTINUE;
        }
        obj_id objShip = getTopMostContainer(self);
        if (!isIdValid(objShip))
        {
            LOG("space", "Terminal not in ship");
            return SCRIPT_CONTINUE;
        }
        permissionsMakePrivate(getCellId(objShip, strCellToLock));
        LOG("space", "made " + strCellToLock + " private");
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        String strCellToLock = getStringObjVar(self, "strCellToLock");
        if (strCellToLock == null)
        {
            LOG("space", "no cell to lock?!@?!?@");
            return SCRIPT_CONTINUE;
        }
        obj_id objShip = getTopMostContainer(self);
        if (!isIdValid(objShip))
        {
            LOG("space", "Terminal not in ship");
            return SCRIPT_CONTINUE;
        }
        permissionsMakePublic(getCellId(objShip, strCellToLock));
        LOG("space", "made " + strCellToLock + " public");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "lairDestroyed"))
        {
            messageTo(self, "destroyDisabledLair", null, 0.5f, false);
        }
        attachScript(self, "systems.npc_lair.lair_interactivity");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectObj(killer, "clienteffect/combat_explosion_lair_large.cef", self, "");
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id objAttacker, obj_id objWeapon, int intDamage) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
