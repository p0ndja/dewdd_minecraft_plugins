/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddinv;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	class LastInv {
		public Inventory inv;
		public String playerName;
		public int now;
		public int x[];
		public int y[];
		public int z[];
		public String wn[];
	}

	int msize = 1000;
	int goslot = 1;
	int backslot = 0;

	int picback = Material.LEATHER.getId();
	int picgo = Material.LEVER.getId();

	public int maxch = 10;

	public JavaPlugin ac = null;
	ArrayList<LastInv> inv = new ArrayList<LastInv>();

	public String folder_name = "plugins" + File.separator + "dewdd_inv";

	public DigEventListener2() {


	}

	@EventHandler
	public void eventja(InventoryClickEvent e) {
		// 26 and 35
		if (!tr.isrunworld(ac.getName(), e.getWhoClicked().getWorld().getName())) {
			return;
		}

		Player p = (Player) e.getWhoClicked();
		// printall("click " + e.getSlot() );
		// printall("name " + e.getInventory().getName());

		int getid = getid(p.getName());

		boolean fou = true;

		Inventory er = e.getInventory();

		if (er.getItem(goslot) == null) {
			fou = false;
		} else {
			if (er.getItem(goslot).getTypeId() != picgo) {
				fou = false;
			}
		}

		if (fou == false) {
			return;
		}

		// back
		fou = true;
		if (er.getItem(backslot) == null) {
			fou = false;
		} else {
			if (er.getItem(backslot).getTypeId() != picback) {
				fou = false;
			}
		}

		if (fou == false) {
			return;
		}

		if (fou == true) {
			// p.sendMessage("ptdew_inv " + inv.get(getid).now);

			if (e.getSlot() == goslot && e.getCurrentItem().getTypeId() == picgo) {
				e.setCancelled(true);
				inv.get(getid).now++;
				if (inv.get(getid).now >= maxch) {
					inv.get(getid).now = 0;
				}

				Block b3 = Bukkit.getWorld(inv.get(getid).wn[inv.get(getid).now]).getBlockAt(inv.get(getid).x[inv.get(getid).now],
						inv.get(getid).y[inv.get(getid).now], inv.get(getid).z[inv.get(getid).now]);

				/*
				 * if (dewset.checkpermissionarea(b3, p, "right") == true) {
				 * p.sendMessage (
				 * "That is another protcetd block you can't open it!"); return;
				 * }
				 */

				if (b3.getTypeId() != 54) {
					p.sendMessage("can't open this chest " + inv.get(getid).now + " location at "
							+ inv.get(getid).x[inv.get(getid).now] + "," + inv.get(getid).y[inv.get(getid).now] + ","
							+ inv.get(getid).z[inv.get(getid).now] + " at " + inv.get(getid).wn[inv.get(getid).now]);
					return;
				}

				Chest xrch = (Chest) b3.getState();

				Inventory xr = xrch.getInventory();
				p.closeInventory();

				p.openInventory(xr);

			} // 26
			else if (e.getSlot() == backslot && e.getCurrentItem().getTypeId() == picback) {
				e.setCancelled(true);

				inv.get(getid).now--;
				if (inv.get(getid).now < 0) {
					inv.get(getid).now = maxch - 1;
				}

				Block b3 = Bukkit.getWorld(inv.get(getid).wn[inv.get(getid).now]).getBlockAt(inv.get(getid).x[inv.get(getid).now],
						inv.get(getid).y[inv.get(getid).now], inv.get(getid).z[inv.get(getid).now]);

				/*
				 * if (dewset.checkpermissionarea(b3, p, "right") == true) {
				 * p.sendMessage (
				 * "That is another protcetd block you can't open it!"); return;
				 * }
				 */

				if (b3.getTypeId() != 54) {
					p.sendMessage("can't open this chest " + inv.get(getid).now + " location at "
							+ inv.get(getid).x[inv.get(getid).now] + "," + inv.get(getid).y[inv.get(getid).now] + ","
							+ inv.get(getid).z[inv.get(getid).now] + " at " + inv.get(getid).wn[inv.get(getid).now]);
					return;
				}

				Chest xrch = (Chest) b3.getState();

				Inventory xr = xrch.getInventory();
				p.closeInventory();

				p.openInventory(xr);

			} // 26

		}

	}

	@EventHandler
	public void eventja(InventoryOpenEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		int getid = getid(e.getPlayer().getName());
		// printall("Inventory open " + e.getInventory().getType().name());
		// printall("Inventory open type " + e.getInventory().getName());

		inv.get(getid).inv = e.getInventory();

		Player p = (Player) e.getPlayer();

		// go
		boolean fou = true;

		Inventory er = e.getInventory();

		if (er.getItem(goslot) == null) {
			fou = false;
		} else {
			if (er.getItem(goslot).getTypeId() != picgo) {
				fou = false;
			}
		}

		if (fou == false) {
			return;
		}

		// back
		fou = true;
		if (er.getItem(backslot) == null) {
			fou = false;
		} else {
			if (er.getItem(backslot).getTypeId() != picback) {
				fou = false;
			}
		}

		if (fou == false) {
			return;
		}

		if (fou == true) {
			// printall("run run run");

			ItemStack it = new ItemStack(picgo);
			it.setAmount(1);
			it.setTypeId(picgo);
			ItemMeta itx = it.getItemMeta();
			itx.setDisplayName("next");

			it.setItemMeta(itx);

			it.addUnsafeEnchantment(Enchantment.OXYGEN, 8);
			// printall("added " + e.getInventory().getItem(26).getTypeId());

			if (e.getInventory().getItem(goslot) == null) {
				e.getInventory().setItem(goslot, it);
				p.updateInventory();
			}

			if (e.getInventory().getItem(goslot).getTypeId() != picgo) {
				e.getInventory().setItem(goslot, it);
				p.updateInventory();
			}

			it.setTypeId(picback);
			itx = it.getItemMeta();
			// itx.setDisplayName("<back");
			itx.setDisplayName("back");
			it.setItemMeta(itx);

			if (e.getInventory().getItem(backslot) == null) {
				e.getInventory().setItem(backslot, it);
				p.updateInventory();
			}

			if (e.getInventory().getItem(backslot).getTypeId() != picback) {
				e.getInventory().setItem(backslot, it);
				p.updateInventory();
			}

		}

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		String m[] = e.getMessage().split("\\s+");
		Player p = e.getPlayer();
		
		int getid = getid(p.getName());
		LastInv tmp = inv.get(getid);

		if (m[0].equalsIgnoreCase("/dinvl")) {
			if (tmp.inv == null) {
				p.sendMessage("ptdew&dewdd : Please Open some inventory...");

				return;
			}

			p.openInventory(tmp.inv);

		}

		if (m[0].equalsIgnoreCase("/dinvload")) {
			loadinventoryfile(p.getName());
		}
		if (m[0].equalsIgnoreCase("/dinvsave")) {
			saveinventoryfile(p.getName());
		}

		if (m[0].equalsIgnoreCase("/dinv")) {
			
			p.sendMessage("/dinv <set||open> <0-10> )");
			if (m.length == 1) {
				// show all chest
				Block b3 = null;
				for (int l1 = 0; l1 < maxch; l1++) {
					if (tmp.wn[l1] == null) {
						tmp.wn[l1] = "world";
					}

					if (tmp.wn[l1].equalsIgnoreCase("")) {
						continue;
					}

					b3 = Bukkit.getWorld(tmp.wn[l1]).getBlockAt(tmp.x[l1], tmp.y[l1],
							tmp.z[l1]);

					if (b3.getTypeId() != 54) {
						continue;
					}

					p.sendMessage("inv_" + l1 + " at " + tmp.x[l1] + "," + tmp.y[l1] + ","
							+ tmp.z[l1] + " at " + tmp.wn[l1]);

				}

			} else if (m.length == 3) {
				int n3 = Integer.parseInt(m[2]);

				Boolean mode = false;

				if (m[1].equalsIgnoreCase("set") || m[1].equalsIgnoreCase("s")) {
					mode = true;
				} else if (m[1].equalsIgnoreCase("open") || m[1].equalsIgnoreCase("o")) {
					mode = false;
				} else {
					p.sendMessage("arguments 2 must be  set,s,open,s");
					return;
				}

				if (mode == true) { // set
					Block b3 = p.getLocation().getBlock().getRelative(0, 0, 0);
					p.sendMessage(b3.getTypeId() + "");

					if (b3.getTypeId() != 54) {
						p.sendMessage("please stand on chest block id 54");
						return;
					}

					// printall ("set");

					tmp.x[n3] = b3.getX();
					tmp.y[n3] = b3.getY();
					tmp.z[n3] = b3.getZ();
					tmp.wn[n3] = b3.getWorld().getName();

					p.sendMessage("saved chest " + n3 + " location at " + tmp.x[n3] + "," + tmp.y[n3]
							+ "," + tmp.z[n3] + " at " + tmp.wn[n3]);
					saveinventoryfile(p.getName());

				} else { // mode open

					Block b3 = Bukkit.getWorld(tmp.wn[n3]).getBlockAt(tmp.x[n3], tmp.y[n3],
							tmp.z[n3]);

					// check permission

					/*
					 * if (dewset.checkpermissionarea(b3, p, "right") == true) {
					 * p.sendMessage(
					 * "That is another protcetd block you can't open it!");
					 * return; }
					 */

					if (b3.getTypeId() != 54) {
						p.sendMessage("can't open this chest " + n3 + " location at " + tmp.x[n3] + ","
								+ tmp.y[n3] + "," + tmp.z[n3] + " at " + tmp.wn[n3]);

						return;
					}

					// must be check permission

					Chest xrch = (Chest) b3.getState();

					/*
					 * Inventory xr = Bukkit.createInventory
					 * (xrch.getInventory().getHolder(),
					 * xrch.getInventory().getSize(), "ptdew_inv " +
					 * tmp.now); Bukkit.
					 */
					Inventory xr = xrch.getInventory();

					boolean fou = true;

					if (xr.getItem(goslot) == null) {
						fou = false;
					} else {
						if (xr.getItem(goslot).getTypeId() != picgo) {
							fou = false;
						}
					}

					if (fou == false) {
						ItemStack it = new ItemStack(picgo);
						it.setAmount(1);
						it.setTypeId(picgo);
						ItemMeta itx = it.getItemMeta();
						itx.setDisplayName("next");

						it.setItemMeta(itx);

						it.addUnsafeEnchantment(Enchantment.OXYGEN, 8);

						xr.setItem(goslot, it);

					}
					// back

					fou = true;

					if (xr.getItem(backslot) == null) {
						fou = false;
					} else {
						if (xr.getItem(backslot).getTypeId() != picback) {
							fou = false;
						}
					}

					if (fou == false) {
						ItemStack it = new ItemStack(picback);
						it.setAmount(1);
						it.setTypeId(picback);
						ItemMeta itx = it.getItemMeta();
						itx.setDisplayName("back");

						it.setItemMeta(itx);

						it.addUnsafeEnchantment(Enchantment.OXYGEN, 8);

						xr.setItem(backslot, it);

					}
					p.openInventory(xr);

				} // mode
			}

		}

	}

	@EventHandler
	public void eventja(PlayerJoinEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		Player p = e.getPlayer();

		// int getid = getid(p.getName());
		loadinventoryfile(p.getName());
	}

	public int getid(String n) {

		for (int i = 0; i < inv.size(); i++) {
			LastInv tmp = inv.get(i);

			if (n.equalsIgnoreCase(tmp.playerName)) {
				return i;
			}
		}
		
		LastInv newer = new LastInv();
		newer.playerName = n;
		inv.add(newer);

		return inv.size()-1;
	}

	public void loadinventoryfile(String pname) {
		String filena2 = "ptdew_dewdd_inv_" + pname + ".txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + filena2;

		try {
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
			int getid = getid(pname);
			inv.get(getid).now = -1;
			for (int l1 = 0; l1 < maxch; l1++) {
				inv.get(getid).x[l1] = 0;
				inv.get(getid).y[l1] = 0;
				inv.get(getid).z[l1] = 0;
				inv.get(getid).wn[l1] = "world";

			}

			int count = 0;
			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");
				count++;
				inv.get(getid).x[count - 1] = Integer.parseInt(m[0]);
				inv.get(getid).y[count - 1] = Integer.parseInt(m[1]);
				inv.get(getid).z[count - 1] = Integer.parseInt(m[2]);
				inv.get(getid).wn[count - 1] = (m[3]);

			}

			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dprint.r.printC("load inventory " + filena);
		Bukkit.getPlayer(pname + " loaded inv file");
	}

	public void saveinventoryfile(String pname) {
		String filena2 = "ptdew_dewdd_inv_" + pname + ".txt";

		File dir = new File(folder_name);
		dir.mkdir();

		String filena = folder_name + File.separator + filena2;
		File fff = new File(filena);
		FileWriter fwriter;

		try {
			fff.createNewFile();

			dprint.r.printC("ptdew&dewdd:Start saving " + filena);
			fwriter = new FileWriter(fff);

			int getid = getid(pname);

			for (int l1 = 0; l1 < maxch; l1++) {
				String wr = inv.get(getid).x[l1] + " " + inv.get(getid).y[l1] + " " + inv.get(getid).z[l1] + " "
						+ inv.get(getid).wn[l1];

				fwriter.write(wr + System.getProperty("line.separator"));

			}

			fwriter.close();
			dprint.r.printC("ptdew&dewdd:saved " + filena);
			return;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dprint.r.printC("saved inventory " + filena);
	}

}