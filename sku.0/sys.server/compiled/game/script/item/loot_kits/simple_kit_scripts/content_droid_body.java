package script.item.loot_kits.simple_kit_scripts;

import script.dictionary;
import script.obj_id;

public class content_droid_body extends script.base_script
{
    public content_droid_body()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int setUp(obj_id self, dictionary params) throws InterruptedException
    {
        String[] needs = new String[4];
        needs[0] = "object/tangible/loot/simple_kit/legacy_droid_body.iff";
        needs[1] = "object/tangible/loot/simple_kit/legacy_droid_inners.iff";
        needs[2] = "object/tangible/loot/simple_kit/legacy_droid_legs.iff";
        needs[3] = "object/tangible/loot/simple_kit/legacy_droid_motivator.iff";
        setObjVar(self, "needs", needs);
        setObjVar(self, "overview", needs);
        setObjVar(self, "reward", "object/tangible/loot/simple_kit/legacy_droid_dead.iff");
        return SCRIPT_CONTINUE;
    }
}
