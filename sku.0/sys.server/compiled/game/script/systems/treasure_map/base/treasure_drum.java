package script.systems.treasure_map.base;

import script.dictionary;
import script.obj_id;

public class treasure_drum extends script.base_script
{
    public treasure_drum()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "cleanUpChest", null, 1000, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUpChest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id chest = self;
        playClientEffectLoc(self, "clienteffect/lair_damage_light.cef", getLocation(self), 1.0f);
        destroyObject(chest);
        return SCRIPT_CONTINUE;
    }
}
