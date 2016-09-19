/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddsetline;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DigEventListener2 implements Listener {
	class chatx implements Runnable {
		Player	p		= null;
		String	message	= "";

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

				}
				catch (NumberFormatException e) {
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

				p.sendMessage("block " + c.getX() + "," + c.getY() + ","
						+ c.getZ() + " = " + c.getTypeId() + ":" + c.getData());

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

					}
					else {

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
						p.sendMessage("admin overide setline completed " + ln
								+ " = " + m[2]);

					}

					break;
				default:
					p.sendMessage("the block you are looking is not a sign is a "
							+ c.getTypeId() + ":" + c.getData());
				}

			}

		}
	}

	class ddata {

		public String	playername[];
		public Block	lastblock[];

	}

	/*
	 * commands: dewsetline: description: dewsetline if for set new line usage:
	 * /<command> [line] [message]
	 * 
	 * permissions: dewdd.private.overide: description: for admin can open any
	 * chest... default: op dewdd.private.setline.everysign: description: for
	 * admin reset line every sign default: op
	 */

	Random		randomG				= new Random();

	JavaPlugin	ac					= null;

	String		psetlineeveryone	= "dewdd.private.setline.everysign";

	int		ddatamax	= 29;

	ddata	nn			= new ddata();

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

		Player player = event.getPlayer();

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd setline");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd setline") == true) {
			player.sendMessage("ปลั๊กอิน setline ไว้แก้ข้อความในป้ายอย่างง่าย");
			event.setCancelled(true);
		}

	}
	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {

		chatx a = new chatx();
		a.p = event.getPlayer();
		a.message = event.getMessage();
		Bukkit.getScheduler().scheduleSyncDelayedTask(ac, a);
	}

	@EventHandler
	public void eventja(PlayerInteractEvent event) {

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

		int getid = getfreeselect(player.getName());
		nn.lastblock[getid] = event.getClickedBlock();

		if (!(nn.lastblock[getid].getTypeId() == 63 || nn.lastblock[getid]
				.getTypeId() == 68)) {
			return;
		}

		Sign s = (Sign) nn.lastblock[getid].getState();
		if ((s.getLine(0).equalsIgnoreCase("[private]") || s.getLine(0)
				.equalsIgnoreCase("[dewdd]") == true)

		&& s.getLine(1).equalsIgnoreCase(player.getName()) == true) {

			player.sendMessage("you_can_change_line_3_and_line 4 (your friend name");
			player.sendMessage("/dewsetline 3 ptdew");
			player.sendMessage("/dewsetline 4 pondja_kung");

		}

	}

	@EventHandler
	public void eventja(SignChangeEvent event) throws IndexOutOfBoundsException {

		Player player = event.getPlayer();

		if (event.getLine(0).equalsIgnoreCase("") == true
				&& event.getLine(1).equalsIgnoreCase("") == true
				&& event.getLine(2).equalsIgnoreCase("") == true
				&& event.getLine(3).equalsIgnoreCase("") == true) {
			event.setLine(0, "[dewdd]");
			event.setLine(1, player.getName());
		}

		if (event.getLine(0).toLowerCase().endsWith("[dewdd]") == true
				|| event.getLine(0).toLowerCase().endsWith("[private]") == true

		) {

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