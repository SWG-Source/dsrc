package script.quest.crowd_pleaser;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.prose;
import script.library.utils;

public class control_performance extends script.base_script
{
    public control_performance()
    {
    }
    public static final String PERFORMANCE_DATATABLE = "datatables/performance/performance.iff";
    public static final String SEATING_DATATABLE = "datatables/quest/crowd_pleaser/theater_seat.iff";
    public static final String PLAYER_PERFORMANCE_SCRIPT = "quest.crowd_pleaser.player_performance";
    public static final String STATUS_OBJVAR = "quest.crowd_pleaser.status";
    public static final String PERFORMANCE_OBJVAR = "quest.crowd_pleaser.performance";
    public static final String SATISFACTION_OBJVAR = PERFORMANCE_OBJVAR + ".satisfaction";
    public static final String PLAYER_OBJVAR = PERFORMANCE_OBJVAR + ".player";
    public static final String LEVEL_OBJVAR = PERFORMANCE_OBJVAR + ".level";
    public static final String CONTROL_OBJVAR = PERFORMANCE_OBJVAR + ".control";
    public static final String OCCUPIED_OBJVAR = PERFORMANCE_OBJVAR + ".occupied";
    public static final String RATING_OBJVAR = PERFORMANCE_OBJVAR + ".ratings";
    public static final String IN_PROGRESS_OBJVAR = PERFORMANCE_OBJVAR + ".in_progress";
    public static final String NOT_PERFORMING_OBJVAR = PERFORMANCE_OBJVAR + ".not_performing";
    public static final String FAILED_OBJVAR = PERFORMANCE_OBJVAR + ".failed";
    public static final String FLOURISH_OBJVAR = PERFORMANCE_OBJVAR + ".flourish";
    public static final String AUDITION_OBJVAR = "quest.crowd_pleaser.audition";
    public static final String TYPE_OBJVAR = AUDITION_OBJVAR + ".type";
    public static final String VOLUME_NAME = "performance_area";
    public static final string_id TIME_REMAINING = new string_id("quest/crowd_pleaser/system_messages", "time_remaining");
    public static final string_id PERFORMANCE_BEGIN = new string_id("quest/crowd_pleaser/system_messages", "performance_begin");
    public static final string_id FAIL_LEFT_PERFORMANCE_AREA = new string_id("quest/crowd_pleaser/system_messages", "fail_left_performance_area");
    public static final string_id FAIL_NOT_IN_AREA = new string_id("quest/crowd_pleaser/system_messages", "fail_not_in_area");
    public static final string_id FAIL_NOT_PLAYING = new string_id("quest/crowd_pleaser/system_messages", "fail_not_playing");
    public static final string_id FAIL_NOT_SATISFIED = new string_id("quest/crowd_pleaser/system_messages", "fail_not_satisfied");
    public static final string_id SUCCEED_PERFORMANCE = new string_id("quest/crowd_pleaser/system_messages", "succeed_performance");
    public static final string_id AUDIENCE_REACTION = new string_id("quest/crowd_pleaser/system_messages", "audience_reaction");
    public static final string_id REACTION_1 = new string_id("quest/crowd_pleaser/system_messages", "reaction_1");
    public static final string_id REACTION_2 = new string_id("quest/crowd_pleaser/system_messages", "reaction_2");
    public static final string_id REACTION_3 = new string_id("quest/crowd_pleaser/system_messages", "reaction_3");
    public static final string_id REACTION_3_5 = new string_id("quest/crowd_pleaser/system_messages", "reaction_3_5");
    public static final string_id REACTION_4 = new string_id("quest/crowd_pleaser/system_messages", "reaction_4");
    public static final string_id REACTION_5 = new string_id("quest/crowd_pleaser/system_messages", "reaction_5");
    public static final string_id REACTION_6 = new string_id("quest/crowd_pleaser/system_messages", "reaction_6");
    public static final int LEVEL_1_THRESHOLD = 100;
    public static final int LEVEL_2_THRESHOLD = 180;
    public static final int LEVEL_3_THRESHOLD = 244;
    public static final int DEFAULT_PERFORMANCE_RATING = 5;
    public static final int MINIMUM_POSITIVE_RATING = 2;
    public static final int MINIMUM_NEGATIVE_RATING = -10;
    public static final float HEARTBEAT = 15.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, SATISFACTION_OBJVAR, 0);
        int level = utils.getIntScriptVar(self, LEVEL_OBJVAR);
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        String type = getStringObjVar(player, TYPE_OBJVAR);
        if (type == null || type.equals(""))
        {
            type = "dance";
        }
        utils.setScriptVar(self, TYPE_OBJVAR, type);
        int time = 0;
        switch (level)
        {
            case 1:
            time = 120;
            break;
            case 2:
            time = 150;
            break;
            case 3:
            time = 180;
            break;
        }
        spawnAudience(self, player, level);
        createTriggerVolume(VOLUME_NAME, 10.0f, true);
        dictionary d = new dictionary();
        d.put("time", time);
        messageTo(self, "handlePrePerformanceCountdown", d, 1.0f, false);
        messageTo(self, "handleBeginPerformance", null, time, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, CONTROL_OBJVAR) || !utils.hasScriptVar(self, LEVEL_OBJVAR) || !utils.hasScriptVar(self, PLAYER_OBJVAR))
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
        if (!utils.hasScriptVar(self, IN_PROGRESS_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        if (who != player)
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, FAIL_LEFT_PERFORMANCE_AREA);
        failPerformance(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handlePrePerformanceCountdown(obj_id self, dictionary params) throws InterruptedException
    {
        int time = params.getInt("time");
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        if (time <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        prose_package pp = prose.getPackage(TIME_REMAINING, time);
        sendSystemMessageProse(player, pp);
        int dec = 30;
        if (time <= 30)
        {
            dec = 10;
        }
        time -= dec;
        dictionary d = new dictionary();
        d.put("time", time);
        messageTo(self, "handlePrePerformanceCountdown", d, dec, false);
        return SCRIPT_CONTINUE;
    }
    public int handleBeginPerformance(obj_id self, dictionary params) throws InterruptedException
    {
        int level = utils.getIntScriptVar(self, LEVEL_OBJVAR);
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        obj_id[] contents = getTriggerVolumeContents(self, VOLUME_NAME);
        if (contents == null || contents.length == 0)
        {
            sendSystemMessage(player, FAIL_NOT_IN_AREA);
            failPerformance(self, player);
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
            failPerformance(self, player);
            return SCRIPT_CONTINUE;
        }
        int time = 0;
        switch (level)
        {
            case 1:
            time = 120;
            break;
            case 2:
            time = 210;
            break;
            case 3:
            time = 300;
            break;
        }
        prose_package pp = prose.getPackage(PERFORMANCE_BEGIN, time);
        sendSystemMessageProse(player, pp);
        utils.setScriptVar(self, IN_PROGRESS_OBJVAR, 1);
        int index = getPerformanceType(player);
        if (index == 0)
        {
            utils.setScriptVar(self, NOT_PERFORMING_OBJVAR, 1);
        }
        messageTo(self, "handleEndPerformance", null, time, false);
        messageTo(self, "handlePerformanceHeartbeat", null, HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePerformanceHeartbeat(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, IN_PROGRESS_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        int index = getPerformanceType(player);
        if (index == 0)
        {
            if (utils.hasScriptVar(self, NOT_PERFORMING_OBJVAR))
            {
                sendSystemMessage(player, FAIL_NOT_PLAYING);
                failPerformance(self, player);
            }
            else 
            {
                utils.setScriptVar(self, NOT_PERFORMING_OBJVAR, 1);
            }
        }
        else 
        {
            if (utils.hasScriptVar(self, NOT_PERFORMING_OBJVAR))
            {
                utils.removeScriptVar(self, NOT_PERFORMING_OBJVAR);
            }
            String performanceName = dataTableGetString(PERFORMANCE_DATATABLE, (index - 1), 0);
            int rating = 0;
            if (utils.hasScriptVar(self, RATING_OBJVAR + "." + performanceName))
            {
                rating = utils.getIntScriptVar(self, RATING_OBJVAR + "." + performanceName);
            }
            else 
            {
                rating = DEFAULT_PERFORMANCE_RATING;
            }
            if (!utils.hasScriptVar(self, SATISFACTION_OBJVAR))
            {
                utils.setScriptVar(self, SATISFACTION_OBJVAR, 0);
            }
            int satisfaction = utils.getIntScriptVar(self, SATISFACTION_OBJVAR);
            satisfaction += rating;
            rating--;
            if ((rating > 0) && (rating < MINIMUM_POSITIVE_RATING))
            {
                rating = MINIMUM_POSITIVE_RATING;
            }
            if ((rating < 0) && (rating < MINIMUM_NEGATIVE_RATING))
            {
                rating = MINIMUM_NEGATIVE_RATING;
            }
            utils.setScriptVar(self, SATISFACTION_OBJVAR, satisfaction);
            utils.setScriptVar(self, RATING_OBJVAR + "." + performanceName, rating);
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "[GOD MODE] Audience satisfaction: " + satisfaction);
            }
            performAudienceReaction(self, player, satisfaction);
        }
        if (utils.hasScriptVar(self, FLOURISH_OBJVAR))
        {
            utils.removeScriptVar(self, FLOURISH_OBJVAR);
        }
        messageTo(self, "handlePerformanceHeartbeat", null, HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int handleObservedFlourish(obj_id self, dictionary params) throws InterruptedException
    {
        int index = params.getInt("flourishIndex");
        int rating = 0;
        if (utils.hasScriptVar(self, RATING_OBJVAR + "." + index))
        {
            rating = utils.getIntScriptVar(self, RATING_OBJVAR + "." + index);
        }
        else 
        {
            rating = DEFAULT_PERFORMANCE_RATING;
        }
        if (!utils.hasScriptVar(self, SATISFACTION_OBJVAR))
        {
            utils.setScriptVar(self, SATISFACTION_OBJVAR, 0);
        }
        int satisfaction = utils.getIntScriptVar(self, SATISFACTION_OBJVAR);
        int number = 0;
        if (utils.hasScriptVar(self, FLOURISH_OBJVAR))
        {
            number = utils.getIntScriptVar(self, FLOURISH_OBJVAR);
        }
        number++;
        if (number < 3)
        {
            satisfaction += rating;
            rating--;
            if ((rating > 0) && (rating < MINIMUM_POSITIVE_RATING))
            {
                rating = MINIMUM_POSITIVE_RATING;
            }
            if ((rating < 0) && (rating < MINIMUM_NEGATIVE_RATING))
            {
                rating = MINIMUM_NEGATIVE_RATING;
            }
            utils.setScriptVar(self, SATISFACTION_OBJVAR, satisfaction);
            utils.setScriptVar(self, RATING_OBJVAR + "." + index, rating);
            utils.setScriptVar(self, FLOURISH_OBJVAR, number);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleEndPerformance(obj_id self, dictionary params) throws InterruptedException
    {
        int level = utils.getIntScriptVar(self, LEVEL_OBJVAR);
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        int threshold = 0;
        switch (level)
        {
            case 1:
            threshold = LEVEL_1_THRESHOLD;
            break;
            case 2:
            threshold = LEVEL_2_THRESHOLD;
            break;
            case 3:
            threshold = LEVEL_3_THRESHOLD;
            break;
        }
        int satisfaction = utils.getIntScriptVar(self, SATISFACTION_OBJVAR);
        performFinalAudienceReaction(self, player, satisfaction);
        if (satisfaction >= threshold)
        {
            sendSystemMessage(player, SUCCEED_PERFORMANCE);
            succeedPerformance(self, player);
        }
        else 
        {
            sendSystemMessage(player, FAIL_NOT_SATISFIED);
            failPerformance(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleReceivePlayerFail(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, PLAYER_OBJVAR);
        failPerformance(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnAudienceMembers(obj_id self, dictionary params) throws InterruptedException
    {
        final int SPAWN_LUMP = 5;
        location loc = getLocation(self);
        int rows = dataTableGetNumRows(SEATING_DATATABLE);
        int number = params.getInt("number");
        String type = params.getString("type");
        Vector occupied = utils.getResizeableIntArrayScriptVar(self, OCCUPIED_OBJVAR);
        if (occupied == null)
        {
            occupied = new Vector();
        }
        int r = 0;
        for (int i = 0; i < SPAWN_LUMP; i++)
        {
            do
            {
                r = rand(0, (rows - 1));
            } while (isOccupied(r, occupied));
            dictionary row = dataTableGetRow(SEATING_DATATABLE, r);
            float x = row.getFloat("seat_x");
            float y = row.getFloat("seat_y");
            float z = row.getFloat("seat_z");
            location seat_loc = new location(x, y, z, loc.area, loc.cell);
            int yaw = row.getInt("yaw");
            obj_id member = create.staticObject("quest_crowd_pleaser_audience_member", seat_loc);
            if (isIdValid(member))
            {
                setYaw(member, yaw);
                setInvulnerable(member, true);
                setCreatureStatic(member, true);
                setAnimationMood(member, "npc_sitting_chair");
                dictionary webster = new dictionary();
                webster.put("control", self);
                messageTo(member, "handleSetup", webster, 1.0f, false);
                occupied = utils.addElement(occupied, r);
            }
        }
        number -= SPAWN_LUMP;
        dictionary d = new dictionary();
        d.put("number", number);
        d.put("type", type);
        utils.setScriptVar(self, OCCUPIED_OBJVAR, occupied);
        if (number > 0)
        {
            messageTo(self, "handleSpawnAudienceMembers", d, 10.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        cleanupAudienceMembers(self);
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        utils.removeScriptVar(control, CONTROL_OBJVAR);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public boolean isOccupied(int seat, Vector list) throws InterruptedException
    {
        if (list == null || list.size() == 0)
        {
            return false;
        }
        for (int i = 0; i < list.size(); i++)
        {
            if ((((Integer)list.get(i))).intValue() == seat)
            {
                return true;
            }
        }
        return false;
    }
    public void succeedPerformance(obj_id self, obj_id player) throws InterruptedException
    {
        int level = utils.getIntScriptVar(self, LEVEL_OBJVAR);
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        obj_id building = getTopMostContainer(self);
        removeTriggerVolume(VOLUME_NAME);
        utils.removeScriptVar(self, IN_PROGRESS_OBJVAR);
        detachScript(player, PLAYER_PERFORMANCE_SCRIPT);
        utils.removeScriptVar(player, CONTROL_OBJVAR);
        int status = 0;
        switch (level)
        {
            case 1:
            status = 9;
            break;
            case 2:
            status = 15;
            break;
            case 3:
            status = 21;
            break;
        }
        setObjVar(player, STATUS_OBJVAR, status);
        messageTo(self, "handleCleanup", null, 20.0f, false);
    }
    public void failPerformance(obj_id self, obj_id player) throws InterruptedException
    {
        int level = utils.getIntScriptVar(self, LEVEL_OBJVAR);
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        removeTriggerVolume(VOLUME_NAME);
        utils.removeScriptVar(self, IN_PROGRESS_OBJVAR);
        detachScript(player, PLAYER_PERFORMANCE_SCRIPT);
        utils.removeScriptVar(player, CONTROL_OBJVAR);
        setObjVar(player, FAILED_OBJVAR, getGameTime());
        int status = 0;
        switch (level)
        {
            case 1:
            status = 7;
            break;
            case 2:
            status = 13;
            break;
            case 3:
            status = 19;
            break;
        }
        setObjVar(player, STATUS_OBJVAR, status);
        messageTo(self, "handleCleanup", null, 20.0f, false);
    }
    public void spawnAudience(obj_id self, obj_id player, int level) throws InterruptedException
    {
        int members = 0;
        switch (level)
        {
            case 1:
            members = 10;
            break;
            case 2:
            members = 20;
            break;
            case 3:
            members = 30;
            break;
        }
        String type = getStringObjVar(player, "quest.crowd_pleaser.audition.type");
        if (type == null || type.equals(""))
        {
            type = "dance";
        }
        dictionary d = new dictionary();
        d.put("number", members);
        d.put("type", type);
        messageTo(self, "handleSpawnAudienceMembers", d, 1.0f, false);
    }
    public void performAudienceReaction(obj_id self, obj_id player, int satisfaction) throws InterruptedException
    {
        int level = utils.getIntScriptVar(self, LEVEL_OBJVAR);
        int threshold = 0;
        switch (level)
        {
            case 1:
            threshold = LEVEL_1_THRESHOLD;
            break;
            case 2:
            threshold = LEVEL_2_THRESHOLD;
            break;
            case 3:
            threshold = LEVEL_3_THRESHOLD;
            break;
        }
        prose_package pp = new prose_package();
        float rating = 100.0f * ((float)satisfaction / (float)threshold);
        if (rating > 125.0f)
        {
            pp = prose.getPackage(AUDIENCE_REACTION, localize(REACTION_1));
        }
        else if (rating > 100.0f)
        {
            pp = prose.getPackage(AUDIENCE_REACTION, localize(REACTION_2));
        }
        else if (rating > 75.0f)
        {
            pp = prose.getPackage(AUDIENCE_REACTION, localize(REACTION_3));
        }
        else if (rating > 50.0f)
        {
            pp = prose.getPackage(AUDIENCE_REACTION, localize(REACTION_3_5));
        }
        else if (rating > 25.0f)
        {
            pp = prose.getPackage(AUDIENCE_REACTION, localize(REACTION_4));
        }
        else if (rating > 0.0f)
        {
            pp = prose.getPackage(AUDIENCE_REACTION, localize(REACTION_5));
        }
        else 
        {
            pp = prose.getPackage(AUDIENCE_REACTION, localize(REACTION_6));
        }
        sendSystemMessageProse(player, pp);
    }
    public void performFinalAudienceReaction(obj_id self, obj_id player, int satisfaction) throws InterruptedException
    {
        int level = utils.getIntScriptVar(self, LEVEL_OBJVAR);
        int threshold = 0;
        switch (level)
        {
            case 1:
            threshold = LEVEL_1_THRESHOLD;
            break;
            case 2:
            threshold = LEVEL_2_THRESHOLD;
            break;
            case 3:
            threshold = LEVEL_3_THRESHOLD;
            break;
        }
        String effectName = "";
        float rating = 100.0f * ((float)satisfaction / (float)threshold);
        if (rating >= 100.0f)
        {
            effectName = "clienteffect/perform_cheer.cef";
        }
        else if (rating >= 75.0f)
        {
            effectName = "clienteffect/perform_clap.cef";
        }
        else if (rating >= 25.0f)
        {
            effectName = "clienteffect/perform_crickets.cef";
        }
        else 
        {
            effectName = "clienteffect/perform_boo.cef";
        }
        location myLoc = getLocation(self);
        location loc = new location(0.16f, 1.20f, 37.74f, myLoc.area, myLoc.cell);
        playClientEffectLoc(player, effectName, loc, 2.0f);
    }
    public void cleanupAudienceMembers(obj_id self) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id contents[] = getContents(loc.cell);
        if (contents == null || contents.length == 0)
        {
            return;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (hasScript(contents[i], "quest.crowd_pleaser.audience_member"))
            {
                destroyObject(contents[i]);
            }
        }
    }
}
