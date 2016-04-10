package script.event.ewok_festival;

import script.dictionary;
import script.obj_id;

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
        if (!getLocation(self).area.equals("naboo"))
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
