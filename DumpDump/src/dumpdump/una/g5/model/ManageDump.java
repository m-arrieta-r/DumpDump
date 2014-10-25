package dumpdump.una.g5.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageDump {
    StringBuffer output = new StringBuffer();
    public Boolean importDump() {
        try {
            Runtime runtime = Runtime.getRuntime();
            File uFile = new File("service/"+"conf1.par");            
            String binPath = uFile.getAbsolutePath();
            Process process;
            String statement = "IMP parfile="+binPath;
            System.out.println("statement "+statement);
            process = runtime.exec(statement);
            process.waitFor(10, TimeUnit.SECONDS);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
            System.err.println("--> "+output.toString());
            System.out.println("importancion "+process.exitValue());
            
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ManageDump.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ManageDump.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static Boolean createTask(String commandStr, String serviceName, 
            String binPathStr, String frec, String fecha, String t) {
        try {
            //process = runtime.exec("SCHTASKS /create /tn \"Dump0 Oracle\" /tr \"C:\\Users\\%UserName%\\test.bat\" /sc daily /sd 10/18/2014 /st 17:45");
            Runtime runtime = Runtime.getRuntime();
            Process process;
            File uFile = new File("service/"+binPathStr);            
            String binPath = uFile.getAbsolutePath();
            if(commandStr.contains("create")) {
                process = runtime.exec("SCHTASKS /Delete /tn \""+serviceName+"\" /F");
                process.waitFor();
                 System.out.println("eliminado tarea anterior "+(process.exitValue() == 0));
            }          
            String statement = "SCHTASKS /"+commandStr+" /tn \""+serviceName
                    +"\" /tr \""+binPath+"\" /sc "+frec+" /sd "+fecha+" /st "+t;
            System.out.println("statement --> "+statement);
            process = runtime.exec(statement);

            System.out.println("exportacion "+(process.exitValue() == 0));
            return true;
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ManageDump.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private void coutLogger(Process p) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
            System.err.println("--> "+output.toString());
        } catch (IOException ex) {
            Logger.getLogger(ManageDump.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Boolean createTask(String frec, String fecha, String t) {
        return createTask("create", "Dump Oracle", "export.bat", frec, fecha, t);
    }        
}
