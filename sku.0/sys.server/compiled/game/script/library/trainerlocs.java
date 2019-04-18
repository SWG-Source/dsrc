package script.library;

import script.location;
import script.obj_id;
import script.string_id;

public class trainerlocs extends script.base_script
{
    public trainerlocs()
    {
    }
    public static obj_id getTrehlaKeeloLocation(obj_id player, String profession, String track) throws InterruptedException
    {
        location targetLoc = new location();
        targetLoc.area = track;
        String waypName = "Trehla Keelo";
        targetLoc.x = 3484.0f;
        targetLoc.y = 0.0f;
        targetLoc.z = -4808.0f;
        obj_id wayp = createWaypointInDatapad(player, targetLoc);
        String playerArea = getLocation(player).area;
        if (playerArea != null && playerArea.equals(targetLoc.area))
        {
            setWaypointActive(wayp, true);
        }
        setWaypointName(wayp, waypName);
        return wayp;
    }
    public static obj_id getTrainerLocationWaypoint(obj_id player, String profession, String track) throws InterruptedException
    {
        location targetLoc = new location();
        targetLoc.area = track;
        String waypName;
        switch (track) {
            case "naboo":
                switch (profession) {
                    case "imperial":
                        targetLoc.x = 5182.0f;
                        targetLoc.y = -192.0f;
                        targetLoc.z = 6750.0f;
                        waypName = "barn_sinkko";
                        break;
                    case "rebel":
                        targetLoc.x = 4767.0f;
                        targetLoc.y = 4.22f;
                        targetLoc.z = -4812.0f;
                        waypName = "v3_fx";
                        break;
                    default:
                        targetLoc.x = -5495.0f;
                        targetLoc.y = 14.00f;
                        targetLoc.z = 4476.0f;
                        waypName = "dinge";
                        break;
                }
                break;
            case "corellia":
                switch (profession) {
                    case "imperial":
                        targetLoc.x = -2184.0f;
                        targetLoc.y = 20.0f;
                        targetLoc.z = 2273.0f;
                        targetLoc.area = "talus";
                        waypName = "hakassha_sireen";
                        break;
                    case "rebel":
                        targetLoc.x = -5170.0f;
                        targetLoc.y = 21.00f;
                        targetLoc.z = -2295.0f;
                        waypName = "kreezo";
                        break;
                    default:
                        targetLoc.x = -275.0f;
                        targetLoc.y = 28.0f;
                        targetLoc.z = -4695.0f;
                        waypName = "rhea";
                        break;
                }
                break;
            default:
                switch (profession) {
                    case "imperial":
                        targetLoc.x = -1132.0f;
                        targetLoc.y = 13.32f;
                        targetLoc.z = -3542.0f;
                        waypName = "akal_colzet";
                        break;
                    case "rebel":
                        targetLoc.x = -2991.0f;
                        targetLoc.y = 5.0f;
                        targetLoc.z = 2123.0f;
                        waypName = "da_la_socuna";
                        break;
                    default:
                        targetLoc.x = 3381.0f;
                        targetLoc.y = 5.0f;
                        targetLoc.z = -4799.0f;
                        waypName = "dravis";
                        break;
                }
                break;
        }
        obj_id wayp = createWaypointInDatapad(player, targetLoc);
        String playerArea = getLocation(player).area;
        if (playerArea != null && playerArea.equals(targetLoc.area))
        {
            setWaypointActive(wayp, true);
        }
        setWaypointName(wayp, utils.packStringId(new string_id("npc_spawner_n", waypName)));
        return wayp;
    }
}
