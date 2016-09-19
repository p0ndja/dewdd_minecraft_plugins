/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddprivate;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	class chatx implements Runnable {
		Player p = null;
		String message = "";

		@Override
		public void run() {
			String m[] = message.split("\\s+");
			if (m[0].equalsIgnoreCase("/dewsetline")) {
				if (m.length < 3) {
					p.sendMessage("need 3 arguments = /dewsetine line message");
					return;
				}

				int getid = getfreeselect(p.getName());
				Block c = nn.lastblock[getid];

				if (c == null) {
					p.sendMessage("please click at sign before use this command");
					return;
				}

				int ln = 0;

				try {

					ln = Integer.parseInt(m[1]);

				} catch (NumberFormatException e) {
					p.sendMessage("line must be number  1 - 4");
					return;
				}

				if (ln > 4 || ln < 1) {
					p.sendMessage("line must be in range 1 - 4");
					return;
				}

				// 38 and 63
				// String xn = "" +((char)63);
				// §

				for (int in = 3; in < m.length; in++) {
					m[2] = m[2] + " " + m[in];
				}

				m[2] = m[2].replace("&", "§");

				boolean adminper = p.hasPermission(psetlineeveryone);

				p.sendMessage("block " + c.getX() + "," + c.getY() + "," + c.getZ() + " = " + c.getTypeId() + ":"
						+ c.getData());

				switch (c.getTypeId()) {
				case 63:
				case 68:
					Sign s = (Sign) c.getState();
					if (s.getLine(0).equalsIgnoreCase("[dewdd]") == true
							&& s.getLine(1).equalsIgnoreCase(p.getName()) == true) {
						if (ln != 3 && ln != 4) {
							if (adminper == false) {
								p.sendMessage("you only can change line 3 and 4");
								return;
							}
						}

						s.setLine(ln - 1, m[2]);
						s.update();
						p.sendMessage("setline completed " + ln + " = " + m[2]);

					} else {

						if (adminper == false) {
							p.sendMessage("only admin can reset everysign");
							return;
						}

						/*
						 * for (int c8 = 0 ; c8 < s.getLine(0).length() ; c8 ++)
						 * {
						 * 
						 * byte []tea = s.getLine(0).substring(c8,c8 +
						 * 1).getBytes(); String an = ""; for (byte tt : tea){
						 * an = an + Byte.toString(tt); } p.sendMessage(c8 +
						 * " _ " + s.getLine(0).substring(c8,c8 + 1) + " = " +
						 * an);
						 * 
						 * }
						 */

						s.setLine(ln - 1, m[2]);
						s.update();
						p.sendMessage("admin overide setline completed " + ln + " = " + m[2]);

					}

					break;
				default:
					p.sendMessage("the block you are looking is not a sign is a " + c.getTypeId() + ":" + c.getData());
				}

			}

			if (p.isOp() == true) {
				if (m[0].equalsIgnoreCase("/dewrollback")) {

					if (m.length == 1) {
						p.sendMessage("/dewrollback <filename> <player>");
						return;
					}
					if (m.length == 3) {
						String pname = m[2];
						String fname = m[1];

						// load
						String folder_name = "plugins" + File.separator + "dewddlogdata";

						File dir = new File(folder_name);
						dir.mkdir();

						String filena = folder_name + File.separator + m[1];
						File fff = new File(filena);

						try {

							fff.createNewFile();

							System.out.println("ptdeW&DewDD Main : " + filena);

							FileInputStream fstream = new FileInputStream(filena);

							DataInputStream in = new DataInputStream(fstream);
							BufferedReader br = new BufferedReader(new InputStreamReader(in));
							String strLine;

							int search = 0;
							String ab = "";

							Block block = null;

							while ((ab = br.readLine()) != null) {
								// loop string
								// searth

								int moden = 0; // 1 place , 2 break

								search = ab.toLowerCase().indexOf(m[2] + "}_blockplaceevent");
								if (search == -1) {
									moden = 1;
									// p("not found place");
								}

								if (search != 1) {

									search = ab.toLowerCase().indexOf(m[2] + "}_blockbreakevent");
									if (search == -1) {
										moden = 2;
										// p("not found break");

									}

								}

								if (search == -1) {
									// p("not found");
									continue;
								}

								dprint.r.printC("block  " + search);

								int x = 0;
								int y = 0;
								int z = 0;
								int colon = 0;

								search = ab.toLowerCase().indexOf("(");
								colon = ab.toLowerCase().indexOf(",", search);
								dprint.r.printC(ab.substring(search + 1, colon));
								x = Integer.parseInt(ab.substring(search + 1, colon));

								search = ab.toLowerCase().indexOf(",");
								colon = ab.toLowerCase().indexOf(",", search + 1);
								dprint.r.printC(ab.substring(search + 1, colon));
								y = Integer.parseInt(ab.substring(search + 1, colon));

								search = ab.toLowerCase().indexOf(",", search + 1);
								colon = ab.toLowerCase().indexOf(")", search + 1);
								dprint.r.printC(ab.substring(search + 1, colon));
								z = Integer.parseInt(ab.substring(search + 1, colon));

								int iteminhand = 0;
								byte dataid = 0;

								search = ab.toLowerCase().indexOf("=", search + 1);
								colon = ab.toLowerCase().indexOf(":", search + 1);
								dprint.r.printC(ab.substring(search + 1, colon));
								iteminhand = Integer.parseInt(ab.substring(search + 1, colon));

								search = ab.toLowerCase().indexOf(":", search + 1);
								colon = ab.toLowerCase().indexOf("|", search + 1);
								dprint.r.printC(ab.substring(search + 1, colon));
								dataid = Byte.parseByte(ab.substring(search + 1, colon));

								block = Bukkit.getWorld("world").getBlockAt(x, y, z);

								int id = Material.getMaterial(iteminhand).getId();
								if (id == -1) {
									continue;
								}

								if (Material.getMaterial(id).isBlock() == false) {
									continue;
								}

								if (moden == 1) {

									block.setTypeId(iteminhand);
									block.setData(dataid);
								} // place
								{
									block.setTypeId(0);
								}

							}

							System.out.println("ptdew&DewDD Main: Loaded " + filena);

							in.close();
						} catch (Exception e) {
							System.err.println("Error load " + filena + e.getMessage());
						}

						// load file

					}

				}

			}
		}
	}

	class ddata {

		public String playername[];
		public Block lastblock[];

	}

	/*
	 * commands: dewsetline: description: dewsetline if for set new line usage:
	 * /<command> [line] [message]
	 * 
	 * permissions: dewdd.private.overide: description: for admin can open any
	 * chest... default: op dewdd.private.setline.everysign: description: for
	 * admin reset line every sign default: op
	 */

	Random randomG = new Random();

	JavaPlugin ac = null;

	String psetlineeveryone = "dewdd.private.setline.everysign";

	int ddatamax = 29;

	ddata nn = new ddata();

	public DigEventListener2() {
		nn.playername = new String[ddatamax];
		nn.lastblock = new Block[ddatamax];

		for (int i = 0; i < ddatamax; i++) {
			nn.playername[i] = "";
			nn.lastblock[i] = null;

		}
	}

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = event.getPlayer();

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd private");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd private") == true) {
			player.sendMessage("ปลั๊กอิน private เป็นระบบ ป้าย [private] , [dewdd]  ตามด้วยชื่อคนมีสิทธิ สามบรรทัด");
			player.sendMessage("ตั้งใจจะ ให้ระบบ dewset มาเชื่อมต่อกับระบบโพเทคนี้ด้วย");
			event.setCancelled(true);
		}

	}

	/*@EventHandler
	public void eventja(BlockBreakEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}
		// player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(200,
		// dew.randomGenerator.nextInt(100)),false);

		Block block = event.getBlock();
		Player player = event.getPlayer();
		// check host block
		boolean goodc1 = false;
		goodc1 = api_private.dewddprivate.checkpermissionareasign(block, player);

		// call check
		if (goodc1 == true) {
			event.setCancelled(true);
		}
	}*/

	/*@EventHandler
	public void eventja(BlockPlaceEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}
		// player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(200,
		// dew.randomGenerator.nextInt(100)),false);

		Block block = event.getBlock();
		Player player = event.getPlayer();
		// check host block
		boolean goodc1 = false;
		goodc1 = api_private.dewddprivate.checkpermissionareasign(block, player);

		// call check
		if (goodc1 == true) {
			event.setCancelled(true);
		}
	}*/

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		chatx a = new chatx();
		a.p = event.getPlayer();
		a.message = event.getMessage();
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, a);
	}

	@EventHandler
	public void eventja(PlayerInteractEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}
		
		

		Action act;
		act = event.getAction();

		if (((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)) {
			return;
		}

		Player player = event.getPlayer();

		Block block = event.getClickedBlock();
		// c = event.getClickedBlock();

		if (block.getTypeId() == 27 || block.getTypeId() == 66) {
			return;
		}
		/*boolean goodc1 = false;
		goodc1 = api_private.dewddprivate.checkpermissionareasign(block, player);

		// call check
		if (goodc1 == true) {

			event.setCancelled(true);
		}*/

		int getid = getfreeselect(player.getName());
		nn.lastblock[getid] = event.getClickedBlock();

		if (!(nn.lastblock[getid].getTypeId() == 63 || nn.lastblock[getid].getTypeId() == 68)) {
			return;
		}

		Sign s = (Sign) nn.lastblock[getid].getState();
		if (s.getLine(0).endsWith("[dewdd]") == true || s.getLine(0).endsWith("[private]") == true) {
			
			s.setLine(0, dprint.r.color("[dewdd]"));
			s.update();

			if (s.getLine(1).equalsIgnoreCase(player.getName()) == true) {

				player.sendMessage("ptdew&dewdd:  you can change line 3 and line 4 (your friend name");
				player.sendMessage("/dewsetline 3 ptdew");
				player.sendMessage("/dewsetline 4 pondja_kung");

			}

		}
		
		if (s.getLine(0).endsWith("[dewdd]") == true || s.getLine(0).endsWith("[private]") == true) {
			
			 nn.lastblock[getid].breakNaturally();
		}

	}

	@EventHandler
	public void eventja(SignChangeEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		Player player = event.getPlayer();

		/*if (event.getLine(0).equalsIgnoreCase("") == true && event.getLine(1).equalsIgnoreCase("") == true
				&& event.getLine(2).equalsIgnoreCase("") == true && event.getLine(3).equalsIgnoreCase("") == true) {
			event.setLine(0, dprint.r.color("[dewdd]"));
			event.setLine(1, player.getName());
		}*/

		if (event.getLine(0).toLowerCase().endsWith("[dewdd]") == true
				|| event.getLine(0).toLowerCase().endsWith("[private]") == true

		) {
			event.setLine(0, dprint.r.color("[dewdd]"));

			if (event.getLine(1).equalsIgnoreCase(player.getName()) == false
					&& event.getLine(2).equalsIgnoreCase(player.getName()) == false
					&& event.getLine(3).equalsIgnoreCase(player.getName()) == false) {
				player.sendMessage("ptdew&dewdd: ป้ายนี้ไม่มีชื่อของคุณอยู่เลย");
				event.setLine(0, "<dewdd>");
				event.setLine(1, "ใส่ชื่อตัวเอง");
				event.setLine(2, "???");
				event.setLine(3, "กล่องของใครเอ่ย?");
				return;
			}

			return;
		}
	}

	public int getfreeselect(String sname) {
		// clean exited player
		boolean foundx = false;
		for (int i = 0; i < ddatamax; i++) {

			foundx = false;

			for (Player pr : Bukkit.getOnlinePlayers()) {
				if (nn.playername[i].equalsIgnoreCase(pr.getName())) {
					foundx = true;
					break;
				}
			}

			if (foundx == false) {
				nn.playername[i] = "";
				nn.lastblock[i] = null;
			}

		}
		// clean

		for (int i = 0; i < ddatamax; i++) {
			if (nn.playername[i].equalsIgnoreCase(sname)) {
				return i;
			}
		}

		for (int i = 0; i < ddatamax; i++) {
			if (nn.playername[i].equalsIgnoreCase("")) {
				nn.playername[i] = sname;
				return i;
			}
		}

		return -1;
	}
} // dig