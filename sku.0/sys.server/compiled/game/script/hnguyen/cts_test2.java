package script.hnguyen;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.StringTokenizer;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.EOFException;

public class cts_test2 extends script.base_script
{
    public cts_test2()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.startsWith("dctsi2 "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if ((st.countTokens() == 2) || (st.countTokens() == 3))
            {
                String command = st.nextToken();
                String file = st.nextToken();
                boolean onServer = true;
                boolean onClient = false;
                if (st.countTokens() == 1)
                {
                    String option = st.nextToken();
                    if (option.equals("clientAlso"))
                    {
                        onClient = true;
                    }
                    else if (option.equals("clientOnly"))
                    {
                        onServer = false;
                        onClient = true;
                    }
                }
                dictionary characterData = new dictionary();
                characterData.put("withItems", true);
                characterData.put("allowOverride", true);
                Object[] triggerParams = new Object[2];
                triggerParams[0] = self;
                triggerParams[1] = characterData;
                try
                {
                    int err = script_entry.runScripts("OnUploadCharacter", triggerParams);
                    if (err == SCRIPT_CONTINUE)
                    {
                        byte[] dictPacked = characterData.pack();
                        if (onServer)
                        {
                            FileOutputStream fos = null;
                            DataOutputStream dos = null;
                            try
                            {
                                fos = new FileOutputStream(file);
                                dos = new DataOutputStream(fos);
                                for (int i = 0; i < dictPacked.length; ++i)
                                {
                                    dos.writeByte(dictPacked[i]);
                                }
                                fos.close();
                                fos = null;
                                long fileSize = (new File(file)).length();
                                sendSystemMessageTestingOnly(self, "dictionary written to " + file + " file size=" + fileSize);
                            }
                            catch(IOException ioe)
                            {
                                sendSystemMessageTestingOnly(self, "IO error: " + ioe);
                            }
                            try
                            {
                                if (fos != null)
                                {
                                    fos.close();
                                }
                            }
                            catch(IOException ioe2)
                            {
                                sendSystemMessageTestingOnly(self, "IO error on closing file: " + ioe2);
                            }
                        }
                        if (onClient)
                        {
                            saveBytesOnClient(self, file, dictPacked);
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "trigger OnUploadCharacter() *DIDN'T* return SCRIPT_CONTINUE");
                    }
                }
                catch(Throwable t)
                {
                    sendSystemMessageTestingOnly(self, "trigger OnUploadCharacter() resulted in exception: " + t);
                }
            }
        }
        else if (strText.startsWith("actsi2 "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                removeObjVar(self, "hasTransferred");
                String command = st.nextToken();
                String file = st.nextToken();
                byte[] dictRead = null;
                FileInputStream fis = null;
                DataInputStream dis = null;
                try
                {
                    long fileSize = (new File(file)).length();
                    sendSystemMessageTestingOnly(self, "reading dictionary " + file + " file size=" + fileSize);
                    fis = new FileInputStream(file);
                    dis = new DataInputStream(fis);
                    dictRead = new byte[(int)(fileSize)];
                    int index = 0;
                    while (true)
                    {
                        dictRead[index++] = dis.readByte();
                    }
                }
                catch(EOFException eof)
                {
                    if (dictRead != null)
                    {
                        Object[] triggerParams = new Object[2];
                        triggerParams[0] = self;
                        triggerParams[1] = dictRead;
                        int err = script_entry.runScripts("OnDownloadCharacter", triggerParams);
                        if (err == SCRIPT_CONTINUE)
                        {
                            sendSystemMessageTestingOnly(self, "trigger OnDownloadCharacter() return SCRIPT_CONTINUE");
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, "trigger OnDownloadCharacter() *DIDN'T* return SCRIPT_CONTINUE");
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "couldn't read dictionary");
                    }
                }
                catch(IOException ioe)
                {
                    sendSystemMessageTestingOnly(self, "IO error: " + ioe);
                }
                try
                {
                    if (fis != null)
                    {
                        fis.close();
                    }
                }
                catch(IOException ioe2)
                {
                    sendSystemMessageTestingOnly(self, "IO error on closing file: " + ioe2);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
