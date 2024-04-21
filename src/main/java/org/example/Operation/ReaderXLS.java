package org.example.Operation;

import java.io.*;

import org.apache.poi.ss.usermodel.*;
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
        }

        Sheet sheet;
        if (a) {
            try {
                int index = Integer.parseInt(which) - 1;
                sheet = workbook.getSheetAt(index);
            } catch (IllegalArgumentException e) {
                System.out.println("неверный формат листа. Используется первый лист.");
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
        Workbook workbook = new XSSFWorkbook();
        Sheet mainSheet = workbook.createSheet("Полученные значения");
        Sheet covarianceSheet = workbook.createSheet("Матрица ковариации");

        String[] statNames = {"Среднее геометрическое", "Среднее арифметическое", "Оценка стандартного отклонения", "Размах", "Коэффициент ковариации с последующей выборкой", "Количество элементов",
                "Нижняя граница доверительного интервала", "Верхняя граница доверительного интервала", "Оценка дисперсии", "Максимум", "Минимум"};

        for (int i = 0; i<statNames.length; i++){
            Row row = mainSheet.createRow(i);
            for(int j = 0;j< mas.length; j++){
                Cell nameCell = row.createCell(j*2);
                nameCell.setCellValue((Repository.getInstance().getParameters())[i][j]);
            }
        }
        for(int i=0;i< mas.length;i++){
            Row headerRow = covarianceSheet.createRow(0);
            headerRow.createCell(i + 1).setCellValue("Выборка " + (i + 1));

            for(int j=0; j< mas.length; j++){
                if(i==0){
                    Row sampleRow = covarianceSheet.createRow(j+1);
                    sampleRow.createCell(0).setCellValue("Выборка "+ (j+1));
                }
                covarianceSheet.getRow(j+1).createCell(i+1).setCellValue(Repository.getInstance().getCov(i,j));

            }
        }
        try(FileOutputStream fileOut = new FileOutputStream("test.xlsx")){
            workbook.write(fileOut);
            System.out.println("Параметры успешно экспортированы");
        } catch (IOException e){
            System.out.println("Ошибка экспорта параметров: " + e.getMessage());
        } finally {
            workbook.close();
        }
}
}
