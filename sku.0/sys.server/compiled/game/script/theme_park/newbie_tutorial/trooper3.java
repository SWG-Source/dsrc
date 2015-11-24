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

public class trooper3 extends script.theme_park.newbie_tutorial.tutorial_base
{
    public trooper3()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setMood(self, "npc_imperial");
        setObjVar(self, "ai.rangedOnly", true);
        aiEquipPrimaryWeapon(self);
        paceInRoom(self, 1);
        messageTo(self, "equipWeapon", null, 5, false);
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
    public void paceInRoom(obj_id trooper, int destination) throws InterruptedException
    {
        location destLoc = new location(getLocation(trooper));
        if (destination != 1)
        {
            destLoc = getHomeLocation(trooper);
        }
        else 
        {
            obj_id bldg = getTopMostContainer(trooper);
            destLoc.x = 2.19f;
            destLoc.y = -4.20f;
            destLoc.z = -109.82f;
            destLoc.cell = getCellId(bldg, "r9");
        }
        pathTo(trooper, destLoc);
        setObjVar(trooper, "newbie.currentLoc", destination);
        messageTo(trooper, "handleNextPacingEvent", null, 45, false);
    }
    public int handleNextPacingEvent(obj_id self, dictionary params) throws InterruptedException
    {
        int currentLoc = getIntObjVar(self, "newbie.currentLoc");
        if (currentLoc == 1)
        {
            currentLoc = 0;
        }
        else 
        {
            currentLoc = 1;
        }
        paceInRoom(self, currentLoc);
        return SCRIPT_CONTINUE;
    }
}
