package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.dictionary;
import script.library.buff;
import script.library.instance;
import script.library.pclib;
import script.library.trial;
import script.obj_id;
import script.string_id;

public class obiwan_player extends script.base_script
{
    public obiwan_player()
    {
    }
    public static final String STF = "dungeon/kash_the_arena";
    public static final string_id SID_DEATH_EJECT = new string_id(STF, "ejected_by_death");
    public static final boolean CONST_FLAG_DO_LOGGING = true;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugLogging("//***// OnAttach: ", "////>>>> entered.");
        messageTo(self, "setUpDungeon", null, 3, false);
        playMusic(self, "sound/mus_mustafar_dark_jedi_master_quest.snd");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (buff.hasBuff(self, "crystal_buff"))
        {
            buff.removeBuff(self, "crystal_buff");
        }
        String scene = getCurrentSceneName();
        if (!scene.equals("mustafar"))
        {
            detachScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_player");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        CustomerServiceLog("DUNGEON_ObiWanFinale", "*ObiwanFinale %TU died in theMustafar ObiwanFinale event, ejecting them from dungeon..", self);
        sendSystemMessage(self, SID_DEATH_EJECT);
        messageTo(self, "handleDeathFailure", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_player/" + section, message);
        }
    }
    public int setUpDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id instancedDungeon = trial.getTop(self);
        if (hasScript(instancedDungeon, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_instance_spawned"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(instancedDungeon, "beginSpawn", null, 1, false);
            attachScript(instancedDungeon, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_instance_spawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDeathFailure(obj_id self, dictionary params) throws InterruptedException
    {
        buff.removeAllBuffs(self);
        pclib.resurrectPlayer(self, true);
        obj_id instancedDungeon = trial.getTop(self);
        messageTo(instancedDungeon, "cleanupScriptVars", null, 1, false);
        instance.requestExitPlayer("obiwan_crystal_cave", self);
        attachScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_player_reattempt_delay");
        detachScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_player");
        return SCRIPT_CONTINUE;
    }
}
