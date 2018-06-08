package script.npe;

import script.library.groundquests;
import script.library.npe;
import script.obj_id;

public class npe_notify_grenade extends script.base_script
{
    public npe_notify_grenade()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(item))
        {
            if (groundquests.isTaskActive(item, "npe_commando", "thugs"))
            {
                npe.giveGrenadePopUp(item, null);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
