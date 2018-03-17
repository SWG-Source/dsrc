package script.systems.respec;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.force_rank;
import script.library.groundquests;
import script.library.npe;
import script.library.respec;
import script.library.skill_template;
import script.library.skill;
import script.library.static_item;
import script.library.sui;
import script.library.utils;
import script.library.xp;

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
