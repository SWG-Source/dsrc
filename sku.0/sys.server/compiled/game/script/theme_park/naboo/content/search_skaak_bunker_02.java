package script.theme_park.naboo.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class search_skaak_bunker_02 extends script.base_script
{
    public search_skaak_bunker_02()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "doGating", null, 19, true);
        return SCRIPT_CONTINUE;
    }
    public int doGating(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id targetRoom = getCellId(self, "core");
        setObjVar(targetRoom, "signal_name", "hugo_rescue_skaak_02");
        if (!hasScript(targetRoom, "quest.task.ground.util.enter_room_signal"))
        {
            attachScript(targetRoom, "quest.task.ground.util.enter_room_signal");
        }
        return SCRIPT_CONTINUE;
    }
}
