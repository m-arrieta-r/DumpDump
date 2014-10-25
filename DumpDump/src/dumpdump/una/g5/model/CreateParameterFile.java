package dumpdump.una.g5.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Minor
 */
public class CreateParameterFile {
    
    private final File file;
    private final Map<String,String> map;
    
    public CreateParameterFile(String fileName, Map<String,String> mapP) {
        file = new File(fileName);
        map = mapP;
        init();
    }
    
    private void init() {
        createFile();
        writeParameters();
    }
    
    private Boolean createFile() {
        try {
            file.createNewFile();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CreateParameterFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Boolean writeParameters(){
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw;
            bw = new BufferedWriter(fw);
            for(Map.Entry<String, String> entry : map.entrySet()) {
                bw.write(entry.getKey()+"="+entry.getValue()+"\n");
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(CreateParameterFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                fw.close();
                return true;
            } catch (IOException ex) {
                Logger.getLogger(CreateParameterFile.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    }
}
