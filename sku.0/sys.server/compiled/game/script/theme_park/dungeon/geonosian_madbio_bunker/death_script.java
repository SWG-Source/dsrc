package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class death_script extends script.base_script
{
    public death_script()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "doubleCheck", null, 3600, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpse) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        if (isIdValid(top))
        {
            String name = getTemplateName(top);
            if (name.equals("object/building/general/bunker_mad_bio.iff"))
            {
                CustomerServiceLog("DUNGEON_GeonosianBunker", "*Geonosian Death: " + self + ": " + getName(self) + " has died in the Geonosian Dungeon.");
                detachScript(self, "theme_park.dungeon.geonosian_madbio_bunker.death_script");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int doubleCheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        if (isIdValid(top))
        {
            String name = getTemplateName(top);
            if (name.equals("object/building/general/bunker_mad_bio.iff"))
            {
                messageTo(self, "doubleCheck", null, 3600, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                detachScript(self, "theme_park.dungeon.geonosian_madbio_bunker.death_script");
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            detachScript(self, "theme_park.dungeon.geonosian_madbio_bunker.death_script");
        }
        return SCRIPT_CONTINUE;
    }
}
