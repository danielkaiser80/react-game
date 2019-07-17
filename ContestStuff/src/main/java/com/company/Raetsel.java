package com.company;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Raetsel {

	static char[][] input = {
			{ 94, 102, 105, 116, 111, 114, 111, 116, 118, 119, 81, 112, 120, 124, 116, 119, 120, 111, 105, 115, 122, 58,
					32, 98, 105, 32, 98, 87, 51, 125, 95, 105 },
			{ 87, 110, 112, 123, 32, 41, 113, 101, 120, 117, 115, 108, 117, 122, 115, 115, 98, 32, 118, 117, 117, 68,
					107, 125, 114, 100, 79, 74, 104, 121, 81, 120 },
			{ 73, 114, 115, 116, 64, 90, 120, 112, 123, 125, 110, 103, 118, 119, 118, 118, 121, 32, 121, 124, 118, 119,
					76, 101, 39, 57, 99, 106, 86, 123, 63, 121 },
			{ 123, 114, 110, 120, 123, 90, 112, 104, 125, 70, 124, 103, 120, 120, 123, 121, 122, 78, 66, 116, 120, 71,
					38, 106, 123, 59, 112, 45, 112, 118, 106, 117 },
			{ 50, 111, 93, 125, 87, 90, 107, 54, 117, 121, 117, 70, 120, 125, 124, 114, 124, 38, 118, 115, 124, 49, 75,
					119, 111, 110, 118, 90, 83, 124, 48, 119 },
			{ 68, 118, 105, 59, 32, 114, 125, 109, 106, 125, 111, 109, 120, 110, 111, 114, 118, 36, 121, 125, 116, 61,
					32, 124, 119, 67, 97, 75, 111, 119, 93, 117 },
			{ 85, 106, 121, 113, 76, 86, 111, 111, 115, 122, 119, 122, 124, 125, 122, 110, 119, 79, 118, 122, 124, 67,
					121, 120, 103, 123, 98, 83, 87, 118, 84, 71 },
			{ 83, 110, 122, 114, 37, 78, 115, 114, 121, 120, 123, 113, 118, 119, 118, 125, 117, 65, 108, 122, 118, 86,
					111, 124, 124, 76, 100, 87, 96, 124, 122, 108 },
			{ 116, 111, 119, 125, 64, 115, 118, 118, 123, 117, 110, 120, 115, 119, 86, 120, 124, 101, 121, 33, 123, 69,
					106, 78, 103, 103, 106, 84, 83, 62, 79, 118 },
			{ 94, 101, 123, 112, 98, 93, 122, 101, 123, 118, 110, 107, 60, 125, 111, 125, 116, 53, 112, 116, 119, 88,
					69, 112, 124, 94, 106, 117, 100, 124, 118, 123 },
			{ 100, 87, 117, 122, 36, 119, 111, 124, 122, 117, 110, 113, 123, 123, 119, 115, 121, 44, 107, 121, 125, 67,
					94, 121, 103, 96, 109, 105, 106, 118, 48, 105 },
			{ 104, 102, 118, 110, 83, 76, 117, 118, 122, 125, 112, 125, 124, 125, 111, 118, 116, 121, 121, 119, 49, 73,
					63, 118, 120, 57, 97, 106, 99, 119, 44, 117 } };

	static byte[] check = { -88, -6, 126, 30, -6, 17, 123, 86, -4, 104, -33, -128, -80, 91, -78, -104, -93, -97, -5,
			-32, -4, -20, 97, -117, -49, 88, -82, -24, 10, 117, -61, 88 };

	public static void main(String[] args) {

		char[][] t = drehen(input);
		for (char[] c : t) {
			i(c, 0, c.length - 1);
		}
		char[] result = drehen(t)[1];

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(String.copyValueOf(result).getBytes("UTF-8"));
			if (Arrays.equals(hash, check)) {
				System.out.println(String.copyValueOf(result));
			} else {
				System.out.println("Etwas ist schiefgegangen");
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			System.out.println("Validierung fehlgeschlagen");
		}

	}

	public static char[][] drehen(char[][] d) {
		int b = d.length;
		int c = d[0].length;
		for (char[] e : d) {
			if (e.length != c) {
				throw new RuntimeException();
			}
		}
		char[][] f = new char[c][];
		for (int i = 0; i < c; i++) {
			f[i] = new char[b];
			for (int j = 0; j < b; j++) {
				f[i][j] = d[j][i];
			}
		}
		return f;
	}

	public static void i(char[] chars, int k, int l) {
		if (k >= l) {
			return;
		}
		int m = k;
		int n = l;
		for (int i = k + 1; i <= l - 1;) {
			if (chars[i] < chars[m]) {
				tauschen(chars, i, m);
				i++;
				m++;
			} else {
				tauschen(chars, i, l);
				l--;
			}
		}
		i(chars, k, l - 1);
		i(chars, l + 1, n);
	}

	public static void tauschen(char[] chars, int b, int c) {
		char v = chars[c];
		chars[c] = chars[b];
		chars[b] = v;
	}
}