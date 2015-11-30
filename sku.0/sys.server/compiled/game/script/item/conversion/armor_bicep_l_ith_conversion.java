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

public class armor_bicep_l_ith_conversion extends script.item.conversion.armor_base_conversion
{
    public armor_bicep_l_ith_conversion()
    {
    }
    public static final String[] ARMOR_SET_ASSAULT = 
    {
        "ithorian_sentinel/ith_armor_s03_bicep_l.iff"
    };
    public static final String[] ARMOR_SET_BATTLE = 
    {
        "ithorian_defender/ith_armor_s01_bicep_l.iff"
    };
    public static final String[] ARMOR_SET_RECON = 
    {
        "ithorian_guardian/ith_armor_s02_bicep_l.iff"
    };
    public static final String[] ASSAULT_TYPE = 
    {
        "Ithorian Sentinel"
    };
    public static final String[] BATTLE_TYPE = 
    {
        "Ithorian Defender"
    };
    public static final String[] RECON_TYPE = 
    {
        "Ithorian Guardian"
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
