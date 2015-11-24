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
import script.library.create;

public class trooper4 extends script.theme_park.newbie_tutorial.tutorial_base
{
    public trooper4()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "ai.rangedOnly", true);
        messageTo(self, "setMeUp", null, 5, false);
        ai_lib.setMood(self, "npc_imperial");
        return SCRIPT_CONTINUE;
    }
    public int setMeUp(obj_id self, dictionary params) throws InterruptedException
    {
        aiEquipPrimaryWeapon(self);
        makeRefugees(self);
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
        messageTo(self, "handleResumeGuarding", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int handleResumeGuarding(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] refugees = getObjIdArrayObjVar(self, "newbie.refugees");
        faceToBehavior(self, refugees[0]);
        return SCRIPT_CONTINUE;
    }
    public void makeRefugees(obj_id trooper) throws InterruptedException
    {
        location spawnLoc = new location(getLocation(trooper));
        spawnLoc.x = REFUGEE_LOCATION_X;
        spawnLoc.y = REFUGEE_LOCATION_Y;
        spawnLoc.z = REFUGEE_LOCATION_Z;
        obj_id[] refugees = new obj_id[3];
        refugees[0] = create.object("commoner", spawnLoc);
        spawnLoc.x += 2;
        spawnLoc.z += 2;
        refugees[1] = create.object("commoner", spawnLoc);
        spawnLoc.x -= 4;
        spawnLoc.z -= 4;
        refugees[2] = create.object("commoner", spawnLoc);
        for (int i = 0; i < 3; i++)
        {
            setInvulnerable(refugees[i], true);
            removeObjVar(refugees[i], "ai.diction");
            faceToBehavior(refugees[i], trooper);
            ai_lib.setDefaultCalmMood(refugees[i], "npc_sitting_ground");
            ai_lib.aiSetPosture(refugees[i], POSTURE_SITTING);
        }
        faceToBehavior(trooper, refugees[0]);
        setObjVar(trooper, "newbie.refugees", refugees);
        messageTo(trooper, "makeWithTheTalking", null, rand(20, 30), false);
    }
    public int makeWithTheTalking(obj_id self, dictionary params) throws InterruptedException
    {
        int attempts = getIntObjVar(self, "newbie.numTries");
        if (attempts > 2)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] refugees = getObjIdArrayObjVar(self, "newbie.refugees");
        if (refugees == null || refugees.length == 0)
        {
            attempts++;
            setObjVar(self, "newbie.numTries", attempts);
            messageTo(self, "makeWithTheTalking", null, rand(20, 30), false);
        }
        chat.chat(refugees[rand(0, 2)], new string_id(NEWBIE_CONVO, "refugee" + rand(1, 5)));
        messageTo(self, "makeWithTheTalking", null, rand(20, 30), false);
        return SCRIPT_CONTINUE;
    }
}
