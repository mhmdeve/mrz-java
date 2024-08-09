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
package com.innovatrics.mrz.records;

import com.innovatrics.mrz.MrzParseException;
import com.innovatrics.mrz.MrzParser;
import com.innovatrics.mrz.MrzRange;
import com.innovatrics.mrz.MrzRecordOptional;
import com.innovatrics.mrz.types.MrzFormat;
import java.util.regex.Pattern;

/**
 * MRTD TD1 format: A three line long, 30 characters per line format.
 *
 * @author Martin Vysny
 */
public class MrtdTd1 extends MrzRecordOptional {

	private static final long serialVersionUID = 1L;

	/**
	 * Optional (for U.S. passport holders, 21-29 may be corresponding passport number).
	 */
	private String optional2;

	/**
	 * Construct a MrtdTd1 Record.
	 */
	public MrtdTd1() {
		super(MrzFormat.MRTD_TD1, "MRTD-TD1");
	}

	@Override
	public void fromMrz(final String mrz) throws MrzParseException {
		super.fromMrz(mrz);
		if (mrz.startsWith("I<PRT")) {
			final MrzParser parser = new MrzParser(mrz);
			setDocumentNumber(replaceNumberChar(parser.parseString(new MrzRange(5, 18, 0))));
			setPersonalNumberID(parser.parseString(new MrzRange(15, 24, 0)));
			setValidDocumentNumber(isValidPortugalIDNumber(replaceNumberChar(parser.parseString(new MrzRange(5, 18, 0)))));
			setOptional(parser.parseString(new MrzRange(15, 30, 0)));
			setDateOfBirth(parser.parseDate(new MrzRange(0, 6, 1)));
			setValidDateOfBirth(parser.checkDigit(6, 1, new MrzRange(0, 6, 1), "date of birth") && getDateOfBirth().isDateValid());
			setSex(parser.parseSex(7, 1));
			setExpirationDate(parser.parseDate(new MrzRange(8, 14, 1)));
			setValidExpirationDate(parser.checkDigit(14, 1, new MrzRange(8, 14, 1), "expiration date") && getExpirationDate().isDateValid());
			setNationality(parser.parseString(new MrzRange(15, 18, 1)));
			setOptional2(parser.parseString(new MrzRange(18, 29, 1)));
			setValidComposite(parser.checkDigit(29, 1, parser.rawValue(new MrzRange(5, 30, 0), new MrzRange(0, 7, 1), new MrzRange(8, 15, 1), new MrzRange(18, 29, 1)), "mrz"));
			setName(parser.parseName(new MrzRange(0, 30, 2)));
		} else {
			final MrzParser parser = new MrzParser(mrz);
			setDocumentNumber(parser.parseString(new MrzRange(5, 14, 0)));
			setPersonalNumberID(parser.parseString(new MrzRange(15, 24, 0)));
			setValidDocumentNumber(parser.checkDigit(14, 0, new MrzRange(5, 14, 0), "document number"));
			setOptional(parser.parseString(new MrzRange(15, 30, 0)));
			setDateOfBirth(parser.parseDate(new MrzRange(0, 6, 1)));
			setValidDateOfBirth(parser.checkDigit(6, 1, new MrzRange(0, 6, 1), "date of birth") && getDateOfBirth().isDateValid());
			setSex(parser.parseSex(7, 1));
			setExpirationDate(parser.parseDate(new MrzRange(8, 14, 1)));
			setValidExpirationDate(parser.checkDigit(14, 1, new MrzRange(8, 14, 1), "expiration date") && getExpirationDate().isDateValid());
			setNationality(parser.parseString(new MrzRange(15, 18, 1)));
			setOptional2(parser.parseString(new MrzRange(18, 29, 1)));
			setValidComposite(parser.checkDigit(29, 1, parser.rawValue(new MrzRange(5, 30, 0), new MrzRange(0, 7, 1), new MrzRange(8, 15, 1), new MrzRange(18, 29, 1)), "mrz"));
			setName(parser.parseName(new MrzRange(0, 30, 2)));
		}
	}

