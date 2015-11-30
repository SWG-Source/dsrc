package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class invtest extends script.base_script
{
    public invtest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "attached!");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id bank = utils.getPlayerBank(self);
        if (!isIdValid(bank))
        {
            sendSystemMessageTestingOnly(self, "bank is invalid");
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(self);
        if (!isIdValid(inv))
        {
            sendSystemMessageTestingOnly(self, "inventory is invalid");
            return SCRIPT_CONTINUE;
        }
        obj_id[] bankContents = getContents(bank);
        if (bankContents == null)
        {
            sendSystemMessageTestingOnly(self, "bank contents is null");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "bank has " + bankContents.length + " items");
        obj_id[] invContents = getContents(inv);
        if (invContents == null)
        {
            sendSystemMessageTestingOnly(self, "inv contents is null");
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "inv has " + invContents.length + " items");
        if (invContents.length > bankContents.length)
        {
            sendSystemMessageTestingOnly(self, "moving inv contents to bank");
            int numMoved = moveObjects(invContents, bank);
            sendSystemMessageTestingOnly(self, "moved " + numMoved + " objects");
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "moving bank contents to inv");
            int numMoved = moveObjects(bankContents, inv);
            sendSystemMessageTestingOnly(self, "moved " + numMoved + " objects");
        }
        return SCRIPT_CONTINUE;
    }
}
