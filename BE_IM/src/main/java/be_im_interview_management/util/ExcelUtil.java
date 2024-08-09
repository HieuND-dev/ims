package be_im_interview_management.util;

import be_im_interview_management.dto.OfferRequestDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by: HieuND64
 * Date Time: 7/30/2024 4:20 PM
 */
public class ExcelUtil {
    public static String[] HEADER = {
            "No.",
            "Candidate ID",
            "Candidate name",
            "Approved by",
            "Contract type",
            "Position",
            "Level",
            "Department",
            "Recruiter owner",
            "Interviewer",
            "Contract start from",
            "Contract to",
            "Basic salary",
            "Interview notes"
    };

    public static String SHEET_NAME = "OffersList";

    public static ByteArrayInputStream dataToExcel(List<OfferRequestDTO> offersList) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row row = sheet.createRow(0);

            for (int i = 0; i < HEADER.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADER[i]);
            }

            for (int i = 0; i < offersList.size(); i++) {
                Row valueRow = sheet.createRow(i+1);
                valueRow.createCell(0).setCellValue(i+1);
                valueRow.createCell(1).setCellValue(offersList.get(i).id());
                valueRow.createCell(2).setCellValue(offersList.get(i).candidateName());
                valueRow.createCell(3).setCellValue(offersList.get(i).approver());
                valueRow.createCell(4).setCellValue(offersList.get(i).contractType().getLabel());
                valueRow.createCell(5).setCellValue(offersList.get(i).position().getLabel());
                valueRow.createCell(6).setCellValue(offersList.get(i).level().getLabel());
                valueRow.createCell(7).setCellValue(offersList.get(i).department().name());
                valueRow.createCell(8).setCellValue(offersList.get(i).recruiterOwner());
                valueRow.createCell(9).setCellValue(offersList.get(i).interviewer());
                valueRow.createCell(10).setCellValue(offersList.get(i).contractFrom());
                valueRow.createCell(11).setCellValue(offersList.get(i).contractTo());
                valueRow.createCell(12).setCellValue(offersList.get(i).basicSalary());
                valueRow.createCell(13).setCellValue(offersList.get(i).interviewNote());
            }

            workbook.write(byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            workbook.close();
            byteArrayOutputStream.close();
        }

    }
}
