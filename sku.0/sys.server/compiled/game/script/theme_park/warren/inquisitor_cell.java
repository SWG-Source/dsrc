package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class inquisitor_cell extends script.base_script
{
    public inquisitor_cell()
    {
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
        if (!cellUnlocked(self))
        {
            permissionsMakePrivate(getCellId(getTopMostContainer(self), "smallroom47"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean cellUnlocked(obj_id self) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        if (!isIdValid(bldg))
        {
            return false;
        }
        if (utils.hasScriptVar(bldg, "warren.cellOpened"))
        {
            return true;
        }
        return false;
    }
}
