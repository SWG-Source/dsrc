package script.theme_park.dungeon.nova_orion_station;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;

public class band_member_instrument extends script.base_script
{
    public band_member_instrument()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "giveBandMemberAnIntrument", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int giveBandMemberAnIntrument(obj_id self, dictionary params) throws InterruptedException
    {
        String myInstrument = utils.getStringObjVar(self, "myInstrument");
        if (myInstrument != null || myInstrument.length() > 0)
        {
            obj_id instrument = createObject(myInstrument, self, "");
            equip(instrument, self);
        }
        return SCRIPT_CONTINUE;
    }
}
