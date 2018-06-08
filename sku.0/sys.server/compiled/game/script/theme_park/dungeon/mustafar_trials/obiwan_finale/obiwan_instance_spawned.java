package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.dictionary;
import script.obj_id;

public class obiwan_instance_spawned extends script.base_script
{
    public obiwan_instance_spawned()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanUp", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanUp", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_instance_spawned");
        return SCRIPT_CONTINUE;
    }
}
