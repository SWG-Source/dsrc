package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.dictionary;
import script.library.utils;
import script.obj_id;

public class obiwan_obi_facer extends script.base_script
{
    public obiwan_obi_facer()
    {
    }
    public static final boolean CONST_FLAG_DO_LOGGING = true;
    public int OnArrivedAtLocation(obj_id self, String location) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// OnAttach: ", "////>>>> dungeon obj_id is invalid. BROKEN");
        }
        obj_id player = utils.getObjIdScriptVar(dungeon, "player");
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_event_manager/" + section, message);
        }
    }
    public int obiwanFacePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// obiwanFacePlayer: ", "////>>>> dungeon obj_id is invalid. BROKEN");
        }
        obj_id player = utils.getObjIdScriptVar(dungeon, "player");
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// obiwanFacePlayer: ", "////>>>> player obj_id is invalid. BROKEN");
        }
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int obiwanFaceCrystal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// obiwanFacePlayer: ", "////>>>> dungeon obj_id is invalid. BROKEN");
        }
        obj_id crystal = utils.getObjIdScriptVar(dungeon, "crystal");
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// obiwanFacePlayer: ", "////>>>> crystal obj_id is invalid. BROKEN");
        }
        faceTo(self, crystal);
        return SCRIPT_CONTINUE;
    }
}
