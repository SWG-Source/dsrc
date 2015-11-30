package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.restuss_event;
import script.library.utils;
import script.library.factions;
import script.library.ai_lib;
import script.library.pclib;
import script.library.instance;

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
            for (int i = 0; i < fullParse.length; i++)
            {
                if (fullParse[i].startsWith("restriction"))
                {
                    String[] restParse = split(fullParse[i], '_');
                    for (int q = 0; q < restParse.length; q++)
                    {
                        if (q == 0)
                        {
                            continue;
                        }
                        if (restParse[q].startsWith("player"))
                        {
                            player = true;
                        }
                        if (restParse[q].startsWith("npc"))
                        {
                            npc = true;
                        }
                        if (restParse[q].startsWith("faction"))
                        {
                            faction = restParse[q].substring(8, restParse[q].length() - 2);
                        }
                    }
                }
                if (fullParse[i].startsWith("range"))
                {
                    String[] rangeParse = split(fullParse[i], '_');
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
            for (int x = 0; x < targets.length; x++)
            {
                if (player)
                {
                    if (isPlayer(targets[x]))
                    {
                        if (!faction.equals(""))
                        {
                            if (faction.equals("imperial"))
                            {
                                if (factions.isImperial(targets[x]) && !factions.isOnLeave(targets[x]))
                                {
                                    utils.addElement(validatedTargets, targets[x]);
                                }
                            }
                            else if (faction.equals("rebel"))
                            {
                                if (factions.isRebel(targets[x]) && !factions.isOnLeave(targets[x]))
                                {
                                    utils.addElement(validatedTargets, targets[x]);
                                }
                            }
                        }
                        else 
                        {
                            utils.addElement(validatedTargets, targets[x]);
                        }
                    }
                }
                if (npc)
                {
                    if (!isPlayer(targets[x]))
                    {
                        if (!faction.equals(""))
                        {
                            String creature = getStringObjVar(targets[x], "ai.creatureBaseName");
                            String socialGroup = dataTableGetString("datatables/mob/creatures.iff", creature, "socialGroup");
                            if (faction.equals(socialGroup))
                            {
                                utils.addElement(validatedTargets, targets[x]);
                            }
                        }
                        else 
                        {
                            utils.addElement(validatedTargets, targets[x]);
                        }
                    }
                }
            }
            if (validatedTargets != null && validatedTargets.size() > 0)
            {
                for (int y = 0; y < validatedTargets.size(); y++)
                {
                    if (isPlayer(((obj_id)validatedTargets.get(y))))
                    {
                        setPosture(((obj_id)validatedTargets.get(y)), POSTURE_DEAD);
                        setObjVar(((obj_id)validatedTargets.get(y)), pclib.VAR_BEEN_COUPDEGRACED, 1);
                        messageTo(((obj_id)validatedTargets.get(y)), "handlePlayerDeath", null, 10, false);
                    }
                    else 
                    {
                        kill(((obj_id)validatedTargets.get(y)));
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
