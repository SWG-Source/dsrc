package script.working.tfiala;

import script.color;
import script.obj_id;
import script.string_id;

public class flytext_test extends script.base_script
{
    public flytext_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("flytext_test"))
        {
            showFlyText(self, new string_id("ui", "game_exit"), 2.0f, new color(0, 255, 0, 0));
        }
        else if (strText.equals("flytextprivate_test"))
        {
            showFlyTextPrivate(self, objSpeaker, new string_id("ui", "game_exit"), 1.0f, new color(0, 0, 255, 0));
        }
        return SCRIPT_CONTINUE;
    }
}
