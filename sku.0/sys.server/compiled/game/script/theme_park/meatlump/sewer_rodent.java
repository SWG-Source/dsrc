package script.theme_park.meatlump;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.create;
import script.library.utils;

public class sewer_rodent extends script.base_script
{
    public sewer_rodent()
    {
    }
    public static final String STARTER_SLOT = "meatlump_wine_starter_slot";
    public static final String COLLECTION_NAME = "col_meatlump_wine_01";
    public static final String SLOT_NAME = "meatlump_wine_juicer_slot";
    public static final float UPDATE_RADIUS = 0.5f;
    public static final string_id SID_COLLECT_JUICE = new string_id("collection", "meatlump_collect_juice");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("meatlump_rodent_trigger_volume", UPDATE_RADIUS, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id breecher) throws InterruptedException
    {
        if (!isIdValid(breecher) || !exists(breecher))
        {
            return SCRIPT_CONTINUE;
        }
        location startLocation = getLocation(self);
        location newLocation = null;
        if (hasCompletedCollectionSlot(breecher, "meatlump_wine_juicer_slot") && !hasCompletedCollection(breecher, COLLECTION_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasCompletedCollectionSlotPrereq(breecher, SLOT_NAME) || hasCompletedCollection(breecher, COLLECTION_NAME))
        {
            for (int i = 0; i < 10; ++i)
            {
                newLocation = utils.getRandomLocationInRing(startLocation, 10.0f, 10.0f);
                if (isIdValid(newLocation.cell))
                {
                    if (isValidInteriorLocation(newLocation))
                    {
                        String creatureName = getCreatureName(self);
                        if (creatureName.equals("meatlump_worrt"))
                        {
                            setMovementPercent(self, 3.0f);
                        }
                        else if (creatureName.equals("meatlump_kreetle"))
                        {
                            setMovementPercent(self, 1.0f);
                        }
                        pathTo(self, newLocation);
                        break;
                    }
                }
            }
        }
        else 
        {
            setInvulnerable(self, false);
            setHealth(self, -1000);
            removeTriggerVolume("meatlump_rodent_trigger_volume");
            modifyCollectionSlotValue(breecher, "meatlump_wine_juicer_slot", 1);
            sendSystemMessage(breecher, SID_COLLECT_JUICE);
        }
        return SCRIPT_CONTINUE;
    }
}
