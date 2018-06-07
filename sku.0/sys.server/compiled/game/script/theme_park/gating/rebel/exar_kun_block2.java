package script.theme_park.gating.rebel;

import script.obj_id;

public class exar_kun_block2 extends script.base_script
{
    public exar_kun_block2()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
