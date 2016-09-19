/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddpicture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	class dewpicturec implements Runnable {
		Player	p			= null;
		boolean	isfillmode	= false;
		String	filename	= "";
		boolean	isurl		= false;

		public void run() {
			dewpicture(p, filename, isfillmode, isurl);
		}
	}
	private static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		int rgb[] = new int[] { (argb >> 16) & 0xff, // red
				(argb >> 8) & 0xff, // green
				(argb) & 0xff // blue
		};
		return rgb;
	}

	JavaPlugin	ac			= null;
	String		ppicture	= "dewdd.picture.draw";
	int			sx			= 0;
	int			sy			= 0;

	int			sz			= 0;
	World		sworld		= null;

	int			max1		= -1;

	int			min1		= Integer.MAX_VALUE;

	public void dewpicture(Player p, String filename, boolean isfillmode,
			boolean isurl) {

		BufferedImage img;
		dprint.r.printA("starting " + filename);

		Block block = p.getLocation().getBlock();
		Block blockd = null;
		int blockdx = 0;
		int blockdz = 0;

		if (block.getRelative(0, -1, 0).getTypeId() != 133) {
			p.sendMessage("please stand on emerald block");
			return;
		}

		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				blockd = block.getRelative(dx, -1, dz);
				if (blockd.getTypeId() == 57) { // diamond block
					blockdx = dx;
					blockdz = dz;
					break;
				}

			}

		}

		if (blockdx == 0 && blockdz == 0) {
			p.sendMessage("not found diamond block");
			return;
		}

		try {
			if (isurl == false) {
				img = ImageIO.read(new File(filename));
			}
			else {
				URL url = new URL(filename);
				img = ImageIO.read(url);
			}

			int[][] pixelData = new int[img.getHeight() * img.getWidth()][3];

			dprint.r.printAll("h = " + img.getHeight() + "," + img.getWidth());
			int[] rgb;

			boolean firsttime = false;

			int counter = 0;
			for (int i = 0; i < img.getHeight(); i++) {
				for (int j = 0; j < img.getWidth(); j++) {
					rgb = getPixelData(img, j, i);

					for (int k = 0; k < rgb.length; k++) {
						pixelData[counter][k] = rgb[k];
					}

					int color = getcolor(img, j, i);

					if (firsttime == false) {
						firsttime = true;
						dprint.r.printAll("color = " + color + " , " + i + ","
								+ j);
					}

					int rx = 0;
					int ry = 0;
					int rz = 0;
					ry = -i;

					if (blockdx == 1) {
						rx = j;
					}
					else if (blockdx == -1) {
						rx = -j;
					}

					if (blockdz == 1) {
						rz = j;
					}
					else if (blockdz == -1) {
						rz = -j;
					}

					if (isfillmode == true) {

						if (p.getLocation().getBlock().getRelative(rx, ry, rz)
								.getTypeId() != 0) {
							continue;
						}
					}

					// render
					int look[] = new int[3];

					if (color > 0) {

						p.getLocation().getBlock().getRelative(rx, ry, rz)
								.setTypeIdAndData(35, (byte) color, false);
					}
					else {
						p.getLocation().getBlock().getRelative(rx, ry, rz)
								.setTypeId(0);
					}

					counter++;
				}
			}

		}
		catch (IOException e) {
			e.printStackTrace();
			dprint.r.printAll("error " + e.getMessage());
		}

		dprint.r.printAll("done min " + min1 + ", max " + max1);

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String m[] = event.getMessage().split("\\s+");

		if (m[0].equalsIgnoreCase("/dewpicture")) {
			if (player.hasPermission(ppicture) == false) {
				player.sendMessage("only op !");
				return;
			}

			player.sendMessage("/dewpicture filename isfillmode?");
			boolean ii = true;

			if (m.length < 2) {
				player.sendMessage("we need 2 arguments or 3");
				return;
			}

			if (m.length == 3) {
				ii = Boolean.parseBoolean(m[2]);
			}

			dewpicturec abc = new dewpicturec();
			abc.p = player;
			abc.isfillmode = ii;
			abc.filename = m[1];
			abc.isurl = false;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc);

			player.sendMessage("dewpicture done");

		}

		if (m[0].equalsIgnoreCase("/dewpictureu")) {
			if (player.hasPermission(ppicture) == false) {
				player.sendMessage("only op !");
				return;
			}

			player.sendMessage("/dewpicture filename isfillmode?");
			boolean ii = true;

			if (m.length < 2) {
				player.sendMessage("we need 2 arguments or 3");
				return;
			}

			if (m.length == 3) {
				ii = Boolean.parseBoolean(m[2]);
			}

			dewpicturec abc = new dewpicturec();
			abc.p = player;
			abc.isfillmode = ii;
			abc.filename = m[1];
			abc.isurl = true;

			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, abc);

			player.sendMessage("dewpicture url done");

		}

	}

	private int getcolor(BufferedImage img, int x, int y) {

		int argb = img.getRGB(x, y);

		if (max1 < argb) max1 = argb;
		if (min1 > argb) min1 = argb;
		return (int) Math.round(Math.abs(argb / 1048576));
	}

	public String rpGetPlayerDirection(Player playerSelf) {
		String dir = "";
		float y = playerSelf.getLocation().getYaw();
		if (y < 0) {
			y += 360;
		}
		y %= 360;
		int i = (int) ((y + 8) / 22.5);
		if (i == 0) {
			dir = "west";
		}
		else if (i == 1) {
			dir = "west northwest";
		}
		else if (i == 2) {
			dir = "northwest";
		}
		else if (i == 3) {
			dir = "north northwest";
		}
		else if (i == 4) {
			dir = "north";
		}
		else if (i == 5) {
			dir = "north northeast";
		}
		else if (i == 6) {
			dir = "northeast";
		}
		else if (i == 7) {
			dir = "east northeast";
		}
		else if (i == 8) {
			dir = "east";
		}
		else if (i == 9) {
			dir = "east southeast";
		}
		else if (i == 10) {
			dir = "southeast";
		}
		else if (i == 11) {
			dir = "south southeast";
		}
		else if (i == 12) {
			dir = "south";
		}
		else if (i == 13) {
			dir = "south southwest";
		}
		else if (i == 14) {
			dir = "southwest";
		}
		else if (i == 15) {
			dir = "west southwest";
		}
		else {
			dir = "west";
		}
		return dir;
	}

} // class

