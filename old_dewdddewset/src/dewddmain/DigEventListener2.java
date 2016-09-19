/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddmain;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddflower.dewset;

class Dewddminecraft extends dewset {

}

public class DigEventListener2 implements Listener {

	class chatx implements Runnable {
		private Player	player	= null;
		private String	message	= "";

		String[]	m		= message.split("\\s+");

		Boolean		canc	= false;

		public chatx(Player player, String message) {
			this.player = player;
			this.message = message;
		}

		@Override
		public void run() {
			m = message.split("\\s+");
			if (message.equalsIgnoreCase("dewdd help") == true) {
				player.sendMessage(">dewdd dewset");
				canc = true;
			}

			if (message.equalsIgnoreCase("dewdd dewset") == true) {
				player.sendMessage("ปลั๊กอิน dewset api ระบบตัวนี้ เป็น api ที่ระบบอื่นจะสืบทอดแล้วนำไปใช้เป็นของตัวเอง เช่น ระบบ main จะมีโพเทคของตัวเอง แต่มาดึง"
						+ "วิธีการวางบล็อค จากที่นี่ไป");
				player.sendMessage("dewset api ช่วยในการวางบล็อคจำนวนมากอย่างรวดเร็ว");
				player.sendMessage(">>dewdd flower");

				canc = true;
			}

			if (message.equalsIgnoreCase("dewdd flower") == true) {
				player.sendMessage(">dewdd flower");
				player.sendMessage("dewdd flower ระบบ ดอกไม้   เป็นการ  กำหนดเขตด้วย Wooden hoe  คลิกซ้ายที่หนึ่ง ขวาอีกที่  (เหมือนขวานไม้ world edit)");
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

				player.sendMessage(".dewdelete   ถือบล็อคแล้วพิมพ์ เป็นการลบบล็อคที่ตรงกับที่เราถือ  (ใช้ได้ในเขตโพเทคที่ตัวเองมีสิทธิ)");
				player.sendMessage(".dewfullcircle   ถือบล็อคแล้วพิมพ์ จะเป็นการวาดวงกลม ในพื้นที่");
				player.sendMessage(".dewspread4   ถือบล็อคแล้วพิมพ์ จะเป็นการวางบล็อคแบบแพร่กระจาย สี่ทิศ");
				player.sendMessage(".dewspread9   ถือบล็อคแล้วพิมพ์ จะเป็นการวางบล็อคแบบแพร่กระจาย 9 ทิศ");
				player.sendMessage(".dewcopy   ไปยืนที่ต้องการแล้วพิมพ์ จะเป็นการคัดลอกมาวาง");
				player.sendMessage(".dewspreadcircle   ไว้แพร่กระจายบล็อคไปรอบทิศจากตัวเรา  ระวังโดนหนีบ");
				player.sendMessage(".dewspreadq   ระบบแพ่รกระจายบล็อคในชั้นที่เรายืนแบบใหม่ คาดว่าจะทำงานเร็วขึ้น");
				player.sendMessage(".dewa   ยืนบนบล็อคมรกต    บล็อคเพชร 1 บล็อคติดรอบ บล็อคมรกตเพื่อกำหนดทิศ   จากนั้นพิมพ์   จำนวนของในมือ"
						+ " เป็นจำนวนครั้งในการ ขยายสิ่งของ ไปในทิศที่ต้องการ ทิศตามบล็อคเพชร    ถ้าอยากยืดข่้นฟ้า  ไม่ต้องมีบล็อคเพชร");

				canc = true;
			}

		} // sync
	}

	class chatz extends Thread {
		Player	player	= null;
		String	message	= "";

		public void run() {
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
			h[30] = "dfc";

			for (int ccc = 0; ccc < h.length; ccc++) {
				if (m[0].equalsIgnoreCase(h[ccc]) == true) {
					if (m.length == 1) {
						dew.runter(h[ccc], player, player.getItemInHand()
								.getTypeId(), player.getItemInHand().getData()
								.getData());
					}
					else if (m.length == 2) {
						String[] m2 = m[1].split(":");

						int itemid = 0;
						byte dataid = 0;

						player.sendMessage("m2[0] = " + m2[0] + " search.. = "
								+ dew.getmaterialrealname(m2[0]));

						if (dew.isNumeric(m2[0]) == true) {
							if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
								player.sendMessage("error argument 1  what the hell item?!");
							}
							else {
								itemid = Material.getMaterial(
										Integer.parseInt(m2[0])).getId();
							}
						}
						else {

							if (Material.getMaterial(dew
									.getmaterialrealname(m2[0])) == null) { // name
								player.sendMessage("error argument 1  what the hell item?!");
							}
							else {
								itemid = Material.getMaterial(
										dew.getmaterialrealname(m2[0])).getId();
							}

						}

						if (m2.length == 2) {
							dataid = Byte.parseByte(m2[1]);
						}

						player.sendMessage("itemid = " + itemid + ", dataid = "
								+ dataid);
						dew.runter(h[ccc], player, itemid, dataid);
					}

					return;
				}
			}

