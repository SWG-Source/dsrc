package script.systems.crafting.structure;

import script.obj_id;

public class harvest_component extends script.base_script
{
    public harvest_component()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (hasObjVar(self, "crafting_components.extractRate"))
        {
            float hs = getFloatObjVar(self, "crafting_components.extractRate");
            names[0] = "examine_extractionrate";
            attribs[0] = Integer.toString(((int)hs));
        }
        return SCRIPT_CONTINUE;
    }
}
