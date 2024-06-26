    package org.example.Operation;

    import java.io.*;

    import org.apache.poi.ss.usermodel.*;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;
    import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

    import javax.swing.*;

    public class ReaderXLS {

        public double[][] readXLSX(File file, String which, boolean a) throws IOException, InvalidFormatException {
            Workbook workbook = null;
            try {
                workbook = WorkbookFactory.create(file);
            } catch (IOException e) {
                // Обработка ошибки открытия файла
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Ошибка при чтении Excel файла: " + e.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            Sheet sheet;
            if (a) {
                try {
                    int index = Integer.parseInt(which) - 1;
                    sheet = workbook.getSheetAt(index);
                } catch (IllegalArgumentException e) {
                    // Обработка ошибки форматирования индекса листа
                    javax.swing.JOptionPane.showMessageDialog(null,
                            "Неверный формат индекса листа. Используется первый лист.",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    sheet = workbook.getSheetAt(0);
                }
            } else {
                sheet = workbook.getSheet(which);
            }

            if (sheet == null) {
                // Обработка ошибки - лист не найден
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Лист не найден, используется первый лист.",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                sheet = workbook.getSheetAt(0);
            }

            int numRows = sheet.getPhysicalNumberOfRows();
            int numCols = 0;
            for (int i = 0; i < numRows; i++) {
                //getlastcellnum возвращает индекс последней заполненной ячейки в строке
                numCols = Math.max(numCols, sheet.getRow(i).getLastCellNum());
            }
            double[][] data = new double[numCols][numRows-1];

            for (int i = 1; i < numRows; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < numCols; j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                            data[j][i-1] = cell.getNumericCellValue();
                        }
                    }
                }
            }
            workbook.close();
            return data;
        }

    public void writeXLSX(double[][] mas, String directoryPath) throws IOException{
            Repository rep = new Repository();
        Workbook workbook = new XSSFWorkbook();
        Sheet mainSheet = workbook.createSheet("Полученные значения");
        Sheet covarianceSheet = workbook.createSheet("Матрица ковариации");

        String[] statNames = {"Среднее геометрическое", "Среднее арифметическое", "Оценка стандартного отклонения", "Размах",
                "Коэффициент вариации", "Количество элементов",
                "Нижняя граница доверительного интервала", "Верхняя граница доверительного интервала", "Оценка дисперсии",
                "Максимум", "Минимум"};

        for (int i = 0; i < statNames.length; i++) {
            Row row = mainSheet.createRow(i);
            for (int j = 0; j < mas.length; j++) {
                Cell nameCell = row.createCell(j * 2);
                nameCell.setCellValue(statNames[i] + " для " + (j + 1) + "-й выборки: ");

                Cell valueCell = row.createCell(j * 2 + 1);
                valueCell.setCellValue((rep.getParameters())[i][j]);
            }
        }
        Row row = covarianceSheet.createRow(0);
        for (int j = 0; j < rep.getMatrix().length; j++) {
            Cell name = row.createCell(j + 1);
            name.setCellValue("Выборка " + (j + 1));
        }
        for (int j = 1; j <= rep.getMatrix().length; j++) {
            row = covarianceSheet.createRow(j);
            Cell name = row.createCell(0);
            name.setCellValue("Выборка " + j);
            for (int i = 1; i <= rep.getMatrix().length; i++) {
                name = row.createCell(i);
                name.setCellValue(rep.getCov(i - 1, j - 1));
            }
            covarianceSheet.autoSizeColumn(j - 1);
        }

        try (FileOutputStream fileOut = new FileOutputStream(directoryPath +  "/test.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Параметры успешно экспортированы");
        } catch (IOException e) {
            System.out.println("Ошибка экспорта параметров: " + e.getMessage());
        } finally {
            workbook.close();
        }
    }
    }

