import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.ArrayListMultimap;
import com.opencsv.CSVReader;

public class GpGraingerCardGen {

	public static void main(String[] args) throws IOException {
		//String tmpIndex = args[0]; 
		//String fileName = args[1];
		
		String strInputFile =        "C:\\GP\\Grainger\\testFiles\\output2_presorted.csv";

		File inputFile = new File(strInputFile);
		new File("C:\\GP\\Grainger\\testFiles\\output2.csv"); 
	    
		CSVReader readerInput = new CSVReader(new FileReader(inputFile), ',');

		new StringBuffer();
		
		ArrayListMultimap.create();
		
		String[] nextlineInput;


		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            // root element
            Element rootElement = doc.createElement("extractFile");
            doc.appendChild(rootElement);
            
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
            
            int recordCount = 0;

            while((nextlineInput=readerInput.readNext()) != null) {        
            
            	recordCount++;
 
    			System.out.println(recordCount);
  			
    			// record element
	            Element record = doc.createElement("record");
	            rootElement.appendChild(record);

	            
	            
	            Element endorse = doc.createElement("endorse");
	            endorse.appendChild(doc.createTextNode(nextlineInput[0]));
	            record.appendChild(endorse);
	            
	            Element cont_id = doc.createElement("cont_id");
	            cont_id.appendChild(doc.createTextNode(nextlineInput[1]));
	            record.appendChild(cont_id);
	            
	            Element traymark_ = doc.createElement("traymark_");
	            traymark_.appendChild(doc.createTextNode(nextlineInput[2]));
	            record.appendChild(traymark_);
	            
	            Element gpb_id = doc.createElement("gpb_id");
	            gpb_id.appendChild(doc.createTextNode(nextlineInput[3]));
	            record.appendChild(gpb_id);
	            
	            Element pkgmark_ = doc.createElement("pkgmark_");
	            pkgmark_.appendChild(doc.createTextNode(nextlineInput[4]));
	            record.appendChild(pkgmark_);
	            
	            Element imbarcode = doc.createElement("imbarcode");
	            imbarcode.appendChild(doc.createTextNode(nextlineInput[5]));
	            record.appendChild(imbarcode);
	            
	            Element imbdigits = doc.createElement("imbdigits");
	            imbdigits.appendChild(doc.createTextNode(nextlineInput[6]));
	            record.appendChild(imbdigits);
	            
	            Element pallet_id = doc.createElement("pallet_id");
	            pallet_id.appendChild(doc.createTextNode(nextlineInput[7]));
	            record.appendChild(pallet_id);
	            
	            Element accountNumber = doc.createElement("accountNumber");
	            accountNumber.appendChild(doc.createTextNode(nextlineInput[8]));
	            record.appendChild(accountNumber);
	            
	            Element mktactivityid = doc.createElement("mktactivityid");
	            mktactivityid.appendChild(doc.createTextNode(nextlineInput[9]));
	            record.appendChild(mktactivityid);
	            
	            Element fullname = doc.createElement("fullname");
	            fullname.appendChild(doc.createTextNode(nextlineInput[10]));
	            record.appendChild(fullname);
	            
	            Element companyname = doc.createElement("companyname");
	            companyname.appendChild(doc.createTextNode(nextlineInput[11]));
	            record.appendChild(companyname);
	            
	            Element state = doc.createElement("state");
	            state.appendChild(doc.createTextNode(nextlineInput[12]));
	            record.appendChild(state);
	            
	            Element titleslug = doc.createElement("titleslug");
	            titleslug.appendChild(doc.createTextNode(nextlineInput[13]));
	            record.appendChild(titleslug);
	            
	            Element zip = doc.createElement("zip");
	            zip.appendChild(doc.createTextNode(nextlineInput[14]));
	            record.appendChild(zip);
	            
	            Element zip4 = doc.createElement("zip4");
	            zip4.appendChild(doc.createTextNode(nextlineInput[15]));
	            record.appendChild(zip4);
	            
	            Element address1 = doc.createElement("address1");
	            address1.appendChild(doc.createTextNode(nextlineInput[16]));
	            record.appendChild(address1);
	            
	            Element address2 = doc.createElement("address2");
	            address2.appendChild(doc.createTextNode(nextlineInput[17]));
	            record.appendChild(address2);
	            
	            Element city = doc.createElement("city");
	            city.appendChild(doc.createTextNode(nextlineInput[18]));
	            record.appendChild(city);
	            
	            Element sku1 = doc.createElement("sku1");
	            sku1.appendChild(doc.createTextNode(nextlineInput[19]));
	            record.appendChild(sku1);
	            
	            Element image1 = doc.createElement("image1");
	            image1.appendChild(doc.createTextNode(saveImageFile("http://"+nextlineInput[20])));
	            record.appendChild(image1);
	            
	            Element gisdesc1 = doc.createElement("gisdesc1");
	            gisdesc1.appendChild(doc.createTextNode(nextlineInput[21]));
	            record.appendChild(gisdesc1);
	            	            
	            Element shortdesc1 = doc.createElement("shortdesc1");
	            shortdesc1.appendChild(doc.createTextNode(nextlineInput[22]));
	            record.appendChild(shortdesc1);
	            	            
	            Element sku2 = doc.createElement("sku2");
	            sku2.appendChild(doc.createTextNode(nextlineInput[23]));
	            record.appendChild(sku2);
	            
	            Element image2 = doc.createElement("image2");
	            image2.appendChild(doc.createTextNode(saveImageFile("http://"+nextlineInput[24])));
	            record.appendChild(image2);
	            
	            Element gisdesc2 = doc.createElement("gisdesc2");
	            gisdesc2.appendChild(doc.createTextNode(nextlineInput[25]));
	            record.appendChild(gisdesc2);
	            	            
	            Element shortdesc2 = doc.createElement("shortdesc2");
	            shortdesc2.appendChild(doc.createTextNode(nextlineInput[26]));
	            record.appendChild(shortdesc2);
	            
	            Element sku3 = doc.createElement("sku3");
	            sku3.appendChild(doc.createTextNode(nextlineInput[27]));
	            record.appendChild(sku3);
	            
	            Element image3 = doc.createElement("image3");
	            image3.appendChild(doc.createTextNode(saveImageFile("http://"+nextlineInput[28])));
	            record.appendChild(image3);
	            
	            Element gisdesc3 = doc.createElement("gisdesc3");
	            gisdesc3.appendChild(doc.createTextNode(nextlineInput[29]));
	            record.appendChild(gisdesc3);
	            	            
	            Element shortdesc3 = doc.createElement("shortdesc3");
	            shortdesc3.appendChild(doc.createTextNode(nextlineInput[30]));
	            record.appendChild(shortdesc3);
	            
	            Element seq = doc.createElement("seq");
	            seq.appendChild(doc.createTextNode(nextlineInput[31]));
	            record.appendChild(seq);
           
            }

           
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\GP\\Grainger\\testFiles\\records2.xml"));
            transformer.transform(source, result);
   	    
    	    readerInput.close();
            
        } catch (Exception e) {
        	System.out.println(e);
		}
      

        System.out.println("DONE");
		
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
            	String outFileName = "C:\\GP\\Grainger\\itemImages\\" + imageName + ".jpg";
                final InputStream is = urlConnection.getInputStream();
                final OutputStream os = new FileOutputStream(outFileName);

                byte[] b = new byte[2048];
                int length;

                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }

                is.close();
                os.close();

                //System.out.println(outFileName);
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
