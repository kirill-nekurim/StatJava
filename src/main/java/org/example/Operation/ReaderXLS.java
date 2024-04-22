package org.example.Operation;

import java.io.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.example.Main;

public class ReaderXLS {

    public double[][] readXLSX(File file, String which, boolean a) throws IOException, InvalidFormatException {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении Excel файла: " + e.getMessage());
            return null;
        }

        Sheet sheet;
        if (a) {
            try {
                int index = Integer.parseInt(which) - 1;
                sheet = workbook.getSheetAt(index);
            } catch (IllegalArgumentException e) {
                System.out.println("Неверный формат листа. Используется первый лист.");
                sheet = workbook.getSheetAt(0);
            }
        } else {
            sheet = workbook.getSheet(which);
        }

        if (sheet == null) {
            System.out.println("Лист не найден, используйте первый лист.");
            sheet = workbook.getSheetAt(0);
        }

        int numRows = sheet.getPhysicalNumberOfRows();
        int numCols = 0;
        for (int i = 0; i < numRows; i++) {
            numCols = Math.max(numCols, sheet.getRow(i).getLastCellNum());
        }
        double[][] data = new double[numCols][numRows];
        for (int i = 0; i < numRows; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                for (int j = 0; j < numCols; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                        data[j][i] = cell.getNumericCellValue();
                    }
                }
            }
        }
        workbook.close();
        return data;
    }

public void writeXLSX(double[][] mas) throws IOException{
    XSSFWorkbook book = new XSSFWorkbook();
    XSSFSheet sheet = book.createSheet("Полученные значения");
    String[] StatNames = {"Среднее геометрическое", "Среднее арифметическое", "Оценка стандартного отклонения", "Размах", "Коэффициент ковариации с последующей выборкой", "Количество элементов",
            "Нижняя граница доверительного интервала", "Верхняя граница доверительного интервала", "Оценка дисперсии", "Максимум", "Минимум"};
    for (int i = 0; i < 11; i++) {
        Row row = sheet.createRow(i);
        int cellNumber = 0;
        for (int j = 0; j < Repository.getInstance().getMatrix().length; j++) {
            Cell name = row.createCell(cellNumber);
            name.setCellValue(StatNames[i] + " для " + (j + 1) + "-й выборки: ");
            Cell x = row.createCell(cellNumber + 1);
            x.setCellValue((Repository.getInstance().getParameters())[i][j]);
            cellNumber += 2;
        }
        sheet.autoSizeColumn(i);
    }
    XSSFSheet sheet2 = book.createSheet("Матрица ковариации");
    Row row = sheet2.createRow(0);
    for (int j = 0; j < Repository.getInstance().getMatrix().length; j++) {
        Cell name = row.createCell(j + 1);
        name.setCellValue("Выборка " + (j + 1));
    }
    for (int j = 1; j <= Repository.getInstance().getMatrix().length; j++) {
        row = sheet2.createRow(j);
        Cell name = row.createCell(0);
        name.setCellValue("Выборка " + j);
        for (int i = 1; i <= Repository.getInstance().getMatrix().length; i++) {
            name = row.createCell(i);
            name.setCellValue(Repository.getInstance().getCov(i - 1, j - 1));
        }
        sheet2.autoSizeColumn(j - 1);
    }
    try {
        book.write(new FileOutputStream("test.xlsx"));
        System.out.println("Parameters were exported successfully");
    } catch (FileNotFoundException e) {
        System.out.println("Close file firstsly");
    }
    book.close();
}
}
//        Workbook workbook = new XSSFWorkbook();
//        Sheet mainSheet = workbook.createSheet("Полученные значения");
//        Sheet covarianceSheet = workbook.createSheet("Матрица ковариации");
//
//        String[] statNames = {"Среднее геометрическое", "Среднее арифметическое", "Оценка стандартного отклонения", "Размах", "Коэффициент ковариации с последующей выборкой", "Количество элементов",
//                "Нижняя граница доверительного интервала", "Верхняя граница доверительного интервала", "Оценка дисперсии", "Максимум", "Минимум"};
//
//        for (int i = 0; i<statNames.length; i++){
//            Row row = mainSheet.createRow(i);
//            for(int j = 0;j< mas.length; j++){
//                Cell nameCell = row.createCell(j);
//                nameCell.setCellValue((Repository.getInstance().getParameters())[i][j]);
//            }
//        }
//        for(int i=0;i< mas.length;i++){
//            Row headerRow = covarianceSheet.createRow(0);
//            headerRow.createCell(i + 1).setCellValue("Выборка " + (i + 1));
//
//            for(int j=0; j< mas.length; j++){
//                if(i==0){
//                    Row sampleRow = covarianceSheet.createRow(j+1);
//                    sampleRow.createCell(0).setCellValue("Выборка "+ (j+1));
//                }
//                covarianceSheet.getRow(j+1).createCell(i+1).setCellValue(Repository.getInstance().getCov(i,j));
//
//            }
//        }
//        try(FileOutputStream fileOut = new FileOutputStream("test.xlsx")){
//            workbook.write(fileOut);
//            System.out.println("Параметры успешно экспортированы");
//        } catch (IOException e){
//            System.out.println("Ошибка экспорта параметров: " + e.getMessage());
//        } finally {
//            workbook.close();
//        }
//}

