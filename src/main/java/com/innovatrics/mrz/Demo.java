/**
 * Java parser for the MRZ records, as specified by the ICAO organization.
 * Copyright (C) 2011 Innovatrics s.r.o.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.innovatrics.mrz;

/**
 * Demo command-line application, demonstrates simple MRZ parsing.
 *
 * @author Martin Vysny, Muhammad Hamza Mushtaq
 */
@SuppressWarnings("HideUtilityClassConstructor")
public class Demo {

	private static int toPos(final int col, final int row, final String text) {
		int currentRow = 0;
		int currentCol = 0;
		int pos = 0;
		while (text.length() > pos) {
			if (row == currentRow && currentCol == col) {
				return pos;
			}
			if (text.charAt(pos) == '\n') {
				currentRow++;
				currentCol = 0;
			} else {
				currentCol++;
			}
			pos++;
		}
		return -1;
	}

	/**
	 * MRZ demo.
	 *
	 * @param args the main arguments
	 */
	public static void main(final String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java Demo <MRZ data>");
			return;
		}

		final String mrz = args[0];
		try {
			final MrzRecord record = MrzParser.parse(mrz);
			System.out.println(record);
		} catch (MrzParseException ex) {
			System.out.println("Error");
			final MrzRange r = ex.getRange();
			final int startPos = toPos(r.getColumn(), r.getRow(), mrz);
			final int endPos = toPos(r.getColumnTo(), r.getRow(), mrz);
            System.out.println("Error at position: " + startPos + " - " + endPos);
		} catch (Exception ex) {
            System.out.println("Parse failed: " + ex);
		}
	}

}
