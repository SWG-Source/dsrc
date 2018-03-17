package script.developer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.lang.Runtime;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import script.library.chat;
import script.developer.file_access;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class perforce extends script.base_script
{
    public perforce()
    {
    }
    public static String getIdOptions() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isGod(self))
        {
            return null;
        }
        String userId = getStringObjVar(self, "P4USER");
        String password = getStringObjVar(self, "P4PASSWD");
        return "-P " + password + " -u " + userId;
    }
    public static boolean isPerforceConfigured() throws InterruptedException
    {
        boolean result = false;
        obj_id self = getSelf();
        if (!isGod(self))
        {
            return false;
        }
        if (hasObjVar(self, "P4USER"))
        {
            String perforceUserId = getStringObjVar(self, "P4USER");
            if (perforceUserId != null && perforceUserId.length() > 0)
            {
                if (hasObjVar(self, "P4PASSWD"))
                {
                    String perforcePassword = getStringObjVar(self, "P4PASSWD");
                    if (perforcePassword != null && perforcePassword.length() > 0)
                    {
                        result = system_process.runAndGetExitCode("p4 -P " + perforcePassword + " -u " + perforceUserId + " opened") == 0;
                    }
                }
            }
        }
        return result;
    }
    public static boolean isLocked(String fileName) throws InterruptedException
    {
        if (!isGod(getSelf()))
        {
            return false;
        }
        boolean result = false;
        if (whoLocked(fileName) != null)
        {
            result = true;
        }
        return result;
    }
    public static String whoLocked(String fileName) throws InterruptedException
    {
        if (!isGod(getSelf()))
        {
            return null;
        }
        String lockFileName = fileName + ".scriptlock";
        String lockContents = file_access.readTextFile(lockFileName);
        return lockContents;
    }
    public static boolean openExistingFileForExclusiveEdit(String fileName) throws InterruptedException
    {
        boolean result = false;
        obj_id self = getSelf();
        if (!isGod(self))
        {
            return false;
        }
        String userId = getStringObjVar(self, "P4USER");
        String password = getStringObjVar(self, "P4PASSWD");
        if (isLocked(fileName))
        {
            String who = whoLocked(fileName);
            if (!who.equals(userId))
            {
                return false;
            }
            else 
            {
                return true;
            }
        }
        if (file_access.isWritable(fileName))
        {
            file_access.writeTextFile(fileName + ".scriptlock", userId);
            result = true;
        }
        else if (isPerforceConfigured())
        {
            if (system_process.runAndGetExitCode("p4 -P " + password + " -u " + userId + " edit " + fileName) == 0)
            {
                file_access.writeTextFile(fileName + ".scriptlock", userId);
                result = true;
            }
        }
        return result;
    }
    public static String[] opened() throws InterruptedException
    {
        if (!isPerforceConfigured())
        {
            setupPerforce();
            return null;
        }
        String[] result = null;
        obj_id self = getSelf();
        if (!isGod(self))
        {
            return null;
        }
        String userId = getStringObjVar(self, "P4USER");
        String password = getStringObjVar(self, "P4PASSWD");
        Runtime run = Runtime.getRuntime();
        if (run != null)
        {
            result = split(system_process.runAndGetOutput("p4 -P " + password + " -u " + userId + " opened"), '\n');
        }
        return result;
    }
    public static void setupPerforce() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isGod(self))
        {
            return;
        }
        int page = createSUIPage("/Script.perforceSetup", self, self);
        if (page >= 0)
        {
            subscribeToSUIEvent(page, sui_event_type.SET_onButton, "btnOk", "onPerforceSetupBtnOk");
            subscribeToSUIPropertyForEvent(page, sui_event_type.SET_onButton, "btnOk", "boxInputUserId.inputUserId", "LocalText");
            subscribeToSUIPropertyForEvent(page, sui_event_type.SET_onButton, "btnOk", "boxInputPassword.inputPassword", "LocalText");
            setSUIAssociatedObject(page, self);
            boolean showResult = showSUIPage(page);
            flushSUIPage(page);
        }
    }
    public static String change(int changeList) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isGod(self))
        {
            return null;
        }
        String result = null;
        String userId = getStringObjVar(self, "P4USER");
        String password = getStringObjVar(self, "P4PASSWD");
        Runtime run = Runtime.getRuntime();
        if (run != null)
        {
            String cmdLine = "p4 -P " + password + " -u " + userId + " change -o";
            if (changeList != 0)
            {
                cmdLine += " " + changeList;
            }
            system_process p = new system_process(cmdLine);
            p.waitFor();
            if (p.getExitValue() == 0)
            {
                result = p.getOutput();
            }
        }
        return result;
    }
    public static String where(String fileLocation) throws InterruptedException
    {
        return system_process.runAndGetOutput("p4 " + getIdOptions() + " where " + fileLocation);
    }
    public static boolean submit(String changeDescription, Vector resultText) throws InterruptedException
    {
        if (!isGod(getSelf()))
        {
            return false;
        }
        String[] changeLines = split(changeDescription, '\n');
        int iter = 0;
        boolean readingFiles = false;
        Vector fileLocks = new Vector();
        for (iter = 0; iter < changeLines.length; ++iter)
        {
            if (readingFiles)
            {
                try
                {
                    java.util.StringTokenizer st = new java.util.StringTokenizer(changeLines[iter], " \t\n");
                    String first = st.nextToken();
                    if (first != null)
                    {
                        String w = where(first);
                        String[] locs = split(w, ' ');
                        if (locs[2] != null)
                        {
                            String fileName = locs[2].trim();
                            fileName += ".scriptlock";
                            fileLocks.add(fileName);
                        }
                    }
                }
                catch(java.util.NoSuchElementException e)
                {
                }
            }
            else if (changeLines[iter].startsWith("Files:"))
            {
                readingFiles = true;
            }
        }
        obj_id self = getSelf();
        boolean result = false;
        String cmdLine = "p4 " + getIdOptions() + " submit -i";
        system_process p = new system_process(cmdLine);
        if (p != null)
        {
            p.putAndCloseInput(changeDescription);
            p.waitFor();
            if (p.getExitValue() == 0)
            {
                resultText.add(p.getOutput());
                for (iter = 0; iter < fileLocks.size(); ++iter)
                {
                    File f = new File(((String)fileLocks.get(iter)));
                    if (f.exists())
                    {
                        if (f.canWrite())
                        {
                            if (f.isFile())
                            {
                                if (!f.delete())
                                {
                                    resultText.add("failed to remove lock " + ((String)fileLocks.get(iter)) + ": could not delete file");
                                }
                            }
                            else 
                            {
                                resultText.add("failed to remove lock " + ((String)fileLocks.get(iter)) + ": not a file");
                            }
                        }
                        else 
                        {
                            resultText.add(((String)fileLocks.get(iter)) + ": not writeable");
                        }
                    }
                    else 
                    {
                        resultText.add("failed to remove file lock " + ((String)fileLocks.get(iter)) + ": it does not exist");
                    }
                }
            }
            else 
            {
                resultText.add(p.getOutput());
                resultText.add("Failed to execute " + cmdLine);
            }
        }
        else 
        {
            resultText.add("Could not execute " + cmdLine);
        }
        return result;
    }
    public static String diff(String fileSpec) throws InterruptedException
    {
        if (!isGod(getSelf()))
        {
            return null;
        }
        String result = null;
        if (fileSpec != null)
        {
            result = system_process.runAndGetOutput("p4 " + getIdOptions() + " diff -dU150 " + fileSpec);
        }
        else 
        {
            result = system_process.runAndGetOutput("p4 " + getIdOptions() + " diff -dU150");
        }
        return result;
    }
}
