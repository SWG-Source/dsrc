package script.event.ewok_festival;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class shaman extends script.base_script
{
    public shaman()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleChiefInitialize", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int handleChiefInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        if (!here.area.equals("naboo"))
        {
            setName(self, "an Ewok shaman");
        }
        if (hasCondition(self, CONDITION_INTERESTING))
        {
            clearCondition(self, CONDITION_INTERESTING);
        }
        if (hasCondition(self, CONDITION_SPACE_INTERESTING))
        {
            clearCondition(self, CONDITION_SPACE_INTERESTING);
        }
        setCondition(self, CONDITION_HOLIDAY_INTERESTING);
        return SCRIPT_CONTINUE;
    }
}
