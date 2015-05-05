package nelson.umkc.edu.bcctexter;

import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//referenced http://www.regexplanet.com/cookbook/phone-number-nanp/index.html

/**
 * Created by Zroh on 2/20/2015.
 */
public class messageController {

// uses the default instance of the SmsManager to send messages
    public void sendSms(String number, String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


//performs a regex match check on the phone number
    public Boolean isValidPhoneNo(String str) {
        String tempNum = "";
        Pattern regExPhoneNo = Pattern.compile("\\D*([2-9]\\d{2})(\\D*)([2-9]\\d{2})(\\D*)(\\d{4})\\D*");
        tempNum = getPhoneNumber(str, false);
        Matcher matchedNumber = regExPhoneNo.matcher(tempNum);
        return (matchedNumber.matches());
    }

//set boolean value to false to return phone number & true for name
    public String getPhoneNumber(String contact, boolean name){
        String tempName = "", tempNum = "";
        boolean found = false;
        for (int i = 0; i < contact.length(); i++) {
            if (contact.charAt(i) != '=' && !found) {
                tempName += contact.charAt(i);
            } else if (contact.charAt(i) == '=') {
                found = true;
                if (name)
                    return tempName;
            } else if (found && !name) {
                tempNum += contact.charAt(i);
            }
        }
        tempNum = tempNum.replaceAll("[^\\d.]", "");
        return tempNum;
    }
//returns only the first name of the contact for sending message using trigger
    public String getName(String contact){
        String fName = "";
        boolean equalFound = false, spaceFound = false;
        for (int i = 0; i < contact.length(); i ++){
            switch (contact.charAt(i)){
                case ' ': spaceFound = true;
                    break;
                case '=': equalFound = true;
                    break;
                default:
                    break;
            }
            if (!spaceFound && !equalFound){
                fName += contact.charAt(i);
            }
        }
        return fName;
    }

    public void processMessage(String triggerText, ArrayList<String> nameNo,
                               String message, Boolean mms){
        triggerController tC = new triggerController();
        String phoneNumber = "", name = "", processedMessage = "";
        Iterator it = nameNo.iterator();
        int errors = 0;
        while (it.hasNext()) {
            String contactInfo = "";
            contactInfo = (String)it.next();
            phoneNumber = getPhoneNumber(contactInfo, false);//false for not name
            name = getName(contactInfo);//first name
            processedMessage = tC.processMessage(message, name, triggerText);
            if (!mms)
                sendSms(phoneNumber, processedMessage);
            else{
                Main main = new Main();
               // main.sendMMS(phoneNumber, processedMessage);
            }
            phoneNumber = ""; name = "";
            it.remove();
        }
    }

}
