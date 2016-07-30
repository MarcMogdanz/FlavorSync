package de.marcmogdanz.flavorsync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FlavorSync {
	
	private String lolPath;
	private List<String> champions = new ArrayList<String>();
	private MainWindow main;
	
	public FlavorSync(MainWindow main) {
		this.main = main;
		lolPath = null;
		this.setupChampionList();
	}
	
	public void startDownload() throws IOException {
		for(int x = 0; x < champions.size(); x++) {
			String laneURL;
			String build;
			for(int y = 0; y < 4; y++) {
				switch(y) {
					default:
					case 0:
						laneURL = this.buildURL(champions.get(x), Lane.LANE);
						build = this.getBuild(laneURL);
						if(build == null) {
							main.textArea.setText(main.textArea.getText() + champions.get(x) + " (Lane): not found\r\n");
						} else {
							this.saveBuildToFile(build, champions.get(x), "lane");
							main.textArea.setText(main.textArea.getText() + champions.get(x) + " (Lane): downloaded\r\n");
							System.out.println("test");
						}
						break;
					case 1:
						laneURL = this.buildURL(champions.get(x), Lane.SUPPORT);
						build = this.getBuild(laneURL);
						if(build == null) {
							main.textArea.setText(main.textArea.getText() + champions.get(x) + " (Support): not found\r\n");
						} else {
							this.saveBuildToFile(build, champions.get(x), "support");
							main.textArea.setText(main.textArea.getText() + champions.get(x) + " (Support): downloaded\r\n");
						}
						break;
					case 2:
						laneURL = this.buildURL(champions.get(x), Lane.JUNGLE);
						build = this.getBuild(laneURL);
						if(build == null) {
							main.textArea.setText(main.textArea.getText() + champions.get(x) + " (Jungle): not found\r\n");
						} else {
							this.saveBuildToFile(build, champions.get(x), "jungle");
							main.textArea.setText(main.textArea.getText() + champions.get(x) + " (Jungle): downloaded\r\n");
						}
						break;
					case 3:
						laneURL = this.buildURL(champions.get(x), Lane.ARAM);
						build = this.getBuild(laneURL);
						if(build == null) {
							main.textArea.setText(main.textArea.getText() + champions.get(x) + " (ARAM): not found\r\n");
						} else {
							this.saveBuildToFile(build, champions.get(x), "aram");
							main.textArea.setText(main.textArea.getText() + champions.get(x) + " (ARAM): downloaded\r\n");
						}
						break;
				}
			}
		}
	}
	
	private String getBuild(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		if(responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			return null;
		}
	}
	
	private void saveBuildToFile(String json, String champ, String lane) {
		if(!new File(this.getLoLPath() + "/" + champ).exists()) {
			new File(this.getLoLPath() + "/" + champ).mkdir();
			new File(this.getLoLPath() + "/" + champ + "/Recommended").mkdir();
		}
		try(PrintWriter out = new PrintWriter(this.lolPath + "/" + champ + "/Recommended/" + champ + "_" + lane + "_scrape.json")) {
			out.println(json);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String buildURL(String champion, Lane lane) {
		switch(lane) {
		default:
		case LANE:
			return "http://lolflavor.com/champions/" + champion + "/Recommended/" + champion + "_lane_scrape.json";
		case SUPPORT:
			return "http://lolflavor.com/champions/" + champion + "/Recommended/" + champion + "_support_scrape.json";
		case JUNGLE:
			return "http://lolflavor.com/champions/" + champion + "/Recommended/" + champion + "_jungle_scrape.json";
		case ARAM:
			return "http://lolflavor.com/champions/" + champion + "/Recommended/" + champion + "_aram_scrape.json";
		}
	}
	
	public void setLoLPath(String path) {
		this.lolPath = path;
	}
	
	public String getLoLPath() {
		return this.lolPath;
	}
	
	private void setupChampionList() {
		champions.add("Aatrox");
		champions.add("Ahri");
		champions.add("Akali");
		champions.add("Alistar");
		champions.add("Amumu");
		champions.add("Anivia");
		champions.add("Annie");
		champions.add("Ashe");
		champions.add("AurelionSol");
		champions.add("Azir");
		champions.add("Bard");
		champions.add("Blitzcrank");
		champions.add("Brand");
		champions.add("Braum");
		champions.add("Caitlyn");
		champions.add("Cassiopeia");
		champions.add("Chogath");
		champions.add("Corki");
		champions.add("Darius");
		champions.add("Diana");
		champions.add("Draven");
		champions.add("DrMundo");
		champions.add("Ekko");
		champions.add("Elise");
		champions.add("Evelynn");
		champions.add("Ezreal");
		champions.add("FiddleSticks");
		champions.add("Fiora");
		champions.add("Fizz");
		champions.add("Galio");
		champions.add("Gangplank");
		champions.add("Garen");
		champions.add("Gnar");
		champions.add("Gragas");
		champions.add("Graves");
		champions.add("Hecarim");
		champions.add("Heimerdinger");
		champions.add("Illaoi");
		champions.add("Irelia");
		champions.add("Janna");
		champions.add("JarvanIV");
		champions.add("Jax");
		champions.add("Jayce");
		champions.add("Jhin");
		champions.add("Jinx");
		champions.add("Kalista");
		champions.add("Karma");
		champions.add("Karthus");
		champions.add("Kassadin");
		champions.add("Katarina");
		champions.add("Kayle");
		champions.add("Kennen");
		champions.add("Khazix");
		champions.add("Kindred");
		champions.add("KogMaw");
		champions.add("Leblanc");
		champions.add("LeeSin");
		champions.add("Leona");
		champions.add("Lissandra");
		champions.add("Lucian");
		champions.add("Lulu");
		champions.add("Lux");
		champions.add("Malphite");
		champions.add("Malzahar");
		champions.add("Maokai");
		champions.add("MasterYi");
		champions.add("MissFortune");
		champions.add("Mordekaiser");
		champions.add("Morgana");
		champions.add("Nami");
		champions.add("Nasus");
		champions.add("Nautilus");
		champions.add("Nidalee");
		champions.add("Nocturne");
		champions.add("Nunu");
		champions.add("Olaf");
		champions.add("Orianna");
		champions.add("Pantheon");
		champions.add("Poppy");
		champions.add("Quinn");
		champions.add("Rammus");
		champions.add("RekSai");
		champions.add("Renekton");
		champions.add("Rengar");
		champions.add("Riven");
		champions.add("Rumble");
		champions.add("Ryze");
		champions.add("Sejuani");
		champions.add("Shaco");
		champions.add("Shen");
		champions.add("Shyvana");
		champions.add("Singed");
		champions.add("Sion");
		champions.add("Sivir");
		champions.add("Skarner");
		champions.add("Sona");
		champions.add("Soraka");
		champions.add("Swain");
		champions.add("Syndra");
		champions.add("TahmKench");
		champions.add("Taliyah");
		champions.add("Talon");
		champions.add("Taric");
		champions.add("Teemo");
		champions.add("Thresh");
		champions.add("Tristana");
		champions.add("Trundle");
		champions.add("Tryndamere");
		champions.add("TwistedFate");
		champions.add("Twitch");
		champions.add("Udyr");
		champions.add("Urgot");
		champions.add("Varus");
		champions.add("Vayne");
		champions.add("Veigar");
		champions.add("Velkoz");
		champions.add("Vi");
		champions.add("Viktor");
		champions.add("Vladimir");
		champions.add("Volibear");
		champions.add("Warwick");
		champions.add("MonkeyKing");
		champions.add("Xerath");
		champions.add("XinZhao");
		champions.add("Yasuo");
		champions.add("Yorick");
		champions.add("Zac");
		champions.add("Zed");
		champions.add("Ziggs");
		champions.add("Zilean");
		champions.add("Zyra");
		
	}
}
