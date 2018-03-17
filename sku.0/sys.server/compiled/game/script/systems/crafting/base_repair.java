package script.systems.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.prose;

public class base_repair extends script.base_script
{
    public base_repair()
    {
    }
    public static final String SCRIPT_ME = "systems.crafting.base_repair";
    public static final String REPAIR_LIST_TITLE = "@sui:repairable_objects";
    public static final String REPAIR_LIST_PROMPT = "@sui:select_repair";
    public static final String HANDLER_REPAIR = "handleRepair";
    public static final String OBJVAR_REPAIR_IDS = "crafting.repair_ids";
    public static final String OBJVAR_REPAIR_TOOL_QUALITY = "repair.quality";
    public static final string_id SID_SYS_NOTHING_REPAIR = new string_id("error_message", "sys_nothing_repair");
    public static final string_id SID_FAILED_REPAIR = new string_id("error_message", "sys_repair_failed");
    public static final string_id SID_IMPERFECT_REPAIR = new string_id("error_message", "sys_repair_imperfect");
    public static final string_id SID_SLIGHT_REPAIR = new string_id("error_message", "sys_repair_slight");
    public static final string_id SID_PERFECT_REPAIR = new string_id("error_message", "sys_repair_perfect");
    public static final string_id PROSE_ITEM_UNREPAIRABLE = new string_id("error_message", "sys_repair_unrepairable");
    public static final float MAX_ROLL = 10000;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCount(self, 5);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, OBJVAR_REPAIR_IDS);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "repair.quality"))
        {
            names[idx] = "repairquality";
            float attrib = getFloatObjVar(self, "repair.quality");
            attribs[idx] = " " + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu;
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        String[][] objects = new String[1][];
        objects[0] = null;
        getRepairableObjectsForTool(player, self, objects);
        if (objects[0] != null)
        {
            if (objects[0].length == 0)
            {
                sendSystemMessage(player, SID_SYS_NOTHING_REPAIR);
                return SCRIPT_CONTINUE;
            }
            String[] objectIds = new String[objects[0].length];
            for (int i = 0; i < objectIds.length; ++i)
            {
                int markerPos = objects[0][i].indexOf('*');
                if (markerPos >= 0)
                {
                    objectIds[i] = objects[0][i].substring(0, markerPos);
                    objects[0][i] = "" + objects[0][i].substring(markerPos + 1);
                }
                else 
                {
                    String err = "ERROR " + (getClass()).getName() + ".OnObjectMenuSelect: handling ITEM_USE " + "objects for player " + player + " returned invalid object string <" + objects[0][i] + ">";
                    debugServerConsoleMsg(null, err);
                    LOG("crafting", err);
                    objectIds[i] = "";
                    objects[0][i] = "";
                }
            }
            setObjVar(self, OBJVAR_REPAIR_IDS, objectIds);
            int pid = sui.listbox(self, player, REPAIR_LIST_PROMPT, sui.OK_CANCEL, REPAIR_LIST_TITLE, objects[0], HANDLER_REPAIR, false);
            sui.setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@sui:repair");
            sui.showSUIPage(pid);
        }
        else 
        {
            sendSystemMessage(player, SID_SYS_NOTHING_REPAIR);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRepair(obj_id self, dictionary params) throws InterruptedException
    {
        String[] objectIds = getStringArrayObjVar(self, OBJVAR_REPAIR_IDS);
        removeObjVar(self, OBJVAR_REPAIR_IDS);
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (objectIds == null)
        {
            String err = "ERROR " + (getClass()).getName() + ".handleRepair: no " + OBJVAR_REPAIR_IDS + " objvar on repair tool " + self;
            debugServerConsoleMsg(null, err);
            LOG("crafting", err);
        }
        else if (idx >= objectIds.length)
        {
            String err = "ERROR " + (getClass()).getName() + ".handleRepair: bad index " + idx + " chosen for object list of length " + objectIds.length;
            debugServerConsoleMsg(null, err);
            LOG("crafting", err);
        }
        else if (objectIds[idx] == null || objectIds[idx].length() == 0)
        {
            String err = "ERROR " + (getClass()).getName() + ".handleRepair: null/empty object id at index " + idx;
            debugServerConsoleMsg(null, err);
            LOG("crafting", err);
        }
        else 
        {
            obj_id object = obj_id.getObjId(Long.parseLong(objectIds[idx]));
            if (object == obj_id.NULL_ID)
            {
                String err = "ERROR " + (getClass()).getName() + ".handleRepair: invalid object id " + objectIds[idx] + " at index " + idx;
                debugServerConsoleMsg(null, err);
                LOG("crafting", err);
            }
            else 
            {
                debugServerConsoleMsg(null, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                debugServerConsoleMsg(null, "@@@");
                int maxHp = getMaxHitpoints(object);
                debugServerConsoleMsg(null, "@@@ Max Hitpoints: " + maxHp);
                if (maxHp <= 10)
                {
                    prose_package ppBeyondRepair = prose.getPackage(PROSE_ITEM_UNREPAIRABLE, object);
                    sendSystemMessageProse(player, ppBeyondRepair);
                    debugServerConsoleMsg(null, "@@@");
                    debugServerConsoleMsg(null, "@@@ Item is beyond repair");
                    debugServerConsoleMsg(null, "@@@");
                    debugServerConsoleMsg(null, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    return SCRIPT_CONTINUE;
                }
                float roll = rand(1, (int)MAX_ROLL);
                float skillMod = getEnhancedSkillStatisticModifier(player, "force_repair_bonus");
                roll = roll + (skillMod * 10);
                float complexity = getRepairComplexity(player, object);
                debugServerConsoleMsg(null, "@@@");
                debugServerConsoleMsg(null, "@@@ Base Roll: " + roll);
                debugServerConsoleMsg(null, "@@@");
                float perfect_repair = MAX_ROLL - ((1 - (complexity / 100)) * 2000);
                float slight_repair = complexity * 100;
                float imperfect_repair = (complexity / 100) * 2000;
                if (hasObjVar(object, "isLightsaber"))
                {
                    imperfect_repair = 0;
                }
                debugServerConsoleMsg(null, "@@@ Perfect Repair: " + perfect_repair);
                debugServerConsoleMsg(null, "@@@ Slight Repair: " + slight_repair);
                debugServerConsoleMsg(null, "@@@ Imperfect Repair: " + imperfect_repair);
                debugServerConsoleMsg(null, "@@@");
                float maxHpMod = 100;
                int new_max_hp = 0;
                if (roll >= perfect_repair)
                {
                    maxHpMod = 0.95f;
                    sendSystemMessage(player, SID_PERFECT_REPAIR);
                    debugServerConsoleMsg(null, "@@@ Result: -- PERFECT REPAIR");
                    new_max_hp = (int)(maxHp * maxHpMod);
                    new_max_hp = validateMaxHp(new_max_hp, object);
                    setMaxHitpoints(object, new_max_hp);
                    setInvulnerableHitpoints(object, getMaxHitpoints(object));
                }
                else if (roll >= slight_repair)
                {
                    maxHpMod = 0.80f;
                    sendSystemMessage(player, SID_SLIGHT_REPAIR);
                    debugServerConsoleMsg(null, "@@@ Result: -- SLIGHT REPAIR");
                    new_max_hp = (int)(maxHp * maxHpMod);
                    new_max_hp = validateMaxHp(new_max_hp, object);
                    setMaxHitpoints(object, new_max_hp);
                    setInvulnerableHitpoints(object, getMaxHitpoints(object));
                }
                else if (roll >= imperfect_repair)
                {
                    maxHpMod = 0.66f;
                    sendSystemMessage(player, SID_IMPERFECT_REPAIR);
                    debugServerConsoleMsg(null, "@@@ Result: -- IMPERFECT REPAIR");
                    new_max_hp = (int)(maxHp * maxHpMod);
                    new_max_hp = validateMaxHp(new_max_hp, object);
                    setMaxHitpoints(object, new_max_hp);
                    setInvulnerableHitpoints(object, getMaxHitpoints(object));
                }
                else if (roll < imperfect_repair)
                {
                    disableRepairTarget(object);
                    sendSystemMessage(player, SID_FAILED_REPAIR);
                    debugServerConsoleMsg(null, "@@@ Result: -- FAILED REPAIR");
                }
            }
        }
        debugServerConsoleMsg(null, "@@@");
        debugServerConsoleMsg(null, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        int count = getCount(self);
        count--;
        setCount(self, count);
        if (count <= 0)
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public float getRepairComplexity(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            return 20f;
        }
        float base_complexity = getComplexity(target);
        if (base_complexity <= 1)
        {
            base_complexity = 10;
        }
        if (base_complexity > 100)
        {
            base_complexity = 20;
        }
        int max_hp = getMaxHitpoints(target);
        int cur_hp = getHitpoints(target);
        debugServerConsoleMsg(null, "@@@");
        debugServerConsoleMsg(null, "@@@ Base Complexity: " + base_complexity);
        debugServerConsoleMsg(null, "@@@");
        debugServerConsoleMsg(null, "@@@ Current Hitpoints: " + cur_hp + " / " + max_hp);
        base_complexity *= 1f + ((max_hp - cur_hp) / max_hp);
        debugServerConsoleMsg(null, "@@@ New Complexity: " + base_complexity + " (Complexity) x (1f+((" + max_hp + "-" + cur_hp + ")/" + max_hp + "))");
        obj_id self = getSelf();
        if (hasObjVar(self, "repair.quality"))
        {
            float repair_quality = getFloatObjVar(self, "repair.quality");
            if (repair_quality > 0f)
            {
                base_complexity /= repair_quality / 100f;
            }
            debugServerConsoleMsg(null, "@@@");
            debugServerConsoleMsg(null, "@@@ Tool Quality: " + repair_quality);
            debugServerConsoleMsg(null, "@@@ New Complexity: " + base_complexity + " (Complexity) / (" + repair_quality + "/100f)");
        }
        int assembly_mod = getSkillStatMod(player, "general_assembly");
        float complexity = base_complexity * (1f - (0.15f * assembly_mod / 100f));
        debugServerConsoleMsg(null, "@@@");
        debugServerConsoleMsg(null, "@@@ Assembly Mod: " + assembly_mod);
        debugServerConsoleMsg(null, "@@@ New Complexity: " + complexity + " (Complexity) x (1f-((0.15f x " + assembly_mod + ")/100f))");
        int elite_mod = 0;
        int got = getGameObjectType(target);
        if (isGameObjectTypeOf(got, GOT_clothing))
        {
            elite_mod = getSkillStatMod(player, "clothing_assembly");
        }
        else if (isGameObjectTypeOf(got, GOT_armor))
        {
            elite_mod = getSkillStatMod(player, "armor_assembly");
        }
        else if (isGameObjectTypeOf(got, GOT_weapon))
        {
            elite_mod = getSkillStatMod(player, "weapon_assembly");
        }
        else if (isGameObjectTypeOf(got, GOT_installation))
        {
            elite_mod = getSkillStatMod(player, "structure_assembly");
        }
        else if (isGameObjectTypeOf(got, GOT_creature_droid))
        {
            elite_mod = getSkillStatMod(player, "clothing_assembly");
        }
        if (elite_mod > 0)
        {
            complexity -= base_complexity * (0.35f * elite_mod / 100f);
        }
        debugServerConsoleMsg(null, "@@@");
        debugServerConsoleMsg(null, "@@@ Elite Mod: " + elite_mod);
        debugServerConsoleMsg(null, "@@@ Final Complexity: " + complexity + " (Complexity) - (Base Complexity) x ((0.35f x " + elite_mod + ")/100f)");
        if (complexity < 2f)
        {
            return 2f;
        }
        return complexity;
    }
    public void disableRepairTarget(obj_id target) throws InterruptedException
    {
        if (isIdValid(target))
        {
            int got = getGameObjectType(target);
            if (isGameObjectTypeOf(got, GOT_clothing) || isGameObjectTypeOf(got, GOT_armor) || isGameObjectTypeOf(got, GOT_weapon))
            {
                setMaxHitpoints(target, 1);
                setInvulnerableHitpoints(target, 1);
                setCondition(target, CONDITION_DISABLED);
            }
            else if (isGameObjectTypeOf(got, GOT_creature_droid))
            {
            }
            else if (isGameObjectTypeOf(got, GOT_installation))
            {
            }
            else 
            {
            }
        }
    }
    public int validateMaxHp(int maxHp, obj_id object) throws InterruptedException
    {
        if (maxHp <= 1)
        {
            maxHp = 1;
            int got = getGameObjectType(object);
            if (isGameObjectTypeOf(got, GOT_clothing) || isGameObjectTypeOf(got, GOT_armor) || isGameObjectTypeOf(got, GOT_weapon))
            {
                setCondition(object, CONDITION_DISABLED);
            }
        }
        return maxHp;
    }
}
