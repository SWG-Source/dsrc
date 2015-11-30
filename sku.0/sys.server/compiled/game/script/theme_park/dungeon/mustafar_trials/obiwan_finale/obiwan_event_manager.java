package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.mustafar;
import script.library.prose;
import script.library.static_item;
import script.library.trial;
import script.library.utils;
import script.library.badge;
import script.library.space_dungeon;

public class obiwan_event_manager extends script.base_script
{
    public obiwan_event_manager()
    {
    }
    public static final String STF_OBI_CONVO = "conversation/som_kenobi_obi_wan";
    public static final string_id SID_LAUNCH = new string_id(mustafar.STF_OBI_MSGS, "obiwan_finale_launch");
    public static final string_id SID_EJECT = new string_id(mustafar.STF_OBI_MSGS, "obiwan_finale_eject");
    public static final string_id SID_LEAVE_YOUR_GROUP = new string_id(mustafar.STF_OBI_MSGS, "obi_leave_your_group");
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (isGod(speaker))
        {
            if (text.equals("reset"))
            {
                clearEventArea(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_event_manager/" + section, message);
        }
    }
    public void clearEventArea(obj_id dungeon) throws InterruptedException
    {
        obj_id cell = getCellId(dungeon, "mainroom");
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            debugLogging("clearEventArea", "Dungeon was empty, return");
            return;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (isPlayer(contents[i]))
            {
                debugLogging("clearEventArea", "Validating player(" + getName(contents[i]) + "/" + contents[i] + ") from event area");
            }
            else 
            {
                debugLogging("clearEventArea", "Destroying object(" + getName(contents[i]) + "/" + contents[i] + ")");
                trial.cleanupNpc(contents[i]);
            }
        }
        cleanupDungeonScriptvars();
    }
    public void eventWon() throws InterruptedException
    {
        obj_id self = getSelf();
        int newEndTime = getGameTime() + 120;
        setObjVar(self, space_dungeon.VAR_DUNGEON_END_TIME, newEndTime);
        debugLogging("darkJediEventWon", "Event won triggered");
        obj_id player = utils.getObjIdScriptVar(self, "player");
        setObjVar(player, "didMustafarCrystalLair", 1);
        if (hasScript(player, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_quest_monitor"))
        {
            detachScript(player, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_quest_monitor");
        }
        if (hasObjVar(player, "sawObiwanAtLauncher"))
        {
            removeObjVar(player, "sawObiwanAtLauncher");
        }
        return;
    }
    public void cleanupDungeonScriptvars() throws InterruptedException
    {
        obj_id self = getSelf();
        if (utils.hasScriptVar(self, "minions"))
        {
            utils.removeScriptVar(self, "minions");
        }
        if (utils.hasScriptVar(self, "obiLocation"))
        {
            utils.removeScriptVar(self, "obiLocation");
        }
        if (utils.hasScriptVar(self, "crystal"))
        {
            utils.removeScriptVar(self, "crystal");
        }
        if (utils.hasScriptVar(self, "obiwan"))
        {
            utils.removeScriptVar(self, "obiwan");
        }
        if (utils.hasScriptVar(self, "player"))
        {
            utils.removeScriptVar(self, "player");
        }
        if (utils.hasScriptVar(self, "darkJedi"))
        {
            utils.removeScriptVar(self, "darkJedi");
        }
    }
    public int dungeonCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public int cleanupSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        return SCRIPT_CONTINUE;
    }
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        mustafar.spawnContents(self, "setpiece", 1);
        obj_id player = utils.getObjIdScriptVar(self, "player");
        return SCRIPT_CONTINUE;
    }
    public int obiwanSpeechDelay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_crystal_speech1");
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        messageTo(self, "obiwanSpeechContinued", params, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int obiwanSpeechContinued(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_crystal_speech2");
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        debugLogging("//***// obiwanSpeechContinued: ", "////>>>> calling mustafar.spawnContents for 'boss'.");
        messageTo(self, "spawnBossDelay", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int lightsCameraAction(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        obj_id darkJedi = utils.getObjIdScriptVar(self, "darkJedi");
        int intermissionNum = utils.getIntScriptVar(self, "intermission");
        if (utils.hasScriptVar(player, "readyToUseCrystal"))
        {
            utils.removeScriptVar(player, "readyToUseCrystal");
        }
        if (utils.hasScriptVar(player, "dealWithCrystal"))
        {
            utils.removeScriptVar(player, "dealWithCrystal");
        }
        debugLogging(" lightsCameraAction: ", "////>>>> entered 'intermission' lightsCameraAction with intermissionNum of: " + intermissionNum);
        debugLogging(" lightsCameraAction: ", "////>>>> player objId is: " + player + " obiwan objId is: " + obiwan + " and darkjedi objid is: " + darkJedi);
        chat.setChatMood(darkJedi, chat.MOOD_MALEVOLENT);
        chat.setChatType(darkJedi, chat.CHAT_HOWL);
        if (intermissionNum == 0)
        {
            debugLogging(" lightsCameraAction: ", "////>>>> Doing IntermissionNumber -0-");
            playClientEffectObj(player, "clienteffect/mustafar/som_dark_jedi_laugh.cef", player, "");
            doAnimationAction(darkJedi, "threaten");
            string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_dark_jedi_crystal_speech1");
            prose_package pp = prose.getPackage(strSpam);
            pp.target.set(player);
            chat.chat(darkJedi, player, pp);
            messageTo(self, "darkJediThrowsDownPartOne", params, 10, false);
        }
        else if (intermissionNum == 1)
        {
            debugLogging(" lightsCameraAction: ", "////>>>> Doing IntermissionNumber -1-");
            doAnimationAction(darkJedi, "point_forward");
            string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_dark_jedi_cannot_defeat_me");
            prose_package pp = prose.getPackage(strSpam);
            prose.setTT(pp, player);
            chat.chat(darkJedi, player, pp);
            messageTo(self, "obiSaysBeCareful", null, 3, false);
            messageTo(self, "moveBossToHomeLoc", null, 10, false);
            dictionary waveParams = new dictionary();
            waveParams.put("waveNum", 2);
            messageTo(self, "minionWaveLaunch", waveParams, 6, false);
        }
        else if (intermissionNum == 2)
        {
            debugLogging(" lightsCameraAction: ", "////>>>> Doing IntermissionNumber -2-");
            string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_dark_jedi_destroy_you_myself");
            prose_package pp = prose.getPackage(strSpam);
            pp.target.set(player);
            chat.chat(darkJedi, player, pp);
            utils.setScriptVar(player, "readyToUseCrystal", 1);
            messageTo(self, "moveObiwanForCrystalComment", null, 1, false);
            messageTo(self, "darkJediThrowsDownPartTwo", null, 17, false);
            messageTo(darkJedi, "startFighting", null, 23, false);
        }
        else if (intermissionNum == 3)
        {
            debugLogging(" lightsCameraAction: ", "////>>>> Doing IntermissionNumber -3-");
            messageTo(self, "moveBossToHomeLoc", null, 1, false);
            dictionary waveParams = new dictionary();
            waveParams.put("waveNum", 3);
            messageTo(self, "minionWaveLaunch", waveParams, 10, false);
        }
        else if (intermissionNum == 4)
        {
            debugLogging(" lightsCameraAction: ", "////>>>> Doing IntermissionNumber -4-");
            chat.setChatMood(darkJedi, chat.MOOD_EXASPERATED);
            string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_dark_jedi_nooo");
            prose_package pp = prose.getPackage(strSpam);
            pp.target.set(player);
            chat.chat(darkJedi, player, pp);
            messageTo(darkJedi, "startFighting", null, 10, false);
        }
        else if (intermissionNum == 5)
        {
            debugLogging(" lightsCameraAction: ", "////>>>> Doing IntermissionNumber -5-");
            if (utils.hasScriptVar(player, "readyToUseCrystal"))
            {
                utils.removeScriptVar(player, "readyToUseCrystal");
            }
            utils.setScriptVar(player, "dealWithCrystal", 1);
            buff.removeAllBuffs(player);
            faceTo(obiwan, player);
            string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_won_congrats");
            prose_package pp = prose.getPackage(strSpam);
            prose.setTT(pp, player);
            chat.chat(obiwan, player, pp);
            messageTo(self, "obiSaysDestroyCrystal", null, 3, false);
        }
        else if (intermissionNum > 5)
        {
            debugLogging(" lightsCameraAction: ", "////>>>> ERROR. RECEIVED AN INTERMISSION VALUE GREATER THAN  -5-");
            return SCRIPT_CONTINUE;
        }
        intermissionNum++;
        utils.setScriptVar(self, "intermission", intermissionNum);
        return SCRIPT_CONTINUE;
    }
    public int spawnBossDelay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id darkJedi = utils.getObjIdScriptVar(self, "darkJedi");
        debugLogging("//***// spawnBossDelay: ", "////>>>> player objid: " + player + " and darkJedi objid: " + darkJedi);
        mustafar.spawnContents(self, "boss", 1);
        return SCRIPT_CONTINUE;
    }
    public int darkJediThrowsDownPartOne(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id darkJedi = utils.getObjIdScriptVar(self, "darkJedi");
        debugLogging("//***// darkJediThrowsDownPartOne: ", "////>>>> player objid: " + player + " and darkJedi objid: " + darkJedi);
        dictionary waveParams = new dictionary();
        waveParams.put("waveNum", 1);
        messageTo(self, "moveBossToHomeLoc", null, 14, false);
        messageTo(self, "minionWaveLaunch", waveParams, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int obiSaysBeCareful(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_be_careful");
        prose_package pp = prose.getPackage(strSpam);
        pp.target.set(player);
        chat.chat(obiwan, player, pp);
        return SCRIPT_CONTINUE;
    }
    public int obiSaysBeCareful2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_be_careful2");
        prose_package pp = prose.getPackage(strSpam);
        pp.target.set(player);
        chat.chat(obiwan, player, pp);
        messageTo(self, "moveObiwanHomeAfterCommenting", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int obiwanWarnsOfSpecialAttack(obj_id self, dictionary params) throws InterruptedException
    {
        debugLogging("//***// obiwanWarnsOfSpecialAttack: ", "////>>>> -------------------------------	ENTERED");
        if (!utils.hasScriptVar(self, "darkJedi"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        if (!utils.hasScriptVar(self, "darkJedi"))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_lookout_special");
        prose_package pp = prose.getPackage(strSpam);
        pp.target.set(player);
        chat.chat(obiwan, player, pp);
        return SCRIPT_CONTINUE;
    }
    public int obiwanCongratsSpecialAttackBlock(obj_id self, dictionary params) throws InterruptedException
    {
        debugLogging("//***// obiwanCongratsSpecialAttackBlock: ", "////>>>> -------------------------------	ENTERED");
        if (!utils.hasScriptVar(self, "darkJedi"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_block_special");
        prose_package pp = prose.getPackage(strSpam);
        pp.target.set(player);
        chat.chat(obiwan, player, pp);
        return SCRIPT_CONTINUE;
    }
    public int darkJediThrowsDownPartTwo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id darkJedi = utils.getObjIdScriptVar(self, "darkJedi");
        setObjVar(darkJedi, "randomTaunt", 1);
        faceTo(darkJedi, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_dark_jedi_snap_you_half");
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(darkJedi, player, pp);
        return SCRIPT_CONTINUE;
    }
    public int minionWaveLaunch(obj_id self, dictionary params) throws InterruptedException
    {
        debugLogging("minionWaveLaunch", "entered");
        int waveNumber = params.getInt("waveNum");
        if (waveNumber < 0)
        {
            debugLogging("minionWaveLaunch", "waveNumber was less than zero");
            debugLogging("//***// minionWaveLaunch: ", "////>>>> messaging ------------------------------- 'lightsCameraAction.'");
            messageTo(self, "lightsCameraAction", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        debugLogging("minionWaveLaunch", "waveNumber is: " + waveNumber);
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id darkJedi = utils.getObjIdScriptVar(self, "darkJedi");
        faceTo(darkJedi, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_dark_jedi_attack_minions_" + waveNumber);
        prose_package pp = prose.getPackage(strSpam);
        pp.target.set(player);
        chat.chat(darkJedi, player, pp);
        if (utils.hasScriptVar(self, "minions"))
        {
            utils.removeScriptVar(self, "minions");
        }
        switch (waveNumber)
        {
            case 1:
            
            {
                mustafar.spawnContents(self, "minionA", 1);
            }
            break;
            case 2:
            
            {
                mustafar.spawnContents(self, "minionA", 2);
                mustafar.spawnContents(self, "minionB", 1);
            }
            break;
            case 3:
            
            {
                mustafar.spawnContents(self, "minionB", 2);
                mustafar.spawnContents(self, "minionB", 3);
            }
            break;
            default:
            
            {
                debugLogging("minionWaveLaunch", "can't figure out wave number. Doing default.");
                debugLogging("//***// minionWaveLaunch: ", "////>>>> messaging ------------------------------- 'lightsCameraAction.'");
                messageTo(self, "lightsCameraAction", null, 1, false);
            }
            break;
        }
        messageTo(self, "moveObiwanOuttaTheWay", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int minionDied(obj_id self, dictionary params) throws InterruptedException
    {
        debugLogging("minionDied", "entered");
        if (!utils.hasScriptVar(self, "minions"))
        {
            debugLogging("minionDied", "////>>>> No minions left. Messaging  'lightsCameraAction.'");
            messageTo(self, "moveBossToPostureLoc", null, 1, false);
            messageTo(self, "lightsCameraAction", null, 8, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int obiScoldsPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        obj_id crystal = utils.getObjIdScriptVar(self, "crystal");
        faceTo(obiwan, crystal);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_disappointed_cont");
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        doAnimationAction(obiwan, "point_forward");
        String effect = "clienteffect/mustafar/som_force_crystal_drain.cef";
        playClientEffectObj(player, effect, crystal, "");
        messageTo(self, "playerGetsCrystal", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int playerGetsCrystal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id crystal = utils.getObjIdScriptVar(self, "crystal");
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        faceTo(obiwan, player);
        doAnimationAction(obiwan, "catchbreath");
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_take_now");
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        obj_id generatedItem = static_item.createNewItemFunction("item_tow_cystal_buff_drained_05_01", player);
        String effect = "clienteffect/pl_force_healing.cef";
        playClientEffectObj(player, effect, crystal, "");
        badge.grantBadge(player, "bdg_must_obiwan_story_bad");
        destroyObject(crystal);
        messageTo(self, "obiLeaves", null, 15, false);
        return SCRIPT_CONTINUE;
    }
    public int obiCongratulatesPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        obj_id crystal = utils.getObjIdScriptVar(self, "crystal");
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_congratulates_player");
        doAnimationAction(obiwan, "nod");
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        obj_id generatedItem = static_item.createNewItemFunction("item_tow_crystal_uber_05_02", player);
        badge.grantBadge(player, "bdg_must_obiwan_story_good");
        messageTo(self, "obiLeaves", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int obiLeaves(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_may_force_you");
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        setCreatureCover(self, 125);
        setCreatureCoverVisibility(self, false);
        playClientEffectObj(player, "clienteffect/combat_special_attacker_cover.cef", obiwan, "");
        destroyObject(obiwan);
        eventWon();
        return SCRIPT_CONTINUE;
    }
    public int moveBossToPostureLoc(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id darkJedi = utils.getObjIdScriptVar(self, "darkJedi");
        obj_id destination = getCellId(self, "mainroom");
        setMovementRun(darkJedi);
        location here = getLocation(self);
        String planet = here.area;
        debugLogging("moveThatBossReally: ", ">>>>  planet: " + planet + " destination: " + destination);
        location home = new location(53f, 0f, 5f, planet, destination);
        ai_lib.aiPathTo(darkJedi, home);
        setHomeLocation(darkJedi, home);
        return SCRIPT_CONTINUE;
    }
    public int moveBossToHomeLoc(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id darkJedi = utils.getObjIdScriptVar(self, "darkJedi");
        obj_id destination = getCellId(self, "mainroom");
        setMovementRun(darkJedi);
        location here = getLocation(self);
        String planet = here.area;
        debugLogging("moveThatBossReally: ", ">>>>  planet: " + planet + " destination: " + destination);
        location home = new location(31, 0f, 6f, planet, destination);
        ai_lib.aiPathTo(darkJedi, home);
        setHomeLocation(darkJedi, home);
        return SCRIPT_CONTINUE;
    }
    public int obiSaysDestroyCrystal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_destroy_crystal");
        prose_package pp = prose.getPackage(strSpam);
        prose.setTT(pp, player);
        chat.chat(obiwan, player, pp);
        playMusic(player, "sound/mus_mustafar_obi_wan_quest.snd");
        messageTo(self, "obiRepeatsDestroyCrystal", null, rand(20, 40), false);
        return SCRIPT_CONTINUE;
    }
    public int obiRepeatsDestroyCrystal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        if (!isIdValid(obiwan))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, "player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(player, "dealWithCrystal"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!mustafar.stillWithinDungeonCheck(player, self))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(obiwan, player);
        string_id strSpam = new string_id(mustafar.STF_OBI_MSGS, "som_obi_destroy_crystal_short");
        prose_package pp = prose.getPackage(strSpam);
        pp.target.set(player);
        chat.chat(obiwan, player, pp);
        messageTo(self, "obiRepeatsDestroyCrystal", null, rand(20, 40), false);
        return SCRIPT_CONTINUE;
    }
    public int moveMinionIntoRoom(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id minion = params.getObjId("minion");
        obj_id destination = getCellId(self, "mainroom");
        setMovementRun(minion);
        location here = getLocation(self);
        String planet = here.area;
        debugLogging("moveMinionIntoRoom: ", ">>>>  planet: " + planet + " destination: " + destination);
        location home = new location(55, 0f, 6f, planet, destination);
        location newDestination = utils.getRandomAwayLocation(home, 1f, 4f);
        ai_lib.aiPathTo(minion, newDestination);
        setHomeLocation(minion, newDestination);
        return SCRIPT_CONTINUE;
    }
    public int moveObiwanToPostureLocation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        obj_id destination = getCellId(self, "mainroom");
        setMovementRun(obiwan);
        location here = getLocation(self);
        String planet = here.area;
        debugLogging("moveObiwanToPostureLocation: ", ">>>>  planet: " + planet + " destination: " + destination);
        location home = new location(53.8f, -0.4f, 5.9f, planet, destination);
        ai_lib.aiPathTo(obiwan, home);
        setHomeLocation(obiwan, home);
        messageTo(obiwan, "obiwanFacePlayer", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int moveObiwanOuttaTheWay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        obj_id destination = getCellId(self, "mainroom");
        setMovementRun(obiwan);
        location here = getLocation(self);
        String planet = here.area;
        debugLogging("moveObiwanOuttaTheWay: ", ">>>>  planet: " + planet + " destination: " + destination);
        location home = new location(48f, 0f, 9f, planet, destination);
        ai_lib.aiPathTo(obiwan, home);
        setHomeLocation(obiwan, home);
        messageTo(obiwan, "obiwanFacePlayer", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int moveObiwanForCrystalComment(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        obj_id destination = getCellId(self, "mainroom");
        setMovementRun(obiwan);
        location here = getLocation(self);
        String planet = here.area;
        debugLogging("moveObiwanForCrystalComment: ", ">>>>  planet: " + planet + " destination: " + destination);
        location home = new location(53.8f, -0.4f, 5.9f, planet, destination);
        ai_lib.aiPathTo(obiwan, home);
        setHomeLocation(obiwan, home);
        messageTo(self, "obiSaysBeCareful2", null, 4, false);
        messageTo(obiwan, "obiwanFacePlayer", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int moveObiwanHomeAfterCommenting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        obj_id destination = getCellId(self, "mainroom");
        setMovementRun(obiwan);
        location here = getLocation(self);
        String planet = here.area;
        debugLogging("moveObiwanHomeAfterCommenting: ", ">>>>  planet: " + planet + " destination: " + destination);
        location home = utils.getLocationScriptVar(self, "obiLocation");
        ai_lib.aiPathTo(obiwan, home);
        setHomeLocation(obiwan, home);
        messageTo(obiwan, "obiwanFacePlayer", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanupScriptVars(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupDungeonScriptvars();
        return SCRIPT_CONTINUE;
    }
    public int moveObiwanToCrystalSuckLocation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id obiwan = utils.getObjIdScriptVar(self, "obiwan");
        obj_id destination = getCellId(self, "mainroom");
        setMovementRun(obiwan);
        location here = getLocation(self);
        String planet = here.area;
        debugLogging("moveObiwanToCrystalSuckLocation: ", ">>>>  planet: " + planet + " destination: " + destination);
        location home = new location(60.5f, 0.1f, 15.7f, planet, destination);
        ai_lib.aiPathTo(obiwan, home);
        setHomeLocation(obiwan, home);
        messageTo(self, "obiScoldsPlayer", null, 5, false);
        messageTo(obiwan, "obiwanFaceCrystal", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int blowUpCrystal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id crystal = utils.getObjIdScriptVar(self, "crystal");
        destroyObject(crystal);
        return SCRIPT_CONTINUE;
    }
}
