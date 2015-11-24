package script.structure.municipal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.structure;
import script.library.utils;

public class bank extends script.base_script
{
    public bank()
    {
    }
    public static final String SCRIPT_ME = "structure.municipal.bank";
    public static final String JUNK_DEALER = "junk_dealer";
    public static final String DATATABLE_TERMINAL_LIST = "datatables/structure/municipal/bank_terminal.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "createBankTerminalsAndVendors", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int createBankTerminalsAndVendors(obj_id self, dictionary params) throws InterruptedException
    {
        location bankLoc = getLocation(self);
        String planet = bankLoc.area;
        float bankYaw = getYaw(self);
        location junkDealerLoc01 = new location(bankLoc.x + 11.8f, bankLoc.y + 0.5f, bankLoc.z + 11.8f, planet, null);
        float junkDealerYaw01 = -135.0f;
        location junkDealerLoc02 = new location(bankLoc.x - 11.8f, bankLoc.y + 0.5f, bankLoc.z - 11.8f, planet, null);
        float junkDealerYaw02 = 45.0f;
        if (bankYaw != 0)
        {
            junkDealerLoc01 = utils.rotatePointXZ(bankLoc, junkDealerLoc01, bankYaw);
            junkDealerYaw01 -= (90 - bankYaw);
            junkDealerLoc02 = utils.rotatePointXZ(bankLoc, junkDealerLoc02, bankYaw);
            junkDealerYaw02 -= (90 - bankYaw);
        }
        obj_id junkDealer01 = create.object(JUNK_DEALER, junkDealerLoc01);
        if (isIdValid(junkDealer01))
        {
            setYaw(junkDealer01, junkDealerYaw01);
            setInvulnerable(junkDealer01, true);
            setObjVar(junkDealer01, "creation.myBank", self);
        }
        obj_id junkDealer02 = create.object(JUNK_DEALER, junkDealerLoc02);
        if (isIdValid(junkDealer02))
        {
            setYaw(junkDealer02, junkDealerYaw02);
            setInvulnerable(junkDealer02, true);
            setObjVar(junkDealer02, "creation.myBank", self);
        }
        structure.createStructureTerminals(self, SCRIPT_ME, DATATABLE_TERMINAL_LIST);
        return SCRIPT_CONTINUE;
    }
}