	/**
	 * Replaces ambiguous characters in the given input with numeric equivalents.
	 * <p>
	 * This method replaces specific characters in the input string with their corresponding numeric values:
	 * <ul>
	 * <li>'O' is replaced with '0'</li>
	 * <li>'I' is replaced with '1'</li>
	 * <li>'B' is replaced with '8'</li>
	 * <li>'S' is replaced with '5'</li>
	 * <li>'J' is replaced with '3'</li>
	 * <li>'Z' is replaced with '2'</li>
	 * </ul>
	 * Characters at positions 10 and 11 (0-based index) are left unchanged, and '<' and space characters are removed.
	 * </p>
	 * 
	 * @param input The input string containing potentially ambiguous characters.
	 * @return A string where ambiguous characters are replaced with numeric values, and '<' and spaces are removed.
	 */
	public static String replaceNumberChar(final String input) {
		if (input == null) {
			return null;
		}

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);

			// Check if the current index is 10 or 11 (0-based index)
			if (i == 9 || i == 10) {
				result.append(ch); // Leave characters at positions 10 and 11 unchanged
			} else {
				switch (ch) {
					case 'O':
						result.append('0');
						break;
					case 'I':
						result.append('1');
						break;
					case 'B':
						result.append('8');
						break;
					case 'S':
						result.append('5');
						break;
					case 'J':
						result.append('3');
						break;
					case 'Z':
						result.append('2');
						break;
					case '<':
					case ' ':
						// Replace < and space with empty string (do not append)
						break;
					default:
						result.append(ch); // Append all other characters as they are
						break;
				}
			}
		}

		// Convert StringBuilder to String and remove any remaining spaces
		return result.toString().replace(" ", "");
	}

	/**
	 * Validates a Portuguese ID number against a predefined format.
	 * <p>
	 * This method checks if the provided document number matches the expected format for Portuguese ID numbers.
	 * The format is defined by the following regular expression:
	 * <pre>
	 * ^[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{1}[A-Za-z]{2}[0-9]{1}$
	 * </pre>
	 * The input is trimmed of any leading or trailing whitespace before validation.
	 * </p>
	 * 
	 * @param documentNumber The Portuguese ID number to be validated.
	 * @return {@code true} if the document number matches the expected format, {@code false} otherwise.
	 */
	public static boolean isValidPortugalIDNumber(final String documentNumber) {
		// Trim the input to remove any leading or trailing whitespace
		String trimmedDocumentNumber = documentNumber.trim();

		// Check if the trimmed document number is empty
		if (trimmedDocumentNumber.isEmpty()) {
			return false;
		}

		// Define the regex pattern based on the actual Portuguese ID format
		String pattern = "^[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{1}[A-Za-z]{2}[0-9]{1}$";
		Pattern compiledPattern = Pattern.compile(pattern);

		return compiledPattern.matcher(trimmedDocumentNumber).matches();
	}


	@Override
	public String toMrz() {
		// first line
		final StringBuilder sb = new StringBuilder();
		sb.append(getCode1());
		sb.append(getCode2());
		sb.append(MrzParser.toMrz(getIssuingCountry(), 3));
		final String dno = MrzParser.toMrz(getDocumentNumber(), 9) + MrzParser.computeCheckDigitChar(MrzParser.toMrz(getDocumentNumber(), 9)) + MrzParser.toMrz(getOptional(), 15);
		sb.append(dno);
		sb.append('\n');
		// second line
		final String dob = getDateOfBirth().toMrz() + MrzParser.computeCheckDigitChar(getDateOfBirth().toMrz());
		sb.append(dob);
		sb.append(getSex().getMrz());
		sb.append(getPersonalNumberID());
		final String ed = getExpirationDate().toMrz() + MrzParser.computeCheckDigitChar(getExpirationDate().toMrz());
		sb.append(ed);
		sb.append(MrzParser.toMrz(getNationality(), 3));
		sb.append(MrzParser.toMrz(getOptional2(), 11));
		sb.append(MrzParser.computeCheckDigitChar(dno + dob + ed + MrzParser.toMrz(getOptional2(), 11)));
		sb.append('\n');
		// third line
		sb.append(MrzParser.nameToMrz(getSurname(), getGivenNames(), 30));
		sb.append('\n');
		return sb.toString();
	}

	/**
	 * Optional (for U.S. passport holders, 21-29 may be corresponding passport number).
	 *
	 * @return the optional2 data
	 */
	public String getOptional2() {
		return optional2;
	}

	/**
	 * Optional (for U.S. passport holders, 21-29 may be corresponding passport number).
	 *
	 * @param optional2 the optional2 data
	 */
	public void setOptional2(final String optional2) {
		this.optional2 = optional2;
	}

	@Override
	protected void buildToString(final StringBuilder sb) {
		super.buildToString(sb);
		sb.append(", optional2=").append(getOptional2());
	}
}
