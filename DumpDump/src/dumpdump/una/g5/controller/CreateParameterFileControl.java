package dumpdump.una.g5.controller;

import dumpdump.una.g5.model.CreateParameterFile;
import java.util.Map;

/**
 *
 * @author Minor
 */
public class CreateParameterFileControl {
    CreateParameterFile cpf;

    public CreateParameterFileControl(String nameP, Map mapP) {
        cpf = new CreateParameterFile(nameP, mapP);
    }
    
    
}
