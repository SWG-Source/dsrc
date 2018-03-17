package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import java.util.Arrays;
import java.util.Vector;

public class magic_item extends script.base_script
{
    public magic_item()
    {
    }
    public static final float PT_DELTA = 0.15f;
    public static final String TBL_COST = "datatables/magic_item/mod_cost.iff";
    public static final String COL_MAX = "MAX";
    public static boolean convertToMagicItem(obj_id item, int mobLevel) throws InterruptedException
    {
        LOG("magic_item", "**************** convertToMagicItem: " + getGameTime() + " **************");
        if (!isIdValid(item))
        {
            return false;
        }
        if (mobLevel < 1)
        {
            return false;
        }
        LOG("magic_item", "params: item = " + item + " mobLevel = " + mobLevel);
        int total = getBuyPoints(mobLevel);
        if (total < 1)
        {
            return false;
        }
        LOG("magic_item", "buy points = " + total);
        Vector mods = new Vector();
        mods.setSize(0);
        int got = getGameObjectType(item);
        LOG("magic_item", "GOT = " + got);
        String apr = getAppearance(item);
        if ((apr == null) || (apr.equals("")))
        {
            return false;
        }
        LOG("magic_item", "FULL appearance = " + apr);
        apr = apr.substring(11);
        LOG("magic_item", "SHORT appearance = " + apr);
        java.util.StringTokenizer st = new java.util.StringTokenizer(apr, "_");
        int tokenCount = st.countTokens();
        LOG("magic_item", "token count = " + tokenCount);
        if (tokenCount < 2)
        {
            return false;
        }
        String tbl = "";
        if (isGameObjectTypeOf(got, GOT_clothing) || got == GOT_misc_container_wearable)
        {
            String tblName = st.nextToken();
            if (tblName.equals("wke"))
            {
                tblName = st.nextToken();
            }
            tbl = "datatables/magic_item/wearable/" + tblName + ".iff";
        }
        else if (isGameObjectTypeOf(got, GOT_weapon))
        {
            int weapon_type = getWeaponType(item);
            switch (weapon_type)
            {
                case 0:
                tbl = "datatables/magic_item/weapon/rifle.iff";
                break;
                case 1:
                tbl = "datatables/magic_item/weapon/carbine.iff";
                break;
                case 2:
                tbl = "datatables/magic_item/weapon/pistol.iff";
                break;
                case 3:
                tbl = "datatables/magic_item/weapon/heavy_ranged.iff";
                break;
                case 4:
                tbl = "datatables/magic_item/weapon/1handed_melee.iff";
                break;
                case 5:
                tbl = "datatables/magic_item/weapon/2handed_melee.iff";
                break;
                case 6:
                tbl = "datatables/magic_item/weapon/unarmed_melee.iff";
                break;
                case 7:
                tbl = "datatables/magic_item/weapon/polearm_melee.iff";
                break;
                default:
                LOG("magic_item", "unsupported weapon type: " + weapon_type);
                return false;
            }
        }
        else if (got >= GOT_armor && got <= GOT_armor_foot)
        {
            tbl = "datatables/magic_item/armor/armor.iff";
        }
        else 
        {
            LOG("magic_item", "GOT not defined in magic_item.convertToMagicItem()!");
            return false;
        }
        LOG("magic_item", "tbl = " + tbl);
        mods = getAppearanceMagicMods(tbl, apr);
        if ((mods == null) || (mods.size() == 0))
        {
            return false;
        }
        LOG("magic_item", "retrieved appearance magic mods...");
        int numMods = getNumMods(mobLevel);
        LOG("magic_item", "calculated num mods = " + numMods);
        if (numMods > mods.size())
        {
            LOG("magic_item", "truncated num mods to " + mods.size());
            numMods = mods.size();
        }
        float spent = 0;
        Vector pts = new Vector();
        pts.setSize(0);
        if (numMods == 1)
        {
            pts = utils.addElement(pts, 100);
            spent = 100;
            LOG("magic_item", "pts[0] = 100");
        }
        else 
        {
            int shares = 0;
            for (int i = 0; i < numMods; i++)
            {
                shares += (i + 1);
            }
            LOG("magic_item", "total calculated shared = " + shares);
            for (int i = 0; i < numMods; i++)
            {
                LOG("magic_item", "*** WEIGHTING: " + i + " ******");
                float pivot = 100f - spent;
                float cntLeft = (float)(numMods - i);
                LOG("magic_item", "pivot = " + pivot + " cntLeft = " + cntLeft);
                int wt = (int)pivot;
                if (cntLeft > 1)
                {
                    float min = pivot / cntLeft;
                    float max = pivot * 2f / 3f;
                    LOG("magic_item", "min = " + min + " max = " + max);
                    wt = rand((int)min, (int)max);
                }
                spent += wt;
                LOG("magic_item", "pts[" + i + "] = " + wt);
                pts = utils.addElement(pts, wt);
            }
        }
        if ((pts != null) && (pts.size() > 0))
        {
            LOG("magic_item", "pts.length = " + pts.size());
            boolean litmus = true;
            boolean appliedMod = false;
            for (int i = 0; i < pts.size(); i++)
            {
                LOG("magic_item", "*** APPLYING: " + i + " ******");
                LOG("magic_item", "mods.length = " + mods.size());
                int idx = rand(0, mods.size() - 1);
                LOG("magic_item", "idx = " + idx);
                String myMod = ((String)mods.get(idx));
                if ((myMod != null) && (!myMod.equals("")))
                {
                    LOG("magic_item", "attempting to apply mod: " + myMod);
                    boolean applyNegative = false;
                    if (myMod.startsWith("-"))
                    {
                        myMod = myMod.substring(1);
                        applyNegative = true;
                    }
                    int tmp = ((Integer)pts.get(i)).intValue();
                    int cost = getMagicModCost(myMod);
                    LOG("magic_item", "mod cost = " + cost);
                    if (cost > 0)
                    {
                        LOG("magic_item", "wt = " + tmp);
                        float perc = (float)(tmp) / 100f;
                        LOG("magic_item", "perc = " + perc);
                        int buyPts = (int)(perc * (float)total);
                        LOG("magic_item", "buyPts = " + buyPts);
                        int modVal = buyPts / cost;
                        LOG("magic_item", "resulting mod value = " + modVal);
                        int modValMax = dataTableGetInt(TBL_COST, myMod, COL_MAX);
                        if (modVal > modValMax)
                        {
                            modVal = modValMax;
                        }
                        if (modVal > 0)
                        {
                            if (applyNegative)
                            {
                                litmus &= setSkillModBonus(item, myMod, -modVal);
                            }
                            else 
                            {
                                litmus &= setSkillModBonus(item, myMod, modVal);
                            }
                            appliedMod = true;
                        }
                    }
                }
                mods = utils.removeElementAt(mods, idx);
                if ((mods == null) || (mods.size() == 0))
                {
                    LOG("magic_item", "ran out of mods... breaking out...");
                    break;
                }
            }
            if (litmus && appliedMod)
            {
                setCondition(item, CONDITION_MAGIC_ITEM);
            }
            return litmus;
        }
        return false;
    }
    public static int getBuyPoints(int mobLevel) throws InterruptedException
    {
        if (mobLevel < 1)
        {
            return -1;
        }
        int ret = (int)((float)mobLevel * rand(1f - PT_DELTA, 1f + PT_DELTA));
        return ret;
    }
    public static int getNumMods(int pivot) throws InterruptedException
    {
        if (pivot < 20)
        {
            return 1;
        }
        if (pivot < 40)
        {
            return rand(1, 2);
        }
        if (pivot < 60)
        {
            return rand(1, 3);
        }
        return rand(2, 3);
    }
    public static int getMagicModCost(String modName) throws InterruptedException
    {
        if ((modName == null) || (modName.equals("")))
        {
            return -1;
        }
        int val = dataTableGetInt(TBL_COST, modName, "COST");
        if (val > 0)
        {
            return val;
        }
        return -1;
    }
    public static Vector getAppearanceMagicMods(String tbl, String apr) throws InterruptedException
    {
        if ((tbl == null) || (tbl.equals("")))
        {
            return null;
        }
        if ((apr == null) || (apr.equals("")))
        {
            return null;
        }
        String[] cols = dataTableGetColumnNames(tbl);
        if ((cols == null) || (cols.length == 0))
        {
            return null;
        }
        String[] mods = new String[0];
        for (int i = 0; i < cols.length; i++)
        {
            if (apr.startsWith(cols[i]))
            {
                mods = dataTableGetStringColumnNoDefaults(tbl, cols[i]);
            }
        }
        if ((mods == null) || (mods.length == 0))
        {
            mods = dataTableGetStringColumnNoDefaults(tbl, "generic");
        }
        if ((mods == null) || (mods.length == 0))
        {
            return null;
        }
        return new Vector(Arrays.asList(mods));
    }
    public static obj_id makeGem(obj_id targetContainer, String gemType, int mobLevel) throws InterruptedException
    {
        LOG("magic_item", "**************** makeGem: " + getGameTime() + " **************");
        if (!isIdValid(targetContainer))
        {
            return null;
        }
        obj_id item = createObject("object/tangible/gem/" + gemType + ".iff", targetContainer, "");
        if (!isIdValid(item))
        {
            return null;
        }
        if (mobLevel < 1)
        {
            return null;
        }
        LOG("magic_item", "params: item = " + item + " mobLevel = " + mobLevel);
        int total = getBuyPoints(mobLevel);
        if (total < 1)
        {
            return null;
        }
        LOG("magic_item", "buy points = " + total);
        String[] allmods = getGemMods(gemType);
        if (allmods == null || allmods.length == 0)
        {
            return null;
        }
        Vector mods = new Vector(Arrays.asList(allmods));
        if ((mods == null) || (mods.size() == 0))
        {
            return null;
        }
        LOG("magic_item", "retrieved appearance magic mods...");
        int numMods = getNumMods(mobLevel);
        LOG("magic_item", "calculated num mods = " + numMods);
        if (numMods > mods.size())
        {
            LOG("magic_item", "truncated num mods to " + mods.size());
            numMods = mods.size();
        }
        float spent = 0;
        Vector pts = new Vector();
        pts.setSize(0);
        if (numMods == 1)
        {
            pts = utils.addElement(pts, 100);
            spent = 100;
            LOG("magic_item", "pts[0] = 100");
        }
        else 
        {
            int shares = 0;
            for (int i = 0; i < numMods; i++)
            {
                shares += (i + 1);
            }
            LOG("magic_item", "total calculated shared = " + shares);
            for (int i = 0; i < numMods; i++)
            {
                LOG("magic_item", "*** WEIGHTING: " + i + " ******");
                float pivot = 100f - spent;
                float cntLeft = (float)(numMods - i);
                LOG("magic_item", "pivot = " + pivot + " cntLeft = " + cntLeft);
                int wt = (int)pivot;
                if (cntLeft > 1)
                {
                    float min = pivot / cntLeft;
                    float max = pivot * 2f / 3f;
                    LOG("magic_item", "min = " + min + " max = " + max);
                    wt = rand((int)min, (int)max);
                }
                spent += wt;
                LOG("magic_item", "pts[" + i + "] = " + wt);
                pts = utils.addElement(pts, wt);
            }
        }
        if ((pts != null) && (pts.size() > 0))
        {
            LOG("magic_item", "pts.length = " + pts.size());
            boolean litmus = true;
            boolean appliedMod = false;
            for (int i = 0; i < pts.size(); i++)
            {
                LOG("magic_item", "*** APPLYING: " + i + " ******");
                LOG("magic_item", "mods.length = " + mods.size());
                int idx = rand(0, mods.size() - 1);
                LOG("magic_item", "idx = " + idx);
                String myMod = (String)mods.elementAt(idx);
                if ((myMod != null) && (!myMod.equals("")))
                {
                    LOG("magic_item", "attempting to apply mod: " + myMod);
                    int tmp = ((Integer)pts.get(i)).intValue();
                    int cost = getMagicModCost(myMod);
                    LOG("magic_item", "mod cost = " + cost);
                    if (cost > 0)
                    {
                        LOG("magic_item", "wt = " + tmp);
                        float perc = (float)(tmp) / 100f;
                        LOG("magic_item", "perc = " + perc);
                        int buyPts = (int)(perc * (float)total);
                        LOG("magic_item", "buyPts = " + buyPts);
                        int modVal = buyPts / cost;
                        LOG("magic_item", "resulting mod value = " + modVal);
                        int modValMax = dataTableGetInt(TBL_COST, myMod, COL_MAX);
                        if (modVal > modValMax)
                        {
                            modVal = modValMax;
                        }
                        if (modVal < 1 && i == 0)
                        {
                            modVal = 1;
                        }
                        if (modVal > 0)
                        {
                            litmus &= setSkillModBonus(item, myMod, modVal);
                            appliedMod = true;
                        }
                    }
                }
                mods.removeElementAt(idx);
                if ((mods == null) || (mods.size() == 0))
                {
                    LOG("magic_item", "ran out of mods... breaking out...");
                    break;
                }
            }
            if (litmus && appliedMod)
            {
                setCondition(item, CONDITION_MAGIC_ITEM);
            }
            return item;
        }
        return null;
    }
    public static obj_id makeGem(obj_id targetContainer, int mobLevel) throws InterruptedException
    {
        return makeGem(targetContainer, "default", mobLevel);
    }
    public static String[] getGemMods(String gemType) throws InterruptedException
    {
        if (gemType == null || gemType.equals(""))
        {
            return null;
        }
        String[] allMods = dataTableGetStringColumn(TBL_COST, "MOD");
        if (allMods == null || allMods.length == 0)
        {
            return null;
        }
        if (gemType.equals("clothing") || gemType.equals("default"))
        {
            return allMods;
        }
        else if (gemType.equals("armor"))
        {
            Vector armorMods = new Vector();
            armorMods.setSize(0);
            for (int i = 0; i < allMods.length; i++)
            {
                if (dataTableGetInt(TBL_COST, allMods[i], "ARMOR_ATTACHMENT_MODS") != 0)
                {
                    armorMods = utils.addElement(armorMods, allMods[i]);
                }
            }
            if (armorMods != null && armorMods.size() > 0)
            {
                String[] _armorMods = new String[0];
                if (armorMods != null)
                {
                    _armorMods = new String[armorMods.size()];
                    armorMods.toArray(_armorMods);
                }
                return _armorMods;
            }
        }
        return null;
    }
}
