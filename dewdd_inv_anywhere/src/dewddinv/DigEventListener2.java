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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	class Position {
		public int x;
		public int y;
		public int z;
		public String worldName;
	}

	class DewInv {
		public Inventory inv;
		public String playerName;
		public int now;
		public ArrayList<Position> position = new ArrayList<Position>();
	}

	int goslot = 1;
	int backslot = 0;
	int menuslot = 17;

	int picback = Material.CHEST.getId();
	int picgo = Material.CHEST.getId();
	int picmenu = Material.CHEST.getId();

	public JavaPlugin ac = null;
	ArrayList<DewInv> inv = new ArrayList<DewInv>();

	public String folder_name = "plugins" + File.separator + "dewdd_inv";

	public DigEventListener2() {

	}

	@EventHandler
	public void eventja(InventoryClickEvent e) {
		// 26 and 35
		if (!tr.isrunworld(ac.getName(), e.getWhoClicked().getWorld().getName())) {
			return;
		}

		// dprint.r.printC("InvType " + e.getInventory().getType().name() + " ,
		// slot " + e.getSlot());

		Player p = (Player) e.getWhoClicked();
		int getid = getid(p.getName());
		DewInv tmp = inv.get(getid);

		// dprint.r.printAll("clicked slot " + e.getSlot() + " , type " +
		// e.getInventory().getType().name());

		// printall("click " + e.getSlot() );
		// printall("name " + e.getInventory().getName());

		if (e.getInventory().getType() == InventoryType.CRAFTING) {
			ItemStack it = new ItemStack(picmenu);
			it.setAmount(1);
			it.setTypeId(picmenu);
			ItemMeta itx = it.getItemMeta();
			itx.setDisplayName("DewInv");

			it.setItemMeta(itx);

			it.addUnsafeEnchantment(Enchantment.OXYGEN, 8);

			// e.getInventory().setItem(menuslot, it);
			try {
				e.getClickedInventory().setItem(menuslot, it);
				// dprint.r.printC("player added " + menuslot);
			} catch (NullPointerException eee) {
				// eee.printStackTrace();
				return;
			}

			if (e.getSlot() == menuslot) {
				// dprint.r.printAll("seem");
				// if
				// (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("DewInv")){
				e.setCancelled(true);
				if (tmp.now >= tmp.position.size()) {
					tmp.now = tmp.position.size() - 1;
				}

				if (tmp.now < 0) {
					tmp.now = 0;
				}

				Position posi = tmp.position.get(tmp.now);

				try {

					Chest chest = (Chest) Bukkit.getWorld(posi.worldName).getBlockAt(posi.x, posi.y, posi.z).getState();

					p.openInventory(chest.getInventory());
				} catch (Exception eee) {
					p.sendMessage(eee.getMessage());
					tmp.position.remove(tmp.now);
					saveinventoryfile(p.getName());

					return;

				}
				// }
			}
			return;

		}

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
			// p.sendMessage("ptdew_inv " + tmp.now);

			if (e.getSlot() == goslot && e.getCurrentItem().getTypeId() == picgo) {
				e.setCancelled(true);
				tmp.now++;
				if (tmp.now >= tmp.position.size()) {
					tmp.now = 0;
				}

				Position posi = tmp.position.get(tmp.now);
				try {
					Bukkit.getWorld(posi.worldName).getBlockAt(posi.x, posi.y, posi.z);
				} catch (NullPointerException ee) {
					tmp.position.remove(tmp.now);
					saveinventoryfile(p.getName());
					return;
				}
				Block b3 = Bukkit.getWorld(posi.worldName).getBlockAt(posi.x, posi.y, posi.z);

				/*
				 * if (dewset.checkpermissionarea(b3, p, "right") == true) {
				 * p.sendMessage (
				 * "That is another protcetd block you can't open it!"); return;
				 * }
				 */

				if (b3.getTypeId() != 54) {
					p.sendMessage("can't open this chest " + tmp.now + " location at " + posi.x + "," + posi.y + ","
							+ posi.z + " at " + posi.worldName);

					tmp.position.remove(tmp.now);
					saveinventoryfile(p.getName());
					return;
				}

				if (api_private.DewddPrivate.hasProtect(b3)) {
					if (api_private.DewddPrivate.cando(b3, p) && api_private.DewddPrivate.nearPrivateSign(b3)) {
						addNewInventory(b3.getLocation(), inv.get(getid(p.getName())), p.getName());

					}

				}

				Chest xrch = (Chest) b3.getState();

				Inventory xr = xrch.getInventory();
				p.closeInventory();

				p.openInventory(xr);

			} // 26
			else if (e.getSlot() == backslot && e.getCurrentItem().getTypeId() == picback) {
				e.setCancelled(true);

				tmp.now--;
				if (tmp.now < 0) {
					tmp.now = tmp.position.size() - 1;
				}

				Position posi = tmp.position.get(tmp.now);
				Block b3 = Bukkit.getWorld(posi.worldName).getBlockAt(posi.x, posi.y, posi.z);

				/*
				 * if (dewset.checkpermissionarea(b3, p, "right") == true) {
				 * p.sendMessage (
				 * "That is another protcetd block you can't open it!"); return;
				 * }
				 */

				if (b3.getTypeId() != 54) {
					p.sendMessage("can't open this chest " + tmp.now + " location at " + posi.x + "," + posi.y + ","
							+ posi.z + " at " + posi.worldName);

					tmp.position.remove(tmp.now);
					saveinventoryfile(p.getName());
					return;
				}

				Chest xrch = (Chest) b3.getState();

				Inventory xr = xrch.getInventory();
				p.closeInventory();

				p.openInventory(xr);

			} // 26

		}

	}

	public void addNewInventory(Location loc, DewInv tmp, String playerName) {
		// search

		// loop all saved inventory

		for (int j = 0; j < tmp.position.size(); j++) {
			Position posi = tmp.position.get(j);
			if (posi.x == loc.getBlockX() && posi.y == loc.getBlockY() && posi.z == loc.getBlockZ()
					&& posi.worldName.equalsIgnoreCase(loc.getWorld().getName())) {
				return;
			}

		}

		Position newPosi = new Position();
		newPosi.x = loc.getBlockX();
		newPosi.y = loc.getBlockY();
		newPosi.z = loc.getBlockZ();
		newPosi.worldName = loc.getWorld().getName();

		tmp.position.add(newPosi);
		saveinventoryfile(playerName);

	}

	@EventHandler
	public void eventja(InventoryMoveItemEvent e) {

		if (!tr.isrunworld(ac.getName(), e.getDestination().getLocation().getWorld().getName()))
			return;

		try {
			
			

			ItemStack x = e.getItem();
			
			if (x == null) {
				return;
			}

			if (x.getItemMeta().getDisplayName().equalsIgnoreCase("next")) {
				e.setCancelled(true);
			}
			if (x.getItemMeta().getDisplayName().equalsIgnoreCase("back")) {
				e.setCancelled(true);
			}

		} catch (NullPointerException eee) {
			return;
		} catch (java.lang.ArrayIndexOutOfBoundsException eee) {
			return;
		}

	}

	@EventHandler
	public void eventja(InventoryPickupItemEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getItem().getWorld().getName()))
			return;

		try {

			if (e.getItem().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("next")) {
				e.setCancelled(true);
			}
			if (e.getItem().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("back")) {
				e.setCancelled(true);
			}

			if (e.getItem().getCustomName().equalsIgnoreCase("next")) {
				e.setCancelled(true);
			}
			if (e.getItem().getCustomName().equalsIgnoreCase("back")) {
				e.setCancelled(true);
			}

		} catch (NullPointerException eee) {

		}

	}

	@EventHandler
	public void eventja(InventoryOpenEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		// dprint.r.printAll(e.getInventory().getType().name());

		if (e.getInventory().getType() == InventoryType.CRAFTING) {
			ItemStack it = new ItemStack(picmenu);
			it.setAmount(1);
			it.setTypeId(picmenu);
			ItemMeta itx = it.getItemMeta();
			itx.setDisplayName("DewInv");

			it.setItemMeta(itx);

			it.addUnsafeEnchantment(Enchantment.OXYGEN, 8);

			e.getInventory().setItem(menuslot, it);
			// dprint.r.printC("player added " + menuslot);
			return;

		}

		if (e.getInventory().getType() != InventoryType.CHEST) {
			return;
		}

		try {
			if (e.getInventory().getLocation().getBlock().getType() != Material.CHEST) {

				return;
			}
		} catch (Exception eee) {
			return;
		}

		ItemStack it = new ItemStack(picgo);
		it.setAmount(1);
		it.setTypeId(picgo);
		ItemMeta itx = it.getItemMeta();
		itx.setDisplayName("next");

		it.setItemMeta(itx);

		it.addUnsafeEnchantment(Enchantment.OXYGEN, 8);

		e.getInventory().setItem(goslot, it);

		// ...........

		it = new ItemStack(picback);
		it.setAmount(1);
		it.setTypeId(picback);
		itx = it.getItemMeta();
		itx.setDisplayName("back");

		it.setItemMeta(itx);

		it.addUnsafeEnchantment(Enchantment.OXYGEN, 8);

		e.getInventory().setItem(backslot, it);
		// .............

		int getid = getid(e.getPlayer().getName());
		// printall("Inventory open " + e.getInventory().getType().name());
		// printall("Inventory open type " + e.getInventory().getName());

		DewInv tmp = inv.get(getid);
		tmp.inv = e.getInventory();

		Player p = (Player) e.getPlayer();

		Position posi = tmp.position.get(tmp.now);
		Block thatBlock = Bukkit.getWorld(posi.worldName).getBlockAt(posi.x, posi.y, posi.z);

		if (api_private.DewddPrivate.hasProtect(thatBlock) && api_private.DewddPrivate.nearPrivateSign(thatBlock)) {
			if (!api_private.DewddPrivate.cando(thatBlock, p)) {
				tmp.position.remove(tmp.now);
				saveinventoryfile(p.getName());
			}

		} else {
			tmp.position.remove(tmp.now);
			saveinventoryfile(p.getName());
		}

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

			it = new ItemStack(picgo);
			it.setAmount(1);
			it.setTypeId(picgo);
			itx = it.getItemMeta();
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
		DewInv tmp = inv.get(getid);

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

				for (int l1 = 0; l1 < tmp.position.size(); l1++) {
					Position posi = tmp.position.get(l1);

					if (posi.worldName == null) {
						posi.worldName = "world";
					}

					if (posi.worldName.equalsIgnoreCase("")) {
						continue;
					}

					b3 = Bukkit.getWorld(posi.worldName).getBlockAt(posi.x, posi.y, posi.z);

					if (b3.getTypeId() != 54) {
						continue;
					}

					p.sendMessage(
							"inv_" + l1 + " at " + posi.x + "," + posi.y + "," + posi.z + " at " + posi.worldName);

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

				if (tmp.position.size() == n3) {
					tmp.position.add(new Position());
					p.sendMessage("added New Slot " + n3);
				}

				if (n3 >= tmp.position.size()) {
					p.sendMessage("Inventory Saved Slot range = 0 to " + (tmp.position.size() - 1));
					return;
				}

				Position posi = tmp.position.get(n3);

				if (mode == true) { // set
					Block b3 = p.getLocation().getBlock().getRelative(0, 0, 0);
					p.sendMessage(b3.getTypeId() + "");

					if (b3.getTypeId() != 54) {
						p.sendMessage("please stand on chest block id 54");
						return;
					}

					// check permission
					if (api_private.DewddPrivate.hasProtect(b3)) {
						if (api_private.DewddPrivate.cando(b3, e.getPlayer())
								&& api_private.DewddPrivate.nearPrivateSign(b3)) {
							/*
							 * addNewInventory(b3.getLocation(),
							 * inv.get(getid(e.getPlayer().getName())),
							 * e.getPlayer().getName());
							 */

						} else {
							p.sendMessage("this is not your chest");
							return;
						}

					} else {
						p.sendMessage("need [private] sign near your chest");
						return;
					}

					// printall ("set");

					posi.x = b3.getX();
					posi.y = b3.getY();
					posi.z = b3.getZ();
					posi.worldName = b3.getWorld().getName();

					p.sendMessage("saved chest " + n3 + " location at " + posi.x + "," + posi.y + "," + posi.z + " at "
							+ posi.worldName);
					saveinventoryfile(p.getName());

				} else { // mode open

					Block b3 = Bukkit.getWorld(posi.worldName).getBlockAt(posi.x, posi.y, posi.z);

					// check permission

					/*
					 * if (dewset.checkpermissionarea(b3, p, "right") == true) {
					 * p.sendMessage(
					 * "That is another protcetd block you can't open it!");
					 * return; }
					 */

					if (b3.getTypeId() != 54) {
						p.sendMessage("can't open this chest " + n3 + " location at " + posi.x + "," + posi.y + ","
								+ posi.z + " at " + posi.worldName);

						tmp.position.remove(n3);
						saveinventoryfile(p.getName());
						return;
					}

					// must be check permission

					Chest xrch = (Chest) b3.getState();

					/*
					 * Inventory xr = Bukkit.createInventory
					 * (xrch.getInventory().getHolder(),
					 * xrch.getInventory().getSize(), "ptdew_inv " + tmp.now);
					 * Bukkit.
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
	public void eventja(PlayerInteractEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			if (b.getType() == Material.CHEST) {

				if (api_private.DewddPrivate.hasProtect(b)) {
					if (api_private.DewddPrivate.cando(b, e.getPlayer())
							&& api_private.DewddPrivate.nearPrivateSign(b)) {
						addNewInventory(b.getLocation(), inv.get(getid(e.getPlayer().getName())),
								e.getPlayer().getName());

					}

				}

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
			DewInv tmp = inv.get(i);

			if (n.equalsIgnoreCase(tmp.playerName)) {
				return i;
			}
		}

		DewInv newer = new DewInv();
		newer.playerName = n;
		inv.add(newer);

		return inv.size() - 1;
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

			DewInv tmp = inv.get(getid);
			tmp.now = -1;
			tmp.position.clear();

			while ((strLine = br.readLine()) != null) {

				m = strLine.split("\\s+");

				Position posi = new Position();

				posi.x = Integer.parseInt(m[0]);
				posi.y = Integer.parseInt(m[1]);
				posi.z = Integer.parseInt(m[2]);
				posi.worldName = (m[3]);

				tmp.position.add(posi);

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
			DewInv tmp = inv.get(getid);

			for (int l1 = 0; l1 < tmp.position.size(); l1++) {
				Position posi = tmp.position.get(l1);

				String wr = posi.x + " " + posi.y + " " + posi.z + " " + posi.worldName;

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