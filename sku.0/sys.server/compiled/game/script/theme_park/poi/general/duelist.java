package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.hue;
import script.library.colors;

public class duelist extends script.theme_park.poi.base
{
    public duelist()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "createDuelists", null, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "createDuelists", null, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int createDuelists(obj_id self, dictionary params) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("duel"))
        {
            obj_id duelist1 = poiCreateNpc("duelist", 1, 1);
            obj_id duelist2 = poiCreateNpc("duelist", 1, 10);
            faceTo(duelist1, duelist2);
            faceTo(duelist2, duelist1);
            attachScript(duelist1, "theme_park.poi.general.conversation.duelist_convo");
            attachScript(duelist2, "theme_park.poi.general.conversation.duelist_convo");
            setObjVar(duelist1, "opponent", duelist2);
            setObjVar(duelist2, "opponent", duelist1);
            ai_lib.setDefaultCalmBehavior(duelist1, ai_lib.BEHAVIOR_SENTINEL);
            ai_lib.setDefaultCalmBehavior(duelist2, ai_lib.BEHAVIOR_SENTINEL);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
