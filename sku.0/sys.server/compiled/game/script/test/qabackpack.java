package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.qa;
import script.library.sui;
import script.library.utils;

public class qabackpack extends script.base_script
{
    public qabackpack()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qabackpack");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qabackpack");
        }
        sendSystemMessage(self, "QA Backpack script attached.\nAny item received will be moved into a QA backpack.\nAny items moved from the backpack should not be automatically moved into the backpack.\nDetach script test.qabackpack to remove qabackpack automatic inventory functions.", null);
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id player = self;
        if (isGod(player))
        {
            if ((toLower(text)).equals("qabackpack stop"))
            {
                sendSystemMessage(self, "Not yet implemented. QA Backpack automatic inventory functions DISABLED.", null);
                return SCRIPT_OVERRIDE;
            }
            if ((toLower(text)).equals("qabackpack start"))
            {
                sendSystemMessage(self, "Not yet implemented. QA Backpack automatic inventory functions ENABLED.", null);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnContainerChildGainItem(obj_id self, obj_id item, obj_id source, obj_id transferer) throws InterruptedException
    {
        putInQaBackpack(item, self, source);
        return SCRIPT_CONTINUE;
    }
    public void putInQaBackpack(obj_id item, obj_id player, obj_id source) throws InterruptedException
    {
        obj_id testerInventoryId = utils.getInventoryContainer(player);
        qa.findOrCreateAndEquipQABag(player, testerInventoryId, false);
        obj_id myBag = getObjectInSlot(player, "back");
        if (!isValidId(myBag) && isValidId(testerInventoryId))
        {
            sendSystemMessage(player, "Error: Something bad happened in test.qabackpack", null);
            return;
        }
        obj_id itemContainer = getContainedBy(item);
        if (itemContainer != testerInventoryId)
        {
            return;
        }
        if (myBag == source)
        {
            return;
        }
        putInOverloaded(item, myBag);
        return;
    }
}
