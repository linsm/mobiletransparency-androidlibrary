/*
** Copyright (C) 2023  Johannes Kepler University Linz, Institute of Networks and Security
** Copyright (C) 2023  CDL Digidow <https://www.digidow.eu/>
**
** Licensed under the EUPL, Version 1.2 or – as soon they will be approved by
** the European Commission - subsequent versions of the EUPL (the "Licence").
** You may not use this work except in compliance with the Licence.
** 
** You should have received a copy of the European Union Public License along
** with this program.  If not, you may obtain a copy of the Licence at:
** <https://joinup.ec.europa.eu/software/page/eupl>
** 
** Unless required by applicable law or agreed to in writing, software
** distributed under the Licence is distributed on an "AS IS" basis,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the Licence for the specific language governing permissions and
** limitations under the Licence.
**
*/
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
