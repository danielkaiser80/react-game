package crack.me;//
// Source code recreated from crack.me.Generator .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public final class Generator {
	private int parameter = 0;

	public Generator() {
	}

	public final String generateFromBytes(final byte[] bytes) {

		byte[] var2 = new byte[8];
		//byte[] var3 = new byte[8];
		int length = bytes.length;
		this.parameter = length;

		int var4 = 0;
		int var5 = 13;
		byte var6 = 21;

		for(int i = 0; i < 8; ++i) {

			for(int var9 = 0; var9 < length; ++var9) {
				byte var8 = bytes[var9];
				var2[var4] = (byte)(var2[var4] + (var8 ^ var5));
				//var3[7 - var4] = (byte)(var6 + var4);
				var8 = var6;
				var6 = (byte)(var6 << 1);
				var5 = (var5 ^ var4) + var2[var4];
				var2[var4] = (byte)(var2[var4] + (var8 & var6));
				var6 = (byte)(var6 + var6 + 1);
				var4 = (var4 + 1) % 8;
			}
		}

		char[] var12 = new char[8];

		for(int var13 = 0; var13 < 8; ++var13) {
			var12[var13] = (char)((var2[var13] & 63) + 48);
		}

		return String.valueOf(var12);
	}

	public final String generate() {
		switch(this.parameter) {
			case 1:
				return "lL8D0uWh";
			case 2:
				return "Vx\\D5<N@";
			case 3:
				return "Whk1=qAO";
			case 4:
				return "Fu6hS_dB";
			case 5:
				return "GBi\\vji6";
			case 6:
				return "e8QO^ro9";
			case 7:
				return "WkTrF:7i";
			case 8:
				return "j9v`CAYT";
			case 9:
				return "FEWPPGDq";
			case 10:
				return "^1y]]Se7";
			case 11:
				return "8vM=lZoU";
			case 12:
				return "@y0^Z2lb";
			case 13:
				return "<m9hJnp7";
			case 14:
				return "e2PNSgej";
			case 15:
				return "j1_SXH^x";
			case 16:
				return "_gtFZoRO";
			case 17:
				return "<mx<U_09";
			case 18:
				return "@k8_;r7H";
			case 19:
				return "mM0bBw=R";
			case 20:
				return "s2>=i40F";
			default:
				return "BQc7qLNU";
		}
	}
}
