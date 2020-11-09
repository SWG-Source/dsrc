package script.fishing;

import script.dictionary;
import script.library.fishing;
import script.obj_id;

public class master_object extends script.base_script {

    public master_object()
    {
    }

    public static final String OBJVAR_LEADERBOARD_UPDATE_TIME = "leaderboards_last_updated";
    public static final String OBJVAR_CONTROLLER_SETUP_TIME = "master_fishing_object_created";

    public int OnAttach(obj_id self) throws InterruptedException {
        messageTo(self, "handleControllerInitialization", null, 120f, true);
        return SCRIPT_CONTINUE;
    }

    public int OnInitialize(obj_id self) throws InterruptedException {
        messageTo(self, "handleControllerInitialization", null, 120f, true);
        return SCRIPT_CONTINUE;
    }

    // Make sure CSRs can't take the master object on a stroll down Amidala's Beach when they should be home doing their homework
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException {
        sendSystemMessageTestingOnly(transferer, "You cannot move this item!");
        return SCRIPT_OVERRIDE;
    }

    /**
     * handleControllerInitialization
     * Handles the setup of the controller and triggers updates to critical functions
     */
    public int handleControllerInitialization(obj_id self, dictionary params) throws InterruptedException {

        setName(self, "Master Fishing Object");
        if(!hasObjVar(self, OBJVAR_CONTROLLER_SETUP_TIME)) {
            messageTo(self, "handleSetupMasterFishingObject", null, 20f, true);
        }
        // Update the leader board weekly, and force update if 7-days have passed since last update (in case of offline fails to trigger update)
        createWeeklyAlarmClock(self, "handleUpdateLeaderboards", null, DAY_OF_WEEK_THU, 19, 0,0);
        if (!hasObjVar(self, OBJVAR_LEADERBOARD_UPDATE_TIME)) {
            messageTo(self, "handleWeeklyUpdateLeaderboards", null, 20f, true);
        }
        if(getCalendarTime() > getIntObjVar(self, OBJVAR_LEADERBOARD_UPDATE_TIME) + 604800) {
            messageTo(self, "handleWeeklyUpdateLeaderboards", null, 20f, true);
        }
        return SCRIPT_CONTINUE;
    }

    /**
     * handleSetupMasterFishingObject
     * Handles all required setup for the Fishing Object, intended to only be called on a fresh server once
     */
    public int handleSetupMasterFishingObject(obj_id self, dictionary params) throws InterruptedException {
        setObjVar(getPlanetByName("tatooine"), fishing.OBJVAR_PLANET_OBJECT_REFERENCE, self);
        setObjVar(self, OBJVAR_CONTROLLER_SETUP_TIME, getCalendarTime());
        String[] elusiveFishTable = dataTableGetStringColumn(fishing.ELUSIVE_FISH_TABLE, "fish");
        for (String fish : elusiveFishTable) {
            setObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fish, 0);
        }
        setObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_COUNT_TOTAL, 0);
        setObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_COUNT_TABLE_MADE, 1);
        LOG("fishing", "Created Master Fishing Object ("+ self +") and completed setupMasterFishingObject.");
        return SCRIPT_CONTINUE;
    }

    /**
     * handleUpdateElusiveFishLeaderboard
     * Adds an array of information to the elusive fish leaderboard as passed in fishing.giveElusiveFishRewards
     */
    public int handleUpdateElusiveFishLeaderboard(obj_id self, dictionary params) throws InterruptedException {
        String[] fishInfo = params.getStringArray("elusiveFishLeaderboardItems");
        String fishType = params.getString("fishType");
        // update total elusive fish caught count
        setObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_COUNT_TOTAL, getIntObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_COUNT_TOTAL)+1);
        // update specific elusive fish caught count
        setObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fishType, getIntObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_COUNT_TABLE_TREE+fishType)+1);
        // add leaderboard entry
        if(!hasObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL)) {
            setObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL, 1);
            setObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_LEADERBOARD_TREE+"0", fishInfo);
        } else {
            int leaderboardEntries = getIntObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL);
            setObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_LEADERBOARD_TREE+leaderboardEntries, fishInfo);
            setObjVar(self, fishing.OBJVAR_ELUSIVE_FISH_LEADERBOARD_COUNT_TOTAL, ++leaderboardEntries);
        }
        return SCRIPT_CONTINUE;
    }

    /**
     * handleWeeklyUpdateLeaderboards
     * Called when it is time to update all of the fishing leaderboards (does not include elusive fish as that is lifetime per galaxy)
     */
    public int handleWeeklyUpdateLeaderboards(obj_id self, dictionary params) throws InterruptedException {
        // placeholder for weekly updated leaderboards
        setObjVar(self, OBJVAR_LEADERBOARD_UPDATE_TIME, getCalendarTime());
        return SCRIPT_CONTINUE;
    }


}
