package at.jku.ins.mobiletransparency;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageService {

    final String fileName = "transparency.store";

    public void writeTrustedRoot(Context context, String trustedRootHash) {
        context.deleteFile(fileName);
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(trustedRootHash.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readTrustedRoot(Context context) {
        try (FileInputStream fis = context.openFileInput(fileName)) {
            int c;
            String result="";
            while( (c = fis.read()) != -1){
                result = result + (char) c;
            }
            fis.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
