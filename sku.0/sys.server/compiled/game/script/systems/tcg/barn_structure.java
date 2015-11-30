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

public class barn_structure extends script.base_script
{
    public barn_structure()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        checkStructureForRanchHandScript(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        checkStructureForRanchHandScript(self);
        return SCRIPT_CONTINUE;
    }
    public boolean checkStructureForRanchHandScript(obj_id self) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        obj_id cellId = getCellId(self, tcg.CELL_NAME);
        if (!isValidId(cellId) || !exists(cellId))
        {
            return false;
        }
        if (hasObjVar(cellId, tcg.RANCHHAND_CELLCHECK) && !hasScript(cellId, tcg.RANCHHAND_CELL_SCRIPT))
        {
            return false;
        }
        attachScript(cellId, tcg.RANCHHAND_CELL_SCRIPT);
        return true;
    }
}
