/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddmain;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import api_admin.dewddadmin;
import dewddflower.dewset;
import dewddtran.tr;
import net.minecraft.server.v1_7_R4.EntityPlayer;

public class DigEventListener2 implements Listener {

	dewset dew ;

	class chatx implements Runnable {
		public Boolean canc = false;

		private String message = "";

		private Player player = null;

		public chatx(String message, Player player) {
			this.message = message;
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {
			if (player.getWorld().getName().equalsIgnoreCase("skywar"))
				return;

			String[] m = message.split("\\s+");
			m = message.split("\\s+");
			if (message.equalsIgnoreCase("dewdd help") == true) {
				player.sendMessage(">dewdd main");
				canc = true;
			}

			if (m[0].equalsIgnoreCase("showworldlist")) {
				for (int lop = 0; lop < dew.dewworldlistmax; lop++) {
					player.sendMessage(lop + " = " + dew.dewworldlist[lop]);
				}
			}

			Block block = player.getLocation().getBlock();

			if (m[0].equalsIgnoreCase("digx") == true) {
				player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(6000, 100));
			}

			if (m[0].equalsIgnoreCase("showsignlist")) {
				if (m.length == 1) {
					// load protect id
					int xyz = dew.checkpermissionarea(block, true);
					if (xyz == -1) {
						player.sendMessage(tr.gettr("this_zone_don't_have_protect"));
						return;
					}

					player.sendMessage("xyz = " + dew.dewsignx1[dew.getworldid(player.getWorld().getName())][xyz] + ","
							+ dew.dewsigny1[dew.getworldid(player.getWorld().getName())][xyz] + ","
							+ dew.dewsignz1[dew.getworldid(player.getWorld().getName())][xyz] + " to "
							+ dew.dewsignx2[dew.getworldid(player.getWorld().getName())][xyz] + ","
							+ dew.dewsigny2[dew.getworldid(player.getWorld().getName())][xyz] + ","
							+ dew.dewsignz2[dew.getworldid(player.getWorld().getName())][xyz]);
					// show all informations
					for (int lop = 0; lop < dew.dewsignnamemax; lop++) {
						player.sendMessage(
								lop + " = " + dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][lop]);
					}

				}
				for (int lop = 0; lop < dew.dewworldlistmax; lop++) {
					player.sendMessage(lop + " = " + dew.dewworldlist[lop]);
				}
			}

			if (m[0].equalsIgnoreCase("gotohell") == true) {
				// search player
				if (dewddadmin.is2admin(player) == false) {
					player.sendMessage("ptdew&dewdd : " + tr.gettr("gotohell_only_admin"));
					return;
				}

				if (m.length != 2) {
					player.sendMessage(tr.gettr("gotohell_need_argument_target"));
					return;
				}

				String goth = m[1];

				for (Player gotb : Bukkit.getWorld("world").getPlayers())
					if (gotb.getName().toLowerCase().startsWith(goth) == true) {
						Location lo1 = gotb.getLocation();
						Location lo2 = lo1;
						lo2.setWorld(Bukkit.getWorld("world_nether"));
						dew.gotohell(gotb, lo1, lo2);
						dprint.r.printC("gotohell : " + gotb.getName());
						break;
					}
			}

			if (m[0].equalsIgnoreCase("dslb")) {
				dew.showdewsetlistblock();
				return;
			}

			if (m[0].equalsIgnoreCase("dslb2")) {
				dew.showdewsetlistblock(player);
				return;
			}

			if (m[0].equalsIgnoreCase("compact")) {
				player.sendMessage("compacting ");

				int nit = 0;
				int nit2 = 0;
				for (ItemStack it : player.getInventory().getContents()) {
					nit++;

					if (it == null) {
						continue;
					}

					if (it.getType().getMaxDurability() <= 0) {
						continue;
					}

					nit2 = 0;
					for (ItemStack it2 : player.getInventory().getContents()) {
						nit2++;

						if (it2 == null) {
							continue;
						}
						if (it2 == it) {
							continue;
						}

						if (it2.getTypeId() != it.getTypeId()) {
							continue;
						}

						if (it2.getType().getMaxDurability() <= 0) {
							continue;
						}

						if (nit2 <= nit) {
							continue;
						}

						it.setAmount(it.getAmount() + 1);
						it2.setTypeId(5);
						it2.setAmount(1);

					}
				}

				player.sendMessage("compacted");

			}

			if (message.equalsIgnoreCase("dewdd main") == true) {
				player.sendMessage(
						"ปลั๊กอิน main เป็นปลั๊กอินแรกของเรา   ตอนแรกสร้างขึ้นเพื่อเป็นระบบโพเทค ป้าย แต่มันทำงานช้า > จึงเปลี่ยนมาเป็น"
								+ "โพเทค วงกลม  แต่มันกำหนดเขตยาก จึงเปลี่ยนมาเป็น โพเทคลูกบาศก์");
				player.sendMessage("ปลั๊กอินนี้ มีรายละเอียดเยอะที่สุด  รายระเอียดมีเยอะลองเอง");
				player.sendMessage(">>dewdd flower");

				canc = true;
			}

			if (message.equalsIgnoreCase("loadmainfile") == true) {
				dprint.r.printAll("loading main file");
				dew.loadmainfile();
				dprint.r.printAll("loaded main file");
			}

			if (message.equalsIgnoreCase("dewdd flower") == true) {
				player.sendMessage(">dewdd flower");
				player.sendMessage(
						"dewdd flower ระบบ ดอกไม้   เป็นการ  กำหนดเขตด้วย Wooden hoe  คลิกซ้ายที่หนึ่ง ขวาอีกที่  (เหมือนขวานไม้ world edit)");
				player.sendMessage("จากนั้น ให้พิมพ์คำสั่ง ซึ่งมีหลายคำสั่งด้วยกัน");
				player.sendMessage(".dewbuy   เป็นการซื้อที่ดิน ซื้อโพเทคตามเขตที่กำหนด");
				player.sendMessage(".dewset   ถือบล็อคแล้วพิมพ์ จะเป็นการวางบล็อคแทนที่ทับทุกสิ่งในนั้น");
				player.sendMessage(".dewfill   ถือบล็อคแล้วพิมพ์ จะวางบล็อค เติม ในพื้นที่นั้น จนเต็ม");
				player.sendMessage(".dewsetwall   ถือบล็อคแล้วพิมพ์ เป็นการสร้างกำแพง โดยทับกำแพง");
				player.sendMessage(".dewfillwall  ถือบล็อคแล้วพิมพ์ เป็นการสร้างกำแพง โดยเติมบล็อคที่ขาดไป");

				player.sendMessage(".dewsetblock   ถือบล็อคแล้วพิมพ์ เป็นการสร้างห้องมีรู  โดยทับกำแพง");
				player.sendMessage(".dewfillblock  ถือบล็อคแล้วพิมพ์ เป็นการสร้างห้องมีรู โดยเติมบล็อคที่ขาดไป");
				player.sendMessage(".dewsetroom   ถือบล็อคแล้วพิมพ์ เป็นการสร้างห้องมี โดยทับกำแพง");
				player.sendMessage(".dewfillroom  ถือบล็อคแล้วพิมพ์ เป็นการสร้างห้อง โดยเติมบล็อคที่ขาดไป");

				player.sendMessage(
						".dewdelete   ถือบล็อคแล้วพิมพ์ เป็นการลบบล็อคที่ตรงกับที่เราถือ  (ใช้ได้ในเขตโพเทคที่ตัวเองมีสิทธิ)");
				player.sendMessage(".dewfullcircle   ถือบล็อคแล้วพิมพ์ จะเป็นการวาดวงกลม ในพื้นที่");
				player.sendMessage(".dewspread4   ถือบล็อคแล้วพิมพ์ จะเป็นการวางบล็อคแบบแพร่กระจาย สี่ทิศ");
				player.sendMessage(".dewspread9   ถือบล็อคแล้วพิมพ์ จะเป็นการวางบล็อคแบบแพร่กระจาย 9 ทิศ");
				player.sendMessage(".dewcopy   ไปยืนที่ต้องการแล้วพิมพ์ จะเป็นการคัดลอกมาวาง");
				player.sendMessage(".dewspreadcircle   ไว้แพร่กระจายบล็อคไปรอบทิศจากตัวเรา  ระวังโดนหนีบ");
				player.sendMessage(".dewspreadq   ระบบแพ่รกระจายบล็อคในชั้นที่เรายืนแบบใหม่ คาดว่าจะทำงานเร็วขึ้น");
				player.sendMessage(
						".dewa   ยืนบนบล็อคมรกต    บล็อคเพชร 1 บล็อคติดรอบ บล็อคมรกตเพื่อกำหนดทิศ   จากนั้นพิมพ์   จำนวนของในมือ"
								+ " เป็นจำนวนครั้งในการ ขยายสิ่งของ ไปในทิศที่ต้องการ ทิศตามบล็อคเพชร    ถ้าอยากยืดข่้นฟ้า  ไม่ต้องมีบล็อคเพชร");

				canc = true;
			}

			if (message.equalsIgnoreCase("pmain") == true) {

				player.sendMessage("overide = " + Boolean.toString(player.hasPermission(dew.pmainoveride)));
				player.sendMessage(
						"dewbuy replace = " + Boolean.toString(player.hasPermission(dew.pmaindewbuyreplace)));
				player.sendMessage(
						"dewbuy notcount = " + Boolean.toString(player.hasPermission(dew.pmaindewbuynotcount)));

				player.sendMessage(
						"dew modify member = " + Boolean.toString(player.hasPermission(dew.pmaindewbuymodifymember)));

				player.sendMessage(
						"dewbuy changehost = " + Boolean.toString(player.hasPermission(dew.pmaindewbuydelete)));
				player.sendMessage("dewbuy delete = " + Boolean.toString(player.hasPermission(dew.pmaindewbuydelete)));
				player.sendMessage(
						"dew modify member = " + Boolean.toString(player.hasPermission(dew.pmaindewbuymodifymember)));

				player.sendMessage(
						"dew dewset everywhere = " + Boolean.toString(player.hasPermission(dew.pmaindewseteverywhere)));

				return;

			}

			// ***********************

			dew.hideshowrun(player);

			int idc = dew.getfreeselect(player);
			if (dew.chatever[idc] == false) {
				player.sendMessage("ptdew&dewdd :" + tr.gettr("please_drop_item_before_item"));

				canc = true;
				return;
			}

			// cleardrop

			// dewdeleteblock

			if (message.equalsIgnoreCase("dewreloadworldfile") == true) {
				dew.loadworldfile();
				return;
			}

			// dewreloadsignfile
			if (message.equalsIgnoreCase("dewreloadsignfile") == true) {
				dew.loadsignfile();
				player.sendMessage("ptdew&dewdd : Reloaded Sign File");
				return;
			}

			// savesignfile
			if (message.equalsIgnoreCase("dewsavesignfile") == true) {
				dew.savesignfile(-1, dew.getworldid(player.getWorld().getName()));
				player.sendMessage("ptdew&dewdd : Saved Sign File");
				return;
			}

			if (message.equalsIgnoreCase("dewaxe") == true || message.equalsIgnoreCase("dew_axe") == true) {

				dew.dewaxe[idc] = !dew.dewaxe[idc];
				player.sendMessage("ptdew&dewdd : '" + player.getName() + "' dewaxe (super axe) = "
						+ Boolean.toString(dew.dewaxe[idc]));
				dprint.r.printC("ptdew&dewdd : '" + player.getName() + "' dewaxe (super axe) = "
						+ Boolean.toString(dew.dewaxe[idc]));
				canc = true;
			}

			dew.linkurl(player, message);

			/*
			 * if (player.getItemInHand().getTypeId() == 46) { canc = true;
			 * return; }
			 */

