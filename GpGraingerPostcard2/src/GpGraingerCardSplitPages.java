import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

public class GpGraingerCardSplitPages {
	
	static String myPath="C:\\GP\\Grainger\\reorder";

	public static void main(String[] args) throws IOException {
		String inFileName = args[0];
		int splitPages = Integer.valueOf(args[1]);
		
		String fullFileName = myPath+"\\output\\"+inFileName;
				
        String fileOnly = fullFileName.substring(fullFileName.lastIndexOf("\\")+1);
        String fileNoExt = fullFileName.substring(fullFileName.lastIndexOf("\\")+1,fullFileName.lastIndexOf(".pdf"));
        String dirName  = fullFileName.substring(0, fullFileName.lastIndexOf("\\"));
        System.out.println(fileOnly);
        System.out.println(dirName);
        System.out.println(fileNoExt);
        
        String newDirName = dirName + "\\" + fileNoExt;
        File newDir = new File(newDirName);
        if (!newDir.exists()) {
            if (newDir.mkdir()) {
                System.out.println(newDirName + " - Directory is created!");
            } else {
                System.out.println(newDirName + " - Failed to create directory!");
            }
        } else {
        	System.out.println(newDirName + " - already exists");
        }
		// Loading an existing PDF document
		File file = new File(fullFileName);
		PDDocument document = PDDocument.load(file);

		// Instantiating Splitter class
		Splitter splitter = new Splitter();

		// Split pages at split count
		splitter.setSplitAtPage(splitPages);
		
		// splitting the pages of a PDF document
		List<PDDocument> Pages = splitter.split(document);

		// Creating an iterator
		Iterator<PDDocument> iterator = Pages.listIterator();

		String outputFile = "";
		// Saving each page as an individual document
		int i = 1;
		while (iterator.hasNext()) {
			PDDocument pd = iterator.next();
			outputFile=newDirName +"\\" + fileNoExt + "." + i++ + ".pdf";
			pd.save(outputFile);
			System.out.println("File Saved: "+outputFile);
		}
		System.out.println("Multiple PDF’s created");
		document.close();
	}
}