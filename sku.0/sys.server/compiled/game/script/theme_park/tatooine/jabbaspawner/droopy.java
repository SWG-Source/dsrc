package script.theme_park.tatooine.jabbaspawner;

import script.dictionary;
import script.library.ai_lib;
import script.obj_id;

public class droopy extends script.base_script
{
    public droopy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        grantSkill(self, "social_entertainer_novice");
        messageTo(self, "startPlaying", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "droopyDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public int startPlaying(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.setMood(self, "themepark_music_3");
        return SCRIPT_CONTINUE;
    }
    public int stopPlaying(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.setMood(self, "calm");
        return SCRIPT_CONTINUE;
    }
}
