package script.gambling.slot;

import script.library.gambling;
import script.obj_id;

public class standard extends script.gambling.base.slot
{
    public standard()
    {
    }
    private static final String GAME_TYPE = "slot_standard";
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
