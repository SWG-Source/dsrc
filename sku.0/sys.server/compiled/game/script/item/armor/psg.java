package script.item.armor;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.armor;
import script.library.utils;

public class psg extends script.base_script
{
    public psg()
    {
    }
    public static final float BASE_UPDATE_TIME = 5.0f;
    public static final float RAND_UPDATE_TIME = 1.0f;
    public static final String PARAM_TIME = "time";
    public static final String PARAM_MESSAGE_ID = "recharge_id";
    public static final String PSG_EFFECT_TIME = "psgEffectTime";
    public static final String SCRIPTVAR_RECHARGE_RATE = armor.OBJVAR_ARMOR_BASE + "." + armor.OBJVAR_PSG_RECHARGE_RATE;
    public static final String SCRIPTVAR_MESSAGE_ID = armor.OBJVAR_ARMOR_BASE + "." + PARAM_MESSAGE_ID;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializePsg", null, 0.1f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        float recharge = utils.getFloatScriptVar(self, SCRIPTVAR_RECHARGE_RATE);
        if (recharge <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        armor.setPsgEfficiency(self, 0);
        if (isPlayer(destContainer))
        {
            startRegeneration(self);
            boolean playStartUpEffect = true;
            if (utils.hasScriptVar(destContainer, PSG_EFFECT_TIME))
            {
                if (getGameTime() < utils.getIntScriptVar(destContainer, PSG_EFFECT_TIME))
                {
                    playStartUpEffect = false;
                }
            }
            if (playStartUpEffect)
            {
                int psgLevel = armor.getArmorLevel(self);
                if (psgLevel < 0 || psgLevel >= armor.PSG_STARTUP_EFFECTS.length)
                {
                    psgLevel = 0;
                }
                playClientEffectObj(destContainer, armor.PSG_STARTUP_EFFECTS[psgLevel], destContainer, "");
                utils.setScriptVar(destContainer, PSG_EFFECT_TIME, (getGameTime() + 5));
            }
        }
        else if (isPlayer(sourceContainer) && !isPlayer(destContainer))
        {
            utils.removeScriptVar(self, SCRIPTVAR_MESSAGE_ID);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInitializePsg(obj_id self, dictionary params) throws InterruptedException
    {
        armor.setPsgEfficiency(self, 0);
        float baseRecharge = getFloatObjVar(self, armor.OBJVAR_ARMOR_BASE + "." + armor.OBJVAR_PSG_RECHARGE_RATE);
        dictionary protections = armor.getArmorSpecialProtections(self);
        float recharge = 0;
        if (protections != null && protections.size() > 0)
        {
            for (int i = 0; i < armor.DATATABLE_SPECIAL_PROTECTIONS.length; ++i)
            {
                float value = protections.getFloat(armor.DATATABLE_SPECIAL_PROTECTIONS[i]);
                if (value > 0)
                {
                    recharge = baseRecharge / value;
                    if (recharge > 1.0f)
                    {
                        recharge = 1.0f;
                    }
                    debugServerConsoleMsg(self, "*** SETTING PSG RECHARGE RATE to " + recharge + " from base " + baseRecharge + ", protection " + value);
                    break;
                }
            }
            if (recharge <= 0)
            {
                CustomerServiceLog("armor", "WARNING: psg " + self + " has no recharge value");
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, SCRIPTVAR_RECHARGE_RATE, recharge);
        if (recharge > 0)
        {
            obj_id parent = getContainedBy(self);
            if (isIdValid(parent) && isPlayer(parent))
            {
                startRegeneration(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleEfficiencyUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, armor.SCRIPTVAR_PSG_EFFICIENCY) || !utils.hasScriptVar(self, SCRIPTVAR_MESSAGE_ID))
        {
            return SCRIPT_CONTINUE;
        }
        int messageId = params.getInt(PARAM_MESSAGE_ID);
        int storedId = utils.getIntScriptVar(self, SCRIPTVAR_MESSAGE_ID);
        if (messageId != storedId)
        {
            return SCRIPT_CONTINUE;
        }
        float efficiency = armor.getPsgEfficiency(self);
        long currentTime = System.currentTimeMillis();
        if (efficiency < 1.0f)
        {
            float recharge = utils.getFloatScriptVar(self, SCRIPTVAR_RECHARGE_RATE);
            if (recharge > 0)
            {
                long startTime = params.getLong(PARAM_TIME);
                float delta = recharge * (currentTime - startTime) / 5000.0f;
                efficiency += delta;
                armor.setPsgEfficiency(self, efficiency);
            }
        }
        startRegeneration(self);
        return SCRIPT_CONTINUE;
    }
    public void startRegeneration(obj_id self) throws InterruptedException
    {
        float time = BASE_UPDATE_TIME + rand(0.0f, RAND_UPDATE_TIME);
        dictionary params = new dictionary();
        params.put(PARAM_TIME, new Long(System.currentTimeMillis()));
        int messageId = rand(0, Integer.MAX_VALUE - 1);
        params.put(PARAM_MESSAGE_ID, messageId);
        utils.setScriptVar(self, SCRIPTVAR_MESSAGE_ID, messageId);
        messageTo(self, "handleEfficiencyUpdate", params, time, false);
    }
}
