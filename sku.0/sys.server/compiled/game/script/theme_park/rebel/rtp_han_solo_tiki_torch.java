package script.theme_park.rebel;

import script.dictionary;
import script.location;
import script.obj_id;

public class rtp_han_solo_tiki_torch extends script.base_script
{
    public rtp_han_solo_tiki_torch()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "questFlavorObjectSpawned"))
        {
            removeObjVar(self, "questFlavorObjectSpawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "questFlavorObjectSpawned"))
        {
            removeObjVar(self, "questFlavorObjectSpawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestFlavorObject(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "questFlavorObjectSpawned"))
        {
            String effectName = "appearance/pt_burning_smokeandembers_md.prt";
            location here = getLocation(self);
            obj_id[] players = getPlayerCreaturesInRange(here, 100.0f);
            playClientEffectLoc(players, effectName, here, 1.445f);
            setObjVar(self, "questFlavorObjectSpawned", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestFlavorObjectCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "questFlavorObjectSpawned"))
        {
            removeObjVar(self, "questFlavorObjectSpawned");
        }
        return SCRIPT_CONTINUE;
    }
}
