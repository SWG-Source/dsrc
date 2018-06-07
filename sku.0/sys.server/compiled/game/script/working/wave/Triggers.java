package script.working.wave;

import script.menu_info;
import script.obj_id;

public final class Triggers
{
    public interface Base
    {
        int OnAttach(obj_id self);
        int OnDetach(obj_id self);
        int OnInitialize(obj_id self);
    }
    public interface Listen
    {
        int OnSpeaking(obj_id self, String text);
        int OnHearSpeech(obj_id self, obj_id speaker, String text);
        //int OnHearSpeech(obj_id speaker, string_id text);
    }
    public interface Menu
    {
        int OnObjectMenuSelect(obj_id self, obj_id player, int item);
        int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item);
    }
}