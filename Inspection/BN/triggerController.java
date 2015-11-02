package nelson.umkc.edu.bcctexter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zroh on 2/21/2015.
 */
public class triggerController {

    public triggerController(){}
    public String processMessage(String sMessage, String name,
                                 String triggerIn){
        Pattern trigger = Pattern.compile(triggerIn);
        Matcher matchTrig = trigger.matcher(sMessage);
        if (matchTrig.find()){
            sMessage = matchTrig.replaceAll(name);
        }
        return sMessage;
    }
}
