package script.library;

import java.util.Calendar;

public class events extends script.base_script {

    public events()
    {
    }

    public static final String EMPIRE_DAY = "empireday";
    public static final String LIFEDAY = "lifeday";
    public static final String EWOK_FESTIVAL_OF_LOVE = "loveday";
    public static final String GALACTIC_MOON_FESTIVAL = "halloween";
    public static final String FOOLS_DAY = "foolsday";
    public static final String LOST_SQUADRON = "lostsquadron";
    public static final String ANNIVERSARY = "anniversary";

    private static final boolean EMPIRE_DAY_CONIFG = utils.checkConfigFlag("GameServer", "empireday_ceremony");
    private static final boolean LIFEDAY_CONFIG = utils.checkConfigFlag("GameServer", "lifeday");
    private static final boolean EWOK_FESTIVAL_CONFIG = utils.checkConfigFlag("GameServer", "loveday");
    private static final boolean GALACTIC_MOON_CONFIG = utils.checkConfigFlag("GameServer", "halloween");
    private static final boolean FOOLS_DAY_CONFIG = utils.checkConfigFlag("GameServer", "foolsDay");
    private static final boolean FORCE_FOOLS_DAY_CONFIG = utils.checkConfigFlag("GameServer", "forceFoolsDay");
    private static final boolean LOST_SQUADRON_CONFIG = utils.checkConfigFlag("EventTeam", "lostSquadron");
    private static final boolean ANNIVERSARY_CONFIG = utils.checkConfigFlag("EventTeam", "anniversary");


    /**
     * Used to determine if an event is currently configured to run
     * @param eventName the name of the event (uses constants above)
     * @return true if it is active, false otherwise
     */
    public static boolean isEventActive(String eventName) {

        switch (eventName) {

            case EMPIRE_DAY:
                return EMPIRE_DAY_CONIFG;

            case EWOK_FESTIVAL_OF_LOVE:
                return EWOK_FESTIVAL_CONFIG;

            case GALACTIC_MOON_FESTIVAL:
                return GALACTIC_MOON_CONFIG;

            case LIFEDAY:
                return LIFEDAY_CONFIG;

            case FOOLS_DAY:
                return FOOLS_DAY_CONFIG;

            case LOST_SQUADRON:
                return LOST_SQUADRON_CONFIG;

            case ANNIVERSARY:
                return ANNIVERSARY_CONFIG;

            default:
                WARNING("events.isEventActive() was called for event named "+eventName+" but no handling for that name is in the method so either the event name is wrong or handling here is missing.");
                break;
        }
        return false;
    }

    /**
     * Used to determine if an event is currently running, meaning,
     * not that it is just active (configured to be on) but that
     * certain other requisites have triggered/been met to make the
     * sequences of the event start
     * @param eventName the name of the event (uses constants above)
     * @return true if it is running, false otherwise
     */
    public static boolean isEventRunning(String eventName) {

        switch (eventName) {

            case FOOLS_DAY:
                if(!isEventActive(FOOLS_DAY)) {
                    return false;
                } else {
                    if(FORCE_FOOLS_DAY_CONFIG) {
                        return true;
                    } else { // if fools day isn't forced, only start it on literally April 1 from 12AM to 11:59PM
                        Calendar cal = Calendar.getInstance();
                        int currentTime = getCalendarTime();
                        int midnightOnAprilFoolsDay = getCalendarTime(cal.get(Calendar.YEAR), 4, 1, 0, 0, 0);
                        int midnightOnDayAfterAprilFools = getCalendarTime(cal.get(Calendar.YEAR), 4, 2, 0, 0, 0);
                        return currentTime >= midnightOnAprilFoolsDay && currentTime <= midnightOnDayAfterAprilFools;
                    }
                }

            default:
                WARNING("events.isEventRunning() was called for event named "+eventName+" but no handling for that name is in the method so either the event name is wrong or handling here is missing.");
                break;

        }
        return false;
    }

}
