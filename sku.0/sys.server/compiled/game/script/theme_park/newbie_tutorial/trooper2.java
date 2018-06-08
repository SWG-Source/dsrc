package script.theme_park.newbie_tutorial;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.menu_info;
import script.obj_id;
import script.string_id;

public class trooper2 extends script.theme_park.newbie_tutorial.tutorial_base
{
    public trooper2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "ai.rangedOnly", true);
        messageTo(self, "equipWeapon", null, 5, false);
        ai_lib.setMood(self, "npc_imperial");
        return SCRIPT_CONTINUE;
    }
    public int equipWeapon(obj_id self, dictionary params) throws InterruptedException
    {
        aiEquipPrimaryWeapon(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        stop(self);
        faceToBehavior(self, player);
        messageTo(self, "handleWaveOn", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleWaveOn(obj_id self, dictionary params) throws InterruptedException
    {
        chat.chat(self, new string_id(NEWBIE_CONVO, "trooper_move_along"));
        doAnimationAction(self, "wave_on_directing");
        return SCRIPT_CONTINUE;
    }
}
