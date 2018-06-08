package script.systems.respec;

import script.obj_id;

public class click_combat_respec extends script.base_script
{
    public click_combat_respec()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        playUiEffect(self, "showMediator=ws_professiontemplateselect");
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        detachScript(self, "systems.respec.click_combat_respec");
        return SCRIPT_CONTINUE;
    }
}
