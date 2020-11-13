import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.opencsv.CSVReader;

public class GpGraingerDownloadImages {

	static String myPath="C:\\GP\\Grainger\\reorder";
	
	public static void main(String[] args) throws IOException {
		
		String presortFilePath = myPath+"\\presort";

		File presortFiles = new File(presortFilePath); 

		ArrayList<String> imageArrayList = new ArrayList<String>();
		
		
		String[] presortFileList;
		presortFileList = presortFiles.list();
		
		
		if (presortFileList.length != 1) {
			System.out.println("Input Files error. Only 1 file may be in the presort directory \n");
			System.out.println(presortFileList.length +" - " + presortFilePath);
			System.exit (0);
		}		
		
		String presortFileName = myPath + "\\presort\\" + presortFileList[0];

		String imageDirName = myPath + "\\images";
		File imageDir = new File(imageDirName);
		
		if (!imageDir.exists()) {
			imageDir.mkdir();
		}
		
		@SuppressWarnings("deprecation")
		CSVReader readerInput = new CSVReader(new FileReader(presortFileName), ',');
		
		System.out.println("File: " + presortFileName);
		String[] nextlineInput;

		try {
			
            int recordCount = 0;

            while((nextlineInput=readerInput.readNext()) != null) {        
            
            	recordCount++;
 
    			System.out.println("Gathering images for " + nextlineInput[8] + ": " +  recordCount);

    			if (!imageArrayList.contains(nextlineInput[20])) {
    				imageArrayList.add(nextlineInput[20]);
    			} 

    			if (!imageArrayList.contains(nextlineInput[24])) {
    				imageArrayList.add(nextlineInput[24]);
    			}
    			if (!imageArrayList.contains(nextlineInput[28])) {
    				imageArrayList.add(nextlineInput[28]);
    			}
    			
            }
           
    	    readerInput.close();
    	    
    	    recordCount=0;
    	    
    	    Iterator<String> iter = imageArrayList.iterator();
    	    while (iter.hasNext()) {
    	    	System.out.print(recordCount + " - ");
    	    	saveImageFile("http://"+iter.next());
    	    	recordCount++;
    	    }
    	    
        } catch (Exception e) {
        	System.out.println(e);
		}
      

        System.out.println("DONE");
        System.out.println("File: " + presortFileName);
	}
	

	public static String saveImageFile(String imageUrl) throws IOException {
    	
        URL url = new URL(imageUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        urlConnection.setInstanceFollowRedirects(true);

        String patternSplit=".*\\/Grainger\\/(.*)\\?.*";	        
        Pattern pSplit = Pattern.compile(patternSplit, Pattern.MULTILINE);

        // Matcher refers to the actual text where the pattern will be found
        Matcher mSplit = pSplit.matcher(imageUrl);
        String imageName = null;
        if (mSplit.find()) {
            imageName = mSplit.group(1);
        }

        if (imageName == null) {

        	return "";
        } else {
            try {
            	//System.out.println("imageUrl: "+imageUrl);
            	String outFileName = myPath+"\\images\\" + imageName + ".jpg";
            	
                final InputStream is = urlConnection.getInputStream();
                final OutputStream os = new FileOutputStream(outFileName);

                byte[] b = new byte[2048];
                int length;

                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }

                is.close();
                os.close();

                System.out.println("Downloading "+imageUrl+ " to " + outFileName);
                return outFileName;
            	
            } catch (Exception e) {
            	System.out.println("Error Code: " +urlConnection.getErrorStream());
            	System.out.println(e);
            	//e.printStackTrace();
            	return "Error Code";
            }        	
        }

     }    

	
	
}
