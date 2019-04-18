package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.dictionary;
import script.library.ai_lib;
import script.library.trial;
import script.library.utils;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class doom_bringer extends script.base_script
{
    public doom_bringer()
    {
    }
    public static final string_id SID_DOOM = new string_id("mob/creature_names", "som_working_doom_bringer");
    public static final string_id SID_HAND = new string_id("mob/creature_names", "som_working_hand_of_doom");
    public static final int HOD_CYCLE_DELAY = 15;
    public static final boolean LOGGING = true;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id top = trial.getTop(self);
        messageTo(top, "doomBringerKilled", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_WORKING_DOOM_BRINGER);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        resetSelf(self);
        return SCRIPT_CONTINUE;
    }
    public void resetSelf(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        ai_lib.clearCombatData();
    }
    public int beginDestruction(obj_id self, dictionary params) throws InterruptedException
    {
        trial.bumpSession(self, "db_control");
        obj_id[] hands = trial.getObjectsInDungeonWithScript(self, "theme_park.dungeon.mustafar_trials.working_droid_factory.doom_hand");
        obj_id[] target = trial.getObjectsInDungeonWithScript(self, "theme_park.dungeon.mustafar_trials.working_droid_factory.doom_target");
        if (hands == null || hands.length == 0 || target == null || target.length == 0)
        {
            doLogging("beginDestruction", "No hands or targets could be found");
            return SCRIPT_CONTINUE;
        }
        Vector activeHand = new Vector();
        activeHand.setSize(0);
        for (obj_id hand : hands) {
            trial.setActiveHand(hand, false);
            utils.addElement(activeHand, hand);
            utils.setScriptVar(hand, trial.WORKING_HOD_TARGET, target[0]);
        }
        for (int q = 0; q < 3; q++)
        {
            utils.removeElement(activeHand, ((obj_id)activeHand.get(rand(0, activeHand.size() - 1))));
        }
        setInvulnerable(target[0], false);
        for (int k = 0; k < activeHand.size(); k++)
        {
            trial.setActiveHand(((obj_id)activeHand.get(k)), true);
            if (k == 0)
            {
                setName(((obj_id)activeHand.get(k)), SID_DOOM);
                trial.setPrimaryHand(((obj_id)activeHand.get(k)), true);
            }
            messageTo(((obj_id)activeHand.get(k)), "activate", trial.getSessionDict(self), 0, false);
        }
        dictionary dict = trial.getSessionDict(self);
        dict.put("cycle", 0);
        messageTo(self, "manageHands", dict, HOD_CYCLE_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int manageHands(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary dict = trial.getSessionDict(self);
        messageTo(self, "manageHands", dict, HOD_CYCLE_DELAY, false);
        obj_id[] handObjects = trial.getObjectsInDungeonWithScript(self, "theme_park.dungeon.mustafar_trials.working_droid_factory.doom_hand");
        Vector hands = new Vector();
        hands.setSize(0);
        for (obj_id handObject : handObjects) {
            if (isIdValid(handObject) && !isDead(handObject)) {
                utils.addElement(hands, handObject);
            }
        }
        if (hands == null || hands.size() == 0)
        {
            messageTo(self, "activateSelf", null, 5, false);
            trial.bumpSession(self);
            return SCRIPT_CONTINUE;
        }
        int cycle = 0;
        if (utils.hasScriptVar(self, "cycle"))
        {
            utils.getIntScriptVar(self, "cycle");
        }
        boolean shuffle = false;
        if (cycle >= 3)
        {
            shuffle = true;
        }
        if (shuffle && hands.size() > 3)
        {
            Vector oldActive = new Vector();
            oldActive.setSize(0);
            Vector oldInactive = new Vector();
            oldInactive.setSize(0);
            Vector newActive = new Vector();
            newActive.setSize(0);
            Vector newInactive = new Vector();
            newInactive.setSize(0);
            for (Object hand : hands) {
                if (!isDead(((obj_id) hand)) && trial.isActiveHand(((obj_id) hand))) {
                    utils.addElement(oldActive, ((obj_id) hand));
                } else if (!isDead(((obj_id) hand))) {
                    utils.addElement(oldInactive, ((obj_id) hand));
                }
            }
            for (int j = 0; j < oldInactive.size(); j++)
            {
                messageTo(((obj_id)oldInactive.get(j)), "activate", null, 0, false);
                trial.setActiveHand(((obj_id)oldInactive.get(j)), true);
                utils.addElement(newActive, ((obj_id)oldInactive.get(j)));
                if (j == oldActive.size() - 1 || j < oldActive.size() - 1)
                {
                    messageTo(((obj_id)oldActive.get(j)), "deactivate", null, 0, false);
                    trial.setActiveHand(((obj_id)oldActive.get(j)), false);
                    trial.setPrimaryHand(((obj_id)oldActive.get(j)), false);
                    setName(((obj_id)oldActive.get(j)), SID_HAND);
                    utils.addElement(newInactive, ((obj_id)oldActive.get(j)));
                }
            }
            Vector nonLeaders = new Vector();
            nonLeaders.setSize(0);
            obj_id oldLeader = obj_id.NULL_ID;
            for (Object o : newActive) {
                if (!trial.isPrimaryHand(((obj_id) o))) {
                    utils.addElement(nonLeaders, ((obj_id) o));
                } else {
                    oldLeader = ((obj_id) o);
                }
            }
            if (nonLeaders == null || nonLeaders.size() == 0 && oldLeader == obj_id.NULL_ID)
            {
                messageTo(self, "activateSelf", null, 5, false);
                trial.bumpSession(self);
                return SCRIPT_CONTINUE;
            }
            else if (nonLeaders != null && nonLeaders.size() > 0)
            {
                if (oldLeader != obj_id.NULL_ID)
                {
                    setName(oldLeader, SID_HAND);
                    trial.setPrimaryHand(oldLeader, false);
                }
                int newLeader = rand(0, nonLeaders.size() - 1);
                for (int r = 0; r < nonLeaders.size(); r++)
                {
                    if (r == newLeader)
                    {
                        setName(((obj_id)nonLeaders.get(r)), SID_DOOM);
                        trial.setPrimaryHand(((obj_id)nonLeaders.get(r)), true);
                    }
                    else 
                    {
                        setName(((obj_id)nonLeaders.get(r)), SID_HAND);
                        trial.setPrimaryHand(((obj_id)nonLeaders.get(r)), false);
                    }
                }
            }
            cycle = 0;
        }
        else 
        {
            Vector activeHands = new Vector();
            activeHands.setSize(0);
            Vector inactiveHands = new Vector();
            inactiveHands.setSize(0);
            for (Object hand : hands) {
                if (isIdValid(((obj_id) hand)) && !isDead(((obj_id) hand)) && trial.isActiveHand(((obj_id) hand))) {
                    utils.addElement(activeHands, ((obj_id) hand));
                } else if (isIdValid(((obj_id) hand)) && !isDead(((obj_id) hand))) {
                    utils.addElement(inactiveHands, ((obj_id) hand));
                }
            }
            if (activeHands.size() < 3)
            {
                if (inactiveHands != null && inactiveHands.size() > 0)
                {
                    int r = 0;
                    for (int q = activeHands.size(); q < 3 && r < inactiveHands.size(); q++)
                    {
                        if (isIdValid(((obj_id)inactiveHands.get(r))))
                        {
                            messageTo(((obj_id)inactiveHands.get(r)), "activate", trial.getSessionDict(self), 0, false);
                            trial.setActiveHand(((obj_id)inactiveHands.get(r)), true);
                            utils.addElement(activeHands, ((obj_id)inactiveHands.get(r)));
                            r++;
                        }
                    }
                }
            }
            if (activeHands.size() <= 0)
            {
                messageTo(self, "activateSelf", null, 5, false);
                trial.bumpSession(self);
                return SCRIPT_CONTINUE;
            }
            Vector nonLeaders = new Vector();
            nonLeaders.setSize(0);
            obj_id oldLeader = obj_id.NULL_ID;
            for (Object activeHand : activeHands) {
                if (!trial.isPrimaryHand(((obj_id) activeHand))) {
                    utils.addElement(nonLeaders, ((obj_id) activeHand));
                } else {
                    oldLeader = ((obj_id) activeHand);
                }
            }
            if (nonLeaders == null || nonLeaders.size() == 0 && oldLeader == obj_id.NULL_ID)
            {
                messageTo(self, "activateSelf", null, 5, false);
                trial.bumpSession(self);
                return SCRIPT_CONTINUE;
            }
            else if (nonLeaders != null && nonLeaders.size() > 0)
            {
                if (oldLeader != obj_id.NULL_ID)
                {
                    setName(oldLeader, SID_HAND);
                    trial.setPrimaryHand(oldLeader, false);
                }
                int newLeader = rand(0, nonLeaders.size() - 1);
                for (int r = 0; r < nonLeaders.size(); r++)
                {
                    if (r == newLeader)
                    {
                        setName(((obj_id)nonLeaders.get(r)), SID_DOOM);
                        trial.setPrimaryHand(((obj_id)nonLeaders.get(r)), true);
                    }
                    else 
                    {
                        setName(((obj_id)nonLeaders.get(r)), SID_HAND);
                        trial.setPrimaryHand(((obj_id)nonLeaders.get(r)), false);
                    }
                }
            }
            cycle += 1;
        }
        utils.setScriptVar(self, "cycle", cycle);
        return SCRIPT_CONTINUE;
    }
    public int activateSelf(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("logging/doom_bringer/" + section, message);
        }
    }
}