			if (message.equalsIgnoreCase("select[]") == true) {
				for (int lp1 = 0; lp1 < dew.selectmax; lp1++) {
					player.sendMessage(lp1 + "=" + dew.selectname[lp1]);
				}
				canc = true;
			}

			if (m[0].equalsIgnoreCase("gift") == true)
				if (m.length == 1) {
					dew.gift(player, player.getItemInHand().getTypeId(), player.getItemInHand().getData().getData());
				} else if (m.length == 2) { // gift 5:1 , gift cobblestone:?
					String[] m2 = m[1].split(":");

					int itemid = 0;
					byte dataid = 0;

					player.sendMessage("m2[0] = " + m2[0] + " search.. = " + dew.getmaterialrealname(m2[0]));

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) {
							player.sendMessage(tr.gettr("what_the_hell_item"));
						} else {
							itemid = Material.getMaterial(Integer.parseInt(m2[0])).getId();
							if (m2.length == 2) {
								dataid = Byte.parseByte(m2[1]);
							}

							player.sendMessage("itemid = " + itemid + ", dataid = " + dataid);
							dew.gift(player, itemid, dataid);
						}
					} else {
						for (Material en : Material.values())
							if (en.name().toLowerCase().indexOf(m2[0].toLowerCase()) > -1) {

								dprint.r.printC("found material real name = " + en.name());
								itemid = Material.getMaterial(dew.getmaterialrealname(m2[0])).getId();
								if (m2.length == 2) {
									dataid = Byte.parseByte(m2[1]);
								}

								player.sendMessage("itemid = " + itemid + ", dataid = " + dataid);
								dew.gift(player, itemid, dataid);
							}
					}

				}

			if (message.equalsIgnoreCase("checkhome") == true) {

				int co = 0;

				for (int wla = 0; wla < dew.dewworldlistmax + 1; wla++) {
					for (int ie = 0; ie < dew.dewsignmax[wla]; ie++) {
						dprint.r.printAll("" + ie);
						if (ie != dew.getworldid(player.getLocation().getWorld().getName())) {
							continue;
						}

						boolean stafffound = false;
						boolean memberfound = false;

						for (String whoin : dew.dewsignname[wla][ie]) {
							co++;

							// dprint.r.printAll(whoin);
							/*
							 * if (api_admin.dewddadmin.issubsubadminname(whoin)
							 * == true) { stafffound = true; dprint.r.printAll(
							 * "staff found +" + whoin);
							 * 
							 * }
							 */

							/*
							 * if (api_admin.dewddadmin.issubsubadminname(whoin)
							 * == false &&
							 * api_admin.dewddadmin.isadminname(whoin) == false
							 * && whoin.startsWith("<") == false &&
							 * whoin.equalsIgnoreCase(dew.flag_everyone) ==
							 * false && whoin.equalsIgnoreCase("null") == false)
							 * { memberfound = true; dprint.r.printAll(
							 * "member = " + whoin); }
							 */
						}

						// dprint.r.printAll(Boolean.toString(stafffound) + ", "
						// +
						// Boolean.toString(memberfound) );
						if (stafffound == true && memberfound == true) {
							player.sendMessage("/* id home = " + ie);
							player.sendMessage("this home staff live with member!!!");
							player.sendMessage("world = " + wla);

							for (String ab : dew.dewsignname[wla][ie]) {
								player.sendMessage(ab);
							}

							player.sendMessage("xyz1=" + dew.dewsignx1[wla][ie] + "," + dew.dewsigny1[wla][ie] + ","
									+ dew.dewsignz1[wla][ie]);
							player.sendMessage("xyz2=" + dew.dewsignx2[wla][ie] + "," + dew.dewsigny2[wla][ie] + ","
									+ dew.dewsignz2[wla][ie]);
							player.sendMessage("*/");
							return;
						}

					} // all sign in world
				}
				dprint.r.printAll("" + co);

				dprint.r.printAll("check home done");
				canc = true;
				return;

			} // check home

			// dewbuy
			if (message.equalsIgnoreCase("dewbuy") == true || message.equalsIgnoreCase("db") == true) {
				dew.dewbuy(player);
				canc = true;
				return;
			}

			// dewbuyzone
			if (message.equalsIgnoreCase("dewbuyzone") == true || message.equalsIgnoreCase("dbz") == true) {
				dew.dewbuyzone(player, player.getLocation().getBlock());
				canc = true;
				return;
			}

			// dewbuyremove
			if (message.equalsIgnoreCase("dewbuyremove") == true || message.equalsIgnoreCase("dbr") == true) {

				dew.dewbuyremove(player);

				canc = true;
				return;
			}

			if (message.equalsIgnoreCase("dewextend") == true || message.equalsIgnoreCase("de") == true) {
				dew.dewextend(player);
				canc = true;
				return;
			}

			if (message.equalsIgnoreCase("dewselectprotect") == true) {
				dew.dewselectprotect(player);
				canc = true;
				return;
			}

			if (m[0].equalsIgnoreCase("dewselectcube") == true)
				if (m.length == 1) {

					player.sendMessage(tr.gettr("item_on_hand_is_radius") + player.getItemInHand().getAmount());
					dew.dewselectcube(player, player.getItemInHand().getAmount());
					canc = true;
					return;

				} else if (m.length == 2) {
					if (dew.isNumeric(m[1]) == false) {
						player.sendMessage(tr.gettr("only_number_please"));
						return;
					}

					player.sendMessage(tr.gettr("argument_2_is_radius") + m[1]);
					dew.dewselectcube(player, Integer.parseInt(m[1]));
					canc = true;
					return;
				}

			// dewinvert

			// dewfillwall

			// dewfly อยากบินได้

			// hi
			if (message.equalsIgnoreCase("drop") == true) {
				if (api_admin.dewddadmin.is2moderator(player) == true) {
					player.sendMessage("ptdew&dewdd : " + tr.gettr("staff_can't_drop_item"));

					return;
				}

				for (int xx = 0; xx <= 39; xx++) {

					ItemStack itm = player.getInventory().getItem(xx);
					if (itm == null) {
						continue;
					}

					Location la = player.getLocation();
					la.setY(200);
					player.sendMessage(
							"ptdew&dewdd : xx >" + xx + " = " + player.getInventory().getItem(xx).getType().name());
					player.getWorld().dropItem(la, itm);
					itm.setTypeId(0);
					player.getInventory().setItem(xx, itm);

				}

				return;
			}

			// d4

			// d4

			String strword1 = message;

			// add player to home
			// dewallow2=ptdew
			// dewallow3=candyman
			// d40123

			// "dewadd="
			if (m[0].equalsIgnoreCase("dewadd") == true) {
				dew.dewps_add(message, player);
			}

			// "dewremove="

			// "dewadd="
			if (m[0].equalsIgnoreCase("dewremove") == true) {
				dew.dewps_remove(message, player);
			}

			if (strword1.equalsIgnoreCase("dewlist") == true) {
				dew.showwhohome(player.getLocation().getBlock(), player);
			}

			// allow 1
			if (strword1.length() > 10) {
				String str11 = strword1.substring(0, 10);

				if (str11.equalsIgnoreCase("dewallow1=") == true) {
					if (player.hasPermission(dew.pmaindewbuychangehost) == false) {
						player.sendMessage("ptdew&dewdd : " + tr.gettr("need_permission_change_host"));
						return;
					}

					str11 = strword1.substring(10, strword1.length());
					int xyz = dew.checkpermissionarea(player.getLocation().getBlock(), true);

					dprint.r.printC("ptdew&dewdd :dewallow1 name = '" + str11 + "'");
					System.out.println("ptdew&dewdd :dewallow1 " + tr.gettr("home_id") + " id = '" + xyz + "'");

					if (xyz == -1) {
						player.sendMessage("ptdew&dewdd : " + tr.gettr("this_zone_don't_have_protect"));
						return;
					}

					// change name 2
					dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][0] = str11;
					player.sendMessage("ptdew&dewdd : " + tr.gettr("this_zone_protected_by") + " ...");
					for (int ici = 0; ici < dew.dewsignnamemax; ici++) {
						player.sendMessage(
								ici + " = " + dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][ici]);
					}
					dew.savesignfile(-1, dew.getworldid(player.getWorld().getName()));

					return;
				}

				str11 = strword1.substring(0, 8);
				// dewallow2
				if (str11.equalsIgnoreCase("dewallow") == true) {
					/*
					 * if (api_admin.dewddadmin.is2moderator(player.getName())
					 * == true) { player.sendMessage(
					 * "ptdew&dewdd : staff can't allow another player to your home zone"
					 * ); player.sendMessage(
					 * "ptdew&dewdd : staff ห้ามเพิ่มคนเข้าบ้าน ห้ามใช้คำสั่งนี้"
					 * );
					 * 
					 * return; }
					 */

					int homeslot = Integer.parseInt(strword1.substring(8, 10));
					if (homeslot < 2 || homeslot > dew.dewsignnamemax) {
						player.sendMessage("homeslot = 1 < ? < " + dew.dewsignnamemax);
						return;
					}
					player.sendMessage("homeslot = " + homeslot);

					str11 = strword1.substring(11, strword1.length());
					int xyz = dew.checkpermissionarea(player.getLocation().getBlock(), true);

					// id of home

					dprint.r.printC("ptdew&dewdd : dewallow " + homeslot + " name = '" + str11 + "'");
					System.out.println("ptdew&dewdd : dewallow " + homeslot + " seacrh home id = '" + xyz + "'");

					if (xyz == -1) {
						player.sendMessage("ptdew&dewdd : " + tr.gettr("this_zone_don't_have_protect"));

						return;
					}

					// check host
					if (dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][0]
							.equalsIgnoreCase(player.getName()) == false
							&& player.hasPermission(dew.pmaindewbuymodifymember) == false) {
						player.sendMessage("ptdew&dewdd : " + tr.gettr("this_zone_host_is")
								+ dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][0]);
						player.sendMessage("ptdew&dewdd : พื้นที่นี้, เจ้าของคือ "
								+ dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][0]);

						return;
					}

					// change name 2
					// add staff but not admin or staff
					/*
					 * if (api_admin.dewddadmin.issubsubadminname(str11) == true
					 * && api_admin.dewddadmin.is2admin(player) == false &&
					 * api_admin.dewddadmin.is2moderator(player) == false) {
					 * 
					 * player.sendMessage("ptdew&dewdd : " +
					 * tr.gettr("Member_can't_add_staff_to_your_zone") +
					 * dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]); player.sendMessage(
					 * "ptdew&dewdd : ผู้เล่นธรรมดา ห้ามเพิ่มสตาฟเข้าบ้านของคุณนะ > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * 
					 * return; }
					 */
					// add member but is staff
					/*
					 * if (api_admin.dewddadmin.issubsubadminname(str11) ==
					 * false && api_admin.dewddadmin.isadminname(str11) == false
					 * && api_admin.dewddadmin.is2moderator(player) == true) {
					 * if (str11.equalsIgnoreCase(dew.flag_sell) == true) {
					 * dprint.r.printAll("ptdew&dewdd : staff " +
					 * player.getName() + " try to add <sell> to protect");
					 * return; } player.sendMessage("ptdew&dewdd : " +
					 * tr.gettr("staff_can't_add_member_to_your_zone") +
					 * dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]); player.sendMessage(
					 * "ptdew&dewdd : สตาฟห้ามเพิ่มคนธรมมดาเข้าบ้านของคุณนะ > "
					 * + dew.dewsignname[dew.getworldid(player
					 * .getWorld().getName())][xyz][0]);
					 * 
					 * return; }
					 */

					dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][homeslot - 1] = str11;

					player.sendMessage("ptdew&dewdd : " + tr.gettr("this_zone_protected_by"));

					for (int ici = 0; ici < dew.dewsignnamemax; ici++) {
						player.sendMessage(
								ici + " = " + dew.dewsignname[dew.getworldid(player.getWorld().getName())][xyz][ici]);
					}

					dew.savesignfile(-1, dew.getworldid(player.getWorld().getName()));
					return;
				}

			}

			if (message.equalsIgnoreCase("dewbuydelete") == true) {
				dew.dewbuydelete(player);
				return;
			}
			// ***************************************
			// ***************************************

			if (api_admin.dewddadmin.is2admin(player.getPlayer()) == true
					|| api_admin.dewddadmin.is2moderator(player.getPlayer()) == true) {
				// clearmon
				if (message.equalsIgnoreCase("clear") == true) {
					player.getInventory().clear();
					player.sendMessage("ptdew&dewdd : " + tr.gettr("cleared_your_inventory"));
					return;
				}
				// dewgoxxx

				if (message.length() == 9)
					// player.sendMessage("'" + message.substring(0, 6) + "'");
					if (message.substring(0, 6).equalsIgnoreCase("dewgo ") == true) {
						int gotox = Integer.parseInt(message.substring(6, 9));

						if (dew.getworldid(player.getWorld().getName()) == -1) {
							player.sendMessage("ptdew&dewdd : " + tr.gettr("this_world_don't_have_protect"));

							return;
						}

						player.sendMessage("dewgo '" + gotox + "'" + "/"
								+ (dew.dewsignmax[dew.getworldid(player.getWorld().getName())] - 1));

						if (gotox < -1 || gotox > dew.dewsignmax[dew.getworldid(player.getWorld().getName())] - 1) {
							player.sendMessage("ptdew&dewdd : dewgo   -1 < ? < "
									+ (dew.dewsignmax[dew.getworldid(player.getWorld().getName())] - 1));
							return;
						}

						Location lox = player.getLocation();
						player.sendMessage("goto : " + dew.dewsignx1[dew.getworldid(player.getWorld().getName())][gotox]
								+ ",?," + dew.dewsignz1[dew.getworldid(player.getWorld().getName())][gotox] + " and "
								+ dew.dewsignx2[dew.getworldid(player.getWorld().getName())][gotox] + ",?,"
								+ dew.dewsignz2[dew.getworldid(player.getWorld().getName())][gotox]);

						if (dew.dewsigny1[dew.getworldid(player.getWorld().getName())][gotox] >= dew.dewsigny2[dew
								.getworldid(player.getWorld().getName())][gotox]) {
							lox.setX(dew.dewsignx1[dew.getworldid(player.getWorld().getName())][gotox]);
							lox.setY(dew.dewsigny1[dew.getworldid(player.getWorld().getName())][gotox]);
							lox.setZ(dew.dewsignz1[dew.getworldid(player.getWorld().getName())][gotox]);
						} else {
							lox.setX(dew.dewsignx2[dew.getworldid(player.getWorld().getName())][gotox]);
							lox.setY(dew.dewsigny2[dew.getworldid(player.getWorld().getName())][gotox]);
							lox.setZ(dew.dewsignz2[dew.getworldid(player.getWorld().getName())][gotox]);
						}

						player.getWorld().loadChunk((int) lox.getX(), (int) lox.getZ());
						player.teleport(lox);
						player.sendMessage("ptdew&dewdd : " + tr.gettr("teleported_your_to_zone_id") + gotox);

						return;
					}

				if (message.equalsIgnoreCase("clearmon") == true) {

					for (Entity ent : player.getWorld().getEntities()) {

						if (ent.getType() == org.bukkit.entity.EntityType.CREEPER) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SKELETON) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ZOMBIE) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SPIDER) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.CAVE_SPIDER) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.BLAZE) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ENDERMAN) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.BAT) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SLIME) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.GHAST) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ENDERMAN) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.GIANT) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.PIG_ZOMBIE) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.WITCH) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.MAGMA_CUBE) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.WITHER) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ENDER_DRAGON) {
							ent.remove();
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SILVERFISH) {
							ent.remove();
						}

					}
					player.sendMessage("DewDD:ClearMon done");
					canc = true;
					return;

				}

				if (message.equalsIgnoreCase("pvp=false") == true) {
					player.getWorld().setPVP(false);
					player.sendMessage("disable-pvp");
				}
				if (message.equalsIgnoreCase("pvp=true") == true) {
					player.getWorld().setPVP(true);
					player.sendMessage("enable-pvp");
				}

				if (message.equalsIgnoreCase("clearpig") == true) {

					for (Entity ent : player.getWorld().getEntities())
						if (ent.getType() == org.bukkit.entity.EntityType.PIG) {
							ent.remove();
						}
					player.sendMessage("ptdew&dewdd :Clearpig");
					canc = true;
					return;

				}

				if (message.equalsIgnoreCase("clearcow") == true) {

					for (Entity ent : player.getWorld().getEntities())
						if (ent.getType() == org.bukkit.entity.EntityType.COW) {
							ent.remove();
						}
					player.sendMessage("ptdew&dewdd :Clearcow");
					canc = true;
					return;

				}
				if (message.equalsIgnoreCase("clearchicken") == true) {

					for (Entity ent : player.getWorld().getEntities())
						if (ent.getType() == org.bukkit.entity.EntityType.CHICKEN) {
							ent.remove();
						}
					player.sendMessage("ptdew&dewdd :Clearchicken");
					canc = true;
					return;

				}

				if (message.equalsIgnoreCase("clearsheep") == true) {

					for (Entity ent : player.getWorld().getEntities())
						if (ent.getType() == org.bukkit.entity.EntityType.SHEEP) {
							ent.remove();
						}
					player.sendMessage("ptdew&dewdd :ClearSHEEP");
					canc = true;
					return;

				}
				if (message.equalsIgnoreCase("clearvillager") == true) {

					for (Entity ent : player.getWorld().getEntities())
						if (ent.getType() == org.bukkit.entity.EntityType.VILLAGER) {
							ent.remove();
						}
					player.sendMessage("ptdew&dewdd :ClearVillager");
					canc = true;
					return;

				}

				if (message.equalsIgnoreCase("clearminecart") == true) {

					for (Entity ent : player.getWorld().getEntities())
						if (ent.getType() == org.bukkit.entity.EntityType.MINECART) {
							ent.remove();
						}
					player.sendMessage("ptdew&dewdd :Clearminecart");
					canc = true;
					return;

				}
			}

			// pt dew
			if (api_admin.dewddadmin.is2admin(player.getPlayer()) == true) {

				if (message.equalsIgnoreCase("moninvi") == true) {
					dew.moninvi = !dew.moninvi;
					dprint.r.printC("ptdew&dewdd : moninvi = " + Boolean.toString(dew.moninvi));
					dprint.r.printA("ptdew&dewdd : moninvi = " + Boolean.toString(dew.moninvi));
					dprint.r.printA(
							"ptdew&dewdd : " + tr.gettr("Monster_Invisible_mode_is") + Boolean.toString(dew.moninvi));
					return;
				}

				if (message.equalsIgnoreCase("monjump") == true) {
					dew.monjump = !dew.monjump;
					dprint.r.printC("ptdew&dewdd : monjump = " + Boolean.toString(dew.monjump));
					dprint.r.printA("ptdew&dewdd : monjump = " + Boolean.toString(dew.monjump));
					dprint.r.printA(
							"ptdew&dewdd : " + tr.gettr("Monster_jump_mode_is") + Boolean.toString(dew.monjump));
					return;
				}

				if (message.equalsIgnoreCase("monfast") == true) {
					dew.monfast = !dew.monfast;
					dprint.r.printC("ptdew&dewdd : monfast = " + Boolean.toString(dew.monfast));
					dprint.r.printA("ptdew&dewdd : monfast = " + Boolean.toString(dew.monfast));
					dprint.r.printA(
							"ptdew&dewdd : " + tr.gettr("Monster_fast_mode_is") + Boolean.toString(dew.monfast));

					return;
				}

				// dewpicturemap

				if (m[0].equalsIgnoreCase("setamo")) {
					player.getItemInHand().setAmount(Integer.parseInt(m[1]));
					player.sendMessage("set " + Integer.parseInt(m[1]) + " done");
					return;
				}

				if (message.equalsIgnoreCase("ปิดกลางคืน") == true
						|| message.equalsIgnoreCase("disable-night") == true) {
					dew.allownight = false;
					player.sendMessage("ptdew&dewdd : " + tr.gettr("disabled_time_night"));
					return;
				}
				if (message.equalsIgnoreCase("เปิดกลางคืน") == true
						|| message.equalsIgnoreCase("enable-night") == true) {
					dew.allownight = true;
					player.sendMessage("ptdew&dewdd : " + tr.gettr("enabled_time_night"));
					return;
				}

				// hide
				if (message.equalsIgnoreCase("hide") == true && api_admin.dewddadmin.is2admin(player) == true) {

					int gga = dew.getfreeselect(player);
					dew.hidemode[gga] = true;

					for (Player pp : Bukkit.getOnlinePlayers())
						if (api_admin.dewddadmin.is2admin(pp) == true) {
							pp.sendMessage("ptdew&dewdd : " + player.getName() + tr.gettr("admin_invisible_mode_on"));
						}
					dew.hideshowrun(player);

					canc = true;
					return;
				}
				// show
				if (message.equalsIgnoreCase("show") == true && api_admin.dewddadmin.is2admin(player) == true) {

					int gga = dew.getfreeselect(player);
					dew.hidemode[gga] = false;
					for (Player pp : Bukkit.getOnlinePlayers())
						if (api_admin.dewddadmin.is2admin(pp) == true) {
							pp.sendMessage("ptdew&dewdd : " + player.getName() + tr.gettr("admin_invisible_mode_off"));
						}
					dew.hideshowrun(player);

					canc = true;
					return;
				}

				// getalldrop

				// flyspeed

			}

			// *****************************************

		} // sync
	}

	class chatz extends Thread {
		String message = "";
		Player player = null;

		@Override
		public void run() {

			if (player.getWorld().getName().equalsIgnoreCase("skywar"))
				return;
			String[] m = message.split("\\s+");

			String h[] = new String[50];
			h[0] = "dewsetwall";
			h[1] = "dewfillwall";
			h[2] = "dsw";
			h[3] = "dfw";

			h[4] = "dewsetblock";
			h[5] = "dsb";
			h[6] = "dewfillblock";
			h[7] = "dfb";

			h[8] = "dewspreadq";
			h[9] = "dsq";

			h[10] = "dewdown";
			h[11] = "dn";
			h[12] = "down";

			h[13] = "dewsetroom";
			h[14] = "dewfillroom";
			h[15] = "dsr";
			h[16] = "dfr";

			h[17] = "dewspreadcircle";
			h[18] = "dsc";

			h[19] = "dewwallcircle";
			h[20] = "dwc";

			h[21] = "dewbreak";
			h[22] = "dewsetlight";
			h[23] = "dsl";

			h[24] = "dewdelete";
			h[25] = "dd";

			h[26] = "dewfill";
			h[27] = "df";

			h[28] = "dewsetcircle";
			h[29] = "dewfillcircle";
			h[31] = "dewfullcircle";
			h[30] = "dfc";

			for (String element : h)
				if (m[0].equalsIgnoreCase(element) == true) {
					if (m.length == 1) {
						dew.runter(element, player, player.getItemInHand().getTypeId(),
								player.getItemInHand().getData().getData());
					} else if (m.length == 2) {
						String[] m2 = m[1].split(":");

						int itemid = 0;
						byte dataid = 0;

						player.sendMessage(
								"m2[0] = " + m2[0] + tr.gettr("search") + " = " + dew.getmaterialrealname(m2[0]));

						if (dew.isNumeric(m2[0]) == true) {
							if (Material.getMaterial(Integer.parseInt(m2[0])) == null) {
								player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
							} else {
								itemid = Material.getMaterial(Integer.parseInt(m2[0])).getId();
							}
						} else if (Material.getMaterial(dew.getmaterialrealname(m2[0])) == null) {
							player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
						} else {
							itemid = Material.getMaterial(dew.getmaterialrealname(m2[0])).getId();
						}

						if (m2.length == 2) {
							dataid = Byte.parseByte(m2[1]);
						}

						player.sendMessage("itemid = " + itemid + ", dataid = " + dataid);
						dew.runter(element, player, itemid, dataid);
					}

					return;
				}

			// dewset
			if (m[0].equalsIgnoreCase("dewset") == true || m[0].equalsIgnoreCase("ds") == true) {
				int a1 = -29;
				byte a2 = -29;
				int a3 = -29;
				byte a4 = 0;

				if (m.length == 1) {
					dew.dewset(player, -29, (byte) -29, player.getItemInHand().getTypeId(),
							player.getItemInHand().getData().getData(), false);
				} else if (m.length == 2) { // dewset 005:?
					String[] m2 = m[1].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) {
							player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
						} else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0])).getId();
						}
					} else if (Material.getMaterial(dew.getmaterialrealname(m2[0])) == null) {
						player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
					} else {
						a3 = Material.getMaterial(dew.getmaterialrealname(m2[0])).getId();
					}

					// data if 2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a3 + ":" + a4 + " and " + a1 + ":" + a2);
					dew.dewset(player, a1, a2, a3, a4, false);
				} else if (m.length == 3) { // dewset 005:? 003:?
					String[] m2 = m[1].split(":");

					// a1

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) {
							player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
						} else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0])).getId();
						}
					} else if (Material.getMaterial(dew.getmaterialrealname(m2[0])) == null) {
						player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
					} else {
						a3 = Material.getMaterial(dew.getmaterialrealname(m2[0])).getId();
					}

					// a2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					// a3
					m2 = m[2].split(":");
					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) {
							player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
						} else {
							a1 = Material.getMaterial(Integer.parseInt(m2[0])).getId();
						}
					} else if (Material.getMaterial(dew.getmaterialrealname(m2[0])) == null) {
						player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
					} else {
						a1 = Material.getMaterial(dew.getmaterialrealname(m2[0])).getId();
					}

					// a2
					if (m2.length == 2) {
						a2 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a1 + ":" + a2 + " and " + a3 + ":" + a4);
					dew.dewset(player, a1, a2, a3, a4, false);
				}

				return;
			} // dewset

			// dewset 444 00 555 00
			if (m[0].equalsIgnoreCase("dewxet") == true || m[0].equalsIgnoreCase("dx") == true) {
				int a1 = -29;
				byte a2 = -29;
				int a3 = -29;
				byte a4 = 0;

				if (m.length == 1) {
					dew.dewset(player, -29, (byte) -29, player.getItemInHand().getTypeId(),
							player.getItemInHand().getData().getData(), true);
				} else if (m.length == 2) { // dewset 005:?
					String[] m2 = m[1].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) {
							player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
						} else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0])).getId();
						}
					} else if (Material.getMaterial(dew.getmaterialrealname(m2[0])) == null) {
						player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
					} else {
						a3 = Material.getMaterial(dew.getmaterialrealname(m2[0])).getId();
					}

					// data if 2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a3 + ":" + a4 + " and " + a1 + ":" + a2);
					dew.dewset(player, a1, a2, a3, a4, true);
				} else if (m.length == 3) { // dewset 005:? 003:?
					String[] m2 = m[1].split(":");

					// a1
					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) {
							player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
						} else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0])).getId();
						}
					} else if (Material.getMaterial(dew.getmaterialrealname(m2[0])) == null) {
						player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
					} else {
						a3 = Material.getMaterial(dew.getmaterialrealname(m2[0])).getId();
					}

					// a2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					// a3
					m2 = m[2].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) {
							player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
						} else {
							a1 = Material.getMaterial(Integer.parseInt(m2[0])).getId();
						}
					} else if (Material.getMaterial(dew.getmaterialrealname(m2[0])) == null) {
						player.sendMessage(tr.gettr("error_argument_1_what_the_hell_item"));
					} else {
						a1 = Material.getMaterial(dew.getmaterialrealname(m2[0])).getId();
					}

					// a2
					if (m2.length == 2) {
						a2 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a1 + ":" + a2 + " and " + a3 + ":" + a4);
					dew.dewset(player, a1, a2, a3, a4, true);
				}

				return;
			} // dewset

			// dewdig
			if (message.equalsIgnoreCase("dewdddig") == true || message.equalsIgnoreCase("ddd") == true) {

				dew.dewdig(player);

				return;
			}

			// dewcopy
			if (message.equalsIgnoreCase("dewcopy") == true || message.equalsIgnoreCase("dc") == true) {
				dew.dewcopy(player);

				return;
			}

			// dewa
			if (m[0].equalsIgnoreCase("dewa") == true || m[0].equalsIgnoreCase("da") == true) {

				int amo = 0;
				if (m.length == 1) {
					dew.dewa(player, 0);
				} else if (m.length == 2) {
					if (dew.isNumeric(m[1]) == false) {
						player.sendMessage("/dewa amount(it's not number T_T)");
						return;
					}

					amo = Integer.parseInt(m[1]);
					dew.dewa(player, amo);
				}

				return;
			}

			if (message.equalsIgnoreCase("dewsetprivate") == true || message.equalsIgnoreCase("dsp") == true) {
				dew.dewsetprivate(player);

				return;
			}

		}
	}

	class delay extends Thread {

		@Override
		public void run() {

			try {
				// dew = null;

				int i = 0;
				while (ac == null) {

					i++;
					Thread.sleep(1000);
					System.out.println("dew main waiting for create dewset sleeping ac +" + i);

				}
				
			    
			    System.out.println("***** main dew = " + dew==null?"null":"not null" );
			    System.out.println("***** main dewddflower.Main.ds = " + dewddflower.Main.ds==null?"null":"not null");
			 

				
				  while (dewddflower.Main.ds == null) {
				  
				  i++
				  ; Thread.sleep(1000); System.out .println(
				  "dew main waiting for create dewset sleeping dew +" + i);
				  
				 
				  		//dew = dewddflower.Main.ds;
				  
				  }
				    dew = dewddflower.Main.ds;
				    
				    
				    System.out.println("***** main dew = " + dew==null?"null":"not null" );
				    System.out.println("***** main dewddflower.Main.ds = " + dewddflower.Main.ds==null?"null":"not null");
				 
				 /* while (dew.ac == null) {
				 * 
				 * i++; Thread.sleep(1000); System.out .println(
				 * "dew main waiting for create dewset sleeping dew ac +" + i);
				 * 
				 * dew.ac = ac;
				 * 
				 * } dew.loadmainfile();
				 */

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				delay eee = new delay();
				eee.start();

				e.printStackTrace();
			}

		}
	}

	class server_c implements Runnable {
		private String message;

		public server_c(String message) {
			this.message = message;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this);
		}

		@Override
		public void run() {

			String m[] = message.split("\\s+");

			if (m[0].equalsIgnoreCase("dewreloadworldfile") == true) {
				dew.loadworldfile();
				return;
			}

			// dewreloadsignfile
			if (m[0].equalsIgnoreCase("dewreloadsignfile") == true) {
				dew.loadsignfile();
				dprint.r.printAll("ptdew&dewdd : Reloaded Sign File");
				return;
			}

			if (m[0].equalsIgnoreCase("drops") == true) {
				int amo = 0;
				for (World ww : Bukkit.getWorlds()) {
					for (Entity amoq : ww.getEntities()) {
						amoq.getType();
						if (amoq.getType() == EntityType.DROPPED_ITEM) {
							amo = amo + 1;
						}

					}
					dprint.r.printAll("ptdew&dewdd : " + ww.getName() + ">" + amo);
				}
				dprint.r.printAll("ptdew&dewdd : " + tr.gettr("amount_of_dropped_item_in_this_server") + amo);

				return;
			}

			if (m[0].equalsIgnoreCase("gotohell") == true) {
				// search player
				String goth = m[1];

				for (Player gotb : Bukkit.getWorld("world").getPlayers())
					if (gotb.getName().toLowerCase().startsWith(goth) == true) {
						Location lo1 = gotb.getLocation();
						Location lo2 = lo1;
						lo2.setWorld(Bukkit.getWorld("world_nether"));
						dew.gotohell(gotb, lo1, lo2);
						dprint.r.printC("gotohell : " + gotb.getName());
						break;
					}
			}

			if (m[0].equalsIgnoreCase("moninvi") == true) {
				dew.moninvi = !dew.moninvi;
				dprint.r.printC("ptdew&dewdd : moninvi = " + Boolean.toString(dew.moninvi));
				dprint.r.printA("ptdew&dewdd : moninvi = " + Boolean.toString(dew.moninvi));
				return;
			}
			if (m[0].equalsIgnoreCase("monfast") == true) {
				dew.monfast = !dew.monfast;
				dprint.r.printC("ptdew&dewdd : monfast = " + Boolean.toString(dew.monfast));
				dprint.r.printA("ptdew&dewdd : monfast = " + Boolean.toString(dew.monfast));

				return;
			}

			if (m[0].equalsIgnoreCase("monjump") == true) {
				dew.monjump = !dew.monjump;
				dprint.r.printC("ptdew&dewdd : monjump = " + Boolean.toString(dew.monjump));
				dprint.r.printA("ptdew&dewdd : monjump = " + Boolean.toString(dew.monjump));

				return;
			}

		}
	}

	public JavaPlugin ac = null;

	public String pseecommand = "dewdd.main.seecommand";

	// Chat Event.class
	// BlockBreakEvent

	public DigEventListener2() {
		// dew = new dewddflower.dewset();

		
		  delay eee = new delay(); eee.start();
		 
	}

	// synchronized
	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		event.getMessage();
		if (event.getMessage().equalsIgnoreCase("ac")) {
			dprint.r.printAll(ac == null ? "ac yes" : "ac no");
			dprint.r.printAll(dew == null ? "dew yes" : "dew no");
			dprint.r.printAll(dew.ac == null ? "dew ac yes" : "dew ac no");

		}

		chatz ar = new chatz();
		ar.player = event.getPlayer();
		ar.message = event.getMessage();
		ar.start();

		chatx ab = new chatx(event.getMessage(), event.getPlayer());

		event.setCancelled(ab.canc);

	}

	@EventHandler
	public void eventja(BlockBreakEvent event) {
		// player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(200,
		// dew.randomG.nextInt(100)),false);

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Block block = event.getBlock();
		Player player = event.getPlayer();
		// check host block

		if (player.getItemInHand().getTypeId() == 335) {
			player.sendMessage("" + block.getTypeId() + ":" + block.getData());
			event.setCancelled(true);
		}

		if (block.getTypeId() == 52 && api_admin.dewddadmin.is2admin(player) == false) {
			player.sendMessage("ptdew&dewdd : " + tr.gettr("only_admin_can_destroy_52"));
			event.setCancelled(true);
		}

		// player.s endMessage("ptdew&dewdd : " + block.getTypeId() + "," +
		// block.getData());

		int digX = block.getX();
		int digY = block.getY();
		int digZ = block.getZ();

		boolean goodc1 = false;
		goodc1 = dew.checkpermissionarea(block, player, "delete");

		// call check
		if (goodc1 == true) {
			event.setCancelled(true);
		} else {

			/*
			 * if (player.getItemInHand().getTypeId() == 323) { if
			 * (block.getTypeId() == 63 || block.getTypeId() == 68 ||
			 * block.getTypeId() == 323) { dew.dewsignthai(block, player);
			 * event.setCancelled(true); return; } }
			 */

			if (player.getItemInHand() != null) {
				if (player.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) > 4) {
					player.getItemInHand().removeEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
					player.getItemInHand().addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 4);
				}
				if (player.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) > 4) {
					player.getItemInHand().removeEnchantment(Enchantment.LOOT_BONUS_MOBS);
					player.getItemInHand().addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 4);
				}
				if (player.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY) > 6) {
					player.getItemInHand().removeEnchantment(Enchantment.DURABILITY);
				}

			}

			if (api_admin.dewddadmin.is2moderator(player) == true) {
				block.setTypeId(0);
				event.setCancelled(true);
				return;
			}

			if (api_admin.dewddadmin.is2admin(player) == true)
				if (player.getItemInHand().getTypeId() == 68) {
					event.setCancelled(true);
					dprint.r.printAll("starting protect rail..");
					dew.protectrail(block, player);
					dprint.r.printAll("starting protect rail..");

				}

			dew.cutwoodsub(event.getBlock(), event.getPlayer(), true);

			switch (block.getTypeId()) {
			case 14:
			case 15:
			case 16:
			case 13:
			case 21:
			case 73:
			case 56:
			case 17:
			case 5:

				player.addPotionEffect(
						PotionEffectType.FAST_DIGGING.createEffect(dew.randomG.nextInt(500), dew.randomG.nextInt(100)),
						false);
				break;
			}

			player = event.getPlayer();

			// remove protect

			Block signblock = block.getTypeId() == 68 || block.getTypeId() == 63 ? block : null;

			if (signblock == null) {
				for (int sx = -1; sx <= 1; sx++) {
					for (int sy = -1; sy <= 1; sy++) {
						for (int sz = -1; sz <= 1; sz++)
							if (block.getRelative(sx, sy, sz).getTypeId() == 63
									|| block.getRelative(sx, sy, sz).getTypeId() == 68) {
								signblock = block.getRelative(sx, sy, sz);
							}
					}
				}
			}

			if (signblock != null)
				if (signblock.getTypeId() == 63 || signblock.getTypeId() == 68) {
					Sign sign = (Sign) signblock.getState();
					if (sign.getLine(0).equalsIgnoreCase("[dewbuy]") == true
							|| sign.getLine(0).equalsIgnoreCase("[dewhome]") == true) {
						// check protect
						int idid = dew.checkpermissionarea(signblock, true);
						if (idid > -1) {
							// some one hoome
							boolean ishost = dew.havethisnameinthishome(
									dew.getworldid(player.getLocation().getWorld().getName()), idid, player.getName());
							if (ishost == false) {
								player.sendMessage(tr.gettr("can't_delete_sign_block_cuz_not_your_zone")
										+ dew.dewsignname[dew
												.getworldid(player.getLocation().getWorld().getName())][idid][0]
										+ "' home");
								event.setCancelled(true);
								return;
							} else {
								player.sendMessage(tr.gettr("removing_your_zone"));
								dew.dewbuydelete(player);

							}

						}

					}

				}

			int ex = 0;
			int ey = 0;
			int ez = 0;
			int d4 = 1;

			World world = block.getWorld();
			boolean allowa = player.getInventory().getHelmet() == null;
			if (allowa == false) {
				allowa = player.getInventory().getHelmet().getTypeId() == 49;

				for (ex = digX - d4; ex <= digX + d4; ex++) {
					for (ey = digY - d4; ey <= digY + d4; ey++) {
						for (ez = digZ - d4; ez <= digZ + d4; ez++) {
							block = world.getBlockAt(ex, ey, ez);
							if (block.getTypeId() == 10 || block.getTypeId() == 11)
								if (allowa == true) {
									player.sendMessage(
											"ptdew&dewdd : " + tr.gettr("obsidian_head_can't_break_near_lava"));
									event.setCancelled(true);
									break;
								}
						}
					}
				}
			}

			// un fall block
			world = block.getWorld();
			allowa = player.getInventory().getBoots() == null;
			if (allowa == false) {
				allowa = player.getInventory().getBoots().getTypeId() == 309;
				ex = digX;
				ey = 0;
				ez = digZ;
				d4 = 1;
				int counthigh = 0;

				for (ey = digY - 1; ey >= digY - 24; ey--) {

					block = world.getBlockAt(ex, ey, ez);
					if (block.getTypeId() == 0) {
						if (allowa == true) {
							counthigh++;
						}
					} else {
						break;
					}
				}

				if (counthigh >= 10) {
					event.setCancelled(true);
				}

			}

			// call fixtool
			
			if (player.getItemInHand() != null) 
				if (player.getItemInHand().getItemMeta() != null)
			if (player.getItemInHand().getItemMeta().getDisplayName() != null ) {
			String itName[] = player.getItemInHand().getItemMeta().getDisplayName().split(" ");
			
			//player.sendMessage("itName " + itName[0] + " " + itName[1]);
			
			if (itName.length == 3) {
				if (itName[0].equalsIgnoreCase("55")) {
					
					dew.amountRecursiveCount = 0;
					
					int ig1 = block.getTypeId();
					byte ig2 = block.getData();
					
					
					//player.sendMessage("ptdew&dewdd : 55 runing..." + ig1 + ":" + ig2);
					dew.item55delete(block, player, ig1, ig2, Integer.parseInt(itName[1]), Integer.parseInt(itName[2]),20);
					//player.sendMessage("ptdew&dewdd : 55 done...");
				}
				
			}
			
			}
			
			
			dew.randomplaynote(player.getLocation());
			
		}
		

	}

	// BlockBurnEvent
	@EventHandler
	public void eventja(BlockBurnEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getBlock().getWorld().getName()))
			return;

		Block ac = event.getBlock();
		if (dew.checkpermissionarea(event.getBlock()) == true || event.getBlock().getTypeId() == 35) {

			int d4 = 4;

			for (int ex = ac.getX() - d4; ex <= ac.getX() + d4; ex++) {
				for (int ey = ac.getY() - d4; ey <= ac.getY() + d4; ey++) {
					for (int ez = ac.getZ() - d4; ez <= ac.getZ() + d4; ez++) {
						Block ag = ac.getWorld().getBlockAt(ex, ey, ez);
						// dprint.r.printC (ag.getTypeId());
						if (ag.getTypeId() == 51) {
							ag.setTypeId(0);
						}
					}
				}
			}

			// event.getEntity().setFireTicks(1);
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(BlockDamageEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;
		// 4
		try {

			Block block = event.getBlock();
			Player player = event.getPlayer();

			// check host block

			boolean goodc1 = false;
			goodc1 = dew.checkpermissionarea(block, player, "damage");
			if (goodc1 == true) { // don't have permission

				event.setCancelled(true);
				return;
			} else { // have permission

				if (player.getItemInHand().getTypeId() == 288 && block.getTypeId() == 7) {
					block.breakNaturally();
				}

				// player.sendMessage("iteminhand= " +
				// player.getItemInHand().getTypeId());

				
				if (player.getItemInHand() != null) 
					if (player.getItemInHand().getItemMeta() != null)
				if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
				String itName[] = player.getItemInHand().getItemMeta().getDisplayName().split(" ");
				
				//player.sendMessage("itName " + itName[0] + " " + itName[1]);
				
				if (itName.length == 3) {
					if (itName[0].equalsIgnoreCase("55")) {
						
						dew.amountRecursiveCount = 0;
						
						int ig1 = block.getTypeId();
						byte ig2 = block.getData();
						
						
						//player.sendMessage("ptdew&dewdd : 55 runing..." + ig1 + ":" + ig2);
						dew.item55delete(block, player, ig1, ig2, Integer.parseInt(itName[1]), Integer.parseInt(itName[2]),20);
							//player.sendMessage("ptdew&dewdd : 55 done...");
					}
					
				}
				}
				
				
				if (player.getItemInHand().getTypeId() == Material.REDSTONE_TORCH_OFF.getId()
						|| player.getItemInHand().getTypeId() == Material.REDSTONE_TORCH_ON.getId())
					if (block.getTypeId() == 54) {
						// auto give item for all player on server
						dew.redtorchchest(block, player);
					}

				if (player.getItemInHand().getTypeId() == 50) {
					dew.dewsetlaround(player, 50, (byte) 0);
				}

				// free break
				if (player.getInventory().first(354) != -1)
					if (player.getItemInHand().getType().getMaxDurability() > 0)
						if (dew.randomG.nextInt(100) < player.getLevel()
								&& api_admin.dewddadmin.is2moderator(player) == false && player.getLevel() < 101) {

							if (dew.isprotectitemid(block.getTypeId()) == true)
								return;

							switch (player.getItemInHand().getTypeId()) {
							case 0:
							case 276:
							case 268:
							case 267:
							case 272:
								return;
							default:

								block.breakNaturally(player.getItemInHand());
								player.getItemInHand()
										.setDurability((short) (player.getItemInHand().getDurability() + (short) 1));
								break;
							}

						} // free break

			} // have permission

		} catch (Exception e) {
			System.err.println("BlockDamageEvent error: Damage event " + e.getMessage());
		}

	}

	// BlockBreakEvent

	// EntityInteractEvent

	// BlockPlaceEvent
	@EventHandler
	public void eventja(BlockPlaceEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Block block = event.getBlock();
		Player player = event.getPlayer();
		block.getWorld();

		block.getX();
		block.getY();
		block.getZ();

		boolean goodc1 = false;

		goodc1 = dew.checkpermissionarea(block, player, "build");

		if (goodc1 == true) {
			event.setCancelled(true);
		} else {

			dew.randomplaynote(player.getLocation());
			// A
			// *************************************

			// rice block Iron = rice

			// rice block Iron = rice
			/*
			 * switch (player.getItemInHand().getTypeId()) { case 295: case 391:
			 * case 392:
			 * 
			 * dew.soiladdseedrecusive(block, player, player.getItemInHand()
			 * .getTypeId(), true);
			 * 
			 * return; }
			 */
			// gold = pumkin

			// block gold = sugar cane

			/*
			 * if (player.getItemInHand().getTypeId() == 46 &&
			 * api_admin.dewddadmin.is2moderator(player.getName()) == false) {
			 * event.setCancelled(true); player.getItemInHand().setTypeId(0);
			 * player.sendMessage("ptdew&dewdd : Can't place TNT");
			 * player.setGameMode(GameMode.SURVIVAL); }
			 */

			// A
			// have permission
		}

		// for
	}

	@EventHandler
	public void eventja(ChunkUnloadEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getWorld().getName()))
			return;

	}

	// BlockPlaceEvent

	// BlockDamageEvent

	@SuppressWarnings("deprecation")
	@EventHandler
	public void eventja(CreatureSpawnEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		if (event.getEntity() == null)
			return;
		if (event.getCreatureType() == null)
			return;

		if (dew.allownight == false) {
			event.setCancelled(true);
			return;
		}

		event.getEntity().addPotionEffect(
				PotionEffectType.JUMP.createEffect(dew.randomG.nextInt(3000), dew.randomG.nextInt(10)));

		if (event.getCreatureType() == CreatureType.CHICKEN)
			return;
		if (event.getCreatureType() == CreatureType.COW)
			return;
		if (event.getCreatureType() == CreatureType.SHEEP)
			return;
		if (event.getCreatureType() == CreatureType.MUSHROOM_COW)
			return;
		if (event.getCreatureType() == CreatureType.PIG)
			return;
		if (event.getCreatureType() == CreatureType.SQUID)
			return;

		if (dew.checkpermissionarea(event.getEntity().getLocation().getBlock()) == true)
			if (dew.checkpermissionarea(event.getEntity().getLocation().getBlock(), true) != -1)
				if (dew.havethisnameinthishome(dew.getworldid(event.getEntity().getWorld().getName()),
						dew.checkpermissionarea(event.getEntity().getLocation().getBlock(), true),
						dew.flag_monster) == false) {
					event.setCancelled(true);
					return;
				}

		for (Player xo : Bukkit.getOnlinePlayers()) {
			if (xo.getInventory().getHelmet() == null) {
				continue;
			}
			if (xo.getInventory().getHelmet().getTypeId() == 397) {
				Location loc = xo.getLocation();
				loc.setWorld(xo.getWorld());
				loc.setY(loc.getY() + 3);
				loc.setX(loc.getX() + 3);
				// search high
				int counthigh = 0;
				Block bd = null;

				for (int loo = -1; loo >= -24; loo--) {
					bd = event.getEntity().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
							.getRelative(0, (int) (loc.getY() + counthigh), 3);

					if (bd.getTypeId() == 0) {
						counthigh++;
					} else {
						break;
					}

					if (counthigh > 8) {
						break;
					}
				}

				bd = event.getEntity().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
						.getRelative(0, (int) loc.getY(), 3);
				if (counthigh <= 8 && bd.getLightLevel() <= 7) {
					event.getEntity().teleport(loc);
				}
			}
		}

		// dew.randomplaynote(event.getEntity().getLocation());

		// dprint.r.printC("xyz="+
		// event.getEntity().getLocation().toString());
		if (dew.allownight == false) {
			event.setCancelled(true);
			event.getEntity().getWorld().setTime(6000);
			return;
		}

		/*
		 * if (event.getEntity().getType() == EntityType.CREEPER) { if
		 * (dew.randomG.nextInt(1000) < 40) {
		 * event.getEntity().getWorld().strikeLightning(
		 * event.getEntity().getLocation()); } }
		 */

		LivingEntity abc = event.getEntity();
		if (abc == null)
			return;

		if (abc.getType() == EntityType.VILLAGER)
			return;
		if (abc.getType() == EntityType.CHICKEN)
			return;
		if (abc.getType() == EntityType.COW)
			return;
		if (abc.getType() == EntityType.SHEEP)
			return;
		if (abc.getType() == EntityType.SQUID)
			return;
		if (abc.getType() == EntityType.SLIME)
			return;
		if (abc.getType() == EntityType.MAGMA_CUBE)
			return;

		if (abc.getType() == EntityType.PIG)
			return;

		Location laco = event.getEntity().getLocation();

		int counthigh = 0;
		for (int gg = -1; gg >= -12; gg--) {
			Block ct = event.getEntity().getWorld().getBlockAt(laco).getRelative(0, gg, 00);
			if (ct.getTypeId() == 0) {
				counthigh++;
				if (counthigh > 10) {
					event.setCancelled(true);
				}
			} /*
				 * else if (ct.getTypeId() == 9 || ct.getTypeId() == 8 ||
				 * ct.getTypeId() == 10 || ct.getTypeId() == 11) {
				 * event.setCancelled(true);
				 * 
				 * }
				 */
			else {
				break;
			}

		}

		laco.setZ(laco.getZ() + 2);
		if (laco.getY() < 1 || laco.getY() > 253) {
			laco.setY(1);
		}
		while (event.getEntity().getWorld().getBlockAt(laco).getTypeId() != 0) {

			if (laco.getY() < 1 || laco.getY() > 253) {
				laco.setY(1);
			}

			laco.setY(laco.getY() + 1);
			laco.setZ(laco.getZ() + 1);

		}

		if (dew.randomG.nextInt(100) < 82)
			if (laco.getBlock().getLightLevel() <= 8) {
				event.getEntity().getWorld().spawnCreature(laco, event.getCreatureType());
			}

		if (dew.randomG.nextInt(100) > 80) {
			event.getEntity().addPotionEffect(
					PotionEffectType.INVISIBILITY.createEffect(dew.randomG.nextInt(3000), dew.randomG.nextInt(10)));
		}
		if (dew.randomG.nextInt(100) > 70) {
			event.getEntity().addPotionEffect(
					PotionEffectType.SPEED.createEffect(dew.randomG.nextInt(3000), dew.randomG.nextInt(10)));
		}
		if (dew.randomG.nextInt(100) > 60) {
			event.getEntity().addPotionEffect(
					PotionEffectType.FIRE_RESISTANCE.createEffect(dew.randomG.nextInt(3000), dew.randomG.nextInt(10)));
		}

		if (dew.randomG.nextInt(100) > 60) {
			event.getEntity().addPotionEffect(
					PotionEffectType.HEAL.createEffect(dew.randomG.nextInt(3000), dew.randomG.nextInt(10)));
		}

		if (dew.randomG.nextInt(100) > 56) {
			event.getEntity().addPotionEffect(
					PotionEffectType.SLOW.createEffect(dew.randomG.nextInt(3000), dew.randomG.nextInt(10)));
		}
		if (dew.randomG.nextInt(100) > 47) {
			event.getEntity().addPotionEffect(
					PotionEffectType.WATER_BREATHING.createEffect(dew.randomG.nextInt(3000), dew.randomG.nextInt(10)));
		}

	}

	@EventHandler
	public void eventja(EntityChangeBlockEvent event) {

		if (event.getEntity() == null)
			return;

		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		if (event.getEntity().getType() == EntityType.ENDERMAN && dew.checkpermissionarea(event.getBlock()) == true) {
			event.setCancelled(true);
			return;
		}

		if (event.getEntity().getType() == EntityType.PLAYER) {
			Player pal = (Player) event.getEntity();

			if (dew.checkpermissionarea(event.getBlock(), pal, "changeBlock") == true) {
				event.setCancelled(true);
			}
		}

	}

	// EntityExplodeEvent
	@EventHandler
	public void eventja(EntityExplodeEvent event) throws InterruptedException {

		/*
		 * if (event.getEntity().getLocation().getWorld().getName().
		 * equalsIgnoreCase ("world")==false) { return; }
		 */
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		Block block = event.getLocation().getBlock();
		// event.setCancelled(true);
		if (dew.checkpermissionarea(block) == true) {
			event.setCancelled(true);
			return;
		}

		boolean vinear = false;
		int exe = dew.randomG.nextInt(100);
		if (exe < 10) {
			vinear = false;

			for (Player vi : event.getEntity().getWorld().getPlayers())
				if (api_admin.dewddadmin.is2vip(vi) == true)
					if (vi.getLocation().distance(event.getEntity().getLocation()) <= 50) {
						vinear = true;
						break;
					}

			if (vinear == true) {
				event.setCancelled(true);
				return;
			}

			// block.getWorld().createExplosion(block.getLocation(),
			// dew.randomG.nextInt(30), true);
			// block.getWorld().strikeLightning(block.getLocation());
		} else {
			vinear = false;
			for (Player vi : event.getEntity().getWorld().getPlayers())
				if (api_admin.dewddadmin.is2vip(vi) == true)
					if (vi.getLocation().distance(event.getEntity().getLocation()) <= 50) {
						// vi.sendMessage("ptdew&dewdd :
						// เราได้หยุดการระเบิดครั้งใหญ่เพราะคุณเป็น vip
						// อยู่ใกล้การระเบิด 50 บล็อค");
						vinear = true;
						break;
					}

			if (vinear == true) {
				event.setCancelled(true);
				return;
			}
		}

	}

	// EntityExplodeEvent

	@EventHandler
	public void eventja(EntityInteractEvent event) {
		if (event.getEntity() == null)
			return;

		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		if (event.getEntity().getType() == EntityType.PLAYER) {
			Player prp = (Player) event.getEntity();
			if (dew.checkpermissionarea(event.getBlock(), prp, "EntityInteract") == true) {
				event.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void eventja(HangingBreakByEntityEvent event) {

		if (event.getRemover() == null) {
			event.setCancelled(true);
			return;
		}

		if (!tr.isrunworld(ac.getName(), event.getRemover().getWorld().getName()))
			return;

		if (event.getRemover().getType() == EntityType.PLAYER) {
			Player br = (Player) event.getRemover();

			if (dew.checkpermissionarea(event.getEntity().getLocation().getBlock(), br,
					"HangingBreakByEntity") == true) {
			//	br.sendMessage("ptdew&dewdd : " + tr.gettr("don't_break_hanging_picture_not_yours"));

				event.setCancelled(true);
				return;
			}

		}
	}

	// Chat Event.class
	@EventHandler
	public void eventja(HangingBreakEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;
		
		
		if (dew.checkpermissionarea(event.getEntity().getLocation().getBlock()) == true) {
			event.setCancelled(true);
		}
		
		if (event.getCause() == RemoveCause.EXPLOSION == true) {
			if (dew.checkpermissionarea(event.getEntity().getLocation().getBlock()) == true) {
				event.setCancelled(true);
				return;
			}
		} else {

			int homeid = dew.checkpermissionarea(event.getEntity().getLocation().getBlock(), true);
			if (homeid > -1) {
				// check near player

				double dist = 10000;
				double temp = 10000;
				Player pl = null;

				for (Player pd : event.getEntity().getWorld().getPlayers()) {
					temp = pd.getLocation().distance(event.getEntity().getLocation());
					if (temp < dist) {
						dist = temp;
						pl = pd;
					}
				}

				if (dist < 10000)
					if (dew.checkpermissionarea(event.getEntity().getLocation().getBlock(), pl, "break") == true) {
					//	pl.sendMessage(tr.gettr("don't_destroy_hanging_picture_not_yours"));
						event.setCancelled(true);
						return;
					}

				// always remove if there are staff
				/*
				 * for (int c = 0; c < dew.dewsignnamemax; c++) if
				 * (api_admin.dewddadmin .issubsubadminname(dew.dewsignname[dew
				 * .getworldid(event.getEntity().getLocation()
				 * .getWorld().getName())][homeid][c]) == true) {
				 * 
				 * event.getEntity().remove(); return;
				 * 
				 * }
				 */

			}

		}

	}

	@EventHandler
	public void eventja(HangingPlaceEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player br = event.getPlayer();
		if (dew.checkpermissionarea(event.getPlayer().getLocation().getBlock(), br, "HangingPlaceEvent") == true) {
			//br.sendMessage("ptdew&dewdd : " + tr.gettr("don't_place_hanging_picture_not_yours"));

			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(EntityDamageEvent e) {
		if (!tr.isrunworld(ac.getName(), e.getEntity().getWorld().getName()))
			return;
		
		if (e.getEntity() instanceof EntityPlayer) {
		Player br = (Player) e.getEntity();
			if (dew.checkpermissionarea(br.getLocation().getBlock(), br, "EntityDamageEvent") == true) {
			//br.sendMessage("ptdew&dewdd : " + tr.gettr("don't_place_hanging_picture_not_yours"));

			e.setCancelled(true);
		}
		}
	}
	
	@EventHandler
	public void eventja(PlayerInteractEntityEvent e) {
		
		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName()))
			return;

		Player br = e.getPlayer();
		if (dew.checkpermissionarea(e.getPlayer().getLocation().getBlock(), br, "HangingPlaceEvent") == true) {
		//	br.sendMessage("ptdew&dewdd : " + tr.gettr("don't_interact_not_your"));

			e.setCancelled(true);
		}

		
	}
	
	@EventHandler
	public void eventja(InventoryOpenEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		if (api_admin.dewddadmin.is2moderator((Player) event.getPlayer()) == true)
			if (InventoryType.PLAYER != event.getInventory().getType()
					&& InventoryType.WORKBENCH != event.getInventory().getType()
					&& InventoryType.CRAFTING != event.getInventory().getType()
					&& InventoryType.CREATIVE != event.getInventory().getType()) {
				event.setCancelled(true);
			}

	}

	@EventHandler
	public void eventja(InventoryPickupItemEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getItem().getWorld().getName()))
			return;

		// search teleport sign
		int x = 0;
		int y = 0;
		int z = 0;
		Block block2 = null;
		int dx = 3;

		/*
		 * dprint.r.printA((int)event.getItem().getLocation().getX() + "," +
		 * (int) event.getItem().getLocation().getY() + "," + (int)
		 * event.getItem().getLocation().getZ());
		 */

		for (x = -dx; x <= dx; x++) {
			for (y = -dx; y <= dx; y++) {
				for (z = -dx; z <= dx; z++) {
					block2 = event.getItem().getLocation().getBlock().getRelative(x, y, z);
					if (block2.getTypeId() == 63 || block2.getTypeId() == 68) {
						Sign sign = (Sign) block2.getState();
						if (sign.getLine(0).equalsIgnoreCase("[dewhopper]") == true) {

							int tx = Integer.parseInt(sign.getLine(1));
							int ty = Integer.parseInt(sign.getLine(2));
							int tz = Integer.parseInt(sign.getLine(3));
							Location loc = event.getItem().getLocation();
							loc.setX(tx);
							loc.setY(ty);
							loc.setZ(tz);
							event.getItem().teleport(loc);
							event.setCancelled(true);
							return;
						}
					}

				}
			}
		}

		// fitter item
		for (x = -dx; x <= dx; x++) {
			for (y = -dx; y <= dx; y++) {
				for (z = -dx; z <= dx; z++) {
					block2 = event.getItem().getLocation().getBlock().getRelative(x, y, z);
					if (block2.getTypeId() == 63 || block2.getTypeId() == 68) {
						Sign sign = (Sign) block2.getState();
						if (sign.getLine(0).equalsIgnoreCase("[dewhopperx]") == true) {

							int tx = Integer.parseInt(sign.getLine(1)); // ID
							int ty = Byte.parseByte(sign.getLine(2)); // data

							if (event.getItem().getItemStack() == null)
								return;

							if (event.getItem().getItemStack().getTypeId() == tx) {
								// dprint.r.printA("seem id");
								// dprint.r.printA("data = " +
								// event.getItem().getItemStack().getData().getData());
								if ((ty == -29 || ty == event.getItem().getItemStack().getData().getData()) == false) {
									// dprint.r.printA("pause");
									event.setCancelled(true);
									return;
								}
							} else {
								event.setCancelled(true);
								return;
							}

						}
					}

				}
			}
		}

	}

	@EventHandler
	public void eventja(ItemDespawnEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		if (event.getEntity().getItemStack().getTypeId() == 344)
			return;

		Location lo2 = event.getLocation();

		if (dew.giftblock == null) {
			dew.loadgiftfile();
		}

		lo2 = dew.giftblock.getLocation();

		ItemStack abx = event.getEntity().getItemStack();
		abx.setAmount(event.getEntity().getItemStack().getAmount());

		// lo2.getChunk().load();
		lo2.getWorld().dropItem(lo2, abx);

		dprint.r.printC("ptdew&dewdd : teleported item '" + event.getEntity().getItemStack().getType().name()
				+ "' amount = '" + event.getEntity().getItemStack().getAmount() + "'" + " from "
				+ event.getLocation().getWorld().getName());

	}

	@EventHandler
	public void eventja(ItemSpawnEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		int ix = 0;
		if (ix == 0)
			return;

		/*
		 * if (dew.checkpermissionarea(event.getLocation().getBlock()) == true)
		 * { for (Player pd : Bukkit.getOnlinePlayers()) if
		 * (api_admin.dewddadmin.issubsubadminname(pd.getName()) == true) if
		 * (dew.havethisnameinthishome(dew.getworldid(event
		 * .getEntity().getWorld().getName()), dew .checkpermissionarea(
		 * event.getLocation().getBlock(), true), pd .getName()) == true) {
		 * 
		 * // dprint.r.printAll("ptdew&dewdd : Not allow ItemSpawnEvent cuz '"
		 * // + pd.getName() + "' are there in server"); // dprint.r.printAll(
		 * "ptdew&dewdd : ไม่อณุญาตให้สิ่งของเกิดขึ้นในเขตโพเทคนี้ เพราะสตาฟคนนี้ อยู่ในเซิฟเวอร์"
		 * ); event.setCancelled(true); break; } }
		 */

		/*
		 * for (Player pd : Bukkit.getOnlinePlayers()) { if
		 * (pd.getWorld().getName()
		 * .equalsIgnoreCase(event.getLocation().getWorld().getName()) == false)
		 * { continue; } if
		 * (api_admin.dewddadmin.issubsubadminname(pd.getName()) == false) {
		 * continue; }
		 * 
		 * if (pd.getLocation().distance(event.getLocation()) <= 30) {
		 * 
		 * event.setCancelled(true); return; }
		 * 
		 * }
		 */
	}

	@EventHandler
	public void eventja(PlayerBucketEmptyEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		if (dew.checkpermissionarea(event.getBlockClicked(), event.getPlayer(), "build") == true) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerBucketFillEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		if (dew.checkpermissionarea(event.getBlockClicked(), event.getPlayer(), "build") == true) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player player = event.getPlayer();

		if (event.getMessage().equalsIgnoreCase("/who") == true && api_admin.dewddadmin.is2admin(player) == false) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("ptdew&dewdd : Block who command");
			return;
		}
		if (event.getMessage().equalsIgnoreCase("/list") == true && api_admin.dewddadmin.is2admin(player) == false) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("ptdew&dewdd : Block list command");
			return;
		}
		if (event.getMessage().startsWith("/motd") == true && api_admin.dewddadmin.is2admin(player) == false) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("ptdew&dewdd : Block motd command");
			return;
		}

		if ((event.getMessage().toLowerCase().startsWith("/eco") == true
				|| event.getMessage().toLowerCase().startsWith("/economy") == true)
				&& api_admin.dewddadmin.is2admin(player) == false) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("ptdew&dewdd : Block eco command");
			return;
		}

		if (event.getMessage().equalsIgnoreCase("/fly") == true || event.getMessage().equalsIgnoreCase("/tp ") == true
				|| event.getMessage().equalsIgnoreCase("/gm") == true
				|| event.getMessage().equalsIgnoreCase("/give") == true
				|| event.getMessage().equalsIgnoreCase("/invsee") == true
				|| event.getMessage().equalsIgnoreCase("/back") == true
				|| event.getMessage().equalsIgnoreCase("/gamemode") == true
				|| event.getMessage().equalsIgnoreCase("/time") == true
				|| event.getMessage().equalsIgnoreCase("/online") == true
				|| event.getMessage().equalsIgnoreCase("/nick") == true
				|| event.getMessage().equalsIgnoreCase("/top") == true)
			if (api_admin.dewddadmin.is2vip(player) == false && api_admin.dewddadmin.is2moderator(player) == false
					&& api_admin.dewddadmin.is2admin(player) == false) {
				dprint.r.printAll("ptdew&dewdd : '" + player.getName() + "' try to use '" + event.getMessage() + "' ");

				event.setCancelled(true);
			}

		if (event.getMessage().toLowerCase().startsWith("/l ") == false
				&& event.getMessage().toLowerCase().startsWith("/login ") == false
				&& event.getMessage().toLowerCase().startsWith("/authme ") == false
				&& event.getMessage().toLowerCase().startsWith("/changepassword ") == false

		) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.hasPermission(pseecommand)) {
					pl.sendMessage(dprint.r.color(player.getName() + " : " + event.getMessage()));
				}
			}

		}

		chatx ab = new chatx(event.getMessage().substring(1), event.getPlayer());

		event.setCancelled(ab.canc);

		chatz ar = new chatz();
		ar.player = event.getPlayer();
		ar.message = event.getMessage().substring(1);
		ar.start();

		dprint.r.printC(event.getMessage());
		dew.hideshowrun(event.getPlayer());

	}

	// PlayerDeathEvent
	@EventHandler(priority = EventPriority.HIGHEST)
	public void eventja(PlayerDeathEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getEntity().getWorld().getName()))
			return;

		final Player player = event.getEntity().getPlayer();
		player.setAllowFlight(false);
		player.setGameMode(GameMode.SURVIVAL);

		event.getDeathMessage();

		if (api_admin.dewddadmin.is2moderator(player) == true) {
			event.getDrops().clear();
			event.setDroppedExp(0);
			return;
		}
		player.sendMessage("ptdew&dewdd : Wak!!!!!");

	}

	// PlayerDeathEvent

	// PlayerDropItemEvent
	@EventHandler
	public void eventja(PlayerDropItemEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player player = event.getPlayer();
		dew.randomplaynote(event.getPlayer().getLocation());

		int idc = dew.getfreeselect(player);
		if (dew.chatever[idc] == false) {
			dew.chatever[idc] = true;
			player.sendMessage("ptdew&dewdd : " + tr.gettr("ok_you_can_chat_now"));

		}

		if (api_admin.dewddadmin.is2moderator(player) == true) {
			player.getItemInHand().setTypeId(0);
			player.sendMessage("ptdew&dewdd : " + tr.gettr("staff_can't_drop_item"));
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(PlayerGameModeChangeEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player player = event.getPlayer();
		if (event.getNewGameMode() == GameMode.CREATIVE)
			if (player.hasPermission("essentials.gamemode") == false) {
				event.setCancelled(true);
				player.setGameMode(GameMode.SURVIVAL);
				/*
				 * Economy.setMoney(player.getName(),
				 * Economy.getMoney(player.getName()) - 500);
				 */

				dprint.r.printAll("ptdew&dewdd :" + player.getName() + tr.gettr("can_be_creative_mode_not_admin"));

			}
	}

	// PlayerInteractEvent

	@EventHandler
	public void eventja(PlayerInteractEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Action act;
		act = event.getAction();

		if ((act == Action.RIGHT_CLICK_BLOCK || act == Action.LEFT_CLICK_BLOCK) == false)
			return;

		Player player = event.getPlayer();
		if (player.getInventory().first(50) == -1)
			if (player.getLocation().getBlock().getLightLevel() < 7) {
				player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(dew.randomG.nextInt(5000) + 1000,
						dew.randomG.nextInt(5)), true);
			} else {
				player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			}

		Block block = event.getClickedBlock();
		if (player.getItemInHand().getTypeId() == 6 && act == Action.LEFT_CLICK_BLOCK) {
			dew.showwhohome(block, player);
			event.setCancelled(true);

			return;
		}

		if (block.getTypeId() == 63 || block.getTypeId() == 68) {
			Sign sd = (Sign) block.getState();
			if (sd.getLine(0).toLowerCase().endsWith("[buy]") || sd.getLine(0).toLowerCase().endsWith("[sell]")
					|| sd.getLine(0).toLowerCase().endsWith("[trade]") || sd.getLine(0).toLowerCase().endsWith("[free]")
							&& block.getWorld().getName().equalsIgnoreCase("world") == false

			) {
				block.breakNaturally();
			}

			if (sd.getLine(0).toLowerCase().endsWith("admin shop")
					&& block.getWorld().getName().equalsIgnoreCase("world") == false) {
				block.breakNaturally();
			}

		}

		boolean goodc1 = false;
		goodc1 = dew.checkpermissionarea(block, player, "right");
		
		/*if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().equals(Material.ITEM_FRAME)
				&& dew.checkpermissionarea(block, player, "playerInteractEvent") == true){
			
			event.setCancelled(true);
		}*/

		if (goodc1 == true) {
			event.setCancelled(true);
		} else {

			if (player.getItemInHand().getTypeId() == Material.REDSTONE_TORCH_OFF.getId()
					|| player.getItemInHand().getTypeId() == Material.REDSTONE_TORCH_ON.getId())
				if (block.getTypeId() == 54) {
					// auto give item for all player on server
					dew.redtorchchest(block, player);
				}

			if (block.getTypeId() == 63 || block.getTypeId() == 68) {

				Sign sign = (Sign) block.getState();

				if (sign.getLine(0).endsWith("[dclear]") == true) {
					if (api_admin.dewddadmin.is2admin(player) == false
							&& api_admin.dewddadmin.is2moderator(player) == false) {
						player.sendMessage("ptdew&dewdd : " + tr.gettr("only_admin_can_build_this_sign"));
						return;
					}

					player.getInventory().clear();
					player.sendMessage("ptdew&dewdd : " + tr.gettr("cleared_your_inventory"));
					return;

				}

				if (sign.getLine(0).endsWith("[dgive]") == true) {
					if (api_admin.dewddadmin.is2admin(player) == false
							&& api_admin.dewddadmin.is2moderator(player) == false) {
						player.sendMessage("ptdew&dewdd : " + tr.gettr("only_admin_can_build_this_sign"));
						return;
					}
					int gid = Integer.parseInt(sign.getLine(1));
					byte gdata = Byte.parseByte(sign.getLine(2));
					int gamount = Integer.parseInt(sign.getLine(3));

					if (gamount <= 0) {
						player.sendMessage("ptdew&dewdd : line 4 = amount  so  amount must > 0");

						return;
					}
					if (gdata < 0) {
						player.sendMessage("ptdew&dewdd : line 3 = data id  so  amount must >= 0");
						return;
					}

					if (gid <= 0) {
						player.sendMessage("ptdew&dewdd : line 2 = id  so  amount must > 0");
						return;
					}

					ItemStack ax = new ItemStack(gid, gamount);
					ax.getData().setData(gdata);
					player.getInventory().addItem(ax);
					player.sendMessage("ptdew&dewdd : " + tr.gettr("gave_item_to_you"));

					return;
				}

			}

			/*
			 * if (player.getItemInHand().getTypeId() == 276 &&
			 * player.getInventory().first(49) > -1) { World world =
			 * player.getWorld(); Location loca = player.getEyeLocation();
			 * 
			 * if (dew.checkpermissionarea(block) == false) {
			 * world.createExplosion(loca, 3); event.setCancelled(true); }
			 * 
			 * }
			 */

			if (player.getItemInHand().getTypeId() == 290 && act == Action.LEFT_CLICK_BLOCK) {
				// set x1y1z1
				int getid = dew.getfreeselect(player);
				dew.selectx1[getid] = block.getX();
				dew.selecty1[getid] = block.getY();
				dew.selectz1[getid] = block.getZ();
				dew.selectworldname[getid] = block.getWorld().getName();

				int countblock = Math.abs(1 + dew.selectx1[getid] - dew.selectx2[getid])
						* Math.abs(1 + dew.selecty1[getid] - dew.selecty2[getid])
						* Math.abs(1 + dew.selectz1[getid] - dew.selectz2[getid]);

				player.sendMessage("ptdew&dewdd : Block 1 = (" + dew.selectx1[getid] + "," + dew.selecty1[getid] + ","
						+ dew.selectz1[getid] + ") to (" + dew.selectx2[getid] + "," + dew.selecty2[getid] + ","
						+ dew.selectz2[getid] + ") = " + countblock);
				event.setCancelled(true);
				return;
			}

			if (player.getItemInHand().getTypeId() == 290 && act == Action.RIGHT_CLICK_BLOCK) {
				// set x1y1z1

				int getid = dew.getfreeselect(player);
				dew.selectx2[getid] = block.getX();
				dew.selecty2[getid] = block.getY();
				dew.selectz2[getid] = block.getZ();
				dew.selectworldname[getid] = block.getWorld().getName();

				int countblock = Math.abs(1 + dew.selectx1[getid] - dew.selectx2[getid])
						* Math.abs(1 + dew.selecty1[getid] - dew.selecty2[getid])
						* Math.abs(1 + dew.selectz1[getid] - dew.selectz2[getid]);

				player.sendMessage("ptdew&dewdd : Block 2 = (" + dew.selectx1[getid] + "," + dew.selecty1[getid] + ","
						+ dew.selectz1[getid] + ") to (" + dew.selectx2[getid] + "," + dew.selecty2[getid] + ","
						+ dew.selectz2[getid] + ") = " + countblock);
				event.setCancelled(true);
			}

			if (player.getItemInHand().getTypeId() == 276 // diamondsword
					&& act == Action.RIGHT_CLICK_BLOCK) {
				// set x1y1z1

				int getid = dew.getfreeselect(player);
				dew.selectblock[getid] = block;
				dew.selectworldname[getid] = block.getWorld().getName();

				player.sendMessage("ptdew&dewdd : " + tr.gettr("diamondsword_seted_dewa_block") + " ("
						+ dew.selectblock[getid].getX() + "," + dew.selectblock[getid].getY() + ","
						+ dew.selectblock[getid].getZ() + ")");
				// event.setCancelled(true);
			}

			/*
			 * if (player.getItemInHand().getTypeId() == 351 &&
			 * player.getItemInHand().getData().getData() == 15 && act ==
			 * Action.RIGHT_CLICK_BLOCK) { switch (block.getTypeId()) { case
			 * 142: case 141: case 59: case 104: case 105: dew.seedglow(block,
			 * player); } }
			 */

		} // else

	}

	@EventHandler
	public void eventja(PlayerJoinEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		dew.saveworld();
		final Player player = event.getPlayer();

		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, new Runnable() {

			public void run() {
				player.sendMessage(tr.gettr("on_main_playerjoin_tell_him_this_word"));
			}
		}, 100);
		
		

		if (api_admin.dewddadmin.is2admin(player) == false && api_admin.dewddadmin.is2moderator(player) == false
				&& api_admin.dewddadmin.is2vip(player) == false) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() + " apple 1");
		}

		int idc = dew.getfreeselect(player);
		dew.chatever[idc] = true;

		int gga = dew.getfreeselect(player);
		if (api_admin.dewddadmin.is2admin(player) == false) {
			dew.hidemode[gga] = false;
		} else {
			dew.hidemode[gga] = true;
		}
		dew.hideshowrun(player);

		if (api_admin.dewddadmin.is2admin(player) == true) {
			event.setJoinMessage("");
		}

		if (api_admin.dewddadmin.is2admin(player) == false) {
			player.setGameMode(GameMode.SURVIVAL);
			player.setFlying(false);
		}

		System.gc();

	}

	@EventHandler
	public void eventja(PlayerQuitEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		int idc = dew.getfreeselect(event.getPlayer());
		dew.chatever[idc] = false;

		dew.saveworld();
		Player player = event.getPlayer();
		dew.hideshowrun(event.getPlayer());

		if (api_admin.dewddadmin.is2admin(player) == true) {
			event.setQuitMessage("");
			int gga = dew.getfreeselect(event.getPlayer());
			dew.hidemode[gga] = true;
		}

		event.setQuitMessage("");
		System.gc();
	}

	@EventHandler
	public void eventja(PlayerTeleportEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		event.getPlayer().addPotionEffect(PotionEffectType.REGENERATION.createEffect(200, 1), false);

		int perhell = dew.randomG.nextInt(6000);
		if (perhell <= 20)
			if (event.getFrom().getWorld().getName().equalsIgnoreCase("world") == true
					&& event.getTo().getWorld().getName().equalsIgnoreCase("world") == true) {

				Location lo1 = event.getTo();
				Location lo2 = lo1;
				lo2.setWorld(Bukkit.getWorld("world_nether"));
				dew.gotohell(event.getPlayer(), lo1, lo2);
			}

		if (event.getFrom().getWorld().getName().equalsIgnoreCase(event.getTo().getWorld().getName()) == false)
			if (dewddadmin.is2admin(event.getPlayer()) == false) {
				event.getPlayer().setGameMode(GameMode.SURVIVAL);
			}

	}

	public void eventja(ServerCommandEvent e) {
		new server_c(e.getCommand());
	}

	@EventHandler
	public void eventja(SignChangeEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName()))
			return;

		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (event.getLine(0).equalsIgnoreCase("[dewbuy]") == true
				|| event.getLine(0).equalsIgnoreCase("[dewhome]") == true) {

			player.sendMessage("checking radius");
			int ra = 5;

			if (event.getLine(1).equalsIgnoreCase("") == true) {
				event.setLine(1, "5");
			} else {

				ra = Integer.parseInt(event.getLine(1));
				if (ra > 50) {
					ra = 50;
				}
				if (ra < 5) {
					ra = 5;
				}
				event.setLine(1, Integer.toString(ra));

			}

			int pi = dew.getfreeselect(player);
			dew.selectx1[pi] = (int) block.getLocation().getX() - (ra + 1);
			dew.selecty1[pi] = (int) block.getLocation().getY() - (ra + 1);
			dew.selectz1[pi] = (int) block.getLocation().getZ() - (ra + 1);

			if (dew.selecty1[pi] < 1) {
				dew.selecty1[pi] = 1;
			}

			dew.selectx2[pi] = (int) block.getLocation().getX() + ra + 1;
			dew.selecty2[pi] = (int) block.getLocation().getY() + ra + 1;
			dew.selectz2[pi] = (int) block.getLocation().getZ() + ra + 1;

			if (dew.selecty2[pi] > 255) {
				dew.selecty2[pi] = 255;
			}

			boolean gx = dew.dewbuy(player);

			if (gx == true) {

				player.sendMessage("dewbuy " + ra + tr.gettr("dewbuy_complete"));
				int c = dew.getwallid();
				ItemStack it = new ItemStack(c);
				it.setAmount(300);
				player.setItemInHand(it);
				dew.dewsetblock(player, player.getItemInHand().getTypeId(), (byte) 0, true);

			} else {
				player.sendMessage(tr.gettr("dewbuy_not_complete_need_money"));
				event.setLine(0, "<dewbuy>");
				event.setLine(1, "no money!");
				event.setLine(2, "need money!");
				event.setLine(3, "@_@");

			}

		}

		if (event.getLine(0).endsWith("[dgive]") == true) {
			if (api_admin.dewddadmin.is2admin(player) == false && api_admin.dewddadmin.is2moderator(player) == false) {
				player.sendMessage("ptdew&dewdd : " + tr.gettr("only_staff_admin_can_build_this_sign"));
				event.getBlock().breakNaturally();
				return;
			}
			int gid = Integer.parseInt(event.getLine(1));
			int gdata = Integer.parseInt(event.getLine(2));
			int gamount = Integer.parseInt(event.getLine(3));

			if (gamount <= 0) {
				player.sendMessage("ptdew&dewdd : line 4 = amount  so  amount must > 0");
				return;
			}
			if (gdata < 0) {
				player.sendMessage("ptdew&dewdd : line 3 = data id  so  amount must >= 0");
				return;
			}

			if (gid <= 0) {
				player.sendMessage("ptdew&dewdd : line 2 = id  so  amount must > 0");
				return;
			}

			player.sendMessage("ptdew&dewdd : " + tr.gettr("created_sign"));
			return;
		}
	}

	// PlayerRespawnEvent

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent event) {
		if (!tr.isrunworld(ac.getName(), event.getToBlock().getWorld().getName()))
			return;

		Block block = event.getBlock();

		Block block2 = event.getToBlock();

		if (block.getTypeId() == 8 || block.getTypeId() == 9 || block.getTypeId() == 10 || block.getTypeId() == 11) {
			int b1pro = dew.checkpermissionarea(block, true);
			if (b1pro >= 0) { // b1 pro

				if (block2.getTypeId() == 0) {

					int proid = dew.checkpermissionarea(block2, true);
					if (proid >= 0) { // b2 protect

						boolean ab = dew.havethisnameinthishome(dew.getworldid(block2.getWorld().getName()), proid,
								dew.flag_stopwater);
						if (ab == true) {
							event.setCancelled(true);
						}

					} // b2pro
					else {
						boolean ab = dew.havethisnameinthishome(dew.getworldid(block.getWorld().getName()), b1pro,
								dew.flag_nooutwater);
						if (ab == true) {
							event.setCancelled(true);
						}
					}

					// do what you need here.

					// dprint.r.printAll("water");
				}

			} // b1 pro
			else // dprint.r.printAll(block2.getTypeId() + "");
				if (block2.getTypeId() == 0) {

				int proid = dew.checkpermissionarea(block2, true);
				if (proid == -1)
					return;

				boolean ab = dew.havethisnameinthishome(dew.getworldid(block2.getWorld().getName()), proid,
						dew.flag_noinwater);
				if (ab == true) {
					// block2.getWorld().strikeLightningEffect(block2.getLocation());
					event.setCancelled(true);
				}
			} else {

			}

		}

	}

	public void signprotectrail(Block block, Player player) {
		if (api_admin.dewddadmin.is2admin(player) == false)
			return;

	}

} // class
