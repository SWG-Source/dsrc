package script.quest.crowd_pleaser;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.create;
import script.library.prose;
import script.library.utils;

public class control_audition extends script.base_script
{
    public control_audition()
    {
    }
    public static final String PERFORMANCE_DATATABLE = "datatables/performance/performance.iff";
    public static final String PLAYER_SCRIPT = "quest.crowd_pleaser.player_performance";
    public static final String STATUS_OBJVAR = "quest.crowd_pleaser.status";
    public static final String AUDITION_OBJVAR = "quest.crowd_pleaser.audition";
    public static final String PLAYER_OBJVAR = AUDITION_OBJVAR + ".player";
    public static final String PROGRESS_OBJVAR = AUDITION_OBJVAR + ".progress";
    public static final String SATISFACTION_OBJVAR = AUDITION_OBJVAR + ".satisfaction";
    public static final String EXPECTATION_OBJVAR = AUDITION_OBJVAR + ".expectation";
    public static final String TYPE_OBJVAR = AUDITION_OBJVAR + ".type";
    public static final String JUDGES_OBJVAR = AUDITION_OBJVAR + ".judges";
    public static final String FURNITURE_OBJVAR = AUDITION_OBJVAR + ".furniture";
    public static final String CONTROL_OBJVAR = AUDITION_OBJVAR + ".control";
    public static final String FAILED_OBJVAR = AUDITION_OBJVAR + ".failed";
    public static final String VOLUME_NAME = "performance_area";
    public static final String MUSIC_COMMAND = "startMusic+";
    public static final String DANCE_COMMAND = "startDance+";
    public static final String[] MUSIC_NAMES = 
    {
        "starwars1",
        "rock",
        "starwars2",
        "folk",
        "starwars3",
        "ceremonial",
        "ballad",
        "waltz",
        "jazz",
        "virtuoso"
    };
    public static final String[] DANCE_NAMES = 
    {
        "basic",
        "rhythmic",
        "basic2",
        "rhythmic2",
        "footloose",
        "formal",
        "footloose2",
        "formal2",
        "popular",
        "poplock",
        "popular2",
        "poplock2",
        "lyrical",
        "exotic",
        "exotic2",
        "lyrical2",
        "exotic3",
        "exotic4"
    };
    public static final string_id TIME_REMAINING = new string_id("quest/crowd_pleaser/system_messages", "audition_time_remaining");
    public static final string_id AUDITION_BEGIN = new string_id("quest/crowd_pleaser/system_messages", "audition_begin");
    public static final string_id AUDITION_STEP_1 = new string_id("quest/crowd_pleaser/system_messages", "audition_step_1");
    public static final string_id AUDITION_STEP_2 = new string_id("quest/crowd_pleaser/system_messages", "audition_step_2");
    public static final string_id AUDITION_STEP_3 = new string_id("quest/crowd_pleaser/system_messages", "audition_step_3");
    public static final string_id AUDITION_STEP_4 = new string_id("quest/crowd_pleaser/system_messages", "audition_step_4");
    public static final string_id AUDITION_STEP_5 = new string_id("quest/crowd_pleaser/system_messages", "audition_step_5");
    public static final string_id AUDITION_STEP_6 = new string_id("quest/crowd_pleaser/system_messages", "audition_step_6");
    public static final string_id FAIL_LEFT_AUDITION_AREA = new string_id("quest/crowd_pleaser/system_messages", "fail_left_audition_area");
    public static final string_id FAIL_NOT_IN_AREA = new string_id("quest/crowd_pleaser/system_messages", "fail_not_in_audition_area");
    public static final string_id FAIL_AUDITION = new string_id("quest/crowd_pleaser/system_messages", "fail_audition");
    public static final string_id SUCCEED_AUDITION = new string_id("quest/crowd_pleaser/system_messages", "succeed_audition");
    public static final float HEARTBEAT = 15.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String type = getStringObjVar(player, TYPE_OBJVAR);
        utils.setScriptVar(self, TYPE_OBJVAR, type);
        spawnJudges(self);
        createTriggerVolume(VOLUME_NAME, 10.0f, true);
        messageTo(self, "handleBeginAudition", null, 30, false);
        sendSystemMessage(player, TIME_REMAINING);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, CONTROL_OBJVAR) || !utils.hasScriptVar(self, PLAYER_OBJVAR))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        if (!isIdValid(player) || !exists(player) || !isIdValid(control) || !exists(control))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (!hasTriggerVolume(self, VOLUME_NAME))
        {
            createTriggerVolume(VOLUME_NAME, 10.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        if (!volumeName.equals(VOLUME_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        if (who != player)
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, FAIL_LEFT_AUDITION_AREA);
        failAudition(self, player);
        messageTo(self, "handleCleanup", null, 20.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        cleanupJudges(self);
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        if (!isIdValid(control))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(control, CONTROL_OBJVAR);
        return SCRIPT_CONTINUE;
    }
    public int handleBeginAudition(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        obj_id[] contents = getTriggerVolumeContents(self, VOLUME_NAME);
        if (contents == null || contents.length == 0)
        {
            sendSystemMessage(player, FAIL_NOT_IN_AREA);
            failAudition(self, player);
            messageTo(self, "handleCleanup", null, 20.0f, false);
            return SCRIPT_CONTINUE;
        }
        boolean plyNotFound = true;
        for (int i = 0; i < contents.length; i++)
        {
            if (contents[i] == player)
            {
                plyNotFound = false;
            }
        }
        if (plyNotFound)
        {
            sendSystemMessage(player, FAIL_NOT_IN_AREA);
            failAudition(self, player);
            messageTo(self, "handleCleanup", null, 20.0f, false);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, AUDITION_BEGIN);
        messageTo(self, "handleAuditionStepOne", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleObservedFlourish(obj_id self, dictionary params) throws InterruptedException
    {
        int index = params.getInt("flourishIndex");
        int progress = utils.getIntScriptVar(self, PROGRESS_OBJVAR);
        if ((progress == 2) || (progress == 3) || (progress == 5) || (progress == 6))
        {
            int expected = utils.getIntScriptVar(self, EXPECTATION_OBJVAR);
            if (index == expected)
            {
                utils.setScriptVar(self, SATISFACTION_OBJVAR, 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleReceivePlayerFail(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        failAudition(self, player);
        messageTo(self, "handleCleanup", null, 20.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAuditionStepOne(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        utils.setScriptVar(self, PROGRESS_OBJVAR, 1);
        String type = utils.getStringScriptVar(self, TYPE_OBJVAR);
        String name = getRandomPerformanceName(player, type);
        utils.setScriptVar(self, EXPECTATION_OBJVAR, name);
        obj_id judge = getRandomJudge(self);
        name = parseName(name);
        prose_package pp = prose.getPackage(AUDITION_STEP_1, name);
        chat.chat(judge, player, null, null, chat.ChatFlag_targetOnly, pp);
        messageTo(self, "handleAuditionStepTwo", null, HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAuditionStepTwo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        String expected = utils.getStringScriptVar(self, EXPECTATION_OBJVAR);
        if (!isPerformanceCorrect(player, expected))
        {
            sendSystemMessage(player, FAIL_AUDITION);
            failAudition(self, player);
            messageTo(self, "handleCleanup", null, 20.0f, false);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, PROGRESS_OBJVAR, 2);
        int flourish = rand(1, 8);
        utils.setScriptVar(self, EXPECTATION_OBJVAR, flourish);
        utils.setScriptVar(self, SATISFACTION_OBJVAR, 0);
        obj_id judge = getRandomJudge(self);
        prose_package pp = prose.getPackage(AUDITION_STEP_2, flourish);
        chat.chat(judge, player, null, null, chat.ChatFlag_targetOnly, pp);
        messageTo(self, "handleAuditionStepThree", null, HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAuditionStepThree(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        int satisfied = utils.getIntScriptVar(self, SATISFACTION_OBJVAR);
        if (satisfied == 0)
        {
            sendSystemMessage(player, FAIL_AUDITION);
            failAudition(self, player);
            messageTo(self, "handleCleanup", null, 20.0f, false);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, PROGRESS_OBJVAR, 3);
        int flourish = rand(1, 8);
        utils.setScriptVar(self, EXPECTATION_OBJVAR, flourish);
        utils.setScriptVar(self, SATISFACTION_OBJVAR, 0);
        obj_id judge = getRandomJudge(self);
        prose_package pp = prose.getPackage(AUDITION_STEP_3, flourish);
        chat.chat(judge, player, null, null, chat.ChatFlag_targetOnly, pp);
        messageTo(self, "handleAuditionStepFour", null, HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAuditionStepFour(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        int satisfied = utils.getIntScriptVar(self, SATISFACTION_OBJVAR);
        if (satisfied == 0)
        {
            sendSystemMessage(player, FAIL_AUDITION);
            failAudition(self, player);
            messageTo(self, "handleCleanup", null, 20.0f, false);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, PROGRESS_OBJVAR, 4);
        String type = utils.getStringScriptVar(self, TYPE_OBJVAR);
        String name = getRandomPerformanceName(player, type);
        utils.setScriptVar(self, EXPECTATION_OBJVAR, name);
        obj_id judge = getRandomJudge(self);
        name = parseName(name);
        prose_package pp = prose.getPackage(AUDITION_STEP_4, name);
        chat.chat(judge, player, null, null, chat.ChatFlag_targetOnly, pp);
        messageTo(self, "handleAuditionStepFive", null, 25.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAuditionStepFive(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        String expected = utils.getStringScriptVar(self, EXPECTATION_OBJVAR);
        if (!isPerformanceCorrect(player, expected))
        {
            sendSystemMessage(player, FAIL_AUDITION);
            failAudition(self, player);
            messageTo(self, "handleCleanup", null, 20.0f, false);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, PROGRESS_OBJVAR, 5);
        int flourish = rand(1, 8);
        utils.setScriptVar(self, EXPECTATION_OBJVAR, flourish);
        utils.setScriptVar(self, SATISFACTION_OBJVAR, 0);
        obj_id judge = getRandomJudge(self);
        prose_package pp = prose.getPackage(AUDITION_STEP_5, flourish);
        chat.chat(judge, player, null, null, chat.ChatFlag_targetOnly, pp);
        messageTo(self, "handleAuditionStepSix", null, HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAuditionStepSix(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        int satisfied = utils.getIntScriptVar(self, SATISFACTION_OBJVAR);
        if (satisfied == 0)
        {
            sendSystemMessage(player, FAIL_AUDITION);
            failAudition(self, player);
            messageTo(self, "handleCleanup", null, 20.0f, false);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, PROGRESS_OBJVAR, 6);
        int flourish = rand(1, 8);
        utils.setScriptVar(self, EXPECTATION_OBJVAR, flourish);
        utils.setScriptVar(self, SATISFACTION_OBJVAR, 0);
        obj_id judge = getRandomJudge(self);
        prose_package pp = prose.getPackage(AUDITION_STEP_6, flourish);
        chat.chat(judge, player, null, null, chat.ChatFlag_targetOnly, pp);
        messageTo(self, "handleAuditionFinish", null, HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAuditionFinish(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        int satisfied = utils.getIntScriptVar(self, SATISFACTION_OBJVAR);
        if (satisfied == 0)
        {
            sendSystemMessage(player, FAIL_AUDITION);
            failAudition(self, player);
            messageTo(self, "handleCleanup", null, 20.0f, false);
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, PROGRESS_OBJVAR);
        utils.removeScriptVar(self, EXPECTATION_OBJVAR);
        utils.removeScriptVar(self, SATISFACTION_OBJVAR);
        sendSystemMessage(player, SUCCEED_AUDITION);
        succeedAudition(self, player);
        messageTo(self, "handleCleanup", null, 20.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupJudges(self);
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        destroyObject(self);
        if (!isIdValid(control))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(control, CONTROL_OBJVAR);
        return SCRIPT_CONTINUE;
    }
    public void succeedAudition(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        removeTriggerVolume(VOLUME_NAME);
        detachScript(player, PLAYER_SCRIPT);
        utils.removeScriptVar(player, "quest.crowd_pleaser.performance.control");
        utils.removeScriptVar(control, "quest.crowd_pleaser.performance.control");
        setObjVar(player, STATUS_OBJVAR, 3);
    }
    public void failAudition(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        removeTriggerVolume(VOLUME_NAME);
        detachScript(player, PLAYER_SCRIPT);
        utils.removeScriptVar(player, "quest.crowd_pleaser.performance.control");
        utils.removeScriptVar(control, "quest.crowd_pleaser.performance.control");
        setObjVar(player, FAILED_OBJVAR, getGameTime());
        setObjVar(player, STATUS_OBJVAR, 1);
    }
    public String getRandomPerformanceName(obj_id player, String type) throws InterruptedException
    {
        String name = "";
        int c = 0;
        do
        {
            if (type.equals("music"))
            {
                int r = rand(0, (MUSIC_NAMES.length - 1));
                String command = MUSIC_COMMAND + MUSIC_NAMES[r];
                if (hasCommand(player, command))
                {
                    return MUSIC_NAMES[r];
                }
            }
            else if (type.equals("dance"))
            {
                int r = rand(0, (DANCE_NAMES.length - 1));
                String command = DANCE_COMMAND + DANCE_NAMES[r];
                if (hasCommand(player, command))
                {
                    return DANCE_NAMES[r];
                }
            }
            else 
            {
                return DANCE_NAMES[0];
            }
            c++;
        } while (c < 100);
        return DANCE_NAMES[0];
    }
    public String parseName(String name) throws InterruptedException
    {
        if (Character.isDigit(name.charAt(name.length() - 1)))
        {
            name = name.substring(0, (name.length() - 1)) + " " + name.substring(name.length() - 1);
        }
        name = (name.substring(0, 1)).toUpperCase() + (name.substring(1)).toLowerCase();
        return name;
    }
    public void spawnJudges(obj_id self) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        obj_id theater = getCellId(building, "theater");
        location my = getLocation(self);
        location table_loc = new location(0.61f, 2.13f, 53.70f, my.area, theater);
        location chair1_loc = new location(-0.95f, 2.13f, 51.69f, my.area, theater);
        location chair2_loc = new location(0.56f, 2.13f, 51.69f, my.area, theater);
        location chair3_loc = new location(2.07f, 2.13f, 51.69f, my.area, theater);
        location judge1_loc = new location(-0.95f, 2.13f, 51.85f, my.area, theater);
        location judge2_loc = new location(0.56f, 2.13f, 51.85f, my.area, theater);
        location judge3_loc = new location(2.07f, 2.13f, 51.85f, my.area, theater);
        obj_id[] furniture = new obj_id[4];
        obj_id[] judges = new obj_id[3];
        furniture[0] = createObject("object/tangible/furniture/modern/dining_table_modern_style_01.iff", table_loc);
        furniture[1] = createObject("object/tangible/furniture/modern/chair_modern_style_01.iff", chair1_loc);
        furniture[2] = createObject("object/tangible/furniture/modern/chair_modern_style_01.iff", chair2_loc);
        furniture[3] = createObject("object/tangible/furniture/modern/chair_modern_style_01.iff", chair3_loc);
        judges[0] = create.staticObject("quest_crowd_pleaser_judge", judge1_loc);
        judges[1] = create.staticObject("quest_crowd_pleaser_judge", judge2_loc);
        judges[2] = create.staticObject("quest_crowd_pleaser_judge", judge3_loc);
        for (int i = 0; i < furniture.length; i++)
        {
            if (isIdValid(furniture[i]))
            {
                setYaw(furniture[i], 0);
            }
        }
        for (int i = 0; i < judges.length; i++)
        {
            if (isIdValid(judges[i]))
            {
                setYaw(judges[i], 0);
                setInvulnerable(judges[i], true);
                setCreatureStatic(judges[i], true);
                setAnimationMood(judges[i], "npc_sitting_chair");
            }
        }
        utils.setScriptVar(self, JUDGES_OBJVAR, judges);
        utils.setScriptVar(self, FURNITURE_OBJVAR, furniture);
    }
    public void cleanupJudges(obj_id self) throws InterruptedException
    {
        obj_id[] furniture = utils.getObjIdArrayScriptVar(self, FURNITURE_OBJVAR);
        obj_id[] judges = utils.getObjIdArrayScriptVar(self, JUDGES_OBJVAR);
        if (judges != null && judges.length > 0)
        {
            for (int i = 0; i < judges.length; i++)
            {
                if (isIdValid(judges[i]) && exists(judges[i]))
                {
                    destroyObject(judges[i]);
                }
            }
        }
        if (furniture != null && furniture.length > 0)
        {
            for (int i = 0; i < furniture.length; i++)
            {
                if (isIdValid(furniture[i]) && exists(furniture[i]))
                {
                    destroyObject(furniture[i]);
                }
            }
        }
        utils.removeScriptVar(self, FURNITURE_OBJVAR);
        utils.removeScriptVar(self, JUDGES_OBJVAR);
    }
    public boolean isPerformanceCorrect(obj_id player, String expected) throws InterruptedException
    {
        int type = getPerformanceType(player);
        if (type == 0)
        {
            return false;
        }
        type--;
        String name = dataTableGetString(PERFORMANCE_DATATABLE, type, 0);
        if (name.equals(expected))
        {
            return true;
        }
        return false;
    }
    public obj_id getRandomJudge(obj_id self) throws InterruptedException
    {
        obj_id[] judges = utils.getObjIdArrayScriptVar(self, JUDGES_OBJVAR);
        if (judges == null || judges.length == 0)
        {
            return self;
        }
        int r = rand(0, (judges.length - 1));
        return judges[r];
    }
}
