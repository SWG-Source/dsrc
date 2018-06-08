package script.theme_park.poi.general.behavior;

import script.obj_id;

public class dangerous_animal extends script.base_script
{
    public dangerous_animal()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "quester");
        messageTo(player, "IDied", null, 0, true);
        return SCRIPT_CONTINUE;
    }
}
