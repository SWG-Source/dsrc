package script.systems.crafting.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.powerup;

public class crafting_base_powerup extends script.systems.crafting.crafting_base
{
    public crafting_base_powerup()
    {
    }
    public static final String VERSION = "v1.00.00";
    public static final float MAX_BARREL_DAMAGE_BONUS = 0.35f;
    public static final float MAX_BARREL_SPEED_PENALTY = 0.25f;
    public static final float MAX_COUPLER_ELEMENTAL_BONUS = 0.35f;
    public static final float MAX_COUPLER_DAMAGE_PENALTY = 0.35f;
    public static final float MAX_GRIP_SPEED_BONUS = 0.35f;
    public static final float MAX_GRIP_ACCURACY_PENALTY = 75.0f;
    public static final float MAX_MUZZLE_WOUND_BONUS = 0.35f;
    public static final float MAX_MUZZLE_ACTION_PENALTY = 0.35f;
    public static final float MAX_SCOPE_ACCURACY_BONUS = 75.0f;
    public static final float MAX_SCOPE_SPEED_PENALTY = 0.3f;
    public static final float MAX_STOCK_ACTION_BONUS = 0.3f;
    public static final float MAX_STOCK_ACCURACY_PENALTY = 30.0f;
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        float effectVal = 0.0f;
        float efficiencyVal = 0.0f;
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("effect"))
                {
                    effectVal = itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("efficiency"))
                {
                    efficiencyVal = itemAttributes[i].currentValue;
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
        setObjVar(prototype, powerup.VAR_POWERUP_EFFECT, effectVal);
        setObjVar(prototype, powerup.VAR_POWERUP_EFFICIENCY, efficiencyVal);
        String template = getTemplateName(prototype);
        LOG("powerup", "Template = " + template);
        if (template.equals("object/tangible/powerup/weapon/ranged_barrel.iff") || template.equals("object/tangible/powerup/weapon/melee_inertial_fluctuator.iff") || template.equals("object/tangible/powerup/weapon/mine_explosive.iff") || template.equals("object/tangible/powerup/weapon/thrown_explosive.iff"))
        {
            calculateBonusAndPenalty(prototype, effectVal, MAX_BARREL_DAMAGE_BONUS, powerup.VAR_POWERUP_DAMAGE, efficiencyVal, MAX_BARREL_SPEED_PENALTY, powerup.VAR_POWERUP_SPEED);
        }
        else if (template.equals("object/tangible/powerup/weapon/ranged_power.iff") || template.equals("object/tangible/powerup/weapon/melee_element_dispursal_tuner.iff"))
        {
            calculateBonusAndPenalty(prototype, effectVal, MAX_COUPLER_ELEMENTAL_BONUS, powerup.VAR_POWERUP_ELEMENTAL_DAMAGE, efficiencyVal, MAX_COUPLER_DAMAGE_PENALTY, powerup.VAR_POWERUP_DAMAGE);
        }
        else if (template.equals("object/tangible/powerup/weapon/ranged_grip.iff") || template.equals("object/tangible/powerup/weapon/melee_tactical_grip_modifier.iff") || template.equals("object/tangible/powerup/weapon/thrown_wiring.iff"))
        {
            calculateBonusAndPenalty(prototype, effectVal, MAX_GRIP_SPEED_BONUS, powerup.VAR_POWERUP_SPEED, efficiencyVal, MAX_GRIP_ACCURACY_PENALTY, powerup.VAR_POWERUP_ACCURACY);
        }
        else if (template.equals("object/tangible/powerup/weapon/ranged_muzzle.iff") || template.equals("object/tangible/powerup/weapon/melee_surface_serration_kit.iff"))
        {
            calculateBonusAndPenalty(prototype, effectVal, MAX_MUZZLE_WOUND_BONUS, powerup.VAR_POWERUP_WOUND, efficiencyVal, MAX_MUZZLE_ACTION_PENALTY, powerup.VAR_POWERUP_ACTION);
        }
        else if (template.equals("object/tangible/powerup/weapon/ranged_scope.iff") || template.equals("object/tangible/powerup/weapon/melee_balancing_weights.iff"))
        {
            calculateBonusAndPenalty(prototype, effectVal, MAX_SCOPE_ACCURACY_BONUS, powerup.VAR_POWERUP_ACCURACY, efficiencyVal, MAX_SCOPE_SPEED_PENALTY, powerup.VAR_POWERUP_SPEED);
        }
        else if (template.equals("object/tangible/powerup/weapon/ranged_stock.iff") || template.equals("object/tangible/powerup/weapon/melee_hilt_reinforcement_kit.iff"))
        {
            calculateBonusAndPenalty(prototype, effectVal, MAX_STOCK_ACTION_BONUS, powerup.VAR_POWERUP_ACTION, efficiencyVal, MAX_STOCK_ACCURACY_PENALTY, powerup.VAR_POWERUP_ACCURACY);
        }
        setObjVar(prototype, "converted", 1);
    }
    public void calculateBonusAndPenalty(obj_id weapon, float effectVal, float maxBonus, String bonusObjVarName, float efficiencyVal, float maxPenalty, String penaltyObjVarName) throws InterruptedException
    {
        float bonus = 0;
        float penalty = 0;
        if (effectVal > 0)
        {
            bonus = (effectVal / 100.0f) * maxBonus;
        }
        penalty = (bonus / maxBonus) * maxPenalty;
        if (efficiencyVal > 0)
        {
            float efficiencyRatio = efficiencyVal / 100f;
            penalty -= (penalty * efficiencyRatio);
            setObjVar(weapon, powerup.VAR_POWERUP_USES_LEFT, Math.round(powerup.DEFAULT_USE_COUNT * efficiencyRatio));
        }
        else 
        {
            setObjVar(weapon, powerup.VAR_POWERUP_USES_LEFT, powerup.DEFAULT_USE_COUNT);
        }
        if (bonusObjVarName.equals(powerup.VAR_POWERUP_ACCURACY))
        {
            bonus = (float)Math.ceil(bonus);
        }
        if (bonusObjVarName.equals(powerup.VAR_POWERUP_SPEED) || bonusObjVarName.equals(powerup.VAR_POWERUP_ACTION))
        {
            bonus *= -1;
        }
        if (penaltyObjVarName.equals(powerup.VAR_POWERUP_ACCURACY))
        {
            penalty = (float)Math.floor(penalty);
        }
        if (!penaltyObjVarName.equals(powerup.VAR_POWERUP_SPEED) && !penaltyObjVarName.equals(powerup.VAR_POWERUP_ACTION))
        {
            penalty *= -1;
        }
        setObjVar(weapon, bonusObjVarName, bonus);
        if (!(getTemplateName(weapon)).equals("object/tangible/powerup/weapon/mine_explosive.iff") && !(getTemplateName(weapon)).startsWith("object/tangible/powerup/weapon/thrown"))
        {
            setObjVar(weapon, penaltyObjVarName, penalty);
        }
    }
    public int OnManufactureObject(obj_id self, obj_id player, obj_id newObject, draft_schematic schematic, boolean isPrototype, boolean isRealObject) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
}