			// dewset
			if (m[0].equalsIgnoreCase("dewset") == true
					|| m[0].equalsIgnoreCase("ds") == true) {
				int a1 = -29;
				byte a2 = -29;
				int a3 = -29;
				byte a4 = 0;

				if (m.length == 1) {
					dew.dewset(player, -29, (byte) -29, player.getItemInHand()
							.getTypeId(), player.getItemInHand().getData()
							.getData(), false);
				}
				else if (m.length == 2) { // dewset 005:?
					String[] m2 = m[1].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// data if 2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a3 + ":" + a4 + " and "
							+ a1 + ":" + a2);
					dew.dewset(player, a1, a2, a3, a4, false);
				}
				else if (m.length == 3) { // dewset 005:? 003:?
					String[] m2 = m[1].split(":");

					// a1

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// a2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					// a3
					m2 = m[2].split(":");
					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a1 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a1 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// a2
					if (m2.length == 2) {
						a2 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a1 + ":" + a2 + " and "
							+ a3 + ":" + a4);
					dew.dewset(player, a1, a2, a3, a4, false);
				}

				return;
			} // dewset

			// dewset 444 00 555 00
			if (m[0].equalsIgnoreCase("dewxet") == true
					|| m[0].equalsIgnoreCase("dx") == true) {
				int a1 = -29;
				byte a2 = -29;
				int a3 = -29;
				byte a4 = 0;

				if (m.length == 1) {
					dew.dewset(player, -29, (byte) -29, player.getItemInHand()
							.getTypeId(), player.getItemInHand().getData()
							.getData(), true);
				}
				else if (m.length == 2) { // dewset 005:?
					String[] m2 = m[1].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// data if 2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a3 + ":" + a4 + " and "
							+ a1 + ":" + a2);
					dew.dewset(player, a1, a2, a3, a4, true);
				}
				else if (m.length == 3) { // dewset 005:? 003:?
					String[] m2 = m[1].split(":");

					// a1
					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a3 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// a2
					if (m2.length == 2) {
						a4 = Byte.parseByte(m2[1]);
					}

					// a3
					m2 = m[2].split(":");

					if (dew.isNumeric(m2[0]) == true) {
						if (Material.getMaterial(Integer.parseInt(m2[0])) == null) { // number
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a1 = Material.getMaterial(Integer.parseInt(m2[0]))
									.getId();
						}
					}
					else {

						if (Material
								.getMaterial(dew.getmaterialrealname(m2[0])) == null) { // name
							player.sendMessage("error argument 1  what the hell item?!");
						}
						else {
							a1 = Material.getMaterial(
									dew.getmaterialrealname(m2[0])).getId();
						}

					}

					// a2
					if (m2.length == 2) {
						a2 = Byte.parseByte(m2[1]);
					}

					player.sendMessage("itemid = " + a1 + ":" + a2 + " and "
							+ a3 + ":" + a4);
					dew.dewset(player, a1, a2, a3, a4, true);
				}

				return;
			} // dewset

			// dewdig
			if (message.equalsIgnoreCase("dewdddig") == true
					|| message.equalsIgnoreCase("ddd") == true) {

				dew.dewdig(player);

				return;
			}

			// dewcopy
			if (message.equalsIgnoreCase("dewcopy") == true
					|| message.equalsIgnoreCase("dc") == true) {
				dew.dewcopy(player);

				return;
			}

			// dewa
			if (m[0].equalsIgnoreCase("dewa") == true
					|| m[0].equalsIgnoreCase("da") == true) {

				int amo = 0;
				if (m.length == 1) {

					dew.dewa(player, 0);
				}
				else if (m.length == 2) {
					if (dew.isNumeric(m[1]) == false) {
						player.sendMessage("/dewa amount(it's not number T_T)");
						return;
					}

					amo = Integer.parseInt(m[1]);
					dew.dewa(player, amo);
				}

				return;
			}

			if (message.equalsIgnoreCase("dewsetprivate") == true
					|| message.equalsIgnoreCase("dsp") == true) {
				try {
					dew.dewsetprivate(player);
				}
				catch (UserDoesNotExistException | NoLoanPermittedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return;
			}

		}
	}

	JavaPlugin		ac	= null;

	Dewddminecraft	dew	= null;

	// synchronized
	@EventHandler
	public void eventja(AsyncPlayerChatEvent event)
			throws UserDoesNotExistException, NoLoanPermittedException {

		chatx ab = new chatx(event.getPlayer(), event.getMessage());
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);

	}

	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		chatx ab = new chatx(event.getPlayer(), event.getMessage().substring(1));
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);

	}

} // class

