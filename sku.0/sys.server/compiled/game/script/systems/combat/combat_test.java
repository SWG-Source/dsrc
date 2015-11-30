package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class combat_test extends script.base_script
{
    public combat_test()
    {
    }
    public static final String VERSION = "v0.00.00";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "systems.combat.combat_test");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "combat_test: entered trigger OnAttach");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "combat_test: entered trigger OnAttach");
        debugServerConsoleMsg(self, "combat_test: Bye!");
        return SCRIPT_CONTINUE;
    }
    public int OnRecapacitated(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "I'm no longer incapacitated!");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String message) throws InterruptedException
    {
        if (message.equals("com recover"))
        {
            if (speaker != self)
            {
                return SCRIPT_CONTINUE;
            }
            if (isIncapacitated(self))
            {
                int attribute;
                attribute = getAttrib(self, HEALTH);
                if (attribute < 0)
                {
                    setAttrib(self, HEALTH, -1);
                }
                attribute = getAttrib(self, ACTION);
                if (attribute < 0)
                {
                    setAttrib(self, ACTION, -1);
                }
                attribute = getAttrib(self, MIND);
                if (attribute < 0)
                {
                    setAttrib(self, MIND, -1);
                }
                return SCRIPT_CONTINUE;
            }
            else 
            {
                debugSpeakMsg(self, "I'm not incapacitated!");
            }
        }
        else if (message.equals("com report"))
        {
            String report = reportAttribs(self);
            debugSpeakMsg(self, "My stats are currently: " + report + "");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String killerName;
        killerName = getName(killer);
        debugServerConsoleMsg(self, "setting negative attribute(s) to -5");
        int attribute;
        attribute = getAttrib(self, HEALTH);
        if (attribute < 0)
        {
            setAttrib(self, HEALTH, -5);
        }
        attribute = getAttrib(self, ACTION);
        if (attribute < 0)
        {
            setAttrib(self, ACTION, -5);
        }
        attribute = getAttrib(self, MIND);
        if (attribute < 0)
        {
            setAttrib(self, MIND, -5);
        }
        String report = reportAttribs(self);
        debugServerConsoleMsg(self, "My stats are currently: " + report + "");
        return SCRIPT_CONTINUE;
    }
    public int OnWeaponCombatAction(obj_id self, obj_id attacker, obj_id defender) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int where) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public String returnDamType(int attribute) throws InterruptedException
    {
        String damType;
        switch (attribute)
        {
            case HEALTH:
            damType = "Health";
            break;
            case CONSTITUTION:
            damType = "Constitution";
            break;
            case ACTION:
            damType = "Action";
            break;
            case STAMINA:
            damType = "Stamina";
            break;
            case MIND:
            damType = "Mind";
            break;
            case WILLPOWER:
            damType = "Willpower";
            break;
            default:
            damType = "None";
            break;
        }
        return (damType);
    }
    public String victimReportDamage(obj_id self, obj_id attacker, attrib_mod[] attackEffects) throws InterruptedException
    {
        int effectsLength = 0;
        if (attackEffects != null)
        {
            effectsLength = attackEffects.length;
        }
        else 
        {
            debugServerConsoleMsg(self, "attackEffects was NULL! Exiting...");
            return null;
        }
        if (effectsLength == 0)
        {
            debugServerConsoleMsg(self, "No damage done. That's odd...");
            return null;
        }
        debugServerConsoleMsg(self, "effectsLength is " + effectsLength);
        int damPoints = 0;
        int damIntType = 0;
        String damType = "unknown";
        String damageReport = "Damage Report: ";
        for (int i = 0; i < attackEffects.length; ++i)
        {
            if (attackEffects[i] == null)
            {
                break;
            }
            int attribute = attackEffects[i].getAttribute();
            damPoints = attackEffects[i].getValue();
            float duration = attackEffects[i].getDuration();
            float attack = attackEffects[i].getAttack();
            float decay = attackEffects[i].getDecay();
            if (damPoints != 0)
            {
                int curAttributeLevel;
                int maxAttributeLevel;
                int newAttributeLevel;
                damType = returnDamType(attribute);
                curAttributeLevel = getAttrib(self, attribute);
                maxAttributeLevel = getMaxAttrib(self, attribute);
                newAttributeLevel = (curAttributeLevel - damPoints);
                if (!damType.equals("none"))
                {
                    String damageElement = "" + damPoints + " of " + damType + " (" + newAttributeLevel + "/" + maxAttributeLevel + "), ";
                    damageReport = join(damageReport, damageElement);
                }
            }
        }
        return damageReport;
    }
    public String getHitLocationName(obj_id self, int hitLocation) throws InterruptedException
    {
        String hitLocationName;
        switch (hitLocation)
        {
            case 0:
            hitLocationName = "body";
            break;
            case 1:
            hitLocationName = "head";
            break;
            case 2:
            hitLocationName = "right arm";
            break;
            case 3:
            hitLocationName = "left arm";
            break;
            case 4:
            hitLocationName = "right leg";
            break;
            case 5:
            hitLocationName = "left leg";
            break;
            default:
            hitLocationName = "none";
            debugServerConsoleMsg(self, "combat_test couldn't determine the hit location!");
            break;
        }
        return (hitLocationName);
    }
    public int doStateCheck(obj_id self, int hitLocation, obj_id weapon, attrib_mod[] attackEffects) throws InterruptedException
    {
        String hitLocationName;
        int hit;
        switch (hitLocation)
        {
            case 0:
            hitLocationName = "body";
            break;
            case 1:
            hitLocationName = "head";
            break;
            case 2:
            hitLocationName = "right arm";
            break;
            case 3:
            hitLocationName = "left arm";
            break;
            case 4:
            hitLocationName = "right leg";
            break;
            case 5:
            hitLocationName = "left leg";
            break;
            default:
            hitLocationName = "none";
            debugServerConsoleMsg(self, "combat_test couldn't determine the hit location!");
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public String reportAttribs(obj_id target) throws InterruptedException
    {
        String reportsum = "";
        String element = "";
        String attribname = "";
        int curAttributeLevel = getAttrib(target, HEALTH);
        attribname = returnDamType(HEALTH);
        element = ("" + attribname + " = " + curAttributeLevel + ",");
        reportsum = join(reportsum, element);
        curAttributeLevel = getAttrib(target, ACTION);
        attribname = returnDamType(ACTION);
        element = ("" + attribname + " = " + curAttributeLevel + ",");
        reportsum = join(reportsum, element);
        curAttributeLevel = getAttrib(target, MIND);
        attribname = returnDamType(MIND);
        element = ("" + attribname + " = " + curAttributeLevel + ",");
        reportsum = join(reportsum, element);
        return reportsum;
    }
}
