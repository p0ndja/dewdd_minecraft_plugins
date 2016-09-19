/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddtmex;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	class abx implements Runnable {
		Block	block	= null;

		public void run() {
			dprint.r.printC("dewdd tmex adding ..." + block.getX() + ","
					+ block.getY() + "," + block.getZ());
			Block b2 = null;
			for (int x2 = -10; x2 <= 10; x2++) {
				for (int y2 = -10; y2 <= 10; y2++) {
					for (int z2 = -10; z2 <= 10; z2++) {

						b2 = block.getRelative(x2, y2, z2);
						if (b2.getTypeId() == 46) {
							continue;
						}

						switch (b2.getTypeId()) {
						case 27:
						case 28:
						case 66:
						case 157:
						case 50:
						case 55:
						case 69:
						case 75:
						case 76:
						case 77:
						case 93:
						case 94:
						case 132:
						case 143:
						case 147:
						case 148:
						case 149:
						case 150:
						case 151:
							x4.push(b2.getX());
							y4.push(b2.getY());
							z4.push(b2.getZ());
							id4.push(b2.getTypeId());
							data4.push(b2.getData());
							world4.push(b2.getWorld());
							continue;
						default:
							if (b2.getType().isBlock() == false) {
								x4.push(b2.getX());
								y4.push(b2.getY());
								z4.push(b2.getZ());
								id4.push(b2.getTypeId());
								data4.push(b2.getData());
								world4.push(b2.getWorld());
								continue;
							}
							else {
								x.push(b2.getX());
								y.push(b2.getY());
								z.push(b2.getZ());
								id.push(b2.getTypeId());
								data.push(b2.getData());
								world.push(b2.getWorld());
								continue;
							}

						}

					}
				}
			}

			dprint.r.printC("dewdd tm ex... added ... " + x.size() + " , "
					+ x4.size());
		}
	}

	class bc implements Runnable {

		public void run() {
			dprint.r.printC("...");
			Block bt = null;

			int mx = randomG.nextInt(50000) + 10000;

			for (int co = 0; co < mx; co++) {
				if (x.size() <= 0) {

					for (int co2 = 0; co2 < x4.size(); co2++) {
						bt = world4.pop().getBlockAt(x4.pop(), y4.pop(),
								z4.pop());

						bt.setTypeIdAndData(id4.pop(), data4.pop(), false);

					}

					dprint.r.printC("dewdd tm ex... restore block remaining... "
							+ x.size() + " , " + x4.size());
					dprint.r.printC("dewdd tm ex... restore complete");

					return;

				}
				else {

					bt = world.pop().getBlockAt(x.pop(), y.pop(), z.pop());
					bt.setTypeIdAndData(id.pop(), data.pop(), false);

				}

			}
			dprint.r.printC("dewdd tm ex... restore block remaining... "
					+ x.size() + " , " + x4.size());

			int gi = getid(world.peek().getName());
			if (gi == -1) {
				return;
			}
			Bukkit.getServer().getScheduler()
					.scheduleSyncDelayedTask(ac, new bc(), isrunworlddelay[gi]);
		}
	}

	JavaPlugin		ac;

	Stack<Byte>		data			= new Stack<Byte>();

	Stack<Byte>		data4			= new Stack<Byte>();
	public String	folder_name		= "plugins" + File.separator
											+ "dewdd_rollback_explosion";
	Stack<Integer>	id				= new Stack<Integer>();
	Stack<Integer>	id4				= new Stack<Integer>();
	String			isrunworld[]	= new String[10];
	int[]			isrunworlddelay	= new int[10];

	int				isrunworldmax	= 0;
	Random			randomG			= new Random();
	Random			rd				= new Random();
	Stack<World>	world			= new Stack<World>();
	Stack<World>	world4			= new Stack<World>();
	Stack<Integer>	x				= new Stack<Integer>();

	Stack<Integer>	x4				= new Stack<Integer>();

	Stack<Integer>	y				= new Stack<Integer>();
	Stack<Integer>	y4				= new Stack<Integer>();
	Stack<Integer>	z				= new Stack<Integer>();

	Stack<Integer>	z4				= new Stack<Integer>();

	public DigEventListener2() {
		loadworldfile();
	}

	public boolean blockexplode(int blockid) {

		switch (blockid) {
		case 54:
		case 58:
		case 61:
		case 62:
		case 146:
		case 137:
		case 52:
		case 154:
		case 158:
		case 23:
		case 63:
		case 68:
		case 117:
		case 19:
			return true;

		}

		return false;
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event)
			throws UnknownDependencyException, InvalidPluginException,
			InvalidDescriptionException {
		Player player = event.getPlayer();
		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd dewddtmex");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd dewddtmex") == true) {
			player.sendMessage("dewdd tmex can rollback explosion in 30 seconds, drop item in hand for forcing rollback");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(ChunkUnloadEvent event) {

		if (x.size() > 0) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(EntityExplodeEvent event) {

		int gi = getid(event.getLocation().getWorld().getName());
		if (gi == -1) {
			return;
		}

		if (x.size() == 0) {
			event.setCancelled(true);
		}

		// search
		// anti explade
		Block b2 = null;
		for (int x2 = -10; x2 <= 10; x2++) {
			for (int y2 = -10; y2 <= 10; y2++) {
				for (int z2 = -10; z2 <= 10; z2++) {

					b2 = event.getLocation().getBlock().getRelative(x2, y2, z2);
					if (blockexplode(b2.getTypeId())) {
						event.setCancelled(true);
						return;
					}

				}
			}
		}

		abx aaa = new abx();
		aaa.block = event.getLocation().getBlock();

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, aaa);

		Bukkit.getServer().getScheduler()
				.scheduleSyncDelayedTask(ac, new bc(), isrunworlddelay[gi]);

		// save
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		String m[] = event.getMessage().split("\\s+");

		if (m[0].equalsIgnoreCase("/rbes")) {

			if (m.length == 1) {
				p.sendMessage("/rbes reload");
				p.sendMessage("/rbes add <worldname> <delay>");
				p.sendMessage("/rbes remove <worldname>");
				p.sendMessage("/rbes list");
				return;
			}
			else if (m.length == 2) {
				if (m[1].equalsIgnoreCase("reload")) {
					p.sendMessage("loading");
					loadworldfile();
					p.sendMessage("loaded");
					return;
				}
				else if (m[1].equalsIgnoreCase("list")) {
					for (int i = 0; i < isrunworldmax; i++) {
						p.sendMessage(isrunworld[i] + " " + isrunworlddelay[i]);
					}
					return;
				}
			}
			else if (m.length == 3) {
				if (m[1].equalsIgnoreCase("remove")) {
					p.sendMessage("removing " + m[2]);
					saveworldfile(m[2]);
					p.sendMessage("removed " + m[2]);
					return;
				}
			}
			else if (m.length == 4) {
				if (m[1].equalsIgnoreCase("add")) {

					boolean s = false;
					for (int i = 0; i < isrunworldmax; i++) {
						if (isrunworld[i].equalsIgnoreCase(m[2])) {
							isrunworlddelay[i] = Integer.parseInt(m[3]);
							saveworldfile("-");
							p.sendMessage("modified delay");
							saveworldfile("-");
							return;
						}
					}

					// not found
					isrunworldmax++;
					isrunworld[isrunworldmax - 1] = m[2];
					isrunworlddelay[isrunworldmax - 1] = Integer.parseInt(m[3]);
					saveworldfile("-");
					p.sendMessage("added this config");

					return;
				}
			}

		}

	}

	@EventHandler
	public void eventja(PlayerDropItemEvent event) {

		Bukkit.getServer().getScheduler().runTask(ac, new bc());

	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {

		// event.getPlayer().sendMessage("dewdd tm ex");
		// event.getPlayer().sendMessage("facebook : https://www.facebook.com/dewddminecraft");
	}

	public int getid(String wn) {
		for (int i = 0; i < isrunworldmax; i++) {
			if (isrunworld[i].equalsIgnoreCase(wn)) {
				return i;
			}

		}

		return -1;
	}

	public void loadworldfile() {
		String worldf = "run_worlds.txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + worldf;
		File fff = new File(filena);

		try {

			isrunworldmax = 0;
			isrunworld = new String[100];
			isrunworlddelay = new int[100];
			fff.createNewFile();

			dprint.r.printC("ptdeW&DewDD rollback explosion loading : "
					+ filena);
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filena);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			// sthae
			// aosthoeau
			// * save
			String m[];

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				// Print the content on the console
				isrunworldmax++;
				isrunworld[isrunworldmax - 1] = m[0];

				if (m.length == 1) {
					isrunworlddelay[isrunworldmax - 1] = 600;
				}
				else if (m.length == 2) {
					isrunworlddelay[isrunworldmax - 1] = Integer.parseInt(m[1]);

				}

				dprint.r.printC(isrunworldmax + " '"
						+ isrunworld[isrunworldmax - 1] + "' delay '"
						+ isrunworlddelay[isrunworldmax - 1] + "'");
			}

			dprint.r.printC("ptdew&DewDD rollback explosion Loaded " + filena);

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			dprint.r.printC("ptdew&dewdd rollback explosion load file error "
					+ filena + e.getMessage());
		}
	}

	public void saveworldfile(String worldname) {

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + "run_worlds.txt";
		File fff = new File(filena);

		FileWriter fwriter;
		try {
			fff.createNewFile();

			dprint.r.printC("ptdew&dewdd rollback explosion : Start saving "
					+ filena);
			fwriter = new FileWriter(fff);

			for (int y = 0; y < isrunworldmax; y++) {

				if (isrunworld[y].equalsIgnoreCase(worldname)) {
					continue;
				}

				String wr = "";
				wr = isrunworld[y] + " " + isrunworlddelay[y];

				fwriter.write(wr + System.getProperty("line.separator"));

			}

			fwriter.close();
			dprint.r.printC("ptdew&dewdd rollback explosion : saved " + filena);
			return;

		}
		catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}
}