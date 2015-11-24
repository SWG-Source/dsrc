package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.create;
import script.library.utils;
import script.library.chat;
import script.library.anims;
import script.library.groundquests;

public class avatar_wke_prisoner_02 extends script.base_script
{
    public avatar_wke_prisoner_02()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id THANKS = new string_id(STF, "wke_thanks_02");
    public static final string_id NO_QUEST = new string_id(STF, "wke_no_quest_01");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        setObjVar(structure, "avatar_platform.wke_prisoner_02", self);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("fleepoint"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGasDeath(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id player = params.getObjId("player");
        setObjVar(structure, "avatar_platform.wke_completed_02", 1);
        playClientEffectLoc(player, "clienteffect/avatar_wke_gas_01.cef", getLocation(self), 0f);
        messageTo(self, "handleDeath", null, 5f, false);
        doAnimationAction(self, anims.PLAYER_HEAVY_COUGH_VOMIT);
        messageTo(self, "handleCleanUp", null, 10f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleFreedom(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = getTopMostContainer(self);
        setObjVar(structure, "avatar_platform.wke_completed_02", 1);
        if (groundquests.hasCompletedQuest(player, "ep3_avatar_wke_prisoner_02") || groundquests.isQuestActive(player, "ep3_avatar_wke_prisoner_02"))
        {
            chat.chat(self, NO_QUEST);
        }
        else 
        {
            chat.chat(self, THANKS);
            groundquests.grantQuest(player, "ep3_avatar_wke_prisoner_02");
        }
        obj_id destination = getCellId(structure, "techhall09");
        location here = getLocation(self);
        String planet = here.area;
        location escape = new location(-168.9f, 0, -122.5f, planet, destination);
        ai_lib.aiPathTo(self, escape);
        addLocationTarget("fleepoint", escape, 1);
        return SCRIPT_CONTINUE;
    }
    public int handleCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleDeath(obj_id self, dictionary params) throws InterruptedException
    {
        setPosture(self, POSTURE_INCAPACITATED);
        return SCRIPT_CONTINUE;
    }
}
