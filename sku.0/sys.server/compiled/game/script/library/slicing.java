package script.library;

import script.dictionary;
import script.obj_id;
import script.prose_package;
import script.string_id;

import java.util.Vector;

public class slicing extends script.base_script
{
    public slicing()
    {
    }
    public static final string_id SID_SLICING_UNDERWAY = new string_id("slicing/slicing", "slicing_underway");
    public static final string_id SID_ALREADY_SLICING = new string_id("slicing/slicing", "already_slicing");
    public static final string_id SID_ALREADY_SLICED = new string_id("slicing/slicing", "already_sliced");
    public static final string_id SID_ALREADY_BEING_SLICED = new string_id("slicing/slicing", "already_being_sliced");
    public static final string_id SID_JEDI_NO_SLICE = new string_id("slicing/slicing", "jedi");
    public static final string_id SID_STATIC_ITEM = new string_id("slicing/slicing", "static");
    public static final string_id SID_CUSTOMIZATION = new string_id("slicing/slicing", "customization");
    public static final string_id SID_SLICE_NO_SKILL = new string_id("slicing/slicing", "no_skill");
    public static final string_id SID_NOT_ENOUGH_SKILL = new string_id("slicing/slicing", "not_enough_skill");
    public static final string_id SID_ALREADY_APPLIED = new string_id("slicing/slicing", "already_applied_sys");
    public static final string_id SID_CANT_APPLY = new string_id("slicing/slicing", "cant_apply_sys");
    public static final string_id SID_COST_TOO_HIGH = new string_id("slicing/slicing", "cost_too_high");
    public static final string_id SID_CANT_FIX = new string_id("slicing/slicing", "cant_fix");
    public static final string_id SID_FIX_SUCCESSFUL = new string_id("slicing/slicing", "fix_successful");
    public static final string_id SID_REQUIRES_COMPONENT = new string_id("slicing/slicing", "requires_component");
    public static final string_id SID_NO_COMPONENT_REQUIRED = new string_id("slicing/slicing", "no_comp_required");
    public static final string_id SID_MISSING_COMP = new string_id("slicing/slicing", "missing_comp");
    public static final String SLICE_WEAPON_TABLE = "datatables/smuggler/slice_weapon.iff";
    public static final String SLICE_ARMOR_TABLE = "datatables/smuggler/slice_armor.iff";
    public static void startSlicing(obj_id player, obj_id item, String callback, String type) throws InterruptedException
    {
        if (jedi.isLightsaber(item))
        {
            sendSystemMessage(player, SID_JEDI_NO_SLICE);
            return;
        }
        if (static_item.isStaticItem(item))
        {
            sendSystemMessage(player, SID_STATIC_ITEM);
            return;
        }
        if (utils.hasScriptVar(player, "slicing.slice_item"))
        {
            sendSystemMessage(player, SID_ALREADY_SLICING);
            return;
        }
        if (hasObjVar(item, "slice_start"))
        {
            int slice_start = getIntObjVar(item, "slice_start");
            int delta = getGameTime() - slice_start;
            if (delta < 172800)
            {
                obj_id first_slicer = getObjIdObjVar(item, "sliced_by");
                if ((first_slicer != player) && (delta < 900))
                {
                    CustomerServiceLog("exploit", "Two players tried to double slice an item within 15 minutes, they may be exploiting.");
                    CustomerServiceLog("exploit", "--First Slicer: %TU", first_slicer);
                    CustomerServiceLog("exploit", "--Second Slicer: %TU", player);
                }
                sendSystemMessage(player, SID_ALREADY_BEING_SLICED);
                return;
            }
        }
        utils.setScriptVar(player, "slicing.slice_item", item);
        utils.setScriptVar(player, "slicing.type", type);
        utils.setScriptVar(player, "slicing.callback", callback);
        setObjVar(item, "slice_start", getGameTime());
        setObjVar(item, "sliced_by", player);
        attachScript(player, "player.player_slicing");
        slicingQuery(player, item, type);
    }
    public static void clearSlicing(obj_id player) throws InterruptedException
    {
        obj_id slice_item = utils.getObjIdScriptVar(player, "slicing.slice_item");
        if (isIdValid(slice_item))
        {
            removeObjVar(slice_item, "slice_start");
            removeObjVar(slice_item, "sliced_by");
        }
        utils.removeScriptVar(player, "slicing.slice");
        utils.removeScriptVar(player, "slicing.type");
        utils.removeScriptVar(player, "slicing.cat");
        utils.removeScriptVar(player, "slicing.callback");
        utils.removeScriptVar(player, "slicing.slice_item");
        utils.removeScriptVar(player, "slicing.last_query");
        utils.removeScriptVar(player, "slicing.apply_status");
        detachScript(player, "player.player_slicing");
    }
    public static void slicingQuery(obj_id player, obj_id item, String query) throws InterruptedException
    {
        int skillMod = getSkillStatisticModifier(player, "slice_" + query);
        if (skillMod == 0)
        {
            if (hasSkill(player, "class_smuggler_phase1_novice"))
            {
                successSlicing(player, item, 0);
                return;
            }
            sendSystemMessage(player, SID_SLICE_NO_SKILL);
            clearSlicing(player);
            return;
        }
        String table = null;
        if (query.equals("weapon"))
        {
            table = SLICE_WEAPON_TABLE;
        }
        else if (query.equals("armor"))
        {
            table = SLICE_ARMOR_TABLE;
        }
        int rows = dataTableGetNumRows(table);
        Vector categories = new Vector();
        categories.setSize(0);
        String is_this_cat;
        for (int i = 0; i < rows; i++)
        {
            is_this_cat = dataTableGetString(table, i, "SLICE_CAT");
            if (is_this_cat == null || is_this_cat.equals(""))
            {
                continue;
            }
            int req = dataTableGetInt(table, i, "SKILL_REQ");
            if (skillMod >= req)
            {
                categories = utils.addElement(categories, "@slicing/slicing_" + query + ":" + is_this_cat);
            }
        }
        if (categories.size() == 0)
        {
            sendSystemMessage(player, SID_SLICE_NO_SKILL);
            clearSlicing(player);
            return;
        }
        sui.listbox(player, player, "@slicing/slicing:" + query, sui.OK_CANCEL, "@slicing/slicing:cat_title", categories, "handleSlicingCategory", true);
    }
    public static void handleSlicingCategory(obj_id player, int idx) throws InterruptedException
    {
        String query = utils.getStringScriptVar(player, "slicing.type");
        obj_id item = utils.getObjIdScriptVar(player, "slicing.slice_item");
        if (!isIdValid(item))
        {
            clearSlicing(player);
            return;
        }
        int skillMod = getSkillStatisticModifier(player, "slice_" + query);
        String table = null;
        if (query.equals("weapon"))
        {
            table = SLICE_WEAPON_TABLE;
        }
        else if (query.equals("armor"))
        {
            table = SLICE_ARMOR_TABLE;
        }
        int rows = dataTableGetNumRows(table);
        Vector slices = new Vector();
        slices.setSize(0);
        idx++;
        int cats = 0;
        int type = 0;
        utils.setScriptVar(player, "slicing.cat", idx);
        String is_this_cat;
        String slice;
        String stat;
        String color_prefix;
        String status_suffix;
        for (int i = 0; i < rows; i++)
        {
            is_this_cat = dataTableGetString(table, i, "SLICE_CAT");
            if (!is_this_cat.equals(""))
            {
                cats++;
                continue;
            }
            if (cats == idx)
            {
                slice = dataTableGetString(table, i, "SLICE");
                stat = dataTableGetString(table, i, "STAT");
                int req = dataTableGetInt(table, i, "SKILL_REQ");
                int rank = dataTableGetInt(table, i, "RANK");
                int cur_rank = getIntObjVar(item, "slice.rank." + stat);
                if (skillMod >= req)
                {
                    color_prefix = "";
                    status_suffix = "";
                    if (rank > cur_rank + 1)
                    {
                        color_prefix = "\\#888888";
                        status_suffix = "-- Can't be applied, yet.";
                    }
                    else if (rank <= cur_rank)
                    {
                        color_prefix = "\\#FFFFFF";
                        status_suffix = "-- Already applied.";
                    }
                    slices = utils.addElement(slices, "@slicing/slicing_" + query + ":" + slice + " " + color_prefix + status_suffix);
                    utils.setScriptVar(player, "slicing." + type, i);
                    type++;
                }
            }
        }
        sui.listbox(player, player, "@slicing/slicing:select_slice", sui.OK_CANCEL, "@slicing/slicing:slice_title", slices, "handleSlicingSelect", true);
    }
    public static void handleSlicingSelect(obj_id player, int idx) throws InterruptedException
    {
        int row = utils.getIntScriptVar(player, "slicing." + idx);
        obj_id item = utils.getObjIdScriptVar(player, "slicing.slice_item");
        String query = utils.getStringScriptVar(player, "slicing.type");
        String table = null;
        if (query.equals("weapon"))
        {
            table = SLICE_WEAPON_TABLE;
        }
        else if (query.equals("armor"))
        {
            table = SLICE_ARMOR_TABLE;
        }
        int skillMod = getSkillStatisticModifier(player, "slice_" + query);
        dictionary effect = dataTableGetRow(table, row);
        int req = effect.getInt("SKILL_REQ");
        if (skillMod < req)
        {
            sendSystemMessage(player, SID_NOT_ENOUGH_SKILL);
            clearSlicing(player);
            return;
        }
        String stat = effect.getString("STAT");
        int rank = effect.getInt("RANK");
        int cur_rank = getIntObjVar(item, "slice.rank." + stat);
        string_id apply_status = null;
        string_id apply_status_also = null;
        if (skillMod >= req)
        {
            if (rank > cur_rank + 1)
            {
                apply_status = new string_id("slicing/slicing", "cant_apply");
                dictionary effect_prereq = dataTableGetRow(table, row - 1);
                apply_status_also = new string_id("slicing/slicing_" + query, effect_prereq.getString("SLICE"));
                utils.setScriptVar(player, "slicing.apply_status", 1);
            }
            else if (rank <= cur_rank)
            {
                apply_status = new string_id("slicing/slicing", "already_applied");
                utils.setScriptVar(player, "slicing.apply_status", 2);
            }
            else 
            {
                apply_status = new string_id("slicing/slicing", "can_apply");
                utils.setScriptVar(player, "slicing.apply_status", 3);
            }
        }
        int new_cust = effect.getInt("COST");
        int cust = getIntObjVar(item, "slice.customized");
        if (cust + new_cust > 100)
        {
            apply_status = new string_id("slicing/slicing", "cant_apply_cost");
            utils.setScriptVar(player, "slicing.apply_status", 5);
        }
        String component = effect.getString("COMPONENT");
        String component_path = "object/tangible/smuggler/" + component + ".iff";
        string_id component_sid = new string_id("smuggler/items", component);
        if (component != null && !component.equals(""))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            obj_id[] contents = utils.getContents(pInv, true);
            int found = 0;
            for (obj_id content : contents) {
                if (getTemplateName(content).equals(component_path)) {
                    found = 1;
                    break;
                }
            }
            if (found == 0)
            {
                apply_status = new string_id("slicing/slicing", "missing_component");
                utils.setScriptVar(player, "slicing.apply_status", 4);
                utils.setScriptVar(player, "slicing.comp", component);
            }
        }
        utils.setScriptVar(player, "slicing.row", row);
        prose_package slice_attributes[] = new prose_package[4];
        slice_attributes[0] = prose.getPackage(SID_CUSTOMIZATION, effect.getInt("COST"));
        slice_attributes[1] = prose.getPackage(new string_id("slicing/slicing_" + query, effect.getString("STAT") + "_attrib"), effect.getInt("AMOUNT"));
        if (component == null || component.equals(""))
        {
            slice_attributes[2] = prose.getPackage(SID_NO_COMPONENT_REQUIRED);
        }
        else 
        {
            slice_attributes[2] = prose.getPackage(SID_REQUIRES_COMPONENT, component_sid);
        }
        slice_attributes[3] = prose.getPackage(apply_status, apply_status_also);
        sui.listbox(player, player, "@slicing/slicing_" + query + ":" + effect.getString("STAT") + "_detail", sui.OK_CANCEL, "@slicing/slicing:slice_attributes", slice_attributes, "handleApplySlice", true, false);
    }
    public static void handleApplySlice(obj_id player, int idx) throws InterruptedException
    {
        int row = utils.getIntScriptVar(player, "slicing.row");
        String query = utils.getStringScriptVar(player, "slicing.type");
        String table = null;
        if (query.equals("weapon"))
        {
            table = SLICE_WEAPON_TABLE;
        }
        else if (query.equals("armor"))
        {
            table = SLICE_ARMOR_TABLE;
        }
        obj_id item = utils.getObjIdScriptVar(player, "slicing.slice_item");
        int apply_status = utils.getIntScriptVar(player, "slicing.apply_status");
        if (apply_status == 2)
        {
            sendSystemMessage(player, SID_ALREADY_APPLIED);
            clearSlicing(player);
            return;
        }
        else if (apply_status == 1)
        {
            dictionary effect_prereq = dataTableGetRow(table, row - 1);
            string_id apply_status_also = new string_id("slicing/slicing_" + query, effect_prereq.getString("SLICE"));
            prose_package pp = prose.getPackage(SID_CANT_APPLY, apply_status_also);
            sendSystemMessageProse(player, pp);
            clearSlicing(player);
            return;
        }
        else if (apply_status == 4)
        {
            String comp = utils.getStringScriptVar(player, "slicing.comp");
            string_id missing_comp = new string_id("smuggler/items", comp);
            prose_package pp = prose.getPackage(SID_MISSING_COMP, missing_comp);
            sendSystemMessageProse(player, pp);
            clearSlicing(player);
            return;
        }
        dictionary effect = dataTableGetRow(table, row);
        int new_cust = effect.getInt("COST");
        int cust = getIntObjVar(item, "slice.customized");
        if (cust + new_cust > 100)
        {
            sendSystemMessage(player, SID_COST_TOO_HIGH);
            clearSlicing(player);
            return;
        }
        String component = effect.getString("COMPONENT");
        String component_path = "object/tangible/smuggler/" + component + ".iff";
        if (component != null && !component.equals(""))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            obj_id[] contents = utils.getContents(pInv, true);
            int found = 0;
            for (obj_id content : contents) {
                if (getTemplateName(content).equals(component_path)) {
                    found = 1;
                    consumeComponent(content);
                    break;
                }
            }
            if (found == 0)
            {
                String comp = utils.getStringScriptVar(player, "slicing.comp");
                string_id missing_comp = new string_id("smuggler/items", comp);
                prose_package pp = prose.getPackage(SID_MISSING_COMP, missing_comp);
                sendSystemMessageProse(player, pp);
                clearSlicing(player);
                return;
            }
        }
        successSlicing(player, item, row);
    }
    public static void successSlicing(obj_id player, obj_id item, int row) throws InterruptedException
    {
        String callback = utils.getStringScriptVar(player, "slicing.callback");
        dictionary outp = new dictionary();
        outp.put("success", 1);
        outp.put("row", row);
        outp.put("player", player);
        messageTo(item, callback, outp, 0.f, true);
        clearSlicing(player);
    }
    public static void failSlicing(obj_id player, obj_id item) throws InterruptedException
    {
        String callback = utils.getStringScriptVar(player, "slicing.callback");
        dictionary outp = new dictionary();
        outp.put("success", 0);
        outp.put("player", player);
        messageTo(item, callback, outp, 0.f, true);
        clearSlicing(player);
    }
    public static void consumeComponent(obj_id item) throws InterruptedException
    {
        int count = getCount(item);
        count--;
        if (count <= 0)
        {
            destroyObject(item);
        }
        else 
        {
            setCount(item, count);
        }
    }
}
