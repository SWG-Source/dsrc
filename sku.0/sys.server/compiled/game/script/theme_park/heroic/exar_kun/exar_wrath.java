package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.chat;
import script.library.combat;
import script.library.trial;

public class exar_wrath extends script.base_script
{
    public exar_wrath()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, 545020);
        setObjVar(self, "isImmobile", true);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        setMovementPercent(self, 0.0f);
        playClientEffectObj(getHateTarget(self), "appearance/pt_force_meditate.prt", self, "");
        messageTo(self, "executeBlast", trial.getSessionDict(self, "blast"), 4.0f, false);
        messageTo(self, "applyWard", trial.getSessionDict(self, "ward"), 0.0f, false);
        buff.applyBuff(self, "mind_trick_immune");
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self, "blast");
        trial.bumpSession(self, "ward");
        return SCRIPT_CONTINUE;
    }
    public int executeBlast(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "blast"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self))
        {
            clog("oh nos, I dead");
            return SCRIPT_CONTINUE;
        }
        obj_id hateTarget = getHateTarget(self);
        clog("Target = " + getName(hateTarget));
        obj_id weapon = getCurrentWeapon(hateTarget);
        clog("weapon = " + getEncodedName(weapon));
        int elementalType = getWeaponElementalType(weapon);
        clog("Elemental Type = " + elementalType);
        switch (elementalType)
        {
            case DAMAGE_ELEMENTAL_HEAT:
            clog("HEAT");
            queueCommand(self, (1097819132), hateTarget, "", COMMAND_PRIORITY_DEFAULT);
            break;
            case DAMAGE_ELEMENTAL_COLD:
            clog("COLD");
            queueCommand(self, (-2065560327), hateTarget, "", COMMAND_PRIORITY_DEFAULT);
            break;
            case DAMAGE_ELEMENTAL_ACID:
            clog("ACID");
            queueCommand(self, (-1503522191), hateTarget, "", COMMAND_PRIORITY_DEFAULT);
            break;
            case DAMAGE_ELEMENTAL_ELECTRICAL:
            clog("ELECTRICAL");
            queueCommand(self, (-692498043), hateTarget, "", COMMAND_PRIORITY_DEFAULT);
            break;
            default:
            clog("RANDOM");
            String[] randomBlast = 
            {
                "heat",
                "cold",
                "acid",
                "electrical"
            };
            String blastChoice = randomBlast[rand(0, 3)];
            String blastCommand = "kun_wrath_" + blastChoice;
            queueCommand(self, getStringCrc(blastCommand.toLowerCase()), hateTarget, "", COMMAND_PRIORITY_DEFAULT);
            break;
        }
        messageTo(self, "executeBlast", trial.getSessionDict(self, "blast"), 4.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int applyWard(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "ward"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objects = trial.getAllObjectsInDungeon(trial.getTop(self));
        Vector wards = new Vector();
        wards.setSize(0);
        wards.add("kun_wrath_ward_acid");
        wards.add("kun_wrath_ward_heat");
        wards.add("kun_wrath_ward_cold");
        wards.add("kun_wrath_ward_electrical");
        if (buff.hasBuff(self, "kun_wrath_ward_acid"))
        {
            wards.remove("kun_wrath_ward_acid");
        }
        if (buff.hasBuff(self, "kun_wrath_ward_heat"))
        {
            wards.remove("kun_wrath_ward_heat");
        }
        if (buff.hasBuff(self, "kun_wrath_ward_cold"))
        {
            wards.remove("kun_wrath_ward_cold");
        }
        if (buff.hasBuff(self, "kun_wrath_ward_electrical"))
        {
            wards.remove("kun_wrath_ward_electrical");
        }
        String newWard = ((String)wards.get(rand(0, wards.size() - 1)));
        for (int i = 0; i < objects.length; i++)
        {
            if (isPlayer(objects[i]) || isMob(objects[i]))
            {
                buff.applyBuff(objects[i], newWard);
            }
        }
        messageTo(self, "applyWard", trial.getSessionDict(self, "ward"), 8.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void clog(String message) throws InterruptedException
    {
    }
}
