package script.theme_park.heroic.tusken;

import script.dictionary;
import script.library.ai_lib;
import script.library.factions;
import script.library.trial;
import script.library.utils;
import script.obj_id;

import java.util.Vector;

public class medic extends script.base_script
{
    public medic()
    {
    }
    public int findSquad(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "squad"))
        {
            obj_id curSquad = utils.getObjIdScriptVar(self, "squad");
            if (isIdValid(curSquad) && exists(curSquad) && !isDead(curSquad))
            {
                return SCRIPT_CONTINUE;
            }
        }
        obj_id[] allObj = getObjectsInRange(getLocation(self), 1000.0f);
        Vector allies = new Vector();
        allies.setSize(0);
        Vector gods = new Vector();
        gods.setSize(0);
        for (obj_id obj_id : allObj) {
            if (!isValidTarget(self, obj_id)) {
                continue;
            }
            if (isGod(obj_id)) {
                gods.add(obj_id);
            }
            if (utils.hasScriptVar(obj_id, "squad")) {
                continue;
            }
            if (obj_id == self) {
                continue;
            }
            allies.add(obj_id);
        }
        if (allies == null || allies.size() == 0)
        {
            messageTo(self, "findAllies", null, 60.0f, false);
            sendGodMessage(gods, "I could locate no valid allies to attach to, will try again in 60 seconds");
            return SCRIPT_CONTINUE;
        }
        obj_id mySquad = ((obj_id)allies.get(rand(0, allies.size() - 1)));
        setCreatureCoverVisibility(self, false);
        setLocation(self, getLocation(mySquad));
        dictionary dict = new dictionary();
        dict.put("squad", mySquad);
        messageTo(self, "followMySquad", dict, 0.0f, false);
        sendGodMessage(gods, "Medic(" + self + ") just attached myself to espa personel (" + mySquad + "). Come to " + getLocation(mySquad) + " to see the happy couple");
        return SCRIPT_CONTINUE;
    }
    public int followMySquad(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id mySquad = params.getObjId("squad");
        ai_lib.aiFollow(self, mySquad);
        setCreatureCoverVisibility(self, true);
        utils.setScriptVar(self, "squad", mySquad);
        messageTo(self, "performHeal", trial.getSessionDict(self), 12.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        messageTo(self, "performHeal", trial.getSessionDict(self), 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int performHeal(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = getObjectsInRange(getLocation(self), 25.0f);
        Vector toHeal = new Vector();
        toHeal.setSize(0);
        for (obj_id target : targets) {
            if (!isValidTarget(self, target)) {
                continue;
            }
            toHeal.add(target);
        }
        if (toHeal == null || toHeal.size() == 0)
        {
            messageTo(self, "performHeal", trial.getSessionDict(self), rand(15.0f, 30.0f), false);
            return SCRIPT_CONTINUE;
        }
        int valueHealed = 0;
        for (Object o : toHeal) {
            int curHealth = getHealth(((obj_id) o));
            int maxHealth = getMaxHealth(((obj_id) o));
            int difference = maxHealth - curHealth;
            if (difference > 0) {
                int capIt = difference > 5000 ? 5000 : difference;
                addToHealth(((obj_id) o), capIt);
                valueHealed += capIt;
                playClientEffectLoc(((obj_id) o), "clienteffect/bacta_bomb.cef", getLocation(((obj_id) o)), 0);
            }
        }
        if (valueHealed > 0)
        {
            playClientEffectLoc(self, "appearance/pt_medic_healing_energy_area.prt", getLocation(self), 0);
        }
        messageTo(self, "performHeal", trial.getSessionDict(self), rand(15.0f, 30.0f), false);
        return SCRIPT_CONTINUE;
    }
    public boolean isValidTarget(obj_id self, obj_id target) throws InterruptedException
    {
        if (!isMob(target) && !isPlayer(target))
        {
            return false;
        }
        if (isIdValid(getLocation(target).cell))
        {
            return false;
        }
        if (isDead(target))
        {
            return false;
        }
        if (factions.getFaction(target) == null || factions.getFaction(target) == "")
        {
            return false;
        }
        if (!(factions.getFaction(target)).equals("espa") && !(factions.getFaction(target)).equals("espa_guard"))
        {
            return false;
        }
        if (!getCreatureCoverVisibility(target))
        {
            return false;
        }
        return true;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public void sendGodMessage(Vector gods, String message) throws InterruptedException
    {
        if (gods != null || gods.size() > 0)
        {
            obj_id[] xfer = new obj_id[0];
            if (gods != null)
            {
                xfer = new obj_id[gods.size()];
                gods.toArray(xfer);
            }
            utils.sendSystemMessageTestingOnly(xfer, message);
        }
    }
}
