/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddlanguage;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

class Dewminecraft {

	int countShow = 0;

	int countLoop = 0;

	Random randomGenerator = new Random();

	void automessage(Player player) {

		countLoop++;
		if (countLoop > 100) {

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
						ics.sendMessage(
								"ptdew&dewdd language Plugin : My Facebook : https://www.facebook.com/dewddminecraft");
					}
					break;
				case 2:
					for (Player ics : world.getPlayers()) {
						ics.sendMessage("ptdew&dewdd language Plugin :  My E-Mail(MSN) : dew_patipat@hotmail.com");
					}
					break;
				case 3:
					for (Player ics : world.getPlayers()) {
						ics.sendMessage(
								"ptdew&dewdd : พิมพ์ ; นำหน้า แล้วตาม ด้วยข้อความไทย แบบลืมเปลี่ยนภาษา สำหรับคนที่ใช้คีย์บอร์ดแบบ qwerty และ kedmanee");
						ics.sendMessage(
								"ptdew&dewdd : พิมพ์ ' นำหน้า แล้วตาม ด้วยข้อความไทย แบบลืมเปลี่ยนภาษา สำหรับคนที่ใช้คีย์บอร์ดแบบ dvorak และ pattachote");
					}
					countShow = 0;
					break;

				} // switch
			}
		}
	}

	public String engtothai(String st) {
		if (st.length() > 1) {
			// System.out.println("" + st.substring(0,1));
			if (st.substring(0, 1).equalsIgnoreCase("'") == true) {
				String stok = "";

				for (int stl = 1; stl < st.length(); stl++) {
					String stt = st.substring(stl, stl + 1);

					// eeee

					if (stt.equals("1") == true) {
						stok = stok + "=";
						continue;
					}
					if (stt.equals("2") == true) {
						stok = stok + "๒";
						continue;
					}
					if (stt.equals("3") == true) {
						stok = stok + "๓";
						continue;
					}
					if (stt.equals("4") == true) {
						stok = stok + "๔";
						continue;
					}
					if (stt.equals("5") == true) {
						stok = stok + "๕";
						continue;
					}
					if (stt.equals("6") == true) {
						stok = stok + "ู";
						continue;
					}
					if (stt.equals("7") == true) {
						stok = stok + "๗";
						continue;
					}
					if (stt.equals("8") == true) {
						stok = stok + "๘";
						continue;
					}
					if (stt.equals("9") == true) {
						stok = stok + "๙";
						continue;
					}
					if (stt.equals("0") == true) {
						stok = stok + "๐";
						continue;
					}
					if (stt.equals("[") == true) {
						stok = stok + "๑";
						continue;
					}
					if (stt.equals("]") == true) {
						stok = stok + "๖";
						continue;
					}
					if (stt.equals("!") == true) {
						stok = stok + "+";
						continue;
					}
					if (stt.equals("@") == true) {
						stok = stok + "\"";
						continue;
					}
					if (stt.equals("#") == true) {
						stok = stok + "/";
						continue;
					}
					if (stt.equals("$") == true) {
						stok = stok + ",";
						continue;
					}
					if (stt.equals("%") == true) {
						stok = stok + "?";
						continue;
					}
					if (stt.equals("^") == true) {
						stok = stok + "ุ";
						continue;
					}
					if (stt.equals("&") == true) {
						stok = stok + "_";
						continue;
					}
					if (stt.equals("*") == true) {
						stok = stok + ".";
						continue;
					}
					if (stt.equals("(") == true) {
						stok = stok + "(";
						continue;
					}
					if (stt.equals(")") == true) {
						stok = stok + ")";
						continue;
					}
					if (stt.equals("{") == true) {
						stok = stok + "-";
						continue;
					}
					if (stt.equals("}") == true) {
						stok = stok + "%";
						continue;
					}
					if (stt.equals("'") == true) {
						stok = stok + "็";
						continue;
					}
					if (stt.equals(",") == true) {
						stok = stok + "ต";
						continue;
					}
					if (stt.equals(".") == true) {
						stok = stok + "ย";
						continue;
					}
					if (stt.equals("p") == true) {
						stok = stok + "อ";
						continue;
					}
					if (stt.equals("y") == true) {
						stok = stok + "ร";
						continue;
					}
					if (stt.equals("f") == true) {
						stok = stok + "่";
						continue;
					}
					if (stt.equals("g") == true) {
						stok = stok + "ด";
						continue;
					}
					if (stt.equals("c") == true) {
						stok = stok + "ม";
						continue;
					}
					if (stt.equals("r") == true) {
						stok = stok + "ว";
						continue;
					}
					if (stt.equals("l") == true) {
						stok = stok + "แ";
						continue;
					}
					if (stt.equals("/") == true) {
						stok = stok + "ใ";
						continue;
					}
					if (stt.equals("=") == true) {
						stok = stok + "ฌ";
						continue;
					}
					if (stt.equals("\\") == true) {
						stok = stok + "";
						continue;
					}
					if (stt.equals("\"") == true) {
						stok = stok + "๊";
						continue;
					}
					if (stt.equals("<") == true) {
						stok = stok + "ฤ";
						continue;
					}
					if (stt.equals(">") == true) {
						stok = stok + "ๆ";
						continue;
					}
					if (stt.equals("P") == true) {
						stok = stok + "ญ";
						continue;
					}
					if (stt.equals("Y") == true) {
						stok = stok + "ษ";
						continue;
					}
					if (stt.equals("F") == true) {
						stok = stok + "ึ";
						continue;
					}
					if (stt.equals("G") == true) {
						stok = stok + "ฝ";
						continue;
					}
					if (stt.equals("C") == true) {
						stok = stok + "ซ";
						continue;
					}
					if (stt.equals("R") == true) {
						stok = stok + "ถ";
						continue;
					}
					if (stt.equals("L") == true) {
						stok = stok + "ฒ";
						continue;
					}
					if (stt.equals("?") == true) {
						stok = stok + "ฯ";
						continue;
					}
					if (stt.equals("+") == true) {
						stok = stok + "ฦ";
						continue;
					}
					if (stt.equals("|") == true) {
						stok = stok + "ํ";
						continue;
					}
					if (stt.equals("a") == true) {
						stok = stok + "้";
						continue;
					}
					if (stt.equals("o") == true) {
						stok = stok + "ท";
						continue;
					}
					if (stt.equals("e") == true) {
						stok = stok + "ง";
						continue;
					}
					if (stt.equals("u") == true) {
						stok = stok + "ก";
						continue;
					}
					if (stt.equals("i") == true) {
						stok = stok + "ั";
						continue;
					}
					if (stt.equals("d") == true) {
						stok = stok + "ี";
						continue;
					}
					if (stt.equals("h") == true) {
						stok = stok + "า";
						continue;
					}
					if (stt.equals("t") == true) {
						stok = stok + "น";
						continue;
					}
					if (stt.equals("n") == true) {
						stok = stok + "เ";
						continue;
					}
					if (stt.equals("s") == true) {
						stok = stok + "ไ";
						continue;
					}
					if (stt.equals("-") == true) {
						stok = stok + "ข";
						continue;
					}
					if (stt.equals("A") == true) {
						stok = stok + "๋";
						continue;
					}
					if (stt.equals("O") == true) {
						stok = stok + "ธ";
						continue;
					}
					if (stt.equals("E") == true) {
						stok = stok + "ำ";
						continue;
					}
					if (stt.equals("U") == true) {
						stok = stok + "ณ";
						continue;
					}
					if (stt.equals("I") == true) {
						stok = stok + "์";
						continue;
					}
					if (stt.equals("D") == true) {
						stok = stok + "ื";
						continue;
					}
					if (stt.equals("H") == true) {
						stok = stok + "ผ";
						continue;
					}
					if (stt.equals("T") == true) {
						stok = stok + "ช";
						continue;
					}
					if (stt.equals("N") == true) {
						stok = stok + "โ";
						continue;
					}
					if (stt.equals("S") == true) {
						stok = stok + "ฆ";
						continue;
					}
					if (stt.equals("_") == true) {
						stok = stok + "ฑ";
						continue;
					}
					if (stt.equals(";") == true) {
						stok = stok + "บ";
						continue;
					}
					if (stt.equals("q") == true) {
						stok = stok + "ป";
						continue;
					}
					if (stt.equals("j") == true) {
						stok = stok + "ล";
						continue;
					}
					if (stt.equals("k") == true) {
						stok = stok + "ห";
						continue;
					}
					if (stt.equals("x") == true) {
						stok = stok + "ิ";
						continue;
					}
					if (stt.equals("b") == true) {
						stok = stok + "ค";
						continue;
					}
					if (stt.equals("m") == true) {
						stok = stok + "ส";
						continue;
					}
					if (stt.equals("w") == true) {
						stok = stok + "ะ";
						continue;
					}
					if (stt.equals("v") == true) {
						stok = stok + "จ";
						continue;
					}
					if (stt.equals("z") == true) {
						stok = stok + "พ";
						continue;
					}
					if (stt.equals(":") == true) {
						stok = stok + "ฎ";
						continue;
					}
					if (stt.equals("Q") == true) {
						stok = stok + "ฏ";
						continue;
					}
					if (stt.equals("J") == true) {
						stok = stok + "ฐ";
						continue;
					}
					if (stt.equals("K") == true) {
						stok = stok + "ภ";
						continue;
					}
					if (stt.equals("X") == true) {
						stok = stok + "ั";
						continue;
					}
					if (stt.equals("B") == true) {
						stok = stok + "ศ";
						continue;
					}
					if (stt.equals("M") == true) {
						stok = stok + "ฮ";
						continue;
					}
					if (stt.equals("W") == true) {
						stok = stok + "ฟ";
						continue;
					}
					if (stt.equals("V") == true) {
						stok = stok + "ฉ";
						continue;
					}
					if (stt.equals("Z") == true) {
						stok = stok + "ฬ";
						continue;
					}

					// jjj

					stok = stok + stt;
				}

				return (stok);

			}
		}

		// . devorok

		// . devorok

		// normal
		// normal
		if (st.length() > 1) {
			// System.out.println("" + st.substring(0,1));
			if (st.substring(0, 1).equalsIgnoreCase(";") == true) {
				String stok = "";

				for (int stl = 1; stl < st.length(); stl++) {
					String stt = st.substring(stl, stl + 1);

					// eeee
					if (stt.equals("1") == true) {
						stok = stok + "ๅ";
						continue;
					}
					if (stt.equals("2") == true) {
						stok = stok + "/";
						continue;
					}
					if (stt.equals("3") == true) {
						stok = stok + "-";
						continue;
					}
					if (stt.equals("4") == true) {
						stok = stok + "ภ";
						continue;
					}
					if (stt.equals("5") == true) {
						stok = stok + "ถ";
						continue;
					}
					if (stt.equals("6") == true) {
						stok = stok + "ุ";
						continue;
					}
					if (stt.equals("7") == true) {
						stok = stok + "ึ";
						continue;
					}
					if (stt.equals("8") == true) {
						stok = stok + "ค";
						continue;
					}
					if (stt.equals("9") == true) {
						stok = stok + "ต";
						continue;
					}
					if (stt.equals("0") == true) {
						stok = stok + "จ";
						continue;
					}
					if (stt.equals("-") == true) {
						stok = stok + "ข";
						continue;
					}
					if (stt.equals("=") == true) {
						stok = stok + "ช";
						continue;
					}
					if (stt.equals("!") == true) {
						stok = stok + "+";
						continue;
					}
					if (stt.equals("@") == true) {
						stok = stok + "๑";
						continue;
					}
					if (stt.equals("#") == true) {
						stok = stok + "๒";
						continue;
					}
					if (stt.equals("$") == true) {
						stok = stok + "๓";
						continue;
					}
					if (stt.equals("%") == true) {
						stok = stok + "๔";
						continue;
					}
					if (stt.equals("^") == true) {
						stok = stok + "ู";
						continue;
					}
					if (stt.equals("&") == true) {
						stok = stok + "฿";
						continue;
					}
					if (stt.equals("*") == true) {
						stok = stok + "๕";
						continue;
					}
					if (stt.equals("(") == true) {
						stok = stok + "๖";
						continue;
					}
					if (stt.equals(")") == true) {
						stok = stok + "๗";
						continue;
					}
					if (stt.equals("_") == true) {
						stok = stok + "๘";
						continue;
					}
					if (stt.equals("+") == true) {
						stok = stok + "๙";
						continue;
					}
					if (stt.equals("q") == true) {
						stok = stok + "ๆ";
						continue;
					}
					if (stt.equals("w") == true) {
						stok = stok + "ไ";
						continue;
					}
					if (stt.equals("e") == true) {
						stok = stok + "ำ";
						continue;
					}
					if (stt.equals("r") == true) {
						stok = stok + "พ";
						continue;
					}
					if (stt.equals("t") == true) {
						stok = stok + "ะ";
						continue;
					}
					if (stt.equals("y") == true) {
						stok = stok + "ั";
						continue;
					}
					if (stt.equals("u") == true) {
						stok = stok + "ี";
						continue;
					}
					if (stt.equals("i") == true) {
						stok = stok + "ร";
						continue;
					}
					if (stt.equals("o") == true) {
						stok = stok + "น";
						continue;
					}
					if (stt.equals("p") == true) {
						stok = stok + "ย";
						continue;
					}
					if (stt.equals("[") == true) {
						stok = stok + "บ";
						continue;
					}
					if (stt.equals("]") == true) {
						stok = stok + "ล";
						continue;
					}
					if (stt.equals("\\") == true) {
						stok = stok + "ฃ";
						continue;
					}
					if (stt.equals("Q") == true) {
						stok = stok + "๐";
						continue;
					}
					if (stt.equals("W") == true) {
						stok = stok + "\"";
						continue;
					}
					if (stt.equals("E") == true) {
						stok = stok + "ฎ";
						continue;
					}
					if (stt.equals("R") == true) {
						stok = stok + "ฑ";
						continue;
					}
					if (stt.equals("T") == true) {
						stok = stok + "ธ";
						continue;
					}
					if (stt.equals("Y") == true) {
						stok = stok + "ํ";
						continue;
					}
					if (stt.equals("U") == true) {
						stok = stok + "๊";
						continue;
					}
					if (stt.equals("I") == true) {
						stok = stok + "ณ";
						continue;
					}
					if (stt.equals("O") == true) {
						stok = stok + "ฯ";
						continue;
					}
					if (stt.equals("P") == true) {
						stok = stok + "ญ";
						continue;
					}
					if (stt.equals("{") == true) {
						stok = stok + "ฐ";
						continue;
					}
					if (stt.equals("}") == true) {
						stok = stok + ",";
						continue;
					}
					if (stt.equals("|") == true) {
						stok = stok + "ฅ";
						continue;
					}
					if (stt.equals("a") == true) {
						stok = stok + "ฟ";
						continue;
					}
					if (stt.equals("s") == true) {
						stok = stok + "ห";
						continue;
					}
					if (stt.equals("d") == true) {
						stok = stok + "ก";
						continue;
					}
					if (stt.equals("f") == true) {
						stok = stok + "ด";
						continue;
					}
					if (stt.equals("g") == true) {
						stok = stok + "เ";
						continue;
					}
					if (stt.equals("h") == true) {
						stok = stok + "้";
						continue;
					}
					if (stt.equals("j") == true) {
						stok = stok + "่";
						continue;
					}
					if (stt.equals("k") == true) {
						stok = stok + "า";
						continue;
					}
					if (stt.equals("l") == true) {
						stok = stok + "ส";
						continue;
					}
					if (stt.equals(";") == true) {
						stok = stok + "ว";
						continue;
					}
					if (stt.equals("'") == true) {
						stok = stok + "ง";
						continue;
					}

					if (stt.equals("A") == true) {
						stok = stok + "ฤ";
						continue;
					}
					if (stt.equals("S") == true) {
						stok = stok + "ฆ";
						continue;
					}
					if (stt.equals("D") == true) {
						stok = stok + "ฏ";
						continue;
					}
					if (stt.equals("F") == true) {
						stok = stok + "โ";
						continue;
					}
					if (stt.equals("G") == true) {
						stok = stok + "ฌ";
						continue;
					}
					if (stt.equals("H") == true) {
						stok = stok + "็";
						continue;
					}
					if (stt.equals("J") == true) {
						stok = stok + "๋";
						continue;
					}
					if (stt.equals("K") == true) {
						stok = stok + "ษ";
						continue;
					}
					if (stt.equals("L") == true) {
						stok = stok + "ศ";
						continue;
					}
					if (stt.equals(":") == true) {
						stok = stok + "ซ";
						continue;
					}
					if (stt.equals("z") == true) {
						stok = stok + "ผ";
						continue;
					}
					if (stt.equals("x") == true) {
						stok = stok + "ป";
						continue;
					}
					if (stt.equals("c") == true) {
						stok = stok + "แ";
						continue;
					}
					if (stt.equals("v") == true) {
						stok = stok + "อ";
						continue;
					}
					if (stt.equals("b") == true) {
						stok = stok + "ิ";
						continue;
					}
					if (stt.equals("n") == true) {
						stok = stok + "ื";
						continue;
					}
					if (stt.equals("m") == true) {
						stok = stok + "ท";
						continue;
					}
					if (stt.equals(",") == true) {
						stok = stok + "ม";
						continue;
					}
					if (stt.equals(".") == true) {
						stok = stok + "ใ";
						continue;
					}
					if (stt.equals("/") == true) {
						stok = stok + "ฝ";
						continue;
					}
					if (stt.equals("Z") == true) {
						stok = stok + "(";
						continue;
					}
					if (stt.equals("X") == true) {
						stok = stok + ")";
						continue;
					}
					if (stt.equals("C") == true) {
						stok = stok + "ฉ";
						continue;
					}
					if (stt.equals("V") == true) {
						stok = stok + "ฮ";
						continue;
					}
					if (stt.equals("B") == true) {
						stok = stok + "ฺ";
						continue;
					}
					if (stt.equals("N") == true) {
						stok = stok + "์";
						continue;
					}
					if (stt.equals("M") == true) {
						stok = stok + "?";
						continue;
					}
					if (stt.equals("<") == true) {
						stok = stok + "ฒ";
						continue;
					}
					if (stt.equals(">") == true) {
						stok = stok + "ฬ";
						continue;
					}
					if (stt.equals("?") == true) {
						stok = stok + "ฦ";
						continue;
					}

					// jjj

					stok = stok + stt;
				}

				return (stok);

			}

		}
		// , normal
		return st;
	}

}

