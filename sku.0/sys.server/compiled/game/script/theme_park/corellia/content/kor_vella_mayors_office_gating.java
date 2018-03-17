package script.theme_park.corellia.content;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class kor_vella_mayors_office_gating extends script.base_script
{
    public kor_vella_mayors_office_gating()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "doGating", null, 19, true);
        return SCRIPT_CONTINUE;
    }
    public int doGating(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id targetRoom = getCellId(self, "livingroom");
        if (isIdValid(targetRoom))
        {
            if (!hasScript(targetRoom, "theme_park.corellia.content.kor_vella_mayors_office_block"))
            {
                attachScript(targetRoom, "theme_park.corellia.content.kor_vella_mayors_office_block");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
