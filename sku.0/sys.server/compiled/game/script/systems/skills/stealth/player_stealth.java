package script.systems.skills.stealth;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.jedi;
import script.library.combat;
import script.library.buff;
import script.library.colors;
import script.library.ai_lib;
import script.library.stealth;
import script.library.group;
import script.library.prose;
import script.library.money;

public class player_stealth extends script.systems.combat.combat_base
{
    public player_stealth()
    {
    }
    public static final java.text.NumberFormat floatFormat = new java.text.DecimalFormat("#####");
    public int smokeBombTimerExpired(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "sp_smoke_bomb"))
        {
            utils.removeScriptVar(self, "sp_smoke_bomb");
        }
        return SCRIPT_CONTINUE;
    }
    public int withoutTraceTimerExpired(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "sp_without_a_trace"))
        {
            utils.removeScriptVar(self, "sp_without_a_trace");
        }
        String invisBuff = stealth.getInvisBuff(self);
        if (invisBuff == null || invisBuff.length() < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (invisBuff.equals("invis_sp_buff_invis_notrace_1"))
        {
            buff.removeBuff(self, "invis_sp_buff_invis_notrace_1");
            buff.applyBuff(self, "invis_sp_buff_invis_1");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgMotionSensorTripped(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id sensor = params.getObjId("sensor");
        obj_id breacher = params.getObjId("breacherId");
        String breacherName = params.getString("breacherName");
        boolean wasFiltered = params.getBoolean("wasFiltered");
        location loc = params.getLocation("sensorLoc");
        String planetString = utils.packStringId(new string_id("planet_n", loc.area));
        String locString = planetString.startsWith("Kashyyyk") || planetString.startsWith("Mustafar") ? " some location " : floatFormat.format(loc.x) + ", " + floatFormat.format(loc.z);
        prose_package pp = null;
        if (!wasFiltered)
        {
            pp = prose.getPackage(new string_id("spam", "sensor_tripped"));
            pp = prose.setTT(pp, locString);
            pp = prose.setTU(pp, planetString);
        }
        else 
        {
            pp = prose.getPackage(new string_id("spam", "sensor_tripped_filtered"));
            pp = prose.setTT(pp, locString);
            pp = prose.setTU(pp, planetString);
            pp = prose.setTO(pp, breacherName);
        }
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int msgTrackingBeaconLocationRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id sendingPlayer = params.getObjId("sendingPlayer");
        obj_id sendingBeacon = params.getObjId("sendingBeacon");
        String myName = getName(self);
        dictionary dic = new dictionary();
        dic.put("location", getLocation(self));
        dic.put("sendingTarget", self);
        dic.put("name", myName);
        messageTo(sendingBeacon, "msgTrackingBeaconLocationReply", dic, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int invisibilityUpkeep(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, stealth.INVIS_UPKEEP_MSG_DISPATCHED);
        boolean perpetuate = false;
        String invisBuff = stealth.getInvisBuff(self);
        if (invisBuff == null || invisBuff.length() < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int invis = getStringCrc(toLower(invisBuff));
        switch (invis)
        {
            case (342850470):
            perpetuate = stealth.checkForceCloakUpkeep(self);
            break;
            case (815407714):
            break;
            case (-1923109470):
            break;
            case (-121504680):
            break;
            case (1206342977):
            perpetuate = stealth.checkUrbanStealthUpkeep(self);
            break;
            case (-50098035):
            perpetuate = stealth.checkWildernessStealthUpkeep(self);
            break;
            case (2068822907):
            perpetuate = stealth.checkSpyStealthUpkeep(self);
            break;
            default:
            break;
        }
        if (perpetuate && !utils.hasScriptVar(self, stealth.INVIS_UPKEEP_MSG_DISPATCHED))
        {
            utils.setScriptVar(self, stealth.INVIS_UPKEEP_MSG_DISPATCHED, 1);
            messageTo(self, "invisibilityUpkeep", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleStealCashSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        int amt = params.getInt(money.DICT_AMOUNT);
        withdrawCashFromBank(self, amt, "handleStealCashFinal", "handleStealCashFail", params);
        return SCRIPT_CONTINUE;
    }
    public int handleStealCashFail(obj_id self, dictionary params) throws InterruptedException
    {
        int amt = params.getInt(money.DICT_AMOUNT);
        CustomerServiceLog("stealing", "%TU failed to steal " + amt + " credits.", self);
        return SCRIPT_CONTINUE;
    }
    public int handleStealCashFinal(obj_id self, dictionary params) throws InterruptedException
    {
        int amt = params.getInt(money.DICT_AMOUNT);
        prose_package pp = prose.getPackage(new string_id("spam", "stole_cash"), amt);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
}
