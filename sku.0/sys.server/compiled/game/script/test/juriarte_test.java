package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.groundquests;
import script.library.pclib;
import script.library.skill;
import script.library.space_quest;
import script.library.sui;
import script.library.utils;
import script.library.weapons;
import script.library.player_structure;

public class juriarte_test extends script.base.remote_object_requester
{
    public juriarte_test()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            if (tok.hasMoreTokens())
            {
                String command = tok.nextToken();
                debugConsoleMsg(self, "command is: " + command);
                if (command.equalsIgnoreCase("ju_getPobBaseItemLimit_target"))
                {
                    final obj_id target = getLookAtTarget(self);
                    int baseItemLimit = getPobBaseItemLimit(target);
                    String s = "getPobBaseItemLimit_target returns " + baseItemLimit;
                    debugSpeakMsg(self, s);
                }
                if (command.equalsIgnoreCase("ju_getPobBaseItemLimit_myContainer"))
                {
                    final obj_id target = getTopMostContainer(self);
                    int baseItemLimit = getPobBaseItemLimit(target);
                    String s = "getPobBaseItemLimit_myContainer returns " + baseItemLimit;
                    debugSpeakMsg(self, s);
                }
                if (command.equalsIgnoreCase("ju_getHologramType_target"))
                {
                    final obj_id target = getLookAtTarget(self);
                    int hologramType = getHologramType(target);
                    String s = "getHologramType returns " + hologramType;
                    debugSpeakMsg(self, s);
                }
                if (command.equalsIgnoreCase("ju_setHologramType4_target"))
                {
                    final obj_id target = getLookAtTarget(self);
                    setHologramType(target, HOLOGRAM_TYPE1_QUALITY4);
                }
                if (command.equalsIgnoreCase("ju_setHologramType1_target"))
                {
                    final obj_id target = getLookAtTarget(self);
                    setHologramType(target, HOLOGRAM_TYPE1_QUALITY1);
                }
                if (command.equalsIgnoreCase("ju_setVisibleOnMapAndRadar_true"))
                {
                    final obj_id target = getLookAtTarget(self);
                    debugConsoleMsg(self, "... command is: " + command + " target is " + target);
                    setVisibleOnMapAndRadar(target, true);
                }
                if (command.equalsIgnoreCase("ju_setVisibleOnMapAndRadar_false"))
                {
                    final obj_id target = getLookAtTarget(self);
                    debugConsoleMsg(self, "... command is: " + command + " target is " + target);
                    setVisibleOnMapAndRadar(target, false);
                }
                if (command.equalsIgnoreCase("ju_getVisibleOnMapAndRadar"))
                {
                    final obj_id target = getLookAtTarget(self);
                    debugConsoleMsg(self, "... command is: " + command + " target is " + target + " result is " + getVisibleOnMapAndRadar(target));
                }
                if (command.equalsIgnoreCase("ju_incubator_development"))
                {
                    final obj_id target = getLookAtTarget(self);
                    debugConsoleMsg(self, "... command is: " + command + " target is " + target);
                    incubatorStart_development(self, target);
                }
                if (command.equalsIgnoreCase("ju_incubator_test0"))
                {
                    final obj_id target = getLookAtTarget(self);
                    debugConsoleMsg(self, "... command is: " + command + " target is " + target);
                    incubatorStart(1, self, target, 72, 2, 3, 4, 5, 6, 7, 4, 5, -1, "foobar_deadbeef_1");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.juriarte_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int startPerform(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int stopPerform(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
