package script.systems.collections;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class trigger_volume_vistas extends script.base_script
{
    public trigger_volume_vistas()
    {
    }
    public static final String COLLECTION_PREFIX = "col_panoramic_vistas_";
    public static final String SLOT_OBJVAR = "collection.marker";
    public static final float UPDATE_RADIUS = 2.0f;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume("vistas_trigger_volume", UPDATE_RADIUS, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id breecher) throws InterruptedException
    {
        if (!isIdValid(breecher) || !exists(breecher))
        {
            return SCRIPT_CONTINUE;
        }
        String planetName = getCurrentSceneName();
        String slotToUpdate = getStringObjVar(self, SLOT_OBJVAR);
        if (slotToUpdate == null || slotToUpdate.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasCompletedCollection(breecher, COLLECTION_PREFIX + planetName) || !hasCompletedCollectionSlotPrereq(breecher, slotToUpdate))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasCompletedCollectionSlot(breecher, slotToUpdate))
        {
            modifyCollectionSlotValue(breecher, slotToUpdate, 1);
        }
        return SCRIPT_CONTINUE;
    }
}
