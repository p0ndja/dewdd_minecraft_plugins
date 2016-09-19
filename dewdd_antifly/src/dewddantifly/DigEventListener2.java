/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddantifly;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

class Dewminecraft {

	int		countShow		= 0;

	int		countLoop		= 0;

	int		maximumfly		= 200;

	int		dewnamemax		= 100;
	String	dewname[]		= new String[dewnamemax];
	double	lasty[]			= new double[dewnamemax];

	Random	randomGenerator	= new Random();

	int		selectmax			= 29;

	String	selectname[]		= new String[selectmax + 1];

	int		selectflycount[]	= new int[selectmax + 1];
	int		dangerlv[]			= new int[selectmax + 1];
	int		d5					= 6;
	void automessage(Player player) {

		countLoop++;
		if (countLoop > 1000) {

			countLoop = 0;
			countShow++;
			for (World world : Bukkit.getWorlds()) {
				world.save();
				for (Player pla : world.getPlayers()) {
					pla.saveData();
				}

				switch (countShow) {
				case 1:
					for (Player ics : world.getPlayers()) {
						ics.sendMessage("ptdew&dewdd antifly Plugin : My Facebook : https://www.facebook.com/dewddminecraft");
						ics.sendMessage("ptdew&dewdd antifly Plugin by ptdew&dewdd");
					}
					break;
				case 2:
					for (Player ics : world.getPlayers()) {
						ics.sendMessage("ptdew&dewdd antifly Plugin :  My E-Mail(MSN) : dew_patipat@hotmail.com");
						ics.sendMessage("ptdew&dewdd : ปลั๊กอิน  ป้องกันม็อดบิน สร้างโดย  ptdew&dewdd");
					}
					countShow = 0;
					break;

				} // switch
			}
		}
	}
	int getfreeselect(Player player) {
		int lp1 = 0;

		boolean foundza = false;
		// clear select array
		for (lp1 = 0; lp1 < selectmax; lp1++) {

			if (selectname[lp1] == null) {
				selectname[lp1] = "null";
				selectflycount[lp1] = 0;
				lasty[lp1] = 255;
				dangerlv[lp1] = 0;
			}
		} // loop allselect

		for (lp1 = 0; lp1 < selectmax; lp1++) {
			foundza = false;
			// loop player for clear
			for (Player pla : player.getWorld().getPlayers()) {
				if (pla.getName().equalsIgnoreCase(selectname[lp1]) == true) {
					foundza = true;
					break;
					// found
				}

			} // loop all player

			if (foundza == false) { // clear
				selectname[lp1] = "null";
				selectflycount[lp1] = 0;
				lasty[lp1] = 0;
				dangerlv[lp1] = 0;
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

		System.out.println("ptdew&dewdd: GetFreeselect return -1");
		return -1;
	}
	// getfreeselect

	// randomplaynote
	void randomplaynote(Location player) {
		if (randomGenerator.nextInt(100) > 10) {
			return;
		}

		for (Player pla : player.getWorld().getPlayers()) {
			pla.playSound(player, Sound.NOTE_PIANO,
					randomGenerator.nextInt(50), randomGenerator.nextFloat()
							+ (float) 1);
		}
	}

}

public class DigEventListener2 implements Listener {
	Dewminecraft	dew	= new Dewminecraft();
	JavaPlugin		ac	= null;

	// BlockDamageEvent

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		dew.automessage(player);
		dew.randomplaynote(player.getLocation());

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd antifly");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd antifly") == true) {
			player.sendMessage("ปลั๊กอิน antifly นี้ เป็นปลั๊กอินป้องกันคนใช้ม็อดบิน    คนไม่มีสิทธิบิน แต่ลอยจากพื้นได้   จะถูกนับจำนวนไว้  ถ้าถึงขั้นที่กำหนด "
					+ "จะมีข้อความเตือน 3 ครั้ง , การกระโดดสูงถูกจัดไว้ในพวกใช้ม็อดบินเช่นกัน ฉะนั้นโปรดระวัง  , ถ้าถึงขั้นที่กำหนด จะโดนแบนถาวร");
			event.setCancelled(true);
		}

