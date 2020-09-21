package codetest.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
* This helper class is used to import all the data from the .xlsx sheets.
* The data is read directly into each student's transcript
*/
public class ParseData {

    /*
     * Import student information
     */
    public static Map<Integer, StudentTranscript> parseStudentInfo(final Map<Integer, StudentTranscript> students)
            throws IOException {
        File file = new File("src/resources/StudentInfo.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> rowIter = sheet.iterator();

        // Each row represents a student
        while (rowIter.hasNext()) {
            Row row = rowIter.next();

            // Skip header row
            if (row.getRowNum() == 0)
                continue;

            Iterator<Cell> cellIter = row.cellIterator();
            StudentTranscript student = new StudentTranscript();

            while (cellIter.hasNext()) {
                Cell cell = cellIter.next();

                // Set student's personal information
                switch (cell.getColumnIndex()) {
                    // Column 0 is the student's ID
                    case 0:
                        student.setID((int) cell.getNumericCellValue());
                        break;
                    // Column 1 is the student's major
                    case 1:
                        student.setMajor(cell.getStringCellValue());
                        break;
                    // Column 2 is the student's gender
                    case 2:
                        student.setGender(cell.getStringCellValue());
                        break;

                }
            }
            // These calls help populate data from the other sheets
            parseTestScores(students, student);
            parseRetakes(students, student);
            // Finally, add all student data to the hashmap
            students.put((int) student.getID(), student);
        }
        wb.close();
        fis.close();
        return students;
    }

    /*
     * Import data from the students' first test
     */
    public static void parseTestScores(final Map<Integer, StudentTranscript> students, StudentTranscript student)
            throws IOException {
        File file = new File("src/resources/TestScores.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> rowIter = sheet.iterator();

        // Each row represents a student
        while (rowIter.hasNext()) {
            Row row = rowIter.next();

            // Skip header row
            if (row.getRowNum() == 0)
                continue;

            Iterator<Cell> cellIter = row.cellIterator();

            while (cellIter.hasNext()) {
                Cell cell = cellIter.next();

                // Populate data from the first test
                if (cell.getColumnIndex() == 0) {
                    if (cell.getNumericCellValue() == student.getID()) {
                        // If correct ID in the first column, grab the data from the second.
                        Cell next = cellIter.next();
                        student.setFirstScore((int) next.getNumericCellValue());
                    }
                }
            }
            wb.close();
            fis.close();
        }
    }

    /*
     * Import data from the students' retake test, if applicable
     */
    public static void parseRetakes(final Map<Integer, StudentTranscript> students, StudentTranscript student)
            throws IOException {
        File file = new File("src/resources/TestRetakeScores.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> rowIter = sheet.iterator();

        // Each row represents a student
        while (rowIter.hasNext()) {
            Row row = rowIter.next();

            // Skip header row
            if (row.getRowNum() == 0)
                continue;

            Iterator<Cell> cellIter = row.cellIterator();

            while (cellIter.hasNext()) {
                Cell cell = cellIter.next();

                // Populate data from the retake
                if (cell.getColumnIndex() == 0) {
                    if (cell.getNumericCellValue() == student.getID()) {
                        // If correct ID in the first column, grab the data from the second.
                        Cell next = cellIter.next();
                        student.setSecondScore((int) next.getNumericCellValue());
                    }
                }
            }
            wb.close();
            fis.close();
        }
    }
}