package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.gcw;

public class regions extends script.base_script
{
    public regions()
    {
    }
    public static final int GEO_DESERT = 0;
    public static final int GEO_FOREST = 1;
    public static final int GEO_JUNGLE = 2;
    public static final int GEO_PLATEAU = 3;
    public static final int GEO_OASIS = 4;
    public static final int GEO_CANYON = 5;
    public static final int GEO_LAKE = 6;
    public static final int GEO_LAKESHORE = 7;
    public static final int GEO_BEACH = 8;
    public static final int GEO_OCEAN = 9;
    public static final int GEO_UNDERWATER = 10;
    public static final int GEO_RIVER = 11;
    public static final int GEO_VOLCANO = 12;
    public static final int GEO_SWAMP = 13;
    public static final int GEO_PLAINS = 14;
    public static final int GEO_PRAIRIE = 15;
    public static final int GEO_MOUNTAINS = 16;
    public static final int GEO_HILLS = 17;
    public static final int GEO_GRASSLAND = 18;
    public static final int GEO_WASTELAND = 19;
    public static final int GEO_CITY = 20;
    public static final int GEO_FICTIONAL = 21;
    public static final int GEO_PATHFIND = 22;
    public static final int GEO_DEFAULT = 23;
    public static final int GEO_THEATER = 24;
    public static final int GEO_OVERLOAD = 25;
    public static final int GEO_MAX = 26;
    public static final int MISSION_NONE = 0;
    public static final int MISSION_DESTROY = 1;
    public static final int MISSION_OTHER = 2;
    public static final int MISSION_TATOOINE_ANCHORHEAD = 3;
    public static final int MISSION_TATOOINE_BESTINE = 4;
    public static final int MISSION_TATOOINE_MOS_EISLEY = 5;
    public static final int MISSION_TATOOINE_MOS_ENTHA = 6;
    public static final int MISSION_TATOOINE_MOS_ESPA = 7;
    public static final int MISSION_TATOOINE_MOS_TEIKE = 8;
    public static final int MISSION_TATOOINE_WAYFAR = 9;
    public static final int MISSION_NABOO_THEED = 10;
    public static final int MISSION_NABOO_DEEJA_PEAK = 11;
    public static final int MISSION_NABOO_KAADARA = 12;
    public static final int MISSION_NABOO_MOENIA = 13;
    public static final int MISSION_NABOO_KEREN = 14;
    public static final int MISSION_TATOOINE_BIB_1 = 15;
    public static final int MISSION_CORELLIA_CORONET = 16;
    public static final int MISSION_CORELLIA_TYRENA = 17;
    public static final int MISSION_CORELLIA_BELA_VISTAL = 18;
    public static final int MISSION_CORELLIA_VRENI_ISLAND = 19;
    public static final int MISSION_CORELLIA_KOR_VELLA = 20;
    public static final int MISSION_CORELLIA_DAOBA_GUERFEL = 21;
    public static final int MISSION_RORI_NARMLE = 22;
    public static final int MISSION_RORI_RESTUSS = 23;
    public static final int MISSION_TALUS_DEARIC = 24;
    public static final int MISSION_TALUS_NASHAL = 25;
    public static final int MISSION_YAVIN4_LABOR_OUTPOST = 26;
    public static final int MISSION_YAVIN4_MINING_OUTPOST = 27;
    public static final int MISSION_ENDOR_NEUTRAL_OUTPOST = 28;
    public static final int MISSION_DANTOOINE_MINING_OUTPOST = 29;
    public static final int MISSION_DANTOOINE_PIRATE_OUTPOST = 30;
    public static final int MISSION_DATHOMIR_TRADE_OUTPOST = 31;
    public static final int MISSION_DATHOMIR_SURVEY_OUTPOST = 32;
    public static final int MISSION_LOK_NYMS_STRONGHOLD = 33;
    public static final int MISSION_MAX = 34;
    public static final int RD_NEWBIE = 0;
    public static final int RD_EASY = 1;
    public static final int RD_NORMAL = 2;
    public static final int RD_MEDIUM = 3;
    public static final int RD_HARD = 4;
    public static final int RD_EXTREME = 5;
    public static final int RD_MAX = 6;
    public static final int PVP_REGION_TYPE_NORMAL = 0;
    public static final int PVP_REGION_TYPE_TRUCE = 1;
    public static final int PVP_REGION_TYPE_BATTLEFIELD_PVP = 2;
    public static final int PVP_REGION_TYPE_BATTLEFIELD_PVE = 3;
    public static final int PVP_REGION_TYPE_ADVANCED = 4;
    public static final int PVP_REGION_TYPE_MAX = 5;
    public static final int BUILD_FALSE = 0;
    public static final int BUILD_TRUE = 1;
    public static final int BUILD_MAX = 2;
    public static final int MUNI_FALSE = 0;
    public static final int MUNI_TRUE = 1;
    public static final int MUNI_MAX = 2;
    public static final int SPAWN_FALSE = 0;
    public static final int SPAWN_TRUE = 1;
    public static final int SPAWN_MISSION = 2;
    public static final int SPAWN_DEFAULT = 3;
    public static final int SPAWN_MAX = 4;
    public static final int SPAWN_DIFFICULTY = 5;
    public static final int BIT_PVP = 0;
    public static final int BIT_BUILD = 1;
    public static final int BIT_MUNICIPAL = 2;
    public static final int BIT_SPAWNABLE = 3;
    public static final int BIT_MISSION = 4;
    public static String translateGeoToString(int geo) throws InterruptedException
    {
        if ((geo >= GEO_MAX) || (geo < 0))
        {
            return null;
        }
        switch (geo)
        {
            case GEO_DESERT:
            return "desert";
            case GEO_FOREST:
            return "forest";
            case GEO_JUNGLE:
            return "jungle";
            case GEO_PLATEAU:
            return "plateau";
            case GEO_OASIS:
            return "oasis";
            case GEO_CANYON:
            return "canyon";
            case GEO_LAKE:
            return "lake";
            case GEO_LAKESHORE:
            return "lakeshore";
            case GEO_BEACH:
            return "beach";
            case GEO_OCEAN:
            return "ocean";
            case GEO_UNDERWATER:
            return "underwater";
            case GEO_RIVER:
            return "river";
            case GEO_VOLCANO:
            return "volcano";
            case GEO_SWAMP:
            return "swamp";
            case GEO_PLAINS:
            return "plains";
            case GEO_PRAIRIE:
            return "prairie";
            case GEO_MOUNTAINS:
            return "mountains";
            case GEO_HILLS:
            return "hills";
            case GEO_GRASSLAND:
            return "grassland";
            case GEO_WASTELAND:
            return "wasteland";
            case GEO_CITY:
            return "city";
            case GEO_FICTIONAL:
            return "fictional";
            case GEO_OVERLOAD:
            return "overload";
            case GEO_DEFAULT:
            return "default";
            case GEO_THEATER:
            return "theater";
        }
        return null;
    }
    public static int getDeliverMissionRegionType(String strRegionName) throws InterruptedException
    {
        if (strRegionName.equals("anchorhead"))
        {
            return MISSION_TATOOINE_ANCHORHEAD;
        }
        else if (strRegionName.equals("bestine"))
        {
            return MISSION_TATOOINE_BESTINE;
        }
        else if (strRegionName.equals("mos_eisley"))
        {
            return MISSION_TATOOINE_MOS_EISLEY;
        }
        else if (strRegionName.equals("mos_entha"))
        {
            return MISSION_TATOOINE_MOS_ENTHA;
        }
        else if (strRegionName.equals("mos_espa"))
        {
            return MISSION_TATOOINE_MOS_ESPA;
        }
        else if (strRegionName.equals("mos_teike"))
        {
            return MISSION_TATOOINE_MOS_TEIKE;
        }
        else if (strRegionName.equals("wayfar"))
        {
            return MISSION_TATOOINE_WAYFAR;
        }
        else if (strRegionName.equals("deeja_peak"))
        {
            return MISSION_NABOO_DEEJA_PEAK;
        }
        else if (strRegionName.equals("theed"))
        {
            return MISSION_NABOO_THEED;
        }
        else if (strRegionName.equals("keren"))
        {
            return MISSION_NABOO_KEREN;
        }
        else if (strRegionName.equals("kaadara"))
        {
            return MISSION_NABOO_KAADARA;
        }
        else if (strRegionName.equals("moenia"))
        {
            return MISSION_NABOO_MOENIA;
        }
        else if (strRegionName.equals("coronet"))
        {
            return MISSION_CORELLIA_CORONET;
        }
        else if (strRegionName.equals("tyrena"))
        {
            return MISSION_CORELLIA_TYRENA;
        }
        else if (strRegionName.equals("bela_vistal"))
        {
            return MISSION_CORELLIA_BELA_VISTAL;
        }
        else if (strRegionName.equals("vreni_island"))
        {
            return MISSION_CORELLIA_VRENI_ISLAND;
        }
        else if (strRegionName.equals("kor_vella"))
        {
            return MISSION_CORELLIA_KOR_VELLA;
        }
        else if (strRegionName.equals("daoba_guerfel"))
        {
            return MISSION_CORELLIA_DAOBA_GUERFEL;
        }
        else if (strRegionName.equals("doaba_guerfel"))
        {
            return MISSION_CORELLIA_DAOBA_GUERFEL;
        }
        else if (strRegionName.equals("narmle"))
        {
            return MISSION_RORI_NARMLE;
        }
        else if (strRegionName.equals("restuss"))
        {
            return MISSION_RORI_RESTUSS;
        }
        else if (strRegionName.equals("dearic"))
        {
            return MISSION_TALUS_DEARIC;
        }
        else if (strRegionName.equals("nashal"))
        {
            return MISSION_TALUS_NASHAL;
        }
        else if (strRegionName.equals("yavin4_labor_outpost"))
        {
            return MISSION_YAVIN4_LABOR_OUTPOST;
        }
        else if (strRegionName.equals("yavin4_mining_outpost"))
        {
            return MISSION_YAVIN4_MINING_OUTPOST;
        }
        else if (strRegionName.equals("endor_neutral_outpost"))
        {
            return MISSION_ENDOR_NEUTRAL_OUTPOST;
        }
        else if (strRegionName.equals("dantooine_mining_outpost"))
        {
            return MISSION_DANTOOINE_MINING_OUTPOST;
        }
        else if (strRegionName.equals("dantooine_pirate_outpost"))
        {
            return MISSION_DANTOOINE_PIRATE_OUTPOST;
        }
        else if (strRegionName.equals("dathomir_trade_outpost"))
        {
            return MISSION_DATHOMIR_TRADE_OUTPOST;
        }
        else if (strRegionName.equals("dathomir_survey_outpost"))
        {
            return MISSION_DATHOMIR_SURVEY_OUTPOST;
        }
        else if (strRegionName.equals("lok_nyms_stronghold"))
        {
            return MISSION_LOK_NYMS_STRONGHOLD;
        }
        return 0;
    }
    public static String getPlanetNameByObjId(obj_id objPlanet) throws InterruptedException
    {
        LOG("regions_spam", "objPlanet is " + objPlanet);
        obj_id objNamedPlanet = getPlanetByName("naboo");
        LOG("regions_spam", "naboo is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "naboo";
        }
        objNamedPlanet = getPlanetByName("tatooine");
        LOG("regions_spam", "tatooine is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "tatooine";
        }
        objNamedPlanet = getPlanetByName("dathomir");
        LOG("regions_spam", "dathomir is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "dathomir";
        }
        objNamedPlanet = getPlanetByName("corellia");
        LOG("regions_spam", "corellia is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "corellia";
        }
        objNamedPlanet = getPlanetByName("endor");
        LOG("regions_spam", "endor is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "endor";
        }
        objNamedPlanet = getPlanetByName("yavin");
        LOG("regions_spam", "yavin is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "yavin";
        }
        objNamedPlanet = getPlanetByName("lok");
        LOG("regions_spam", "lok is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "lok";
        }
        objNamedPlanet = getPlanetByName("tanaab");
        LOG("regions_spam", "tanaab is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "tanaab";
        }
        objNamedPlanet = getPlanetByName("talus");
        LOG("regions_spam", "talus is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "talus";
        }
        objNamedPlanet = getPlanetByName("rori");
        LOG("regions_spam", "rori is " + objNamedPlanet);
        if (objNamedPlanet == objPlanet)
        {
            return "rori";
        }
        return null;
    }
    public static boolean isInPvPRegion(obj_id object) throws InterruptedException
    {
        location loc = getLocation(object);
        region[] regions = getRegionsAtPoint(loc);
        if (regions == null || regions.length == 0)
        {
            return false;
        }
        for (int i = 0; i < regions.length; i++)
        {
            String regionName = regions[i].getName();
            if (isIdValid(gcw.getPvpRegionControllerIdByName(object, regionName)))
            {
                return true;
            }
        }
        return false;
    }
}
