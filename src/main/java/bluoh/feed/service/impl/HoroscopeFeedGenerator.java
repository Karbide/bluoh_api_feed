package bluoh.feed.service.impl;

import bluoh.feed.model.*;
import bluoh.feed.service.FeedGenerator;
import bluoh.feed.service.MessageParser;
import bluoh.feed.util.Connection;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ashutosh on 10-12-2016.
 */
@Component
public class HoroscopeFeedGenerator implements FeedGenerator,CommandLineRunner {

    private static String url = "http://www.findyourfate.com/rss/dailyhoroscope-feed.asp?sign={#sign}&id=45";
    private final MessageParser parser;
    private final Connection connection;
    private static Map<String,String> zodiacSigns = new HashMap<String, String>();

    static {
        zodiacSigns.put("Aries","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Aries_004075_32.jpg");
        zodiacSigns.put("Taurus","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Taurus_004080_32.jpg");
        zodiacSigns.put("Gemini","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Gemini_004069_32.jpg");
        zodiacSigns.put("Cancer","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Cancer_004076_32.jpg");
        zodiacSigns.put("Leo","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Leo_004074_32.jpg");
        zodiacSigns.put("Virgo","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Virgo_004072_32.jpg");
        zodiacSigns.put("Libra","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Libra_004070_32.jpg");
        zodiacSigns.put("Scorpio","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Scorpio_004078_32.jpg");
        zodiacSigns.put("Sagittarius","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Sagittarius_004079_32.jpg");
        zodiacSigns.put("Capricorn","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Capricorn_004073_32.jpg");
        zodiacSigns.put("Aquarius","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Aquarius_004071_32.jpg");
        zodiacSigns.put("Pisces","http://www.zastavki.com/pictures/286x180/2012/Zodiac_signs_Pisces_004077_32.jpg");
    }

    @Autowired
    public HoroscopeFeedGenerator(MessageParser messageParser, Connection connection) {
        this.parser = messageParser;
        this.connection = connection;
    }

    @Override
    public void run(String... strings) throws Exception {
        getFeed();
        System.exit(0);
    }

    @Override
    public void getFeed() {

        try {
            String[] sun_signs = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
            List<Horoscope> horoscopes = new ArrayList<Horoscope>();
            horoscopes.add(new Horoscope("Horoscopes",""));
            for (String sign : sun_signs) {
                horoscopes.add(parser.parse(url.replace("{#sign}", sign)));
            }
            updateDeck(horoscopes);
//            JSONObject object = new JSONObject(initializeDeck(horoscopes));
//            System.out.println(connection.sendPostWithBody("http://api.chequemate.io/deck/",object.toString()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void updateDeck(List<Horoscope> horoscopes) throws Exception{
        Map<String,String> updateIdMap = populateIdMap();
        for (Horoscope horoscope : horoscopes) {
            Card card = new Card();
            card.setContent(horoscope.getDescription());
            JSONObject object = new JSONObject(card);
            System.out.println(connection.sendPutWithBody("http://api.chequemate.io/card/"+updateIdMap.get(horoscope.getTitle()),object.toString()));
        }
    }

    private Map<String,String> populateIdMap() {
        Map<String,String> updateIdMap = new HashMap<String, String>();
        updateIdMap.put("Aries","584c92e7fe334c0fd78a31fa");
        updateIdMap.put("Taurus","584c92e7fe334c0fd78a31fb");
        updateIdMap.put("Gemini","584c92e7fe334c0fd78a31fc");
        updateIdMap.put("Cancer","584c92e7fe334c0fd78a31fd");
        updateIdMap.put("Leo","584c92e7fe334c0fd78a31fe");
        updateIdMap.put("Virgo","584c92e7fe334c0fd78a31ff");
        updateIdMap.put("Libra","584c92e7fe334c0fd78a3200");
        updateIdMap.put("Scorpio","584c92e7fe334c0fd78a3201");
        updateIdMap.put("Sagittarius","584c92e7fe334c0fd78a3202");
        updateIdMap.put("Capricorn","584c92e7fe334c0fd78a3203");
        updateIdMap.put("Aquarius","584c92e7fe334c0fd78a3204");
        updateIdMap.put("Pisces","584c92e7fe334c0fd78a3205");
        return updateIdMap;
    }

    private Deck initializeDeck(List<Horoscope> horoscopes) {
        Deck deck = new Deck();
        deck.setApprover(new Approver(1, "1", getCurrentDate(), ""));
        deck.setAuthor("@ashu");
        deck.setDisplayName("@ashu");
        deck.setType("deck");
        deck.setPublishedDate(getCurrentDate());
        deck.setCategories(Arrays.asList("Horoscopes"));
        deck.setTags(Arrays.asList("Horoscopes"));
        deck.setStatus("Draft");
        deck.setCards(generateCards(horoscopes));
        return deck;
    }

    private List<Card> generateCards(List<Horoscope> horoscopes) {
        List<Card> cards = new ArrayList<Card>();
        int start = 0;
        for (Horoscope horoscope : horoscopes) {
            start++;
            Card card = new Card();
            card.setAuthor("@ashu");
            card.setTitle(horoscope.getTitle());
            card.setContent(horoscope.getDescription());
            card.setSourceName("http://www.findyourfate.com/astrology/astrotop.jpg");
            card.setStatus("Draft");
            card.setTemplate("50_50");
            if(start>1){
                card.setMedia(new Media("image", zodiacSigns.get(horoscope.getTitle()), "karbide"));
            }else {
                card.setMedia(new Media("image", "https://jewellerybyjoe.files.wordpress.com/2011/02/zodiac-clock-3d-screensaver_2.png", "karbide"));
            }
            cards.add(card);
        }
        return cards;
    }

    private String getCurrentDate(){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.format(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}