		if (event.getMessage().equalsIgnoreCase("antifly") == true
				|| event.getMessage().equalsIgnoreCase("dewantifly") == true) {
			event.setCancelled(true);

			dprint.r.printAll("ptdew&dewdd antifly Plugin : My Facebook : https://www.facebook.com/MinecraftDewserverthzaptoorg");
			dprint.r.printAll("ptdew&dewdd antifly Plugin by ptdew&dewdd");

			dprint.r.printAll("ptdew&dewdd antifly Plugin :  My E-Mail(MSN) : dew_patipat@hotmail.com");
			dprint.r.printA("ptdew&dewdd : ปลั๊กอิน  ป้องกันม็อดบิน สร้างโดย  ptdew&dewdd");

		}

	}

	// Chat Event.class
	@EventHandler
	public void eventja(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		dew.automessage(player);
		dew.randomplaynote(player.getLocation());
	}

	@EventHandler
	public void eventja(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		int digx = (int) player.getLocation().getX();
		int digy = (int) player.getLocation().getY();
		int digz = (int) player.getLocation().getZ();

		int y = 0;
		int x = 0;
		int z = 0;

		boolean freeright = false;
		freeright = false;
		Block block;

		for (x = -2; x <= 2; x++) {
			for (z = -2; z <= 2; z++) {
				for (y = 0 - dew.d5; y <= 1; y++) {

					block = player.getWorld().getBlockAt(digx, digy, digz)
							.getRelative(x, y, z);

					if (block.getTypeId() != 0) {
						freeright = true;
						return;
					}
				}
			}
		}

		// player.sendMessage("ex");

		if (freeright == false && player.getAllowFlight() == false) {
			// if (freeright == false ) {

			dew.automessage(player);
			int getid = dew.getfreeselect(player);

			int nowdanger = 0;

			if (dew.lasty[getid] <= player.getLocation().getY()) {
				nowdanger = ((dew.selectflycount[getid] * 100) / dew.maximumfly);
				String showstr = "ptdew&DewDD: antifly '" + player.getName()
						+ "' = " + nowdanger + "%";

				// player.sendMessage("nowdanger = " + nowdanger +
				// ", dangerLV : " + dew.dangerlv[getid]);

				if (nowdanger > 80) {
					if (dew.dangerlv[getid] == 50) {
						// player.sendMessage("ptdew&dewdd: '" +
						// player.getName() +
						// "' antifly don't use mod fly agane!  (3/3)");
						// player.sendMessage("ptdew&dewdd: เตือนครั้งสุดท้าย สำหรับคนใช้ม็อดบิน  โดยแบนตลอดไป ลาก่อน  (3/3)");
						player.sendMessage("ptdew&dewdd : [ระบบป้องกันการใช้ม็อดบิน] เตือนครั้งสุดท้าย ก่อนโดนแบน  ห้ามบินอีก!");
						dew.dangerlv[getid] = 80;
					}
				}
				else if (nowdanger > 50) {
					if (dew.dangerlv[getid] == 10) {
						// player.sendMessage("ptdew&dewdd: '" +
						// player.getName() +
						// "' antifly don't use mod fly agane!  (2/3)");
						// player.sendMessage("ptdew&dewdd: เลิกเกรียนได้แล้ว กล้าใช้ม็อดบินอีกหรอ หน้าด้านว่ะ โดยแบนถาวรแน่  (2/3)");
						player.sendMessage("ptdew&dewdd : [ระบบป้องกันการใช้ม็อดบิน] ห้ามใช้ม็อดบิน นี่เป็นการเตือนครั้งที่สอง");
						dew.dangerlv[getid] = 50;
					}
				}
				else if (nowdanger > 10) {
					if (dew.dangerlv[getid] == 0) {
						// player.sendMessage("ptdew&dewdd:'" + player.getName()
						// + "'antifly don't use mod fly agane!  (1/3)");
						// player.sendMessage("ptdew&dewdd: ห้ามใช้ม็อดบินอีกนะ  ไม่งั้นเราจะแบน นายถาวร  (1/3)");
						player.sendMessage("ptdew&dewdd : [ระบบป้องกันการใช้ม็อดบิน] เราตรวจพบว่าคุณลอยอยู่บนฟ้า โดยไม่มีสิทธิ์บิน  หยุดใช้ม็อดบินเดี๋ยวนี้");
						dew.dangerlv[getid] = 10;
					}
				}

				System.out.println(showstr);
				dprint.r.printFly(showstr);

				/*
				 * for (Player cde : Bukkit.getOnlinePlayers()) { if
				 * (cde.getAllowFlight() == true) { cde.sendMessage(showstr); }
				 * 
				 * 
				 * }
				 */

				dew.selectflycount[getid]++;

				dew.lasty[getid] = player.getLocation().getY();
			}

			else {
				dew.lasty[getid] = player.getLocation().getY();
			}

			if (dew.selectflycount[getid] > dew.maximumfly) {
				for (Player cde : Bukkit.getOnlinePlayers()) {
					cde.sendMessage("ptdew&dewdd: " + player.getName()
							+ " โดนแบนแล้ว เพราะใช้ม็อดบิน");
				}
				System.out.println("ptdew&dewdd antifly : baned .. "
						+ player.getName() + " mod -fly");

				player.setBanned(true);
				player.kickPlayer("ptdew&DewDD : baned you cuz mod-fly");

			}

			return;
		}

	} // class

} // class

