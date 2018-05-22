package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class lairtest extends script.base_script
{
    public lairtest()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("makePOI"))
        {
            debugSpeakMsg(self, "Making lair at your location");
            location locHome = getLocation(self);
            String strLairDifficulty = create.getLairDifficulty(4, 40, 20);
            obj_id objLair = createObject("object/tangible/lair/npc_lair.iff", locHome);
            setObjVar(objLair, "spawning.intDifficultyLevel", 20);
            setObjVar(objLair, "spawning.lairDifficulty", strLairDifficulty);
            setObjVar(objLair, "spawning.lairType", "evil_tatooine_village");
            messageTo(objLair, "handle_Spawn_Setup_Complete", null, 0, false);
        }
        if (strText.equals("lairTest"))
        {
            debugSpeakMsg(self, "making womprat lair");
            location locHome = getLocation(self);
            String strLairDifficulty = create.getLairDifficulty(1, 15, 8);
            obj_id objLair = createObject("object/tangible/lair/npc_lair.iff", locHome);
            setObjVar(objLair, "spawning.intDifficultyLevel", 8);
            setObjVar(objLair, "spawning.lairDifficulty", strLairDifficulty);
            setObjVar(objLair, "spawning.lairType", "lesser_desert_womprat");
            setObjVar(objLair, "spawning.target", "object/tangible/lair/base/trash_dark.iff");
            messageTo(objLair, "handle_Spawn_Setup_Complete", null, 0, false);
        }
        if (strText.equals("theaterTest1"))
        {
            debugSpeakMsg(self, "Making lair at your location");
            location locHome = getLocation(self);
            String strLairDifficulty = create.getLairDifficulty(4, 40, 20);
            obj_id objLair = createObject("object/tangible/lair/npc_lair.iff", locHome);
            setObjVar(objLair, "spawning.intDifficultyLevel", 20);
            setObjVar(objLair, "spawning.lairDifficulty", strLairDifficulty);
            setObjVar(objLair, "spawning.lairType", "patrol_test");
            setObjVar(objLair, "spawning.target", "object/installation/battlefield/destructible/shared_bfield_target_power_generator.iff");
            messageTo(objLair, "handle_Spawn_Setup_Complete", null, 0, false);
        }
        if (strText.equals("theaterTest2"))
        {
            debugSpeakMsg(self, "Making lair at your location");
            location locHome = getLocation(self);
            String strLairDifficulty = create.getLairDifficulty(4, 40, 20);
            obj_id objLair = createObject("object/tangible/lair/npc_lair.iff", locHome);
            setObjVar(objLair, "spawning.intDifficultyLevel", 20);
            setObjVar(objLair, "spawning.lairDifficulty", strLairDifficulty);
            setObjVar(objLair, "spawning.lairType", "patrol_test");
            setObjVar(objLair, "spawning.target", "object/installation/battlefield/destructible/shared_bfield_target_power_transformer_2.iff");
            messageTo(objLair, "handle_Spawn_Setup_Complete", null, 0, false);
        }
        if (strText.equals("sampleHeight"))
        {
            int intI = 0;
            while (intI < 9)
            {
                location locHome = getLocation(self);
                location locTest = utils.getRandomLocationInRing(locHome, 1, 20);
                float fltHeight = getHeightAtLocation(locTest.x, locTest.z);
                debugSpeakMsg(self, "location tested was " + locTest);
                debugSpeakMsg(self, "height at location was " + fltHeight);
                intI = intI + 1;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