public class DigEventListener2 implements Listener {
	JavaPlugin ac = null;
	Dewminecraft dew = new Dewminecraft();

	// BlockDamageEvent

	public void dewsignthai(Block block, Player player) {

		if (block.getTypeId() != 63 && block.getTypeId() != 68) {
			return;
		}
		switch (player.getItemInHand().getTypeId()) {
		case 279:
		case 258:
		case 271:
		case 275:
		case 286:
			break;
		default:
			return;
		}

		Sign sign = (Sign) block.getState();

		// §

		if (sign.getLine(0).length() > 1) {
			if (sign.getLine(0).substring(0, 1).equalsIgnoreCase("§") == false) {
				sign.setLine(0, dew.engtothai(sign.getLine(0)));
			} else {
				if (sign.getLine(0).length() > 2) {
					sign.setLine(0, dew.engtothai(sign.getLine(0).substring(0, 2)
							+ dew.engtothai(sign.getLine(0).substring(2, sign.getLine(0).length()))));
				}
			}
		}

		// ********************
		// §
		if (sign.getLine(1).length() > 1) {
			if (sign.getLine(1).substring(0, 1).equalsIgnoreCase("§") == false) {
				sign.setLine(1, dew.engtothai(sign.getLine(1)));
			} else {
				if (sign.getLine(0).length() > 2) {
					sign.setLine(1, dew.engtothai(sign.getLine(1).substring(0, 2)
							+ dew.engtothai(sign.getLine(1).substring(2, sign.getLine(1).length()))));
				}
			}
		}
		// ********************
		// ********************
		// §
		if (sign.getLine(2).length() > 1) {
			if (sign.getLine(2).substring(0, 1).equalsIgnoreCase("§") == false) {
				sign.setLine(2, dew.engtothai(sign.getLine(2)));
			} else {
				if (sign.getLine(2).length() > 2) {
					sign.setLine(2, dew.engtothai(sign.getLine(2).substring(0, 2)
							+ dew.engtothai(sign.getLine(2).substring(2, sign.getLine(2).length()))));
				}
			}
		}
		// ********************
		// ********************
		// §
		if (sign.getLine(3).length() > 1) {
			if (sign.getLine(3).substring(0, 1).equalsIgnoreCase("§") == false) {
				sign.setLine(3, dew.engtothai(sign.getLine(3)));
			} else {
				if (sign.getLine(0).length() > 2) {
					sign.setLine(3, dew.engtothai(sign.getLine(3).substring(0, 2)
							+ dew.engtothai(sign.getLine(3).substring(2, sign.getLine(3).length()))));
				}
			}
		}
		// ********************

		sign.update(true);
		player.sendMessage("ptdew&dewdd:updated sign");
	}

