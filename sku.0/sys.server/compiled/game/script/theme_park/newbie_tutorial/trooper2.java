package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.chat;
import script.library.ai_lib;

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
