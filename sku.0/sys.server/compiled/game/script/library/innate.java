package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.buff;

public class innate extends script.base_script
{
    public innate()
    {
    }
    public static final int ONE_HOUR = 3600;
    public static final int TWO_HOURS = ONE_HOUR * 2;
    public static final float DURATION_REGEN = 300f;
    public static final float DURATION_ROAR = 300f;
    public static final float DURATION_VIT = 300f;
    public static final int VALUE_VIT_BUFF = 50;
    public static final int VALUE_EQUALIZE_AMOUNT = 250;
    public static final float RAMP_REGEN = 30f;
    public static final float RAMP_VIT = 0f;
    public static final String VAR_INNATE_BASE = "innate";
    public static final String VAR_REGENERATION = VAR_INNATE_BASE + ".regeneration";
    public static final String VAR_ROAR = VAR_INNATE_BASE + ".roar";
    public static final String VAR_VITALIZE = VAR_INNATE_BASE + ".vitalize";
    public static final String VAR_EQUILIBRIUM = VAR_INNATE_BASE + ".equilibrium";
    public static final String STF = "innate";
    public static final string_id SID_REGEN = new string_id(STF, "regen");
    public static final string_id SID_ROAR = new string_id(STF, "roar");
    public static final string_id SID_VIT = new string_id(STF, "vit");
    public static final string_id SID_EQUIL = new string_id(STF, "equil");
    public static final String REGEN = "regeneration";
    public static final String ROAR = "roar";
    public static final String VIT = "vitalize";
    public static final String EQUIL = "equilibrium";
    public static final string_id PROSE_INNATE_WAIT = new string_id(STF, "innate_wait");
    public static final string_id PROSE_INNATE_NA = new string_id(STF, "innate_na");
    public static final string_id SID_REGEN_ACTIVE = new string_id(STF, "regen_active");
    public static final string_id SID_ROAR_ACTIVE = new string_id(STF, "roar_active");
    public static final string_id SID_VIT_ACTIVE = new string_id(STF, "vit_active");
    public static final string_id SID_EQUIL_ACTIVE = new string_id(STF, "equil_active");
    public static final String[] INNATE_CMD = 
    {
        EQUIL,
        REGEN,
        ROAR,
        VIT
    };
    public static final int AMR_ERROR = -1;
    public static final int AMR_CONTINUE = 0;
    public static final int AMR_OVERRIDE = 1;
    public static final int AMR_AMPLIFY = 2;
    public static String parseInnateCommand(String txt) throws InterruptedException
    {
        if ((txt == null) || (txt.equals("")))
        {
            return null;
        }
        for (int i = 0; i < INNATE_CMD.length; i++)
        {
            if (INNATE_CMD[i].startsWith(txt))
            {
                return INNATE_CMD[i];
            }
        }
        return null;
    }
    public static boolean regeneration() throws InterruptedException
    {
        obj_id self = getSelf();
        if (buff.hasBuff(self, "innate_regeneration"))
        {
            return false;
        }
        if (buff.applyBuff(self, "innate_regeneration"))
        {
            sendCombatSpamMessage(self, SID_REGEN_ACTIVE, COMBAT_RESULT_GENERIC);
            return true;
        }
        return false;
    }
    public static boolean vitalize() throws InterruptedException
    {
        obj_id self = getSelf();
        if (buff.hasBuff(self, "innate_vitalize"))
        {
            return false;
        }
        if (buff.applyBuff(self, "innate_vitalize"))
        {
            sendCombatSpamMessage(self, SID_VIT_ACTIVE, COMBAT_RESULT_GENERIC);
            return true;
        }
        return false;
    }
    public static boolean equilibrium() throws InterruptedException
    {
        obj_id self = getSelf();
        equalizeEffect(self);
        sendCombatSpamMessage(self, SID_EQUIL_ACTIVE, COMBAT_RESULT_GENERIC);
        return true;
    }
    public static void equalizeEffect(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (getAttrib(player, HEALTH) <= VALUE_EQUALIZE_AMOUNT)
        {
            return;
        }
        addAttribModifier(player, HEALTH, (-1 * VALUE_EQUALIZE_AMOUNT), 0, 0, MOD_POOL);
        addAttribModifier(player, ACTION, VALUE_EQUALIZE_AMOUNT, 0, 0, MOD_POOL);
    }
    public static int doAntiModCheck(obj_id target, String skill_mod) throws InterruptedException
    {
        if (!isIdValid(target) || (skill_mod == null) || (skill_mod.equals("")))
        {
            return AMR_ERROR;
        }
        int val = getSkillStatMod(target, skill_mod);
        if (val < 0)
        {
            return AMR_CONTINUE;
        }
        else if (val == 0)
        {
            return AMR_CONTINUE;
        }
        int roll = rand(1, 100);
        if (val > 0 && val <= 100)
        {
            if (roll < val && roll <= 95)
            {
                return AMR_OVERRIDE;
            }
            return AMR_CONTINUE;
        }
        else if (val > 100)
        {
            if (roll < (95 + ((val - 100) / 10)))
            {
                return AMR_OVERRIDE;
            }
            return AMR_CONTINUE;
        }
        return AMR_ERROR;
    }
}
