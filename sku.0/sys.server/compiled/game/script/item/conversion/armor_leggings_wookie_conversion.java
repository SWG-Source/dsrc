package script.item.conversion;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;

public class armor_leggings_wookie_conversion extends script.item.conversion.armor_base_conversion
{
    public armor_leggings_wookie_conversion()
    {
    }
    public static final String[] ARMOR_SET_ASSAULT = 
    {
        "kashyyykian_hunting/armor_kashyyykian_hunting_leggings.iff"
    };
    public static final String[] ARMOR_SET_BATTLE = 
    {
        "kashyyykian_black_mtn/armor_kashyyykian_black_mtn_leggings.iff"
    };
    public static final String[] ARMOR_SET_RECON = 
    {
        "kashyyykian_ceremonial/armor_kashyyykian_ceremonial_leggings.iff"
    };
    public static final String[] ASSAULT_TYPE = 
    {
        "Kashyykian Hunting"
    };
    public static final String[] BATTLE_TYPE = 
    {
        "Kashyykian Black Mountain"
    };
    public static final String[] RECON_TYPE = 
    {
        "Kashyykian Ceremonial"
    };
    public String[] getAssaultTemplates() throws InterruptedException
    {
        return ARMOR_SET_ASSAULT;
    }
    public String[] getBattleTemplates() throws InterruptedException
    {
        return ARMOR_SET_BATTLE;
    }
    public String[] getReconTemplates() throws InterruptedException
    {
        return ARMOR_SET_RECON;
    }
    public String[] getAssaultTypes() throws InterruptedException
    {
        return ASSAULT_TYPE;
    }
    public String[] getBattleTypes() throws InterruptedException
    {
        return BATTLE_TYPE;
    }
    public String[] getReconTypes() throws InterruptedException
    {
        return RECON_TYPE;
    }
}
