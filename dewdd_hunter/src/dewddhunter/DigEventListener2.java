/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddhunter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class DigEventListener2 implements Listener {

	public Random	randomGenerator	= new Random();

	public int		selectmax		= 29;

	public String	selectname[]	= new String[selectmax + 1];
	public int		selectkill[]	= new int[selectmax + 1];
	public int		selectdead[]	= new int[selectmax + 1];
	public Location	selectloca[]	= new Location[selectmax + 1];

	public void compass() {
		for (Player bb : Bukkit.getOnlinePlayers()) {
			compasssub(bb);
		}
	}

	public void compasssub(Player player) {
		String far1 = "";
		int farR = 20000;
		int tr = 0;

		for (Player px : Bukkit.getOnlinePlayers()) {
			if (px.getName().equalsIgnoreCase(player.getName()) == true) {
				continue;
			}
			if (px.getWorld().getName()
					.equalsIgnoreCase(player.getWorld().getName()) == false) {
				continue;
			}

			tr = distance2d((int) px.getLocation().getX(), (int) px
					.getLocation().getZ(), (int) player.getLocation().getX(),
					(int) player.getLocation().getZ());
			if (tr < farR && tr > 60) {
				farR = tr;
				far1 = px.getName();
			}

		}
		if (far1.equalsIgnoreCase("") == true) {
			// player.setCompassTarget(player.getLocation());
		}
		else {
			player.setCompassTarget(Bukkit.getPlayer(far1).getLocation());
		}
	}

	public int distance2d(int x1, int z1, int x2, int z2) {
		double t1 = Math.pow(x1 - x2, 2);
		double t2 = Math.pow(z1 - z2, 2);
		double t3 = Math.pow(t1 + t2, 0.5);
		int t4 = (int) t3;
		return t4;
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event)
			throws UserDoesNotExistException {
		Player player = event.getPlayer();
		int getid = getfreeselect(player);

		if (selectloca[getid] != null) {

			player.getWorld().loadChunk((int) selectloca[getid].getX(),
					(int) selectloca[getid].getZ());
			player.teleport(selectloca[getid]);

			player.sendMessage("warp player to : (" + selectloca[getid].getX()
					+ "," + selectloca[getid].getZ() + ")");
			selectloca[getid] = null;
		}

		if (event.getMessage().equalsIgnoreCase("compass") == true) {
			if (player.getInventory().first(Material.COMPASS.getId()) == -1) {
				ItemStack xx = new ItemStack(Material.COMPASS.getId(), 1);

				player.getInventory().addItem(xx.getData().toItemStack());

			}
			event.setCancelled(true);
		}

		if (event.getMessage().equalsIgnoreCase("dig_speed") == true) {
			if (player.getItemInHand() == null) {
				player.sendMessage("ptdew&dewdd: your item in hand is empty... can't enchant for you");
				return;
			}
			event.setCancelled(true);
			switch (player.getItemInHand().getTypeId()) {
			case 269:
			case 270:
			case 271:

			case 273:
			case 274:
			case 275:

			case 277:
			case 278:
			case 279:

			case 284:
			case 285:
			case 286:

			case 256:
			case 257:
			case 258:
				// dew.printA("EN" +
				// player.getItemInHand().getEnchantmentLevel(Enchantment.DIG_SPEED));
				if (player.getItemInHand().getEnchantmentLevel(
						Enchantment.DIG_SPEED) == 0) {
					player.getItemInHand().addUnsafeEnchantment(
							Enchantment.DIG_SPEED, 100);
					player.sendMessage("ptdew&dewdd: เพิ่มพลังการขุดเร็วให้กับ '"
							+ player.getItemInHand().getType().name() + "' :D");
				}
				return;
			default:
				player.sendMessage("ptdew&dewdd: อัพเกรดให้แต่ ขวาน ที่ขุดแร่ และที่ขุดดินครับ");
				return;
			}
		}

		if (event.getMessage().equalsIgnoreCase("loadall") == true) {
			for (int x = -500; x <= 500; x += 16) {
				for (int z = -500; z <= 500; z += 16) {
					Bukkit.getWorld("world").loadChunk(x, z);
					printAll("loadall (" + x + "," + z + ")");
				}
			}
			event.setCancelled(true);
		}

		if (event.getMessage().equalsIgnoreCase("stats") == true) {
			for (Player jj : Bukkit.getOnlinePlayers()) {
				int geti = getfreeselect(jj);
				player.sendMessage("'" + jj.getName() + "' Kill("
						+ selectkill[geti] + ") , Dead(" + selectdead[geti]
						+ ") , money(" + Economy.getMoney(jj.getName()) + ")");

			}
			event.setCancelled(true);
		}

		if (event.getMessage().equalsIgnoreCase("0854267172") == true) {
			int x = randomGenerator.nextInt(1000);
			x -= 500;
			int z = randomGenerator.nextInt(1000);
			z -= 500;

			player = event.getPlayer();
			Block block = null;
			player.getWorld().loadChunk(x, z);

			for (int y = 253; y >= 1; y--) {
				block = player.getWorld().getBlockAt(x, y, z);
				if (block.getTypeId() != 0 && block.getTypeId() != 8
						&& block.getTypeId() != 9 && block.getTypeId() != 10) {
					Location loc = player.getLocation();
					loc.setX(x);
					loc.setZ(z);
					loc.setY(y);
					player.teleport(loc);
					player.sendMessage("teleport to (" + x + "," + z + ")");
					break;
				}

			}
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(BlockBreakEvent event) {

		compass();

		if (event.getBlock().getTypeId() == 52) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(BlockPlaceEvent event) {
		compass();
	}

	@EventHandler
	public void eventja(PlayerDeathEvent event)
			throws UserDoesNotExistException, NoLoanPermittedException {
		Player player = event.getEntity().getPlayer();
		if (event.getEntity().getKiller() == null) {
			Economy.subtract(player.getName(),
					((Economy.getMoney(player.getName()) * 30) / 100));
			return;
		}
		if (event.getEntity().getKiller().getType() != EntityType.PLAYER) {
			Economy.subtract(player.getName(),
					((Economy.getMoney(player.getName()) * 30) / 100));
			return;
		}

		Player killer = event.getEntity().getKiller();
		double g10 = ((Economy.getMoney(player.getName()) * 10) / 100);
		Economy.subtract(player.getName(), g10);
		Economy.add(killer.getName(), g10);

		int getId = getfreeselect(killer);
		selectkill[getId]++;
		printAll("Hunter...'" + killer.getName() + "' Kill("
				+ selectkill[getId] + ") , Dead(" + selectdead[getId]
				+ ") , money(" + Economy.getMoney(killer.getName()) + ")");

		getId = getfreeselect(player);
		selectdead[getId]++;
		printAll("Vitim...'" + player.getName() + "' Kill(" + selectkill[getId]
				+ ") , Dead(" + selectdead[getId] + ") , money("
				+ Economy.getMoney(player.getName()) + ")");

		savekilldeadfile(player);
		savekilldeadfile(killer);
		/*
		 * for (ItemStack itm : player.getInventory().getArmorContents()) {
		 * if (itm == null) {
		 * continue;
		 * }
		 * if (itm.getTypeId() == 0) {
		 * continue;
		 * }
		 * Location loc = player.getLocation();
		 * loc.setY(253);
		 * player.getWorld().dropItemNaturally(loc,
		 * itm.getData().toItemStack());
		 * }
		 * for (ItemStack itm : player.getInventory().getContents()) {
		 * if (itm == null) {
		 * continue;
		 * }
		 * if (itm.getTypeId() == 0) {
		 * continue;
		 * }
		 * Location loc = player.getLocation();
		 * loc.setY(253);
		 * player.getWorld().dropItemNaturally(loc,
		 * itm.getData().toItemStack());
		 * }
		 * event.getDrops().clear();
		 */
		event.setDroppedExp((int) (event.getDroppedExp() * 3.3333));
		printAll("ptdew&dewdd: '" + player.getName() + "' game over @_@");
		printAll("ptdew&dewdd: '" + player.getName()
				+ "' ได้จากโลกนี้ไปแล้ว  @_@");
		/*
		 * player.setBanned(true);
		 * player.kickPlayer( "'" + player.getName() + "' game over @_@" );
		 */
	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) throws UserDoesNotExistException {
		Player player = event.getPlayer();
		player.sendMessage("ptdew&dewdd: Welcome to Hunter-Dewdd");
		player.sendMessage("ptdew&dewdd: dewdd.no-ip.org:25568");
		player.sendMessage("ptdew&dewdd: พิมพ์ว่า compass เพื่อขอเข็มทิศ");
		player.sendMessage("ptdew&dewdd : เข็มทิศ จะชี้ไปหาผู้เล่นที่อยู่ใกล้เรามากที่สุดเสมอ");
		player.sendMessage("ptdew&dewdd : พิมพ์ว่า stats เพื่อดูสถิติ");
		player.sendMessage("ptdew&dewdd : ใช้ dig_speed ได้นะครับ");

		loadkilldeadfile(player);
		int getId = getfreeselect(player);

		printAll("'" + player.getName() + "' Kill(" + selectkill[getId]
				+ ") , Dead(" + selectdead[getId] + ") , money("
				+ Economy.getMoney(player.getName()) + ")");

	}

	@EventHandler
	public void eventja(PlayerRespawnEvent event) {

		boolean teleportt = false;
		while (teleportt == false) {
			int x = randomGenerator.nextInt(1000);
			x -= 500;
			int z = randomGenerator.nextInt(1000);
			z -= 500;

			Player player = event.getPlayer();
			Block block = null;
			player.getWorld().loadChunk(x, z);

			for (int y = 253; y >= 1; y--) {
				block = player.getWorld().getBlockAt(x, y, z);
				if (block.getTypeId() != 0) {
					if (block.getTypeId() == 8 || block.getTypeId() == 9
							|| block.getTypeId() == 10
							|| block.getTypeId() == 11) {
						continue;
					}
					Location loc = player.getLocation();
					loc.setY(y + 2);
					loc.setX(x);
					loc.setZ(z);
					int getid = getfreeselect(player);

					selectloca[getid] = loc;
					// event.setRespawnLocation(loc);
					// player.teleport(loc);
					player.sendMessage("พิมพ์ไรก็ได้เพื่อวาปไปที่ใหม่ teleport to ("
							+ x + "," + z + ")");
					teleportt = true;
					break;
				}

			}

		}
	}

	public int getfreeselect(Player player) {
		int lp1 = 0;

		boolean foundza = false;
		// clear select array
		for (lp1 = 0; lp1 < selectmax; lp1++) {

			if (selectname[lp1] == null
					|| selectname[lp1].equalsIgnoreCase("") == true) {
				selectname[lp1] = "null";
				selectkill[lp1] = 0;
				selectdead[lp1] = 0;
			}
		} // loop allselect

		// clear ofline player
		for (lp1 = 0; lp1 < selectmax; lp1++) {
			foundza = false;
			// loop player for clear
			for (Player pla : Bukkit.getOnlinePlayers()) {
				if (pla.getName().equalsIgnoreCase(selectname[lp1]) == true) {
					foundza = true;
					break;
					// found
				}

			} // loop all player

			if (foundza == false) { // clear
				selectname[lp1] = "null";
				selectkill[lp1] = 0;
				selectdead[lp1] = 0;
			}

		}

		// if fonund return
		for (lp1 = 0; lp1 < selectmax; lp1++) {
			if (player.getName().equalsIgnoreCase(selectname[lp1]) == true) {
				return lp1;
			}
		}

		// if not found
		for (lp1 = 0; lp1 < selectmax; lp1++) {
			if (selectname[lp1].equalsIgnoreCase("null") == true) {
				selectname[lp1] = player.getName();
				return lp1;
			}
		}

		printC("ptdew&dewdd: GetFreeselect return -1");
		return -1;
	}

	public void loadkilldeadfile(Player player) {
		String folderpathh = "plugins\\dewddhunter";
		String filepathh = folderpathh + "\\" + player.getName() + ".txt";

		try {

			System.out.println("ptdeW&DewDD hunter : Starting Loading '"
					+ filepathh + "'");
			// Open the file that is the first
			// command line parameter

			File theDir = new File(folderpathh);

			// if the directory does not exist, create it
			if (!theDir.exists()) {
				boolean result = theDir.mkdir();
				if (result == true) {
					System.out.println("DIR dewddhunter created");
				}
			}
			File f = new File(filepathh);
			int getId = getfreeselect(player);

			if (!f.exists()) {
				selectkill[getId] = 0;
				selectdead[getId] = 0;
				return;
			}

			FileInputStream fstream = new FileInputStream(filepathh);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line

			strLine = br.readLine();
			selectkill[getId] = Integer.parseInt(strLine);

			strLine = br.readLine();
			selectdead[getId] = Integer.parseInt(strLine);

			System.out.println("ptdew&DewDD hunter : 'dewddhunter\\"
					+ player.getName() + ".txt'");

			in.close();
		}
		catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void printA(String abc) {
		for (Player pla : Bukkit.getOnlinePlayers()) {
			pla.sendMessage(abc);
		}
	}

	public void printAll(String abc) {
		printA(abc);
		printC(abc);
	}

	public void printC(String abc) {
		System.out.println(abc);
	}

	public void savekilldeadfile(Player player) {

		String folderpathh = "plugins\\dewddhunter";
		String filepathh = folderpathh + "\\" + player.getName() + ".txt";

		File inFile = new File(filepathh);

		FileWriter fwriter;
		try {
			printC("ptdew&dewdd:Start saving '" + filepathh + "'");
			fwriter = new FileWriter(inFile);

			int getId = getfreeselect(player);

			fwriter.write(Integer.toString(selectkill[getId])
					+ System.getProperty("line.separator"));
			fwriter.write(Integer.toString(selectdead[getId])
					+ System.getProperty("line.separator"));

			// printC ("ptdew&dewdd: Saved y= " + y );

			fwriter.close();
			printC("ptdew&dewdd:saved '" + filepathh + "'");
			return;

		}
		catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

		// printC ("ptdew&dewdd: save Done...");

		// ***************************88

		// ******************************
	}

	// EntityExplodeEvent

	// PlayerDeathEvent

	// PlayerDropItemEvent

	// PlayerExpChangeEvent

	// PlayerInteractEvent

	// PlayerMoveEvent

	// PlayerMoveEvent

	// PlayerRespawnEvent

} // class

