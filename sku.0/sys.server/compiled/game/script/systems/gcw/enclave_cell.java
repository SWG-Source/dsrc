package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.force_rank;
import script.library.trace;
import script.library.utils;
import script.library.pet_lib;

public class enclave_cell extends script.base_script
{
    public enclave_cell()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (force_rank.checkCellPermission(item, self, false))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
}
