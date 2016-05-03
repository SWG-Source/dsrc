package script.faction_perk.hq;

import script.dictionary;
import script.library.hq;
import script.obj_id;

public class objective_manager extends script.base_script
{
    public objective_manager()
    {
    }
    private static final int VULN_DELAY = 14400;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "isPvpBase"))
        {
            messageTo(self, "handlePveVulnerability", null, VULN_DELAY, true);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleLoadVulnerability", null, VULN_DELAY, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "isPvpBase"))
        {
            messageTo(self, "handlePveVulnerability", null, VULN_DELAY, true);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleLoadVulnerability", null, VULN_DELAY, true);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        hq.unloadVulnerability(self);
        return SCRIPT_CONTINUE;
    }
    public int handlePveVulnerability(obj_id self, dictionary params) throws InterruptedException
    {
        hq.loadFullTimeVulnerability(self);
        return SCRIPT_CONTINUE;
    }
    public int handleLoadVulnerability(obj_id self, dictionary params) throws InterruptedException
    {
        hq.loadVulnerability(self);
        return SCRIPT_CONTINUE;
    }
    public int handleUnloadVulnerability(obj_id self, dictionary params) throws InterruptedException
    {
        hq.unloadVulnerability(self);
        return SCRIPT_CONTINUE;
    }
    public int handleFlagVulnerabilityChange(obj_id self, dictionary params) throws InterruptedException
    {
        int status = params.getInt("status");
        LOG("hq", "faction_perk.hq.objective_manager -- handleFlagVulnerability: message received. Firing hq.planetMapVulnerabilityStatusChange(self, status) immediately with status of: " + status);
        hq.planetMapVulnerabilityStatusChange(self, status);
        return SCRIPT_CONTINUE;
    }
}
