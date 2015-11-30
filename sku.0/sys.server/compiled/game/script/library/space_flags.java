package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class space_flags extends script.base_script
{
    public space_flags()
    {
    }
    public static final String PRIVATEER_TATOOINE = "tatooinePrivateer";
    public static final String PRIVATEER_NABOO = "nabooPrivateer";
    public static final String PRIVATEER_CORELLIA = "corelliaPrivateer";
    public static final String IMPERIAL_TATOOINE = "tatooineImperial";
    public static final String IMPERIAL_NABOO = "nabooImperial";
    public static final String IMPERIAL_CORELLIA = "corelliaImperial";
    public static final String REBEL_TATOOINE = "tatooineRebel";
    public static final String REBEL_NABOO = "nabooRebel";
    public static final String REBEL_CORELLIA = "corelliaRebel";
    public static final String SPACE_TRACK_FLAG = "spaceTrackFlagListName";
    public static void setSpaceTrack(obj_id player, String spaceTrack) throws InterruptedException
    {
        clearSpaceTrack(player);
        setObjVar(player, SPACE_TRACK_FLAG, spaceTrack);
        setObjVar(player, spaceTrack + ".member", true);
    }
    public static String getSpaceTrack(obj_id player) throws InterruptedException
    {
        return getStringObjVar(player, SPACE_TRACK_FLAG);
    }
    public static void clearSpaceTrack(obj_id player) throws InterruptedException
    {
        String trackName = getSpaceTrack(player);
        if (trackName == null)
        {
            return;
        }
        removeObjVar(player, trackName);
        removeObjVar(player, SPACE_TRACK_FLAG);
    }
    public static boolean isSpaceTrack(obj_id player, String spaceTrack) throws InterruptedException
    {
        String currentSpaceTrack = getSpaceTrack(player);
        if (currentSpaceTrack == null && spaceTrack == null)
        {
            return true;
        }
        else if (currentSpaceTrack == null || spaceTrack == null)
        {
            return false;
        }
        else 
        {
            return (spaceTrack.equals(currentSpaceTrack));
        }
    }
    public static void setSpaceFlag(obj_id player, String flagName, int flagValue) throws InterruptedException
    {
        String currentSpaceTrack = getSpaceTrack(player);
        if (currentSpaceTrack == null)
        {
            return;
        }
        setObjVar(player, currentSpaceTrack + ". " + flagName, flagValue);
    }
    public static void setSpaceFlag(obj_id player, String flagName, boolean flagValue) throws InterruptedException
    {
        String currentSpaceTrack = getSpaceTrack(player);
        if (currentSpaceTrack == null)
        {
            return;
        }
        if (flagValue)
        {
            setObjVar(player, currentSpaceTrack + ". " + flagName, 1);
        }
        else 
        {
            setObjVar(player, currentSpaceTrack + ". " + flagName, 0);
        }
    }
    public static boolean hasSpaceFlag(obj_id player, String flagName) throws InterruptedException
    {
        String currentSpaceTrack = getSpaceTrack(player);
        if (currentSpaceTrack == null)
        {
            return false;
        }
        return hasObjVar(player, currentSpaceTrack + ". " + flagName);
    }
    public static int getIntSpaceFlag(obj_id player, String flagName) throws InterruptedException
    {
        String currentSpaceTrack = getSpaceTrack(player);
        if (currentSpaceTrack == null)
        {
            return 0;
        }
        return getIntObjVar(player, currentSpaceTrack + ". " + flagName);
    }
    public static boolean getBooleanSpaceFlag(obj_id player, String flagName) throws InterruptedException
    {
        String currentSpaceTrack = getSpaceTrack(player);
        if (currentSpaceTrack == null)
        {
            return false;
        }
        return (getIntObjVar(player, currentSpaceTrack + ". " + flagName) == 1);
    }
    public static void removeSpaceFlag(obj_id player, String flagName) throws InterruptedException
    {
        String currentSpaceTrack = getSpaceTrack(player);
        if (currentSpaceTrack == null)
        {
            return;
        }
        removeObjVar(player, currentSpaceTrack + ". " + flagName);
    }
    public static boolean isRebelPilot(obj_id player) throws InterruptedException
    {
        return hasSkill(player, "pilot_rebel_navy_novice");
    }
    public static boolean isImperialPilot(obj_id player) throws InterruptedException
    {
        return hasSkill(player, "pilot_imperial_navy_novice");
    }
    public static boolean isNeutralPilot(obj_id player) throws InterruptedException
    {
        return hasSkill(player, "pilot_neutral_novice");
    }
    public static boolean hasAnyPilotSkill(obj_id player) throws InterruptedException
    {
        return (isRebelPilot(player) || isImperialPilot(player) || isNeutralPilot(player));
    }
    public static String getProfessionPrefix(obj_id player) throws InterruptedException
    {
        if (isRebelPilot(player))
        {
            return REBEL_PILOT_PROFESSION;
        }
        else if (isImperialPilot(player))
        {
            return IMPERIAL_PILOT_PROFESSION;
        }
        else if (isNeutralPilot(player))
        {
            return NEUTRAL_PILOT_PROFESSION;
        }
        return null;
    }
    public static boolean isRebelHelperPilot(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) && !exists(player))
        {
            return false;
        }
        obj_id ship = space_transition.getContainingShip(player);
        if (hasObjVar(ship, "spaceFaction.FactionOverride") && isNeutralPilot(player))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (370444368))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isImperialHelperPilot(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) && !exists(player))
        {
            return false;
        }
        obj_id ship = space_transition.getContainingShip(player);
        if (hasObjVar(ship, "spaceFaction.FactionOverride"))
        {
            int faction = getIntObjVar(ship, "spaceFaction.FactionOverride");
            if (faction == (-615855020))
            {
                return true;
            }
        }
        return false;
    }
    public static final String REBEL_PILOT_PROFESSION = "pilot_rebel_navy";
    public static final String IMPERIAL_PILOT_PROFESSION = "pilot_imperial_navy";
    public static final String NEUTRAL_PILOT_PROFESSION = "pilot_neutral";
    public static final int TIER1_INDEXSTART = 0;
    public static final int TIER1_INDEXSTOP = 3;
    public static final int TIER2_INDEXSTART = 4;
    public static final int TIER2_INDEXSTOP = 7;
    public static final int TIER3_INDEXSTART = 8;
    public static final int TIER3_INDEXSTOP = 11;
    public static final int TIER4_INDEXSTART = 12;
    public static final int TIER4_INDEXSTOP = 15;
    public static final String[] SKILL_NAMES = 
    {
        "_starships_01",
        "_weapons_01",
        "_procedures_01",
        "_droid_01",
        "_starships_02",
        "_weapons_02",
        "_procedures_02",
        "_droid_02",
        "_starships_03",
        "_weapons_03",
        "_procedures_03",
        "_droid_03",
        "_starships_04",
        "_weapons_04",
        "_procedures_04",
        "_droid_04"
    };
    public static boolean isInTierOne(obj_id player) throws InterruptedException
    {
        String professionPrefix = getProfessionPrefix(player);
        if (professionPrefix == null)
        {
            return false;
        }
        return (!hasCompletedTierOne(player));
    }
    public static boolean isInTierTwo(obj_id player) throws InterruptedException
    {
        if (!hasCompletedTierOne(player))
        {
            return false;
        }
        return (!hasCompletedTierTwo(player));
    }
    public static boolean isInTierThree(obj_id player) throws InterruptedException
    {
        if (!hasCompletedTierTwo(player))
        {
            return false;
        }
        return (!hasCompletedTierThree(player));
    }
    public static boolean isInTierFour(obj_id player) throws InterruptedException
    {
        if (!hasCompletedTierThree(player))
        {
            return false;
        }
        return (!hasCompletedTierFour(player));
    }
    public static int getPilotTier(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return 0;
        }
        if (isInTierOne(player))
        {
            return 1;
        }
        if (isInTierTwo(player))
        {
            return 2;
        }
        if (isInTierThree(player))
        {
            return 3;
        }
        if (isInTierFour(player))
        {
            return 4;
        }
        if (hasCompletedTierFour(player))
        {
            return 5;
        }
        return 0;
    }
    public static boolean hasOneTierOneSkill(obj_id player) throws InterruptedException
    {
        String professionPrefix = getProfessionPrefix(player);
        if (professionPrefix == null)
        {
            return false;
        }
        for (int i = TIER1_INDEXSTART; i <= TIER1_INDEXSTOP; ++i)
        {
            if (hasSkill(player, professionPrefix + SKILL_NAMES[i]))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean hasCompletedTierOne(obj_id player) throws InterruptedException
    {
        String professionPrefix = getProfessionPrefix(player);
        if (professionPrefix == null)
        {
            return false;
        }
        for (int i = TIER1_INDEXSTART; i <= TIER1_INDEXSTOP; ++i)
        {
            if (!hasSkill(player, professionPrefix + SKILL_NAMES[i]))
            {
                return false;
            }
        }
        return true;
    }
    public static boolean hasCompletedTierTwo(obj_id player) throws InterruptedException
    {
        String professionPrefix = getProfessionPrefix(player);
        if (professionPrefix == null)
        {
            return false;
        }
        for (int i = TIER2_INDEXSTART; i <= TIER2_INDEXSTOP; ++i)
        {
            if (!hasSkill(player, professionPrefix + SKILL_NAMES[i]))
            {
                return false;
            }
        }
        return true;
    }
    public static boolean hasCompletedTierThree(obj_id player) throws InterruptedException
    {
        String professionPrefix = getProfessionPrefix(player);
        if (professionPrefix == null)
        {
            return false;
        }
        for (int i = TIER3_INDEXSTART; i <= TIER3_INDEXSTOP; ++i)
        {
            if (!hasSkill(player, professionPrefix + SKILL_NAMES[i]))
            {
                return false;
            }
        }
        return true;
    }
    public static boolean hasCompletedTierFour(obj_id player) throws InterruptedException
    {
        String professionPrefix = getProfessionPrefix(player);
        if (professionPrefix == null)
        {
            return false;
        }
        for (int i = TIER4_INDEXSTART; i <= TIER4_INDEXSTOP; ++i)
        {
            if (!hasSkill(player, professionPrefix + SKILL_NAMES[i]))
            {
                return false;
            }
        }
        return true;
    }
    public static void restoreClientPath(obj_id player) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "jtlNewbieStartLoc"))
        {
            return;
        }
        location destination = utils.getLocationScriptVar(player, "jtlNewbieStartLoc");
        location origin = utils.getLocationScriptVar(player, "jtlNewbieTrainerLoc");
        destroyClientPath(player);
        createClientPath(player, origin, destination);
    }
    public static void clearClientPath(obj_id player) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "hasClientPath"))
        {
            destroyClientPath(player);
        }
    }
}
