package script.theme_park.restuss_event;

import script.dictionary;
import script.library.*;
import script.obj_id;

import java.util.Vector;

public class restuss_clientfx_controller extends script.base_script
{
    public restuss_clientfx_controller()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleFX", null, 0.25f, false);
        setName(self, getStringObjVar(self, restuss_event.EFFECT_NAME));
        messageTo(self, "handle_cleanup", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleFX(obj_id self, dictionary params) throws InterruptedException
    {
        String effectName = getStringObjVar(self, restuss_event.EFFECT_NAME);
        String visability = getStringObjVar(self, restuss_event.EFFECT_VISABILITY);
        float delta = utils.stringToFloat(getStringObjVar(self, restuss_event.EFFECT_DELTA));
        effectName = getEffectType(effectName) + effectName;
        obj_id[] players = instance.getPlayerIdList(self);
        if (players == null || players.length == 0)
        {
            String[] parse = split(visability, '-');
            if (parse == null || parse.length < 2)
            {
                doLogging("handleFx", "Parse length in forced visability was less than two");
                return SCRIPT_CONTINUE;
            }
            float range = utils.stringToFloat(parse[1]);
            players = getPlayerCreaturesInRange(getLocation(self), utils.stringToFloat(parse[1]));
            if (players == null || players.length == 0)
            {
                return SCRIPT_CONTINUE;
            }
        }
        playClientEffectLoc(players, effectName, getLocation(self), delta);
        doAdditionalEffects(self);
        return SCRIPT_CONTINUE;
    }
    public String getEffectType(String name) throws InterruptedException
    {
        if (name.endsWith(".prt"))
        {
            return "appearance/";
        }
        if (name.endsWith(".cef"))
        {
            return "clienteffect/";
        }
        return null;
    }
    public void doAdditionalEffects(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "kill_effect"))
        {
            String fullString = getStringObjVar(self, "kill_effect");
            String[] fullParse = split(fullString, '-');
            boolean player = false;
            boolean npc = false;
            String faction = "";
            float range = 0.0f;
            for (String s : fullParse) {
                if (s.startsWith("restriction")) {
                    String[] restParse = split(s, '_');
                    for (int q = 0; q < restParse.length; q++) {
                        if (q == 0) {
                            continue;
                        }
                        if (restParse[q].startsWith("player")) {
                            player = true;
                        }
                        if (restParse[q].startsWith("npc")) {
                            npc = true;
                        }
                        if (restParse[q].startsWith("faction")) {
                            faction = restParse[q].substring(8, restParse[q].length() - 2);
                        }
                    }
                }
                if (s.startsWith("range")) {
                    String[] rangeParse = split(s, '_');
                    range = utils.stringToFloat(rangeParse[1]);
                }
            }
            obj_id[] targets = getObjectsInRange(getLocation(self), range);
            if (targets == null || targets.length == 0)
            {
                return;
            }
            Vector validatedTargets = new Vector();
            validatedTargets.setSize(0);
            for (obj_id target : targets) {
                if (player) {
                    if (isPlayer(target)) {
                        if (!faction.equals("")) {
                            if (faction.equals("imperial")) {
                                if (factions.isImperial(target) && !factions.isOnLeave(target)) {
                                    utils.addElement(validatedTargets, target);
                                }
                            } else if (faction.equals("rebel")) {
                                if (factions.isRebel(target) && !factions.isOnLeave(target)) {
                                    utils.addElement(validatedTargets, target);
                                }
                            }
                        } else {
                            utils.addElement(validatedTargets, target);
                        }
                    }
                }
                if (npc) {
                    if (!isPlayer(target)) {
                        if (!faction.equals("")) {
                            String creature = getStringObjVar(target, "ai.creatureBaseName");
                            String socialGroup = dataTableGetString("datatables/mob/creatures.iff", creature, "socialGroup");
                            if (faction.equals(socialGroup)) {
                                utils.addElement(validatedTargets, target);
                            }
                        } else {
                            utils.addElement(validatedTargets, target);
                        }
                    }
                }
            }
            if (validatedTargets != null && validatedTargets.size() > 0)
            {
                for (Object validatedTarget : validatedTargets) {
                    if (isPlayer(((obj_id) validatedTarget))) {
                        setPosture(((obj_id) validatedTarget), POSTURE_DEAD);
                        setObjVar(((obj_id) validatedTarget), pclib.VAR_BEEN_COUPDEGRACED, 1);
                        messageTo(((obj_id) validatedTarget), "handlePlayerDeath", null, 10, false);
                    } else {
                        kill(((obj_id) validatedTarget));
                    }
                }
            }
        }
    }
    public int handle_cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        trial.unregisterObjectWithSequencer(trial.getParent(self), self);
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/restuss_clientfx_controller/" + section, message);
        }
    }
}