	// Chat Event.class

	// BlockDamageEvent
	/*
	 * @EventHandler public void eventja(BlockBreakEvent event) {
	 * 
	 * /* if (event.getPlayer().getItemInHand().getTypeId() == 323) { if
	 * (event.getBlock().getTypeId() == 63 || event.getBlock().getTypeId() == 68
	 * || event.getBlock().getTypeId() == 323) { dewsignthai(event.getBlock(),
	 * event.getPlayer()); event.setCancelled(true); return; } }
	 * 
	 * dewsignthai(event.getBlock(), event.getPlayer());
	 * 
	 * 
	 * }
	 */

	@EventHandler
	public void eventja(AsyncPlayerChatEvent event) {

		// update

		Player player = event.getPlayer();

		String message = event.getMessage();

		if (event.getMessage().equalsIgnoreCase("dewdd help") == true) {
			player.sendMessage(">dewdd language");
			event.setCancelled(true);
		}
		if (event.getMessage().equalsIgnoreCase("dewdd language") == true) {
			player.sendMessage("ปลั๊กอิน language สร้างมาเพื่อให้คนพิมพ์ไทยได้โดยไม่ต้องเปลี่ยนภาษา");
			player.sendMessage(
					"จะอธิบายสั้นๆ   พิมพ์  ; โคลอน ตามด้วยภาษาไทยแบบคนลืมเปลี่ยนภาษาเช่น ;fb; คือ ดิว  (qwerty and kedmanee");
			player.sendMessage(
					"คีย์บอร์ดอีกแบบ พิมพ์  ' นำหน้าตามด้วย ภาษาไทยแบบลืมเปลี่ยนภาษา (dvorak and pattachote)");

			player.sendMessage(
					"ระบบ ตีป้าย ด้วยมือป่าว  จะแปลภาษาในป้าย   ยังอยู่ในช่วงเขียนโปรแกรม  ตอนนี้มันไปตีกับ dewdd main");
			event.setCancelled(true);
		}

		dew.automessage(player);

		event.setMessage(dew.engtothai(event.getMessage()));
	}

