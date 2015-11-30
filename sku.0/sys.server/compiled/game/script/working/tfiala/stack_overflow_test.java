package script.working.tfiala;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class stack_overflow_test extends script.base_script
{
    public stack_overflow_test()
    {
    }
    public void doJavaStackOverflow1(int param) throws InterruptedException
    {
        int a = param * 5 - 3;
        doJavaStackOverflow2(a);
    }
    public void doJavaStackOverflow2(int param) throws InterruptedException
    {
        int b = param * 3 - 2;
        doJavaStackOverflow1(b);
    }
    public void doJavaOutOfMemory() throws InterruptedException
    {
        Vector x = new Vector();
        while (true)
        {
            x.add(new int[1000000]);
        }
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("doJavaStackOverflow"))
        {
            doJavaStackOverflow1(1);
        }
        else if (strText.equals("doJavaOutOfMemory"))
        {
            doJavaOutOfMemory();
        }
        return SCRIPT_CONTINUE;
    }
}
