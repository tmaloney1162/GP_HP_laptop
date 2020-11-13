
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class GpGraingerCardMerge2up {

	static String myPath = "C:\\GP\\Grainger\\reorder";

	public static void main(String[] args) throws IOException {
		String fileIdentifier = args[0];
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HH.mm.ss");
		LocalDateTime now = LocalDateTime.now();

		String timeStamp = dtf.format(now);
		System.out.println(timeStamp);

		String[] skuFileList;
		String[] extractFileList;
		String[] tabFileList;

		String fullSkuFilePath = myPath + "\\inputSKU";
		String fullExtractFilePath = myPath + "\\inputExtract";
		String fullTabFilePath = myPath + "\\inputTab";

		File extractFiles = new File(fullExtractFilePath);
		File skuFiles = new File(fullSkuFilePath);
		File tabFiles = new File(fullTabFilePath);

		skuFileList = skuFiles.list();
		extractFileList = extractFiles.list();
		tabFileList = tabFiles.list();

		if (skuFileList.length != 1 || extractFileList.length != 1 || tabFileList.length != 1) {
			System.out.println("Input Files error. Only 1 file may be in the input directories \n");
			System.out.println(skuFileList.length + " - " + fullSkuFilePath);
			System.out.println(extractFileList.length + " - " + fullExtractFilePath);
			System.out.println(tabFileList.length + " - " + fullTabFilePath);
			System.exit(0);
		}

		// new File(<directory path>).list().length

		String strSkuListFile = fullSkuFilePath + "\\" + skuFileList[0];
		String strLettershopFile = fullExtractFilePath + "\\" + extractFileList[0];
		String strTabFile = fullTabFilePath + "\\" + tabFileList[0];
		String strOutputFile = myPath + "\\output\\" + fileIdentifier + "_merged_" + timeStamp + ".csv";

		File fileSkuList = new File(strSkuListFile);
		File fileLetterShop = new File(strLettershopFile);
		File tabFileName = new File(strTabFile);
		File fileOutput = new File(strOutputFile);

		@SuppressWarnings("deprecation")
		CSVReader readerTab = new CSVReader(new FileReader(tabFileName), '\t');

		StringBuffer errorBuffer = new StringBuffer();

		ListMultimap<String, String> multimapSkuList = ArrayListMultimap.create();
		Map<String, String> hashmapLettershop = new HashMap<>();
		Map<String, String> hashmapTab = new HashMap<>();
		TreeMap<String, String> treemapLettershop = new TreeMap<>();

		// create FileWriter object with file as parameter
		FileWriter outputfile = new FileWriter(fileOutput);
		CSVWriter writer = new CSVWriter(outputfile, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
		List<String[]> csvData = new ArrayList<String[]>();

		/*
		 * 1 - Load Sku List File 2 - Load Lettershop File 3 - Load Tab file
		 * 
		 * Loop thru Lettershop Hashmap - Get SKUs from SkuList Multimap - For each SKU,
		 * get info from Tab hashmap
		 */

		// Load Sku List File - multimapSkulist
		System.out.println("Loading Sku List File - " + strSkuListFile);
		FileInputStream excelSkuList = new FileInputStream(fileSkuList);
		Workbook workbookSkuList = new XSSFWorkbook(excelSkuList);
		workbookSkuList.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
		Sheet sheetSkuList = workbookSkuList.getSheetAt(0);

		String strSkuSkuId, strSkuAccount, strSkuContact, strSkuShortDesc, strSkuGisDesc, allSkuFields = "";
		String strSimSku, strSimShortDesc, strSimGisDesc, strAbdSku, strAbdShortDesc, strAbdGisDesc, strAbdImage, strSimImage = "";

		for (Row r : sheetSkuList) {
			try {
					strSkuAccount = getCellValue(0, r);
					strSkuContact = getCellValue(1, r);
					strSimSku = getCellValue(6, r);
					strSimShortDesc = getCellValue(12, r);
					strSimGisDesc = getCellValue(13, r);
					strAbdSku = getCellValue(2, r);
					strAbdShortDesc = getCellValue(10, r);
					strAbdGisDesc = getCellValue(11, r);
					allSkuFields = String.join("|", strSkuAccount, strSkuContact, strSimSku, strSimShortDesc,
							strSimGisDesc, strAbdSku, strAbdShortDesc, strAbdGisDesc);

				multimapSkuList.put(strSkuAccount, allSkuFields);

			} catch (IOException e) {
				System.out.println(e);
			}
		}

		// Load Lettershop File - hashmapLettershop
		System.out.println("Loading Lettershop File - " + strLettershopFile);
		FileInputStream excelLettershop = new FileInputStream(fileLetterShop);
		Workbook workbookLettershop = new XSSFWorkbook(excelLettershop);
		workbookLettershop.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
		Sheet sheetLettershop = workbookLettershop.getSheetAt(0);

		String strLetterAcctNum, strLetterContact, strLetterMktActivityId, strLetterActivityId, strLetterFullName,
				strLetterTitleSlug, strLetterCompany = "";
		String strLetterAddress1, strLetterAddress2, strLetterCity, strLetterState, strLetterZip, strLetterZip4,
				allLetterFields = "";

		for (Row r : sheetLettershop) {
			try {
				getCellValue(0, r);
				strLetterContact = getCellValue(1, r);
				strLetterMktActivityId = getCellValue(4, r);
				strLetterActivityId = getCellValue(5, r);
				strLetterAcctNum = getCellValue(6, r);
				strLetterFullName = getCellValue(12, r);
				strLetterTitleSlug = getCellValue(13, r);
				strLetterCompany = getCellValue(14, r);
				strLetterAddress1 = getCellValue(15, r);
				strLetterAddress2 = getCellValue(16, r);
				strLetterCity = getCellValue(17, r);
				strLetterState = getCellValue(18, r);
				strLetterZip = getCellValue(19, r);
				strLetterZip4 = getCellValue(20, r);

				allLetterFields = String.join("|", strLetterAcctNum, strLetterContact, strLetterMktActivityId,
						strLetterActivityId, strLetterFullName, strLetterTitleSlug, strLetterCompany, strLetterAddress1,
						strLetterAddress2, strLetterCity, strLetterState, strLetterZip, strLetterZip4, "END");

				hashmapLettershop.put(strLetterAcctNum, allLetterFields);

			} catch (IOException e) {
				System.out.println(e);
			}
		}

		workbookSkuList.close();
		workbookLettershop.close();

		treemapLettershop.putAll(hashmapLettershop);

		// Load Tab File - hashmapTab
		String tabLargeImageUrl, tabKey;
		String[] nextlineTab;

		int tabCount = 0;

		System.out.println("Loading Tab file - " + strTabFile);
		while ((nextlineTab = readerTab.readNext()) != null) {
			tabCount++;
			if (tabCount > 20000) {
				System.out.print(".");
				tabCount = 0;
			}

			if (nextlineTab[0].toString().equals("## SC") || nextlineTab[0].toString().equals("Key")) {
				// skip
			} else {
				tabKey = nextlineTab[0].toString();
				tabLargeImageUrl = nextlineTab[60].toString();
				hashmapTab.put(tabKey, tabLargeImageUrl);
			}
		}
		System.out.println("\nTab file loaded");

		Iterator<?> iter = treemapLettershop.entrySet().iterator();
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
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			String[] lettershopRecord;
			String[] productSkus;

			String tmpKey2, tmpValue2, strTmpSku;

			int lettershopCount = 0;

			String strAccountNumber, strAddress1, strAddress2, strCity, strCompanyName, strFullName, strMkeActivityId,
					strState, strTitleSlug, strZip, strZip4;
			String strShortDesc1, strShortDesc2, strShortDesc3, strSku1, strSku2, strSku3, strImage1, strImage2,
					strImage3, strGisDesc1, strGisDesc2, strGisDesc3;

			strAccountNumber = strAddress1 = strAddress2 = strCity = strCompanyName = strFullName = strMkeActivityId = strState = strTitleSlug = strZip = strZip4 = "";

			csvData.add(
					new String[] { "AccountNumber", "MktActivityId", "FullName", "CompanyName", "State", "TitleSlug",
							"Zip", "Zip4", "Address1", "Address2", "City",
							"AbdSku", "AbdShortDesc", "AbdGisDesc", "AbdImage", "SimSku", "SimShortDesc", "SimGisDesc", "SimImage"
							});

			while (iter.hasNext()) {
				lettershopCount++;
				strShortDesc1 = strShortDesc2 = strShortDesc3 = strSku1 = strSku2 = strSku3 = strImage1 = strImage2 = strImage3 = strGisDesc1 = strGisDesc2 = strGisDesc3 = "";
				strSimSku = strSimShortDesc = strSimGisDesc = strAbdSku = strAbdShortDesc = strAbdGisDesc = strAbdImage = strSimImage = "";
				
				Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
				tmpKey2 = entry.getKey().toString();

				tmpValue2 = (String) entry.getValue();

				System.out.println(tmpKey2 + "(" + lettershopCount + ")");

				if (lettershopCount == 60) {
					System.out.println("STOP");
				}

				lettershopRecord = tmpValue2.split("\\|");

				strAccountNumber = lettershopRecord[0];
				strMkeActivityId = lettershopRecord[2];
				strFullName = lettershopRecord[4];
				strTitleSlug = lettershopRecord[5];
				strCompanyName = lettershopRecord[6];
				strAddress1 = lettershopRecord[7];
				strAddress2 = lettershopRecord[8];
				strCity = lettershopRecord[9];
				strState = lettershopRecord[10];
				strZip = lettershopRecord[11];
				strZip4 = lettershopRecord[12];

				int prodCount = 1;

				String skuNotAvail = "";


					for (String strProduct : multimapSkuList.get(tmpKey2)) {

						productSkus = strProduct.split("\\|");
						
						//String strSimSku, strSimShortDesc, strSimGisDesc, strAbdSku, strAbdShortDesc, strAbdGisDesc = "";
						
						// get image URL from Tab hashmap
						strAbdSku = productSkus[2];
						strSimSku = productSkus[5];

						if (hashmapTab.get(strAbdSku) == null || hashmapTab.get(strAbdSku).contains("NOTAVAIL")) {
							skuNotAvail = skuNotAvail + strAbdSku + ",";
						} else {
							strAbdSku = productSkus[2];
							strAbdShortDesc = productSkus[3];
							strAbdGisDesc = productSkus[4];
							strAbdImage = hashmapTab.get(strAbdSku);
						}

						if (hashmapTab.get(strSimSku) == null || hashmapTab.get(strSimSku).contains("NOTAVAIL")) {
							skuNotAvail = skuNotAvail + strSimSku + ",";
						} else {
							strSimSku = productSkus[2];
							strSimShortDesc = productSkus[3];
							strSimGisDesc = productSkus[4];
							strSimImage = hashmapTab.get(strSimSku);
						}
						
						if (!skuNotAvail.equals("")) {
							System.out.println(strAccountNumber + " - Invalid SKU: " + skuNotAvail);
							errorBuffer.append(strAccountNumber + " - Invalid SKU: " + skuNotAvail);
							errorBuffer.append(System.getProperty("line.separator"));
						}

						if (strAbdSku.equals("")) {
							System.out.println(strAccountNumber + " - No SKUs found in Tab file: " + skuNotAvail);
							errorBuffer.append(strAccountNumber + " - No SKUs found in Tab file: " + skuNotAvail);
							errorBuffer.append(System.getProperty("line.separator"));
						} else {
							csvData.add(new String[] { strAccountNumber, strMkeActivityId, strFullName, strCompanyName,
									strState, strTitleSlug, strZip, strZip4, strAddress1, strAddress2, strCity, 
									strAbdSku, strAbdShortDesc, strAbdGisDesc, strAbdImage, strSimSku, strSimShortDesc, strSimGisDesc, strSimImage
									});
						}						
					}



			}

			BufferedWriter bwr = new BufferedWriter(
					new FileWriter(new File(myPath + "\\" + fileIdentifier + "_errors.txt")));

			// write contents of StringBuffer to a file
			bwr.write(errorBuffer.toString());

			// flush the stream
			bwr.flush();

			// close the stream
			bwr.close();

			writer.writeAll(csvData);

			// closing writer connection
			writer.close();

			readerTab.close();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		System.out.println("Moving files...");

		// moveFile(strSkuListFile, myPath+"\\Archive\\"+timeStamp+"_"+skuFileList[0]);
		// moveFile(strLettershopFile,
		// myPath+"\\Archive\\"+timeStamp+"_"+extractFileList[0]);

		System.out.println("Process complete. Merged file: " + strOutputFile);

	}

	public static String getCellValue(int intCellNum, Row row) throws IOException {

		try {
			Cell cell = row.getCell(intCellNum, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			DataFormatter formatter = new DataFormatter();
			String cellValue = formatter.formatCellValue(cell);
			return cellValue;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return "Error Code";
		}

	}

	private static void moveFile(String src, String dest) {
		Path result = null;
		try {
			result = Files.move(Paths.get(src), Paths.get(dest));
		} catch (IOException e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}
		if (result != null) {
			System.out.println(src + " move successfully to the Archive folder.");
		} else {
			System.out.println(src + " move failed. The file is still in the input folder.");
		}
	}

}