	@EventHandler
	public void eventja(BlockBreakEvent event) {
		Block block = event.getBlock();

		if ((block.getTypeId() == 63 || block.getTypeId() == 68)
				&& (event.getPlayer().getItemInHand().getTypeId() == 323)) {
			Sign sign = (Sign) block.getState();
			sign.setLine(0, dew.engtothai(sign.getLine(0)));
			sign.setLine(1, dew.engtothai(sign.getLine(1)));
			sign.setLine(2, dew.engtothai(sign.getLine(2)));
			sign.setLine(3, dew.engtothai(sign.getLine(3)));
			sign.update(true);
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(BlockDamageEvent event) {
		Block block = event.getBlock();

		if ((block.getTypeId() == 63 || block.getTypeId() == 68)
				&& (event.getPlayer().getItemInHand().getTypeId() == 323)) {
			Sign sign = (Sign) block.getState();
			sign.setLine(0, dew.engtothai(sign.getLine(0)));
			sign.setLine(1, dew.engtothai(sign.getLine(1)));
			sign.setLine(2, dew.engtothai(sign.getLine(2)));
			sign.setLine(3, dew.engtothai(sign.getLine(3)));
			sign.update(true);
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void eventja(ServerCommandEvent event) {
		if (event.getCommand().startsWith("say ") == true) {
			event.setCommand("say " + dew.engtothai(event.getCommand().substring(4)));
		}
	}

} // class
