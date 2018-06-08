package script.systems.gcw;

import script.library.force_rank;
import script.obj_id;

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
