package script.working.jfreeman;

import script.dictionary;
import script.library.ai_lib;
import script.library.pet_lib;
import script.obj_id;

public class makepet extends script.base_script
{
    public makepet()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached.");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (pet_lib.hasMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("pet"))
        {
            dictionary params = new dictionary();
            params.put("pet", self);
            params.put("master", speaker);
            messageTo(self, "handleAddMaster", params, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
}
