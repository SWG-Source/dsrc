package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.attrib;
import script.library.groundquests;
import script.library.utils;

public class entertainer_rancor extends script.base_script
{
    public entertainer_rancor()
    {
    }
    public static final boolean debug = false;
    public static final String creatureTypesToLookFor = "gnarled_rancor,rancor,rancor_pygmy,rancor_youth";
    public static final String SYSTEM_MESSAGES = "theme_park/wod";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, "wod_entertainer_rancor_following");
        return SCRIPT_CONTINUE;
    }
    public int OnChangedPosture(obj_id self, int before, int after) throws InterruptedException
    {
        if (after == POSTURE_SKILL_ANIMATING)
        {
            if (!utils.hasScriptVar(self, "wod_entertainer_rancor_following"))
            {
                messageTo(self, "checkForRancors", null, 5.0f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkForRancors(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] creaturesNearMe = getCreaturesInRange(self, 10.0f);
        if (creaturesNearMe == null || creaturesNearMe.length == 0)
        {
            if (debug)
            {
                sendSystemMessageTestingOnly(self, "No creatures in range");
            }
            return SCRIPT_CONTINUE;
        }
        if (debug)
        {
            sendSystemMessageTestingOnly(self, "Number of creatures in range: " + creaturesNearMe.length);
        }
        if (utils.hasScriptVar(self, "wod_entertainer_rancor_following"))
        {
            obj_id targetRancor = utils.getObjIdScriptVar(self, "wod_entertainer_rancor_following");
            if (utils.hasScriptVar(targetRancor, "wod_angery"))
            {
                sendSystemMessage(self, new string_id(SYSTEM_MESSAGES, "renew_control"));
                utils.removeScriptVar(targetRancor, "wod_angery");
                return SCRIPT_CONTINUE;
            }
        }
        for (int i = 0; i < creaturesNearMe.length; i++)
        {
            if (hasObjVar(creaturesNearMe[i], "creature_type"))
            {
                String creatureType = getStringObjVar(creaturesNearMe[i], "creature_type");
                if (debug)
                {
                    sendSystemMessageTestingOnly(self, "Creatures " + i + " type: " + creatureType);
                }
                if (debug)
                {
                    sendSystemMessageTestingOnly(self, "Creatures " + i + " creatureTypesToLookFor contains creatureType: " + (creatureTypesToLookFor.indexOf(creatureType) != -1));
                }
                if (creatureTypesToLookFor.indexOf(creatureType) != -1)
                {
                    if (!ai_lib.isInCombat(creaturesNearMe[i]) && !ai_lib.isAiDead(creaturesNearMe[i]))
                    {
                        controlRancor(self, creaturesNearMe[i]);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void controlRancor(obj_id self, obj_id targetRancor) throws InterruptedException
    {
        utils.setScriptVar(targetRancor, "wod_rancor_followingPlayer", self);
        utils.setScriptVar(self, "wod_entertainer_rancor_following", targetRancor);
        setInvulnerable(targetRancor, true);
        pvpSetPermanentPersonalEnemyFlag(self, targetRancor);
        ai_lib.aiFollow(targetRancor, self);
        sendSystemMessage(self, new string_id(SYSTEM_MESSAGES, "got_control"));
        setAttributeAttained(targetRancor, attrib.BEAST);
        messageTo(self, "rancorUpdate", null, 60.0f, false);
        messageTo(self, "rancorControlUpdate", null, 150.0f, false);
    }
    public int rancorUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            sendSystemMessageTestingOnly(self, "wod entertainer script: rancorUpdate");
        }
        messageTo(self, "rancorUpdate", null, 60.0f, false);
        int messageToSend = rand(1, 4);
        sendSystemMessage(self, new string_id(SYSTEM_MESSAGES, "rancor_flavor_" + messageToSend));
        obj_id targetRancor = utils.getObjIdScriptVar(self, "wod_entertainer_rancor_following");
        if (!groundquests.isQuestActive(self, "wod_themepark_ns_rancor_tamer") && !groundquests.isQuestActive(self, "wod_themepark_sm_rancor_tamer"))
        {
            if (debug)
            {
                sendSystemMessageTestingOnly(self, "wod entertainer script: removing script, quest not active");
            }
            detachScript(self, "theme_park.wod.entertainer_rancor");
        }
        if (!isValidId(targetRancor) || !exists(targetRancor))
        {
            if (debug)
            {
                sendSystemMessageTestingOnly(self, "wod entertainer script: removing scriptvar, rancor does not exist");
            }
            utils.removeScriptVar(self, "wod_entertainer_rancor_following");
        }
        return SCRIPT_CONTINUE;
    }
    public int rancorControlUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            sendSystemMessageTestingOnly(self, "wod entertainer script: rancorControlUpdate");
        }
        messageTo(self, "rancorControlUpdate", null, 150.0f, false);
        obj_id targetRancor = utils.getObjIdScriptVar(self, "wod_entertainer_rancor_following");
        int rancorCheck = rand(0, 100);
        if (rancorCheck < 50)
        {
            sendSystemMessage(self, new string_id(SYSTEM_MESSAGES, "control_warning"));
            utils.setScriptVar(targetRancor, "wod_angery", 1);
            messageTo(self, "angryRancorAngery", null, 25.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int angryRancorAngery(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            sendSystemMessageTestingOnly(self, "wod entertainer script: angryRancorAngery");
        }
        obj_id targetRancor = utils.getObjIdScriptVar(self, "wod_entertainer_rancor_following");
        if (utils.hasScriptVar(targetRancor, "wod_angery"))
        {
            setInvulnerable(targetRancor, false);
            addHate(targetRancor, self, 5000.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int nsRancorFinished(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            sendSystemMessageTestingOnly(self, "wod entertainer script: nsRancorFinished");
        }
        sendSystemMessage(self, new string_id(SYSTEM_MESSAGES, "rancor_returned_ns"));
        groundquests.sendSignal(self, "returnedNsRancor");
        utils.removeScriptVar(self, "wod_entertainer_rancor_following");
        detachScript(self, "theme_park.wod.entertainer_rancor");
        return SCRIPT_CONTINUE;
    }
    public int smRancorFinished(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            sendSystemMessageTestingOnly(self, "wod entertainer script: nsRancorFinished");
        }
        sendSystemMessage(self, new string_id(SYSTEM_MESSAGES, "rancor_returned_sm"));
        groundquests.sendSignal(self, "returnedSmRancor");
        utils.removeScriptVar(self, "wod_entertainer_rancor_following");
        detachScript(self, "theme_park.wod.entertainer_rancor");
        return SCRIPT_CONTINUE;
    }
}
