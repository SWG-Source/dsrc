package script.quest.hero_of_tatooine;

import script.dictionary;
import script.obj_id;

public class altruism_farmer extends script.base_script
{
    public altruism_farmer()
    {
    }
    public int handleDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
