package script.quest.hero_of_tatooine;

import script.dictionary;
import script.obj_id;

public class pirates extends script.base_script
{
    public pirates()
    {
    }
    public int die(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
