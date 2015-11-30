package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.ai_lib;
import script.library.utils;
import script.library.anims;
import script.library.create;
import script.ai.ai_combat;

public class pirate_01 extends script.base_script
{
    public pirate_01()
    {
    }
    public static final string_id NPC_NAME = new string_id("quest/hero_of_tatooine/npc_names", "detr");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai_combat");
        setInvulnerable(self, true);
        setName(self, "");
        setName(self, localize(NPC_NAME));
        messageTo(self, "action01", null, 1f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("wifeLoc") && !hasObjVar(self, "wifeReached"))
        {
            obj_id building = getTopMostContainer(self);
            obj_id wife = getObjIdObjVar(building, "wife");
            setInvulnerable(wife, false);
            attribute[] attrib = getAttribs(wife);
            attrib[0] = new attribute(attrib[0].getType(), -50);
            setAttribs(wife, attrib);
            clearCondition(wife, CONDITION_CONVERSABLE);
            detachScript(wife, "conversation.quest_hero_of_tatooine_wife");
            doAnimationAction(self, "cheer");
            setObjVar(self, "wifeReached", 1);
            messageTo(self, "action02", null, 3f, false);
            chat.chat(self, "Let's go guys!");
        }
        if (name.equals("door") && !hasObjVar(self, "doorReached"))
        {
            setObjVar(self, "doorReached", 1);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int action01(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id building = getTopMostContainer(self);
        obj_id wife = getCellId(building, "livingroom");
        location here = getLocation(self);
        String planet = here.area;
        location wifeTarget = new location(-6.53f, 0.03f, -3.21f, planet, wife);
        ai_lib.aiPathTo(self, wifeTarget);
        addLocationTarget("wifeLoc", wifeTarget, 1);
        return SCRIPT_CONTINUE;
    }
    public int action02(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id building = getTopMostContainer(self);
        obj_id door = getCellId(building, "hall1");
        location here = getLocation(self);
        String planet = here.area;
        location doorTarget = new location(-6.20f, 0.57f, 10.92f, planet, door);
        ai_lib.aiPathTo(self, doorTarget);
        addLocationTarget("door", doorTarget, 1);
        return SCRIPT_CONTINUE;
    }
}
