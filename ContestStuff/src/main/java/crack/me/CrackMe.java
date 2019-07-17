package crack.me;

import java.util.Scanner;

public class CrackMe {

	public static void main(String[] var0) {

		byte[] var1 = new byte[16];
		boolean success = false;
		Generator generator = new Generator();

		String password = "BQc7qLNU!";

		System.arraycopy(password.getBytes(), 0, var1, 0, 9);

		Scanner scanner = new Scanner(System.in);

		while(!success) {
			System.out.println("Passwort 2:");
			if(!(password = scanner.nextLine()).isEmpty()) { //
				byte[] passwordBytes = password.getBytes();
				String var8 = generator.generateFromBytes(passwordBytes);
				System.arraycopy((var8).getBytes(), 0, var1, 8, Math.min(8, passwordBytes.length));
				if(generator.generate().equals(var8)) {
					var1[1] = passwordBytes[3];
					success = true;
				}
			}
		}

		scanner.close();
		password = generator.generateFromBytes(var1);
		if((password).substring(1, 2).equals("h")) {
			System.out.println("Dein Gewinncode lautet: " + password);
		}

	}
}
