/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddbuff;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

class filedown {
	public static void download(String address, boolean isplugin) {
		int lastSlashIndex = address.lastIndexOf('/');
		if (lastSlashIndex >= 0 && lastSlashIndex < address.length() - 1) {
			if (isplugin == false) {
				download(address, address.substring(lastSlashIndex + 1));
			}
			else {
				download(address,
						"plugins\\" + address.substring(lastSlashIndex + 1));

			}
		}
		else {
			System.err.println("Could not figure out local file name for "
					+ address);
		}
	}

	public static void download(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;

		try {
			URL url = new URL(address);
			out = new BufferedOutputStream(new FileOutputStream(localFileName));
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];

			int numRead;
			long numWritten = 0;

			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
				numWritten += numRead;
				for (Player ppp : Bukkit.getOnlinePlayers()) {
					ppp.sendMessage("downloading > "
							+ Long.toString(numWritten) + " byte");
					System.out.println("downloading > "
							+ Long.toString(numWritten) + " byte");
				}
			}

			System.out.println(localFileName + "\t" + numWritten);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
			catch (IOException ioe) {
			}
		}
	}

}
