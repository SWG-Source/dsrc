package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class getgoodlocation_test extends script.base_script
{
    public getgoodlocation_test()
    {
    }
    public void end(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Testing of GetGoodLocation has begun");
    }
    public void start(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Testing of GetGoodLocation has begun");
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        start(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        end(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        String[] words = split(text, ' ');
        if (words[0].equals("getgoodloc"))
        {
            getGoodLoc(self);
        }
        if (words[0].equals("getheight"))
        {
            getHeight(self);
        }
        if (words[0].equals("dropobjectstest"))
        {
            dropTestObjects(self, 100);
        }
        return SCRIPT_CONTINUE;
    }
    public void getGoodLoc(obj_id self) throws InterruptedException
    {
        location sll = new location(getLocation(self));
        sll.x -= 8;
        sll.z -= 8;
        location sur = new location(getLocation(self));
        sur.x += 8;
        sur.z += 8;
        location goodLocation = getGoodLocation(8, 8, sll, sur, false, true);
        if (goodLocation != null)
        {
            debugSpeakMsg(self, goodLocation.x + ", " + goodLocation.y + ", " + goodLocation.z + " is a good location.");
        }
        else 
        {
            debugSpeakMsg(self, "Failed");
        }
    }
    public void dropTestObjects(obj_id self, int numObjects) throws InterruptedException
    {
        int areaSizeX = 8;
        int areaSizeY = 8;
        location sll = new location(getLocation(self));
        sll.x -= 100;
        sll.z -= 100;
        location sur = new location(getLocation(self));
        sur.x += 100;
        sur.z += 100;
        for (int i = 0; i < numObjects; ++i)
        {
            location goodLocation = getGoodLocation(areaSizeX, areaSizeY, sll, sur, false, true);
            if (goodLocation != null)
            {
                createObject("object/tangible/furniture/frn_all_table_s01.iff", goodLocation);
            }
            else 
            {
                debugSpeakMsg(self, "Failed to find good location.");
            }
        }
    }
    public void getHeight(obj_id self) throws InterruptedException
    {
        float result = getHeightAtLocation(0, 0);
        debugSpeakMsg(self, "" + result);
    }
}
