/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddpublicchat;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class DigEventListener2 implements Listener {
	public static Plugin ac = null;

	/*
	 * @EventHandler public void onChat(ChatEvent e) { ProxiedPlayer player =
	 * (ProxiedPlayer) e.getSender();
	 * 
	 * for (ProxiedPlayer cc : ac.getProxy().getPlayers()) { if
	 * (cc.getAddress().getHostString() ==
	 * (e.getSender().getAddress()).getHostName()) { if
	 * (cc.getAddress().getPort() == e.getSender().getAddress().getPort()) {
	 * continue; } } //cc.sendMessage(e.getMessage()); cc.chat(e.getMessage());
	 * System.out.println(cc.getName() + " to him " + e.getMessage()); } }
	 */

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

	@EventHandler
	public void onPlayerChat(ChatEvent event) {
		if (event.getSender() instanceof ProxiedPlayer) {

			if (event.isCommand()) {
				return;
			}

			ProxiedPlayer player = (ProxiedPlayer) event.getSender();
			for (ProxiedPlayer pp : ac.getProxy().getPlayers()) {

				if (player.getServer().getInfo().getName().equalsIgnoreCase(pp.getServer().getInfo().getName())
						|| pp == player) {
					continue;
				}

				String newMessage = "§4";
				newMessage = newMessage + player.getServer().getInfo().getName();
				newMessage = newMessage + "> §6 " + player.getDisplayName();
				newMessage = newMessage + ": §3";
				pp.sendMessage(newMessage + " " + engtothai(event.getMessage()));
			}
		}
	}
} // class