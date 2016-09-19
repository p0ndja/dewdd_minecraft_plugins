/*
 *      Author: patiphat mana-u-krid (dew)
 *      E-Mail: dewtx29@gmail.com
 *      facebook: https://www.facebook.com/dewddminecraft
 */
package dewddsay;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import dewddtran.tr;

public class DigEventListener2 implements Listener {
	class chatc implements Runnable {
		String message = "";
		Boolean canc = false;
		Player player = null;

		@Override
		public void run() {

			String m[] = message.split("\\s+");

			if (m[0].equalsIgnoreCase("vd") == true) {
				if (m.length == 1) {
					player.sendMessage(dprint.r.color("vd = " + Bukkit.getViewDistance()));
					return;
				}

			}

			if (player.isOp() == true) {
				if (m[0].equalsIgnoreCase("givespitem")) {
					for (Player pp : Bukkit.getOnlinePlayers()) {

						pp.getInventory().addItem(player.getItemInHand());
					}

				}
			}

			if (m[0].equalsIgnoreCase("flyspeed")) {
				if (m.length == 1) {
					player.sendMessage(dprint.r.color("flyspeed = " + player.getFlySpeed()));
					return;
				} else if (m.length == 2) {
					player.setFlySpeed(Float.parseFloat(m[1]));
					player.sendMessage(dprint.r.color("flyspeed = " + player.getFlySpeed()));
					return;
				}

			}

			if (m[0].equalsIgnoreCase("cleananimal")) {
				cleananimal cl = new cleananimal(player);

				return;
			}

			if (m[0].equalsIgnoreCase("dewfly") == true || m[0].equalsIgnoreCase("wing") == true
					|| m[0].equalsIgnoreCase("i need fly") == true || m[0].equalsIgnoreCase("123") == true) {

				if (player.getAllowFlight() == true) {
					player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("already_in_fly_mode_not_work")));

					return;
				}
				try {

					int moneyfly = (int) (tr.gettrint("CONFIG_DEWFLY_PRICE"));

					if (Economy.getMoney(player.getName()) < moneyfly) {
						player.sendMessage(dprint.r.color("ptdew&dewdd : DewFly " + tr.gettr("use_money") + moneyfly));

						return;
					} else {
						try {
							Economy.setMoney(player.getName(), Economy.getMoney(player.getName()) - moneyfly);
						} catch (UserDoesNotExistException | NoLoanPermittedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						player.setAllowFlight(true);
						player.sendMessage(dprint.r.color("ptdew&dewdd : " + tr.gettr("canfly_now")));
					}
				} catch (UserDoesNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// dewsuper
			if (m[0].equalsIgnoreCase("dewsuper") == true) {
				if (m.length == 1) {
					// show list of potion
					for (PotionEffectType ene : PotionEffectType.values()) {
						if (ene == null) {
							continue;
						}
						player.sendMessage(dprint.r.color("Potion = " + ene.getId() + "=" + ene.getName()));
					}

					return;
				}

				if (m.length != 4) {
					player.sendMessage(dprint.r.color("dewsuper potioneffecttype dura ampi"));
					return;
				}

				// search enchantname
				PotionEffectType enx = null;
				for (PotionEffectType ene : PotionEffectType.values()) {
					if (ene == null) {
						continue;
					}
					if (ene.getName().toLowerCase().indexOf(m[1]) > -1) {
						player.sendMessage(dprint.r.color(tr.gettr("found_it") + ene.getName()));
						enx = ene;
						break;
					}
				}

				if (enx == null) {
					player.sendMessage(dprint.r.color(tr.gettr("not_found_this_potioneffecttype")));
					return;
				}

				// get dura
				int mone = 0;
				int dura = 0;
				int pora = 0;

				try {
					dura = Integer.parseInt(m[2]);
				} catch (NumberFormatException e) {
					player.sendMessage(dprint.r.color(tr.gettr("error_dura_must_be_integer")));
					return;
				}

				try {
					pora = Integer.parseInt(m[3]);
				} catch (NumberFormatException e) {
					player.sendMessage(dprint.r.color(tr.gettr("error_ampi_must_be_integer")));
					return;
				}

				mone = (int) (((pora * tr.gettrint("CONFIG_PORA_MULTIPLY"))
						+ (dura * tr.gettrint("CONFIG_DURA_MULTIPLY"))) * tr.gettrint("CONFIG_DEWSUPER_MULTIPLY"));

				dura = dura * 100;

				try {
					if (Economy.getMoney(player.getName()) < mone) {
						player.sendMessage(dprint.r.color("ptdew&dewdd: dewsuper need money= dura*(pora*2) = " + mone));

						return;
					}
				} catch (UserDoesNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				player.sendMessage(dprint.r.color("ptdew&dewdd: dewsuper you paid money= dura*(pora*2) = " + mone));

				player.addPotionEffect(PotionEffectType.getById(enx.getId()).createEffect(dura, pora), true);
				player.sendMessage(dprint.r.color("ptdew&dewdd:" + PotionEffectType.getById(enx.getId()).getName()
						+ "| dura = " + dura + ",pora = " + pora));
				canc = true;
				return;
			}
			// dewsuperall
			if (m[0].equalsIgnoreCase("dewsuperall") == true) {
				if (m.length == 1) {
					// show list of potion
					for (PotionEffectType ene : PotionEffectType.values()) {
						if (ene == null) {
							continue;
						}
						player.sendMessage(dprint.r.color("Potion = " + ene.getId() + "=" + ene.getName()));
					}

					return;
				}

				if (m.length != 4) {
					player.sendMessage(dprint.r.color(tr.gettr("dewsuper potioneffecttype dura ampi")));
					return;
				}

				// search enchantname
				PotionEffectType enx = null;
				for (PotionEffectType ene : PotionEffectType.values()) {
					if (ene == null) {
						continue;
					}
					if (ene.getName().toLowerCase().indexOf(m[1]) > -1) {
						player.sendMessage(dprint.r.color("found it " + ene.getName()));
						enx = ene;
						break;
					}
				}

				if (enx == null) {
					player.sendMessage(dprint.r.color("not found this potioneffecttype"));
					return;
				}

				// get dura
				int mone = 0;
				int dura = 0;
				int pora = 0;

				try {
					dura = Integer.parseInt(m[2]);
				} catch (NumberFormatException e) {
					player.sendMessage(dprint.r.color("error dura  must be integer"));
					return;
				}

				try {
					pora = Integer.parseInt(m[3]);
				} catch (NumberFormatException e) {
					player.sendMessage(dprint.r.color("error ampi  must be integer"));
					return;
				}

				mone = (int) (((pora * tr.gettrint("CONFIG_PORA_MULTIPLY"))
						+ (dura * tr.gettrint("CONFIG_DURA_MULTIPLY"))) * tr.gettrint("CONFIG_DEWSUPER_MULTIPLY"));

				dura = dura * 100;

				try {
					if (Economy.getMoney(player.getName()) < mone) {
						player.sendMessage(dprint.r.color("ptdew&dewdd: dewsuper need money= dura*(pora*2) = " + mone));

						return;
					}
				} catch (UserDoesNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				player.sendMessage(dprint.r.color("ptdew&dewdd: dewsuper you paid money= dura*(pora*2) = " + mone));

				player.addPotionEffect(PotionEffectType.getById(enx.getId()).createEffect(dura, pora), true);
				player.sendMessage(dprint.r.color("ptdew&dewdd:" + PotionEffectType.getById(enx.getId()).getName()
						+ "| dura = " + dura + ",pora = " + pora));
				canc = true;

				for (Player pd : Bukkit.getOnlinePlayers()) {
					pd.addPotionEffect(PotionEffectType.getById(enx.getId()).createEffect(dura, pora), true);
					pd.sendMessage(dprint.r.color("ptdew&dewdd:" + PotionEffectType.getById(enx.getId()).getName()
							+ "| dura = " + dura + ",pora = " + pora));
				}
				dprint.r.printA("ptdew&dewdd: '" + player.getName() + "' ได้ซื้อยาพิเศษให้คุณ '"
						+ PotionEffectType.getById(enx.getId()).getName() + "' , ระยะเวลาของยา = " + dura
						+ ", ความรุนแรงของยา = " + pora + " , ใช้เงินไป = " + mone);

				canc = true;
				return;
			}

			if (m[0].equalsIgnoreCase("dewdd help") == true) {
				player.sendMessage(dprint.r.color(">dewdd say"));
				canc = true;
			}

			if (m[0].equalsIgnoreCase("dewdd say") == true) {
				player.sendMessage(
						dprint.r.color("ปลั๊กอิน dewdd say เป็นที่รวมของ ระบบ คำพูด เพื่อซื้อพลังพิเศษต่างๆ"));
				player.sendMessage(dprint.r.color("แค่พิมพ์คำพูดออกมา และมีตังค์ :)"));
				player.sendMessage(dprint.r.color(".dewbuy  ซื้อโหมดการบิน"));
				player.sendMessage(dprint.r.color(".555  บินได"));
				player.sendMessage(dprint.r.color(".255  ไปชั้น 255"));
				player.sendMessage(dprint.r.color(".unloadchunk   ปิดพื้นที่ทั้งหมดที่ไม่มีคนอยู่ใกล้"));
				player.sendMessage(dprint.r.color(".loadchunk"));
				player.sendMessage(dprint.r
						.color(".cleardrop  ล้างของที่ตกอยู่ในแมพ ถ้าเป็นแมพ world มันจะโดนวาปไปที่ /warp newgift"));
				player.sendMessage(dprint.r.color(".drops   แสดงปริมาณของที่ตกอยูในเซิฟ"));
				player.sendMessage(dprint.r.color(".dg   ดูดของที่ตกอยู่ในแมพมารวมกัน"));
				player.sendMessage(dprint.r.color(".on  หรือ .yes   ตาไม่มืด"));
				player.sendMessage(dprint.r.color(".no หรือ .off   ตาบอด"));
				player.sendMessage(dprint.r.color(".digspeed   อัพของให้ขุดเร็ว"));
				player.sendMessage(dprint.r.color(".id   บอกหมายเลขสิ่งของในมือ"));

				player.sendMessage(dprint.r.color(".water  ซื้อยาหายใจใต้น้ำ"));
				player.sendMessage(dprint.r.color(".:)  แบ่งเงินให้คนในเซิฟคนละ 1%"));
				player.sendMessage(dprint.r.color(".255  ไปชั้น 255"));
				player.sendMessage(dprint.r.color(".night  ซื้อยามองกลางคืน"));
				player.sendMessage(dprint.r.color(".head  ใส่หัว จำนวนของในช่องที่  1"));
				player.sendMessage(dprint.r.color(".save   เซิฟข้อมูล"));

				player.sendMessage(dprint.r.color(".bye bye   ลาก่อน"));
				player.sendMessage(dprint.r.color(".harm   ทำให้ตัวเองเจ็บ"));
				player.sendMessage(dprint.r.color(".lag   ทำให้ช้า"));
				player.sendMessage(dprint.r.color(".antifire   ซื้อยากันไฟ"));
				player.sendMessage(dprint.r.color(".anti   เกราะป้องกัน"));

				player.sendMessage(dprint.r.color(".best  รักษาตัวเอง"));
				player.sendMessage(dprint.r.color(".dig   ขุดเร็ว"));
				player.sendMessage(dprint.r.color(".cry   อ่อนแอ"));
				player.sendMessage(dprint.r.color(".jump   กระโดดสูง"));
				player.sendMessage(dprint.r.color(".fast   เร็ว"));

				player.sendMessage(dprint.r.color(".shy  อายจังเลย"));
				player.sendMessage(dprint.r.color(".wak   โหดแล้วโว็ยยย"));
				player.sendMessage(dprint.r.color(".what   ที่นี่ที่หนายยย"));
				player.sendMessage(dprint.r.color(".wow   โอ้ เย้"));

				player.sendMessage(dprint.r.color(".eiei   ดูดมอนมารวมกัน"));
				player.sendMessage(dprint.r.color(".clearmon  ล้างมอน"));

				canc = true;
			}

			// 555

			if (m[0].equalsIgnoreCase("xD") == true) {
				player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(30, 1));

			}

			if (m[0].equalsIgnoreCase("555") == true) {
				player.setAllowFlight(false);
				player.setGameMode(GameMode.SURVIVAL);

				Location Locaa = player.getLocation();
				Locaa.setY(255);

				player.teleport(Locaa);

				dprint.r.printA("ptdew&dewdd: '" + player.getName() + "' pom bin dai k3ub!!b!b!b!");

				return;
			}
			try {
				if (m[0].equalsIgnoreCase("dewfix") == true || m[0].equalsIgnoreCase("dew_fix") == true) {
					dprint.r.printC("ptdew&dewdd: Starting dewfix '" + player.getName() + "'...");
					dprint.r.printA("ptdew&dewdd: dewfix เริ่มต้นการซ่อมของในตัว '" + player.getName() + "'...");

					fixtool(player);

					dprint.r.printA("ptdew&dewdd: dewfix ช่อมของในตัว '" + player.getName() + "' เสร็จแล้ว : )");
					dprint.r.printC("ptdew&dewdd: finished dewfig '" + player.getName() + "' เสร็จแล้ว : )");
				}

				if (m[0].equalsIgnoreCase("clearmon") == true) {

					for (Entity ent : player.getWorld().getEntities()) {
						if (ent == null)
							continue;

						if (!(ent instanceof Creature))
							continue;

						LivingEntity en = (LivingEntity) ent;
						if (en.getCustomName() != null)
							continue;

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
					player.sendMessage(dprint.r.color("DewDD:ClearMon done"));
					canc = true;
					return;

				}

				if (m[0].equalsIgnoreCase("eiei") == true) {
					canc = true;

					int eieiprice = (int) tr.gettrint("CONFIG_EIEI_PRICE");
					if (Economy.getMoney(player.getName()) <= eieiprice) {
						player.sendMessage(dprint.r.color("eiei use money eieiprice"));
						return;
					}
					Economy.subtract(player.getName(), eieiprice);

					for (Entity ent : player.getWorld().getEntities()) {
						boolean okk = false;

						if (ent.getType() == org.bukkit.entity.EntityType.CREEPER) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SKELETON) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ZOMBIE) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SPIDER) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.CAVE_SPIDER) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.BLAZE) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ENDERMAN) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.BAT) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SLIME) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.GHAST) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ENDERMAN) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.GIANT) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.PIG_ZOMBIE) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.WITCH) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.MAGMA_CUBE) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.WITHER) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.ENDER_DRAGON) {
							okk = true;
						}
						if (ent.getType() == org.bukkit.entity.EntityType.SILVERFISH) {
							okk = true;
						}

						if (okk == true) {
							Location loc = player.getLocation();
							loc.setY(loc.getY() + 3);
							loc.setX(loc.getX() + 3);
							ent.teleport(loc);
						}
					}
					canc = true;
				}

				// ลาก่อน
				if (m[0].equalsIgnoreCase("bye bye") == true || m[0].equalsIgnoreCase("good bye") == true
						|| m[0].equalsIgnoreCase("see you") == true) {
					player.setHealth(0);

					dprint.r.printA("ptdew&dewdd: " + player.getName() + " อยากตาย ก็เลยตาย");

					return;
				}

				// harm
				if (m[0].equalsIgnoreCase("harm") == true || m[0].equalsIgnoreCase("เจ็บ") == true
						|| m[0].equalsIgnoreCase("อะไรกัด") == true) {

					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r.color("ptdew&dewdd: harm ต้องใช้เงิน  150"));
						player.sendMessage(
								dprint.r.color("ptdew&dewdd: harm use money " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.HARM.createEffect(randomGenerator.nextInt(5000) + 1000,
								randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: harm"));
					}
					return;
				}

				// ไม่ต้องรีบ
				if (m[0].equalsIgnoreCase("net kak") == true || m[0].equalsIgnoreCase("slow") == true
						|| m[0].equalsIgnoreCase("lag") == true || m[0].equalsIgnoreCase("lag") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(
								dprint.r.color("ptdew&dewdd: ต้องใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.SLOW.createEffect(randomGenerator.nextInt(5000) + 1000,
								randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: เออนั่นดิ"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: m1ai lag llaw kub"));

					}
					return;
				}

				// ไฟไม่ไหม้
				if (m[0].equalsIgnoreCase("antifire") == true || m[0].equalsIgnoreCase("antifire") == true
						|| m[0].equalsIgnoreCase("nofire") == true || m[0].equalsIgnoreCase("cool") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r.color("ptdew&dewdd: antifire ต้องใช้เงิน  150"));
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: antifire use money " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.FIRE_RESISTANCE
								.createEffect(randomGenerator.nextInt(5000) + 1000, randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: เย็นจังเลย"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: so cool!"));
					}
					return;
				}

				// ป้องกัน
				if (m[0].equalsIgnoreCase("die hard") == true || m[0].equalsIgnoreCase("defend") == true
						|| m[0].equalsIgnoreCase("anti") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r.color("ptdew&dewdd: anti ต้องใช้เงิน  150"));
						player.sendMessage(
								dprint.r.color("ptdew&dewdd: anti use money " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE
								.createEffect(randomGenerator.nextInt(5000) + 1000, randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: ตายยาก"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: die hard"));
					}
					return;
				}

				// สบายดี
				if (m[0].equalsIgnoreCase("best") == true || m[0].equalsIgnoreCase("am fine") == true
						|| m[0].equalsIgnoreCase("feel good") == true || m[0].equalsIgnoreCase("i am fine") == true
						|| m[0].equalsIgnoreCase("i'm fine") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: am fine ต้องใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.HEAL.createEffect(randomGenerator.nextInt(5000) + 1000,
								randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: แข็งแรงซินะ"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: oh good healty"));
					}
					return;
				}

				// ขุดเร็ว
				if (m[0].equalsIgnoreCase("digger") == true || m[0].equalsIgnoreCase("fast dig") == true
						|| m[0].equalsIgnoreCase("digdigdig") == true || m[0].equalsIgnoreCase("dig") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: fast dig ต้องใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.FAST_DIGGING
								.createEffect(randomGenerator.nextInt(5000) + 1000, randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: ขุดๆๆ"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: digdigdig"));
					}
					return;
				}

				// อ่อนแอ
				if (m[0].equalsIgnoreCase("cry") == true || m[0].equalsIgnoreCase("weakness") == true
						|| m[0].equalsIgnoreCase("weak") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: weakness ต้องใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.WEAKNESS
								.createEffect(randomGenerator.nextInt(5000) + 1000, randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: แงๆๆ"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: weakness..."));
					}
					return;
				}

				// กระโดดสูง
				if (m[0].equalsIgnoreCase("jumper") == true || m[0].equalsIgnoreCase("jump") == true
						|| m[0].equalsIgnoreCase("high jump") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: jump ต้องใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.JUMP.createEffect(randomGenerator.nextInt(5000) + 1000,
								randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: ว้าว  โครตสูง"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: Wow jing jo"));
					}
					return;
				}

				// เร็วจัง
				if (m[0].equalsIgnoreCase("very fast") == true || m[0].equalsIgnoreCase("fast") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: fast ต้องใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.SPEED.createEffect(randomGenerator.nextInt(5000) + 1000,
								randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: โคตรเร็วอ่ะ"));
					}
					return;
				}

				// อยากหายไป
				if (m[0].equalsIgnoreCase("shy") == true || m[0].equalsIgnoreCase("invisible") == true
						|| m[0].equalsIgnoreCase("ghost") == true || m[0].equalsIgnoreCase("invi") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: invi ต้องใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.INVISIBILITY
								.createEffect(randomGenerator.nextInt(5000) + 1000, randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: อ้าว อยู่ไหนอ่ะ"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: hey where are you?"));
					}
					return;
				}

				// โหดแล้วนะ
				if (m[0].equalsIgnoreCase("angry") == true || m[0].equalsIgnoreCase("attack") == true
						|| m[0].equalsIgnoreCase("wak") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: attack ต้องใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE
								.createEffect(randomGenerator.nextInt(5000) + 1000, randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: น่ากลัวจัง"));
					}
					return;
				}
				// งง
				if (m[0].equalsIgnoreCase("what") == true || m[0].equalsIgnoreCase("ngng") == true
						|| m[0].equalsIgnoreCase("confuse") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: ngng ต้องใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.CONFUSION
								.createEffect(randomGenerator.nextInt(3000) + 1000, randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: มืนเลย"));
					}
					return;
				}

				// wow
				if (m[0].equalsIgnoreCase("wow") == true || m[0].equalsIgnoreCase("ว้าว") == true) {

					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(
								dprint.r.color("ptdew&dewdd: wow ใช้เงิน " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.getWorld().strikeLightningEffect(player.getLocation());
						player.sendMessage(dprint.r.color("ptdew&dewdd: oh yea"));
					}
					return;
				}

				// ********************************************
				// หายใจใต้น้ำ
				if (m[0].equalsIgnoreCase("water") == true || m[0].equalsIgnoreCase("underwater") == true
						|| m[0].equalsIgnoreCase("fish") == true) {
					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r.color("ptdew&dewdd: หายใจใต้น้ำ ต้องใช้เงิน  150"));
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: underwater use money " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.WATER_BREATHING
								.createEffect(randomGenerator.nextInt(5000) + 1000, randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: เป็นปลาหรอ"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: Hey fish!"));
					}
					return;
				}

				// ใจดี
				if (m[0].equalsIgnoreCase("good man") == true || m[0].equalsIgnoreCase(":)") == true
						|| m[0].equalsIgnoreCase("goodman") == true || m[0].equalsIgnoreCase(": )") == true
						|| m[0].equalsIgnoreCase("good") == true) {

					for (Player pl : Bukkit.getOnlinePlayers()) {
						if (player.getName().equalsIgnoreCase(pl.getName()) == true) {
							continue;
						}

						double nowmoney = Economy.getMoney(player.getName());
						if (nowmoney > 100000) {
							player.sendMessage(
									dprint.r.color("ptdew&dewdd: ห้ามคนมีเงินมากกว่า 100,000 แบ่งเงินให้ผู้อื่น"));
							player.sendMessage(dprint.r
									.color("ptdew&dewdd: If you have money > 100,000 can't share to another player"));
							return;
						}
						nowmoney = (nowmoney * 1) / 100;

						Economy.setMoney(pl.getName(), Economy.getMoney(pl.getName()) + nowmoney);
						Economy.setMoney(player.getName(), Economy.getMoney(player.getName()) - nowmoney);

						pl.sendMessage(
								dprint.r.color("ptdew&dewdd: คุณได้เงิน " + nowmoney + " จาก " + player.getName()));
						player.sendMessage(dprint.r
								.color("ptdew&dewdd: คุณแบ่งเงินให้  " + pl.getName() + " จำนวนเงิน " + nowmoney));
					}
					return;
				}

				// มองไม่เห็น
				if (m[0].equalsIgnoreCase("night") == true || m[0].equalsIgnoreCase("fear") == true
						|| m[0].equalsIgnoreCase("dark") == true || m[0].equalsIgnoreCase("torch") == true) {

					if (Economy.getMoney(player.getName()) < tr.gettrint("CONFIG_SAY_POTION_150")) {
						player.sendMessage(dprint.r.color("ptdew&dewdd: dark ต้องใช้เงิน  150"));
						player.sendMessage(
								dprint.r.color("ptdew&dewdd: dark use money " + tr.gettrint("CONFIG_SAY_POTION_150")));
						return;
					} else {
						Economy.setMoney(player.getName(),
								Economy.getMoney(player.getName()) - tr.gettrint("CONFIG_SAY_POTION_150"));
						player.addPotionEffect(PotionEffectType.NIGHT_VISION
								.createEffect(randomGenerator.nextInt(5000) + 1000, randomGenerator.nextInt(5)), true);
						player.sendMessage(dprint.r.color("ptdew&dewdd: เห็นแล้วนะ"));
						player.sendMessage(dprint.r.color("ptdew&dewdd: you can see around at night"));
					}
					return;
				}

				if (m[0].equalsIgnoreCase("ban me") == true || m[0].equalsIgnoreCase("ban me please") == true
						|| m[0].equalsIgnoreCase("kuy") || m[0].equalsIgnoreCase("fuck")
						|| m[0].equalsIgnoreCase("bad server")) {
					if (player.getInventory().getItem(0) == null) {
						return;
					}

					player.setBanned(true);
					player.kickPlayer("bye bye you don ban llaw na ja");

					dprint.r.printA("ptdew&dewdd: Ban '" + player.getName() + " bye byee");
					dprint.r.printA("ptdew&dewdd: แบน '" + player.getName()
							+ " เรียบร้อยแล้ว ตามความอยากของท่านผู้นั้น เราจัดให้ 555");

					return;
				}

				// save
				if (m[0].equalsIgnoreCase("save") == true) {
					for (World world : Bukkit.getWorlds()) {
						world.save();
						for (Player pla : world.getPlayers()) {
							pla.saveData();

							for (World world2 : Bukkit.getWorlds()) {
								for (Player pla2 : world2.getPlayers()) {
									pla2.sendMessage(dprint.r.color("ptdew&dewdd: saving '" + pla.getName() + "'"
											+ " in '" + world2.getName() + "'"));
								}
							}

						}
					}
					player.sendMessage(dprint.r.color("ptdew&dewdd: Saved World"));
					canc = true;
					return;
				}

				if (m[0].equalsIgnoreCase("item") == true || m[0].equalsIgnoreCase("id") == true) {

					if (player.getItemInHand() == null) {
						player.sendMessage(dprint.r.color("ptdew&dewdd : Item = null"));
						return;
					}

					player.sendMessage(dprint.r.color("ptdew&dewdd : Item = " + player.getItemInHand().getType().name()
							+ ":" + player.getItemInHand().getData().getData()));
					canc = true;
					return;
				}

				if (m[0].equalsIgnoreCase("255") == true) {
					Location loc = player.getLocation();
					loc.setY(255);
					player.teleport(loc);
					player.sendMessage(dprint.r.color("ptdew&dewdd: goto 255!"));
					canc = true;
				}

				if (m[0].equalsIgnoreCase("off") == true || m[0].equalsIgnoreCase("no") == true) {
					player.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(60000, 1), true);
					dprint.r.printA("ptdew&dewdd: โอมมม เพี่้ยง '" + player.getName() + "' จงตาบอด...");
					canc = true;
				}

				if (m[0].equalsIgnoreCase("on") == true || m[0].equalsIgnoreCase("yes") == true) {
					player.removePotionEffect(PotionEffectType.BLINDNESS);
					dprint.r.printA("ptdew&dewdd: คำสาปตาบอดของ '" + player.getName() + "' ได้เสื่อมลงแล้ว...");
					canc = true;
				}

				if (m[0].equalsIgnoreCase("dig_speed") == true) {
					if (player.getItemInHand() == null) {
						player.sendMessage(
								dprint.r.color("ptdew&dewdd: your item in hand is empty... can't enchant for you"));
						return;
					}
					canc = true;
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
						// dew.dprint.r.printA("EN" +
						// player.getItemInHand().getEnchantmentLevel(Enchantment.DIG_SPEED));
						if (player.getItemInHand().getEnchantmentLevel(Enchantment.DIG_SPEED) == 0) {
							player.getItemInHand().addUnsafeEnchantment(Enchantment.DIG_SPEED, 100);
							player.sendMessage(dprint.r.color("ptdew&dewdd: เพิ่มพลังการขุดเร็วให้กับ '"
									+ player.getItemInHand().getType().name() + "' :D"));
						}
						return;
					default:
						player.sendMessage(
								dprint.r.color("ptdew&dewdd: อัพเกรดให้แต่ ขวาน ที่ขุดแร่ และที่ขุดดินครับ"));
						return;
					}
				}

				if (m[0].equalsIgnoreCase("dewgetalldrop") == true || m[0].equalsIgnoreCase("dg") == true
						|| m[0].equalsIgnoreCase("dgad") == true) {
					canc = true;
					player.sendMessage(dprint.r.color("ptdew&dewdd: start getalldrop"));

					for (Entity ent : player.getWorld().getEntities()) {
						if (ent.getType() == EntityType.DROPPED_ITEM || ent.getType() == EntityType.EXPERIENCE_ORB) {
							ent.teleport(player.getLocation());
						}
					}
				}

				if (m[0].equalsIgnoreCase("drops") == true) {
					canc = true;
					int amo = 0;
					for (World ww : Bukkit.getWorlds()) {
						for (Entity amoq : ww.getEntities()) {
							amoq.getType();
							if (amoq.getType() == EntityType.DROPPED_ITEM) {
								amo = amo + 1;
							}

						}
						dprint.r.printAll("ptdew&dewdd: " + ww.getName() + ">" + amo);
					}

					dprint.r.printAll("ptdew&dewdd: Amount of Dropped Item in this server = " + amo);
					dprint.r.printA("ptdew&dewdd: จำนวนของที่ตกอยู่ในแมพมีทั้งหมด = " + amo + " ชิ้น");

					return;
				}

				if (m[0].equalsIgnoreCase("unloadchunk") == true) {
					dprint.r.printAll("ptdew&dewdd: unloading all chunk with no player");
					for (World wx : Bukkit.getWorlds()) {

						boolean playernear = false;

						for (Chunk cj : wx.getLoadedChunks()) {
							playernear = false;

							for (Player pj : wx.getPlayers()) {
								if (Math.pow((Math.pow(((cj.getX() * 16) - pj.getLocation().getBlockX()), 2))
										+ (Math.pow(((cj.getZ() * 16) - pj.getLocation().getBlockZ()), 2)), 0.5)

								<= 50) {
									playernear = true;
									break;
								}

							}

							if (playernear == false) {
								cj.unload(true);
								dprint.r.printAll("ptdew&dewdd: unloaded chunk at (" + cj.getX() * 16 + ","
										+ cj.getZ() * 16 + ")");
							}

						}
					}
					canc = true;
					return;
				}

				if (m[0].equalsIgnoreCase("loadchunk") == true) {
					dprint.r.printAll("ptdew&dewdd: loading all chunk neary you");
					World wx = player.getWorld();

					for (int x = -200; x < 200; x += 16) {

						for (int z = -200; z < 200; z += 16) {

							wx.loadChunk((int) ((x + player.getLocation().getX()) / 16),
									(int) ((z + player.getLocation().getZ()) / 16), true);
							dprint.r.printAll("ptdew&dewdd: loaded chunk at (" + (x + player.getLocation().getX()) + ","
									+ (z + player.getLocation().getZ()) + ")");
						}

					}

					canc = true;
					return;
				}

			} catch (UserDoesNotExistException | NoLoanPermittedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class cleananimal implements Runnable {
		private Player player;

		public cleananimal(Player player) {
			this.player = player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ac, this, 20);
		}

		@Override
		public void run() {
			for (Entity en : player.getWorld().getEntities()) {

				if (!(en instanceof Ageable))
					continue;

				Ageable age = (Ageable) en;

				String a1 = tr.gettr("CONFIG_MAX_ANIMAL_" + age.getType().name());
				a1 = a1.trim();
				String a2 = tr.gettr("CONFIG_MAX_ANIMAL_amount" + age.getType().name());
				a2 = a2.trim();
			}

			int count = 0;
			for (Entity en : player.getWorld().getEntities()) {

				if (!(en instanceof Ageable))
					continue;

				Ageable age = (Ageable) en;

				String a1 = tr.gettr("CONFIG_MAX_ANIMAL_" + age.getType().name());
				a1 = a1.trim();
				String a2 = tr.gettr("CONFIG_MAX_ANIMAL_amount" + age.getType().name());
				a2 = a2.trim();

				int anymal = Integer.parseInt(a1);
				int anyamo = Integer.parseInt(a2);

				// count neary mon
				count = 0;
				for (Entity en2 : player.getWorld().getEntities()) {

					if (!(en2 instanceof Ageable))
						continue;

					Ageable age2 = (Ageable) en2;

					if (age2.getType() != age.getType())
						continue;

					if (age2.getLocation().distance(age.getLocation()) > anyamo) {
						continue;
					}

					count++;
					continue;

					// in radius

				}

				if (count > anymal) {
					dprint.r.printAll(tr.gettr("removed_animal_this_type") + age.getType() + "_at_"
							+ age.getLocation().getBlockX() + "," + age.getLocation().getBlockY() + ","
							+ age.getLocation().getBlockZ());
					age.remove();
					count = 0;

					cleananimal cc = new cleananimal(player);
					return;
				}

			}
		}
	}

	class serverc implements Runnable {
		String message = "";

		@Override
		public void run() {

			if (message.equalsIgnoreCase("loadallchunk") == true) {

				World world = Bukkit.getWorld("world");

				for (int x = -2000; x <= 2000; x++) {
					for (int z = -2000; z <= 2000; z++) {
						world.loadChunk(x / 16, z / 16);
						dprint.r.printAll("load all chunk (" + x + "," + z + ")");

					}

				}

				return;
			}

			if (message.equalsIgnoreCase("clearmon") == true || message.equalsIgnoreCase("cm") == true) {

				for (Entity ent : Bukkit.getWorld("world").getEntities()) {
					if (!(ent instanceof LivingEntity))
						continue;
					LivingEntity en = (LivingEntity) ent;
					if (!en.getCustomName().equalsIgnoreCase(""))
						continue;

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
				dprint.r.printC("DewDD:ClearMon done");

				return;

			}

			if (message.equalsIgnoreCase("gc") == true) {
				dprint.r.printAll("ptdew&dewdd: starting gc...");
				System.gc();
				dprint.r.printAll("ptdew&dewdd: finished gc...");
				return;
			}

			if (message.equalsIgnoreCase("chunknow") == true) {
				for (Player pp : Bukkit.getOnlinePlayers()) {
					pp.sendMessage(dprint.r.color("chucknow = " + pp.getWorld().getLoadedChunks().length));
				}

				return;
			}

			if (message.equalsIgnoreCase("unloadchunk") == true) {
				dprint.r.printAll("ptdew&dewdd: unloading all chunk with no player");
				for (World wx : Bukkit.getWorlds()) {

					boolean playernear = false;

					for (Chunk cj : wx.getLoadedChunks()) {
						playernear = false;

						for (Player pj : wx.getPlayers()) {
							if (Math.pow((Math.pow((cj.getX() * 16 - pj.getLocation().getBlockX()), 2))
									+ (Math.pow((cj.getZ() * 16 - pj.getLocation().getBlockZ()), 2)), 0.5)

							<= 50) {
								playernear = true;
								break;
							}

						}

						if (playernear == false) {
							cj.unload(true);
							dprint.r.printAll(
									"ptdew&dewdd: unloaded chunk at (" + cj.getX() * 16 + "," + cj.getZ() * 16 + ")");
						}

					}
				}

				return;
			}

			if (message.equalsIgnoreCase("cleardrop") == true) {

				for (Entity ent : Bukkit.getWorld("world").getEntities()) {

					if (ent.getType() == org.bukkit.entity.EntityType.DROPPED_ITEM) {
						ent.remove();
					}

					if (ent.getType() == org.bukkit.entity.EntityType.FIREBALL) {
						ent.remove();
					}

					if (ent.getType() == org.bukkit.entity.EntityType.ARROW) {
						ent.remove();
					}

					if (ent.getType() == org.bukkit.entity.EntityType.EXPERIENCE_ORB) {
						ent.remove();
					}

					if (ent.getType() == org.bukkit.entity.EntityType.SMALL_FIREBALL) {
						ent.remove();
					}

				}
				dprint.r.printC("ptdew&dewdd:ClearDroped");

				return;

			}

			if (message.startsWith("qdewd ") == true) {
				int j = message.indexOf(" ");

				if (j == -1) {

					return;
				}

				int k = message.indexOf(" ", j + 1);
				if (k == -1) {

					return;
				}

				String pl = message.substring(j + 1, k - 1);
				String com = message.substring(k + 1);

				dprint.r.printC("pl = '" + pl + "'");

				if (Bukkit.getPlayer(pl) == null) {

					return;
				}

				dprint.r.printC("com = '" + com + "'");

				for (Plugin pl3 : Bukkit.getPluginManager().getPlugins()) { // for
					pl3.getServer().dispatchCommand(Bukkit.getPlayer(pl), com);
				}
				// Bukkit.dispatchCommand(Bukkit.getPlayer(pl),com);

				return;
			}

			if (message.startsWith("qdewd2 ") == true) {
				int j = message.indexOf(" ");

				if (j == -1) {

					return;
				}

				int k = message.indexOf(" ", j + 1);
				if (k == -1) {

					return;
				}

				String pl = message.substring(j + 1, k - 1);
				String com = message.substring(k + 1);

				dprint.r.printC("pl = '" + pl + "'");

				if (Bukkit.getPlayer(pl) == null) {

					return;
				}

				dprint.r.printC("com = '" + com + "'");

				Bukkit.getPlayer(pl).chat(com);

				return;
			}
		}
	}

	JavaPlugin ac = null;

	Random randomGenerator = new Random();

	String phack = "dewdd.say.hack";

	String peveryone = "dewdd.say.everyone";

	@EventHandler
	// synchronized
	public void eventja(AsyncPlayerChatEvent e) throws UserDoesNotExistException, NoLoanPermittedException {

		if (!tr.isrunworld(ac.getName(), e.getPlayer().getWorld().getName())) {
			return;
		}

		String message = e.getMessage();
		message = (message.replace("แอดมิน", "Dew"));
		message = (message.replace("แอด", "Dew"));
		e.setMessage(message);

		chatc ab = new chatc();
		ab.message = message;
		ab.player = e.getPlayer();

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);
		e.setMessage(ab.message);
		e.setCancelled(ab.canc);

	} // chat

	@EventHandler(priority = EventPriority.HIGHEST)
	public void eventja(PlayerCommandPreprocessEvent event) {

		if (!tr.isrunworld(ac.getName(), event.getPlayer().getWorld().getName())) {
			return;
		}

		String message = event.getMessage();
		Player player = event.getPlayer();

		chatc ab = new chatc();
		ab.message = message.substring(1);
		ab.player = event.getPlayer();

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);

		if (player.isOp() == true || player.hasPermission(peveryone) == true) {

			if (message.indexOf("@a") > 1) { // all player
				String aa = message;

				for (Player cc : Bukkit.getOnlinePlayers()) {
					if (cc.getName().equalsIgnoreCase(player.getName()) == true) {
						continue;
					}

					aa = message;
					aa = message.replace("@a", cc.getName());

					Bukkit.dispatchCommand(player, aa.substring(1));

					dprint.r.printC("'" + aa.substring(1) + "'");

				}

			}

			if (message.indexOf("@f") > 1) { // all player
				String aa = message;

				for (Player cc : Bukkit.getOnlinePlayers()) {
					if (cc.getName().equalsIgnoreCase(player.getName()) == true) {
						continue;
					}

					aa = message;
					aa = message.replace("@f", cc.getName());

					Bukkit.dispatchCommand(cc, aa.substring(1));

					dprint.r.printC("'" + aa.substring(1) + "'");

				}

			}

			if (message.indexOf("@c") > 1) { // all player
				String aa = message;

				for (Player cc : Bukkit.getOnlinePlayers()) {
					if (cc.getName().equalsIgnoreCase(player.getName()) == true) {
						continue;
					}

					aa = message;
					aa = message.replace("@c", "");

					cc.chat(aa.substring(1));

					dprint.r.printC("'" + aa.substring(1) + "'");

				}

			}

			if (message.indexOf("@w") > 1) {
				String aa = message;

				for (World cc : Bukkit.getWorlds()) {
					if (cc.getName().equalsIgnoreCase(player.getName()) == true) {
						continue;
					}

					aa = message;
					aa = message.replace("@w", cc.getName());

					dprint.r.printC("'" + aa.substring(1) + "' '");

				}
			}

		}

		if (message.startsWith("/qdewd ") == true) {
			if (player.hasPermission(phack) == false) {
				player.sendMessage(dprint.r.color("you can't hack player command..."));
				return;
			}
			int j = message.indexOf(" ");

			if (j == -1) {
				player.sendMessage(dprint.r.color("not wrong player"));
				return;
			}

			int k = message.indexOf(" ", j + 1);
			if (k == -1) {
				player.sendMessage(dprint.r.color("not wrong command"));
				return;
			}

			String pl = message.substring(j + 1, k - 1);
			String com = message.substring(k + 1);

			dprint.r.printC("pl = '" + pl + "'");

			if (Bukkit.getPlayer(pl) == null) {
				player.sendMessage(dprint.r.color("not found player"));
				return;
			}

			dprint.r.printC("com = '" + com + "'");

			for (Plugin pl3 : Bukkit.getPluginManager().getPlugins()) { // for
				pl3.getServer().dispatchCommand(Bukkit.getPlayer(pl), com);
			}
			// Bukkit.dispatchCommand(Bukkit.getPlayer(pl),com);
			event.setCancelled(true);
			return;
		}

		if (message.startsWith("/qdewd2 ") == true) {
			if (player.hasPermission(phack) == false) {
				player.sendMessage(dprint.r.color("you can't hack player chat..."));
				return;
			}

			int j = message.indexOf(" ");

			if (j == -1) {
				player.sendMessage(dprint.r.color("not wrong player"));
				return;
			}

			int k = message.indexOf(" ", j + 1);
			if (k == -1) {
				player.sendMessage(dprint.r.color("not wrong command"));
				return;
			}

			String pl = message.substring(j + 1, k - 1);
			String com = message.substring(k + 1);

			dprint.r.printC("pl = '" + pl + "'");

			if (Bukkit.getPlayer(pl) == null) {
				player.sendMessage(dprint.r.color("not found player"));
				return;
			}

			dprint.r.printC("com = '" + com + "'");

			Bukkit.getPlayer(pl).chat(com);

			event.setCancelled(true);
			return;
		}

	}

	@EventHandler
	public void eventja(ServerCommandEvent event) {
		String message = event.getCommand();

		serverc ab = new serverc();
		ab.message = message;
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ac, ab);
	}

	// fixtool
	public void fixtool(Player player) throws UserDoesNotExistException, NoLoanPermittedException {
		// fixtool

		for (ItemStack itm : player.getInventory().getContents()) {
			if (itm == null) {
				continue;
			}

			fixtoolsub(player, itm);
		}

		for (ItemStack itm : player.getInventory().getArmorContents()) {
			if (itm == null) {
				continue;
			}

			fixtoolsub(player, itm);
		}
		// fixtool
	}

	// fixtool
	// fixtool itemstack and player
	public void fixtoolsub(Player player, ItemStack itm) throws UserDoesNotExistException, NoLoanPermittedException {
		short dula = itm.getDurability();
		short dulamax = itm.getType().getMaxDurability();
		double money = Economy.getMoney(player.getName());
		double money2 = 0;

		money2 = ((dulamax - (dulamax - dula)) * tr.gettrint("CONFIG_FIXTOOL_MULTIPLY"));

		if (dulamax == 0) {
			return;
		}

		// player.sendMessage"ptdew&dewdd: คุณเป็น vip เราคิดเงินค่าซ่อมของลดลง
		// 50% = " // + money2));

		if (money < money2) {
			player.sendMessage(dprint.r.color("ptdew&dewdd:You don't have money enough for repair "
					+ itm.getType().name() + " (much use money=" + money2 + ")"));

		} else {

			player.sendMessage(dprint.r.color("***********"));
			dprint.r.printC("***********");

			player.sendMessage(dprint.r.color("ptdew&dewdd: fixtoolmode1 ItemName:" + itm.getType().name()));
			dprint.r.printC("ptdew&dewdd: fixtoolmode1 ItemName:" + itm.getType().name());

			player.sendMessage(dprint.r.color("your money:" + money));
			dprint.r.printC("your money:" + money);

			money = money - money2;
			player.sendMessage(dprint.r.color("Durability :" + dula + "/" + dulamax + "(pay money =" + money2 + ")"));
			dprint.r.printC("Durability :" + dula + "/" + dulamax + "(pay money =" + money2 + ")");

			dula = (short) (0);

			itm.setDurability(dula);
			Economy.setMoney(player.getName(), money);

			player.sendMessage(dprint.r
					.color("ptdew&dewdd:You used $" + money2 + " for repair " + itm.getType().name() + " Thanks"));

			dprint.r.printC("ptdew&dewdd:You used $" + money2 + " for repair " + itm.getType().name() + " Thanks");

			player.sendMessage(dprint.r.color("money left:" + money));
			dprint.r.printC("money left:" + money);

		}

	} // 0.5

} // dig