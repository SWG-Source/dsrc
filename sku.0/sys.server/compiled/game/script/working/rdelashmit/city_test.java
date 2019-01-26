package script.working.rdelashmit;

import script.dictionary;
import script.location;
import script.obj_id;

import java.util.StringTokenizer;

public class city_test extends script.base_script
{
    public city_test()
    {
    }
    public obj_id getPlayerByName(obj_id actor, String name) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(name);
        String compareName = toLower(st.nextToken());
        obj_id[] players = getPlayerCreaturesInRange(actor, 128.0f);
        if (players != null)
        {
            for (obj_id player : players) {
                StringTokenizer st2 = new StringTokenizer(getName(player));
                String playerName = toLower(st2.nextToken());
                if (compareName.equals(playerName)) {
                    return player;
                }
            }
        }
        return obj_id.NULL_ID;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "city test script attached.");
        return SCRIPT_CONTINUE;
    }
    public int OnCityChanged(obj_id self, int oldCityId, int newCityId) throws InterruptedException
    {
        if (oldCityId != 0)
        {
            debugSpeakMsg(self, "Leaving city " + cityGetName(oldCityId));
        }
        if (newCityId != 0)
        {
            debugSpeakMsg(self, "Entering city " + cityGetName(newCityId));
        }
        return SCRIPT_CONTINUE;
    }
    public void registerCloningFacilities() throws InterruptedException
    {
        int[] cityIds = getAllCityIds();
        for (int cityId : cityIds) {
            location cityLoc = cityGetLocation(cityId);
            location cloneLoc = cityGetCloneLoc(cityId);
            location cloneRespawn = cityGetCloneRespawn(cityId);
            obj_id cloneId = cityGetCloneId(cityId);
            if (isIdValid(cloneId)) {
                obj_id planet = getPlanetByName(cityLoc.area);
                if (isIdValid(planet)) {
                    dictionary params = new dictionary();
                    params.put("id", cloneId);
                    params.put("loc", cloneLoc);
                    params.put("respawn", cloneRespawn);
                    messageTo(planet, "registerCloningFacility", params, 1.0f, false);
                }
            }
        }
    }
    public void dumpInfo(obj_id self, int cityId) throws InterruptedException
    {
        debugSpeakMsg(self, "City name=" + cityGetName(cityId) + " hall=" + cityGetCityHall(cityId) + " loc=" + cityGetLocation(cityId) + " radius=" + cityGetRadius(cityId) + " leader=" + cityGetLeader(cityId) + " incomeTax=" + cityGetIncomeTax(cityId) + " propertyTax=" + cityGetPropertyTax(cityId) + " salesTax=" + cityGetSalesTax(cityId) + " cloneLoc=" + cityGetCloneLoc(cityId) + " cloneRespawn=" + cityGetCloneRespawn(cityId) + " cloneId=" + cityGetCloneId(cityId));
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (speaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (text.startsWith("city "))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            st.nextToken();
            int cityId = getCityAtLocation(getLocation(speaker), 0);
            String tok = st.nextToken();
            switch (tok) {
                case "create":
                    createCity(st.nextToken(), speaker, getLocation(speaker), 128, speaker, 1, 2, 3, new location(), 0, false, new location(), new location(), obj_id.NULL_ID);
                    break;
                case "destroy":
                    removeCity(cityId);
                    break;
                case "info":
                    dumpInfo(speaker, getCityAtLocation(getLocation(speaker), 0));
                    break;
                case "militia":
                    citySetCitizenInfo(cityId, speaker, getName(speaker), speaker, 1);
                    break;
                case "citizen":
                    citySetCitizenInfo(cityId, speaker, getName(speaker), speaker, 0);
                    break;
                case "register":
                    registerCloningFacilities();
                    break;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
