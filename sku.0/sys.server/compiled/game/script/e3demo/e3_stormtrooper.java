package script.e3demo;

import script.dictionary;
import script.library.factions;
import script.obj_id;

public class e3_stormtrooper extends script.base_script
{
    public e3_stormtrooper()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "doFaceTo", null, 5, false);
        setAnimationMood(self, "npc_imperial");
        factions.setFaction(self, "Imperial");
        return SCRIPT_CONTINUE;
    }
    public int doFaceTo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objTestObjects = getAllObjectsWithObjVar(getLocation(self), 2000, "stormtrooperFace");
        faceTo(self, getLocation(objTestObjects[0]));
        return SCRIPT_CONTINUE;
    }
    public int doEmote(obj_id self, dictionary params) throws InterruptedException
    {
        String strEmote = params.getString("strEmote");
        doAnimationAction(self, strEmote);
        return SCRIPT_CONTINUE;
    }
}
