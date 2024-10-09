//package com.util;
//
//import java.util.List;
//
//import com.Model.ModelInterface;
//
//public class PrintInTable {
//	public static <T extends ModelInterface> void printTable(List<T> data, List<String> headers, List<String> fields) {
//		int columnWidth[] = new int[headers.size()];
//
//		for (int i = 0; i < columnWidth.length; i++) {
//			columnWidth[i] = headers.get(i).length();
//		}
//		for (T row : data) {
//			List<String> rowData = row.toRow(fields);
//			for (int i = 0; i < rowData.size(); i++) {
//				columnWidth[i + 1] = Math.max(columnWidth[i + 1], rowData.get(i).length());
//			}
//		}
//		printRow(headers, columnWidth);
//		printSepartor(columnWidth);
//		int serialNumber = 1;
//		for (T row : data) {
//			List<String> rowData = row.toRow(fields);
//			rowData.add(0, String.valueOf(serialNumber)); // Add serial number to the row
//			printRow(rowData, columnWidth);
//			printSepartor(columnWidth);
//			serialNumber++;
//		}
//	}
//	private static void printSepartor(int[] columnWidth) {
//
//		for (int width : columnWidth) {
//			System.out.print("+");
//			for (int i = 0; i < width + 2; i++) { // +2 for padding
//				System.out.print("-");
//			}
//		}
//		System.out.println("+");
//	}
//	private static void printRow(List<String> row, int[] columnWidth) {
//		for (int i = 0; i < row.size(); i++) {
//			System.out.printf("| %-" + columnWidth[i] + "s ", row.get(i));
//		}
//		System.out.println("|");
//	}
//}



