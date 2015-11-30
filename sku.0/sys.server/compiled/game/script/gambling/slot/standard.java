package script.gambling.slot;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.gambling;

public class standard extends script.gambling.base.slot
{
    public standard()
    {
    }
    public static final String GAME_TYPE = "slot_standard";
    public static final String TBL = "datatables/gambling/slot/" + GAME_TYPE + ".iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String gameType = GAME_TYPE;
        if (hasObjVar(self, gambling.VAR_PREDEFINED_TYPE))
        {
            gameType = getStringObjVar(self, gambling.VAR_PREDEFINED_TYPE);
        }
        gambling.initializeTable(self, gameType);
        return super.OnInitialize(self);
    }
}
