package script.theme_park.naboo.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class search_skaak_bunker_01 extends script.base_script
{
    public search_skaak_bunker_01()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "doGating", null, 19, false);
        return SCRIPT_CONTINUE;
    }
    public int doGating(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id targetRoom2 = getCellId(self, "war_room");
        obj_id targetRoom1 = getCellId(self, "cell4");
        setObjVar(targetRoom1, "signal_name", "hugo_rescue_skaak_01");
        setObjVar(targetRoom2, "signal_name", "hugo_rescue_skaak_02");
        if (!hasScript(targetRoom1, "quest.task.ground.util.enter_room_signal"))
        {
            attachScript(targetRoom1, "quest.task.ground.util.enter_room_signal");
        }
        if (!hasScript(targetRoom2, "quest.task.ground.util.enter_room_signal"))
        {
            attachScript(targetRoom2, "quest.task.ground.util.enter_room_signal");
        }
        return SCRIPT_CONTINUE;
    }
}
