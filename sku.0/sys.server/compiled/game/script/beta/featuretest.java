package script.beta;

import script.library.features;
import script.library.utils;
import script.obj_id;

public class featuretest extends script.base_script
{
    public featuretest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
