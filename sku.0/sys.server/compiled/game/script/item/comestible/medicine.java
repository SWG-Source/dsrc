package script.item.comestible;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.healing;
import script.library.consumable;
import script.library.doctor_bag;

public class medicine extends script.base_script
{
    public medicine()
    {
    }
    public static final String SCRIPT_MEDICINE = "item.comestible.medicine";
    public static final String MSG_TARGET_NOT_PLAYER = "@error_message:target_not_player";
    public static final string_id SID_TARGET_NOT_CREATURE = new string_id("error_message", "target_not_creature");
    public static final string_id SID_TARGETTING_ERROR = new string_id("error_message", "targetting_error");
    public static final string_id SID_USE_ITEM_SELF = new string_id("sui", "use_item_self");
    public static final string_id SID_USE_ITEM_TARGET = new string_id("sui", "use_item_target");
    public static final string_id SID_ON_MOUNT = new string_id("error_message", "survey_on_mount");
    public static final string_id SID_NO_DOCTOR_BAG = new string_id("doctor_bag", "no_doctor_bag");
    public static final string_id SID_PLACE_IN_DOCTOR_BAG = new string_id("doctor_bag", "place_in_doctor_bag");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, consumable.VAR_CONSUMABLE_BASE))
        {
            int charges = rand(1, 3);
            setObjVar(self, consumable.VAR_CONSUMABLE_MEDICINE, true);
            setCount(self, charges);
            int[] stomach = 
            {
                0,
                0,
                0
            };
            setObjVar(self, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
            String[] skill_mod = new String[1];
            if (healing.isApplyDotMedicine(self))
            {
                skill_mod[0] = "healing_efficiency";
            }
            setObjVar(self, consumable.VAR_SKILL_MOD_REQUIRED, skill_mod);
            int skill_req = rand(1, 25);
            int[] skill_value = 
            {
                skill_req
            };
            setObjVar(self, consumable.VAR_SKILL_MOD_MIN, skill_value);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "OnObjectMenuRequest");
        if (hasObjVar(self, consumable.VAR_CONSUMABLE_BASE))
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
            if (mid != null)
            {
                mid.setServerNotify(true);
            }
            menu_info_data mid2 = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid2 != null)
            {
                mid2.setServerNotify(true);
                if (!healing.isRevivePack(self))
                {
                    int sub_self = mi.addSubMenu(1, menu_info_types.ITEM_USE_SELF, SID_USE_ITEM_SELF);
                }
                int sub_target = mi.addSubMenu(1, menu_info_types.ITEM_USE_OTHER, SID_USE_ITEM_TARGET);
            }
            if (!hasScript(self, "systems.combat.combat_delayed_tracker"))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_PLACE_IN_DOCTOR_BAG);
            }
        }
        else 
        {
            detachScript(self, SCRIPT_MEDICINE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id playerCurrentMount = getMountId(player);
        if (isIdValid(playerCurrentMount))
        {
            sendSystemMessage(player, SID_ON_MOUNT);
            return SCRIPT_CONTINUE;
        }
        LOG("LOG_CHANNEL", "item ->" + item);
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id target = getLookAtTarget(player);
            if (healing.isRevivePack(self))
            {
                queueCommand(player, (-915040138), target, self.toString(), COMMAND_PRIORITY_DEFAULT);
                return SCRIPT_CONTINUE;
            }
            if (target == null)
            {
                String strParams = self.toString();
                if (healing.isHealDamageMedicine(self))
                {
                    queueCommand(player, (178192544), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isHealStateMedicine(self))
                {
                    queueCommand(player, (1245211605), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isBuffMedicine(self))
                {
                    queueCommand(player, (-287299121), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isCurePoisonMedicine(self))
                {
                    queueCommand(player, (391422949), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isCureFireMedicine(self))
                {
                    queueCommand(player, (-548804008), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isCureDiseaseMedicine(self))
                {
                    queueCommand(player, (-376119652), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isApplyPoisonMedicine(self))
                {
                    queueCommand(player, (-528116005), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isApplyDiseaseMedicine(self))
                {
                    queueCommand(player, (-1352774256), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else 
                {
                    queueCommand(player, (545771012), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
            }
            else 
            {
                String strParams = self.toString();
                if (healing.isHealDamageMedicine(self))
                {
                    queueCommand(player, (178192544), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isHealStateMedicine(self))
                {
                    queueCommand(player, (1245211605), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isBuffMedicine(self))
                {
                    queueCommand(player, (-287299121), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isCurePoisonMedicine(self))
                {
                    queueCommand(player, (391422949), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isCureFireMedicine(self))
                {
                    queueCommand(player, (-548804008), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isCureDiseaseMedicine(self))
                {
                    queueCommand(player, (-376119652), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isApplyPoisonMedicine(self))
                {
                    queueCommand(player, (-528116005), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isApplyDiseaseMedicine(self))
                {
                    queueCommand(player, (-1352774256), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else 
                {
                    queueCommand(player, (545771012), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
            }
        }
        else if (item == menu_info_types.ITEM_USE_SELF)
        {
            if (healing.isRevivePack(self))
            {
                return SCRIPT_CONTINUE;
            }
            String strParams = self.toString();
            if (healing.isHealDamageMedicine(self))
            {
                queueCommand(player, (178192544), player, strParams, COMMAND_PRIORITY_DEFAULT);
            }
            else if (healing.isHealStateMedicine(self))
            {
                queueCommand(player, (1245211605), player, strParams, COMMAND_PRIORITY_DEFAULT);
            }
            else if (healing.isBuffMedicine(self))
            {
                queueCommand(player, (-287299121), player, strParams, COMMAND_PRIORITY_DEFAULT);
            }
            else if (healing.isCurePoisonMedicine(self))
            {
                queueCommand(player, (391422949), player, strParams, COMMAND_PRIORITY_DEFAULT);
            }
            else if (healing.isCureFireMedicine(self))
            {
                queueCommand(player, (-548804008), player, strParams, COMMAND_PRIORITY_DEFAULT);
            }
            else if (healing.isCureDiseaseMedicine(self))
            {
                queueCommand(player, (-376119652), player, strParams, COMMAND_PRIORITY_DEFAULT);
            }
            else if (healing.isApplyPoisonMedicine(self))
            {
                queueCommand(player, (-528116005), player, strParams, COMMAND_PRIORITY_DEFAULT);
            }
            else if (healing.isApplyDiseaseMedicine(self))
            {
                queueCommand(player, (-1352774256), player, strParams, COMMAND_PRIORITY_DEFAULT);
            }
            else 
            {
                queueCommand(player, (545771012), player, strParams, COMMAND_PRIORITY_DEFAULT);
            }
        }
        else if (item == menu_info_types.ITEM_USE_OTHER)
        {
            obj_id target = getLookAtTarget(player);
            if (healing.isRevivePack(self))
            {
                queueCommand(player, (-915040138), target, self.toString(), COMMAND_PRIORITY_DEFAULT);
                return SCRIPT_CONTINUE;
            }
            if (isIdValid(target))
            {
                String strParams = self.toString();
                if (healing.isHealDamageMedicine(self))
                {
                    queueCommand(player, (178192544), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isHealStateMedicine(self))
                {
                    queueCommand(player, (1245211605), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isBuffMedicine(self))
                {
                    queueCommand(player, (-287299121), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isCurePoisonMedicine(self))
                {
                    queueCommand(player, (391422949), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isCureFireMedicine(self))
                {
                    queueCommand(player, (-548804008), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isCureDiseaseMedicine(self))
                {
                    queueCommand(player, (-376119652), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isApplyPoisonMedicine(self))
                {
                    queueCommand(player, (-528116005), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else if (healing.isApplyDiseaseMedicine(self))
                {
                    queueCommand(player, (-1352774256), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
                else 
                {
                    queueCommand(player, (545771012), target, strParams, COMMAND_PRIORITY_DEFAULT);
                }
            }
            else 
            {
                sendSystemMessage(player, SID_TARGETTING_ERROR);
            }
        }
        else if (item == menu_info_types.EXAMINE)
        {
            String template = getTemplateName(self);
            if(template == null){
                LOG("medicine","Unable to get template name from item (" + item + ") with object id (" + self + ") and name (" + getName(self) + ") while examining.");
                return SCRIPT_CONTINUE;
            }
            String id = template.substring(template.lastIndexOf("/") + 1, template.lastIndexOf("."));
            String title = "@food_name:" + id;
            String prompt = "Select USE from the radial menu options to apply the following attribute modifiers to your target.";
            attrib_mod[] am = getAttribModArrayObjVar(self, consumable.VAR_CONSUMABLE_MODS);
            Vector dsrc = new Vector();
            dsrc.setSize(0);
            dsrc = utils.addElement(null, "MEDICINAL PROPERTIES");
            if ((am == null) || (am.length == 0))
            {
                if (healing.isHealStateMedicine(self))
                {
                    String heal_state = healing.getHealingState(self);
                    heal_state = (heal_state.substring(0, 1)).toUpperCase() + (heal_state.substring(1)).toLowerCase();
                    dsrc = utils.addElement(dsrc, "HEAL STATE: " + heal_state);
                }
                else 
                {
                    dsrc = utils.addElement(dsrc, " None");
                }
            }
            else 
            {
                for (int i = 0; i < am.length; i++)
                {
                    int attrib = am[i].getAttribute();
                    int val = am[i].getValue();
                    float atk = am[i].getAttack();
                    float dcy = am[i].getDecay();
                    switch ((int)(atk))
                    {
                        case (int)healing.AM_HEAL_WOUND:
                        dsrc = utils.addElement(dsrc, "  * HEAL WOUND: " + consumable.STAT_NAME[attrib] + " " + val);
                        break;
                        case (int)healing.AM_HEAL_SHOCK:
                        dsrc = utils.addElement(dsrc, "  * HEAL SHOCK: " + val);
                        break;
                        case (int)healing.VAR_BUFF_MOD_ATTACK:
                        dsrc = utils.addElement(dsrc, "  * ENHANCE: " + consumable.STAT_NAME[attrib] + " " + val);
                        dsrc = utils.addElement(dsrc, "  * DURATION: " + (int)am[i].getDuration());
                        break;
                        default:
                        switch ((int)(dcy))
                        {
                            case (int)MOD_POOL:
                            dsrc = utils.addElement(dsrc, "  * HEAL DAMAGE: " + consumable.STAT_NAME[attrib] + " " + val);
                            break;
                            case (int)MOD_ANTIDOTE:
                            dsrc = utils.addElement(dsrc, "  * ANTIDOTE: " + consumable.STAT_NAME[attrib]);
                            break;
                        }
                    }
                }
            }
            if (hasObjVar(self, consumable.VAR_CONSUMABLE_CHARGES))
            {
                dsrc = utils.addElement(dsrc, "  * CHARGES: " + getIntObjVar(self, consumable.VAR_CONSUMABLE_CHARGES));
            }
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            obj_id bag = doctor_bag.getDoctorBag(player);
            if (bag != null)
            {
                doctor_bag.addMedicine(player, bag, self);
            }
            else 
            {
                sendSystemMessage(player, SID_NO_DOCTOR_BAG);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        attrib_mod[] am = getAttribModArrayObjVar(self, consumable.VAR_CONSUMABLE_MODS);
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if ((am == null) || (am.length == 0))
        {
            if (healing.isHealStateMedicine(self))
            {
                String heal_state = healing.getHealingState(self);
                names[idx] = "examine_heal_state";
                attribs[idx] = "@obj_attr_n:state_type_" + heal_state;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            else if (healing.isCureDotMedicine(self))
            {
                String dot_type = healing.getCureDot(self);
                int dot_power = healing.getDotPower(self);
                names[idx] = "examine_dot_cure";
                attribs[idx] = "@obj_attr_n:dot_type_" + dot_type;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "examine_dot_cure_power";
                attribs[idx] = Integer.toString(dot_power);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            else if (healing.isApplyDotMedicine(self))
            {
                String dot_type = healing.getApplyDot(self);
                int dot_attribute = healing.getDotAttribute(self);
                String attribute_name = consumable.STAT_NAME[dot_attribute];
                attribute_name = (attribute_name.substring(0, 1)).toUpperCase() + (attribute_name.substring(1)).toLowerCase();
                int dot_potency = healing.getDotPotency(self);
                int dot_power = healing.getDotPower(self);
                int dot_duration = healing.getDotDuration(self);
                names[idx] = "examine_dot_apply";
                attribs[idx] = "@obj_attr_n:dot_type_" + dot_type;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "examine_dot_attribute";
                attribs[idx] = attribute_name;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "examine_dot_potency";
                attribs[idx] = Integer.toString(dot_potency);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "examine_dot_apply_power";
                attribs[idx] = Integer.toString(dot_power);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "examine_dot_duration";
                attribs[idx] = Integer.toString(dot_duration);
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        else 
        {
            for (int i = 0; i < am.length; i++)
            {
                int attrib = am[i].getAttribute();
                int val = am[i].getValue();
                float atk = am[i].getAttack();
                float dcy = am[i].getDecay();
                switch ((int)(atk))
                {
                    case (int)healing.AM_HEAL_WOUND:
                    names[idx] = "examine_heal_wound_" + consumable.STAT_NAME[attrib];
                    attribs[idx] = Integer.toString(val);
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    break;
                    case (int)healing.AM_HEAL_SHOCK:
                    names[idx] = "examine_heal_shock";
                    attribs[idx] = Integer.toString(val);
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    break;
                    case (int)healing.VAR_BUFF_MOD_ATTACK:
                    names[idx] = "examine_enhance_" + consumable.STAT_NAME[attrib];
                    int newVal = Integer.MIN_VALUE;
                    attribs[idx] = Integer.toString(newVal) + "%";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    names[idx] = "examine_duration_" + consumable.STAT_NAME[attrib];
                    attribs[idx] = Integer.toString((int)am[i].getDuration());
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    break;
                    default:
                    switch ((int)(dcy))
                    {
                        case (int)MOD_POOL:
                        names[idx] = "examine_heal_damage_" + consumable.STAT_NAME[attrib];
                        attribs[idx] = Integer.toString(val);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                        break;
                        case (int)MOD_ANTIDOTE:
                        names[idx] = "examine_antidote_" + consumable.STAT_NAME[attrib];
                        attribs[idx] = Integer.toString(val);
                        idx++;
                        if (idx >= names.length)
                        {
                            return SCRIPT_CONTINUE;
                        }
                        break;
                    }
                }
            }
        }
        if (hasObjVar(self, healing.VAR_HEALING_RANGE))
        {
            names[idx] = "examine_heal_range";
            attribs[idx] = Integer.toString(getIntObjVar(self, healing.VAR_HEALING_RANGE));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, healing.VAR_HEALING_AREA))
        {
            names[idx] = "examine_heal_area";
            attribs[idx] = Integer.toString(getIntObjVar(self, healing.VAR_HEALING_AREA));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, consumable.VAR_SKILL_MOD_REQUIRED))
        {
            String[] skill_mods = getStringArrayObjVar(self, consumable.VAR_SKILL_MOD_REQUIRED);
            LOG("LOG_CHANNEL", "skill ->" + skill_mods[0]);
            names[idx] = skill_mods[0];
            int[] skill_values = getIntArrayObjVar(self, consumable.VAR_SKILL_MOD_MIN);
            attribs[idx] = Integer.toString(skill_values[0]);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, healing.VAR_HEALING_ABSORPTION))
        {
            names[idx] = "examine_heal_absorption";
            attribs[idx] = Integer.toString(getIntObjVar(self, healing.VAR_HEALING_ABSORPTION));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
