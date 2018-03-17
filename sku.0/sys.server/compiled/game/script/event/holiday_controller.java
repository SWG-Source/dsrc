package script.event;

import script.dictionary;
import script.library.holiday;
import script.library.utils;
import script.obj_id;

public class holiday_controller extends script.base_script
{
    public holiday_controller()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("holidayEvent", "holiday_controller.OnInitialize planet initialized, holiday controller called.");
        messageTo(self, "halloweenServerStart", null, 600.0f, false);
        messageTo(self, "lifedayServerStart", null, 610.0f, false);
        messageTo(self, "lovedayServerStart", null, 615.0f, false);
        messageTo(self, "empiredayServerStart", null, holiday.EMPIRE_DAY_EVENT_START_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "halloweenServerStart", null, 720.0f, false);
        messageTo(self, "lifedayServerStart", null, 730.0f, false);
        messageTo(self, "lovedayServerStart", null, 735.0f, false);
        messageTo(self, "empiredayServerStart", null, (holiday.EMPIRE_DAY_EVENT_START_DELAY + 100), false);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isGod(player))
        {
            return SCRIPT_CONTINUE;
        }
        String halloweenRunning = getConfigSetting("GameServer", "halloween");
        String lifedayRunning = getConfigSetting("GameServer", "lifeday");
        String lovedayRunning = getConfigSetting("GameServer", "loveday");
        String empiredayRunning = getConfigSetting("GameServer", "empireday_ceremony");
        if (halloweenRunning != null && (halloweenRunning.equals("true") || halloweenRunning.equals("1")))
        {
            names[idx] = "holiday_event";
            attribs[idx] = "Halloween Event Running = True";
            idx++;
        }
        else 
        {
            names[idx] = "holiday_event";
            attribs[idx] = "Halloween Event Running = False";
            idx++;
        }
        if (lifedayRunning != null && (lifedayRunning.equals("true") || lifedayRunning.equals("1")))
        {
            names[idx] = "holiday_event";
            attribs[idx] = "Life Day Event Running = True";
            idx++;
        }
        else 
        {
            names[idx] = "holiday_event";
            attribs[idx] = "Life Day Event Running = False";
            idx++;
        }
        if (lovedayRunning != null && (lovedayRunning.equals("true") || lovedayRunning.equals("1")))
        {
            names[idx] = "holiday_event";
            attribs[idx] = "Love Day Event Running = True";
            idx++;
        }
        else 
        {
            names[idx] = "holiday_event";
            attribs[idx] = "Love Day Event Running = False";
            idx++;
        }
        if (empiredayRunning != null && (empiredayRunning.equals("true") || empiredayRunning.equals("1")))
        {
            names[idx] = "holiday_event";
            attribs[idx] = "Empire Day Event Running = True";
        }
        else 
        {
            names[idx] = "holiday_event";
            attribs[idx] = "Empire Day Event Running = False";
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isGod(speaker))
        {
            return SCRIPT_CONTINUE;
        }

        String universeWideEvents = getCurrentUniverseWideEvents();
        int halloween = universeWideEvents.indexOf("halloween");
        String halloweenRunning = getConfigSetting("GameServer", "halloween");
        int lifeday = universeWideEvents.indexOf("lifeday");
        String lifedayRunning = getConfigSetting("GameServer", "lifeday");
        int loveday = universeWideEvents.indexOf("loveday");
        String lovedayRunning = getConfigSetting("GameServer", "loveday");
        int empireday = universeWideEvents.indexOf("empireday_ceremony");
        String empiredayRunning = getConfigSetting("GameServer", "empireday_ceremony");

        switch (text) {
            case "halloweenStart":
                startHolidayEvent(speaker, "halloween", halloweenRunning, halloween);
                return SCRIPT_OVERRIDE;
            case "halloweenStop":
                stopHolidayEvent(speaker, "halloween", halloweenRunning, halloween);
                return SCRIPT_OVERRIDE;
            case "halloweenStartForReals":
                startHolidayEventForReals(speaker, "halloween", halloweenRunning);
                return SCRIPT_OVERRIDE;
            case "halloweenStopForReals":
                stopHolidayEventForReals(speaker, "halloween");
                return SCRIPT_OVERRIDE;
            case "lifedayStart":
                startHolidayEvent(speaker, "lifeday", lifedayRunning, lifeday);
                return SCRIPT_OVERRIDE;
            case "lifedayStop":
                stopHolidayEvent(speaker, "lifeday", lifedayRunning, lifeday);
                return SCRIPT_OVERRIDE;
            case "lifedayStartForReals":
                startHolidayEventForReals(speaker, "lifeday", lifedayRunning);
                return SCRIPT_OVERRIDE;
            case "lifedayStopForReals":
                stopHolidayEventForReals(speaker, "lifeday");
                return SCRIPT_OVERRIDE;
            case "lovedayStart":
                startHolidayEvent(speaker, "loveday", lovedayRunning, loveday);
                return SCRIPT_OVERRIDE;
            case "lovedayStop":
                stopHolidayEvent(speaker, "loveday", lovedayRunning, loveday);
                return SCRIPT_OVERRIDE;
            case "lovedayStartForReals":
                startHolidayEventForReals(speaker, "loveday", lovedayRunning);
                return SCRIPT_OVERRIDE;
            case "lovedayStopForReals":
                stopHolidayEventForReals(speaker, "loveday");
                return SCRIPT_OVERRIDE;
            case "empiredayStart":
                startHolidayEvent(speaker, "empireday_ceremony", empiredayRunning, empireday);
                return SCRIPT_OVERRIDE;
            case "empiredayStop":
                stopHolidayEvent(speaker, "empireday_ceremony", empiredayRunning, empireday);
                return SCRIPT_OVERRIDE;
            case "empiredayStartForReals":
                startHolidayEventForReals(speaker, "empireday_ceremony", empiredayRunning);
                return SCRIPT_OVERRIDE;
            case "empiredayStopForReals":
                stopHolidayEventForReals(speaker, "empireday_ceremony");
                return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    private void startHolidayEvent(obj_id speaker, String holidayName, String holidayRunning, int holidayStatus) throws InterruptedException
    {
        if (holidayRunning == null)
        {
            sendSystemMessageTestingOnly(speaker, "Server config is not marked as " + holidayName + " running");
            return;
        }
        if (holidayRunning.equals("true") || holidayRunning.equals("1"))
        {
            if (holidayStatus > -1)
            {
                sendSystemMessageTestingOnly(speaker, "Server says that " + holidayName + " is already running. If you are sure that it's not, say " + holidayName + "StartForReals");
            }
            if (holidayStatus < 0)
            {
                sendSystemMessageTestingOnly(speaker, holidayName + " started.");
                startUniverseWideEvent(holidayName);
            }
        }
    }
    private void startHolidayEventForReals(obj_id speaker, String holidayName, String holidayRunning) throws InterruptedException
    {
        if (holidayRunning == null)
        {
            sendSystemMessageTestingOnly(speaker, "Server config is not marked as " + holidayName + " running");
            return;
        }
        if (holidayRunning.equals("true") || holidayRunning.equals("1"))
        {
            sendSystemMessageTestingOnly(speaker, holidayName + " started.");
            startUniverseWideEvent(holidayName);
        }
    }
    private void stopHolidayEvent(obj_id speaker, String holidayName, String holidayRunning, int holidayStatus) throws InterruptedException
    {
        if (holidayRunning.equals("true") || holidayRunning.equals("1"))
        {
            sendSystemMessageTestingOnly(speaker, "Server config is marked as " + holidayName + " running. If you are sure that it should not be running anyway, say " + holidayName + "StopForReals");
        }
    }
    private void stopHolidayEventForReals(obj_id speaker, String holidayName) throws InterruptedException
    {
        sendSystemMessageTestingOnly(speaker, holidayName + " stopped.");
        stopUniverseWideEvent(holidayName);
    }
    public int halloweenServerStart(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("holidayEvent", "holiday_controller.halloweenServerStart Halloween event handler called.");
        String halloweenString = getCurrentUniverseWideEvents();
        int halloween = halloweenString.indexOf("halloween");
        String halloweenRunning = getConfigSetting("GameServer", "halloween");
        CustomerServiceLog("holidayEvent", "holiday_controller.halloweenServerStart halloweenString: " + halloweenString + " halloween: " + halloween + " halloweenRunning: " + halloweenRunning);
        if (halloweenRunning == null)
        {
            CustomerServiceLog("holidayEvent", "holiday_controller.halloweenServerStart Halloween event is either not running or not in the server configs.");
            if (halloween > -1)
            {
                stopUniverseWideEvent("halloween");
            }
        }
        else if (halloweenRunning.equals("true") || halloweenRunning.equals("1"))
        {
            if (halloween < 0)
            {
                if (!startUniverseWideEvent("halloween"))
                {
                    CustomerServiceLog("holidayEvent", "holiday_controller.halloweenServerStart startUniverseWideEvent reports FAILURE to start universe wide event.");
                }
                else 
                {
                    CustomerServiceLog("holidayEvent", "holiday_controller.halloweenServerStart startUniverseWideEvent reports SUCCESS starting universe wide event.");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int lifedayServerStart(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("holidayEvent", "holiday_controller.lifedayServerStart Life Day event handler called.");
        String lifedayString = getCurrentUniverseWideEvents();
        int lifeday = lifedayString.indexOf("lifeday");
        String lifedayRunning = getConfigSetting("GameServer", "lifeday");
        CustomerServiceLog("holidayEvent", "holiday_controller.lifedayServerStart lifedayString: " + lifedayString + " lifeday: " + lifeday + " lifedayRunning: " + lifedayRunning);
        if (lifedayRunning == null)
        {
            CustomerServiceLog("holidayEvent", "holiday_controller.lifedayServerStart event is either not running or not in the server configs.");
            if (lifeday > -1)
            {
                stopUniverseWideEvent("lifeday");
            }
        }
        else if (lifedayRunning.equals("true") || lifedayRunning.equals("1"))
        {
            if (lifeday < 0)
            {
                if (!startUniverseWideEvent("lifeday"))
                {
                    CustomerServiceLog("holidayEvent", "holiday_controller.lifedayServerStart startUniverseWideEvent reports FAILURE to start universe wide event.");
                }
                else 
                {
                    CustomerServiceLog("holidayEvent", "holiday_controller.lifedayServerStart startUniverseWideEvent reports SUCCESS starting universe wide event.");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int lovedayServerStart(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("holidayEvent", "holiday_controller.lovedayServerStart Love Day event handler called.");
        String lovedayString = getCurrentUniverseWideEvents();
        int loveday = lovedayString.indexOf("loveday");
        String lovedayRunning = getConfigSetting("GameServer", "loveday");
        CustomerServiceLog("holidayEvent", "holiday_controller.lovedayServerStart lovedayString: " + lovedayString + " loveday: " + loveday + " lovedayRunning: " + lovedayRunning);
        if (lovedayRunning == null)
        {
            CustomerServiceLog("holidayEvent", "holiday_controller.lovedayServerStart event is either not running or not in the server configs.");
            if (loveday > -1)
            {
                stopUniverseWideEvent("loveday");
            }
        }
        else if (lovedayRunning.equals("true") || lovedayRunning.equals("1"))
        {
            if (loveday < 0)
            {
                if (!startUniverseWideEvent("loveday"))
                {
                    CustomerServiceLog("holidayEvent", "holiday_controller.lovedayServerStart startUniverseWideEvent reports FAILURE to start universe wide event.");
                }
                else 
                {
                    CustomerServiceLog("holidayEvent", "holiday_controller.lovedayServerStart startUniverseWideEvent reports SUCCESS starting universe wide event.");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int empiredayServerStart(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("holidayEvent", "holiday_controller.empiredayServerStart Empire Day event handler called.");
        String empiredayString = getCurrentUniverseWideEvents();
        int empireday = empiredayString.indexOf("empireday_ceremony");
        String empiredayRunning = getConfigSetting("GameServer", "empireday_ceremony");
        CustomerServiceLog("holidayEvent", "holiday_controller.empiredayServerStart empiredayString: " + empiredayString + " empireday: " + empireday + " empiredayRunning: " + empiredayRunning);
        if (empiredayRunning == null)
        {
            CustomerServiceLog("holidayEvent", "holiday_controller.empiredayServerStart event is either not running or not in the server configs.");
            if (empireday > -1)
            {
                stopUniverseWideEvent("empireday_ceremony");
            }
        }
        else if (empiredayRunning.equals("true") || empiredayRunning.equals("1"))
        {
            CustomerServiceLog("holidayEvent", "holiday_controller.empiredayServerStart event is starting.");
            if (empireday < 0)
            {
                if (!startUniverseWideEvent("empireday_ceremony"))
                {
                    CustomerServiceLog("holidayEvent", "holiday_controller.empiredayServerStart startUniverseWideEvent reports FAILURE to start universe wide event.");
                }
                else 
                {
                    CustomerServiceLog("holidayEvent", "holiday_controller.empiredayServerStart startUniverseWideEvent reports SUCCESS starting universe wide event.");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
