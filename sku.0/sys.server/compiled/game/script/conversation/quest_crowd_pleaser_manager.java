package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.money;
import script.library.prose;
import script.library.utils;

public class quest_crowd_pleaser_manager extends script.base_script
{
    public quest_crowd_pleaser_manager()
    {
    }
    public static String c_stringFile = "conversation/quest_crowd_pleaser_manager";
    public boolean quest_crowd_pleaser_manager_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_entertainer(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.ENTERTAINER);
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 1)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_event_in_progress(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(npc, "quest.crowd_pleaser.performance.control");
    }
    public boolean quest_crowd_pleaser_manager_condition_has_dance_skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "class_entertainer_phase1_master");
    }
    public boolean quest_crowd_pleaser_manager_condition_has_music_skill(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "class_entertainer_phase1_master");
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 4)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 3)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_5(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 5)
            {
                if (!hasScript(player, "quest.crowd_pleaser.player_popularity"))
                {
                    attachScript(player, "quest.crowd_pleaser.player_popularity");
                }
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_6(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 6)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_7(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 7)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_9(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 9)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_10(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 10)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_11(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 11)
            {
                if (!hasScript(player, "quest.crowd_pleaser.player_popularity"))
                {
                    attachScript(player, "quest.crowd_pleaser.player_popularity");
                }
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_12(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 12)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_13(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 13)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_15(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 15)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_16(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 16)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_17(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 17)
            {
                if (!hasScript(player, "quest.crowd_pleaser.player_popularity"))
                {
                    attachScript(player, "quest.crowd_pleaser.player_popularity");
                }
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_18(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 18)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_19(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 19)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_status_21(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.status"))
        {
            int status = getIntObjVar(player, "quest.crowd_pleaser.status");
            if (status == 21)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_failed_recently(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.performance.failed"))
        {
            int failed = getIntObjVar(player, "quest.crowd_pleaser.performance.failed");
            int time = getGameTime();
            int test_time = 86400;
            String strTest = getConfigSetting("Quest", "CrowdPleaserTestTime");
            if (strTest != null && !strTest.equals(""))
            {
                test_time = utils.stringToInt(strTest);
                if (test_time < 0)
                {
                    test_time = 86400;
                }
                if (isGod(player))
                {
                    sendSystemMessageTestingOnly(player, "<GOD MODE> Failure timer override - You need only wait " + test_time + " seconds after a failure");
                }
            }
            if ((time - failed) < test_time)
            {
                return true;
            }
            else 
            {
                removeObjVar(player, "quest.crowd_pleaser.performance.failed");
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_failed_audition(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.audition.failed"))
        {
            int failed = getIntObjVar(player, "quest.crowd_pleaser.audition.failed");
            int time = getGameTime();
            int test_time = 86400;
            String strTest = getConfigSetting("Quest", "CrowdPleaserTestTime");
            if (strTest != null && !strTest.equals(""))
            {
                test_time = utils.stringToInt(strTest);
                if (test_time < 0)
                {
                    test_time = 86400;
                }
                if (isGod(player))
                {
                    sendSystemMessageTestingOnly(player, "<GOD MODE> Failure timer override - You need only wait " + test_time + " seconds after a failure");
                }
            }
            if ((time - failed) < test_time)
            {
                return true;
            }
            else 
            {
                removeObjVar(player, "quest.crowd_pleaser.audition.failed");
            }
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_completed_both(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.crowd_pleaser.complete"))
        {
            return false;
        }
        String type = getStringObjVar(player, "quest.crowd_pleaser.complete");
        if (type == null || type.equals(""))
        {
            removeObjVar(player, "quest.crowd_pleaser.complete");
            return false;
        }
        if (type.equals("both"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_completed_one(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.crowd_pleaser.complete"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_completed_music(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.crowd_pleaser.complete"))
        {
            return false;
        }
        String type = getStringObjVar(player, "quest.crowd_pleaser.complete");
        if (type == null || type.equals(""))
        {
            return false;
        }
        if (type.equals("music"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_crowd_pleaser_manager_condition_has_completed_dance(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.crowd_pleaser.complete"))
        {
            return false;
        }
        String type = getStringObjVar(player, "quest.crowd_pleaser.complete");
        if (type == null || type.equals(""))
        {
            return false;
        }
        if (type.equals("dance"))
        {
            return true;
        }
        return false;
    }
    public void quest_crowd_pleaser_manager_action_set_status_1(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.crowd_pleaser.status", 1);
    }
    public void quest_crowd_pleaser_manager_action_set_status_2(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id building = getTopMostContainer(npc);
        if (!isIdValid(building))
        {
            return;
        }
        obj_id cell = getCellId(building, "theater");
        if (!isIdValid(cell))
        {
            return;
        }
        location myLoc = getLocation(npc);
        location loc = new location(0.58f, 2.13f, 58.69f, myLoc.area, cell);
        obj_id control = createObject("object/tangible/theme_park/invisible_object.iff", loc);
        if (!isIdValid(control))
        {
            return;
        }
        utils.setScriptVar(control, "quest.crowd_pleaser.audition.player", player);
        utils.setScriptVar(control, "quest.crowd_pleaser.audition.control", npc);
        utils.setScriptVar(npc, "quest.crowd_pleaser.performance.control", control);
        utils.setScriptVar(player, "quest.crowd_pleaser.performance.control", control);
        attachScript(control, "quest.crowd_pleaser.control_audition");
        attachScript(player, "quest.crowd_pleaser.player_performance");
        setObjVar(player, "quest.crowd_pleaser.status", 2);
    }
    public void quest_crowd_pleaser_manager_action_set_music(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.crowd_pleaser.audition.type", "music");
    }
    public void quest_crowd_pleaser_manager_action_set_dance(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.crowd_pleaser.audition.type", "dance");
    }
    public void quest_crowd_pleaser_manager_action_set_status_4(obj_id player, obj_id npc) throws InterruptedException
    {
        final int PAY = 500;
        setObjVar(player, "quest.crowd_pleaser.status", 4);
        money.systemPayout(money.ACCT_TEST, player, PAY, null, null);
        prose_package pp = prose.getPackage(new string_id("quest/crowd_pleaser/system_messages", "payout"), PAY);
        sendSystemMessageProse(player, pp);
    }
    public void quest_crowd_pleaser_manager_action_set_status_5(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.crowd_pleaser.status", 5);
        setObjVar(player, "quest.crowd_pleaser.popularity.number", 10);
        setObjVar(player, "quest.crowd_pleaser.popularity.level", 1);
        attachScript(player, "quest.crowd_pleaser.player_popularity");
    }
    public void quest_crowd_pleaser_manager_action_set_status_7(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.crowd_pleaser.status", 7);
    }
    public void quest_crowd_pleaser_manager_action_set_status_8(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id building = getTopMostContainer(npc);
        if (!isIdValid(building))
        {
            return;
        }
        obj_id cell = getCellId(building, "theater");
        if (!isIdValid(cell))
        {
            return;
        }
        location myLoc = getLocation(npc);
        location loc = new location(0.58f, 2.13f, 58.69f, myLoc.area, cell);
        obj_id control = createObject("object/tangible/theme_park/invisible_object.iff", loc);
        if (!isIdValid(control))
        {
            return;
        }
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.player", player);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.level", 1);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.control", npc);
        String type = getStringObjVar(player, "quest.crowd_pleaser.audition.type");
        String pos = "";
        String neg = "";
        int c = 0;
        do
        {
            pos = getRandomPerformanceName(player, type);
            c++;
        } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + pos) && c < 100);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + pos, 10);
        int flourish = rand(1, 8);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish, 10);
        c = 0;
        do
        {
            neg = getRandomPerformanceName(player, type);
            c++;
        } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + neg) && c < 100);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + neg, -2);
        do
        {
            flourish = rand(1, 8);
        } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish));
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish, -2);
        utils.setScriptVar(npc, "quest.crowd_pleaser.performance.control", control);
        utils.setScriptVar(player, "quest.crowd_pleaser.performance.control", control);
        attachScript(control, "quest.crowd_pleaser.control_performance");
        attachScript(player, "quest.crowd_pleaser.player_performance");
        setObjVar(player, "quest.crowd_pleaser.status", 8);
    }
    public void quest_crowd_pleaser_manager_action_set_status_10(obj_id player, obj_id npc) throws InterruptedException
    {
        final int PAY = 1000;
        setObjVar(player, "quest.crowd_pleaser.status", 10);
        money.systemPayout(money.ACCT_TEST, player, PAY, null, null);
        prose_package pp = prose.getPackage(new string_id("quest/crowd_pleaser/system_messages", "payout"), PAY);
        sendSystemMessageProse(player, pp);
    }
    public void quest_crowd_pleaser_manager_action_set_status_11(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.crowd_pleaser.status", 11);
        setObjVar(player, "quest.crowd_pleaser.popularity.number", 20);
        setObjVar(player, "quest.crowd_pleaser.popularity.level", 2);
        attachScript(player, "quest.crowd_pleaser.player_popularity");
    }
    public void quest_crowd_pleaser_manager_action_set_status_13(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.crowd_pleaser.status", 13);
    }
    public void quest_crowd_pleaser_manager_action_set_status_14(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id building = getTopMostContainer(npc);
        if (!isIdValid(building))
        {
            return;
        }
        obj_id cell = getCellId(building, "theater");
        if (!isIdValid(cell))
        {
            return;
        }
        location myLoc = getLocation(npc);
        location loc = new location(0.58f, 2.13f, 58.69f, myLoc.area, cell);
        obj_id control = createObject("object/tangible/theme_park/invisible_object.iff", loc);
        if (!isIdValid(control))
        {
            return;
        }
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.player", player);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.level", 2);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.control", npc);
        String type = getStringObjVar(player, "quest.crowd_pleaser.audition.type");
        String pos = "";
        String neg = "";
        int c = 0;
        int flourish = 0;
        for (int i = 0; i < 2; i++)
        {
            do
            {
                pos = getRandomPerformanceName(player, type);
                c++;
            } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + pos) && c < 100);
            utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + pos, 10);
            do
            {
                flourish = rand(1, 8);
            } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish));
            utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish, 10);
        }
        c = 0;
        do
        {
            neg = getRandomPerformanceName(player, type);
            c++;
        } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + neg) && c < 100);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + neg, -2);
        do
        {
            flourish = rand(1, 8);
        } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish));
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish, -2);
        utils.setScriptVar(npc, "quest.crowd_pleaser.performance.control", control);
        utils.setScriptVar(player, "quest.crowd_pleaser.performance.control", control);
        attachScript(control, "quest.crowd_pleaser.control_performance");
        attachScript(player, "quest.crowd_pleaser.player_performance");
        setObjVar(player, "quest.crowd_pleaser.status", 14);
    }
    public void quest_crowd_pleaser_manager_action_set_status_16(obj_id player, obj_id npc) throws InterruptedException
    {
        final int PAY = 1500;
        setObjVar(player, "quest.crowd_pleaser.status", 16);
        money.systemPayout(money.ACCT_CROWD_PLEASER, player, PAY, null, null);
        prose_package pp = prose.getPackage(new string_id("quest/crowd_pleaser/system_messages", "payout"), PAY);
        sendSystemMessageProse(player, pp);
    }
    public void quest_crowd_pleaser_manager_action_set_status_17(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.crowd_pleaser.status", 17);
        setObjVar(player, "quest.crowd_pleaser.popularity.number", 30);
        setObjVar(player, "quest.crowd_pleaser.popularity.level", 3);
        attachScript(player, "quest.crowd_pleaser.player_popularity");
    }
    public void quest_crowd_pleaser_manager_action_set_status_19(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.crowd_pleaser.status", 19);
    }
    public void quest_crowd_pleaser_manager_action_set_status_20(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id building = getTopMostContainer(npc);
        if (!isIdValid(building))
        {
            return;
        }
        obj_id cell = getCellId(building, "theater");
        if (!isIdValid(cell))
        {
            return;
        }
        location myLoc = getLocation(npc);
        location loc = new location(0.58f, 2.13f, 58.69f, myLoc.area, cell);
        obj_id control = createObject("object/tangible/theme_park/invisible_object.iff", loc);
        if (!isIdValid(control))
        {
            return;
        }
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.player", player);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.level", 3);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.control", npc);
        String type = getStringObjVar(player, "quest.crowd_pleaser.audition.type");
        String pos = "";
        String neg = "";
        int c = 0;
        int flourish = 0;
        for (int i = 0; i < 3; i++)
        {
            do
            {
                pos = getRandomPerformanceName(player, type);
                c++;
            } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + pos) && c < 100);
            utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + pos, 10);
            do
            {
                flourish = rand(1, 8);
            } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish));
            utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish, 10);
        }
        c = 0;
        do
        {
            neg = getRandomPerformanceName(player, type);
            c++;
        } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + neg) && c < 100);
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + neg, -2);
        do
        {
            flourish = rand(1, 8);
        } while (utils.hasScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish));
        utils.setScriptVar(control, "quest.crowd_pleaser.performance.ratings." + flourish, -2);
        utils.setScriptVar(npc, "quest.crowd_pleaser.performance.control", control);
        utils.setScriptVar(player, "quest.crowd_pleaser.performance.control", control);
        attachScript(control, "quest.crowd_pleaser.control_performance");
        attachScript(player, "quest.crowd_pleaser.player_performance");
        setObjVar(player, "quest.crowd_pleaser.status", 20);
    }
    public void quest_crowd_pleaser_manager_action_set_completion(obj_id player, obj_id npc) throws InterruptedException
    {
        final int PAY = 2000;
        money.systemPayout(money.ACCT_CROWD_PLEASER, player, PAY, null, null);
        prose_package pp = prose.getPackage(new string_id("quest/crowd_pleaser/system_messages", "payout"), PAY);
        sendSystemMessageProse(player, pp);
        if (hasObjVar(player, "quest.crowd_pleaser.audition.type"))
        {
            String type = getStringObjVar(player, "quest.crowd_pleaser.audition.type");
            if (type != null && !type.equals(""))
            {
                if (type.equals("dance"))
                {
                    if (hasObjVar(player, "quest.crowd_pleaser.complete"))
                    {
                        removeObjVar(player, "quest.crowd_pleaser");
                        setObjVar(player, "quest.crowd_pleaser.complete", "both");
                    }
                    else 
                    {
                        removeObjVar(player, "quest.crowd_pleaser");
                        setObjVar(player, "quest.crowd_pleaser.complete", "dance");
                    }
                    grantCommand(player, "startDance+theatrical");
                    grantCommand(player, "startDance+theatrical2");
                }
                else if (type.equals("music"))
                {
                    if (hasObjVar(player, "quest.crowd_pleaser.complete"))
                    {
                        removeObjVar(player, "quest.crowd_pleaser");
                        setObjVar(player, "quest.crowd_pleaser.complete", "both");
                    }
                    else 
                    {
                        removeObjVar(player, "quest.crowd_pleaser");
                        setObjVar(player, "quest.crowd_pleaser.complete", "music");
                    }
                    grantCommand(player, "startMusic+western");
                }
            }
        }
    }
    public void quest_crowd_pleaser_manager_action_giveMissingRewards(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasCommand(player, "startDance+theatrical"))
        {
            grantCommand(player, "startDance+theatrical");
        }
        if (!hasCommand(player, "startDance+theatrical2"))
        {
            grantCommand(player, "startDance+theatrical2");
        }
        if (!hasCommand(player, "startMusic+western"))
        {
            grantCommand(player, "startMusic+western");
        }
    }
    public int quest_crowd_pleaser_manager_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36a4e374"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b751f222");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd4bb16d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d730c5dd");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55e428e8"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2cf69669");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61657d0f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fd047f06");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_326a697e"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9d801ae8");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dd4bb16d"))
        {
            if (quest_crowd_pleaser_manager_condition_event_in_progress(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f148a2c3");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_20(player, npc);
                string_id message = new string_id(c_stringFile, "s_86731ce6");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d730c5dd"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ab1c9c5f");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61657d0f"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_20");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fd047f06"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f72be127");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (quest_crowd_pleaser_manager_condition_event_in_progress(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f148a2c3");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_20(player, npc);
                string_id message = new string_id(c_stringFile, "s_86731ce6");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_24"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5a936e2e"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_668b00bf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57c8489e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ad679858");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2f3d57d6"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fe82f47f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a99397a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_da9a29e9");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1fe0d27c"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57c8489e"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cef0609");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c6403b16");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0fc43d");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ad679858"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c6403b16"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_17(player, npc);
                string_id message = new string_id(c_stringFile, "s_bba5f6f2");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d0fc43d"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_942ddb10");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a99397a"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_668b00bf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57c8489e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ad679858");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_da9a29e9"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1dc24a1"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_250ebca6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59a59142");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fb55e1c0");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3ef23ade"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a19891a9");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59a59142"))
        {
            if (quest_crowd_pleaser_manager_condition_event_in_progress(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_14(player, npc);
                string_id message = new string_id(c_stringFile, "s_e66da464");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fb55e1c0"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38a3b1e2");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7b58a334"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1e0d968c");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_847c3be4"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58ea419");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e04f6f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11a3b43");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6b02387e"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fa31756f");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3e04f6f"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c9770db7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b6dac7cf");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a820a6d1");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11a3b43"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b6dac7cf"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_11(player, npc);
                string_id message = new string_id(c_stringFile, "s_f66a7b69");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a820a6d1"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b1522710");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1377703a"))
        {
            if (quest_crowd_pleaser_manager_condition_event_in_progress(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_8(player, npc);
                string_id message = new string_id(c_stringFile, "s_db3a3e40");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4365b273"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85b97ed7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_99"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_101");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_93"))
        {
            if (quest_crowd_pleaser_manager_condition_event_in_progress(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_8(player, npc);
                string_id message = new string_id(c_stringFile, "s_db3a3e40");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_95"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_97");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_694f2644"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_353af024");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8ddf8010");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e8276d66");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_414898b2"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_124");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8ddf8010"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4dbb8ff6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34bf7b50");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c7cb7929");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e8276d66"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34bf7b50"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2224e2b8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7943785d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_350a3257");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c7cb7929"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_118");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7943785d"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_5(player, npc);
                string_id message = new string_id(c_stringFile, "s_8b00fbc4");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_350a3257"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_115");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4a1d2431"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1204a0c6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!quest_crowd_pleaser_manager_condition_has_completed_music(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!quest_crowd_pleaser_manager_condition_has_completed_dance(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_183e8ee4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9172f29c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c73756f7");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f3d46f0b"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_144");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_183e8ee4"))
        {
            quest_crowd_pleaser_manager_action_set_music(player, npc);
            if (!quest_crowd_pleaser_manager_condition_has_music_skill(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1c296980");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition_event_in_progress(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57f90c0e");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_274a8930");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9172f29c"))
        {
            quest_crowd_pleaser_manager_action_set_dance(player, npc);
            if (!quest_crowd_pleaser_manager_condition_has_dance_skill(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_135");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition_event_in_progress(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_137");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_139");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c73756f7"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_303aa95e");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dda95847"))
        {
            if (quest_crowd_pleaser_manager_condition_has_completed_dance(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_919f9d5a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39aa9593");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9728f9e3");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition_has_completed_music(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a84a3743");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39aa9593");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9728f9e3");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_68c92267");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c5a66e82"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_67e4d556");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39aa9593"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70da1c6a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9739cb4c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4be62475");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9728f9e3"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1b58131a");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9739cb4c"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_1(player, npc);
                string_id message = new string_id(c_stringFile, "s_14556b51");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4be62475"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56d2a81f");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_39aa9593"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70da1c6a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9739cb4c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4be62475");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9728f9e3"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1b58131a");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_499afb89"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c08ff946");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a2fae368");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388438a6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1343b0ab");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2883b989"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_180");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a2fae368"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7b9543aa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9288900f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64d20f97");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_388438a6"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a2ce6dd9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8e4955d6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b85a558e");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1343b0ab"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_177");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch85(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9288900f"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                quest_crowd_pleaser_manager_action_set_status_1(player, npc);
                string_id message = new string_id(c_stringFile, "s_705ff1a9");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64d20f97"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82b273a8");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_crowd_pleaser_manager_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8e4955d6"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7b9543aa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9288900f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64d20f97");
                    }
                    utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b85a558e"))
        {
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
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
        "footloose2",
        "popular",
        "poplock",
        "popular2",
        "poplock2",
        "exotic",
        "exotic2",
        "exotic3",
        "exotic4"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.quest_crowd_pleaser_manager");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        clearCondition(self, CONDITION_INTERESTING);
        detachScript(self, "conversation.quest_crowd_pleaser_manager");
        return SCRIPT_CONTINUE;
    }
    public String getRandomPerformanceName(obj_id player, String type) throws InterruptedException
    {
        String name = "";
        int c = 0;
        if (type == null || type.equals(""))
        {
            type = "dance";
        }
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
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (quest_crowd_pleaser_manager_condition_has_completed_both(player, npc))
        {
            quest_crowd_pleaser_manager_action_giveMissingRewards(player, npc);
            string_id message = new string_id(c_stringFile, "s_e5438cc6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!quest_crowd_pleaser_manager_condition_has_entertainer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2a8b6bf");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_failed_audition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3936bc63");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_failed_recently(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_aa18ac6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_event_in_progress(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d66dc712");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_21(player, npc))
        {
            quest_crowd_pleaser_manager_action_set_completion(player, npc);
            string_id message = new string_id(c_stringFile, "s_7223d3c0");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_19(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_16e98373");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36a4e374");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55e428e8");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_326a697e");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 7);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_18(player, npc))
        {
            quest_crowd_pleaser_manager_action_set_status_19(player, npc);
            string_id message = new string_id(c_stringFile, "s_b5c6a62d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_17(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_664ead2a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_16(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ffa16d1d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5a936e2e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2f3d57d6");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1fe0d27c");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 19);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_15(player, npc))
        {
            quest_crowd_pleaser_manager_action_set_status_16(player, npc);
            string_id message = new string_id(c_stringFile, "s_1a0fba4e");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_13(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3cdabd0e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1dc24a1");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3ef23ade");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 29);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_12(player, npc))
        {
            quest_crowd_pleaser_manager_action_set_status_13(player, npc);
            string_id message = new string_id(c_stringFile, "s_f29e46b6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7b58a334");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 35);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_11(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3d244a46");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_10(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ed9c9f8d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_847c3be4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6b02387e");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 38);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_9(player, npc))
        {
            quest_crowd_pleaser_manager_action_set_status_10(player, npc);
            string_id message = new string_id(c_stringFile, "s_cad51774");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_7(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b5131941");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1377703a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4365b273");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_99");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 46);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_6(player, npc))
        {
            quest_crowd_pleaser_manager_action_set_status_7(player, npc);
            string_id message = new string_id(c_stringFile, "s_2b8d253d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_5(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_810fffd0");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_937f2001");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_694f2644");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_414898b2");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 54);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_3(player, npc))
        {
            quest_crowd_pleaser_manager_action_set_status_4(player, npc);
            string_id message = new string_id(c_stringFile, "s_857ede28");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_status_1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_472f2a5e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4a1d2431");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 64);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition_has_completed_one(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_95037778");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dda95847");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c5a66e82");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 74);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_37b822c1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_crowd_pleaser_manager_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_499afb89");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                }
                utils.setScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId", 83);
                npcStartConversation(player, npc, "quest_crowd_pleaser_manager", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("quest_crowd_pleaser_manager"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
        if (branchId == 7 && quest_crowd_pleaser_manager_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && quest_crowd_pleaser_manager_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && quest_crowd_pleaser_manager_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && quest_crowd_pleaser_manager_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && quest_crowd_pleaser_manager_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && quest_crowd_pleaser_manager_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && quest_crowd_pleaser_manager_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && quest_crowd_pleaser_manager_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && quest_crowd_pleaser_manager_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && quest_crowd_pleaser_manager_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && quest_crowd_pleaser_manager_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && quest_crowd_pleaser_manager_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && quest_crowd_pleaser_manager_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && quest_crowd_pleaser_manager_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && quest_crowd_pleaser_manager_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && quest_crowd_pleaser_manager_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && quest_crowd_pleaser_manager_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && quest_crowd_pleaser_manager_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && quest_crowd_pleaser_manager_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && quest_crowd_pleaser_manager_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && quest_crowd_pleaser_manager_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && quest_crowd_pleaser_manager_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && quest_crowd_pleaser_manager_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && quest_crowd_pleaser_manager_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && quest_crowd_pleaser_manager_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && quest_crowd_pleaser_manager_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && quest_crowd_pleaser_manager_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && quest_crowd_pleaser_manager_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 85 && quest_crowd_pleaser_manager_handleBranch85(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && quest_crowd_pleaser_manager_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.quest_crowd_pleaser_manager.branchId");
        return SCRIPT_CONTINUE;
    }
}
