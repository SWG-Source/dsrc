package script.item.slicing;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.slicing;
import script.library.utils;
import script.library.weapons;
import script.library.xp;

public class slicing_armor extends script.base_script
{
    public slicing_armor()
    {
    }
    public static final string_id SID_SLICE = new string_id("slicing/slicing", "slice");
    public static final string_id SID_REPAIR = new string_id("slicing/slicing", "repair");
    public static final string_id SID_NO_KIT = new string_id("slicing/slicing", "no_armor_kit");
    public static final string_id SID_RES_MOD = new string_id("slicing/slicing", "res_mod");
    public static final string_id SID_DEF_MOD = new string_id("slicing/slicing", "def_mod");
    public static final string_id SID_CRIT_MOD = new string_id("slicing/slicing", "acrit_mod");
    public static final string_id SID_AB_MOD = new string_id("slicing/slicing", "ab_mod");
    public static final string_id SID_FAIL_ARMOR = new string_id("slicing/slicing", "fail_armor");
    public static final string_id SID_ARMOR_AT_MAX = new string_id("slicing/slicing", "armor_at_max");
    public static final string_id SID_NOT_IN_INV = new string_id("slicing/slicing", "not_in_inv");
    public static final string_id SID_NOT_ARMOR = new string_id("slicing/slicing", "not_armor");
    public static final string_id SID_FAILED_NO_EQUIP = new string_id("slicing/slicing", "failed_no_equip");
    public static final string_id SID_SLICE_APPLIED = new string_id("slicing/slicing", "slice_applied");
    public static final String COMP_BI_L = "armor_composite_bicep_l.iff";
    public static final String COMP_BI_R = "armor_composite_bicep_r.iff";
    public static final String COMP_BOOTS = "armor_composite_boots.iff";
    public static final String COMP_BRC_L = "armor_composite_bracer_l.iff";
    public static final String COMP_BRC_R = "armor_composite_bracer_r.iff";
    public static final String COMP_CHEST = "armor_composite_chest_plate.iff";
    public static final String COMP_GLOVE = "armor_composite_gloves.iff";
    public static final String COMP_HELM = "armor_composite_helmet.iff";
    public static final String COMP_LEGS = "armor_composite_leggings.iff";
    public static final float ARMOR_MIN = 0.0f;
    public static final float ARMOR_MAX = 1.0f;
    public static final String SLICE_TABLE = "datatables/smuggler/slice_armor.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int ourType = getGameObjectType(self);
        if (!isGameObjectTypeOf(ourType, GOT_armor))
        {
            detachScript(self, "item.slicing.slicing_armor");
        }
        return SCRIPT_CONTINUE;
    }
    public int finishSlicing(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isIdValid(pInv))
        {
            return SCRIPT_CONTINUE;
        }
        if (!contains(pInv, self))
        {
            sendSystemMessage(player, SID_NOT_IN_INV);
            return SCRIPT_CONTINUE;
        }
        int success = params.getInt("success");
        int row = params.getInt("row");
        if (success == 1)
        {
            dictionary effect = dataTableGetRow(SLICE_TABLE, row);
            String slice_name = effect.getString("SLICE");
            String stat = effect.getString("STAT");
            int slice_cost = effect.getInt("COST");
            int slice_amount = effect.getInt("AMOUNT");
            boolean asuccess = false;
            if (stat.equals("resilience"))
            {
                asuccess = applyResilienceChange(self, player, slice_amount);
            }
            else if (stat.equals("deflect"))
            {
                asuccess = applyDeflectChange(self, player, slice_amount);
            }
            else if (stat.equals("crit_chance"))
            {
                asuccess = applyCritChange(self, player, slice_amount);
            }
            else if (stat.equals("armor_break"))
            {
                asuccess = applyArmorBreakChange(self, player, slice_amount);
            }
            if (asuccess)
            {
                int armor_cust = getIntObjVar(self, "slice.customized");
                armor_cust += slice_cost;
                setObjVar(self, "slice.customized", armor_cust);
                setObjVar(self, "slice.rank." + stat, effect.getInt("RANK"));
                prose_package pp = prose.getPackage(SID_SLICE_APPLIED, new string_id("slicing/slicing_armor", slice_name));
                sendSystemMessageProse(player, pp);
                setObjVar(self, "slicing.new_hacked", 1);
                sendDirtyAttributesNotification(self);
            }
        }
        else 
        {
        }
        return SCRIPT_CONTINUE;
    }
    public boolean applyResilienceChange(obj_id self, obj_id player, int slice_amount) throws InterruptedException
    {
        prose_package pp = prose.getPackage(SID_RES_MOD, slice_amount);
        sendSystemMessageProse(player, pp);
        setObjVar(self, "slice.resilience", slice_amount);
        return true;
    }
    public boolean applyDeflectChange(obj_id self, obj_id player, int slice_amount) throws InterruptedException
    {
        prose_package pp = prose.getPackage(SID_DEF_MOD, slice_amount);
        sendSystemMessageProse(player, pp);
        setObjVar(self, "slice.deflect", slice_amount);
        return true;
    }
    public boolean applyCritChange(obj_id self, obj_id player, int slice_amount) throws InterruptedException
    {
        prose_package pp = prose.getPackage(SID_CRIT_MOD, slice_amount);
        sendSystemMessageProse(player, pp);
        setObjVar(self, "slice.crit", slice_amount);
        return true;
    }
    public boolean applyArmorBreakChange(obj_id self, obj_id player, int slice_amount) throws InterruptedException
    {
        prose_package pp = prose.getPackage(SID_AB_MOD, slice_amount);
        sendSystemMessageProse(player, pp);
        setObjVar(self, "slice.armor_break", slice_amount);
        return true;
    }
}
