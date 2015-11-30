package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.colors;
import java.util.Set;
import java.util.Iterator;

public class hue extends script.base_script
{
    public hue()
    {
    }
    public static final String INDEX_BASE = "/private/index_color_";
    public static final String INDEX_1 = "/private/index_color_1";
    public static final String INDEX_2 = "/private/index_color_2";
    public static boolean setColor(obj_id target, int varIdx, color c) throws InterruptedException
    {
        if (!isIdValid(target) || (c == null) || (varIdx < 0))
        {
            return false;
        }
        String varPathName = INDEX_BASE + varIdx;
        return setColor(target, varPathName, c);
    }
    public static boolean setColor(obj_id target, String varPathName, color c) throws InterruptedException
    {
        if (!isIdValid(target) || (varPathName == null) || (c == null))
        {
            return false;
        }
        if (varPathName.startsWith("/"))
        {
            varPathName = varPathName.substring(1);
        }
        custom_var cv = getCustomVarByName(target, varPathName);
        if (cv == null)
        {
            return false;
        }
        if (!cv.isPalColor())
        {
            return false;
        }
        palcolor_custom_var pcv = (palcolor_custom_var)cv;
        if (pcv == null)
        {
            return false;
        }
        pcv.setToClosestColor(c);
        return true;
    }
    public static boolean setColor(obj_id target, int varIdx, int paletteIdx) throws InterruptedException
    {
        if (!isIdValid(target) || (varIdx < 0) || (paletteIdx < 0))
        {
            return false;
        }
        String varPathName = INDEX_BASE + varIdx;
        return setColor(target, varPathName, paletteIdx);
    }
    public static boolean setColor(obj_id target, String varPathName, int paletteIdx) throws InterruptedException
    {
        if (!isIdValid(target) || (varPathName == null) || (paletteIdx < 0))
        {
            return false;
        }
        if (varPathName.startsWith("/"))
        {
            varPathName = varPathName.substring(1);
        }
        custom_var cv = getCustomVarByName(target, varPathName);
        if (cv == null)
        {
            return false;
        }
        if (!cv.isPalColor())
        {
            return false;
        }
        ranged_int_custom_var ri = (ranged_int_custom_var)cv;
        if (ri == null)
        {
            return false;
        }
        ri.setValue(paletteIdx);
        return true;
    }
    public static int getVarColorIndex(obj_id target, String varPathName) throws InterruptedException
    {
        if (!isIdValid(target) || (varPathName == null))
        {
            return -1;
        }
        if (varPathName.startsWith("/"))
        {
            varPathName = varPathName.substring(1);
        }
        custom_var cv = getCustomVarByName(target, varPathName);
        if (cv == null)
        {
            return -1;
        }
        if (!cv.isPalColor())
        {
            return -1;
        }
        ranged_int_custom_var ri = (ranged_int_custom_var)cv;
        if (ri == null)
        {
            return -1;
        }
        return ri.getValue();
    }
    public static void setPalcolorCustomVarClosestColor(obj_id target, String varPathName, color c) throws InterruptedException
    {
        if (varPathName.startsWith("/"))
        {
            varPathName = varPathName.substring(1);
        }
        setPalcolorCustomVarClosestColor(target, varPathName, c.getR(), c.getG(), c.getB(), c.getA());
    }
    public static ranged_int_custom_var[] getPalcolorVars(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        PROFILER_START("hue.getPalcolorVars");
        custom_var[] allVars = getAllCustomVars(target);
        if (allVars == null)
        {
            PROFILER_STOP("hue.getPalcolorVars");
            return null;
        }
        int count = 0;
        for (int i = 0; i < allVars.length; i++)
        {
            if (allVars[i].isPalColor())
            {
                ++count;
            }
        }
        ranged_int_custom_var[] ret = new ranged_int_custom_var[count];
        int pos = 0;
        for (int i = 0; i < allVars.length; i++)
        {
            if (allVars[i].isPalColor())
            {
                ranged_int_custom_var ri = (ranged_int_custom_var)(allVars[i]);
                ret[pos++] = ri;
            }
        }
        if ((ret == null) || (ret.length == 0))
        {
            PROFILER_STOP("hue.getPalcolorVars");
            return null;
        }
        PROFILER_STOP("hue.getPalcolorVars");
        return ret;
    }
    public static dictionary getPalcolorData(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        PROFILER_START("hue.getPalcolorData");
        ranged_int_custom_var[] palColors = getPalcolorVars(target);
        if ((palColors == null) || (palColors.length == 0))
        {
            PROFILER_STOP("hue.getPalcolorData");
            return null;
        }
        dictionary d = new dictionary();
        for (int i = 0; i < palColors.length; i++)
        {
            ranged_int_custom_var ri = palColors[i];
            d.put(ri.getVarName(), ri.getValue());
        }
        PROFILER_STOP("hue.getPalcolorData");
        return d;
    }
    public static void setPalcolorData(obj_id target, dictionary colorData) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        if (colorData == null)
        {
            return;
        }
        ranged_int_custom_var[] palColors = getPalcolorVars(target);
        if (palColors == null || palColors.length == 0)
        {
            return;
        }
        for (int i = 0; i < palColors.length; i++)
        {
            String varName = palColors[i].getVarName();
            if (colorData.containsKey(varName))
            {
                int colorValue = colorData.getInt(varName);
                palColors[i].setValue(colorValue);
            }
        }
    }
    public static void hueObject(obj_id target) throws InterruptedException
    {
        PROFILER_START("hue.hueObject");
        ranged_int_custom_var[] c = getPalcolorVars(target);
        if (c != null)
        {
            for (int i = 0; i < c.length; i++)
            {
                int min = c[i].getMinRangeInclusive();
                int max = c[i].getMaxRangeInclusive();
                int randVal = rand(min, max);
                c[i].setValue(randVal);
            }
        }
        PROFILER_STOP("hue.hueObject");
    }
    public static void hueObject(obj_id target, int color) throws InterruptedException
    {
        PROFILER_START("hue.hueObjectColor");
        ranged_int_custom_var[] c = getPalcolorVars(target);
        if (c != null)
        {
            for (int i = 0; i < c.length; i++)
            {
                c[i].setValue(color);
            }
        }
        PROFILER_STOP("hue.hueObjectColor");
    }
    public static ranged_int_custom_var[] getRangedIntVars(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        PROFILER_START("hue.getRangedIntVars.custom");
        custom_var[] allVars = getAllCustomVars(target);
        PROFILER_STOP("hue.getRangedIntVars.custom");
        if (allVars == null)
        {
            return null;
        }
        PROFILER_START("hue.getRangedIntVars.alloc");
        ranged_int_custom_var[] ri = new ranged_int_custom_var[allVars.length];
        PROFILER_STOP("hue.getRangedIntVars.alloc");
        if (ri == null)
        {
            return null;
        }
        int n = 0;
        PROFILER_START("hue.getRangedIntVars.loopA." + allVars.length);
        for (int i = 0; i < allVars.length; i++)
        {
            if (allVars[i].isRangedInt())
            {
                ri[n] = (ranged_int_custom_var)allVars[i];
                n++;
            }
        }
        PROFILER_STOP("hue.getRangedIntVars.loopA." + allVars.length);
        if (n == 0)
        {
            return null;
        }
        PROFILER_START("hue.getRangedIntVars.alloc2");
        ranged_int_custom_var[] ret = new ranged_int_custom_var[n];
        PROFILER_STOP("hue.getRangedIntVars.alloc2");
        if (ret == null)
        {
            return null;
        }
        PROFILER_START("hue.getRangedIntVars.loopB." + n);
        for (int i = 0; i < n; i++)
        {
            ret[i] = ri[i];
        }
        PROFILER_STOP("hue.getRangedIntVars.loopB." + n);
        return ret;
    }
    public static void reshapeObject(obj_id target) throws InterruptedException
    {
        PROFILER_START("hue.reshapeObject");
        ranged_int_custom_var[] c = getRangedIntVars(target);
        if (c != null)
        {
            for (int i = 0; i < c.length; i++)
            {
                PROFILER_START("hue.reshapeObject.innerLoop");
                int min = c[i].getMinRangeInclusive();
                int max = c[i].getMaxRangeInclusive();
                int randVal = rand(min, max);
                c[i].setValue(randVal);
                PROFILER_STOP("hue.reshapeObject.innerLoop");
            }
        }
        else 
        {
        }
        PROFILER_STOP("hue.reshapeObject");
    }
    public static void randomizeObject(obj_id target) throws InterruptedException
    {
        reshapeObject(target);
        hueObject(target);
    }
    public static boolean setRangedIntCustomVar(obj_id target, String cvar_name, int idx) throws InterruptedException
    {
        if (!isIdValid(target) || (cvar_name == null) || cvar_name.equals("") || (idx < 0))
        {
            return false;
        }
        if (cvar_name.startsWith("/"))
        {
            cvar_name = cvar_name.substring(1);
        }
        custom_var cv = getCustomVarByName(target, cvar_name);
        if (cv == null)
        {
            return false;
        }
        if (!cv.isRangedInt())
        {
            return false;
        }
        ranged_int_custom_var ri = (ranged_int_custom_var)cv;
        if (ri == null)
        {
            return false;
        }
        ri.setValue(idx);
        return true;
    }
    public static boolean setTexture(obj_id target, int idx, int value) throws InterruptedException
    {
        if (!isIdValid(target) || (idx < 1) || (value < 0))
        {
            return false;
        }
        String varPathName = "private/index_texture_" + idx;
        return setRangedIntCustomVar(target, varPathName, value);
    }
}
