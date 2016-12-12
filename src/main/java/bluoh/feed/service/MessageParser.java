package bluoh.feed.service;

import bluoh.feed.model.Horoscope;

/**
 * Created by Ashutosh on 11-12-2016.
 */
public interface MessageParser {

    Horoscope parse(String message);
}
